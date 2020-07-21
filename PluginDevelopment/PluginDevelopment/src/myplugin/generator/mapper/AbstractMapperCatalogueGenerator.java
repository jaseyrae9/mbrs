package myplugin.generator.mapper;

import java.util.Map;

import myplugin.generator.PerMicroserviceGenerator;
import myplugin.generator.fmmodel.FMMicroservice;
import myplugin.generator.options.GeneratorOptions;

public class AbstractMapperCatalogueGenerator extends PerMicroserviceGenerator{

	public AbstractMapperCatalogueGenerator(GeneratorOptions generatorOptions) {
		super(generatorOptions);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void prepareContext(FMMicroservice microservice, Map<String, Object> context) {
		context.clear();
		context.put("classes", microservice.getPersistantClasses());
		context.put("feignClasses", microservice.getFeignClasses());
	}
}
