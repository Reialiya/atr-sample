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
import hu.icellmobilsoft.dto.sample.patient.DepartmentRequest;
import hu.icellmobilsoft.dto.sample.patient.DepartmentResponse;
import javassist.NotFoundException;

/**
 * The interface Department rest.
 *
 * @author juhaszkata
 */
@Path("/rest/department/")
public interface IDepartmentRest {

    /**
     * Gets department.
     *
     * @param departmentID
     *            the department id
     * @return the department
     * @throws BaseException
     *             the base exception
     * @throws NotFoundException
     *             the not found exception
     */
    @GET
    @Path("/{id}")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, })
    DepartmentResponse getDepartment(@PathParam("id") String departmentID) throws BaseException, NotFoundException;

    /**
     * Update department data.
     *
     * @param departmentRequest
     *            the department request
     * @param id
     *            the id
     * @return the department response
     * @throws BaseException
     *             the base exception
     */
    // ahol van request, ott mindig kell Consumesnek lennie
    @PUT
    @Path("/{id}")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    DepartmentResponse putDepartment(DepartmentRequest departmentRequest, @PathParam("id") String id) throws BaseException;

    /**
     * Post department department response.
     *
     * @param departmentRequest
     *            the department request
     * @return the department response
     * @throws BaseException
     *             the base exception
     */
    @POST
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    DepartmentResponse postDepartment(DepartmentRequest departmentRequest) throws BaseException;

    /**
     * Delete department department response. Delete helyett put methodot hasznalunk
     *
     * @param departmentID
     *            the department id
     * @return the department response
     * @throws BaseException
     *             the base exception
     */
    @PUT
    @Path("/delete/{id}")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    DepartmentResponse deleteDepartment(@PathParam("id") String departmentID) throws BaseException;

}
