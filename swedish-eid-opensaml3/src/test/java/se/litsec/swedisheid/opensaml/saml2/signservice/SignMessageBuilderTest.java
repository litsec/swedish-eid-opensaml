package se.litsec.swedisheid.opensaml.saml2.signservice;

import java.security.KeyStore;
import java.security.cert.X509Certificate;

import org.junit.Assert;
import org.junit.Test;
import org.opensaml.security.x509.BasicX509Credential;
import org.opensaml.security.x509.impl.KeyStoreX509CredentialAdapter;
import org.springframework.core.io.ClassPathResource;
import org.w3c.dom.Element;

import net.shibboleth.utilities.java.support.xml.SerializeSupport;
import se.litsec.opensaml.utils.KeyStoreUtils;
import se.litsec.opensaml.utils.ObjectUtils;
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
