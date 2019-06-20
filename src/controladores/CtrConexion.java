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
    
    /**
     * Constructor del controlador de Conexión, inicializa variable.
     */
    public CtrConexion() {
        conexion = Conexion.getInstancia();
    }
    /**
     * Llama el método que abre la conexión con la base de datos.
     * @return Verdadero si se abrió correctamente.
     */
    public boolean abrirConexion() {
        return conexion.abrirConexion();
    }
    
    /**
     * Llama el método que cierra la conexión con la base de datos.
     * @return Verdadero si se cerró exitosamente.
     */
    public boolean cerrarConexion() {
        return conexion.cerrarConexion();
    }
    
    /**
     * Llama el método que ejecuta una consulta en la base de datos.
     * @param consulta consulta SQL  
     * @return Resultado de la consulta
     * @throws SQLException Excepción SQL
     */
    public ResultSet ejecutarConsulta(String consulta) throws SQLException {
        return conexion.ejecutarConsulta(consulta);
    }
    
    /**
     * Llama el método que ejecuta una modificación en la base de datos 
     * (insert, update, delete)
     * @param consulta consulta SQL
     * @return Verdadero si se ejecuta la consulta correctamente
     * @throws SQLException Excepción SQL
     */
    public boolean ejecutarActualizar(String consulta) throws SQLException {
        return conexion.ejecutarActualizar(consulta);
    }
    
    /**
     * Llama método que ejecuta un procedimiento almacenado sin parámetros de 
     * entrada
     * @param procedure procedimiento a ejecutar
     * @return Resultado del procedimiento a ejecutar
     * @throws SQLException excepción SQL
     */
    public ResultSet ejecutarProcedimiento(String procedure) throws SQLException {
        return conexion.ejecutarProcedimiento(procedure);
    }
    
    /**
     * Llama método que ejecuta un procedimiento almacenado con parámetros de 
     * entrada en la base de datos.
     * @param procedure procedimiento a ejecutar
     * @param params parametros del procedimiento
     * @return Resultado del procedimientos 
     * @throws SQLException Excepción SQL
     */
    public ResultSet ejecutarProcedimiento(String procedure, 
            ArrayList<Object> params) throws SQLException {
        return conexion.ejecutarProcedimiento(procedure, params);
    }
}
