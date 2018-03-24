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

import java.net.InetAddress;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.opensaml.saml.common.assertion.ValidationContext;
import org.opensaml.saml.common.assertion.ValidationResult;
import org.opensaml.saml.saml1.core.AttributeStatement;
import org.opensaml.saml.saml2.assertion.ConditionValidator;
import org.opensaml.saml.saml2.assertion.SAML2AssertionValidationParameters;
import org.opensaml.saml.saml2.assertion.StatementValidator;
import org.opensaml.saml.saml2.assertion.SubjectConfirmationValidator;
import org.opensaml.saml.saml2.assertion.impl.AudienceRestrictionConditionValidator;
import org.opensaml.saml.saml2.core.Assertion;
import org.opensaml.saml.saml2.core.AuthnStatement;
import org.opensaml.saml.saml2.core.Condition;
import org.opensaml.saml.saml2.core.NameID;
import org.opensaml.saml.saml2.core.Statement;
import org.opensaml.saml.saml2.core.SubjectConfirmation;
import org.opensaml.xmlsec.signature.support.SignaturePrevalidator;
import org.opensaml.xmlsec.signature.support.SignatureTrustEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.litsec.opensaml.saml2.common.assertion.AssertionValidator;

/**
 * An assertion validator that makes checks based on what is required by the Swedish eID Framework.
 * 
 * <p>
 * Apart from the validation parameters documented for {@link AssertionValidator}, the following static parameters are
 * handled:
 * 
 * <ul>
 * <li>
 * {@link SAML2AssertionValidationParameters#SC_VALID_ADDRESSES}: Optional. If the set of {@link InetAddress}
 * objects are given, the Address-attribute found in the Subject confirmation will be compared against these.</li>
 * <li>
 * {@link SAML2AssertionValidationParameters#SC_VALID_RECIPIENTS}: Required. A set of valid recipient URL:s.
 * </li>
 * <li>
 * {@link SAML2AssertionValidationParameters#COND_VALID_AUDIENCES}: Required. A set of valid audiences of the assertion.
 * </li>
 * </ul>
 * </p>
 * 
 * @author Martin Lindström (martin.lindstrom@litsec.se)
 */
public class SwedishEidAssertionValidator extends AssertionValidator {

  /** Class logger. */
  private final Logger log = LoggerFactory.getLogger(SwedishEidAssertionValidator.class);

  /**
   * Constructor setting up the validator with the following validators:
   * <ul>
   * <li>confirmationValidators: {@link SwedishEidSubjectConfirmationValidator}</li>
   * <li>conditionValidators: {@link AudienceRestrictionConditionValidator}</li>
   * <li>statementValidators: {@link SwedishEidAuthnStatementValidator}, {@link SwedishEidAttributeStatementValidator}.</li>
   * </ul>
   * 
   * @param trustEngine
   *          the trust used to validate the object's signature
   * @param signaturePrevalidator
   *          the signature pre-validator used to pre-validate the object's signature
   */
  public SwedishEidAssertionValidator(SignatureTrustEngine trustEngine, SignaturePrevalidator signaturePrevalidator) {
    this(trustEngine, signaturePrevalidator, 
      Arrays.asList(new SwedishEidSubjectConfirmationValidator()),
      Arrays.asList(new AudienceRestrictionConditionValidator()), 
      Arrays.asList(new SwedishEidAuthnStatementValidator(), new SwedishEidAttributeStatementValidator()));
  }
    
  /**
   * Constructor.
   * 
   * @param trustEngine
   *          the trust used to validate the object's signature
   * @param signaturePrevalidator
   *          the signature pre-validator used to pre-validate the object's signature
   * @param confirmationValidators
   *          validators used to validate {@link SubjectConfirmation} methods within the assertion
   * @param conditionValidators
   *          validators used to validate the {@link Condition} elements within the assertion
   * @param statementValidators
   *          validators used to validate {@link Statement}s within the assertion
   */  
  public SwedishEidAssertionValidator(
      SignatureTrustEngine trustEngine,
      SignaturePrevalidator signaturePrevalidator,
      Collection<SubjectConfirmationValidator> confirmationValidators,
      Collection<ConditionValidator> conditionValidators,
      Collection<StatementValidator> statementValidators) {

    super(trustEngine, signaturePrevalidator, confirmationValidators, conditionValidators, statementValidators);
  }

  /**
   * A {@code Subject} element in the Assertion is required by the Swedish eID Framework. We assert that and that it
   * holds a NameID value of the correct format. We also check that there is a {@code SubjectConfirmation} element for
   * the bearer method. After that, the base implementation may execute.
   */
  @Override
  protected ValidationResult validateSubject(Assertion assertion, ValidationContext context) {

    if (assertion.getSubject() == null) {
      context.setValidationFailureMessage("Missing Subject element in Assertion");
      return ValidationResult.INVALID;
    }

    // Assert that there is a NameID ...
    //
    if (assertion.getSubject().getNameID() == null) {
      context.setValidationFailureMessage("Missing NameID in Subject element of Assertion");
      return ValidationResult.INVALID;
    }
    // And that it holds a value ...
    //
    if (assertion.getSubject().getNameID().getValue() == null) {
      context.setValidationFailureMessage("Missing NameID value in Subject element of Assertion");
      return ValidationResult.INVALID;
    }
    // Also check that it is persistent or transient ...
    //
    if (assertion.getSubject().getNameID().getFormat() == null) {
      final String msg = "NameID element of Assertion/@Subject is missing Format attribute";
      if (isStrictValidation(context)) {
        context.setValidationFailureMessage(msg);
        return ValidationResult.INVALID;
      }
      else {
        log.warn(msg);
      }
    }
    else {
      final String format = assertion.getSubject().getNameID().getFormat();
      if (!(format.equals(NameID.PERSISTENT) || format.equals(NameID.TRANSIENT))) {
        final String msg = String.format("NameID format in Subject of Assertion is not valid (%s) - '%s' or '%s' is required",
          format, NameID.PERSISTENT, NameID.TRANSIENT);
        if (isStrictValidation(context)) {
          context.setValidationFailureMessage(msg);
          return ValidationResult.INVALID;
        }
        else {
          log.warn(msg);
        }
      }
    }

    List<SubjectConfirmation> confirmations = assertion.getSubject().getSubjectConfirmations();
    if (confirmations == null || confirmations.isEmpty()) {
      context.setValidationFailureMessage("Assertion/@Subject element contains no SubjectConfirmation elements - invalid");
      return ValidationResult.INVALID;
    }

    // We require the bearer method ...
    //
    boolean bearerFound = false;
    for (SubjectConfirmation sc : confirmations) {
      if (SubjectConfirmation.METHOD_BEARER.equals(sc.getMethod())) {
        bearerFound = true;
        break;
      }
    }
    if (!bearerFound) {
      final String msg = String.format("No SubjectConfirmation with method '%s' is available under Assertion's Subject element",
        SubjectConfirmation.METHOD_BEARER);
      context.setValidationFailureMessage(msg);
      return ValidationResult.INVALID;
    }

    return super.validateSubject(assertion, context);
  }

  /**
   * Extends the base implementation with requirements from the Swedish eID Framework.
   */
  @Override
  protected ValidationResult validateConditions(Assertion assertion, ValidationContext context) {

    if (assertion.getConditions() == null) {
      context.setValidationFailureMessage("Missing Conditions element in Assertion");
      return ValidationResult.INVALID;
    }

    // Assert that the NotBefore is there ...
    //
    if (assertion.getConditions().getNotBefore() == null) {
      context.setValidationFailureMessage("Missing NotBefore attribute of Conditions element in Assertion");
      return ValidationResult.INVALID;
    }

    // ... and NotOnOrAfter ...
    //
    if (assertion.getConditions().getNotOnOrAfter() == null) {
      context.setValidationFailureMessage("Missing NotOnOrAfter attribute of Conditions element in Assertion");
      return ValidationResult.INVALID;
    }

    // The Swedish eID Framework requires the AudienceRestriction to be there ...
    //
    if (assertion.getConditions().getAudienceRestrictions().isEmpty()) {
      context.setValidationFailureMessage("Missing AudienceRestriction element of Conditions element in Assertion");
      return ValidationResult.INVALID;
    }

    return super.validateConditions(assertion, context);
  }

  /**
   * Overrides the default implementation with checks to ensure the the {@link AuthnStatement} and
   * {@link AttributeStatement} elements are in place.
   */
  @Override
  protected ValidationResult validateStatements(Assertion assertion, ValidationContext context) {

    if (assertion.getAuthnStatements() == null || assertion.getAuthnStatements().isEmpty()) {
      context.setValidationFailureMessage("No AuthnStatement in Assertion");
      return ValidationResult.INVALID;
    }
    if (assertion.getAttributeStatements() == null || assertion.getAttributeStatements().isEmpty()) {
      context.setValidationFailureMessage("No AttributeStatement in Assertion");
      return ValidationResult.INVALID;
    }
    
    return super.validateStatements(assertion, context);
  }

}
