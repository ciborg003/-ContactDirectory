package com.itechart.projects.contactDirectory.model.entity;

public enum EnumGender {
    NONE("None"),
    MALE("Male"), 
    FEMALE("Female");

    private String description;
    
    private EnumGender(String description) {
        this.description = description;
    }
    
    public String getDescription(){
        return description;
    }

    @Override
    public String toString() {
        return description;
    }   
}
