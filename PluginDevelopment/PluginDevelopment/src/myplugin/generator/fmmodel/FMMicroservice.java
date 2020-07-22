package myplugin.generator.fmmodel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FMMicroservice extends FMElement {
	public static final String groupIdField = "groupId";
	public static final String artifactIdField = "artifactId";
	public static final String versionField = "version";
	public static final String portField = "port";
	public static final String dbUrlField = "dbUrl";
	public static final String dbUsernameField = "dbUsername";
	public static final String dbPasswordField = "dbPassword";

	private String groupId;
	private String artifactId;
	private String version;
	private Integer port;
	private String dbUrl;
	private String dbUsername;
	private String dbPassword;
	private List<FMEnumeration> enumerations = new ArrayList<FMEnumeration>();
	private List<FMClass> classes = new ArrayList<FMClass>();
	private List<FMClass> feignClasses = new ArrayList<FMClass>();

	public FMMicroservice(String magicDrawId, String name) {
		super(magicDrawId, name);
		groupId = "unknown";
		artifactId = name.toLowerCase();
		version = "0.1";
		port = 8080;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getArtifactId() {
		return artifactId;
	}

	public void setArtifactId(String artifactId) {
		this.artifactId = artifactId;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
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

	public List<FMClass> getPersistantClasses() {
		return classes.stream().filter(c -> c.isPersistant()).collect(Collectors.toList());
	}

	public void setClasses(List<FMClass> classes) {
		this.classes = classes;
	}

	public List<FMClass> getFeignClasses() {
		return feignClasses;
	}

	public void setFeignClasses(List<FMClass> feignClasses) {
		this.feignClasses = feignClasses;
	}
}
