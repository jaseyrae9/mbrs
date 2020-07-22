package myplugin.generator;

import myplugin.generator.additionalfiles.ApplicationYmlGenerator;
import myplugin.generator.additionalfiles.PomXmlGenerator;
import myplugin.generator.additionalfiles.PostmanGenerator;
import myplugin.generator.controller.AbstractControllerGenerator;
import myplugin.generator.controller.ConcreteControllerGenerator;
import myplugin.generator.dto.AbstractDTOGenerator;
import myplugin.generator.dto.ConcreteDTOGenerator;
import myplugin.generator.feign.AbstractFeignClientGenerator;
import myplugin.generator.feign.AbstractFeignDTOGenerator;
import myplugin.generator.feign.ConcreteFeignClientGenerator;
import myplugin.generator.feign.ConcreteFeignDTOGenerator;
import myplugin.generator.mapper.AbstractMapperCatalogueGenerator;
import myplugin.generator.mapper.AbstractMapperGenerator;
import myplugin.generator.mapper.ConcreteMapperGenerator;
import myplugin.generator.model.EnumerationGenerator;
import myplugin.generator.options.GeneratorOptions;
import myplugin.generator.repository.AbstractRepositoryGenerator;
import myplugin.generator.repository.ConcreteRepositoryGenerator;
import myplugin.generator.service.AbstractServiceGenerator;
import myplugin.generator.service.ConcreteServiceGenerator;

public class GeneratorFactory {
	
	public static StaticFilesGenerator getStaticFilesGenerator() {
		return new StaticFilesGenerator();
	}

	public static BasicGenerator getGenerator(String name, GeneratorOptions options) {
		if(name.equals(ApplicationYmlGenerator.class.getSimpleName())) {
			return new ApplicationYmlGenerator(options);
		}
		else if(name.equals(PomXmlGenerator.class.getSimpleName())) {
			return new PomXmlGenerator(options);
		}
		else if(name.equals(PostmanGenerator.class.getSimpleName())) {
			return new PostmanGenerator(options);
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
		else if(name.equals(AbstractMapperGenerator.class.getSimpleName())) {
			return new AbstractMapperGenerator(options);
		}
		else if(name.equals(ConcreteMapperGenerator.class.getSimpleName())) {
			return new ConcreteMapperGenerator(options);
		}
		else if(name.equals(AbstractMapperCatalogueGenerator.class.getSimpleName())) {
			return new AbstractMapperCatalogueGenerator(options);
		}
		else if(name.equals(AbstractFeignClientGenerator.class.getSimpleName())) {
			return new AbstractFeignClientGenerator(options);
		}
		else if(name.equals(ConcreteFeignClientGenerator.class.getSimpleName())) {
			return new ConcreteFeignClientGenerator(options);
		}
		else if(name.equals(AbstractFeignDTOGenerator.class.getSimpleName())) {
			return new AbstractFeignDTOGenerator(options);
		}
		else if(name.equals(ConcreteFeignDTOGenerator.class.getSimpleName())) {
			return new ConcreteFeignDTOGenerator(options);
		}
		else if(name.equals(AbstractDTOGenerator.class.getSimpleName())) {
			return new AbstractDTOGenerator(options);
		}
		else if(name.equals(ConcreteDTOGenerator.class.getSimpleName())) {
			return new ConcreteDTOGenerator(options);
		}
		else {
			return null;
		}
	}
}
