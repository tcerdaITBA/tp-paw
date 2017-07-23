package tp.paw.khet.webapp.exceptionmapper;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import tp.paw.khet.exception.DuplicateFavListException;
import tp.paw.khet.webapp.dto.ExceptionDTO;

@Provider
public class DuplicateFavListMapper implements ExceptionMapper<DuplicateFavListException> {

	@Override
	public Response toResponse(final DuplicateFavListException exception) {
		return Response.status(Status.CONFLICT).entity(new ExceptionDTO("Duplicate collection name")).type(MediaType.APPLICATION_JSON).build();
	}

}
