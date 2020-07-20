package myplugin.generator;

import myplugin.generator.additionalfiles.ApplicationYmlGenerator;
import myplugin.generator.controller.AbstractControllerGenerator;
import myplugin.generator.controller.ConcreteControllerGenerator;
import myplugin.generator.model.EnumerationGenerator;
import myplugin.generator.options.GeneratorOptions;
import myplugin.generator.repository.AbstractRepositoryGenerator;
import myplugin.generator.repository.ConcreteRepositoryGenerator;
import myplugin.generator.service.AbstractServiceGenerator;
import myplugin.generator.service.ConcreteServiceGenerator;

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
		else if(name.equals(AbstractServiceGenerator.class.getSimpleName())) {
			return new AbstractServiceGenerator(options);
		}
		else if(name.equals(ConcreteServiceGenerator.class.getSimpleName())) {
			return new ConcreteServiceGenerator(options);
		}
		else if(name.equals(AbstractControllerGenerator.class.getSimpleName())) {
			return new AbstractControllerGenerator(options);
		}
		else if(name.equals(ConcreteControllerGenerator.class.getSimpleName())) {
			return new ConcreteControllerGenerator(options);
		}
		else {
			return null;
		}
	}
}
