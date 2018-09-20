/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import util.Modulo;

/**
 * Clase de acceso, almacena la variable de sesión.
 * @author ahoihanabi
 */
public class Acceso {
    
    private Usuario usuario;
    private Modulo moduloActual;
    
    
    /**
     * Constructor de clase acceso.
     */
    public Acceso() {
        moduloActual = Modulo.MODULO_ACCESO;
    }
    
    /**
     * Obtener usuario.
     * @return Usuario.
     */
    public Usuario getUsuario() {
        return usuario;
    }
    
    /**
     * Establecer usuario.
     * @param usuario Usuario.
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }    

    /**
     * Obtener módulo en uso.
     * @return el módulo en uso.
     */
    public Modulo getModuloActual() {
        return moduloActual;
    }

    /**
     * Establecer módulo en utilización.
     * @param moduloActual el modulo a establecer.
     */
    public void setModuloActual(Modulo moduloActual) {
        this.moduloActual = moduloActual;
    }
}
