/*
 * Copyright 2016-2018 Litsec AB
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
import se.litsec.opensaml.core.AbstractSAMLObjectBuilder;
import se.litsec.opensaml.saml2.metadata.provider.MetadataProvider;
import se.litsec.opensaml.utils.ObjectUtils;
import se.litsec.swedisheid.opensaml.saml2.signservice.dss.EncryptedMessage;
import se.litsec.swedisheid.opensaml.saml2.signservice.dss.Message;
import se.litsec.swedisheid.opensaml.saml2.signservice.dss.SignMessage;
import se.litsec.swedisheid.opensaml.saml2.signservice.dss.SignMessageMimeTypeEnum;

/**
 * Creates a {@link SignMessage} instance using the builder patterns.
 * 
 * @author Martin Lindström (martin.lindstrom@litsec.se)
 */
public class SignMessageBuilder extends AbstractSAMLObjectBuilder<SignMessage> {

  /** Logger instance. */
  private Logger logger = LoggerFactory.getLogger(SignMessageBuilder.class);

  /**
   * The data encryption parameters to use when (if) encrypting. By default the algorithm
   * {@link org.opensaml.xml.encryption.EncryptionConstants#ALGO_ID_BLOCKCIPHER_AES256} is used.
   */
  private DataEncryptionParameters dataEncryptionParameters;

  /**
   * The key encryption parameters to use when (if) encrypting. By default the algorithm
   * {@link EncryptionConstants#ALGO_ID_KEYTRANSPORT_RSA15} is used.
   */
  private KeyEncryptionParameters keyEncryptionParameters;

  /**
   * Utility method that creates a builder.
   * 
   * @return a builder
   */
  public static SignMessageBuilder builder() {
    return new SignMessageBuilder();
  }

  public SignMessage buildEncrypted(Credential keyEncryptionCredential) throws EncryptionException {

    if (this.object().getMessage() == null || this.object().getMessage().getValue() == null) {
      throw new EncryptionException("No message to encrypt has been installed");
    }

    String keAlgo = null;

    try {
      if (this.dataEncryptionParameters == null) {
        this.dataEncryptionParameters = new DataEncryptionParameters();
        this.dataEncryptionParameters.setAlgorithm(EncryptionConstants.ALGO_ID_BLOCKCIPHER_AES256);
      }

      KeyEncryptionParameters kep;
      if (this.keyEncryptionParameters == null) {
        kep = new KeyEncryptionParameters();
        kep.setAlgorithm(EncryptionConstants.ALGO_ID_KEYTRANSPORT_RSA15);
      }
      else {
        keAlgo = this.keyEncryptionParameters.getAlgorithm();
        kep = this.keyEncryptionParameters;
      }
      kep.setEncryptionCredential(keyEncryptionCredential);

      Message msg = ObjectUtils.createXMLObject(Message.class, Message.DEFAULT_ELEMENT_NAME);
      msg.setValue(this.object().getMessage().getValue());

      Encrypter encrypter = new Encrypter();
      EncryptedData encryptedData = encrypter.encryptElement(msg, this.dataEncryptionParameters, kep);

      EncryptedMessage encryptedMessage = ObjectUtils.createSamlObject(EncryptedMessage.class);
      encryptedMessage.setEncryptedData(encryptedData);

      this.object().setMessage(null);
      this.object().setEncryptedMessage(encryptedMessage);

      return this.object();
    }
    finally {
      // Restore installed parameter object.
      if (this.keyEncryptionParameters != null && keAlgo != null) {
        this.keyEncryptionParameters.setAlgorithm(keAlgo);
      }
    }
  }

  public SignMessage buildEncrypted(MetadataProvider metadataProvider) throws EncryptionException {

    if (this.object().getDisplayEntity() == null) {
      throw new EncryptionException("The displayEntity attribute is required for encrypted messages");
    }
    String idp = this.object().getDisplayEntity();

    try {
      // Finds encryption credentials from metadata.
      //
      MetadataCredentialResolver credentialResolver = new MetadataCredentialResolver();
      List<KeyInfoProvider> keyInfoProviders = new ArrayList<>();
      keyInfoProviders.add(new DSAKeyValueProvider());
      keyInfoProviders.add(new RSAKeyValueProvider());
      keyInfoProviders.add(new InlineX509DataProvider());
      credentialResolver.setKeyInfoCredentialResolver(new BasicProviderKeyInfoCredentialResolver(keyInfoProviders));

      Optional<IDPSSODescriptor> idpMetadata = metadataProvider.getIDPSSODescriptor(idp);
      if (!idpMetadata.isPresent()) {
        String msg = String.format("Failed to find metadata for IdP '%s' - can not encrypt message", idp);
        logger.error(msg);
        throw new EncryptionException(msg);
      }

      Credential keyEncryptionCredential = null;

      CriteriaSet criteriaSet = new CriteriaSet();
      criteriaSet.add(new RoleDescriptorCriterion(idpMetadata.get()));
      criteriaSet.add(new UsageCriterion(UsageType.ENCRYPTION));

      Iterable<Credential> credentials = credentialResolver.resolve(criteriaSet);
      Iterator<Credential> i = credentials.iterator();
      while (i.hasNext()) {
        keyEncryptionCredential = i.next();
        logger.debug("Found encryption key of type '{}' for IdP '{}'", keyEncryptionCredential.getCredentialType().getName(), idp);
        break;
      }

      if (keyEncryptionCredential == null) {
        // OK, no key with encryption usage was to be found. Let's try with unspecified usage.
        criteriaSet.add(new UsageCriterion(UsageType.UNSPECIFIED), true);
        credentials = credentialResolver.resolve(criteriaSet);
        i = credentials.iterator();
        while (i.hasNext()) {
          Credential c = i.next();
          // There is a bug in OpenSAML that returns also credentials for signing when we specify
          // unspecified. Therefore, we have to check this.
          if (c.getUsageType() != null && c.getUsageType().equals(UsageType.SIGNING)) {
            continue;
          }
          logger.debug("Found encryption key of type '{}' for IdP '{}'", c.getCredentialType().getName(), idp);
          keyEncryptionCredential = c;
          break;
        }
      }

      if (keyEncryptionCredential == null) {
        String msg = String.format("Failed to find valid encryption key for IdP '%s'", idp);
        logger.warn(msg);
        throw new EncryptionException(msg);
      }

      return this.buildEncrypted(keyEncryptionCredential);
    }
    catch (ResolverException e) {
      String msg = String.format("Failed to find encryption key for IdP '%s' - %s", idp, e.getMessage());
      logger.warn(msg, e);
      throw new EncryptionException(msg);
    }
  }

  /**
   * Assigns the data encryption parameters to use when encrypting. The default is to use the
   * {@link EncryptionConstants#ALGO_ID_BLOCKCIPHER_AES256} algorithm.
   * 
   * <p>
   * Note the JCE unlimited strength policy files must be installed for using strong crypto. For Java 8, download it
   * from
   * <a href="http://www.oracle.com/technetwork/java/javase/downloads/jce8-download-2133166.html">http://www.oracle.com/
   * technetwork/java/javase/downloads/jce8-download-2133166.html</a>.
   * </p>
   * 
   * @param parameters
   *          data encryption parameters
   * @return the builder
   */
  public SignMessageBuilder dataEncryptionParameters(DataEncryptionParameters parameters) {
    this.dataEncryptionParameters = parameters;
    return this;
  }

  /**
   * Assigns the key encryption parameters to use when encrypting. The default is to use the
   * {@link EncryptionConstants#ALGO_ID_KEYTRANSPORT_RSA15} algorithm.
   * 
   * <p>
   * Note: The encryption credential ({@link KeyEncryptionParameters#setEncryptionCredential(Credential)}) should not be
   * given in the supplied parameter. This will be added by the {@code buildEncrypted} methods.
   * </p>
   * <p>
   * Note that if an algorithm that uses larger keys is required the JCE unlimited strength policy files must be
   * installed. For Java 8, download it from
   * <a href="http://www.oracle.com/technetwork/java/javase/downloads/jce8-download-2133166.html">http://www.oracle.com/
   * technetwork/java/javase/downloads/jce8-download-2133166.html</a>.
   * </p>
   * 
   * @param parameters
   *          key encryption parameters
   * @return the builder
   */
  public SignMessageBuilder keyEncryptionParameters(KeyEncryptionParameters parameters) {
    this.keyEncryptionParameters = parameters;
    return this;
  }

  /**
   * Assigns the message to include.
   * <p>
   * If the message should be encrypted, the {@link SignMessage} should be built using
   * {@link #buildEncrypted(Credential)} or {@link #buildEncrypted(MetadataProvider)}. For sign messages in cleartext
   * use the ordinary {@link #build()} method.
   * </p>
   * 
   * @param message
   *          the message to include (in cleartext)
   * @return the builder
   */
  public SignMessageBuilder message(String message) {
    Message msg = ObjectUtils.createXMLObject(Message.class, Message.DEFAULT_ELEMENT_NAME);
    msg.setContent(message);
    this.object().setMessage(msg);
    return this;
  }

  /**
   * Assigns the entityID of the entity responsible for displaying the sign message to the signer. When the sign message
   * is encrypted, then this entity is also the holder of the private decryption key necessary to decrypt the sign
   * message.
   * 
   * @param displayEntity
   *          the entityID of the recipient
   * @return the builder
   */
  public SignMessageBuilder displayEntity(String displayEntity) {
    this.object().setDisplayEntity(displayEntity);
    return this;
  }

  /**
   * Assigns the MIME type of the message.
   * 
   * @param mimeType
   *          the MIME type
   * @return the builder
   */
  public SignMessageBuilder mimeType(SignMessageMimeTypeEnum mimeType) {
    this.object().setMimeType(mimeType);
    return this;
  }

  /**
   * Assigns the {@code MustShow} attribute. When this parameter is set to {@code true} then the requested signature
   * MUST NOT be created unless this message has been displayed and accepted by the signer.
   * 
   * @param mustShow
   *          the must show flag
   * @return the builder
   */
  public SignMessageBuilder mustShow(Boolean mustShow) {
    this.object().setMustShow(mustShow);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  protected Class<SignMessage> getObjectType() {
    return SignMessage.class;
  }

}
