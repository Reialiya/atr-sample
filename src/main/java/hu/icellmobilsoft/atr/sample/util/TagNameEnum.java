package hu.icellmobilsoft.atr.sample.util;

public enum TagNameEnum {

    SAMPLE("sample"),
    ID ("id"),
    NAME ("name"),
    EMAIL("email"),
    USERNAME("username"),
    STATUS("status"),
    TYPE("type"),
    DEPARTMENT("department"),
    DEPARTMENTS("departments"),
    INSTITUTE("institute"),
    INSTITUTES("institutes"),
    PATIENT("patient"),
    PATIENTS("patients");
//    DESCRIPTION("description");


    private String tagname;

    TagNameEnum(String tagname){
        this.setTagname(tagname);
    }

    public String getTagName() {
        return tagname;
    }

    public void setTagname(String tagname) {
        this.tagname = tagname;
    }
}
