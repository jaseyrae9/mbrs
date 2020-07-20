package myplugin.generator.additionalfiles;

import java.util.Map;

import myplugin.generator.PerMicroserviceGenerator;
import myplugin.generator.fmmodel.FMMicroservice;
import myplugin.generator.options.GeneratorOptions;

public class ApplicationYmlGenerator extends PerMicroserviceGenerator{

	public ApplicationYmlGenerator(GeneratorOptions generatorOptions) {
		super(generatorOptions);
	}

	@Override
	public void prepareContext(FMMicroservice microservice, Map<String, Object> context) {
		context.clear();
		context.put("name", microservice.getName());
		context.put("dbUrl", microservice.getDbUrl());
		context.put("dbUsername", microservice.getDbUsername());
		context.put("dbPassword", microservice.getDbPassword());
	}
}
