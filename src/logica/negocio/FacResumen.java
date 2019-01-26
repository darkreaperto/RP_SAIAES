/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica.negocio;

/**
 * Instancia con sus atributos, la clase para el resumen de factura.
 * @author darkreaper
 */
public class FacResumen {
    
    private String codigoMoneda;
    private double tipoCambio;
    private double totalServGravados;
    private double totalSerExentos;
    private double totalMercanciasGravadas;
    private double totalMercanciasExentas;
    private double totalGravado;
    private double totalExento;
    private double totalVenta;
    private double totalDescuentos;
    private double totalVentaNeta; //total venta menos total descuentos
    private double totalImpuesto;
    private double totalComprobante;
    
    /**
     * Constructor de clase Resumen contenida en factura, inicializa variables.
     * @param codigoMoneda codigo de moneda 
     * @param tipoCambio tipo de cambio
     * @param totalServGravados total de servicios gravados
     * @param totalSerExentos total de servicios exentos
     * @param totalMercanciasGravadas total de mercancías gravadas
     * @param totalMercanciasExentas total de mercancías exentas
     * @param totalGravado total gravado
     * @param totalExento total exento
     * @param totalVenta total de la venta
     * @param totalDescuentos total de descuento
     * @param totalVentaNeta total de la venta neta (total venta menos total descuento)
     * @param totalImpuesto total de impuesto (suma de todos los montos de impuesto)
     * @param totalComprobante total del comprobante (venta neta mas impuesto)
     */
    public FacResumen(String codigoMoneda, double tipoCambio, 
            double totalServGravados, double totalSerExentos, 
            double totalMercanciasGravadas, double totalMercanciasExentas, 
            double totalGravado, double totalExento, double totalVenta, 
            double totalDescuentos, double totalVentaNeta, double totalImpuesto, 
            double totalComprobante) {
        this.codigoMoneda = codigoMoneda;
        this.tipoCambio = tipoCambio;
        this.totalServGravados = totalServGravados;
        this.totalSerExentos = totalSerExentos;
        this.totalMercanciasGravadas = totalMercanciasGravadas;
        this.totalMercanciasExentas = totalMercanciasExentas;
        this.totalGravado = totalGravado;
        this.totalExento = totalExento;
        this.totalVenta = totalVenta;
        this.totalDescuentos = totalDescuentos;
        this.totalVentaNeta = totalVentaNeta;
        this.totalImpuesto = totalImpuesto;
        this.totalComprobante = totalComprobante;
    }

    /**
     * Obtener el código de moneda.
     * @return the codigoMoneda
     */
    public String getCodigoMoneda() {
        return codigoMoneda;
    }

    /**
     * Establecer el código de moneda.
     * @param codigoMoneda the codigoMoneda to set
     */
    public void setCodigoMoneda(String codigoMoneda) {
        this.codigoMoneda = codigoMoneda;
    }

    /**
     * Obtener el tipo de cambio.
     * @return the tipoCambio
     */
    public double getTipoCambio() {
        return tipoCambio;
    }

    /**
     * Establecer el tipo de cambio.
     * @param tipoCambio the tipoCambio to set
     */
    public void setTipoCambio(double tipoCambio) {
        this.tipoCambio = tipoCambio;
    }

    /**
     * Obtener el total de servicios gravados de la factura.
     * @return the totalServGravados
     */
    public double getTotalServGravados() {
        return totalServGravados;
    }

    /**
     * Establecer el total de servicios gravados de la factura.
     * @param totalServGravados the totalServGravados to set
     */
    public void setTotalServGravados(double totalServGravados) {
        this.totalServGravados = totalServGravados;
    }

    /**
     * Obtener el total de servicios exentos de la factura.
     * @return the totalSerExentos
     */
    public double getTotalSerExentos() {
        return totalSerExentos;
    }

    /**
     * Establecer el total de servicios exentos de la factura.
     * @param totalSerExentos the totalSerExentos to set
     */
    public void setTotalSerExentos(double totalSerExentos) {
        this.totalSerExentos = totalSerExentos;
    }

    /**
     * Obtener el total de mercancías gravadas de la factura.
     * @return the totalMercanciasGravadas
     */
    public double getTotalMercanciasGravadas() {
        return totalMercanciasGravadas;
    }

    /**
     * Establecer el total de mercancías gravadas de la factura.
     * @param totalMercanciasGravadas the totalMercanciasGravadas to set
     */
    public void setTotalMercanciasGravadas(double totalMercanciasGravadas) {
        this.totalMercanciasGravadas = totalMercanciasGravadas;
    }

    /**
     * Obtener el total de mercancías exentas de la factura.
     * @return the totalMercanciasExentas
     */
    public double getTotalMercanciasExentas() {
        return totalMercanciasExentas;
    }

    /**
     * Establecer el total de mercancías exentas de la factura.
     * @param totalMercanciasExentas the totalMercanciasExentas to set
     */
    public void setTotalMercanciasExentas(double totalMercanciasExentas) {
        this.totalMercanciasExentas = totalMercanciasExentas;
    }

    /**
     * Obtener el total gravado de la factura.
     * @return the totalGravado
     */
    public double getTotalGravado() {
        return totalGravado;
    }

    /**
     * Establecer el total gravado de la factura.
     * @param totalGravado the totalGravado to set
     */
    public void setTotalGravado(double totalGravado) {
        this.totalGravado = totalGravado;
    }

    /**
     * Obtener el total exento de la factura.
     * @return the totalExento
     */
    public double getTotalExento() {
        return totalExento;
    }

    /**
     * Establecer el total exento de la factura.
     * @param totalExento the totalExento to set
     */
    public void setTotalExento(double totalExento) {
        this.totalExento = totalExento;
    }

    /**
     * Obtener el total de la venta.
     * @return the totalVenta
     */
    public double getTotalVenta() {
        return totalVenta;
    }

    /**
     * Establecer el total de la venta.
     * @param totalVenta the totalVenta to set
     */
    public void setTotalVenta(double totalVenta) {
        this.totalVenta = totalVenta;
    }

    /**
     * Obtener el total de descuento.
     * @return the totalDescuentos
     */
    public double getTotalDescuentos() {
        return totalDescuentos;
    }

    /**
     * Establecer el total de descuento.
     * @param totalDescuentos the totalDescuentos to set
     */
    public void setTotalDescuentos(double totalDescuentos) {
        this.totalDescuentos = totalDescuentos;
    }

    /**
     * Obtener el total de la venta neta.
     * @return the totalVentaNeta
     */
    public double getTotalVentaNeta() {
        return totalVentaNeta;
    }

    /**
     * Establecer el total de la venta neta.
     * @param totalVentaNeta the totalVentaNeta to set
     */
    public void setTotalVentaNeta(double totalVentaNeta) {
        this.totalVentaNeta = totalVentaNeta;
    }

    /**
     * Obtener el impuesto total de la factura.
     * @return the totalImpuesto
     */
    public double getTotalImpuesto() {
        return totalImpuesto;
    }

    /**
     * Establecer el impuesto total de la factura.
     * @param totalImpuesto the totalImpuesto to set
     */
    public void setTotalImpuesto(double totalImpuesto) {
        this.totalImpuesto = totalImpuesto;
    }

    /**
     * Obtener el total de la factura.
     * @return the totalComprobante
     */
    public double getTotalComprobante() {
        return totalComprobante;
    }

    /**
     * Establecer el total de la factura.
     * @param totalComprobante the totalComprobante to set
     */
    public void setTotalComprobante(double totalComprobante) {
        this.totalComprobante = totalComprobante;
    }
}
