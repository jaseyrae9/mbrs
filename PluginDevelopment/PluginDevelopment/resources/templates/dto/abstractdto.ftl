package generated.dto${package};

import lombok.Getter;
import lombok.Setter;

<#if needsSet>
import java.util.HashSet;
import java.util.Set;
</#if>
<#list imports as import>
<#if import.umlType && import.typePackage != "java.lang"> <#-- Import za uml tipove poput datuma. Java.lang se ne importuje. -->
import ${import.typePackage}.${import.name};
<#elseif import.enumType> <#-- Import za enume iz modela (jer njih ne menjamo sa dto) -->
import generated.model${import.typePackage}.${import.name};
<#elseif import.classType && import.microservice.name == microservice && import.typePackage != package>  <#-- Import za dto -->
import generated.dto${import.typePackage}.Abstract${import.name}DTO;
<#elseif import.classType && import.microservice.name != microservice> <#-- Import za feign dto -->
import generated.dto.feign${import.typePackage}.AbstractFeign${import.name}DTO;
</#if>
</#list>

@Getter
@Setter
public abstract class Abstract${name}DTO {
	<#list simpleProperties as property> 
	<#if property.upper == 1 >   
	protected ${property.type.name} ${property.name};
	<#else> 
	protected Set<${property.type.name}> ${property.name} = new HashSet<${property.type.name}>();	  
	</#if>
	</#list>	
	<#list classProperties as property> 
	<#if property.upper == 1 >   
	protected Abstract${property.type.name}DTO ${property.name};
	<#else> 
	protected Set<Abstract${property.type.name}DTO> ${property.name} = new HashSet<Abstract${property.type.name}DTO>();	  
	</#if>
	</#list>	
	<#list feignClassProperties as property> 
	<#if property.upper == 1 >   
	protected AbstractFeign${property.type.name}DTO ${property.name};
	<#else> 
	protected Set<AbstractFeign${property.type.name}DTO> ${property.name} = new HashSet<${property.type.name}>();
	</#if>
	</#list>	
}