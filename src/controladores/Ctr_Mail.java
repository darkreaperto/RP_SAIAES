/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import bd.Mail;

/**
 *
 * @author dark-reaper
 */
public class Ctr_Mail {
    
    private static Mail mail;
    
    public Ctr_Mail() {
        mail = new Mail();
    }
    
    public Mail getMail() {
        return mail;
    }
    
    public boolean enviarCorreoRecuperacion(String correoPara, String codigo) {
        return mail.enviaCorreoRecuperacion(correoPara, codigo);
    }
}
