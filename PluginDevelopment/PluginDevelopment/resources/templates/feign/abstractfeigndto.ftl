package generated.dto.feign;

import lombok.Getter;
import lombok.Setter;

<#if needsSet>
import java.util.HashSet;
import java.util.Set;
</#if>

<#-- Ove se koriste samo polja koja su java tipovi, pa zato nema obrade za ostale tipove -->
<#list imports as import> 
<#if import.umlType && import.typePackage != "java.lang">
import ${import.typePackage}.${import.name};;
</#if>
</#list>

@Getter
@Setter
public abstract class AbstractFeign${name}DTO {
	<#list properties as property> 
	<#if property.upper == 1 >   
	protected ${property.type.name} ${property.name};
	<#else> 
	protected Set<${property.type.name}> ${property.name} = new HashSet<${property.type.name}>();
	</#if>
	</#list>
}