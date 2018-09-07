/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import logica.Regex;

/**
 * Controladorde la clase Verificacion.
 * @author dark-reaper
 */
public class CtrVerificacion {
    
    private static Regex verficacion;
    
    public CtrVerificacion() {
        verficacion = new Regex();
    }
    
    public boolean validateEmail(String email) {
        return verficacion.validaEmail(email);
    }

    public boolean validatePassword(String password) {
        return verficacion.validaClave(password);
    }
    
    public Regex getVerificacion() {
        return verficacion;
    }
}
