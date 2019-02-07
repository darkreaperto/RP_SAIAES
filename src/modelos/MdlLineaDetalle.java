/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import controladores.CtrConexion;
import controladores.CtrExoneracion;
import controladores.CtrImpuesto;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import logica.negocio.Impuesto;
import logica.servicios.Mensaje;
import util.TipoContacto;

/**
 * Modelo de clase línea de detalle
 * @author aoihanabi
 */
public class MdlLineaDetalle {
    private static CtrConexion conexion;
    private static CtrImpuesto ctrImpuesto;
    private static CtrExoneracion ctrExoneracion;
    private static String procedimiento;
    private static ResultSet resultado;
    private static Mensaje msgError;

    /**
     * Constructo de clase modelo de línea de detalle.
     */
    public MdlLineaDetalle() {
        conexion = new CtrConexion();
        msgError = new Mensaje();
        ctrImpuesto = new CtrImpuesto();
        ctrExoneracion = new CtrExoneracion();
    }   
    
    /**
     * Inserta una nueva linea de detalle en la BD.
     * @param impuesto codigo del impuesto para la bd
     * @param numLinea consecutivo que enumera la linea de detalle
     * @param tipoCodProducto tipo de codigo de producto (indicado por hacienda)
     * @param codProducto codigo del producto asignado por el aserradero
     * @param cantidadLinea cantidad de productos a vender
     * @param unidadMedida unidad de medida para los productos
     * @param detalleLinea descripción de la venta
     * @param precioLinea precio unitario del producto que se vende
     * @param totalLinea valor total (precio*cantidad) de la venta
     * @param descuentoLinea valor de descuento si se realiza
     * @param natDescuento naturaleza/razón del descuento
     * @param subtotal total de la venta menos el descuento
     * @param montoTotalLinea monto toal de la venta, sumando los impuestos
     * @param mercancia si el producto es mercancia o servicio
     * @return el código de la linea creada
     */
    public int crearLineaDetalle(Impuesto impuesto, int numLinea, 
            String tipoCodProducto, String codProducto, double cantidadLinea, 
            String unidadMedida, String detalleLinea, double precioLinea, 
            double totalLinea, double descuentoLinea, String natDescuento, 
            double subtotal, double montoTotalLinea, boolean mercancia) {
        
        System.out.println("Dentro de crear linea detalle");
        //obtener el indice de impuesto
        int indiceImpuesto = ctrImpuesto.crearImpuesto(impuesto.getCodigoImpuesto(), 
                impuesto.getTarifaImpuesto(), impuesto.getMontoImpuesto());
        System.out.println("El indice de impuesto: " + indiceImpuesto);
        //Si tiene exoneración, la inserta en la base
        if(impuesto.getExoneracion() != null) {
            
            String tipoDoc = impuesto.getExoneracion().getTipoDocumento();
            String numDoc = impuesto.getExoneracion().getNumeroDocumento();
            String institucion = impuesto.getExoneracion().getNombreInstitucion();
            Date fecha = impuesto.getExoneracion().getFechaEmision();
            double montoImpuesto = impuesto.getExoneracion().getMontoImpuesto();
            double porcentCompra = impuesto.getExoneracion().getPorcentajeCompra();
            
            ctrExoneracion.crearExoneracion(indiceImpuesto, tipoDoc,
                    numDoc, institucion, fecha, montoImpuesto, porcentCompra);            
        }
        
        ArrayList<Object> params = new ArrayList<>();
        params.add(indiceImpuesto);
        params.add(numLinea);
        params.add(tipoCodProducto);
        params.add(codProducto);
        params.add(cantidadLinea);
        params.add(unidadMedida);
        params.add(detalleLinea);
        params.add(precioLinea);
        params.add(totalLinea);
        params.add(descuentoLinea);
        params.add(natDescuento);
        params.add(subtotal);
        params.add(montoTotalLinea);
        params.add(mercancia);
        params.add(Types.BIGINT);
        
        boolean creacionExitosa = true;
        int indice = 0;
        try {
            procedimiento = "pc_crear_linea_detalle(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            conexion.abrirConexion();
            resultado = conexion.ejecutarProcedimiento(procedimiento, params);
            
            //obtener el índice de la fila insertada
            while (resultado.next()) {
                indice = resultado.getInt("@indiceLD");
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
}
