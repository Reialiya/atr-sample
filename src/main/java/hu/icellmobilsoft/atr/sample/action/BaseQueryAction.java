package hu.icellmobilsoft.atr.sample.action;

import hu.icellmobilsoft.dto.sample.common.QueryRequestDetails;

import java.util.Objects;

/**
 * Base class for all other query action class
 * 
 * @author katalin.juhasz
 * @since 0.1.0
 *
 */
public abstract class BaseQueryAction {

    /**
     * Alapértelmezett lapozás
     * 
     * @param paginationParams
     *            A kérésben érkező {@link QueryRequestDetails}
     * @return
     */
    public QueryRequestDetails defaultPaginationParams(QueryRequestDetails paginationParams) {
        if (Objects.nonNull(paginationParams)) {
            return paginationParams;
        }
        QueryRequestDetails defaultPaginationParams = new QueryRequestDetails();
        defaultPaginationParams.setPage(1);
        defaultPaginationParams.setRows(15);
        return defaultPaginationParams;
    }

}
