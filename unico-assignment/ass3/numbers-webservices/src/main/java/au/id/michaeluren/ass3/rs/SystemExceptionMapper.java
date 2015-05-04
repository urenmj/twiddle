package au.id.michaeluren.ass3.rs;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import au.id.michaeluren.ass3.util.SystemException;

@Provider
public class SystemExceptionMapper implements ExceptionMapper<SystemException> {

	@Override
	public Response toResponse(SystemException exception) {
		Response.ResponseBuilder responseBuilder = Response.status(Response.Status.INTERNAL_SERVER_ERROR); // set the HTTP response
		responseBuilder.entity(Integer.toString(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())); // and the same value in the content, as required
		return responseBuilder.build();
	}

}
