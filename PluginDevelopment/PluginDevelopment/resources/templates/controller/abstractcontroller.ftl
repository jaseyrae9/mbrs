package generated.controller${package};

import javax.inject.Inject;
<#if generateReadAll>
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
</#if>
<#if generateCreate || generateUpdate>
import io.micronaut.http.annotation.Body;
</#if>
<#if generateDelete>
import io.micronaut.http.annotation.Delete;
</#if>
<#if generateReadAll || generateReadOne>
import io.micronaut.http.annotation.Get;
</#if>
<#if generateCreate>
import io.micronaut.http.annotation.Post;
</#if>
<#if generateUpdate>
import io.micronaut.http.annotation.Put;
</#if>

import handwritten.mapper.MapperCatalogue;
import generated.dto${package}.Abstract${name}DTO;
import generated.model${package}.${name};
import handwritten.service${package}.${name}Service;

public abstract class Abstract${name}Controller {	
	@Inject
	protected ${name}Service service;
	
	@Inject
	protected MapperCatalogue mapperCatalogue;
	
	<#if generateReadAll>
	@Get
	public Page<Abstract${name}DTO> getAll(Pageable pageable) {
		return service.findAll(pageable).map(el -> mapperCatalogue.get${name}Mapper().fullConversion(el));
	}
	
	</#if>
	<#if generateReadOne>
	@Get("/{id}")
	public Abstract${name}DTO getOne(${key} id){
		return mapperCatalogue.get${name}Mapper().fullConversion(service.findOne(id));
	}
	
	</#if>
	<#if generateCreate>
	@Post
	public Abstract${name}DTO save(@Body ${name} entity) {
		return mapperCatalogue.get${name}Mapper().fullConversion(service.save(entity));
	}
	
	</#if>
	<#if generateUpdate>
	@Put("/{id}")
	public Abstract${name}DTO edit(${key} id, @Body ${name} entity){
		return mapperCatalogue.get${name}Mapper().fullConversion(service.update(id, entity));
	}

	</#if>
	<#if generateDelete>
	@Delete("/{id}")
	public void delete(${key} id){
		service.delete(id);
	}
	
	</#if>
}