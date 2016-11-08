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

import static se.litsec.swedisheid.opensaml.utils.InputUtils.trim;

import java.io.IOException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.saml.ext.saml2mdattr.EntityAttributes;
import org.opensaml.saml.ext.saml2mdui.UIInfo;
import org.opensaml.saml.saml2.core.Attribute;
import org.opensaml.saml.saml2.metadata.ContactPerson;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml.saml2.metadata.Extensions;
import org.opensaml.saml.saml2.metadata.KeyDescriptor;
import org.opensaml.saml.saml2.metadata.NameIDFormat;
import org.opensaml.saml.saml2.metadata.Organization;
import org.opensaml.saml.saml2.metadata.SSODescriptor;
import org.opensaml.security.credential.UsageType;
import org.opensaml.xmlsec.signature.KeyInfo;
import org.opensaml.xmlsec.signature.KeyName;
import org.opensaml.xmlsec.signature.X509Data;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.core.io.Resource;

import net.shibboleth.utilities.java.support.xml.XMLParserException;
import se.litsec.swedisheid.opensaml.saml2.attribute.AttributeUtils;
import se.litsec.swedisheid.opensaml.saml2.metadata.MetadataUtils;
import se.litsec.swedisheid.opensaml.saml2.metadata.entitycategory.EntityCategoryMetadataHelper;
import se.litsec.swedisheid.opensaml.utils.SAMLUtils;

/**
 * Abstract base factory bean for creating {@code EntityDescriptor} objects using setter methods, and optionally a
 * template object.
 * <p>
 * When a template object is used, the factory bean is created using the
 * {@link #AbstractEntityDescriptorFactoryBean(Resource)} or
 * {@link #AbstractEntityDescriptorFactoryBean(EntityDescriptor)} constructors. The user may later change, or add, any
 * of the elements and attributes of the template object using the setter methods.
 * </p>
 * <p>
 * Note that no Signature will be included.
 * </p>
 * 
 * @author Martin Lindström (martin.lindstrom@litsec.se)
 */
public abstract class AbstractEntityDescriptorFactoryBean extends AbstractFactoryBean<EntityDescriptor> {

  /** The template descriptor. */
  protected EntityDescriptor template;

  /**
   * Constructor setting up the factory bean with no template. This means that the entire {@code EntityDescriptor}
   * object is created from data assigned in setter methods.
   */
  public AbstractEntityDescriptorFactoryBean() {
    this.template = SAMLUtils.createSamlObject(EntityDescriptor.class);
  }

  /**
   * Constructor setting up the factory bean with a template {@code EntityDescriptor} that is read from a resource.
   * Users of the bean may now change, add or delete, the elements and attributes of the template object using the
   * setter methods of the bean.
   * 
   * @param resource
   *          the template resource
   * @throws IOException
   *           if the resource can not be read
   * @throws UnmarshallingException
   *           for unmarshalling errors
   * @throws XMLParserException
   *           for XML parsing errors
   */
  public AbstractEntityDescriptorFactoryBean(Resource resource) throws XMLParserException, UnmarshallingException, IOException {
    this.template = SAMLUtils.unmarshall(resource.getInputStream(), EntityDescriptor.class);
    if (!this.matchingSSODescriptorType(this.template)) {
      throw new IllegalArgumentException("The SSO descriptor of the template does not match the factory bean type");
    }

    // Remove signature
    this.template.setSignature(null);
  }

  /**
   * Constructor setting up the factory bean with a template {@code EntityDescriptor}. Users of the bean may now change,
   * add or delete, the elements and attributes of the template object using the setter methods of the bean.
   * 
   * @param template
   *          the template
   * @throws UnmarshallingException
   *           for unmarshalling errors
   * @throws MarshallingException
   *           for marshalling errors
   */
  public AbstractEntityDescriptorFactoryBean(EntityDescriptor template) throws UnmarshallingException, MarshallingException {
    this.template = XMLObjectSupport.cloneXMLObject(template);
    if (!this.matchingSSODescriptorType(this.template)) {
      throw new IllegalArgumentException("The SSO descriptor of the template does not match the factory bean type");
    }

    // Remove signature
    this.template.setSignature(null);
  }

  /** {@inheritDoc} */
  @Override
  public Class<?> getObjectType() {
    return EntityDescriptor.class;
  }

  /** {@inheritDoc} */
  @Override
  protected EntityDescriptor createInstance() throws Exception {
    return this.isSingleton() ? this.template : XMLObjectSupport.cloneXMLObject(this.template);
  }

  /**
   * Returns the SSO role descriptor of the template entity descriptor. If no role descriptor is present, the method
   * creates such an object.
   * 
   * @return the role descriptor
   */
  protected abstract SSODescriptor ssoDescriptor();

  /**
   * Checks that the SSO descriptor of the supplied {@code EntityDescriptor} is of the correct type given the factory
   * bean type.
   * 
   * @param descriptor
   *          the descriptor to check
   * @return {@code true} if the type is OK, and {@code false} otherwise
   */
  protected abstract boolean matchingSSODescriptorType(EntityDescriptor descriptor);

  /**
   * Assigns the entityID for the {@code EntityDescriptor}.
   * 
   * @param entityID
   *          the entityID
   */
  public void setEntityID(String entityID) {
    this.template.setEntityID(trim(entityID));
  }

  /**
   * Assigns the ID attribute for the {@code EntityDescriptor}.
   * 
   * @param id
   *          the ID
   */
  public void setID(String id) {
    this.template.setID(trim(id));
  }

  /**
   * Assigns the cacheDuration attribute for the {@code EntityDescriptor}.
   * 
   * @param cacheDuration
   *          the cache duration (in milliseconds)
   */
  public void setCacheDuration(Long cacheDuration) {
    this.template.setCacheDuration(cacheDuration);
  }

  /**
   * Assigns the attribute {@code http://macedir.org/entity-category} to the {@code mdattr:EntityAttributes} of the
   * entity descriptors {@code Extensions} element using the supplied values.
   * <p>
   * If {@code null} is supplied, the entity categories will be cleared from the template entity descriptor (if
   * present).
   * </p>
   * 
   * @param entityCategories
   *          a list of attribute values for the entity category attribute
   */
  public void setEntityCategories(List<String> entityCategories) {
    entityCategories = trim(entityCategories);
    if (this.template.getExtensions() == null) {
      if (entityCategories == null) {
        return;
      }
      this.template.setExtensions(SAMLUtils.createSamlObject(Extensions.class));
    }
    Optional<EntityAttributes> entityAttributes = MetadataUtils.getEntityAttributes(this.template);
    if (!entityAttributes.isPresent()) {
      if (entityCategories == null) {
        return;
      }
      entityAttributes = Optional.of(SAMLUtils.createSamlObject(EntityAttributes.class));
      this.template.getExtensions().getUnknownXMLObjects().add(entityAttributes.get());
    }
    Optional<Attribute> ecAttribute = AttributeUtils.getAttribute(EntityCategoryMetadataHelper.ENTITY_CATEGORY_ATTRIBUTE_NAME,
      entityAttributes.get().getAttributes());
    if (!ecAttribute.isPresent()) {
      if (entityCategories == null) {
        return;
      }
      entityAttributes.get().getAttributes().add(
        EntityCategoryMetadataHelper.createEntityCategoryAttribute(entityCategories.toArray(new String[] {})));
    }
    else {
      if (entityCategories == null) {
        entityAttributes.get().getAttributes().remove(ecAttribute.get());
        if (entityAttributes.get().getAttributes().isEmpty()) {
          this.template.getExtensions().getUnknownXMLObjects().remove(entityAttributes.get());
          if (this.template.getExtensions().getUnknownXMLObjects().isEmpty()) {
            this.template.setExtensions(null);
          }
        }
      }
      else {
        ecAttribute.get().getAttributeValues().clear();
        AttributeUtils.addAttributeStringValues(ecAttribute.get(), entityCategories.toArray(new String[] {}));
      }
    }
  }

  /**
   * Assigns the {@code mdui:UIInfo} element as an extension to the role descriptor.
   * <p>
   * If {@code null} is supplied, the extension will be removed from the template entity descriptor (if present).
   * </p>
   * 
   * @param uiInfo
   *          the {@code UIInfo} element
   * @throws UnmarshallingException
   *           for unmarshalling errors
   * @throws MarshallingException
   *           for marshalling errors
   */
  public void setUIInfoExtension(UIInfo uiInfo) throws MarshallingException, UnmarshallingException {

    SSODescriptor ssoDescriptor = this.ssoDescriptor();
    if (ssoDescriptor.getExtensions() == null) {
      if (uiInfo == null) {
        return;
      }
      ssoDescriptor.setExtensions(SAMLUtils.createSamlObject(Extensions.class));
    }
    Optional<UIInfo> previousUIInfo = MetadataUtils.getMetadataExtension(ssoDescriptor.getExtensions(), UIInfo.class);
    if (previousUIInfo.isPresent()) {
      ssoDescriptor.getExtensions().getUnknownXMLObjects().remove(previousUIInfo.get());
      if (uiInfo == null) {
        if (ssoDescriptor.getExtensions().getUnknownXMLObjects().isEmpty()) {
          ssoDescriptor.setExtensions(null);
        }
        return;
      }
    }
    ssoDescriptor.getExtensions().getUnknownXMLObjects().add(XMLObjectSupport.cloneXMLObject(uiInfo));
  }

  /**
   * Assigns a certificate to the {@code md:KeyDescriptor} element having the attribute {@code use="signing"}.
   * 
   * @param certificate
   *          the certificate to assign
   */
  public void setSignatureCertificate(X509Certificate certificate) {
    this.setKeyInfoObject(certificate, UsageType.SIGNING, X509Certificate.class);
  }

  /**
   * Assigns a key name to the {@code md:KeyDescriptor} element having the attribute {@code use="signing"}.
   * 
   * @param name
   *          the name to assign
   */
  public void setSignatureCertificateKeyName(String name) {
    this.setKeyInfoObject(trim(name), UsageType.SIGNING, String.class);
  }

  /**
   * Assigns a certificate to the {@code md:KeyDescriptor} element having the attribute {@code use="encryption"}.
   * 
   * @param certificate
   *          the certificate to assign
   */
  public void setEncryptionCertificate(X509Certificate certificate) {
    this.setKeyInfoObject(certificate, UsageType.ENCRYPTION, X509Certificate.class);
  }

  /**
   * Assigns a key name to the {@code md:KeyDescriptor} element having the attribute {@code use="encryption"}.
   * 
   * @param name
   *          the name to assign
   */
  public void setEncryptionCertificateKeyName(String name) {
    this.setKeyInfoObject(trim(name), UsageType.ENCRYPTION, String.class);
  }

  /**
   * Assigns a certificate to the {@code md:KeyDescriptor} element with no {@code use} attribute set. This means that
   * the certificate may be used for both signing and encryption.
   * 
   * @param certificate
   *          the certificate to assign
   */
  public void setGenericCertificate(X509Certificate certificate) {
    this.setKeyInfoObject(certificate, UsageType.UNSPECIFIED, X509Certificate.class);
  }

  /**
   * Assigns a key name to the {@code md:KeyDescriptor} element with no {@code use} attribute set.
   * 
   * @param name
   *          the name to assign
   */
  public void setGenericCertificateKeyName(String name) {
    this.setKeyInfoObject(trim(name), UsageType.UNSPECIFIED, String.class);
  }

  /**
   * Helper method that assigns a certificate or key name to the {@code md:KeyDescriptor} element.
   * <p>
   * If {@code object} is {@code null} any matching occurences in the template will be removed.
   * </p>
   * 
   * @param object
   *          the object to assign (certificate or name)
   * @param usageType
   *          the usage type
   * @param type
   *          the class of the object parameter
   */
  protected void setKeyInfoObject(Object object, UsageType usageType, Class<?> type) {
    SSODescriptor ssoDescriptor = this.ssoDescriptor();

    if (ssoDescriptor.getKeyDescriptors().isEmpty() && object == null) {
      return;
    }

    KeyDescriptor keyDescriptor = null;
    for (KeyDescriptor kd : ssoDescriptor.getKeyDescriptors()) {
      if (usageType.equals(kd.getUse()) || (usageType.equals(UsageType.UNSPECIFIED) && kd.getUse() == null)) {
        keyDescriptor = kd;
        break;
      }
    }
    if (keyDescriptor == null) {
      if (object == null) {
        return;
      }
      keyDescriptor = SAMLUtils.createSamlObject(KeyDescriptor.class);
      if (!usageType.equals(UsageType.UNSPECIFIED)) {
        keyDescriptor.setUse(usageType);
      }
    }
    if (keyDescriptor.getKeyInfo() == null) {
      if (object == null) {
        return;
      }
      keyDescriptor.setKeyInfo(SAMLUtils.createXMLObject(KeyInfo.class, KeyInfo.DEFAULT_ELEMENT_NAME));
    }
    if (type.isAssignableFrom(X509Certificate.class)) {
      keyDescriptor.getKeyInfo().getX509Datas().clear();
    }
    else {
      keyDescriptor.getKeyInfo().getKeyNames().clear();
    }
    if (object == null) {
      if (keyDescriptor.getKeyInfo().getX509Datas().isEmpty() && keyDescriptor.getKeyInfo().getKeyNames().isEmpty()) {
        ssoDescriptor.getKeyDescriptors().remove(keyDescriptor);
      }
      return;
    }
    if (type.isAssignableFrom(X509Certificate.class)) {
      X509Data x509Data = SAMLUtils.createXMLObject(X509Data.class, X509Data.DEFAULT_ELEMENT_NAME);
      org.opensaml.xmlsec.signature.X509Certificate cert = SAMLUtils.createXMLObject(
        org.opensaml.xmlsec.signature.X509Certificate.class, org.opensaml.xmlsec.signature.X509Certificate.DEFAULT_ELEMENT_NAME);
      try {
        cert.setValue(Base64.getEncoder().encodeToString(((X509Certificate) object).getEncoded()));
      }
      catch (CertificateEncodingException e) {
        throw new SecurityException(e);
      }
      x509Data.getX509Certificates().add(cert);
      keyDescriptor.getKeyInfo().getX509Datas().add(x509Data);
    }
    else {
      KeyName keyName = SAMLUtils.createXMLObject(KeyName.class, KeyName.DEFAULT_ELEMENT_NAME);
      keyName.setValue((String) object);
      keyDescriptor.getKeyInfo().getKeyNames().add(keyName);
    }
  }

  /**
   * Assigns the {@code md:NameIDFormat} elements.
   * 
   * @param nameIDFormats
   *          the nameID format strings
   */
  public void setNameIDFormats(List<String> nameIDFormats) {
    nameIDFormats = trim(nameIDFormats);
    SSODescriptor ssoDescriptor = this.ssoDescriptor();
    ssoDescriptor.getNameIDFormats().clear();
    if (nameIDFormats == null) {
      return;
    }
    for (String id : nameIDFormats) {
      NameIDFormat name = SAMLUtils.createSamlObject(NameIDFormat.class);
      name.setFormat(id);
      ssoDescriptor.getNameIDFormats().add(name);
    }
  }

  /**
   * Assigns the {@code Organization} element to the entity descriptor.
   * 
   * @param organization
   *          the organization
   * @throws UnmarshallingException
   *           for unmarshalling errors
   * @throws MarshallingException
   *           for marshalling errors
   */
  public void setOrganization(Organization organization) throws MarshallingException, UnmarshallingException {
    this.template.setOrganization(XMLObjectSupport.cloneXMLObject(organization));
  }

  /**
   * Assigns the {@code ContactPerson} elements to the entity descriptor.
   * 
   * @param contactPersons
   *          the contact person elements
   * @throws UnmarshallingException
   *           for unmarshalling errors
   * @throws MarshallingException
   *           for marshalling errors
   */
  public void setContactPersons(List<ContactPerson> contactPersons) throws MarshallingException, UnmarshallingException {
    this.template.getContactPersons().clear();
    if (contactPersons != null) {
      for (ContactPerson cp : contactPersons) {
        this.template.getContactPersons().add(XMLObjectSupport.cloneXMLObject(cp));
      }
    }
  }

}
