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

import java.util.List;
import java.util.Optional;

import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.core.xml.schema.XSString;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.saml.common.xml.SAMLConstants;
import org.opensaml.saml.ext.idpdisco.DiscoveryResponse;
import org.opensaml.saml.ext.saml2mdattr.EntityAttributes;
import org.opensaml.saml.saml2.core.Attribute;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml.saml2.metadata.Extensions;
import org.opensaml.saml.saml2.metadata.SPSSODescriptor;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.util.Assert;

import se.litsec.swedisheid.opensaml.saml2.attribute.AttributeBuilder;
import se.litsec.swedisheid.opensaml.saml2.attribute.AttributeUtils;
import se.litsec.swedisheid.opensaml.saml2.metadata.MetadataUtils;
import se.litsec.swedisheid.opensaml.saml2.metadata.entitycategory.EntityCategoryMetadataHelper;
import se.litsec.swedisheid.opensaml.utils.InputUtils;
import se.litsec.swedisheid.opensaml.utils.SAMLUtils;

/**
 * A factory bean that builds an {@link EntityDescriptor} instance using the supplied template and parameters.
 * 
 * @author Martin Lindström (martin.lindstrom@litsec.se)
 */
public class TemplateEntityDescriptorFactoryBean extends AbstractFactoryBean<EntityDescriptor> {

  private EntityDescriptor template;

  public TemplateEntityDescriptorFactoryBean() {
    this.template = SAMLUtils.createSamlObject(EntityDescriptor.class);
  }

  public TemplateEntityDescriptorFactoryBean(EntityDescriptor template) throws MarshallingException, UnmarshallingException {
    InputUtils.assertNotNull(template, "template");
    this.template = XMLObjectSupport.cloneXMLObject(template);
  }

  public void setEntityID(String entityID) {
    this.template.setEntityID(entityID);
  }

  public void setEntityCategories(List<String> entityCategories) {
    Optional<EntityAttributes> entityAttributes = MetadataUtils.getEntityAttributes(this.template);

    if (entityCategories == null || entityCategories.isEmpty()) {
      if (entityAttributes.isPresent()) {
        Optional<Attribute> attr = AttributeUtils.getAttribute(
          EntityCategoryMetadataHelper.ENTITY_CATEGORY_ATTRIBUTE_NAME, entityAttributes.get().getAttributes());
        if (attr.isPresent()) {
          entityAttributes.get().getAttributes().remove(attr.get());
        }
        if (entityAttributes.get().getAttributes().isEmpty()) {
          this.template.getExtensions().getUnknownXMLObjects().remove(entityAttributes.get());
          if (this.template.getExtensions().getUnknownXMLObjects().isEmpty()) {
            this.template.setExtensions(null);
          }
        }
      }
    }
    else {
      if (!entityAttributes.isPresent()) {
        if (this.template.getExtensions() == null) {
          this.template.setExtensions(SAMLUtils.createSamlObject(Extensions.class));
        }
        EntityAttributes ea = SAMLUtils.createSamlObject(EntityAttributes.class);
        ea.getAttributes().add(EntityCategoryMetadataHelper.createEntityCategoryAttribute(entityCategories));
        this.template.getExtensions().getUnknownXMLObjects().add(ea);
      }
      else {
        Optional<Attribute> attr = AttributeUtils.getAttribute(
          EntityCategoryMetadataHelper.ENTITY_CATEGORY_ATTRIBUTE_NAME, entityAttributes.get().getAttributes());
        if (attr.isPresent()) {
          attr.get().getAttributeValues().clear();
          
          entityCategories.stream()
            .forEach((e) -> {
              XSString sv = AttributeBuilder.createValueObject(XSString.class);
              sv.setValue(e);
              attr.get().getAttributeValues().add(sv);
            });          
        }
        else {
          entityAttributes.get().getAttributes().add(EntityCategoryMetadataHelper.createEntityCategoryAttribute(entityCategories));
        }
      }
    }
  }

  // TODO: move
  public void setAssuranceCertification(List<String> assuranceCertification) {
  }

  public void setAuthnRequestsSigned(Boolean b) {
    this.getSPSSODescriptor().setAuthnRequestsSigned(b);
  }

  public void setWantAssertionsSigned(Boolean b) {
    this.getSPSSODescriptor().setWantAssertionsSigned(b);
  }

  public void setDiscoveryResponse(List<String> locations) {
    SPSSODescriptor descriptor = this.getSPSSODescriptor();
    if (locations == null || locations.isEmpty()) {
      if (descriptor.getExtensions() != null) {
        List<DiscoveryResponse> drs = MetadataUtils.getMetadataExtensions(descriptor.getExtensions(), DiscoveryResponse.class);
        for (DiscoveryResponse d : drs) {
          descriptor.getExtensions().getUnknownXMLObjects().remove(d);
        }
      }
    }
    else {
      if (descriptor.getExtensions() == null) {
        this.template.setExtensions(SAMLUtils.createSamlObject(Extensions.class));
      }
      else {
        List<DiscoveryResponse> drs = MetadataUtils.getMetadataExtensions(descriptor.getExtensions(), DiscoveryResponse.class);
        for (DiscoveryResponse d : drs) {
          descriptor.getExtensions().getUnknownXMLObjects().remove(d);
        }
      }
      int index = 1;
      for (String location : locations) {
        DiscoveryResponse dr = SAMLUtils.createSamlObject(DiscoveryResponse.class);
        dr.setBinding(SAMLConstants.SAML_IDP_DISCO_NS);
        if (index == 1) {
          dr.setIsDefault(Boolean.TRUE);
        }
        dr.setIndex(index++);        
        dr.setLocation(location);
        descriptor.getExtensions().getUnknownXMLObjects().add(dr);
      }
    }
  }

  private SPSSODescriptor getSPSSODescriptor() {
    if (this.template.getSPSSODescriptor(SAMLConstants.SAML20P_NS) == null) {
      SPSSODescriptor d = SAMLUtils.createSamlObject(SPSSODescriptor.class);
      d.addSupportedProtocol(SAMLConstants.SAML20P_NS);
      this.template.getRoleDescriptors().add(d);
    }
    return this.template.getSPSSODescriptor(SAMLConstants.SAML20P_NS);
  }

  /** {@inheritDoc} */
  @Override
  public Class<?> getObjectType() {
    return EntityDescriptor.class;
  }

  /** {@inheritDoc} */
  @Override
  protected EntityDescriptor createInstance() throws Exception {
    if (this.isSingleton()) {
      return this.template;
    }
    else {
      return XMLObjectSupport.cloneXMLObject(this.template);
    }
  }

  /** {@inheritDoc} */
  @Override
  public void afterPropertiesSet() throws Exception {
    super.afterPropertiesSet();

    Assert.hasText(this.template.getEntityID(), "entityID must be set");
  }

}
