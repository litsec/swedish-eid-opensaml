/*
 * Copyright 2016-2019 Litsec AB
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
package se.litsec.swedisheid.opensaml.saml2.authentication.psc.impl;

import org.opensaml.core.xml.schema.impl.XSStringImpl;
import org.opensaml.core.xml.util.AttributeMap;

import se.litsec.swedisheid.opensaml.saml2.authentication.psc.MatchValue;

/**
 * Implementation class for {@link MatchValue}.
 * 
 * @author Martin Lindström (martin.lindstrom@litsec.se)
 */
public class MatchValueImpl extends XSStringImpl implements MatchValue {
  
  /** The NameFormat attribute. */
  private String nameFormat;
  
  /** The Name attribute. */
  private String name;
  
  /** "anyAttribute" attributes */
  private final AttributeMap unknownAttributes;

  /**
   * Constructor.
   * 
   * @param namespaceURI
   *          the namespace the element is in
   * @param elementLocalName
   *          the local name of the XML element this Object represents
   * @param namespacePrefix
   *          the prefix for the given namespace
   */  
  public MatchValueImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
    super(namespaceURI, elementLocalName, namespacePrefix);
    this.unknownAttributes = new AttributeMap(this);
  }

  /** {@inheritDoc} */
  @Override
  public AttributeMap getUnknownAttributes() {
    return this.unknownAttributes;
  }

  /** {@inheritDoc} */
  @Override
  public String getNameFormat() {
    return this.nameFormat;
  }

  /** {@inheritDoc} */
  @Override
  public void setNameFormat(String nameFormat) {
    this.nameFormat = this.prepareForAssignment(this.nameFormat, nameFormat);
  }

  /** {@inheritDoc} */
  @Override
  public String getName() {
    return this.name;
  }

  /** {@inheritDoc} */
  @Override
  public void setName(String name) {
    this.name = this.prepareForAssignment(this.name, name);
  }

}
