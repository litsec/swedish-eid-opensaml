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
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.saml.common.xml.SAMLConstants;
import org.opensaml.saml.ext.idpdisco.DiscoveryResponse;
import org.opensaml.saml.saml2.metadata.AssertionConsumerService;
import org.opensaml.saml.saml2.metadata.AttributeConsumingService;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml.saml2.metadata.Extensions;
import org.opensaml.saml.saml2.metadata.SPSSODescriptor;
import org.opensaml.saml.saml2.metadata.SSODescriptor;
import org.springframework.core.io.Resource;

import net.shibboleth.utilities.java.support.xml.XMLParserException;
import se.litsec.swedisheid.opensaml.saml2.metadata.MetadataUtils;
import se.litsec.swedisheid.opensaml.utils.SAMLUtils;

/**
 * Factory bean for creating SAML Service Provider {@code EntityDescriptor} objects using setter methods, and optionally
 * a template object.
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
 */
public class SpEntityDescriptorFactoryBean extends AbstractEntityDescriptorFactoryBean {

  /**
   * @see AbstractEntityDescriptorFactoryBean#AbstractEntityDescriptorFactoryBean()
   */
  public SpEntityDescriptorFactoryBean() {
  }

  /**
   * @see AbstractEntityDescriptorFactoryBean#AbstractEntityDescriptorFactoryBean(Resource)
   */
  public SpEntityDescriptorFactoryBean(Resource resource) throws XMLParserException, UnmarshallingException, IOException {
    super(resource);
  }

  /**
   * @see AbstractEntityDescriptorFactoryBean#AbstractEntityDescriptorFactoryBean(EntityDescriptor)
   */
  public SpEntityDescriptorFactoryBean(EntityDescriptor template) throws UnmarshallingException, MarshallingException {
    super(template);
  }

  /**
   * Assigns the {@code AuthnRequestsSigned} attribute of the {@code md:SPSSODescriptor} element.
   * 
   * @param b
   *          boolean (if {@code null}, the attribute is not set)
   */
  public void setAuthnRequestsSigned(Boolean b) {
    ((SPSSODescriptor) this.ssoDescriptor()).setAuthnRequestsSigned(b);
  }

  /**
   * Assigns the {@code WantAssertionsSigned} attribute of the {@code md:SPSSODescriptor} element.
   * 
   * @param b
   */
  public void setWantAssertionsSigned(Boolean b) {
    ((SPSSODescriptor) this.ssoDescriptor()).setWantAssertionsSigned(b);
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
   */
  public void setDiscoveryResponse(List<String> locations) {
    SSODescriptor ssoDescriptor = this.ssoDescriptor();
    if (ssoDescriptor.getExtensions() == null) {
      if (locations == null) {
        return;
      }
      ssoDescriptor.setExtensions(SAMLUtils.createSamlObject(Extensions.class));
    }
    List<DiscoveryResponse> discoveryResponses = MetadataUtils.getMetadataExtensions(ssoDescriptor.getExtensions(),
      DiscoveryResponse.class);
    discoveryResponses.stream().forEach(dr -> ssoDescriptor.getExtensions().getUnknownXMLObjects().remove(dr));
    if (locations == null) {
      if (ssoDescriptor.getExtensions().getUnknownXMLObjects().isEmpty()) {
        ssoDescriptor.setExtensions(null);
      }
      return;
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
  }

  /**
   * Assigns {@code AssertionConsumerService} elements to the {@code md:SPSSODescriptor} element.
   * 
   * @param assertionConsumerServices
   *          the {@code AssertionConsumerService} elements
   * @throws UnmarshallingException
   *           for unmarshalling errors
   * @throws MarshallingException
   *           for marshalling errors
   */
  public void setAssertionConsumerServices(List<AssertionConsumerService> assertionConsumerServices) throws MarshallingException,
      UnmarshallingException {
    SPSSODescriptor ssoDescriptor = (SPSSODescriptor) this.ssoDescriptor();
    ssoDescriptor.getAssertionConsumerServices().clear();
    if (assertionConsumerServices == null) {
      return;
    }
    for (AssertionConsumerService service : assertionConsumerServices) {
      ssoDescriptor.getAssertionConsumerServices().add(XMLObjectSupport.cloneXMLObject(service));
    }
  }

  /**
   * If only one {@code AssertionConsumerService} element is to be installed to the {@code md:SPSSODescriptor} element
   * this method may be used.
   * 
   * @param assertionConsumerService
   *          the {@code AssertionConsumerService} element
   * @throws UnmarshallingException
   *           for unmarshalling errors
   * @throws MarshallingException
   *           for marshalling errors
   * @see #setAssertionConsumerServices(List)
   */
  public void setAssertionConsumerService(AssertionConsumerService assertionConsumerService) throws MarshallingException,
      UnmarshallingException {
    this.setAssertionConsumerServices(Arrays.asList(assertionConsumerService));
  }

  public void setAttributeConsumingServices(List<AttributeConsumingService> attributeConsumingServices) {
  }

  public void setAttributeConsumingService(AttributeConsumingService attributeConsumingService) {
    this.setAttributeConsumingServices(Arrays.asList(attributeConsumingService));
  }

  /** {@inheritDoc} */
  @Override
  protected SSODescriptor ssoDescriptor() {
    if (this.template.getSPSSODescriptor(SAMLConstants.SAML20P_NS) == null) {
      SPSSODescriptor d = SAMLUtils.createSamlObject(SPSSODescriptor.class);
      d.addSupportedProtocol(SAMLConstants.SAML20P_NS);
      this.template.getRoleDescriptors().add(d);
    }
    return this.template.getSPSSODescriptor(SAMLConstants.SAML20P_NS);
  }

  /** {@inheritDoc} */
  @Override
  protected boolean matchingSSODescriptorType(EntityDescriptor descriptor) {
    return this.template.getSPSSODescriptor(SAMLConstants.SAML20P_NS) != null;
  }

}
