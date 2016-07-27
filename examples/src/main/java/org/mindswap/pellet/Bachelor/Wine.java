package org.mindswap.pellet.Bachelor;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFLanguages;
import org.apache.xerces.util.SynchronizedSymbolTable;
import org.mindswap.pellet.PelletOptions;
import org.mindswap.pellet.jena.vocabulary.SWRL;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.OWLOntologyDocumentSource;
import org.semanticweb.owlapi.io.StreamDocumentSource;
import org.semanticweb.owlapi.io.SystemOutDocumentTarget;
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.MissingImportHandlingStrategy;
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
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.PrefixManager;
import org.semanticweb.owlapi.reasoner.InferenceType;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.semanticweb.owlapi.util.InferredAxiomGenerator;
import org.semanticweb.owlapi.util.InferredClassAssertionAxiomGenerator;
import org.semanticweb.owlapi.util.InferredDataPropertyCharacteristicAxiomGenerator;
import org.semanticweb.owlapi.util.InferredDisjointClassesAxiomGenerator;
import org.semanticweb.owlapi.util.InferredEquivalentClassAxiomGenerator;
import org.semanticweb.owlapi.util.InferredEquivalentDataPropertiesAxiomGenerator;
import org.semanticweb.owlapi.util.InferredEquivalentObjectPropertyAxiomGenerator;
import org.semanticweb.owlapi.util.InferredInverseObjectPropertiesAxiomGenerator;
import org.semanticweb.owlapi.util.InferredObjectPropertyAxiomGenerator;
import org.semanticweb.owlapi.util.InferredObjectPropertyCharacteristicAxiomGenerator;
import org.semanticweb.owlapi.util.InferredOntologyGenerator;
import org.semanticweb.owlapi.util.InferredPropertyAssertionGenerator;
import org.semanticweb.owlapi.util.InferredSubClassAxiomGenerator;
import org.semanticweb.owlapi.util.InferredSubDataPropertyAxiomGenerator;
import org.semanticweb.owlapi.util.InferredSubObjectPropertyAxiomGenerator;

import com.clarkparsia.owlapi.explanation.PelletExplanation;
import com.clarkparsia.owlapi.explanation.io.manchester.ManchesterSyntaxExplanationRenderer;
import com.clarkparsia.owlapiv3.OWL;
import com.clarkparsia.pellet.owlapiv3.PelletReasoner;
import com.clarkparsia.pellet.owlapiv3.PelletReasonerFactory;
import com.google.common.base.Strings;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.ModelFactory;

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
	static ArrayList<String> strings;
	static String buffer;
	static int index = 0;
	static int resSize = 0;
	private static String line;
	static ArrayList<String> ans;
	private static OWLDataFactory fac;
	private static OWLOntology ontology;
	private static OWLOntology infOnt;
	private static OWLOntologyManager manager;
	private static PrintWriter out2;
	private static ManchesterSyntaxExplanationRenderer renderer;
	private static PelletReasoner reasoner;
	private static PelletExplanation expGen;
	private static ArrayList<OWLObjectProperty> objectProps;
	private static ArrayList<BiValue> mapperProps;
	private static ArrayList<BiValue> mapperClasses;
	private static int countExplanations;
	private static String ruleClass1;
	private static String ruleClass2;
	private static StringWriter stringOut;
	private static String target;
	private static int max;
	private static HashMap<String, ArrayList<String>> subjects;
	private static HashMap<String, ArrayList<String>> objects;
	private static HashMap<String, ArrayList<String>> subToObj;
	private static String outputFile1 = "/Users/zamzamy/Desktop/pellet2/examples/src/main/resources/data/output.txt";
	private static String outputFile2 = "/Users/zamzamy/Desktop/pellet2/examples/src/main/resources/data/out2.txt";
	private static String outputFile3 = "/Users/zamzamy/Desktop/pellet2/examples/src/main/resources/data/out3.txt";

	public Wine(String f, int count) throws OWLOntologyCreationException, OWLException, IOException {
		file = "file://" + f;
		out = new PrintWriter(new FileWriter(outputFile1));
		// stringOut = new StringWriter();
		strings = new ArrayList<String>();
		renderer = new ManchesterSyntaxExplanationRenderer();
		PelletExplanation.setup();
		renderer.startRendering(out);
		manager = OWL.manager;
		ontology = manager.loadOntology(IRI.create(file));
		fac = manager.getOWLDataFactory();
		reasoner = PelletReasonerFactory.getInstance().createReasoner(ontology);
		expGen = new PelletExplanation(reasoner);
		objectProps = new ArrayList<OWLObjectProperty>();
		mapperProps = new ArrayList<BiValue>();
		mapperClasses = new ArrayList<BiValue>();
		initObjectProperties();
		countExplanations = count;
		addObjectProperties();
		PelletOptions.DL_SAFE_RULES = false;
		subjects = new HashMap<String, ArrayList<String>>();
		objects = new HashMap<String, ArrayList<String>>();
		subToObj = new HashMap<String, ArrayList<String>>();
		line = "";
		target = "";
		// inferOntology();

	}

	public static String getTarget() {
		return target;
	}

	public static void setTarget(String target) {
		Wine.target = target;
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

	// renderer.endRendering();
	// out.flush();
	// naturalGeneration();
	// removeExtras();

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

	public static ArrayList<BiValue> getMapperProps() {
		return mapperProps;
	}

	public static ArrayList<BiValue> getMapperClasses() {
		return mapperClasses;
	}

	public static Set<OWLClass> getClasses() {

		System.out.println(ontology.getClassesInSignature());
		return ontology.getClassesInSignature();

	}

	public static void initObjectProperties() {

		mapperProps.add(new BiValue("hasVintageYear", "wine"));
		mapperProps.add(new BiValue("hasFood", "food"));
		mapperProps.add(new BiValue("course", "food"));
		mapperProps.add(new BiValue("adjacentRegion", "wine"));
		mapperProps.add(new BiValue("hasSugar", "wine"));
		mapperProps.add(new BiValue("hasWineDescriptor", "wine"));
		mapperProps.add(new BiValue("hasDrink", "food"));
		mapperProps.add(new BiValue("hasFlavor", "wine"));
		mapperProps.add(new BiValue("hasMaker", "wine"));
		mapperProps.add(new BiValue("locatedIn", "wine"));
		mapperProps.add(new BiValue("madeIntoWine", "wine"));
		mapperProps.add(new BiValue("hasBody", "wine"));
		mapperProps.add(new BiValue("madeFromFruit", "food"));
		mapperProps.add(new BiValue("hasColor", "wine"));
		mapperProps.add(new BiValue("goesWellWith", "food"));
		mapperProps.add(new BiValue("producesWine", "wine"));
		mapperProps.add(new BiValue("madeFromGrape", "wine"));

		mapperClasses.add(new BiValue("RedBordeaux", "wine"));
		mapperClasses.add(new BiValue("DarkMeatFowlCourse", "food"));
	}

	public static void removeExtras() throws IOException {

		fr = new FileReader(outputFile2);
		br = new BufferedReader(fr);
		out = new PrintWriter(outputFile3);

		String finalString = "";
		String s = br.readLine();
		String s2 = br.readLine();
		while (s != null) {
			if(s.equals(s2)) s2 = br.readLine();
			String exp = s;
			// System.out.println("EXP::::::::" + exp);
			if (exp.trim().isEmpty() || exp.trim() == null) {
				s = br.readLine();
				continue;
			}

			s = modifyStatements(s);
			s = s.replaceAll("  ", " ");
			s = s.trim();
			String res = s;
			// System.out.println("MIDDLE: " + s);
			String finale = "";
			String res1 = "";
			String res2 = "";
			boolean split = false;
			// res.replace("is of type has the color", "has the color");

			try {
				finale = res.substring(0, res.length()) + ".";

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
			// System.out.println("FINALE: " + finale);
			if (!split)
				finalString += "-" + finale + "\n";
			else {
				finalString += res1 + "\n";
				finalString += res2 + "\n";

			}
			s = s2;
			s2 = br.readLine();
		}
		System.out.println("**************FINAL STRING*******************\n" + finalString);
		out.println(finalString);
		out.close();

	}

	private static String modifyStatements(String s) {
		
		if (s.matches("\\d.*")) {
			s = s.substring(s.lastIndexOf(41) + 1);
		}
		s = s.replaceAll("\\s+", " ");
		if (isContain(s, "is similar to") && isContain(s, "or")) {
			int pos = s.indexOf("is similar to") + 13;
			s = s.substring(0, pos) + " either " + s.substring(pos);
		}
		if (isContain(s, "is identical to") && isContain(s, "or")) {
			int pos = s.indexOf("is identical to") + 16;
			s = s.substring(0, pos) + " either " + s.substring(pos);
		}
		if (isContain(s, "is a special kind of has the following drink: only has the color")) {
		s =	s.replace("is a special kind of has the following drink: only has the color",
					"goes well with a drink that has the color");
		}
		s = s.replace("a special kind of has the color", "a special kind of wine that with red color");
		
			System.out.println("s before:  "+ s);
			s = s.replace("that Red", "and Red");
			System.out.println("s after:  "+ s);
		
		s = s.replace("that White", "and White");
		System.out.println("s after:  "+ s);
		s = s.replace("is a special kind of has the color", "has color");
		System.out.println("s after:  "+ s);
		// System.out.println("EXP2:::::::::::" + s);
		s = s.replace("not contains", "does not contain");
		s = s.replace("contains only", "only contains");
		s = s.replaceAll("drinks is", "Drinks are");
		
		// System.out.println("SUPPOSED TO FUCKING GO THERE: " + s);
		if (s.contains("has color"))
			s = shiftWord(s, "color");
		if (s.contains("has flavor"))
			s = shiftWord(s, "flavor");
		if (s.contains("has sugar"))
			s = shiftWord(s, "sugar");
		s = s.replace("that White Wine", "that is also white wine");
		s = s.replace("that red Wine", "that is also red wine");
		return s;

	}

	public static String shiftWord(String s, String word) {
		String[] sentence = s.split(" ");
		int sel = 0;
		for (int i = 0; i < sentence.length; i++) {
			if (!(sentence[i].equals(word)))
				continue;
			sel = i;
			sentence[i - 1] += " a ";
			break;
		}
		for (int i = sel; i < sentence.length - 1; i++) {
			sentence[i + 1] = sentence[i + 1].toLowerCase();
			sentence[i] = sentence[i + 1];
		}
		sentence[sentence.length - 1] = word;
		System.out.println("shiftword: _~_~_~_~ " + s);
		return s = StringUtils.join(sentence, " ");

	}

	private static void addObjectProperties() {

		OWLObjectProperty local;
		for (int i = 0; i < 17; i++) {

			if (mapperProps.get(i).getValue() == "wine") {
				local = fac.getOWLObjectProperty((IRI.create(NSWine + mapperProps.get(i).getKey())));
			} else {
				local = fac.getOWLObjectProperty((IRI.create(NS + mapperProps.get(i).getKey())));
			}

			objectProps.add(local);
		}
	}

	private static boolean isContain(String source, String subItem) {
		String pattern = "\\b" + subItem + "\\b";
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(source);
		return m.find();
	}

	public static BiValue searchMapperClasses(String s) {

		BiValue local = null;
		mapperClasses.size();
		System.out.println(mapperClasses);
		for (int i = 0; i < mapperClasses.size(); i++) {
			// System.out.println(mapperClasses.get(i).getKey() + " <-- mapper :
			// s--> " + s);
			if (mapperClasses.get(i).getKey().equals(s)) {
				local = mapperClasses.get(i);
				System.out.println("FOUND::::::::::::::::" + mapperClasses.get(i));
				break;
			}
		}
		System.out.println("#####################################" + local);
		return local;
	}

	public static Collection getIndividuals() {
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

	public static void printFile(String yarab) throws IOException {
		FileReader fer = new FileReader(yarab);
		BufferedReader ber = new BufferedReader(fer);
		String toPrint = ber.readLine();

		while (toPrint != null) {
			System.out.println("=========" + toPrint);
			toPrint = ber.readLine();
		}

	}

	public static void clearFile2() throws IOException {
		FileWriter fwOb = new FileWriter(outputFile2, false);
		PrintWriter pwOb = new PrintWriter(fwOb, false);
		pwOb.flush();
		pwOb.close();
		fwOb.close();
	}

	public static void clearMaps() {
		subjects.clear();
		objects.clear();
		subToObj.clear();
		subjects = new HashMap<String, ArrayList<String>>();
		objects = new HashMap<String, ArrayList<String>>();
		subToObj = new HashMap<String, ArrayList<String>>();
	}

	public void naturalGeneration() throws IOException {

		// outer = new PrintWriter(outputFile2);
		// clearFile2();
		// fr = new FileReader(outputFile1);
		// StringBuffer str = stringOut.getBuffer();
		// br = new BufferedReader(new InputStreamReader(new
		// ByteArrayInputStream(str.toString().getBytes())));
		// stringOut.getBuffer();
		// br2 = new BufferedReader(new InputStreamReader(new
		// ByteArrayInputStream(str.toString().getBytes())));
		// Lexicon lexicon = Lexicon.getDefaultLexicon();
		// NLGFactory nlgFactory = new NLGFactory(lexicon);
		// Realiser realiser = new Realiser(lexicon);

		clearMaps();
		index = 0;
		int resSize = 0;
		fr = new FileReader(outputFile1);
		outer = new PrintWriter(outputFile2);
		br = new BufferedReader(fr);

		statement = "";
		String word = "";
		System.out.println("BUFFER1: " + buffer);
		boolean checked = false;
		int count = 0;
		boolean end = false;
		ans = new ArrayList<String>();
		String strLine;
		while ((strLine = br.readLine()) != null) {
			System.out.println(strLine);
			ans.add(strLine);
		}
		resSize = ans.size();

		int num = 0;
		max = 0;
		int loop = 0;
		for (String singleLine : ans) {
			buffer = singleLine;
			System.out.println("BUFFER: " + buffer);
			if (!buffer.isEmpty() && Character.isDigit(buffer.charAt(0))) {
				if (!buffer.isEmpty() && Character.isDigit(buffer.charAt(1))) {
					num = Integer.parseInt(buffer.substring(0, 2));
				} else {
					num = Integer.parseInt(buffer.substring(0, 1));
				}
				if (num > max) {
					max = num;
				}
			}
		}

		System.out.println("NUMBER TO TAKE:" + max);
		buffer = ans.get(index++);
		line = "";
		while (buffer != null) {
			if (!buffer.isEmpty() && buffer.startsWith("" + max))
				break;
			else if (index < resSize)
				buffer = ans.get(index++);
			else
				break;
		}

		System.out.println("NUMBER that was actually taken:" + max);

		while (buffer != null)

		{
			if (buffer.contains("subPropertyOf"))

			{
				if (index < resSize)
					buffer = ans.get(index++);
				else
					break;
			}
			if (buffer.contains("Explanations"))
				buffer = ans.get(index++);
			statement = buffer;
			if (index < resSize)
				buffer = ans.get(index++);
			else
				break;

			if (checkNextLine()) {
				end = true;
			} else {
				end = false;
			}

			if (statement.contains("Rule")) {
				checkRule(statement);
				System.out.println("dsvfeqgqrer" + ruleClass1 + "          " + ruleClass2);
				if (index < resSize)
					buffer = ans.get(index++);
				else
					break;
				continue;
			} else if (statement.contains("DisjointClasses")) {
				System.out.println("statement: " + statement);
				line += disjointPrinting(statement) + "\n";
				if (index < resSize)
					buffer = ans.get(index++);
				else
					break;
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

				// String s = getCorrectness(orig) + " ";
				String s = orig + " ";

				line += s;
				System.out.println("Natural generation strings: " + s);

				if (end) {

				} else {
					line += " ";
				}
			}
			if (end) {
				line += "\n";
				end = false;

			} else {

			}
		}
		String output = hashStatements(line);
		outer.println(output);
		outer.close();
		countExplanations++;
	}

	public static String hashStatements(String single) {
		strings = new ArrayList<String>();

		if (max > 1) {
			single = single.substring(single.lastIndexOf(41) + 1);
		}

		System.out.println("string testing: ********" + single + "********");
		int emptyLine = single.lastIndexOf(10);
		single = single.substring(0, emptyLine);

		emptyLine = single.lastIndexOf(10);
		single = single.substring(0, emptyLine);
		single.replaceAll("^\\s+", "");
		single = single.trim();

		while (single.lastIndexOf(10) != -1) {

			emptyLine = single.lastIndexOf(10);
			String oneLine = single.substring(emptyLine + 1);

			strings.add(oneLine);
			if (!single.isEmpty())
				single = single.substring(0, emptyLine);

		}
		System.out.println("SD");
		strings.add(single);
		System.out.println("STRING testing: ********" + strings + "********");
		// System.out.println("*****STRINGS*****: " + strings.size() + "lets
		// go\n 1-" + strings.get(0) + "\n2- "
		// + strings.get(1) + "\n3- " + strings.get(2) + "\n4- " +
		// strings.get(3) + "\n5- " + strings.get(4)
		// + "\n6- " + strings.get(5) + "\n7 -" + strings.get(6));

		for (String i : strings) {
			String[] words = i.split(" ");
			String first = words[0];
			String last = words[words.length - 1];
			if (subjects.containsKey(first)) {
				subjects.get(first).add(i);
				continue;
			} else {
				subjects.put(first, new ArrayList<String>() {
					{
						add(i);
					}
				});
			}
			if (objects.containsKey(last)) {
				objects.get(last).add(i);
				continue;
			} else {
				objects.put(last, new ArrayList<String>() {
					{
						add(i);
					}
				});
			}
			if (subToObj.containsKey(first)) {
				subToObj.get(first).add(last);
			} else {
				subToObj.put(first, new ArrayList<String>() {
					{
						add(last);
					}
				});
			}

		}
		System.out.println("subjects: " + subjects);
		System.out.println("objects: " + objects);
		System.out.println("subToOBj: " + subToObj);
		System.out.println("Stringsssss size: " + strings.size());

		return reArrange();

	}

	public static String reArrange() {
		ArrayList<String> output = new ArrayList<String>();
		String nextSubject = "";
		System.out.println(subjects);
		String sentence = "";
		boolean end = false;
		if (subjects.containsKey(target)) {
			sentence = subjects.get(target).get(0);
			output.add(sentence);
			String[] parts = sentence.split(" ");
			String object = parts[parts.length - 1];
			subjects.get(target).remove(subjects.get(target).indexOf(sentence));
			objects.get(object).remove(objects.get(object).indexOf(sentence));
			subToObj.get(target).remove(object);
			removeEmptyHashes(target, object);
			
			nextSubject = object;
			
			System.out.println("**********MY FUCKING OUTPUT No.1: " + output.get(0));
			System.out.println("My next subject: " + nextSubject);
			System.out.println("strings size --" + strings.size());
		} else {
			Map.Entry<String, ArrayList<String>> randomSentence = subjects.entrySet().iterator().next();
			String randomSubject = randomSentence.getKey();
			String selectedSentence = subjects.get(randomSubject).get(0);
			output.add(selectedSentence);
			String [] sentenceArray = selectedSentence.split(" ");
			String selectedObject = sentenceArray[sentenceArray.length-1];
			
			System.out.println("random object: " + selectedObject);
			System.out.println("selected sentence: " + selectedSentence);
			// remove output sentence from hash maps
			subjects.get(randomSubject).remove(selectedSentence);
			objects.get(selectedObject).remove(selectedSentence);
//			subToObj.get(randomSubject).remove(selectedObject);
			removeEmptyHashes(randomSubject, selectedObject);
			nextSubject = selectedObject;
		}
		ArrayList<String> links = new ArrayList<String>();
		for (int i = 0; i < strings.size(); i++) {
			if(end) break;
			System.out.println("HELLOOOOOOOOOOO");
			if(subjects.isEmpty()) end = true;
			if (subjects.containsKey(nextSubject) && subjects.get(nextSubject) != null) {
				System.out.println("!@$%^%#@!%^&*%$#@:\n" + output);
				ArrayList<String> selectedSentences = subjects.get(nextSubject);
				String selectedSentence = selectedSentences.get(0); 
				System.out.println("sub to obj of next sub: " + links);
				String [] sentenceArray = selectedSentence.split(" ");
				String selectedObject = sentenceArray[sentenceArray.length-1];
				
				System.out.println("did i even get here?: " + selectedObject);
				
				String toOutput = selectedSentence;
				System.out.println("did i even get here?2: " + toOutput);
				output.add(toOutput);
				// remove the output sentence from hash maps
				subjects.get(nextSubject).remove(toOutput);
				objects.get(selectedObject).remove(toOutput);
//				subToObj.get(nextSubject).remove(selectedObject);
				removeEmptyHashes(nextSubject, selectedObject);
				nextSubject = selectedObject;
			} else {
				System.out.println("!@$%^%#@!%^&*%$#@:\n" + output);
				Map.Entry<String, ArrayList<String>> randomSentence;
				try{
				randomSentence = subjects.entrySet().iterator().next();
				} catch(Exception e){
					break;
				}
				
				System.out.println("RANDOM SENTENCE:   " + randomSentence);
				String randomSubject = randomSentence.getKey();
				String selectedSentence = subjects.get(randomSubject).get(0);
				output.add(selectedSentence);
				String [] sentenceArray = selectedSentence.split(" ");
				String selectedObject = sentenceArray[sentenceArray.length-1];
				System.out.println("random object: " + selectedObject);
				System.out.println("selected sentence: " + selectedSentence);
				output.add(selectedSentence);
				// remove output sentence from hash maps
				System.out.println("Selected object that doesnt exist: " + randomSubject + "\n the real array is: " + subjects);
				if( subjects.get(randomSubject) !=null && !subjects.get(randomSubject).isEmpty()  )
				subjects.get(randomSubject).remove(selectedSentence);
				if(objects.get(selectedObject) !=null && !objects.get(selectedObject).isEmpty() )
				objects.get(selectedObject).remove(selectedSentence);
//				subToObj.get(randomSubject).remove(selectedObject);
				removeEmptyHashes(randomSubject, selectedObject);
				nextSubject = selectedObject;
			}

		}
		System.out.println("**********MY FUCKING OUTPUT: " + output);

		return reStructureSentence(output);
	}

	public static void removeEmptyHashes(String sub, String obj) {
		if (subjects.get(sub) == null || subjects.get(sub).isEmpty()) {
			subjects.remove(sub);
		}
		if (objects.get(obj) == null || objects.get(obj).isEmpty()) {
			objects.remove(obj);
		}
//		if (subToObj.get(sub) == null || subToObj.get(sub).isEmpty()) {
//			subjects.remove(sub);
//		}
	}

	public static String reStructureSentence(ArrayList<String> output) {
		String out = "";
		for (int i = 0; i < output.size(); i++) {
			st = new StringTokenizer(output.get(i));
			while (st.hasMoreTokens()) {
				out += getCorrectness(st.nextToken()) + " ";
			}
			out += "\n";
		}
		return out;
	}

	public static void checkRule(String s) {
		String p1;
		char p11 = '\0';
		char p22 = '\0';
		String p2 = "";
		char prop1 = '\0';
		char prop2 = '\0';
		st = new StringTokenizer(s);
		System.out.println("HOBAAAAA");

		String m = st.nextToken();
		System.out.println("________________________________________________________" + m);
		int c = m.substring(5).indexOf(40);
		System.out.println("C!!!!! should be bracket " + c);
		p1 = m.substring(5, c + 5);
		p11 = m.substring(c + 7, c + 8).charAt(0);
		m = st.nextToken();
		System.out.println(m);
		c = m.indexOf(40);
		p2 = m.substring(0, c);
		p22 = m.substring(c + 2, c + 3).charAt(0);
		st.nextToken();
		m = st.nextToken();
		System.out.println(m);
		c = m.indexOf(40);
		prop1 = m.substring(c + 2, c + 3).charAt(0);
		m = st.nextToken();
		System.out.println(m);
		prop2 = m.substring(1, 2).charAt(0);
		System.out.println("p1: " + p1 + "----- p2: " + p2 + "----- p11: " + p11 + "----- p22: " + p22
				+ "------- prop1: " + prop1 + "----- prop2: " + prop2);
		if (p11 == prop1) {
			System.out.println("AM I HEEEERE?");
			ruleClass1 = p1;
			ruleClass2 = p2;
		} else if (p11 == prop2) {
			System.out.println("OR AM I THEEEEERE?");
			ruleClass1 = p2;
			ruleClass2 = p1;
		}
		System.out.println(mapperClasses);

	}

	public static int getCountExplanations() {
		return countExplanations;
	}

	public static String getRuleClass1() {
		return ruleClass1;
	}

	public static String getRuleClass2() {
		return ruleClass2;
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

	public static String getOutputFromFile() throws IOException {
		fr = new FileReader(outputFile3);
		br = new BufferedReader(fr);
		String result = "";
		String s = br.readLine();
		while (s != null) {
			result += s + "\n";
		}
		return result;

	}

	public static String disjointPrinting(String s) throws IOException {
		String result = "";
		System.out.println("what is this??" + s);
		result += s.substring(21) + ", ";
		String component = buffer;
		component.trim();
		while (component.charAt(component.length() - 1) != ')') {
			result += getCorrectness(component);
			result += ", ";
			if (index < resSize)
				buffer = ans.get(index++);
			else
				break;
			component = buffer;
		}
		component.replace(" ", "");
		component = component.substring(0, component.length());

		if (component != null)
			result += getCorrectness(component);

		System.out.println("RESULT IIIIIISSSS: " + result);
		result += " are disjoint classes";
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

	public static String splitCamelCase(String s) {
		return s.replaceAll(String.format("(?<!(^|[A-Z0-9]))(?=[A-Z0-9])|(?<!^)(?=[A-Z][a-z])"), " ");
	}

	public static void inferOntology() throws OWLOntologyCreationException {
		reasoner.precomputeInferences(InferenceType.CLASS_HIERARCHY);
		reasoner.precomputeInferences(InferenceType.OBJECT_PROPERTY_HIERARCHY);
		reasoner.precomputeInferences(InferenceType.OBJECT_PROPERTY_ASSERTIONS);
		reasoner.precomputeInferences(InferenceType.DIFFERENT_INDIVIDUALS);

		List<InferredAxiomGenerator<? extends OWLAxiom>> gens = new ArrayList<InferredAxiomGenerator<? extends OWLAxiom>>();

		gens.add(new InferredSubClassAxiomGenerator());
		gens.add(new InferredEquivalentClassAxiomGenerator());
		gens.add(new InferredSubClassAxiomGenerator());
		gens.add(new InferredClassAssertionAxiomGenerator());
		gens.add(new InferredDisjointClassesAxiomGenerator());
		gens.add(new InferredEquivalentClassAxiomGenerator());
		gens.add(new InferredEquivalentDataPropertiesAxiomGenerator());
		gens.add(new InferredEquivalentObjectPropertyAxiomGenerator());
		gens.add(new InferredInverseObjectPropertiesAxiomGenerator());
		gens.add(new InferredObjectPropertyCharacteristicAxiomGenerator());
		gens.add(new InferredPropertyAssertionGenerator());
		gens.add(new InferredSubDataPropertyAxiomGenerator());
		gens.add(new InferredDataPropertyCharacteristicAxiomGenerator());
		gens.add(new InferredSubObjectPropertyAxiomGenerator());

		reasoner.getKB().realize();
		InferredOntologyGenerator iog = new InferredOntologyGenerator(reasoner, gens);
		infOnt = manager.createOntology();
		iog.fillOntology(reasoner.getManager().getOWLDataFactory(), infOnt);

		for (OWLOntology o : manager.getImportsClosure(ontology)) {
			for (OWLAnnotationAssertionAxiom ax : o.getAxioms(AxiomType.ANNOTATION_ASSERTION)) {
				manager.applyChange(new AddAxiom(infOnt, ax));
			}
		}

		// ontology = infOnt;
		fac = manager.getOWLDataFactory();
		reasoner = PelletReasonerFactory.getInstance().createReasoner(infOnt);
		ontology = infOnt;
	}

}