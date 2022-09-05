package hu.icellmobilsoft.atr.sample.rest;

import hu.icellmobilsoft.atr.sample.action.SamplePatientAction;

public interface ILoadData {
    SamplePatientAction loadFromXml();

    SamplePatientAction loadFromJson();


  //  void loadFromXml();
}
