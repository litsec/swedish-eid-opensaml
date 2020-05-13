/*
 * Copyright 2016-2020 Litsec AB
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

import java.util.Arrays;
import java.util.List;

import org.opensaml.core.xml.XMLRuntimeException;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.core.xml.util.XMLObjectSupport;

import se.litsec.opensaml.core.AbstractSAMLObjectBuilder;
import se.litsec.swedisheid.opensaml.saml2.authentication.psc.MatchValue;
import se.litsec.swedisheid.opensaml.saml2.authentication.psc.RequestedPrincipalSelection;

/**
 * A builder for {@link RequestedPrincipalSelection} objects.
 * 
 * @author Martin Lindström (martin.lindstrom@litsec.se)
 */
public class RequestedPrincipalSelectionBuilder extends AbstractSAMLObjectBuilder<RequestedPrincipalSelection> {

  /**
   * Creates a new {@code RequestedPrincipalSelectionBuilder} instance.
   * 
   * @return a RequestedPrincipalSelectionBuilder instance
   */
  public static RequestedPrincipalSelectionBuilder builder() {
    return new RequestedPrincipalSelectionBuilder();
  }

  /** {@inheritDoc} */
  @Override
  protected Class<RequestedPrincipalSelection> getObjectType() {
    return RequestedPrincipalSelection.class;
  }

  /**
   * Assigns the match values.
   * 
   * @param matchValues
   *          a list of match values
   * @return the builder
   */
  public RequestedPrincipalSelectionBuilder matchValues(List<MatchValue> matchValues) {
    if (matchValues != null && !matchValues.isEmpty()) {
      for (MatchValue mv : matchValues) {
        try {
          this.object().getMatchValues().add(XMLObjectSupport.cloneXMLObject(mv));
        }
        catch (MarshallingException | UnmarshallingException e) {
          throw new XMLRuntimeException(e);
        }
      }
    }
    else {
      this.object().getMatchValues().clear();
    }
    return this;
  }

  /**
   * Assigns the match values.
   * 
   * @param matchValues
   *          the match values
   * @return the builder
   */
  public RequestedPrincipalSelectionBuilder matchValues(MatchValue... matchValues) {
    return this.matchValues(matchValues != null ? Arrays.asList(matchValues) : null);
  }

}
