/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica.negocio;

/**
 *
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
     * @return the codigoMoneda
     */
    public String getCodigoMoneda() {
        return codigoMoneda;
    }

    /**
     * @param codigoMoneda the codigoMoneda to set
     */
    public void setCodigoMoneda(String codigoMoneda) {
        this.codigoMoneda = codigoMoneda;
    }

    /**
     * @return the tipoCambio
     */
    public double getTipoCambio() {
        return tipoCambio;
    }

    /**
     * @param tipoCambio the tipoCambio to set
     */
    public void setTipoCambio(double tipoCambio) {
        this.tipoCambio = tipoCambio;
    }

    /**
     * @return the totalServGravados
     */
    public double getTotalServGravados() {
        return totalServGravados;
    }

    /**
     * @param totalServGravados the totalServGravados to set
     */
    public void setTotalServGravados(double totalServGravados) {
        this.totalServGravados = totalServGravados;
    }

    /**
     * @return the totalSerExentos
     */
    public double getTotalSerExentos() {
        return totalSerExentos;
    }

    /**
     * @param totalSerExentos the totalSerExentos to set
     */
    public void setTotalSerExentos(double totalSerExentos) {
        this.totalSerExentos = totalSerExentos;
    }

    /**
     * @return the totalMercanciasGravadas
     */
    public double getTotalMercanciasGravadas() {
        return totalMercanciasGravadas;
    }

    /**
     * @param totalMercanciasGravadas the totalMercanciasGravadas to set
     */
    public void setTotalMercanciasGravadas(double totalMercanciasGravadas) {
        this.totalMercanciasGravadas = totalMercanciasGravadas;
    }

    /**
     * @return the totalMercanciasExentas
     */
    public double getTotalMercanciasExentas() {
        return totalMercanciasExentas;
    }

    /**
     * @param totalMercanciasExentas the totalMercanciasExentas to set
     */
    public void setTotalMercanciasExentas(double totalMercanciasExentas) {
        this.totalMercanciasExentas = totalMercanciasExentas;
    }

    /**
     * @return the totalGravado
     */
    public double getTotalGravado() {
        return totalGravado;
    }

    /**
     * @param totalGravado the totalGravado to set
     */
    public void setTotalGravado(double totalGravado) {
        this.totalGravado = totalGravado;
    }

    /**
     * @return the totalExento
     */
    public double getTotalExento() {
        return totalExento;
    }

    /**
     * @param totalExento the totalExento to set
     */
    public void setTotalExento(double totalExento) {
        this.totalExento = totalExento;
    }

    /**
     * @return the totalVenta
     */
    public double getTotalVenta() {
        return totalVenta;
    }

    /**
     * @param totalVenta the totalVenta to set
     */
    public void setTotalVenta(double totalVenta) {
        this.totalVenta = totalVenta;
    }

    /**
     * @return the totalDescuentos
     */
    public double getTotalDescuentos() {
        return totalDescuentos;
    }

    /**
     * @param totalDescuentos the totalDescuentos to set
     */
    public void setTotalDescuentos(double totalDescuentos) {
        this.totalDescuentos = totalDescuentos;
    }

    /**
     * @return the totalVentaNeta
     */
    public double getTotalVentaNeta() {
        return totalVentaNeta;
    }

    /**
     * @param totalVentaNeta the totalVentaNeta to set
     */
    public void setTotalVentaNeta(double totalVentaNeta) {
        this.totalVentaNeta = totalVentaNeta;
    }

    /**
     * @return the totalImpuesto
     */
    public double getTotalImpuesto() {
        return totalImpuesto;
    }

    /**
     * @param totalImpuesto the totalImpuesto to set
     */
    public void setTotalImpuesto(double totalImpuesto) {
        this.totalImpuesto = totalImpuesto;
    }

    /**
     * @return the totalComprobante
     */
    public double getTotalComprobante() {
        return totalComprobante;
    }

    /**
     * @param totalComprobante the totalComprobante to set
     */
    public void setTotalComprobante(double totalComprobante) {
        this.totalComprobante = totalComprobante;
    }
}
