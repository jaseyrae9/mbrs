package myplugin.generator.mapper;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import myplugin.generator.PerClassGenerator;
import myplugin.generator.fmmodel.FMClass;
import myplugin.generator.fmmodel.FMProperty;
import myplugin.generator.options.GeneratorOptions;

public class AbstractMapperGenerator extends PerClassGenerator{

	public AbstractMapperGenerator(GeneratorOptions generatorOptions) {
		super(generatorOptions);
	}

	@Override
	public void prepareContext(FMClass fmClass, Map<String, Object> context) {
		super.prepareContext(fmClass, context);
		context.put("package", fmClass.getTypePackage());
		context.put("name", fmClass.getName());
		//polja za simpleConversion
		List<FMProperty> simpleProperties = fmClass.getProperties().stream()
				.filter(p -> isSimpleProperty(p))
				.collect(Collectors.toList());
		context.put("simpleProperties", simpleProperties);
		//polja za fullConversion
		List<FMProperty> fullProperties = fmClass.getProperties().stream()
				.filter(p -> isFullProperty(p))
				.collect(Collectors.toList());
		context.put("fullProperties", fullProperties);
		context.put("needsCollector", fullProperties.stream().anyMatch(p -> p.getUpper() == -1));
	}
	
	private boolean isSimpleProperty(FMProperty p) {
		//polja koja nisu nase klase (dakle enumi i uml su ok) i koja nisu liste
		return p.isCreateGetter() && p.getUpper() == 1 && !p.getType().isClassType();
	}
	
	private boolean isFullProperty(FMProperty p) {
		//sva polja koja imaju getter, a nisu simple
		return p.isCreateGetter() && !(isSimpleProperty(p));
	}
}
