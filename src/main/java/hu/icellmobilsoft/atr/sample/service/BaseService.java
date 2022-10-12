package hu.icellmobilsoft.atr.sample.service;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import hu.icellmobilsoft.atr.sample.exception.BaseException;
import hu.icellmobilsoft.atr.sample.util.SimplePatientConstans;

@Dependent
public class BaseService {

    @Inject
    private EntityManager entityManager;


    /**
     * Transaction required!
     *
     * @param entity
     *            entity to save
     * @return saved entity
     * @throws BaseException
     *             exception
     */
    public <T> T save(T entity) throws BaseException {
        if (entity == null) {
            throw new BaseException("entity is null!");
        }

        T savedEntity;
        try {
            savedEntity = entityManager.merge(entity);
            entityManager.flush();
            entityManager.refresh(savedEntity);
            return savedEntity;
        } catch (Exception e) {
            throw new BaseException(SimplePatientConstans.ENTITY_SAVE_FAILED);
        }
    }
}
