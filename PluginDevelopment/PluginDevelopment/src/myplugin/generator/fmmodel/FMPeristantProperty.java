package myplugin.generator.fmmodel;

public class FMPeristantProperty extends FMProperty {
	
	public static final String isKeyField = "isKey";
	public static final String isUniqueField = "isUnique";
	public static final String lengthField = "length";
	public static final String precisionField = "precision";
	public static final String scaleField = "scale";
	public static final String generatorField = "generator";
	
	private Boolean key;
	private Boolean unique;
	private Integer length;
	private Integer precision;
	private Integer scale;
	private FMGeneratorType generator;
	
	public FMPeristantProperty(String magicDrawId, String name, FMType type, String visibility, int lower, int upper) {
		super(magicDrawId, name, type, visibility, lower, upper);
		// TODO Auto-generated constructor stub
	}
	
	public FMPeristantProperty(FMProperty property) {
		super(property.getMagicDrawId(), property.getName(), property.getType(), property.getVisibility(), property.getLower(), property.getUpper());
	}
	
	public Boolean getIsKey() {
		return key;
	}
	public void setIsKey(Boolean key) {
		this.key = key;
	}
	public Boolean getIsUnique() {
		return unique;
	}
	public void setIsUnique(Boolean unique) {
		this.unique = unique;
	}
	public Integer getLength() {
		return length;
	}
	public void setLength(Integer length) {
		this.length = length;
	}
	public Integer getPrecision() {
		return precision;
	}
	public void setPrecision(Integer precision) {
		this.precision = precision;
	}
	public Integer getScale() {
		return scale;
	}
	public void setScale(Integer scale) {
		this.scale = scale;
	}
	public FMGeneratorType getGenerator() {
		return generator;
	}
	public void setGenerator(FMGeneratorType generator) {
		this.generator = generator;
	}
	
	
}
