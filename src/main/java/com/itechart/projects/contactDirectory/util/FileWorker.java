package com.itechart.projects.contactDirectory.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileWorker {

    public static synchronized Properties getPropertyFile(String fileName) {
        Properties prop = new Properties();
        InputStream inputStream = null;

        try {
            inputStream = new FileInputStream(fileName);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
//            Logger.getLogger(FileWorker.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            prop.load(inputStream);

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return prop;
    }
    
    public static void createFolder(String folderName){
        File file = new File(folderName);
        file.mkdirs();
    }
}
