package com.itechart.projects.contactDirectory.controller;

import com.cloudrail.si.CloudRail;
import com.cloudrail.si.interfaces.CloudStorage;
import com.cloudrail.si.servicecode.commands.awaitCodeRedirect.LocalReceiver;
import com.cloudrail.si.servicecode.commands.string.UrlEncode;
import com.cloudrail.si.services.Dropbox;
import com.dropbox.core.DbxException;
import com.itechart.projects.contactDirectory.model.dropbox.DbxService;
import com.itechart.projects.contactDirectory.model.dropbox.DbxUser;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;

public class NewClass {

    private static String licenseKey = "59e0973da5a11670ad739bad";
    private static String key = "cdhnvulp856ahth";
    private static String secret = "217dz0hr6ipjf3z";
    private static String token = "dT7ZKCx7PaAAAAAAAAAAK9K2clwUAqH-JMExBpW063QDRhOInK0hiClOP0-FC9J3";
    
    public static void main(String[] args) throws IOException, DbxException {
        DbxUser user = new DbxUser(token);
        DbxService dbxService = new DbxService(user);
        
        System.out.println(dbxService.isExists(URLEncoder.encode("users_attachments/13/Закон-успеха.pdf", "UTF-8")));
        File f = new File("file.pdf");
        f.createNewFile();
        FileOutputStream stream = new FileOutputStream(f);
        dbxService.readFile("users_attachments/13/Закон-успеха.pdf", stream);
//        CloudRail.setAppKey(licenseKey);
//        
//        CloudStorage service = null;
//        
//        Dropbox dropbox = new Dropbox(new LocalReceiver(8081), 
//                key, 
//                secret, 
//                "http://localhost:8080/ServletController/m",
//                "Dropbox");
//        
//        service = dropbox;
//        System.out.println("start");
//        service.login();
//        System.out.println("Exists: " + service.exists("users_attachments/13/Закон-успеха.pdf"));
//        service.logout();
    }
}
