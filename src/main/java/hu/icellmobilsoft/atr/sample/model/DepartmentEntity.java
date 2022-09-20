package hu.icellmobilsoft.atr.sample.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sun.istack.NotNull;

import hu.icellmobilsoft.atr.sample.util.PatientStatusEnum;

/**
 * The type Department entity.
 * 
 * @author juhaszkata
 */
@Entity
@Table(name = "JK_DEPARTMENT")
public class DepartmentEntity {

    @Id
    @NotNull
    @Column(name = "ID", nullable = false, length = 30)
    private String id;
    @Column(name = "NAME", nullable = false, length = 200)
    private String name;

    @Column(nullable = false)
    @NotNull
    private PatientStatusEnum status;
    /**
     * Gets id.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id
     *            the id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name
     *
     */
    public void setName(String name) {
        this.name = name;
    }

    public PatientStatusEnum getStatus() {
        return status;
    }

    public void setStatus(PatientStatusEnum status) {
        this.status = status;
    }
}
