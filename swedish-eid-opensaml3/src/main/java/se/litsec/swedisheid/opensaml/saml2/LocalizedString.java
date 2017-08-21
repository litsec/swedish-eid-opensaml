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
package se.litsec.swedisheid.opensaml.saml2;

import java.util.Locale;

/**
 * Utility class for a localized string.
 * 
 * @author Martin Lindström (martin.lindstrom@litsec.se)
 * @see Locale
 */
public class LocalizedString {
  
  /** Default language tag if no tag is given. */
  public static final String DEFAULT_LANGUAGE_TAG = "en";

  /** Localized string. */
  private String localizedString;

  /** Language of the localized string. */
  private String language;

  /**
   * Constructor creating an empty string with the default language tag.
   */
  public LocalizedString() {
  }

  /**
   * Constructor creating a string with the default language tag.
   * 
   * @param localString
   *          the string
   */
  public LocalizedString(String localString) {
    this.localizedString = localString;
  }

  /**
   * Constructor.
   *
   * @param localString
   *          the localized string
   * @param language
   *          the language of the string
   */
  public LocalizedString(String localString, String language) {
    this.localizedString = localString;
    this.language = language;
  }

  /**
   * Constructor.
   *
   * @param localString
   *          the localized string
   * @param locale
   *          the locale (the language is obtained using {@link Locale#getLanguage()})
   */
  public LocalizedString(String localString, Locale locale) {
    this.localizedString = localString;
    this.language = locale.getLanguage();
  }

  /**
   * Parses strings on the format <lang-tag>-<string according to language>. The string "en-Hello" will give a
   * LocalizedString where:
   * 
   * <pre>
   * ls.getLanguage() => "en"
   * ls.getLocalString() => "Hello"
   * </pre>
   * 
   * @param source
   *          the sting to parse
   * @return a {@code LocalizedString}
   */
  public static LocalizedString parse(String source) {
    LocalizedString s = new LocalizedString();
    int i = source.indexOf('-');
    if (i <= 0 || i > 3) {
      s.setLocalizedString(source);
    }
    else {
      s.setLanguage(source.substring(0, i));
      s.setLocalizedString(source.substring(i + 1));
    }
    return s;
  }

  /**
   * Gets the localized string.
   *
   * @return the localized string
   */
  public String getLocalString() {
    return this.localizedString;
  }

  /**
   * Sets the localized string.
   *
   * @param newString
   *          the localized string
   */
  public void setLocalizedString(String newString) {
    this.localizedString = newString;
  }

  /**
   * Gets the language of the string.
   *
   * @return the language of the string
   */
  public String getLanguage() {
    return this.language != null ? this.language : DEFAULT_LANGUAGE_TAG;
  }

  /**
   * Sets the language of the string.
   *
   * @param newLanguage
   *          the language of the string
   */
  public void setLanguage(String newLanguage) {
    this.language = newLanguage;
  }

  /** {@inheritDoc} */
  @Override
  public int hashCode() {
    int hash = 1;
    hash = hash * 31 + this.language.hashCode();
    hash = hash * 31 + this.localizedString.hashCode();
    return hash;
  }

  /**
   * Determines if two LocalizedStrings are equal, that is, if both thier localized string and language have
   * case-sentivite equality.
   *
   * @param obj
   *          the object this object is compared with
   *
   * @return true if the objects are equal, false if not
   */
  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj instanceof LocalizedString) {
      LocalizedString otherLString = (LocalizedString) obj;
      return this.localizedString.equals(otherLString.getLocalString()) && this.language.equals(otherLString.getLanguage());
    }
    return false;
  }

  /** {@inheritDoc} */
  @Override
  public String toString() {
    return  String.format("[%s] %s", this.getLanguage(), this.localizedString);
  }

}
