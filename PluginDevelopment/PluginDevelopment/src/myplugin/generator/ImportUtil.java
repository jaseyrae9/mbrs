package myplugin.generator;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import myplugin.generator.fmmodel.FMClass;
import myplugin.generator.fmmodel.FMProperty;
import myplugin.generator.fmmodel.FMType;

/*
 * Za prosleđenu listu obeležja generiše jedinstvene tipove koje treba importovati.
 */
public class ImportUtil {
	public static Collection<FMType> uniqueTypesUsed(List<FMProperty> properties, boolean useKeyIfFeign){
		Map<String, FMType> uniqueTypes = new HashMap<String, FMType>();
		for(FMProperty property: properties) {
			FMType type = property.getType();
			//zameni sa tipom kljuca ako se koriste feign
			if(useKeyIfFeign && property.getFeign() && property.getType().isClassType()) {
				FMClass feignClass = (FMClass) property.getType();
				type = feignClass.getKeyType();
			}
			uniqueTypes.put(type.getMagicDrawId(), type);
		}
		return uniqueTypes.values();
	}
}
