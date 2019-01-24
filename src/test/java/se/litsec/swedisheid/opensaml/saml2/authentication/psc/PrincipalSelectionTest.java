/*
 * Copyright 2016-2019 Litsec AB
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package se.litsec.swedisheid.opensaml.saml2.authentication.psc;

import org.junit.Assert;
import org.junit.Test;
import org.opensaml.saml.saml2.core.Attribute;
import org.w3c.dom.Element;

import se.litsec.opensaml.utils.ObjectUtils;
import se.litsec.swedisheid.opensaml.OpenSAMLTestBase;
import se.litsec.swedisheid.opensaml.saml2.attribute.AttributeConstants;

/**
 * Test cases for {@link PrincipalSelection} and {@link MatchValue}.
 * 
 * @author Martin Lindström (martin.lindstrom@litsec.se)
 */
public class PrincipalSelectionTest extends OpenSAMLTestBase {

  /**
   * Test to marshall and unmarshall the object.
   * 
   * @throws Exception
   *           for errors
   */
  @Test
  public void testMarshallUnmarshall() throws Exception {
    
    PrincipalSelection ps = ObjectUtils.createSamlObject(PrincipalSelection.class);
    
    MatchValue mv1 = ObjectUtils.createSamlObject(MatchValue.class);
    mv1.setValue("198906059483");
    mv1.setNameFormat(Attribute.URI_REFERENCE);
    mv1.setName(AttributeConstants.ATTRIBUTE_NAME_PERSONAL_IDENTITY_NUMBER);
    ps.getMatchValues().add(mv1);
    
    MatchValue mv2 = ObjectUtils.createSamlObject(MatchValue.class);
    mv2.setValue("NO:05068907693");
    mv2.setName(AttributeConstants.ATTRIBUTE_NAME_PRID);
    ps.getMatchValues().add(mv2);
    
    Element element = ObjectUtils.marshall(ps);
    
    // System.out.println(SerializeSupport.prettyPrintXML(element));
    
    PrincipalSelection ps2 = ObjectUtils.unmarshall(element, PrincipalSelection.class);
    Assert.assertTrue("Expected two MatchValue elements", ps2.getMatchValues().size() == 2);
    Assert.assertEquals("198906059483", ps2.getMatchValues().get(0).getValue());
    Assert.assertEquals(Attribute.URI_REFERENCE, ps2.getMatchValues().get(0).getNameFormat());
    Assert.assertEquals(AttributeConstants.ATTRIBUTE_NAME_PERSONAL_IDENTITY_NUMBER, ps2.getMatchValues().get(0).getName());
    Assert.assertEquals("NO:05068907693", ps2.getMatchValues().get(1).getValue());
    Assert.assertNull(ps2.getMatchValues().get(1).getNameFormat());
    Assert.assertEquals(AttributeConstants.ATTRIBUTE_NAME_PRID, ps2.getMatchValues().get(1).getName());
  }

}
