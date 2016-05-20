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
package se.litsec.swedisheid.opensaml.saml2.metadata;

import java.util.List;
import java.util.Optional;

import org.opensaml.saml.common.xml.SAMLConstants;
import org.opensaml.saml.ext.saml2mdattr.EntityAttributes;
import org.opensaml.saml.ext.saml2mdui.Description;
import org.opensaml.saml.ext.saml2mdui.DisplayName;
import org.opensaml.saml.ext.saml2mdui.UIInfo;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml.saml2.metadata.Extensions;
import org.opensaml.saml.saml2.metadata.SSODescriptor;

import se.litsec.swedisheid.opensaml.utils.SAMLUtils;

/**
 * Utility methods for accessing metadata elements.
 * 
 * @author Martin Lindström (martin.lindstrom@litsec.se)
 */
public class MetadataUtils {

  /**
   * Finds the first extension matching the supplied type.
   * 
   * @param extensions
   *          the {@link Extensions} to search
   * @param clazz
   *          the extension type
   * @return the matching extension
   */
  public static <T> Optional<T> getMetadataExtension(Extensions extensions, Class<T> clazz) {
    if (extensions == null) {
      return Optional.empty();
    }
    return extensions.getOrderedChildren().stream()
        .filter(e -> clazz.isAssignableFrom(e.getClass()))
        .map(e -> clazz.cast(e))
        .findFirst();
  }

  /**
   * Returns the EntityAttributes element that is placed as an extension to the supplied entity descriptor.
   * 
   * @param ed
   *          the entity descriptor
   * @return the EntityAttributes element
   */
  public static Optional<EntityAttributes> getEntityAttributes(EntityDescriptor ed) {
    return getMetadataExtension(ed.getExtensions(), EntityAttributes.class);
  }

  /**
   * Utility method that returns a list of the {@code mdui:DisplayName} element found in the supplied SSO descriptor's
   * extension.
   * 
   * @param ed
   *          the entity descriptor
   * @return a list of {@code DisplayName} elements or {@code null} if none are found.
   */
  public static List<DisplayName> getUiDisplayNames(EntityDescriptor ed) {
    SSODescriptor ssoDescriptor = MetadataUtils.getSSODescriptor(ed);
    if (ssoDescriptor == null) {
      return null;
    }
    UIInfo uiInfo = SAMLUtils.getSamlExtension(ssoDescriptor.getExtensions(), UIInfo.class);
    if (uiInfo == null) {
      return null;
    }
    return uiInfo.getDisplayNames();
  }

  /**
   * Utility method that returns the {@code mdui:DisplayName} element for the given language tag from the supplied SSO
   * descriptor's extension.
   * 
   * @param ed
   *          the entity descriptor
   * @param language
   *          the language tag
   * @return the display name for the given language
   */
  public static Optional<String> getUiDisplayName(EntityDescriptor ed, String language) {
    List<DisplayName> names = MetadataUtils.getUiDisplayNames(ed);
    if (names == null) {
      return null;
    }
    for (DisplayName dn : names) {
      if (dn.getName().getLanguage().equals(language)) {
        return dn.getName().getLocalString();
      }
    }
    return null;
  }

  /**
   * Utility method that returns a list of the {@code mdui:Description} element found in the supplied SSO descriptor's
   * extension.
   * 
   * @param ed
   *          the entity descriptor
   * @return a list of {@code Description} elements or {@code null} if none are found.
   */
  public static List<Description> getUiDescriptions(EntityDescriptor ed) {
    SSODescriptor ssoDescriptor = MetadataUtils.getSSODescriptor(ed);
    if (ssoDescriptor == null) {
      return null;
    }
    UIInfo uiInfo = SAMLUtils.getSamlExtension(ssoDescriptor.getExtensions(), UIInfo.class);
    if (uiInfo == null) {
      return null;
    }
    return uiInfo.getDescriptions();
  }

  /**
   * Utility method that returns the {@code mdui:Description} element for the given language tag from the supplied SSO
   * descriptor's extension.
   * 
   * @param ed
   *          the entity descriptor
   * @param language
   *          the language tag
   * @return a {@code Description} element or {@code null} if none are found.
   */
  public static String getUiDescription(EntityDescriptor ed, String language) {
    List<Description> descriptions = MetadataUtils.getUiDescriptions(ed);
    if (descriptions == null) {
      return null;
    }
    for (Description d : descriptions) {
      if (d.getDescription().getLanguage().equals(language)) {
        return d.getDescription().getLocalString();
      }
    }
    return null;
  }

  /**
   * Returns the SSODescriptor for the supplied SP or IdP entity descriptor.
   * 
   * @param ed
   *          the entity descriptor
   * @return the SSODescriptor
   */
  private static SSODescriptor getSSODescriptor(EntityDescriptor ed) {
    if (ed.getIDPSSODescriptor(SAMLConstants.SAML20P_NS) != null) {
      return ed.getIDPSSODescriptor(SAMLConstants.SAML20P_NS);
    }
    else {
      return ed.getSPSSODescriptor(SAMLConstants.SAML20P_NS);
    }
  }

  // Hidden
  private MetadataUtils() {
  }

}
