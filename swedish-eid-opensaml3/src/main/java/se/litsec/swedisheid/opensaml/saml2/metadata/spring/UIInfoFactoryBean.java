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
import java.util.Map;

import org.opensaml.saml.ext.saml2mdui.Logo;
import org.opensaml.saml.ext.saml2mdui.UIInfo;

import se.litsec.swedisheid.opensaml.saml2.AbstractXMLObjectBuilder;
import se.litsec.swedisheid.opensaml.saml2.LocalizedString;
import se.litsec.swedisheid.opensaml.saml2.metadata.builders.UIInfoBuilder;
import se.litsec.swedisheid.opensaml.saml2.spring.AbstractXMLObjectBuilderFactoryBean;

/**
 * A Spring factory bean for creating {@link UIInfo} objects.
 * 
 * @author Martin Lindström (martin.lindstrom@litsec.se)
 * @see UIInfoBuilder
 */
public class UIInfoFactoryBean extends AbstractXMLObjectBuilderFactoryBean<UIInfo> {
  
  /** The builder. */
  private UIInfoBuilder builder;
  
  /**
   * Constructor.
   */
  public UIInfoFactoryBean() {
    this.builder = new UIInfoBuilder();
  }

  /** {@inheritDoc} */
  @Override
  public Class<?> getObjectType() {
    return UIInfo.class;
  }
  
  /** {@inheritDoc} */  
  @Override
  protected AbstractXMLObjectBuilder<UIInfo> builder() {
    return this.builder;
  }

  /**
   * @see UIInfoBuilder#displayNames(LocalizedString...)
   */
  public void setDisplayNames(List<LocalizedString> displayNames) {
    this.builder.displayNames(localizedStringListToVarArgs(displayNames));
  }

  /**
   * @see UIInfoBuilder#displayNames(LocalizedString...)
   */
  public void setDisplayName(LocalizedString displayName) {
    this.builder.displayNames(displayName);
  }

  /**
   * @see UIInfoBuilder#keywords(Map)
   */
  public void setKeywords(Map<String, List<String>> keywords) {
    this.builder.keywords(keywords);
  }

  /**
   * @see UIInfoBuilder#descriptions(LocalizedString...)
   */
  public void setDescriptions(List<LocalizedString> descriptions) {
    this.builder.descriptions(localizedStringListToVarArgs(descriptions));
  }

  /**
   * @see UIInfoBuilder#descriptions(LocalizedString...)
   */
  public void setDescription(LocalizedString description) {
    this.builder.descriptions(description);
  }

  /**
   * @see UIInfoBuilder#logos(Logo...)
   */
  public void setLogos(List<Logo> logos) {
    this.builder.logos(toVarArgs(logos, Logo.class));
  }

  /**
   * @see UIInfoBuilder#logos(Logo...)
   */
  public void setLogo(Logo logo) {
    this.builder.logos(logo);
  }

  /**
   * @see UIInfoBuilder#informationURLs(LocalizedString...)
   */
  public void setInformationURLs(List<LocalizedString> informationURLs) {
    this.builder.informationURLs(localizedStringListToVarArgs(informationURLs));
  }

  /**
   * @see UIInfoBuilder#informationURLs(LocalizedString...)
   */
  public void setInformationURL(LocalizedString informationURL) {
    this.builder.informationURLs(informationURL);
  }

  /**
   * @see UIInfoBuilder#privacyStatementURLs(LocalizedString...)
   */
  public void setPrivacyStatementURLs(List<LocalizedString> privacyStatementURLs) {
    this.builder.privacyStatementURLs(localizedStringListToVarArgs(privacyStatementURLs));
  }

  /**
   * @see UIInfoBuilder#privacyStatementURLs(LocalizedString...)
   */
  public void setPrivacyStatementURL(LocalizedString privacyStatementURL) {
    this.builder.privacyStatementURLs(privacyStatementURL);
  }

}
