package tp.paw.khet.webapp.exceptionmapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import tp.paw.khet.webapp.dto.ExceptionDTO;
import tp.paw.khet.webapp.exception.DTOValidationException;

@Provider
public class ValidationMapper implements ExceptionMapper<DTOValidationException>{
	
	private static final int UNPROCESSABLE_ENTITY = 422;

	@Override
	public Response toResponse(final DTOValidationException exception) {
		return Response.status(UNPROCESSABLE_ENTITY).entity(new ExceptionDTO(exception.getMessage(), exception.getConstraintViolations())).build();
	}

}
