/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import bd.Conexion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Controlador de la clase Conexion
 * @author dark-reaper
 */
public class CtrConexion {
    
    private static Conexion conexion;
    
    public CtrConexion() {
        conexion = Conexion.getInstancia();
    }
    
    public boolean abrirConexion() {
        return conexion.abrirConexion();
    }
    
    public boolean cerrarConexion() {
        return conexion.cerrarConexion();
    }
    
    public ResultSet ejecutarConsulta(String consulta) throws SQLException {
        return conexion.ejecutarConsulta(consulta);
    }
    
    public boolean ejecutarActualizar(String consulta) throws SQLException {
        return conexion.ejecutarActualizar(consulta);
    }
    
    public ResultSet ejecutarProcedimiento(String procedure) throws SQLException {
        return conexion.ejecutarProcedimiento(procedure);
    }
    
    public ResultSet ejecutarProcedimiento(String procedure, 
            ArrayList<Object> params) throws SQLException {
        return conexion.ejecutarProcedimiento(procedure, params);
    }
}
