package hu.icellmobilsoft.atr.sample.converter;

import hu.icellmobilsoft.atr.sample.model.DepartmentEntity;
import hu.icellmobilsoft.atr.sample.util.SimplePatientConstans;
import hu.icellmobilsoft.dto.sample.patient.DepartmentType;

public class DepartmentConverter {
// db-ből jön az adat és entityvé lesz alakítva és fordítva is műkődnie kell
    public DepartmentEntity convert(DepartmentType departmentTypeDto) {
        if (departmentTypeDto == null) {
            throw new IllegalArgumentException(SimplePatientConstans.PARAMETER_CANNOT_NULL_MSG);
        }
        DepartmentEntity departmentEntity = new DepartmentEntity();
        departmentEntity.setId(departmentEntity.getId());
        departmentEntity.setName(departmentEntity.getName());
        return departmentEntity;
    }

    public DepartmentType convert(DepartmentEntity departmentEntity) {
        if (departmentEntity == null) {
            throw new IllegalArgumentException(SimplePatientConstans.PARAMETER_CANNOT_NULL_MSG);
        }
        DepartmentType departmentType = new DepartmentType();
        departmentType.setId(departmentEntity.getId());
        departmentType.setName(departmentEntity.getName());

        return departmentType;
    }

}
