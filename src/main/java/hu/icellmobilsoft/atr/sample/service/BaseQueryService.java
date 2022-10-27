package hu.icellmobilsoft.atr.sample.service;

import org.apache.commons.collections4.CollectionUtils;

import javax.enterprise.context.Dependent;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;
import java.util.Collection;
import java.util.List;

@Dependent
public class BaseQueryService<T> extends BaseService {

    protected void addInPredicate(Collection<?> incomingParam, SingularAttribute<? super T, ?> toInspectParam, Root<T> root,
                                  List<Predicate> predicates) {
        if (CollectionUtils.isNotEmpty(incomingParam)) {
            predicates.add(root.get(toInspectParam).in(incomingParam));
        }
    }
}
