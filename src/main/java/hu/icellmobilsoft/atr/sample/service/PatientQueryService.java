package hu.icellmobilsoft.atr.sample.service;

import hu.icellmobilsoft.atr.sample.common.PagingResult;
import hu.icellmobilsoft.atr.sample.common.PagingUtil;
import hu.icellmobilsoft.atr.sample.model.PatientEntity;
import hu.icellmobilsoft.atr.sample.model.PatientEntity_;
import hu.icellmobilsoft.atr.sample.util.ActiveInactiveStatus;
import hu.icellmobilsoft.atr.sample.util.EnumUtil;
import hu.icellmobilsoft.atr.sample.util.SQLUtil;
import hu.icellmobilsoft.atr.sample.util.SimplePatientConstans;
import hu.icellmobilsoft.dto.sample.common.OrderByTypeType;
import hu.icellmobilsoft.dto.sample.common.QueryRequestDetails;
import hu.icellmobilsoft.dto.sample.patient.PatientQueryOrderType;
import hu.icellmobilsoft.dto.sample.patient.PatientQueryParamsType;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The type patient service.
 */
@Model
public class PatientQueryService extends BaseService {

    @Inject
    private EntityManager entityManager;


    // queryorders kívülről kapom meg az értékét, korábban be van állítva

    public PagingResult<PatientEntity> findByQueryParam(PatientQueryParamsType queryParams,
                                                        List<PatientQueryOrderType> queryOrders, QueryRequestDetails paginationParams) {
        if (queryParams == null || paginationParams == null) {
            throw new IllegalArgumentException(SimplePatientConstans.PARAMETER_CANNOT_NULL_MSG);
        }

        //queryparams = where, queryOrders= ordersby, countQuery számolós vagy nem
        TypedQuery<PatientEntity> query = createpatientEntityQuery(queryParams, queryOrders, false, PatientEntity.class);
        TypedQuery<Long> countQuery = createpatientEntityQuery(queryParams, queryOrders, true, Long.class);

        return PagingUtil.getPagingResult(query, countQuery.getSingleResult(), paginationParams.getPage(), paginationParams.getRows());
    }


    private <T> TypedQuery<T> createpatientEntityQuery(PatientQueryParamsType queryParams, List<PatientQueryOrderType> queryOrders, boolean countQuery,
                                                       Class<T> rootClass) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(rootClass);
        Root<PatientEntity> root = query.from(PatientEntity.class);

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

    private void addQueryFilters(PatientQueryParamsType queryParams, CriteriaBuilder builder, Root<PatientEntity> root, ArrayList<Predicate> predicates) {
        if (CollectionUtils.isNotEmpty(queryParams.getId())) {
            SQLUtil.buildCriteriaInClause(builder, root.get(PatientEntity_.id), queryParams.getId(), predicates);
        }

        if (StringUtils.isNotBlank(queryParams.getName())) {
            predicates.add(builder.like(builder.lower(root.get(PatientEntity_.name)), queryParams.getName().toLowerCase()));
        }

        if (StringUtils.isNotBlank(queryParams.getEmail())) {
            predicates.add(builder.like(root.get(PatientEntity_.EMAIL), queryParams.getEmail().toLowerCase()));
        }

        if (StringUtils.isNotBlank(queryParams.getUsername())) {
            predicates.add(builder.like(root.get(PatientEntity_.USERNAME), queryParams.getUsername().toLowerCase()));
        }

        if (StringUtils.isNotBlank(queryParams.getDepartmentId())) {
            predicates.add(builder.like(root.get(PatientEntity_.DEPARTMENT_ID), queryParams.getDepartmentId().toLowerCase()));
        }

        if (StringUtils.isNotBlank(queryParams.getInstituteId())) {
            predicates.add(builder.like(root.get(PatientEntity_.INSTITUTE_ID), queryParams.getInstituteId().toLowerCase()));
        }
        if (queryParams.getStatus() != null) {
            predicates.add(builder.equal(root.get(PatientEntity_.status), EnumUtil.convert(queryParams.getStatus(), ActiveInactiveStatus.class)));
        }
    }

    private List<Order> createOrdering(List<PatientQueryOrderType> queryOrders, CriteriaBuilder builder, Root<PatientEntity> root) {
        List<Order> os = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(queryOrders)) {
            for (PatientQueryOrderType order : queryOrders) {
                Path<?> attr = null;
                switch (order.getOrder()) {
                    case ID:
                        attr = root.get(PatientEntity_.id);
                        break;
                    case NAME:
                        attr = root.get(PatientEntity_.name);
                        break;
                    case EMAIL:
                        attr = root.get(PatientEntity_.email);
                        break;
                    case USERNAME:
                        attr = root.get(PatientEntity_.username);
                        break;
                    case DEPARTMENT_ID:
                        attr = root.get(PatientEntity_.departmentId);
                        break;
                    case INSTITUTE_ID:
                        attr = root.get(PatientEntity_.instituteId);
                        break;
                    case STATUS:
                        attr = root.get(PatientEntity_.status);

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
        os.add(builder.asc(root.get(PatientEntity_.id)));
        return os;
    }
}
