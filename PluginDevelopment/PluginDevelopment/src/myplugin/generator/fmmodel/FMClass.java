package myplugin.generator.fmmodel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class FMClass extends FMType {
	public static final String schemeField = "scheme";
	public static final String tableNameField = "tableName";
	public static final String generateCreateField = "generateCreate";
	public static final String generateUpdateField = "generateUpdate";
	public static final String generateDeleteField = "generateDelete";
	public static final String generateReadOneField = "generateReadOne";
	public static final String generateReadAllField = "generateReadAll";
	
	private boolean isPersistant;
	private String scheme;
	private String tableName;
	private boolean generateCreate;
	private boolean generateUpdate;
	private boolean generateDelete;
	private boolean generateReadOne;
	private boolean generateReadAll;

	//Class properties
	private List<FMProperty> fmProperties = new ArrayList<FMProperty>();
	
	public FMClass(String magicDrawId, String name, String typePackage, FMMicroservice microservice) {
		super(magicDrawId, name, typePackage, false, true, false, microservice);
		isPersistant = false;
		generateCreate = true;
		generateUpdate = true;
		generateDelete = true;
		generateReadOne = true;
		generateReadAll = true;
	}
	
	public boolean isPersistant() {
		return isPersistant;
	}

	public void setPersistant(boolean isPersistant) {
		this.isPersistant = isPersistant;
	}

	public String getScheme() {
		return scheme;
	}

	public void setScheme(String scheme) {
		this.scheme = scheme;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public boolean isGenerateCreate() {
		return generateCreate;
	}

	public void setGenerateCreate(boolean generateCreate) {
		this.generateCreate = generateCreate;
	}

	public boolean isGenerateUpdate() {
		return generateUpdate;
	}

	public void setGenerateUpdate(boolean generateUpdate) {
		this.generateUpdate = generateUpdate;
	}

	public boolean isGenerateDelete() {
		return generateDelete;
	}

	public void setGenerateDelete(boolean generateDelete) {
		this.generateDelete = generateDelete;
	}

	public boolean isGenerateReadOne() {
		return generateReadOne;
	}

	public void setGenerateReadOne(boolean generateReadOne) {
		this.generateReadOne = generateReadOne;
	}

	public boolean isGenerateReadAll() {
		return generateReadAll;
	}

	public void setGenerateReadAll(boolean generateReadAll) {
		this.generateReadAll = generateReadAll;
	}

	public List<FMProperty> getProperties() {
		return fmProperties;
	}

	public void setProperties(List<FMProperty> fmProperties) {
		this.fmProperties = fmProperties;
	}
	
	public FMType getKeyType() {
		Stream<FMPeristantProperty> persistantProperties = this.fmProperties.stream()
				.filter(p-> p instanceof FMPeristantProperty)
				.map(p -> (FMPeristantProperty)p);
		Optional<FMPeristantProperty> key = persistantProperties.filter(p -> p.isKey()).findFirst();
		if(!key.isPresent()) {
			return null;
		}
		return key.get().getType();
	}	
}
