package hu.icellmobilsoft.atr.sample.rest;


import hu.icellmobilsoft.atr.sample.action.SamplePatientAction;

public class LoadDataImpl implements ILoadData {
    private final SamplePatientAction oSampleActionPatient = new SamplePatientAction();

    private static final String XML_FILE = "sample.xml";
    private static final String JSON_FILE = "example.json";

    @Override
    public SamplePatientAction loadFromXml() {
//        oSampleActionPatient.loadFromXml(XML_FILE);
        return this.oSampleActionPatient;
    }

    @Override
    public SamplePatientAction loadFromJson() {
//        oSampleActionPatient.loadFromJson(JSON_FILE);
        return this.oSampleActionPatient;
    }

}
