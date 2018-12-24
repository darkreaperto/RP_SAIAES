/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica.negocio;

import java.util.ArrayList;

/**
 * Instancia la línea de pedido con lista de productos para facturar.
 * @author aoihanabi
 */
public class LineaPedido {
    private String codigoLPedido;
    //Línea de detalle
    private String codigoLDetalle;
    //private ArrayList<LineaDetalle> detalleServicio; //agrupa lineas de detalle

    /**
     * Constructor vacío de clase LineaPedido.
     */
    public LineaPedido() {
    }
    
    /**
     * Constructor de clase Línea de pedido, inicializa variables.
     * @param codigoLPedido código de línea de pedido para la bd
     * @param codigoLDetalle lista de productos a facturar en la linea de pedido
     */
    public LineaPedido(String codigoLPedido, 
            String codigoLDetalle) {
        this.codigoLPedido = codigoLPedido;
        this.codigoLDetalle = codigoLDetalle;
    }    

    /**
     * Establecer codigo de la línea de pedido
     * @param codigoLPedido codigo de la linea de pedido
     */
    public void setCodigoLPedido(String codigoLPedido) {
        this.codigoLPedido = codigoLPedido;
    }

    /**
     * Establecer lista con detalles del producto a facturar
     * @param codigoLDetalle lista que describe los productos a facturar
     */
    public void setDetalleServicio(String codigoLDetalle) {
        this.codigoLDetalle = codigoLDetalle;
    }

    
}

