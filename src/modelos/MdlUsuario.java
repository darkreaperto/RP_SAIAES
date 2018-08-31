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
import bd.Conexion;
import util.Estado;
import util.Rol;

/**
 *
 * @author ahoihanabi
 */
public class MdlUsuario {
    Conexion conexion; 
    private static AESEncrypt crypter;

    public MdlUsuario() {
        conexion = Conexion.getInstancia();
        crypter = new AESEncrypt();
        crypter.addKey("SAI");
    }
    
    public ArrayList<Usuario> obtenerUsuarios() {
        ArrayList<Usuario> usuarios = new ArrayList<>();
        try {
            
            String consulta = "SELECT cod_Usuarios, nombre_Usuarios, "
                            + "clave_Usuarios, correo_Usuarios, cod_RolUsuar, estado_Usuarios"
                            + " FROM Usuarios";            
            conexion.abrirConexion();
            ResultSet result = conexion.ejecutarConsulta(consulta);

            String codUsuario = "", nombreUsuario = "", claveUsuario = "", 
                   correoUsuario = "", codRolUsuario = "", estadoUsuario = "";

            while (result.next()) {
                codUsuario = result.getString("cod_Usuarios");
                nombreUsuario = result.getString("nombre_Usuarios");
                claveUsuario = result.getString("clave_Usuarios");
                correoUsuario = result.getString("correo_Usuarios");
                codRolUsuario = result.getString("cod_RolUsuar"); 
                estadoUsuario = result.getString("estado_Usuarios");
//                System.out.println("**Codigo: " + codUsuario + 
//                                    "\nNombre: " + nombreUsuario + 
//                                    "\nClave: " + claveUsuario + 
//                                    "\nRol: " + codRolUsuario);
                Usuario usuario = new Usuario(codUsuario, nombreUsuario, claveUsuario, 
                                        correoUsuario, codRolUsuario, estadoUsuario);
                if (!usuarios.contains(usuario))
                    usuarios.add(usuario);                
            }

        } catch (SQLException ex) {
            System.err.println(ex);
        }
        finally {
            conexion.cerrarConexion();
        }
        return usuarios;
    }
    
    public boolean crearUsuario(String nombre, String contra, String correo, 
            Rol rol) {
        
        //Código de rol de usuario. 1: Administrador, 2: Estándar
        int codRol = rol.equals(Rol.Administrador) ? 1 : 2;
        contra = crypter.encriptar(contra);
        
        boolean res = false;
        try {
            String consulta = "INSERT INTO `Usuarios`(`cod_Usuarios`, "
                    + "`nombre_Usuarios`, `clave_Usuarios`, `correo_Usuarios`,"
                    + " `cod_RolUsuar`, `estado_Usuarios`) "
                    + "VALUES (NULL, '" + nombre + "', '" + contra + "', '" 
                                + correo + "', " + codRol + ", 'A' )";
            
            conexion.abrirConexion();
            res = conexion.ejecutarActualizar(consulta) != -1;

        } catch (SQLException ex) {
            System.err.println(ex);
        }
        finally {
            conexion.cerrarConexion();
        }
        return res;
    }
    
    public boolean updateUsuario( String nombre, String contra, String correo, 
            Rol rol, Estado estado, int codigo) {
        //Código de rol de usuario. 1: Administrador, 2: Estándar
        int codRol = rol.equals(Rol.Administrador) ? 1 : 2;
        //contra = crypter.encriptar(contra);
        String state = estado.equals(Estado.Activo) ? "A" : "I";
        boolean res = false;
        try {
            String consulta =  "UPDATE Usuarios"+ 
                               " SET nombre_Usuarios = '"+nombre+"', "+ 
                               " clave_Usuarios = '"+contra+"' , correo_Usuarios = '"+correo+"', "+
                               " cod_RolUsuar = "+codRol+", estado_Usuarios = '"+state+"' "+
                               " WHERE cod_Usuarios = "+codigo+";";
            conexion.abrirConexion();
            res = conexion.ejecutarActualizar(consulta) != -1;

        } catch (SQLException ex) {
            System.err.println(ex);
        }
        finally {
            conexion.cerrarConexion();
        }
        return res;
    }
}
