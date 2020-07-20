package myplugin.generator.controller;

import java.util.Map;

import myplugin.generator.PerClassGenerator;
import myplugin.generator.fmmodel.FMClass;
import myplugin.generator.options.GeneratorOptions;

public class ConcreteControllerGenerator extends PerClassGenerator {

	public ConcreteControllerGenerator(GeneratorOptions generatorOptions) {
		super(generatorOptions);
	}
	
	@Override
	public void prepareContext(FMClass fmClass, Map<String, Object> context) {
		super.prepareContext(fmClass, context);
		context.put("package", fmClass.getTypePackage());
		context.put("name", fmClass.getName());
		context.put("url", fmClass.getName().toLowerCase());
	}
	
}
