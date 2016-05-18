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
package se.litsec.swedisheid.opensaml.config;

import org.junit.Assert;
import org.junit.Test;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.saml.common.SAMLObjectBuilder;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;

public class OpenSAMLInitializerTest {

  @Test
  public void testOpenSAMLInitializer() throws Exception {
    
    // If the OpenSAML library isn't initialized, this shouldn't work ...
    try {
      this.createEntityDescriptor();
      Assert.fail("Expected exception since OpenSAML hasn't been initialized");
    }
    catch (Exception e) {      
    }
    
    // Now, initialize OpenSAML and try again ...
    
    OpenSAMLInitializer initializer = OpenSAMLInitializer.getInstance();
    initializer.setInitOpenSAML(true);
    initializer.initialize();

    Assert.assertTrue(initializer.isInitialized());

    EntityDescriptor ed = this.createEntityDescriptor();
    Assert.assertNotNull(ed);
  }

  private EntityDescriptor createEntityDescriptor() throws Exception {
    SAMLObjectBuilder<EntityDescriptor> builder = (SAMLObjectBuilder<EntityDescriptor>) XMLObjectProviderRegistrySupport.getBuilderFactory()
      .<EntityDescriptor> getBuilderOrThrow(EntityDescriptor.DEFAULT_ELEMENT_NAME);
    return builder.buildObject();
  }

}
