/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica.servicios;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Almacena el log del usuario en el sistema.
 * @author dark-reaper
 */
public class Logger {
    
    private static PrintWriter pw;
    private static StringWriter sw;
    private static File file;
    private static String fileName;
    private static String filePath;
    private static SimpleDateFormat formatter;
    private static String dateFormat;
    private static String timeFormat;
    private static String date;
    private static String rootPath;
    
    private static void setParams() {
        rootPath = System.getProperty("user.dir");
        dateFormat = "yyyyMM";//"yyyyMMdd";
        timeFormat = "";//"HHmmss";
        
        formatter = new SimpleDateFormat(dateFormat+timeFormat);
        date = formatter.format(Calendar.getInstance().getTime());
        
        fileName = "Log_"+date+".txt";
        filePath = rootPath + "\\" + fileName;
        
        System.out.println("LOG: "+filePath);
        
        dateFormat = "yyyyMMdd";
        timeFormat = "HHmmss";
        
        formatter = new SimpleDateFormat(dateFormat+timeFormat);
        date = formatter.format(Calendar.getInstance().getTime());
        
        System.out.println("LOG: "+date);
        
        file = new File(filePath);
    }
    
    public static void registerNewLog(String text) {
        setParams();
        
        
    }
    
    public static void registerNewLog(Exception ex) {
        sw = new StringWriter();
            PrintWriter printWriter= new PrintWriter(writer);
            exception.printStackTrace(printWriter);
    }
    
    public static void registerNewLog(Exception ex, String text) {
        
    }
}
