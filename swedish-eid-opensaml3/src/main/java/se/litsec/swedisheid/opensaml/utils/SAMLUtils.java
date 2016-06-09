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

import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Optional;
import java.util.Random;

import javax.xml.namespace.QName;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.XMLObjectBuilder;
import org.opensaml.core.xml.XMLObjectBuilderFactory;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.common.SignableSAMLObject;
import org.opensaml.saml.saml2.core.Extensions;
import org.opensaml.security.credential.Credential;
import org.opensaml.security.x509.BasicX509Credential;
import org.opensaml.xmlsec.SecurityConfigurationSupport;
import org.opensaml.xmlsec.SignatureSigningParameters;
import org.opensaml.xmlsec.criterion.SignatureSigningConfigurationCriterion;
import org.opensaml.xmlsec.impl.BasicSignatureSigningConfiguration;
import org.opensaml.xmlsec.impl.BasicSignatureSigningParametersResolver;
import org.opensaml.xmlsec.signature.support.SignatureException;
import org.opensaml.xmlsec.signature.support.SignatureSupport;
import org.w3c.dom.Element;

import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import net.shibboleth.utilities.java.support.resolver.ResolverException;
import net.shibboleth.utilities.java.support.xml.XMLParserException;

/**
 * Utility methods for working with OpenSAML.
 * 
 * @author Martin Lindström (martin.lindstrom@litsec.se)
 */
public class SAMLUtils {

  /** The builder factory for XML objects. */
  private static XMLObjectBuilderFactory builderFactory = XMLObjectProviderRegistrySupport.getBuilderFactory();
  
  /** Possible chars in the strings. */
  private static final char[] _idChars = "abcdefghijklmnopqrstuvxyzABCDEFGHIJKLMNOPQRSTUVXYZ0123456789".toCharArray();

  /** Randomizer for generating authentication identifiers. */
  private static Random random = new Random();
  
  static {
    random.setSeed(System.currentTimeMillis());
  }

  /**
   * Utility method for creating an OpenSAML object using the default element name of the class.
   * <p>
   * Note: The field DEFAULT_ELEMENT_NAME of the given class will be used as the object's element name.
   * </p>
   * 
   * @param clazz
   *          the class to create
   * @return the XML object
   * @see #createSamlObject(Class, QName)
   */
  public static <T extends XMLObject> T createSamlObject(Class<T> clazz) {
    return createSamlObject(clazz, getDefaultElementName(clazz));
  }

  /**
   * Utility method for creating an OpenSAML object given its element name.
   * 
   * @param clazz
   *          the class to create
   * @param elementName
   *          the element name for the XML object to create
   * @return the XML object
   */
  public static <T extends XMLObject> T createSamlObject(Class<T> clazz, QName elementName) {
    if (!XMLObject.class.isAssignableFrom(clazz)) {
      throw new RuntimeException(String.format("%s is not a XMLObject class", clazz.getName()));
    }
    XMLObjectBuilder<? extends XMLObject> builder = builderFactory.getBuilder(elementName);
    if (builder == null) {
      // No builder registered for the given element name. Try creating a builder for the default element name.
      builder = builderFactory.getBuilder(getDefaultElementName(clazz));
    }
    Object object = builder.buildObject(elementName);
    return clazz.cast(object);
  }

  /**
   * Returns the default element name for the supplied class
   * 
   * @param clazz
   *          class to check
   * @return the default QName
   */
  public static <T extends XMLObject> QName getDefaultElementName(Class<T> clazz) {
    try {
      return (QName) clazz.getDeclaredField("DEFAULT_ELEMENT_NAME").get(null);
    }
    catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException | SecurityException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Returns the builder object that can be used to create objects of the supplied class type.
   * <p>
   * Note: The field DEFAULT_ELEMENT_NAME of the given class will be used as the object's element name.
   * </p>
   * 
   * @param clazz
   *          the class which we want a builder for
   * @return a builder object
   * @see #getBuilder(QName)
   */
  public static <T extends XMLObject> XMLObjectBuilder<T> getBuilder(Class<T> clazz) {
    return getBuilder(getDefaultElementName(clazz));
  }

  /**
   * Returns the builder object that can be used to build object for the given element name.
   * 
   * @param elementName
   *          the element name for the XML object that the builder should return
   * @return a builder object
   */
  @SuppressWarnings("unchecked")
  public static <T extends XMLObject> XMLObjectBuilder<T> getBuilder(QName elementName) {
    return (XMLObjectBuilder<T>) builderFactory.getBuilder(elementName);
  }

  /**
   * Marshalls the supplied {@code XMLObject} into an {@code Element}.
   * 
   * @param object
   *          the object to marshall
   * @return an XML element
   * @throws MarshallingException
   *           for marshalling errors
   */
  public static <T extends XMLObject> Element marshall(T object) throws MarshallingException {
    return XMLObjectProviderRegistrySupport.getMarshallerFactory().getMarshaller(object).marshall(object);
  }

  /**
   * Unmarshalls the supplied element into the given type.
   * 
   * @param xml
   *          the DOM (XML) to unmarshall
   * @param targetClass
   *          the required class
   * @return an {@code XMLObject} of the given type
   * @throws UnmarshallingException
   *           for unmarshalling errors
   */
  public static <T extends XMLObject> T unmarshall(Element xml, Class<T> targetClass) throws UnmarshallingException {
    XMLObject xmlObject = XMLObjectProviderRegistrySupport.getUnmarshallerFactory().getUnmarshaller(xml).unmarshall(xml);
    return targetClass.cast(xmlObject);
  }

  /**
   * Unmarshalls the supplied input stream into the given type.
   * 
   * @param inputStream
   *          the input stream of the XML resource
   * @param targetClass
   *          the required class
   * @return an {@code XMLObject} of the given type
   * @throws XMLParserException
   *           for XML parsing errors
   * @throws UnmarshallingException
   *           for unmarshalling errors
   */
  public static <T extends XMLObject> T unmarshall(InputStream inputStream, Class<T> targetClass) throws XMLParserException,
      UnmarshallingException {
    return unmarshall(XMLObjectProviderRegistrySupport.getParserPool().parse(inputStream).getDocumentElement(), targetClass);
  }

  /**
   * Finds the first extension matching the supplied type.
   * 
   * @param extensions
   *          the {@link Extensions} to search
   * @param clazz
   *          the extension type
   * @return the matching extension
   */
  public static <T> Optional<T> getExtension(Extensions extensions, Class<T> clazz) {
    if (extensions == null) {
      return Optional.empty();
    }
    return extensions.getOrderedChildren()
      .stream()
      .filter(e -> clazz.isAssignableFrom(e.getClass()))
      .map(e -> clazz.cast(e))
      .findFirst();
  }

  /**
   * Signs the supplied SAML object using the credentials.
   * 
   * @param object
   *          object to sign
   * @param signingCredentials
   *          signature credentials
   * @throws SignatureException
   *           for signature creation errors
   * @see #sign(SignableSAMLObject, Credential)
   */
  public static <T extends SignableSAMLObject> void sign(T object, KeyStore.PrivateKeyEntry signingCredentials) throws SignatureException {
    sign(object, new BasicX509Credential((X509Certificate) signingCredentials.getCertificate(), signingCredentials.getPrivateKey()));
  }

  /**
   * Signs the supplied SAML object using the credentials.
   * 
   * @param object
   *          object to sign
   * @param signingCredentials
   *          signature credentials
   * @throws SignatureException
   *           for signature creation errors
   * @see #sign(SignableSAMLObject, java.security.KeyStore.PrivateKeyEntry)
   */
  public static <T extends SignableSAMLObject> void sign(T object, Credential signingCredentials) throws SignatureException {
    try {
      object.setSignature(null);

      BasicSignatureSigningConfiguration signatureCreds = new BasicSignatureSigningConfiguration();
      signatureCreds.setSigningCredentials(Arrays.asList(signingCredentials));

      BasicSignatureSigningParametersResolver signatureParametersResolver = new BasicSignatureSigningParametersResolver();
      CriteriaSet criteriaSet = new CriteriaSet(new SignatureSigningConfigurationCriterion(SecurityConfigurationSupport
        .getGlobalSignatureSigningConfiguration(), signatureCreds));

      SignatureSigningParameters parameters = signatureParametersResolver.resolveSingle(criteriaSet);
      SignatureSupport.signObject(object, parameters);
    }
    catch (ResolverException | org.opensaml.security.SecurityException | MarshallingException e) {
      throw new SignatureException(e);
    }
  }
  
  /**
   * Generates a random identifier for usage as an ID.
   * 
   * @param number
   *          of chars in the returned string
   * @return a random identifier
   */
  public static String generateIDAttribute(int size) {
    char[] r = new char[size];
    for (int i = 0; i < size; i++) {
      r[i] = _idChars[random.nextInt(_idChars.length)];
    }
    return new String(r);
  }
  
  /**
   * Generates a random identifier with a supplied prefix for usage as an ID.
   * @param prefix prefix string
   * @param size number of chars of the resulting string
   * @return a random identifier
   */
  public static String generateIDAttribute(String prefix, int size) {
    StringBuilder sb = prefix != null ? new StringBuilder(prefix) : new StringBuilder();
    sb.append(generateIDAttribute(size - sb.length()));
    return sb.toString();
  }

  // Hidden constructor
  private SAMLUtils() {
  }

}
