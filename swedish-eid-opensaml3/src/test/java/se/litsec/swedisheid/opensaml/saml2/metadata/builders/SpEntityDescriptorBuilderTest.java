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
package se.litsec.swedisheid.opensaml.saml2.metadata.builders;

import org.junit.Test;

import se.litsec.swedisheid.opensaml.OpenSAMLTestBase;

/**
 * Test cases for {@code SpEntityDescriptorBuilder}.
 * 
 * @author Martin Lindström (martin.lindstrom@litsec.se)
 */
public class SpEntityDescriptorBuilderTest extends OpenSAMLTestBase {

  @Test
  public void testBuilder() throws Exception {
    
    SpEntityDescriptorBuilder builder = new SpEntityDescriptorBuilder();
    
    builder
      .entityID("http://www.litsec.se/sp")
      .entityCategories("http://id.elegnamnden.se/ec/1.0/loa3-pnr", "http://id.elegnamnden.se/ec/1.0/loa4-pnr")
      .wantAssertionsSigned(true)
      .build();
    
  }

}
