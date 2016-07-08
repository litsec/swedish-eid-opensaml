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
package se.litsec.swedisheid.opensaml.saml2.metadata.builders;

import org.opensaml.saml.saml2.metadata.Company;
import org.opensaml.saml.saml2.metadata.ContactPerson;
import org.opensaml.saml.saml2.metadata.ContactPersonTypeEnumeration;
import org.opensaml.saml.saml2.metadata.EmailAddress;
import org.opensaml.saml.saml2.metadata.GivenName;
import org.opensaml.saml.saml2.metadata.SurName;
import org.opensaml.saml.saml2.metadata.TelephoneNumber;

import se.litsec.swedisheid.opensaml.saml2.AbstractXMLObjectBuilder;
import se.litsec.swedisheid.opensaml.utils.SAMLUtils;

/**
 * A builder for {@code ContactPerson} elements.
 * 
 * @author Martin Lindström (martin.lindstrom@litsec.se)
 */
public class ContactPersonBuilder extends AbstractXMLObjectBuilder<ContactPerson> {

  /**
   * Constructor assigning the type of contact person.
   * 
   * @param type
   *          the type
   */
  public ContactPersonBuilder(ContactPersonTypeEnumeration type) {
    super();
    this.object().setType(type);
  }
  
  /** {@inheritDoc} */
  @Override
  protected Class<ContactPerson> getObjectType() {
    return ContactPerson.class;
  }

  /**
   * Assigns the {@code Company} element.
   * 
   * @param company
   *          the company
   * @return the builder
   */
  public ContactPersonBuilder company(String company) {
    if (company != null) {
      Company c = SAMLUtils.createSamlObject(Company.class);
      c.setName(company);
      this.object().setCompany(c);
    }
    return this;
  }

  /**
   * Assigns the {@code GivenName} element.
   * 
   * @param givenName
   *          the name
   * @return the builder
   */
  public ContactPersonBuilder givenName(String givenName) {
    if (givenName != null) {
      GivenName gn = SAMLUtils.createSamlObject(GivenName.class);
      gn.setName(givenName);
      this.object().setGivenName(gn);
    }
    return this;
  }

  /**
   * Assigns the {@code SurName} element.
   * 
   * @param surname
   *          the name
   * @return the builder
   */
  public ContactPersonBuilder surname(String surname) {
    if (surname != null) {
      SurName sn = SAMLUtils.createSamlObject(SurName.class);
      sn.setName(surname);
      this.object().setSurName(sn);
    }
    return this;
  }

  /**
   * Assigns the {@code EmailAddress} elements.
   * 
   * @param emailAddresses
   *          the email addesses
   * @return the builder
   */
  public ContactPersonBuilder emailAddresses(String... emailAddresses) {
    if (emailAddresses != null) {
      for (String e : emailAddresses) {
        EmailAddress ea = SAMLUtils.createSamlObject(EmailAddress.class);
        ea.setAddress(e);
        this.object().getEmailAddresses().add(ea);
      }
    }
    return this;
  }

  /**
   * Assigns the {@code TelephoneNumber} elements.
   * 
   * @param telephoneNumbers
   *          the numbers to assign
   * @return the builder
   */
  public ContactPersonBuilder telephoneNumbers(String... telephoneNumbers) {
    if (telephoneNumbers != null) {
      for (String t : telephoneNumbers) {
        TelephoneNumber tn = SAMLUtils.createSamlObject(TelephoneNumber.class);
        tn.setNumber(t);
        this.object().getTelephoneNumbers().add(tn);
      }
    }
    return this;
  }

}
