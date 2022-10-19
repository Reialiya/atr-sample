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

/**
 * The interface Institute rest.
 */
@Path("/rest/institute")
public interface IInstituteRest {

    /**
     * Gets institute.
     *
     * @param instituteID
     *            the institute id
     * @return the institute
     * @throws BaseException
     *             the base exception
     * @throws NotFoundException
     *             the not found exception
     */
    @GET
    @Path("/{id}")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, })
    InstituteResponse getInstitute(@PathParam("id") String instituteID) throws BaseException, NotFoundException;

    /**
     * Post institute institute response.
     *
     * @param instituteRequest
     *            the institute request
     * @return the institute response
     * @throws BaseException
     *             the base exception
     */
    @POST
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    InstituteResponse postInstitute(InstituteRequest instituteRequest) throws BaseException;

    /**
     * Put institute institute response.
     *
     * @param instituteRequest
     *            the institute request
     * @param id
     *            the id
     * @return the institute response
     * @throws BaseException
     *             the base exception
     */
    @PUT
    @Path("/{id}")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    InstituteResponse putInstitute(InstituteRequest instituteRequest, @PathParam("id") String id) throws BaseException;

    /**
     * Delete institute institute response.
     *
     * @param instituteID
     *            the institute id
     * @return the institute response
     * @throws BaseException
     *             the base exception
     */
    // delete helyett put
    @PUT
    @Path("/delete/{id}")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    InstituteResponse deleteInstitute(@PathParam("id") String instituteID) throws BaseException;

}
