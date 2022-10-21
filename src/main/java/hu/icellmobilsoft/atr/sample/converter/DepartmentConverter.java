package hu.icellmobilsoft.atr.sample.converter;

import hu.icellmobilsoft.atr.sample.model.DepartmentEntity;
import hu.icellmobilsoft.atr.sample.util.EnumUtil;
import hu.icellmobilsoft.atr.sample.util.SimplePatientConstans;
import hu.icellmobilsoft.dto.sample.common.ActiveInactiveStatus;
import hu.icellmobilsoft.dto.sample.department.DepartmentType;

/**
 * The type Department converter.
 * 
 * @author juhaszkata
 */
public class DepartmentConverter {
    /**
     * Convert department entity.
     *
     * @param departmentType
     *            the department type
     * @return the department entity
     */
    public DepartmentEntity convert(DepartmentType departmentType) {
        if (departmentType == null) {
            throw new IllegalArgumentException(SimplePatientConstans.PARAMETER_CANNOT_NULL_MSG);
        }
        DepartmentEntity departmentEntity = new DepartmentEntity();

        departmentEntity.setId(departmentType.getId());
        convert(departmentType, departmentEntity);

        return departmentEntity;
    }

    /**
     * Convert.
     *
     * @param departmentType
     *            the department type
     * @param departmentEntity
     *            the department entity
     */
    public void convert(DepartmentType departmentType, DepartmentEntity departmentEntity) {
        departmentEntity.setName(departmentType.getName());

    }

    /**
     * Convert department type.
     *
     * @param departmentEntity
     *            the department entity
     * @return the department type
     */
    public DepartmentType convert(DepartmentEntity departmentEntity) {
        if (departmentEntity == null) {
            throw new IllegalArgumentException(SimplePatientConstans.PARAMETER_CANNOT_NULL_MSG);
        }
        DepartmentType departmentType = new DepartmentType();

        departmentType.setId(departmentEntity.getId());

        departmentType.setName(departmentEntity.getName());
        departmentType.setStatus(EnumUtil.convert(departmentEntity.getStatus(), ActiveInactiveStatus.class));

        return departmentType;
    }

}
