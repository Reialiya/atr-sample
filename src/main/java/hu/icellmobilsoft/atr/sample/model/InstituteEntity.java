package hu.icellmobilsoft.atr.sample.model;
import java.lang.String;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import javax.validation.constraints.NotNull;
import hu.icellmobilsoft.atr.sample.util.ActiveInactiveStatus;

/**
 * The type Institute entity.
 * @author juhaszkata
 */
@Entity
@Table(name = "JK_INSTITUTE")
public class InstituteEntity {

    @Id
    @NotNull
    @Column(name = "ID", nullable = false, length = 30)
    private String id;

    @Column(name = "NAME", nullable = false, length = 200)
    private String name;

    @Column(name = "DEPARTMENT_ID")
    private String departmentId;

    @Column(name = "INSTITUTE_ID")
    private String instituteId;

    @Column(name = "STATUS")
    @Enumerated (EnumType.STRING)
    private ActiveInactiveStatus status;

    /**
     * Gets department id.
     *
     * @return the department id
     */
    public String getDepartmentId() {
        return departmentId;
    }

    /**
     * Sets department id.
     *
     * @param departmentId the department id
     */
    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    /**
     * Gets institute id.
     *
     * @return the institute id
     */
    public String getInstituteId() {
        return instituteId;
    }

    /**
     * Sets institute id.
     *
     * @param instituteId the institute id
     */
    public void setInstituteId(String instituteId) {
        this.instituteId = instituteId;
    }

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
     * @param id the id
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
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public ActiveInactiveStatus getStatus() {
        return status;
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(ActiveInactiveStatus status) {
        this.status = status;
    }

}
