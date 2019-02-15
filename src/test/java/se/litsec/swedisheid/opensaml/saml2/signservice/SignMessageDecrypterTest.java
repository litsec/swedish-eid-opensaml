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
package se.litsec.swedisheid.opensaml.saml2.signservice;

import java.security.KeyStore;

import org.junit.Assert;
import org.junit.Test;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.saml2.core.AuthnRequest;
import org.opensaml.security.credential.Credential;
import org.opensaml.security.x509.impl.KeyStoreX509CredentialAdapter;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import se.litsec.opensaml.utils.KeyStoreUtils;
import se.litsec.opensaml.utils.ObjectUtils;
import se.litsec.swedisheid.opensaml.OpenSAMLTestBase;
import se.litsec.swedisheid.opensaml.saml2.signservice.dss.Message;
import se.litsec.swedisheid.opensaml.saml2.signservice.dss.SignMessage;

/**
 * Test cases for SignMessageDecrypter.
 * 
 * @author Martin Lindström (martin.lindstrom@litsec.se)
 */
public class SignMessageDecrypterTest extends OpenSAMLTestBase {
  
  private Credential credential;
  
  public SignMessageDecrypterTest() throws Exception {
    KeyStore keyStore = KeyStoreUtils.loadKeyStore(new ClassPathResource("idp-credentials.jks").getInputStream(), "secret", "JKS");
    this.credential = new KeyStoreX509CredentialAdapter(keyStore, "encryption", "secret".toCharArray());
  }

  // Bad type
//  @Test
//  public void testDecrypt1() throws Exception {
//    SignMessageDecrypter decrypter = new SignMessageDecrypter(this.credential); 
//    
//    Resource xml = new ClassPathResource("signmessage_bad_type.xml");
//    AuthnRequest authnRequest = ObjectUtils.unmarshall(xml.getInputStream(), AuthnRequest.class);
//    
//    XMLObject sm = authnRequest.getExtensions().getUnknownXMLObjects(SignMessage.DEFAULT_ELEMENT_NAME).get(0);
//    SignMessage signMessage = (SignMessage) sm;
//    
//    // Message message = decrypter.decrypt(signMessage);
//    
//    // Gives:
//    // <?xml version="1.0" encoding="UTF-8"?>
//    // <base64Binary>dGVzdGluZw==</base64Binary>
//    
//  }

  @Test
  public void testDecrypt2() throws Exception {
    SignMessageDecrypter decrypter = new SignMessageDecrypter(this.credential); 
    
    Resource xml = new ClassPathResource("signmessage_rsa_1_5.xml");
    AuthnRequest authnRequest = ObjectUtils.unmarshall(xml.getInputStream(), AuthnRequest.class);
    
    XMLObject sm = authnRequest.getExtensions().getUnknownXMLObjects(SignMessage.DEFAULT_ELEMENT_NAME).get(0);
    SignMessage signMessage = (SignMessage) sm;
    
    Message message = decrypter.decrypt(signMessage);
    Assert.assertNotNull(message);
    Assert.assertNotNull(message.getContent());
    // <ns:Message xmlns:ns="http://id.elegnamnden.se/csig/1.1/dss-ext/ns">WW91IGFyZSByZXF1ZXN0ZWQgdG8gc2lnbiB0aGUgZm9sbG93aW5nIGRvY3VtZW50OgoKRG9jdW1lbnQgbmFtZTogZjgzOGE3MDRjNjc3NDYyYjkxYWM4NjQ3ZTdkNmRmZjYueG1sClNpZ25lciBuYW1lOiBPc2thciBKb2hhbnNzb24KU2lnbmVyIElEOiBwZXJzb25hbElkZW50aXR5TnVtYmVyOiAxOTkwMDgyNTIzOTgK</ns:Message>    
  }

}
