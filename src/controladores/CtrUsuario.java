/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import java.util.ArrayList;
import logica.negocio.Usuario;
import modelos.MdlUsuario;
import util.Estado;
import util.Rol;
/**
 * Controlador de la clase Usuario.
 * @author ahoihanabi
 */
public class CtrUsuario {
    
    private static CtrUsuario instancia = null;
    MdlUsuario mdlUsuario;
    Usuario usuario;
    ArrayList <Usuario> usuarios;
    
    /**
     * Constructor del controlador de usuario, inicializa variables.
     */
    public CtrUsuario() {
        usuarios = new ArrayList<>();
        mdlUsuario = new MdlUsuario();
    }
    
    /**
     * Constructor del controlador de usuario con parámetros,
     * inicializa variables.
     * @param codigo Código de usuario.
     * @param nombre Nombre de usuario.
     * @param contrasenna Contrasenna de usuario.
     * @param correo Correo de usuario.
     * @param codRol Codigo del Rol de usuario.
     * @param descRol Descripción de usuario.
     * @param estado Estado de usuario.
     */
    public CtrUsuario(String codigo, String nombre, String contrasenna, 
            String correo, String codRol, String descRol, String estado) {
        usuario = new Usuario(codigo, nombre, contrasenna, correo, codRol, 
                descRol, estado);
    }
    
    /**
     * Obtener instancia única del controlador de usuario
     * @return Instancia única de Usuario
     */
    public static CtrUsuario getInstancia() {
        return instancia == null ? new CtrUsuario() : instancia;
    }
 
//    public void addUsers(String codigo, String nombre, String contrasenna, 
//            String correo, String codRol, String descRol, String estado) {
//        usuario =  new Usuario(codigo, nombre, contrasenna, correo, codRol, 
//                descRol, estado);
//        usuarios.add(usuario);
//    }
    
    /**
     * Obtener código.
     * @return Código.
     */
    public String getCodigo() {
        return usuario.getCodigo();
    }
    
    /**
     * Establece Código de usuario.
     * @param codigo código de usuario
     */
    public void setCodigo(String codigo) {
        usuario.setCodigo(codigo);
    }
    
    /**
     * Obtener nombre.
     * @return Nombre.
     */
    public String getNombre() {
        return usuario.getNombre();
    }
    
    /**
     * Establece nombre de usuario.
     * @param nombre Nombre de usuario.
     */
    public void setNombre(String nombre) {
        usuario.setNombre(nombre);
    }

    /**
     * Obtener contraseña.
     * @return contraseña.
     */
    public String getContrasenna() {
        return usuario.getContrasenna();
    }

    /**
     * Establece contrasenna de usuario.
     * @param contrasenna contraseña.
     */
    public void setContrasenna(String contrasenna) {
        usuario.setContrasenna(contrasenna);
    }
    
    /**
     * Obtener correo.
     * @return Correo.
     */
    public String getCorreo() {
        return usuario.getCorreo();
    }
    
    /**
     * Establece correo de usuario.
     * @param correo correo.
     */
    public void setCorreo(String correo) {
        usuario.setCorreo(correo);
    }
    
    /**
     * Obtener rol.
     * @return Rol.
     */
    public Rol getRol() {
        return usuario.getRol();
    }
    
    /**
     * Establece rol de usuario.
     * @param rol Rol.
     */
    public void setRol(Rol rol) {
        usuario.setRol(rol);
    }
    
    /**
     * Obtener código.
     * @return Código.
     */
    public String getCodRol() {
        return usuario.getCodRol();
    }
    
    /**
     * Establece codigo de rol de usuario.
     * @param codRol código de rol
     */
    public void setCodRol(String codRol) {
        usuario.setCodRol(codRol);
    }
    
    /**
     * Obtener descripción de rol.
     * @return Descripción de rol.
     */
    public String getDescRol() {
        return usuario.getDescRol();
    }
    
    /**
     * Establece descripción del rol de usuario.
     * @param descRol descripción del rol.
     */
    public void setDescRol(String descRol) {
        usuario.setDescRol(descRol);
    }
    
    /**
     * Obtener estado.
     * @return Estado.
     */
    public Estado getEstado() {
        return usuario.getEstado();
    }
    
    /**
     * Establece estado de usuario.
     * @param estado estado del usuario
     */
    public void setEstado(Estado estado) {
        usuario.setEstado(estado);
    }
    
    /**
     * Llama el método que llena una lista con todos los usuarios almacenados 
     * en la BD.
     * @return la lista de todos los usuarios.
     */
    public ArrayList<Usuario> obtenerUsuarios() {
        return mdlUsuario.obtenerUsuarios();
    }
    
    /**
     * Llena método que inserta un nuevo usuario en la BD.
     * @param nombre Nombre de usuario.
     * @param contra Contraseña de usuario.
     * @param correo Correo de usuario.
     * @param rol Rol de usuario.
     * @return Verdadero si la creación fue exitosa.
     */
    public boolean crearUsuario(String nombre, String contra, String correo, 
            Rol rol) {
        return mdlUsuario.crearUsuario(nombre, contra, correo, rol);
    }
    
    /**
     * Llama método que actualiza toda la información del usuario en la BD.
     * @param nombre Nombre de usuario.
     * @param contra Contraseña de usuario.
     * @param correo Correo de usuario.
     * @param rol Rol de usuario.
     * @param estado estado a actualizar.
     * @param codigo código del cliente a actualizar.
     * @return verdadero si la actualización fue éxitosa.
     */
    public boolean actualizarUsuario( String nombre, String contra, String correo, 
            Rol rol, Estado estado, String codigo) {
        return mdlUsuario.actualizarUsuario(nombre, contra, correo, rol, estado, codigo);
    }
    /**
     * Llama método que actualiza toda la información del usuario en la BD.
     * @param nombre Nombre de usuario.
     * @param rol Rol de usuario.
     * @return verdadero si la actualización fue éxitosa.
     */
    public boolean actualizarRolUsuario( String nombre, Rol rol) {
        return mdlUsuario.actualizarRolUsuario(nombre, rol);
    }
    /**
     * Llama el método que actualiza únicamente la contraseña en la BD.
     * @param nombre Nombre de usuario.
     * @param contra Contraseña de usuario.
     * @return Verdadero si se restableció correctamente.
     */
    public boolean restablecerClave(String nombre, String contra) {
        return mdlUsuario.restablecerClave(nombre, contra);
    }
    
    /**
     * Llama método que busca un usuario enviando por parámetro el criterio de 
     * búsqueda.
     * @param param parámetro de consulta.
     * @return Usuario encontrado.
     */
    public ArrayList consultarUsuarios(String param) {
        return mdlUsuario.consultarUsuarios(param);
    }
}
