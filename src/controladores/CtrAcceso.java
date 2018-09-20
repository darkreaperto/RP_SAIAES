/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import logica.Acceso;
import logica.Usuario;
import modelos.MdlAcceso;
import util.Modulo;

/**
 * Controlador de la clase Acceso.
 * @author ahoihanabi
 */
public class CtrAcceso {
    
    /**
     * Instancia única de clase de acceso.
     */
    public static CtrAcceso instancia;
    private Acceso acceso;
    private MdlAcceso mdlAcceso;

    /**
     * Controlador de clase de acceso, inicializa varables.
     */
    public CtrAcceso() {
        acceso = new Acceso();
        mdlAcceso = new MdlAcceso();
    }
    
    /**
     * Obtener la instancia única de la clase de acceso.
     * @return la instancia única de la clase de acceso.
     */
    public static CtrAcceso getInstancia() {
        return instancia == null ? new CtrAcceso() : instancia;
    }
    
    /**
     * Obtener usuario en sesión.
     * @return instancia del usuario en sesión.
     */
    public Usuario getUsuario() {
        return acceso.getUsuario();
    }
    
    /**
     * Establece el usuario en sesión.
     * @param usuario nuevo usuario a establecer.
     */
    public void setUsuario(Usuario usuario) {
        acceso.setUsuario(usuario);
    }
    
    /**
     * Obtener módulo en uso.
     * @return el módulo en uso.
     */
    public Modulo getModuloActual() {
        return acceso.getModuloActual();
    }

    /**
     * Establecer módulo en utilización.
     * @param moduloActual el modulo a establecer.
     */
    public void setModuloActual(Modulo moduloActual) {
        acceso.setModuloActual(moduloActual);
    }
    
    /**
     * Envía a comparar el usuario que ingresa con los usuarios de la bd.
     * @param user nuevo usuario en sesión
     * @param pass contraseña del usuario que ingresa.
     * @return Verdadero si la clave y usuario coinciden.
     */
    public boolean compararClave(String user, String pass) {
        return mdlAcceso.compararClave(user, pass);
    }
}
