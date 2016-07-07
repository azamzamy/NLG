// Copyright (c) 2006 - 2008, Clark & Parsia, LLC. <http://www.clarkparsia.com>
// This source code is available under the terms of the Affero General Public
// License v3.
//
// Please see LICENSE.txt for full license terms, including the availability of
// proprietary exceptions.
// Questions, comments, or requests for clarification: licensing@clarkparsia.com

package org.mindswap.pellet.Bachelor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLEntity;
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
public class ArtistModel {

	private static final String file = "file:src/main/resources/data/art_labeled.owl";
	private static final String NS = "http://www.example.org/art.owl#";
	private static PrintWriter out;

	@SuppressWarnings("deprecation")
	public void run() throws OWLOntologyCreationException, OWLException, IOException {

		//File file = new File("src/main/resources/data/output.txt");
		PrintWriter out = new PrintWriter(new FileWriter("/Users/zamzamy/Downloads/pellet-master/examples/src/main/resources/data/output.txt"));
		

		PelletExplanation.setup();

		ManchesterSyntaxExplanationRenderer renderer = new ManchesterSyntaxExplanationRenderer();
		
		
		renderer.startRendering(out);
		OWLOntologyManager manager = OWL.manager;
		OWLOntology ontology = manager.loadOntology(IRI.create(file));
		
		PelletReasoner reasoner = PelletReasonerFactory.getInstance().createReasoner(ontology);
		
		PelletExplanation expGen = new PelletExplanation(reasoner);


		OWLClass artist = OWL.Class(NS + "artist");
		OWLClass romanticArtist = OWL.Class(NS + "RomanticismArtist");
		OWLClass person = OWL.Class(NS + "Person");
		OWLClass thing = OWL.Class("http://www.w3.org/2002/07/owl#Thing");
		
		int count = 0;


		Set<OWLNamedIndividual> individuals = reasoner.getInstances(person, false).getFlattened();
		System.out.println("person instances: " + individuals.size());
		
		for (OWLNamedIndividual ind : individuals) {
			
			IRI cIRI = ind.getIRI();
			//if(ontology.getAnnotationAssertionAxioms(cIRI).isEmpty()) continue;
			
			out.println("loop" + ++count);
			//out.println("CIRI: " + cIRI);
			

			Set<OWLAnnotationAssertionAxiom> list = ontology.getAnnotationAssertionAxioms(cIRI);
			
			for (OWLAnnotationAssertionAxiom a : list) {
				
				out.println("list: " + list.toString());
				
				if (a.getValue() instanceof OWLLiteral) {
					
					OWLLiteral val = (OWLLiteral) a.getValue();
					out.println("Explain: " + ind.getIRI().getFragment());

					//OWLAxiom axiom = OWL.classAssertion(ind, person);
					Set<Set<OWLAxiom>> exp2 = expGen.getInstanceExplanations(ind, romanticArtist);

					renderer.render(exp2);
					out.println("--------------------------------------------------------------");
					//break;

				}
			}

		}

		renderer.endRendering();
		out.flush();
		//naturalGeneration();

	}
	
	public void naturalGeneration(){
		
	}

	public static void main(String[] args) throws OWLOntologyCreationException, OWLException, IOException {
		ArtistModel app = new ArtistModel();

		app.run();
	}
}