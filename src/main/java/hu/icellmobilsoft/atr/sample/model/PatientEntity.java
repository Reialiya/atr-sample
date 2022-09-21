package hu.icellmobilsoft.atr.sample.model;
import java.lang.String;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sun.istack.NotNull;
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
    @Column(name = "Department_ID")
    private String departmentId;

    @Column(name = "STATUS")
    @Enumerated (EnumType.STRING)
    private ActiveInactiveStatus status;

    public PatientEntity(String id, String name, String email, String username, String instituteId, String departmentId) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.username = username;
        this.instituteId = instituteId;
        this.departmentId = departmentId;
    }

    public PatientEntity() {

    }

    public java.lang.String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getInstituteId() {
        return instituteId;
    }

    public void setInstituteId(String string) {
        this.instituteId = string;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String department) {
        this.departmentId = department;
    }

    public void setStatus(ActiveInactiveStatus status) {
        this.status = status;
    }

    public ActiveInactiveStatus getStatus() {
        return status;
    }

}
