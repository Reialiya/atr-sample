package hu.icellmobilsoft.atr.sample.action;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import hu.icellmobilsoft.atr.sample.exception.BaseException;
import hu.icellmobilsoft.atr.sample.model.DepartmentEntity;
import hu.icellmobilsoft.atr.sample.repository.DepartmentRepository;
import hu.icellmobilsoft.atr.sample.util.SimplePatientConstans;
import hu.icellmobilsoft.dto.sample.patient.DepartmentResponse;

@Model
public class DepartmentAction {

    @Inject
    private DepartmentRepository departmentRepository;


    public DepartmentResponse getDepartment(String departmentID) throws BaseException {
        if (StringUtils.isBlank(departmentID)) {
            throw new BaseException(SimplePatientConstans.PARAMETER_CANNOT_NULL_MSG);
        }

        DepartmentEntity department = departmentRepository.findDepartment(departmentID);
        if (StringUtils.isNull(departmentID)) {
            throw new BaseException(SimplePatientConstans.PARAMETER_CANNOT_NULL_MSG);
        }
        return getDepartment(departmentID);
    }

    private DepartmentResponse toResponse(DepartmentEntity department) {
        DepartmentResponse departmentResponse = new DepartmentResponse();

    return null;
    }


}
