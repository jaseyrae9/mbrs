package myplugin.generator.additionalfiles;

import java.util.Map;

import myplugin.generator.PerMicroserviceGenerator;
import myplugin.generator.fmmodel.FMMicroservice;
import myplugin.generator.options.GeneratorOptions;

public class PostmanGenerator extends PerMicroserviceGenerator{

	public PostmanGenerator(GeneratorOptions generatorOptions) {
		super(generatorOptions);
	}

	@Override
	public void prepareContext(FMMicroservice microservice, Map<String, Object> context) {
		context.clear();
		context.put("id", microservice.getMagicDrawId());
		context.put("name", microservice.getName());
		context.put("port", microservice.getPort());
		context.put("classes", microservice.getPersistantClasses());
	}
}
