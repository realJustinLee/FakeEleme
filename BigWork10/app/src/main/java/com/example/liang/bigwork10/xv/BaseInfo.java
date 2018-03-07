package com.example.liang.bigwork10.xv;


/**
 * @author lixin
 */
public class BaseInfo {
    protected String id;
    protected String name;
    protected boolean isSelected;

    public BaseInfo() {
        super();
    }

    public BaseInfo(String id, String name) {
        super();
        this.id = id;
        this.name = name;
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

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

}
