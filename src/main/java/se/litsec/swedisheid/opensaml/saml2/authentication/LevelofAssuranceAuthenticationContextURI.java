/*
 * Copyright 2016-2021 Litsec AB
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
    LOA_1(1, LevelofAssuranceAuthenticationContextURI.AUTH_CONTEXT_URI_LOA1),

    /** Level of Assurance 2. */
    LOA_2(2, LevelofAssuranceAuthenticationContextURI.AUTH_CONTEXT_URI_LOA2),

    /** Level of Assurance 2 - for use in signature services. */
    LOA_2_SIGMESSAGE(2, LevelofAssuranceAuthenticationContextURI.AUTH_CONTEXT_URI_LOA2_SIGMESSAGE, true, false,
        LevelofAssuranceAuthenticationContextURI.AUTH_CONTEXT_URI_LOA2),

    /** Level of Assurance 3. */
    LOA_3(3, LevelofAssuranceAuthenticationContextURI.AUTH_CONTEXT_URI_LOA3),

    /** Level of Assurance 3 - for use in signature services. */
    LOA_3_SIGMESSAGE(3, LevelofAssuranceAuthenticationContextURI.AUTH_CONTEXT_URI_LOA3_SIGMESSAGE, true, false,
        LevelofAssuranceAuthenticationContextURI.AUTH_CONTEXT_URI_LOA3),

    /** Uncertified (self-declared) LoA 3. */
    LOA_3_UNCERTIFIED(0, LevelofAssuranceAuthenticationContextURI.AUTH_CONTEXT_URI_UNCERTIFIED_LOA3),

    /** Uncertified (self-declared) LoA 3 - for use in signature services. */
    LOA_3_UNCERTIFIED_SIGMESSAGE(0, LevelofAssuranceAuthenticationContextURI.AUTH_CONTEXT_URI_UNCERTIFIED_LOA3_SIGMESSAGE, true, false,
        LevelofAssuranceAuthenticationContextURI.AUTH_CONTEXT_URI_UNCERTIFIED_LOA3),

    /** Level of Assurance 4. */
    LOA_4(4, LevelofAssuranceAuthenticationContextURI.AUTH_CONTEXT_URI_LOA4),

    /** Level of Assurance 4 - for use in signature services. */
    LOA_4_SIGMESSAGE(4, LevelofAssuranceAuthenticationContextURI.AUTH_CONTEXT_URI_LOA4_SIGMESSAGE, true, false,
        LevelofAssuranceAuthenticationContextURI.AUTH_CONTEXT_URI_LOA4),

    /** eIDAS "low". */
    LOA_EIDAS_LOW(2, LevelofAssuranceAuthenticationContextURI.AUTH_CONTEXT_URI_EIDAS_LOW),

    /** eIDAS "low" - notified eID scheme. */
    LOA_EIDAS_LOW_NOTIFIED(2, LevelofAssuranceAuthenticationContextURI.AUTH_CONTEXT_URI_EIDAS_LOW_NF, false, true,
        LevelofAssuranceAuthenticationContextURI.AUTH_CONTEXT_URI_EIDAS_LOW),

    /** eIDAS "low" - for use in signature services. */
    LOA_EIDAS_LOW_SIGMESSAGE(2, LevelofAssuranceAuthenticationContextURI.AUTH_CONTEXT_URI_EIDAS_LOW_SIGMESSAGE, true, false,
        LevelofAssuranceAuthenticationContextURI.AUTH_CONTEXT_URI_EIDAS_LOW),

    /** eIDAS "substantial" - notified eID scheme for use in signature services. */
    LOA_EIDAS_LOW_NOTIFIED_SIGMESSAGE(3, LevelofAssuranceAuthenticationContextURI.AUTH_CONTEXT_URI_EIDAS_LOW_NF_SIGMESSAGE,
        true, true, LevelofAssuranceAuthenticationContextURI.AUTH_CONTEXT_URI_EIDAS_LOW),

    /** eIDAS "substantial". */
    LOA_EIDAS_SUBSTANTIAL(3, LevelofAssuranceAuthenticationContextURI.AUTH_CONTEXT_URI_EIDAS_SUBSTANTIAL),

    /** eIDAS "substantial" - notified eID scheme. */
    LOA_EIDAS_SUBSTANTIAL_NOTIFIED(3, LevelofAssuranceAuthenticationContextURI.AUTH_CONTEXT_URI_EIDAS_SUBSTANTIAL_NF, false, true,
        LevelofAssuranceAuthenticationContextURI.AUTH_CONTEXT_URI_EIDAS_SUBSTANTIAL),

    /** eIDAS "substantial" - for use in signature services. */
    LOA_EIDAS_SUBSTANTIAL_SIGMESSAGE(3, LevelofAssuranceAuthenticationContextURI.AUTH_CONTEXT_URI_EIDAS_SUBSTANTIAL_SIGMESSAGE, true, false,
        LevelofAssuranceAuthenticationContextURI.AUTH_CONTEXT_URI_EIDAS_SUBSTANTIAL),

    /** eIDAS "substantial" - notified eID scheme for use in signature services. */
    LOA_EIDAS_SUBSTANTIAL_NOTIFIED_SIGMESSAGE(3, LevelofAssuranceAuthenticationContextURI.AUTH_CONTEXT_URI_EIDAS_SUBSTANTIAL_NF_SIGMESSAGE,
        true, true, LevelofAssuranceAuthenticationContextURI.AUTH_CONTEXT_URI_EIDAS_SUBSTANTIAL),

    /** eIDAS "high". */
    LOA_EIDAS_HIGH(4, LevelofAssuranceAuthenticationContextURI.AUTH_CONTEXT_URI_EIDAS_HIGH),

    /** eIDAS "high" - notified eID scheme. */
    LOA_EIDAS_HIGH_NOTIFIED(4, LevelofAssuranceAuthenticationContextURI.AUTH_CONTEXT_URI_EIDAS_HIGH_NF, false, true,
        LevelofAssuranceAuthenticationContextURI.AUTH_CONTEXT_URI_EIDAS_HIGH),

    /** eIDAS "high" - for use in signature services. */
    LOA_EIDAS_HIGH_SIGMESSAGE(4, LevelofAssuranceAuthenticationContextURI.AUTH_CONTEXT_URI_EIDAS_HIGH_SIGMESSAGE, true, false,
        LevelofAssuranceAuthenticationContextURI.AUTH_CONTEXT_URI_EIDAS_HIGH),

    /** eIDAS "high" - notified eID scheme for use in signature services. */
    LOA_EIDAS_HIGH_NOTIFIED_SIGMESSAGE(4, LevelofAssuranceAuthenticationContextURI.AUTH_CONTEXT_URI_EIDAS_HIGH_NF_SIGMESSAGE, true, true,
        LevelofAssuranceAuthenticationContextURI.AUTH_CONTEXT_URI_EIDAS_HIGH);

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
     * Indicates whether the identifier is an URI for a notified eID scheme.
     * 
     * @return if the identifier is an URI for a notified eID scheme {@code true} is returned, otherwise {@code false}
     */
    public boolean isNotified() {
      return this.notified;
    }

    /**
     * Returns the base URI when the LoA represents a notified or sign message URI.
     * 
     * @return the base URI, or the URI itself if the URI represents a "plain" authentication context
     */
    public String getBaseUri() {
      return this.baseUri;
    }

    /**
     * Predicate that tells if this LoA represents an eIDAS authentication context.
     * 
     * @return if the LoA represents an eIDAS authentication context {@code true} is returned, otherwise {@code false}
     */
    public boolean isEidasUri() {
      return this.uri.contains("eidas");
    }

    /**
     * Given a 'sigmessage' URI, the method returns the corresponding LoA without the sigmessage.
     * 
     * @param loa
     *          the LoA to transform
     * @return the same LoA but without the sigmessage
     */
    public static LoaEnum minusSigMessage(LoaEnum loa) {
      for (LoaEnum l : LoaEnum.values()) {
        if (l.getBaseUri().equals(loa.getBaseUri()) && !l.isSignatureMessageUri() && l.isNotified() == loa.isNotified()) {
          return l;
        }
      }
      return null;
    }

    /**
     * Given an URI, the method returns the corresponding LoA with the sigmessage.
     * 
     * @param loa
     *          the LoA to transform
     * @return the same LoA but with the sigmessage
     */
    public static LoaEnum plusSigMessage(LoaEnum loa) {
      for (LoaEnum l : LoaEnum.values()) {
        if (l.getBaseUri().equals(loa.getBaseUri()) && l.isSignatureMessageUri() && l.isNotified() == loa.isNotified()) {
          return l;
        }
      }
      return null;
    }

    /**
     * Enum constructor.
     * 
     * @param level
     *          the numeric level for this LoA level
     * @param uri
     *          the URL identifier for this LoA level
     */
    LoaEnum(int level, String uri) {
      this(level, uri, false, false, uri);
    }

    /**
     * Enum constructor.
     * 
     * @param level
     *          the numeric level for this LoA level
     * @param uri
     *          the URL identifier for this LoA level
     * @param sigMessage
     *          indicator whether this LoA is for signature messages
     * @param notified
     *          tells whether the identifier is an URI for a notified eID scheme
     * @param baseUri
     *          the base URI when the LoA represents a notified or sign message URI
     */
    LoaEnum(int level, String uri, boolean sigMessage, boolean notified, String baseUri) {
      this.level = level;
      this.uri = uri;
      this.sigMessage = sigMessage;
      this.notified = notified;
      this.baseUri = baseUri;
    }

    /** The URI as defined by the Swedish e-identification board. */
    private String uri;

    /** The number representing the level. */
    private int level;

    /** Is this is signature message LoA identifier? */
    private boolean sigMessage;

    /** Is this an URI for a notified eID scheme? */
    private boolean notified;

    /** The base URI when the LoA represents a notified or sign message URI. */
    private String baseUri;
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

  /** The Authentication Context URI for uncertified (self-declared) Level of Assurance 3 compliance. */
  public static final String AUTH_CONTEXT_URI_UNCERTIFIED_LOA3 = "http://id.swedenconnect.se/loa/1.0/uncertified-loa3";

  /**
   * The Authentication Context URI for uncertified (self-declared) Level of Assurance 3 for use during "authentication
   * for signature".
   */
  public static final String AUTH_CONTEXT_URI_UNCERTIFIED_LOA3_SIGMESSAGE = "http://id.swedenconnect.se/loa/1.0/uncertified-loa3-sigmessage";

  /** The Authentication Context URI for Level of Assurance 4. */
  public static final String AUTH_CONTEXT_URI_LOA4 = "http://id.elegnamnden.se/loa/1.0/loa4";

  /** The Authentication Context URI for Level of Assurance 4 for use during "authentication for signature". */
  public static final String AUTH_CONTEXT_URI_LOA4_SIGMESSAGE = "http://id.elegnamnden.se/loa/1.0/loa4-sigmessage";

  /** The Authentication Context URI for eIDAS "low". */
  public static final String AUTH_CONTEXT_URI_EIDAS_LOW = "http://id.elegnamnden.se/loa/1.0/eidas-low";

  /** The Authentication Context URI for eIDAS "low" for notified eID:s. */
  public static final String AUTH_CONTEXT_URI_EIDAS_LOW_NF = "http://id.elegnamnden.se/loa/1.0/eidas-nf-low";

  /** The Authentication Context URI for eIDAS "low" for use during "authentication for signature". */
  public static final String AUTH_CONTEXT_URI_EIDAS_LOW_SIGMESSAGE = "http://id.elegnamnden.se/loa/1.0/eidas-low-sigm";

  /**
   * The Authentication Context URI for eIDAS "low" of notified eID:s for use during "authentication for signature".
   */
  public static final String AUTH_CONTEXT_URI_EIDAS_LOW_NF_SIGMESSAGE = "http://id.elegnamnden.se/loa/1.0/eidas-nf-low-sigm";

  /** The Authentication Context URI for eIDAS "substantial". */
  public static final String AUTH_CONTEXT_URI_EIDAS_SUBSTANTIAL = "http://id.elegnamnden.se/loa/1.0/eidas-sub";

  /** The Authentication Context URI for eIDAS "substantial" for notified eID:s. */
  public static final String AUTH_CONTEXT_URI_EIDAS_SUBSTANTIAL_NF = "http://id.elegnamnden.se/loa/1.0/eidas-nf-sub";

  /** The Authentication Context URI for eIDAS "substantial" for use during "authentication for signature". */
  public static final String AUTH_CONTEXT_URI_EIDAS_SUBSTANTIAL_SIGMESSAGE = "http://id.elegnamnden.se/loa/1.0/eidas-sub-sigm";

  /**
   * The Authentication Context URI for eIDAS "substantial" of notified eID:s for use during "authentication for
   * signature".
   */
  public static final String AUTH_CONTEXT_URI_EIDAS_SUBSTANTIAL_NF_SIGMESSAGE = "http://id.elegnamnden.se/loa/1.0/eidas-nf-sub-sigm";

  /** The Authentication Context URI for eIDAS "high". */
  public static final String AUTH_CONTEXT_URI_EIDAS_HIGH = "http://id.elegnamnden.se/loa/1.0/eidas-high";

  /** The Authentication Context URI for eIDAS "high" for notified eID:s. */
  public static final String AUTH_CONTEXT_URI_EIDAS_HIGH_NF = "http://id.elegnamnden.se/loa/1.0/eidas-nf-high";

  /** The Authentication Context URI for eIDAS "high" for use during "authentication for signature". */
  public static final String AUTH_CONTEXT_URI_EIDAS_HIGH_SIGMESSAGE = "http://id.elegnamnden.se/loa/1.0/eidas-high-sigm";

  /**
   * The Authentication Context URI for eIDAS "substantial" of notified eID:s for use during "authentication for
   * signature".
   */
  public static final String AUTH_CONTEXT_URI_EIDAS_HIGH_NF_SIGMESSAGE = "http://id.elegnamnden.se/loa/1.0/eidas-nf-high-sigm";

  // Hidden constructor.
  private LevelofAssuranceAuthenticationContextURI() {
  }

}
