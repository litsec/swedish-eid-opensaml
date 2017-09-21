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

import java.util.Comparator;
import java.util.List;
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
        
    Comparator<ServiceEntityCategory> sorter = new Comparator<ServiceEntityCategory>() {
      @Override
      public int compare(ServiceEntityCategory o1, ServiceEntityCategory o2) {
        LevelofAssuranceAuthenticationContextURI.LoaEnum l1 = LevelofAssuranceAuthenticationContextURI.LoaEnum.parse(o1.getLevelOfAssurance());
        LevelofAssuranceAuthenticationContextURI.LoaEnum l2 = LevelofAssuranceAuthenticationContextURI.LoaEnum.parse(o2.getLevelOfAssurance());
        
        return l1.getLevel() < l2.getLevel() ? -1 : (l1.getLevel() > l2.getLevel() ? 1 : 0);
      }
    };
    
    return EntityCategoryMetadataHelper.getEntityCategories(ed).stream()
      .map(e -> entityCategoryRegistry.getEntityCategory(e))
      .filter(e -> e.isPresent())
      .map(e -> e.get())
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
