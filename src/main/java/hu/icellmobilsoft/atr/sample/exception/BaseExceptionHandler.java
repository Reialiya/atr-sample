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

        if (e instanceof NotFoundException){
            return Response.status(Response.Status.NOT_FOUND).entity((e.getMessage())).build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
    }
}
