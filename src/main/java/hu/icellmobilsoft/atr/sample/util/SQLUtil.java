package hu.icellmobilsoft.atr.sample.util;


import com.google.common.collect.Iterables;
import hu.icellmobilsoft.atr.sample.common.PagingResult;
import hu.icellmobilsoft.atr.sample.common.QueryMetaData;
import hu.icellmobilsoft.dto.sample.common.QueryResponseDetails;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * DB kezelessel kapcsolatos util
 *
 * @author laszlo.padar
 * @since 0.1.0
 */
public final class SQLUtil {

    public static final int PARTITION_MAX_SIZE = 1000;

    /**
     * Kitolti a lapozos query valaszban kötelezoen benne levo lapozasi detailt
     *
     * @param queryDetails {@link QueryMetaData}
     * @return {@link QueryResponseDetails}
     */
    public static QueryResponseDetails createResponseDetails(QueryMetaData queryDetails) {
        QueryResponseDetails details = new QueryResponseDetails();
        details.setMaxPage(queryDetails.getMaxPage().intValue());
        details.setTotalRows(queryDetails.getTotalRows().intValue());
        details.setPage(queryDetails.getPage().intValue());
        details.setRows(queryDetails.getRows().intValue());
        return details;
    }

    /**
     * IN szures feltetel tagolas a Criteria Api szamara.
     * 1000 darabos blokkokat kepez OR-al osszefuzve.
     *
     * @param <T>
     * @param <Y>
     * @param builder    Criteria builder
     * @param path       Szurendo entitas path
     * @param ids        Szuresi ertekek
     * @param predicates Predicate gyujto lista
     */
    public static <T, Y> void buildCriteriaInClause(CriteriaBuilder builder, Path<Y> path, Collection<T> ids, List<Predicate> predicates) {
        Iterable<List<T>> it = getIdFragmentIterator(ids);
        List<Predicate> tempPredicateList = new ArrayList<>();
        for (List<T> fragment : it) {
            Predicate inPredicate = path.in(fragment);
            tempPredicateList.add(inPredicate);
        }
        predicates.add(builder.or(tempPredicateList.toArray(new Predicate[tempPredicateList.size()])));
    }

    /**
     * PagingResult létrehozása default értékekkel
     *
     * @param <T> pagingResult típusa
     * @return {@link PagingResult}
     */
    public static <T> PagingResult<T> createDefaultPagingResult() {
        PagingResult<T> pagingResult = new PagingResult<>();
        QueryMetaData queryMetaData = new QueryMetaData();
        queryMetaData.setMaxPage(BigInteger.ZERO);
        queryMetaData.setTotalRows(BigInteger.ZERO);
        queryMetaData.setPage(BigInteger.ONE);
        queryMetaData.setRows(BigInteger.ZERO);
        pagingResult.setDetails(queryMetaData);
        return pagingResult;
    }

    private static <T> Iterable<List<T>> getIdFragmentIterator(Collection<T> ids) {
        return Iterables.partition(ids, PARTITION_MAX_SIZE);
    }

}
