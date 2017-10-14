package com.itechart.projects.contactDirectory.model.entity;

public enum EnumFamilyState {
    MARRIED("Married"),
    SINGLE("Single");

    private String description;

    private EnumFamilyState(String description) {
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
