package se.litsec.swedisheid.opensaml.saml2.signservice;

import java.security.KeyStore;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Test;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml.saml2.metadata.KeyDescriptor;
import org.opensaml.security.credential.Credential;
import org.opensaml.security.credential.UsageType;
import org.opensaml.security.x509.impl.KeyStoreX509CredentialAdapter;
import org.opensaml.xmlsec.encryption.support.EncryptionConstants;
import org.opensaml.xmlsec.encryption.support.EncryptionException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import net.shibboleth.utilities.java.support.component.ComponentInitializationException;
import se.litsec.opensaml.saml2.metadata.build.IdpEntityDescriptorBuilder;
import se.litsec.opensaml.saml2.metadata.build.KeyDescriptorBuilder;
import se.litsec.opensaml.saml2.metadata.provider.CompositeMetadataProvider;
import se.litsec.opensaml.saml2.metadata.provider.MetadataProvider;
import se.litsec.opensaml.saml2.metadata.provider.StaticMetadataProvider;
import se.litsec.opensaml.utils.KeyStoreUtils;
import se.litsec.opensaml.xmlsec.SAMLObjectEncrypter;
import se.litsec.swedisheid.opensaml.OpenSAMLTestBase;
import se.litsec.swedisheid.opensaml.saml2.signservice.dss.Message;
import se.litsec.swedisheid.opensaml.saml2.signservice.dss.SignMessage;
import se.litsec.swedisheid.opensaml.saml2.signservice.dss.SignMessageMimeTypeEnum;

/**
 * Test cases for {@code SignMessageEncrypter}.
 * 
 * @author Martin Lindström (martin.lindstrom@litsec.se)
 */
public class SignMessageEncrypterTest extends OpenSAMLTestBase {

  private static final String ENTITY_ID = "http://www.example.com/idp";
  private static final String CONTENTS = "This is the sign message";

  @Test
  public void testDefault() throws Exception {

    SignMessage signMessage = SignMessageBuilder.builder()
      .displayEntity(ENTITY_ID)
      .message(CONTENTS)
      .mimeType(SignMessageMimeTypeEnum.TEXT)
      .mustShow(true)
      .build();

    // Setup metadata
    //
    EntityDescriptor ed = this.createMetadata(KeyDescriptorBuilder.builder()
      .use(UsageType.ENCRYPTION)
      .certificate(new ClassPathResource("Litsec_SAML_Encryption.crt").getInputStream())
      .build(), KeyDescriptorBuilder.builder()
        .use(UsageType.SIGNING)
        .certificate(new ClassPathResource("Litsec_SAML_Signing.crt").getInputStream())
        .build());
    SAMLObjectEncrypter objectEncrypter = new SAMLObjectEncrypter(this.createMetadataProvider(ed)); 

    SignMessageEncrypter encrypter = new SignMessageEncrypter(objectEncrypter);

    encrypter.encrypt(signMessage, ENTITY_ID);

    Assert.assertNotNull(signMessage.getEncryptedMessage());

    // Element e = ObjectUtils.marshall(signMessage);
    // System.out.println(SerializeSupport.prettyPrintXML(e));

    String decryptedMsg = this.decrypt(signMessage, new ClassPathResource("Litsec_SAML_Encryption.jks"), "secret",
      "litsec_saml_encryption");

    Assert.assertEquals(CONTENTS, decryptedMsg);
  }

  @Test
  public void testNoEncryptionCredentials() throws Exception {
    SignMessage signMessage = SignMessageBuilder.builder()
      .displayEntity(ENTITY_ID)
      .message(CONTENTS)
      .mimeType(SignMessageMimeTypeEnum.TEXT)
      .mustShow(true)
      .build();

    // Setup metadata
    //
    EntityDescriptor ed = this.createMetadata(KeyDescriptorBuilder.builder()
      .use(UsageType.SIGNING)
      .certificate(new ClassPathResource("Litsec_SAML_Signing.crt").getInputStream())
      .build());
    SAMLObjectEncrypter objectEncrypter = new SAMLObjectEncrypter(this.createMetadataProvider(ed));
    SignMessageEncrypter encrypter = new SignMessageEncrypter(objectEncrypter);

    try {
      encrypter.encrypt(signMessage, ENTITY_ID);
      Assert.fail("Expected error - no encryption credentials found");
    }
    catch (EncryptionException e) {
      System.out.println(e.getMessage());
    }
  }
  
  @Test
  public void testUnspecified() throws Exception {

    SignMessage signMessage = SignMessageBuilder.builder()
      .displayEntity(ENTITY_ID)
      .message(CONTENTS)
      .mimeType(SignMessageMimeTypeEnum.TEXT)
      .mustShow(true)
      .build();

    // Setup metadata
    //
    EntityDescriptor ed = this.createMetadata(KeyDescriptorBuilder.builder()
      .use(UsageType.UNSPECIFIED)
      .certificate(new ClassPathResource("Litsec_SAML_Encryption.crt").getInputStream())
      .build(), KeyDescriptorBuilder.builder()
        .use(UsageType.SIGNING)
        .certificate(new ClassPathResource("Litsec_SAML_Signing.crt").getInputStream())
        .build());
    SAMLObjectEncrypter objectEncrypter = new SAMLObjectEncrypter(this.createMetadataProvider(ed));

    SignMessageEncrypter encrypter = new SignMessageEncrypter(objectEncrypter);

    encrypter.encrypt(signMessage, ENTITY_ID);

    Assert.assertNotNull(signMessage.getEncryptedMessage());

    // Element e = ObjectUtils.marshall(signMessage);
    // System.out.println(SerializeSupport.prettyPrintXML(e));

    String decryptedMsg = this.decrypt(signMessage, new ClassPathResource("Litsec_SAML_Encryption.jks"), "secret",
      "litsec_saml_encryption");

    Assert.assertEquals(CONTENTS, decryptedMsg);
  }
  
  @Test
  public void testCapabilitiesSimple() throws Exception {

    SignMessage signMessage = SignMessageBuilder.builder()
      .displayEntity(ENTITY_ID)
      .message(CONTENTS)
      .mimeType(SignMessageMimeTypeEnum.TEXT)
      .mustShow(true)
      .build();

    // Setup metadata
    //
    EntityDescriptor ed = this.createMetadata(KeyDescriptorBuilder.builder()
      .use(UsageType.ENCRYPTION)
      .certificate(new ClassPathResource("Litsec_SAML_Encryption.crt").getInputStream())
      .encryptionMethods(EncryptionConstants.ALGO_ID_BLOCKCIPHER_AES256_GCM, EncryptionConstants.ALGO_ID_KEYTRANSPORT_RSA15)
      .build(), 
      KeyDescriptorBuilder.builder()
        .use(UsageType.SIGNING)
        .certificate(new ClassPathResource("Litsec_SAML_Signing.crt").getInputStream())
        .build());
    SAMLObjectEncrypter objectEncrypter = new SAMLObjectEncrypter(this.createMetadataProvider(ed));
    SignMessageEncrypter encrypter = new SignMessageEncrypter(objectEncrypter);

    encrypter.encrypt(signMessage, ENTITY_ID);

    Assert.assertNotNull(signMessage.getEncryptedMessage());

//    Element e = ObjectUtils.marshall(signMessage);
//    System.out.println(SerializeSupport.prettyPrintXML(e));

    String decryptedMsg = this.decrypt(signMessage, new ClassPathResource("Litsec_SAML_Encryption.jks"), "secret",
      "litsec_saml_encryption");

    Assert.assertEquals(CONTENTS, decryptedMsg);
  }  

  private String decrypt(SignMessage signMessage, Resource jks, String password, String alias) throws Exception {
    KeyStore keyStore = KeyStoreUtils.loadKeyStore(jks.getInputStream(), password, "JKS");
    Credential cred = new KeyStoreX509CredentialAdapter(keyStore, alias, password.toCharArray());

    SignMessageDecrypter decrypter = new SignMessageDecrypter(cred);
    Message msg = decrypter.decrypt(signMessage);
    return msg.getContent();
  }

  private EntityDescriptor createMetadata(KeyDescriptor... descriptors) {
    IdpEntityDescriptorBuilder builder = new IdpEntityDescriptorBuilder();
    return builder.entityID(ENTITY_ID).id("_id123456").keyDescriptors(descriptors).build();
  }

  private MetadataProvider createMetadataProvider(EntityDescriptor... descriptors) throws MarshallingException,
      ComponentInitializationException {
    MetadataProvider mp;
    if (descriptors.length == 1) {
      mp = new StaticMetadataProvider(descriptors[0]);
    }
    else {
      mp = new CompositeMetadataProvider("md", Arrays.asList(descriptors).stream().map(d -> {
        try {
          return new StaticMetadataProvider(d);
        }
        catch (MarshallingException e) {
          throw new RuntimeException(e);
        }
      }).collect(Collectors.toList()));
    }
    mp.initialize();
    return mp;
  }

}
