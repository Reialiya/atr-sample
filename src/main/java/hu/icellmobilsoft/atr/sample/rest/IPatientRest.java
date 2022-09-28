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
import hu.icellmobilsoft.dto.sample.patient.PatientRequest;
import hu.icellmobilsoft.dto.sample.patient.PatientResponse;
import javassist.NotFoundException;

@Path("/rest/patient")
public interface IPatientRest {

    @GET
    @Path("/{id}")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, })
    PatientResponse getPatient(@PathParam("id") String patientID) throws BaseException, NotFoundException;

    @POST
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Consumes
    PatientResponse postPatient(PatientRequest patientRequest) throws BaseException;

    @PUT
    @Path("/{id}")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    PatientResponse putPatient(PatientRequest patientRequest) throws BaseException;

    @PUT
    @Path("/delete/{id}")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    PatientResponse deletePatient(@PathParam("id") String patientID) throws BaseException;

}
