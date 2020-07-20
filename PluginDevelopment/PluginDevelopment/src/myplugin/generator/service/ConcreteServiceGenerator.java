package myplugin.generator.service;

import java.util.Map;

import myplugin.generator.PerClassGenerator;
import myplugin.generator.fmmodel.FMClass;
import myplugin.generator.options.GeneratorOptions;

public class ConcreteServiceGenerator extends PerClassGenerator{

	public ConcreteServiceGenerator(GeneratorOptions generatorOptions) {
		super(generatorOptions);
	}

	@Override
	public void prepareContext(FMClass fmClass, Map<String, Object> context) {
		super.prepareContext(fmClass, context);
		context.put("package", fmClass.getTypePackage());
		context.put("name", fmClass.getName());
	}
}
