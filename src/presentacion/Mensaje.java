/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacion;

import javax.swing.JOptionPane;
import util.MessageHelper;
import util.MessageType;

/**
 *
 * @author dark-reaper
 */
public class Mensaje {
    
    public String obtenerMensaje(MessageHelper mensaje) {
        
        String msg = "";
        
        switch (mensaje) {
            case EMPTY_USERNAME_FIELD:
                msg = "Debe proporcionar un nombre de usuario";
                break;
            case EMPTY_PASSWORD_FIELD:
                msg = "Debe proporcionar una contraseña";
                break;
            case EMPTY_EMAIL_FIELD:
                msg = "Debe proporcionar un correo electrónico";
                break;
            case MISMATCHING_PASSWORD_FIELDS:
                msg = "Las contraseñas no coinciden";
                break;
            case USER_INSERTION_SUCCESS:
                msg = "La creación del usuario ha sido éxitosa";
                break;
            case USER_INSERTION_FAILURE:
                msg = "Ha ocurrido un error en la creación del usuario";
                break;
            case USER_ACCESS_SUCCESS:
                msg = "Acceso concedido. Bienvenido/a.";
                break;
            case USER_ACCESS_FAILURE:
                msg = "Ha ocurrido un error en el del usuario \n Verifique la información ingresada";
                break;
            case ANY_ROW_SELECTED:
                msg = "Seleccione un elemento de la lista";
                break;
            default:
                msg = "¡Ups! ¡Algo no ha salido bien!";
                break;
        }
        
        return msg;
    }
    public void mostrarMensaje(MessageType tipo, MessageHelper msg) {
        
        int pan;
        
        switch (tipo) {
            case INFORMATION:
                pan = JOptionPane.INFORMATION_MESSAGE;
                break;
            case WARNING:
                pan = JOptionPane.WARNING_MESSAGE;
                break;
            case ERROR:
                pan = JOptionPane.ERROR_MESSAGE;
                break;
            default:
                pan = JOptionPane.INFORMATION_MESSAGE;
                break;
        }
        
        JOptionPane.showMessageDialog(null, this.obtenerMensaje(msg), tipo.toString(), pan);
    }
}
