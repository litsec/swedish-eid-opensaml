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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.litsec.opensaml.saml2.common.response.ResponseProfileValidator;

/**
 * Validator that ensures that a {@code Response} element is valid according to the Swedish eID Framework.
 * 
 * @author Martin Lindström (martin.lindstrom@litsec.se)
 */
public class SwedishEidResponseProfileValidator extends ResponseProfileValidator {
  
  /** Logging instance. */
  private final Logger log = LoggerFactory.getLogger(SwedishEidResponseProfileValidator.class);

  /**
   * Ensures that the {@code InResponseTo} attribute is present.
   */
  @Override
  public ValidationResult validateInResponseTo(Response response, ValidationContext context) {
    if (response.getInResponseTo() == null) {
      context.setValidationFailureMessage("Missing InResponseTo attribute in Response");
      return ValidationResult.INVALID;
    }
    return ValidationResult.VALID;
  }

  /**
   * Ensures that the {@code Destination} attribute is present.
   */
  @Override
  public ValidationResult validateDestination(Response response, ValidationContext context) {
    if (response.getDestination() == null) {
      context.setValidationFailureMessage("Missing Destination attribute in Response");
      return ValidationResult.INVALID;
    }
    return ValidationResult.VALID;
  }

  /**
   * Ensures that the {@code Issuer} element is present.
   */
  @Override
  public ValidationResult validateIssuer(Response response, ValidationContext context) {
    if (response.getIssuer() == null || response.getIssuer().getValue() == null) {
      context.setValidationFailureMessage("Missing Issuer element in Response");
      return ValidationResult.INVALID;
    }
    return ValidationResult.VALID;
  }

  /**
   * Ensures that the {@code Signature} element is present.
   */
  @Override
  public ValidationResult validateSignaturePresent(Response response, ValidationContext context) {
    if (!response.isSigned()) {
      context.setValidationFailureMessage("Response element was not signed");
      return ValidationResult.INVALID;
    }
    return ValidationResult.VALID;
  }

  /**
   * Checks according to {@link ResponseProfileValidator#validateAssertions(Response, ValidationContext)} and extends
   * the check to validate that assertion is encrypted.
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
        if (this.isStrictValidation()) {
          context.setValidationFailureMessage(msg);
          return ValidationResult.INVALID;
        }
        log.warn(msg);
      }
      if (!response.getAssertions().isEmpty()) {
        String msg = "Response contains non encrypted Assertion(s)";
        if (this.isStrictValidation()) {
          context.setValidationFailureMessage(msg);
          return ValidationResult.INVALID;
        }
        log.warn(msg);        
      }
    }
    return ValidationResult.VALID;
  }

}
