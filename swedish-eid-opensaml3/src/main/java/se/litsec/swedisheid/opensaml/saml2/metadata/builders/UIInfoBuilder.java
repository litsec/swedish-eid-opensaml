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

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.saml.ext.saml2mdui.Description;
import org.opensaml.saml.ext.saml2mdui.DisplayName;
import org.opensaml.saml.ext.saml2mdui.InformationURL;
import org.opensaml.saml.ext.saml2mdui.Keywords;
import org.opensaml.saml.ext.saml2mdui.Logo;
import org.opensaml.saml.ext.saml2mdui.PrivacyStatementURL;
import org.opensaml.saml.ext.saml2mdui.UIInfo;

import se.litsec.swedisheid.opensaml.saml2.AbstractXMLObjectBuilder;
import se.litsec.swedisheid.opensaml.saml2.LocalizedString;
import se.litsec.swedisheid.opensaml.utils.SAMLUtils;

/**
 * A builder for creating {@link UIInfo} objects.
 * 
 * @author Martin Lindström (martin.lindstrom@litsec.se)
 */
public class UIInfoBuilder extends AbstractXMLObjectBuilder<UIInfo> {

  /** {@inheritDoc} */
  @Override
  protected Class<UIInfo> getObjectType() {
    return UIInfo.class;
  }

  /**
   * Assigns the display names.
   * 
   * @param displayNames
   *          the names
   * @return the builder
   */
  public UIInfoBuilder displayNames(LocalizedString... displayNames) {
    this.object().getDisplayNames().clear();
    if (displayNames == null) {
      return this;
    }
    for (LocalizedString s : displayNames) {
      DisplayName dn = SAMLUtils.createSamlObject(DisplayName.class);
      dn.setValue(s.getLocalString());
      dn.setXMLLang(s.getLanguage());
      this.object().getDisplayNames().add(dn);
    }
    return this;
  }

  /**
   * Assigns the keywords.
   * 
   * @param keywords
   *          the keywords where the map keys are language tags
   * @return the builder
   */
  public UIInfoBuilder keywords(Map<String, List<String>> keywords) {
    this.object().getKeywords().clear();
    if (keywords == null) {
      return this;
    }
    for (Map.Entry<String, List<String>> e : keywords.entrySet()) {
      Keywords kw = SAMLUtils.createSamlObject(Keywords.class);
      if (!e.getKey().isEmpty()) {
        kw.setXMLLang(e.getKey());
      }
      kw.setKeywords(e.getValue());
      this.object().getKeywords().add(kw);
    }
    return this;
  }

  /**
   * Assigns a set of keywords that does not have the language tag.
   * 
   * @param keywords
   *          the keywords
   * @return the builder
   */
  public UIInfoBuilder keywords(String... keywords) {
    if (keywords != null) {
      Map<String, List<String>> m = new HashMap<String, List<String>>();
      m.put("", Arrays.asList(keywords));
      return this.keywords(m);
    }
    else {
      return this.keywords((Map<String, List<String>>) null);
    }
  }

  /**
   * Assigns the descriptions.
   * 
   * @param descriptions
   *          the descriptions
   * @return the builder
   */
  public UIInfoBuilder descriptions(LocalizedString... descriptions) {
    this.object().getDescriptions().clear();
    if (descriptions == null) {
      return this;
    }
    for (LocalizedString s : descriptions) {
      Description d = SAMLUtils.createSamlObject(Description.class);
      d.setValue(s.getLocalString());
      d.setXMLLang(s.getLanguage());
      this.object().getDescriptions().add(d);
    }
    return this;
  }

  /**
   * Assigns the logotypes.
   * 
   * @param logos
   *          the logos (will be cloned before assignment)
   * @return the builder
   */
  public UIInfoBuilder logos(Logo... logos) {
    this.object().getLogos().clear();
    if (logos == null) {
      return this;
    }
    try {
      for (Logo logo : logos) {
        this.object().getLogos().add(XMLObjectSupport.cloneXMLObject(logo));
      }
      return this;
    }
    catch (UnmarshallingException | MarshallingException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Assigns the information URL:s.
   * 
   * @param informationURLs
   *          the information URL:s
   * @return the builder
   */
  public UIInfoBuilder informationURLs(LocalizedString... informationURLs) {
    this.object().getInformationURLs().clear();
    if (informationURLs == null) {
      return this;
    }
    for (LocalizedString u : informationURLs) {
      InformationURL url = SAMLUtils.createSamlObject(InformationURL.class);
      url.setValue(u.getLocalString());
      url.setXMLLang(u.getLanguage());
      this.object().getInformationURLs().add(url);
    }
    return this;
  }

  /**
   * Assigns the privacy statement URL:s.
   * 
   * @param privacyStatementURLs
   *          the URL:s
   * @return the builder
   */
  public UIInfoBuilder privacyStatementURLs(LocalizedString... privacyStatementURLs) {
    this.object().getPrivacyStatementURLs().clear();
    if (privacyStatementURLs == null) {
      return this;
    }
    for (LocalizedString u : privacyStatementURLs) {
      PrivacyStatementURL url = SAMLUtils.createSamlObject(PrivacyStatementURL.class);
      url.setValue(u.getLocalString());
      url.setXMLLang(u.getLanguage());
      this.object().getPrivacyStatementURLs().add(url);
    }
    return this;
  }

}
