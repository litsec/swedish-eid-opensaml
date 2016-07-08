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

import org.junit.Assert;
import org.junit.Test;
import org.opensaml.saml.saml2.metadata.Organization;

import se.litsec.swedisheid.opensaml.OpenSAMLTestBase;
import se.litsec.swedisheid.opensaml.saml2.LocalizedString;

/**
 * Test cases for {@code OrganizationBuilder}.
 * 
 * @author Martin Lindström (martin.lindstrom@litsec.se)
 */
public class OrganizationBuilderTest extends OpenSAMLTestBase {

  public static LocalizedString[] ORG_NAMES = new LocalizedString[] {
      new LocalizedString("Lindström IT Security AB", "se"),
      new LocalizedString("Lindström IT Security Ltd.", "en")
  };
  
  public static LocalizedString[] ORG_DISPLAY_NAMES = new LocalizedString[] {
      new LocalizedString("Litsec AB", "se"),
      new LocalizedString("Litsec Ltd.", "en")
  };
  
  public static LocalizedString ORG_URL = new LocalizedString("http://www.litsec.se");

  /**
   * Tests building an Organization object.
   * 
   * @throws Exception
   *           for errors
   */
  @Test
  public void testBuild() throws Exception {

    Organization org = (new OrganizationBuilder())
        .organizationNames(ORG_NAMES)
        .organizationDisplayNames(ORG_DISPLAY_NAMES)
        .organizationURLs(ORG_URL)
        .build();

    Assert.assertEquals(ORG_NAMES.length, org.getOrganizationNames().size());
    for (int i = 0; i < ORG_NAMES.length; i++) {
      Assert.assertEquals(ORG_NAMES[i].getLocalString(), org.getOrganizationNames().get(i).getValue());
      Assert.assertEquals(ORG_NAMES[i].getLanguage(), org.getOrganizationNames().get(i).getXMLLang());
    }
    Assert.assertEquals(ORG_DISPLAY_NAMES.length, org.getDisplayNames().size());
    for (int i = 0; i < ORG_DISPLAY_NAMES.length; i++) {
      Assert.assertEquals(ORG_DISPLAY_NAMES[i].getLocalString(), org.getDisplayNames().get(i).getValue());
      Assert.assertEquals(ORG_DISPLAY_NAMES[i].getLanguage(), org.getDisplayNames().get(i).getXMLLang());
    }
    Assert.assertEquals(1, org.getURLs().size());
    Assert.assertEquals(ORG_URL.getLocalString(), org.getURLs().get(0).getValue());
    Assert.assertEquals(ORG_URL.getLanguage(), org.getURLs().get(0).getXMLLang());
  }

  /**
   * Tests assigning null values.
   * 
   * @throws Exception
   *           for errors
   */
  @Test
  public void testBuildNull() throws Exception {

    Organization org = (new OrganizationBuilder())
        .organizationNames((LocalizedString[]) null)
        .organizationDisplayNames(new LocalizedString[]{})
        .build();

    Assert.assertTrue(org.getOrganizationNames().isEmpty());
    Assert.assertTrue(org.getDisplayNames().isEmpty());
    Assert.assertTrue(org.getURLs().isEmpty());
  }

}
