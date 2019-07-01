/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bd;

import controladores.CtrConexion;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author Aoihanabi
 */
public class Procedimiento {
    
    /** Controlador de conexión. */
    private static CtrConexion conexion = new CtrConexion();
    /** Procedimiento a ejecutar en la base. */
    private static String procedimiento;
    /** Resultado de las consultas a la base. */
    private static ResultSet resultado;
    
    private int regActualizados;
      
    /**
     * Ejecuta un procedimiento de select (que no hace modificaciones en la bd) 
     * y guarda todos sus elementos en un ResultSet.
     * @param procName Nombre del procedimiento a ejecutar
     * @return ResultSet con los registros obtenidos de la base de datos.
     * @throws java.sql.SQLException
     */
    public ResultSet procWithoutParam(String procName) throws SQLException{
        
        //Crear nombre completo del procedimiento
        procedimiento = "pc_" + procName + "()";
        //conexion.abrirConexion();
        resultado = conexion.ejecutarProcedimiento(procedimiento);

        return resultado;
    }
   
    public ResultSet procWithParam(String procName, ArrayList<Object> inParams) 
            throws SQLException {
        procedimiento = "pc_" + procName + 
                "(" + generateParamSymbol(inParams.size()) + ")";
        //conexion.abrirConexion();
        resultado = conexion.ejecutarProcedimiento(procedimiento, inParams);       
       
        return resultado;
    }
     public String generateParamSymbol(int cantParams) {
        String signo="?"; //String.format("%012d", Integer.valueOf(numId))
        for(int i = 0; i<(cantParams-1); i++) {
            signo += ", ?";
        }
        return signo;
    }
    
    /*public void obtenerRegistros(ResultSet resultado, ArrayList<Object> dataToGet) 
            throws SQLException {
        try {
            //Obtener nombres de las columnas
            ResultSetMetaData rsmd = resultado.getMetaData();
            ArrayList<String> colname = new ArrayList<>();
            for(int i = 0; i < rsmd.getColumnCount(); i++) {
                colname.add(rsmd.getColumnLabel(i));
            }
            //Obtener el valor de cada columna
            while (resultado.next()) {
                //Para cada columna pedida obtener el valor para su tipo de dato
                for(int i = 0; i < dataToGet.size(); i++) {
                    conexion.getParamValue(dataToGet.get(i), 
                            resultado, colname.get(i));
                }
            }
        } catch(SQLException ex) {
            
        }
    }*/
//    public ResultSet getOutParamValues(ResultSet res, 
//            ArrayList<Object> paramsOut, ArrayList<String> outParamName) 
//            throws SQLException {
//        
//        while (res.next()) {
//            for(int i = 0; i < paramsOut.size(); i++) {
//                conexion.getParamValue(paramsOut.get(i), 
//                        res, outParamName.get(i));
//            }                
//        }
//        return res;
//    }
    
    /**
     * Cargar tabla con los datos de un resultSet de forma dinámica
     * @param res resultset con las filas a mostrar
     * @param tabla tabla en que se mostrará la información.
     * @throws SQLException Excepción sql
     */
    public void cargarTabla(ResultSet res, JTable tabla) throws SQLException {
        //Obtener cantidad de columnas
        ResultSetMetaData rsmd = res.getMetaData();
        int cantColumn = rsmd.getColumnCount();
        
        Object[] row = new Object[cantColumn];
        
        DefaultTableModel model = (DefaultTableModel) tabla.getModel();
        model.setRowCount(0);
        model.setColumnCount(cantColumn);
        
        while (res.next()) {
            
            for(int n = 0; n < row.length; n++) {
                row[n] = res.getObject(n+1);
                //System.out.println("datos: " + res.getObject(n+1).toString());
            }
            model.addRow(row);
        }
    }
    
    /**
     * Procesa una consulta SQL cualquiera, determina si es una actualización o
     * selección desde la bd
     * @param query Consulta sql a procesar
     * @return Un resultset con datos si fue un select o null si ya hizo alguna
     * modificación en la bd
     * @throws java.sql.SQLException
     */
//    public ResultSet dinamico(String query) throws SQLException {
//           
//        Statement stmt = conexion.abrirConexion().createStatement();
//        //True si es un SELECT o false si algun INSERT etc
//        boolean status = stmt.execute(query);
//        if(status){
//            resultado = stmt.getResultSet();
//        } else {
//            int count = stmt.getUpdateCount();
//            System.out.println("Total registros actualizados: " + count);                
//        }        
//        
//        return resultado;
//    }
    
//    public void createStudent(String nombreEst, String cedulaEst, String cursoEst) {
//        String createTblStudent = "CREATE TABLE IF NOT EXISTS `students` (" +
//                                "    `cedula_student` VARCHAR(12) NOT NULL," +
//                                "    `nombre_student` VARCHAR(100) NOT NULL," +
//                                "    `curso_student` VARCHAR(100) NOT NULL) " +
//                                "ENGINE=INNODB COMMENT='Table with students' ;";
//        dinamico(createTblStudent);
//        //Insertar en la tabla
//        String insertStudent = "INSERT INTO `students` "
//                + "(cedula_student, nombre_student, curso_student) "
//                + "VALUES ("+cedulaEst+","+nombreEst+","+cursoEst+")";
//        dinamico(insertStudent);
//    }

    
}
