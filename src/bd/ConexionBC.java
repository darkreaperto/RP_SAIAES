/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bd;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;

/**
 * Realiza la conexión con la base de datos y envía consultas y
 * procedimientos a la misma.
 * @author dark-reaper
 */
public final class ConexionBC {

//    private static Conexion instancia = new Conexion();
//    private Connection conexion;
//    private Statement sentencia;
//    private ResultSet resultado;
//    private CallableStatement procedimiento;
//    
//    /**
//     * Constructor de la clase conexión.
//     */
//    private Conexion() {
//        abrirConexion();
//    }
//    /**
//     * Retorna la única instancia de la clase conexión.
//     * @return instancia de la clase.
//     */
//    public static Conexion getInstancia() {
//        if (instancia == null) {
//            instancia = new Conexion();
//        }
//        return instancia;
//    }
//
//    /**
//     * Abre la conexión para manipular la base de datos.
//     * @return el estado de la conexión.
//     */
//    public boolean abrirConexion() {
//        boolean conexionExitosa = false;
//        try {
//            conexion = DriverManager.getConnection("jdbc:mysql://localhost/"
//                    + "sai_aes?useSSL=false&useUnicode=true&"
//                    + "useJDBCCompliantTimezoneShift=true&"
//                    + "useLegacyDatetimeCode=false&serverTimezone=GMT-06:00",
//                    "usuario", "usuario2018");
//
//
////                conexion = DriverManager.getConnection("jdbc:mysql://localhost/"
////                    +"sai_aes?serverTimezone=GMT-06:00",
////                    "usuario", "usuario2018");//serverTimezone=GMT-06:00
//            conexionExitosa = true;
//        } catch (SQLException ex) {
//            conexionExitosa = false;
//            System.err.println(ex);
//        } finally {
//            return conexionExitosa;
//        }
//    }
//    
//    /**
//     * Cierra la conexión con la base de datos, no permite manipularla.
//     * @return el estado de la desconexión.
//     */
//    public boolean cerrarConexion() {
//        boolean desconexionExitosa = false;
//        try {
//            conexion.close();
//            desconexionExitosa = true;
//        } catch (SQLException ex) {
//            desconexionExitosa = false;
//            System.err.println(ex);
//        } finally {
//            return desconexionExitosa;
//        }
//    }
//    
//    /**
//     * Ejecuta en la base de datos la consulta SQL que recibe por parámetro.
//     * @param consulta consulta SQL
//     * @return resultado de la consulta SQL.
//     * @throws SQLException excepción SQL
//     */
//    public ResultSet ejecutarConsulta(String consulta) throws SQLException {
//
//        sentencia = conexion.createStatement();
//        resultado = sentencia.executeQuery(consulta);
//        return resultado;
//    }
//    
//    /**
//     * Ejecuta una modificación en la base de datos (insert, update, delete)
//     * @param consulta consulta SQL
//     * @return resultado de la modificacion.
//     * @throws SQLException Excepción SQL
//     */
//    public boolean ejecutarActualizar(String consulta) throws SQLException {
//
//        boolean actualizacionExitosa = false;
//
//        try {
//            sentencia = conexion.createStatement();
//            actualizacionExitosa = sentencia.executeUpdate(consulta) >= 0;
//        } catch (SQLException ex) {
//            actualizacionExitosa = false;
//            throw ex;
//        } finally {
//            return actualizacionExitosa;
//        }
//    }
//
//    /**
//     * Ejecuta un procedimiento almacenado sin parámetros de entrada 
//     * en la base de datos.
//     * @param procedure procedimiento a ejecutar
//     * @return resultado del procedimiento.
//     * @throws java.sql.SQLException Excepción SQL
//     */
//    public ResultSet ejecutarProcedimiento(String procedure) 
//            throws SQLException {
//        
//        procedimiento = conexion.prepareCall("{ CALL " + procedure + " }");
//        resultado = procedimiento.executeQuery();
//        
//        return resultado;
//        
//    }
//
//    /**
//     * Ejecuta un procedimiento almacenado con parámetros de entrada 
//     * en la base de datos.
//     * @param procedure Procedimiento a ejecutar
//     * @param params Parametros del procedimiento
//     * @return resultado del procedimiento.
//     * @throws java.sql.SQLException Excepción SQL
//     */
//    public ResultSet ejecutarProcedimiento(String procedure,
//            ArrayList<Object> params) throws SQLException {
//        
//        procedimiento = conexion.prepareCall("{ CALL "
//                + procedure + " }");
//
//        //Recorrer la lista de parametros a recibir 
//        //por el procedimiento almacenado
//        for (int i = 0; i < params.size(); i++) {
//            //Si el parametro es entero
//            if (params.get(i) instanceof Integer){
//                int temp = (int) params.get(i);
//                procedimiento.setInt(i + 1, temp);
//            } else if (params.get(i) instanceof Float) {
//                float temp =  (float) params.get(i);
//                procedimiento.setFloat(i + 1, temp);
//            } else if (params.get(i) instanceof Double) {
//                double temp =  (double) params.get(i);
//                procedimiento.setDouble(i + 1, temp);
//            } else if (params.get(i) instanceof String) {
//                String temp = (String) params.get(i);
//                procedimiento.setString(i + 1, temp);
//            } else if (params.get(i) instanceof Date) {
//                Date temp = (Date) params.get(i);
//                procedimiento.setDate(i + 1, temp);
//            } else if (params.get(i) instanceof Timestamp) {
//                Timestamp temp = (Timestamp) params.get(i);
//                procedimiento.setTimestamp(i + 1, temp);
//            } else if (params.get(i) instanceof Boolean) {
//                boolean temp =  (boolean) params.get(i);
//                procedimiento.setBoolean(i + 1, temp);
//            } else if (params.get(i) == null) {
//                int temp = Integer.valueOf(params.get(i).toString());
//                procedimiento.setNull(i + 1, temp);
//            } else if (params.get(i) instanceof Types) {
//                int temp = Integer.valueOf(params.get(i).toString());
//                procedimiento.registerOutParameter(i + 1, temp);
//            }
//        }
//
//        resultado = procedimiento.executeQuery();
//        return resultado;
//    }    
}
