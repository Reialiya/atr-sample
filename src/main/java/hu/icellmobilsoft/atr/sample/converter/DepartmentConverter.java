package hu.icellmobilsoft.atr.sample.converter;

import hu.icellmobilsoft.atr.sample.model.DepartmentEntity;
import hu.icellmobilsoft.atr.sample.util.EnumUtil;
import hu.icellmobilsoft.atr.sample.util.SimplePatientConstans;
import hu.icellmobilsoft.dto.sample.patient.ActiveInactiveStatus;
import hu.icellmobilsoft.dto.sample.patient.DepartmentType;

public class DepartmentConverter {
    // db-ből jön az adat és entityvé lesz alakítva és fordítva is műkődnie kell
    public DepartmentEntity convert(DepartmentType departmentType) {
        if (departmentType == null) {
            throw new IllegalArgumentException(SimplePatientConstans.PARAMETER_CANNOT_NULL_MSG);
        }
        DepartmentEntity departmentEntity = new DepartmentEntity();


//        departmentEntity.setId(departmentType.getId());
        departmentEntity.setName(departmentType.getName());
        convert(departmentType, departmentEntity);
//        departmentEntity.setStatus(EnumUtil.convert(departmentType.getStatus(), hu.icellmobilsoft.atr.sample.util.ActiveInactiveStatus.class));

        return departmentEntity;
    }

    public void convert(DepartmentType departmentType, DepartmentEntity departmentEntity){
        departmentEntity.setName(departmentType.getName());
    }


    public DepartmentType convert(DepartmentEntity departmentEntity) {
        if (departmentEntity == null) {
            throw new IllegalArgumentException(SimplePatientConstans.PARAMETER_CANNOT_NULL_MSG);
        }
        DepartmentType departmentType = new DepartmentType();

        // ID generálás
//        departmentType.setId(departmentEntity.getId());

        departmentType.setName(departmentEntity.getName());
        departmentType.setStatus(EnumUtil.convert(departmentEntity.getStatus(), ActiveInactiveStatus.class));

        return departmentType;
    }

}
