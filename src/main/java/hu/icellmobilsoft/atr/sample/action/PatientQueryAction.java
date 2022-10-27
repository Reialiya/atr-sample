package hu.icellmobilsoft.atr.sample.action;

import hu.icellmobilsoft.atr.sample.common.PagingResult;
import hu.icellmobilsoft.atr.sample.converter.PatientConverter;
import hu.icellmobilsoft.atr.sample.exception.BaseException;
import hu.icellmobilsoft.atr.sample.model.PatientEntity;
import hu.icellmobilsoft.atr.sample.service.PatientQueryService;
import hu.icellmobilsoft.atr.sample.util.SQLUtil;
import hu.icellmobilsoft.atr.sample.util.SimplePatientConstans;
import hu.icellmobilsoft.dto.sample.common.FunctionCodeType;
import hu.icellmobilsoft.dto.sample.patient.PatientQueryRequest;
import hu.icellmobilsoft.dto.sample.patient.PatientQueryResponse;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

@Model
public class PatientQueryAction extends BaseQueryAction {

    @Inject
    private PatientQueryService patientQueryService;

    @Inject
    private PatientConverter patientConverter;

    public PatientQueryResponse query(PatientQueryRequest patientQueryRequest) throws BaseException {
        if (patientQueryRequest == null) {
            throw new BaseException(SimplePatientConstans.PARAMETER_CANNOT_NULL_MSG);
        }

        PagingResult<PatientEntity> pagingResult = patientQueryService.findByQueryParam(patientQueryRequest.getQueryParams(),
                patientQueryRequest.getQueryOrders(), defaultPaginationParams(patientQueryRequest.getPaginationParams()));

        return topatientQueryResponse(pagingResult);
    }

    private PatientQueryResponse topatientQueryResponse(PagingResult<PatientEntity> pagingResult) {
        PatientQueryResponse response = new PatientQueryResponse();

        //response osszeallitas
        for (PatientEntity patientEntity : pagingResult.getResults()) {
            response.getPatients().add(patientConverter.convert(patientEntity));
        }

        response.setFuncCode(FunctionCodeType.OK);
        response.setPaginationParams(SQLUtil.createResponseDetails(pagingResult.getDetails()));

        return response;
    }


}
