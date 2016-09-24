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

import simplenlg.framework.*;
import simplenlg.lexicon.*;
import simplenlg.realiser.english.*;
import simplenlg.phrasespec.*;
import simplenlg.features.*;

public class NLGEngine {

	private static final String NS = "http://www.w3.org/TR/2003/PR-owl-guide-20031209/food#";
	private static final String NSWine = "http://www.w3.org/TR/2003/PR-owl-guide-20031209/wine#";
	private static String outputFile1 = "/Users/zamzamy/Desktop/pellet2/examples/src/main/resources/data/output.txt";
	private static String outputFile2 = "/Users/zamzamy/Desktop/pellet2/examples/src/main/resources/data/out2.txt";
	private static String outputFile3 = "/Users/zamzamy/Desktop/pellet2/examples/src/main/resources/data/out3.txt";
	private static String file;
	private static PrintWriter out;
	private static PrintWriter out2;
	private static String lang = "en";
	private static String country = "US";
	private static Locale currentLocale = new Locale(lang, country);
	private static final ResourceBundle messages = ResourceBundle.getBundle("MessagesBundle", currentLocale);
	private static PrintWriter outer;
	private static FileReader fr;
	private static BufferedReader br;
	private static StringTokenizer st;

	private static OWLDataFactory fac;
	private static OWLOntology ontology;
	private static OWLOntology infOnt;
	private static OWLOntologyManager manager;
	private static ManchesterSyntaxExplanationRenderer renderer;
	private static PelletReasoner reasoner;
	private static PelletExplanation expGen;

	private static boolean lastSentence;
	private static boolean sameSentence = false;
	private static int index = 0;
	private static int resSize = 0;
	private static int max;
	private static int countExplanations;

	private static String ruleClass1;
	private static String ruleClass2;
	private static String target = "";
	private static String statement;
	private static String buffer;
	private static String line = "";
	private static ArrayList<OWLObjectProperty> objectProps;
	private static ArrayList<BiValue> mapperProps;
	private static ArrayList<BiValue> mapperClasses;
	private static ArrayList<String> strings;
	private static ArrayList<String> ans;
	private static ArrayList<String> output;
	private static ArrayList<String> finalOutput; 
	private static HashMap<String, ArrayList<String>> subjects;
	private static HashMap<String, ArrayList<String>> objects;

	public NLGEngine(String f, int count) throws OWLOntologyCreationException, OWLException, IOException {
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
//		addObjectProperties();
		PelletOptions.DL_SAFE_RULES = false;
		subjects = new HashMap<String, ArrayList<String>>();
		objects = new HashMap<String, ArrayList<String>>();

	}

	public static String getTarget() {
		return target;
	}

	public static void setTarget(String target) {
		NLGEngine.target = target;
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

		// System.out.println(ontology.getClassesInSignature());
		return ontology.getClassesInSignature();

	}

	public static void initObjectProperties() {

		Set<OWLObjectProperty> objProperties = ontology.getObjectPropertiesInSignature();
		Iterator<OWLObjectProperty> it = objProperties.iterator();
		while(it.hasNext()){
			OWLObjectProperty obj = it.next();
			System.out.println(obj.getIRI().getShortForm());
			objectProps.add(obj);
		}
		mapperClasses.add(new BiValue("RedBordeaux", "wine"));
		mapperClasses.add(new BiValue("DarkMeatFowlCourse", "food"));
	}

	public static void removeExtras() throws IOException {

		FileReader fileR = new FileReader(outputFile2);
		br = new BufferedReader(fileR);
		out = new PrintWriter(outputFile3);

		int arIndex = 0;
		System.out.println("3andak aho el final output size ya m3alem shof enta b2a: " + finalOutput.size());
		String finalString = "";
		String s2 = "";
		String s = finalOutput.get(arIndex++);
		if(arIndex<finalOutput.size())
		s2 = finalOutput.get(arIndex++);
		while (s != null || s.equals("")) {
			//System.out.println("RX: " + s);

			if (s.equals(s2))
				if(arIndex < finalOutput.size())
					s2 = finalOutput.get(arIndex++);
			s = s.replaceFirst(" and ", " that ");
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
			// res.replace("is of type has the color", "has the color");

			try {
				finale = res.substring(0, res.length()) + ".";

			} catch (Exception e) {
				System.out.println("caught an exception");
			}

			// System.out.println("FINALE: " + finale);
				finalString += "- " + finale + "\n";
		
				s = s2;
			if(arIndex < finalOutput.size())
				s2 = finalOutput.get(arIndex++);
			else break;
		}
		out.println(finalString);
		out.close();

	}

	private static String modifyStatements(String s) {

		if (s.matches("\\d.*")) {
			s = s.substring(s.lastIndexOf(41) + 1);
		}
		s = s.replace("is a special kind of has", "has");
		s = s.replace("not contains", "does not contain");
		s = s.replace("contains only", "only contains");
		s = s.replaceAll("drinks is", "Drinks are");
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

	private static boolean isContain(String source, String subItem) {
		String pattern = "\\b" + subItem + "\\b";
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(source);
		return m.find();
	}

	public ArrayList<OWLObjectProperty> getDataProperties() {

		String x = "";
		x.lastIndexOf(".");

		return objectProps;

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

		subjects = new HashMap<String, ArrayList<String>>();
		objects = new HashMap<String, ArrayList<String>>();

	}

	

	public static String disjointPrinting(String s) throws IOException {
		String result = "";
		System.out.println("what is this??" + s);
		s = s.trim();
		result += s.substring(16) + ", ";
		System.out.println("DisjointClasses????? :::::::::::::::: " + result);
		String component = buffer;

		while (component.charAt(component.length() - 1) != ')') {
			component = component.trim();
			System.out.println("component: " + component);
			result += getCorrectness(component);
			result += ", ";
			buffer = ans.get(index++);
			component = buffer;
		}
		component = component.trim();
		component = component.substring(0, component.length());

		if (component != null)
			result += getCorrectness(component);
		result = result.substring(0, result.length() - 1);

		System.out.println("RESULT IIIIIISSSS: " + result);
		result += " are disjoint classes";
		return result;
	}
	
	public void naturalGeneration() throws IOException {

		clearMaps();
		index = 0;
		int resSize = 0;
		fr = new FileReader(outputFile1);
		outer = new PrintWriter(outputFile2);
		br = new BufferedReader(fr);
		
		statement = "";
		String word = "";
		boolean checked = false;
		int count = 0;
		boolean end = false;
		ans = new ArrayList<String>();
		String strLine;
		resSize = ans.size();
		int num = 0;
		max = 0;
		int loop = 0;
		
		while ((strLine = br.readLine()) != null) {
			System.out.println(strLine);
			ans.add(strLine);
		}
		
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
		if (index < resSize)
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

		while (buffer != null) {

			if (buffer.contains("Explanations") || buffer.contains("Functional") || buffer.contains("subPropertyOf"))
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
				String rule = checkRule(statement);
				line += rule + "\n";
				if (index < resSize)
					buffer = ans.get(index++);
				else
					break;
				continue;
			} else if (statement.contains("DisjointClasses")) {
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
				if ((int) orig.charAt(0) == 49) {
					continue;
				}
				String s = orig + " ";

				line += s;
				if (!end){
					line += " ";
				}
			}
			if (end) {
				line += "\n";
				end = false;

			}
		}
		ArrayList<String> outputString = hashStatements(line);
		
		outer.println(outputString);
		removeExtras();
		outer.close();
		countExplanations++;
	}

	public static ArrayList<String> hashStatements(String single) {
		strings = new ArrayList<String>();

		if (max > 1) {
			single = single.substring(single.lastIndexOf(41) + 1);
		}

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

		strings.add(single);

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

		}
		return reArrange();
	}

	public static ArrayList<String> reArrange() {
		output = new ArrayList<String>();
		String nextSubject = "";
		String prevSentence = "";
		String selectedSentence = "";
		Map.Entry<String, ArrayList<String>> randomSentence = null;
		boolean foundMatch = false;
		String sentence = "";
		boolean end = false;
		if (subjects.containsKey(target)) {
			sentence = subjects.get(target).get(0);
			output.add(sentence);
			String[] parts = sentence.split(" ");
			String object = parts[parts.length - 1];
			subjects.get(target).remove(subjects.get(target).indexOf(sentence));
			objects.get(object).remove(objects.get(object).indexOf(sentence));
			removeEmptyHashes(target, object);
			nextSubject = object;
		} else {
			randomSentence = subjects.entrySet().iterator().next();
			String randomSubject = randomSentence.getKey();
			selectedSentence = subjects.get(randomSubject).get(0);
			output.add(selectedSentence);
			String[] sentenceArray = selectedSentence.split(" ");
			String selectedObject = sentenceArray[sentenceArray.length - 1];
			subjects.get(randomSubject).remove(selectedSentence);
			objects.get(selectedObject).remove(selectedSentence);
			removeEmptyHashes(randomSubject, selectedObject);
			nextSubject = selectedObject;
			prevSentence = selectedSentence;
		}
		ArrayList<String> links = new ArrayList<String>();
		for (int i = 0; i < strings.size(); i++) {
			if (end)
				break;
			if (subjects.isEmpty())
				end = true;
			if (subjects.containsKey(nextSubject) && subjects.get(nextSubject) != null) {
				ArrayList<String> selectedSentences = subjects.get(nextSubject);
				selectedSentence = selectedSentences.get(0);
				String[] sentenceArray = selectedSentence.split(" ");
				String selectedObject = sentenceArray[sentenceArray.length - 1];
				String toOutput = selectedSentence;
				output.add(toOutput);
				// remove the output sentence from hash maps
				subjects.get(nextSubject).remove(toOutput);
				objects.get(selectedObject).remove(toOutput);
				removeEmptyHashes(nextSubject, selectedObject);
				nextSubject = selectedObject;
				prevSentence = toOutput;
			} else {
				Iterator<Map.Entry<String, ArrayList<String>>> it = subjects.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry<String, ArrayList<String>> entry = it.next();
					if (prevSentence.length() == 0)
						break;
					for (int z = 0; z < entry.getValue().size(); z++) {
						String entrySentence = entry.getValue().get(z);
						if (entrySentence != null) {
							String[] sentenceArray = entrySentence.split(" ");
							for (int k = 2; k < sentenceArray.length; k++) {
								if (nextSubject.equals(sentenceArray[k])) {
									randomSentence = entry;
									String randomSubject = randomSentence.getKey();
									String[] selectedSentenceArray = entrySentence.split(" ");
									String selectedObject = selectedSentenceArray[selectedSentenceArray.length - 1];
									output.add(entrySentence);
									nextSubject = selectedObject;
									prevSentence = entrySentence;
									if (subjects.get(randomSubject) != null && !subjects.get(randomSubject).isEmpty())
										subjects.get(randomSubject).remove(entrySentence);
									if (objects.get(selectedObject) != null && !objects.get(selectedObject).isEmpty())
										objects.get(selectedObject).remove(entrySentence);
									removeEmptyHashes(randomSubject, selectedObject);
									foundMatch = true;
									break;
								}
							}
						}
						if (foundMatch)
							break;
					}
					if (foundMatch)
						break;
				}
				if (!foundMatch) {
					try {
						randomSentence = subjects.entrySet().iterator().next();
					} catch (Exception e) {
						break;
					}
					String randomSubject = randomSentence.getKey();
					if (subjects.get(randomSubject).get(0) != null)
						selectedSentence = subjects.get(randomSubject).get(0);
					else
						break;
					output.add(selectedSentence);
					String[] sentenceArray = selectedSentence.split(" ");
					String selectedObject = sentenceArray[sentenceArray.length - 1];
					output.add(selectedSentence);
					if (subjects.get(randomSubject) != null && !subjects.get(randomSubject).isEmpty())
						subjects.get(randomSubject).remove(selectedSentence);
					if (objects.get(selectedObject) != null && !objects.get(selectedObject).isEmpty())
						objects.get(selectedObject).remove(selectedSentence);
					removeEmptyHashes(randomSubject, selectedObject);
					nextSubject = selectedObject;
					prevSentence = selectedSentence;
				}
				foundMatch = false;
			}
		}
		return reStructureSentence(output);
	}

	public static void removeEmptyHashes(String sub, String obj) {
		if (subjects.get(sub) == null || subjects.get(sub).isEmpty()) {
			subjects.remove(sub);
		}
		if (objects.get(obj) == null || objects.get(obj).isEmpty()) {
			objects.remove(obj);
		}
	}

	public static ArrayList<String> reStructureSentence(ArrayList<String> outArray) {
		String out = "";
		finalOutput = new ArrayList<String>();
		for (int i = 0; i < output.size(); i++) {
			out="";
			if (outArray.get(i).contains("or")) {
				String modified = addEither(outArray.get(i));
				st = new StringTokenizer(modified);
				while (st.hasMoreTokens()) {
					out += getCorrectness(st.nextToken()) + " ";
				}
				finalOutput.add(out);
			} else {
				st = new StringTokenizer(outArray.get(i));
				while (st.hasMoreTokens()) {
					out += getCorrectness(st.nextToken()) + " ";
				}
				finalOutput.add(out);

			}
		}
		return finalOutput;
	}

	public static String addEither(String op) {
		String res = "";
		op.indexOf("or");
		StringTokenizer tokenizer = new StringTokenizer(op);
		String curr = "";
		String next = "";
		while (tokenizer.hasMoreTokens()) {
			curr = tokenizer.nextToken();
			if(tokenizer.hasMoreTokens()) next = tokenizer.nextToken();
			else{
				res += curr; 
				break;
			}
			if(next.equals("or")){
					res += " either " + curr + " " + next + " ";
					break;
				}
				else {
					res += curr + " " + next + " ";
				}
		}
		while (tokenizer.hasMoreTokens()) {
			res += " " + tokenizer.nextToken() + " ";
		}
		return res;
	}

	public static String checkRule(String s) {
		String p1;
		char p11 = '\0';
		char p22 = '\0';
		String p2 = "";
		char prop1 = '\0';
		char prop2 = '\0';
		st = new StringTokenizer(s);
		String m = st.nextToken();
		int c = m.substring(5).indexOf(40);
		p1 = m.substring(5, c + 5);
		p11 = m.substring(c + 7, c + 8).charAt(0);
		m = st.nextToken();
		c = m.indexOf(40);
		p2 = m.substring(0, c);
		p22 = m.substring(c + 2, c + 3).charAt(0);
		st.nextToken();
		m = st.nextToken();
		c = m.indexOf(40);
		prop1 = m.substring(c + 2, c + 3).charAt(0);
		m = st.nextToken();
		String objectProperty = m.substring(0, m.length() - 4);
		prop2 = m.substring(1, 2).charAt(0);
		if (p11 == prop1) {
			ruleClass1 = p1;
			ruleClass2 = p2;
		} else if (p11 == prop2) {
			ruleClass1 = p2;
			ruleClass2 = p1;
		}
		return p2 + " " + objectProperty + p1;

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
			if ((int) buffer.charAt(i) == 32)
				continue;
			else
				break;

		}
		System.out.println();
		if (i > 12)
			return false;
		return true;

	}

	public static int endOfSentence(String statement) {
		int tabs = 0;

		for (int i = 0; i < statement.length() - 1; i++) {

			if ((int) statement.charAt(i) == 32)
				tabs++;
		}
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

	public static String getCorrectness(String s) {

		String ret = "";
		try {
			if (s.contains("value"))
				return "";
			messages.getString("" + s + "");
		} catch (Exception e) {
			ret = splitCamelCase(s);
			return ret;
			
		}
		
		ret = messages.getString("" + s + "");
		return ret; 
		

	}

	public static String splitCamelCase(String s) {
		s = s.replaceAll(String.format("(?<!(^|[A-Z0-9]))(?=[A-Z0-9])|(?<!^)(?=[A-Z][a-z])"), " ");
		String[] stringArray = s.split(" ");
		for (int i = 1; i < stringArray.length; i++) {
			stringArray[i] = stringArray[i].toLowerCase();
		}
		String result = "";
		for (int i = 0; i < stringArray.length; i++) {
			if (i == stringArray.length - 1)
				result += stringArray[i];
			else
				result += stringArray[i] + " ";
		}
		return result;

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
		fac = manager.getOWLDataFactory();
		reasoner = PelletReasonerFactory.getInstance().createReasoner(infOnt);
		ontology = infOnt;
	}

}