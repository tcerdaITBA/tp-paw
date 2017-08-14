package tp.paw.khet.webapp.exceptionmapper;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import tp.paw.khet.exception.DuplicateEmailException;
import tp.paw.khet.webapp.dto.ExceptionDTO;

@Provider
public class DuplicateEmailMapper implements ExceptionMapper<DuplicateEmailException> {

	@Override
	public Response toResponse(final DuplicateEmailException exception) {
		return Response.status(Status.CONFLICT).entity(new ExceptionDTO("Duplicate user email")).type(MediaType.APPLICATION_JSON).build();
	}

}
