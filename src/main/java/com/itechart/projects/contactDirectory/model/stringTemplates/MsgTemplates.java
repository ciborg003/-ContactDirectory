package com.itechart.projects.contactDirectory.model.stringTemplates;

import java.util.List;
import java.util.ResourceBundle;

public enum MsgTemplates {
    HappyBirthday("HappyBirthday"),
    BIRTHDAY_LIST("BirthdayList");
    
    private final static String STRING_TEMPLATE_PROPERTY = "StringTemplates";
    private String msg;
    private String msgName;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsgName() {
        return msgName;
    }

    public void setMsgName(String msgName) {
        this.msgName = msgName;
    }
    
    private MsgTemplates(String msgName){
        ResourceBundle bundle = ResourceBundle.getBundle(STRING_TEMPLATE_PROPERTY);
        this.msg = bundle.getString(msgName);
        this.msgName = msgName;
    }

    @Override
    public String toString() {
        return msgName;
    }
    
    
}
