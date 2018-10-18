/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacion;

import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JRootPane;

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
        
        FrmPrincipal ventana = new FrmPrincipal();
        //ventana.setExtendedState(JFrame.MAXIMIZED_BOTH);
        ventana.setLocation(0, 0);
        //ventana.setUndecorated(true);
        //Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle winSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        ventana.setSize(winSize.width, winSize.height);
        ventana.getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
        ventana.setVisible(true);
        
    }
}
