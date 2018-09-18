/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

/**
 * Clase de acceso, almacena la variable de sesión.
 * @author ahoihanabi
 */
public class Acceso {
    
    private Usuario usuario;
    
    
    /**
     * Constructor de clase acceso
     */
    public Acceso() {
        
    }
    
    /**
     * Establecer usuario.
     * @param user Usuario.
     */
    public void setUsuario(Usuario user) {
        usuario = user;
    }
    
    /**
     * Obtener usuario.
     * @return Usuario.
     */
    public Usuario getUsuario() {
        return usuario;
    }    
}
