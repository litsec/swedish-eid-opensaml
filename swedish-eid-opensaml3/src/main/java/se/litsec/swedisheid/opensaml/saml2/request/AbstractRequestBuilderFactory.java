package se.litsec.swedisheid.opensaml.saml2.request;

import java.security.KeyStore.PrivateKeyEntry;
import java.util.Optional;

import org.opensaml.saml.common.xml.SAMLConstants;
import org.opensaml.saml.saml2.core.RequestAbstractType;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml.saml2.metadata.IDPSSODescriptor;
import org.opensaml.saml.saml2.metadata.SPSSODescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.shibboleth.utilities.java.support.resolver.ResolverException;
import se.litsec.swedisheid.opensaml.saml2.metadata.provider.MetadataProvider;
import se.litsec.swedisheid.opensaml.utils.InputUtils;

/**
 * Abstract base class for request builder factories.
 * 
 * @author Martin Lindström (martin.lindstrom@litsec.se)
 *
 * @param <T>
 *          the concrete request type
 */
public abstract class AbstractRequestBuilderFactory<T extends RequestAbstractType> implements RequestBuilderFactory<T> {

  /** The default setting for the number of characters used for the ID-attribute. */
  public static final int DEFAULT_ID_SIZE = 40;

  /** Logging instance. */
  private Logger logger = LoggerFactory.getLogger(AbstractRequestBuilderFactory.class);

  /** The entityID of the Service Provider that this factory creates AuthnRequest builders for. */
  private String serviceProviderEntityID;

  /** The SAML the bidning to use when sending the request message. */
  private String binding = SAMLConstants.SAML2_REDIRECT_BINDING_URI;

  /** The credentials used when signing. */
  private PrivateKeyEntry signatureCredentials;

  /** The federation metadata. */
  private MetadataProvider metadataProvider;

  /** The SP metadata. */
  private SPSSODescriptor spMetadata;

  /** The number of characters in the ID-attribute. */
  private int idSize = DEFAULT_ID_SIZE;

  /**
   * Constructor assigning the service provider entityID, the metadata provider for the federation and
   * optionally the signing credentials.
   * 
   * @param serviceProviderEntityID
   *          the entityID for the Service Provider this factory serves
   * @param metadataProvider
   *          the provider of metadata (must not be {@code null})
   * @param signatureCredentials
   *          the credentials used when signing requests (if {@code null} no signing of request messages will
   *          be possible)
   * @throws ResolverException
   *           for metadata errors
   */
  public AbstractRequestBuilderFactory(String serviceProviderEntityID, MetadataProvider metadataProvider,
      PrivateKeyEntry signatureCredentials) throws ResolverException {

    InputUtils.assertNotNull(serviceProviderEntityID, "serviceProviderEntityID");
    InputUtils.assertNotNull(metadataProvider, "metadataProvider");

    this.serviceProviderEntityID = serviceProviderEntityID;
    this.metadataProvider = metadataProvider;
    this.signatureCredentials = signatureCredentials;
    if (this.signatureCredentials == null) {
      logger.info(
          "No signature credentials configured for RequestBuilderFactory - will not be able to sign request messages");
    }
  }

  /**
   * Constructor assigning the service provider metadata, the metadata provider for the federation and
   * optionally the signing credentials.
   * <p>
   * Note: It is not required to assign the {@code metadataProvider} parameter, but in those cases only
   * {@link #newBuilder(EntityDescriptor)} will be possible to use when creating request builder objects (not
   * {@link #newBuilder(String)}).
   * </p>
   * 
   * @param spMetadata
   *          the metadata for the SP that this factory serves
   * @param metadataProvider
   *          the provider of metadata
   * @param signatureCredentials
   *          the credentials used when signing requests (if {@code null} no signing of request messages will
   *          be possible)
   * @throws ResolverException
   *           for metadata errors
   */
  public AbstractRequestBuilderFactory(EntityDescriptor spMetadata, MetadataProvider metadataProvider,
      PrivateKeyEntry signatureCredentials) throws ResolverException {

    InputUtils.assertNotNull(spMetadata, "spMetadata");
    InputUtils.assertNotNull(metadataProvider, "metadataProvider");

    this.spMetadata = spMetadata.getSPSSODescriptor(SAMLConstants.SAML20P_NS);
    if (this.spMetadata == null) {
      throw new ResolverException("Could not find SPSSODescriptor of supplied metadata entry");
    }
    this.serviceProviderEntityID = spMetadata.getEntityID();
    this.metadataProvider = metadataProvider;
    this.signatureCredentials = signatureCredentials;
    if (this.signatureCredentials == null) {
      logger.info(
          "No signature credentials configured for RequestBuilderFactory - will not be able to sign request messages");
    }
  }

  /** {@inheritDoc} */
  @Override
  public RequestBuilder<T> newBuilder(String idpEntityID) throws ResolverException {

    logger.debug("Creating a Request for IdP '{}' ... [sp-entity-id: '{}']", idpEntityID, this.serviceProviderEntityID);

    // Find the IdP metadata entry.
    Optional<IDPSSODescriptor> idpDescriptor = this.metadataProvider.getIDPSSODescriptor(idpEntityID);
    if (!idpDescriptor.isPresent()) {
      throw new ResolverException(
          String.format("No metadata found for IdP '%s' - cannot create request.", idpEntityID));
    }

    return this.newBuilder(idpEntityID, this.getSpMetadata(), idpDescriptor.get());
  }

  /** {@inheritDoc} */
  @Override
  public RequestBuilder<T> newBuilder(EntityDescriptor idpMetadata) throws ResolverException {

    logger.debug("Creating a Request for IdP '{}' ... [sp-entity-id: '{}']", idpMetadata.getEntityID(),
        this.serviceProviderEntityID);

    IDPSSODescriptor idpDescriptor = idpMetadata.getIDPSSODescriptor(SAMLConstants.SAML20P_NS);
    if (idpDescriptor == null) {
      throw new ResolverException("Could not find IDPSSODescriptor of supplied metadata");
    }

    return this.newBuilder(idpMetadata.getEntityID(), this.getSpMetadata(), idpDescriptor);
  }

  /**
   * Creates a new builder object for creating a request that is to be sent to the IdP that is identified by
   * the supplied entityID.
   * 
   * @param idpEntityID
   *          the entityID for the IdP that should receive the request
   * @param spDescriptor
   *          the metadata for "our" SP
   * @param idpDescriptor
   *          the metadata for the IdP
   * @return a RequestBuilder object
   * @throws ResolverException
   *           for metadata related errors
   */
  protected abstract RequestBuilder<T> newBuilder(String idpEntityID, SPSSODescriptor spDescriptor,
      IDPSSODescriptor idpDescriptor) throws ResolverException;

  /**
   * Returns the metadata for the SP that we are building the request for.
   * 
   * @return metadata
   * @throws ResolverException
   *           for provider errors
   */
  private SPSSODescriptor getSpMetadata() throws ResolverException {
    if (this.spMetadata != null) {
      return this.spMetadata;
    }
    else {
      Optional<SPSSODescriptor> md = this.metadataProvider.getSPSSODescriptor(this.serviceProviderEntityID);
      if (!md.isPresent()) {
        throw new ResolverException("Could not find SP metadata for " + this.serviceProviderEntityID);
      }
      return md.get();
    }
  }

  /** {@inheritDoc} */
  @Override
  public String getServiceProviderEntityID() {
    return this.serviceProviderEntityID;
  }

  /** {@inheritDoc} */
  @Override
  public String getBinding() {
    return this.binding;
  }
  
  /** {@inheritDoc} */
  @Override
  public PrivateKeyEntry getSignatureCredentials() {
    return this.signatureCredentials;
  }

  /**
   * Assigns the bidning to use when sending the request message.
   * 
   * @param binding
   *          the SAML binding
   * @see RequestBuilderFactory#getBinding()
   */
  public void setBinding(String binding) {
    this.binding = binding;
  }

  /** {@inheritDoc} */
  @Override
  public int getIdSize() {
    return this.idSize;
  }

  /**
   * Assigns the number of characters that should be used when generating the ID attribute for the request.
   * 
   * @param idSize
   *          number of characters for the ID
   */
  public void setIdSize(int idSize) {
    this.idSize = idSize;
  }

}
