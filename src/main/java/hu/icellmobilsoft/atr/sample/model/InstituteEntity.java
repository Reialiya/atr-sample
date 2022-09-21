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

@Entity
@Table(name = "JK_INSTITUTE")
public class InstituteEntity {

    @Id
    @NotNull
    @Column(name = "ID", nullable = false, length = 30)
    private String id;

    @Column(name = "NAME", nullable = false, length = 200)
    private String name;

    @Column(name = "Department_ID")
    private String departmentId;


    @Column(name = "STATUS")
    @Enumerated (EnumType.STRING)
    private ActiveInactiveStatus status;

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getId() {
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

    public ActiveInactiveStatus getStatus() {
        return status;
    }
    public void setStatus(ActiveInactiveStatus status) {
        this.status = status;
    }

}
