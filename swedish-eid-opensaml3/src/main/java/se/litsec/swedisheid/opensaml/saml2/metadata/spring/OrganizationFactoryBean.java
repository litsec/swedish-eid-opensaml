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
package se.litsec.swedisheid.opensaml.saml2.metadata.spring;

import java.util.List;

import org.opensaml.saml.saml2.metadata.Organization;

import se.litsec.swedisheid.opensaml.saml2.AbstractXMLObjectBuilder;
import se.litsec.swedisheid.opensaml.saml2.LocalizedString;
import se.litsec.swedisheid.opensaml.saml2.metadata.builders.OrganizationBuilder;
import se.litsec.swedisheid.opensaml.saml2.spring.AbstractXMLObjectBuilderFactoryBean;

/**
 * A Spring factory bean for creating {@link Organization} objects.
 * 
 * @author Martin Lindström (martin.lindstrom@litsec.se)
 * @see OrganizationBuilder
 */
public class OrganizationFactoryBean extends AbstractXMLObjectBuilderFactoryBean<Organization> {
  
  /** The builder. */
  private OrganizationBuilder builder;
  
  /**
   * Constructor.
   */
  public OrganizationFactoryBean() {
    this.builder = new OrganizationBuilder();
  }

  /** {@inheritDoc} */
  @Override
  public Class<?> getObjectType() {
    return Organization.class;
  }
    
  /** {@inheritDoc} */
  @Override
  protected AbstractXMLObjectBuilder<Organization> builder() {
    return this.builder;
  }

  /**
   * @see OrganizationBuilder#organizationNames(LocalizedString...)
   */
  public void setOrganizationNames(List<LocalizedString> organizationNames) {
    this.builder.organizationNames(localizedStringListToVarArgs(organizationNames));
  }

  /**
   * @see OrganizationBuilder#organizationNames(LocalizedString...)
   */
  public void setOrganizationName(LocalizedString organizationName) {
    this.builder.organizationNames(organizationName);
  }

  /**
   * @see OrganizationBuilder#organizationDisplayNames(LocalizedString...)
   */
  public void setOrganizationDisplayNames(List<LocalizedString> organizationDisplayNames) {
    this.builder.organizationDisplayNames(localizedStringListToVarArgs(organizationDisplayNames));
  }

  /**
   * @see OrganizationBuilder#organizationDisplayNames(LocalizedString...)
   */
  public void setOrganizationDisplayName(LocalizedString organizationDisplayName) {
    this.builder.organizationDisplayNames(organizationDisplayName);
  }

  /**
   * @see OrganizationBuilder#organizationURLs(LocalizedString...)
   */
  public void setOrganizationURLs(List<LocalizedString> organizationURLs) {
    this.builder.organizationURLs(localizedStringListToVarArgs(organizationURLs));
  }

  /**
   * @see OrganizationBuilder#organizationURLs(LocalizedString...)
   */
  public void setOrganizationURL(LocalizedString organizationURL) {
    this.builder.organizationURLs(organizationURL);
  }

}
