package com.itechart.projects.contactDirectory.model.mail;

import com.itechart.projects.contactDirectory.model.entity.Contact;
import java.util.List;

public class Mail {
    private List<Contact> contacts;
    private String title;
    private String message;

    public Mail() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Mail(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }
}