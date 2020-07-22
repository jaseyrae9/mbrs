package generated.dto${package};

import lombok.Getter;
import lombok.Setter;

<#if needsSet>
import java.util.HashSet;
import java.util.Set;
</#if>
<#list simpleProperties as property> 
<#if property.type?? && property.type.umlType && property.type.typePackage != "java.lang">
import ${property.type.typePackage}.${property.type.name};
</#if>
<#if property.type?? && property.type.enumType>
import generated.model${property.type.typePackage}.${property.type.name};
</#if>
</#list>
<#list classProperties as property> 
<#if property.type.typePackage != package>
import generated.dto${property.type.typePackage}.Abstract${property.type.name}DTO;
</#if>
</#list>
<#list feignClassProperties as property> 
import generated.dto.feign.AbstractFeign${property.type.name}DTO;
</#list>

@Getter
@Setter
public abstract class Abstract${name}DTO {
	<#list simpleProperties as property> 
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
	<#list classProperties as property> 
	<#if property.upper == 1 >   
	protected Abstract${property.type.name}DTO ${property.name};
	<#elseif property.upper == -1 > 
	protected Set<Abstract${property.type.name}DTO> ${property.name} = new HashSet<Abstract${property.type.name}DTO>();
	<#else>   
	<#list 1..property.upper as i>
	protected Abstract${property.type.name}DTO ${property.name}${i};
	</#list>  
	</#if>
	</#list>	
	<#list feignClassProperties as property> 
	<#if property.upper == 1 >   
	protected AbstractFeign${property.type.name}DTO ${property.name};
	<#elseif property.upper == -1 > 
	protected Set<AbstractFeign${property.type.name}DTO> ${property.name} = new HashSet<${property.type.name}>();
	<#else>   
	<#list 1..property.upper as i>
	protected AbstractFeign${property.type.name}DTO ${property.name}${i};
	</#list>  
	</#if>
	</#list>	
}