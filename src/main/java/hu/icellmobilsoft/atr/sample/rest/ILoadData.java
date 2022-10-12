package hu.icellmobilsoft.atr.sample.rest;

import hu.icellmobilsoft.atr.sample.action.LoadDataAction;

//@Path("/rest/ticketService")
//@Path("/rest/load-data")
public interface ILoadData {
    // SamplePatientAction loadFromXml();
    //
    // SamplePatientAction loadFromJson();

//    @GET
//    // @Path()
//    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
//    BaseResponse loadFromXml(String filename) throws BaseException, NotFoundException;
//
//    @GET
//    // @Path()
//    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
//    BaseResponse loadFromJson(String filename) throws BaseException, NotFoundException;

    // TODO: endpointok (get), loadFromJson, xml visszarakása, csak ezekre, path pluszba

    // meghívás a loadDataActiont, baseresponse visszaválaszol

    LoadDataAction loadFromXml();

    LoadDataAction loadFromJson();
}
