package hu.icellmobilsoft.atr.common.system.jpa.util;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

import org.hibernate.Session;
import org.hibernate.engine.spi.SessionImplementor;

import com.google.common.collect.Iterables;

import hu.icellmobilsoft.coffee.dto.common.common.QueryResponseDetails;
import hu.icellmobilsoft.coffee.dto.exception.BaseException;
import hu.icellmobilsoft.coffee.dto.exception.TechnicalException;
import hu.icellmobilsoft.coffee.dto.exception.enums.CoffeeFaultType;
import hu.icellmobilsoft.coffee.jpa.sql.paging.PagingResult;
import hu.icellmobilsoft.coffee.jpa.sql.paging.QueryMetaData;

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
     * @param queryDetails
     *            {@link QueryMetaData}
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
     * JPA query szövegeben megcsinalja az IN szures feltetel tagolast az ORA-01795 elkerulese vegett. pelda: where name in ('value1', 'value2', ...,
     * 'value999') or name in ('value1000', ..., 'value1999') or ...
     * 
     * @param <T>
     * @param columnName
     *            Oszlop neve, amire szurunk
     * @param ids
     *            Szuresi ertekek
     * @return
     */
    public static <T> String writeInClause(String columnName, Collection<T> ids) {
        StringBuilder sb = new StringBuilder(" (");
        Iterable<List<T>> it = getIdFragmentIterator(ids);
        int i = 0;
        for (List<T> fragment : it) {
            if (i != 0) {
                sb.append(" OR ");
            }
            sb.append(columnName).append(" IN :").append(getVariableFromColumnName(columnName, i++));
        }
        sb.append(") ");
        return sb.toString();
    }

    /**
     * Beilleszti a változokat az IN feltetelbe, amit a {@link SQLUtil#writeInClause} allitott ossze elozoleg. A {@link SQLUtil#writeInClause} és az
     * {@link SQLUtil#bindInClause} argumentumai megg kell egyezzenek!
     * 
     * @param <T>
     * @param query
     *            JPA query
     * @param columnName
     *            Oszlop neve, amire szurunk
     * @param ids
     *            Szuresi ertekek
     */
    public static <T> void bindInClause(Query query, String columnName, Collection<T> ids) {
        Iterable<List<T>> it = getIdFragmentIterator(ids);
        int i = 0;
        for (Collection<T> fragment : it) {
            query.setParameter(getVariableFromColumnName(columnName, i++), fragment);
        }
    }

    /**
     * IN szures feltetel tagolas a Criteria Api szamara. Logika megegyezik a {@link SQLUtil#writeInClause} -al, de Predicate listaban megvalositva.
     * 1000 darabos blokkokat kepez OR-al osszefuzve.
     * 
     * @param <T>
     * @param <Y>
     * @param builder
     *            Criteria builder
     * @param path
     *            Szurendo entitas path
     * @param ids
     *            Szuresi ertekek
     * @param predicates
     *            Predicate gyujto lista
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

    /**
     * Get connection from JPA hibernate<br>
     * <br>
     * Hogyha kell allitani az autocommit-ot akkor azt csak Transactional
     * annotacioval a metodusnal, egyebkent elszalas van! <br>
     * <br>
     * Pelda:
     * 
     * <pre>
     * con = SQLUtil.getJpaConnection(new AccountJndiQualifier());
     * </pre>
     * 
     * @param entityManager
     *            entityManager qualifier
     * @return JDBC connection
     * @throws BaseException
     *             exception
     */
    public static Connection getJpaConnection(EntityManager entityManager) throws BaseException {
        if (entityManager == null) {
            return null;
        }
        try {
            return getJPAConnectionMain(entityManager);
        } catch (Exception e) {
            String msg = "Error in get JPA connection from entityManager";
            throw new TechnicalException(CoffeeFaultType.OPERATION_FAILED, msg, e);
        }
    }

    private static Connection getJPAConnectionMain(EntityManager entityManager) throws SQLException {
        Session session = entityManager.unwrap(Session.class);
        SessionImplementor sessionImplementor = (SessionImplementor) session.getSessionFactory().getCurrentSession();
        Connection conn = sessionImplementor.getJdbcConnectionAccess().obtainConnection();
        return conn;
    }

    public static void close(final Connection con) throws BaseException {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            throw new TechnicalException(CoffeeFaultType.OPERATION_FAILED, "Connection close error");
        }
    }

    public static void close(final Statement stmt) throws BaseException {
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
            throw new TechnicalException(CoffeeFaultType.OPERATION_FAILED, "Statement close error");
        }
    }
    private static <T> Iterable<List<T>> getIdFragmentIterator(Collection<T> ids) {
        return Iterables.partition(ids, PARTITION_MAX_SIZE);
    }

    private static String getVariableFromColumnName(String columnName, int counter) {
        return columnName.replaceAll("\\.", "_") + counter;
    }
}
