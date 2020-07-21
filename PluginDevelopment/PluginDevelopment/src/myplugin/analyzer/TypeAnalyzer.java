package myplugin.analyzer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import myplugin.generator.fmmodel.FMClass;
import myplugin.generator.fmmodel.FMMicroservice;
import myplugin.generator.fmmodel.FMModel;
import myplugin.generator.fmmodel.FMProperty;
import myplugin.generator.fmmodel.FMType;

/*
 * Kada se tek naprave Property svuda se pravi novi FMType.
 * Ovo nam smeta za klase i enume (jer nekad treba include), a i zbog feign klijenata.
 * Moramo proci kroz sve veze i proveriti jesu li zapravo veze ka drugoj klasi/enumu i zameniti ih.
 * Ovo obavezno zvati na kraju, jer ne znamo koja klasa će se kada registrovati.
 */
public class TypeAnalyzer {
	//kolekcija svih tipova koje imamo u svim mikroservisima
	private Map<String, FMType> types = new HashMap<String, FMType>();
	
	public TypeAnalyzer() {
		//napuni kolekciju
		for(FMMicroservice microservice: FMModel.getInstance().getMicroservices()) {
			microservice.getClasses().forEach(cl -> types.put(cl.getMagicDrawId(), cl));
			microservice.getEnumerations().forEach(cl -> types.put(cl.getMagicDrawId(), cl));
		}
	}
	
	public void analyze() throws AnalyzeException {
		for(FMMicroservice microservice:FMModel.getInstance().getMicroservices()) {
			analyzeMicroservice(microservice);
		}
	}
	
	private void analyzeMicroservice(FMMicroservice microservice) throws AnalyzeException {
		for(FMClass fmClass:microservice.getClasses()) {
			analyzeClass(fmClass);
		}
	}
	
	private void analyzeClass(FMClass fmClass) throws AnalyzeException {
		List<FMProperty> properties = fmClass.getProperties().stream().filter(p -> !p.getType().isUmlType()).collect(Collectors.toList());
		for(FMProperty property:properties) {
			FMType type = findLocalType(property.getType());
			property.setType(type);
			if(!fmClass.getMicroservice().equals(type.getMicroservice())) {
				property.setFeign(true);
				if(property.getType() instanceof FMClass) {
					//ako je class pravicemo feign klijente
					FMClass feignClass = (FMClass) property.getType();
					fmClass.getMicroservice().getFeignClasses().add(feignClass);
				}
			}
		}
	}
	
	private FMType findLocalType(FMType type) throws AnalyzeException {
		if(type.isUmlType()) {
			return null;
		}
		//tip nije uml tip nego naš
		FMType localType = types.get(type.getMagicDrawId());
		if(localType == null) {
			throw new AnalyzeException(type.getName() + " not found.");
		}
		return localType;
	}
}
