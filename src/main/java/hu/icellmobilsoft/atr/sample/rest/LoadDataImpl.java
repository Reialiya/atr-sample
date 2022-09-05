package hu.icellmobilsoft.atr.sample.rest;


import hu.icellmobilsoft.atr.sample.action.SamplePatientAction;

public class LoadDataImpl implements ILoadData {
    private final SamplePatientAction oSampleActionPatient = new SamplePatientAction();
    /// elérési útvonala a xml, xsd
    private static final String XML_FILE = "sample.xml";
    private static final String JSON_FILE = "example.json";

   /* @Override
    public void loadFromXml() throws BaseException {
        simplePatientAction.loadFromXml(XML_FILE);
    }

    @Override
    public void loadFromJson() throws BaseException {
        simplePatientAction.loadFromJson(JSON_FILE);
    }*/
    @Override
    public SamplePatientAction loadFromXml() {
        oSampleActionPatient.loadFromXml(XML_FILE);
        return this.oSampleActionPatient;
    }

    @Override
    public SamplePatientAction loadFromJson() {
        oSampleActionPatient.loadFromJson(JSON_FILE);
        return this.oSampleActionPatient;
    }

}
