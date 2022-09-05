package hu.icellmobilsoft.atr.sample.rest;

import java.util.ArrayList;

import hu.icellmobilsoft.atr.sample.model.Patient;

public interface IRequestData {
     Patient getPatientData(String id);

    ArrayList<Patient> getAllUsersData();

}

