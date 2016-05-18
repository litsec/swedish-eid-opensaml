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
package se.litsec.swedisheid.opensaml.saml2.authentication;

/**
 * Authentication Context URIs defined for the Swedish eID Framework.
 * 
 * @author Martin Lindström (martin.lindstrom@litsec.se)
 */
public class LevelofAssuranceAuthenticationContextURI {

  /**
   * Enum for Level of Assurance.
   */
  public enum LoaEnum {

    /** Level of Assurance 1. */
    LOA_1(1, LevelofAssuranceAuthenticationContextURI.AUTH_CONTEXT_URI_LOA1, false),

    /** Level of Assurance 2. */
    LOA_2(2, LevelofAssuranceAuthenticationContextURI.AUTH_CONTEXT_URI_LOA2, false),

    /** Level of Assurance 2 - for use in signature services. */
    LOA_2_SIGMESSAGE(2, LevelofAssuranceAuthenticationContextURI.AUTH_CONTEXT_URI_LOA2_SIGMESSAGE, true),

    /** Level of Assurance 3. */
    LOA_3(3, LevelofAssuranceAuthenticationContextURI.AUTH_CONTEXT_URI_LOA3, false),

    /** Level of Assurance 3 - for use in signature services. */
    LOA_3_SIGMESSAGE(3, LevelofAssuranceAuthenticationContextURI.AUTH_CONTEXT_URI_LOA3_SIGMESSAGE, true),

    /** Level of Assurance 4. */
    LOA_4(4, LevelofAssuranceAuthenticationContextURI.AUTH_CONTEXT_URI_LOA4, false),

    /** Level of Assurance 4 - for use in signature services. */
    LOA_4_SIGMESSAGE(4, LevelofAssuranceAuthenticationContextURI.AUTH_CONTEXT_URI_LOA4_SIGMESSAGE, true);

    /**
     * Given a URI the method returns the enum value matching.
     * 
     * @param uri
     *          URI
     * @return the matching enum value or {@code null} is no match is found
     */
    public static LoaEnum parse(String uri) {
      for (LoaEnum loa : LoaEnum.values()) {
        if (loa.getUri().equals(uri)) {
          return loa;
        }
      }
      return null;
    }

    /**
     * Returns the numeric level for this LoA.
     * 
     * @return the numeric level.
     */
    public int getLevel() {
      return this.level;
    }

    /**
     * Returns the URI for this LoA as defined by the Swedish e-identification board.
     * 
     * @return an URI.
     */
    public String getUri() {
      return this.uri;
    }

    /**
     * Indicates whether the identifier is intended for use with signature services.
     * 
     * @return if the identifier is intended for use with signature services {@code true} is returned and otherwise
     *         {@code false}
     */
    public boolean isSignatureMessageUri() {
      return this.sigMessage;
    }

    /**
     * Enum constructor.
     * 
     * @param level
     *          the numeric level for this LoA level
     * @param url
     *          the URL identifier for this LoA level
     * @param sigMessage
     *          indicator whether this LoA is for signature messages
     */
    LoaEnum(int level, String url, boolean sigMessage) {
      this.level = level;
      this.uri = url;
      this.sigMessage = sigMessage;
    }

    /** The URI as defined by the Swedish e-identification board. */
    private String uri;

    /** The number representing the level. */
    private int level;

    /** Is this is signature message LoA identifier? */
    private boolean sigMessage;
  }

  /**
   * Returns the Level of Assurance numeric level based on an URI.
   * 
   * @param uri
   *          the Authentication Context URI representing a level of assurance
   * @return the numeric value
   */
  public static int getLevel(String uri) {
    LoaEnum e = LoaEnum.parse(uri);
    if (e == null) {
      return 0;
    }
    return e.getLevel();
  }

  /**
   * Based on a level indicator the method returns the authentication context URI representing the level of assurance.
   * 
   * @param level
   *          numeric level of assurance
   * @return the authentication context URI representing the supplied level of assurance
   */
  public static String getLoaUri(int level) {
    for (LoaEnum loa : LoaEnum.values()) {
      if (loa.getLevel() == level && !loa.isSignatureMessageUri()) {
        return loa.getUri();
      }
    }
    return null;
  }

  /**
   * Based on a level indicator the method returns the signature message authentication context URI representing the
   * level of assurance.
   * 
   * @param level
   *          numeric level of assurance
   * @return the authentication context URI representing the supplied level of assurance
   */
  public static String getSignatureMessageLoaUri(int level) {
    for (LoaEnum loa : LoaEnum.values()) {
      if (loa.getLevel() == level && loa.isSignatureMessageUri()) {
        return loa.getUri();
      }
    }
    return null;
  }

  /**
   * Given an authentication context URI the method returns the corresponding signature message authentication context
   * URI.
   * 
   * @param loaUri
   *          authentication context URI
   * @return a signature message authentication context URI
   */
  public static String toSignatureMessageLoaUri(String loaUri) {
    LoaEnum loa = LoaEnum.parse(loaUri);
    if (loa == null) {
      return null;
    }
    return getSignatureMessageLoaUri(loa.getLevel());
  }

  /**
   * Given an signature message authentication context URI the method returns the corresponding basic authentication
   * context URI.
   * 
   * @param loaUri
   *          authentication context URI
   * @return an authentication context URI
   */
  public static String toLoaUri(String signatureMessageLoaUri) {
    LoaEnum loa = LoaEnum.parse(signatureMessageLoaUri);
    if (loa == null) {
      return null;
    }
    return getLoaUri(loa.getLevel());
  }

  /**
   * Predicate that tells if the supplied URI represents a signature message authentication context URI.
   * 
   * @param uri
   *          URI
   * @return {@code true} if the supplied URI represents a signature message authentication context URI and
   *         {@code false} otherwise
   */
  public static boolean isSignatureMessageLoaUri(String uri) {
    LoaEnum loa = LoaEnum.parse(uri);
    if (loa == null) {
      return false;
    }
    return loa.isSignatureMessageUri();
  }

  //
  // Level of Assurance URIs
  //

  /** The Authentication Context URI for Level of Assurance 1. */
  public static final String AUTH_CONTEXT_URI_LOA1 = "http://id.elegnamnden.se/loa/1.0/loa1";

  /** The Authentication Context URI for Level of Assurance 2. */
  public static final String AUTH_CONTEXT_URI_LOA2 = "http://id.elegnamnden.se/loa/1.0/loa2";

  /** The Authentication Context URI for Level of Assurance 2 for use during "authentication for signature". */
  public static final String AUTH_CONTEXT_URI_LOA2_SIGMESSAGE = "http://id.elegnamnden.se/loa/1.0/loa2-sigmessage";

  /** The Authentication Context URI for Level of Assurance 3. */
  public static final String AUTH_CONTEXT_URI_LOA3 = "http://id.elegnamnden.se/loa/1.0/loa3";

  /** The Authentication Context URI for Level of Assurance 3 for use during "authentication for signature". */
  public static final String AUTH_CONTEXT_URI_LOA3_SIGMESSAGE = "http://id.elegnamnden.se/loa/1.0/loa3-sigmessage";

  /** The Authentication Context URI for Level of Assurance 4. */
  public static final String AUTH_CONTEXT_URI_LOA4 = "http://id.elegnamnden.se/loa/1.0/loa4";

  /** The Authentication Context URI for Level of Assurance 4 for use during "authentication for signature". */
  public static final String AUTH_CONTEXT_URI_LOA4_SIGMESSAGE = "http://id.elegnamnden.se/loa/1.0/loa4-sigmessage";

  // Hidden constructor.
  private LevelofAssuranceAuthenticationContextURI() {
  }

}
