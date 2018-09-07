/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacion;

/**
 * Clase principal que hace visible el formulario de inicio.
 * @author ahoihanabi
 */
public class Principal {
    
    /** 
     * Hace visible la ventana principal
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        FrmPrincipal ventana = new FrmPrincipal();
        ventana.setVisible(true);
        ventana.setLocationRelativeTo(null);
        
    }
}
