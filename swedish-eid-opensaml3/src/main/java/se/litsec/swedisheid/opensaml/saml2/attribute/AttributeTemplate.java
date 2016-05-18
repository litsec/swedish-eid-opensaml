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

import org.opensaml.saml.saml2.core.Attribute;

/**
 * An attribute template is a bean that represents the name, friendly name and name format but not the value of an SAML
 * attribute.
 * 
 * @author Martin Lindström (martin.lindstrom@litsec.se)
 * @see AttributeConstants
 */
public class AttributeTemplate {

  /** The attribute name. */
  private String name;

  /** The attribute friendly name. */
  private String friendlyName;

  /**
   * The name format of this attribute. The default is {@code urn:oasis:names:tc:SAML:2.0:attrname-format:uri} ({@link Attribute#URI_REFERENCE}).
   */
  private String nameFormat = Attribute.URI_REFERENCE;

  /**
   * Indicates if this attribute is multi-valued, i.e. whether it may have more than one value. Default is {@code false}
   * .
   */
  private boolean multiValued = false;

  /**
   * Creates an attribute template with the given name and friendly name, the default name format
   * {@code urn:oasis:names:tc:SAML:2.0:attrname-format:uri} ({@link Attribute#URI_REFERENCE}) and not multi-valued.
   * 
   * @param name
   *          the attribute name
   * @param friendlyName
   *          the attribute friendly name
   */
  public AttributeTemplate(String name, String friendlyName) {
    if (name == null) {
      throw new NullPointerException("'name' must not be null");
    }
    this.name = name;
    if (friendlyName == null) {
      throw new NullPointerException("'friendlyName' must not be null");
    }
    this.friendlyName = friendlyName;
  }

  /**
   * Creates an attribute template with the given name and friendly name, the default name format
   * {@code urn:oasis:names:tc:SAML:2.0:attrname-format:uri} ({@link Attribute#URI_REFERENCE}).
   * 
   * @param name
   *          the attribute name
   * @param friendlyName
   *          the attribute friendly name
   * @param multiValued
   *          indicates whether this attribute may have more than one value
   */
  public AttributeTemplate(String name, String friendlyName, boolean multiValued) {
    if (name == null) {
      throw new NullPointerException("'name' must not be null");
    }
    this.name = name;
    if (friendlyName == null) {
      throw new NullPointerException("'friendlyName' must not be null");
    }
    this.friendlyName = friendlyName;
    this.multiValued = multiValued;
  }

  /**
   * Creates an attribute template with the given name, friendly name and name format.
   * 
   * @param name
   *          the attribute name
   * @param friendlyName
   *          the attribute friendly name
   * @param multiValued
   *          indicates whether this attribute may have more than one value
   * @param nameFormat
   *          the name format
   */
  public AttributeTemplate(String name, String friendlyName, boolean multiValued, String nameFormat) {
    this(name, friendlyName, multiValued);
    if (nameFormat == null) {
      throw new NullPointerException("'nameFormat' must not be null");
    }
    this.nameFormat = nameFormat;
  }

  /**
   * Get the name of this attribute template.
   * 
   * @return the name of this attribute template
   */
  public String getName() {
    return this.name;
  }

  /**
   * Get the friendly name of this attribute template.
   * 
   * @return the friendly name of this attribute template
   */
  public String getFriendlyName() {
    return this.friendlyName;
  }

  /**
   * Get the name format of this attribute template.
   * 
   * @return the name format of this attribute template
   */
  public String getNameFormat() {
    return this.nameFormat;
  }

  /**
   * Predicate that tells if this attribute may have multiple values.
   * 
   * @return if multiple values are allowed {@code true} is returned, otherwise {@code false}
   */
  public boolean isMultiValued() {
    return this.multiValued;
  }

  /**
   * Based on the attribute template an {@link Attribute} object is created.
   * 
   * @param values
   *          the string value(s)
   * @return an {@code Attribute} object
   * @throws IllegalArgumentException
   *           if more than one value is supplied to a template that is not multi-valued
   */
  public Attribute createAttribute(String... values) throws IllegalArgumentException {

    if (values.length > 1 && !this.multiValued) {
      throw new IllegalArgumentException("Only one value may be given for templates that is not multi-valued.");
    }
    return null;
//    return AttributeUtils.createAttribute(this.name, this.nameFormat, this.friendlyName, values);
  }

}
