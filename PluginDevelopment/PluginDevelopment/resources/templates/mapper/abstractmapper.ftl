package generated.mapper${package};

import generated.dto${package}.Abstract${name}DTO;
import generated.model${package}.${name};
import handwritten.dto${package}.${name}DTO;
<#if needsCollector>
import java.util.stream.Collectors;
</#if>

public abstract class Abstract${name}Mapper implements Mapper<${name}, Abstract${name}DTO>{
	protected AbstractMapperCatalogue mapperCatalogue;
	
	public Abstract${name}Mapper(AbstractMapperCatalogue mapperCatalogue) {
		this.mapperCatalogue = mapperCatalogue;
	}
		
	public Abstract${name}DTO fullConversion(${name} entity) {
		Abstract${name}DTO ret = simpleConversion(entity);
		<#list fullProperties as property>
		<#if property.feign>
		<#if property.upper = 1>
		ret.set${property.name?cap_first}(mapperCatalogue.get${property.type.name?cap_first}FeignClient().getOne(entity.get${property.type.name?cap_first}()));
		<#else>
		ret.set${property.name?cap_first}(entity.get${property.name?cap_first}().stream()
			.map(el -> mapperCatalogue.get${property.type.name?cap_first}FeignClient().getOne(entity.get${property.type.name?cap_first}()))
			.collect(Collectors.toSet()));
		</#if>
		<#else>
		<#if property.upper = 1>
		ret.set${property.name?cap_first}(mapperCatalogue.get${property.type.name?cap_first}Mapper().simpleConversion(entity.get${property.type.name?cap_first}()));
		<#else>
		ret.set${property.name?cap_first}(entity.get${property.name?cap_first}().stream()
			.map(el -> mapperCatalogue.get${property.type.name?cap_first}Mapper().simpleConversion(el))
			.collect(Collectors.toSet()));
		</#if>		
		</#if>
		</#list>
		return ret;
	}
	
	public Abstract${name}DTO simpleConversion(${name} entity) {
		Abstract${name}DTO ret = new ${name}DTO();
		<#list simpleProperties as property>
		ret.set${property.name?cap_first}(entity.get${property.name?cap_first}());
		</#list>
		return ret;
	}
}
