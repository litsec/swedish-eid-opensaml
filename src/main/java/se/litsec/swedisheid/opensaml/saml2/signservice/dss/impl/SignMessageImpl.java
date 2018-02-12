/*
 * Copyright 2016-2018 Litsec AB
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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

  /** {@inheritDoc} */
  @Override
  public List<XMLObject> getOrderedChildren() {
    ArrayList<XMLObject> children = new ArrayList<>();

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

  /** {@inheritDoc} */
  @Override
  public Boolean isMustShow() {
    if (this.mustShow != null) {
      return this.mustShow.getValue();
    }
    return Boolean.FALSE;
  }

  /** {@inheritDoc} */
  @Override
  public XSBooleanValue isMustShowXSBoolean() {
    return this.mustShow;
  }

  /** {@inheritDoc} */
  @Override
  public void setMustShow(Boolean mustShow) {
    if (mustShow != null) {
      this.mustShow = this.prepareForAssignment(this.mustShow, new XSBooleanValue(mustShow, false));
    }
    else {
      this.mustShow = prepareForAssignment(this.mustShow, null);
    }
  }

  /** {@inheritDoc} */
  @Override
  public void setMustShow(XSBooleanValue mustShow) {
    this.mustShow = prepareForAssignment(this.mustShow, mustShow);
  }

  /** {@inheritDoc} */
  @Override
  public String getDisplayEntity() {
    return this.displayEntity;
  }

  /** {@inheritDoc} */
  @Override
  public void setDisplayEntity(String displayEntity) {
    this.displayEntity = this.prepareForAssignment(this.displayEntity, displayEntity);
  }

  /** {@inheritDoc} */
  @Override
  public String getMimeType() {
    return this.mimeType;
  }

  /** {@inheritDoc} */
  @Override
  public SignMessageMimeTypeEnum getMimeTypeEnum() {
    return SignMessageMimeTypeEnum.parse(this.mimeType);
  }

  /** {@inheritDoc} */
  @Override
  public void setMimeType(String mimeType) {
    this.mimeType = this.prepareForAssignment(this.mimeType, mimeType);
  }

  /** {@inheritDoc} */
  @Override
  public void setMimeType(SignMessageMimeTypeEnum mimeType) {
    if (mimeType != null) {
      this.mimeType = this.prepareForAssignment(this.mimeType, mimeType.getMimeType());
    }
  }

  /** {@inheritDoc} */
  @Override
  public Message getMessage() {
    return this.message;
  }

  /** {@inheritDoc} */
  @Override
  public void setMessage(Message message) {
    this.message = this.prepareForAssignment(this.message, message);
  }

  /** {@inheritDoc} */
  @Override
  public EncryptedMessage getEncryptedMessage() {
    return this.encryptedMessage;
  }

  /** {@inheritDoc} */
  @Override
  public void setEncryptedMessage(EncryptedMessage encryptedMessage) {
    this.encryptedMessage = this.prepareForAssignment(this.encryptedMessage, encryptedMessage);
  }

  /** {@inheritDoc} */
  @Override
  public AttributeMap getUnknownAttributes() {
    return this.unknownAttributes;
  }

}
