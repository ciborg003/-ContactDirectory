package com.itechart.projects.contactDirectory.model.stringTemplates;

import com.itechart.projects.contactDirectory.model.entity.Contact;
import java.util.List;
import org.stringtemplate.v4.ST;

public class MsgRender {

    public String getMsgByTemplate(List<Contact> contacts, String message) {

        ST strTemplate = new ST(message);

        for (Contact contact : contacts) {
            strTemplate.add("user", contact);
        }

        return strTemplate.render();
    }

    public String getMsgByTemplate(Contact contact, String message) {

        ST strTemplate = new ST(message);

        strTemplate.add("user", contact);

        return strTemplate.render();
    }

    public MsgRender() {
    }

}
