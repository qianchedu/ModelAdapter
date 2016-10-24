package com.dsz.db2;

import java.io.Serializable;

/**
 * Created by Soham on 17-Mar-15.
 */
public class AddNameBean implements Serializable {
    private long id;
    private String name;

    public AddNameBean() {
    }

    public AddNameBean(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
