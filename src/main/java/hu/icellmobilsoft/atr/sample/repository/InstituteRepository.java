package hu.icellmobilsoft.atr.sample.repository;

import java.util.NoSuchElementException;

import javax.enterprise.inject.Model;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;

import hu.icellmobilsoft.atr.sample.model.InstituteEntity;
import hu.icellmobilsoft.atr.sample.util.ActiveInactiveStatus;
import hu.icellmobilsoft.atr.sample.util.PersistenceHelper;
import hu.icellmobilsoft.atr.sample.util.SimplePatientConstans;

@Model
public class InstituteRepository {
    @Inject
    private PersistenceHelper persistenceHelper;

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
        CDI.current().select(InstituteRepository.class).get().saveInst(institute);
    }

    @Transactional
    public void saveInst(InstituteEntity institute){
        persistenceHelper.getEntityManager().persist(institute);
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

    public void deleteInstitute(String id) {
        if (StringUtils.isBlank(id)) {
            throw new IllegalArgumentException(SimplePatientConstans.PARAMETER_CANNOT_NULL_MSG);
        }
        InstituteEntity findInst = findInstitute(id);
        if (findInst == null) {
            throw new NoSuchElementException(SimplePatientConstans.NO_TICKET_WITH_THIS_ID_MSG);
        }
        findInst.setStatus(ActiveInactiveStatus.INACTIVE);
        persistenceHelper.getEntityManager().remove(findInst);

    }

}
