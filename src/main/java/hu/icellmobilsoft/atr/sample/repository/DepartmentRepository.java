package hu.icellmobilsoft.atr.sample.repository;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import hu.icellmobilsoft.atr.sample.model.DepartmentEntity;
import hu.icellmobilsoft.atr.sample.util.PersistenceHelper;
import hu.icellmobilsoft.atr.sample.util.SimplePatientConstans;

@Model
public class DepartmentRepository {

    @Inject
    PersistenceHelper persistenceHelper;

    ArrayList<DepartmentEntity> departments = new ArrayList<>();

    public DepartmentEntity findDepartment(String id) {
        if (StringUtils.isBlank(id)) {
            throw new IllegalArgumentException(SimplePatientConstans.PARAMETER_CANNOT_NULL_MSG);
        }
        return persistenceHelper.getEntityManager().find(DepartmentEntity.class, id);
    }

    public void saveDepartment(DepartmentEntity department) {
        if (department == null) {
            throw new IllegalArgumentException(SimplePatientConstans.PARAMETER_CANNOT_NULL_MSG);
        }
        DepartmentEntity existingDepartment = findDepartment(department.getId());

        if (existingDepartment != null) {
            existingDepartment.setId(department.getId());
            existingDepartment.setName(department.getName());
        } else {
            persistenceHelper.getEntityManager().persist(department);
        }
    }

//    státusszal kiegészítve később
    public void deleteDepartment(String id) {
        if (StringUtils.isBlank(id)) {
            throw new IllegalArgumentException(SimplePatientConstans.PARAMETER_CANNOT_NULL_MSG);
        }
        DepartmentEntity findDep = findDepartment(id);
        if (findDep == null) {
            throw new NoSuchElementException(SimplePatientConstans.NO_TICKET_WITH_THIS_ID_MSG);
        }
        persistenceHelper.getEntityManager().remove(findDep);

    }

}
