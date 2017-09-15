package com.itechart.projects.contactDirectory.model.entity;

import java.io.Serializable;

public class Attachment extends Entity{

    private static final long serialVersionUID = 1L;
    private String fileURL;
    private int idContact;
    private String comment;

    public Attachment() {
    }

    public Attachment(Integer idAttachment, String fileURL, int idContact) {
        super(idAttachment);
        this.fileURL = fileURL;
        this.idContact = idContact;
    }

    public String getFileURL() {
        return fileURL;
    }

    public void setFileURL(String fileURL) {
        this.fileURL = fileURL;
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
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Attachment)) {
            return false;
        }
        Attachment other = (Attachment) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.getId().equals(other.getId()))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Attachment{" + "fileURL=" + fileURL + ", idContact=" + idContact + ", comment=" + comment + '}';
    }
}
