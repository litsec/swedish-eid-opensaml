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
import org.springframework.core.io.Resource;

import net.shibboleth.utilities.java.support.xml.XMLParserException;
import se.litsec.swedisheid.opensaml.saml2.AbstractXMLObjectBuilder;
import se.litsec.swedisheid.opensaml.saml2.attribute.AttributeUtils;
import se.litsec.swedisheid.opensaml.saml2.metadata.MetadataUtils;
import se.litsec.swedisheid.opensaml.saml2.metadata.entitycategory.EntityCategoryMetadataHelper;
import se.litsec.swedisheid.opensaml.utils.SAMLUtils;

/**
 * Abstract base builder for creating {@code EntityDescriptor} objects using the builder pattern, and optionally a
 * template object.
 * <p>
 * When a template object is used, the builder is created using the {@link #AbstractEntityDescriptorBuilder(Resource)}
 * or {@link #AbstractEntityDescriptorBuilder(EntityDescriptor)} constructors. The user may later change, or add, any of
 * the elements and attributes of the template object using the assignment methods.
 * </p>
 * <p>
 * Note that no Signature will be included.
 * </p>
 * 
 * @author Martin Lindström (martin.lindstrom@litsec.se)
 * 
 * @param <T>
 *          the concrete builder type
 */
public abstract class AbstractEntityDescriptorBuilder<T extends AbstractXMLObjectBuilder<EntityDescriptor>> extends
    AbstractXMLObjectBuilder<EntityDescriptor> {

  /**
   * Constructor setting up the builder with no template. This means that the entire {@code EntityDescriptor} object is
   * created from data assigned using the builder.
   */
  public AbstractEntityDescriptorBuilder() {
    super();
  }

  /**
   * Constructor setting up the builder with a template {@code EntityDescriptor} that is read from a resource. Users of
   * the bean may now change, add or delete, the elements and attributes of the template object using the assignment
   * methods of the builder.
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
  public AbstractEntityDescriptorBuilder(Resource resource) throws XMLParserException, UnmarshallingException, IOException {
    EntityDescriptor ed = SAMLUtils.unmarshall(resource.getInputStream(), EntityDescriptor.class);
    if (!this.matchingSSODescriptorType(ed)) {
      throw new IllegalArgumentException("The SSO descriptor of the template does not match the builder type");
    }
    this.setObject(ed);

    // Remove signature
    this.object().setSignature(null);
  }

  /**
   * Constructor setting up the builder with a template {@code EntityDescriptor}. Users of the bean may now change, add
   * or delete, the elements and attributes of the template object using the assignment methods of the builder.
   * 
   * @param template
   *          the template
   * @throws UnmarshallingException
   *           for unmarshalling errors
   * @throws MarshallingException
   *           for marshalling errors
   */
  public AbstractEntityDescriptorBuilder(EntityDescriptor template) throws UnmarshallingException, MarshallingException {
    EntityDescriptor ed = XMLObjectSupport.cloneXMLObject(template);
    if (!this.matchingSSODescriptorType(ed)) {
      throw new IllegalArgumentException("The SSO descriptor of the template does not match the builder type");
    }
    this.setObject(ed);

    // Remove signature
    this.object().setSignature(null);
  }

  /** {@inheritDoc} */
  @Override
  protected Class<EntityDescriptor> getObjectType() {
    return EntityDescriptor.class;
  }

  /**
   * In order for us to be able to make chaining calls we need to return the concrete type of the bulder.
   * 
   * @return the concrete type of the builder
   */
  protected abstract T getThis();

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
   * @return the builder
   */
  public T entityID(String entityID) {
    this.object().setEntityID(entityID);
    return this.getThis();
  }

  /**
   * Assigns the ID attribute for the {@code EntityDescriptor}.
   * 
   * @param id
   *          the ID
   * @return the builder
   */
  public T id(String id) {
    this.object().setID(id);
    return this.getThis();
  }

  /**
   * Assigns the cacheDuration attribute for the {@code EntityDescriptor}.
   * 
   * @param cacheDuration
   *          the cache duration (in milliseconds)
   * @return the builder
   */
  public T cacheDuration(Long cacheDuration) {
    this.object().setCacheDuration(cacheDuration);
    return this.getThis();
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
   *          attribute values for the entity category attribute
   * @return the builder
   */
  public T entityCategories(String... entityCategories) {
    if (this.object().getExtensions() == null) {
      if (entityCategories == null) {
        return this.getThis();
      }
      this.object().setExtensions(SAMLUtils.createSamlObject(Extensions.class));
    }
    Optional<EntityAttributes> entityAttributes = MetadataUtils.getEntityAttributes(this.object());
    if (!entityAttributes.isPresent()) {
      if (entityCategories == null) {
        return this.getThis();
      }
      entityAttributes = Optional.of(SAMLUtils.createSamlObject(EntityAttributes.class));
      this.object().getExtensions().getUnknownXMLObjects().add(entityAttributes.get());
    }
    Optional<Attribute> ecAttribute = AttributeUtils.getAttribute(EntityCategoryMetadataHelper.ENTITY_CATEGORY_ATTRIBUTE_NAME,
      entityAttributes.get().getAttributes());
    if (!ecAttribute.isPresent()) {
      if (entityCategories == null) {
        return this.getThis();
      }
      entityAttributes.get().getAttributes().add(EntityCategoryMetadataHelper.createEntityCategoryAttribute(entityCategories));
    }
    else {
      if (entityCategories == null) {
        entityAttributes.get().getAttributes().remove(ecAttribute.get());
        if (entityAttributes.get().getAttributes().isEmpty()) {
          this.object().getExtensions().getUnknownXMLObjects().remove(entityAttributes.get());
          if (this.object().getExtensions().getUnknownXMLObjects().isEmpty()) {
            this.object().setExtensions(null);
          }
        }
      }
      else {
        ecAttribute.get().getAttributeValues().clear();
        AttributeUtils.addAttributeStringValues(ecAttribute.get(), entityCategories);
      }
    }
    return this.getThis();
  }

  /**
   * Assigns the {@code mdui:UIInfo} element as an extension to the role descriptor.
   * <p>
   * If {@code null} is supplied, the extension will be removed from the template entity descriptor (if present).
   * </p>
   * 
   * @param uiInfo
   *          the {@code UIInfo} element (will be cloned before assignment)
   * @return the builder
   */
  public T uiInfoExtension(UIInfo uiInfo) {

    SSODescriptor ssoDescriptor = this.ssoDescriptor();
    if (ssoDescriptor.getExtensions() == null) {
      if (uiInfo == null) {
        return this.getThis();
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
        return this.getThis();
      }
    }
    try {
      ssoDescriptor.getExtensions().getUnknownXMLObjects().add(XMLObjectSupport.cloneXMLObject(uiInfo));
    }
    catch (MarshallingException | UnmarshallingException e) {
      throw new RuntimeException(e);
    }
    return this.getThis();
  }

  /**
   * Assigns a certificate to the {@code md:KeyDescriptor} element having the attribute {@code use="signing"}.
   * 
   * @param certificate
   *          the certificate to assign
   * @return the builder
   */
  public T signatureCertificate(X509Certificate certificate) {
    this.setKeyInfoObject(certificate, UsageType.SIGNING, X509Certificate.class);
    return this.getThis();
  }

  /**
   * Assigns a key name to the {@code md:KeyDescriptor} element having the attribute {@code use="signing"}.
   * 
   * @param name
   *          the name to assign
   * @return the builder
   */
  public T signatureCertificateKeyName(String name) {
    this.setKeyInfoObject(name, UsageType.SIGNING, String.class);
    return this.getThis();
  }

  /**
   * Assigns a certificate to the {@code md:KeyDescriptor} element having the attribute {@code use="encryption"}.
   * 
   * @param certificate
   *          the certificate to assign
   * @return the builder
   */
  public T encryptionCertificate(X509Certificate certificate) {
    this.setKeyInfoObject(certificate, UsageType.ENCRYPTION, X509Certificate.class);
    return this.getThis();
  }

  /**
   * Assigns a key name to the {@code md:KeyDescriptor} element having the attribute {@code use="encryption"}.
   * 
   * @param name
   *          the name to assign
   * @return the builder
   */
  public T encryptionCertificateKeyName(String name) {
    this.setKeyInfoObject(name, UsageType.ENCRYPTION, String.class);
    return this.getThis();
  }

  /**
   * Assigns a certificate to the {@code md:KeyDescriptor} element with no {@code use} attribute set. This means that
   * the certificate may be used for both signing and encryption.
   * 
   * @param certificate
   *          the certificate to assign
   * @return the builder
   */
  public T genericCertificate(X509Certificate certificate) {
    this.setKeyInfoObject(certificate, UsageType.UNSPECIFIED, X509Certificate.class);
    return this.getThis();
  }

  /**
   * Assigns a key name to the {@code md:KeyDescriptor} element with no {@code use} attribute set.
   * 
   * @param name
   *          the name to assign
   * @return the builder
   */
  public T genericCertificateKeyName(String name) {
    this.setKeyInfoObject(name, UsageType.UNSPECIFIED, String.class);
    return this.getThis();
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
   * @return the builder
   */
  public T nameIDFormats(String... nameIDFormats) {
    SSODescriptor ssoDescriptor = this.ssoDescriptor();
    ssoDescriptor.getNameIDFormats().clear();
    if (nameIDFormats == null) {
      return this.getThis();
    }
    for (String id : nameIDFormats) {
      NameIDFormat name = SAMLUtils.createSamlObject(NameIDFormat.class);
      name.setFormat(id);
      ssoDescriptor.getNameIDFormats().add(name);
    }
    return this.getThis();
  }

  /**
   * Assigns the {@code Organization} element to the entity descriptor.
   * 
   * @param organization
   *          the organization (will be cloned before assignment)
   * @return the builder
   */
  public T organization(Organization organization) {
    try {
      this.object().setOrganization(XMLObjectSupport.cloneXMLObject(organization));
    }
    catch (MarshallingException | UnmarshallingException e) {
      throw new RuntimeException(e);
    }
    return this.getThis();
  }

  /**
   * Assigns the {@code ContactPerson} elements to the entity descriptor.
   * 
   * @param contactPersons
   *          the contact person elements (will be cloned before assignment)
   * @return the builder
   */
  public T contactPersons(List<ContactPerson> contactPersons) {
    this.object().getContactPersons().clear();
    if (contactPersons != null) {
      for (ContactPerson cp : contactPersons) {
        try {
          this.object().getContactPersons().add(XMLObjectSupport.cloneXMLObject(cp));
        }
        catch (MarshallingException | UnmarshallingException e) {
          throw new RuntimeException(e);
        }
      }
    }
    return this.getThis();
  }

}
