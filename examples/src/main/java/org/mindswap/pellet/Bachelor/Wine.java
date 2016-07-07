package org.mindswap.pellet.Bachelor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import com.clarkparsia.owlapi.explanation.GlassBoxExplanation;
import com.clarkparsia.owlapi.explanation.HSTExplanationGenerator;
import com.clarkparsia.owlapi.explanation.MultipleExplanationGenerator;
import com.clarkparsia.owlapi.explanation.PelletExplanation;
import com.clarkparsia.owlapi.explanation.SatisfiabilityConverter;
import com.clarkparsia.owlapi.explanation.io.manchester.ManchesterSyntaxExplanationRenderer;
import com.clarkparsia.owlapiv3.OWL;
import com.clarkparsia.pellet.owlapiv3.OWLAPILoader;
import com.clarkparsia.pellet.owlapiv3.PelletReasoner;
import com.clarkparsia.pellet.owlapiv3.PelletReasonerFactory;

public class Wine  {

	static int count = 0;

	public static void main(String[] args) throws Exception {

		Wine expl = new Wine();
		//expl.run();
	}
//
//	@Override
//	public String getAppId() {
//		// TODO Auto-generated method stub
//		return "Explanation";
//	}
//
//	@Override
//	public String getAppCmd() {
//		// TODO Auto-generated method stub
//		return "Explanation";
//	}
//
//	@Override
//	public PelletCmdOptions getOptions() {
//		// TODO Auto-generated method stub
//		PelletCmdOptions options = getGlobalOptions();
//
//		options.add(getIgnoreImportsOption());
//
//		return options;
//	}
//
//	// public static void runWithJena() {
//	// // ontology that will be used
//	//
//	// // String ont = "http://www.mindswap.org/2004/owl/mindswappers#";
//	// String ont = "file:src/main/resources/data/people+pets.owl";
//	// String NS = "http://cohse.semanticweb.org/ontologies/people#";
//	//
//	// // load the ontology with its imports and no reasoning
//	// OntModel model =
//	// ModelFactory.createOntologyModel(PelletReasonerFactory.THE_SPEC);
//	// model.read(ont);
//	//
//	// // load the model to the reasoner
//	// model.prepare();
//	//
//	// Property foafName =
//	// model.getProperty("http://cohse.semanticweb.org/ontologies/people#has_father");
//	// OntClass man = model.getOntClass(NS + "man");
//	//
//	// try {
//	// ExtendedIterator properties = model.listAllOntProperties();
//	// while (properties.hasNext()) {
//	// OntProperty prop = (OntProperty) properties.next();
//	// System.out.println("property name: " + prop.getURI());
//	//
//	// }
//	//
//	// ExtendedIterator classes = model.listClasses();
//	// while (classes.hasNext()) {
//	// OntClass essaClasse = (OntClass) classes.next();
//	// System.out.println("Class name: " + essaClasse.getURI());
//	// }
//	// } catch (Exception e) {
//	// System.out.println(e.getMessage());
//	// }
//	//
//	// // get all instances of wineFlavor class
//	// Iterator<?> i = man.listInstances();
//	// while (i.hasNext()) {
//	//
//	// Individual ind = (Individual) i.next();
//	// Resource type = ind.getRDFType();
//	//
//	// System.out.println("Type: " + type.getLocalName() + "\n Name: " +
//	// ind.getLabel(null));
//	// System.out.println();
//	// }
//	//
//	// }
//
//	@Override
//	public void run() throws OWLException, UnsupportedOperationException, IOException {
//
//		String ont = "file:src/main/resources/data/wine.owl";
//		String NS = "http://www.w3.org/TR/2003/PR-owl-guide-20031209/wine#";
//
//		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
//		OWLDataFactory factory = manager.getOWLDataFactory();
//		OWLOntology ontology = manager.loadOntology(IRI.create(ont));
//
//		PelletReasoner reasoner = PelletReasonerFactory.getInstance().createReasoner(ontology);
//
//		OWLClass wineFlavor = OWL.Class(NS + "WineFlavor");
//		OWLClass wineTaste = OWL.Class(NS + "WineTaste");
//
//		Set<OWLNamedIndividual> individuals = reasoner.getInstances(wineFlavor, false).getFlattened();
//		// check boolean
//		ManchesterSyntaxExplanationRenderer renderer = new ManchesterSyntaxExplanationRenderer();
//
//		PrintWriter out = new PrintWriter(System.out);
//
//		renderer.startRendering(out);
//
//		MultipleExplanationGenerator generator = new HSTExplanationGenerator(new GlassBoxExplanation(reasoner));
//		out.println("CHECK");
//
//		// OWLClass oldLady = factory.getOWLClass(IRI.create(NS + "old+lady"));
//
//		PelletExplanation expGen = new PelletExplanation(reasoner);
//		expGen.setup();
//
//		// Set<Set<OWLAxiom>> exp = expGen.getSubClassExplanations(wineFlavor,
//		// wineTaste);
//		out.println("before rendering");
//		// renderer.render(exp);
//		out.println("after rendering");
//
//		for (OWLNamedIndividual ind : individuals) {
//
//			IRI cIRI = ind.getIRI();
//			out.println(cIRI);
//			out.println(ontology.getAnnotationAssertionAxioms(cIRI).toString());
//			for (OWLAnnotationAssertionAxiom a : ontology.getAnnotationAssertionAxioms(cIRI)) {
//
//				if (a.getValue() instanceof OWLLiteral) {
//
//					OWLLiteral val = (OWLLiteral) a.getValue();
//					out.println(wineFlavor + " labelled " + val.getLiteral());
//
//					// System.out.println(count++);
//					// Set<Set<OWLAxiom>> exp =
//					// expGen.getInstanceExplanations(ind, oldLady);
//
//					OWLAxiom axiom = OWL.classAssertion(ind, wineFlavor);
//
//					OWLAPILoader loader = (OWLAPILoader) this.getLoader("OWLAPIv3");
//
//					SatisfiabilityConverter converter = new SatisfiabilityConverter(
//							loader.getManager().getOWLDataFactory());
//					out.println("HERE3");
//					OWLClassExpression classExp = converter.convert(axiom);
//					out.println("Class Expression: " + classExp.toString());
//					Set<Set<OWLAxiom>> exp2 = expGen.getSubClassExplanations(wineFlavor, wineTaste);
//
//					try {
//
//						out.println("Explain: " + ind.getClass());
//
//					} catch (Exception E) {
//						out.println(E.toString());
//					}
//				} else
//					out.println(true);
//
//			}
//		}
//		renderer.endRendering();
//		out.close();
//
//	}

}
