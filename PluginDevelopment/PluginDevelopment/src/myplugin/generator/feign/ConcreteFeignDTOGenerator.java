package myplugin.generator.feign;

import java.util.Map;

import myplugin.generator.PerFeignClassGenerator;
import myplugin.generator.fmmodel.FMClass;
import myplugin.generator.options.GeneratorOptions;

public class ConcreteFeignDTOGenerator extends PerFeignClassGenerator{

	public ConcreteFeignDTOGenerator(GeneratorOptions generatorOptions) {
		super(generatorOptions);
	}

	@Override
	public void prepareContext(FMClass fmClass, Map<String, Object> context) {
		super.prepareContext(fmClass, context);
		context.put("name", fmClass.getName());
	}
}
