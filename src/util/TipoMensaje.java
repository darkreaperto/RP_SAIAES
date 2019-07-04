/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import logica.negocio.Madera;

/**
 * Enumeración de los mensajes de interfaz para mostrar, por tipo.
 * @author dark-reaper
 */
public enum TipoMensaje {
    /** Identificador del mensaje: ninguna fila seleccionada.
        Identificador del mensaje: correo de confirmación no encontrado.
        Identificador del mensaje: cancelar restablecer contraseña.
        Identificador del mensaje: fallo en la inserción de cliente.
        @see Madera 
        
    */
    ANY_ROW_SELECTED,
    
    ANY_RADIO_BUTTON_SELECTED,
    
    CONFIRMATION_EMAIL_NOT_FOUND,
    
    CONFIRM_RESET_PASSWORD_CANCEL,
    
    CUSTOMER_INSERTION_FAILURE,
    /**Identificador del mensaje: No se encontró el cliente solicitado**/
    CUSTOMER_NOT_FOUND,
    
    CUSTOMER_UPDATE_FAILURE,
    
    CUSTOMER_UPDATE_SUCCESS,
    /** Identificador del mensaje: exito en la inserción de cliente. */
    CUSTOMER_INSERTION_SUCCESS,
    /** Identificador del mensaje: fallo de acceso. */
    /** Identificador del mensaje: fallo en la sintáxis del correo. */
    EMAIL_SYNTAX_FAILURE,
    /** Identificador del mensaje: campo de confirmación de correo vacío. */
    EMPTY_CONFIRMATION_CODE_FIELD,
    /** Identificador del mensaje: campos de cliente vacíos. */
    EMPTY_CUSTOMER_FIELDS,
    /** Identificador del mensaje: campos de texto vacío. */
    EMPTY_TEXT_FIELD,
    /** Identificador del mensaje: campo de correo vacío. */
    EMPTY_EMAIL_FIELD,
    /** Identificador del mensaje: campo de contraseña vacío. */
    EMPTY_PASSWORD_FIELD,
    /** Identificador del mensaje: campo de nombre de usuario vacío. */
    EMPTY_USERNAME_FIELD,
    /** Identificador del mensaje: ArrayIndexOutOfBounds, NullPointerException, y errores relacionados con listas. */
    LIST_HANDLER_ERROR,
    /** Identificador del mensaje: campos de contraseñas no coinciden. */
    MISMATCHING_PASSWORD_FIELDS,
    /** Identificador del mensaje: campos de número con carácteres alfanumérico. */
    NUMBER_FORMAT_EXCEPTION,
    /** Identificador del mensaje: fallo en la sintaxis de contraseña. */
    PASSWORD_SYNTAX_FAILURE,
    /** Identificador del mensaje: fallo en la sintaxix del número de teléfono. */
    PHONE_SYNTAX_FAILURE,
    /** Identificador del mensaje: fallo en la recuperación de contraseña. */
    RESET_PASSWORD_FAILURE,
    /** Identificador del mensaje: éxito en la recuperación de contraseña. */
    RESET_PASSWORD_SUCCESS,
    /** Identificador del mensaje: fallo al enviar correo de confirmación. */
    SEND_CONFIRMATION_EMAIL_FAILURE,
    /** Identificador del mensaje: éxito al enviar correo de confirmación. */
    SEND_CONFIRMATION_EMAIL_SUCCESS,
    /** Identificador del mensaje: precio ingresado no es correcto. */
    PRICE_SYNTAX_FAILURE,
    
    PRODUCT_AMOUNT_EXCEEDED,
    /** Identificador del mensaje: fallo en la inserción del producto. */
    PRODUCT_INSERTION_FAILURE,
    /** Identificador del mensaje: exito en la inserción del producto. */
    PRODUCT_INSERTION_SUCCESS,
    /** Identificador del mensaje: fallo en la actualización del producto. */
    PRODUCT_UPDATE_FAILURE,
    /** Identificador del mensaje: exito en la actualización del producto. */
    PRODUCT_UPDATE_SUCCESS,
    /** Identificador del mensaje: fallo en la suma/resta de cantidad del producto. */
    PRODUCT_SUM_RES_FAILURE,
    
    TAX_CODE_MISSING,
    
    TAX_MISSING,
    
    TOTALS_CALCULATION_FAILURE,
    /** Identificador del mensaje: solo puede ingresar numeros enteros. Precio ingresado no es correcto*/
    UNITQUANTITY_SYNTAX_FAILURE,
    /** Identificador del mensaje: fallo en la inserción de usuario. */
    USER_INSERTION_FAILURE,
    /** Identificador del mensaje: exito en la inserción de usuario. */
    USER_INSERTION_SUCCESS,
    /** Identificador del mensaje: fallo de acceso. */
    USER_ACCESS_FAILURE,
    /** Identificador del mensaje: exito de acceso. */
    USER_UPDATE_SUCCESS,
    /** Identificador del mensaje: fallo de actualización de usuario. */
    USER_UPDATE_FAILURE,
    /** Identificador del mensaje: exito en acceso de usuario. */
    USER_ACCESS_SUCCESS,
    /** Identificador del mensaje: fallo en la sintaxis del nombre de usuario. */
    USERNAME_SYNTAX_FAILURE,
    /** Identificador del mensaje: codigo de confirmación incorrecto. */
    WRONG_CONFIRMATION_CODE,
    /** Identificador del mensaje: campos de cliente incorrectos. */
    WRONG_CUSTOMER_FIELDS,
    WRONG_DECIMAL_NUMBER,
    /** Identificador del mensaje: algo ha salido mal. */
    SOMETHING_WENT_WRONG
}
