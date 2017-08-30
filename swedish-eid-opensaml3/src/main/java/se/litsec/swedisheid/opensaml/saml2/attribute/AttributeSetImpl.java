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
package se.litsec.swedisheid.opensaml.saml2.attribute;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.opensaml.saml.saml2.core.Assertion;
import org.opensaml.saml.saml2.core.Attribute;
import org.opensaml.saml.saml2.metadata.RequestedAttribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.litsec.opensaml.saml2.attribute.AttributeTemplate;

/**
 * A bean representing an Attribute Set as defined in Attribute Specification for the Swedish eID Framework.
 * 
 * @author Martin Lindström (martin.lindstrom@litsec.se)
 */
public class AttributeSetImpl implements AttributeSet {

  /** Logging instance. */
  private static final Logger logger = LoggerFactory.getLogger(AttributeSetImpl.class);

  /** The unique profile identifier. */
  private String identifier;

  /** The unique profile URI. */
  private String uri;

  /** The "friendly name" of the attribute profile. */
  private String friendlyName;

  /** The required attributes for this attribute profile. */
  private List<AttributeTemplate> requiredAttributes;

  /** The recommended attributes for this attribute profile. */
  private List<AttributeTemplate> recommendedAttributes;

  /**
   * Default constructor.
   */
  public AttributeSetImpl() {
  }

  /**
   * A constructor setting all properties of this bean.
   * 
   * @param identifier
   *          the unique profile identifier
   * @param uri
   *          the unique profile URI
   * @param friendlyName
   *          the "friendly name" of the attribute set
   * @param requiredAttributes
   *          the required attributes for this attribute set
   */
  public AttributeSetImpl(String identifier, String uri, String friendlyName, AttributeTemplate[] requiredAttributes, AttributeTemplate[] recommendedAttributes) {
    this.setIdentifier(identifier);
    this.setUri(uri);
    this.setFriendlyName(friendlyName);
    this.setRequiredAttributes(requiredAttributes);
    this.setRecommendedAttributes(recommendedAttributes);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void validateAttributes(Assertion assertion, List<RequestedAttribute> explicitlyRequestedAttributes)
      throws AttributesValidationException {

    logger.trace("Validating the attributes from assertion '{}' against attribute set '{}' ({}) ...",
      assertion.getID(), this.identifier, this.uri);

    List<Attribute> attributes = assertion.getAttributeStatements().get(0).getAttributes(); 

    // Make sure that all attributes required by the attribute set was received in the assertion.
    //            
    for (AttributeTemplate requiredAttribute : this.requiredAttributes) {
      Optional<Attribute> found = attributes.stream().filter(a -> requiredAttribute.getName().equals(a.getName())).findFirst();
      if (!found.isPresent()) {
        String msg = String.format(
          "Attribute '%s' (%s) is required according to the attribute set '%s' (%s) but is not included in assertion '%s'",
          requiredAttribute.getName(), requiredAttribute.getFriendlyName(), this.identifier, this.uri,
          assertion.getID());
        logger.error(msg);
        throw new AttributesValidationException(msg);
      }
    }
    logger.debug("All requested attributes according to attribute profile '{}' ({}) was received in assertion '{}'",
      this.identifier, this.uri, assertion.getID());

    // Next, check that all requested attributes are there.
    //
    if (explicitlyRequestedAttributes != null) {      
      for (RequestedAttribute ra : explicitlyRequestedAttributes) {
        Optional<Attribute> found = attributes.stream().filter(a -> ra.getName().equals(a.getName())).findFirst();
        if (!found.isPresent()) {
          if (ra.isRequired() != null && ra.isRequired().booleanValue()) {
            String msg = String.format(
              "Attribute '%s' (%s) is listed a RequestedAttribute with isRequired=true in SP metadata, but does not appear in assertion '%s'",
              ra.getName(), ra.getFriendlyName(), assertion.getID());
            logger.error(msg);
            throw new AttributesValidationException(msg);
          }
          else {
            logger.info(
              "Attribute '{}' ({}) is listed a requested, but not required, in SP metadata. It does not appear in assertion '{}'",
              ra.getName(), ra.getFriendlyName(), assertion.getID());
          }
        }
        else {
          // Note: There are some odd cases when the SP may give also a value for the requested attribute. We do not
          // make any checks for this since it has no bearing on the Swedish eID Framework.
          //
          logger.debug("Attribute '{}' ({}) was explicitly requested in SP metadata and it appears in assertion '{}'",
            ra.getName(), ra.getFriendlyName(), assertion.getID());
        }
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getIdentifier() {
    return this.identifier;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getUri() {
    return this.uri;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getFriendlyName() {
    return this.friendlyName;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AttributeTemplate[] getRequiredAttributes() {
    return this.requiredAttributes != null ? this.requiredAttributes.toArray(new AttributeTemplate[]{}) : new AttributeTemplate[0];  
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public AttributeTemplate[] getRecommendedAttributes() {
    return this.recommendedAttributes != null ? this.recommendedAttributes.toArray(new AttributeTemplate[]{}) : new AttributeTemplate[0]; 
  }  

  /**
   * Each attribute set within the Swedish eID Framework is assigned an unique profile identifier. This method assigns
   * this unique value.
   * 
   * @param identifier
   *          the identifier to assign
   */
  public void setIdentifier(String identifier) {
    this.identifier = identifier;
  }

  /**
   * Each attribute set within the Swedish eID Framework is assigned an unique URI. This method assigns this unique
   * value.
   * 
   * @param uri
   *          the URI to assign
   */
  public void setUri(String uri) {
    this.uri = uri;
  }

  /**
   * Assigns the friendly name for this attribute set.
   * 
   * @param friendlyName
   *          the friendlyName to set
   */
  public void setFriendlyName(String friendlyName) {
    this.friendlyName = friendlyName;
  }

  /**
   * Assigns the required attributes for this attribute set.
   * 
   * @param requiredAttributes
   *          the attributes to assign
   */
  public void setRequiredAttributes(AttributeTemplate[] requiredAttributes) {
    this.requiredAttributes = requiredAttributes != null ? Arrays.asList(requiredAttributes) : Collections.emptyList(); 
  }

  /**
   * Assigns the recommended attributes for this set.
   * 
   * @param recommendedAttributes
   *          the attributes to assign
   */
  public void setRecommendedAttributes(AttributeTemplate[] recommendedAttributes) {
    this.recommendedAttributes = recommendedAttributes != null ? Arrays.asList(recommendedAttributes) : Collections.emptyList(); 
  }

}
