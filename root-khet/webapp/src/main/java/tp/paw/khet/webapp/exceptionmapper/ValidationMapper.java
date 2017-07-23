package tp.paw.khet.webapp.exceptionmapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status.Family;
import javax.ws.rs.core.Response.StatusType;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import tp.paw.khet.webapp.dto.ExceptionDTO;
import tp.paw.khet.webapp.exception.DTOValidationException;

@Provider
public class ValidationMapper implements ExceptionMapper<DTOValidationException>{
	
	private final static StatusType UNPROCESSABLE_ENTITY = new StatusType() {
		
		@Override
		public int getStatusCode() {
			return 422;
		}
		
		@Override
		public String getReasonPhrase() {
			return "Unprocessable Entity";
		}
		
		@Override
		public Family getFamily() {
			return Family.CLIENT_ERROR;
		}
	};

	@Override
	public Response toResponse(final DTOValidationException exception) {
		return Response.status(UNPROCESSABLE_ENTITY).entity(new ExceptionDTO(exception.getMessage(), exception.getConstraintViolations())).build();
	}
}