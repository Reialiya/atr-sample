package hu.icellmobilsoft.atr.sample.converter;

import hu.icellmobilsoft.atr.sample.model.InstituteEntity;
import hu.icellmobilsoft.atr.sample.util.ActiveInactiveStatus;
import hu.icellmobilsoft.atr.sample.util.EnumUtil;
import hu.icellmobilsoft.atr.sample.util.SimplePatientConstans;
import hu.icellmobilsoft.dto.sample.patient.InstituteType;

public class InstituteConverter {
    // db-ből jön az adat és entityvé lesz alakítva és fordítva is műkődnie kell
    public InstituteEntity convert(InstituteType instituteTypeDto) {
        if (instituteTypeDto == null) {
            throw new IllegalArgumentException(SimplePatientConstans.PARAMETER_CANNOT_NULL_MSG);
        }
        InstituteEntity instituteEntity = new InstituteEntity();
        instituteEntity.setId(instituteEntity.getId());
        instituteEntity.setName(instituteEntity.getName());
        instituteEntity.setDepartmentId(instituteEntity.getDepartmentId());
        instituteEntity.setStatus(EnumUtil.convert(instituteTypeDto.getStatus() , ActiveInactiveStatus.class));

        return instituteEntity;
    }

    public InstituteType convert(InstituteEntity instituteEntity) {
        if (instituteEntity == null) {
            throw new IllegalArgumentException(SimplePatientConstans.PARAMETER_CANNOT_NULL_MSG);
        }
        InstituteType instituteType = new InstituteType();
        instituteType.setId(instituteEntity.getId());
        instituteEntity.setName(instituteEntity.getName());
        instituteEntity.setDepartmentId(instituteEntity.getDepartmentId());

        return instituteType;
    }

}
