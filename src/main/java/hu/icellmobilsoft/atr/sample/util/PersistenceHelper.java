package hu.icellmobilsoft.atr.sample.util;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * The type Persistence helper.
 * 
 * @author juhaszkata
 */
public class PersistenceHelper {
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Gets entity manager.
     *
     * @return the entity manager
     */
    public EntityManager getEntityManager() {
        return entityManager;
    }
}
