package org.mindswap.pellet.gui;

import org.mindswap.pellet.Bachelor.Wine;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLNamedIndividual;

public class IndObj {

	   private OWLNamedIndividual cls;
	    private String value;
	    
	    public IndObj(OWLNamedIndividual cl) {
	        cls = cl;
	        this.value = cls.getIRI().getShortForm();
	        
	    }
	    
	    
	    public OWLNamedIndividual getIndOf() {
	        return cls;
	    }

	    public String getValue() {
	        return value;
	    }
	    
	    public String toString(){
	    	return Wine.getCorrectness(value);
	    }
	}
