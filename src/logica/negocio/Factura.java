/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica.negocio;

import java.util.Date;

/**
 * Instancia las facturas con sus atributos.
 * @author aoihanabi
 */
public class Factura {
    //Encabezado
    private String codigoFac;
    private String clave;
    private String numeroConsecutivo;
    private Date fechaEmision;
    private String nombreEmisor;
    private String tipoIdentEm;
    private String numeroIdentEm;
    private String provinciaEm;
    private String cantonEm;
    private String distritoEm;
    private String otrasSenasEm;
    private int codigoPaisEm;
    private int numTelefonoEm;
    private String correoElectronicoEm;
    private String codReceptor;
    private String condicionVenta;
    private String plazoCredito; 
    private String medioPago;
    //Linea de pedido
    private String codigoLPedido;
    //ResumenFactura
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
    //Información de referencia
    //Normativa vigente (resolución)
    //Mecanismo de seguridad
    

    /**
     * Constructor vacío de clase de factura.
     */
    public Factura() {
        
    }
    
    /**
     * Constructor de clase de factura, inicializa variables.
     * @param codigoFac codigo de factura para la bd.
     * @param clave clave numerica de factura.
     * @param numeroConsecutivo consecutivo de factura.
     * @param fechaEmision fecha en que se emite la factura.
     * @param nombreEmisor nombre de quien realiza la factura (aserradero).
     * @param tipoIdentEm tipo de identificación del emisor de factura.
     * @param numeroIdentEm numero de identificación del emisor de factura.
     * @param provinciaEm ubicación del emisor: provincia.
     * @param cantonEm ubicación del emisor: cantón.
     * @param distritoEm ubicación del emisor: distrito.
     * @param otrasSenasEm ubicación del emisor: otras señas.
     * @param codigoPaisEm código de país del emisor (506).
     * @param numTelefonoEm número telefónico del emisor .
     * @param correoElectronicoEm correo electrónico del emisor.
     * @param codReceptor codigo del cliente que recibe la factura.
     * @param condicionVenta condición de venta (crédito, contado).
     * @param plazoCredito plazo para crédito.
     * @param medioPago medio de págo de factura (efectivo, tarjeta, cheque, etc).
     * @param codigoLPedido codigo del pedido (lista de productos a facturar)
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
    public Factura(String codigoFac, String clave, String numeroConsecutivo, 
            Date fechaEmision, String nombreEmisor, String tipoIdentEm, 
            String numeroIdentEm, String provinciaEm, String cantonEm, 
            String distritoEm, String otrasSenasEm, int codigoPaisEm, 
            int numTelefonoEm, String correoElectronicoEm, String codReceptor, 
            String condicionVenta, String plazoCredito, String medioPago, 
            String codigoLPedido, String codigoMoneda, double tipoCambio, 
            double totalServGravados, double totalSerExentos, 
            double totalMercanciasGravadas, double totalMercanciasExentas, 
            double totalGravado, double totalExento, double totalVenta, 
            double totalDescuentos, double totalVentaNeta, double totalImpuesto, 
            double totalComprobante) {
        this.codigoFac = codigoFac;
        this.clave = clave;
        this.numeroConsecutivo = numeroConsecutivo;
        this.fechaEmision = fechaEmision;
        this.nombreEmisor = nombreEmisor;
        this.tipoIdentEm = tipoIdentEm;
        this.numeroIdentEm = numeroIdentEm;
        this.provinciaEm = provinciaEm;
        this.cantonEm = cantonEm;
        this.distritoEm = distritoEm;
        this.otrasSenasEm = otrasSenasEm;
        this.codigoPaisEm = codigoPaisEm;
        this.numTelefonoEm = numTelefonoEm;
        this.correoElectronicoEm = correoElectronicoEm;
        this.codReceptor = codReceptor;
        this.condicionVenta = condicionVenta;
        this.plazoCredito = plazoCredito;
        this.medioPago = medioPago;
        this.codigoLPedido = codigoLPedido;
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
     * Establecer código de factura para la base de datos
     * @param codigoFac código de factura para la bd
     */
    public void setCodigoFac(String codigoFac) {
        this.codigoFac = codigoFac;
    }

    /**
     * Establecer clave numérica de factura.
     * @param clave clave numérica solicitada por Hacienda
     */
    public void setClave(String clave) {
        this.clave = clave;
    }

    /**
     * Establecer número consecutivo de factura.
     * @param numeroConsecutivo consecutivo solicitado por Hacienda
     */
    public void setNumeroConsecutivo(String numeroConsecutivo) {
        this.numeroConsecutivo = numeroConsecutivo;
    }

    /**
     * Establecer fecha de emisión de factura
     * @param fechaEmision fecha en que es emitida la factura
     */
    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    /**
     * Establecer nombre de quien realiza/envía la factura
     * @param nombreEmisor nombre del emisor(Aserradero)
     */
    public void setNombreEmisor(String nombreEmisor) {
        this.nombreEmisor = nombreEmisor;
    }

    /**
     * Establecer tipo de identificación del emisor de factura
     * @param tipoIdentEm tipo de identificación del emisor(Aserradero)
     */
    public void setTipoIdentEm(String tipoIdentEm) {
        this.tipoIdentEm = tipoIdentEm;
    }

    /**
     * Establecer número de identificación del emisor
     * @param numeroIdentEm número de identifación del emisor(Aserradero)
     */
    public void setNumeroIdentEm(String numeroIdentEm) {
        this.numeroIdentEm = numeroIdentEm;
    }

    /**
     * Establecer provincia en que se ubica el emisor.
     * @param provinciaEm provincia en que se ubica el emisor(Aserradero)
     */
    public void setProvinciaEm(String provinciaEm) {
        this.provinciaEm = provinciaEm;
    }
    /**
     * Establecer cantón en que se ubica el emisor.
     * @param cantonEm cantón en que se ubica el emisor(Aserradero)
     */
    public void setCantonEm(String cantonEm) {
        this.cantonEm = cantonEm;
    }
    
    /**
     * Establecer distrito en que se ubica el emisor.
     * @param distritoEm distrito en que se ubica el emisor
     */
    public void setDistritoEm(String distritoEm) {
        this.distritoEm = distritoEm;
    }

    /**
     * Establecer otras señas detallando ubicación del emisor
     * @param otrasSenasEm otras señas para ubicar el emisor
     */
    public void setOtrasSenasEm(String otrasSenasEm) {
        this.otrasSenasEm = otrasSenasEm;
    }

    /**
     * Establecer número/código de país del emisor
     * @param codigoPaisEm código de país del emisor
     */
    public void setCodigoPaisEm(int codigoPaisEm) {
        this.codigoPaisEm = codigoPaisEm;
    }

    /**
     * Establecer número telefónico del emisor
     * @param numTelefonoEm número telefónico del emisor(Aserradero)
     */
    public void setNumTelefonoEm(int numTelefonoEm) {
        this.numTelefonoEm = numTelefonoEm;
    }

    /**
     * Establecer correo electrónico del emisor
     * @param correoElectronicoEm correo electrónico del emisor(Aserradero)
     */
    public void setCorreoElectronicoEm(String correoElectronicoEm) {
        this.correoElectronicoEm = correoElectronicoEm;
    }

    /**
     * Establecer código de cliente que recibirá la factura
     * @param codReceptor código cliente receptor de factura
     */
    public void setCodReceptor(String codReceptor) {
        this.codReceptor = codReceptor;
    }

    /**
     * Establecer condición de venta
     * @param condicionVenta condición de venta (crédito, contado)
     */
    public void setCondicionVenta(String condicionVenta) {
        this.condicionVenta = condicionVenta;
    }

    /**
     * Establecer plazo para crédito
     * @param plazoCredito plazo de crédito
     */
    public void setPlazoCredito(String plazoCredito) {
        this.plazoCredito = plazoCredito;
    }

    /**
     * Establecer medio de pago de factura
     * @param medioPago medio de pago(tarjeta, efectivo, etc.)
     */
    public void setMedioPago(String medioPago) {
        this.medioPago = medioPago;
    }

    /**
     * Obtener código de factura (BD)
     * @return código de factura para bd
     */
    public String getCodigoFac() {
        return codigoFac;
    }
        
    /**
     * Obtener clave numérica
     * @return clave numérica
     */
    public String getClave() {
        return clave;
    }

    /**
     * Obtener número consecutivo de factura
     * @return número consecutivo
     */
    public String getNumeroConsecutivo() {
        return numeroConsecutivo;
    }
    
    /**
     * Obtener fecha de emisión de factura
     * @return fecha de emisión
     */
    public Date getFechaEmision() {
        return fechaEmision;
    }

    /**
     * Obtener nombre del emisor de factura
     * @return nombre del emisor de factura
     */
    public String getNombreEmisor() {
        return nombreEmisor;
    }

    /**
     * Obtener tipo de identificación del emisor
     * @return tipo de identificación del emisor
     */
    public String getTipoIdentEm() {
        return tipoIdentEm;
    }

    /**
     * Obtener número de identificación del emisor
     * @return número de identificación del emisor
     */
    public String getNumeroIdentEm() {
        return numeroIdentEm;
    }

    /**
     * Obtener provincia en que se ubica el emisor
     * @return provincia del emisor
     */
    public String getProvinciaEm() {
        return provinciaEm;
    }

    /**
     * Obtener catón en que se ubica el emisor
     * @return cantón del emisor
     */
    public String getCantonEm() {
        return cantonEm;
    }

    /**
     * Obtener distrito en que se ubica el emisor
     * @return distrito del emisor
     */
    public String getDistritoEm() {
        return distritoEm;
    }

    /**
     * Obtener otras señas para ubicar el emisor
     * @return otras señas para ubicar emisor
     */
    public String getOtrasSenasEm() {
        return otrasSenasEm;
    }

    /**
     * Obtener código de país del emisor
     * @return código de país del emisor
     */
    public int getCodigoPaisEm() {
        return codigoPaisEm;
    }

    /**
     * Obtener número de teléfono del emisor
     * @return número telefónico del emisor
     */
    public int getNumTelefonoEm() {
        return numTelefonoEm;
    }

    /**
     * Obtener correo electrónico del emisor
     * @return correo del emisor
     */
    public String getCorreoElectronicoEm() {
        return correoElectronicoEm;
    }

    /**
     * Obtener cliente receptor de la facutura
     * @return cliente al que se le realiza la factura
     */
    public String getReceptor() {
        return codReceptor;
    }

    /**
     * Obtener condición de venta de factura 
     * @return condición de venta de factura (crédito, contado, etc.)
     */
    public String getCondicionVenta() {
        return condicionVenta;
    }

    /**
     * Obtener plazo del crédito
     * @return plazo del crédito
     */
    public String getPlazoCredito() {
        return plazoCredito;
    }

    /**
     * Obtener medio de pago de factura
     * @return medio de pago de factura (efectivo, tarjeta, etc.)
     */
    public String getMedioPago() {
        return medioPago;
    }
}
