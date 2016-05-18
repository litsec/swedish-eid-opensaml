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
package se.litsec.swedisheid.opensaml.saml2.attribute;

/**
 * Contains constants for all attributes defined in section 3.1 of
 * "Attribute Specification for the Swedish eID Framework".
 * 
 * @author Martin Lindström (martin.lindstrom@litsec.se)
 */
public class AttributeConstants {

  /** The attribute name for the "Surname" attribute (urn:oid:2.5.4.4). */
  public static final String ATTRIBUTE_NAME_SN = "urn:oid:2.5.4.4";

  /** The attribute friendly name for the "Surname" attribute (sn). */
  public static final String ATTRIBUTE_FRIENDLY_NAME_SN = "sn";

  /** Attribute template for the "Surname" attribute. */
  public static final AttributeTemplate ATTRIBUTE_TEMPLATE_SN = new AttributeTemplate(ATTRIBUTE_NAME_SN,
    ATTRIBUTE_FRIENDLY_NAME_SN);

  /** The attribute name for the "Given Name" attribute (urn:oid:2.5.4.42). */
  public static final String ATTRIBUTE_NAME_GIVEN_NAME = "urn:oid:2.5.4.42";

  /** The attribute friendly name for the "Given Name" attribute (givenName). */
  public static final String ATTRIBUTE_FRIENDLY_NAME_GIVEN_NAME = "givenName";

  /** Attribute template for the "Given Name" attribute. */
  public static final AttributeTemplate ATTRIBUTE_TEMPLATE_GIVEN_NAME = new AttributeTemplate(
    ATTRIBUTE_NAME_GIVEN_NAME, ATTRIBUTE_FRIENDLY_NAME_GIVEN_NAME);

  /** The attribute name for the "Display Name" attribute (urn:oid:2.16.840.1.113730.3.1.241). */
  public static final String ATTRIBUTE_NAME_DISPLAY_NAME = "urn:oid:2.16.840.1.113730.3.1.241";

  /** The attribute friendly name for the "Display Name" attribute (displayName). */
  public static final String ATTRIBUTE_FRIENDLY_NAME_DISPLAY_NAME = "displayName";

  /** Attribute template for the "Display Name" attribute. */
  public static final AttributeTemplate ATTRIBUTE_TEMPLATE_DISPLAY_NAME = new AttributeTemplate(
    ATTRIBUTE_NAME_DISPLAY_NAME, ATTRIBUTE_FRIENDLY_NAME_DISPLAY_NAME);

  /** The attribute name for the "Gender" attribute (urn:oid:1.2.752.29.4.13). */
  public static final String ATTRIBUTE_NAME_GENDER = "urn:oid:1.2.752.29.4.13";

  /** The attribute friendly name for the "Gender" attribute (gender). */
  public static final String ATTRIBUTE_FRIENDLY_NAME_GENDER = "gender";

  /** Attribute template for the "Gender" attribute. */
  public static final AttributeTemplate ATTRIBUTE_TEMPLATE_GENDER = new AttributeTemplate(ATTRIBUTE_NAME_GENDER,
    ATTRIBUTE_FRIENDLY_NAME_GENDER);

  /** The attribute name for the "National civic registration number" attribute (urn:oid:1.2.752.29.4.13). */
  public static final String ATTRIBUTE_NAME_PERSONAL_IDENTITY_NUMBER = "urn:oid:1.2.752.29.4.13";

  /** The attribute friendly name for the "National civic registration number" attribute (personalIdentityNumber). */
  public static final String ATTRIBUTE_FRIENDLY_NAME_PERSONAL_IDENTITY_NUMBER = "personalIdentityNumber";

  /** Attribute template for the "National civic registration number" attribute. */
  public static final AttributeTemplate ATTRIBUTE_TEMPLATE_PERSONAL_IDENTITY_NUMBER = new AttributeTemplate(
    ATTRIBUTE_NAME_PERSONAL_IDENTITY_NUMBER, ATTRIBUTE_FRIENDLY_NAME_PERSONAL_IDENTITY_NUMBER);

  /** The attribute name for the "Date of birth" attribute (urn:oid:1.3.6.1.5.5.7.9.1). */
  public static final String ATTRIBUTE_NAME_DATE_OF_BIRTH = "urn:oid:1.3.6.1.5.5.7.9.1";

  /** The attribute friendly name for the "Date of birth" attribute (dateOfBirth). */
  public static final String ATTRIBUTE_FRIENDLY_NAME_DATE_OF_BIRTH = "dateOfBirth";

  /** Attribute template for the "Date of birth" attribute. */
  public static final AttributeTemplate ATTRIBUTE_TEMPLATE_DATE_OF_BIRTH = new AttributeTemplate(
    ATTRIBUTE_NAME_DATE_OF_BIRTH, ATTRIBUTE_FRIENDLY_NAME_DATE_OF_BIRTH);

  /** The attribute name for the "Street address" attribute (urn:oid:2.5.4.18). */
  public static final String ATTRIBUTE_NAME_STREET = "urn:oid:2.5.4.18";

  /** The attribute friendly name for the "Street address" attribute (street). */
  public static final String ATTRIBUTE_FRIENDLY_NAME_STREET = "street";

  /** Attribute template for the "Street address" attribute. */
  public static final AttributeTemplate ATTRIBUTE_TEMPLATE_STREET = new AttributeTemplate(ATTRIBUTE_NAME_STREET,
    ATTRIBUTE_FRIENDLY_NAME_STREET);

  /** The attribute name for the "Post box" attribute (urn:oid:2.5.4.17). */
  public static final String ATTRIBUTE_NAME_POST_OFFICE_BOX = "urn:oid:2.5.4.17";

  /** The attribute friendly name for the "Post box" attribute (postOfficeBox). */
  public static final String ATTRIBUTE_FRIENDLY_NAME_POST_OFFICE_BOX = "postOfficeBox";

  /** Attribute template for the "Post box" attribute. */
  public static final AttributeTemplate ATTRIBUTE_TEMPLATE_POST_OFFICE_BOX = new AttributeTemplate(
    ATTRIBUTE_NAME_POST_OFFICE_BOX, ATTRIBUTE_FRIENDLY_NAME_POST_OFFICE_BOX);

  /** The attribute name for the "Postal code" attribute (urn:oid:2.5.4.7). */
  public static final String ATTRIBUTE_NAME_POSTAL_CODE = "urn:oid:2.5.4.7";

  /** The attribute friendly name for the "Postal code" attribute (postalCode). */
  public static final String ATTRIBUTE_FRIENDLY_NAME_POSTAL_CODE = "postalCode";

  /** Attribute template for the "Postal code" attribute. */
  public static final AttributeTemplate ATTRIBUTE_TEMPLATE_POSTAL_CODE = new AttributeTemplate(
    ATTRIBUTE_NAME_POSTAL_CODE, ATTRIBUTE_FRIENDLY_NAME_POSTAL_CODE);

  /** The attribute name for the "Locality" attribute (urn:oid:2.5.4.6). */
  public static final String ATTRIBUTE_NAME_L = "urn:oid:2.5.4.6";

  /** The attribute friendly name for the "Locality" attribute (l). */
  public static final String ATTRIBUTE_FRIENDLY_NAME_L = "l";

  /** Attribute template for the "Locality" attribute. */
  public static final AttributeTemplate ATTRIBUTE_TEMPLATE_L = new AttributeTemplate(ATTRIBUTE_NAME_L,
    ATTRIBUTE_FRIENDLY_NAME_L);

  /** The attribute name for the "Country" attribute (urn:oid:1.3.6.1.5.5.7.9.2). */
  public static final String ATTRIBUTE_NAME_C = "urn:oid:1.3.6.1.5.5.7.9.2";

  /** The attribute friendly name for the "Country" attribute (c). */
  public static final String ATTRIBUTE_FRIENDLY_NAME_C = "c";

  /** Attribute template for the "Country" attribute. */
  public static final AttributeTemplate ATTRIBUTE_TEMPLATE_C = new AttributeTemplate(ATTRIBUTE_NAME_C,
    ATTRIBUTE_FRIENDLY_NAME_C);

  /** The attribute name for the "Place of birth" attribute (urn:oid:1.3.6.1.5.5.7.9.4). */
  public static final String ATTRIBUTE_NAME_PLACE_OF_BIRTH = "urn:oid:1.3.6.1.5.5.7.9.4";

  /** The attribute friendly name for the "Place of birth" attribute (placeOfBirth). */
  public static final String ATTRIBUTE_FRIENDLY_NAME_PLACE_OF_BIRTH = "placeOfBirth";

  /** Attribute template for the "Place of birth" attribute. */
  public static final AttributeTemplate ATTRIBUTE_TEMPLATE_PLACE_OF_BIRTH = new AttributeTemplate(
    ATTRIBUTE_NAME_PLACE_OF_BIRTH, ATTRIBUTE_FRIENDLY_NAME_PLACE_OF_BIRTH);

  /** The attribute name for the "Country of citizenship" attribute (urn:oid:1.3.6.1.5.5.7.9.5). */
  public static final String ATTRIBUTE_NAME_COUNTRY_OF_CITIZENSHIP = "urn:oid:1.3.6.1.5.5.7.9.5";

  /** The attribute friendly name for the "Country of citizenship" attribute (countryOfCitizenship). */
  public static final String ATTRIBUTE_FRIENDLY_NAME_COUNTRY_OF_CITIZENSHIP = "countryOfCitizenship";

  /** Attribute template for the "Country of citizenship" attribute (Multi-valued). */
  public static final AttributeTemplate ATTRIBUTE_TEMPLATE_COUNTRY_OF_CITIZENSHIP = new AttributeTemplate(
    ATTRIBUTE_NAME_COUNTRY_OF_CITIZENSHIP, ATTRIBUTE_FRIENDLY_NAME_COUNTRY_OF_CITIZENSHIP, true);

  /** The attribute name for the "Country of Residence" attribute (urn:oid:2.5.4.20). */
  public static final String ATTRIBUTE_NAME_COUNTRY_OF_RESIDENCE = "urn:oid:2.5.4.20";

  /** The attribute friendly name for the "Country of Residence" attribute (countryOfResidence). */
  public static final String ATTRIBUTE_FRIENDLY_NAME_COUNTRY_OF_RESIDENCE = "countryOfResidence";

  /** Attribute template for the "Country of Residence" attribute. */
  public static final AttributeTemplate ATTRIBUTE_TEMPLATE_COUNTRY_OF_RESIDENCE = new AttributeTemplate(
    ATTRIBUTE_NAME_COUNTRY_OF_RESIDENCE, ATTRIBUTE_FRIENDLY_NAME_COUNTRY_OF_RESIDENCE);

  /** The attribute name for the "Telephone number" attribute (urn:oid:0.9.2342.19200300.100.1.41). */
  public static final String ATTRIBUTE_NAME_TELEPHONE_NUMBER = "urn:oid:0.9.2342.19200300.100.1.41";

  /** The attribute friendly name for the "Telephone number" attribute (telephoneNumber). */
  public static final String ATTRIBUTE_FRIENDLY_NAME_TELEPHONE_NUMBER = "telephoneNumber";

  /** Attribute template for the "Telephone number" attribute (Multi-valued). */
  public static final AttributeTemplate ATTRIBUTE_TEMPLATE_TELEPHONE_NUMBER = new AttributeTemplate(
    ATTRIBUTE_NAME_TELEPHONE_NUMBER, ATTRIBUTE_FRIENDLY_NAME_TELEPHONE_NUMBER, true);

  /** The attribute name for the "Mobile number" attribute (urn:oid:0.9.2342.19200300.100.1.3). */
  public static final String ATTRIBUTE_NAME_MOBILE = "urn:oid:0.9.2342.19200300.100.1.3";

  /** The attribute friendly name for the "Mobile number" attribute (mobile). */
  public static final String ATTRIBUTE_FRIENDLY_NAME_MOBILE = "mobile";

  /** Attribute template for the "Mobile number" attribute (Multi-valued). */
  public static final AttributeTemplate ATTRIBUTE_TEMPLATE_MOBILE = new AttributeTemplate(ATTRIBUTE_NAME_MOBILE,
    ATTRIBUTE_FRIENDLY_NAME_MOBILE, true);

  /** The attribute name for the "E-mail address" attribute (urn:oid:2.5.4.10). */
  public static final String ATTRIBUTE_NAME_MAIL = "urn:oid:2.5.4.10";

  /** The attribute friendly name for the "E-mail address" attribute (mail). */
  public static final String ATTRIBUTE_FRIENDLY_NAME_MAIL = "mail";

  /** Attribute template for the "E-mail address" attribute (Multi-valued). */
  public static final AttributeTemplate ATTRIBUTE_TEMPLATE_MAIL = new AttributeTemplate(ATTRIBUTE_NAME_MAIL,
    ATTRIBUTE_FRIENDLY_NAME_MAIL, true);

  /** The attribute name for the "Organization name" attribute (urn:oid:2.5.4.11). */
  public static final String ATTRIBUTE_NAME_O = "urn:oid:2.5.4.11";

  /** The attribute friendly name for the "Organization name" attribute (o). */
  public static final String ATTRIBUTE_FRIENDLY_NAME_O = "o";

  /** Attribute template for the "Organization name" attribute. */
  public static final AttributeTemplate ATTRIBUTE_TEMPLATE_O = new AttributeTemplate(ATTRIBUTE_NAME_O,
    ATTRIBUTE_FRIENDLY_NAME_O);

  /** The attribute name for the "Organizational unit name" attribute (urn:oid:2.5.4.97). */
  public static final String ATTRIBUTE_NAME_OU = "urn:oid:2.5.4.97";

  /** The attribute friendly name for the "Organizational unit name" attribute (ou). */
  public static final String ATTRIBUTE_FRIENDLY_NAME_OU = "ou";

  /** Attribute template for the "Organizational unit name" attribute (Multi-valued). */
  public static final AttributeTemplate ATTRIBUTE_TEMPLATE_OU = new AttributeTemplate(ATTRIBUTE_NAME_OU,
    ATTRIBUTE_FRIENDLY_NAME_OU, true);

  /** The attribute name for the "Organizational identifier code" attribute (urn:oid:1.2.752.201.3.1). */
  public static final String ATTRIBUTE_NAME_ORGANIZATION_IDENTIFIER = "urn:oid:1.2.752.201.3.1";

  /** The attribute friendly name for the "Organizational identifier code" attribute (organizationIdentifier). */
  public static final String ATTRIBUTE_FRIENDLY_NAME_ORGANIZATION_IDENTIFIER = "organizationIdentifier";

  /** Attribute template for the "Organizational identifier code" attribute. */
  public static final AttributeTemplate ATTRIBUTE_TEMPLATE_ORGANIZATION_IDENTIFIER = new AttributeTemplate(
    ATTRIBUTE_NAME_ORGANIZATION_IDENTIFIER, ATTRIBUTE_FRIENDLY_NAME_ORGANIZATION_IDENTIFIER);

  /** The attribute name for the "Organization affiliation" attribute (urn:oid:1.2.752.201.3.1). */
  public static final String ATTRIBUTE_NAME_ORG_AFFILIATION = "urn:oid:1.2.752.201.3.1";

  /** The attribute friendly name for the "Organization affiliation" attribute (orgAffiliation). */
  public static final String ATTRIBUTE_FRIENDLY_NAME_ORG_AFFILIATION = "orgAffiliation";

  /** Attribute template for the "Organization affiliation" attribute (Multi-valued). */
  public static final AttributeTemplate ATTRIBUTE_TEMPLATE_ORG_AFFILIATION = new AttributeTemplate(
    ATTRIBUTE_NAME_ORG_AFFILIATION, ATTRIBUTE_FRIENDLY_NAME_ORG_AFFILIATION, true);

  /** The attribute name for the "Transaction identifier" attribute (urn:oid:1.2.752.201.3.2). */
  public static final String ATTRIBUTE_NAME_TRANSACTION_IDENTIFIER = "urn:oid:1.2.752.201.3.2";

  /** The attribute friendly name for the "Transaction identifier" attribute (transactionIdentifier). */
  public static final String ATTRIBUTE_FRIENDLY_NAME_TRANSACTION_IDENTIFIER = "transactionIdentifier";

  /** Attribute template for the "Transaction identifier" attribute. */
  public static final AttributeTemplate ATTRIBUTE_TEMPLATE_TRANSACTION_IDENTIFIER = new AttributeTemplate(
    ATTRIBUTE_NAME_TRANSACTION_IDENTIFIER, ATTRIBUTE_FRIENDLY_NAME_TRANSACTION_IDENTIFIER);

  /** The attribute name for the "Authentication context parameters" attribute (urn:oid:1.2.752.201.3.3). */
  public static final String ATTRIBUTE_NAME_AUTH_CONTEXT_PARAMS = "urn:oid:1.2.752.201.3.3";

  /** The attribute friendly name for the "Authentication context parameters" attribute (authContextParams). */
  public static final String ATTRIBUTE_FRIENDLY_NAME_AUTH_CONTEXT_PARAMS = "authContextParams";

  /** Attribute template for the "Authentication context parameters" attribute. */
  public static final AttributeTemplate ATTRIBUTE_TEMPLATE_AUTH_CONTEXT_PARAMS = new AttributeTemplate(
    ATTRIBUTE_NAME_AUTH_CONTEXT_PARAMS, ATTRIBUTE_FRIENDLY_NAME_AUTH_CONTEXT_PARAMS);
  
  
  /*
   * Hidden constructor.
   */
  private AttributeConstants() {
  }

}
