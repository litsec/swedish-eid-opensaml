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
package se.litsec.swedisheid.opensaml.saml2.metadata.entitycategory;

import se.litsec.swedisheid.opensaml.saml2.attribute.AttributeSet;

/**
 * Represents a "Service Entity Category".
 * 
 * @author Martin Lindström (martin.lindstrom@litsec.se)
 */
public interface ServiceEntityCategory extends EntityCategory {

  /**
   * Returns the Level of Assurance associated with this service entity category.
   * <p>
   * The identifier indicates that only services conforming to at least the specified level of assurance have the
   * capability to satisfy the security requirements of the Service Provider. An Identity Provider declaring this
   * Service Entity Category MUST be able to provide this level of assurance.
   * </p>
   * 
   * @return the URI representing the Level of Assurance
   */
  String getLevelOfAssurance();

  /**
   * Returns the attribute set tied to this service entity category.
   * <p>
   * The attribute set Indicates that only services that implement attribute release according to the identified
   * attribute set have the capability to satisfy the minimum attribute requirements of the Service Provider. An
   * Identity Provider declaring this Service Entity Category MUST be able to provide these attributes.
   * </p>
   * 
   * @return the attribute set
   */
  AttributeSet getAttributeSet();

}
