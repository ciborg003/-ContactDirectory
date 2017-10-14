package com.itechart.projects.contactDirectory.model.entity;

import java.io.Serializable;
import java.sql.Date;

public class Attachment extends Entity{

    private static final long serialVersionUID = 1L;
    private String url;
    private int idContact;
    private Date loadDate;
    private String comment;

    public Attachment() {
    }

    public Date getLoadDate() {
        return loadDate;
    }

    public void setLoadDate(Date loadDate) {
        this.loadDate = loadDate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
        hash += (this.getId() != null ? this.getId().hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "Attachment{" + "url=" + url + ", idContact=" + idContact + ", comment=" + comment + '}';
    }

    
}
