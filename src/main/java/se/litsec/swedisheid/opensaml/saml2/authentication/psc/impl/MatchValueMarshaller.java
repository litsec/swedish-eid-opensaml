/*
 * Copyright 2016-2021 Litsec AB
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

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.schema.impl.XSStringMarshaller;
import org.w3c.dom.Element;

import se.litsec.swedisheid.opensaml.saml2.authentication.psc.MatchValue;

/**
 * A marshaller for {@link MatchValue}.
 * 
 * @author Martin Lindström (martin.lindstrom@litsec.se)
 */
public class MatchValueMarshaller extends XSStringMarshaller {

  /** {@inheritDoc} */
  @Override
  protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
    
    MatchValue mv = (MatchValue) xmlObject;

    if (mv.getNameFormat() != null) {
      domElement.setAttributeNS(null, MatchValue.NAME_FORMAT_ATTR_NAME, mv.getNameFormat());
    }
    if (mv.getName() != null) {
      domElement.setAttributeNS(null, MatchValue.NAME_ATTR_NAME, mv.getName());
    }
    this.marshallUnknownAttributes(mv, domElement);
  }

}
