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
package se.litsec.swedisheid.opensaml.saml2.attribute;

import javax.xml.namespace.QName;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.XMLObjectBuilder;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.core.xml.schema.XSString;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.saml.saml2.core.Attribute;
import org.opensaml.saml.saml2.core.AttributeValue;

import se.litsec.swedisheid.opensaml.saml2.AbstractXMLObjectBuilder;

/**
 * Implements the build pattern to create {@link Attribute} objects.
 * 
 * @author Martin Lindström (martin.lindstrom@litsec.se)
 */
public class AttributeBuilder extends AbstractXMLObjectBuilder<Attribute> {

  /** The default name format for the attribute being built. */
  public static final String DEFAULT_NAME_FORMAT = Attribute.URI_REFERENCE;

  /**
   * Default constructor.
   */
  public AttributeBuilder() {
  }

  /**
   * Constructs a builder that it assigned an attribute (so that we may additional values to it).
   * 
   * @param attribute
   *          the template attribute
   */
  public AttributeBuilder(Attribute attribute) {
    try {
      this.setObject(XMLObjectSupport.cloneXMLObject(attribute));
    }
    catch (MarshallingException | UnmarshallingException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Static utility method that creates a default {@code AttributeBuilder}.
   * 
   * @return an {@code AttributeBuilder} instance.
   * @see #AttributeBuilder()
   */
  public static AttributeBuilder builder() {
    return new AttributeBuilder();
  }

  /**
   * Static utility method that creates an {@code AttributeBuilder} instance that is instantiated with the supplied
   * {@code attribute} parameter.
   * 
   * @param attribute
   *          the template attribute
   * @return an {@code AttributeBuilder} instance.
   * @see #AttributeBuilder(Attribute)
   */
  public static AttributeBuilder builder(Attribute attribute) {
    return new AttributeBuilder(attribute);
  }

  /** {@inheritDoc} */
  @Override
  public Attribute build() {
    if (this.object().getNameFormat() == null) {
      this.object().setNameFormat(DEFAULT_NAME_FORMAT);
    }
    return super.build();
  }

  /**
   * Assigns the attribute name.
   * 
   * @param name
   *          the attribute name
   * @return the builder
   */
  public AttributeBuilder name(String name) {
    this.object().setName(name);
    return this;
  }

  /**
   * Assigns the attribute friendly name.
   * 
   * @param friendlyName
   *          the friendly name
   * @return the builder
   */
  public AttributeBuilder friendlyName(String friendlyName) {
    this.object().setFriendlyName(friendlyName);
    return this;
  }

  /**
   * Assigns the attribute name format.
   * 
   * @param nameFormat
   *          the name format URI
   * @return the builder
   */
  public AttributeBuilder nameFormat(String nameFormat) {
    this.object().setNameFormat(nameFormat);
    return this;
  }

  /**
   * Assigns an attribute string value.
   * 
   * @param value
   *          the string value to add
   * @return the builder
   */
  public AttributeBuilder value(String value) {
    XSString sv = createValueObject(XSString.TYPE_NAME, XSString.class);
    sv.setValue(value);
    this.object().getAttributeValues().add(sv);
    return this;
  }

  /**
   * Assigns an attribute value.
   * 
   * @param value
   *          the value to add
   * @return the builder
   */
  public <T extends XMLObject> AttributeBuilder value(T value) {
    this.object().getAttributeValues().add(value);
    return this;
  }

  /**
   * Creates an {@code AttributeValue} object of the given class. The type of the attribute value will be the field that
   * is declared as {@code TYPE_NAME} of the given class.
   * <p>
   * After the object has been constructed, its setter methods should be called to setup the value object before adding
   * it to the attribute itself.
   * </p>
   * <p>
   * Note: For attribute having string values, there is no need to explictly create an attribute value. Instead the
   * {@link #value(String)} method may be used directly.
   * </p>
   *
   * @param <T>
   *          the type
   * @param clazz
   *          the type of attribute value
   * @return the attribute value
   * @see #createValueObject(QName, Class)
   */
  public static <T extends XMLObject> T createValueObject(Class<T> clazz) {
    try {
      QName schemaType = (QName) clazz.getDeclaredField("TYPE_NAME").get(null);
      return createValueObject(schemaType, clazz);
    }
    catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException | SecurityException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Creates an {@code AttributeValue} object of the given class and schema type.
   * <p>
   * After the object has been constructed, its setter methods should be called to setup the value object before adding
   * it to the attribute itself.
   * </p>
   * <p>
   * Note: For attribute having string values, there is no need to explictly create an attribute value. Instead the
   * {@link #value(String)} method may be used directly.
   * </p>
   * 
   * @param <T>
   *          the type
   * @param schemaType
   *          the schema type that should be assigned to the attribute value, i.e., {@code xsi:type="ns:ValueType"}
   * @param clazz
   *          the type of the attribute value
   * @return the attribute value
   * @see #createValueObject(Class)
   */
  public static <T extends XMLObject> T createValueObject(QName schemaType, Class<T> clazz) {
    XMLObjectBuilder<?> builder = XMLObjectProviderRegistrySupport.getBuilderFactory().getBuilder(schemaType);
    XMLObject object = builder.buildObject(AttributeValue.DEFAULT_ELEMENT_NAME, schemaType);
    return clazz.cast(object);
  }

  /** {@inheritDoc} */
  @Override
  protected Class<Attribute> getObjectType() {
    return Attribute.class;
  }

}
