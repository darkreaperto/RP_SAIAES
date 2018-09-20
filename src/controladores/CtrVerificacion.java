/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import logica.servicios.Regex;

/**
 * Controladorde la clase Verificacion.
 * @author dark-reaper
 */
public class CtrVerificacion {
    
    private static Regex verficacion;
    
    /**
     * Constructor de clase controlador de verificación, inicializa variables.
     */
    public CtrVerificacion() {
        verficacion = new Regex();
    }
    
    /**
     * Llama el método que valida una dirección de correo de entrada con 
     * EMAIL_REGEX.
     * @param email Correo.
     * @return Verdadero si el correo es adecuado.
     */
    public boolean validateEmail(String email) {
        return verficacion.validaEmail(email);
    }
    
    /**
     * Llama método que valida una contraseña de entrada con PASSWORD_REGEX.
     * @param password Contraseña.
     * @return Verdadero si la contraseña es adecuada.
     */
    public boolean validatePassword(String password) {
        return verficacion.validaClave(password);
    }
    
    /**
     * Obtener verificación.
     * @return Verificación.
     */
    public Regex getVerificacion() {
        return verficacion;
    }
}
