package hu.icellmobilsoft.atr.sample.service;

import hu.icellmobilsoft.atr.sample.common.PagingResult;
import hu.icellmobilsoft.atr.sample.common.PagingUtil;
import hu.icellmobilsoft.atr.sample.exception.BaseException;
import hu.icellmobilsoft.atr.sample.model.DepartmentEntity;

import hu.icellmobilsoft.atr.sample.util.SQLUtil;
import hu.icellmobilsoft.atr.sample.util.SimplePatientConstans;
import hu.icellmobilsoft.dto.sample.common.OrderByTypeType;
import hu.icellmobilsoft.dto.sample.common.QueryRequestDetails;
import hu.icellmobilsoft.dto.sample.department.DepartmentQueryOrderType;
import hu.icellmobilsoft.dto.sample.department.DepartmentQueryParamsType;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;


import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The type Department service.
 */
@Model
public class DepartmentQueryService extends BaseService {

    @Inject
    private EntityManager entityManager;


    public PagingResult<DepartmentEntity> findByQueryParam(DepartmentQueryParamsType queryParams,
                                                           List<DepartmentQueryOrderType> queryOrders, QueryRequestDetails paginationParams) {
       // Condition.notNull(queryParams, "queryParams");
        //Condition.notNull(paginationParams, "paginationParams");


        //queryparams = where, queryOrders
            TypedQuery<DepartmentEntity> query = createDepartmentEntityQuery(queryParams, queryOrders, false, DepartmentEntity.class);
            TypedQuery<Long> countQuery = createDepartmentEntityQuery(queryParams, queryOrders, true, Long.class);
            return PagingUtil.getPagingResult(query, countQuery.getSingleResult(), paginationParams.getPage(), paginationParams.getRows());


    }
    private <T> TypedQuery<T> createDepartmentEntityQuery(DepartmentQueryParamsType queryParams, List<DepartmentQueryOrderType> queryOrders, boolean countQuery,
                                                     Class<T> rootClass) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(rootClass);
        Root<DepartmentEntity> root = query.from(DepartmentEntity.class);

        ArrayList<Predicate> predicates = new ArrayList<>();
        addQueryFilters(queryParams, builder, root, predicates);

        if (countQuery) {
            query.select((Selection<? extends T>) builder.countDistinct(root));
        } else {
            query.select((Selection<? extends T>) root);
            query.distinct(true);
            List<Order> os = createOrdering(queryOrders, builder, root);
            query.orderBy(os);
        }
        query.where(builder.and(predicates.toArray(new Predicate[0])));
        return entityManager.createQuery(query);
    }

    private void addQueryFilters(DepartmentQueryParamsType queryParams, CriteriaBuilder builder, Root<DepartmentEntity> root, ArrayList<Predicate> predicates) {
        if (org.apache.commons.collections4.CollectionUtils.isNotEmpty(queryParams.getId())) {
           // SQLUtil.buildCriteriaInClause(builder, root.get(DepartmentEntity_.id), queryParams.getId(), predicates);
        }
        // likeos keresés queryParams.getName()
        // queryParams.getStatus
       // predicates.add(builder.equal(root.get(MedicalDiagnosis_.status), EnumUtil.convert(queryParams.getStatus(), ActiveInactiveStatus.class)));

    }

    private List<Order> createOrdering(List<DepartmentQueryOrderType> queryOrders, CriteriaBuilder builder, Root<DepartmentEntity> root) {
        List<Order> os = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(queryOrders)) {
            for (DepartmentQueryOrderType order : queryOrders) {
                Path<?> attr = null;
                switch (order.getOrder()) {
                    case ID:
                       // attr = root.get(DepartmentEntity_.id);
                        break;
                    case NAME:
                       // attr = root.get(DepartmentEntity_.name);
                        break;
                    // case STATUS

                }
                if (attr != null) {
                    OrderByTypeType orderType = order.getType() == null ? OrderByTypeType.ASC : order.getType();
                    Order orderBy;
                    if (orderType == OrderByTypeType.ASC) {
                        orderBy = builder.asc(attr);
                    } else {
                        orderBy = builder.desc(attr);
                    }
                    os.add(orderBy);
                }
            }
        }
       // os.add(builder.asc(root.get(DepartmentEntity_.id)));
        return os;
    }
}
