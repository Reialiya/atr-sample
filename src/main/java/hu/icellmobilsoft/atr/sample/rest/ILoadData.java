package hu.icellmobilsoft.atr.sample.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import hu.icellmobilsoft.atr.sample.exception.BaseException;
import hu.icellmobilsoft.atr.sample.exception.NotFoundException;
import hu.icellmobilsoft.dto.sample.patient.BaseResponse;

//@Path("/rest/ticketService")
@Path("/rest/load-data")
public interface ILoadData {
    // SamplePatientAction loadFromXml();
    //
    // SamplePatientAction loadFromJson();

    @GET
    // @Path()
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    BaseResponse loadFromXml(String filename) throws BaseException, NotFoundException;

    @GET
    @Path("/json")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    BaseResponse loadFromJson(String filename) throws BaseException, NotFoundException;

    // TODO: endpointok (get), loadFromJson, xml visszarakása, csak ezekre, path pluszba

    // meghívás a loadDataActiont, baseresponse visszaválaszol

}
