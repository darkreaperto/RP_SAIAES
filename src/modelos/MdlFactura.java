/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import controladores.CtrConexion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import logica.negocio.Consecutivo;
import logica.negocio.Madera;
import logica.servicios.Mensaje;

/**
 * Modelo de clase factura
 * @author aoihanabi
 */
public class MdlFactura {
    
    private static CtrConexion conexion;
    private static String procedimiento;
    private static ResultSet resultado;
    private static Mensaje msgError;
    ArrayList<Madera> productos;
    private static ArrayList<Consecutivo> consecutivos;
    

    /**
     * Constructor de clase modelo de factura.
     */
    public MdlFactura() {
        conexion = new CtrConexion();
        msgError = new Mensaje();
    }
    
    public boolean crearFacResumen(String codigoMoneda, double tipoCambio, 
            double totalServGravados, double totalSerExentos, 
            double totalMercanciasGravadas, double totalMercanciasExentas, 
            double totalGravado, double totalExento, double totalVenta, 
            double totalDescuentos, double totalVentaNeta, double totalImpuesto, 
            double totalComprobante) {

        ArrayList<Object> params = new ArrayList<>();
        params.add(codigoMoneda);
        params.add(tipoCambio);
        params.add(totalServGravados);
        params.add(totalSerExentos);
        params.add(totalMercanciasGravadas);
        params.add(totalMercanciasExentas);
        params.add(totalGravado);
        params.add(totalExento);
        params.add(totalVenta);
        params.add(totalDescuentos);
        params.add(totalVentaNeta);
        params.add(totalImpuesto);
        params.add(totalComprobante);

        boolean creacionExitosa = true;
        try {
            procedimiento = "pc_crear_facresumen(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            conexion.abrirConexion();
            resultado = conexion.ejecutarProcedimiento(procedimiento, params);
            
            int indice = 0;
            //obtener el índice de la fila insertada
            while (resultado.next()) {
                indice = resultado.getInt("@indice");
            }
            System.out.println(indice);
            System.out.println(resultado);
        } catch (SQLException ex) {
            System.err.println(ex);            
            creacionExitosa = false;
            System.out.println("ERROR SQL " + ex.getErrorCode());
            msgError.mostrarMensajeErrorSQL(ex.getErrorCode());
        } finally {
            conexion.cerrarConexion();
            return creacionExitosa;
        }
    }
    
    /**
     * Llena una lista con todos los consecutivos de comprobantes almacenados 
     * en la BD.
     * @return lista de consecutivos.
     */
    public ArrayList<Consecutivo> obtenerConsecutivos() {
        consecutivos = new ArrayList<>();

        try {
            procedimiento = "pc_obtener_consecutivos()";
            conexion.abrirConexion();
            resultado = conexion.ejecutarProcedimiento(procedimiento);

            String cod;
            String codComprob;
            String tipoComprob;
            int consecutivo;

            while (resultado.next()) {
                cod = resultado.getString("cod_Consecutivos");
                codComprob = resultado.getString("codComprob_Consecutivos");
                tipoComprob = resultado.getString("tipoComprob_Consecutivos");
                consecutivo = resultado.getInt("consecutivo_Consecutivos");

                Consecutivo conse = new Consecutivo(cod, codComprob, 
                        tipoComprob, consecutivo);

                if (!consecutivos.contains(conse)) {
                    consecutivos.add(conse);
                }
            }
        } catch (SQLException ex) {
            System.err.println(ex);
        } finally {
            conexion.cerrarConexion();
            return consecutivos;
        }
    }
    
}
