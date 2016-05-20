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
 * Implementation of the {@link ServiceEntityCategory} interface.
 * 
 * @author Martin Lindström (martin)
 */
public class ServiceEntityCategoryImpl extends EntityCategoryImpl implements ServiceEntityCategory {

  /** The level of assurance URI of this category. */
  private String levelOfAssurance;

  /** The attribute profile for this category. */
  private AttributeSet attributeSet;

  /**
   * Default constructor.
   */
  public ServiceEntityCategoryImpl() {
    this.setType(EntityCategoryType.SERVICE_ENTITY);
  }

  /**
   * Constructor assigning the URI, Level of Assurance and the attribute set.
   * 
   * @param uri
   *          the unique URI
   * @param levelOfAssurance
   *          the Level of Assurance URI
   * @param attributeSet
   *          the attribute set
   */
  public ServiceEntityCategoryImpl(String uri, String levelOfAssurance, AttributeSet attributeSet) {
    super(uri, EntityCategoryType.SERVICE_ENTITY);
    this.levelOfAssurance = levelOfAssurance;
    this.attributeSet = attributeSet;
  }

  /** {@inheritDoc} */
  @Override
  public String getUri() {
    return this.uri;
  }

  /** {@inheritDoc} */
  @Override
  public EntityCategoryType getType() {
    return EntityCategoryType.SERVICE_ENTITY;
  }

  /** {@inheritDoc} */
  @Override
  public String getLevelOfAssurance() {
    return this.levelOfAssurance;
  }

  /** {@inheritDoc} */
  public void setLevelOfAssurance(String levelOfAssurance) {
    this.levelOfAssurance = levelOfAssurance;
  }

  /** {@inheritDoc} */
  @Override
  public AttributeSet getAttributeSet() {
    return this.attributeSet;
  }

  /**
   * Assigns the attribute set tied to this service entity category.
   * 
   * @param attributeSet
   *          the attribute set to assign
   */
  public void setAttributeProfile(AttributeSet attributeSet) {
    this.attributeSet = attributeSet;
  }

}
