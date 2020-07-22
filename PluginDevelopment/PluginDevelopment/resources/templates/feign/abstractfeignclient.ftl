package generated.feignclient;

import handwritten.dto.feign.Feign${name}DTO;
import io.micronaut.http.annotation.Get;

public interface Abstract${name}FeignClient {

	@Get("/{id}")
	public Feign${name}DTO getOne(${key} id);
    
}
