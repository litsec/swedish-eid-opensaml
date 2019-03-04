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

import java.security.KeyStore;
import java.security.cert.X509Certificate;

import org.junit.Assert;
import org.junit.Test;
import org.opensaml.security.x509.BasicX509Credential;
import org.opensaml.security.x509.impl.KeyStoreX509CredentialAdapter;
import org.springframework.core.io.ClassPathResource;

import se.litsec.opensaml.utils.KeyStoreUtils;
import se.litsec.opensaml.utils.X509CertificateUtils;
import se.litsec.swedisheid.opensaml.OpenSAMLTestBase;
import se.litsec.swedisheid.opensaml.saml2.signservice.dss.Message;
import se.litsec.swedisheid.opensaml.saml2.signservice.dss.SignMessage;
import se.litsec.swedisheid.opensaml.saml2.signservice.dss.SignMessageMimeTypeEnum;

/**
 * Test cases for {@code SignMessageBuilder}.
 *   
 * @author Martin Lindström (martin.lindstrom@litsec.se)
 */
public class SignMessageBuilderTest extends OpenSAMLTestBase {

  @SuppressWarnings("deprecation")
  @Test
  public void testBuild() throws Exception {
    
    X509Certificate encryptionCertificate = X509CertificateUtils.decodeCertificate(new ClassPathResource("Litsec_SAML_Encryption.crt").getInputStream()); 
    
    BasicX509Credential credential = new BasicX509Credential(encryptionCertificate); 
    
    SignMessage msg = SignMessageBuilder.builder()
      .mustShow(true)
      .displayEntity("http://www.example.com/idp")
      .mimeType(SignMessageMimeTypeEnum.TEXT)
      .message("This is the sign message")
      .buildEncrypted(credential);
      
    Assert.assertNotNull(msg.getEncryptedMessage());
    
    KeyStore decryptionStore = KeyStoreUtils.loadKeyStore(new ClassPathResource("Litsec_SAML_Encryption.jks").getInputStream(), "secret", "JKS");
    KeyStoreX509CredentialAdapter decryptionCredential = new KeyStoreX509CredentialAdapter(decryptionStore, "litsec_saml_encryption", "secret".toCharArray());
        
    SignMessageDecrypter decrypter = new SignMessageDecrypter(decryptionCredential); 
    Message clearText = decrypter.decrypt(msg);
    String content = clearText.getContent();
    Assert.assertEquals("This is the sign message", content);    
  }
  
}
