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

/**
 * Interface for a builder pattern according to:
 * 
 * <pre>
 * EntityDescriptorBuilder builder = new EntityDescriptorBuilder();
 * EntityDescriptor ed = builder.entityID("http://www.litsec.se").entityCategories(...)[...].build();
 * </pre>
 * 
 * @author Martin Lindström (martin.lindstrom@litsec.se)
 *
 * @param <T>
 *          the type
 */
public interface XMLObjectBuilder<T extends XMLObject> {

  /**
   * Builds the {@code XMLObject}.
   * <p>
   * If invoked several times the method <b>must</b> return the same object.
   * </p>
   * 
   * @return the built object
   */
  T build();

}
