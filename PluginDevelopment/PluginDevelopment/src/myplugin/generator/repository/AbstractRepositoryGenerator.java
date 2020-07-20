package myplugin.generator.repository;

import java.util.Map;

import myplugin.generator.fmmodel.FMClass;
import myplugin.generator.options.GeneratorOptions;

public class AbstractRepositoryGenerator extends RepositoryGenerator {

	public AbstractRepositoryGenerator(GeneratorOptions generatorOptions) {
		super(generatorOptions);
	}
	
	@Override
	public void prepareContext(FMClass fmClass, Map<String, Object> context) {
		super.prepareContext(fmClass, context);
		context.put("package", fmClass.getTypePackage());
		context.put("name", fmClass.getName());
		//TODO: zameniti tako da se postavi pravi kljuc klase
		context.put("key", "Integer");
	}
}
