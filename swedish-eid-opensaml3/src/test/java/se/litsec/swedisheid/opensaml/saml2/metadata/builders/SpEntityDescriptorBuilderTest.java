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
package se.litsec.swedisheid.opensaml.saml2.metadata.builders;

import java.security.cert.X509Certificate;
import java.time.Duration;
import java.util.Locale;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.opensaml.saml.saml2.core.NameID;
import org.opensaml.saml.saml2.metadata.ContactPersonTypeEnumeration;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;
import org.opensaml.security.credential.UsageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import net.shibboleth.utilities.java.support.xml.SerializeSupport;
import se.litsec.swedisheid.opensaml.BaseConfig;
import se.litsec.swedisheid.opensaml.saml2.LocalizedString;
import se.litsec.swedisheid.opensaml.utils.SAMLUtils;

/**
 * Test cases for {@code SpEntityDescriptorBuilder}.
 * 
 * @author Martin Lindström (martin.lindstrom@litsec.se)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = BaseConfig.class)
public class SpEntityDescriptorBuilderTest {
  
  @Autowired
  @Qualifier("samlSigningCertificate")
  private X509Certificate signingCertificate;
  
  @Autowired
  @Qualifier("samlEncryptionCertificate")
  private X509Certificate encryptionCertificate;

  @Test
  public void testBuilder() throws Exception {

    SpEntityDescriptorBuilder builder = new SpEntityDescriptorBuilder();

    EntityDescriptor entityDescriptor = builder
      .entityID("http://www.litsec.se/sp")
      .id("QWERTY123456789")
      .cacheDuration(Duration.ofDays(1))
      .validUntilDuration(Duration.ofDays(7))
      .entityCategories("http://id.elegnamnden.se/ec/1.0/loa3-pnr", "http://id.elegnamnden.se/ec/1.0/loa4-pnr")
      .wantAssertionsSigned(true)
      .authnRequestsSigned(false)
      .uiInfoExtension((new UIInfoBuilder())
        .displayNames(new LocalizedString("Litsec SP för test", Locale.forLanguageTag("sv")), new LocalizedString("Litsec SP for testing", Locale.ENGLISH))
        .descriptions(new LocalizedString("Litsec SAML SP för teständamål", "sv"), new LocalizedString("Litsec SAML SP for testing purposes", "en"))
        .informationURLs(new LocalizedString("http://www.litsec.se/testsp"))
        .logos((new LogoBuilder())
          .url("http://www.litsec.se/onewebstatic/4a6ec88da7-pics-litsec.png")
          .height(79)
          .width(300)
          .build())
        .build())
      .discoveryResponse("https://eid.litsec.se/testsp/authnrequest/disco")
      .keyDescriptors(
        KeyDescriptorBuilder.build(UsageType.SIGNING, "Litsec Signing", this.signingCertificate),
        KeyDescriptorBuilder.build(UsageType.ENCRYPTION, "Litsec Encryption", this.encryptionCertificate))
      .nameIDFormats(NameID.PERSISTENT, NameID.TRANSIENT)
      .assertionConsumerServicePostLocations("https://eid.litsec.se/sp/post", "https://eid.litsec.se/sp/post2")
      .organization((new OrganizationBuilder())
        .organizationNames(new LocalizedString("Litsec AB", "sv"), new LocalizedString("Litsec Inc.", "en"))
        .organizationDisplayNames(new LocalizedString("Litsec"))
        .organizationURLs(new LocalizedString("http://www.litsec.se"))
        .build())
      .contactPersons(
        (new ContactPersonBuilder(ContactPersonTypeEnumeration.TECHNICAL))
          .company("Litsec AB")
          .givenName("Martin")
          .surname("Lindström")
          .emailAddresses("martin@litsec.se")
          .telephoneNumbers("+46 (0)70 361 98 80")
          .build(),
        (new ContactPersonBuilder(ContactPersonTypeEnumeration.SUPPORT))
          .company("Litsec AB")
          .givenName("Kalle")
          .surname("Kula")
          .emailAddresses("kalle@litsec.se")
          .telephoneNumbers("+46 (0)70 361 98 81")
          .build())
      .build();

    SAMLUtils.marshall(entityDescriptor);

    System.out.println(SerializeSupport.prettyPrintXML(entityDescriptor.getDOM()));
  }

}
