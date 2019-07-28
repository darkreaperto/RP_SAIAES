/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica.servicios;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;

/**
 * Almacena el log del usuario en el sistema.
 * @author dark-reaper
 */
public class Logger {
    
    private static PrintStream ps;
    private static StringWriter sw;
    private static File file;
    private static String fileName;
    private static String filePath;
    private static SimpleDateFormat formatter;
    private static String dateFormat;
    private static String timeFormat;
    private static String date;
    private static String rootPath;
    
    private static void setInitParams() {
        
        rootPath = System.getProperty("user.dir");
        
        createDir();
    }
    
    private static void createDir() {
        try {
            file = new File(rootPath + "\\logs\\");
            if (!file.exists()) {
                file.mkdirs();
            }
        } catch (Exception ex) {
            Logger.registerNewError(ex);
            ex.printStackTrace();
        }
    }
    
    public static void registerNewLog(String text) {
        setInitParams();
        
        dateFormat = "yyyyMMdd";
        
        formatter = new SimpleDateFormat(dateFormat);
        date = formatter.format(Calendar.getInstance().getTime());
        
        fileName = "Log_"+date+".txt";
        filePath = rootPath + "\\logs\\" + fileName;
        
        dateFormat = "yyyyMMdd";
        timeFormat = "HHmmss";
        
        formatter = new SimpleDateFormat(dateFormat+timeFormat);
        date = formatter.format(Calendar.getInstance().getTime());
        
        try {
            file = new File(filePath);
            ps = new PrintStream(file);
            String log = date + ": " + text;
            ps.println(log);
        } catch (FileNotFoundException ex) {
            Logger.registerNewError(ex);
            ex.printStackTrace();
        } catch (Exception ex) {
            Logger.registerNewError(ex);
            ex.printStackTrace();
        }
        ps.close();
    }
    
    public static void registerNewError(Exception ex) {
        setInitParams();
        
        dateFormat = "yyyyMMdd";
        timeFormat = "HHmmss";
        
        formatter = new SimpleDateFormat(dateFormat+timeFormat);
        date = formatter.format(Calendar.getInstance().getTime());
        
        fileName = "Error_"+date+".txt";
        filePath = rootPath + "\\logs\\" + fileName;
        
        try {
            file = new File(filePath);
            ps = new PrintStream(file);
        } catch (FileNotFoundException e) {
            Logger.registerNewError(ex);
            e.printStackTrace();
        } catch (Exception e) {
            Logger.registerNewError(ex);
            e.printStackTrace();
        }
        ex.printStackTrace(ps);
        ps.close();
    }
    
    public static void registerNewLog(Exception ex, String text) {
        
    }
}
