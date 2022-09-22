package hu.icellmobilsoft.atr.sample.repository;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import hu.icellmobilsoft.atr.sample.model.PatientEntity;
import hu.icellmobilsoft.atr.sample.util.PersistenceHelper;
import hu.icellmobilsoft.atr.sample.util.SimplePatientConstans;

@Model
public class PatientRepository {
    @Inject
    private PersistenceHelper persistenceHelper;
    ArrayList<PatientEntity> patients = new ArrayList<>();

    public PatientEntity findPatient(String id) {
        if (StringUtils.isBlank(id)) {
            throw new IllegalArgumentException(SimplePatientConstans.PARAMETER_CANNOT_NULL_MSG);
        }
        return persistenceHelper.getEntityManager().find(PatientEntity.class, id);
    }

    public void savePatient(PatientEntity patient) {
        if (patient == null) {
            throw new IllegalArgumentException(SimplePatientConstans.PARAMETER_CANNOT_NULL_MSG);
        }

        PatientEntity existingPatient = findPatient(patient.getInstituteId());
        if (existingPatient != null){
            existingPatient.setId(patient.getId());
            existingPatient.setName(patient.getName());
            existingPatient.setUsername(patient.getUsername());
            existingPatient.setEmail(patient.getEmail());
            existingPatient.setInstituteId(patient.getInstituteId());
            existingPatient.setDepartmentId(patient.getDepartmentId());
            existingPatient.setStatus(patient.getStatus());
        }
        persistenceHelper.getEntityManager().persist(patient);

    }
// később státusszak kiegészítve
    public void deletePatient(String id) {
        if (StringUtils.isBlank(id)) {
            throw new IllegalArgumentException(SimplePatientConstans.PARAMETER_CANNOT_NULL_MSG);
        }
        PatientEntity findPat = findPatient(id);
        if (findPat == null) {
            throw new NoSuchElementException(SimplePatientConstans.NO_TICKET_WITH_THIS_ID_MSG);
        }
        persistenceHelper.getEntityManager().remove(findPat);

    }






}
