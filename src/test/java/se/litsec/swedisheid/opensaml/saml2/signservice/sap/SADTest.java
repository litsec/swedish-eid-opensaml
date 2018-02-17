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

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import se.litsec.swedisheid.opensaml.saml2.attribute.AttributeConstants;
import se.litsec.swedisheid.opensaml.saml2.authentication.LevelofAssuranceAuthenticationContextURI;

/**
 * Test cases for the {@link SAD} implementation.
 * 
 * @author Martin Lindström (martin.lindstrom@litsec.se)
 */
public class SADTest {

  /**
   * Tests creating a SAD and serializing and deserializing.
   * 
   * @throws Exception
   *           for errors
   */
  @Test
  public void testEncodeDecode() throws Exception {
    
    DateTime issuance = new DateTime(2018, 1, 17, 14, 22, 37, 0);
    DateTime expiry = issuance.plusMinutes(5);
    
    SAD sad = new SAD();
    sad.setSubject("196302052383");
    sad.setAudience("http://www.example.com/sigservice");
    sad.setIssuer("https://idp.svelegtest.se/idp");
    sad.setExpiry(expiry);
    sad.setIssuedAt((int) (issuance.getMillis() / 1000L));
    sad.setJwtId("d4073fc74b1b9199");
    SAD.Extension ext = new SAD.Extension();
    ext.setVersion(SADVersion.VERSION_10.toString());
    ext.setInResponseTo("_a74a068d0548a919e503e5f9ef901851");
    ext.setAttributeName(AttributeConstants.ATTRIBUTE_NAME_PERSONAL_IDENTITY_NUMBER);
    ext.setLoa(LevelofAssuranceAuthenticationContextURI.AUTH_CONTEXT_URI_LOA3);
    ext.setRequestID("f6e7d061a23293b0053dc7b038a04dad");
    ext.setNumberOfDocuments(1);
    sad.setSeElnSadext(ext);
    
    DateTime exp = sad.getExpiryDateTime();
    Assert.assertEquals(sad.getExpiry().intValue(), (int) (exp.getMillis() / 1000));
    
    System.out.println(exp);

    String json = sad.toJson();

    SAD sad2 = SAD.fromJson(json);

    Assert.assertEquals(sad, sad2);

    json = (new ObjectMapper()).writerWithDefaultPrettyPrinter().writeValueAsString(sad);
    System.out.println(json);

    SAD sad3 = SAD.fromJson(json);

    Assert.assertEquals(sad, sad3);
  }

}
