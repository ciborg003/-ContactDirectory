package com.itechart.projects.contactDirectory.model.entity;

import java.io.Serializable;

public class Phones extends Entity{

    private static final long serialVersionUID = 1L;
    private String countryCode;
    private String operatorCode;
    private String phoneNumber;
    private String phoneType;
    private int idContact;
    private String comment;

    public Phones() {
    }

    public Phones(Integer idPhones) {
        super(idPhones);
    }

    public Phones(Integer idPhones, String countryCode, String operatorCode, String phoneNumber, String phoneType, int idContact) {
        super(idPhones);
        this.countryCode = countryCode;
        this.operatorCode = operatorCode;
        this.phoneNumber = phoneNumber;
        this.phoneType = phoneType;
        this.idContact = idContact;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getOperatorCode() { 
        return operatorCode;
    }

    public void setOperatorCode(String operatorCode) {
        this.operatorCode = operatorCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(String phoneType) {
        this.phoneType = phoneType;
    }

    public int getIdContact() {
        return idContact;
    }

    public void setIdContact(int idContact) {
        this.idContact = idContact;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getId() != null ? getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Phones)) {
            return false;
        }
        Phones other = (Phones) object;
        if ((this.getId() == null && other.getId() != null) || 
                (this.getId() != null && !this.getId().equals(other.getId()))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Phones{" + "countryCode=" + countryCode + ", operatorCode=" + operatorCode + ", phoneNumber=" + phoneNumber + ", phoneType=" + phoneType + ", idContact=" + idContact + ", comment=" + comment + '}';
    }

    
}
