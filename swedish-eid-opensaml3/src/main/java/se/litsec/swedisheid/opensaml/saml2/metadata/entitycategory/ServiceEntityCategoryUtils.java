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

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.opensaml.saml.saml2.metadata.EntityDescriptor;

import se.litsec.swedisheid.opensaml.saml2.authentication.LevelofAssuranceAuthenticationContextURI;

/**
 * Utility methods for service entity categories.
 * 
 * @author Martin Lindström (martin.lindstrom@litsec.se)
 */
public class ServiceEntityCategoryUtils {

  /**
   * Returns a list of Level of Assurance identifiers using the entity categories found in the supplied metadata. The
   * list is sorted where the lowest LoA is placed first in the list.
   * 
   * @param ed
   *          metadata to search
   * @param entityCategoryRegistry
   *          registry of entity categories
   * @return a list of LoA identifiers
   */
  public static List<String> getLoAIdentifiersFromEntityCategories(EntityDescriptor ed,
      EntityCategoryRegistry entityCategoryRegistry) {
        
    Comparator<ServiceEntityCategory> sorter = (o1, o2) -> {
      LevelofAssuranceAuthenticationContextURI.LoaEnum l1 = LevelofAssuranceAuthenticationContextURI.LoaEnum.parse(o1.getLevelOfAssurance());
      LevelofAssuranceAuthenticationContextURI.LoaEnum l2 = LevelofAssuranceAuthenticationContextURI.LoaEnum.parse(o2.getLevelOfAssurance());

      return l1.getLevel() < l2.getLevel() ? -1 : (l1.getLevel() > l2.getLevel() ? 1 : 0);
    };
    
    return EntityCategoryMetadataHelper.getEntityCategories(ed).stream()
      .map(entityCategoryRegistry::getEntityCategory)
      .filter(Optional::isPresent)
      .map(Optional::get)
      .filter(e -> EntityCategoryType.SERVICE_ENTITY.equals(e.getType()))
      .map(ServiceEntityCategory.class::cast)
      .sorted(sorter)
      .map(ServiceEntityCategory::getLevelOfAssurance)
      .distinct()
      .collect(Collectors.toList());    
  }

  // Hidden
  private ServiceEntityCategoryUtils() {
  }

}
