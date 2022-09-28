package hu.icellmobilsoft.atr.sample.rest;


import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import hu.icellmobilsoft.atr.sample.exception.BaseException;
import hu.icellmobilsoft.dto.sample.patient.InstituteRequest;
import hu.icellmobilsoft.dto.sample.patient.InstituteResponse;
import javassist.NotFoundException;

@Path("/rest/institute")
public interface IInstituteRest {

    @GET
    @Path("/{id}")
//    @Produces({ "text/plain", MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, })
    InstituteResponse getInstitute(@PathParam("id") String instituteID) throws BaseException, NotFoundException;

    @POST
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes
    InstituteResponse postInstitute(InstituteRequest instituteRequest) throws BaseException;

    @PUT
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    InstituteResponse putInstitute(InstituteRequest instituteRequest) throws BaseException;


    // delete helyett put
    @PUT
    @Path("/delete/{id}")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    InstituteResponse deleteInstitute(@PathParam("id") String instituteID) throws BaseException;

}
