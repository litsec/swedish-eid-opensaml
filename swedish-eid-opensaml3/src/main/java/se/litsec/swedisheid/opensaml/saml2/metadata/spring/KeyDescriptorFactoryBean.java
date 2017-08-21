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
package se.litsec.swedisheid.opensaml.saml2.metadata.spring;

import java.security.cert.X509Certificate;
import java.util.List;

import org.opensaml.saml.saml2.metadata.EncryptionMethod;
import org.opensaml.saml.saml2.metadata.KeyDescriptor;
import org.opensaml.security.credential.UsageType;
import org.opensaml.xmlsec.signature.KeyInfo;

import se.litsec.swedisheid.opensaml.saml2.AbstractXMLObjectBuilder;
import se.litsec.swedisheid.opensaml.saml2.metadata.builders.KeyDescriptorBuilder;
import se.litsec.swedisheid.opensaml.saml2.spring.AbstractXMLObjectBuilderFactoryBean;

/**
 * A Spring factory bean for creating {@link KeyDescriptor} objects.
 * 
 * @author Martin Lindström (martin.lindstrom@litsec.se)
 * @see KeyDescriptorBuilder
 */
public class KeyDescriptorFactoryBean extends AbstractXMLObjectBuilderFactoryBean<KeyDescriptor> {

  /** The builder. */
  private KeyDescriptorBuilder builder;

  /**
   * Default constructor.
   */
  public KeyDescriptorFactoryBean() {
    this.builder = new KeyDescriptorBuilder();
  }

  /**
   * @see KeyDescriptorBuilder#usage(UsageType)
   */
  public void setUsage(UsageType usage) {
    this.builder.usage(usage);
  }

  /**
   * @see KeyDescriptorBuilder#keyInfoName(String)
   */
  public void setKeyInfoName(String name) {
    this.builder.keyInfoName(name);
  }
  
  /**
   * @see KeyDescriptorBuilder#keyInfoCertificate(X509Certificate)
   */
  public void setKeyInfoCertificate(X509Certificate certificate) {
    this.builder.keyInfoCertificate(certificate);
  }
  
  /**
   * @see KeyDescriptorBuilder#keyInfo(KeyInfo)
   */
  public void setKeyInfo(KeyInfo keyInfo) {
    this.builder.keyInfo(keyInfo);
  }
  
  /**
   * @see KeyDescriptorBuilder#encryptionMethods(List)
   */
  public void setEncryptionMethods(List<EncryptionMethod> encryptionMethods) {
    this.builder.encryptionMethods(encryptionMethods);
  }

  /** {@inheritDoc} */
  @Override
  protected AbstractXMLObjectBuilder<KeyDescriptor> builder() {
    return this.builder;
  }

  /** {@inheritDoc} */
  @Override
  public Class<?> getObjectType() {
    return KeyDescriptor.class;
  }

}
