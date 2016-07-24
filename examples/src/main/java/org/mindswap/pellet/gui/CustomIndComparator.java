package org.mindswap.pellet.gui;

import java.util.Comparator;

public class CustomIndComparator implements Comparator<IndObj> {

	@Override
	public int compare(IndObj cl1, IndObj cl2) {

		return cl1.toString().compareTo(cl2.toString());

	}

}
