/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica.servicios;

import javax.swing.JOptionPane;
import util.TipoMensaje;

/**
 * Contiene los mensajes para informar al usuario el resultado de las
 * transacciones en el programa.
 * @author dark-reaper
 */
public class Mensaje {

    /**
     * Descripción del mensaje a mostrar.
     *
     * @param mensaje Enum identificador para escoger el detalle del mensaje
     * @return el mensaje a mostrar.
     */
    private String obtenerMensaje(TipoMensaje mensaje) {

        String msg;

        switch (mensaje) {
            case ANY_ROW_SELECTED:
                msg = "Seleccione un elemento de la lista";
                break;
            case CONFIRMATION_EMAIL_NOT_FOUND:
                msg = "No se ha podido encontrar un correo asociado \nal "
                        + "nombre de usuario especificado";
                break;
            case CONFIRM_RESET_PASSWORD_CANCEL:
                msg = "¿Está seguro que desea salir de la recuperación de "
                        + "contraseña?";
                break;
            case EMAIL_SYNTAX_FAILURE:
                msg = "El correo electrónico ingresado no es admitido";
                break;
            case EMPTY_TEXT_FIELD:
                msg = "Algunos campos están vacíos, "
                        + "ingrese la información solicitada";
                break;
            case EMPTY_CONFIRMATION_CODE_FIELD:
                msg = "Debe proporcionar un código de recuperación";
                break;
            case EMPTY_EMAIL_FIELD:
                msg = "Debe proporcionar un correo electrónico";
                break;
            case EMPTY_PASSWORD_FIELD:
                msg = "Debe proporcionar una contraseña";
                break;
            case EMPTY_USERNAME_FIELD:
                msg = "Debe proporcionar un nombre de usuario";
                break;
            case MISMATCHING_PASSWORD_FIELDS:
                msg = "Las contraseñas no coinciden";
                break;
            case PASSWORD_SYNTAX_FAILURE:
                msg = "La contraseña debe contener:"
                        + "\n Al menos una letra minúcula."
                        + "\n Al menos un número."
                        + "\n Mínimo 6 caracteres."
                        + "\n Máximo 16 caracteres."
                        + "\n Ningún espacio en blanco.";
                break;
            case PHONE_SYNTAX_FAILURE:
                msg = "El número de teléfono ingresado no es admitido";
                break;
            case SEND_CONFIRMATION_EMAIL_FAILURE:
                msg = "Ha ocurrido un error. No se ha podido enviar \nel "
                        + "correo de recuperación. ¿Desea reintentarlo?";
                break;
            case SEND_CONFIRMATION_EMAIL_SUCCESS:
                msg = "El correo de recuperación se ha enviado con éxito. "
                        + "\nRevise el correo asociado al nombre de usuario "
                        + "\ningresado para obtener el código de recuperación";
                break;
            case PRICE_SYNTAX_FAILURE:
                msg = "Precio ingresado no es correcto";
                break;
            case PRODUCT_INSERTION_SUCCESS:
                msg = "La creación del producto ha sido éxitosa";
                break;
            case PRODUCT_INSERTION_FAILURE:
                msg = "Ha ocurrido un error en la creación del producto";
                break;
            case UNITQUANTITY_SYNTAX_FAILURE:
                msg = "Solo puede ingresar números enteros";
                break;
            case USER_ACCESS_SUCCESS:
                msg = "Acceso concedido. Bienvenido/a.";
                break;
            case USER_ACCESS_FAILURE:
                msg = "Ha ocurrido un error en el del usuario "
                        + "\n Verifique la información ingresada";
                break;
            case USER_INSERTION_SUCCESS:
                msg = "La creación del usuario ha sido éxitosa";
                break;
            case USER_INSERTION_FAILURE:
                msg = "Ha ocurrido un error en la creación del usuario";
                break;
            case USER_UPDATE_FAILURE:
                msg = "Ha ocurrido un error en la actualización del usuario";
                break;
            case USER_UPDATE_SUCCESS:
                msg = "La actualización del usuario ha sido éxitosa";
                break;
            case USERNAME_SYNTAX_FAILURE:
                msg = "El nombre de usuario debe ser de"
                        + "\n al menos 4 caracteres de largo.";
                break;
            case RESET_PASSWORD_FAILURE:
                msg = "No se ha podido restablecer la "
                        + "\ncontraseña para el usuario especificado";
                break;
            case RESET_PASSWORD_SUCCESS:
                msg = "La contraseña para el usuario especificado "
                        + "\nha sido restablecida con éxito";
                break;
            case WRONG_CONFIRMATION_CODE:
                msg = "El código de recuperación es incorrecto";
                break;
            default:
                msg = "¡Ups! ¡Algo no ha salido bien!";
                break;
        }

        return msg;
    }
    
    /**
     * Descripción de la excepción SQL capturada.
     * @param error Codigo de excepción SQL
     * @return Mensaje que detalla la excepción
     */
    public String obtenerErrorSQL(int error) {

        String msg;

        switch (error) {
            case 1062:
                msg = "El nombre de usuario indicado ya existe.";
                break;
            default:
                msg = "¡Ups! ¡Ha ocurrido un error de SQL! "
                        + "\n Verifique la información ingresada.";
                break;
        }

        return msg;
    }
    /**
     * Agrega la descripción indicada al JOptionPane y lo muestra en un dialogo
     * informativo.
     *
     * @param tipo Tipo de mensaje (informacion, error, etc)
     * @param msg Enum del tipo de mensaje
     */
    public void mostrarMensaje(int tipo, TipoMensaje msg) {

        JOptionPane.showMessageDialog(null, obtenerMensaje(msg),
                "ADVERTENCIA", tipo);
    }

    /**
     * Muestra en pantalla un dialogo con todos sus elementos pasados por
     * parámetro.
     *
     * @param opcion Tipo de dialogo (yes, no.. ok etc)
     * @param tipo Tipo de mensaje
     * @param mensaje Detalle del mensaje dependiendo de su tipo.
     * @return mensaje emergente que permite al usuario escoger sí o no.
     */
    public int mostrarDialogo(int opcion, int tipo, TipoMensaje mensaje) {

        int dialogResult = JOptionPane.showConfirmDialog(null,
                obtenerMensaje(mensaje), "ADVERTENCIA", opcion, tipo);
        return dialogResult;
    }
    
    //https://dev.mysql.com/doc/refman/5.5/en/error-messages-server.html
    public void mostrarMensajeErrorSQL(int codError) {
        //codError = 2012;
        JOptionPane.showMessageDialog(null, obtenerErrorSQL(codError),
                "ERROR", JOptionPane.ERROR_MESSAGE);
    }
}
