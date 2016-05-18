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

import org.opensaml.saml.saml2.core.RequestAbstractType;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;

import net.shibboleth.utilities.java.support.resolver.ResolverException;

/**
 * Factory for creating request builder objects. The request builder returned are initiated with the settings the
 * factory collects by reading the metadata for the SP and IdP combined with the settings that are configured for the
 * factory.
 * 
 * @author Martin Lindström (martin.lindstrom@litsec.se)
 *
 * @param <T>
 *          the concrete request type
 * @see RequestBuilder
 */
public interface RequestBuilderFactory<T extends RequestAbstractType> {

  /**
   * Creates a new builder object for creating a request that is to be sent to the IdP that is identified by the
   * supplied entityID.
   * <p>
   * Note that the factory needs access to a metadata provider where it can locate the IdP metadata. If no such provider
   * is installed, the {@link #newBuilder(EntityDescriptor)} method should be used.
   * </p>
   * 
   * @param idpEntityID
   *          the entityID for the IdP that should receive the request
   * @return a RequestBuilder object
   * @throws ResolverException
   *           for metadata related errors
   * @see #newBuilder(EntityDescriptor)
   */
  RequestBuilder<T> newBuilder(String idpEntityID) throws ResolverException;

  /**
   * Creates a new builder object for creating a request that is to be sent to the IdP that is identified by the
   * supplied metadata entry.
   * 
   * @param idpMetadata
   *          the metadata for the IdP that should received the request
   * @return a RequestBuilder object
   * @throws ResolverException
   *           for metadata related errors
   * @see #newBuilder(String)
   */
  RequestBuilder<T> newBuilder(EntityDescriptor idpMetadata) throws ResolverException;

  /**
   * Returns the entityID of the Service Provider that this factory creates request builders for.
   * 
   * @return the entityID of the owning Service Provider
   */
  String getServiceProviderEntityID();

  /**
   * Returns the bidning to use when sending the request message.
   * <p>
   * If not explicitly assigned the default binding is HTTP-Redirect (
   * {@code urn:oasis:names:tc:SAML:2.0:bindings:HTTP-Redirect}), but HTTP-POST (
   * {@code urn:oasis:names:tc:SAML:2.0:bindings:HTTP-POST}) is also supported.
   * </p>
   * <p>
   * This value may be overridded by using the {@link RequestBuilder#binding(String)} method.
   * </p>
   * 
   * @return the SAML binding telling how the request should be sent to the IdP
   */
  String getBinding();

  /**
   * Returns the number of characters that should be used when generating the ID attribute for the request.
   * 
   * @return number of characters for the ID
   */
  int getIdSize();

}
