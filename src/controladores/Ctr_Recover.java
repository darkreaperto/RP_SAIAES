/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import bd.Recover;

/**
 *
 * @author dark-reaper
 */
public class Ctr_Recover {
    
    private static Recover recover;
    
    public Ctr_Recover(String correo) {
        recover = new Recover(correo);
    }
    
    public void nuevoRecovery(String correo) {
        recover = null;
        recover = new Recover(correo);
    }
    
    public boolean confirmarCodigo(String correo, String codigo) {
        return recover.confirmarCodigo(correo, codigo);
    }
    
    public Recover getRecovery() {
        return recover;
    }
    
    public String getCorreo() {
        return recover.getCorreo();
    }
    
    public String getCodigo() {
        return recover.getCodigo();
    }
}
