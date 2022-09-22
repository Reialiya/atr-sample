package hu.icellmobilsoft.atr.sample.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import hu.icellmobilsoft.atr.sample.action.SamplePatientAction;

@Path("/rest/ticketService")
public interface ILoadData {
//    SamplePatientAction loadFromXml();
//
//    SamplePatientAction loadFromJson();

    @GET
    @Path("/hello")
    @Produces({ "text/plain", MediaType.APPLICATION_JSON })
    Response getHello();

//    @GET
//    @Path("/getDepartment")
//    @Produces({ "text/plain", MediaType.APPLICATION_JSON })
//    Response getDepartment();

    SamplePatientAction loadFromXml();

    SamplePatientAction loadFromJson();
}
