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
  
  /** The prefix for Service Entity categories. */
  public static final String SERVICE_ENTITY_CATEGORY_PREFIX = "http://id.elegnamnden.se/ec/";
  
  /** The prefix for Service Entity categories defined by Sweden Connect. */
  public static final String SERVICE_ENTITY_CATEGORY_PREFIX_SC = "http://id.swedenconnect.se/ec/";
  
  /** The prefix for Service Property categories. */
  public static final String SERVICE_PROPERTY_CATEGORY_PREFIX = "http://id.elegnamnden.se/sprop/";
  
  /** The prefix for Service Type categories. */
  public static final String SERVICE_TYPE_CATEGORY_PREFIX = "http://id.elegnamnden.se/st/";
  
  /** The prefix for Service Contract categories. */
  public static final String SERVICE_CONTRACT_CATEGORY_PREFIX = "http://id.swedenconnect.se/contract/";

  /**
   * Service entity category: User authentication according to assurance level 3 and attribute release according to the
   * attribute set “Natural Personal Identity with Civic Registration Number (personnum-mer)” (ELN-AP-Pnr-01).
   */
  public static final ServiceEntityCategory SERVICE_ENTITY_CATEGORY_LOA3_PNR = new ServiceEntityCategoryImpl(
    SERVICE_ENTITY_CATEGORY_PREFIX + "1.0/loa3-pnr", LevelofAssuranceAuthenticationContextURI.AUTH_CONTEXT_URI_LOA3,
    AttributeSetConstants.ATTRIBUTE_SET_NATURAL_PERSON_WITH_PERSONAL_ID);

  /**
   * Service entity category: User authentication according to assurance level 2 and attribute release according to the
   * attribute set “Natural Personal Identity with Civic Registration Number (personnum-mer)” (ELN-AP-Pnr-01).
   */
  public static final ServiceEntityCategory SERVICE_ENTITY_CATEGORY_LOA2_PNR = new ServiceEntityCategoryImpl(
    SERVICE_ENTITY_CATEGORY_PREFIX + "1.0/loa2-pnr", LevelofAssuranceAuthenticationContextURI.AUTH_CONTEXT_URI_LOA2,
    AttributeSetConstants.ATTRIBUTE_SET_NATURAL_PERSON_WITH_PERSONAL_ID);

  /**
   * Service entity category: User authentication according to assurance level 4 and attribute release according to the
   * attribute set “Natural Personal Identity with Civic Registration Number (personnum-mer)” (ELN-AP-Pnr-01).
   */
  public static final ServiceEntityCategory SERVICE_ENTITY_CATEGORY_LOA4_PNR = new ServiceEntityCategoryImpl(
    SERVICE_ENTITY_CATEGORY_PREFIX + "1.0/loa4-pnr", LevelofAssuranceAuthenticationContextURI.AUTH_CONTEXT_URI_LOA4,
    AttributeSetConstants.ATTRIBUTE_SET_NATURAL_PERSON_WITH_PERSONAL_ID);
  
  /**
   * Service entity category: User authentication according to any of the eIDAS assurance levels and attribute release 
   * according to “eIDAS Natural Person Attribute Set” (ELN-AP-eIDAS-NatPer-01).
   */
  public static final ServiceEntityCategory SERVICE_ENTITY_CATEGORY_EIDAS_NATURAL_PERSON = new ServiceEntityCategoryImpl(
    SERVICE_ENTITY_CATEGORY_PREFIX + "1.0/eidas-naturalperson", null,
    AttributeSetConstants.ATTRIBUTE_SET_EIDAS_NATURAL_PERSON);
  
  /**
   * Service entity category: For asserting a Swedish identity to a foreign service provider via the Swedish eIDAS Proxy 
   * Service. This entity category MUST NOT be set by any entity other than Identity Provider providing identity assertions
   * to the Swedish eIDAS Proxy Service and by the Swedish eIDAS Proxy Service itself.
   * 
   * Note that the Identity Providers release attributes according to the "Natural Personal Identity with Civic Registration
   * Number" attribute set. It is the responsibility of the Swedish eIDAS Proxy Service to transform these attributes into 
   * eIDAS attributes.
   */
  public static final ServiceEntityCategory SERVICE_ENTITY_CATEGORY_EIDAS_PNR_DELIVERY = new ServiceEntityCategoryImpl(
    SERVICE_ENTITY_CATEGORY_PREFIX + "1.0/eidas-pnr-delivery", null,
    AttributeSetConstants.ATTRIBUTE_SET_NATURAL_PERSON_WITH_PERSONAL_ID);
  
  /**
   * Service entity category: User authentication according to "uncertified" (self-declared) assurance level 3 and attribute 
   * release according to the attribute set “Natural Personal Identity with Civic Registration Number (personnum-mer)” (ELN-AP-Pnr-01).
   */
  public static final ServiceEntityCategory SERVICE_ENTITY_CATEGORY_UNCERTIFIED_LOA3_PNR = new ServiceEntityCategoryImpl(
    SERVICE_ENTITY_CATEGORY_PREFIX_SC + "sc/uncertified-loa3-pnr", LevelofAssuranceAuthenticationContextURI.AUTH_CONTEXT_URI_UNCERTIFIED_LOA3,
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
    SERVICE_PROPERTY_CATEGORY_PREFIX + "1.0/mobile-auth", EntityCategoryType.SERVICE_PROPERTY);
  
  /**
   * Service property category: A service property declaring that the service is adapted to support Sole Control 
   * Assurance Level 2 (SCAL2) in accordance with the "Signature Activation Protocol for Federated Signing"
   * specification.
   * 
   * For a providing service, i.e. an Identity Provider, inclusion of the scal2 service property states that the 
   * Identity Provider will return a "SAD" in response to a {@code SADRequest} in an authentication requests from 
   * a signing service.
   * 
   * For consuming services, Signature Services MAY include this service property if all authentication requests 
   * from the particular Signature Service include a {@code SADRequest} extension. A Service Provider that is not 
   * declared as a Signature Service MUST NOT include this service property in its metadata.
   */
  public static final EntityCategory SERVICE_PROPERTY_CATEGORY_SCAL2 = new EntityCategoryImpl(
    SERVICE_PROPERTY_CATEGORY_PREFIX + "1.0/scal2", EntityCategoryType.SERVICE_PROPERTY);

  /**
   * Service type category: A service type for a Service Provider that provides electronic signature services within the
   * Swedish eID framework.
   */
  public static final EntityCategory SERVICE_TYPE_CATEGORY_SIGSERVICE = new EntityCategoryImpl(
    SERVICE_TYPE_CATEGORY_PREFIX + "1.0/sigservice", EntityCategoryType.SERVICE_TYPE);
  
  /**
   * Service type category: A service type that indicates that an Service Provider is a "public sector" SP. This category 
   * MUST be used by public sector Service Providers wishing to use eIDAS authentication so that the Swedish eIDAS connector 
   * may include this information in the eIDAS authentication request.
   */
  public static final EntityCategory SERVICE_TYPE_CATEGORY_PUBLIC_SECTOR_SP = new EntityCategoryImpl(
    SERVICE_TYPE_CATEGORY_PREFIX + "1.0/public-sector-sp", EntityCategoryType.SERVICE_TYPE);
  
  /**
   * Service type category: A service type that indicates that an Service Provider is a "private sector" SP. This category 
   * MUST be used by public sector Service Providers wishing to use eIDAS authentication so that the Swedish eIDAS connector 
   * may include this information in the eIDAS authentication request.
   */
  public static final EntityCategory SERVICE_TYPE_CATEGORY_PRIVATE_SECTOR_SP = new EntityCategoryImpl(
    SERVICE_TYPE_CATEGORY_PREFIX + "1.0/private-sector-sp", EntityCategoryType.SERVICE_TYPE);
  
  /**
   * Service contract category: A service contract type that indicates that the holder has signed the Sweden Connect
   * federation contract. 
   */
  public static final EntityCategory SERVICE_CONTRACT_CATEGORY_SWEDEN_CONNECT = new EntityCategoryImpl(
    SERVICE_CONTRACT_CATEGORY_PREFIX + "sc/sweden-connect", EntityCategoryType.SERVICE_CONTRACT);
  
  /**
   * Service contract category: A service contract type that indicates that the holder has signed the 
   * "eID system of choice 2017" (Valfrihetssystem 2017) contract. 
   */
  public static final EntityCategory SERVICE_CONTRACT_CATEGORY_EID_CHOICE_2017 = new EntityCategoryImpl(
    SERVICE_CONTRACT_CATEGORY_PREFIX + "sc/eid-choice-2017", EntityCategoryType.SERVICE_CONTRACT);

  /*
   * Hidden.
   */
  private EntityCategoryConstants() {
  }

}
