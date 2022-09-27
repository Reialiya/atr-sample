package hu.icellmobilsoft.atr.sample.action;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;

import hu.icellmobilsoft.atr.sample.converter.PatientConverter;
import hu.icellmobilsoft.atr.sample.exception.BaseException;
import hu.icellmobilsoft.atr.sample.exception.DeleteException;
import hu.icellmobilsoft.atr.sample.model.PatientEntity;
import hu.icellmobilsoft.atr.sample.repository.PatientRepository;
import hu.icellmobilsoft.atr.sample.util.SimplePatientConstans;
import hu.icellmobilsoft.dto.sample.patient.PatientRequest;
import hu.icellmobilsoft.dto.sample.patient.PatientResponse;

/**
 * The type Patient action.
 */
// savet kell meghívni benne
public class PatientAction {

    /**
     * The Patient repository.
     */
    @Inject
    PatientRepository patientRepository;

    /**
     * The Patient converter.
     */
    @Inject
    PatientConverter patientConverter;

    /**
     * Gets patient.
     *
     * @param patientID the patient id
     * @return the patient
     * @throws BaseException the base exception
     */
// kivadászom instituteID és departmentID alapján és talán patientID szűkítem
    public PatientResponse getPatient(String patientID) throws BaseException {
        if (StringUtils.isBlank(patientID)) {
            throw new BaseException(SimplePatientConstans.PARAMETER_CANNOT_NULL_MSG);
        }
        // patient ID?
        PatientEntity patient = patientRepository.findPatient(patientID);
        if (StringUtils.isBlank(patientID)) {
            throw new BaseException(SimplePatientConstans.PARAMETER_CANNOT_NULL_MSG);
        }
        return patientToResponse(patient);
    }

    /**
     * Post patient patient response.
     *
     * @param patientRequest the patient request
     * @return the patient response
     * @throws BaseException the base exception
     */
    @Transactional
    public PatientResponse postPatient(PatientRequest patientRequest) throws BaseException {
        if (patientRequest == null) {
            throw new BaseException(SimplePatientConstans.PARAMETER_CANNOT_NULL_MSG);
        }

        PatientEntity patientEntity = patientConverter.convert(patientRequest.getPatient());
        // patientRepository.findPatient(patientRequest.getPatient().getId());

        patientRepository.savePatient(patientEntity);
        return patientToResponse(patientEntity);
    }

    /**
     * Put patient patient response.
     *
     * @param patientRequest the patient request
     * @return the patient response
     * @throws BaseException the base exception
     */
    public PatientResponse putPatient(PatientRequest patientRequest) throws BaseException {
        if (patientRequest == null) {
            throw new BaseException(SimplePatientConstans.PARAMETER_CANNOT_NULL_MSG);
        }

        PatientEntity patientEntity = patientConverter.convert(patientRequest.getPatient());
        patientRepository.updatePatient(patientEntity);

        return patientToResponse(patientEntity);

    }

    /**
     * Delete patient patient response.
     *
     * @param patientID the patient id
     * @return the patient response
     * @throws DeleteException the delete exception
     */
    public PatientResponse deletePatient(String patientID) throws DeleteException {
        if (StringUtils.isBlank(patientID)) {
            throw new IllegalArgumentException(SimplePatientConstans.PARAMETER_CANNOT_NULL_MSG);
        }

        PatientEntity patientEntity = patientRepository.findPatient((patientID));
        if (patientEntity == null) {
            throw new DeleteException(SimplePatientConstans.NO_DEPARTMENT_WITH_THIS_ID_MSG);
        }

        patientRepository.deletePatient(patientID);
        return patientToResponse((patientEntity));

    }

    private PatientResponse patientToResponse(PatientEntity patient) {
        PatientResponse patientResponse = new PatientResponse();
        return patientResponse.withPatient(patientConverter.convert(patient));
    }

}
