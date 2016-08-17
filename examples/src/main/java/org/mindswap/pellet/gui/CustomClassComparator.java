package org.mindswap.pellet.gui;

import java.util.Comparator;

public class CustomClassComparator implements Comparator<ClassObj> {

	@Override
	public int compare(ClassObj cl1, ClassObj cl2) {

		return cl1.toString().compareTo(cl2.toString());

	}

}
