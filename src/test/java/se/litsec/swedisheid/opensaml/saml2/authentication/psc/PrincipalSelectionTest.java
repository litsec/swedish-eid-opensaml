/*
 * Copyright 2016-2020 Litsec AB
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
import se.litsec.swedisheid.opensaml.saml2.authentication.psc.build.MatchValueBuilder;
import se.litsec.swedisheid.opensaml.saml2.authentication.psc.build.PrincipalSelectionBuilder;
import se.litsec.swedisheid.opensaml.saml2.authentication.psc.build.RequestedPrincipalSelectionBuilder;

/**
 * Test cases for {@link PrincipalSelection}, {@link RequestedPrincipalSelection} and {@link MatchValue}.
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
    
    RequestedPrincipalSelection rps = ObjectUtils.createSamlObject(RequestedPrincipalSelection.class);

    MatchValue rmv1 = ObjectUtils.createSamlObject(MatchValue.class);
    rmv1.setName(AttributeConstants.ATTRIBUTE_NAME_PERSONAL_IDENTITY_NUMBER);
    rps.getMatchValues().add(rmv1);

    MatchValue rmv2 = ObjectUtils.createSamlObject(MatchValue.class);
    rmv2.setName(AttributeConstants.ATTRIBUTE_NAME_PRID);
    rps.getMatchValues().add(rmv2);

    Element relement = ObjectUtils.marshall(rps);

    // System.out.println(SerializeSupport.prettyPrintXML(relement));

    RequestedPrincipalSelection rps2 = ObjectUtils.unmarshall(relement, RequestedPrincipalSelection.class);
    Assert.assertTrue("Expected two MatchValue elements", rps2.getMatchValues().size() == 2);
    Assert.assertEquals(AttributeConstants.ATTRIBUTE_NAME_PERSONAL_IDENTITY_NUMBER, rps2.getMatchValues().get(0).getName());
    Assert.assertEquals(AttributeConstants.ATTRIBUTE_NAME_PRID, rps2.getMatchValues().get(1).getName());
  }

  /**
   * Tests using builders to create the objects.
   * 
   * @throws Exception
   *           for errors
   */
  @Test
  public void testBuilders() throws Exception {
    PrincipalSelection ps = PrincipalSelectionBuilder.builder()
      .matchValues(
        MatchValueBuilder.builder()
          .value("198906059483")
          .nameFormat(Attribute.URI_REFERENCE)
          .name(AttributeConstants.ATTRIBUTE_NAME_PERSONAL_IDENTITY_NUMBER)
          .build(),
        MatchValueBuilder.builder().value("NO:05068907693").name(AttributeConstants.ATTRIBUTE_NAME_PRID).build())
      .build();

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
    
    RequestedPrincipalSelection rps = RequestedPrincipalSelectionBuilder.builder()
        .matchValues(
          MatchValueBuilder.builder().name(AttributeConstants.ATTRIBUTE_NAME_PERSONAL_IDENTITY_NUMBER).build(),
          MatchValueBuilder.builder().name(AttributeConstants.ATTRIBUTE_NAME_PRID).build())
        .build();

      Element relement = ObjectUtils.marshall(rps);

      // System.out.println(SerializeSupport.prettyPrintXML(relement));

      PrincipalSelection rps2 = ObjectUtils.unmarshall(relement, RequestedPrincipalSelection.class);
      Assert.assertTrue("Expected two MatchValue elements", rps2.getMatchValues().size() == 2);
      Assert.assertEquals(AttributeConstants.ATTRIBUTE_NAME_PERSONAL_IDENTITY_NUMBER, rps2.getMatchValues().get(0).getName());   
      Assert.assertEquals(AttributeConstants.ATTRIBUTE_NAME_PRID, rps2.getMatchValues().get(1).getName());
  }

}
