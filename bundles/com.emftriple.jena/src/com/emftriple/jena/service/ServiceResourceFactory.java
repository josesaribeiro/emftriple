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
package com.emftriple.jena.service;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryImpl;

import com.emftriple.resource.ETripleResourceImpl;

public class ServiceResourceFactory extends ResourceFactoryImpl {
	@Override
	public Resource createResource(URI uri) {
		return new ETripleResourceImpl(uri, new JenaService("sparql", getLocation(uri)));
	}

	private String getLocation(URI uri) {
		if (!uri.scheme().equals("emftriple"))
			throw new IllegalArgumentException("URI is not well formed.");
		
		return uri.host();
	}
}
