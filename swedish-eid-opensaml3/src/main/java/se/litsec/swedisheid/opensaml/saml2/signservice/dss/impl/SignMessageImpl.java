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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.schema.XSBooleanValue;
import org.opensaml.core.xml.util.AttributeMap;
import org.opensaml.saml.common.AbstractSAMLObject;

import se.litsec.swedisheid.opensaml.saml2.signservice.dss.EncryptedMessage;
import se.litsec.swedisheid.opensaml.saml2.signservice.dss.Message;
import se.litsec.swedisheid.opensaml.saml2.signservice.dss.SignMessage;
import se.litsec.swedisheid.opensaml.saml2.signservice.dss.SignMessageMimeTypeEnum;

/**
 * Implementation class for the {@link SignMessage} interface.
 * 
 * @author Martin Lindström (martin.lindstrom@litsec.se)
 */
public class SignMessageImpl extends AbstractSAMLObject implements SignMessage {

  /** The MustShow attribute. */
  private XSBooleanValue mustShow;

  /** The DisplayEntity attribute. */
  private String displayEntity;

  /** The MimeType attribute. */
  private String mimeType;

  /** The message element. */
  private Message message;

  /** The encryptedMessage element. */
  private EncryptedMessage encryptedMessage;
  
  /** "anyAttribute" attributes */
  private final AttributeMap unknownAttributes;

  /**
   * Constructor creating an SignMessage object given the namespace URI, local element name and namespace prefix.
   * 
   * @param namespaceURI
   *          the namespace URI.
   * @param elementLocalName
   *          the element local name.
   * @param namespacePrefix
   *          the name space prefix.
   */
  public SignMessageImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
    super(namespaceURI, elementLocalName, namespacePrefix);
    this.unknownAttributes = new AttributeMap(this);
  }

  @Override
  public List<XMLObject> getOrderedChildren() {
    ArrayList<XMLObject> children = new ArrayList<XMLObject>();

    if (this.message != null) {
      children.add(this.message);
    }
    if (this.encryptedMessage != null) {
      children.add(this.encryptedMessage);
    }
    if (children.size() == 0) {
      return null;
    }
    return Collections.unmodifiableList(children);
  }

  @Override
  public Boolean isMustShow() {
    if (this.mustShow != null) {
      return this.mustShow.getValue();
    }
    return Boolean.FALSE;
  }

  @Override
  public XSBooleanValue isMustShowXSBoolean() {
    return this.mustShow;
  }

  @Override
  public void setMustShow(Boolean mustShow) {
    if (mustShow != null) {
      this.mustShow = this.prepareForAssignment(this.mustShow, new XSBooleanValue(mustShow, false));
    }
    else {
      this.mustShow = prepareForAssignment(this.mustShow, null);
    }
  }

  @Override
  public void setMustShow(XSBooleanValue mustShow) {
    this.mustShow = prepareForAssignment(this.mustShow, mustShow);
  }

  @Override
  public String getDisplayEntity() {
    return this.displayEntity;
  }

  @Override
  public void setDisplayEntity(String displayEntity) {
    this.displayEntity = this.prepareForAssignment(this.displayEntity, displayEntity);
  }

  @Override
  public String getMimeType() {
    return this.mimeType;
  }

  @Override
  public SignMessageMimeTypeEnum getMimeTypeEnum() {
    return SignMessageMimeTypeEnum.parse(this.mimeType);
  }

  @Override
  public void setMimeType(String mimeType) {
    this.mimeType = this.prepareForAssignment(this.mimeType, mimeType);
  }

  @Override
  public void setMimeType(SignMessageMimeTypeEnum mimeType) {
    if (mimeType != null) {
      this.mimeType = this.prepareForAssignment(this.mimeType, mimeType.getMimeType());
    }
  }

  @Override
  public Message getMessage() {
    return this.message;
  }

  @Override
  public void setMessage(Message message) {
    this.message = this.prepareForAssignment(this.message, message);
  }

  @Override
  public EncryptedMessage getEncryptedMessage() {
    return this.encryptedMessage;
  }

  @Override
  public void setEncryptedMessage(EncryptedMessage encryptedMessage) {
    this.encryptedMessage = this.prepareForAssignment(this.encryptedMessage, encryptedMessage);
  }

  @Override
  public AttributeMap getUnknownAttributes() {
    return this.unknownAttributes;
  }

}
