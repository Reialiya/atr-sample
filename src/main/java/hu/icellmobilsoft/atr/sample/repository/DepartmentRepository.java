package hu.icellmobilsoft.atr.sample.repository;

import org.apache.deltaspike.data.api.Repository;

//@Model
@Repository
public interface DepartmentRepository {

//    @Inject
//    private PersistenceHelper persistenceHelper;


//    @Query


//    public DepartmentEntity findDepartment(String id) {
//        if (StringUtils.isBlank(id)) {
//            throw new IllegalArgumentException(SimplePatientConstans.PARAMETER_CANNOT_NULL_MSG);
//        }
//        return persistenceHelper.getEntityManager().find(DepartmentEntity.class, id);
//    }

//    public void saveDepartment(DepartmentEntity department) {
//        if (department == null) {
//            throw new IllegalArgumentException(SimplePatientConstans.PARAMETER_CANNOT_NULL_MSG);
//        }
//        CDI.current().select(DepartmentRepository.class).get().saveDep(department);
//    }

    // Baseservice save method
//    @Transactional
//    public void saveDep(DepartmentEntity department) {
//
//        persistenceHelper.getEntityManager().persist(department);
//    }

    // public void updateDepartment(DepartmentEntity department) {
    // if (department == null) {
    // throw new IllegalArgumentException(SimplePatientConstans.PARAMETER_CANNOT_NULL_MSG);
    // }
    //
    // DepartmentEntity existingDepartment = findDepartment(department.getId());
    //
    // if (existingDepartment != null) {
    // existingDepartment.setName(department.getName());
    // existingDepartment.setStatus(department.getStatus());
    // persistenceHelper.getEntityManager().persist(existingDepartment);
    // }
    //
    // }
    //
    // public void deleteDepartment(String id) {
    // if (StringUtils.isBlank(id)) {
    // throw new IllegalArgumentException(SimplePatientConstans.PARAMETER_CANNOT_NULL_MSG);
    // }
    // DepartmentEntity findDep = findDepartment(id);
    //
    // if (findDep == null) {
    // throw new NoSuchElementException(SimplePatientConstans.NO_DEPARTMENT_WITH_THIS_ID_MSG);
    // }
    //
    // findDep.setStatus(ActiveInactiveStatus.INACTIVE);
    // persistenceHelper.getEntityManager().persist(findDep);
    //
    // }

}
