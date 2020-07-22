package handwritten.feignclient;

import generated.feignclient.Abstract${name}FeignClient;
import io.micronaut.http.client.annotation.Client;

@Client(id = "${microservice}", path = "/${name?lower_case}")
public interface ${name}FeignClient extends Abstract${name}FeignClient {

}
