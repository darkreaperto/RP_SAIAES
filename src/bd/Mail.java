/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bd;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Authenticator;
import javax.mail.NoSuchProviderException;

/**
 *
 * @author dark-reaper
 */
public class Mail {

    private final String usuario;
    private final String clave;
    private final String correoDe;
    private AESEncrypt crypter;
    
    public Mail () {
        
        crypter = new AESEncrypt();
        crypter.addKey("SAI");
        
        this.usuario = crypter.desencriptar("WOw9aCjysC++fD24Q4AR3NoY7vrsNj6TbQWPrXaL20w=");
        this.clave = crypter.desencriptar("0DCsLyRjMyfzRF0sy5ATrA==");
        this.correoDe = "rodrigocedenocedeno@gmail.com";
    }
    
    public Mail (String usuario, String clave) {
        
        crypter = new AESEncrypt();
        crypter.addKey("SAI");
        
        this.usuario = usuario;
        this.clave = clave;
        this.correoDe = "rodrigocedenocedeno@gmail.com";
    }
    
    /**
     * Enviar un correo con el codigo de recuperacion
     * @param correoPara
     * @param codigo
     * @return exito
     */
    public boolean enviaCorreoRecuperacion(String correoPara, String codigo)throws MessagingException {
        
        boolean exito = false;
        String asunto = "SAI-AES: Recuperacion de contraseña";
        String mensaje = "Tu código de seguridad para recuperar tu contraseña "
                + "es: " + codigo + ""
               + "\nIngrésalo en la aplicación para restablecer tu contraseña.";
        
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.timeout", "2000");    
        props.put("mail.smtp.connectiontimeout", "2000");
        
        System.out.println("Props-end");

        Session session = Session.getInstance(props,
          new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(usuario, clave);
            }
          });
        
        System.out.println("Session-end");
            
        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(correoDe));
            message.setRecipients(Message.RecipientType.TO, 
                    InternetAddress.parse(correoPara));
            message.setSubject(asunto);
            message.setText(mensaje);

            System.out.println("Message-end");
            
            Transport.send(message);

            System.out.println("Transport-end");
            
            exito = true;
            
        } catch (MessagingException ex) {
            System.err.println(ex);
        }finally {
            return exito;
        }
    }
    
    public boolean enviaCorreo(String correoPara, String asunto, String mensaje){
        
        boolean exito = false;
        
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        
        System.out.println("Props-end");

        Session session = Session.getInstance(props,
          new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(usuario, clave);
            }
          });
        
        System.out.println("Session-end");

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(correoDe));
            message.setRecipients(Message.RecipientType.TO, 
                    InternetAddress.parse(correoPara));
            message.setSubject(asunto);
            message.setText(mensaje);

            System.out.println("Message-end");
            
            Transport.send(message);

            System.out.println("Transport-end");
            
            exito = true;
            
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        } finally {
            return exito;
        }
    }
}