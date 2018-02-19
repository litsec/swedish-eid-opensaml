/*
 * Copyright 2016-2018 Litsec AB
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
package se.litsec.swedisheid.opensaml.saml2.signservice.sap;

import java.io.ByteArrayInputStream;
import java.nio.charset.Charset;

import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Element;

import se.litsec.opensaml.utils.ObjectUtils;
import se.litsec.swedisheid.opensaml.OpenSAMLTestBase;

/**
 * Test cases for {@code SADRequest}.
 * 
 * @author Martin Lindström (martin.lindstrom@litsec.se)
 */
public class SADRequestTest extends OpenSAMLTestBase {

  /**
   * Test to marshall and unmarshall the object.
   * 
   * @throws Exception
   *           for errors
   */
  @Test
  public void testMarshallUnmarshall() throws Exception {
    SADRequest request = ObjectUtils.createSamlObject(SADRequest.class);
    request.setID("_a74a068d0548a919e503e5f9ef901851");
    request.setRequesterID("http://www.example.com/sigservice");
    request.setSignRequestID("123456");
    request.setDocCount(5);
    request.setRequestedVersion(SADVersion.VERSION_10);
    
    Parameter p1 = ObjectUtils.createSamlObject(Parameter.class);
    p1.setName("param1");
    p1.setValue("value1");
    
    Parameter p2 = ObjectUtils.createSamlObject(Parameter.class);
    p2.setName("param2");
    p2.setValue("value2");
    
    RequestParams rp = ObjectUtils.createSamlObject(RequestParams.class);
    rp.getParameters().add(p1);
    rp.getParameters().add(p2);
    request.setRequestParams(rp);

    Element element = ObjectUtils.marshall(request);
    
    SADRequest request2 = ObjectUtils.unmarshall(element, SADRequest.class);

    Assert.assertEquals(request.getID(), request2.getID());
    Assert.assertEquals(request.getRequesterID(), request2.getRequesterID());
    Assert.assertEquals(request.getSignRequestID(), request2.getSignRequestID());
    Assert.assertEquals(request.getDocCount(), request2.getDocCount());
    Assert.assertEquals(request.getRequestedVersion(), request2.getRequestedVersion());
    Assert.assertTrue(request2.getRequestParams().getParameters().size() == 2);
    Assert.assertEquals(p1.getValue(), request2.getRequestParams().getParameters().get(0).getValue());
    Assert.assertEquals(p1.getName(), request2.getRequestParams().getParameters().get(0).getName());
    Assert.assertEquals(p2.getValue(), request2.getRequestParams().getParameters().get(1).getValue());
    Assert.assertEquals(p2.getName(), request2.getRequestParams().getParameters().get(1).getName());
  }

  @Test
  public void testUnmarshallOtherNs() throws Exception {

    String xml = 
        "<xyz:SADRequest ID=\"_a74a068d0548a919e503e5f9ef901851\" xmlns:xyz=\"http://id.elegnamnden.se/csig/1.1/sap/ns\">\n" +
        "  <xyz:RequesterID>http://www.example.com/sigservice</xyz:RequesterID>" +
        "  <xyz:SignRequestID>123456</xyz:SignRequestID>\n" +
        "  <xyz:DocCount>5</xyz:DocCount>\n" +
        "  <xyz:RequestedVersion>2.0</xyz:RequestedVersion>\n" +
        "  <xyz:RequestParams>\n" +
        "    <xyz:Parameter Name=\"param1\">value1</xyz:Parameter>\n" +
        "    <xyz:Parameter Name=\"param2\">value2</xyz:Parameter>\n" +
        "  </xyz:RequestParams>\n" +
        "</xyz:SADRequest>";

    SADRequest request = ObjectUtils.unmarshall(new ByteArrayInputStream(xml.getBytes(Charset.forName("UTF-8"))), SADRequest.class);
    
    Assert.assertEquals("_a74a068d0548a919e503e5f9ef901851", request.getID());
    Assert.assertEquals("http://www.example.com/sigservice", request.getRequesterID());
    Assert.assertEquals("123456", request.getSignRequestID());
    Assert.assertEquals(new Integer(5), request.getDocCount());
    Assert.assertEquals(SADVersion.valueOf("2.0"), request.getRequestedVersion());
    Assert.assertTrue(request.getRequestParams().getParameters().size() == 2);
    Assert.assertEquals("value1", request.getRequestParams().getParameters().get(0).getValue());
    Assert.assertEquals("param1", request.getRequestParams().getParameters().get(0).getName());
    Assert.assertEquals("value2", request.getRequestParams().getParameters().get(1).getValue());
    Assert.assertEquals("param2", request.getRequestParams().getParameters().get(1).getName());    
  }

}
