package generated.dto.feign;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractFeign${name}DTO {
	<#list properties as property> 
	<#if property.upper == 1 >   
	protected ${property.type.name} ${property.name};
	<#elseif property.upper == -1 > 
	protected Set<${property.type.name}> ${property.name} = new HashSet<${property.type}>();
	<#else>   
	<#list 1..property.upper as i>
	protected ${property.type.name} ${property.name}${i};
	</#list>  
	</#if>
	</#list>
}