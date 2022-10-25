package hu.icellmobilsoft.atr.common.system.action;

import java.util.Objects;

import hu.icellmobilsoft.coffee.dto.common.common.QueryRequestDetails;

/**
 * Base class for all other query action class
 * 
 * @author karoly.tamas
 * @since 0.1.0
 *
 */
public abstract class BaseQueryAction extends BaseClientAction {

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
