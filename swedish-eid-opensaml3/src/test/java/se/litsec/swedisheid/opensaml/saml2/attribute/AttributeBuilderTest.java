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

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;
import org.opensaml.core.xml.schema.XSBoolean;
import org.opensaml.core.xml.schema.XSBooleanValue;
import org.opensaml.saml.saml2.core.Attribute;

import se.litsec.swedisheid.opensaml.OpenSAMLTestBase;

/**
 * Tests for the {@link AttributeBuilder}.
 * 
 * @author Martin Lindström (martin.lindstrom@litsec.se)
 */
public class AttributeBuilderTest extends OpenSAMLTestBase {

  @Test
  public void testCreateStringValueAttribute() {
    Attribute attribute = AttributeBuilder.builder()
        .name(AttributeConstants.ATTRIBUTE_NAME_SN)
        .friendlyName(AttributeConstants.ATTRIBUTE_FRIENDLY_NAME_SN)
        .value("Eriksson")
        .build();
    
    Assert.assertEquals(AttributeConstants.ATTRIBUTE_NAME_SN, attribute.getName());
    Assert.assertEquals(Attribute.URI_REFERENCE, attribute.getNameFormat());
    Assert.assertEquals(AttributeConstants.ATTRIBUTE_FRIENDLY_NAME_SN, attribute.getFriendlyName());
    Assert.assertTrue(AttributeUtils.getAttributeStringValues(attribute).size() == 1);
    Assert.assertEquals("Eriksson", AttributeUtils.getAttributeStringValue(attribute));
  }
  
  @Test
  public void testCreateMultipleStringValuesAttribute() {
    Attribute attribute = AttributeBuilder.builder()
        .name(AttributeConstants.ATTRIBUTE_NAME_MAIL)
        .friendlyName(AttributeConstants.ATTRIBUTE_FRIENDLY_NAME_MAIL)
        .value("martin@litsec.se")
        .value("martin.lindstrom@litsec.se")
        .build();
    
    Assert.assertEquals(AttributeConstants.ATTRIBUTE_NAME_MAIL, attribute.getName());
    Assert.assertEquals(Attribute.URI_REFERENCE, attribute.getNameFormat());
    Assert.assertEquals(AttributeConstants.ATTRIBUTE_FRIENDLY_NAME_MAIL, attribute.getFriendlyName());
    Assert.assertEquals(Arrays.asList("martin@litsec.se", "martin.lindstrom@litsec.se"), AttributeUtils.getAttributeStringValues(attribute));
  }
  
  @Test
  public void testCreateNonStringAttribute() {
   
    // We pretend that there is a attribute that holds a boolean ...    
    XSBoolean value = AttributeBuilder.createValueObject(XSBoolean.class);
    value.setValue(XSBooleanValue.valueOf("true"));
    
    Attribute attribute = AttributeBuilder.builder()
        .name("http://eid.litsec.se/types/boolean")
        .friendlyName("booleanAttribute")
        .value(value)
        .build();
    
    Assert.assertEquals("http://eid.litsec.se/types/boolean", attribute.getName());
    Assert.assertEquals(Attribute.URI_REFERENCE, attribute.getNameFormat());
    Assert.assertEquals("booleanAttribute", attribute.getFriendlyName());
    Assert.assertTrue(AttributeUtils.getAttributeValues(attribute, XSBoolean.class).size() == 1);
    Assert.assertEquals(AttributeUtils.getAttributeValue(attribute, XSBoolean.class).getValue().getValue(), Boolean.TRUE);
  }

}
