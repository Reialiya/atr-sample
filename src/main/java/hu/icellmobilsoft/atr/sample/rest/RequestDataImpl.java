package hu.icellmobilsoft.atr.sample.rest;

import java.util.ArrayList;

import hu.icellmobilsoft.atr.sample.model.Patient;

public class RequestDataImpl implements IRequestData {
    @Override
    public Patient getPatientData(String id) {
        return null;
    }

    @Override
    public ArrayList<Patient> getAllUsersData() {
        return null;
    }

//    private PatientRepository patRep;
//
//    @Override
//    public Patient getPatientData(String id) {
//        Stream<Patient> patientStream = patRep.getAllPatient().stream().filter(predicate -> predicate.getId().equals(id));
//        return patientStream.findFirst().orElse(null);
//    }
//
//    @Override
//    public ArrayList<Patient> getAllUsersData() {
//        return patRep.getAllPatient();
//    }

}
