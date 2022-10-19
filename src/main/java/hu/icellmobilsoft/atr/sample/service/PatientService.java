package hu.icellmobilsoft.atr.sample.service;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.apache.deltaspike.jpa.api.transaction.Transactional;

import hu.icellmobilsoft.atr.sample.exception.BaseException;
import hu.icellmobilsoft.atr.sample.model.PatientEntity;
import hu.icellmobilsoft.atr.sample.util.SimplePatientConstans;

/**
 * The type Patient repository.
 */
@Model
public class PatientService extends BaseService {

    @Inject
    private EntityManager entityManager;

    /**
     * Find patient patient entity.
     *
     * @param id the id
     * @return the patient entity
     */
    public PatientEntity findPatientById(String id) {
        if (StringUtils.isBlank(id)) {
            throw new IllegalArgumentException(SimplePatientConstans.PARAMETER_CANNOT_NULL_MSG);
        }
        return entityManager.find(PatientEntity.class, id);
    }

    /**
     * Save patient.
     *
     * @param patient the patient
     * @throws BaseException the base exception
     */
    @Transactional
    public void savePatient(PatientEntity patient) throws BaseException  {
        save(patient);
    }

    /**
     * Find by username patient entity.
     *
     * @param username the username
     * @return the patient entity
     */
// criteriaBuilder alapján felépített db lekérdezés, entity válasz
    public PatientEntity findByUsername(String username) {
        if (StringUtils.isBlank(username)) {
            throw new IllegalArgumentException(SimplePatientConstans.PARAMETER_CANNOT_NULL_MSG);
        }

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<PatientEntity> query = criteriaBuilder.createQuery(PatientEntity.class);
        Root<PatientEntity> patientEntityRoot = query.from(PatientEntity.class);
        query.select(patientEntityRoot);
        query.where(criteriaBuilder.equal(patientEntityRoot.get("username"), username));

        return entityManager.createQuery(query).getSingleResult();
        // getResultStream() a végére, optional
    }

    // TODO: query lekérdezés deltaspike szerint


}
