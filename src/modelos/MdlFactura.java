/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import controladores.CtrConexion;
import controladores.CtrFactura;
import controladores.CtrLineaDetalle;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import logica.negocio.FacEncabezado;
import logica.negocio.FacNormativa;
import logica.negocio.FacReferencia;
import logica.negocio.FacResumen;
import logica.negocio.Factura;
import logica.negocio.LineaDetalle;
import logica.negocio.Madera;
import logica.servicios.Mensaje;

/**
 * Modelo de clase factura
 * @author aoihanabi
 */
public class MdlFactura {
    
    private static CtrConexion conexion;
    private static CtrLineaDetalle ctrLineaDetalle;
    private static CtrFactura ctrFactura;
    private static String procedimiento;
    private static ResultSet resultado;
    private static Mensaje msgError;
    ArrayList<Madera> productos;

    /**
     * Constructor de clase modelo de factura.
     */
    public MdlFactura() {
        conexion = new CtrConexion();
        msgError = new Mensaje();
    }
    
    public int crearResumen(String codigoMoneda, double tipoCambio, 
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
        int indice = 0;
        try {
            procedimiento = "pc_crear_facresumen(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            conexion.abrirConexion();
            resultado = conexion.ejecutarProcedimiento(procedimiento, params);
            
            
            //obtener el índice de la fila insertada
            while (resultado.next()) {
                indice = resultado.getInt("@indice");
            }
            System.out.println(resultado);
        } catch (SQLException ex) {
            System.err.println(ex);            
            creacionExitosa = false;
            System.out.println("ERROR SQL " + ex.getErrorCode());
            msgError.mostrarMensajeErrorSQL(ex.getErrorCode());
        } finally {
            conexion.cerrarConexion();
            return indice;
        }
    }
    
    public int crearResumen(FacResumen resumen) {
                
        ArrayList<Object> params = new ArrayList<>();
        params.add(resumen.getCodigoMoneda());
        params.add(resumen.getTipoCambio());
        params.add(resumen.getTotalServGravados());
        params.add(resumen.getTotalSerExentos());
        params.add(resumen.getTotalMercanciasGravadas());
        params.add(resumen.getTotalMercanciasExentas());
        params.add(resumen.getTotalGravado());
        params.add(resumen.getTotalExento());
        params.add(resumen.getTotalVenta());
        params.add(resumen.getTotalDescuentos());
        params.add(resumen.getTotalVentaNeta());
        params.add(resumen.getTotalImpuesto());
        params.add(resumen.getTotalComprobante());

        boolean creacionExitosa = true;
        int indice = 0;
        try {
            procedimiento = "pc_crear_facresumen(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            conexion.abrirConexion();
            resultado = conexion.ejecutarProcedimiento(procedimiento, params);
            
            
            //obtener el índice de la fila insertada
            while (resultado.next()) {
                indice = resultado.getInt("@indice");
            }
            System.out.println(resultado);
        } catch (SQLException ex) {
            System.err.println(ex);            
            creacionExitosa = false;
            System.out.println("ERROR SQL " + ex.getErrorCode());
            msgError.mostrarMensajeErrorSQL(ex.getErrorCode());
        } finally {
            conexion.cerrarConexion();
            return indice;
        }
    }
    
    public int crearEncabezado(FacEncabezado encab) {
        
        return 0;
    }
     
    public int crearReferencia(FacReferencia ref) {
        return 0;
    }
    
    /**
     * Llama el procedimiento almacenado que crea un registro 'factura' en la bd
     * @param fac Objeto factura con los datos necesarios para crear un registro 
     * de factura en la BD
     * @return el código de la factura creada
     */
    public boolean crearFactura (Factura fac) {
        
        //Crear los elementos que forman la factura
        int indEncab = crearEncabezado(fac.getEncabezado());
        int indResumen = crearResumen(fac.getResumen());
        int indReferencia = crearReferencia(fac.getInfoReferencia());
        int indNormativa = 1;//Estático, única fila en la BD
        
        ArrayList<Object> params = new ArrayList<>();
        params.add(indEncab);
        params.add(indResumen);
        params.add(indReferencia);
        params.add(indNormativa);

        boolean creacionExitosa = true;
        int indice = 0;
        try {
            procedimiento = "pc_crear_factura(?, ?, ?, ?)";

            conexion.abrirConexion();
            resultado = conexion.ejecutarProcedimiento(procedimiento, params);
            
            //obtener el índice de la factura insertada
            while (resultado.next()) {
                indice = resultado.getInt("@indice");
            }
            
            //Crear una linea de detalle en la bd por cada una encontrada 
            //en la lista de lineas
            for(LineaDetalle linea : fac.getLineasDetalle()) {
                //obtener el indice de linea
                int indiceLinea = ctrLineaDetalle.crearLineaDetalle(
                        linea.getImpuesto(),
                        linea.getNumeroLinea(), linea.getTipoCodProducto(), 
                        linea.getCodigoProducto(), linea.getCantidad(), 
                        linea.getUnidadMedida(), linea.getDetalle(), 
                        linea.getPrecioUnitario(), linea.getTotal(), 
                        linea.getDescuento(), linea.getNaturalezaDescuento(), 
                        linea.getSubtotal(), linea.getMontoTotalLinea(), 
                        linea.isMercancia());
                
                crearLineaxFactura(indiceLinea, indice);
            }
                       
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
     * Llama el procedimiento almacenado que crea un registro 'lineasxfactura' 
     * en la bd
     * @param codlinea codigo de la linea de detalle de la factura
     * @param codfactura codigo de la factura a la que se relaciona la linea
     * @return el código de la lineaxfactura creada
     */
    public boolean crearLineaxFactura (int codlinea, int codfactura) {
        
        
        ArrayList<Object> params = new ArrayList<>();
        params.add(codlinea);
        params.add(codfactura);
//        params.add(Types.BIGINT);

        boolean creacionExitosa = true;
        try {
            procedimiento = "pc_crear_lineaxfactura(?, ?)";

            conexion.abrirConexion();
            resultado = conexion.ejecutarProcedimiento(procedimiento, params);
                                   
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
}
