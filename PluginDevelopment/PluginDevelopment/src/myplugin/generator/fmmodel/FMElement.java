package myplugin.generator.fmmodel;

public abstract class FMElement {
	protected String magicDrawId;
	protected String name;

	public FMElement(String magicDrawId, String name) {
		super();
		this.name = name;
		this.magicDrawId = magicDrawId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMagicDrawId() {
		return magicDrawId;
	}

	public void setMagicDrawId(String magicDrawId) {
		this.magicDrawId = magicDrawId;
	}

}
