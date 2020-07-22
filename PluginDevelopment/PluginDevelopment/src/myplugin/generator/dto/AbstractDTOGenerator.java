package myplugin.generator.dto;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import myplugin.generator.ImportUtil;
import myplugin.generator.PerClassGenerator;
import myplugin.generator.fmmodel.FMClass;
import myplugin.generator.fmmodel.FMProperty;
import myplugin.generator.options.GeneratorOptions;

public class AbstractDTOGenerator extends PerClassGenerator {

	public AbstractDTOGenerator(GeneratorOptions generatorOptions) {
		super(generatorOptions);
	}

	@Override
	public void prepareContext(FMClass fmClass, Map<String, Object> context) {
		super.prepareContext(fmClass, context);
		context.put("microservice", fmClass.getMicroservice().getName());
		context.put("package", fmClass.getTypePackage());
		context.put("name", fmClass.getName());

		// sve sto ima getter se pojavljuje u DTO
		List<FMProperty> all = fmClass.getProperties().stream().filter(p -> p.isCreateGetter())
				.collect(Collectors.toList());
		context.put("imports", ImportUtil.uniqueTypesUsed(all, false));
		context.put("needsSet", all.stream().anyMatch(p -> p.getUpper() != 1));

		List<FMProperty> simpleProperties = all.stream().filter(p -> isSimple(p)).collect(Collectors.toList());
		context.put("simpleProperties", simpleProperties);

		List<FMProperty> classProperties = all.stream().filter(p -> isClass(p)).collect(Collectors.toList());
		context.put("classProperties", classProperties);

		List<FMProperty> feignClassProperties = all.stream().filter(p -> isFeignClass(p)).collect(Collectors.toList());
		context.put("feignClassProperties", feignClassProperties);

	}

	private boolean isClass(FMProperty p) {
		return p.getType().isClassType() && !p.getFeign();
	}

	private boolean isFeignClass(FMProperty p) {
		return p.getType().isClassType() && p.getFeign();
	}

	private boolean isSimple(FMProperty p) {
		return !p.getType().isClassType();
	}
}
