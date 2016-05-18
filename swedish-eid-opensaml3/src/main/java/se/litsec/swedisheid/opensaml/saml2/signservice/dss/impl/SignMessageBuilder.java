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

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;

import se.litsec.swedisheid.opensaml.saml2.signservice.dss.SignMessage;

/**
 * Builder class for {@link SignMessage}.
 * 
 * @author Martin Lindström (martin.lindstrom@litsec.se)
 */
public class SignMessageBuilder extends AbstractSAMLObjectBuilder<SignMessage> {

  @Override
  public SignMessage buildObject() {
    return buildObject(SignMessage.DEFAULT_ELEMENT_NAME.getNamespaceURI(),
      SignMessage.DEFAULT_ELEMENT_NAME.getLocalPart(), SignMessage.DEFAULT_ELEMENT_NAME.getPrefix());
  }

  @Override
  public SignMessage buildObject(String namespaceURI, String localName, String namespacePrefix) {
    return new SignMessageImpl(namespaceURI, localName, namespacePrefix);
  }

}
