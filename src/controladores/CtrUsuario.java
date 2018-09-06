/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import java.util.ArrayList;
import logica.Usuario;
import modelos.MdlUsuario;
import util.Estado;
import util.Rol;
/**
 *
 * @author ahoihanabi
 */
public class CtrUsuario {
    
    private static CtrUsuario instancia = null;
    MdlUsuario mdlUsuario;
    Usuario usuario;
    ArrayList <Usuario> usuarios;

    public CtrUsuario() {
        usuarios = new ArrayList<>();
        mdlUsuario = new MdlUsuario();
    }
        
    public CtrUsuario(String codigo, String nombre, String contrasenna, 
            String correo, String codRol, String descRol, String estado) {
        usuario = new Usuario(codigo, nombre, contrasenna, correo, codRol, 
                descRol, estado);
    }
    
    public static CtrUsuario getInstancia() {
        return instancia == null ? new CtrUsuario() : instancia;
    }
    public void addUsers(String codigo, String nombre, String contrasenna, 
            String correo, String codRol, String descRol, String estado) {
        usuario =  new Usuario(codigo, nombre, contrasenna, correo, codRol, 
                descRol, estado);
        usuarios.add(usuario);
    }
    
    public String getCodigo() {
        return usuario.getCodigo();
    }
    
    public void setCodigo(String codigo) {
        usuario.setCodigo(codigo);
    }
    
    public String getNombre() {
        return usuario.getNombre();
    }

    public void setNombre(String nombre) {
        usuario.setNombre(nombre);
    }

    public String getContrasenna() {
        return usuario.getContrasenna();
    }

    public void setContrasenna(String contrasenna) {
        usuario.setContrasenna(contrasenna);
    }
    
    public String getCorreo() {
        return usuario.getCorreo();
    }

    public void setCorreo(String correo) {
        usuario.setCorreo(correo);
    }
    
    public Rol getRol() {
        return usuario.getRol();
    }
    
    public void setRol(Rol rol) {
        usuario.setRol(rol);
    }
    
    public String getCodRol() {
        return usuario.getCodRol();
    }
    
    public void setCodRol(String codRol) {
        usuario.setCodRol(codRol);
    }
    
    public String getDescRol() {
        return usuario.getDescRol();
    }
    
    public void setDescRol(String descRol) {
        usuario.setDescRol(descRol);
    }
    
    public Estado getEstado() {
        return usuario.getEstado();
    }

    public void setEstado(Estado estado) {
        usuario.setEstado(estado);
    }
    
    public ArrayList<Usuario> obtenerUsuarios() {
        return mdlUsuario.obtenerUsuarios();
    }
    
    public boolean crearUsuario(String nombre, String contra, String correo, 
            Rol rol) {
        return mdlUsuario.crearUsuario(nombre, contra, correo, rol);
    }
    
    public boolean actualizarUsuario( String nombre, String contra, String correo, 
            Rol rol, Estado estado, String codigo) {
        return mdlUsuario.actualizarUsuario(nombre, contra, correo, rol, estado, codigo);
    }
    
    public boolean restablecerClave(String nombre, String contra) {
        return mdlUsuario.restablecerClave(nombre, contra);
    }
    
    public ArrayList consultarUsuarios(String param) {
        return mdlUsuario.consultarUsuarios(param);
    }
}
