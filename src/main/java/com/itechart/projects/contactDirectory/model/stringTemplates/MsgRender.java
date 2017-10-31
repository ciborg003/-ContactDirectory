package com.itechart.projects.contactDirectory.model.stringTemplates;

import com.itechart.projects.contactDirectory.model.entity.Contact;
import java.util.List;
import org.stringtemplate.v4.ST;

public class MsgRender {

    public String getMsgByTemplate(List<Contact> contacts, String message) {

        ST strTemplate = new ST(message);

        for (Contact contact : contacts) {
            Object args[] = {contact.getName(), contact.getSurname()};
            strTemplate.addAggr("users.{name, surname}", args);
        }

        return strTemplate.render();
    }

    public String getMsgByTemplate(Contact contact, String message) {

        ST strTemplate = new ST(message);

        Object args[] = {contact.getName(), contact.getSurname()};
        strTemplate.addAggr("users.{name, surname}", args);

        return strTemplate.render();
    }

    public MsgRender() {
    }

}
