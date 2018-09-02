/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import controladores.CtrAcceso;
import controladores.CtrUsuario;

/**
 *
 * @author ahoihanabi
 */
public class Acceso {
    
    private String usuario;
    private Usuario usuario1;
    
    
    public Acceso() {       
    }
    
    public void setUsuario(String user) {
        usuario = user;
    }
    
//    public String getUsuario() {
//        return usuario;
//    }
    
    public void setUsuario(Usuario user) {
        usuario1 = user;
    }
    
    public Usuario getUsuario() {
        return usuario1;
    }
    
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
