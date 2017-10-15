package com.itechart.projects.contactDirectory.controller;

import com.cloudrail.si.CloudRail;
import com.cloudrail.si.interfaces.CloudStorage;
import com.cloudrail.si.servicecode.commands.awaitCodeRedirect.LocalReceiver;
import com.cloudrail.si.servicecode.commands.string.UrlEncode;
import com.cloudrail.si.services.Dropbox;
import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxClientV2;
import com.itechart.projects.contactDirectory.model.dropbox.DbxService;
import com.itechart.projects.contactDirectory.model.dropbox.DbxUser;
import java.io.File;
import java.io.FileInputStream;
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
        
        DbxClientV2 client = dbxService.getClient();
//        System.out.println(client.files().listFolder("/users_attachments/13").toStringMultiline());
        System.out.println(dbxService.isExists("/users_attachments/24/Закон-успеха.pdf"));
//        dbxService.deleteFile("/users_attachments/13/Закон-успеха.pdf");
        File file = new File("D:\\Закон-успеха.pdf");
        FileInputStream stream = new FileInputStream(file);
        dbxService.uploadFile(stream, "/users_attachments/24/Закон-успеха.pdf");
        System.out.println(dbxService.isExists("/users_attachments/24/Закон-успеха.pdf"));
        
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
