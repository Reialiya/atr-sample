package hu.icellmobilsoft.atr.sample.repository;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import javax.enterprise.inject.Model;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;

import hu.icellmobilsoft.atr.sample.model.PatientEntity;
import hu.icellmobilsoft.atr.sample.util.ActiveInactiveStatus;
import hu.icellmobilsoft.atr.sample.util.PersistenceHelper;
import hu.icellmobilsoft.atr.sample.util.SimplePatientConstans;

/**
 * The type Patient repository.
 */
@Model
public class PatientRepository {
    @Inject
    private PersistenceHelper persistenceHelper;
    /**
     * The Patients.
     */
    ArrayList<PatientEntity> patients = new ArrayList<>();

    /**
     * Find patient patient entity.
     *
     * @param id
     *            the id
     * @return the patient entity
     */
    public PatientEntity findPatient(String id) {
        if (StringUtils.isBlank(id)) {
            throw new IllegalArgumentException(SimplePatientConstans.PARAMETER_CANNOT_NULL_MSG);
        }
        return persistenceHelper.getEntityManager().find(PatientEntity.class, id);
    }

    /**
     * Save patient.
     *
     * @param patient
     *            the patient
     */

    public void savePatient(PatientEntity patient) {
        if (patient == null) {
            throw new IllegalArgumentException(SimplePatientConstans.PARAMETER_CANNOT_NULL_MSG);
        }

        CDI.current().select(PatientRepository.class).get().savePat(patient);

    }

    @Transactional
    public void savePat(PatientEntity patient) {
        persistenceHelper.getEntityManager().persist(patient);
    }

    /**
     * Update patient.
     *
     * @param patientEntity
     *            the patient entity
     */
    public void updatePatient(PatientEntity patientEntity) {
        if (patientEntity == null) {
            throw new IllegalArgumentException(SimplePatientConstans.PARAMETER_CANNOT_NULL_MSG);
        }

        PatientEntity existingPatient = findPatient(patientEntity.getId());

        if (existingPatient != null) {
            existingPatient.setName(existingPatient.getName());
            existingPatient.setEmail(existingPatient.getEmail());
            existingPatient.setUsername(existingPatient.getUsername());
            existingPatient.setInstituteId(existingPatient.getInstituteId());
            existingPatient.setDepartmentId(existingPatient.getDepartmentId());
            existingPatient.setStatus(existingPatient.getStatus());
            persistenceHelper.getEntityManager().persist(existingPatient);
        }

    }

    /**
     * Delete patient.
     *
     * @param id
     *            the id
     */
    public void deletePatient(String id) {
        if (StringUtils.isBlank(id)) {
            throw new IllegalArgumentException(SimplePatientConstans.PARAMETER_CANNOT_NULL_MSG);
        }
        PatientEntity findPat = findPatient(id);
        if (findPat == null) {
            throw new NoSuchElementException(SimplePatientConstans.NO_TICKET_WITH_THIS_ID_MSG);
        }
        findPat.setStatus(ActiveInactiveStatus.INACTIVE);
        persistenceHelper.getEntityManager().remove(findPat);

    }

}
