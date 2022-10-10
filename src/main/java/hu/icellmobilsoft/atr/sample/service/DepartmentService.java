package hu.icellmobilsoft.atr.sample.service;

import org.apache.commons.lang3.StringUtils;

import hu.icellmobilsoft.atr.sample.model.DepartmentEntity;
import hu.icellmobilsoft.atr.sample.repository.BaseRepository;
import hu.icellmobilsoft.atr.sample.util.SimplePatientConstans;

public class DepartmentService extends BaseRepository {

    public DepartmentEntity findDepartment(String id) {
        if (StringUtils.isBlank(id)) {
            throw new IllegalArgumentException(SimplePatientConstans.PARAMETER_CANNOT_NULL_MSG);
        }
        return getEntityManager().find(DepartmentEntity.class, id);
    }



}
