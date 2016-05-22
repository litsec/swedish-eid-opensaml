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

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

  /** {@inheritDoc} */
  @Override
  public Optional<EntityCategory> getEntityCategory(String uri) {
    return this.entityCategories.stream()
        .filter(e -> e.getUri().equals(uri))
        .findFirst();
  }

  /** {@inheritDoc} */
  @Override
  public List<EntityCategory> getEntityCategories() {
    return Collections.unmodifiableList(this.entityCategories);
  }

  /** {@inheritDoc} */
  @Override
  public List<ServiceEntityCategory> getServiceEntityCategories() {
    return this.entityCategories.stream()
      .filter(e -> EntityCategoryType.SERVICE_ENTITY.equals(e.getType()))
      .map(ServiceEntityCategory.class::cast)
      .collect(Collectors.toList());
  }

}
