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

import java.time.Duration;
import java.util.List;

import org.opensaml.saml.ext.saml2mdui.UIInfo;
import org.opensaml.saml.saml2.metadata.ContactPerson;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml.saml2.metadata.KeyDescriptor;
import org.opensaml.saml.saml2.metadata.Organization;
import org.springframework.core.io.Resource;

import se.litsec.swedisheid.opensaml.saml2.AbstractXMLObjectBuilder;
import se.litsec.swedisheid.opensaml.saml2.metadata.builders.AbstractEntityDescriptorBuilder;
import se.litsec.swedisheid.opensaml.saml2.spring.AbstractXMLObjectBuilderFactoryBean;

/**
 * Abstract base factory bean for creating {@code EntityDescriptor} objects. using setter methods, and optionally a
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
 * @see AbstractEntityDescriptorBuilder
 */
public abstract class AbstractEntityDescriptorFactoryBean extends AbstractXMLObjectBuilderFactoryBean<EntityDescriptor> {

  /** {@inheritDoc} */
  @Override
  protected AbstractXMLObjectBuilder<EntityDescriptor> builder() {
    return this._builder();
  }

  /**
   * Returns the builder.
   * 
   * @return the builder instance
   */
  protected abstract AbstractEntityDescriptorBuilder<?> _builder();

  /** {@inheritDoc} */
  @Override
  public Class<?> getObjectType() {
    return EntityDescriptor.class;
  }

  /**
   * @see AbstractEntityDescriptorBuilder#entityID(String)
   */
  public void setEntityID(String entityID) {
    this._builder().entityID(entityID);
  }

  /**
   * @see AbstractEntityDescriptorBuilder#id(String)
   */
  public void setID(String id) {
    this._builder().id(id);
  }

  /**
   * @see AbstractEntityDescriptorBuilder#cacheDuration(Long)
   */
  public void setCacheDuration(Long cacheDuration) {
    this._builder().cacheDuration(cacheDuration);
  }

  /**
   * @see AbstractEntityDescriptorBuilder#cacheDuration(Duration)
   */
  public void setCacheDuration(Duration cacheDuration) {
    this._builder().cacheDuration(cacheDuration);
  }

  /**
   * @see AbstractEntityDescriptorBuilder#validUntilDuration(Long)
   */
  public void setValidUntilDuration(Long validUntilDuration) {
    this._builder().validUntilDuration(validUntilDuration);
  }

  /**
   * @see AbstractEntityDescriptorBuilder#validUntilDuration(Duration)
   */
  public void setValidUntilDuration(Duration validUntilDuration) {
    this._builder().validUntilDuration(validUntilDuration);
  }

  /**
   * @see AbstractEntityDescriptorBuilder#entityCategories(String...)
   */
  public void setEntityCategories(List<String> entityCategories) {
    this._builder().entityCategories(stringListToVarArgs(entityCategories));
  }

  /**
   * @see AbstractEntityDescriptorBuilder#uiInfoExtension(UIInfo)
   */
  public void setUIInfoExtension(UIInfo uiInfo) {
    this._builder().uiInfoExtension(uiInfo);
  }

  /**
   * @see AbstractEntityDescriptorBuilder#keyDescriptors(List)
   */
  public void setKeyDescriptors(List<KeyDescriptor> keyDescriptors) {
    this._builder().keyDescriptors(keyDescriptors);
  }

  /**
   * @see AbstractEntityDescriptorBuilder#nameIDFormats(String...)
   */
  public void setNameIDFormats(List<String> nameIDFormats) {
    this._builder().nameIDFormats(stringListToVarArgs(nameIDFormats));
  }

  /**
   * @see AbstractEntityDescriptorBuilder#organization(Organization)
   */
  public void setOrganization(Organization organization) {
    this._builder().organization(organization);
  }

  /**
   * @see AbstractEntityDescriptorBuilder#contactPersons(List)
   */
  public void setContactPersons(List<ContactPerson> contactPersons) {
    this._builder().contactPersons(contactPersons);
  }

}
