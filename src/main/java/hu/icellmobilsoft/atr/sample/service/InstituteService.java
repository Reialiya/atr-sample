package hu.icellmobilsoft.atr.sample.service;

import java.util.NoSuchElementException;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;

import hu.icellmobilsoft.atr.sample.exception.BaseException;
import hu.icellmobilsoft.atr.sample.model.InstituteEntity;
import hu.icellmobilsoft.atr.sample.util.ActiveInactiveStatus;
import hu.icellmobilsoft.atr.sample.util.SimplePatientConstans;

@Model
public class InstituteService extends BaseService {

    @Inject
    private EntityManager entityManager;

    public InstituteEntity findInstitute(String id) {
        if (StringUtils.isBlank(id)) {
            throw new IllegalArgumentException(SimplePatientConstans.PARAMETER_CANNOT_NULL_MSG);
        }
        return entityManager.find(InstituteEntity.class, id);
    }

    public InstituteEntity findByIds(String departmentId, String instituteId) {
        if (StringUtils.isBlank(departmentId) && StringUtils.isBlank(instituteId)) {
            throw new IllegalArgumentException(SimplePatientConstans.PARAMETER_CANNOT_NULL_MSG);
        }

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<InstituteEntity> query = cb.createQuery(InstituteEntity.class);
        Root<InstituteEntity> instituteEntityRoot = query.from(InstituteEntity.class);

        Predicate isDepEqual = cb.equal(instituteEntityRoot.get("departmentId"), departmentId);
        Predicate isInstEqual = cb.equal(instituteEntityRoot.get("instituteId"), instituteId);

        query.select(instituteEntityRoot).where(cb.and(isDepEqual, isInstEqual));
        // select * from JK_INST where departmentId = "12345" and instituteId = "5432"

        return entityManager.createQuery(query).getSingleResult();
    }

    @Transactional
    public void saveInstitute(InstituteEntity institute) throws BaseException {
        save(institute);
    }

    public void deleteInstitute(String id) {
        if (StringUtils.isBlank(id)) {
            throw new IllegalArgumentException(SimplePatientConstans.PARAMETER_CANNOT_NULL_MSG);
        }
        InstituteEntity findInst = findInstitute(id);
        if (findInst == null) {
            throw new NoSuchElementException(SimplePatientConstans.NO_INSTITUTE_WITH_THIS_ID_MSG);
        }
        findInst.setStatus(ActiveInactiveStatus.INACTIVE);
        entityManager.remove(findInst);

    }

}
