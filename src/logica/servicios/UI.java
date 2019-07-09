/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica.servicios;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JTable;

/**
 *
 * @author aohanabi
 */
public class UI {
    public UI() {
        
    }
    
    /**
     * Establece el estilo de todas las tablas.
     * @param tabla el nombre de la tabla de la interfaz que se estilizará.
     */
    public void estilizarTablas(JTable tabla) {
        Font bigBoldFont = new Font("Yu Gothic IU", Font.BOLD, 16);
        Font bigPlainFont = new Font("Yu Gothic IU", Font.PLAIN, 16);
        Color ferguson = new Color(163, 36, 29);
        //HEADER
        tabla.getTableHeader().setFont(bigBoldFont);
        tabla.getTableHeader().setBackground(ferguson);
        tabla.getTableHeader().setForeground(Color.WHITE);
        //ROWS
        tabla.setFont(bigPlainFont);
        tabla.setRowHeight(25);
    }
}
