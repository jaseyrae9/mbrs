package generated.mapper;

<#list classes as el>
import handwritten.mapper${el.typePackage}.${el.name}Mapper;
</#list>

<#if feignClasses?size != 0>
import javax.inject.Inject;
</#if>
<#list feignClasses as el>
import handwritten.feignclient.${el.name}FeignClient;
</#list>

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractMapperCatalogue {
	<#list classes as el>
	protected ${el.name}Mapper ${el.name?uncap_first}Mapper = new ${el.name}Mapper(this);
	</#list>
	<#list feignClasses as el>
	@Inject
	protected ${el.name}FeignClient ${el.name?uncap_first}FeignClient;
	</#list>
}
