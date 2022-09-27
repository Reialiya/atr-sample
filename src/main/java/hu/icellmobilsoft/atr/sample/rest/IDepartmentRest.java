package hu.icellmobilsoft.atr.sample.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import hu.icellmobilsoft.atr.sample.exception.BaseException;
import hu.icellmobilsoft.dto.sample.patient.DepartmentRequest;
import hu.icellmobilsoft.dto.sample.patient.DepartmentResponse;

/**
 * The interface Department rest.
 */
@Path("/rest/ticketService/")
public interface IDepartmentRest {

    /**
     * Gets department.
     *
     * @param departmentID the department id
     * @return the department
     * @throws BaseException the base exception
     */
    @GET
    @Path("/getDepartment/{id}")
    @Produces({ "text/plain", MediaType.APPLICATION_JSON })
    DepartmentResponse getDepartment(@PathParam("id") String departmentID) throws BaseException;

    /**
     * Post department department response.
     *
     * @param departmentRequest the department request
     * @return the department response
     * @throws BaseException the base exception
     */
    @POST
    @Path("/department")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    DepartmentResponse postDepartment(DepartmentRequest departmentRequest) throws BaseException;

    /**
     * Put department department response.
     *
     * @param departmentRequest the department request
     * @return the department response
     * @throws BaseException the base exception
     */
    @PUT
    @Path("/department/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    DepartmentResponse putDepartment(DepartmentRequest departmentRequest) throws BaseException;

    /**
     * Delete department department response.
     *
     * @param departmentID the department id
     * @return the department response
     * @throws BaseException the base exception
     */
    @DELETE
    @Path("/department/{id}")
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.TEXT_XML, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    DepartmentResponse deleteDepartment(@PathParam("id") String departmentID) throws BaseException;

}
