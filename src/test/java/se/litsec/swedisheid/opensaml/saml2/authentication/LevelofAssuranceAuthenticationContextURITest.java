/*
 * Copyright 2016-2021 Litsec AB
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
package se.litsec.swedisheid.opensaml.saml2.authentication;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Test;

import se.litsec.swedisheid.opensaml.saml2.authentication.LevelofAssuranceAuthenticationContextURI.LoaEnum;

/**
 * Test cases for {@link LevelofAssuranceAuthenticationContextURI}.
 * 
 * @author Martin Lindström (martin.lindstrom@litsec.se)
 */
public class LevelofAssuranceAuthenticationContextURITest {

  @Test
  public void testParse() {
    List<String> loas = Arrays.asList(LoaEnum.values()).stream().map(LoaEnum::getUri).collect(Collectors.toList());
    for (String loa : loas) {
      Assert.assertEquals(loa, LoaEnum.parse(loa).getUri());
    }
    
    Assert.assertNull("Expected null for unknown LoA", LoaEnum.parse("https://id.loa.unknown"));
  }
    
}
