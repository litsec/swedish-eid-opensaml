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

import se.swedenconnect.opensaml.xmlsec.config.SAML2IntSecurityConfiguration;
import se.swedenconnect.opensaml.xmlsec.config.SecurityConfiguration;

/**
 * A {@link SecurityConfiguration} instance with algorithm defaults according to the Swedish eID Framework (see
 * https://docs.swedenconnect.se).
 * <p>
 * Currently the implementation equals {@link SAML2IntSecurityConfiguration}.
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

}
