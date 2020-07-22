package generated.model${package};

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

<#if needsGetterImport>
import lombok.Getter;
</#if>
<#if needsSetterImport>
import lombok.Setter;
</#if>

<#if needsSet>
import java.util.HashSet;
import java.util.Set;
</#if>

<#list imports as import>
<#if import.umlType && import.typePackage != "java.lang"> <#-- Import za uml tipove poput datuma. Java.lang se ne importuje. -->
import ${import.typePackage}.${import.name};
<#elseif !import.umlType && import.typePackage != package>
import generated.model${import.typePackage}.${import.name}; <#-- Import za tipove koji nisu u istom paketu. -->
</#if>
</#list>

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity<#if tableName??>
@Table(name = "${tableName?lower_case}")</#if>
public class ${name} {

	<#list properties as property> <#-- Oni koji ne idu u bazu -->
	<#if property.createGetter>
	@Getter
	</#if> 
	<#if property.createSetter>
	@Setter
	</#if>
	<#if property.upper == 1 >   
	${property.visibility} ${property.type.name} ${property.name};
	<#else> 
	${property.visibility} Set<${property.type.name}> ${property.name} = new HashSet<${property.type.name}>();	
	</#if>
	</#list>
		
	<#list peristantProperties as peristantProperty> <#-- Obicni koji idu u bazu -->
	<#if peristantProperty.createGetter>
	@Getter
	</#if> 
	<#if peristantProperty.createSetter>
	@Setter
	</#if>
	<#if peristantProperty.key >
	@EqualsAndHashCode.Include
	@Id	
	<#if peristantProperty.generator == "AUTO" || peristantProperty.generator == "IDENTITY" >
	@GeneratedValue(strategy = GenerationType.${peristantProperty.generator})
	<#elseif peristantProperty.generator == "SEQUENCE">
	@GeneratedValue(strategy = GenerationType.${peristantProperty.generator}, generator = "${name?lower_case}_generator")
	@SequenceGenerator(name = "${name?lower_case}_generator", sequenceName = "${name?lower_case}_seq")		
	</#if>	 
	</#if>
	<#if peristantProperty.upper == 1 >
	<#if !peristantProperty.key>
	@Column(name = "${peristantProperty.name?lower_case}", <#if (peristantProperty.length)??>length = ${peristantProperty.length}, </#if><#if (peristantProperty.scale)??> scale = ${peristantProperty.scale}, </#if><#if (peristantProperty.precision)??> precision = ${peristantProperty.precision}, </#if>nullable = <#if peristantProperty.lower == 0>false<#else>true</#if>, unique = <#if peristantProperty.unique>true<#else>false</#if>)   
	</#if>
	${peristantProperty.visibility} ${peristantProperty.type.name} ${peristantProperty.name};	
	</#if>	
	</#list>	
	
	<#list linkedProperties as linkedProperty> <#-- Reference -->
	<#if linkedProperty.createGetter>
	@Getter
	</#if> 
	<#if linkedProperty.createSetter>
	@Setter
	</#if>
	${linkedProperty.visibility} ${linkedProperty.type.name} ${linkedProperty.name};
	</#list>

}