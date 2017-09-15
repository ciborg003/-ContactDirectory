package com.itechart.projects.contactDirectory.model.entity;

import java.io.Serializable;

abstract public class Entity implements Serializable, Cloneable{
    private Integer id;

    public Entity() {
    }

    public Entity(int id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    
}
