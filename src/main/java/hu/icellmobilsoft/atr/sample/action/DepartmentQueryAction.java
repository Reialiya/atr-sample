package hu.icellmobilsoft.atr.sample.action;

import hu.icellmobilsoft.atr.sample.common.PagingResult;
import hu.icellmobilsoft.atr.sample.converter.DepartmentConverter;
import hu.icellmobilsoft.atr.sample.exception.BaseException;
import hu.icellmobilsoft.atr.sample.model.DepartmentEntity;
import hu.icellmobilsoft.atr.sample.service.DepartmentQueryService;
import hu.icellmobilsoft.atr.sample.util.SQLUtil;
import hu.icellmobilsoft.atr.sample.util.SimplePatientConstans;
import hu.icellmobilsoft.dto.sample.common.FunctionCodeType;
import hu.icellmobilsoft.dto.sample.department.DepartmentQueryRequest;
import hu.icellmobilsoft.dto.sample.department.DepartmentQueryResponse;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

@Model
public class DepartmentQueryAction extends BaseQueryAction {

    @Inject
    private DepartmentQueryService departmentQueryService;

    @Inject
    private DepartmentConverter departmentConverter;

    public DepartmentQueryResponse query(DepartmentQueryRequest departmentQueryRequest) throws BaseException {
        if (departmentQueryRequest == null) {
            throw new BaseException(SimplePatientConstans.PARAMETER_CANNOT_NULL_MSG);
        }

        PagingResult<DepartmentEntity> pagingResult = departmentQueryService.findByQueryParam(departmentQueryRequest.getQueryParams(),
                departmentQueryRequest.getQueryOrders(), defaultPaginationParams(departmentQueryRequest.getPaginationParams()));

        return toDepartmentQueryResponse(pagingResult);
    }

    private DepartmentQueryResponse toDepartmentQueryResponse(PagingResult<DepartmentEntity> pagingResult) {
        DepartmentQueryResponse response = new DepartmentQueryResponse();

        //response osszeallitas
        for (DepartmentEntity departmentEntity : pagingResult.getResults()) {
            response.getDepartment().add(departmentConverter.convert(departmentEntity));
        }

        response.setFuncCode(FunctionCodeType.OK);
        response.setPaginationParams(SQLUtil.createResponseDetails(pagingResult.getDetails()));

        return response;
    }


}
