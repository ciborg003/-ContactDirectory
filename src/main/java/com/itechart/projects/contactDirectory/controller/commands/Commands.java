package com.itechart.projects.contactDirectory.controller.commands;

import java.util.HashMap;
import java.util.Map;
import com.itechart.projects.contactDirectory.controller.commands.CommandProcess;

public class Commands {

    private static final Map processors = new HashMap();

    static {
        processors.put("addContact", AddContactCommand.class);
        processors.put("createContact", CreateContactCommand.class);
        processors.put("saveContactChanges", SaveContactChangesCommand.class);
        processors.put("updateContact", UpdateContactCommand.class);
        processors.put("deleteContact", DeleteContactCommand.class);
        processors.put("search", SearchCommand.class);
        processors.put("sendEmail", MailSenderCommand.class);
        processors.put("changePage", ChangePageCommand.class);
        processors.put("downloadFile", DownloadFileCommand.class);
        processors.put("getMailPage", GetMailPageCommand.class);
        processors.put("getSearchPage", GetSearchPageCommand.class);
        processors.put("cloudRail", GetSearchPageCommand.class);
        processors.put(null, GetMainPageCommand.class);
    }

    public static CommandProcess getRequestProcessor(String command) {
        System.out.println("Command: " + command);
        Class processClass = (Class) processors.get(command);
        if (processClass == null) {
            processClass = (Class) processors.get(null);
        }

        try {
            return (CommandProcess) processClass.newInstance();
        } catch (InstantiationException | IllegalAccessException ex) {
            ex.printStackTrace();
        }
        
        return null;
    }
}
