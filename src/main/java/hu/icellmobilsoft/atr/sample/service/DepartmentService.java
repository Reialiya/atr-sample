package hu.icellmobilsoft.atr.sample.service;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;

import hu.icellmobilsoft.atr.sample.exception.BaseException;
import hu.icellmobilsoft.atr.sample.model.DepartmentEntity;
import hu.icellmobilsoft.atr.sample.util.SimplePatientConstans;

@Model
public class DepartmentService extends BaseService {

    @Inject
    private EntityManager entityManager;

    public DepartmentEntity findDepartment(String id) {
        if (StringUtils.isBlank(id)) {
            throw new IllegalArgumentException(SimplePatientConstans.PARAMETER_CANNOT_NULL_MSG);
        }
        return entityManager.find(DepartmentEntity.class, id);
    }

    @Transactional
    public void saveDepartment(DepartmentEntity department) throws BaseException {
         save(department);
    }

    public DepartmentEntity findDepartmentById(String id) {
        if (StringUtils.isBlank(id)) {
            throw new IllegalArgumentException(SimplePatientConstans.PARAMETER_CANNOT_NULL_MSG);
        }
        return entityManager.find(DepartmentEntity.class, id);
    }


}
