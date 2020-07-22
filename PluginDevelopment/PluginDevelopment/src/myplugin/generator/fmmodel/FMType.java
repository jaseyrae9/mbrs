package myplugin.generator.fmmodel;

public class FMType extends FMElement {
	// Qualified package name, used for import declaration
	// Empty string for standard library types
	private FMMicroservice microservice;
	private String typePackage;
	private boolean umlType;
	private boolean classType;
	private boolean enumType;
	// ovo je za generisanje postman testova
	private String defaultValue;

	public FMType(String magicDrawId, String name, String typePackage, boolean umlType, boolean classType,
			boolean enumType, String defaultValue) {
		super(magicDrawId, name);
		this.typePackage = typePackage;
		this.umlType = umlType;
		this.classType = classType;
		this.enumType = enumType;
		this.defaultValue = defaultValue;
	}

	public FMType(String magicDrawId, String name, String typePackage, boolean umlType, boolean classType,
			boolean enumType, String defaultValue, FMMicroservice microservice) {
		this(magicDrawId, name, typePackage, umlType, classType, enumType, defaultValue);
		this.microservice = microservice;
	}

	public FMMicroservice getMicroservice() {
		return microservice;
	}

	public void setMicroservice(FMMicroservice microservice) {
		this.microservice = microservice;
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

	public boolean isEnumType() {
		return enumType;
	}

	public void setEnumType(boolean enumType) {
		this.enumType = enumType;
	}

	public boolean isUmlType() {
		return umlType;
	}

	public void setUmlType(boolean umlType) {
		this.umlType = umlType;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

}
