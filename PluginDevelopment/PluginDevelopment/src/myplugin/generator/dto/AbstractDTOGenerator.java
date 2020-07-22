package myplugin.generator.dto;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import myplugin.generator.PerClassGenerator;
import myplugin.generator.fmmodel.FMClass;
import myplugin.generator.fmmodel.FMProperty;
import myplugin.generator.options.GeneratorOptions;

public class AbstractDTOGenerator extends PerClassGenerator{

	public AbstractDTOGenerator(GeneratorOptions generatorOptions) {
		super(generatorOptions);
	}

	@Override
	public void prepareContext(FMClass fmClass, Map<String, Object> context) {
		super.prepareContext(fmClass, context);
		context.put("package", fmClass.getTypePackage());
		context.put("name", fmClass.getName());
		context.put("needsSet", fmClass.getProperties().stream().anyMatch(p -> p.isCreateGetter() && p.getUpper() == -1));
		
		List<FMProperty> simpleProperties = fmClass.getProperties().stream()
				.filter(p -> isSimple(p)).collect(Collectors.toList());
		context.put("simpleProperties", simpleProperties);
		
		List<FMProperty> classProperties = fmClass.getProperties().stream()
				.filter(p -> isClass(p)).collect(Collectors.toList());
		context.put("classProperties", classProperties);
		
		List<FMProperty> feignClassProperties = fmClass.getProperties().stream()
				.filter(p -> isFeignClass(p)).collect(Collectors.toList());
		context.put("feignClassProperties", feignClassProperties);
	}
	
	private boolean isClass(FMProperty p) {
		return p.isCreateGetter() && p.getType().isClassType() && !p.getFeign();
	}
	
	private boolean isFeignClass(FMProperty p) {
		return p.isCreateGetter() && p.getType().isClassType() && p.getFeign();
	}
	
	private boolean isSimple(FMProperty p) {
		return p.isCreateGetter() && !p.getType().isClassType();
	}
}
