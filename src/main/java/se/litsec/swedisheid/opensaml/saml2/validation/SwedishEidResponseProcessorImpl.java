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
package se.litsec.swedisheid.opensaml.saml2.validation;

import org.opensaml.xmlsec.signature.support.SignaturePrevalidator;
import org.opensaml.xmlsec.signature.support.SignatureTrustEngine;

import se.litsec.opensaml.saml2.common.assertion.AssertionValidator;
import se.litsec.opensaml.saml2.common.response.ResponseProcessor;
import se.litsec.opensaml.saml2.common.response.ResponseProcessorImpl;
import se.litsec.opensaml.saml2.common.response.ResponseValidator;

/**
 * A {@link ResponseProcessor} implementation that uses validators for the Swedish eID Framework.
 * 
 * @author Martin Lindström (martin.lindstrom@litsec.se)
 */
public class SwedishEidResponseProcessorImpl extends ResponseProcessorImpl {

  /** {@inheritDoc} */
  @Override
  protected ResponseValidator createResponseValidator(final SignatureTrustEngine signatureTrustEngine,
      final SignaturePrevalidator signatureProfileValidator) {
    return new SwedishEidResponseValidator(signatureTrustEngine, signatureProfileValidator);
  }

  /** {@inheritDoc} */
  @Override
  protected AssertionValidator createAssertionValidator(final SignatureTrustEngine signatureTrustEngine,
      final SignaturePrevalidator signatureProfileValidator) {
    return new SwedishEidAssertionValidator(signatureTrustEngine, signatureProfileValidator);
  }
  
}
