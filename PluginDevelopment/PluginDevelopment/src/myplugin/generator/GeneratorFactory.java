package myplugin.generator;

import myplugin.generator.options.GeneratorOptions;

public class GeneratorFactory {

	public static BasicGenerator getGenerator(String name, GeneratorOptions options) {
		if(name.equals(ApplicationYmlGenerator.class.getName())) {
			return new ApplicationYmlGenerator(options);
		}
		else {
			return null;
		}
	}
}
