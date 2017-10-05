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
package se.litsec.swedisheid.opensaml.saml2.signservice.dss;

import javax.xml.namespace.QName;

import org.opensaml.core.xml.schema.XSBase64Binary;

/**
 * XMLObject representing the {@code Message} element that is a child to {@link SignMessage}.
 * 
 * @author Martin Lindström (martin.lindstrom@litsec.se)
 */
public interface Message extends XSBase64Binary {
  
  /** Element local name. */
  String DEFAULT_ELEMENT_LOCAL_NAME = "Message";

  /** Default element name. */
  QName DEFAULT_ELEMENT_NAME = new QName(DssExtensionsConstants.SWEID_DSS_EXT_NS,
    DEFAULT_ELEMENT_LOCAL_NAME, DssExtensionsConstants.SWEID_DSS_EXT_PREFIX);

  /**
   * Returns string content of the {@code Message} element, i.e., its Base64 decoded form.
   * 
   * @return the message content
   */
  String getContent();
  
  /**
   * Assigns the {@code Message} element by assigning the text that it should hold. The method will Base64 encode the
   * text.
   * 
   * @param messageContent
   *          content of the {@code Message} element
   */
  void setContent(String messageContent);  
  
}
