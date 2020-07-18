package myplugin.generator.fmmodel;

public class FMType extends FMElement {
	//Qualified package name, used for import declaration 
	//Empty string for standard library types
	private String typePackage;
	private boolean classType;
	
	public FMType() {
		super();
	}
	
	public FMType(String name, String typePackage) {
		super(name);
		this.typePackage = typePackage;
	}

	public FMType(String name, String typePackage, boolean classType) {
		this(name, typePackage);
		this.classType = classType;
	}

	public String getTypePackage() {
		return typePackage;
	}

	public void setTypePackage(String typePackage) {
		this.typePackage = typePackage;
	}
	
	public void setClassType(boolean classType) {
		this.classType = classType;
	}
	
	public boolean isClassType() {
		return classType;
	}
}
