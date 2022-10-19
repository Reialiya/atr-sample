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
 * The type Patient entity.
 *
 * @author juhaszkata
 */
@Entity
@Table(name = "JK_PATIENT")

public class PatientEntity {

    @Id
    @NotNull
    @Column(name = "ID", nullable = false, length = 30)
    private String id;

    @Column(name = "NAME", length = 200)
    private String name;
    @NotNull
    @Column(name = "EMAIL", nullable = false, length = 200)
    private String email;
    @NotNull
    @Column(name = "USERNAME", nullable = false, length = 200)
    private String username;
    @Column(name = "INSTITUTE_ID")
    private String instituteId;
    @Column(name = "DEPARTMENT_ID")
    private String departmentId;

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
     *            the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email.
     *
     * @param email
     *            the email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets username.
     *
     * @param username
     *            the username
     */
    public void setUsername(String username) {
        this.username = username;
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
     * @param string
     *            the string
     */
    public void setInstituteId(String string) {
        this.instituteId = string;
    }

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
     * @param department
     *            the department
     */
    public void setDepartmentId(String department) {
        this.departmentId = department;
    }

    /**
     * Sets status.
     *
     * @param status
     *            the status
     */
    public void setStatus(ActiveInactiveStatus status) {
        this.status = status;
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public ActiveInactiveStatus getStatus() {
        return status;
    }

}
