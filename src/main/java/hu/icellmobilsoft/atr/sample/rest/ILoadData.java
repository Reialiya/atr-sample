package hu.icellmobilsoft.atr.sample.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import hu.icellmobilsoft.atr.sample.exception.BaseException;
import hu.icellmobilsoft.dto.sample.patient.BaseResponse;

@Path("/rest/load-data")
public interface ILoadData {

    @GET
    // @Path()
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    BaseResponse loadFromXml(String filename) throws BaseException;

    @GET
    @Path("/json")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    BaseResponse loadFromJson(String filename) throws BaseException;

}
