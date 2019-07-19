/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import java.util.ArrayList;
import logica.negocio.Impuesto;
import modelos.MdlLineaDetalle;

/**
 * Constrolador de la clase linea de detalle
 * @author aoihanabi
 */
public class CtrLineaDetalle {
    private static CtrLineaDetalle instancia = null;
    MdlLineaDetalle mdlLineaDetalle;

    /**
     * Constructor del controlador de línea de detalle, inicializa variables.
     */
    public CtrLineaDetalle() {
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
     * @param codProducto codigo del producto asignado por el aserradero
     * @param cantidadLinea cantidad de productos a vender
     * @param detalleLinea descripción de la venta
     * @param precioLinea precio unitario del producto que se vende
     * @param totalLinea valor total (precio*cantidad) de la venta
     * @param descuentoLinea valor de descuento si se realiza
     * @param subtotal total de la venta menos el descuento
     * @param montoTotalLinea monto toal de la venta, sumando los impuestos
     * @param mercancia si el producto es una mercancia o un servicio
     * @return el código de la linea creada
     */
    public int crearLineaDetalle(int numLinea, String codProducto, 
            double cantidadLinea, String detalleLinea, double precioLinea, 
            boolean mercancia, Impuesto impuesto, double descuentoLinea, 
            double subtotal, double totalLinea, double montoTotalLinea) {
        
        return mdlLineaDetalle.crearLineaDetalle(numLinea, 
                codProducto, cantidadLinea, detalleLinea, precioLinea, 
                mercancia, impuesto, descuentoLinea, subtotal, totalLinea, 
                montoTotalLinea);
    }
}
