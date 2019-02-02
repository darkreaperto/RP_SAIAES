/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import java.util.ArrayList;
import logica.negocio.Impuesto;
import logica.negocio.LineaDetalle;
import modelos.MdlLineaDetalle;

/**
 * Constrolador de la clase linea de detalle
 * @author aoihanabi
 */
public class CtrLineaDetalle {
    private static CtrLineaDetalle instancia = null;
    MdlLineaDetalle mdlLineaDetalle;
    LineaDetalle lineaDetalle;
    ArrayList <LineaDetalle> lineasDetalle;

    /**
     * Constructor del controlador de línea de detalle, inicializa variables.
     */
    public CtrLineaDetalle() {
        lineasDetalle = new ArrayList<>();
        mdlLineaDetalle = new MdlLineaDetalle();
    }
    
    /**
     * Obtener instancia única del controlador de línea de detalle.
     * @return Instancia única de línea de detalle
     */
    public static CtrLineaDetalle getInstancia() {
        return  instancia == null ? new CtrLineaDetalle() : instancia;
    }
    
    /**
     * Llama el método que inserta un nuevo registro 'exoneración' en la BD
     * @param impuesto codigo de impuesto para la bd
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
     * @param mercancia si el producto es una mercancia o un servicio
     * @return el código de la linea creada
     */
    public int crearLineaDetalle(Impuesto impuesto, int numLinea, 
            String tipoCodProducto, String codProducto, double cantidadLinea, 
            String unidadMedida, String detalleLinea, double precioLinea, 
            double totalLinea, double descuentoLinea, String natDescuento, 
            double subtotal, double montoTotalLinea, boolean mercancia) {
        
        return mdlLineaDetalle.crearLineaDetalle(impuesto, numLinea, 
                tipoCodProducto, codProducto, cantidadLinea, unidadMedida,
                detalleLinea, precioLinea, totalLinea, descuentoLinea, 
                natDescuento, subtotal, montoTotalLinea, mercancia);
    }
}
