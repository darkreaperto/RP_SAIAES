/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica.servicios;

import presentacion.Mensaje;
import util.MessageType;
import javax.swing.JOptionPane;
import controladores.CtrMail;
import controladores.CtrRecover;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.swing.JButton;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

/**
 * Implementación Runnable para enviar correos en un hilo diferente a la 
 * ejecución del programa.
 * @author dark-reaper
 */
public class MailThread implements Runnable {
    
    private boolean enviarCorreo;
    private boolean retry;
    private final Mensaje msg;
    private final String correo;
    private final CtrRecover recover;
    private final CtrMail mail;
    private final JTextField txt_codigoConf_recClv;
    private final JButton btn_codigoConf_recClv;
    private final JProgressBar pb_enviarCorreo;
    
    /**
     * Constructor de MailThread, inicializa variables.
     * @param msg Mensaje para informar al usuario.
     * @param correo Correo de destino.
     * @param recover Contiene el código de recuperación.
     * @param mail Clase para gestionar el envío del correo.
     * @param txt_codigoConf_recClv Texto con el código de recuperación.
     * @param btn_codigoConf_recClv Botón para confirmar coincidencia del código.
     * @param pb_enviarCorreo Barra mostrando envío del correo.
     */
    public MailThread(Mensaje msg, String correo, CtrRecover recover, 
            CtrMail mail, JTextField txt_codigoConf_recClv, 
            JButton btn_codigoConf_recClv, JProgressBar pb_enviarCorreo) {
        this.enviarCorreo = false;
        this.retry = true;
        this.msg = msg;
        this.correo = correo;
        this.recover = recover;
        this.mail = mail;
        this.txt_codigoConf_recClv = txt_codigoConf_recClv;
        this.btn_codigoConf_recClv = btn_codigoConf_recClv;
        this.pb_enviarCorreo = pb_enviarCorreo;
    }
    @Override
    public void run() {
        try {
            enviarCorreo = mail.enviarCorreoRecuperacion(correo,
                    recover.getCodigo());
            while (!enviarCorreo && retry) {
                int dialogo;
                if ((dialogo =
                        msg.mostrarDialogo(JOptionPane.YES_NO_OPTION,
                                JOptionPane.ERROR_MESSAGE,
                                MessageType.SEND_CONFIRMATION_EMAIL_FAILURE)) ==
                        JOptionPane.YES_OPTION) {
                    
                    enviarCorreo = mail.enviarCorreoRecuperacion(correo,
                            recover.getCodigo());
                }
                retry = dialogo == JOptionPane.YES_OPTION;
                
                try {
                    Thread.sleep(250);
                } catch (InterruptedException ex) {
                    System.err.println(ex);
                }
            }
            
            if (enviarCorreo) {
                //Habilitar campo para ingresar codigo de recuperacion
                txt_codigoConf_recClv.setEnabled(true);
                //Habilitar boton para confirmar codigo de recuperacion
                btn_codigoConf_recClv.setEnabled(true);
                
                msg.mostrarMensaje(JOptionPane.INFORMATION_MESSAGE,
                        MessageType.SEND_CONFIRMATION_EMAIL_SUCCESS);
            }
            //Ocultar barra de tarea
            pb_enviarCorreo.setVisible(false);
        } catch (MessagingException ex) {
            Logger.getLogger(MailThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
