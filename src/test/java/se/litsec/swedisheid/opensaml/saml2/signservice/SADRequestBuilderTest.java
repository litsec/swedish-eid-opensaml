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
package se.litsec.swedisheid.opensaml.saml2.signservice;

import org.junit.Assert;
import org.junit.Test;

import se.litsec.swedisheid.opensaml.OpenSAMLTestBase;
import se.litsec.swedisheid.opensaml.saml2.signservice.SADRequestBuilder.RequestParamsBuilder;
import se.litsec.swedisheid.opensaml.saml2.signservice.sap.SADRequest;
import se.litsec.swedisheid.opensaml.saml2.signservice.sap.SADVersion;

/**
 * Test cases for {@code SADRequestBuilder}.
 * 
 * @author Martin Lindström (martin.lindstrom@litsec.se)
 */
public class SADRequestBuilderTest extends OpenSAMLTestBase {

  @Test
  public void testBuildSADRequest() throws Exception {
    
    SADRequest request = SADRequestBuilder.builder()
        .id("_a74a068d0548a919e503e5f9ef901851")
        .requesterID("http://www.example.com/sigservice")
        .signRequestID("123456")
        .docCount(5)
        .requestedVersion(SADVersion.VERSION_10)
        .requestParams(
          RequestParamsBuilder.builder()
            .parameters(RequestParamsBuilder.parameter("param1", "value1"), RequestParamsBuilder.parameter("param2", "value2"))
            .build())
        .build();
        
    Assert.assertEquals("_a74a068d0548a919e503e5f9ef901851", request.getID());
    Assert.assertEquals("http://www.example.com/sigservice", request.getRequesterID());
    Assert.assertEquals("123456", request.getSignRequestID());
    Assert.assertEquals(Integer.valueOf(5), request.getDocCount());
    Assert.assertEquals(SADVersion.VERSION_10, request.getRequestedVersion());
    Assert.assertTrue(request.getRequestParams().getParameters().size() == 2);
    Assert.assertEquals("value1", request.getRequestParams().getParameters().get(0).getValue());
    Assert.assertEquals("param1", request.getRequestParams().getParameters().get(0).getName());
    Assert.assertEquals("value2", request.getRequestParams().getParameters().get(1).getValue());
    Assert.assertEquals("param2", request.getRequestParams().getParameters().get(1).getName());
        
    
  }
  
}
