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

    /** Level of Assurance 3. */
    LOA_3(3, LevelofAssuranceAuthenticationContextURI.AUTH_CONTEXT_URI_LOA3),

    /** Uncertified (self-declared) LoA 3. */
    LOA_3_UNCERTIFIED(0, LevelofAssuranceAuthenticationContextURI.AUTH_CONTEXT_URI_UNCERTIFIED_LOA3),

    /** Level of Assurance 4. */
    LOA_4(4, LevelofAssuranceAuthenticationContextURI.AUTH_CONTEXT_URI_LOA4),

    /** eIDAS "low". */
    LOA_EIDAS_LOW(2, LevelofAssuranceAuthenticationContextURI.AUTH_CONTEXT_URI_EIDAS_LOW),

    /** eIDAS "low" - notified eID scheme. */
    LOA_EIDAS_LOW_NOTIFIED(2, LevelofAssuranceAuthenticationContextURI.AUTH_CONTEXT_URI_EIDAS_LOW_NF, true,
        LevelofAssuranceAuthenticationContextURI.AUTH_CONTEXT_URI_EIDAS_LOW),

    /** Uncertified eIDAS "low". */
    LOA_EIDAS_LOW_UNCERTIFIED(2, LevelofAssuranceAuthenticationContextURI.AUTH_CONTEXT_URI_UNCERTIFIED_EIDAS_LOW, false,
        LevelofAssuranceAuthenticationContextURI.AUTH_CONTEXT_URI_EIDAS_LOW),

    /** eIDAS "substantial". */
    LOA_EIDAS_SUBSTANTIAL(3, LevelofAssuranceAuthenticationContextURI.AUTH_CONTEXT_URI_EIDAS_SUBSTANTIAL),

    /** eIDAS "substantial" - notified eID scheme. */
    LOA_EIDAS_SUBSTANTIAL_NOTIFIED(3, LevelofAssuranceAuthenticationContextURI.AUTH_CONTEXT_URI_EIDAS_SUBSTANTIAL_NF, true,
        LevelofAssuranceAuthenticationContextURI.AUTH_CONTEXT_URI_EIDAS_SUBSTANTIAL),

    /** Uncertified eIDAS "substantial". */
    LOA_EIDAS_SUBSTANTIAL_UNCERTIFIED(3, LevelofAssuranceAuthenticationContextURI.AUTH_CONTEXT_URI_UNCERTIFIED_EIDAS_SUBSTANTIAL, false,
        LevelofAssuranceAuthenticationContextURI.AUTH_CONTEXT_URI_EIDAS_SUBSTANTIAL),

    /** eIDAS "high". */
    LOA_EIDAS_HIGH(4, LevelofAssuranceAuthenticationContextURI.AUTH_CONTEXT_URI_EIDAS_HIGH),

    /** eIDAS "high" - notified eID scheme. */
    LOA_EIDAS_HIGH_NOTIFIED(4, LevelofAssuranceAuthenticationContextURI.AUTH_CONTEXT_URI_EIDAS_HIGH_NF, true,
        LevelofAssuranceAuthenticationContextURI.AUTH_CONTEXT_URI_EIDAS_HIGH),

    /** Uncertified eIDAS "high". */
    LOA_EIDAS_HIGH_UNCERTIFIED(4, LevelofAssuranceAuthenticationContextURI.AUTH_CONTEXT_URI_UNCERTIFIED_EIDAS_HIGH, false,
        LevelofAssuranceAuthenticationContextURI.AUTH_CONTEXT_URI_EIDAS_HIGH);

    /**
     * Given a URI the method returns the enum value matching.
     * 
     * @param uri
     *          URI
     * @return the matching enum value or null is no match is found
     */
    public static LoaEnum parse(final String uri) {
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
     * @return the numeric level
     */
    public int getLevel() {
      return this.level;
    }

    /**
     * Returns the URI for this LoA as defined by the Swedish e-identification board.
     * 
     * @return an URI
     */
    public String getUri() {
      return this.uri;
    }

    /**
     * Indicates whether the identifier is an URI for a notified eID scheme.
     * 
     * @return if the identifier is an URI for a notified eID scheme true is returned, otherwise false
     */
    public boolean isNotified() {
      return this.notified;
    }

    /**
     * Returns the base URI when the LoA represents a notified URI.
     * 
     * @return the base URI, or the URI itself if the URI represents a "plain" authentication context
     */
    public String getBaseUri() {
      return this.baseUri;
    }

    /**
     * Predicate that tells if this LoA represents an eIDAS authentication context.
     * 
     * @return if the LoA represents an eIDAS authentication context true is returned, otherwise false
     */
    public boolean isEidasUri() {
      return this.uri.contains("eidas");
    }

    /**
     * Predicate that tells if this LoA represents a certified scheme.
     * 
     * @return true if certified and false if uncertified
     */
    public boolean isCertified() {
      return !this.uri.contains("uncertified");
    }

    /**
     * Enum constructor.
     * 
     * @param level
     *          the numeric level for this LoA level
     * @param uri
     *          the URL identifier for this LoA level
     */
    LoaEnum(final int level, final String uri) {
      this(level, uri, false, uri);
    }

    /**
     * Enum constructor.
     * 
     * @param level
     *          the numeric level for this LoA level
     * @param uri
     *          the URL identifier for this LoA level
     * @param notified
     *          tells whether the identifier is an URI for a notified eID scheme
     * @param baseUri
     *          the base URI when the LoA represents a notified or sign message URI
     */
    LoaEnum(final int level, final String uri, final boolean notified, final String baseUri) {
      this.level = level;
      this.uri = uri;
      this.notified = notified;
      this.baseUri = baseUri;
    }

    /** The URI as defined by the Swedish e-identification board. */
    private String uri;

    /** The number representing the level. */
    private int level;

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

  /** The Authentication Context URI for Level of Assurance 3. */
  public static final String AUTH_CONTEXT_URI_LOA3 = "http://id.elegnamnden.se/loa/1.0/loa3";

  /** The Authentication Context URI for uncertified (self-declared) Level of Assurance 3 compliance. */
  public static final String AUTH_CONTEXT_URI_UNCERTIFIED_LOA3 = "http://id.swedenconnect.se/loa/1.0/uncertified-loa3";

  /** The Authentication Context URI for Level of Assurance 4. */
  public static final String AUTH_CONTEXT_URI_LOA4 = "http://id.elegnamnden.se/loa/1.0/loa4";

  /** The Authentication Context URI for eIDAS "low". */
  public static final String AUTH_CONTEXT_URI_EIDAS_LOW = "http://id.elegnamnden.se/loa/1.0/eidas-low";

  /** The Authentication Context URI for eIDAS "low" for notified eID:s. */
  public static final String AUTH_CONTEXT_URI_EIDAS_LOW_NF = "http://id.elegnamnden.se/loa/1.0/eidas-nf-low";

  /** The Authentication Context URI for uncertified eIDAS "low". */
  public static final String AUTH_CONTEXT_URI_UNCERTIFIED_EIDAS_LOW = "http://id.swedenconnect.se/loa/1.0/uncertified-eidas-low";

  /** The Authentication Context URI for eIDAS "substantial". */
  public static final String AUTH_CONTEXT_URI_EIDAS_SUBSTANTIAL = "http://id.elegnamnden.se/loa/1.0/eidas-sub";

  /** The Authentication Context URI for eIDAS "substantial" for notified eID:s. */
  public static final String AUTH_CONTEXT_URI_EIDAS_SUBSTANTIAL_NF = "http://id.elegnamnden.se/loa/1.0/eidas-nf-sub";

  /** The Authentication Context URI for uncertified eIDAS "substantial". */
  public static final String AUTH_CONTEXT_URI_UNCERTIFIED_EIDAS_SUBSTANTIAL = "http://id.swedenconnect.se/loa/1.0/uncertified-eidas-sub";

  /** The Authentication Context URI for eIDAS "high". */
  public static final String AUTH_CONTEXT_URI_EIDAS_HIGH = "http://id.elegnamnden.se/loa/1.0/eidas-high";

  /** The Authentication Context URI for eIDAS "high" for notified eID:s. */
  public static final String AUTH_CONTEXT_URI_EIDAS_HIGH_NF = "http://id.elegnamnden.se/loa/1.0/eidas-nf-high";

  /** The Authentication Context URI for uncertified eIDAS "high". */
  public static final String AUTH_CONTEXT_URI_UNCERTIFIED_EIDAS_HIGH = "http://id.swedenconnect.se/loa/1.0/uncertified-eidas-high";

  // Hidden constructor.
  private LevelofAssuranceAuthenticationContextURI() {
  }

}
