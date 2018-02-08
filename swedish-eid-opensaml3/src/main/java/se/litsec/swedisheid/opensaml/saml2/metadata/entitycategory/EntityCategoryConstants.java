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

import se.litsec.swedisheid.opensaml.saml2.attribute.AttributeSetConstants;
import se.litsec.swedisheid.opensaml.saml2.authentication.LevelofAssuranceAuthenticationContextURI;

/**
 * Represents the Entity Categories defined by the Swedish eID Framework.
 * 
 * @author Martin Lindström (martin.lindstrom@litsec.se)
 */
public class EntityCategoryConstants {

  /**
   * Service entity category: User authentication according to assurance level 3 and attribute release according to the
   * attribute set “Natural Personal Identity with Civic Registration Number (personnum-mer)” (ELN-AP-Pnr-01).
   */
  public static final ServiceEntityCategory SERVICE_ENTITY_CATEGORY_LOA3_PNR = new ServiceEntityCategoryImpl(
    "http://id.elegnamnden.se/ec/1.0/loa3-pnr", LevelofAssuranceAuthenticationContextURI.AUTH_CONTEXT_URI_LOA3,
    AttributeSetConstants.ATTRIBUTE_SET_NATURAL_PERSON_WITH_PERSONAL_ID);

  /**
   * Service entity category: User authentication according to assurance level 2 and attribute release according to the
   * attribute set “Natural Personal Identity with Civic Registration Number (personnum-mer)” (ELN-AP-Pnr-01).
   */
  public static final ServiceEntityCategory SERVICE_ENTITY_CATEGORY_LOA2_PNR = new ServiceEntityCategoryImpl(
    "http://id.elegnamnden.se/ec/1.0/loa2-pnr", LevelofAssuranceAuthenticationContextURI.AUTH_CONTEXT_URI_LOA2,
    AttributeSetConstants.ATTRIBUTE_SET_NATURAL_PERSON_WITH_PERSONAL_ID);

  /**
   * Service entity category: User authentication according to assurance level 4 and attribute release according to the
   * attribute set “Natural Personal Identity with Civic Registration Number (personnum-mer)” (ELN-AP-Pnr-01).
   */
  public static final ServiceEntityCategory SERVICE_ENTITY_CATEGORY_LOA4_PNR = new ServiceEntityCategoryImpl(
    "http://id.elegnamnden.se/ec/1.0/loa4-pnr", LevelofAssuranceAuthenticationContextURI.AUTH_CONTEXT_URI_LOA4,
    AttributeSetConstants.ATTRIBUTE_SET_NATURAL_PERSON_WITH_PERSONAL_ID);

  /**
   * Service property category: For a providing service, i.e. an Identity Provider, inclusion of the mobile-auth
   * category states that the Identity Provider supports authentication using mobile devices, and that the end-user
   * interface of the Identity Provider is adapted for mobile clients.
   * 
   * Note that an Identity Provider may of course support authentication for both desktop and mobile users. In these
   * cases the service must be able to display end user interfaces for both types of clients.
   */
  public static final EntityCategory SERVICE_PROPERTY_CATEGORY_MOBILE_AUTH = new EntityCategoryImpl(
    "http://id.elegnamnden.se/sprop/1.0/mobile-auth", EntityCategoryType.SERVICE_PROPERTY);

  /**
   * Service type category: A service type for a Service Provider that provides electronic signature services within the
   * Swedish eID framework.
   */
  public static final EntityCategory SERVICE_TYPE_CATEGORY_SIGSERVICE = new EntityCategoryImpl(
    "http://id.elegnamnden.se/st/1.0/sigservice", EntityCategoryType.SERVICE_TYPE);

  /*
   * Hidden.
   */
  private EntityCategoryConstants() {
  }

}
