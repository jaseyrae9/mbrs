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
	
		context.put("persistenceImports", persistenceImports(fmClass.getTableName(), peristantProperties, linked));
	}

	public static Set<String> persistenceImports(String tableName, List<FMProperty> pp, List<FMProperty> lp){
		Set<String> imports = new HashSet<String>();
		imports.add("import javax.persistence.Entity;");

		if(tableName != null) {
			imports.add("import javax.persistence.Table;");
		}
		
		List<FMPeristantProperty> persistantProperties = pp.stream()
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
		
		List<FMLinkedProperty> linkedProperties = lp.stream()
				.filter(p-> p instanceof FMLinkedProperty)
				.map(p -> (FMLinkedProperty)p).collect(Collectors.toList());
		
		if(linkedProperties.stream().anyMatch(p ->  p.getUpper() == -1 && p.getOppositeEnd().getUpper()== -1 )) { //manytomany
			imports.add("import javax.persistence.ManyToMany;");
		}
		if(linkedProperties.stream().anyMatch(p ->  p.getUpper() == -1 && p.getOppositeEnd().getUpper()== 1 )) { //OneToMany
			imports.add("import javax.persistence.OneToMany;");
		}
		if(linkedProperties.stream().anyMatch(p ->  p.getUpper() == 1 && p.getOppositeEnd().getUpper()== -1 )) { //ManyToOne
			imports.add("import javax.persistence.ManyToOne;");
		}
		if(linkedProperties.stream().anyMatch(p ->  p.getUpper() == 1 && p.getOppositeEnd().getUpper()== 1 )) { //OneToOne
			imports.add("import javax.persistence.OneToOne;");
		}
		if(linkedProperties.stream().anyMatch(p -> p.getFetch() != null)) { 
			imports.add("import javax.persistence.FetchType;");
		}
		if(linkedProperties.stream().anyMatch(p -> p.getCascade() != null)) { 
			imports.add("import javax.persistence.CascadeType;");
		}

		return imports;
	}
}
