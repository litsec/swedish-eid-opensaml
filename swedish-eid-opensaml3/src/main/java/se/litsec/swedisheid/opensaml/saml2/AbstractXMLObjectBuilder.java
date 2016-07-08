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
package se.litsec.swedisheid.opensaml.saml2;

import org.opensaml.core.xml.XMLObject;

import se.litsec.swedisheid.opensaml.utils.SAMLUtils;

/**
 * Abstract base class for the builder pattern.
 * 
 * @author Martin Lindström (martin.lindstrom@litsec.se)
 *
 * @param <T>
 *          the type
 */
public abstract class AbstractXMLObjectBuilder<T extends XMLObject> implements XMLObjectBuilder<T> {

  /** The object that is being built. */
  private T object;

  /**
   * Constructor setting up the object to build.
   */
  public AbstractXMLObjectBuilder() {
    this.object = SAMLUtils.createSamlObject(this.getObjectType());
  }

  /**
   * The default implementation of this method assumes that the object has been built during assignment of its
   * attributes and elements so it simply returns the object.
   * <p>
   * Implementations that need to perform additional processing during the build step should override this method.
   * </p>
   */
  @Override
  public T build() {
    return this.object();
  }

  /**
   * Returns the object type.
   * 
   * @return the object type
   */
  protected abstract Class<T> getObjectType();

  /**
   * Returns the object being built.
   * 
   * @return the object
   */
  protected final T object() {
    return this.object;
  }

  /**
   * Assigns the object that we are building.
   * 
   * @param object
   *          the object to assign
   */
  protected final void setObject(T object) {
    this.object = object;
  }

}
