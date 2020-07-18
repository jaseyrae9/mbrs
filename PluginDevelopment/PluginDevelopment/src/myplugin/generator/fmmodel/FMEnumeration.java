package myplugin.generator.fmmodel;

import java.util.ArrayList;

public class FMEnumeration extends FMElement {
	private ArrayList<String> values = new ArrayList<String>();

	public FMEnumeration(String name) {
		super(name);
	}

	public ArrayList<String> getValues() {
		return values;
	}

	public void setValues(ArrayList<String> values) {
		this.values = values;
	}

}
