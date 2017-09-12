/*
 * The swedish-eid-opensaml project is an open-source package that extends OpenSAML
 * with functions for the Swedish eID Framework.
 *
 * More details on <https://github.com/litsec/swedish-eid-opensaml>
 * Copyright (C) 2016-2017 Litsec AB
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package se.litsec.swedisheid.opensaml.saml2.signservice;

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
  private List<String> blacklistedAlgorithms;

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
  public void setBlacklistedAlgorithms(List<String> blacklistedAlgorithms) {
    this.blacklistedAlgorithms = blacklistedAlgorithms;
  }

}
