/*
 * Copyright 2016-2018 Litsec AB
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
