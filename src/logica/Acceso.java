/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

/**
 * Almacena la variable de sesión.
 * @author ahoihanabi
 */
public class Acceso {
    
    private Usuario usuario;
    
    
    public Acceso() {
        
    }
    
    public void setUsuario(Usuario user) {
        usuario = user;
    }
    
    public Usuario getUsuario() {
        return usuario;
    }    
}
