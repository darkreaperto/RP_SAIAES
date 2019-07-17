/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica.negocio;

/**
 * Instancia con sus atributos, la clase para linea de detalle de las 
 * facturas, con los detalles de cada producto a facturar. 
 * @author aoihanabi
 */
public class LineaDetalle {
    
    private int numeroLinea;//consecutivo de línea
    private Madera producto;// = new Madera();
    private Varios varios; //= new Varios();
    private double cantSolicitada;
    private String detalle;
    //Adicional clasificación de producto
    private boolean mercancia;
    private Impuesto impuesto;//objeto impuesto
    private double descuento;
    private double subtotal; //(total-descuentos)
    private double total; //(cantidad*preciounitario)
    private double montoTotalLinea;
    private boolean isProdVario;

    
    
    /**
     * Constructor vacío de clase LíneaDetalle.
     */
    public LineaDetalle() {
        
    }

    /**
     * Constructor de clase LineaDetalle, inicializa las variables requeridas 
     * incluyendo el objeto Madera para obtener los datos de productos de madera.
     * @param producto
     * @param numeroLinea número consecutivo en línea de pedido
     * @param cantSolicitada cantidad de productos para la linea
     * @param detalle descripción del producto/linea
     * @param descuento valor/monto de descuento
     * @param impuesto valor de impuesto gravado al producto
     * @param isProdVario identifica si se trata de un producto vario o un 
     * producto de madera (prodVario = true, prodMadera = false)
     */
    public LineaDetalle(int numeroLinea, Madera producto, double cantSolicitada, 
            String detalle, Impuesto impuesto, double descuento) {
        
        this.producto = producto;
        this.numeroLinea = numeroLinea;
        this.cantSolicitada = cantSolicitada;
        this.detalle = detalle;
        this.mercancia = true;
        this.impuesto = impuesto;
        this.descuento = descuento;
        this.total = cantSolicitada * producto.getPrecioXvara();
        this.subtotal = this.total - descuento;
        this.montoTotalLinea = this.subtotal + impuesto.getMontoImpuesto();
        this.isProdVario = false;
        this.varios = new Varios();
    }
    
    /**
     * Constructor de clase LineaDetalle, inicializa las variables requeridas 
     * incluyendo el objeto Varios para obtener los datos de productos varios 
     * que no estén incluidos en el inventario del sistema.
     * @param prodVario producto de tipo varios (no son una madera específica)
     * @param numeroLinea número consecutivo en línea de pedido
     * @param descuento valor/monto de descuento
     * @param impuesto valor de impuesto gravado al producto
     * @param mercancia clasificación de producto (mercancia = true, servicio = false)
     * @param isProdVario identifica si se trata de un producto vario o un 
     * producto de madera (mercancia = true, servicio = false)
     */
    public LineaDetalle(int numeroLinea, Varios prodVario,
            Impuesto impuesto, double descuento, boolean mercancia) {
        
        this.varios = prodVario;
        this.numeroLinea = numeroLinea;
        this.mercancia = true;
        this.impuesto = impuesto;
        this.descuento = descuento;
        this.total = prodVario.getPrecio();
        this.subtotal = this.total - descuento;
        this.montoTotalLinea = this.subtotal + impuesto.getMontoImpuesto();
        this.isProdVario = true;
        this.producto = new Madera();
    }

    /**
     * Establecer número de linea detalle
     * @param numeroLinea número de linea detalle
     */
    public void setNumeroLinea(int numeroLinea) {
        this.numeroLinea = numeroLinea;
    }

    /**
     * Establecer cantidad de producto a facturar
     * @param cantSolicitada cantidad de productos a facturar
     */
    public void setCantSolicitada(int cantSolicitada) {
        this.cantSolicitada = cantSolicitada;
    }

    /**
     * Establecer detalle que describe el producto
     * @param detalle descripción del producto
     */
    public void setDetalle(String detalle) {
        this.detalle = detalle;
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
     * Establecer subtotal de la factura
     * @param subtotal subtotal de factura
     */
    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    /**
     * Establecer el impuesto.
     * @param impuesto impuesto del producto de la línea
     */
    public void setImpuesto(Impuesto impuesto) {
        this.impuesto = impuesto;
    }

    /**
     * Establecer monto total por el que se facturará
     * @param montoTotalLinea monto final por el que se facturará
     */
    public void setMontoTotalLinea(double montoTotalLinea) {
        this.montoTotalLinea = montoTotalLinea;
    }
    
    /**
     * Establecer la clasificación de producto (mercancia = true, servicio = false).
     * @param mercancia the mercancia to set
     */
    public void setMercancia(boolean mercancia) {
        this.mercancia = mercancia;
    }

    /**
     * Obtener el número consecutivo en la línea de detalle.
     * @return número consecutivo en línea
     */
    public int getNumeroLinea() {
        return numeroLinea;
    }

    /**
     * Obtener la cantidad de productos a facturar.
     * @return cantidad de productos
     */
    public double getCantSolicitada() {
        return cantSolicitada;
    }
    
    /**
     * Obtener el detalle o descripción del producto.
     * @return detalle/descripción del producto
     */
    public String getDetalle() {
        return detalle;
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
     * Obtener el subtotal (total menos descuentos) del producto.
     * @return subtotal
     */
    public double getSubtotal() {
        return subtotal;
    }

    /**
     * Obtener el impuesto.
     * @return el impuesto del producto
     */
    public Impuesto getImpuesto() {
        return impuesto;
    }

    /**
     * Obtener el monto total/final de la línea de detalle.
     * @return monto total de la línea
     */
    public double getMontoTotalLinea() {
        return montoTotalLinea;
    }

    /**
     * Otener la clasificación de producto (mercancia = true, servicio = false).
     * @return the mercancia
     */
    public boolean isMercancia() {
        return mercancia;
    }
    
    public Madera getProducto() {
        return producto;
    }

    public void setProducto(Madera producto) {
        this.producto = producto;
    }

    public Varios getVarios() {
        return varios;
    }

    public void setVarios(Varios varios) {
        this.varios = varios;
    }
    
    /**
     * Obtener si la linea se trata de un producto vario o un producto 
     * en inventario.
     * @return Verdadero si es producto vario, o falso si es producto de madera 
     * disponible en el inventario
     */
    public boolean isProdVario() {
        return isProdVario;
    }

    public void setIsProdVario(boolean isProdVario) {
        this.isProdVario = isProdVario;
    }
}
