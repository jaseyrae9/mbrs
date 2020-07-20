package myplugin.generator.fmmodel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FMMicroservice extends FMElement {
	public static final String dbUrlField = "dbUrl";
	public static final String dbUsernameField = "dbUsername";
	public static final String dbPasswordField = "dbPassword";
	
	private String dbUrl;
	private String dbUsername;
	private String dbPassword;
	private List<FMEnumeration> enumerations = new ArrayList<FMEnumeration>();
	private List<FMClass> classes = new ArrayList<FMClass>();
	
	public FMMicroservice(String name) {
		super(name);
	}

	public FMMicroservice(String name, String dbUrl, String dbUsername, String dbPassword) {
		super(name);
		this.dbUrl = dbUrl;
		this.dbUsername = dbUsername;
		this.dbPassword = dbPassword;
	}

	public String getDbUrl() {
		return dbUrl;
	}

	public void setDbUrl(String dbUrl) {
		this.dbUrl = dbUrl;
	}

	public String getDbUsername() {
		return dbUsername;
	}

	public void setDbUsername(String dbUsername) {
		this.dbUsername = dbUsername;
	}

	public String getDbPassword() {
		return dbPassword;
	}

	public void setDbPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}

	public List<FMEnumeration> getEnumerations() {
		return enumerations;
	}

	public void setEnumerations(List<FMEnumeration> enumerations) {
		this.enumerations = enumerations;
	}

	public List<FMClass> getClasses() {
		return classes;
	}
	
	public List<FMClass> getPersistantClasses(){
		return classes.stream().filter(c -> c.isPersistant()).collect(Collectors.toList());
	}

	public void setClasses(List<FMClass> classes) {
		this.classes = classes;
	}
}
