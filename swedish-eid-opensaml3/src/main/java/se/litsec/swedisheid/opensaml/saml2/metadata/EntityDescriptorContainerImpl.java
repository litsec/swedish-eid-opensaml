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
import java.time.Duration;

import org.joda.time.DateTime;
import org.joda.time.chrono.ISOChronology;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;
import org.opensaml.xmlsec.signature.support.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

import se.litsec.swedisheid.opensaml.utils.SAMLUtils;

/**
 * Concrete implementation of the {@link EntityDescriptorContainer} interface.
 * 
 * @author Martin Lindström (martin)
 */
public class EntityDescriptorContainerImpl implements EntityDescriptorContainer {

  /** The default validity for metadata - one week. */
  public static final Duration DEFAULT_VALIDITY = Duration.ofDays(7);

  /**
   * The default update factor for the metadata - 0,75 (75%), i.e. update
   * "update the metadata when less than 75% of its original validity time remains".
   * 
   * @see #getUpdateFactor()
   */
  public static final float DEFAULT_UPDATE_FACTOR = 0.75f;

  /** Default size for the ID attribute string. */
  public static final int DEFAULT_ENTITY_DESCRIPTOR_ID_SIZE = 32;

  /** Logging instance. */
  private Logger logger = LoggerFactory.getLogger(EntityDescriptorContainerImpl.class);

  /** The encapsulated EntityDescriptor element. */
  protected EntityDescriptor entityDescriptor;

  /** The validity time for created entries (in minutes). */
  protected Duration validity = DEFAULT_VALIDITY;

  /** The update factor. */
  protected float updateFactor = DEFAULT_UPDATE_FACTOR;

  /** The size of the ID attribute string. */
  protected int idSize = DEFAULT_ENTITY_DESCRIPTOR_ID_SIZE;

  /**
   * The signature credentials for signing the metadata entry.
   * <p>
   * Note that the assigned entry must be "unlocked".
   * </p>
   */
  protected KeyStore.PrivateKeyEntry signatureCredentials;

  /**
   * Constructor assigning the encapsulated EntityDescriptor element.
   * 
   * @param entityDescriptor
   *          the {@code EntityDescriptor} object
   * @param signatureCredentials
   *          the signature credentials for signing the entity descriptor. May be {@code null}, but then no signing will
   *          be possible
   */
  public EntityDescriptorContainerImpl(EntityDescriptor entityDescriptor, KeyStore.PrivateKeyEntry signatureCredentials) {
    this.entityDescriptor = entityDescriptor;
    this.signatureCredentials = signatureCredentials;
  }

  /** {@inheritDoc} */
  @Override
  public EntityDescriptor getEntityDescriptor() {
    return this.entityDescriptor;
  }

  /** {@inheritDoc} */
  @Override
  public EntityDescriptor cloneEntityDescriptor() throws MarshallingException, UnmarshallingException {
    return XMLObjectSupport.cloneXMLObject(this.entityDescriptor);
  }

  /** {@inheritDoc} */
  @Override
  public boolean updateRequired(boolean signatureRequired) {
    if (!this.entityDescriptor.isValid() || (signatureRequired && !this.entityDescriptor.isSigned())) {
      return true;
    }
    if (this.entityDescriptor.getValidUntil() == null) {
      return true;
    }
    long expireInstant = this.entityDescriptor.getValidUntil().getMillis();
    long now = new DateTime(ISOChronology.getInstanceUTC()).getMillis();

    return (this.updateFactor * this.validity.toMillis()) > (expireInstant - now);
  }

  /** {@inheritDoc} */
  @Override
  public EntityDescriptor update(boolean sign) throws SignatureException, MarshallingException {

    // Reset the signature
    this.entityDescriptor.setSignature(null);

    // Generate a new ID.
    this.entityDescriptor.setID(SAMLUtils.generateIDAttribute("M", this.idSize));

    // Assign the validity.
    DateTime now = new DateTime(ISOChronology.getInstanceUTC());
    DateTime validUntil = now.plusSeconds((int) this.validity.getSeconds());
    this.entityDescriptor.setValidUntil(validUntil);

    logger.debug("Entity descriptor for '{}' was updated with ID '{}' and validUntil '{}'",
      this.entityDescriptor.getEntityID(), this.entityDescriptor.getID(), this.entityDescriptor.getValidUntil().toString());

    return sign ? this.sign() : this.entityDescriptor;
  }

  /** {@inheritDoc} */
  @Override
  public EntityDescriptor sign() throws SignatureException, MarshallingException {

    logger.trace("Signing entity descriptor for entityID '{}' ...", this.entityDescriptor.getEntityID());

    if (this.entityDescriptor.getID() == null || this.getEntityDescriptor().getValidUntil() == null) {
      return this.update(true);
    }

    SAMLUtils.sign(this.entityDescriptor, this.signatureCredentials);

    logger.debug("Entity descriptor for entityID '{}' successfully signed.", this.entityDescriptor.getEntityID());

    return this.entityDescriptor;
  }

  /** {@inheritDoc} */
  @Override
  public Element marshall() throws MarshallingException {
    return SAMLUtils.marshall(this.entityDescriptor);
  }

  /** {@inheritDoc} */
  @Override
  public Duration getValidity() {
    return this.validity;
  }

  /**
   * Assigns the duration of the validity that the encapsulated {@code EntityDescriptor} should have.
   * <p>
   * The default value is {@value #DEFAULT_VALIDITY}.
   * </p>
   * 
   * @param validity
   *          the validity
   */
  public void setValidity(Duration validity) {
    this.validity = validity;
  }

  /** {@inheritDoc} */
  @Override
  public float getUpdateFactor() {
    return this.updateFactor;
  }

  /**
   * Assigns the factor (between 0 and 1) that is used to compute whether it is time to update the contained
   * {@code EntityDescriptor}.
   * <p>
   * The default value is {@link #DEFAULT_UPDATE_FACTOR}.
   * </p>
   * 
   * @param updateFactor
   *          the update factor
   * @see #getUpdateFactor()
   */
  public void setUpdateFactor(float updateFactor) {
    if (updateFactor < 0 || updateFactor > 1) {
      throw new IllegalArgumentException("Supplied updateFactor must be greater than 0 and equal or less than 1");
    }
    this.updateFactor = updateFactor;
  }

  /**
   * Returns the size of the ID attribute that is generated.
   * 
   * @return the size
   */
  public int getIdSize() {
    return this.idSize;
  }

  /**
   * Assigns the size of the ID attribute that is generated.
   *
   * <p>
   * The default value is {@link #DEFAULT_ENTITY_DESCRIPTOR_ID_SIZE}.
   * </p>
   * 
   * @param idSize
   *          the size
   */
  public void setIdSize(int idSize) {
    this.idSize = idSize;
  }

}
