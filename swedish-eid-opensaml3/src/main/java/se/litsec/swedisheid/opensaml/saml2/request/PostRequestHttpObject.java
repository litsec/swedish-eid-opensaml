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
package se.litsec.swedisheid.opensaml.saml2.request;

import java.io.UnsupportedEncodingException;
import java.security.KeyStore.PrivateKeyEntry;
import java.util.HashMap;
import java.util.Map;

import org.opensaml.messaging.context.MessageContext;
import org.opensaml.messaging.encoder.MessageEncodingException;
import org.opensaml.saml.common.binding.SAMLBindingSupport;
import org.opensaml.saml.common.xml.SAMLConstants;
import org.opensaml.saml.saml2.binding.encoding.impl.HTTPPostEncoder;
import org.opensaml.saml.saml2.core.RequestAbstractType;
import org.opensaml.saml.saml2.metadata.Endpoint;
import org.opensaml.xmlsec.signature.support.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

import net.shibboleth.utilities.java.support.codec.Base64Support;
import net.shibboleth.utilities.java.support.codec.HTMLEncoder;
import net.shibboleth.utilities.java.support.xml.SerializeSupport;
import se.litsec.swedisheid.opensaml.utils.SAMLUtils;

/**
 * A RequestHttpObject for sending using HTTP POST.
 * <p>
 * If signature credentials are supplied when creating the object the request will be signed.
 * </p>
 *
 * @author Martin Lindström (martin.lindstrom@litsec.se)
 *
 * @param <T>
 *          the type of the request
 */
public class PostRequestHttpObject<T extends RequestAbstractType> extends HTTPPostEncoder implements RequestHttpObject<T> {

  /** Logging instance. */
  private static final Logger logger = LoggerFactory.getLogger(PostRequestHttpObject.class);

  /** The request. */
  private T request;

  /** The URL to redirect to. */
  private String sendUrl;

  /** HTTP headers. */
  private Map<String, String> httpHeaders = new HashMap<String, String>();

  /** The POST parameters. */
  private Map<String, String> postParameters = new HashMap<String, String>();

  /**
   * Constructor that puts together to resulting object.
   *
   * @param request
   *          the request object
   * @param relayState
   *          the relay state
   * @param signatureCredentials
   *          optional signature credentials
   * @param endpoint
   *          the endpoint where we send this request to
   * @throws MessageEncodingException
   *           for encoding errors
   * @throws SignatureException
   *           for signature errors
   */
  public PostRequestHttpObject(T request, String relayState, PrivateKeyEntry signatureCredentials, Endpoint endpoint)
      throws MessageEncodingException, SignatureException {

    this.request = request;

    MessageContext<T> context = new MessageContext<T>();
    context.setMessage(request);

    // Assign endpoint (sendUrl)
    //
    String encodedEndpointURL = HTMLEncoder.encodeForHTMLAttribute(endpoint.getLocation());
    logger.trace("Encoding POST of '{}' with encoded value '{}'", endpoint, encodedEndpointURL);
    this.sendUrl = encodedEndpointURL;

    // Assign SAMLRequest
    //
    if (signatureCredentials != null) {
      logger.trace("Signing SAML Request message ...");
      SAMLUtils.sign(this.request, signatureCredentials);
    }

    logger.trace("Marshalling and Base64 encoding SAML message");
    Element domMessage = this.marshallMessage(context.getMessage());

    try {
      String messageXML = SerializeSupport.nodeToString(domMessage);
      String encodedMessage = Base64Support.encode(messageXML.getBytes("UTF-8"), Base64Support.UNCHUNKED);
      this.postParameters.put("SAMLRequest", encodedMessage);
    }
    catch (UnsupportedEncodingException e) {
      logger.error("UTF-8 encoding is not supported, this VM is not Java compliant.");
      throw new MessageEncodingException("Unable to encode message, UTF-8 encoding is not supported");
    }

    // Assign RelayState
    //
    if (SAMLBindingSupport.checkRelayState(relayState)) {
      String encodedRelayState = HTMLEncoder.encodeForHTMLAttribute(relayState);
      logger.debug("Setting RelayState parameter to: '{}', encoded as '{}'", relayState, encodedRelayState);
      this.postParameters.put("RelayState", encodedRelayState);
    }

    // HTTP headers
    //
    this.httpHeaders.put("Cache-control", "no-cache, no-store");
    this.httpHeaders.put("Pragma", "no-cache");
  }

  @Override
  public String getSendUrl() {
    return this.sendUrl;
  }

  @Override
  public String getMethod() {
    return SAMLConstants.POST_METHOD;
  }

  @Override
  public Map<String, String> getPostParameters() {
    return this.postParameters;
  }

  @Override
  public Map<String, String> getHttpHeaders() {
    return this.httpHeaders;
  }

  @Override
  public T getRequest() {
    return this.request;
  }

  @Override
  public String toString() {
    return String.format("request-type='%s', sendUrl='%s', httpHeaders='%s, postParameters=%s", this.request.getClass().getSimpleName(),
      this.sendUrl, this.httpHeaders, this.postParameters);
  }

}
