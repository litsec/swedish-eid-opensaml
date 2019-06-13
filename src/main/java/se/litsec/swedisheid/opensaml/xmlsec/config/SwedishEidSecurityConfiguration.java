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
package se.litsec.swedisheid.opensaml.xmlsec.config;

import org.opensaml.xmlsec.DecryptionConfiguration;
import org.opensaml.xmlsec.EncryptionConfiguration;
import org.opensaml.xmlsec.config.impl.DefaultSecurityConfigurationBootstrap;
import org.opensaml.xmlsec.encryption.support.EncryptionConstants;
import org.opensaml.xmlsec.encryption.support.RSAOAEPParameters;
import org.opensaml.xmlsec.impl.BasicDecryptionConfiguration;
import org.opensaml.xmlsec.impl.BasicEncryptionConfiguration;
import org.opensaml.xmlsec.signature.support.SignatureConstants;

import se.swedenconnect.opensaml.xmlsec.config.SAML2IntSecurityConfiguration;
import se.swedenconnect.opensaml.xmlsec.config.SecurityConfiguration;

/**
 * A {@link SecurityConfiguration} instance with algorithm defaults according to the Swedish eID Framework (see
 * https://docs.swedenconnect.se).
 * <p>
 * The Swedish eID Framework follows SAML2Int ({@link SAML2IntSecurityConfiguration}) with the exception that we it does
 * not black lists RSA 1.5 for decryption (for interop and historical reasons) and that SHA-1 is used for RSA-OAEP
 * key transport.
 * </p>
 * 
 * @author Martin Lindström (martin.lindstrom@litsec.se)
 */
public class SwedishEidSecurityConfiguration extends SAML2IntSecurityConfiguration {

  /** {@inheritDoc} */
  @Override
  public String getProfileName() {
    return "swedish-eid-framework";
  }

  /**
   * Some implementations (specifically those built using OpenSAML 2 do not handle SHA-256 for RSAOAEP).
   */
  @Override
  protected EncryptionConfiguration createDefaultEncryptionConfiguration() {
    BasicEncryptionConfiguration config = (BasicEncryptionConfiguration) super.createDefaultEncryptionConfiguration();

    config.setRSAOAEPParameters(
      new RSAOAEPParameters(SignatureConstants.ALGO_ID_DIGEST_SHA1, EncryptionConstants.ALGO_ID_MGF1_SHA1, null));

    return config;
  }

  /**
   * Removes the blacklisting of RSA 1.5.
   */
  @Override
  protected DecryptionConfiguration createDefaultDecryptionConfiguration() {
    BasicDecryptionConfiguration config = DefaultSecurityConfigurationBootstrap.buildDefaultDecryptionConfiguration();
    config.setBlacklistedAlgorithms(null);
    return config;
  }

}
