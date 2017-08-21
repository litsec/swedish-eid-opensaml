/*
 * The swedish-eid-opensaml project is an open-source package that extends OpenSAML
 * with functions for the Swedish eID Framework.
 *
 * More details on <https://github.com/litsec/swedish-eid-opensaml>
 * Copyright (C) 2016 Litsec AB
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
package se.litsec.swedisheid.opensaml.saml2.metadata.builders;

import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.saml.saml2.metadata.EncryptionMethod;
import org.opensaml.saml.saml2.metadata.KeyDescriptor;
import org.opensaml.security.credential.UsageType;
import org.opensaml.xmlsec.signature.KeyInfo;
import org.opensaml.xmlsec.signature.KeyName;
import org.opensaml.xmlsec.signature.X509Data;

import se.litsec.swedisheid.opensaml.saml2.AbstractXMLObjectBuilder;
import se.litsec.swedisheid.opensaml.utils.SAMLUtils;

/**
 * A builder for {@code KeyDescriptor} elements.
 * 
 * @author Martin Lindström (martin.lindstrom@litsec.se)
 */
public class KeyDescriptorBuilder extends AbstractXMLObjectBuilder<KeyDescriptor> {

  /**
   * Default constructor.
   */
  public KeyDescriptorBuilder() {
  }

  /**
   * Constructor that initializes the builder with the descriptor usage and a {@code KeyInfo} element with a name and
   * certificate.
   * 
   * @param usage
   *          the usage
   * @param keyInfoName
   *          the key info name
   * @param keyInfoCertificate
   *          the key info certificate
   */
  public KeyDescriptorBuilder(UsageType usage, String keyInfoName, X509Certificate keyInfoCertificate) {
    this.usage(usage).keyInfoName(keyInfoName).keyInfoCertificate(keyInfoCertificate);
  }

  /**
   * Utility method that builds a {@code KeyDescriptor} object from the supplied usage, key name and certificate.
   * 
   * @param usage
   *          the usage
   * @param keyInfoName
   *          the key info name
   * @param keyInfoCertificate
   *          the key info certificate
   * @return a {@code KeyDescriptor} object
   */
  public static KeyDescriptor build(UsageType usage, String keyInfoName, X509Certificate keyInfoCertificate) {
    KeyDescriptorBuilder builder = new KeyDescriptorBuilder(usage, keyInfoName, keyInfoCertificate);
    return builder.build();
  }

  /** {@inheritDoc} */
  @Override
  protected Class<KeyDescriptor> getObjectType() {
    return KeyDescriptor.class;
  }

  /**
   * Assigns the usage type
   * 
   * @param usage
   *          the usage
   * @return the builder
   */
  public KeyDescriptorBuilder usage(UsageType usage) {
    this.object().setUse(usage);
    return this;
  }

  /**
   * A short-cut method that sets a {@code KeyName} element of the {@code KeyInfo} element (which is created on the fly
   * if not already present).
   * 
   * @param name
   *          the name to assign
   * @return the builder
   * @see #keyInfo(KeyInfo)
   */
  public KeyDescriptorBuilder keyInfoName(String name) {
    if (name == null) {
      if (this.object().getKeyInfo() != null) {
        this.object().getKeyInfo().getKeyNames().clear();
      }
      return this;
    }
    if (this.object().getKeyInfo() == null) {
      this.object().setKeyInfo(SAMLUtils.createXMLObject(KeyInfo.class, KeyInfo.DEFAULT_ELEMENT_NAME));
    }
    this.object().getKeyInfo().getKeyNames().clear();
    KeyName keyName = SAMLUtils.createXMLObject(KeyName.class, KeyName.DEFAULT_ELEMENT_NAME);
    keyName.setValue(name);
    this.object().getKeyInfo().getKeyNames().add(keyName);
    return this;
  }

  /**
   * A short-cut method that assigns a certificate to the {@code KeyInfo} element (which is created on the fly if not
   * already present).
   * 
   * @param certificate
   *          the certificate to assign
   * @return the builder
   * @see #keyInfo(KeyInfo)
   */
  public KeyDescriptorBuilder keyInfoCertificate(X509Certificate certificate) {
    if (certificate == null) {
      if (this.object().getKeyInfo() != null) {
        this.object().getKeyInfo().getX509Datas().clear();
      }
      return this;
    }
    if (this.object().getKeyInfo() == null) {
      this.object().setKeyInfo(SAMLUtils.createXMLObject(KeyInfo.class, KeyInfo.DEFAULT_ELEMENT_NAME));
    }
    X509Data x509Data = SAMLUtils.createXMLObject(X509Data.class, X509Data.DEFAULT_ELEMENT_NAME);
    org.opensaml.xmlsec.signature.X509Certificate cert = SAMLUtils.createXMLObject(
      org.opensaml.xmlsec.signature.X509Certificate.class, org.opensaml.xmlsec.signature.X509Certificate.DEFAULT_ELEMENT_NAME);
    try {
      cert.setValue(Base64.getEncoder().encodeToString(((X509Certificate) certificate).getEncoded()));
    }
    catch (CertificateEncodingException e) {
      throw new SecurityException(e);
    }
    x509Data.getX509Certificates().add(cert);
    this.object().getKeyInfo().getX509Datas().add(x509Data);
    return this;
  }

  /**
   * Assigns the {@code KeyInfo} element.
   * <p>
   * If only a certificate and a key name is to be added to a {@code KeyInfo} element (which is the most common case),
   * you may use the {@link #keyInfoName(String)} and {@link #keyInfoCertificate(X509Certificate)} methods instead.
   * </p>
   * 
   * @param keyInfo
   *          the key info to add (will be cloned before assignment)
   * @return the builder
   * @see #keyInfoName(String)
   * @see #keyInfoCertificate(X509Certificate)
   */
  public KeyDescriptorBuilder keyInfo(KeyInfo keyInfo) {
    try {
      this.object().setKeyInfo(XMLObjectSupport.cloneXMLObject(keyInfo));
    }
    catch (MarshallingException | UnmarshallingException e) {
      throw new RuntimeException(e);
    }
    return this;
  }

  /**
   * Assigns the encryption methods to use.
   * <p>
   * Note that any previous encryption methods will be overwritten.
   * </p>
   * 
   * @param encryptionMethods
   *          the methods to assign (will be cloned before assignment)
   * @return the builder
   */
  public KeyDescriptorBuilder encryptionMethods(List<EncryptionMethod> encryptionMethods) {
    this.object().getEncryptionMethods().clear();
    if (encryptionMethods != null) {
      for (EncryptionMethod em : encryptionMethods) {
        try {
          this.object().getEncryptionMethods().add(XMLObjectSupport.cloneXMLObject(em));
        }
        catch (MarshallingException | UnmarshallingException e) {
          throw new RuntimeException(e);
        }
      }
    }
    return this;
  }

  /**
   * Assigns the encryption methods to use.
   * <p>
   * Note that any previous encryption methods will be overwritten.
   * </p>
   * 
   * @param encryptionMethods
   *          the methods to assign (will be cloned before assignment)
   * @return the builder
   */
  public KeyDescriptorBuilder encryptionMethods(EncryptionMethod... encryptionMethods) {
    return this.encryptionMethods(encryptionMethods != null ? Arrays.asList(encryptionMethods) : (List<EncryptionMethod>) null);
  }

}
