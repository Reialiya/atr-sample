package hu.icellmobilsoft.atr.sample.util;

public class TagNameEnum {

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
