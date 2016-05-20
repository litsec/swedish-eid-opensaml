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
 * Implementation of the {@link EntityCategory} interface.
 * 
 * @author Martin Lindström (martin.lindstrom@litsec.se)
 */
public class EntityCategoryImpl implements EntityCategory {

  /** The unique URI for this category. */
  protected String uri;

  /** The type of entity category. */
  protected EntityCategoryType type;

  /**
   * Default constructor.
   */
  public EntityCategoryImpl() {
  }

  /**
   * Constructor assigning the unique URI and the category type.
   * 
   * @param uri
   *          the URI
   * @param type
   *          the type
   */
  public EntityCategoryImpl(String uri, EntityCategoryType type) {
    this.uri = uri;
    this.type = type;
  }

  /*
   * (non-Javadoc)
   * 
   * @see se.sveleg.saml2.entitycategory.EntityCategory#getUri()
   */
  @Override
  public String getUri() {
    return this.uri;
  }

  /**
   * Each entity category is assigned an unique URI. This method assigns this value.
   * 
   * @param uri
   *          the URI to assign.
   */
  public void setUri(String uri) {
    this.uri = uri;
  }

  /*
   * (non-Javadoc)
   * 
   * @see se.sveleg.saml2.entitycategory.EntityCategory#getType()
   */
  @Override
  public EntityCategoryType getType() {
    return this.type;
  }

  /**
   * Assigns the type of entity category.
   * 
   * @param type
   *          the type to assign.
   */
  public void setType(EntityCategoryType type) {
    this.type = type;
  }

}
