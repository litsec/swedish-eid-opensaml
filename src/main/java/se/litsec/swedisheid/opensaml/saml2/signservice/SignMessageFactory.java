/*
 * Copyright 2016-2019 Litsec AB
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package se.litsec.swedisheid.opensaml.saml2.signservice;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.opensaml.saml.criterion.RoleDescriptorCriterion;
import org.opensaml.saml.saml2.metadata.IDPSSODescriptor;
import org.opensaml.saml.security.impl.MetadataCredentialResolver;
import org.opensaml.security.credential.Credential;
import org.opensaml.security.credential.UsageType;
import org.opensaml.security.criteria.UsageCriterion;
import org.opensaml.xmlsec.encryption.EncryptedData;
import org.opensaml.xmlsec.encryption.support.DataEncryptionParameters;
import org.opensaml.xmlsec.encryption.support.Encrypter;
import org.opensaml.xmlsec.encryption.support.EncryptionConstants;
import org.opensaml.xmlsec.encryption.support.EncryptionException;
import org.opensaml.xmlsec.encryption.support.KeyEncryptionParameters;
import org.opensaml.xmlsec.keyinfo.impl.BasicProviderKeyInfoCredentialResolver;
import org.opensaml.xmlsec.keyinfo.impl.KeyInfoProvider;
import org.opensaml.xmlsec.keyinfo.impl.provider.DSAKeyValueProvider;
import org.opensaml.xmlsec.keyinfo.impl.provider.InlineX509DataProvider;
import org.opensaml.xmlsec.keyinfo.impl.provider.RSAKeyValueProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import net.shibboleth.utilities.java.support.resolver.ResolverException;
import se.litsec.opensaml.saml2.metadata.provider.MetadataProvider;
import se.litsec.opensaml.utils.ObjectUtils;
import se.litsec.swedisheid.opensaml.saml2.signservice.dss.EncryptedMessage;
import se.litsec.swedisheid.opensaml.saml2.signservice.dss.Message;
import se.litsec.swedisheid.opensaml.saml2.signservice.dss.SignMessage;
import se.litsec.swedisheid.opensaml.saml2.signservice.dss.SignMessageMimeTypeEnum;

/**
 * A builder for an easy way to create and encrypt messages for
 * {@link se.litsec.swedisheid.opensaml.saml2.signservice.dss.SignMessage}.
 * <p>
 * Deprecated. Use {@link SignMessageBuilder} and {@link SignMessageEncrypter} instead.
 * </p>
 * 
 * @author Martin Lindström (martin.lindstrom@litsec.se)
 */
@Deprecated
public class SignMessageFactory {

  /** Logger instance. */
  private Logger logger = LoggerFactory.getLogger(SignMessageFactory.class);

  /** The metadata where to find an IdP:s credentials. */
  private MetadataProvider metadataProvider;

  /** Finds encryption credentials from metadata. */
  private MetadataCredentialResolver credentialResolver;

  /**
   * The encryption algorithm to use when encrypting messages. The default is
   * {@link org.opensaml.xml.encryption.EncryptionConstants#ALGO_ID_BLOCKCIPHER_AES128}.
   */
  private String encryptionAlgorithmId = EncryptionConstants.ALGO_ID_BLOCKCIPHER_AES128;

  /**
   * Constructor.
   * 
   * @param federationMetadataProvider
   *          the federation metadata that is used for locating IdP encryption keys
   */
  public SignMessageFactory(MetadataProvider federationMetadataProvider) {
    this.metadataProvider = federationMetadataProvider;

    this.credentialResolver = new MetadataCredentialResolver();
    List<KeyInfoProvider> keyInfoProviders = new ArrayList<>();
    keyInfoProviders.add(new DSAKeyValueProvider());
    keyInfoProviders.add(new RSAKeyValueProvider());
    keyInfoProviders.add(new InlineX509DataProvider());
    this.credentialResolver.setKeyInfoCredentialResolver(new BasicProviderKeyInfoCredentialResolver(keyInfoProviders));
  }

  /**
   * Creates a {@code SignMessage} object.
   * 
   * @param message
   *          the message to include (in cleartext)
   * @param mimeType
   *          the MIME type of the message
   * @param mustShow
   *          when this parameter is set to {@code true} then the requested signature MUST NOT be created unless this
   *          message has been displayed and accepted by the signer
   * @param displayEntity
   *          the entityID of the entity responsible for displaying the sign message to the signer. When the sign
   *          message is encrypted, then this entity is also the holder of the private decryption key necessary to
   *          decrypt the sign message
   * @param encrypt
   *          should the message be encryped?
   * @return a {@code SignMessage} object
   * @throws ResolverException
   *           for metadata related errors
   * @throws EncryptionException
   *           for encryption errors
   */
  public SignMessage create(String message, SignMessageMimeTypeEnum mimeType, Boolean mustShow, String displayEntity, boolean encrypt)
      throws ResolverException, EncryptionException {

    SignMessage signMessage = ObjectUtils.createSamlObject(SignMessage.class);
    signMessage.setDisplayEntity(displayEntity);
    signMessage.setMimeType(mimeType);
    signMessage.setMustShow(mustShow);

    Message msg = ObjectUtils.createXMLObject(Message.class, Message.DEFAULT_ELEMENT_NAME);
    msg.setContent(message);

    if (encrypt) {

      if (displayEntity == null) {
        throw new IllegalArgumentException("create invoked with no displayEntity. This is required for creating encrypted messages.");
      }

      // Locate the IdP encryption key.
      Credential keyEncryptionCredential = this.getKeyEncryptionCredential(displayEntity);
      if (keyEncryptionCredential == null) {
        throw new EncryptionException("No valid encryption key was found for IdP " + displayEntity);
      }

      // Encrypt
      EncryptedMessage encryptedMessage = this.encrypt(msg, keyEncryptionCredential);
      signMessage.setEncryptedMessage(encryptedMessage);
    }
    else {
      signMessage.setMessage(msg);
    }

    return signMessage;
  }

  /**
   * Locates a encryption credential for the given IdP from the federaion metadata.
   * 
   * @param idpEntityID
   *          the IdP entityID
   * @return an encryption credential, or {@code null} if none is found
   */
  public Credential getKeyEncryptionCredential(String idpEntityID) {

    try {
      Optional<IDPSSODescriptor> idpMetadata = this.metadataProvider.getIDPSSODescriptor(idpEntityID);
      if (!idpMetadata.isPresent()) {
        logger.error("Failed to find metadata for IdP '{}'", idpEntityID);
        return null;
      }

      CriteriaSet criteriaSet = new CriteriaSet();
      criteriaSet.add(new RoleDescriptorCriterion(idpMetadata.get()));
      criteriaSet.add(new UsageCriterion(UsageType.ENCRYPTION));

      Iterable<Credential> credentials = this.credentialResolver.resolve(criteriaSet);
      Iterator<Credential> i = credentials.iterator();
      while (i.hasNext()) {
        Credential c = i.next();
        logger.debug("Found encryption key of type '{}' for IdP '{}'", c.getCredentialType().getName(), idpEntityID);
        return c;
      }
      // OK, no key with encryption usage was to be found. Let's try with unspecified usage.
      criteriaSet.add(new UsageCriterion(UsageType.UNSPECIFIED), true);
      credentials = this.credentialResolver.resolve(criteriaSet);
      i = credentials.iterator();
      while (i.hasNext()) {
        Credential c = i.next();
        // There is a bug in OpenSAML that returns also credentials for signing when we specify
        // unspecified. Therefore, we have to check this.
        if (c.getUsageType() != null && c.getUsageType().equals(UsageType.SIGNING)) {
          continue;
        }
        logger.debug("Found encryption key of type '{}' for IdP '{}'", c.getCredentialType().getName(), idpEntityID);
        return c;
      }
      logger.info("Failed to find valid encryption key for IdP '{}'", idpEntityID);
      return null;
    }
    catch (ResolverException e) {
      logger.error("Failed to find encryption key for IdP '{}' - {}", idpEntityID, e.getMessage(), e);
      return null;
    }
  }

  /**
   * Given a {@code Message} object and key encryption credentials the method encrypts the message into a
   * {@code EncryptedMessage} object.
   * 
   * @param message
   *          the message to encrypt
   * @param credential
   *          the key encryption credential (IdP public key/certificate)
   * @return an {@code EncryptedMessage} object
   * @throws EncryptionException
   *           for encryption errors
   */
  public EncryptedMessage encrypt(Message message, Credential credential) throws EncryptionException {

    DataEncryptionParameters dataEncryptionParameters = new DataEncryptionParameters();
    dataEncryptionParameters.setAlgorithm(this.encryptionAlgorithmId);

    // EncryptionParameters encParams = new EncryptionParameters();
    // encParams.setDataEncryptionAlgorithm(this.encryptionAlgorithmId);

    KeyEncryptionParameters kekParams = new KeyEncryptionParameters();
    kekParams.setEncryptionCredential(credential);
    // kekParams.setAlgorithm(EncryptionConstants.ALGO_ID_KEYTRANSPORT_RSAOAEP); // TODO: make configurable
    kekParams.setAlgorithm(EncryptionConstants.ALGO_ID_KEYTRANSPORT_RSA15);

    // KeyInfoGeneratorFactory kigf = Configuration.getGlobalSecurityConfiguration()
    // .getKeyInfoGeneratorManager()
    // .getDefaultManager()
    // .getFactory(credential);
    // kekParams.setKeyInfoGenerator(kigf.newInstance());

    Encrypter encrypter = new Encrypter();
    EncryptedData encryptedData = encrypter.encryptElement(message, dataEncryptionParameters, kekParams);

    EncryptedMessage encryptedMessage = ObjectUtils.createSamlObject(EncryptedMessage.class);
    encryptedMessage.setEncryptedData(encryptedData);

    return encryptedMessage;
  }

  /**
   * Assigns the encryption algorithm to use when encrypting messages.
   * <p>
   * The default is {@link EncryptionConstants#ALGO_ID_BLOCKCIPHER_AES128}.
   * </p>
   * <p>
   * Note that if an algorithm that uses larger keys is required the JCE unlimited strength policy files must be
   * installed. For Java 8, download it from
   * <a href="http://www.oracle.com/technetwork/java/javase/downloads/jce8-download-2133166.html">http://www.oracle.com/
   * technetwork/java/javase/downloads/jce8-download-2133166.html</a>.
   * </p>
   * 
   * @param encryptionAlgorithmId
   *          the algorithm to assign
   */
  public void setEncryptionAlgorithmId(String encryptionAlgorithmId) {
    this.encryptionAlgorithmId = encryptionAlgorithmId;
  }

}
