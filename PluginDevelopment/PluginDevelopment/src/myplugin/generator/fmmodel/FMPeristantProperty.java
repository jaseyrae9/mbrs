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
		key = false;
		unique = false;
		generator = FMGeneratorType.NONE;
	}
	
	public FMPeristantProperty(FMProperty property) {
		super(property.getMagicDrawId(), property.getName(), property.getType(), property.getVisibility(), property.getLower(), property.getUpper());
		key = false;
		unique = false;
		generator = FMGeneratorType.NONE;
	}
	
	public Boolean getKey() {
		return key;
	}

	public void setKey(Boolean key) {
		this.key = key;
	}

	public Boolean getUnique() {
		return unique;
	}

	public void setUnique(Boolean unique) {
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
