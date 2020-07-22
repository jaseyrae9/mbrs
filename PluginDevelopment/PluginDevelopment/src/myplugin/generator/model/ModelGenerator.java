package myplugin.generator.model;

import java.util.Map;
import java.util.stream.Collectors;

import myplugin.generator.PerClassGenerator;
import myplugin.generator.fmmodel.FMClass;
import myplugin.generator.fmmodel.FMLinkedProperty;
import myplugin.generator.fmmodel.FMPeristantProperty;
import myplugin.generator.options.GeneratorOptions;

public class ModelGenerator extends PerClassGenerator {

	public ModelGenerator(GeneratorOptions generatorOptions) {
		super(generatorOptions);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void prepareContext(FMClass fmClass, Map<String, Object> context) {
		super.prepareContext(fmClass, context);
		context.put("package", fmClass.getTypePackage());
		context.put("name", fmClass.getName());
		context.put("needsSet", fmClass.getProperties().stream().anyMatch(p -> p.isCreateGetter() && p.getUpper() == -1));
		context.put("needsSetterImport", fmClass.getProperties().stream().anyMatch(p -> p.isCreateSetter()));
		context.put("needsGetterImport", fmClass.getProperties().stream().anyMatch(p -> p.isCreateGetter()));

		context.put("peristantProperties", fmClass.getProperties().stream().filter(p -> p instanceof FMPeristantProperty).collect(Collectors.toList()));
		context.put("linkedProperties", fmClass.getProperties().stream().filter(p -> p instanceof FMLinkedProperty).collect(Collectors.toList()));
		context.put("properties", fmClass.getProperties().stream().filter(p -> p.isPersistant()).collect(Collectors.toList()));
	}

}
