package hu.icellmobilsoft.atr.sample.action;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import hu.icellmobilsoft.atr.sample.converter.DepartmentConverter;
import hu.icellmobilsoft.atr.sample.exception.BaseException;
import hu.icellmobilsoft.atr.sample.exception.DeleteException;
import hu.icellmobilsoft.atr.sample.model.DepartmentEntity;
import hu.icellmobilsoft.atr.sample.repository.DepartmentRepository;
import hu.icellmobilsoft.atr.sample.util.SimplePatientConstans;
import hu.icellmobilsoft.dto.sample.patient.DepartmentRequest;
import hu.icellmobilsoft.dto.sample.patient.DepartmentResponse;
import javassist.NotFoundException;

/**
 * The type Department action.
 */
@Model
public class DepartmentAction {

    @Inject
    private DepartmentRepository departmentRepository;

    @Inject
    private DepartmentConverter departmentConverter;

    /**
     * Gets department.
     *
     * @param departmentID
     *            the department id
     * @return the departmentID alapján visszatérünk az entity
     * @throws BaseException
     *             the base exception
     */
    // departmentID alapján az entityvel visszatérünk
    public DepartmentResponse getDepartment(String departmentID) throws BaseException, NotFoundException {
        if (StringUtils.isBlank(departmentID)) {
            throw new BaseException(SimplePatientConstans.PARAMETER_CANNOT_NULL_MSG);
        }

        DepartmentEntity department = departmentRepository.findDepartment(departmentID);

        if (department == null) {
            throw new NotFoundException("nincs ilyen adat!");
        }

        return departmentToResponse(department);
    }

    /**
     * Post department department response, convertáljuk át entetyvé a mentés miatt
     *
     * @param departmentRequest
     *            the department request
     * @return the department response
     * @throws BaseException
     *             the base exception
     */

    public DepartmentResponse postDepartment(DepartmentRequest departmentRequest) throws BaseException {
        if (departmentRequest == null) {
            throw new BaseException(SimplePatientConstans.PARAMETER_CANNOT_NULL_MSG);
        }

        DepartmentEntity departmentEntity = departmentConverter.convert(departmentRequest.getDepartment());
        departmentRepository.saveDepartment(departmentEntity);

        return departmentToResponse(departmentEntity);
    }

    public DepartmentResponse putDepartment(DepartmentRequest departmentRequest) throws BaseException {
        if (departmentRequest == null) {
            throw new BaseException(SimplePatientConstans.PARAMETER_CANNOT_NULL_MSG);
        }
        DepartmentEntity departmentEntity = departmentConverter.convert(departmentRequest.getDepartment());
        departmentRepository.updateDepartment(departmentEntity);

        return departmentToResponse(departmentEntity);
    }

    /**
     * Paraméterként kapott departmentID alapján törli a repoból a departmentet és visszatér depEntityvel
     *
     * @param departmentID
     *            the department id
     * @return the department response
     * @throws DeleteException
     *             the delete exception
     */
    public DepartmentResponse deleteDepartment(String departmentID) throws DeleteException {
        if (StringUtils.isBlank(departmentID)) {
            throw new IllegalArgumentException(SimplePatientConstans.PARAMETER_CANNOT_NULL_MSG);
        }

        DepartmentEntity departmentEntity = departmentRepository.findDepartment(departmentID);
        if (departmentEntity == null) {
            throw new DeleteException(SimplePatientConstans.NO_DEPARTMENT_WITH_THIS_ID_MSG);
        }
        departmentRepository.deleteDepartment(departmentID);
        return departmentToResponse(departmentEntity);
    }

    // DepartmentResponse beállítva lesz, with-ben benne van a set is
    private DepartmentResponse departmentToResponse(DepartmentEntity department) {

        DepartmentResponse departmentResponse = new DepartmentResponse();
        return departmentResponse.withDepartment(departmentConverter.convert(department));
    }

}
