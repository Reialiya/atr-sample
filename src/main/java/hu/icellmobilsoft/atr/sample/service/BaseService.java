package hu.icellmobilsoft.atr.sample.service;

import java.util.Collection;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import hu.icellmobilsoft.atr.sample.exception.BaseException;
import hu.icellmobilsoft.atr.sample.util.SimplePatientConstans;

/**
 * The type Base service.
 */
@Dependent
public class BaseService {

    @PersistenceContext(unitName = "defaultPU")
    private EntityManager defaultEm;

    /**
     * Gets default entity manager.
     *
     * @return the default entity manager
     */
    @Produces
    @ApplicationScoped
    public EntityManager getDefaultEntityManager() {
        return defaultEm;
    }

    /**
     * Transaction required!
     *
     * @param <T>    the type parameter
     * @param entity entity to save
     * @return saved entity
     * @throws BaseException exception
     */
    public <T> T save(T entity) throws BaseException {
        if (entity == null) {
            throw new BaseException("entity is null!");
        }

        T savedEntity;
        try {
            savedEntity = getDefaultEntityManager().merge(entity);
            getDefaultEntityManager().flush();
            getDefaultEntityManager().refresh(savedEntity);
            return savedEntity;
        } catch (Exception e) {
            throw new BaseException(SimplePatientConstans.ENTITY_SAVE_FAILED);
        }
    }

    /**
     * Save collection.
     *
     * @param <T>        the type parameter
     * @param collection the collection
     */
    public <T> void saveCollection(Collection<T> collection) {
        collection.forEach(getDefaultEntityManager()::merge);
    }
}
