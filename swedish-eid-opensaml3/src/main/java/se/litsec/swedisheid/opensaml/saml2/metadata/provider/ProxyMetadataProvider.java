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
package se.litsec.swedisheid.opensaml.saml2.metadata.provider;

import java.util.ArrayList;
import java.util.List;

import org.opensaml.saml.metadata.resolver.MetadataResolver;
import org.opensaml.saml.metadata.resolver.filter.MetadataFilter;
import org.opensaml.saml.metadata.resolver.filter.impl.MetadataFilterChain;
import org.opensaml.saml.metadata.resolver.impl.AbstractMetadataResolver;

import net.shibboleth.utilities.java.support.component.ComponentInitializationException;
import net.shibboleth.utilities.java.support.component.ComponentSupport;

/**
 * A metadata provider that is constructed by assigning an OpenSAML {@link MetadataResolver} instance.
 * 
 * @author Martin Lindström (martin.lindstrom@litsec.se)
 */
public class ProxyMetadataProvider extends AbstractMetadataProvider {

  /** The metadata resolver that we wrap in this class. */
  private AbstractMetadataResolver metadataResolver;

  /**
   * Constructor assigning the OpenSAML metadata resolver that this instance should proxy.
   * <p>
   * The supplied instance must extend the {@link AbstractMetadataResolver} class.
   * </p>
   * 
   * @param metadataResolver
   *          the metadata resolver to proxy
   */
  public ProxyMetadataProvider(MetadataResolver metadataResolver) {
    if (metadataResolver instanceof AbstractMetadataResolver) {
      this.metadataResolver = (AbstractMetadataResolver) metadataResolver;
    }
    else {
      throw new IllegalArgumentException("Supplied metadata resolver must extend AbstractMetadataResolver");
    }
  }
  
  /** {@inheritDoc} */
  @Override
  public MetadataResolver getMetadataResolver() {
    return this.metadataResolver;
  }

  /** {@inheritDoc} */  
  @Override
  protected void createMetadataResolver(boolean requireValidMetadata, boolean failFastInitialization, MetadataFilter filter) {
    ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
    
    this.metadataResolver.setRequireValidMetadata(requireValidMetadata);
    this.metadataResolver.setFailFastInitialization(failFastInitialization);
    MetadataFilter installedFilter = this.metadataResolver.getMetadataFilter();
    if (installedFilter == null) {
      this.metadataResolver.setMetadataFilter(filter);
    }
    else { 
      List<MetadataFilter> chain = new ArrayList<MetadataFilter>();
      if (filter instanceof MetadataFilterChain) {
        chain.addAll(((MetadataFilterChain) installedFilter).getFilters());
      }
      else {
        chain.add(filter);
      }      
      if (installedFilter instanceof MetadataFilterChain) {
        chain.addAll(((MetadataFilterChain) installedFilter).getFilters());
      }
      else {
        chain.add(installedFilter);
      }
      MetadataFilterChain newFilter = new MetadataFilterChain();
      newFilter.setFilters(chain);
      this.metadataResolver.setMetadataFilter(newFilter);      
    }
  }

  /** {@inheritDoc} */  
  @Override
  protected void initializeMetadataResolver() throws ComponentInitializationException {
    this.metadataResolver.initialize();
  }

  /** {@inheritDoc} */  
  @Override
  protected void destroyMetadataResolver() {
    this.metadataResolver.destroy();
  }

}
