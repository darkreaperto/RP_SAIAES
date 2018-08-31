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
    
    /**
     * 
     */
    public Conexion() {
        
    }
    
    public static Conexion getInstancia() {
        if (instancia == null) {
            instancia = new Conexion();
        }
        return instancia;
    }
    
    public boolean abrirConexion() {
        boolean conexionExitosa = false;
        try {
            //?useSSL=false
            conexion = DriverManager.getConnection("jdbc:mysql://localhost/" + 
                                                   "sai_aes?useSSL=false", 
                                                   "usuario", "usuario2018");
            conexionExitosa = true;
        } catch (SQLException ex) {
            conexionExitosa = false;
            System.err.println(ex);
        } finally {
            return conexionExitosa;
        }
    }
    
    public boolean cerrarConexion() {
        boolean desconexionExitosa = false;
        try {
            conexion.close();
            desconexionExitosa = true;
        } catch (SQLException ex) {
            desconexionExitosa = false;
            System.err.println(ex);
        } finally {
            return desconexionExitosa;
        }
    }
    
    public ResultSet ejecutarConsulta(String consulta) throws SQLException {
        try {
            sentencia = conexion.createStatement();
            resultado = sentencia.executeQuery(consulta);
            
        } catch (SQLException ex) {
            System.err.println(ex);
        } finally {
            return resultado;
        }
    }
    
    public boolean ejecutarActualizar(String consulta) throws SQLException {
        
        boolean actualizacionExitosa = false;
        
        try {
            actualizacionExitosa = sentencia.executeUpdate(consulta) >= 0;
        } catch (SQLException ex) {
            actualizacionExitosa = false;
            System.err.println(ex);
        } finally {
            return actualizacionExitosa;
        }
    }
}
