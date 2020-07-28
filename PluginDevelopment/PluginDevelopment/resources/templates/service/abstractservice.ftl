package generated.service${package};

import java.util.Optional;
import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;

import generated.model${package}.${name};
import handwritten.repository${package}.${name}Repository;

<#list feigns as feign>
import generated.dto.feign.AbstractFeign${feign.type.name}DTO;
import handwritten.feignclient.${feign.type.name}FeignClient;	
</#list>

public abstract class Abstract${name}Service{	
	@Inject
	protected ${name}Repository repo;
	
	<#list feigns as feign>
	@Inject
	protected ${feign.type.name}FeignClient ${feign.type.name?lower_case}FeignClient;	
	</#list>
	
	<#if generateReadAll>
	public Page<${name}> findAll(Pageable pageable) {
		return repo.findAll(pageable);
	}
	
	</#if>	
	<#if generateReadOne || generateUpdate || generateDelete>
	<#if generateReadOne>public<#else>private</#if> ${name} findOne(${key} id){
		Optional<${name}> optional = repo.findById(id);
		if (optional.isPresent()) {
			return optional.get();
		}
		throw new EntityNotFoundException("No row with the given identifier exists: [" + ${name}.class.getName() + ":" + id +"]");
	}
	
	</#if>	
	<#if generateCreate || generateUpdate>
	<#if generateCreate>public<#else>private</#if> ${name} save(${name} entity) {
		<#list feigns as feign>
		AbstractFeign${feign.type.name}DTO ${feign.type.name?lower_case} = ${feign.type.name?lower_case}FeignClient.getOne(entity.get${feign.name?cap_first}());
		if(${feign.type.name?lower_case} == null) {
			throw new EntityNotFoundException("No row with the given identifier exists: [ ${feign.type.name}:" + entity.get${feign.name?cap_first}() +"]");
		}
		</#list>
		return repo.update(entity);
	}
	
	</#if>	
	<#if generateUpdate>
	public ${name} update(${key} id, ${name} newEntity){
		${name} entity = findOne(id);
		<#list properties as property>
		entity.set${property.name?cap_first}(newEntity.get${property.name?cap_first}());
		</#list>
		return save(entity);
	}
	
	</#if>	
	<#if generateDelete>
	public void delete(${key} id){
		${name} entity = findOne(id);
		repo.delete(entity);
	}
	
	</#if>	
}
