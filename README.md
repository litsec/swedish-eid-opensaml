![Logo](https://github.com/litsec/eidas-opensaml/blob/master/docs/img/litsec-small.png)

------

# swedish-eid-opensaml

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/se.litsec.opensaml.sweid/swedish-eid-opensaml3/badge.svg)](https://maven-badges.herokuapp.com/maven-central/se.litsec.opensaml.sweid/swedish-eid-opensaml3)

OpenSAML extensions for the Swedish eID Framework

This open source package is an extension to OpenSAML 3.X that offers interfaces and classes for the Swedish eID Framework, see https://github.com/elegnamnden/technical-framework.

The library contains support for the following functionality:

* Attribute definitions according to the [Attribute Specification for the Swedish eID Framework](http://elegnamnden.github.io/technical-framework/latest/ELN-0604_-_Attribute_Specification_for_the_Swedish_eID_Framework.html) specification.

* Mapping of level of assurance URI:s as defined by the Swedish eID Framework ([Registry for identifiers assigned by the Swedish e-identification board](http://elegnamnden.github.io/technical-framework/latest/ELN-0602_-_Deployment_Profile_for_the_Swedish_eID_Framework.html)).

* Representation of entity categories as defined in the [Entity Categories for the Swedish eID Framework](http://elegnamnden.github.io/technical-framework/latest/ELN-0606_-_Entity_Categories_for_the_Swedish_eID_Framework.html) specification.

* Support for the `SignMessage` extension type, including utility classes for building sign message extensions and for decrypting sign messages. See the [DSS Extension for Federated Central Signing Services](http://elegnamnden.github.io/technical-framework/latest/ELN-0609_-_DSS_Extension_for_Federated_Signing_Services.html) specification.

* Support for the `SADRequest` extension type as well as the SAD JWT. The SAD supports also includes a SAD factory for creating and signing a SAD JWT and a SAD parser with validation support. See the [Signature Activation Protocol for Federated Signing](http://elegnamnden.github.io/technical-framework/updates/ELN-0613_-_Signature_Activation_Protocol.html) specification.

* Validation support for validating SAML responses according to the [Deployment Profile for the Swedish eID Framework](http://elegnamnden.github.io/technical-framework/latest/ELN-0602_-_Deployment_Profile_for_the_Swedish_eID_Framework.html) specification.

Java API documentation of the swedish-eid-opensaml library is found at [https://litsec.github.io/swedish-eid-opensaml](https://litsec.github.io/swedish-eid-opensaml/).

Generated project information is found at [https://litsec.github.io/swedish-eid-opensaml/site](https://litsec.github.io/swedish-eid-opensaml/site).

------

Copyright &copy; 2016-2018, [Litsec AB](http://www.litsec.se). Licensed under version 2.0 of the [Apache License](http://www.apache.org/licenses/LICENSE-2.0).


