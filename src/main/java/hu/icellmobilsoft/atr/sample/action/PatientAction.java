package hu.icellmobilsoft.atr.sample.action;

import javax.enterprise.inject.Model;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import hu.icellmobilsoft.atr.sample.converter.PatientConverter;
import hu.icellmobilsoft.atr.sample.exception.BaseException;
import hu.icellmobilsoft.atr.sample.exception.DeleteException;
import hu.icellmobilsoft.atr.sample.model.PatientEntity;
import hu.icellmobilsoft.atr.sample.service.PatientService;
import hu.icellmobilsoft.atr.sample.util.ActiveInactiveStatus;
import hu.icellmobilsoft.atr.sample.util.EnumUtil;
import hu.icellmobilsoft.atr.sample.util.RandomUtil;
import hu.icellmobilsoft.atr.sample.util.SimplePatientConstans;
import hu.icellmobilsoft.dto.sample.patient.PatientRequest;
import hu.icellmobilsoft.dto.sample.patient.PatientResponse;

/**
 * The type Patient action.
 */
@Model
public class PatientAction {

    /**
     * The Patient repository.
     */
    @Inject
    PatientService patientService;

    /**
     * The Patient converter.
     */
    @Inject
    PatientConverter patientConverter;

    /**
     * Gets patient.
     *
     * @param patientID
     *            the patient id
     * @return the patient
     * @throws BaseException
     *             the base exception
     */
    public PatientResponse getPatient(String patientID) throws BaseException {
        if (StringUtils.isBlank(patientID)) {
            throw new BaseException(SimplePatientConstans.PARAMETER_CANNOT_NULL_MSG);
        }
        // patient ID?
        PatientEntity patient = patientService.findPatient(patientID);
        if (StringUtils.isBlank(patientID)) {
            throw new BaseException(SimplePatientConstans.PARAMETER_CANNOT_NULL_MSG);
        }
        return patientToResponse(patient);
    }

    /**
     * Post patient patient response.
     *
     * @param patientRequest
     *            the patient request
     * @return the patient response
     * @throws BaseException
     *             the base exception
     */

    public PatientResponse postPatient(PatientRequest patientRequest) throws BaseException {
        if (patientRequest == null) {
            throw new BaseException(SimplePatientConstans.PARAMETER_CANNOT_NULL_MSG);
        }
        // ellenőrzi az usernamet
        isUsernameExist(patientRequest.getPatient().getUsername());

        PatientEntity patientEntity = patientConverter.convert(patientRequest.getPatient());
        patientEntity.setId(RandomUtil.generateId());
        patientEntity.setStatus(ActiveInactiveStatus.ACTIVE);

        CDI.current().select(PatientService.class).get().savePatient(patientEntity);
        return patientToResponse(patientEntity);
    }

//    @Transactional
//    public void savePatient(PatientEntity patient) throws BaseException {
//        patientService.save(patient);
//    }

    /**
     * Put patient patient response.
     *
     * @param patientRequest
     *            the patient request
     * @return the patient response
     * @throws BaseException
     *             the base exception
     */
    public PatientResponse putPatient(String patientID, PatientRequest patientRequest) throws BaseException {
        if (patientRequest == null) {
            throw new BaseException(SimplePatientConstans.PARAMETER_CANNOT_NULL_MSG);
        }
        if (patientRequest.getPatient().getUsername() != null) {
            throw new BaseException("módosításkor nem lehet usernamet megadni");
        }

        PatientEntity patientEntity = patientService.findPatient(patientID);
        if (patientEntity == null) {
            throw new BaseException(SimplePatientConstans.ENTITY_DOES_NOT_EXIST_MSG);
        }
        patientConverter.convert(patientRequest.getPatient(), patientEntity);
        CDI.current().select(PatientService.class).get().savePatient(patientEntity);

        return patientToResponse(patientEntity);

    }

    public void isUsernameExist(String username) throws BaseException {
        if (StringUtils.isBlank(username)) {
            throw new BaseException(SimplePatientConstans.ENTITY_DOES_NOT_EXIST_MSG);
        }
        PatientEntity patientEntity = patientService.findByUsername(username);
        if (patientEntity != null) {
            throw new BaseException("already exist");
        }
    }

    /**
     * Delete patient patient response.
     *
     * @param patientID
     *            the patient id
     * @return the patient response
     * @throws DeleteException
     *             the delete exception
     */
    public PatientResponse deletePatient(String patientID) throws BaseException {
        if (StringUtils.isBlank(patientID)) {
            throw new IllegalArgumentException(SimplePatientConstans.PARAMETER_CANNOT_NULL_MSG);
        }

        PatientEntity patientEntity = patientService.findPatient((patientID));
        if (patientEntity == null) {
            throw new DeleteException(SimplePatientConstans.NO_DEPARTMENT_WITH_THIS_ID_MSG);
        }

        patientEntity.setStatus(ActiveInactiveStatus.INACTIVE);

        CDI.current().select(PatientService.class).get().savePatient(patientEntity);

        return patientToResponse((patientEntity));

    }

    private PatientResponse patientToResponse(PatientEntity patient) {
        PatientResponse patientResponse = new PatientResponse();
        patientResponse.setId(patient.getId());
        patientResponse.setStatus(EnumUtil.convert(patient.getStatus(), hu.icellmobilsoft.dto.sample.patient.ActiveInactiveStatus.class));
        return patientResponse.withPatient(patientConverter.convert(patient));
    }

}
