/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import logica.Usuario;
import modelos.MdlAcceso;

/**
 * Controlador de la clase Acceso.
 * @author ahoihanabi
 */
public class CtrAcceso {
    
    /**
     * Instancia única de clase de acceso.
     */
    public static CtrAcceso instancia;
    private Usuario usuario;
    private MdlAcceso mdlAcceso;

    /**
     * Controlador de clase de acceso, inicializa varables.
     */
    public CtrAcceso() {
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
     * Establece el usuario en sesión.
     * @param usuario nuevo usuario a establecer.
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    /**
     * Obtener usuario en sesión.
     * @return instancia del usuario en sesión.
     */
    public Usuario getUsuario() {
        return usuario;
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
