/*
 * Copyright 2016-2021 Litsec AB
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.opensaml.saml.ext.saml2mdattr.EntityAttributes;
import org.opensaml.saml.saml2.core.Attribute;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;

import se.litsec.opensaml.saml2.attribute.AttributeBuilder;
import se.litsec.opensaml.saml2.attribute.AttributeUtils;
import se.litsec.opensaml.saml2.metadata.MetadataUtils;

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
    return createEntityCategoryAttribute(Arrays.asList(categories));
  }

  /**
   * See {@link #createEntityCategoryAttribute(String...)}.
   * 
   * @param categories
   *          the entity category values
   * @return an attribute holding all entity categories
   */
  public static Attribute createEntityCategoryAttribute(List<String> categories) {
    if (categories == null || categories.isEmpty()) {
      return null;
    }
    AttributeBuilder builder = AttributeBuilder.builder(ENTITY_CATEGORY_ATTRIBUTE_NAME).nameFormat(Attribute.URI_REFERENCE);
    categories.stream().forEach(builder::value);
    return builder.build();
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
    List<Attribute> attrs = entityAttributes.get().getAttributes().stream()
        .filter(a -> a.getName().equals(ENTITY_CATEGORY_ATTRIBUTE_NAME))
        .collect(Collectors.toList());
    List<String> categories = new ArrayList<>();
    for (Attribute attr : attrs) {
      categories.addAll(AttributeUtils.getAttributeStringValues(attr));
    }
    return categories;
  }

  // Hidden
  private EntityCategoryMetadataHelper() {
  }

}
