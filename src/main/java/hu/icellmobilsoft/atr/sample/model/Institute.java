package hu.icellmobilsoft.atr.sample.model;

import java.util.ArrayList;


public class Institute {

    private String id;
    private String name;
    private ArrayList<String> departmentsId ;

    public Institute() {
    }

    // public Institute(String id, String name, ArrayList<Department> dep) {
    public Institute(String id, String name, ArrayList<String> departments) {
        this.id = id.trim();
        this.name = name.trim();
        this.departmentsId = departments;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addDepartments(String departmentId) {
        this.departmentsId.add(departmentId);
    }

    public String getId() {
        return id;
    }

    public ArrayList<String> getDepartments() {
        return departmentsId;
    }

    public String getName() {
        return name;
    }
}
