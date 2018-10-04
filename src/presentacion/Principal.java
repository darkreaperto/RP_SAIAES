/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacion;

import javax.swing.JFrame;
import javax.swing.JRootPane;
import javax.swing.UIManager;

/**
 * Clase principal que hace visible la ventana de inicio.
 * @author ahoihanabi
 */
public class Principal {
    
    /** 
     * Hace visible la ventana principal
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        try { 
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel")
            
        } catch (Exception ex) { 
            ex.printStackTrace(); 
        }
        FrmPrincipal ventana = new FrmPrincipal();
        //ventana.setExtendedState(JFrame.MAXIMIZED_BOTH);
        ventana.setLocation(0, 0);
        //ventana.setUndecorated(true);
        ventana.getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
        ventana.setVisible(true);
        
    }
}
