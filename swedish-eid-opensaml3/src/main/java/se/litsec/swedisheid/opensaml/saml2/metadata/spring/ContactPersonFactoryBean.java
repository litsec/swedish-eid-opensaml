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
package se.litsec.swedisheid.opensaml.saml2.metadata.spring;

import java.util.List;

import org.opensaml.saml.saml2.metadata.ContactPerson;
import org.opensaml.saml.saml2.metadata.ContactPersonTypeEnumeration;

import se.litsec.swedisheid.opensaml.saml2.AbstractXMLObjectBuilder;
import se.litsec.swedisheid.opensaml.saml2.metadata.builders.ContactPersonBuilder;
import se.litsec.swedisheid.opensaml.saml2.spring.AbstractXMLObjectBuilderFactoryBean;

/**
 * A Spring factory bean for creating {@link ContactPerson} objects.
 * 
 * @author Martin Lindström (martin.lindstrom@litsec.se)
 * @see ContactPersonBuilder
 */
public class ContactPersonFactoryBean extends AbstractXMLObjectBuilderFactoryBean<ContactPerson> {

  /** The builder. */
  private ContactPersonBuilder builder;

  /**
   * Constructor assigning the type of contact person.
   * 
   * @param type
   *          the type
   */
  ContactPersonFactoryBean(ContactPersonTypeEnumeration type) {
    this.builder = new ContactPersonBuilder(type);
  }

  /** {@inheritDoc} */
  @Override
  public Class<?> getObjectType() {
    return ContactPerson.class;
  }

  /** {@inheritDoc} */
  @Override
  protected AbstractXMLObjectBuilder<ContactPerson> builder() {
    return this.builder;
  }

  /**
   * @see ContactPersonBuilder#company(String)
   */
  public void setCompany(String company) {
    this.builder.company(company);
  }

  /**
   * @see ContactPersonBuilder#givenName(String)
   */
  public void setGivenName(String givenName) {
    this.builder.givenName(givenName);
  }

  /**
   * @see ContactPersonBuilder#surname(String)
   */
  public void setSurname(String surname) {
    this.builder.surname(surname);
  }

  /**
   * @see ContactPersonBuilder#emailAddresses(String...)
   */
  public void setEmailAddresses(List<String> emailAddresses) {
    this.builder.emailAddresses(stringListToVarArgs(emailAddresses));
  }

  /**
   * @see ContactPersonBuilder#emailAddresses(String...)
   */
  public void setEmailAddresses(String emailAddress) {
    this.builder.emailAddresses(emailAddress);
  }

  /**
   * @see ContactPersonBuilder#telephoneNumbers(String...)
   */
  public void setTelephoneNumbers(List<String> telephoneNumbers) {
    this.builder.telephoneNumbers(stringListToVarArgs(telephoneNumbers));
  }

  /**
   * @see ContactPersonBuilder#telephoneNumbers(String...)
   */
  public void setTelephoneNumber(String telephoneNumber) {
    this.builder.telephoneNumbers(telephoneNumber);
  }

}
