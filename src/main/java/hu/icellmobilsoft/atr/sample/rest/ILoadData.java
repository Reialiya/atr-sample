package hu.icellmobilsoft.atr.sample.rest;

import hu.icellmobilsoft.atr.sample.action.LoadDataAction;

//@Path("/rest/ticketService")
public interface ILoadData {
//    SamplePatientAction loadFromXml();
//
//    SamplePatientAction loadFromJson();




// TODO: endpointok (get), loadFromJson, xml visszarakása, csak ezekre, path pluszba

// meghívás a loadDataActiont, baseresponse visszaválaszol

    LoadDataAction loadFromXml();

    LoadDataAction loadFromJson();
}
