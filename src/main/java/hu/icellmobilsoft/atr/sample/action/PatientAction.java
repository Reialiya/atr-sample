package hu.icellmobilsoft.atr.sample.action;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import hu.icellmobilsoft.atr.sample.converter.PatientConverter;
import hu.icellmobilsoft.atr.sample.exception.BaseException;
import hu.icellmobilsoft.atr.sample.exception.DeleteException;
import hu.icellmobilsoft.atr.sample.model.PatientEntity;
import hu.icellmobilsoft.atr.sample.service.PatientService;
import hu.icellmobilsoft.atr.sample.util.ActiveInactiveStatus;
import hu.icellmobilsoft.atr.sample.util.RandomUtil;
import hu.icellmobilsoft.atr.sample.util.SimplePatientConstans;
import hu.icellmobilsoft.dto.sample.patient.PatientRequest;
import hu.icellmobilsoft.dto.sample.patient.PatientResponse;

/**
 * The type Patient action.
 *
 * @author juhaszkata
 */
@Model
public class PatientAction {

    /**
     * The Patient repository.
     */
    @Inject
    private PatientService patientService;

    /**
     * The Patient converter.
     */
    @Inject
    private PatientConverter patientConverter;

    /**
     * Gets patient.
     *
     * @param patientID the patient id
     * @return the patient
     * @throws BaseException the base exception
     */
    public PatientResponse getPatient(String patientID) throws BaseException {
        if (StringUtils.isBlank(patientID)) {
            throw new BaseException(SimplePatientConstans.PARAMETER_CANNOT_NULL_MSG);
        }

        PatientEntity patient = patientService.findPatientById(patientID);
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
    public PatientResponse postPatient(PatientRequest patientRequest) throws BaseException {
        if (patientRequest == null) {
            throw new BaseException(SimplePatientConstans.PARAMETER_CANNOT_NULL_MSG);
        }
        // ellenőrzi az usernamet
        PatientEntity patientEntity = patientConverter.convert(patientRequest.getPatient());
        try {
            isUsernameExist(patientRequest.getPatient().getUsername());

        } catch (Exception exception) {
            patientEntity.setId(RandomUtil.generateId());
            patientEntity.setStatus(ActiveInactiveStatus.ACTIVE);

            patientService.savePatient(patientEntity);

            return patientToResponse(patientEntity);
        }
        throw new BaseException("nem megfelelo adatok/username/");
    }

    /**
     * Put patient patient response.
     *
     * @param patientID      the patient id
     * @param patientRequest the patient request
     * @return the patient response
     * @throws BaseException the base exception
     */
    public PatientResponse putPatient(String patientID, PatientRequest patientRequest) throws BaseException {
        if (patientRequest == null) {
            throw new BaseException(SimplePatientConstans.PARAMETER_CANNOT_NULL_MSG);
        }
        if (patientRequest.getPatient().getUsername() != null) {
            throw new BaseException("módosításkor nem lehet usernamet megadni");
        }

        PatientEntity patientEntity = patientService.findPatientById(patientID);
        if (patientEntity == null) {
            throw new BaseException(SimplePatientConstans.ENTITY_DOES_NOT_EXIST_MSG);
        }
        patientConverter.convert(patientRequest.getPatient(), patientEntity);
        patientService.savePatient(patientEntity);

        return patientToResponse(patientEntity);

    }

    /**
     * Is username exist.
     *
     * @param username the username
     * @throws BaseException the base exception
     */
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
     * @param patientID the patient id
     * @return the patient response
     * @throws BaseException the base exception
     */
    public PatientResponse deletePatient(String patientID) throws BaseException {
        if (StringUtils.isBlank(patientID)) {
            throw new IllegalArgumentException(SimplePatientConstans.PARAMETER_CANNOT_NULL_MSG);
        }

        PatientEntity patientEntity = patientService.findPatientById((patientID));
        if (patientEntity == null) {
            throw new DeleteException(SimplePatientConstans.NO_DEPARTMENT_WITH_THIS_ID_MSG);
        }

        patientEntity.setStatus(ActiveInactiveStatus.INACTIVE);

        patientService.savePatient(patientEntity);

        return patientToResponse((patientEntity));
    }

    private PatientResponse patientToResponse(PatientEntity patient) {
        PatientResponse patientResponse = new PatientResponse();
        patientResponse.setId(patient.getId());

        return patientResponse.withPatient(patientConverter.convert(patient));
    }

}
