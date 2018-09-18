/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/**
 * Enumeración de los mensajes de interfaz para mostrar, por tipo.
 * @author dark-reaper
 */
public enum MessageType {
    /**
     * Identificador del mensaje: ninguna fila seleccionada.
     */
    ANY_ROW_SELECTED,
    /**
     * Identificador del mensaje: correo de confirmación no encontrado.
     */
    CONFIRMATION_EMAIL_NOT_FOUND,
    /**
     * Identificador del mensaje: cancelar restablecer contraseña.
     */
    CONFIRM_RESET_PASSWORD_CANCEL,
    
    /**
     * Identificador del mensaje: fallo en la sintáxis del correo.
     */
    EMAIL_SYNTAX_FAILURE,
    
    /**
     *  Identificador del mensaje: campo de confirmación de correo vacío.
     */
    EMPTY_CONFIRMATION_CODE_FIELD,
    
    /**
     * Identificador del mensaje: campo de correo vacío.
     */
    EMPTY_EMAIL_FIELD,
    
    /**
     * Identificador del mensaje: campo de contraseña vacío.
     */
    EMPTY_PASSWORD_FIELD,
    
    /**
     * Identificador del mensaje: campo de nombre de usuario vacío.
     */
    EMPTY_USERNAME_FIELD,
    
    /**
     * Identificador del mensaje: campos de contraseñas no coinciden.
     */
    MISMATCHING_PASSWORD_FIELDS,
    
    /**
     * Identificador del mensaje: fallo en la sintaxis de contraseña.
     */
    PASSWORD_SYNTAX_FAILURE,
    
    /**
     * Identificador del mensaje: fallo en la recuperación de contraseña.
     */
    RESET_PASSWORD_FAILURE,
    
    /**
     * Identificador del mensaje: éxit en la recuperación de contraseña.
     */
    RESET_PASSWORD_SUCCESS,
    
    /**
     * Identificador del mensaje: fallo al enviar correo de confirmación.
     */
    SEND_CONFIRMATION_EMAIL_FAILURE,
    
    /**
     * Identificador del mensaje: éxito al enviar correo de confirmación.
     */
    SEND_CONFIRMATION_EMAIL_SUCCESS,
    
    /**
     * Identificador del mensaje: fallo en la inserción de usuario.
     */
    USER_INSERTION_FAILURE,
    
    /**
     * Identificador del mensaje: exito en la inserción de usuario.
     */
    USER_INSERTION_SUCCESS,
    
    /**
     * Identificador del mensaje: fallo de acceso.
     */
    USER_ACCESS_FAILURE,
    
    /**
     * Identificador del mensaje: exito de acceso.
     */
    USER_UPDATE_SUCCESS,
    
    /**
     * Identificador del mensaje: fallo de actualización de usuario.
     */
    USER_UPDATE_FAILURE,
    
    /**
     * Identificador del mensaje: exito en acceso de usuario.
     */
    USER_ACCESS_SUCCESS,
    
    /**
     * Identificador del mensaje: fallo en la sintaxis del nombre de usuario.
     */
    USERNAME_SYNTAX_FAILURE,
    
    /**
     * Identificador del mensaje: codigo de confirmación incorrecto.
     */
    WRONG_CONFIRMATION_CODE,
}
