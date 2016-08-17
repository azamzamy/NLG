package org.mindswap.pellet.gui;

import org.mindswap.pellet.Bachelor.NLGEngine;
import org.semanticweb.owlapi.model.OWLObjectProperty;

import com.github.jsonldjava.utils.Obj;

public class PropertyObj {
	
	OWLObjectProperty obj;
	String value;
	
	public PropertyObj(OWLObjectProperty ob){
		obj = ob;
		value = obj.getIRI().getShortForm();
	}
	
	public OWLObjectProperty getObj() {
		return obj;
	}
	public void setObj(OWLObjectProperty obj) {
		this.obj = obj;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return value;
	}
	
	
	

}
