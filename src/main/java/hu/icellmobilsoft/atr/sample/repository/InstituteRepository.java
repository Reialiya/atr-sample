package hu.icellmobilsoft.atr.sample.repository;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import hu.icellmobilsoft.atr.sample.model.InstituteEntity;
import hu.icellmobilsoft.atr.sample.util.PersistenceHelper;
import hu.icellmobilsoft.atr.sample.util.SimplePatientConstans;

@Model
public class InstituteRepository {
    @Inject
    private PersistenceHelper persistenceHelper;

    ArrayList<InstituteEntity> institutes = new ArrayList<>();

    public InstituteEntity findInstitute(String id) {
        if (StringUtils.isBlank(id)) {
            throw new IllegalArgumentException(SimplePatientConstans.PARAMETER_CANNOT_NULL_MSG);
        }
        return persistenceHelper.getEntityManager().find(InstituteEntity.class, id);
    }

    public void saveInstitute(InstituteEntity institute) {
        if (institute == null) {
            throw new IllegalArgumentException(SimplePatientConstans.PARAMETER_CANNOT_NULL_MSG);
        }
        InstituteEntity existingInstitute = findInstitute(institute.getId());

        if (existingInstitute != null) {
            existingInstitute.setId(institute.getId());
            existingInstitute.setName(institute.getName());
            existingInstitute.setDepartmentId(institute.getDepartmentId());
            existingInstitute.setStatus(institute.getStatus());
        } else {
            persistenceHelper.getEntityManager().persist(institute);
        }
    }

    public void updateInstitute(InstituteEntity instituteEntity) {
        if (instituteEntity == null) {
            throw new IllegalArgumentException(SimplePatientConstans.PARAMETER_CANNOT_NULL_MSG);
        }

        InstituteEntity existingInstitute = findInstitute(instituteEntity.getId());

        if (existingInstitute != null) {
            existingInstitute.setName(instituteEntity.getName());
            existingInstitute.setStatus(instituteEntity.getStatus());
            persistenceHelper.getEntityManager().persist(existingInstitute);
        }

    }

//    státusszal kiegészítve később
    public void deleteInstitute(String id) {
        if (StringUtils.isBlank(id)) {
            throw new IllegalArgumentException(SimplePatientConstans.PARAMETER_CANNOT_NULL_MSG);
        }
        InstituteEntity findInst = findInstitute(id);
        if (findInst == null) {
            throw new NoSuchElementException(SimplePatientConstans.NO_TICKET_WITH_THIS_ID_MSG);
        }
        persistenceHelper.getEntityManager().remove(findInst);

    }

}
