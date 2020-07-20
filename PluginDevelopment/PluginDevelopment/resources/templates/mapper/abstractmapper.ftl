package generated.mapper${package};

import generated.dto${package}.Abstract${name}DTO;
import generated.model${package}.${name};
import handwritten.dto${package}.${name}DTO;

public abstract class Abstract${name}Mapper implements Mapper<${name}, Abstract${name}DTO>{
	protected AbstractMapperCatalogue mapperCatalogue;
	
	public Abstract${name}Mapper(AbstractMapperCatalogue mapperCatalogue) {
		this.mapperCatalogue = mapperCatalogue;
	}
		
	public Abstract${name}DTO fullConversion(${name} entity) {
		Abstract${name}DTO ret = simpleConversion(entity);
		//TODO: setovati reference pozivom njhovih simpleConversiona
		return ret;
	}
	
	public Abstract${name}DTO simpleConversion(${name} entity) {
		Abstract${name}DTO ret = new ${name}DTO();
		//TODO: setovati prosta polja
		return ret;
	}
}
