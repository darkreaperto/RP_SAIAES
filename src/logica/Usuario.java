/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

/**
 *
 * @author dark-reaper
 */
public class Usuario {
    
    private String nombre;
    private String contrasenna;
    
    public Usuario(String nombre) {
        setNombre(nombre);
    }
    
    public Usuario(String nombre, String contrasenna) {
        setNombre(nombre);
        setContrasenna(contrasenna);
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the contrasenna
     */
    public String getContrasenna() {
        return contrasenna;
    }

    /**
     * @param contrasenna the contrasenna to set
     */
    public void setContrasenna(String contrasenna) {
        this.contrasenna = contrasenna;
    }
    
    
}
