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
package se.litsec.swedisheid.opensaml.saml2.metadata.builders;

import org.opensaml.saml.saml2.metadata.AttributeConsumingService;
import org.opensaml.saml.saml2.metadata.ServiceDescription;
import org.opensaml.saml.saml2.metadata.ServiceName;

import se.litsec.swedisheid.opensaml.saml2.AbstractXMLObjectBuilder;
import se.litsec.swedisheid.opensaml.saml2.LocalizedString;
import se.litsec.swedisheid.opensaml.utils.SAMLUtils;

/**
 * A builder for {@code AttributeConsumingService} elements.
 * 
 * @author Martin Lindström (martin.lindstrom@litsec.se)
 */
public class AttributeConsumingServiceBuilder extends AbstractXMLObjectBuilder<AttributeConsumingService> {

  /**
   * Default constructor.
   */
  public AttributeConsumingServiceBuilder() {
  }

  /**
   * Assigns the service names.
   * 
   * @param names
   *          the service names
   * @return the builder
   */
  public AttributeConsumingServiceBuilder names(LocalizedString... names) {
    this.object().getNames().clear();
    if (names == null) {
      return this;
    }
    for (LocalizedString s : names) {
      ServiceName sn = SAMLUtils.createSamlObject(ServiceName.class);
      sn.setValue(s.getLocalString());
      sn.setXMLLang(s.getLanguage());
      this.object().getNames().add(sn);
    }
    return this;
  }

  /**
   * Assigns the service descriptions.
   * 
   * @param descriptions
   *          the service descriptions
   * @return the builder
   */
  public AttributeConsumingServiceBuilder descriptions(LocalizedString... descriptions) {
    this.object().getDescriptions().clear();
    if (descriptions == null) {
      return this;
    }
    for (LocalizedString s : descriptions) {
      ServiceDescription sd = SAMLUtils.createSamlObject(ServiceDescription.class);
      sd.setValue(s.getLocalString());
      sd.setXMLLang(s.getLanguage());
      this.object().getDescriptions().add(sd);
    }
    return this;
  }
  
  // RequestedAttributes ...
//  RequestedAttribute x;

  /** {@inheritDoc} */
  @Override
  protected Class<AttributeConsumingService> getObjectType() {
    return AttributeConsumingService.class;
  }

}
