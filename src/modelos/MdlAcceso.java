/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import bd.AESEncrypt;
import bd.Conexion;
import java.sql.ResultSet;

/**
 *
 * @author ahoihanabi
 */
public class MdlAcceso {
    
    Conexion conexion; 
    private static AESEncrypt crypter;

    public MdlAcceso() {
        conexion = Conexion.getInstancia();
        crypter = new AESEncrypt();
        crypter.addKey("SAI");
    }
    
    public boolean comparacion(String user, String pass) {
        
        boolean go = false;
        try {
            conexion.abrirConexion();
            
            String sql = "SELECT nombre_Usuarios, clave_Usuarios FROM Usuarios "
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
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conexion.cerrarConexion();
        }
        return go;
    }
}
