package hu.icellmobilsoft.atr.sample.rest;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

import hu.icellmobilsoft.atr.sample.action.DepartmentAction;
import hu.icellmobilsoft.atr.sample.exception.BaseException;
import hu.icellmobilsoft.dto.sample.patient.DepartmentRequest;
import hu.icellmobilsoft.dto.sample.patient.DepartmentResponse;
import javassist.NotFoundException;

/**
 * The type Department rest.
 * @author juhaszkata
 */
@Model
public class DepartmentRest implements IDepartmentRest{
    @Inject
    private DepartmentAction departmentAction;

    @Override
    public DepartmentResponse getDepartment(String id) throws BaseException, NotFoundException {
        return departmentAction.getDepartment(id);
    }

    @Override
    public DepartmentResponse postDepartment(DepartmentRequest departmentRequest) throws BaseException {
        return departmentAction.postDepartment(departmentRequest);
    }

    @Override
    public DepartmentResponse putDepartment(DepartmentRequest departmentRequest, String id) throws BaseException {
        return departmentAction.putDepartment(departmentRequest, id);
    }

    @Override
    public DepartmentResponse deleteDepartment(String departmentID) throws BaseException {
        return departmentAction.deleteDepartment(departmentID);
    }



}
