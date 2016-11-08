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

package se.litsec.swedisheid.opensaml;

import java.util.Properties;

import org.springframework.util.StringUtils;

import se.litsec.swedisheid.opensaml.saml2.LocalizedString;

/**
 * Helper methods for testing.
 * 
 * @author Martin Lindström (martin.lindstrom@litsec.se)
 */
public class TestHelper {

  // Hidden
  private TestHelper() {
  }
  
  public static LocalizedString getLocalizedString(Properties p, String key) {
    String s = p.getProperty(key);
    if (!StringUtils.hasText(s)) {
      return null;
    }
    return LocalizedString.parse(s.trim());
  }

  public static String[] getStringArray(Properties p, String key) {
    String s = p.getProperty(key);
    if (!StringUtils.hasText(s)) {
      return null;
    }
    String[] arr = s.trim().split(",");
    String[] larr = new String[arr.length];
    for (int i = 0; i < arr.length; i++) {
      larr[i] = arr[i].trim(); 
    }
    return larr;
  }

  public static LocalizedString[] getLocalizedStringArray(Properties p, String key) {
    String s = p.getProperty(key);
    if (!StringUtils.hasText(s)) {
      return null;
    }
    String[] arr = s.trim().split(",");
    LocalizedString[] larr = new LocalizedString[arr.length];
    for (int i = 0; i < arr.length; i++) {
      larr[i] = LocalizedString.parse(arr[i]); 
    }
    return larr;
  }

}
