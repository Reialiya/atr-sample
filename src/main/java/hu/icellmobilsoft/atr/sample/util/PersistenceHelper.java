package hu.icellmobilsoft.atr.sample.util;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * The type Persistence helper.
 * 
 * @author juhaszkata
 */
public class PersistenceHelper {
    @PersistenceContext(unitName = "defaultPU")
    private EntityManager entityManager;


    /**
     * Gets entity manager.
     *
     * @return the entity manager
     */
    @Produces
    @Dependent
    public EntityManager getEntityManager() {
        return entityManager;
    }

}
