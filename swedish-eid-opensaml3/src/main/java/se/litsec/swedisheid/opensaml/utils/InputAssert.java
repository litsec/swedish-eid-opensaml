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

/**
 * Misc. methods used to assert input parameters.
 * 
 * @author Martin Lindström (martin.lindstrom@litsec.se)
 */
public class InputAssert {

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
  public static void notNull(Object object, String parameterName) throws IllegalArgumentException {
    if (object == null) {
      throw new IllegalArgumentException(String.format("%s must not be null", parameterName != null ? parameterName : "Argument"));
    }
  }
  
  public static void notEmpty(String object, String parameterName) throws IllegalArgumentException {
    if (object == null || object.trim().isEmpty()) {
      throw new IllegalArgumentException(String.format("%s must not be null or empty string", parameterName != null ? parameterName : "Argument"));
    }
  }

  // Hidden
  private InputAssert() {
  }

}
