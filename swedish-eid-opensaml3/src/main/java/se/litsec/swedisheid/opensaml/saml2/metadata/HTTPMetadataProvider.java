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
package se.litsec.swedisheid.opensaml.saml2.metadata;

import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.saml.metadata.resolver.MetadataResolver;
import org.opensaml.saml.metadata.resolver.filter.MetadataFilter;
import org.opensaml.saml.metadata.resolver.impl.FileBackedHTTPMetadataResolver;
import org.opensaml.saml.metadata.resolver.impl.HTTPMetadataResolver;
import org.opensaml.security.credential.Credential;
import org.opensaml.security.credential.impl.CollectionCredentialResolver;
import org.opensaml.security.httpclient.impl.TrustEngineTLSSocketFactory;
import org.opensaml.security.trust.TrustEngine;
import org.opensaml.security.trust.impl.ExplicitX509CertificateTrustEngine;
import org.opensaml.security.x509.BasicX509Credential;
import org.opensaml.security.x509.X509Credential;

import net.shibboleth.utilities.java.support.component.ComponentInitializationException;
import net.shibboleth.utilities.java.support.resolver.ResolverException;
import se.litsec.swedisheid.opensaml.utils.InputAssert;

/**
 * A provider that downloads metadata from a HTTP resource.
 * 
 * @author Martin Lindström (martin.lindstrom@litsec.se)
 * @see HTTPMetadataResolver
 * @see FileBackedHTTPMetadataResolver
 */
public class HTTPMetadataProvider extends AbstractMetadataProvider {

  /** The metadata resolver. */
  private HTTPMetadataResolver metadataResolver;

  /** The keystore holding the certificates that we trust for TLS connections. */
  private KeyStore tlsTrustStore;
  
  // TODO: Find out how we use cacerts ...

  /**
   * Creates a provider that peiodically downloads data from the URL given by {@code metadataUrl}. If the
   * {@code backupFile} parameter is given the provider also stores the downloaded metadata on disk as backup.
   * <p>
   * This constructor will initialize the underlying {@code MetadataResolver} with a default {@code HttpClient}
   * instance that uses the system defaults ({@code HttpClients.createSystem()}).
   * </p>
   * 
   * @param metadataUrl
   *          the URL to use when downloading metadata
   * @param backupFile
   *          optional path to the file to where the provider should store downloaded metadata
   * @throws ResolverException
   *           if the supplied metadata URL is invalid
   * @see #HTTPMetadataProvider(String, String, HttpClient)
   */
  public HTTPMetadataProvider(String metadataUrl, String backupFile) throws ResolverException {
    this(metadataUrl, backupFile, HttpClients.createSystem());
  }

  /**
   * Creates a provider that peiodically downloads data from the URL given by {@code metadataUrl}. If the
   * {@code backupFile} parameter is given the provider also stores the downloaded metadata on disk as backup.
   * 
   * @param metadataUrl
   *          the URL to use when downloading metadata
   * @param backupFile
   *          optional path to the file to where the provider should store downloaded metadata
   * @param httpClient
   *          the {@code HttpClient} that should be used to download the metadata
   * @throws ResolverException
   *           if the supplied metadata URL is invalid
   */
  public HTTPMetadataProvider(String metadataUrl, String backupFile, HttpClient httpClient) throws ResolverException {
    InputAssert.notNull(metadataUrl, "metadataUrl");
    InputAssert.notNull(httpClient, "httpClient");

    this.metadataResolver = backupFile != null
        ? new FileBackedHTTPMetadataResolver(httpClient, metadataUrl, backupFile)
        : new HTTPMetadataResolver(httpClient, metadataUrl);
  }

//  private HttpClient createDefaultHttpClient() {
//    CloseableHttpClient httpClient = HttpClients.createSystem()
//    TrustEngineTLSSocketFactory tlsFactory = new TrustEngineTLSSocketFactory(httpClient.get 
//    
//    return null;
//  }
  
  /** {@inheritDoc} */
  @Override
  public MetadataResolver getMetadataResolver() {
    return this.metadataResolver;
  }

  /** {@inheritDoc} */
  @Override
  protected void createMetadataResolver(boolean requireValidMetadata, boolean failFastInitialization, MetadataFilter filter)
      throws ResolverException {
    try {
      this.metadataResolver.setId(this.metadataResolver.getMetadataURI());
      this.metadataResolver.setFailFastInitialization(failFastInitialization);
      this.metadataResolver.setRequireValidMetadata(requireValidMetadata);
      this.metadataResolver.setParserPool(XMLObjectProviderRegistrySupport.getParserPool());
      this.metadataResolver.setMetadataFilter(filter);

      // Setup TLS trust engine.
      this.metadataResolver.setTLSTrustEngine(this.createTlsTrustEngine());
    }
    catch (KeyStoreException e) {
      throw new ResolverException(e);
    }
  }

  /**
   * Creates a {@code TrustEngine} instance based on the trust key store that this provider was initialized with.
   * 
   * @return a {@code TrustEngine} instance
   * @throws KeyStoreException
   *           for errors reading the TLS trust key store
   */
  private TrustEngine<? super X509Credential> createTlsTrustEngine() throws KeyStoreException {
    List<Credential> trustedCertificates = new ArrayList<Credential>();
    Enumeration<String> aliases = this.tlsTrustStore.aliases();
    while (aliases.hasMoreElements()) {
      String alias = aliases.nextElement();
      if (this.tlsTrustStore.isCertificateEntry(alias)) {
        trustedCertificates.add(new BasicX509Credential((X509Certificate) this.tlsTrustStore.getCertificate(alias)));
      }
    }
    return new ExplicitX509CertificateTrustEngine(new CollectionCredentialResolver(trustedCertificates));
  }

  /** {@inheritDoc} */
  @Override
  protected void initializeMetadataResolver() throws ComponentInitializationException {
    this.metadataResolver.initialize();
  }

  /** {@inheritDoc} */
  @Override
  protected void destroyMetadataResolver() {
    if (this.metadataResolver != null) {
      this.metadataResolver.destroy();
    }
  }

}
