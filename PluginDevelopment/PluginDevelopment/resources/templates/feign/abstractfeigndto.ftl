package generated.dto.feign;

import lombok.Getter;
import lombok.Setter;

<#if needsSet>
import java.util.HashSet;
import java.util.Set;
</#if>
<#list properties as property> 
<#if property.type?? && property.type.typePackage != "java.lang">
import ${property.type.typePackage}.${property.type.name};
</#if>
</#list>

@Getter
@Setter
public abstract class AbstractFeign${name}DTO {
	<#list properties as property> 
	<#if property.upper == 1 >   
	protected ${property.type.name} ${property.name};
	<#elseif property.upper == -1 > 
	protected Set<${property.type.name}> ${property.name} = new HashSet<${property.type.name}>();
	<#else>   
	<#list 1..property.upper as i>
	protected ${property.type.name} ${property.name}${i};
	</#list>  
	</#if>
	</#list>
}