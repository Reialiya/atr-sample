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

        CDI.current().select(PatientRepository.class).get().savePat(patient);

    }

    @Transactional
    public void savePat(PatientEntity patient) {
        persistenceHelper.getEntityManager().persist(patient);
    }

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
