package org.mindswap.pellet.gui;

import org.mindswap.pellet.Bachelor.Wine;
import org.semanticweb.owlapi.model.OWLClass;

public class ClassObj {

	   private OWLClass cls;
	    private String value;

	    public ClassObj(OWLClass cl) {
	        cls = cl;
	        this.value = cls.getIRI().getShortForm();
	    }

	    public OWLClass getClassOf() {
	        return cls;
	    }

	    public String getValue() {
	        return value;
	    }

		@Override
		public String toString() {
			return Wine.getCorrectness(value);
		}

}
