package hu.icellmobilsoft.atr.sample.converter;

import hu.icellmobilsoft.atr.sample.model.InstituteEntity;
import hu.icellmobilsoft.atr.sample.util.EnumUtil;
import hu.icellmobilsoft.atr.sample.util.SimplePatientConstans;
import hu.icellmobilsoft.dto.sample.patient.InstituteType;

public class InstituteConverter {
    public InstituteEntity convert(InstituteType instituteType) {
        if (instituteType == null) {
            throw new IllegalArgumentException(SimplePatientConstans.PARAMETER_CANNOT_NULL_MSG);
        }
        InstituteEntity instituteEntity = new InstituteEntity();

        convert(instituteEntity, instituteType);
        // status, id actionba állítva
        return instituteEntity;
    }

    public void convert(InstituteEntity instituteEntity, InstituteType instituteType) {
        instituteEntity.setName(instituteType.getName());
        instituteEntity.setDepartmentId(instituteType.getDepartmentId());
        instituteEntity.setInstituteId(instituteType.getInstituteId());
    }

    public InstituteType convert(InstituteEntity instituteEntity) {
        if (instituteEntity == null) {
            throw new IllegalArgumentException(SimplePatientConstans.PARAMETER_CANNOT_NULL_MSG);
        }
        InstituteType instituteType = new InstituteType();

        instituteType.setId(instituteEntity.getId());
        instituteType.setName(instituteEntity.getName());
        instituteType.setDepartmentId(instituteEntity.getDepartmentId());
        instituteType.setInstituteId(instituteEntity.getInstituteId());
        instituteType.setStatus(EnumUtil.convert(instituteEntity.getStatus(), hu.icellmobilsoft.dto.sample.patient.ActiveInactiveStatus.class));

        return instituteType;
    }

}
