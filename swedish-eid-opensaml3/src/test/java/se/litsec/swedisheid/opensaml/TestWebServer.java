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
package se.litsec.swedisheid.opensaml;

import java.io.IOException;
import java.net.URI;
import java.security.KeyStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.springframework.core.io.Resource;

import se.litsec.swedisheid.opensaml.saml2.metadata.ResourceProvider;

/**
 * Class for supporting test cases that need a web server.
 * 
 * @author Martin Lindström (martin.lindstrom@litsec.se)
 */
public class TestWebServer {

  /**
   * The signature credentials for signing metadata. If no credential is configured, the service will not sign its
   * metadata.
   */
  private KeyStore.PrivateKeyEntry signatureCredential;

  /** The credentials for the web server. If not set, the server will run HTTP instead of HTTPS. */
  private KeyStore serverCredentials;

  /** The web server. */
  private Server server;

  /** The scheme (HTTP/HTTPS). */
  private String scheme;

  public TestWebServer(ResourceProvider resourceProvider) {
    QueuedThreadPool serverThreads = new QueuedThreadPool();
    serverThreads.setName("server");
    this.server = new Server(serverThreads);
    ServerConnector connector = new ServerConnector(this.server /* sslContextFactory */);
    this.server.addConnector(connector);
    server.setHandler(new MetadataHandler(resourceProvider));
    // server.start();
  }

  /**
   * Starts the metadata service.
   * 
   * @throws Exception
   *           if the server fails to start
   */
  public URI start() throws Exception {
    // this.server = new Server(0);

    this.server.start();    

    return this.server.getURI();
  }

  /**
   * Stops the metadata service.
   * 
   * @throws Exception
   *           if the service fails to stop
   */
  public void stop() throws Exception {
    if (this.server != null && this.server.isStarted() && !this.server.isStopped()) {
      this.server.stop();
    }
  }

  /**
   * Returns the URL on which the service exposes the metadata.
   * 
   * @return metadata URL
   */
  public String getMetadataUrl() {
    return null;
  }
  
  @FunctionalInterface
  public interface ResourceProvider {
    Resource getResource();
  }

  public static class ResourceHandler extends AbstractHandler {
    
    private ResourceProvider resourceProvider;

    public ResourceHandler(ResourceProvider resourceProvider) {
      this.resourceProvider = resourceProvider;
    }

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException,
        ServletException {      
      
      response.getOutputStream().write(IOUtils.toByteArray(this.resourceProvider.getResource().getInputStream()));
      baseRequest.setHandled(true);

    }

  }

}
