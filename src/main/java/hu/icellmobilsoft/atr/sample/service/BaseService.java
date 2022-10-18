package hu.icellmobilsoft.atr.sample.service;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import hu.icellmobilsoft.atr.sample.exception.BaseException;
import hu.icellmobilsoft.atr.sample.util.SimplePatientConstans;

@Dependent
public class BaseService {

    @PersistenceContext(unitName = "defaultPU")
    private EntityManager defaultEm;

    @Produces
    @ApplicationScoped
    public EntityManager getDefaultEntityManager() {
        return defaultEm;
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
            savedEntity = getDefaultEntityManager().merge(entity);
            getDefaultEntityManager().flush();
            getDefaultEntityManager().refresh(savedEntity);
            return savedEntity;
        } catch (Exception e) {
            throw new BaseException(SimplePatientConstans.ENTITY_SAVE_FAILED);
        }
    }

//    BatchService

//    protected void validateInput(Collection<?> entities) throws BaseException {
//        if (entities == null) {
//            throw new BaseException("entity is null!");
//        } else {
//            if (entities.isEmpty()) {
//            }
//
//        }
//    }
//    public <E> Map<String, Status> batchMergeNative(Collection<E> entities, Class<E> clazz) throws BaseException {
//        this.validateInput(entities);
//        if (entities.isEmpty()) {
//            return Collections.emptyMap();
//        } else {
//            List<E> insert = (List)entities.stream().filter((e) -> {
//                return this.getId(e) == null;
//            }).collect(Collectors.toList());
//            List<E> update = (List)entities.stream().filter((e) -> {
//                return this.getId(e) != null;
//            }).collect(Collectors.toList());
//            Map<String, Status> mergeResult = new HashMap();
//            mergeResult.putAll(this.batchInsertNative(insert, clazz));
//            mergeResult.putAll(this.batchUpdateNative(update, clazz));
//            return mergeResult;
//        }
//    }
}
