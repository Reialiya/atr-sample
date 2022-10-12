package hu.icellmobilsoft.atr.sample.common;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Default entityManager producer
 * 
 * @author imre.scheffer
 *
 */
@Dependent
public class EntityManagerProducer {

    @PersistenceContext(unitName = "defaultPU")
    private EntityManager defaultEm;

    @Produces
    @Dependent
    public EntityManager createDefaultEntityManager() {
        return defaultEm;
    }
}
