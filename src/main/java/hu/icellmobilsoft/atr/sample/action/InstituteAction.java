package hu.icellmobilsoft.atr.sample.action;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;

import hu.icellmobilsoft.atr.sample.converter.InstituteConverter;
import hu.icellmobilsoft.atr.sample.exception.BaseException;
import hu.icellmobilsoft.atr.sample.exception.DeleteException;
import hu.icellmobilsoft.atr.sample.model.InstituteEntity;
import hu.icellmobilsoft.atr.sample.repository.InstituteRepository;
import hu.icellmobilsoft.atr.sample.util.SimplePatientConstans;
import hu.icellmobilsoft.dto.sample.patient.InstituteRequest;
import hu.icellmobilsoft.dto.sample.patient.InstituteResponse;

/**
 * The type Institute action.
 * 
 * @author juhaszkata
 * @version 1.0
 */
public class InstituteAction {

    @Inject
    private InstituteRepository instituteRepository;

    @Inject
    private InstituteConverter instituteConverter;

    /**
     * Gets institute.
     *
     * @param instituteID
     *            the institute id
     * @return the institute
     * @throws BaseException
     *             the base exception
     */
    public InstituteResponse getInstitute(String instituteID) throws BaseException {
        if (StringUtils.isBlank(instituteID)) {
            throw new BaseException(SimplePatientConstans.PARAMETER_CANNOT_NULL_MSG);
        }

        InstituteEntity institute = instituteRepository.findInstitute(instituteID);
        if (StringUtils.isBlank(instituteID)) {
            throw new BaseException(SimplePatientConstans.PARAMETER_CANNOT_NULL_MSG);
        }

        return instituteToResponse(institute);
    }

    /**
     * Post institute institute response.
     *
     * @param instituteRequest
     *            the institute request
     * @return the institute response
     * @throws BaseException
     *             the base exception
     */
    @Transactional
    public InstituteResponse postInstitute(InstituteRequest instituteRequest) throws BaseException {
        if (instituteRequest == null) {
            throw new BaseException(SimplePatientConstans.PARAMETER_CANNOT_NULL_MSG);
        }

        InstituteEntity instituteEntity = instituteConverter.convert(instituteRequest.getInstitute());
        instituteRepository.saveInstitute(instituteEntity);

        return instituteToResponse(instituteEntity);
    }

    /**
     * Put institute institute response.
     *
     * @param instituteRequest
     *            the institute request
     * @return the institute response
     * @throws BaseException
     *             the base exception
     */
    public InstituteResponse putInstitute(InstituteRequest instituteRequest) throws BaseException {
        if (instituteRequest == null) {
            throw new BaseException(SimplePatientConstans.PARAMETER_CANNOT_NULL_MSG);
        }

        InstituteEntity instituteEntity = instituteConverter.convert(instituteRequest.getInstitute());
        instituteRepository.updateInstitute(instituteEntity);

        return instituteToResponse(instituteEntity);

    }

    /**
     * Delete instituteResponse instituteID alapján, megvizsgáljuk van-e id és az Entity üres-e aztán töröljük id alapján az institute-t
     *
     * @param instituteID
     *            alaőján töröljük az institute-t the institute id
     * @return the institute response
     * @throws DeleteException
     *             the delete exception
     */
    public InstituteResponse deleteInstitute(String instituteID) throws DeleteException {
        if (StringUtils.isBlank(instituteID)) {
            throw new IllegalArgumentException(SimplePatientConstans.PARAMETER_CANNOT_NULL_MSG);
        }

        InstituteEntity instituteEntity = instituteRepository.findInstitute(instituteID);
        if (instituteEntity == null) {
            throw new DeleteException(SimplePatientConstans.NO_DEPARTMENT_WITH_THIS_ID_MSG);
        }
        instituteRepository.deleteInstitute(instituteID);
        return instituteToResponse(instituteEntity);
    }

    private InstituteResponse instituteToResponse(InstituteEntity institute) {
        InstituteResponse instituteResponse = new InstituteResponse();

        return instituteResponse.withInstitute(instituteConverter.convert(institute));

    }

}