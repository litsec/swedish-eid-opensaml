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
package se.litsec.swedisheid.opensaml.utils.spring;

import org.opensaml.core.xml.XMLObject;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

import se.litsec.swedisheid.opensaml.utils.SAMLUtils;

/**
 * Abstract base class for factories for {@code XMLObject} classes.
 * 
 * @author Martin Lindström (martin.lindstrom@litsec.se)
 *
 * @param <T>
 *          the {@code XMLObject} type
 */
public abstract class AbstractXMLObjectFactoryBean<T extends XMLObject> extends AbstractFactoryBean<T> {

  /** The resource to read from. */
  private Resource resource;

  /**
   * Assigns the resource holding the XML representing the XMLObject that we want to create
   * 
   * @param resource
   *          the resource
   */
  public void setResource(Resource resource) {
    this.resource = resource;
  }

  /** {@inheritDoc} */
  @SuppressWarnings("unchecked")
  @Override
  protected final T createInstance() throws Exception {
    return SAMLUtils.unmarshall(this.resource.getInputStream(), (Class<T>) this.getObjectType());
  }

  /** {@inheritDoc} */
  @Override
  public void afterPropertiesSet() throws Exception {
    super.afterPropertiesSet();
    Assert.notNull(this.resource, "Property 'resource' must be assigned");
  }

}
