package com.emftriple.junit.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.junit.Before;
import org.junit.Test;

import com.emftriple.ETriple;
import com.emftriple.jena.JenaModule;
import com.emftriple.jena.JenaTDB;
import com.emftriple.jena.TDBResourceFactory;
import com.emftriple.query.ETripleQueryImpl;
import com.junit.model.ModelFactory;
import com.junit.model.ModelPackage;
import com.junit.model.Person;

public class BasicTest {

	Resource resource;
	ResourceSet resourceSet;
	
	@Before
	public void tearUp() {
		Resource.Factory.Registry.INSTANCE.getProtocolToFactoryMap().put("emftriple", new TDBResourceFactory());
		ETriple.init(ModelPackage.eINSTANCE, new JenaModule(JenaTDB.class, null));
		
		resourceSet = new ResourceSetImpl();
		resource = resourceSet.createResource(URI.createURI("emftriple://data?graph=http://graph"));
	}
	
	@Test
	public void testCreateAndStore() throws IOException {
		Person person = ModelFactory.eINSTANCE.createPerson();
		person.setName("John Doe");
		
		resource.getContents().add(person);
		resource.save(null);
	}
	
	@Test
	public void testLoadEmptyResource() throws IOException {
		assertNotNull(resource);
		resource.load(null);
		
		assertFalse(resource.getContents().isEmpty());
		EObject obj = resource.getContents().get(0);
		assertTrue(obj instanceof Person);
		
		System.out.println( ((Person) obj).getName() );
	}
	
	@Test
	public void testBasicQuery() throws IOException {
		Resource query = resourceSet.createResource(new ETripleQueryImpl("select ?s where { ?s ?p ?o }",
				URI.createURI("emftriple://data?graph=http://graph")).toURI());
		query.load(null);
		
		assertFalse(query.getContents().isEmpty());
		EObject obj = query.getContents().get(0);
		assertTrue(obj instanceof Person);
		
		System.out.println( ((Person) obj).getName() );
	}
	
}
