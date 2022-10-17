package hu.icellmobilsoft.atr.sample.action;

import javax.enterprise.inject.Model;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.deltaspike.jpa.api.transaction.Transactional;

import hu.icellmobilsoft.atr.sample.converter.DepartmentConverter;
import hu.icellmobilsoft.atr.sample.exception.BaseException;
import hu.icellmobilsoft.atr.sample.exception.DeleteException;
import hu.icellmobilsoft.atr.sample.model.DepartmentEntity;
import hu.icellmobilsoft.atr.sample.service.DepartmentService;
import hu.icellmobilsoft.atr.sample.util.ActiveInactiveStatus;
import hu.icellmobilsoft.atr.sample.util.RandomUtil;
import hu.icellmobilsoft.atr.sample.util.SimplePatientConstans;
import hu.icellmobilsoft.dto.sample.patient.DepartmentRequest;
import hu.icellmobilsoft.dto.sample.patient.DepartmentResponse;
import javassist.NotFoundException;

/**
 * The type Department action.
 */
@Model
public class DepartmentAction {

//    @Inject
//    private DepartmentRepository departmentRepository;

    @Inject
    private DepartmentService departmentService;

    @Inject
    private DepartmentConverter departmentConverter;

    // Boolean vs boolean utánaolvasás
    public boolean isIdBlank(DepartmentRequest departmentRequest) {
        if (departmentRequest == null) {
            return true;
        }
        return StringUtils.isBlank(departmentRequest.getDepartment().getId());
    }

    /**
     * Gets department.
     *
     * @param departmentID
     *            the department id
     * @return the departmentID alapján visszatérünk az entity
     * @throws BaseException
     *             the base exception
     */
    public DepartmentResponse getDepartment(String departmentID) throws BaseException, NotFoundException {
        if (StringUtils.isBlank(departmentID)) {
            throw new BaseException(SimplePatientConstans.PARAMETER_CANNOT_NULL_MSG);
        }

        DepartmentEntity department = departmentService.findDepartment(departmentID);
        if (department == null) {
            throw new NotFoundException("nincs ilyen department!");
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
        if (isIdBlank(departmentRequest)) {
            throw new BaseException(SimplePatientConstans.PARAMETER_CANNOT_NULL_MSG);
        }

        DepartmentEntity departmentEntity = departmentConverter.convert(departmentRequest.getDepartment());
        departmentEntity.setId(RandomUtil.generateId());
        departmentEntity.setStatus(ActiveInactiveStatus.ACTIVE);

    //  departmentService.saveDepartment(departmentEntity);
        CDI.current().select(DepartmentAction.class).get().saveDepartment(departmentEntity);

        return departmentToResponse(departmentEntity);
    }

    @Transactional
    public void saveDepartment(DepartmentEntity departmentEntity) throws BaseException {

        departmentService.saveDepartment(departmentEntity);
    }

    // id generálva, ne küldjön id-t a requestben, status állítjuk-> deletenél lesz módosítva, update name
    //
    public DepartmentResponse putDepartment(DepartmentRequest departmentRequest, String id) throws BaseException {
        //        megvizsgáljuk a departmentRequestet, hogy mit küld magában, pl id-t és ha igen hibát dobunk, ha nem küldött akkor oké
        if (departmentRequest == null) {
            throw new BaseException(SimplePatientConstans.PARAMETER_CANNOT_NULL_MSG);
        }

//        if(departmentRequest.getDepartment().getId() != null){
//            throw new BaseException("a bejövő adatok között szerepel id");
//        }

        //        findByID departmententityt ad vissza, a paraméterben lévő id alapján

        DepartmentEntity departmentEntity = departmentService.findDepartment(id);
        if (departmentEntity == null){
            throw new BaseException(SimplePatientConstans.ENTITY_DOES_NOT_EXIST_MSG);
        }
        departmentConverter.convert(departmentRequest.getDepartment(), departmentEntity);
//        departmentService.saveDepartment(departmentEntity);
        CDI.current().select(DepartmentAction.class).get().saveDepartment(departmentEntity);

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

        DepartmentEntity departmentEntity = departmentService.findDepartment(departmentID);
        if (departmentEntity == null) {
            throw new DeleteException(SimplePatientConstans.NO_DEPARTMENT_WITH_THIS_ID_MSG);
        }

        departmentEntity.setStatus(ActiveInactiveStatus.INACTIVE);
        // status állítás, mentés
        return departmentToResponse(departmentEntity);
    }

      // DepartmentResponse beállítva lesz, with-ben benne van a set is
    private DepartmentResponse departmentToResponse(DepartmentEntity department) {

        DepartmentResponse departmentResponse = new DepartmentResponse();
        return departmentResponse.withDepartment(departmentConverter.convert(department));
    }

}
