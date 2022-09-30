package hu.icellmobilsoft.atr.sample.repository;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import hu.icellmobilsoft.atr.sample.exception.BaseException;
import hu.icellmobilsoft.atr.sample.util.SimplePatientConstans;

public class BaseRepository {
    @Inject
    protected
    EntityManager entityManager;

    /**
     * Gets entity manager.
     *
     * @return the entity manager
     */

    protected EntityManager getEntityManager() {
        return entityManager;
    }

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
            savedEntity = getEntityManager().merge(entity);
            getEntityManager().flush();
            getEntityManager().refresh(savedEntity);
            return savedEntity;
        } catch (Exception e) {
            throw new BaseException(SimplePatientConstans.ENTITY_SAVE_FAILED);
        }
    }
}
