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

import java.util.List;

/**
 * A registry that handles all "registered" entity categories. It is used to find the definition of an entity category
 * based on its URI.
 * 
 * @author Martin Lindström (martin.lindstrom@litsec.se)
 */
public interface EntityCategoryRegistry {

  /**
   * Given an URI, the method will find the {@link EntityCategory} object that is registered for this identifier.
   * 
   * @param uri
   *          the entity category URI
   * @return the {@code EntityCategory} object, or {@code null} if no category is registered for the supplied URI
   */
  EntityCategory getEntityCategory(String uri);

  /**
   * Returns a list of all registered entity categories.
   * 
   * @return a list of all registered entity categories
   */
  List<EntityCategory> getEntityCategories();

  /**
   * Returns a list of all registered entity categories that are of the type "Service entity category".
   * 
   * @return a list of all registered service entity categories
   */
  List<ServiceEntityCategory> getServiceEntityCategories();

}
