package myplugin.generator.additionalfiles;

import java.util.Map;

import myplugin.generator.PerMicroserviceGenerator;
import myplugin.generator.fmmodel.FMMicroservice;
import myplugin.generator.options.GeneratorOptions;

public class PomXmlGenerator extends PerMicroserviceGenerator{

	public PomXmlGenerator(GeneratorOptions generatorOptions) {
		super(generatorOptions);
	}

	@Override
	public void prepareContext(FMMicroservice microservice, Map<String, Object> context) {
		context.clear();
		context.put("groupId", microservice.getGroupId());
		context.put("artifactId", microservice.getArtifactId());
		context.put("version", microservice.getVersion());
	}
}
