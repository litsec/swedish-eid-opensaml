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
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.core.xml.schema.XSBooleanValue;
import org.opensaml.saml.common.AbstractSAMLObjectUnmarshaller;
import org.w3c.dom.Attr;

import se.litsec.swedisheid.opensaml.saml2.signservice.dss.EncryptedMessage;
import se.litsec.swedisheid.opensaml.saml2.signservice.dss.Message;
import se.litsec.swedisheid.opensaml.saml2.signservice.dss.SignMessage;

/**
 * Unmarshaller for the {@code SignMessage} element.
 * 
 * @author Martin Lindström (martin.lindstrom@litsec.se)
 */
public class SignMessageUnmarshaller extends AbstractSAMLObjectUnmarshaller {

  @Override
  protected void processChildElement(XMLObject parentSAMLObject, XMLObject childSAMLObject)
      throws UnmarshallingException {

    SignMessage signMessage = (SignMessage) parentSAMLObject;

    if (childSAMLObject instanceof EncryptedMessage) {
      signMessage.setEncryptedMessage((EncryptedMessage) childSAMLObject);
    }
    else if (childSAMLObject instanceof Message) {
      signMessage.setMessage((Message) childSAMLObject);
    }
    else {
      super.processChildElement(parentSAMLObject, childSAMLObject);
    }
  }

  @Override
  protected void processAttribute(XMLObject samlObject, Attr attribute) throws UnmarshallingException {

    SignMessage signMessage = (SignMessage) samlObject;

    if (attribute.getLocalName().equals(SignMessage.MUST_SHOW_ATTR_NAME)) {
      signMessage.setMustShow(XSBooleanValue.valueOf(attribute.getValue()));
    }
    else if (attribute.getLocalName().equals(SignMessage.DISPLAY_ENTITY_ATTR_NAME)) {
      signMessage.setDisplayEntity(attribute.getValue());
    }
    else if (attribute.getLocalName().equals(SignMessage.MIME_TYPE_ATTR_NAME)) {
      signMessage.setMimeType(attribute.getValue());
    }
    else {
      this.processUnknownAttribute(signMessage, attribute);
    }
  }

}
