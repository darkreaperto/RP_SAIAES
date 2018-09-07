/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bd;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author dark-reaper
 */
public final class Conexion {

    private static Conexion instancia = new Conexion();
    private Connection conexion;
    private Statement sentencia;
    private ResultSet resultado;
    private CallableStatement procedimiento;

    public Conexion() {

    }
    /**
     * Retorna la única instancia de la clase conexión.
     * @return instancia de la clase.
     */
    public static Conexion getInstancia() {
        if (instancia == null) {
            instancia = new Conexion();
        }
        return instancia;
    }

    /**
     * Abre la conexión para manipular la base de datos.
     * @return el estado de la conexión.
     */
    public boolean abrirConexion() {
        boolean conexionExitosa = false;
        try {
            conexion = DriverManager.getConnection("jdbc:mysql://localhost/"
                    + "sai_aes?useSSL=false",
                    "usuario", "usuario2018");
            conexionExitosa = true;
        } catch (SQLException ex) {
            conexionExitosa = false;
            System.err.println(ex);
        } finally {
            return conexionExitosa;
        }
    }
    
    /**
     * Cierra la conexión con la base de datos, no permite manipularla.
     * @return el estado de la desconexión.
     */
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
    
    /**
     * Ejecuta en la base de datos la consulta SQL que recibe por parámetro.
     * @param consulta
     * @return resultado de la consulta SQL.
     * @throws SQLException 
     */
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
    
    /**
     * Ejecuta una modificación en la base de datos (insert, update, delete)
     * @param consulta
     * @return resultado de la modificacion.
     * @throws SQLException 
     */
    public boolean ejecutarActualizar(String consulta) throws SQLException {

        boolean actualizacionExitosa = false;

        try {
            sentencia = conexion.createStatement();
            actualizacionExitosa = sentencia.executeUpdate(consulta) >= 0;
        } catch (SQLException ex) {
            actualizacionExitosa = false;
            System.err.println(ex);
        } finally {
            return actualizacionExitosa;
        }
    }

    /**
     * Ejecuta un procedimiento almacenado sin parámetros de entrada 
     * en la base de datos.
     * @param procedure
     * @return resultado del procedimiento.
     */
    public ResultSet ejecutarProcedimiento(String procedure) {
        try {
            procedimiento = conexion.prepareCall("{ CALL " + procedure + " }");
            resultado = procedimiento.executeQuery();
        } catch (SQLException ex) {
            System.err.println(ex);
        } finally {
            return resultado;
        }
    }

    /**
     * Ejecuta un procedimiento almacenado con parámetros de entrada 
     * en la base de datos.
     * @param procedure
     * @param params
     * @return resultado del procedimiento.
     */
    public ResultSet ejecutarProcedimiento(String procedure,
            ArrayList<Object> params) throws SQLException {
        //try {
            procedimiento = conexion.prepareCall("{ CALL "
                    + procedure + " }");

            //Recorrer la lista de parametros a recibir 
            //por el procedimiento almacenado
            for (int i = 0; i < params.size(); i++) {
                //Si el parametro es un String
                if (params.get(i) instanceof String) {
                    String temp = (String) params.get(i);
                    procedimiento.setString(i + 1, temp);
                    //Si el parametro es entero
                } else {
                    int temp = (int) params.get(i);
                    procedimiento.setInt(i + 1, temp);
                }
            }

            resultado = procedimiento.executeQuery();
        //} catch (SQLException ex) {
            //throw ex;
        //} finally {
            return resultado;
        //}
    }
}
