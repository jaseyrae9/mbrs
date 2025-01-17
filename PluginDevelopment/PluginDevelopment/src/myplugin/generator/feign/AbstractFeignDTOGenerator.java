package myplugin.generator.feign;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import myplugin.generator.ImportUtil;
import myplugin.generator.PerFeignClassGenerator;
import myplugin.generator.fmmodel.FMClass;
import myplugin.generator.fmmodel.FMProperty;
import myplugin.generator.options.GeneratorOptions;

public class AbstractFeignDTOGenerator extends PerFeignClassGenerator{

	public AbstractFeignDTOGenerator(GeneratorOptions generatorOptions) {
		super(generatorOptions);
	}

	@Override
	public void prepareContext(FMClass fmClass, Map<String, Object> context) {
		super.prepareContext(fmClass, context);
		context.put("name", fmClass.getName());
		//obelezja koja treba dodati
		List<FMProperty> properties = fmClass.getProperties().stream()
				.filter(p -> isUmlProperty(p))
				.collect(Collectors.toList());
		context.put("properties", properties);
		//da li importovati setove
		context.put("needsSet", properties.stream().anyMatch(p -> p.getUpper() != 1));
		//koje tipove treba importovati
		context.put("imports", ImportUtil.uniqueTypesUsed(properties, false));
	}
	
	private boolean isUmlProperty(FMProperty property) {
		//generisu se samo osobine koje imaju getter (dakle poslace ih kontroler) i koje su uml (da se ne bi pravilo jos objekata)
		return property.isCreateGetter() && property.getType().isUmlType();
	}
}
