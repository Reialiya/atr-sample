package hu.icellmobilsoft.atr.sample.action;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import hu.icellmobilsoft.atr.sample.converter.InstituteConverter;
import hu.icellmobilsoft.atr.sample.exception.BaseException;
import hu.icellmobilsoft.atr.sample.exception.DeleteException;
import hu.icellmobilsoft.atr.sample.model.InstituteEntity;
import hu.icellmobilsoft.atr.sample.service.InstituteService;
import hu.icellmobilsoft.atr.sample.util.ActiveInactiveStatus;
import hu.icellmobilsoft.atr.sample.util.RandomUtil;
import hu.icellmobilsoft.atr.sample.util.SimplePatientConstans;
import hu.icellmobilsoft.dto.sample.patient.InstituteRequest;
import hu.icellmobilsoft.dto.sample.patient.InstituteResponse;
import javassist.NotFoundException;

/**
 * The type Institute action.
 *
 * @author juhaszkata
 */
public class InstituteAction {

    @Inject
    private InstituteService instituteService;

    @Inject
    private InstituteConverter instituteConverter;

    /**
     * Is id blank boolean.
     *
     * @param instituteRequest the institute request
     * @return the boolean
     */
    public Boolean isIdBlank(InstituteRequest instituteRequest) {
        if (instituteRequest == null) {
            return true;
        }
        return StringUtils.isBlank(instituteRequest.getInstitute().getId());
    }

    /**
     * Gets institute.
     *
     * @param instituteID the institute id
     * @return the institute
     * @throws BaseException     the base exception
     * @throws NotFoundException the not found exception
     */
    public InstituteResponse getInstitute(String instituteID) throws BaseException, NotFoundException {
        if (StringUtils.isBlank(instituteID)) {
            throw new BaseException(SimplePatientConstans.PARAMETER_CANNOT_NULL_MSG);
        }

        InstituteEntity institute = instituteService.findInstitute(instituteID);
        if (institute == null) {
            throw new NotFoundException("nincs ilyen institute!");
        }

        return instituteToResponse(institute);
    }

    /**
     * Post institute institute response.
     *
     * @param instituteRequest the institute request
     * @return the institute response
     * @throws BaseException the base exception
     */
    public InstituteResponse postInstitute(InstituteRequest instituteRequest) throws BaseException {
        if (isIdBlank(instituteRequest)) {
            throw new BaseException(SimplePatientConstans.PARAMETER_CANNOT_NULL_MSG);
        }

        InstituteEntity instituteEntity = instituteConverter.convert(instituteRequest.getInstitute());
        instituteEntity.setId(RandomUtil.generateId());
        instituteEntity.setStatus(ActiveInactiveStatus.ACTIVE);

        instituteService.saveInstitute(instituteEntity);

        return instituteToResponse(instituteEntity);
    }

    /**
     * Put institute institute response.
     *
     * @param instituteRequest the institute request
     * @param id               the id
     * @return the institute response
     * @throws BaseException the base exception
     */
    public InstituteResponse putInstitute(InstituteRequest instituteRequest, String id) throws BaseException {
        if (instituteRequest == null) {
            throw new BaseException(SimplePatientConstans.PARAMETER_CANNOT_NULL_MSG);
        }

        InstituteEntity instituteEntity = instituteService.findInstitute(id);
        if (instituteEntity == null) {
            throw new DeleteException(SimplePatientConstans.NO_INSTITUTE_WITH_THIS_ID_MSG);
        }

        instituteConverter.convert(instituteRequest.getInstitute());
        instituteService.saveInstitute(instituteEntity);

        return instituteToResponse(instituteEntity);
    }

    /**
     * Delete instituteResponse instituteID alapján, megvizsgáljuk van-e id és az Entity üres-e aztán töröljük id
     * alapján az institute-t
     *
     * @param instituteID alaőján töröljük az institute-t the institute id
     * @return the institute response
     * @throws DeleteException the delete exception
     */
    public InstituteResponse deleteInstitute(String instituteID) throws DeleteException {
        if (StringUtils.isBlank(instituteID)) {
            throw new IllegalArgumentException(SimplePatientConstans.PARAMETER_CANNOT_NULL_MSG);
        }

        InstituteEntity instituteEntity = instituteService.findInstitute(instituteID);
        if (instituteEntity == null) {
            throw new DeleteException(SimplePatientConstans.NO_DEPARTMENT_WITH_THIS_ID_MSG);
        }
        instituteService.deleteInstitute(instituteID);
        return instituteToResponse(instituteEntity);
    }

    private InstituteResponse instituteToResponse(InstituteEntity institute) {
        InstituteResponse instituteResponse = new InstituteResponse();

        return instituteResponse.withInstitute(instituteConverter.convert(institute));

    }

}
