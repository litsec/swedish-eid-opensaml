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

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.opensaml.core.xml.schema.impl.XSBase64BinaryImpl;

import se.litsec.swedisheid.opensaml.saml2.signservice.dss.Message;

/**
 * Implementation of the {@link Message} interface.
 * 
 * @author Martin Lindström (martin.lindstrom@litsec.se)
 */
public class MessageImpl extends XSBase64BinaryImpl implements Message {

  /**
   * Constructor creating an Message object given the namespace URI, local element name and namespace prefix.
   * 
   * @param namespaceURI
   *          the namespace URI.
   * @param elementLocalName
   *          the element local name.
   * @param namespacePrefix
   *          the name space prefix.
   */  
  protected MessageImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
    super(namespaceURI, elementLocalName, namespacePrefix);
  }

  @Override
  public String getContent() {
    return this.getValue() != null ? new String(Base64.getDecoder().decode(this.getValue()), StandardCharsets.UTF_8) : null;
  }

  @Override
  public void setContent(String messageContent) {
    if (messageContent != null) {
      this.setValue(Base64.getEncoder().encodeToString(messageContent.getBytes(StandardCharsets.UTF_8)));
    }
    else {
      this.setValue(null);
    }

  }

}
