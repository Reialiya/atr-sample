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

// savet kell meghívni benne
public class PatientAction {

    @Inject
    PatientRepository patientRepository;

    @Inject
    PatientConverter patientConverter;

    // kivadászom instituteID és departmentID alapján és talán patientID szűkítem
    public PatientResponse getPatient(String patientID) throws BaseException {
        if (StringUtils.isBlank(patientID)) {
            throw new BaseException(SimplePatientConstans.PARAMETER_CANNOT_NULL_MSG);
        }
//patient ID?
        PatientEntity patient = patientRepository.findPatient(patientID);
        if (StringUtils.isBlank(patientID)) {
            throw new BaseException(SimplePatientConstans.PARAMETER_CANNOT_NULL_MSG);
        }
return patientToResponse(patient);
    }

    @Transactional
    public PatientResponse postPatient(PatientRequest patientRequest) throws BaseException {
        if (patientRequest == null) {
            throw new BaseException(SimplePatientConstans.PARAMETER_CANNOT_NULL_MSG);
        }

        PatientEntity patient = patientRepository.findPatient(patientRequest.getPatient().getId());

        patientRepository.savePatient(patient);
        return patientToResponse(patient);
    }

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
