package au.id.michaeluren.ass3.rs;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ParamExceptionMapper implements ExceptionMapper<NumberFormatException> {

	@Override
	public Response toResponse(NumberFormatException exception) {
		Response.ResponseBuilder responseBuilder = Response.status(Response.Status.BAD_REQUEST);
		responseBuilder.entity(Integer.toString(Response.Status.BAD_REQUEST.getStatusCode()));
		return responseBuilder.build();
	}

}
