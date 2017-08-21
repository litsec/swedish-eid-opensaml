package se.litsec.swedisheid.opensaml.saml2.request;

import java.security.KeyStore.PrivateKeyEntry;

import org.opensaml.saml.saml2.core.RequestAbstractType;
import org.opensaml.saml.saml2.metadata.IDPSSODescriptor;
import org.opensaml.saml.saml2.metadata.SPSSODescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.shibboleth.utilities.java.support.resolver.ResolverException;

/**
 * Abstract base class for building Request messages.
 * 
 * @author Martin Lindström (martin@litsec.se)
 *
 * @param <T>
 *          the concrete request type
 */
abstract class AbstractRequestBuilder<T extends RequestAbstractType> implements RequestBuilder<T> {

  /** Logging instance. */
  private Logger log = LoggerFactory.getLogger(AbstractRequestBuilder.class);

  /** The request being built. */
  private T request;

  /** The request RelayState. */
  private String relayState;

  /** The binding in use. */
  private String binding;

  /**
   * Flag that is set if we explicitly set whether signing should be performed or not. If not set, we decide
   * this by analyzing the metadata.
   */
  private Boolean performSignature;

  /**
   * Signature credentials are configured for the factory, but may be overridden for each builder instance.
   */
  private PrivateKeyEntry overrideSignatureCredentials;
  
  /** For testing purposes. */
  private String endpoint;

  /**
   * Default constructor setting up the request.
   */
  public AbstractRequestBuilder() {
    this.request = this.createRequest();
  }

  /**
   * Creates the request object.
   * 
   * @return the request object
   */
  protected abstract T createRequest();

  /** {@inheritDoc} */
  @Override
  public RequestBuilder<T> relayState(String relayState) {
    this.relayState = relayState;
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public String relayState() {
    return this.relayState;
  }

  /** {@inheritDoc} */
  @Override
  public RequestBuilder<T> request(T request) {
    this.request = request;
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public T request() {
    return this.request;
  }

  /** {@inheritDoc} */
  @Override
  public RequestBuilder<T> binding(String binding) throws ResolverException {
    // TODO: check to see if the IdP supports it ...
    this.binding = binding;
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public String binding() {
    return this.binding != null ? this.binding : this.getConfiguredBinding();
  }

  protected abstract IDPSSODescriptor getIdpMetadata();

  protected abstract SPSSODescriptor getSpMetadata();

  /**
   * Returns the binding that was originally configured for this builder (via its factory configuration).
   * 
   * @return the configured binding method
   */
  protected abstract String getConfiguredBinding();

  /** {@inheritDoc} */
  @Override
  public RequestBuilder<T> performSignature(boolean signatureFlag) {
    this.performSignature = signatureFlag;
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public boolean performSignature() {
    if (this.performSignature != null) {
      log.debug("Request builder has been configured {} the request",
          this.performSignature ? "to sign" : "not to sign");
      return this.performSignature;
    }
    return this.getSignatureRequirement();
  }

  /**
   * Based on metadata settings and/or the type of request the method returns whether the request should be
   * signed.
   * 
   * @return if the request should be signed {@code true} is returned, and otherwise {@code false}
   */
  protected abstract boolean getSignatureRequirement();

  /** {@inheritDoc} */
  @Override
  public RequestBuilder<T> signatureCredentials(PrivateKeyEntry signatureCredentials) {
    this.overrideSignatureCredentials = signatureCredentials;
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public PrivateKeyEntry signatureCredentials() {
    if (this.overrideSignatureCredentials != null) {
      return this.overrideSignatureCredentials;
    }
    return this.getConfiguredSignatureCredentials();
  }

  /**
   * Returns the signature credentials that are configured in the factory.
   * 
   * @return the configured signature credentials.
   */
  protected abstract PrivateKeyEntry getConfiguredSignatureCredentials();
  
  /**
   * <b>For testing purposes</b>
   * <p>
   * The method will change the endpoint to where the request will be sent, but will <b>not</b> modify the
   * {@code Destination} attribute of the request element.
   * </p>
   * 
   * @param url
   *          the endpoint to assign
   * @return an updated builder object
   */
  public RequestBuilder<T> endpoint(String url) {
    this.endpoint = url;
    return this;
  }

  // TODO: endpoint()
}
