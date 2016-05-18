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
package se.litsec.swedisheid.opensaml.saml2.signservice.dss.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectMarshaller;
import org.w3c.dom.Element;

import se.litsec.swedisheid.opensaml.saml2.signservice.dss.SignMessage;

/**
 * Marshaller for the {@code SignMessage} element.
 * 
 * @author Martin Lindström (martin.lindstrom@litsec.se)
 */
public class SignMessageMarshaller extends AbstractSAMLObjectMarshaller {

  @Override
  protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {

    SignMessage signMessage = (SignMessage) xmlObject;

    if (signMessage.isMustShowXSBoolean() != null) {
      domElement.setAttributeNS(null, SignMessage.MUST_SHOW_ATTR_NAME,
        signMessage.isMustShowXSBoolean().getValue().toString());
    }
    if (signMessage.getDisplayEntity() != null) {
      domElement.setAttributeNS(null, SignMessage.DISPLAY_ENTITY_ATTR_NAME, signMessage.getDisplayEntity());
    }
    if (signMessage.getMimeType() != null) {
      domElement.setAttributeNS(null, SignMessage.MIME_TYPE_ATTR_NAME, signMessage.getMimeType());
    }

    this.marshallUnknownAttributes(signMessage, domElement);    
  }

  @Override
  protected void marshallElementContent(XMLObject xmlObject, Element domElement) throws MarshallingException {
    super.marshallElementContent(xmlObject, domElement);
  }

}
