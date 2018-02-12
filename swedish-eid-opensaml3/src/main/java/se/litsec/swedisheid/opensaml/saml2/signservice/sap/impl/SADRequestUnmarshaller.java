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
package se.litsec.swedisheid.opensaml.saml2.signservice.sap.impl;

import javax.xml.namespace.QName;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.core.xml.schema.XSInteger;
import org.opensaml.core.xml.schema.XSString;
import org.opensaml.saml.common.AbstractSAMLObjectUnmarshaller;

import se.litsec.swedisheid.opensaml.saml2.signservice.sap.RequestParams;
import se.litsec.swedisheid.opensaml.saml2.signservice.sap.SADRequest;
import se.litsec.swedisheid.opensaml.saml2.signservice.sap.SADVersion;

/**
 * Unmarshaller for {@link SADRequest}.
 * 
 * @author Martin Lindström (martin.lindstrom@litsec.se)
 */
public class SADRequestUnmarshaller extends AbstractSAMLObjectUnmarshaller {

  /** {@inheritDoc} */
  @Override
  protected void processChildElement(XMLObject parentSAMLObject, XMLObject childSAMLObject)
      throws UnmarshallingException {

    SADRequest sadRequest = (SADRequest) parentSAMLObject;
    
    final QName signRequestIdQName = new QName(sadRequest.getElementQName().getNamespaceURI(), 
      SADRequest.SIGN_REQUEST_ID_LOCAL_NAME, sadRequest.getElementQName().getPrefix());
    
    final QName docCountQName = new QName(sadRequest.getElementQName().getNamespaceURI(), 
      SADRequest.DOC_COUNT_LOCAL_NAME, sadRequest.getElementQName().getPrefix());
    
    final QName requestedVersionQName = new QName(sadRequest.getElementQName().getNamespaceURI(), 
      SADRequest.REQUESTED_VERSION_LOCAL_NAME, sadRequest.getElementQName().getPrefix());
        
    if ((childSAMLObject instanceof XSString) && signRequestIdQName.equals(childSAMLObject.getElementQName())) {
      if (sadRequest instanceof SADRequestImpl) {
        ((SADRequestImpl) sadRequest).setSignRequestID((XSString) childSAMLObject);
      }
      else {
        sadRequest.setSignRequestID(((XSString) childSAMLObject).getValue());
      }
    }
    else if ((childSAMLObject instanceof XSInteger) && docCountQName.equals(childSAMLObject.getElementQName())) {
      if (sadRequest instanceof SADRequestImpl) {
        ((SADRequestImpl) sadRequest).setDocCount((XSInteger) childSAMLObject);
      }
      else {
        sadRequest.setDocCount(((XSInteger) childSAMLObject).getValue());
      }
    }
    else if ((childSAMLObject instanceof XSString) && requestedVersionQName.equals(childSAMLObject.getElementQName())) {
      if (sadRequest instanceof SADRequestImpl) {
        ((SADRequestImpl) sadRequest).setRequestedVersion((XSString) childSAMLObject);
      }
      else {
        sadRequest.setRequestedVersion(SADVersion.valueOf(((XSString) childSAMLObject).getValue()));
      }
    }
    else if (childSAMLObject instanceof RequestParams) {
      sadRequest.setRequestParams((RequestParams) childSAMLObject);
    }
    else {
      super.processChildElement(parentSAMLObject, childSAMLObject);
    }
  }
    
}
