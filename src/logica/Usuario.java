/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import util.Estado;
import util.Rol;

/**
 *
 * @author dark-reaper
 */
public class Usuario {
    private String codigo;
    private String nombre;
    private String contrasenna;
    private String correo;
    private Rol rol;
    private String codRol;
    private String descRol;
    private Estado estado;
    
    public Usuario(String nombre) {
        this.nombre = nombre;
    }
    
    public Usuario(String codigo, String nombre, String contrasenna, 
            String correo, String codRol, String descRol, String estado) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.contrasenna = contrasenna;
        this.correo = correo;
        this.rol = codRol.equals("1") ? Rol.Administrador : Rol.Estándar;
        this.codRol = codRol;
        this.descRol = descRol;
        this.estado = estado.equals("A") ? Estado.Activo : Estado.Deshabilitado;
    }
    
    public String getCodigo() {
        return codigo;
    }
    
    public void setCodigo(String codigo) {
        this.codigo = codigo;
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
    
    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
    
    public Rol getRol() {
        return rol;
    }
    
    public void setRol(Rol rol) {
        this.rol = rol;
    }
    
    public String getCodRol() {
        return codRol;
    }
    
    public void setCodRol(String codRol) {
        this.codRol = codRol;
    }
    
    public String getDescRol() {
        return descRol;
    }
    
    public void setDescRol(String descRol) {
        this.descRol = descRol;
    }
    
    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
    
}
