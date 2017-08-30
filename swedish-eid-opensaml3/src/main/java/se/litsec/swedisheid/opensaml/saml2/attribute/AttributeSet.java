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

import java.util.List;

import org.opensaml.saml.saml2.core.Assertion;
import org.opensaml.saml.saml2.metadata.RequestedAttribute;

import se.litsec.opensaml.saml2.attribute.AttributeTemplate;

/**
 * The specification "Attribute Specification for the Swedish eID Framework" of the Swedish eID Framework defines a
 * number of "Attribute Sets". This interface represents such an attribute set.
 * 
 * @author Martin Lindström (martin.lindstrom@litsec.se)
 * @see AttributeSetConstants
 */
public interface AttributeSet {

  /**
   * Each attribute set within the Swedish eID Framework is assigned an unique profile identifier. This method
   * returns this unique value.
   * 
   * @return the attribute set identifier
   * @see #getUri()
   */
  String getIdentifier();

  /**
   * Each attribute set within the Swedish eID Framework is assigned an unique URI. This method returns this value.
   * 
   * @return the attribute set URI
   * @see #getIdentifier()
   */
  String getUri();

  /**
   * Returns the friendly name for this attribute set.
   * 
   * @return the attribute set friendly name
   */
  String getFriendlyName();

  /**
   * Returns the required attributes for this attribute set
   * 
   * @return an array of required attributes for this set
   */
  AttributeTemplate[] getRequiredAttributes();
  
  /**
   * Returns the recommended attributes for this attribute set
   * 
   * @return an array of recommended attributes for this set
   */
  AttributeTemplate[] getRecommendedAttributes();  

  /**
   * Validates the attributes received in the assertion against the attribute set.
   * <p>
   * The validation logic is as follows:
   * <ul>
   * <li>Make sure that all the attributes that the set states as "required" are included in the assertion.</li>
   * <li>Make sure that all explicitly requested attributes, that has the attribute isRequired set, are included in the
   * assertion. These requested attributes are listed in the SP metadata record as {@code <md:RequestedAttribute>}
   * elements.</li>
   * </ul>
   * </p>
   * 
   * @param assertion
   *          the assertion containing the attributes to validate
   * @param explicitlyRequestedAttributes
   *          a list of explicitly requested attributes that the Service Provider has specified in its metadata record
   *          (using {@code <md:RequestedAttribute>} elements). This parameter may be {@code null} if no explicitly
   *          requested attributes exist
   * @throws AttributesValidationException
   *           for violations of the attribute set
   */
  void validateAttributes(Assertion assertion, List<RequestedAttribute> explicitlyRequestedAttributes)
      throws AttributesValidationException;

}
