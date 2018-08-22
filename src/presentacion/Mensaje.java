/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacion;

import util.MessageHelper;

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
            case MISMATCHING_PASSWORD_FIELDS:
                msg = "Las contraseñas no coinciden";
                break;
            case USER_INSERTION_SUCCESS:
                msg = "La creación del usuario ha sido éxitosa";
                break;
            case USER_INSERTION_FAILURE:
                msg = "Ha ocurrido un error en la creación del usuario";
                break;
            default:
                msg = "¡Ups! ¡Algo no ha salido bien!";
                break;
        }
        
        return msg;
    }
}
