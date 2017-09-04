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

import org.opensaml.core.xml.AttributeExtensibleXMLObject;
import org.opensaml.core.xml.schema.XSBooleanValue;
import org.opensaml.saml.common.SAMLObject;

/**
 * Definition of the SignMessage type.
 * <p>
 * The {@code <SignMessage>} element holds a message to the signer with information about what is being signed. The sign
 * message is provided either in plain text using the {@code <Message>} child element or as an encrypted message using
 * the {@code <EncryptedMessage>} child element. This element's <b>SignMessageType</b> complex type includes the
 * following attributes and elements:
 * </p>
 * <dl>
 * <dt>{@code MustShow} [Optional] (Default "false")</dt>
 * <dd>When this attribute is set to true then the requested signature MUST NOT be created unless this message has been
 * displayed and accepted by the signer. The default is false.</dd>
 * <dt>{@code DisplayEntity} [Optional]</dt>
 * <dd>The EntityID of the entity responsible for displaying the sign message to the signer. When the sign message is
 * encrypted, then this entity is also the holder of the private decryption key necessary to decrypt the sign message.
 * </dd>
 * <dt>{@code MimeType} [Optional] (Default "text")</dt>
 * <dd>The mime type defining the message format. This is an enumeration of the valid attribute values text (plain
 * text), text/html (html) or text/markdown (markdown). This specification does not specify any particular restrictions
 * on the provided message but it is RECOMMENDED that sign message content is restricted to a limited set of valid tags
 * and attributes, and that the display entity performs filtering to enforce these restrictions before displaying the
 * message. The means through which parties agree on such restrictions are outside the scope of this specification, but
 * one valid option to communicate such restrictions could be through federation metadata.</dd>
 * <dt>{@code <Message>} [Choice]</dt>
 * <dd>The base64 encoded sign message in unencrypted form. The message MUST be encoded using UTF-8.</dd>
 * <dt>{@code <EncryptedMessage>} [Choice]</dt>
 * <dd>An encrypted {@code <Message>} element. Either a {@code <Message>} or an {@code <EncryptedMessage>} element MUST
 * be present.</dd>
 * </dl>
 * 
 * The following schema fragment defines the {@code <SignMessage>} element and the SignMessageType complex type:
 * 
 * <pre>{@code
 * <xs:complexType name="SignMessageType">
 *   <xs:choice>
 *     <xs:element ref="csig:Message"/>
 *     <xs:element ref="csig:EncryptedMessage"/>
 *   </xs:choice>
 *   <xs:attribute name="MustShow" type="xs:boolean" default="false"/>
 *   <xs:attribute name="DisplayEntity" type="xs:anyURI"/>
 *   <xs:attribute name="MimeType" default="text">
 *     <xs:simpleType>
 *       <xs:restriction base="xs:string">
 *         <xs:enumeration value="text/html"/>
 *         <xs:enumeration value="text"/>
 *         <xs:enumeration value="text/markdown"/>
 *       </xs:restriction>
 *     </xs:simpleType>
 *   </xs:attribute>
 *   <xs:anyAttribute namespace="##other" processContents="lax"/>
 * </xs:complexType>
 * 
 * <xs:element name="Message" type="xs:base64Binary"/>
 * <xs:element name="EncryptedMessage" type="saml:EncryptedElementType"/>}
 * </pre>
 * <p>
 * See "DSS Extension for Federated Central Signing Services - Version 1.1".
 * </p>
 * 
 * @author Martin Lindström (martin.lindstrom@litsec.se)
 */
public interface SignMessage extends SAMLObject, AttributeExtensibleXMLObject {

  /** Name of the element. */
  public static final String DEFAULT_ELEMENT_LOCAL_NAME = "SignMessage";

  /** Default element name. */
  public static final QName DEFAULT_ELEMENT_NAME = new QName(DssExtensionsConstants.SWEID_DSS_EXT_NS, DEFAULT_ELEMENT_LOCAL_NAME,
    DssExtensionsConstants.SWEID_DSS_EXT_PREFIX);

  /** Local name of the type */
  public final static String TYPE_LOCAL_NAME = "SignMessageType";

  /** Attribute label for the MustShow attribute. */
  public static final String MUST_SHOW_ATTR_NAME = "MustShow";

  /** Attribute label for the DisplayEntity attribute. */
  public static final String DISPLAY_ENTITY_ATTR_NAME = "DisplayEntity";

  /** Attribute label for the MimeType attribute. */
  public static final String MIME_TYPE_ATTR_NAME = "MimeType";

  /**
   * Returns the value of the {@code MustShow} attribute.
   * 
   * @return the {@code MustShow} attribute
   */
  Boolean isMustShow();

  /**
   * Returns the {@code MustShow} attribute as a {@code XSBooleanValue}.
   * 
   * @return the {@code MustShow} attribute
   * @see #isMustShow()
   */
  XSBooleanValue isMustShowXSBoolean();

  /**
   * Assigns the value of the {@code MustShow} attribute.
   * 
   * @param mustShow
   *          the value to assign
   */
  void setMustShow(Boolean mustShow);

  /**
   * Assigns the value of the {@code MustShow} attribute.
   * 
   * @param mustShow
   *          the value to assign
   * @see #setMustShow(Boolean)
   */
  void setMustShow(XSBooleanValue mustShow);

  /**
   * Returns the value of the {@code DisplayEntity} attribute.
   * 
   * @return the {@code DisplayEntity} attribute
   */
  String getDisplayEntity();

  /**
   * Assigns the value for the {@code DisplayEntity} attribute.
   * 
   * @param displayEntity
   *          the entityID to assign
   */
  void setDisplayEntity(String displayEntity);

  /**
   * Returns the value of the {@code MimeType} attribute.
   * 
   * @return the {@code MimeType} attribute
   */
  String getMimeType();

  /**
   * Returns the value of the {@code MimeType} attribute as an enum.
   * 
   * @return the {@code MimeType} attribute
   */
  SignMessageMimeTypeEnum getMimeTypeEnum();

  /**
   * Assigns the {@code MimeType} attribute.
   * 
   * @param mimeType
   *          the mime type to assign
   */
  void setMimeType(String mimeType);

  /**
   * Assigns the {@code MimeType} attribute.
   * 
   * @param mimeType
   *          the mime type as an enum to assign
   */
  void setMimeType(SignMessageMimeTypeEnum mimeType);

  /**
   * Returns the {@code Message} element.
   * 
   * @return the {@code Message} element
   */
  Message getMessage();

  /**
   * Assigns the {@code Message} element.
   * 
   * @param message
   *          the message to assign
   */
  void setMessage(Message message);

  /**
   * Returns the {@code EncryptedMessage} element.
   * 
   * @return the {@code EncryptedMessage} element
   */
  EncryptedMessage getEncryptedMessage();

  /**
   * Assigns the {@code EncryptedMessage} element.
   * 
   * @param encryptedMessage
   *          the {@code EncryptedMessage} element to assign
   */
  void setEncryptedMessage(EncryptedMessage encryptedMessage);

}
