package myplugin.generator.controller;

import java.util.Map;

import myplugin.generator.PerClassGenerator;
import myplugin.generator.fmmodel.FMClass;
import myplugin.generator.options.GeneratorOptions;

public class AbstractControllerGenerator extends PerClassGenerator{

	public AbstractControllerGenerator(GeneratorOptions generatorOptions) {
		super(generatorOptions);
	}

	@Override
	public void prepareContext(FMClass fmClass, Map<String, Object> context) {
		super.prepareContext(fmClass, context);
		context.put("package", fmClass.getTypePackage());
		context.put("name", fmClass.getName());
		context.put("generateReadAll", fmClass.isGenerateReadAll());
		context.put("generateReadOne", fmClass.isGenerateReadOne());
		context.put("generateCreate", fmClass.isGenerateCreate());
		context.put("generateUpdate", fmClass.isGenerateUpdate());
		context.put("generateDelete", fmClass.isGenerateDelete());
		//TODO: zameniti tako da se postavi pravi kljuc klase
		context.put("key", "Integer");
	}
}
