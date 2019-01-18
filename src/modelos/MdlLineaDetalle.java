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
import logica.servicios.Mensaje;
import util.TipoContacto;

/**
 * Modelo de clase línea de detalle
 * @author aoihanabi
 */
public class MdlLineaDetalle {
    private static CtrConexion conexion;
    private static String procedimiento;
    private static ResultSet resultado;
    private static Mensaje msgError;

    /**
     * Constructo de clase modelo de línea de detalle.
     */
    public MdlLineaDetalle() {
        conexion = new CtrConexion();
        msgError = new Mensaje();
    }   
    
    /**
     * Inserta una nueva linea de detalle en la BD.
     * @param codigoImpuesto codigo del tipo de impuesto indicado por hacienda
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
     * @param exoneracion si se realiza o no exoneración (nulo si no se da exoneración)
     * @return verdadero si se crea exitosamente la linea de detalle
     */
    public boolean crearLineaDetalle(String codigoImpuesto, int numLinea, String tipoCodProducto, 
            String codProducto, double cantidadLinea, String unidadMedida,
            String detalleLinea, double precioLinea, double totalLinea,
            double descuentoLinea, String natDescuento, double subtotal,
            double montoTotalLinea) {

        ArrayList<Object> params = new ArrayList<>();
        params.add(codigoImpuesto);
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
        //params.add(exoneracion);
        
//        `codigoImpuesto` VARCHAR(2), 
//        `tarifaImpuesto` DOUBLE(4,2), 
//        `montoImpuesto` DOUBLE(18,5), 
//        `numLinea` INT(11),
//        `tipoCodProducto` VARCHAR(2),
//        `codProducto` VARCHAR(20),
//        `cantidadLinea` DOUBLE(16,3), 
//        `unidadMedidaLinea` VARCHAR(15),
//        `detalleLinea` VARCHAR(160),
//        `precioLinea` DOUBLE(18,5), 
//        `totalLinea` DOUBLE(18,5),
//        `descuentoLinea` DOUBLE(18,5),
//        `natDescuentoLinea` VARCHAR(80),
//        `subtotalLinea` DOUBLE(18,5),
//        `montoTotalLinea` DOUBLE(18,5),
//        `exoneracion` VARCHAR(3)
        boolean creacionExitosa = true;
        try {
            procedimiento = "pc_crear_linea_detalle(?,?,?,?,?,?,?,?,?,?,?,?,?)";

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
