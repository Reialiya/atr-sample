package hu.icellmobilsoft.atr.sample.rest;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

import hu.icellmobilsoft.atr.sample.action.PatientAction;
import hu.icellmobilsoft.atr.sample.exception.BaseException;
import hu.icellmobilsoft.dto.sample.patient.PatientRequest;
import hu.icellmobilsoft.dto.sample.patient.PatientResponse;

/**
 * The type Patient rest.
 * @author juhaszkata
 */
@Model
public class PatientRest implements IPatientRest {

    @Inject
    private PatientAction patientAction;

    @Override
    public PatientResponse getPatient(String id) throws BaseException {
        return patientAction.getPatient(id);
    }

    @Override
    public PatientResponse postPatient(PatientRequest patientRequest) throws BaseException {
        return patientAction.postPatient(patientRequest);
    }

    @Override
    public PatientResponse putPatient(String patientID, PatientRequest patientRequest) throws BaseException {
        return patientAction.putPatient(patientID, patientRequest);
    }

    @Override
    public PatientResponse deletePatient(String patientID) throws BaseException {
        return patientAction.deletePatient(patientID);
    }
}
