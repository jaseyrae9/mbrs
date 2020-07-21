package myplugin.generator.fmmodel;

public class FMProperty extends FMElement {
	// Property type
	private FMType type;
	// Property visibility (public, private, protected, package)
	private String visibility;
	// Multiplicity (lower value)
	private Integer lower;
	// Multiplicity (upper value)
	private Integer upper;
	// Da li je potrebno u okviru klasu u modelu napraviti getter i setter
	private boolean createSetter;
	private boolean createGetter;
	// Da li se obelezje cuva u bazi podataka
	private boolean persistant;
	// Potrebno za generisanje importa i Feign klasa
	private boolean feign;

	public FMProperty(String magicDrawId, String name, FMType type, String visibility, int lower, int upper) {
		super(magicDrawId, name);
		this.type = type;
		this.visibility = visibility;

		this.lower = lower;
		this.upper = upper;
		
		this.createGetter = true;
		this.createSetter = true;
		
		//TODO: ovo postavljati na true preko setter kada obelezje ima odgovarajuci stereotip
		this.persistant = false;
		
		this.feign = false;
	}

	public FMType getType() {
		return type;
	}

	public void setType(FMType type) {
		this.type = type;
	}

	public String getVisibility() {
		return visibility;
	}

	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}

	public Integer getLower() {
		return lower;
	}

	public void setLower(Integer lower) {
		this.lower = lower;
	}

	public Integer getUpper() {
		return upper;
	}

	public void setUpper(Integer upper) {
		this.upper = upper;
	}

	public boolean isCreateSetter() {
		return createSetter;
	}

	public void setCreateSetter(boolean createSetter) {
		this.createSetter = createSetter;
	}

	public boolean isCreateGetter() {
		return createGetter;
	}

	public void setCreateGetter(boolean createGetter) {
		this.createGetter = createGetter;
	}

	public boolean isPersistant() {
		return persistant;
	}

	public void setPersistant(boolean isPersistant) {
		this.persistant = isPersistant;
	}

	public boolean isFeign() {
		return feign;
	}

	public void setFeign(boolean isFeign) {
		this.feign = isFeign;
	}
}