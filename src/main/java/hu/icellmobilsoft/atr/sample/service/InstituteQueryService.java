package hu.icellmobilsoft.atr.sample.service;

import hu.icellmobilsoft.atr.sample.common.PagingResult;
import hu.icellmobilsoft.atr.sample.common.PagingUtil;
import hu.icellmobilsoft.atr.sample.model.InstituteEntity;
import hu.icellmobilsoft.atr.sample.model.InstituteEntity_;
import hu.icellmobilsoft.atr.sample.util.ActiveInactiveStatus;
import hu.icellmobilsoft.atr.sample.util.EnumUtil;
import hu.icellmobilsoft.atr.sample.util.SQLUtil;
import hu.icellmobilsoft.atr.sample.util.SimplePatientConstans;
import hu.icellmobilsoft.dto.sample.common.OrderByTypeType;
import hu.icellmobilsoft.dto.sample.common.QueryRequestDetails;
import hu.icellmobilsoft.dto.sample.institute.InstituteQueryOrderType;
import hu.icellmobilsoft.dto.sample.institute.InstituteQueryParamsType;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The type Institute service.
 */
@Model
public class InstituteQueryService extends BaseService {

    @Inject
    private EntityManager entityManager;


    // queryorders kívülről kapom meg az értékét, korábban be van állítva

    public PagingResult<InstituteEntity> findByQueryParam(InstituteQueryParamsType queryParams,
                                                          List<InstituteQueryOrderType> queryOrders, QueryRequestDetails paginationParams) {
        if (queryParams == null || paginationParams == null) {
            throw new IllegalArgumentException(SimplePatientConstans.PARAMETER_CANNOT_NULL_MSG);
        }

        //queryparams = where, queryOrders= ordersby, countQuery számolós vagy nem
        TypedQuery<InstituteEntity> query = createInstituteEntityQuery(queryParams, queryOrders, false, InstituteEntity.class);
        TypedQuery<Long> countQuery = createInstituteEntityQuery(queryParams, queryOrders, true, Long.class);

        return PagingUtil.getPagingResult(query, countQuery.getSingleResult(), paginationParams.getPage(), paginationParams.getRows());
    }


    private <T> TypedQuery<T> createInstituteEntityQuery(InstituteQueryParamsType queryParams, List<InstituteQueryOrderType> queryOrders, boolean countQuery,
                                                         Class<T> rootClass) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(rootClass);
        Root<InstituteEntity> root = query.from(InstituteEntity.class);

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

    private void addQueryFilters(InstituteQueryParamsType queryParams, CriteriaBuilder builder, Root<InstituteEntity> root, ArrayList<Predicate> predicates) {
        if (CollectionUtils.isNotEmpty(queryParams.getId())) {
            SQLUtil.buildCriteriaInClause(builder, root.get(InstituteEntity_.ID), queryParams.getId(), predicates);
        }

        if (StringUtils.isNotBlank(queryParams.getName())) {
            predicates.add(builder.like(root.get(InstituteEntity_.NAME), queryParams.getName().toLowerCase()));
        }

        if (StringUtils.isNotBlank(queryParams.getDepartmentId())) {
            predicates.add(builder.like(root.get(InstituteEntity_.DEPARTMENT_ID), queryParams.getDepartmentId().toLowerCase()));
        }

        if (StringUtils.isNotBlank(queryParams.getInstituteId())) {
            predicates.add(builder.like(root.get(InstituteEntity_.INSTITUTE_ID), queryParams.getInstituteId().toLowerCase()));
        }

        if (queryParams.getStatus() != null) {
            predicates.add(builder.equal(root.get(InstituteEntity_.STATUS), EnumUtil.convert(queryParams.getStatus(), ActiveInactiveStatus.class)));
        }
    }

    private List<Order> createOrdering(List<InstituteQueryOrderType> queryOrders, CriteriaBuilder builder, Root<InstituteEntity> root) {
        List<Order> os = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(queryOrders)) {
            for (InstituteQueryOrderType order : queryOrders) {
                Path<?> attr = null;
                switch (order.getOrder()) {
                    case ID:
                        attr = root.get(InstituteEntity_.id);
                        break;
                    case NAME:
                        attr = root.get(InstituteEntity_.name);
                        break;
                    case INSTITUTE_ID:
                        attr = root.get(InstituteEntity_.instituteId);
                        break;
                    case DEPARTMENT_ID:
                        attr = root.get(InstituteEntity_.departmentId);
                        break;
                    case STATUS:
                        attr = root.get(InstituteEntity_.status);

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
        os.add(builder.asc(root.get(InstituteEntity_.id)));
        return os;
    }
}
