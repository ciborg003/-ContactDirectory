package com.itechart.projects.contactDirectory.model.entity;

public enum EnumPhoneType {
    Mobile("Mobile"),
    Home("Home");
    
    private String description;

    private EnumPhoneType(String description) {
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
