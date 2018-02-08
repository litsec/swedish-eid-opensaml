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

import java.util.Collection;
import java.util.List;

import org.opensaml.security.credential.Credential;
import org.opensaml.xmlsec.DecryptionParameters;
import org.opensaml.xmlsec.encryption.support.Decrypter;
import org.opensaml.xmlsec.encryption.support.DecryptionException;
import org.opensaml.xmlsec.encryption.support.EncryptedKeyResolver;
import org.opensaml.xmlsec.encryption.support.InlineEncryptedKeyResolver;
import org.opensaml.xmlsec.keyinfo.KeyInfoCredentialResolver;
import org.opensaml.xmlsec.keyinfo.impl.StaticKeyInfoCredentialResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.litsec.swedisheid.opensaml.saml2.signservice.dss.Message;
import se.litsec.swedisheid.opensaml.saml2.signservice.dss.SignMessage;

/**
 * A bean for decrypting encrypted messages within {@link SignMessage} objects.
 * 
 * @author Martin Lindström (martin.lindstrom@litsec.se)
 */
public class SignMessageDecrypter {

  /** Logger instance. */
  private Logger logger = LoggerFactory.getLogger(SignMessageDecrypter.class);

  /** The resolver for key encryption keys. */
  private KeyInfoCredentialResolver keyEncryptionKeyResolver;

  /** The resolver for encrypted keys - always inlined for EncryptedMessage. */
  private EncryptedKeyResolver encryptedKeyResolver = new InlineEncryptedKeyResolver();

  /** Optional black list of algorithms. */
  private Collection<String> blacklistedAlgorithms;

  /** Optional white list of algorithms. */
  private Collection<String> whitelistedAlgorithms;

  /** The decrypter. */
  private Decrypter decrypter;

  /**
   * Constructor given the credential to use to decrypt the messages (certificate or key pair)
   * 
   * @param decryptionCredential
   *          decryption credential
   */
  public SignMessageDecrypter(Credential decryptionCredential) {
    this.keyEncryptionKeyResolver = new StaticKeyInfoCredentialResolver(decryptionCredential);
  }

  /**
   * Constructor accepting several credentials (certificates or key pairs) to be used when decrypting. This may be
   * useful after a key rollover.
   * 
   * @param decryptionCredentials
   *          decryption credentials
   */
  public SignMessageDecrypter(List<Credential> decryptionCredentials) {
    this.keyEncryptionKeyResolver = new StaticKeyInfoCredentialResolver(decryptionCredentials);
  }

  /**
   * Constructor accepting a key encryption key resolver.
   * 
   * @param keyEncryptionKeyResolver
   *          the resolver
   */
  public SignMessageDecrypter(KeyInfoCredentialResolver keyEncryptionKeyResolver) {
    this.keyEncryptionKeyResolver = keyEncryptionKeyResolver;
  }

  /**
   * Decrypts the encrypted message of a {@link SignMessage} and returns the cleartext {@code Message}.
   * 
   * @param signMessage
   *          the element holding the encrypted message
   * @return a cleartext {@code Message} element
   * @throws DecryptionException
   *           for decryption errors
   */
  public Message decrypt(SignMessage signMessage) throws DecryptionException {
    if (signMessage.getEncryptedMessage() == null && signMessage.getMessage() != null) {
      logger.info("No decryption required - SignMessage contains cleartext message");
      return signMessage.getMessage();
    }
    if (signMessage.getEncryptedMessage() == null) {
      final String msg = "No message available";
      logger.error(msg);
      throw new DecryptionException(msg);
    }
    return (Message) this.getDecrypter().decryptData(signMessage.getEncryptedMessage().getEncryptedData());
  }

  /**
   * Returns the decrypter to use.
   * 
   * @return the decrypter
   */
  private Decrypter getDecrypter() {
    if (this.decrypter == null) {
      DecryptionParameters pars = new DecryptionParameters();
      pars.setKEKKeyInfoCredentialResolver(this.keyEncryptionKeyResolver);
      pars.setEncryptedKeyResolver(this.encryptedKeyResolver);
      pars.setBlacklistedAlgorithms(this.blacklistedAlgorithms);
      pars.setWhitelistedAlgorithms(this.whitelistedAlgorithms);
      this.decrypter = new Decrypter(pars);
    }
    return this.decrypter;
  }

  /**
   * Assigns a list of black listed algorithms
   * 
   * @param blacklistedAlgorithms
   *          non allowed algorithms
   */
  public void setBlacklistedAlgorithms(Collection<String> blacklistedAlgorithms) {
    this.blacklistedAlgorithms = blacklistedAlgorithms;
  }

  /**
   * Assigns a list of white listed algorithms
   * 
   * @param whitelistedAlgorithms
   *          white listed algorithms
   */
  public void setWhitelistedAlgorithms(Collection<String> whitelistedAlgorithms) {
    this.whitelistedAlgorithms = whitelistedAlgorithms;
  }

}
