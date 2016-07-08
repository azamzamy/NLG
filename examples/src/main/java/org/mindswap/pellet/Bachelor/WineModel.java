
package org.mindswap.pellet.Bachelor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.xerces.util.SynchronizedSymbolTable;
import org.semanticweb.owlapi.io.SystemOutDocumentTarget;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
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

import simplenlg.framework.*;
import simplenlg.lexicon.*;
import simplenlg.realiser.english.*;
import simplenlg.phrasespec.*;
import simplenlg.features.*;

public class WineModel {

	private static final String file = "file:src/main/resources/data/wine77.owl";
	private static final String NS = "http://www.w3.org/TR/2003/PR-owl-guide-20031209/food#";
	private static final String NSWine = "http://www.w3.org/TR/2003/PR-owl-guide-20031209/wine#";
	private static PrintWriter out;
	private static String lang = "en";
	private static String country = "US";
	private static Locale currentLocale = new Locale(lang, country);
	private static final ResourceBundle messages = ResourceBundle.getBundle("MessagesBundle", currentLocale);
    private boolean sameSentence = false;
    private static PrintWriter outer; 
    private static FileReader fr;
    private static BufferedReader br = new BufferedReader(fr);
	private static StringTokenizer st;

	@SuppressWarnings("deprecation")
	public void run() throws OWLOntologyCreationException, OWLException, IOException {

		outer = new PrintWriter("/Users/zamzamy/Desktop/pellet2/examples/src/main/resources/data/out2.txt");
		fr = new FileReader("/Users/zamzamy/Desktop/pellet2/examples/src/main/resources/data/output.txt");
		PrintWriter out = new PrintWriter(
				new FileWriter("/Users/zamzamy/Desktop/pellet2/examples/src/main/resources/data/output.txt"));

		
		PelletExplanation.setup();
		
		PrintWriter out2 = new PrintWriter(System.out);
		ManchesterSyntaxExplanationRenderer renderer = new ManchesterSyntaxExplanationRenderer();

		renderer.startRendering(out);
		OWLOntologyManager manager = OWL.manager;
		OWLOntology ontology = manager.loadOntology(IRI.create(file));
		OWLDataFactory fac = manager.getOWLDataFactory();
//		PrefixManager pm = new DefaultPrefixManager(
//				IRI.create("file:src/main/resources/data/wineTunaSubclass3.owl").toString());
		PelletReasoner reasoner = PelletReasonerFactory.getInstance().createReasoner(ontology);
		PelletExplanation expGen = new PelletExplanation(reasoner);

		OWLClass meatCourse = OWL.Class(NS + "DarkMeatFowlCourse");
		OWLClass mealCourse = OWL.Class(NS + "MealCourse");
		OWLClass consumable = OWL.Class(NS + "ConsumableThing");
		OWLClass tuna = OWL.Class(NS + "Tuna");
		OWLClass seafoodCourse = OWL.Class(NS + "seaFoodCourse");
		OWLClass wine = OWL.Class(NSWine + "Winery");
		OWLClass sauvignon = OWL.Class(NSWine + "SauvignonBlanc");
		OWLClass margaux = OWL.Class(NSWine + "Margaux");
		OWLClass thing = OWL.Class("http://www.w3.org/2002/07/owl#Thing");
		int count = 0;
		
		// manager.saveOntology(ontology, new SystemOutDocumentTarget());
		OWLNamedIndividual tuna1 = fac.getOWLNamedIndividual(IRI.create(NS+"TunaSalad"));
		OWLNamedIndividual wine1 = fac.getOWLNamedIndividual(IRI.create(NS+"StonleighSauvignonBlanc"));
		OWLObjectPropertyExpression property = fac.getOWLObjectProperty(IRI.create("http://www.w3.org/TR/2003/PR-owl-guide-20031209/food#goesWellWith"));
		try {
			//System.out.println("testing mealCourse: " + mealCourse.toString()+"\n");
			//System.out.println("testing goesWellWith Property: " + property.toString() +"\n");
			
			//System.out.println("testing tunasalad Property: " + tuna1.toString()+"\n");
			//System.out.println("testing StonleighSauvignonBlanc Property: " + wine1.toString()+"\n");
		} catch (NullPointerException e) {
			e.printStackTrace();
		}

		// OWLObjectPropertyAssertionAxiom assertion =
		// fac.getOWLObjectPropertyAssertionAxiom(property, tuna1, wine1);
		// //System.out.println(assertion.toString());
		// //OWLAxiom axiom = OWL.classAssertion(
		// Set<Set<OWLAxiom>> exp2 =
		// expGen.getEntailmentExplanations(assertion);
		// renderer.render(exp2);
		// //System.out.println("--------------------------------------------------------------");

		Set<OWLNamedIndividual> individuals = reasoner.getInstances(consumable, false).getFlattened();
			Set<OWLNamedIndividual> individuals2 = reasoner.getInstances(margaux, false).getFlattened();
			//System.out.println("ind 2 size: "+individuals2.size());
			//System.out.println("individuals: " + individuals.size());
			for (OWLNamedIndividual ind : individuals) {
				int c = 0;
				IRI cIRI = ind.getIRI();
//				boolean check = cIRI.toString().equals(tuna1.getIRI().toString());
//				if(check){
				//System.out.println("loop" + ++count);
			Set<OWLAnnotationAssertionAxiom> list = ontology.getAnnotationAssertionAxioms(cIRI);
			for (OWLAnnotationAssertionAxiom a : list) {
				//System.out.println("In 2nd Loop");
				////System.out.println(ind.getIRI().getFragment());
				count++;
						for (OWLNamedIndividual ind2 : individuals2) {
							//System.out.println("counter Two: " + c++);
							//System.out.println("ind 2 size: " + individuals2.size());
							IRI cIRI2 = ind2.getIRI();
							for (OWLAnnotationAssertionAxiom a2 : list) {
								if (a2.getValue() instanceof OWLLiteral && !a2.equals(null)) {
									//if (cIRI2.toString().equals("http://www.w3.org/TR/2003/PR-owl-guide-20031209/wine#StonleighSauvignonBlanc")) {
										//System.out.println("horraaaay");
										OWLLiteral val = (OWLLiteral) a2.getValue();
										OWLObjectPropertyAssertionAxiom assertion = fac
												.getOWLObjectPropertyAssertionAxiom(property, ind, ind2);
										//System.out.println(assertion.toString());
										// OWLAxiom axiom = OWL.classAssertion(
										try{
										Set<Set<OWLAxiom>> exp2 = expGen.getEntailmentExplanations(assertion, 1);
										renderer.render(exp2);
										} catch(NullPointerException e){
											//System.out.println(e.toString());
											
										} catch(Exception e){
											//System.out.println(e.toString());
										}
										
										
										//System.out.println(
//												"--------------------------------------------------------------");
										break;
									//}
//								}
//								OWLLiteral val = (OWLLiteral) a.getValue();
								// OWLObjectPropertyAssertionAxiom assertion =
								// fac.getOWLObjectPropertyAssertionAxiom(property,
								// tuna1, wine1);
								// //System.out.println(assertion.toString());
								// OWLAxiom axiom = OWL.classAssertion(
								// Set<Set<OWLAxiom>> exp2 =
								// expGen.getEntailmentExplanations(assertion);
								// renderer.render(exp2);
//								//System.out.println("--------------------------------------------------------------");
//								break;
							}
						}
					}
				}
			}
		
		renderer.endRendering();
		out.flush();
		naturalGeneration();
	}

	public void naturalGeneration() throws IOException {
		
		
		String statement;
		String word = "";
		
	     Lexicon lexicon = Lexicon.getDefaultLexicon();
         NLGFactory nlgFactory = new NLGFactory(lexicon);
         Realiser realiser = new Realiser(lexicon);
		
//         NLGElement s1 = nlgFactory.createSentence("my dog is happy");
//         String output = realiser.realiseSentence(s1);	
         

//         System.out.println(messages.getString("tunasalad"));
         br.readLine();
        statement = br.readLine();
        int count  = 0;

		while(statement != null){
			st = new StringTokenizer(statement);
			while(st.hasMoreTokens()){
				String orig = st.nextToken();
				if(orig.contains("DisjointClasses")){
					System.out.println("statement: " + statement);
					sameSentence = true;
					disjointPrinting(statement);
				}
				String s = getCorrectness(orig);
				
//				System.out.println(orig);
//				System.out.println(orig + "  -  " + s);				
//				System.out.println("____________");
				if(s.contains("Rule")) break;
					outer.print(s + " ");
			}
			count++;
			if(count == 3) {count =0; outer.println(); }
			statement = br.readLine();
		}
		
		 
		outer.close();
	}
	
	public static String disjointPrinting(String s){
		outer.print("Disjoint Classes are: "+ );
		outer.print(s.substring(16));
		String component = br.readLine();
		while(!component.contains(")")){
		st = new StringTokenizer(br.readLine());
		
		component = br.readLine();
		}
		outer.print(getCorrectness(component.substring(0, component.length()-1)));
		

		
		
		
		
		return "";
	}
	
	public static String getCorrectness(String s){
		
		String ret = "";
		try {
			messages.getString(""+s+"");
		} catch(Exception e){
//			System.out.println("error came from the word: " + s);
			ret = splitCamelCase(s);
			return ret; 
			
		}
		
		
		
		return messages.getString(""+s+"");
		
	}
	
	public static String splitCamelCase(String s) {
		   return s.replaceAll(
				   String.format("(?<!(^|[A-Z0-9]))(?=[A-Z0-9])|(?<!^)(?=[A-Z][a-z])"
					    		  		),	
					      					" "
					   );
		}

	public static void main(String[] args) throws OWLOntologyCreationException, OWLException, IOException {
		WineModel app = new WineModel();

		app.run();
	}
}