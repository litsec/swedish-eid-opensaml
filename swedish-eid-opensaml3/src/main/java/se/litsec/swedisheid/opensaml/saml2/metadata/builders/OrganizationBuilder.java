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

import org.opensaml.saml.saml2.metadata.Organization;
import org.opensaml.saml.saml2.metadata.OrganizationDisplayName;
import org.opensaml.saml.saml2.metadata.OrganizationName;
import org.opensaml.saml.saml2.metadata.OrganizationURL;

import se.litsec.swedisheid.opensaml.saml2.AbstractXMLObjectBuilder;
import se.litsec.swedisheid.opensaml.saml2.LocalizedString;
import se.litsec.swedisheid.opensaml.utils.SAMLUtils;

/**
 * A builder for {@code Organization} elements.
 * 
 * @author Martin Lindström (martin.lindstrom@litsec.se)
 */
public class OrganizationBuilder extends AbstractXMLObjectBuilder<Organization> {

  /** {@inheritDoc} */
  @Override
  protected Class<Organization> getObjectType() {
    return Organization.class;
  }

  /**
   * Assigns the {@code OrganizationName} elements.
   * 
   * @param organizationNames
   *          the names
   * @return the builder
   */
  public OrganizationBuilder organizationNames(LocalizedString... organizationNames) {
    if (organizationNames != null) {
      for (LocalizedString s : organizationNames) {
        OrganizationName on = SAMLUtils.createSamlObject(OrganizationName.class);
        on.setValue(s.getLocalString());
        on.setXMLLang(s.getLanguage());
        this.object().getOrganizationNames().add(on);
      }
    }
    return this;
  }

  /**
   * Assigns the {@code OrganizationDisplayName} elements
   * 
   * @param organizationDisplayNames
   *          the names
   * @return the builder
   */
  public OrganizationBuilder organizationDisplayNames(LocalizedString... organizationDisplayNames) {
    if (organizationDisplayNames != null) {
      for (LocalizedString s : organizationDisplayNames) {
        OrganizationDisplayName on = SAMLUtils.createSamlObject(OrganizationDisplayName.class);
        on.setValue(s.getLocalString());
        on.setXMLLang(s.getLanguage());
        this.object().getDisplayNames().add(on);
      }
    }
    return this;
  }

  /**
   * Assigns the {@code OrganizationURL} elements
   * 
   * @param organizationURLs
   *          the URLs
   * @return the builder
   */
  public OrganizationBuilder organizationURLs(LocalizedString... organizationURLs) {
    if (organizationURLs != null) {
      for (LocalizedString s : organizationURLs) {
        OrganizationURL on = SAMLUtils.createSamlObject(OrganizationURL.class);
        on.setValue(s.getLocalString());
        on.setXMLLang(s.getLanguage());
        this.object().getURLs().add(on);
      }
    }
    return this;
  }

}
