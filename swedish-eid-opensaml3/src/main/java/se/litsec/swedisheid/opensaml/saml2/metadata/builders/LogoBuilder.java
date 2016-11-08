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

import org.opensaml.saml.ext.saml2mdui.Logo;

import se.litsec.swedisheid.opensaml.saml2.AbstractXMLObjectBuilder;

/**
 * A builder for {@code mdui:Logo} elements.
 * 
 * @author Martin Lindström (martin.lindstrom@litsec.se)
 */
public class LogoBuilder extends AbstractXMLObjectBuilder<Logo> {

  /** {@inheritDoc} */
  @Override
  protected Class<Logo> getObjectType() {
    return Logo.class;
  }

  /**
   * Assigns the URL of the {@code Logo}.
   * 
   * @param url
   *          the URL
   * @return the builder
   */
  public LogoBuilder url(String url) {
    this.object().setURL(url);
    return this;
  }

  /**
   * Assigns the language tag of the {@code Logo}.
   * 
   * @param language
   *          the language tag
   * @return the builder
   */
  public LogoBuilder language(String language) {
    this.object().setXMLLang(language);
    return this;
  }

  /**
   * Assigns the height of the {@code Logo}.
   * 
   * @param height
   *          the height (in pixels)
   * @return the builder
   */
  public LogoBuilder height(Integer height) {
    this.object().setHeight(height);
    return this;
  }

  /**
   * Assigns the width of the {@code Logo}.
   * 
   * @param width
   *          the width (in pixels)
   * @return the builder
   */
  public LogoBuilder width(Integer width) {
    this.object().setWidth(width);
    return this;
  }

}
