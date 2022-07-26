package hu.icellmobilsoft.atr.sample.converter;

import hu.icellmobilsoft.atr.sample.model.PatientEntity;
import hu.icellmobilsoft.atr.sample.util.EnumUtil;
import hu.icellmobilsoft.atr.sample.util.SimplePatientConstans;
import hu.icellmobilsoft.dto.sample.patient.ActiveInactiveStatus;
import hu.icellmobilsoft.dto.sample.patient.PatientType;

public class PatientConverter {
    public PatientEntity convert(PatientType patientType) {
        if (patientType == null) {
            throw new IllegalArgumentException(SimplePatientConstans.PARAMETER_CANNOT_NULL_MSG);
        }
        PatientEntity patientEntity = new PatientEntity();
        convert(patientType, patientEntity);
        return patientEntity;
    }

    public void convert(PatientType patientType, PatientEntity patientEntity) {
        patientEntity.setName(patientType.getName());
        patientEntity.setUsername(patientType.getUsername());
        patientEntity.setEmail(patientType.getEmail());
        patientEntity.setDepartmentId(patientType.getDepartment());
        patientEntity.setInstituteId(patientType.getInstitute());
    }

    public PatientType convert(PatientEntity patientEntity) {
        if (patientEntity == null) {
            throw new IllegalArgumentException(SimplePatientConstans.PARAMETER_CANNOT_NULL_MSG);
        }
        PatientType patientType = new PatientType();
        patientType.setStatus(EnumUtil.convert(patientEntity.getStatus(), ActiveInactiveStatus.class));
        patientType.setName(patientEntity.getName());
        patientType.setEmail(patientEntity.getEmail());
        patientType.setUsername(patientEntity.getUsername());
        patientType.setDepartment(patientEntity.getDepartmentId());
        patientType.setInstitute(patientEntity.getInstituteId());

        return patientType;
    }

}
