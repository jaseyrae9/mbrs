package myplugin.generator.model;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import myplugin.generator.ImportUtil;
import myplugin.generator.PerClassGenerator;
import myplugin.generator.fmmodel.FMClass;
import myplugin.generator.fmmodel.FMLinkedProperty;
import myplugin.generator.fmmodel.FMPeristantProperty;
import myplugin.generator.fmmodel.FMProperty;
import myplugin.generator.options.GeneratorOptions;

public class ModelGenerator extends PerClassGenerator {

	public ModelGenerator(GeneratorOptions generatorOptions) {
		super(generatorOptions);
	}

	@Override
	public void prepareContext(FMClass fmClass, Map<String, Object> context) {
		super.prepareContext(fmClass, context);
		context.put("package", fmClass.getTypePackage());
		context.put("name", fmClass.getName());
		context.put("tableName", fmClass.getTableName());
		context.put("needsSet", fmClass.getProperties().stream().anyMatch(p -> p.isCreateGetter() && p.getUpper() == -1));
		context.put("needsSetterImport", fmClass.getProperties().stream().anyMatch(p -> p.isCreateSetter()));
		context.put("needsGetterImport", fmClass.getProperties().stream().anyMatch(p -> p.isCreateGetter()));
		context.put("imports", ImportUtil.uniqueTypesUsed(fmClass.getProperties(), true));
		//obicna polja
		context.put("properties", fmClass.getProperties().stream().filter(p -> !p.isPersistant()).collect(Collectors.toList()));
		//cuvaju se u bazi
		List<FMProperty> peristantProperties = fmClass.getProperties().stream()
				.filter(p -> p instanceof FMPeristantProperty).collect(Collectors.toList());
		context.put("peristantProperties", peristantProperties);
		//rerefence ka drugim (isti mikroservis)
		List<FMProperty> linked = fmClass.getProperties().stream().filter(p -> p instanceof FMLinkedProperty && !p.getFeign())
				.collect(Collectors.toList());
		context.put("linkedProperties", linked);
		//reference ka drugim mikroservisima
		List<FMProperty> feign = fmClass.getProperties().stream().filter(p -> p instanceof FMLinkedProperty && p.getFeign())
				.collect(Collectors.toList());
		context.put("feignProperties", feign);
	}

}
