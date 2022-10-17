package hu.icellmobilsoft.atr.sample.rest;

import hu.icellmobilsoft.atr.sample.action.LoadDataAction;
import hu.icellmobilsoft.atr.sample.exception.BaseException;
import hu.icellmobilsoft.atr.sample.exception.NotFoundException;
import hu.icellmobilsoft.dto.sample.patient.BaseResponse;

public class LoadDataImpl implements ILoadData {
    private final LoadDataAction oSampleActionPatient = new LoadDataAction();

    private static final String XML_FILE = "sample.xml";
    private static final String JSON_FILE = "example.json";

    @Override
    public BaseResponse loadFromXml(String filename) throws BaseException, NotFoundException {
        return null;
    }

    @Override
    public BaseResponse loadFromJson(String filename) throws BaseException, NotFoundException {
        return null;
    }

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
