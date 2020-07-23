package generated.model${package};

import javax.persistence.*; 

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
<#if needsGetterImport>import lombok.Getter;</#if>
<#if needsSetterImport>import lombok.Setter;</#if>

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
	<#list properties as property><#-- Oni koji ne idu u bazu -->
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
	</#if>${'\n'}
	</#list>	
	<#list peristantProperties as peristantProperty><#-- Obicni koji idu u bazu -->
	<#if peristantProperty.createGetter>
	@Getter
	</#if> 
	<#if peristantProperty.createSetter>
	@Setter
	</#if>
	<#if peristantProperty.upper == 1>
	<#if peristantProperty.key >
	@EqualsAndHashCode.Include
	@Id	
	<#else>
	@Column(name = "${peristantProperty.name?lower_case}", <#if (peristantProperty.length)??>length = ${peristantProperty.length}, </#if><#if (peristantProperty.scale)??> scale = ${peristantProperty.scale}, </#if><#if (peristantProperty.precision)??> precision = ${peristantProperty.precision}, </#if>nullable = <#if peristantProperty.lower == 0>false<#else>true</#if>, unique = <#if peristantProperty.unique>true<#else>false</#if>)
	</#if>
	<#if peristantProperty.generator == "AUTO" || peristantProperty.generator == "IDENTITY" >
	@GeneratedValue(strategy = GenerationType.${peristantProperty.generator})
	<#elseif peristantProperty.generator == "SEQUENCE">
	@GeneratedValue(strategy = GenerationType.${peristantProperty.generator}, generator = "${name?lower_case}_generator")
	@SequenceGenerator(name = "${name?lower_case}_generator", sequenceName = "${name?lower_case}_seq")		
	</#if>
	${peristantProperty.visibility} ${peristantProperty.type.name} ${peristantProperty.name};
	<#else>
	@ElementCollection
	${property.visibility} Set<${property.type.name}> ${property.name} = new HashSet<${property.type.name}>();
	</#if>${'\n'}
	</#list>	
	<#list linkedProperties as property><#-- Reference -->
	<#if property.createGetter>
	@Getter
	</#if> 
	<#if property.createSetter>
	@Setter
	</#if>
	<#if property.upper == -1 && property.oppositeEnd.upper == -1>@ManyToMany<#elseif property.upper == -1 && property.oppositeEnd.upper == 1>@OneToMany<#elseif property.upper == 1 && property.oppositeEnd.upper == -1>@ManyToOne<#else>@OneToOne</#if><#rt>
	<#lt><#if (property.fetch)?? || (property.cascade)?? || (property.mappedBy)?? || (property.optional)?? || (property.orphanRemoval)??>(<#rt>
		<#if (property.cascade)??>
			<#lt>cascade = CascadeType.${property.cascade}<#rt>
		</#if>
		<#if (property.fetch)??>
			<#lt><#if (property.cascade)??>, </#if>fetch = FetchType.${property.fetch}<#rt>
		</#if>
		<#if (property.mappedBy)??>
			<#lt><#if (property.cascade)?? || (property.fetch)??>, </#if>mappedBy = "${property.mappedBy}"<#rt>
		</#if>
		<#if (property.optional)??>
			<#lt><#if (property.cascade)?? || (property.fetch)?? || (property.mappedBy)??>, </#if>optional = ${property.optional?c}<#rt>
		</#if>
		<#if (property.orphanRemoval)??>
			<#lt><#if (property.cascade)?? || (property.fetch)?? || (property.mappedBy)?? || (property.optional)??>, </#if>orphanRemoval = ${property.orphanRemoval?c}<#rt>
		</#if>
		<#lt>)</#if>	
	<#if property.upper == 1>
	${property.visibility} ${property.type.name} ${property.name};
	<#else>
	${property.visibility} Set<${property.type.name}> ${property.name} = new HashSet<${property.type.name}>();	
	</#if>${'\n'}
	</#list>	
	<#list feignProperties as property><#-- Feign obelezja -->
	<#if property.createGetter>
	@Getter
	</#if>
	<#if property.createSetter>
	@Setter
	</#if>
	<#if property.upper == 1>
	@Column<#if !property.optional>(nullable = ${property.optional?c})</#if>
	${property.visibility} ${property.type.keyType.name} ${property.name};
	<#else>
	@ElementCollection
	${property.visibility} Set<${property.type.keyType.name}> ${property.name} = new HashSet<${property.type.keyType.name}>();
	</#if>${'\n'}
	</#list>
}