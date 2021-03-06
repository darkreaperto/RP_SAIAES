/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import logica.servicios.AESEncrypt;
import bd.Conexion;
import java.sql.ResultSet;
import logica.servicios.Logger;

/**
 * Modelo de acceso con los procedimientos y consultas de base de datos
 * @author ahoihanabi
 */
public class MdlAcceso {
    
    Conexion conexion; 
    private static AESEncrypt crypter;

    /**
     * Constructor de clase modelo de acceso.
     */
    public MdlAcceso() {
        conexion = Conexion.getInstancia();
        crypter = new AESEncrypt();
        crypter.addKey("SAI");
    }
    /**
     * Compara la clave ingresada con la clave almacenada en la base de datos.
     * 
     * @param user nombre de usuario para el acceso.
     * @param pass contraseña de acceso.
     * @return verdadero o falso si las claves coinciden.
     */
    public boolean compararClave(String user, String pass) {
        
        boolean go = false;
        try {
            conexion.abrirConexion();
            
            String sql = "SELECT nombre_Usuarios, clave_Usuarios "
                    + "FROM usuarios "
                    + "WHERE nombre_Usuarios = '" + user + "'";
            
            ResultSet rs = conexion.ejecutarConsulta(sql);
            
            String usernameBD = "";
            String conBD = "";
            
            while (rs.next()) {
                usernameBD = rs.getString("nombre_Usuarios");
                conBD = rs.getString("clave_Usuarios");
                //System.out.println("con " + conBD);
            }
            
            pass = crypter.encriptar(pass);
            if (user.equals(usernameBD)) {
                System.out.println("USER " + user + " USERBD " + usernameBD);
                System.out.println("CON " + pass + " CONBD " + conBD);
                if (conBD.equals(pass)){
                    go = true;
                } else {
                    go = false;
                }
            }
        } catch (Exception ex) {
            Logger.registerNewError(ex);
            ex.printStackTrace();
        } finally {
            conexion.cerrarConexion();
        }
        return go;
    }
}
