/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import logica.Verificacion;

/**
 *
 * @author dark-reaper
 */
public class CtrVerificacion {
    
    private static Verificacion verficacion;
    
    public CtrVerificacion() {
        verficacion = new Verificacion();
    }
    
    public boolean validateEmail(String email) {
        return verficacion.validateEmail(email);
    }

    public boolean validatePassword(String password) {
        return verficacion.validatePassword(password);
    }
    
    public Verificacion getVerificacion() {
        return verficacion;
    }
}
