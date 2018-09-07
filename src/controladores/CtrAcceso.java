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
    
    public static CtrAcceso instancia;
    private Usuario usuario;
    private MdlAcceso mdlAcceso;

    public CtrAcceso() {
        mdlAcceso = new MdlAcceso();
    }
    
    public static CtrAcceso getInstancia() {
        return instancia == null ? new CtrAcceso() : instancia;
    }
    
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public Usuario getUsuario() {
        return usuario;
    }
    
    public boolean compararClave(String user, String pass) {
        return mdlAcceso.compararClave(user, pass);
    }
}
