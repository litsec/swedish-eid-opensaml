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
package se.litsec.swedisheid.opensaml.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import se.litsec.swedisheid.opensaml.saml2.LocalizedString;

/**
 * Misc. methods used to assert and process input parameters.
 * 
 * @author Martin Lindström (martin.lindstrom@litsec.se)
 */
public class InputUtils {

  /**
   * Asserts that the supplied object is not {@code null}.
   * 
   * @param object
   *          the object to check
   * @param parameterName
   *          optional name of parameter that is checked (will be included in the exception message if {@code object} is
   *          {@code null})
   * @throws IllegalArgumentException
   *           thrown if the supplied object is {@code null}
   */
  public static void assertNotNull(Object object, String parameterName) throws IllegalArgumentException {
    if (object == null) {
      throw new IllegalArgumentException(String.format("%s must not be null", parameterName != null ? parameterName : "Argument"));
    }
  }
  
  public static void assertNotEmpty(String object, String parameterName) throws IllegalArgumentException {
    if (object == null || object.trim().isEmpty()) {
      throw new IllegalArgumentException(String.format("%s must not be null or empty string", parameterName != null ? parameterName : "Argument"));
    }
  }
  
  /**
   * Trims a string and if it only contains blanks returns {@code null}.
   * 
   * @param s
   *          the string to trim
   * @return the trimmed string or {@code null}
   */
  public static String trim(String s) {
    if (s == null) {
      return null;
    }
    String s2 = StringUtils.trimWhitespace(s); 
    return s2.isEmpty() ? null : s2;
  }

  /**
   * Trims a string and if it only contains blanks returns {@code null}.
   * 
   * @param s
   *          the string to trim
   * @return the trimmed string or {@code null}
   */  
  public static LocalizedString trim(LocalizedString s) {
    if (s == null) {
      return null;
    }
    LocalizedString s2 = new LocalizedString(trim(s.getLocalString()), trim(s.getLanguage()));
    return s2.getLocalString() != null ? s2 : null;
  }
  
  /**
   * Trims the strings of the list and removes those that only contains blanks.
   * 
   * @param list
   *          the list to trim
   * @return the trimmed list or {@code null}
   */
  public static List<String> trim(List<String> list) {
    if (list == null) {
      return null;
    }
    List<String> list2 = new ArrayList<String>();
    for (String s : list) {
      String s2 = trim(s);
      if (s2 != null) {
        list2.add(s2);
      }
    }
    return list2.isEmpty() ? null : list2;
  }


  // Hidden
  private InputUtils() {
  }

}
