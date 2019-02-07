/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import controladores.CtrConexion;
import controladores.CtrImpuesto;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import logica.negocio.Impuesto;
import logica.servicios.Mensaje;

/**
 * Modelo de clase exoneración
 * @author aoihanabi
 */
public class MdlExoneracion {
    private static CtrConexion conexion;
    private static CtrImpuesto ctrImpuesto;
    private static String procedimiento;
    private static ResultSet resultado;
    private static Mensaje msgError;

    /**
     * Constructor de clase modelo exoneración.
     */
    public MdlExoneracion() {
        conexion = new CtrConexion();
        msgError = new Mensaje();
    }
    /**
     * Llama el procedimiento almacenado que crea un registro 'exoneracion' 
     * en la bd
     * @param codImpuesto codigo del tipo de impuesto indicado por hacienda
     * @param tipoDocumento tipo de documento de exoneración
     * @param numDocumento numero de documento de exoneración
     * @param institucion nombre de institución que emitió la exoneración 
     * @param fechaEmision fecha en que se emite el documento de exoneración
     * @param montoImpuesto monto de impuesto exonerado/autorizado sin impuesto
     * @param porcentajeCompra porcentaje de la compra autorizada o exoneración
     * @return verdadero si el impuesto se crea exitosamente
     */
    public boolean crearExoneración(int codImpuesto, String tipoDocumento, 
            String numDocumento, String institucion, Date fechaEmision,
            double montoImpuesto, double porcentajeCompra) {
        
        ArrayList<Object> params = new ArrayList<>();
        params.add(codImpuesto);
        params.add(tipoDocumento);
        params.add(numDocumento);
        params.add(institucion);
        params.add(fechaEmision);
        params.add(montoImpuesto);
        params.add(porcentajeCompra);
        

        boolean creacionExitosa = true;
        try {
            procedimiento = "pc_crear_exoneracion(?, ?, ?, ?, ?, ?, ?)";

            conexion.abrirConexion();
            resultado = conexion.ejecutarProcedimiento(procedimiento, params);
           
            System.out.println(resultado);
        } catch (SQLException ex) {
            System.err.println(ex);         
            ex.printStackTrace();
            creacionExitosa = false;
            System.out.println("ERROR SQL " + ex.getErrorCode());
            msgError.mostrarMensajeErrorSQL(ex.getErrorCode());
        } finally {
            conexion.cerrarConexion();
            return creacionExitosa;
        }
    }
    
//     /**
//     * Llama el procedimiento almacenado que crea un registro 'exoneracion' 
//     * en la bd
//     * @param codigoImpuesto codigo del impuesto para bd
//     * @param tipoDocumento tipo de documento de exoneración
//     * @param numDocumento numero de documento de exoneración
//     * @param institucion nombre de institución que emitió la exoneración 
//     * @param fechaEmision fecha en que se emite el documento de exoneración
//     * @param montoImpuesto monto de impuesto exonerado/autorizado sin impuesto
//     * @param porcentajeCompra porcentaje de la compra autorizada o exoneración
//     * @return verdadero si el impuesto se crea exitosamente
//     */
//    public boolean crearExoneración(int codigoImpuesto, String tipoDocumento, 
//            String numDocumento, String institucion, String fechaEmision,
//            double montoImpuesto, int porcentajeCompra) {
//        ArrayList<Object> params = new ArrayList<>();
//        params.add(codigoImpuesto);
//        params.add(tipoDocumento);
//        params.add(numDocumento);
//        params.add(institucion);
//        params.add(fechaEmision);
//        params.add(montoImpuesto);
//        params.add(porcentajeCompra);
//        
//
//        boolean creacionExitosa = true;
//        try {
//            procedimiento = "pc_crear_exoneracion(?, ?, ?, ?, ?, ?, ?)";
//
//            conexion.abrirConexion();
//            resultado = conexion.ejecutarProcedimiento(procedimiento, params);
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
    
    public ArrayList<Timestamp> obtenerExoneraciones() {
        ArrayList<Timestamp> fechasEmision = new ArrayList<>();
        boolean go = false;
        try {
            conexion.abrirConexion();
            
            String sql = "SELECT fechaEmision_Exoneraciones "
                    + "FROM `exoneraciones`";
            ResultSet rs = conexion.ejecutarConsulta(sql);
            
            Timestamp fecha;
            
            while (rs.next()) {
                fecha = rs.getTimestamp("fechaEmision_Exoneraciones");
                fechasEmision.add(fecha);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conexion.cerrarConexion();
        }
        return fechasEmision;
    }
    
}
