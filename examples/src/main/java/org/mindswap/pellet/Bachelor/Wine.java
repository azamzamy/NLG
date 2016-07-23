package org.mindswap.pellet.Bachelor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
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
import org.semanticweb.owlapi.model.OWLDataProperty;
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

public class Wine {

	private static String file;
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
	private static BufferedReader br;
	private static StringTokenizer st;
	boolean lastSentence;
	static String statement;
	static String buffer;
	private static OWLDataFactory fac;
	private static OWLOntology ontology;
	private static OWLOntologyManager manager;
	private static PrintWriter out2;
	private static ManchesterSyntaxExplanationRenderer renderer;
	private static PelletReasoner reasoner;
	private static PelletExplanation expGen;
	private static ArrayList<OWLObjectProperty> objectProps;
	private static ArrayList<BiValue> mapper;
	private static int countExplanations;
	private static String outputFile1 = "/Users/zamzamy/Desktop/pellet2/examples/src/main/resources/data/output.txt";
	private static String outputFile2 = "/Users/zamzamy/Desktop/pellet2/examples/src/main/resources/data/out2.txt";
	private static String outputFile3 = "/Users/zamzamy/Desktop/pellet2/examples/src/main/resources/data/out3.txt";

	public Wine(String f) throws OWLOntologyCreationException, OWLException, IOException {
		file = "file://" + f;
		
		outer = new PrintWriter(outputFile2);
		out = new PrintWriter(outputFile1);
		renderer = new ManchesterSyntaxExplanationRenderer();
		PelletExplanation.setup();
		renderer.startRendering(out);
		manager = OWL.manager;
		ontology = manager.loadOntology(IRI.create(file));
		fac = manager.getOWLDataFactory();
		reasoner = PelletReasonerFactory.getInstance().createReasoner(ontology);
		expGen = new PelletExplanation(reasoner);
		objectProps = new ArrayList<OWLObjectProperty>();
		mapper = new ArrayList<BiValue>();
		System.out.println("been through here");
		initObjectProperties();
		countExplanations = 1;
		run();

	}

	public String getOutputFile1() {
		return outputFile1;
	}

	public String getOutputFile2() {
		return outputFile2;
	}

	public String getOutputFile3() {
		return outputFile3;
	}

	@SuppressWarnings("deprecation")
	public void run() throws OWLOntologyCreationException, OWLException, IOException {

		addObjectProperties();
		System.out.println(manager.getOntologies().toString());

		// PrefixManager pm = new DefaultPrefixManager(
		// IRI.create("file:src/main/resources/data/wineTunaSubclass3.owl").toString());

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
		// OWLNamedIndividual tuna1 = fac.getOWLNamedIndividual(IRI.create(NS +
		// "TunaSalad"));
		// OWLNamedIndividual wine1 = fac.getOWLNamedIndividual(IRI.create(NS +
		// "StonleighSauvignonBlanc"));
		// OWLObjectPropertyExpression property =
		// fac.getOWLObjectProperty(IRI.create("http://www.w3.org/TR/2003/PR-owl-guide-20031209/food#goesWellWith"));
		// try {
		// // System.out.println("testing mealCourse: " +
		// // mealCourse.toString()+"\n");
		// // System.out.println("testing goesWellWith Property: " +
		// // property.toString() +"\n");
		//
		// // System.out.println("testing tunasalad Property: " +
		// // tuna1.toString()+"\n");
		// // System.out.println("testing StonleighSauvignonBlanc Property: " +
		// // wine1.toString()+"\n");
		// } catch (NullPointerException e) {
		// e.printStackTrace();
		// }
		//
		// // OWLObjectPropertyAssertionAxiom assertion =
		// // fac.getOWLObjectPropertyAssertionAxiom(property, tuna1, wine1);
		// // //System.out.println(assertion.toString());
		// // //OWLAxiom axiom = OWL.classAssertion(
		// // Set<Set<OWLAxiom>> exp2 =
		// // expGen.getEntailmentExplanations(assertion);
		// // renderer.render(exp2);
		// //
		// //System.out.println("--------------------------------------------------------------");
		//
		// Set<OWLNamedIndividual> individuals =
		// reasoner.getInstances(consumable, false).getFlattened();
		// Set<OWLNamedIndividual> individuals2 = reasoner.getInstances(margaux,
		// false).getFlattened();
		// // System.out.println("ind 2 size: "+individuals2.size());
		// // System.out.println("individuals: " + individuals.size());
		// for (OWLNamedIndividual ind : individuals) {
		// int c = 0;
		// IRI cIRI = ind.getIRI();
		// // boolean check =
		// // cIRI.toString().equals(tuna1.getIRI().toString());
		// // if(check){
		// // System.out.println("loop" + ++count);
		// Set<OWLAnnotationAssertionAxiom> list =
		// ontology.getAnnotationAssertionAxioms(cIRI);
		// for (OWLAnnotationAssertionAxiom a : list) {
		// // System.out.println("In 2nd Loop");
		// //// System.out.println(ind.getIRI().getFragment());
		// count++;
		// for (OWLNamedIndividual ind2 : individuals2) {
		// // System.out.println("counter Two: " + c++);
		// // System.out.println("ind 2 size: " + individuals2.size());
		// IRI cIRI2 = ind2.getIRI();
		// for (OWLAnnotationAssertionAxiom a2 : list) {
		// if (a2.getValue() instanceof OWLLiteral && !a2.equals(null)) {
		// // if
		// //
		// (cIRI2.toString().equals("http://www.w3.org/TR/2003/PR-owl-guide-20031209/wine#StonleighSauvignonBlanc"))
		// // {
		// // System.out.println("horraaaay");
		// OWLLiteral val = (OWLLiteral) a2.getValue();
		// OWLObjectPropertyAssertionAxiom assertion =
		// fac.getOWLObjectPropertyAssertionAxiom(property,
		// ind, ind2);
		// // System.out.println(assertion.toString());
		// // OWLAxiom axiom = OWL.classAssertion(
		// try {
		// Set<Set<OWLAxiom>> exp2 = expGen.getEntailmentExplanations(assertion,
		// 1);
		// renderer.render(exp2);
		// } catch (NullPointerException e) {
		// // System.out.println(e.toString());
		//
		// } catch (Exception e) {
		// // System.out.println(e.toString());
		// }
		//
		// // System.out.println(
		// // "--------------------------------------------------------------");
		// break;
		// // }
		// // }
		// // OWLLiteral val = (OWLLiteral) a.getValue();
		// // OWLObjectPropertyAssertionAxiom assertion =
		// // fac.getOWLObjectPropertyAssertionAxiom(property,
		// // tuna1, wine1);
		// // //System.out.println(assertion.toString());
		// // OWLAxiom axiom = OWL.classAssertion(
		// // Set<Set<OWLAxiom>> exp2 =
		// // expGen.getEntailmentExplanations(assertion);
		// // renderer.render(exp2);
		// //
		// //System.out.println("--------------------------------------------------------------");
		// // break;
		// }
		// }
		// }
		// }
		// }
		//
		// renderer.endRendering();
		// out.flush();
		// naturalGeneration();
		// removeExtras();
	}

	public static String getFile() {
		return file;
	}

	public static String getNs() {
		return NS;
	}

	public static String getNswine() {
		return NSWine;
	}

	public static PrintWriter getOut() {
		return out;
	}

	public static String getLang() {
		return lang;
	}

	public static String getCountry() {
		return country;
	}

	public static Locale getCurrentLocale() {
		return currentLocale;
	}

	public static ResourceBundle getMessages() {
		return messages;
	}

	public boolean isSameSentence() {
		return sameSentence;
	}

	public static PrintWriter getOuter() {
		return outer;
	}

	public static FileReader getFr() {
		return fr;
	}

	public static BufferedReader getBr() {
		return br;
	}

	public static StringTokenizer getSt() {
		return st;
	}

	public boolean isLastSentence() {
		return lastSentence;
	}

	public static String getStatement() {
		return statement;
	}

	public static String getBuffer() {
		return buffer;
	}

	public static OWLDataFactory getFac() {
		return fac;
	}

	public static OWLOntology getOntology() {
		return ontology;
	}

	public static OWLOntologyManager getManager() {
		return manager;
	}

	public static PrintWriter getOut2() {
		return out2;
	}

	public static ManchesterSyntaxExplanationRenderer getRenderer() {
		return renderer;
	}

	public static PelletReasoner getReasoner() {
		return reasoner;
	}

	public static PelletExplanation getExpGen() {
		return expGen;
	}

	public static ArrayList<OWLObjectProperty> getObjectProps() {
		return objectProps;
	}

	public static ArrayList<BiValue> getMapper() {
		return mapper;
	}

	public Set<OWLClass> getClasses() {

		System.out.println(ontology.getClassesInSignature());
		return ontology.getClassesInSignature();

	}

	public void initObjectProperties() {

		mapper.add(new BiValue("hasVintageYear", "wine"));
		mapper.add(new BiValue("hasFood", "food"));
		mapper.add(new BiValue("course", "food"));
		mapper.add(new BiValue("adjacentRegion", "wine"));
		mapper.add(new BiValue("hasSugar", "wine"));
		mapper.add(new BiValue("hasWineDescriptor", "wine"));
		mapper.add(new BiValue("hasDrink", "food"));
		mapper.add(new BiValue("hasFlavor", "wine"));
		mapper.add(new BiValue("hasMaker", "wine"));
		mapper.add(new BiValue("locatedIn", "wine"));
		mapper.add(new BiValue("madeIntoWine", "wine"));
		mapper.add(new BiValue("hasBody", "wine"));
		mapper.add(new BiValue("madeFromFruit", "food"));
		mapper.add(new BiValue("hasColor", "wine"));
		mapper.add(new BiValue("goesWellWith", "food"));
		mapper.add(new BiValue("producesWine", "wine"));
		mapper.add(new BiValue("madeFromGrape", "wine"));

	}

	public static void removeExtras() throws IOException {
		
		fr = new FileReader(outputFile2);
		out = new PrintWriter(outputFile3);
		br = new BufferedReader(fr);
		String s = br.readLine();
		while (s != null) {
			String exp = s;

			if (s.matches(".*\\d+.*") || exp.trim().equals("") || exp.trim() == null) {
				s = br.readLine();
				continue;
			}
			String res = s.replaceAll("\\s+", " ");
			String finale = "";
			String res1 = "";
			String res2 = "";
			boolean split = false;
			if (res.contains("is of type has the color")) {
				res.replace("is of type has the color", "has the color");
			}
			try {
				finale = res.substring(0, res.length() - 1) + ".";
			} catch (Exception e) {
				System.out.println();
			}
			if (finale.length() >= 81) {

				for (int i = 71; i < 91; i++) {
					try {
						if ((int) finale.charAt(i) == 32) {
							res1 = "-" + finale.substring(0, i);
							res2 = finale.substring(i);
							split = true;
						}
					} catch (Exception e) {

					}
				}
			}

			if (!split)
				out.println("-" + finale);
			else {
				out.println(res1);
				out.println(res2);

			}
			s = br.readLine();
		}
		out.close();

	}

	private void addObjectProperties() {

		OWLObjectProperty local;
		for (int i = 0; i < 17; i++) {

			if (mapper.get(i).getValue() == "wine") {
				local = fac.getOWLObjectProperty((IRI.create(NSWine + mapper.get(i).getKey())));
			} else {
				local = fac.getOWLObjectProperty((IRI.create(NS + mapper.get(i).getKey())));
			}

			objectProps.add(local);
		}
	}

	public Collection getIndividuals() {
		Collection classes = ontology.getClassesInSignature();
		// System.out.println("size: " + classes.size());
		// for (Iterator it = classes.iterator(); it.hasNext();) {
		//
		// OWLClass cls = (OWLClass) it.next();
		// // Collection instances = cls.getInstances(false);
		// // System.out.println("Class " + cls.getIRI().getFragment());
		//
		// }

		return classes;

	}

	public Collection getInstances(OWLClass cls) {
		Set<OWLNamedIndividual> individuals = reasoner.getInstances(cls, false).getFlattened();
		// for (Iterator jt = individuals.iterator(); jt.hasNext();) {
		// OWLNamedIndividual individual = (OWLNamedIndividual) jt.next();
		//
		// }
		return individuals;
	}

	public ArrayList<OWLObjectProperty> getDataProperties() {

		String x = "";
		x.lastIndexOf(".");

		return objectProps;

	}
	public static void printFile(String yarab) throws IOException{
		FileReader fer = new FileReader(yarab);
		BufferedReader ber = new BufferedReader(fer);
		String toPrint = ber.readLine();
		
		while( toPrint != null){
			System.out.println("=========" + toPrint);
			toPrint = ber.readLine();
		}
		
	}
	public void naturalGeneration() throws IOException {
		fr = new FileReader(outputFile1);
		br = new BufferedReader(fr);
		buffer = br.readLine();
		int loop = 0;
			while(loop<1000){
				if(buffer != null && buffer.startsWith(""+countExplanations)) break;
				buffer = br.readLine();
				loop++;
			}
			
			
		// Lexicon lexicon = Lexicon.getDefaultLexicon();
		// NLGFactory nlgFactory = new NLGFactory(lexicon);
		// Realiser realiser = new Realiser(lexicon);
		outer = new PrintWriter(outputFile2);
		statement = "";
		String word = "";
		
		boolean checked = false;
		int count = 0;
		boolean end = false;
		
		while (buffer != null) {
			statement = buffer;
			buffer = br.readLine();
			if (checkNextLine()) {
				end = true;
			} else {
				end = false;
			}

			// skipping the domain sentence until we found a solution
			if (statement.contains("domain") || statement.contains("Rule")) {
				statement = br.readLine();
				continue;
			} else if (statement.contains("DisjointClasses")) {
				System.out.println("statement: " + statement);
				outer.print(disjointPrinting(statement) + "\n");
				buffer = br.readLine();
				continue;
			}

			st = new StringTokenizer(statement);
			String out = "";
			while (st.hasMoreTokens()) {
				String orig = st.nextToken();
				System.out.println("A TOKEN HERE IS: " + orig);
				if ((int) orig.charAt(0) == 49) {
					continue;
				}

				String s = getCorrectness(orig) + " ";
				outer.print(s);

				if (end) {

				} else {
					outer.print(" ");
				}
			}
			if (end) {
				outer.print("\n");
				end = false;

			} else {

			}
		}

		outer.close();
		countExplanations++;

	}

	public static boolean checkNextLine() throws IOException {
		int i = 0;
		if (buffer == null) {
			return true;
		}
		for (i = 0; i < buffer.length(); i++) {
			// System.out.print("#" + i + ": " + (int) buffer.charAt(i) + " ");
			if ((int) buffer.charAt(i) == 32)
				continue;
			else
				break;

		}
		System.out.println();
		if (i > 17)
			return false;
		return true;

	}

	public static int endOfSentence(String statement) {
		int tabs = 0;

		for (int i = 0; i < statement.length() - 1; i++) {

			if ((int) statement.charAt(i) == 32)
				tabs++;
			// System.out.print(" #"+i+": " + (int) statement.charAt(i));
		}
		// System.out.println();
		return tabs;
	}

	public static String disjointPrinting(String s) throws IOException {
		String result = "";
		System.out.println("what is this??" + s);
		result += "Disjoint Classes are: ";
		result += s.substring(21) + ", ";
		String component = buffer;
		component.replace(" ", "");
		System.out.println("should be meat, is it?" + component);
		while (component.charAt(component.length() - 1) != ')') {
			component.replace(" ", "");
			result += getCorrectness(component);
			result += ", ";
			buffer = br.readLine();
			component = buffer;
		}
		component.replace(" ", "");
		component = component.substring(0, component.length());

		if (component != null)
			result += getCorrectness(component);

		System.out.println("RESULT IIIIIISSSS: " + result);
		return result;
	}

	public static String getCorrectness(String s) {

		String ret = "";
		try {
			if (s.contains("value"))
				return "";
			messages.getString("" + s + "");
		} catch (Exception e) {
			// System.out.println("error came from the word: " + s);
			ret = splitCamelCase(s);
			return ret;

		}

		return messages.getString("" + s + "");

	}

	public static void resetRenderer() throws FileNotFoundException {
		// out2 = new PrintWriter(outputFile1);
		// out2.print("");
		// out2.close();
		// out2 = new PrintWriter(outputFile2);
		// out2.print("");
		// out2.close();
		// out2 = new PrintWriter(outputFile3);
		// out2.print("");
		// out2.close();
		//

	}

	public static String splitCamelCase(String s) {
		return s.replaceAll(String.format("(?<!(^|[A-Z0-9]))(?=[A-Z0-9])|(?<!^)(?=[A-Z][a-z])"), " ");
	}

	// public static void main(String[] args) throws
	// OWLOntologyCreationException, OWLException, IOException {
	// Wine app = new Wine();
	//
	// app.run();
	// }

}