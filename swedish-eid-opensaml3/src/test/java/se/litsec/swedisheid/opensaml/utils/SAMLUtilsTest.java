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
package se.litsec.swedisheid.opensaml.utils;

import javax.xml.namespace.QName;

import org.junit.Assert;
import org.junit.Test;
import org.opensaml.saml.common.xml.SAMLConstants;
import org.opensaml.saml.saml2.core.Assertion;

import se.litsec.swedisheid.opensaml.OpenSAMLTestBase;

/**
 * Test cases for the {@link SAMLUtils} class.
 * 
 * @author Martin Lindström (martin.lindstrom@litsec.se)
 */
public class SAMLUtilsTest extends OpenSAMLTestBase {

  @Test
  public void testCreateXmlObject() throws Exception {
         
    Assertion assertion = SAMLUtils.createSamlObject(Assertion.class);
    Assert.assertNotNull(assertion);
    Assert.assertEquals(Assertion.DEFAULT_ELEMENT_NAME, assertion.getElementQName());
    
    QName qname = new QName(SAMLConstants.SAML20_NS, "OtherAssertion", SAMLConstants.SAML20_PREFIX);
    
    assertion = SAMLUtils.createSamlObject(Assertion.class, qname);
    Assert.assertNotNull(assertion);
    Assert.assertEquals(qname, assertion.getElementQName());
  }
  
}
