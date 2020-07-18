package myplugin.generator.fmmodel;

public abstract class FMElement {
	protected String name;

	public FMElement(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
