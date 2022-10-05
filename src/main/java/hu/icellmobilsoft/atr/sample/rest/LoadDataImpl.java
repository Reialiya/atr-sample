package hu.icellmobilsoft.atr.sample.rest;

import hu.icellmobilsoft.atr.sample.action.LoadDataAction;

public class LoadDataImpl implements ILoadData {
    private final LoadDataAction oSampleActionPatient = new LoadDataAction();

    private static final String XML_FILE = "sample.xml";
    private static final String JSON_FILE = "example.json";
//
//    @Override
//    public Response getHello() {
//        return Response.ok("Hello !").build();
//    }

//    @Override
//    public Response getDepartment(){
//        return Response.
//    }


     @Override
     public LoadDataAction loadFromXml() {
    // oSampleActionPatient.loadFromXml(XML_FILE);
     return this.oSampleActionPatient;
     }

     @Override
     public LoadDataAction loadFromJson() {
    // oSampleActionPatient.loadFromJson(JSON_FILE);
     return this.oSampleActionPatient;
     }

}
