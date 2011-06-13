/*******************************************************************************
 * Copyright (c) 2011 Guillaume Hillairet.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Guillaume Hillairet - initial API and implementation
 *******************************************************************************/
package com.emftriple.datasources;

import org.eclipse.emf.common.util.URI;

/**
 * The {@link IDataSource} interface provides an abstraction over diverse kinds of RDF data sources.
 * 
 * @author <a href="mailto:g.hillairet at gmail.com">Guillaume Hillairet</a>
 * @since 0.8.0
 * 
 * @param <G> abstract type for RDF Graph
 * @param <T> abstract type for RDF Triple
 * @param <N> abstract type for RDF Node
 * @param <U> abstract type for RDF URI
 * @param <L> abstract type for RDF Literal
 */
public abstract interface IDataSource<G, T, N, U extends N, L extends N> {
	
	/**
	 * Returns true if the data source supports named graphs.
	 */
	boolean supportsNamedGraph();
	
	/**
	 * Returns true if the data source can be modified.
	 */
	boolean isMutable();
	
	/**
	 * Returns true if the data source supports SPARQL 1.1 Update Queries.
	 */
	boolean supportsUpdateQuery();
	
	/**
	 * Returns a {@link IResultSet} against a Select SPARQL Query. 
	 * 
	 * @param query to execute
	 * @return query execution value
	 */
	IResultSet<N,U,L> selectQuery(String query, String graphURI);
	
	/**
	 * Returns a {@link RDFGraph} against a Construct SPARQL Query.
	 * 
	 * @param query to execute
	 * @return query execution value
	 */
	G constructQuery(String query, String graphURI);

	/**
	 * Add result of a construct query in the graph given as parameter.
	 * 
	 * @param aQuery
	 * @param aGraph
	 */
	void constructQuery(String aQuery, String graphURI, G aGraph);
	
	/**
	 * Execute a describre query against the data source.
	 * 
	 * @param query to execute
	 * @return query execution value
	 */
	G describeQuery(String query, String graphURI);
	
	/**
	 * Add result of a describe query in the graph given as parameter.
	 * 
	 * @param aQuery
	 * @param aGraph
	 */
	void describeQuery(String aQuery, String graphURI, G aGraph);
	
	/**
	 * Returns the value obtained from the execution of an ask query against the 
	 * {@link IDataSource}
	 * 
	 * @param query to execute
	 * @return query execution value
	 */
	boolean askQuery(String query, String graphURI);

	/**
	 * Test if the current {@link IDataSource} supports transactions
	 * 
	 * @return true if transactions are supported
	 */
	boolean supportsTransaction();
		
	/**
	 * Returns true if the {@link IDataSource} if connected
	 * 
	 * @return true if connected
	 */
	boolean isConnected();
	
	/**
	 * Connect the {@link IDataSource}
	 */
	void connect();
	
	/**
	 * Disconnect the {@link IDataSource}
	 */
	void disconnect();
	
	/**
	 * Returns the graph identify by the URI. If URI is null, it 
	 * returns the default graph.
	 * 
	 * @param graphURI identifying the named graph.
	 * @return graph
	 */
	G getGraph(String graphURI);

	/**
	 * Adds the {@link Triple} from the {@link IDataSource}
	 * 
	 * @param triples to remove
	 */
	void add(Iterable<T> triples, String graphURI);

	/**
	 * Delete the content of the {@link IDataSource}.
	 */
	void delete(String graphURI);

	/**
	 * Removes the {@link Triple} from the {@link IDataSource}
	 * 
	 * @param triples to remove
	 */
	void remove(Iterable<T> triples, String graphURI);

	/**
	 * Registry for {@link IDataSource}
	 * 
	 * @author guillaume hillairet
	 * @since 0.8.0
	 */
	public interface Registry {
		
		public final static Registry INSTANCE = new DataSourceRegistry();
		
		/**
		 * Returns the data source identified by the URI.
		 * 
		 * @param <G> abstract type for RDF Graph
		 * @param <T> abstract type for RDF Triple
		 * @param <N> abstract type for RDF Node
		 * @param <U> abstract type for RDF URI
		 * @param <L> abstract type for RDF Literal
		 * @param uri identifying the data source
		 * 
		 * @return IDataSource
		 */
		<G, T, N, U extends N, L extends N> IDataSource<G, T, N, U, L> getDataSource(URI uri);
		
		/**
		 * Register the IDataSource to the given URI.
		 * 
		 * @param <G> abstract type for RDF Graph
		 * @param <T> abstract type for RDF Triple
		 * @param <N> abstract type for RDF Node
		 * @param <U> abstract type for RDF URI
		 * @param <L> abstract type for RDF Literal
		 * @param uri identifying the data source
		 * 
		 * @return IDataSource
		 */
		<G, T, N, U extends N, L extends N> void register(URI uri, IDataSource<G, T, N, U, L> dataSource);
		
	}
}
