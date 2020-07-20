package myplugin.generator;

import myplugin.generator.additionalfiles.ApplicationYmlGenerator;
import myplugin.generator.model.EnumerationGenerator;
import myplugin.generator.options.GeneratorOptions;
import myplugin.generator.repository.AbstractRepositoryGenerator;
import myplugin.generator.repository.ConcreteRepositoryGenerator;

public class GeneratorFactory {

	public static BasicGenerator getGenerator(String name, GeneratorOptions options) {
		if(name.equals(ApplicationYmlGenerator.class.getSimpleName())) {
			return new ApplicationYmlGenerator(options);
		}
		else if(name.equals(EnumerationGenerator.class.getSimpleName())) {
			return new EnumerationGenerator(options);
		}
		else if(name.equals(AbstractRepositoryGenerator.class.getSimpleName())) {
			return new AbstractRepositoryGenerator(options);
		}
		else if(name.equals(ConcreteRepositoryGenerator.class.getSimpleName())) {
			return new ConcreteRepositoryGenerator(options);
		}
		else {
			return null;
		}
	}
}
