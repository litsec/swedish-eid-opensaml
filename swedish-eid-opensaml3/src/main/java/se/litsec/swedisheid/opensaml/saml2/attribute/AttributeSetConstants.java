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
 * Defines all Attribute Set defined in section 2 of "Attribute Specification for the Swedish eID Framework".
 * 
 * @author Martin Lindström (martin.lindstrom@litsec.se)
 */
public class AttributeSetConstants {

  /**
   * Pseudonym Identity - This attribute set specifies the condition where there are no mandatory or recommended
   * attributes.
   * <p>
   * <b>Typical use:</b> In a pseudonym attribute release policy that just provides a persistent NameID identifier in
   * the assertion but no attributes.
   * </p>
   */
  public static final AttributeSet ATTRIBUTE_SET_PSEUDONYM_IDENTITY = new AttributeSetImpl("ELN-AP-Pseudonym-01",
    "http://id.elegnamnden.se/ap/1.0/pseudonym-01", "Pseudonym Identity", null, null);

  /**
   * Natural Personal Identity without Civic Registration Number - The “Personal Identity without Civic Registration
   * Number” attribute set provides basic natural person information without revealing the civic registration number of
   * the subject.
   * <p>
   * <b>Typical use:</b> In an attribute release policy that provides basic user name information together with a
   * persistent NameID identifier in the assertion.
   * </p>
   */
  public static final AttributeSet ATTRIBUTE_SET_NATURAL_PERSON_NO_PERSONAL_ID = new AttributeSetImpl(
    "ELN-AP-NaturalPerson-01", "http://id.elegnamnden.se/ap/1.0/natural-person-01",
    "Natural Personal Identity without Civic Registration Number", new AttributeTemplate[] {
        AttributeConstants.ATTRIBUTE_TEMPLATE_DISPLAY_NAME, AttributeConstants.ATTRIBUTE_TEMPLATE_SN,
        AttributeConstants.ATTRIBUTE_TEMPLATE_GIVEN_NAME }, null);

  /**
   * Natural Personal Identity with Civic Registration Number (Personnummer) - The “Personal Identity with Civic
   * Registration Number” attribute set provides basic personal identity information including a Swedish civic
   * registration number of the subject.
   * <p>
   * <b>Typical use:</b> In an attribute release policy that provides basic user name information together with the
   * person’s Swedish civic registration number.
   * </p>
   */
  public static final AttributeSet ATTRIBUTE_SET_NATURAL_PERSON_WITH_PERSONAL_ID = new AttributeSetImpl(
    "ELN-AP-Pnr-01", "http://id.elegnamnden.se/ap/1.0/pnr-01",
    "Natural Personal Identity with Civic Registration Number (Personnummer)", new AttributeTemplate[] {
        AttributeConstants.ATTRIBUTE_TEMPLATE_PERSONAL_IDENTITY_NUMBER, AttributeConstants.ATTRIBUTE_TEMPLATE_SN,
        AttributeConstants.ATTRIBUTE_TEMPLATE_GIVEN_NAME, AttributeConstants.ATTRIBUTE_TEMPLATE_DISPLAY_NAME }, null);

  /**
   * Organizational Identity for Natural Persons - The “Organizational Identity for Natural Persons” attribute set
   * provides basic organizational identity information about a person. The organizational identity does not necessarily
   * imply that the subject has any particular rela-tionship with or standing within the organization, but rather that
   * this identity has been issued/provided by that organization for any particular reason (employee, customer,
   * consultant, etc.).
   * <p>
   * <b>Typical use:</b> In an attribute release policy that provides basic organizational identity information about a
   * natural person.
   * </p>
   */
  public static final AttributeSet ATTRIBUTE_SET_ORGANIZATIONAL_IDENTITY_FOR_NATURAL_PERSONS = new AttributeSetImpl(
    "ELN-AP-OrgPerson-01", "http://id.elegnamnden.se/ap/1.0/org-person-01",
    "Organizational Identity for Natural Persons", new AttributeTemplate[] { AttributeConstants.ATTRIBUTE_TEMPLATE_SN,
        AttributeConstants.ATTRIBUTE_TEMPLATE_GIVEN_NAME, AttributeConstants.ATTRIBUTE_TEMPLATE_DISPLAY_NAME,
        AttributeConstants.ATTRIBUTE_TEMPLATE_ORG_AFFILIATION, AttributeConstants.ATTRIBUTE_TEMPLATE_O },
    new AttributeTemplate[] { AttributeConstants.ATTRIBUTE_TEMPLATE_ORGANIZATION_IDENTIFIER,
        AttributeConstants.ATTRIBUTE_TEMPLATE_OU });

  /*
   * Hidden constructor.
   */
  private AttributeSetConstants() {
  }

}
