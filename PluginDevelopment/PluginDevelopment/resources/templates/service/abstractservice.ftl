package generated.service${package};

import java.util.Optional;
import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;

import generated.model${package}.${name};
import handwritten.repository${package}.${name}Repository;

public abstract class Abstract${name}Service{	
	@Inject
	protected ${name}Repository repo;
	
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
		return repo.update(entity);
	}
	
	</#if>	
	<#if generateUpdate>
	public ${name} update(${key} id, ${name} newEntity){
		${name} entity = findOne(id);
		//TODO: setovati polja
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
