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

import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.common.xml.SAMLConstants;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml.saml2.metadata.SPSSODescriptor;
import org.opensaml.saml.saml2.metadata.SSODescriptor;
import org.springframework.core.io.Resource;

import net.shibboleth.utilities.java.support.xml.XMLParserException;
import se.litsec.swedisheid.opensaml.utils.SAMLUtils;

public class SpEntityDescriptorBuilder extends AbstractEntityDescriptorBuilder<SpEntityDescriptorBuilder> {

  public SpEntityDescriptorBuilder() {
    super();
  }

  public SpEntityDescriptorBuilder(Resource resource) throws XMLParserException, UnmarshallingException, IOException {
    super(resource);
  }

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
   * @return the builder
   */
  public SpEntityDescriptorBuilder wantAssertionsSigned(Boolean b) {
    ((SPSSODescriptor) this.ssoDescriptor()).setWantAssertionsSigned(b);
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
