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
package se.litsec.swedisheid.opensaml.saml2.metadata.spring;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.saml2.metadata.AssertionConsumerService;
import org.opensaml.saml.saml2.metadata.AttributeConsumingService;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;
import org.springframework.core.io.Resource;

import net.shibboleth.utilities.java.support.xml.XMLParserException;
import se.litsec.swedisheid.opensaml.saml2.metadata.builders.AbstractEntityDescriptorBuilder;
import se.litsec.swedisheid.opensaml.saml2.metadata.builders.SpEntityDescriptorBuilder;

/**
 * Factory bean for creating {@code EntityDescriptor} objects for SAML Service Providers.
 * <p>
 * When a template object is used, the factory bean is created using the
 * {@link #AbstractEntityDescriptorFactoryBean(Resource)} or
 * {@link #AbstractEntityDescriptorFactoryBean(EntityDescriptor)} constructors. The user may later change, or add, any
 * of the elements and attributes of the template object using the setter methods.
 * </p>
 * <p>
 * Note that no Signature will be included.
 * </p>
 * 
 * @author Martin Lindström (martin.lindstrom@litsec.se)
 * @see SpEntityDescriptorBuilder
 */
public class SpEntityDescriptorFactoryBean extends AbstractEntityDescriptorFactoryBean {

  /** The builder. */
  private SpEntityDescriptorBuilder builder;

  /**
   * Constructor setting up the factory bean with no template. This means that the entire {@code EntityDescriptor}
   * object is created from data assigned in setter methods.
   */
  public SpEntityDescriptorFactoryBean() {
    this.builder = new SpEntityDescriptorBuilder();
  }

  /**
   * Constructor setting up the factory bean with a template {@code EntityDescriptor} that is read from a resource.
   * Users of the bean may now change, add or delete, the elements and attributes of the template object using the
   * setter methods of the bean.
   * 
   * @param resource
   *          the template resource
   * @throws IOException
   *           if the resource can not be read
   * @throws UnmarshallingException
   *           for unmarshalling errors
   * @throws XMLParserException
   *           for XML parsing errors
   */
  public SpEntityDescriptorFactoryBean(Resource resource) throws XMLParserException, UnmarshallingException, IOException {
    this.builder = new SpEntityDescriptorBuilder(resource.getInputStream());
  }

  /**
   * Constructor setting up the factory bean with a template {@code EntityDescriptor}. Users of the bean may now change,
   * add or delete, the elements and attributes of the template object using the setter methods of the bean.
   * 
   * @param template
   *          the template
   * @throws UnmarshallingException
   *           for unmarshalling errors
   * @throws MarshallingException
   *           for marshalling errors
   */
  public SpEntityDescriptorFactoryBean(EntityDescriptor template) throws UnmarshallingException, MarshallingException {
    this.builder = new SpEntityDescriptorBuilder(template);
  }

  /** {@inheritDoc} */
  @Override
  protected AbstractEntityDescriptorBuilder<?> _builder() {
    return this.builder;
  }

  /**
   * @see SpEntityDescriptorBuilder#authnRequestsSigned(Boolean)
   */
  public void setAuthnRequestsSigned(Boolean b) {
    this.builder.authnRequestsSigned(b);
  }

  /**
   * @see SpEntityDescriptorBuilder#wantAssertionsSigned(Boolean)
   */
  public void setWantAssertionsSigned(Boolean b) {
    this.builder.wantAssertionsSigned(b);
  }

  /**
   * @see SpEntityDescriptorBuilder#discoveryResponse(String...)
   */
  public void setDiscoveryResponse(String location) {
    this.builder.discoveryResponse(location);
  }

  /**
   * @see SpEntityDescriptorBuilder#discoveryResponse(String...)
   */
  public void setDiscoveryResponses(List<String> locations) {
    this.builder.discoveryResponse(locations != null ? locations.toArray(new String[] {}) : null);
  }

  /**
   * @see SpEntityDescriptorBuilder#assertionConsumerServicePostLocations(String...)
   */
  public void setAssertionConsumerServicePostLocations(List<String> assertionConsumerServices) {
    this.builder.assertionConsumerServicePostLocations(assertionConsumerServices != null ? assertionConsumerServices.toArray(new String[]{}) : null);
  }
  
  /**
   * @see SpEntityDescriptorBuilder#assertionConsumerServices(List)
   */
  public void setAssertionConsumerServices(List<AssertionConsumerService> assertionConsumerServices) {
    this.builder.assertionConsumerServices(assertionConsumerServices);
  }
  
  public void setAttributeConsumingServices(List<AttributeConsumingService> attributeConsumingServices) {
  }

  public void setAttributeConsumingService(AttributeConsumingService attributeConsumingService) {
    this.setAttributeConsumingServices(Arrays.asList(attributeConsumingService));
  }

}
