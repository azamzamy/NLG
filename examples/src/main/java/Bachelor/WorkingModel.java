// Copyright (c) 2006 - 2008, Clark & Parsia, LLC. <http://www.clarkparsia.com>
// This source code is available under the terms of the Affero General Public
// License v3.
//
// Please see LICENSE.txt for full license terms, including the availability of
// proprietary exceptions.
// Questions, comments, or requests for clarification: licensing@clarkparsia.com

package org.mindswap.pellet.Bachelor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import com.clarkparsia.owlapi.explanation.PelletExplanation;
import com.clarkparsia.owlapi.explanation.io.manchester.ManchesterSyntaxExplanationRenderer;
import com.clarkparsia.owlapiv3.OWL;
import com.clarkparsia.pellet.owlapiv3.PelletReasoner;
import com.clarkparsia.pellet.owlapiv3.PelletReasonerFactory;

/**
 * <p>
 * Title: ExplanationExample
 * </p>
 * <p>
 * Description: This program shows how to use Pellet's explanation service
 * </p>
 * <p>
 * Copyright: Copyright (c) 2008
 * </p>
 * <p>
 * Company: Clark & Parsia, LLC. <http://www.clarkparsia.com>
 * </p>
 * 
 * @author Markus Stocker
 * @author Evren Sirin
 */
public class WorkingModel {

	private static final String file = "file:src/main/resources/data/people+pets.owl";
	private static final String NS = "http://cohse.semanticweb.org/ontologies/people#";

	@SuppressWarnings("deprecation")
	public void run() throws OWLOntologyCreationException, OWLException, IOException {

		PelletExplanation.setup();

		// The renderer is used to pretty print explanation
		ManchesterSyntaxExplanationRenderer renderer = new ManchesterSyntaxExplanationRenderer();
		// The writer used for the explanation rendered
		PrintWriter out = new PrintWriter(System.out);
		renderer.startRendering(out);

		// Create an OWLAPI manager that allows to load an ontology file and
		// create OWLEntities
		OWLOntologyManager manager = OWL.manager;
		OWLOntology ontology = manager.loadOntology(IRI.create(file));

		// Create the reasoner and load the ontology
		PelletReasoner reasoner = PelletReasonerFactory.getInstance().createReasoner(ontology);

		// Create an explanation generator
		PelletExplanation expGen = new PelletExplanation(reasoner);

		// Create some concepts
		OWLClass madCow = OWL.Class(NS + "mad+cow");
		OWLClass animalLover = OWL.Class(NS + "animal+lover");
		OWLClass petOwner = OWL.Class(NS + "pet+owner");
		OWLClass dailyMirror = OWL.Class(NS + "daily+mirror");

		OWLClass person = OWL.Class(NS + "person");
		OWLClass oldLady = OWL.Class(NS + "old+lady");
		OWLClass male = OWL.Class(NS + "male");
		OWLClass tabloid = OWL.Class(NS + "tabloid");
		OWLClass thing = OWL.Class("http://www.w3.org/2002/07/owl#Thing");

		int count = 0;

		// Explain why mad cow is an unsatisfiable concept
//		Set<Set<OWLAxiom>> exp = expGen.getUnsatisfiableExplanations(madCow);
//		out.println("Why is " + madCow.getIRI().getFragment() + " concept unsatisfiable?");
//		renderer.render(exp);
//		out.println("---------------------------------------------------");

		// Explain why animal lover is a sub class of pet owner
		// exp = expGen.getSubClassExplanations( animalLover, petOwner );
		// out.println( "Why is " + animalLover.getIRI().getFragment() + "
		// subclass of " + petOwner.getIRI().getFragment() + "?" );
		// renderer.render( exp );
		// out.println("---------------------------------------------------");

		Set<OWLNamedIndividual> individuals = reasoner.getInstances(person, false).getFlattened();
		out.println("person instances: " + individuals.size());

		for (OWLNamedIndividual ind : individuals) {
			out.println("loop " + count++);
			IRI cIRI = ind.getIRI();
			// out.println(cIRI);
			//out.println(ontology.getAnnotationAssertionAxioms(cIRI).toString());
			out.println("ANNOTATION ASSERTION AXIOM: "+ontology.getAnnotationAssertionAxioms(cIRI).toString());

			Set<OWLAnnotationAssertionAxiom> list;
			for (OWLAnnotationAssertionAxiom a : list = ontology.getAnnotationAssertionAxioms(cIRI)) {
				
				//out.print("list size: " + list.toString());
				if (a.getValue() instanceof OWLLiteral) {

					OWLLiteral val = (OWLLiteral) a.getValue();
					out.println("Explain this: " + ind.getIRI().getFragment());

					OWLAxiom axiom = OWL.classAssertion(ind, thing);
					Set<Set<OWLAxiom>> exp2 = expGen.getInstanceExplanations(ind, person);

					renderer.render(exp2);
					out.println("--------------------------------------------------------------");
					break;

				}
			}

		}
		
		renderer.endRendering();
		out.flush();
	}

	public static void main(String[] args) throws OWLOntologyCreationException, OWLException, IOException {
		WorkingModel app = new WorkingModel();

		app.run();
	}
}