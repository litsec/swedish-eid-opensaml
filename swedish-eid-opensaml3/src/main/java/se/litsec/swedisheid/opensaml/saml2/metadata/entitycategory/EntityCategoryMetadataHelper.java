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
import java.util.Optional;

import org.opensaml.saml.ext.saml2mdattr.EntityAttributes;
import org.opensaml.saml.saml2.core.Attribute;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;

import se.litsec.swedisheid.opensaml.saml2.attribute.AttributeUtils;
import se.litsec.swedisheid.opensaml.saml2.metadata.MetadataUtils;

/**
 * Helper class for handling entity categories in metadata.
 * 
 * @author Martin Lindström (martin.lindstrom@litsec.se)
 */
public class EntityCategoryMetadataHelper {

  /** The attribute name for entity categories stored as attributes. */
  public static final String ENTITY_CATEGORY_ATTRIBUTE_NAME = "http://macedir.org/entity-category";

  /**
   * Given a number of Entity Category URIs a SAML attribute holding these values is created.
   * 
   * @param categories
   *          the entity category values
   * @return an attribute holding all entity categories
   */
  public static Attribute createEntityCategoryAttribute(String... categories) {
    if (categories == null || categories.length == 0) {
      return null;
    }
    return AttributeUtils.createAttribute(ENTITY_CATEGORY_ATTRIBUTE_NAME, Attribute.URI_REFERENCE, null, categories);
  }

  /**
   * Given an entity descriptor the method returns a list of all entity categories that are stored under the
   * md:EntityAttributes extension.
   * 
   * @param ed
   *          the entity descriptor
   * @return a list of entity category URIs
   */
  public static List<String> getEntityCategories(EntityDescriptor ed) {
    Optional<EntityAttributes> entityAttributes = MetadataUtils.getEntityAttributes(ed);
    if (!entityAttributes.isPresent()) {
      return Collections.emptyList();
    }
    List<Attribute> attrs = AttributeUtils.getAttributes(ENTITY_CATEGORY_ATTRIBUTE_NAME, entityAttributes.get().getAttributes());
    List<String> categories = new ArrayList<String>();    
    for (Attribute attr : attrs) {
      categories.addAll(AttributeUtils.getAttributeStringValues(attr));
    }
    return categories;
  }

  // Hidden
  private EntityCategoryMetadataHelper() {
  }

}
