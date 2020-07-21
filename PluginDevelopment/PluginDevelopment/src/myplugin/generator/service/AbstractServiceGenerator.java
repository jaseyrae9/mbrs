package myplugin.generator.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import myplugin.generator.PerClassGenerator;
import myplugin.generator.fmmodel.FMClass;
import myplugin.generator.fmmodel.FMProperty;
import myplugin.generator.options.GeneratorOptions;

public class AbstractServiceGenerator extends PerClassGenerator{

	public AbstractServiceGenerator(GeneratorOptions generatorOptions) {
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
		context.put("key", fmClass.getKeyType().getName());
		//filtriraj polja koja treba setovati
		List<FMProperty> properties = fmClass.getProperties().stream()
				.filter(p -> setProperty(p))
				.collect(Collectors.toList());
		context.put("properties", properties);
	}
	
	private boolean setProperty(FMProperty p) {
		//setuju se polja koja se cuvaju u bazi, koja imaju seter i koja nisu liste
		return p.isPersistant() && p.isCreateSetter() && p.getUpper() == 1;
	}
}