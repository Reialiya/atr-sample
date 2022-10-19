package hu.icellmobilsoft.atr.sample.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import javax.validation.constraints.NotNull;

import hu.icellmobilsoft.atr.sample.util.ActiveInactiveStatus;

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

    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private ActiveInactiveStatus status;

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

    public ActiveInactiveStatus getStatus() {
        return status;
    }

    public void setStatus(ActiveInactiveStatus status) {
        this.status = status;
    }

}
