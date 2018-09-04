/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import bd.AESEncrypt;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import logica.Usuario;
import controladores.CtrConexion;
import util.Estado;
import util.Rol;

/**
 *
 * @author ahoihanabi
 */
public class MdlUsuario {

    private static CtrConexion conexion;
    private static AESEncrypt crypter;
    private static String procedimiento;
    private static ResultSet resultado;
    private static ArrayList<Usuario> usuarios;

    public MdlUsuario() {
        conexion = new CtrConexion();

        crypter = new AESEncrypt();
        crypter.addKey("SAI");
    }

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

    public boolean crearUsuario(String nombre, String contra, String correo,
            Rol rol) {

        //Código de rol de usuario. 1: Administrador, 2: Estándar
        int codRol = rol.equals(Rol.Administrador) ? 1 : 2;
        ArrayList<Object> params = new ArrayList<>();
        params.add(nombre);
        params.add(contra);
        params.add(correo);
        params.add(codRol);

        boolean creacionExitosa = false;
        try {
            procedimiento = "pc_crear_usuario(?, ?, ?, ?)";

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
            System.err.println(ex);
        } finally {
            conexion.cerrarConexion();
            return creacionExitosa;
        }
    }

    public boolean actualizarUsuario(String nombre, String contra, String correo,
            Rol rol, Estado estado, String codigo) {
        //Código de rol de usuario. 1: Administrador, 2: Estándar
        int codRol = rol.equals(Rol.Administrador) ? 1 : 2;
        //contra = crypter.encriptar(contra);
        String state = estado.equals(Estado.Activo) ? "A" : "I";
        boolean res = false;
        try {
            String consulta = "UPDATE Usuarios"
                    + " SET nombre_Usuarios = '" + nombre + "', "
                    + " clave_Usuarios = '" + contra + "' , "
                    + " correo_Usuarios = '" + correo + "', "
                    + " cod_RolUsuar = " + codRol + ", "
                    + " estado_Usuarios = '" + state + "' "
                    + " WHERE cod_Usuarios = " + codigo + ";";
            conexion.abrirConexion();
            res = conexion.ejecutarActualizar(consulta);

        } catch (SQLException ex) {
            System.err.println(ex);
        } finally {
            conexion.cerrarConexion();
        }
        return res;
    }
}
