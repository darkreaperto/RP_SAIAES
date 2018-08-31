/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import modelos.MdlAcceso;

/**
 *
 * @author ahoihanabi
 */
public class CtrAcceso {
    
    public static CtrAcceso instancia;
    private String usuario;
    private MdlAcceso mdlAcceso;

    public CtrAcceso() {
        mdlAcceso = new MdlAcceso();
    }
    
    public static CtrAcceso getInstancia() {
        return instancia == null ? new CtrAcceso() : instancia;
    }
    
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    
    public String getUsuario() {
        return usuario;
    }
    
    public boolean comparacion(String user, String pass) {
        return mdlAcceso.comparacion(user, pass);
    }
}
