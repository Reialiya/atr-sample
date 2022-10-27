package hu.icellmobilsoft.atr.sample.action;

import hu.icellmobilsoft.atr.sample.common.PagingResult;
import hu.icellmobilsoft.atr.sample.converter.InstituteConverter;
import hu.icellmobilsoft.atr.sample.exception.BaseException;
import hu.icellmobilsoft.atr.sample.model.InstituteEntity;
import hu.icellmobilsoft.atr.sample.service.InstituteQueryService;
import hu.icellmobilsoft.atr.sample.util.SQLUtil;
import hu.icellmobilsoft.atr.sample.util.SimplePatientConstans;
import hu.icellmobilsoft.dto.sample.common.FunctionCodeType;
import hu.icellmobilsoft.dto.sample.institute.InstituteQueryRequest;
import hu.icellmobilsoft.dto.sample.institute.InstituteQueryResponse;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

@Model
public class InstituteQueryAction extends BaseQueryAction {

    @Inject
    private InstituteQueryService instituteQueryService;

    @Inject
    private InstituteConverter instituteConverter;

    public InstituteQueryResponse query(InstituteQueryRequest InstituteQueryRequest) throws BaseException {
        if (InstituteQueryRequest == null) {
            throw new BaseException(SimplePatientConstans.PARAMETER_CANNOT_NULL_MSG);
        }

        PagingResult<InstituteEntity> pagingResult = instituteQueryService.findByQueryParam(InstituteQueryRequest.getQueryParams(),
                InstituteQueryRequest.getQueryOrders(), defaultPaginationParams(InstituteQueryRequest.getPaginationParams()));

        return toInstituteQueryResponse(pagingResult);
    }

    private InstituteQueryResponse toInstituteQueryResponse(PagingResult<InstituteEntity> pagingResult) {
        InstituteQueryResponse response = new InstituteQueryResponse();

        //response osszeallitas
        for (InstituteEntity instituteEntity : pagingResult.getResults()) {
            response.getInstitute().add(instituteConverter.convert(instituteEntity));
        }

        response.setFuncCode(FunctionCodeType.OK);
        response.setPaginationParams(SQLUtil.createResponseDetails(pagingResult.getDetails()));

        return response;
    }


}
