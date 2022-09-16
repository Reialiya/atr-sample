package hu.icellmobilsoft.atr.sample.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * @author juhaszkata
 */
@Provider
public class BaseExceptionHandler implements ExceptionMapper<BaseException> {
    @Override
    public Response toResponse(BaseException e) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
    }
}
