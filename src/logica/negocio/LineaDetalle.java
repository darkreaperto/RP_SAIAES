/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica.negocio;

/**
 * Instancia los detalles de los productos a facturar. 
 * @author aoihanabi
 */
public class LineaDetalle {
    private String codLDetalle; //código de bd para línea de pedido
    private int numeroLinea;//consecutivo de línea
    private String tipoCodProducto; //04 cod interno?
    private String codigoProducto;
    private int cantidad;
    private String unidadMedida; //es unidades, buscar codigo para unidades y poner estático?
    private String detalle;
    private double precioUnitario;
    private double total; //(cantidad*preciounitario)
    private double descuento;
    private String naturalezaDescuento;
    private double subtotal; //(total-descuentos)
    //Impuesto
    private String codImpuesto;
    private double montoTotalLinea;

    /**
     * Constructor vacío de clase LíneaDetalle.
     */
    public LineaDetalle() {
    }
    
    /**
     * Constructor de clase LineaPedido, inicializa variables.
     * @param numeroLinea número consecutivo en línea de pedido
     * @param tipoCodProducto tipo codigo de producto
     * @param codigoProducto codigo del producto
     * @param cantidad cantidad del productos
     * @param unidadMedida unidad en que se miden los productos
     * @param detalle descripción del producto
     * @param precioUnitario precio de producto por unidad
     * @param total cantidad de productos por su precio
     * @param descuento monto del descuento
     * @param naturalezaDescuento naturaleza o razón del descuento
     * @param subtotal productos sin descuento
     * @param codImpuesto codigo del tipo de impuesto
     * @param montoTotalLinea monto final por el que se facturará
     */
    public LineaDetalle(int numeroLinea, 
            String tipoCodProducto, String codigoProducto, int cantidad, 
            String unidadMedida, String detalle, double precioUnitario, 
            double total, double descuento, String naturalezaDescuento,
            double subtotal, String codImpuesto, double montoTotalLinea) {
        this.numeroLinea = numeroLinea;
        this.tipoCodProducto = tipoCodProducto;
        this.codigoProducto = codigoProducto;
        this.cantidad = cantidad;
        this.unidadMedida = unidadMedida;
        this.detalle = detalle;
        this.precioUnitario = precioUnitario;
        this.total = total;
        this.subtotal = subtotal;
        this.codImpuesto = codImpuesto;
        this.montoTotalLinea = montoTotalLinea;
    }

    /**
     * Establecer código de linea de detalle para BD
     * @param codLDetalle codigo de linea de detalle para la bd
     */
    public void setCodLDetalle(String codLDetalle) {
        this.codLDetalle = codLDetalle;
    }

    /**
     * Establecer número de linea detalle
     * @param numeroLinea número de linea detalle
     */
    public void setNumeroLinea(int numeroLinea) {
        this.numeroLinea = numeroLinea;
    }

    /**
     * Establecer tipo de código de producto
     * @param tipoCodProducto tipo de código de producto linea detalle
     */
    public void setTipoCodProducto(String tipoCodProducto) {
        this.tipoCodProducto = tipoCodProducto;
    }

    /**
     * Establecer código de producto
     * @param codigoProducto 
     */
    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    /**
     * Establecer cantidad de producto a facturar
     * @param cantidad cantidad de productos a facturar
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * Establecer unidad en que se mide el producto
     * @param unidadMedida unidad de medida
     */
    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    /**
     * Establecer detalle que describe el producto
     * @param detalle descripción del producto
     */
    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    /**
     * Establecer precio del producto por unidad
     * @param precioUnitario precio de cada producto
     */
    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    /**
     * Establecer total de la factura
     * @param total cantidad de productos por el precio unitario
     */
    public void setTotal(double total) {
        this.total = total;
    }
    
    /**
     * Establecer descuento del producto en la factura
     * @param descuento monto de descuento del producto
     */
    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }
    /**
     * Establecer la naturaleza del descuento
     * @param naturalezaDescuento cantidad de productos por el precio unitario
     */
    public void setNaturalezaDescuento (String naturalezaDescuento) {
        this.naturalezaDescuento = naturalezaDescuento;
    }
    /**
     * Establecer subtotal de la factura
     * @param subtotal subtotal de factura
     */
    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    /**
     * Establecer código/tipo de impuesto
     * @param codImpuesto codigo que especifíca el tipo de impuesto
     */
    public void setCodImpuesto(String codImpuesto) {
        this.codImpuesto = codImpuesto;
    }

    /**
     * Establecer monto total por el que se facturará
     * @param montoTotalLinea monto final por el que se facturará
     */
    public void setMontoTotalLinea(double montoTotalLinea) {
        this.montoTotalLinea = montoTotalLinea;
    }

    /**
     * Obtener el codigo (bd) de la línea de detalle.
     * @return código de línea de detalle (bd)
     */
    public String getCodLDetalle() {
        return codLDetalle;
    }

    /**
     * Obtener el número consecutivo en la línea de detalle.
     * @return número consecutivo en línea
     */
    public int getNumeroLinea() {
        return numeroLinea;
    }

    /**
     * Obtener el tipo de código del producto.
     * @return tipo de código del producto
     */
    public String getTipoCodProducto() {
        return tipoCodProducto;
    }

    /**
     * Obtener el código del producto.
     * @return código de producto
     */
    public String getCodigoProducto() {
        return codigoProducto;
    }

    /**
     * Obtener la cantidad de productos a facturar.
     * @return cantidad de productos
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * Obtener la unidad de medida.
     * @return unidad de medida utilizada
     */
    public String getUnidadMedida() {
        return unidadMedida;
    }

    /**
     * Obtener el detalle o descripción del producto.
     * @return detalle/descripción del producto
     */
    public String getDetalle() {
        return detalle;
    }

    /**
     * Obtener el precio por unidad del producto.
     * @return precio unitario del producto
     */
    public double getPrecioUnitario() {
        return precioUnitario;
    }

    /**
     * Obtener el valor total (cantidad de producto por su precio) del producto.
     * @return valor total por el producto
     */
    public double getTotal() {
        return total;
    }
    
    /**
     * Obtener el valor de descuento del producto.
     * @return valor total por descuento del producto
     */
    public double getDescuento() {
        return descuento;
    }
    
    /**
     * Obtener la naturaleza del descuento del producto.
     * @return naturaleza de descuento del producto
     */
    public String getNaturalezaDescuento() {
        return naturalezaDescuento;
    }

    /**
     * Obtener el subtotal (total menos descuentos) del producto.
     * @return subtotal
     */
    public double getSubtotal() {
        return subtotal;
    }

    /**
     * Obtener el codigo o tipo de impuesto.
     * @return codigo de impuesto
     */
    public String getCodImpuesto() {
        return codImpuesto;
    }

    /**
     * Obtener el monto total/final de la línea de detalle.
     * @return monto total de la línea
     */
    public double getMontoTotalLinea() {
        return montoTotalLinea;
    }
    
}
