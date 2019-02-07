/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import controladores.CtrConexion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import logica.servicios.Mensaje;
import util.TipoMensaje;

/**
 * Modelo de clase impuesto
 * @author aoihanabi
 */
public class MdlImpuesto {
    private static CtrConexion conexion;
    private static String procedimiento;
    private static ResultSet resultado;
    private static Mensaje msgError;

    /**
     * Constructor de clase modelo de impuesto.
     */
    public MdlImpuesto() {
        conexion = new CtrConexion();
        msgError = new Mensaje();
    }
    /**
     * Llama el procedimiento almacenado que crea un registro 'impuesto' en la bd
     * @param codigoImpuesto codigo del tipo de impuesto indicado por haciendda
     * @param tarifaImpuesto porcentaje de impuesto aplicado al producto
     * @param montoImpuesto valor extra(impuesto) que se sumará al precio del 
     * producto
     * @return verdadero si el impuesto se crea exitosamente
     */
//    public boolean crearImpuesto(String codigoImpuesto, double tarifaImpuesto, 
//            double montoImpuesto) {
//        ArrayList<Object> params = new ArrayList<>();
//        params.add(codigoImpuesto);
//        params.add(tarifaImpuesto);
//        params.add(montoImpuesto);
//        params.add(Types.BIGINT);
//
//        boolean creacionExitosa = true;
//        try {
//            procedimiento = "pc_crear_impuesto(?, ?, ?, ?)";
//
//            conexion.abrirConexion();
//            resultado = conexion.ejecutarProcedimiento(procedimiento, params);
//            
//            int indice = 0;
//            //obtener el índice de la fila insertada
//            while (resultado.next()) {
//                indice = resultado.getInt("@indiceImpuesto");
//            }
//                       
//            System.out.println(resultado);
//        } catch (SQLException ex) {
//            System.err.println(ex);            
//            creacionExitosa = false;
//            System.out.println("ERROR SQL " + ex.getErrorCode());
//            msgError.mostrarMensajeErrorSQL(ex.getErrorCode());
//        } finally {
//            conexion.cerrarConexion();
//            return creacionExitosa;
//        }
//    }
    /**
     * Llama el procedimiento almacenado que crea un registro 'impuesto' en la bd
     * @param codigoImpuesto codigo del tipo de impuesto indicado por haciendda
     * @param tarifaImpuesto porcentaje de impuesto aplicado al producto
     * @param montoImpuesto valor extra(impuesto) que se sumará al precio del 
     * producto
     * @return verdadero si el impuesto se crea exitosamente
     */
    public int crearImpuesto(String codigoImpuesto, double tarifaImpuesto, 
            double montoImpuesto) {
        
        ArrayList<Object> params = new ArrayList<>();
        params.add(codigoImpuesto);
        params.add(tarifaImpuesto);
        params.add(montoImpuesto);
        params.add(Types.BIGINT);

        boolean creacionExitosa = true;
        int indice = 0;
        try {
            procedimiento = "pc_crear_impuesto(?, ?, ?, ?)";

            conexion.abrirConexion();
            resultado = conexion.ejecutarProcedimiento(procedimiento, params);
            
            
            //obtener el índice de la fila insertada
            while (resultado.next()) {
                indice = resultado.getInt("@indiceImpuesto");
            }
                       
            System.out.println(resultado);
        } catch (SQLException ex) {
            System.err.println(ex);  
            ex.printStackTrace();
            creacionExitosa = false;
            System.out.println("ERROR SQL " + ex.getErrorCode());
            msgError.mostrarMensajeErrorSQL(ex.getErrorCode());
        } finally {
            conexion.cerrarConexion();
            return indice;
        }
    }
//    public int getCodImpuesto() {
//        return indice;
//    }
}
