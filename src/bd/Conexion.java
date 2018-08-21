/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author dark-reaper
 */
public final class Conexion {
    
    private static Conexion instancia = new Conexion();
    private Connection conexion;
    private Statement sentencia;
    private ResultSet resultado;
    
    public Conexion() {
        
    }
    
    public void abrirConexion() {
        try {
            //?useSSL=false
            conexion = DriverManager.getConnection("jdbc:mysql://localhost/" + 
                                                   "sai_aes?useSSL=false", 
                                                   "usuario", "usuario2018");
            
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }
    
    public void cerrarConexion() {
        try {
            conexion.close();
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }
    
    public static Conexion getInstancia() {
        if (instancia == null) {
            instancia = new Conexion();
        }
        return instancia;
    }
    
    public ResultSet ejecutarConsulta(String consulta) throws SQLException {
        try {
            //abrirConexion();
            //sentencia.executeQuery(consulta);
            sentencia = conexion.createStatement();
            resultado = sentencia.executeQuery(consulta);
            
        }
        catch (SQLException ex) {
            System.err.println(ex);
        }
        finally {
            //cerrarConexion();
        }
        return resultado;
    }
    
    public int ejecutarActualizar(String consulta) throws SQLException {
        int res = 0;
        try {
            //abrirConexion();
            //sentencia.executeQuery(consulta);
            sentencia = conexion.createStatement();
            sentencia.executeUpdate(consulta);
        }
        catch (SQLException ex) {
            System.err.println(ex);
        }
        finally {
            //cerrarConexion();
        }
        return res;
    }
}
