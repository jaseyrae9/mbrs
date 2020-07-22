package myplugin.generator.fmmodel;

import java.util.ArrayList;

public class FMEnumeration extends FMType {
	private ArrayList<String> values = new ArrayList<String>();

	public FMEnumeration(String magicDrawId, String name, String typePackage, FMMicroservice microservice) {
		super(magicDrawId, name, typePackage, false, false, true, "0", microservice);
	}

	public ArrayList<String> getValues() {
		return values;
	}

	public void setValues(ArrayList<String> values) {
		this.values = values;
	}

}
