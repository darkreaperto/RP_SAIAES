/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacion;

import bd.Conexion;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author ahoihanabi
 */
public class Principal {
    
    /** 
     * Hace visible la ventana principal
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
        
        Conexion conn = Conexion.getInstancia();
        conn.abrirConexion();
        
        ResultSet res = conn.ejecutarProcedimiento("pc_obtener_usuarios()");
        
        while (res.next()) {
            System.out.println(res.getString("cod_Usuarios"));
            System.out.println(res.getString("nombre_Usuarios"));
        }
        
        conn.cerrarConexion();
        
        FrmPrincipal ventana = new FrmPrincipal();
        ventana.setVisible(true);
        ventana.setLocationRelativeTo(null);
        
    }
}
