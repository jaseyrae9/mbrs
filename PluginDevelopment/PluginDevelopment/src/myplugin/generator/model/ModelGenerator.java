package myplugin.generator.model;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import myplugin.generator.ImportUtil;
import myplugin.generator.PerClassGenerator;
import myplugin.generator.fmmodel.FMClass;
import myplugin.generator.fmmodel.FMGeneratorType;
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
		context.put("needsSet",
				fmClass.getProperties().stream().anyMatch(p -> p.isCreateGetter() && p.getUpper() == -1));
		context.put("needsSetterImport", fmClass.getProperties().stream().anyMatch(p -> p.isCreateSetter()));
		context.put("needsGetterImport", fmClass.getProperties().stream().anyMatch(p -> p.isCreateGetter()));
		context.put("imports", ImportUtil.uniqueTypesUsed(fmClass.getProperties(), true));

		List<FMProperty> peristantProperties = fmClass.getProperties().stream()
				.filter(p -> p instanceof FMPeristantProperty).collect(Collectors.toList());
		
		context.put("peristantProperties", peristantProperties);
		context.put("linkedProperties", fmClass.getProperties().stream().filter(p -> p instanceof FMLinkedProperty)
				.collect(Collectors.toList()));
		context.put("properties", fmClass.getProperties().stream().filter(p -> !p.isPersistant()).collect(Collectors.toList()));
	
		context.put("persistenceImports", persistenceImports(fmClass.getTableName(), peristantProperties));
	}

	public static Set<String> persistenceImports(String tableName, List<FMProperty> properties){
		Set<String> imports = new HashSet<String>();
		imports.add("import javax.persistence.Entity;");

		if(tableName != null) {
			imports.add("import javax.persistence.Table;");
		}
		
		List<FMPeristantProperty> persistantProperties = properties.stream()
				.filter(p-> p instanceof FMPeristantProperty)
				.map(p -> (FMPeristantProperty)p).collect(Collectors.toList());
		
		if(persistantProperties.stream().anyMatch(p -> p.getKey())) {
			imports.add("import javax.persistence.Id;");
		}
		if(persistantProperties.stream().anyMatch(p -> p.getGenerator() != FMGeneratorType.NONE )) {
			imports.add("import javax.persistence.GeneratedValue;");
		}
		if(persistantProperties.stream().anyMatch(p -> p.getGenerator() == FMGeneratorType.SEQUENCE )) {
			imports.add("import javax.persistence.SequenceGenerator;");
		}
		if(persistantProperties.stream().anyMatch(p -> p.getUpper() == 1)) {
			imports.add("import javax.persistence.Column;");
		}
				
		return imports;
	}
}