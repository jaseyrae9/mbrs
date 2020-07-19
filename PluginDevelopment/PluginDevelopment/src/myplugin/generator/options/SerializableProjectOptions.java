package myplugin.generator.options;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Ova klasa moze da se sacuva kao xml i ucita.
 * Nije singlton, ima prazan konstruktor i get i set za sva polja.
 */
public class SerializableProjectOptions implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private List<TypeMapping> typeMappings = new ArrayList<TypeMapping>();
	private Map<String, GeneratorOptions> generatorOptions = new HashMap<String, GeneratorOptions>();
	
	public SerializableProjectOptions(ProjectOptions projectOptions) {
		typeMappings = projectOptions.getTypeMappings();
		generatorOptions = projectOptions.getGeneratorOptions();
	}
	
	public SerializableProjectOptions() {
		super();
	}

	public List<TypeMapping> getTypeMappings() {
		return typeMappings;
	}

	public void setTypeMappings(List<TypeMapping> typeMappings) {
		this.typeMappings = typeMappings;
	}

	public Map<String, GeneratorOptions> getGeneratorOptions() {
		return generatorOptions;
	}

	public void setGeneratorOptions(Map<String, GeneratorOptions> generatorOptions) {
		this.generatorOptions = generatorOptions;
	}
}
