package com.itechart.projects.contactDirectory.controller;

import com.itechart.projects.contactDirectory.model.exceptions.DAOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;

public class Main {
    
    private static final Logger logger = Logger.getRootLogger();
    private static final String pattern = "^[A-Za-z]+'?[A-Za-z]+$|^[А-Яа-я]+$";
    
    public static void main(String[] args) throws DAOException {
        String value = "NONE";
        System.out.println(value != null && value.trim().length() > 0 
                        && !value.toLowerCase().equals("none"));
    }
    protected static boolean validateWord(String word){
        Pattern p = Pattern.compile(pattern);
        Matcher matcher = p.matcher(word);
        
        return matcher.find();
    }
    
    protected static boolean validatePhone(String phone){
        String patternPhone = "^\\+?[0-9]{2,3}-[0-9]{2}-[0-9]{7}$";
        Pattern p = Pattern.compile(patternPhone);
        Matcher matcher = p.matcher(phone);
        
        return matcher.find();
    }
}

