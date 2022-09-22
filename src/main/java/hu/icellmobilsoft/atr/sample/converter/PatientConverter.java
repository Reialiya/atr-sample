package hu.icellmobilsoft.atr.sample.converter;

import hu.icellmobilsoft.atr.sample.model.PatientEntity;
import hu.icellmobilsoft.atr.sample.util.EnumUtil;
import hu.icellmobilsoft.atr.sample.util.SimplePatientConstans;
import hu.icellmobilsoft.dto.sample.patient.ActiveInactiveStatus;
import hu.icellmobilsoft.dto.sample.patient.PatientType;

public class PatientConverter {
// db-ből jön az adat és entityvé lesz alakítva és fordítva is műkődnie kell
    public PatientEntity convert(PatientType patientType) {
        if (patientType == null) {
            throw new IllegalArgumentException(SimplePatientConstans.PARAMETER_CANNOT_NULL_MSG);
        }
        PatientEntity patientEntity = new PatientEntity();
        patientEntity.setId(patientType.getId());
        patientEntity.setName(patientType.getName());
        patientEntity.setUsername(patientType.getUsername());
        patientEntity.setEmail(patientType.getEmail());
        patientEntity.setDepartmentId(patientEntity.getDepartmentId());
        patientEntity.setInstituteId(patientEntity.getInstituteId());
        patientEntity.setStatus(EnumUtil.convert(patientType.getStatus(), hu.icellmobilsoft.atr.sample.util.ActiveInactiveStatus.class));

        return patientEntity;
    }

    public PatientType convert(PatientEntity patientEntity) {
        if (patientEntity == null) {
            throw new IllegalArgumentException(SimplePatientConstans.PARAMETER_CANNOT_NULL_MSG);
        }
        PatientType patientType = new PatientType();

        patientType.setId(patientType.getId());
        patientType.setName(patientEntity.getName());
        patientType.setEmail(patientEntity.getEmail());
        patientType.setUsername(patientEntity.getUsername());
        patientType.setDepartment(patientEntity.getDepartmentId());
        patientType.setInstitute(patientEntity.getInstituteId());
        patientType.setStatus(EnumUtil.convert(patientEntity.getStatus(), ActiveInactiveStatus.class));


        return patientType;
    }

}
