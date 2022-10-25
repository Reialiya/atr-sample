package hu.icellmobilsoft.atr.sample.common;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//



import java.util.List;

public class PagingResult<T> {
    private List<T> results;
    private QueryMetaData metaData;

    public PagingResult() {
    }

    public List<T> getResults() {
        return this.results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }

    public QueryMetaData getDetails() {
        return this.metaData;
    }

    public void setDetails(QueryMetaData metaData) {
        this.metaData = metaData;
    }
}
