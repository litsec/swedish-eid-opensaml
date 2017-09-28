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
