/*
 * Copyright 2016-2022 Litsec AB
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
import java.util.Collections;
import java.util.List;

import org.opensaml.saml.saml2.metadata.EntityDescriptor;

/**
 * Utility methods for service entity categories.
 * 
 * @author Martin Lindström (martin.lindstrom@litsec.se)
 */
public class ServiceEntityCategoryUtils {

  /**
   * Returns a list of Level of Assurance identifiers using the entity categories found in the supplied metadata.
   * 
   * @param ed
   *          metadata to search
   * @param entityCategoryRegistry
   *          registry of entity categories
   * @return a list of LoA identifiers
   */
  public static List<String> getLoAIdentifiersFromEntityCategories(final EntityDescriptor ed,
      final EntityCategoryRegistry entityCategoryRegistry) {

    final List<String> loas = new ArrayList<>();
    for (final String cat : EntityCategoryMetadataHelper.getEntityCategories(ed)) {
      final List<String> uris =
          entityCategoryRegistry.getEntityCategory(cat)
            .filter(e -> EntityCategoryType.SERVICE_ENTITY.equals(e.getType()))
            .map(ServiceEntityCategory.class::cast)
            .map(ServiceEntityCategory::getLevelOfAssuranceUris)
            .orElse(Collections.emptyList());
      for (final String uri : uris) {
        if (!loas.contains(uri)) {
          loas.add(uri);
        }
      }
    }
    return loas;
  }

  // Hidden
  private ServiceEntityCategoryUtils() {
  }

}
