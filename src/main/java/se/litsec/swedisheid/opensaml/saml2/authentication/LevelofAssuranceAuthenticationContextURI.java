/*
 * Copyright 2016-2022 Litsec AB
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
    LOA_1(LevelofAssuranceAuthenticationContextURI.AUTHN_CONTEXT_URI_LOA1, true),

    /** Level of Assurance 2. */
    LOA_2(LevelofAssuranceAuthenticationContextURI.AUTHN_CONTEXT_URI_LOA2, true),

    /** Level of Assurance 2 - uncertified. */
    LOA_2_UNCERTIFIED(LevelofAssuranceAuthenticationContextURI.AUTHN_CONTEXT_URI_UNCERTIFIED_LOA2, false),

    /** Level of Assurance 2 - for non-residents. */
    LOA_2_NON_RESIDENT(LevelofAssuranceAuthenticationContextURI.AUTHN_CONTEXT_URI_LOA2_NONRESIDENT, true),

    /** Level of Assurance 3. */
    LOA_3(LevelofAssuranceAuthenticationContextURI.AUTHN_CONTEXT_URI_LOA3, true),

    /** Uncertified (self-declared) LoA 3. */
    LOA_3_UNCERTIFIED(LevelofAssuranceAuthenticationContextURI.AUTHN_CONTEXT_URI_UNCERTIFIED_LOA3, false),

    /** Level of Assurance 3 - for non-residents. */
    LOA_3_NON_RESIDENT(LevelofAssuranceAuthenticationContextURI.AUTHN_CONTEXT_URI_LOA3_NONRESIDENT, true),

    /** Level of Assurance 4. */
    LOA_4(LevelofAssuranceAuthenticationContextURI.AUTHN_CONTEXT_URI_LOA4, true),

    /** Level of Assurance 4 - for non-residents. */
    LOA_4_NON_RESIDENT(LevelofAssuranceAuthenticationContextURI.AUTHN_CONTEXT_URI_LOA4_NONRESIDENT, true),

    /** eIDAS "low". */
    LOA_EIDAS_LOW(LevelofAssuranceAuthenticationContextURI.AUTHN_CONTEXT_URI_EIDAS_LOW, true, false),

    /** eIDAS "low" - notified eID scheme. */
    LOA_EIDAS_LOW_NOTIFIED(LevelofAssuranceAuthenticationContextURI.AUTHN_CONTEXT_URI_EIDAS_LOW_NF, true, false),

    /** eIDAS "low" - uncertified */
    LOA_EIDAS_LOW_UNCERTIFIED(LevelofAssuranceAuthenticationContextURI.AUTHN_CONTEXT_URI_UNCERTIFIED_EIDAS_LOW, false, true),

    /** eIDAS "substantial". */
    LOA_EIDAS_SUBSTANTIAL(LevelofAssuranceAuthenticationContextURI.AUTHN_CONTEXT_URI_EIDAS_SUBSTANTIAL, true, false),

    /** eIDAS "substantial" - notified eID scheme. */
    LOA_EIDAS_SUBSTANTIAL_NOTIFIED(LevelofAssuranceAuthenticationContextURI.AUTHN_CONTEXT_URI_EIDAS_SUBSTANTIAL_NF, true, true),

    /** eIDAS "substantial" - uncertified */
    LOA_EIDAS_SUBSTANTIAL_UNCERTIFIED(
      LevelofAssuranceAuthenticationContextURI.AUTHN_CONTEXT_URI_UNCERTIFIED_EIDAS_SUBSTANTIAL, false, true),

    /** eIDAS "high". */
    LOA_EIDAS_HIGH(LevelofAssuranceAuthenticationContextURI.AUTHN_CONTEXT_URI_EIDAS_HIGH, true, false),

    /** eIDAS "high" - notified eID scheme. */
    LOA_EIDAS_HIGH_NOTIFIED(LevelofAssuranceAuthenticationContextURI.AUTHN_CONTEXT_URI_EIDAS_HIGH_NF, true, true),

    /** eIDAS "high" - uncertified */
    LOA_EIDAS_HIGHL_UNCERTIFIED(LevelofAssuranceAuthenticationContextURI.AUTHN_CONTEXT_URI_UNCERTIFIED_EIDAS_HIGH, false, true);

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
     * Returns the URI for this LoA as defined by the Swedish e-identification board.
     * 
     * @return an URI.
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
     * Indicates whether the identifier is an URI that is for an official LoA.
     * 
     * @return whether the URI is "certified"
     */
    public boolean isCertified() {
      return this.certified;
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
     * Enum constructor.
     * 
     * @param uri
     *          the URL identifier for this LoA level
     * @param certified
     *          whether the URI is certified
     */
    LoaEnum(final String uri, final boolean certified) {
      this(uri, certified, false);
    }

    /**
     * Enum constructor.
     * 
     * @param uri
     *          the URL identifier for this LoA level
     * @param certified
     *          whether the URI is certified
     * @param notified
     *          tells whether the identifier is an URI for a notified eID scheme
     */
    LoaEnum(final String uri, final boolean certified, final boolean notified) {
      this.uri = uri;
      this.certified = certified;
      this.notified = notified;
    }

    /** The URI as defined by the Swedish e-identification board. */
    private String uri;

    /** Is this an URI for a notified eIDAS eID scheme? */
    private boolean notified;

    /** Is this URI a "certified" URI, i.e., an URI that is official. */
    private boolean certified;

  }

  //
  // Level of Assurance URIs
  //

  /** The Authentication Context URI for Level of Assurance 1. */
  public static final String AUTHN_CONTEXT_URI_LOA1 = "http://id.elegnamnden.se/loa/1.0/loa1";

  /** The Authentication Context URI for Level of Assurance 2. */
  public static final String AUTHN_CONTEXT_URI_LOA2 = "http://id.elegnamnden.se/loa/1.0/loa2";

  /** The Authentication Context URI for uncertified (self-declared) Level of Assurance 2 compliance. */
  public static final String AUTHN_CONTEXT_URI_UNCERTIFIED_LOA2 = "http://id.swedenconnect.se/loa/1.0/uncertified-loa2";

  /** The Authentication Context URI for Level of Assurance 2 for non residents. */
  public static final String AUTHN_CONTEXT_URI_LOA2_NONRESIDENT = "http://id.swedenconnect.se/loa/1.0/loa2-nonresident";

  /** The Authentication Context URI for Level of Assurance 3. */
  public static final String AUTHN_CONTEXT_URI_LOA3 = "http://id.elegnamnden.se/loa/1.0/loa3";

  /** The Authentication Context URI for uncertified (self-declared) Level of Assurance 3 compliance. */
  public static final String AUTHN_CONTEXT_URI_UNCERTIFIED_LOA3 = "http://id.swedenconnect.se/loa/1.0/uncertified-loa3";

  /** The Authentication Context URI for Level of Assurance 3 for non residents. */
  public static final String AUTHN_CONTEXT_URI_LOA3_NONRESIDENT = "http://id.swedenconnect.se/loa/1.0/loa3-nonresident";

  /** The Authentication Context URI for Level of Assurance 4. */
  public static final String AUTHN_CONTEXT_URI_LOA4 = "http://id.elegnamnden.se/loa/1.0/loa4";

  /** The Authentication Context URI for Level of Assurance 4 for non residents. */
  public static final String AUTHN_CONTEXT_URI_LOA4_NONRESIDENT = "http://id.swedenconnect.se/loa/1.0/loa4-nonresident";

  /** The Authentication Context URI for eIDAS "low". */
  public static final String AUTHN_CONTEXT_URI_EIDAS_LOW = "http://id.elegnamnden.se/loa/1.0/eidas-low";

  /** The Authentication Context URI for eIDAS "low" for notified eID:s. */
  public static final String AUTHN_CONTEXT_URI_EIDAS_LOW_NF = "http://id.elegnamnden.se/loa/1.0/eidas-nf-low";

  /** The Authentication Context URI for uncertified eIDAS "low". */
  public static final String AUTHN_CONTEXT_URI_UNCERTIFIED_EIDAS_LOW = "http://id.swedenconnect.se/loa/1.0/uncertified-eidas-low";

  /** The Authentication Context URI for eIDAS "substantial". */
  public static final String AUTHN_CONTEXT_URI_EIDAS_SUBSTANTIAL = "http://id.elegnamnden.se/loa/1.0/eidas-sub";

  /** The Authentication Context URI for eIDAS "substantial" for notified eID:s. */
  public static final String AUTHN_CONTEXT_URI_EIDAS_SUBSTANTIAL_NF = "http://id.elegnamnden.se/loa/1.0/eidas-nf-sub";

  /** The Authentication Context URI for uncertified eIDAS "substantial". */
  public static final String AUTHN_CONTEXT_URI_UNCERTIFIED_EIDAS_SUBSTANTIAL = "http://id.swedenconnect.se/loa/1.0/uncertified-eidas-sub";

  /** The Authentication Context URI for eIDAS "high". */
  public static final String AUTHN_CONTEXT_URI_EIDAS_HIGH = "http://id.elegnamnden.se/loa/1.0/eidas-high";

  /** The Authentication Context URI for eIDAS "high" for notified eID:s. */
  public static final String AUTHN_CONTEXT_URI_EIDAS_HIGH_NF = "http://id.elegnamnden.se/loa/1.0/eidas-nf-high";

  /** The Authentication Context URI for uncertified eIDAS "high". */
  public static final String AUTHN_CONTEXT_URI_UNCERTIFIED_EIDAS_HIGH = "http://id.swedenconnect.se/loa/1.0/uncertified-eidas-high";

  // Hidden constructor.
  private LevelofAssuranceAuthenticationContextURI() {
  }

}
