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

import static se.litsec.swedisheid.opensaml.utils.InputUtils.trim;

import org.opensaml.saml.ext.saml2mdui.Logo;

import se.litsec.swedisheid.opensaml.saml2.AbstractXMLObjectBuilder;
import se.litsec.swedisheid.opensaml.saml2.metadata.builders.LogoBuilder;
import se.litsec.swedisheid.opensaml.saml2.spring.AbstractXMLObjectBuilderFactoryBean;

/**
 * A Spring factory bean for creating {@link Logo} objects.
 * 
 * @author Martin Lindström (martin.lindstrom@litsec.se)
 * @see LogoBuilder
 */
public class LogoFactoryBean extends AbstractXMLObjectBuilderFactoryBean<Logo> {

  /** The builder. */
  private LogoBuilder builder;

  /**
   * Constructor setting the the URL, height and width, but no language tag.
   * 
   * @param url
   *          the logo URL
   * @param height
   *          the height in pixels
   * @param width
   *          the width in pixels
   */
  public LogoFactoryBean(String url, Integer height, Integer width) {
    this(url, null, height, width);
  }

  /**
   * Constructor setting the the URL, its language tag and the height and width.
   * 
   * @param url
   *          the logo URL
   * @param language
   *          the language tag
   * @param height
   *          the height in pixels
   * @param width
   *          the width in pixels
   */
  public LogoFactoryBean(String url, String language, Integer height, Integer width) {
    this.builder = new LogoBuilder();
    this.builder.url(trim(url));
    this.builder.language(trim(language));
    this.builder.height(height);
    this.builder.width(width);
  }

  /** {@inheritDoc} */
  @Override
  public Class<?> getObjectType() {
    return Logo.class;
  }

  /** {@inheritDoc} */
  @Override
  protected AbstractXMLObjectBuilder<Logo> builder() {
    return this.builder;
  }

}
