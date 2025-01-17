package generated.exception;

import javax.persistence.PersistenceException;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.server.exceptions.ExceptionHandler;

@SuppressWarnings("rawtypes")
public abstract class AbstractPersistenceExceptionHandler implements ExceptionHandler<PersistenceException, HttpResponse>{

	@Override
	public HttpResponse handle(HttpRequest request, PersistenceException exception) {		
		return HttpResponse.badRequest(exception.getCause().getCause().getMessage());
	}

}
