package hu.icellmobilsoft.atr.sample.action;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;

import hu.icellmobilsoft.atr.sample.converter.DepartmentConverter;
import hu.icellmobilsoft.atr.sample.exception.BaseException;
import hu.icellmobilsoft.atr.sample.exception.DeleteException;
import hu.icellmobilsoft.atr.sample.model.DepartmentEntity;
import hu.icellmobilsoft.atr.sample.repository.DepartmentRepository;
import hu.icellmobilsoft.atr.sample.util.SimplePatientConstans;
import hu.icellmobilsoft.dto.sample.patient.DepartmentRequest;
import hu.icellmobilsoft.dto.sample.patient.DepartmentResponse;

@Model
public class DepartmentAction {

    @Inject
    private DepartmentRepository departmentRepository;

    @Inject
    private DepartmentConverter departmentConverter;

// departmentID alapján az entityvel visszatérünk
    public DepartmentResponse getDepartment(String departmentID) throws BaseException {
        if (StringUtils.isBlank(departmentID)) {
            throw new BaseException(SimplePatientConstans.PARAMETER_CANNOT_NULL_MSG);
        }

        DepartmentEntity department = departmentRepository.findDepartment(departmentID);
        if (StringUtils.isBlank(departmentID)) {
            throw new BaseException(SimplePatientConstans.PARAMETER_CANNOT_NULL_MSG);
        }
        return DepartmentToResponse(department);
    }

//    DepartmentResponse beállítva lesz, with-ben benne van a set is
    private DepartmentResponse DepartmentToResponse(DepartmentEntity department) {
//        üres vizsgálni kell-e?

        DepartmentResponse departmentResponse = new DepartmentResponse();
    return departmentResponse.withDepartment(departmentConverter.convert(department));
    }

//    dpertmentRequestet convertáljuk át entityvé, h menteni lehessen
    @Transactional
    public DepartmentResponse postDepartment(DepartmentRequest departmentRequest) throws BaseException {
        if (departmentRequest == null) {
            throw new BaseException(SimplePatientConstans.PARAMETER_CANNOT_NULL_MSG);
        }
        DepartmentEntity department = departmentRepository.findDepartment(departmentRequest.getDepartment().getId());

        departmentRepository.saveDepartment(department);
        return DepartmentToResponse(department);

    }

public void deleteDepartment(String DepartmentID) throws DeleteException {
    if (StringUtils.isBlank(DepartmentID)) {
        throw new IllegalArgumentException(SimplePatientConstans.PARAMETER_CANNOT_NULL_MSG);
    }
    if(departmentRepository.findDepartment(DepartmentID) == null){
        throw new DeleteException(SimplePatientConstans.NO_DEPARTMENT_WITH_THIS_ID_MSG);
    }
    departmentRepository.deleteDepartment(DepartmentID);
}


}
