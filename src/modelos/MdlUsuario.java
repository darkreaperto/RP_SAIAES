/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import logica.servicios.AESEncrypt;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import logica.negocio.Usuario;
import controladores.CtrConexion;
import logica.servicios.Mensaje;
import util.Estado;
import util.Rol;

/**
 * Modelo de usuario con los procedimientos y consultas de base de datos
 * @author ahoihanabi
 */
public class MdlUsuario {

    /** Controlador de conexión. */
    private static CtrConexion conexion;
    /** Variable para encriptar datos. */
    private static AESEncrypt crypter;
    /** Procedimiento a ejecutar en la base. */
    private static String procedimiento;
    /** Resultado de las consultas a la base. */
    private static ResultSet resultado;
    /** Lista de usuarios en la base. */
    private static ArrayList<Usuario> usuarios;
    /** Mensaje de error desde la base. */
    private static Mensaje msgError;

    /**
     * Constructor de clase modelo de usuario.
     */
    public MdlUsuario() {
        conexion = new CtrConexion();
        crypter = new AESEncrypt();        
        crypter.addKey("SAI");
        msgError = new Mensaje();
    }

    /**
     * Llena una lista con todos los usuarios almacenados en la BD.
     *
     * @return lista de usuarios.
     */
    public ArrayList<Usuario> obtenerUsuarios() {
        usuarios = new ArrayList<>();

        try {
            procedimiento = "pc_obtener_usuarios()";
            conexion.abrirConexion();
            resultado = conexion.ejecutarProcedimiento(procedimiento);

            String codUsuario;
            String nombreUsuario;
            String claveUsuario;
            String correoUsuario;
            String codRolUsuario;
            String descRolUsuario;
            String estadoUsuario;

            while (resultado.next()) {
                codUsuario = resultado.getString("cod_Usuarios");
                nombreUsuario = resultado.getString("nombre_Usuarios");
                claveUsuario = resultado.getString("clave_Usuarios");
                correoUsuario = resultado.getString("correo_Usuarios");
                codRolUsuario = resultado.getString("cod_RolUsuar");
                descRolUsuario = resultado.getString("desc_RolUsuar");
                estadoUsuario = resultado.getString("estado_Usuarios");

                Usuario usuario
                        = new Usuario(codUsuario, nombreUsuario, claveUsuario,
                                correoUsuario, codRolUsuario, descRolUsuario, 
                                estadoUsuario);

                if (!usuarios.contains(usuario)) {
                    usuarios.add(usuario);
                }
            }
        } catch (SQLException ex) {
            System.err.println(ex);
        } finally {
            conexion.cerrarConexion();
            return usuarios;
        }
    }

    /**
     * Inserta un nuevo usuario en la BD
     *
     * @param nombre nuevo nombre de usuario
     * @param contra nueva contraseña del usuario
     * @param correo nuevo correo del usuario
     * @param rol nuevo rol del usuario
     * @return true si inserta el usuario.
     */
    public boolean crearUsuario(String nombre, String contra, String correo,
            Rol rol) {

        //Código de rol de usuario. 1: Administrador, 2: Estándar
        int codRol = rol.equals(Rol.Administrador) ? 1 : 2;
        ArrayList<Object> params = new ArrayList<>();
        params.add(nombre);
        params.add(contra);
        params.add(correo);
        params.add(codRol);

        boolean creacionExitosa = true;
        try {
            procedimiento = "pc_crear_usuario(?, ?, ?, ?)";

            conexion.abrirConexion();
            resultado = conexion.ejecutarProcedimiento(procedimiento, params);
            //creacionExitosa = true;
            System.out.println(resultado);

        } catch (SQLException ex) {
            System.err.println(ex);            
            creacionExitosa = false;
            System.out.println("ERROR SQL " + ex.getErrorCode());
            msgError.mostrarMensajeErrorSQL(ex.getErrorCode());
        } finally {
            conexion.cerrarConexion();
            return creacionExitosa;
        }
    }

    /**
     * Actualiza únicamente la contraseña en la BD.
     *
     * @param nombre Nombre de usuario
     * @param contra nueva contraseña del usuario
     * @return true si actualiza.
     */
    public boolean restablecerClave(String nombre, String contra) {

        ArrayList<Object> params = new ArrayList<>();
        params.add(nombre);
        params.add(contra);

        boolean creacionExitosa = false;
        try {
            procedimiento = "pc_restablecer_clave(?, ?)";

            conexion.abrirConexion();
            resultado = conexion.ejecutarProcedimiento(procedimiento, params);
            creacionExitosa = true;

        } catch (SQLException ex) {
            creacionExitosa = false;
            //msgError.mostrarMensajeErrorSQL(ex.getErrorCode());
            System.err.println(ex);
        } finally {
            conexion.cerrarConexion();
            return creacionExitosa;
        }
    }

    /**
     * Actualiza toda la información del usuario en la BD.
     * @param nombre Nombre de usuario
     * @param contra Contraseña de usuario
     * @param correo Correo brindado por el usuario
     * @param rol Rol del usuario
     * @param estado Estado del usuario
     * @param codigo Codigo para identificar el usuario
     * @return true si actualiza.
     */
    public boolean actualizarUsuario(String nombre, String contra, String correo,
            Rol rol, Estado estado, String codigo) {
        
        int codRol = rol.equals(Rol.Administrador) ? 1 : 2;
        String varEstado = estado.equals(Estado.Activo) ? "A" : "I";

        ArrayList<Object> params = new ArrayList<>();
        params.add(nombre);
        params.add(contra);
        params.add(correo);
        params.add(codRol);
        params.add(varEstado);
        params.add(codigo);

        boolean creacionExitosa = false;
        try {
            procedimiento = "pc_actualizar_usuario(?, ?, ?, ?, ?, ?)";

            conexion.abrirConexion();
            resultado = conexion.ejecutarProcedimiento(procedimiento, params);
            creacionExitosa = true;

        } catch (SQLException ex) {
            creacionExitosa = false;
            System.err.println(ex);
        } finally {
            conexion.cerrarConexion();
            return creacionExitosa;
        }
    }

    /**
     * Buscar usuario enviando por parámetro el criterio de búsqueda.
     * @param param Parametros para consultar usuario en la base de datos
     * @return lista de usuarios
     */
    public ArrayList consultarUsuarios(String param) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(param);

        usuarios = new ArrayList<>();

        try {
            procedimiento = "pc_consultar_usuarios(?)";
            conexion.abrirConexion();
            resultado = conexion.ejecutarProcedimiento(procedimiento, params);

            String codUsuario;
            String nombreUsuario;
            String claveUsuario;
            String correoUsuario;
            String codRolUsuario;
            String descRolUsuario;
            String estadoUsuario;

            while (resultado.next()) {
                codUsuario = resultado.getString("cod_Usuarios");
                nombreUsuario = resultado.getString("nombre_Usuarios");
                claveUsuario = resultado.getString("clave_Usuarios");
                correoUsuario = resultado.getString("correo_Usuarios");
                codRolUsuario = resultado.getString("cod_RolUsuar");
                descRolUsuario = resultado.getString("desc_RolUsuar");
                estadoUsuario = resultado.getString("estado_Usuarios");

                Usuario usuario
                        = new Usuario(codUsuario, nombreUsuario, claveUsuario,
                                correoUsuario, codRolUsuario, descRolUsuario, estadoUsuario);

                if (!usuarios.contains(usuario)) {
                    usuarios.add(usuario);
                }
            }
        } catch (SQLException ex) {
            System.err.println(ex);
        } finally {
            conexion.cerrarConexion();
            return usuarios;
        }
    }
}
