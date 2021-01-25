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
package se.litsec.swedisheid.opensaml.saml2.authentication.psc.build;

import se.litsec.opensaml.core.AbstractSAMLObjectBuilder;
import se.litsec.swedisheid.opensaml.saml2.authentication.psc.MatchValue;

/**
 * A builder for {@link MatchValue} objects.
 * 
 * @author Martin Lindström (martin.lindstrom@litsec.se)
 */
public class MatchValueBuilder extends AbstractSAMLObjectBuilder<MatchValue> {

  /**
   * Creates a new {@code MatchValueBuilder} instance.
   * 
   * @return a {@code MatchValueBuilder} instance
   */
  public static MatchValueBuilder builder() {
    return new MatchValueBuilder();
  }

  /** {@inheritDoc} */
  @Override
  protected Class<MatchValue> getObjectType() {
    return MatchValue.class;
  }

  /**
   * Assigns the value.
   * 
   * @param value
   *          the value
   * @return the builder
   */
  public MatchValueBuilder value(String value) {
    this.object().setValue(value);
    return this;
  }

  /**
   * Assigns the {@code Name} attribute of the {@code MatchValue} object.
   * 
   * @param name
   *          the name
   * @return the builder
   */
  public MatchValueBuilder name(String name) {
    this.object().setName(name);
    return this;
  }

  /**
   * Assigns the {@code NameFormat} attribute of the {@code MatchValue} object.
   * 
   * @param nameFormat
   *          the name format URI
   * @return the builder
   */
  public MatchValueBuilder nameFormat(String nameFormat) {
    this.object().setNameFormat(nameFormat);
    return this;
  }

}
