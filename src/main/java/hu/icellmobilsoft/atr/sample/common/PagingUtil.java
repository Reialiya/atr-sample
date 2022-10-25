package hu.icellmobilsoft.atr.sample.common;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

public final class PagingUtil {
    public static final long DEFAULT_ROWS = 6L;

    private PagingUtil() {
    }

    public static <T> PagingResult<T> getPagingResult(TypedQuery<T> query, Long count, long page, long rows) {
        return getPagingResultAndSetQueryDetails(query, count, page, rows, new QueryMetaData());
    }

    private static <T> PagingResult<T> getPagingResultAndSetQueryDetails(TypedQuery<T> query, Long count, long page, long rows, QueryMetaData details) {
        if (query != null && details != null) {
            long currentPage = getCurrentPage(page);
            long currentRows = getCurrentRows(rows);
           PagingResult<T> result = new PagingResult();
            if (currentPage != -1L) {
                long maxCount;
                if (count != null) {
                    maxCount = count;
                } else {
                    maxCount = (long)query.getResultList().size();
                }

                long maxPage = getPageCount(maxCount, currentRows);
                long resultCount = 0L;
                List resultList;
                if (currentPage > 0L && currentPage <= maxPage) {
                    query.setMaxResults((int)currentRows);
                    query.setFirstResult((int)(currentRows * (currentPage - 1L)));
                    resultList = query.getResultList();
                    result.setResults(resultList);
                    resultCount = (long)resultList.size();
                } else if (currentPage == -1L) {
                    resultList = query.getResultList();
                    result.setResults(resultList);
                    resultCount = (long)resultList.size();
                    currentPage = 1L;
                } else {
                    result.setResults(new ArrayList());
                }

                setDetails(details, currentPage, maxCount, maxPage, resultCount, result);
            } else {
                List<T> resultList = query.getResultList();
                result.setResults(resultList);
                setDetails(details, 1L, (long)resultList.size(), 1L, (long)resultList.size(), result);
            }

            return result;
        } else {
            return null;
        }
    }

    public static <T> PagingResult<T> getPagingResult(Query query, Query countQuery, long page, long rows) {
        return getPagingResultAndSetQueryDetails(query, countQuery, page, rows, new QueryMetaData());
    }

    public static <T> PagingResult<T> getPagingResultAndSetQueryDetails(Query query, Query countQuery, long page, long rows, QueryMetaData details) {
        if (query != null && details != null) {
            long currentPage = getCurrentPage(page);
            long currentRows = getCurrentRows(rows);
            PagingResult<T> result = new PagingResult();
            if (currentPage != -1L) {
                long maxCount;
                if (countQuery != null) {
                    maxCount = ((Number)countQuery.getSingleResult()).longValue();
                } else {
                    maxCount = (long)query.getResultList().size();
                }

                long maxPage = getPageCount(maxCount, currentRows);
                long resultCount = 0L;
                if (currentPage > 0L && currentPage <= maxPage) {
                    query.setMaxResults((int)currentRows);
                    query.setFirstResult((int)(currentRows * (currentPage - 1L)));
                    List<T> resultList = query.getResultList();
                    result.setResults(resultList);
                    resultCount = (long)resultList.size();
                } else {
                    result.setResults(new ArrayList());
                }

                setDetails(details, currentPage, maxCount, maxPage, resultCount, result);
            } else {
                List<T> resultList = query.getResultList();
                result.setResults(resultList);
                setDetails(details, 1L, (long)resultList.size(), 1L, (long)resultList.size(), result);
            }

            return result;
        } else {
            return null;
        }
    }

    public static QueryMetaData createDetails(long maxCount, long resultCount, long page, long rows) {
        long currentPage = getCurrentPage(page);
        long currentRows = getCurrentRows(rows);
        long maxPage = getPageCount(maxCount, currentRows);
        QueryMetaData details = new QueryMetaData();
        details.setRows(BigInteger.valueOf(resultCount));
        details.setPage(BigInteger.valueOf(currentPage));
        details.setTotalRows(BigInteger.valueOf(maxCount));
        details.setMaxPage(BigInteger.valueOf(maxPage));
        return details;
    }

    public static long getPageCount(long sum, long rows) {
        return rows == 0L ? 0L : (long)Math.ceil((double)sum / (double)rows);
    }

    private static long getCurrentPage(long page) {
        return page == 0L ? 1L : page;
    }

    private static long getCurrentRows(long rows) {
        return rows == 0L ? 6L : rows;
    }

    private static <T> void setDetails(QueryMetaData details, long currentPage, long maxCount, long maxPage, long resultCount, PagingResult<T> result) {
        details.setRows(BigInteger.valueOf(resultCount));
        details.setPage(BigInteger.valueOf(currentPage));
        details.setTotalRows(BigInteger.valueOf(maxCount));
        details.setMaxPage(BigInteger.valueOf(maxPage));
        result.setDetails(details);
    }
}
