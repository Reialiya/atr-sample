package hu.icellmobilsoft.atr.sample.converter;

import hu.icellmobilsoft.atr.sample.model.InstituteEntity;
import hu.icellmobilsoft.atr.sample.util.ActiveInactiveStatus;
import hu.icellmobilsoft.atr.sample.util.EnumUtil;
import hu.icellmobilsoft.atr.sample.util.SimplePatientConstans;
import hu.icellmobilsoft.dto.sample.patient.InstituteType;

public class InstituteConverter {
    // db-ből jön az adat és entityvé lesz alakítva és fordítva is műkődnie kell
    public InstituteEntity convert(InstituteType instituteType) {
        if (instituteType== null) {
            throw new IllegalArgumentException(SimplePatientConstans.PARAMETER_CANNOT_NULL_MSG);
        }
        InstituteEntity instituteEntity = new InstituteEntity();
        instituteEntity.setId(instituteType.getId());
        instituteEntity.setName(instituteType.getName());
        instituteEntity.setDepartmentId(instituteEntity.getDepartmentId());
        instituteEntity.setStatus(EnumUtil.convert(instituteType.getStatus() , ActiveInactiveStatus.class));

        return instituteEntity;
    }

    public InstituteType convert(InstituteEntity instituteEntity) {
        if (instituteEntity == null) {
            throw new IllegalArgumentException(SimplePatientConstans.PARAMETER_CANNOT_NULL_MSG);
        }
        InstituteType instituteType = new InstituteType();
        instituteType.setId(instituteEntity.getId());
        instituteType.setName(instituteEntity.getName());
        instituteType.setStatus(EnumUtil.convert(instituteEntity.getStatus(), hu.icellmobilsoft.dto.sample.patient.ActiveInactiveStatus.class));

        return instituteType;
    }

}
