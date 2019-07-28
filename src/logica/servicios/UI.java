/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica.servicios;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
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
        Font bigBoldFont = new Font("Yu Gothic IU", Font.BOLD, 18);
        Font bigPlainFont = new Font("Yu Gothic IU", Font.PLAIN, 18);
        Color ferguson = new Color(163, 36, 29);
        //HEADER
        tabla.getTableHeader().setFont(bigBoldFont);
        tabla.getTableHeader().setBackground(ferguson);
        tabla.getTableHeader().setForeground(Color.WHITE);
        //ROWS
        tabla.setFont(bigPlainFont);
        tabla.setRowHeight(25);
    }
    
    public void setLogoImg(JLabel label) {
            ImageIcon logo = new ImageIcon("src\\recursos\\aes_logo_blanco.jpg");//(Toolkit.getDefaultToolkit().getImage(getClass().getResource("aes_logo_blanco.jpg")));
            Image normalimg = logo.getImage();
            Image scaled = normalimg.getScaledInstance(290, 200,
                    Image.SCALE_SMOOTH);
            ImageIcon imageIcon = new ImageIcon(scaled);
            label.setIcon(imageIcon);

    }
}
