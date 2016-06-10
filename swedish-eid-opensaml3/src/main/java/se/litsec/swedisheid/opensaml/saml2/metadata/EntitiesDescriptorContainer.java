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

import java.security.KeyStore.PrivateKeyEntry;

import org.opensaml.saml.saml2.metadata.EntitiesDescriptor;

/**
 * A {@code MetadataContainer} for {@code EntityDescriptor} elements. This class is useful for an entity wishing to
 * publicize its metadata.
 * 
 * @author Martin Lindström (martin.lindstrom@litsec.se)
 */
public class EntitiesDescriptorContainer extends AbstractMetadataContainer<EntitiesDescriptor> {

  /**
   * @see AbstractMetadataContainer#AbstractMetadataContainer(org.opensaml.saml.saml2.common.TimeBoundSAMLObject,
   *      PrivateKeyEntry)
   */
  public EntitiesDescriptorContainer(EntitiesDescriptor descriptor, PrivateKeyEntry signatureCredentials) {
    super(descriptor, signatureCredentials);
  }

  /** {@inheritDoc} */
  @Override
  protected String getID(EntitiesDescriptor descriptor) {
    return descriptor.getID();
  }

  /** {@inheritDoc} */
  @Override
  protected void assignID(EntitiesDescriptor descriptor, String id) {
    descriptor.setID(id);
  }

  /**
   * Returns the Name attribute.
   */
  @Override
  protected String getLogString(EntitiesDescriptor descriptor) {
    return descriptor.getName();
  }

}
