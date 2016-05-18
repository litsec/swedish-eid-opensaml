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

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.opensaml.core.xml.XMLObjectBuilder;
import org.opensaml.core.xml.schema.XSString;
import org.opensaml.saml.saml2.core.Attribute;
import org.opensaml.saml.saml2.core.AttributeValue;

import se.litsec.swedisheid.opensaml.utils.SAMLUtils;

/**
 * Helper methods for accessing attribute values.
 * 
 * @author Martin Lindström (martin.lindstrom@litsec.se)
 */
public class AttributeUtils {

  /**
   * Creates a SAML attribute with no value.
   * 
   * @param name
   *          the attribute name
   * @param nameFormat
   *          the name format
   * @param friendlyName
   *          the optional attribute friendly name
   * @return a SAML attribute with no values stored
   * @see #createAttribute(String, String, String, String...)
   * @see #addAttributeStringValues(Attribute, String...)
   */
  public static Attribute createAttribute(String name, String nameFormat, String friendlyName) {
    Attribute attribute = SAMLUtils.createSamlObject(Attribute.class);
    attribute.setName(name);
    attribute.setNameFormat(nameFormat);
    attribute.setFriendlyName(friendlyName);
    return attribute;
  }

  /**
   * Creates a SAML attribute with one or more string values.
   * 
   * @param name
   *          the attribute name
   * @param nameFormat
   *          the name format
   * @param friendlyName
   *          the optional attribute friendly name
   * @param values
   *          the string values
   * @return a SAML attribute with string value(s)
   */
  public static Attribute createAttribute(String name, String nameFormat, String friendlyName, String... values) {
    Attribute attribute = SAMLUtils.createSamlObject(Attribute.class);
    attribute.setName(name);
    attribute.setNameFormat(nameFormat);
    attribute.setFriendlyName(friendlyName);
    AttributeUtils.addAttributeStringValues(attribute, values);
    return attribute;
  }

  /**
   * Adds one or more string values to the supplied SAML attribute.
   * 
   * @param attribute
   *          the attribute to update
   * @param values
   *          the string values
   */
  public static void addAttributeStringValues(Attribute attribute, String... values) {
    for (String v : values) {      
      XMLObjectBuilder<XSString> stringBuilder = SAMLUtils.getBuilder(XSString.TYPE_NAME);
      XSString stringValue = stringBuilder.buildObject(AttributeValue.DEFAULT_ELEMENT_NAME, XSString.TYPE_NAME);      
      stringValue.setValue(v);
      attribute.getAttributeValues().add(stringValue);
    }
  }

  /**
   * Given an attribute holding string values this method will return a list of these values.
   * 
   * @param attribute
   *          the attribute
   * @return a (possibly empty) list of string values
   */
  public static List<String> getAttributeStringValues(Attribute attribute) {
    return attribute.getAttributeValues()
        .stream()
        .filter(a -> XSString.class.isInstance(a))
        .map(XSString.class::cast)
        .map(v -> v.getValue())
        .collect(Collectors.toList());    
  }

  /**
   * Given a single-valued attribute, this method returns its string value
   * 
   * @param attribute
   *          the attribute
   * @return the value, or {@code null} if no value is stored
   */
  public static String getAttributeStringValue(Attribute attribute) {
    Optional<String> value = attribute.getAttributeValues()
        .stream()
        .filter(a -> XSString.class.isInstance(a))
        .map(XSString.class::cast)
        .map(v -> v.getValue())
        .findFirst();
    
    return value.isPresent() ? value.get() : null;
  }

  /**
   * Returns an attribute with a given name from an attribute list.
   * 
   * @param name
   *          the attribute name
   * @param attributes
   *          the list of attributes
   * @return the attribute or {@code null}
   * @see #getAttributes(String, List)
   */
  public static Attribute getAttribute(String name, List<Attribute> attributes) {
    if (attributes == null) {
      return null;
    }
    Optional<Attribute> attribute = attributes.stream().filter(a -> a.getName().equals(name)).findFirst();
    return attribute.isPresent() ? attribute.get() : null;
  }

  /**
   * Returns all attributes that matches the given name from the supplied attribute list.
   * 
   * @param name
   *          the attribute name
   * @param attributes
   *          the list of attributes
   * @return a list, possibly empty, of the matching attributes
   * @see #getAttribute(String, List)
   */
  public static List<Attribute> getAttributes(String name, List<Attribute> attributes) {
    if (attributes == null) {
      return Collections.emptyList();
    }
    return attributes.stream().filter(a -> a.getName().equals(name)).collect(Collectors.toList());
  }

  // Hidden
  private AttributeUtils() {
  }

}
