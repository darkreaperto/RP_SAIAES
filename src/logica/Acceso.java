/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;
import bd.Conexion;
import bd.AESEncrypt;
import java.sql.ResultSet;
/**
 *
 * @author ahoihanabi
 */
public class Acceso {
    
    private String usuario;
//    private static Conexion conexion = new Conexion();
//    private AESEncrypt crypter = new AESEncrypt();
    
    public Acceso() {
        //crypter.addKey("SAI");        
    }
    
    public void setUsuario(String user) {
        usuario = user;
    }
    
    public String getUsuario() {
        return usuario;
    }
    
//    public boolean comparacion(String user, String pass) {
//        boolean go = false;
//        try {
//            conexion.abrirConexion();
//            
//            String sql = "SELECT nombre_Usuarios, clave_Usuarios FROM Usuarios "
//                    + "WHERE nombre_Usuarios = '" + user + "'";
//            
//            ResultSet rs = conexion.ejecutarConsulta(sql);
//            
//            String usernameBD = "";
//            String conBD = "";
//            
//            while (rs.next()) {
//                usernameBD = rs.getString("nombre_Usuarios");
//                conBD = rs.getString("clave_Usuarios");
//                //System.out.println("con " + conBD);
//            }
//            
//            pass = crypter.encriptar(pass);
//            if (user.equals(usernameBD)) {
//                System.out.println("USER " + user + " USERBD " + usernameBD);
//                System.out.println("CON " + pass + " CONBD " + conBD);
//                if (conBD.equals(pass)){
//                    go = true;
//                } else {
//                    go = false;
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            conexion.cerrarConexion();
//        }
//        return go;
//    }
//    private boolean cambiarContrasenna(String pass, String newPass) {
//
//        boolean go = false;
//        try {
//
//            pass = crypter.encriptar(pass);
//            newPass = crypter.encriptar(newPass);
//
//            System.out.println("pass " + pass);
//            System.out.println("newPass " + newPass);
//            
//            Conexion conexion1 = new Conexion();
//            
//            String sqlito = "UPDATE Usuarios SET clave_Usuarios = '" + newPass
//                    + "' WHERE nombre_Usuarios = '" + username + "'";
//            int res = conexion1.ejecutarActualizar(sqlito);
//            
//            System.out.println(sqlito);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return go;
//    }    
}
