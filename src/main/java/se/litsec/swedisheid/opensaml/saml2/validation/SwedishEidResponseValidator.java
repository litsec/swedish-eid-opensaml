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
package se.litsec.swedisheid.opensaml.saml2.validation;

import org.opensaml.saml.common.assertion.ValidationContext;
import org.opensaml.saml.common.assertion.ValidationResult;
import org.opensaml.saml.saml2.core.Response;
import org.opensaml.saml.saml2.core.StatusCode;
import org.opensaml.xmlsec.signature.support.SignaturePrevalidator;
import org.opensaml.xmlsec.signature.support.SignatureTrustEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.litsec.opensaml.common.validation.CoreValidatorParameters;
import se.litsec.opensaml.saml2.common.response.ResponseValidator;

/**
 * Extends the default response validator with requirements for the Swedish eID Framework.
 * <p>
 * Supports the following {@link ValidationContext} static parameters as described in {@link ResponseValidator}.
 * </p>
 * 
 * @author Martin Lindström (martin.lindstrom@litsec.se)
 */
public class SwedishEidResponseValidator extends ResponseValidator {

  /** Class logger. */
  private final Logger log = LoggerFactory.getLogger(SwedishEidResponseValidator.class);

  /**
   * Constructor.
   * 
   * @param trustEngine
   *          the trust used to validate the object's signature
   * @param signaturePrevalidator
   *          the signature pre-validator used to pre-validate the object's signature
   * @throws IllegalArgumentException
   *           if {@code null} values are supplied
   */
  public SwedishEidResponseValidator(SignatureTrustEngine trustEngine, SignaturePrevalidator signaturePrevalidator)
      throws IllegalArgumentException {
    super(trustEngine, signaturePrevalidator);
    if (trustEngine == null) {
      throw new IllegalArgumentException("trustEngine must not be null");
    }
    if (signaturePrevalidator == null) {
      throw new IllegalArgumentException("signaturePrevalidator must not be null");
    }
  }

  /**
   * Overrides the default signature validation by enforcing signature validation because a Response message MUST be
   * signed according to the Swedish eID Framework.
   */
  @Override
  protected ValidationResult validateSignature(Response token, ValidationContext context) {

    Boolean signatureRequired = (Boolean) context.getStaticParameters().get(CoreValidatorParameters.SIGNATURE_REQUIRED);
    if (signatureRequired != null && !signatureRequired.booleanValue()) {
      log.warn("The flag CoreValidatorParameters.SIGNATURE_REQUIRED is false - signature "
          + "validation MUST be performed according to the Swedish eID Framework - Setting flag to true");
    }

    // Validate params and requirements.
    if (!token.isSigned()) {
      context.setValidationFailureMessage(String.format("%s was required to be signed, but was not", this.getObjectName()));
      return ValidationResult.INVALID;
    }
    return this.performSignatureValidation(token, context);
  }

  /**
   * Checks according to {@link ResponseValidator#validateAssertions(Response, ValidationContext)} and extends the check
   * to validate that assertion is encrypted.
   */
  @Override
  public ValidationResult validateAssertions(Response response, ValidationContext context) {
    ValidationResult result = super.validateAssertions(response, context);
    if (!result.equals(ValidationResult.VALID)) {
      return result;
    }
    if (StatusCode.SUCCESS.equals(response.getStatus().getStatusCode().getValue())) {
      if (response.getEncryptedAssertions().isEmpty()) {
        context.setValidationFailureMessage("Response does not contain EncryptedAssertion");
        return ValidationResult.INVALID;
      }
      if (response.getEncryptedAssertions().size() > 1) {
        String msg = "Response contains more than one EncryptedAssertion";
        if (isStrictValidation(context)) {
          context.setValidationFailureMessage(msg);
          return ValidationResult.INVALID;
        }
        log.warn(msg);
      }
      if (!response.getAssertions().isEmpty()) {
        String msg = "Response contains non encrypted Assertion(s)";
        if (isStrictValidation(context)) {
          context.setValidationFailureMessage(msg);
          return ValidationResult.INVALID;
        }
        log.warn(msg);
      }
    }
    return ValidationResult.VALID;
  }

}