package com.smartown.demo.entity;

/**
 * Author:Tiger
 * <p>
 * CrateTime:2016-11-02 17:07
 * <p>
 * Description:
 */
public class SimpleListItem {

    private String name;
    private Class aClass;

    public SimpleListItem(String name, Class aClass) {
        this.name = name;
        this.aClass = aClass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class getaClass() {
        return aClass;
    }

    public void setaClass(Class aClass) {
        this.aClass = aClass;
    }

}
