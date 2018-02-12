/*
 * Copyright 2016-2018 Litsec AB
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package se.litsec.swedisheid.opensaml;

import java.io.IOException;

import org.junit.BeforeClass;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.springframework.core.io.Resource;

import net.shibboleth.utilities.java.support.xml.XMLParserException;
import se.litsec.opensaml.config.OpenSAMLInitializer;

/**
 * Abstract base class that initializes OpenSAML for test classes.
 * 
 * @author Martin Lindström (martin.lindstrom@litsec.se)
 */
public abstract class OpenSAMLTestBase {

  /**
   * Initializes the OpenSAML library.
   * 
   * @throws Exception
   *           for init errors
   */
  @BeforeClass
  public static void initializeOpenSAML() throws Exception {
    OpenSAMLInitializer bootstrapper = OpenSAMLInitializer.getInstance();
    if (!bootstrapper.isInitialized()) {
      bootstrapper.initialize();
    }
  }

  /**
   * Reads the contents from a resource and unmarshalls it to an {@code XMLObject}.
   * 
   * @param resource
   *          the resource to read
   * @return an {@code XMLObject}
   * @throws XMLParserException
   *           for parsing errors
   * @throws UnmarshallingException
   *           for unmarshalling errors
   * @throws IOException
   *           if the file can not be read
   */
  protected XMLObject xmlObjectFromResource(Resource resource) throws XMLParserException, UnmarshallingException, IOException {
    return XMLObjectSupport.unmarshallFromInputStream(XMLObjectProviderRegistrySupport.getParserPool(), resource.getInputStream());
  }

}
