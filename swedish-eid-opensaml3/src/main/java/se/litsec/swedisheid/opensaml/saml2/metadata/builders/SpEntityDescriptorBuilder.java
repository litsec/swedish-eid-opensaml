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

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.saml.common.xml.SAMLConstants;
import org.opensaml.saml.ext.idpdisco.DiscoveryResponse;
import org.opensaml.saml.saml2.metadata.AssertionConsumerService;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml.saml2.metadata.Extensions;
import org.opensaml.saml.saml2.metadata.SPSSODescriptor;
import org.opensaml.saml.saml2.metadata.SSODescriptor;

import net.shibboleth.utilities.java.support.xml.XMLParserException;
import se.litsec.swedisheid.opensaml.saml2.metadata.MetadataUtils;
import se.litsec.swedisheid.opensaml.utils.SAMLUtils;

/**
 * Builder for constructing metadata ({@code EntityDescriptor}) objects for a Service Provider.
 * 
 * @author Martin Lindström (martin.lindstrom@litsec.se)
 */
public class SpEntityDescriptorBuilder extends AbstractEntityDescriptorBuilder<SpEntityDescriptorBuilder> {

  /**
   * Default constructor.
   */
  public SpEntityDescriptorBuilder() {
    super();
  }

  /**
   * Constructor setting up the builder with a template {@code EntityDescriptor} that is read from an input stream.
   * Users of the bean may now change, add or delete, the elements and attributes of the template object using the
   * assignment methods of the builder.
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
  public SpEntityDescriptorBuilder(InputStream resource) throws XMLParserException, UnmarshallingException, IOException {
    super(resource);
  }

  /**
   * Constructor setting up the builder with a template {@code EntityDescriptor}. Users of the bean may now change, add
   * or delete, the elements and attributes of the template object using the assignment methods of the builder.
   * 
   * @param template
   *          the template
   * @throws UnmarshallingException
   *           for unmarshalling errors
   * @throws MarshallingException
   *           for marshalling errors
   */
  public SpEntityDescriptorBuilder(EntityDescriptor template) throws UnmarshallingException, MarshallingException {
    super(template);
  }

  /** {@inheritDoc} */
  @Override
  protected SpEntityDescriptorBuilder getThis() {
    return this;
  }

  /**
   * Assigns the {@code AuthnRequestsSigned} attribute of the {@code md:SPSSODescriptor} element.
   * 
   * @param b
   *          boolean (if {@code null}, the attribute is not set)
   * @return the builder
   */
  public SpEntityDescriptorBuilder authnRequestsSigned(Boolean b) {
    ((SPSSODescriptor) this.ssoDescriptor()).setAuthnRequestsSigned(b);
    return this;
  }

  /**
   * Assigns the {@code WantAssertionsSigned} attribute of the {@code md:SPSSODescriptor} element.
   * 
   * @param b
   *          boolean (if {@code null}, the attribute is not set)
   * @return the builder
   */
  public SpEntityDescriptorBuilder wantAssertionsSigned(Boolean b) {
    ((SPSSODescriptor) this.ssoDescriptor()).setWantAssertionsSigned(b);
    return this;
  }

  /**
   * Assigns the {@code idpdisco:DiscoveryResponse} elements that are placed as an extensions element of the
   * {@code md:SPSSODescriptor} element.
   * <p>
   * The {@code index} attributes of the {@code idpdisco:DiscoveryResponse} elements are set according the order of the
   * supplied {@code locations} parameter where the first element will be made the default choice.
   * </p>
   * <p>
   * If {@code null} is supplied, the discovery response elements will be cleared from the template entity descriptor
   * (if present).
   * </p>
   * 
   * @param locations
   *          a list of discovery response locations
   * @return the builder
   */
  public SpEntityDescriptorBuilder discoveryResponse(String... locations) {

    SSODescriptor ssoDescriptor = this.ssoDescriptor();
    if (ssoDescriptor.getExtensions() == null) {
      if (locations == null || locations.length == 0) {
        return this;
      }
      ssoDescriptor.setExtensions(SAMLUtils.createSamlObject(Extensions.class));
    }
    List<DiscoveryResponse> discoveryResponses = MetadataUtils.getMetadataExtensions(ssoDescriptor.getExtensions(),
      DiscoveryResponse.class);
    discoveryResponses.stream().forEach(dr -> ssoDescriptor.getExtensions().getUnknownXMLObjects().remove(dr));
    if (locations == null || locations.length == 0) {
      if (ssoDescriptor.getExtensions().getUnknownXMLObjects().isEmpty()) {
        ssoDescriptor.setExtensions(null);
      }
      return this;
    }
    int index = 1;
    for (String location : locations) {
      DiscoveryResponse dr = SAMLUtils.createSamlObject(DiscoveryResponse.class);
      if (index == 1) {
        dr.setIsDefault(true);
      }
      dr.setIndex(index++);
      dr.setLocation(location);
      dr.setBinding(SAMLConstants.SAML_IDP_DISCO_NS);
    }

    return this;
  }

  /**
   * A short-cut method that adds one, or several, {@code AssertionConsumerService} elements that all have the
   * POST-binding. If more than one location is given the index-attributes for each element starts with 0 for the first
   * string and so on. The first location is also made the default.
   * 
   * @param assertionConsumerServices
   *          assertion consumer service locations
   * @return the builder
   * @see #assertionConsumerServices(List)
   */
  public SpEntityDescriptorBuilder assertionConsumerServicePostLocations(String... assertionConsumerServices) {
    if (assertionConsumerServices == null) {
      return this.assertionConsumerServices((List<AssertionConsumerService>) null);
    }
    int index = 0;
    List<AssertionConsumerService> list = new ArrayList<AssertionConsumerService>();
    for (String location : assertionConsumerServices) {
      AssertionConsumerService acs = SAMLUtils.createSamlObject(AssertionConsumerService.class);
      acs.setBinding(SAMLConstants.SAML2_POST_BINDING_URI);
      if (index == 0) {
        acs.setIsDefault(true);
      }
      acs.setIndex(index++);
      acs.setLocation(location);
      list.add(acs);
    }
    return this.assertionConsumerServices(list);
  }

  /**
   * Adds {@code AssertionConsumerService} element(s).
   * 
   * @param assertionConsumerServices
   *          assertion consumer service(s)
   * @return the builder
   * @see #assertionConsumerServices(List)
   */
  public SpEntityDescriptorBuilder assertionConsumerServices(AssertionConsumerService... assertionConsumerServices) {
    return this.assertionConsumerServices(assertionConsumerServices != null ? Arrays.asList(assertionConsumerServices)
        : (List<AssertionConsumerService>) null);
  }

  /**
   * Adds {@code AssertionConsumerService} element(s).
   * 
   * @param assertionConsumerServices
   *          a list of {@code AssertionConsumerService} objects
   * @return the builder
   * @see #assertionConsumerServices(AssertionConsumerService...)
   */
  public SpEntityDescriptorBuilder assertionConsumerServices(List<AssertionConsumerService> assertionConsumerServices) {
    SPSSODescriptor ssoDescriptor = (SPSSODescriptor) this.ssoDescriptor();
    ssoDescriptor.getAssertionConsumerServices().clear();
    if (assertionConsumerServices == null) {
      return this;
    }
    for (AssertionConsumerService acs : assertionConsumerServices) {
      try {
        ssoDescriptor.getAssertionConsumerServices().add(XMLObjectSupport.cloneXMLObject(acs));
      }
      catch (MarshallingException | UnmarshallingException e) {
        throw new RuntimeException(e);
      }
    }
    return this;
  }

  /** {@inheritDoc} */
  @Override
  protected SSODescriptor ssoDescriptor() {
    if (this.object().getSPSSODescriptor(SAMLConstants.SAML20P_NS) == null) {
      SPSSODescriptor d = SAMLUtils.createSamlObject(SPSSODescriptor.class);
      d.addSupportedProtocol(SAMLConstants.SAML20P_NS);
      this.object().getRoleDescriptors().add(d);
    }
    return this.object().getSPSSODescriptor(SAMLConstants.SAML20P_NS);
  }

  /** {@inheritDoc} */
  @Override
  protected boolean matchingSSODescriptorType(EntityDescriptor descriptor) {
    return this.object().getSPSSODescriptor(SAMLConstants.SAML20P_NS) != null;
  }

}
