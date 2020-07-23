package myplugin.generator.fmmodel;

public class FMLinkedProperty extends FMProperty {

	public static final String mappedByField = "mappedBy";
	public static final String fetchField = "fetch";
	public static final String cascadeField = "cascade";
	public static final String optionalField = "optional";
	public static final String orphanRemovalField = "orphanRemoval";
	
	private String mappedBy;
	private FMFetchType fetch;
	private FMCascadeType cascade;
	private Boolean optional;
	private Boolean orphanRemoval;

	private FMLinkedProperty oppositeEnd;

	public FMLinkedProperty(String magicDrawId, String name, FMType type, String visibility, int lower, int upper) {
		super(magicDrawId, name, type, visibility, lower, upper);
	}
	
	public FMLinkedProperty(FMProperty property) {
		super(property.getMagicDrawId(), property.getName(), property.getType(), property.getVisibility(), property.getLower(), property.getUpper());
		super.setColumnName(property.getColumnName());
		super.setCreateSetter(property.isCreateSetter());
		super.setCreateGetter(property.isCreateGetter());
		setPersistant(property.isPersistant());
	}
	
	public String getMappedBy() {
		return mappedBy;
	}

	public void setMappedBy(String mappedBy) {
		this.mappedBy = mappedBy;
	}

	public FMFetchType getFetch() {
		return fetch;
	}

	public void setFetch(FMFetchType fetch) {
		this.fetch = fetch;
	}

	public FMCascadeType getCascade() {
		return cascade;
	}

	public void setCascade(FMCascadeType cascade) {
		this.cascade = cascade;
	}

	public Boolean getOptional() {
		return optional;
	}

	public void setOptional(Boolean optional) {
		this.optional = optional;
	}

	public Boolean getOrphanRemoval() {
		return orphanRemoval;
	}

	public void setOrphanRemoval(Boolean orphanRemoval) {
		this.orphanRemoval = orphanRemoval;
	}

	public FMLinkedProperty getOppositeEnd() {
		return oppositeEnd;
	}

	public void setOppositeEnd(FMLinkedProperty oppositeEnd) {
		this.oppositeEnd = oppositeEnd;
	}

}
