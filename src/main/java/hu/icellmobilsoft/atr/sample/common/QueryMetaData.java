package hu.icellmobilsoft.atr.sample.common;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//
import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
        name = "QueryMetaData",
        propOrder = {"totalRows", "maxPage", "rows", "page"}
)
public class QueryMetaData {
    @XmlElement(
            required = true
    )
    protected BigInteger totalRows;
    @XmlElement(
            required = true
    )
    protected BigInteger maxPage;
    @XmlElement(
            required = true
    )
    protected BigInteger rows;
    @XmlElement(
            required = true
    )
    protected BigInteger page;

    public QueryMetaData() {
    }

    public BigInteger getTotalRows() {
        return this.totalRows;
    }

    public void setTotalRows(BigInteger value) {
        this.totalRows = value;
    }

    public BigInteger getMaxPage() {
        return this.maxPage;
    }

    public void setMaxPage(BigInteger value) {
        this.maxPage = value;
    }

    public BigInteger getRows() {
        return this.rows;
    }

    public void setRows(BigInteger value) {
        this.rows = value;
    }

    public BigInteger getPage() {
        return this.page;
    }

    public void setPage(BigInteger value) {
        this.page = value;
    }
}

