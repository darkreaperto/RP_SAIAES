/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica.negocio;

import util.Estado;
import util.Rol;

/**
 * Instancia los usuarios con sus atributos.
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

    /**
     * Constructor de clase usuario.
     * @param nombre Nombre de usuario.
     */
    public Usuario(String nombre) {
        this.nombre = nombre;
    }
    
    /**
     * Constructor de clase usuario, con parámetros.
     * @param codigo Código usuario.
     * @param nombre Nombre usuario.
     * @param contrasenna Contraseña usuario.
     * @param correo Correo usuario.
     * @param codRol Código rol usuario.
     * @param descRol Descripción de rol usuario.
     * @param estado Estado de usuario.
     */
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
    
    /**
     * Obtener código.
     * @return Código.
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Establecer código.
     * @param codigo Código.
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    
    /**
     * Obtener nombre.
     * @return Nombre.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establecer nombre.
     * @param nombre nombre de usuario.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtener contraseña.
     * @return Contraseña.
     */
    public String getContrasenna() {
        return contrasenna;
    }
    
    /**
     * Establecer contraseña.
     * @param contrasenna Contraseña.
     */
    public void setContrasenna(String contrasenna) {
        this.contrasenna = contrasenna;
    }

    /**
     * Obtener correo.
     * @return Correo.
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * Establecer correo.
     * @param correo correo.
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    
    /**
     * Obtener rol.
     * @return Rol.
     */
    public Rol getRol() {
        return rol;
    }

    /**
     * Establecer rol.
     * @param rol Rol.
     */
    public void setRol(Rol rol) {
        this.rol = rol;
    }

    /**
     * Obtener código rol.
     * @return Código rol.
     */
    public String getCodRol() {
        return codRol;
    }

    /**
     * Establecer código de rol.
     * @param codRol Código de rol.
     */
    public void setCodRol(String codRol) {
        this.codRol = codRol;
    }

    /**
     * Obtener descripción del rol.
     * @return Descripción del rol
     */
    public String getDescRol() {
        return descRol;
    }

    /**
     * Establecer descripción de rol.
     * @param descRol Descripción de rol.
     */
    public void setDescRol(String descRol) {
        this.descRol = descRol;
    }

    /**
     * Obtener estado.
     * @return Estado.
     */
    public Estado getEstado() {
        return estado;
    }

    /**
     * Establecer estado.
     * @param estado Estado.
     */
    public void setEstado(Estado estado) {
        this.estado = estado;
    }

}
