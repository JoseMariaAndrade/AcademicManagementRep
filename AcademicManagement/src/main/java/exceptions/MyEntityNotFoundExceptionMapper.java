package exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.logging.Logger;

@Provider
public class MyEntityNotFoundExceptionMapper implements ExceptionMapper<MyEntityNotFoundException> {

    private static final Logger LOGGER = Logger.getLogger("exceptions.MyEntityNotFoundExceptionMapper");

    @Override
    public Response toResponse(MyEntityNotFoundException e) {
        String errorMessage = e.getMessage();
        LOGGER.warning("ERROR: " + errorMessage);
        return Response.status(Response.Status.NOT_FOUND).entity(errorMessage).build();
    }
}
