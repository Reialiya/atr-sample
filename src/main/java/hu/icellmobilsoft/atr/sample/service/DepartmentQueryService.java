package hu.icellmobilsoft.atr.sample.service;

import hu.icellmobilsoft.atr.sample.exception.BaseException;
import hu.icellmobilsoft.atr.sample.model.DepartmentEntity;
import hu.icellmobilsoft.atr.sample.util.SimplePatientConstans;
import org.apache.commons.lang3.StringUtils;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

/**
 * The type Department service.
 */
@Model
public class DepartmentQueryService extends BaseService {

    @Inject
    private EntityManager entityManager;

    /**
     * Find department department entity.
     *
     * @param id the id
     * @return the department entity
     */
    public DepartmentEntity findDepartment(String id) {
        if (StringUtils.isBlank(id)) {
            throw new IllegalArgumentException(SimplePatientConstans.PARAMETER_CANNOT_NULL_MSG);
        }
        return entityManager.find(DepartmentEntity.class, id);
    }

    /**
     * Save department.
     *
     * @param department the department
     * @throws BaseException the base exception
     */
    @Transactional
    public void saveDepartment(DepartmentEntity department) throws BaseException {
        save(department);
    }

}
