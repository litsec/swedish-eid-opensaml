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

/**
 * Represents the different Entity Category types defined within the Swedish eiD Framework.
 * 
 * @author Martin Lindström (martin.lindstrom@litsec.se)
 */
public enum EntityCategoryType {

  /**
   * Meaning for a consuming service: Each declared category represents an alternative set of requirements for the
   * service. Meaning for a providing service: Represents the ability to deliver assertions in accordance with each
   * declared category.
   */
  SERVICE_ENTITY,
  /**
   * Meaning for a consuming service: Represents a property of this service. Meaning for a providing service: Represents
   * the ability to deliver assertions to a consuming service that has the declared property.
   */
  SERVICE_PROPERTY,
  /**
   * Declares the type of service provided by a consuming service.
   */
  SERVICE_TYPE
}
