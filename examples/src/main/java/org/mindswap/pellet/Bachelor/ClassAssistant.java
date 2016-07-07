// Portions Copyright (c) 2006 - 2008, Clark & Parsia, LLC. <http://www.clarkparsia.com>
// Clark & Parsia, LLC parts of this source code are available under the terms of the Affero General Public License v3.
//
// Please see LICENSE.txt for full license terms, including the availability of proprietary exceptions.
// Questions, comments, or requests for clarification: licensing@clarkparsia.com

package org.mindswap.pellet.Bachelor;

import java.util.Set;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.PrefixManager;
import org.semanticweb.owlapi.util.DefaultPrefixManager;

import com.clarkparsia.owlapi.explanation.PelletExplanation;
import com.clarkparsia.owlapi.explanation.io.manchester.ManchesterSyntaxExplanationRenderer;
import com.clarkparsia.owlapiv3.OWL;
import com.clarkparsia.pellet.owlapiv3.PelletReasoner;
import com.clarkparsia.pellet.owlapiv3.PelletReasonerFactory;

import com.clarkparsia.owlapi.explanation.PelletExplanation;
import com.clarkparsia.owlapiv3.OWL;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;

/**
 * Example to demonstrate how to use the reasoner for queries related to
 * individuals. Exact same functionality is shown for both Jena and OWL-API
 * interfaces.
 * 
 * @author Evren Sirin
 */
public class ClassAssistant {
	static int count = 0;

	public static void main(String[] args) throws Exception {
		// System.out.println("Results using Jena interface");
		// System.out.println("----------------------------");
		// runWithJena();

		System.out.println("Results using OWL-API interface");
		System.out.println("-------------------------------");
		// runWithOWLAPI();

		printInstances();
	}

	public static void printInstances() throws OWLOntologyCreationException {
		String NS = "http://www.w3.org/TR/2003/PR-owl-guide-20031209/wine#";
		String file = "file:src/main/resources/data/wineTunaSubclass3.owl";
		
		OWLOntologyManager manager = OWL.manager;
		OWLOntology ontology = manager.loadOntology(IRI.create(file));
		OWLDataFactory fac = manager.getOWLDataFactory();
		PelletReasoner reasoner = PelletReasonerFactory.getInstance().createReasoner(ontology);
		
		OWLClass thing = OWL.Class("http://www.w3.org/2002/07/owl#Thing");
		
		
		Set<OWLNamedIndividual> individuals = reasoner.getInstances(thing, false).getFlattened();
		System.out.println(individuals.size());
		
		for (OWLNamedIndividual ind : individuals) {
			int c = 0;
			IRI cIRI = ind.getIRI();
			System.out.println(cIRI.toString().substring(53));
			Set<OWLAnnotationAssertionAxiom> list = ontology.getAnnotationAssertionAxioms(cIRI);
			
			
		}

	}

	// public static void runWithJena() {
	// // ontology that will be used
	//
	// // String ont = "http://www.mindswap.org/2004/owl/mindswappers#";
	// String ont = "file:src/main/resources/data/wineTunaSubclass3.owl";
	// String NS = "http://www.w3.org/TR/2003/PR-owl-guide-20031209/wine#";
	//
	// // load the ontology with its imports and no reasoning
	// OntModel model =
	// ModelFactory.createOntologyModel(PelletReasonerFactory.THE_SPEC);
	// model.read(ont);
	// // load the model to the reasoner
	// model.prepare();
	//
	// // create property and resources to query the reasoner
	// // OntClass man = model.getOntClass("http://xmlns.com/foaf/0.1/adult");
	// // Property workHomepage =
	// // model.getProperty("http://xmlns.com/foaf/0.1/workInfoHomepage");
	//
	// //Property foafName =
	// model.getProperty("http://cohse.semanticweb.org/ontologies/people#has_father");
	// //OntClass wine = model.getOntClass(NS + "WineFlavor");
	//
	// try {
	// ExtendedIterator<OntProperty> properties =
	// (ExtendedIterator<OntProperty>) model.listAllOntProperties();
	// while (properties.hasNext()) {
	// OntProperty prop = properties.next();
	// System.out.println(prop.getLocalName() );
	//
	// }
	//
	//// ExtendedIterator classes = model.listClasses();
	//// while (classes.hasNext()) {
	//// OntClass essaClasse = (OntClass) classes.next();
	//// System.out.println(essaClasse.getLocalName());
	//// // System.out.println("Class URI: " + essaClasse.getURI());
	//// // OntClass cla = inf.getOntClass(URI + vClasse);
	//// // for (Iterator i = cla.listSubClasses(); i.hasNext();) {
	//// // OntClass c = (OntClass) i.next();
	//// // System.out.print(" " + c.getLocalName() + " " + "\n");
	//// }
	// } catch (Exception e) {
	//
	// System.out.println(e.getMessage());
	//
	// }
	//
	// // get all instances of Person class
	// // Iterator<?> i = man.listInstances();
	// // while( i.hasNext() ) {
	// // Individual ind = (Individual) i.next();
	// //
	// // // get the info about this specific individual
	// // //System.out.println(ind.getPropertyValue( foafName ) +
	// // "###################");
	// // // String name = ((Literal) ind.getPropertyValue( foafName
	// // )).getString();
	// // Resource type = ind.getRDFType();
	// //
	// // Resource homepage = (Resource) ind.getPropertyValue(foafName);
	//
	// // print the results
	// // System.out.println("Name: " + name);
	// // System.out.println("Type: " + type.getLocalName() +"\n Name: " +
	// // ind.getLabel(null));
	// // if(homepage == null)
	// // System.out.println("Homepage: Unknown");
	// // else
	// // System.out.println("Homepage: " + homepage);
	// System.out.println();
	// }
	// }

	public static void runWithOWLAPI() throws OWLException {
		String ont = "file:src/main/resources/data/people+pets.owl";
		String NS = "http://cohse.semanticweb.org/ontologies/people#";
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		OWLDataFactory factory = manager.getOWLDataFactory();
		OWLOntology ontology = manager.loadOntology(IRI.create(ont));
		PelletReasoner reasoner = com.clarkparsia.pellet.owlapiv3.PelletReasonerFactory.getInstance()
				.createReasoner(ontology);
		OWLClass Person = factory.getOWLClass(IRI.create(NS + "person"));
		Set<OWLNamedIndividual> individuals = reasoner.getInstances(Person, false).getFlattened();
		for (OWLNamedIndividual ind : individuals) {
			// get the info about this specific individual
			IRI cIRI = ind.getIRI();

			for (OWLAnnotationAssertionAxiom a : ontology.getAnnotationAssertionAxioms(cIRI)) {
				if (a.getProperty().isLabel()) {
					if (a.getValue() instanceof OWLLiteral) {
						OWLLiteral val = (OWLLiteral) a.getValue();
						System.out.println(Person + " labelled " + val.getLiteral());
						System.out.println(count++);
					}
				}

				System.out.println();
			}
		}
	}

}
