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

/**
 * Represents the different Entity Category types defined within the Swedish eiD Framework.
 * 
 * @author Martin Lindström (martin.lindstrom@litsec.se)
 */
public enum EntityCategoryType {

  /**
   * Meaning for a consuming service: Each declared category represents an alternative set of requirements for the
   * service. Meaning for a providing service: Represents the ability to deliver assertions in accordance with each
   * declared category.
   */
  SERVICE_ENTITY,
  /**
   * Meaning for a consuming service: Represents a property of this service. Meaning for a providing service: Represents
   * the ability to deliver assertions to a consuming service that has the declared property.
   */
  SERVICE_PROPERTY,
  /**
   * Declares the type of service provided by a consuming service.
   */
  SERVICE_TYPE;
}
