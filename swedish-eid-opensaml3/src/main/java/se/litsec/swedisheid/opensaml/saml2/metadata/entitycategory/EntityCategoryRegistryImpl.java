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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Implementation of the {@link EntityCategoryRegistry} interface.
 * 
 * @author Martin Lindström (martin.lindstrom@litsec.se)
 */
public class EntityCategoryRegistryImpl implements EntityCategoryRegistry {

  /** The registered entity categories. */
  private List<EntityCategory> entityCategories;

  /**
   * Constructor.
   * 
   * @param entityCategories
   *          registered entity categories
   */
  public EntityCategoryRegistryImpl(List<EntityCategory> entityCategories) {
    this.entityCategories = entityCategories;
  }

  @Override
  public EntityCategory getEntityCategory(String uri) {
    for (EntityCategory c : this.entityCategories) {
      if (c.getUri().equals(uri)) {
        return c;
      }
    }
    return null;
  }

  @Override
  public List<EntityCategory> getEntityCategories() {
    return Collections.unmodifiableList(this.entityCategories);
  }

  @Override
  public List<ServiceEntityCategory> getServiceEntityCategories() {
    List<ServiceEntityCategory> list = new ArrayList<ServiceEntityCategory>();
    for (EntityCategory e : this.entityCategories) {
      if (e.getType().equals(EntityCategoryType.SERVICE_ENTITY)) {
        list.add(ServiceEntityCategory.class.cast(e));
      }      
    }
    return list;
  }

}
