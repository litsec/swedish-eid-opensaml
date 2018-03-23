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

import org.opensaml.saml.common.assertion.AssertionValidationException;
import org.opensaml.saml.common.assertion.ValidationContext;
import org.opensaml.saml.common.assertion.ValidationResult;
import org.opensaml.saml.saml2.assertion.SAML2AssertionValidationParameters;
import org.opensaml.saml.saml2.assertion.impl.AbstractSubjectConfirmationValidator;
import org.opensaml.saml.saml2.core.Assertion;
import org.opensaml.saml.saml2.core.AuthnRequest;
import org.opensaml.saml.saml2.core.SubjectConfirmation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.shibboleth.utilities.java.support.primitive.StringSupport;
import se.litsec.opensaml.common.validation.AbstractObjectValidator;
import se.litsec.opensaml.common.validation.CoreValidatorParameters;

/**
 * A subject confirmation validator compliant with the Swedish eID Framework.
 * 
 * @author Martin Lindström (martin.lindstrom@litsec.se)
 */
public class SwedishEidSubjectConfirmationValidator extends AbstractSubjectConfirmationValidator {

  /** Class logger. */
  private final Logger log = LoggerFactory.getLogger(SwedishEidSubjectConfirmationValidator.class);

  /**
   * Returns {@link SubjectConfirmation#METHOD_BEARER}.
   * 
   * @return the bearer method
   */
  @Override
  public String getServicedMethod() {
    return SubjectConfirmation.METHOD_BEARER;
  }

  /**
   * Extends the validator with checks for the Swedish eID Framework.
   */
  @Override
  protected ValidationResult doValidate(SubjectConfirmation confirmation, Assertion assertion, ValidationContext context)
      throws AssertionValidationException {

    if (confirmation.getSubjectConfirmationData() == null) {
      final String msg = String.format(
        "SubjectConfirmationData is missing from Subject/@SubjectConfirmation for method '%s' for Assertion '%s'",
        this.getServicedMethod(), assertion.getID());
      context.setValidationFailureMessage(msg);
      return ValidationResult.INVALID;
    }
    
    this.validateInResponseTo(confirmation, assertion, context);
    
    // NotOnOrAfter is required according to the Swedish eID Framework. The base implementation only
    // checks it if present.
    //
    if (confirmation.getSubjectConfirmationData().getNotOnOrAfter() == null) {
      final String msg = String.format(
        "NotOnOrAfter attribute is missing from Subject/@SubjectConfirmationData for method '%s' for Assertion '%s'",
        this.getServicedMethod(), assertion.getID());
      context.setValidationFailureMessage(msg);
      return ValidationResult.INVALID;
    }
    
    // Recipient is required according to the Swedish eID Framework. The base implementation only
    // checks it if present.
    //
    if (confirmation.getSubjectConfirmationData().getRecipient() == null) {
      final String msg = String.format(
        "Recipient attribute is missing from Subject/@SubjectConfirmationData for method '%s' for Assertion '%s'",
        this.getServicedMethod(), assertion.getID());
      context.setValidationFailureMessage(msg);
      return ValidationResult.INVALID;
    }

    return ValidationResult.VALID;
  }

  /**
   * Ensures that the {@code InResponseTo} attribute is present and that it matches the ID of the {@code AuthnRequest}.
   * The ID is found in the {@code context} parameter under the key {@link CoreValidatorParameters#AUTHN_REQUEST_ID} or
   * from the object stored under {@link CoreValidatorParameters#AUTHN_REQUEST}.
   * 
   * @param confirmation
   *          the subject confirmation element
   * @param assertion
   *          the assertion
   * @param context
   *          the validation context
   * @return validation result
   */
  protected ValidationResult validateInResponseTo(SubjectConfirmation confirmation, Assertion assertion, ValidationContext context) {

    if (confirmation.getSubjectConfirmationData().getInResponseTo() == null) {
      final String msg = String.format(
        "InResponseTo attribute is missing from Subject/@SubjectConfirmationData for method '%s' for Assertion '%s'",
        this.getServicedMethod(), assertion.getID());
      context.setValidationFailureMessage(msg);
      return ValidationResult.INVALID;
    }

    final String inResponseTo = confirmation.getSubjectConfirmationData().getInResponseTo();

    String expectedInResponseTo = (String) context.getStaticParameters().get(CoreValidatorParameters.AUTHN_REQUEST_ID);
    if (expectedInResponseTo == null) {
      AuthnRequest authnRequest = (AuthnRequest) context.getStaticParameters().get(CoreValidatorParameters.AUTHN_REQUEST);
      if (authnRequest != null) {
        expectedInResponseTo = authnRequest.getID();
      }
    }
    if (expectedInResponseTo != null) {
      if (!inResponseTo.equals(expectedInResponseTo)) {
        final String msg = String.format(
          "Mismatching InResponseTo attribute is missing from Subject/@SubjectConfirmationData for method '%s' for Assertion '%s'." +
              " Expected: '%s', was: '%s'", this.getServicedMethod(), assertion.getID(), expectedInResponseTo, inResponseTo);
        context.setValidationFailureMessage(msg);
        return ValidationResult.INVALID;
      }
    }
    else {
      final String msg = String.format("Could not validate InResponseTo attribute is missing from " +
          "Subject/@SubjectConfirmationData for method '%s' for Assertion '%s' (no AuthnRequest ID available)",
        this.getServicedMethod(), assertion.getID());
      context.setValidationFailureMessage(msg);
      return ValidationResult.INDETERMINATE;
    }

    return ValidationResult.VALID;
  }

  /**
   * Overrides the default implementation.
   */
  @Override
  protected ValidationResult validateAddress(SubjectConfirmation confirmation, Assertion assertion, ValidationContext context)
      throws AssertionValidationException {

    String address = StringSupport.trimOrNull(confirmation.getSubjectConfirmationData().getAddress());
    if (address == null) {
      final String msg = String.format("SubjectConfirmationData of Assertion '%s' is missing Address attribute", assertion.getID());
      if (AbstractObjectValidator.isStrictValidation(context)) {
        context.setValidationFailureMessage(msg);
        return ValidationResult.INVALID;
      }
      else {
        log.warn(msg);
      }
    }
    
    if (!AbstractObjectValidator.isStrictValidation(context) && context.getStaticParameters().get(SAML2AssertionValidationParameters.SC_VALID_ADDRESSES) == null) {
      log.info("Address check skipped for SubjectConfirmationData - no indata supplied");
      return ValidationResult.VALID;
    }

    ValidationResult result = super.validateAddress(confirmation, assertion, context);
    if (result != ValidationResult.VALID && !AbstractObjectValidator.isStrictValidation(context)) {
      final String msg = context.getValidationFailureMessage();
      log.warn(msg);
      context.setValidationFailureMessage(null);
      return ValidationResult.VALID;
    }
    return result;
  }

}
