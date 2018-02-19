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
package se.litsec.swedisheid.opensaml.saml2.signservice;

import java.util.Arrays;
import java.util.List;

import se.litsec.opensaml.core.AbstractSAMLObjectBuilder;
import se.litsec.opensaml.utils.ObjectUtils;
import se.litsec.swedisheid.opensaml.saml2.signservice.sap.Parameter;
import se.litsec.swedisheid.opensaml.saml2.signservice.sap.RequestParams;
import se.litsec.swedisheid.opensaml.saml2.signservice.sap.SADRequest;
import se.litsec.swedisheid.opensaml.saml2.signservice.sap.SADVersion;

/**
 * Builder for creating a {@link SADRequest} using the builder pattern.
 * 
 * @author Martin Lindström (martin.lindstrom@litsec.se)
 */
public class SADRequestBuilder extends AbstractSAMLObjectBuilder<SADRequest> {

  /**
   * Utility method that creates a builder.
   * 
   * @return a builder
   */
  public static SADRequestBuilder builder() {
    return new SADRequestBuilder();
  }

  /**
   * Assigns the ID attribute for the {@code SADRequest}.
   * 
   * @param id
   *          the ID attribute
   * @return the builder
   */
  public SADRequestBuilder id(String id) {
    this.object().setID(id);
    return this;
  }

  /**
   * Assigns the requester ID (entityID of the SP requesting the SAD).
   * 
   * @param requesterID
   *          the entityID of the requester
   * @return the builder
   */
  public SADRequestBuilder requesterID(String requesterID) {
    this.object().setRequesterID(requesterID);
    return this;
  }

  /**
   * Assigns the value of the {@code RequestID} attribute of the associated {@code SignRequest}.
   * 
   * @param signRequestID
   *          the signature request ID
   * @return the builder
   */
  public SADRequestBuilder signRequestID(String signRequestID) {
    this.object().setSignRequestID(signRequestID);
    return this;
  }

  /**
   * Assigns the number of requested signatures in the associated sign request.
   * 
   * @param docCount
   *          the document count
   * @return the builder
   */
  public SADRequestBuilder docCount(Integer docCount) {
    this.object().setDocCount(docCount);
    return this;
  }

  /**
   * Assigns the requested version of the SAD.
   * 
   * @param sadVersion
   *          the SAD version
   * @return the builder
   */
  public SADRequestBuilder requestedVersion(SADVersion sadVersion) {
    this.object().setRequestedVersion(sadVersion);
    return this;
  }

  /**
   * Assigns the {@code RequestParams} element.
   * 
   * @param requestParams
   *          the {@code RequestParams}
   * @return the builder
   */
  public SADRequestBuilder requestParams(RequestParams requestParams) {
    this.object().setRequestParams(requestParams);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  protected Class<SADRequest> getObjectType() {
    return SADRequest.class;
  }

  /**
   * Builder for creating a {@link RequestParams} using the builder pattern.
   * 
   * @author Martin Lindström (martin.lindstrom@litsec.se)
   */
  public static class RequestParamsBuilder extends AbstractSAMLObjectBuilder<RequestParams> {

    /**
     * Utility method that creates a builder.
     * 
     * @return a builder
     */
    public static RequestParamsBuilder builder() {
      return new RequestParamsBuilder();
    }

    /**
     * Utility method creating a {@link Parameter} object.
     * 
     * @param name
     *          the parameter name
     * @param value
     *          the parameter value
     * @return the {@code Parameter} object
     */
    public static Parameter parameter(String name, String value) {
      Parameter p = ObjectUtils.createSamlObject(Parameter.class);
      p.setName(name);
      p.setValue(value);
      return p;
    }

    /**
     * Assigns the parameters to the request params.
     * 
     * @param parameters
     *          a list of parameters
     * @return the builder
     */
    public RequestParamsBuilder parameters(List<Parameter> parameters) {
      this.object().getParameters().clear();
      if (parameters != null) {
        this.object().getParameters().addAll(parameters);
      }
      return this;
    }
    
    /**
     * Assigns the parameters to the request params.
     * 
     * @param parameters
     *          a list of parameters
     * @return the builder
     */
    public RequestParamsBuilder parameters(Parameter... parameters) {
      return this.parameters(parameters != null ? Arrays.asList(parameters) : null);
    }

    /** {@inheritDoc} */
    @Override
    protected Class<RequestParams> getObjectType() {
      return RequestParams.class;
    }

  }

}
