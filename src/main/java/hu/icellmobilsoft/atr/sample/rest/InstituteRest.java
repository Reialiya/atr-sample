package hu.icellmobilsoft.atr.sample.rest;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

import hu.icellmobilsoft.atr.sample.action.InstituteAction;
import hu.icellmobilsoft.atr.sample.action.InstituteQueryAction;
import hu.icellmobilsoft.atr.sample.exception.BaseException;
import hu.icellmobilsoft.dto.sample.department.DepartmentQueryRequest;
import hu.icellmobilsoft.dto.sample.department.DepartmentQueryResponse;
import hu.icellmobilsoft.dto.sample.institute.InstituteQueryRequest;
import hu.icellmobilsoft.dto.sample.institute.InstituteQueryResponse;
import hu.icellmobilsoft.dto.sample.institute.InstituteRequest;
import hu.icellmobilsoft.dto.sample.institute.InstituteResponse;
import javassist.NotFoundException;

@Model
public class InstituteRest implements IInstituteRest {

    @Inject
    private InstituteAction instituteAction;

    @Inject
    private InstituteQueryAction instituteQueryAction;

    @Override
    public InstituteResponse getInstitute(String id) throws BaseException, NotFoundException {
        return instituteAction.getInstitute(id);
    }

    @Override
    public InstituteResponse postInstitute(InstituteRequest instituteRequest) throws BaseException {
        return instituteAction.postInstitute(instituteRequest);

    }

    @Override
    public InstituteResponse putInstitute(InstituteRequest instituteRequest, String id) throws BaseException {
        return instituteAction.putInstitute(instituteRequest, id);
    }

    @Override
    public InstituteResponse deleteInstitute(String instituteID) throws BaseException {
        return instituteAction.deleteInstitute(instituteID);
    }

    @Override
    public InstituteQueryResponse postInstituteQuery(InstituteQueryRequest instituteQueryRequest) throws BaseException {
        return instituteQueryAction.query(instituteQueryRequest);
    }
}
