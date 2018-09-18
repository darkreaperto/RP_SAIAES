/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import bd.Mail;
import javax.mail.MessagingException;

/**
 * Controlador de la clase Mail
 * @author dark-reaper
 */
public class CtrMail {
    
    private static Mail mail;
    
    /**
     * Constructor de la clase controlador del correo, inicializa variable.
     */
    public CtrMail() {
        mail = new Mail();
    }
    
    /**
     * Obtener la instancia de clase Mail.
     * @return instancia de clase Mail.
     */
    public Mail getMail() {
        return mail;
    }
    
    /**
     * Llama método que envía un correo con el codigo de recuperacion.
     * @param correoPara Correo destino.
     * @param codigo Código de recuperación para el destinatario.
     * @return Verdadero si el envío fue exitoso.
     * @throws MessagingException Excepción.
     */
    public boolean enviarCorreoRecuperacion(String correoPara, String codigo) 
            throws MessagingException {
        return mail.enviaCorreoRecuperacion(correoPara, codigo);
    }
}
