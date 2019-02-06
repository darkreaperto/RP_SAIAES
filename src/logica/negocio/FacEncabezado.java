/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica.negocio;

import java.util.Date;

/**
 * Instancia con sus atributos, la clase para encabezado de las facturas.
 * @author darkreaper 
 */
public class FacEncabezado {
    
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
    private Cliente receptor;
    private String condicionVenta;
    private String plazoCredito; 
    private String medioPago;
    
    /**
     * Constructor vacío de la clase Encabezado contenida en Factura.
     */
    public FacEncabezado() {
        
    }
    
    /**
     * Constructor de clase Encabezado contenida en Factura, inicializa variables.
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
     * @param receptor objeto Cliente con toda la información que recibe la factura.
     * @param condicionVenta condición de venta (crédito, contado).
     * @param plazoCredito plazo para crédito.
     * @param medioPago medio de págo de factura (efectivo, tarjeta, cheque, etc). 
     */
    
    public FacEncabezado(String codigoFac, String clave, String numeroConsecutivo, 
            Date fechaEmision, String nombreEmisor, String tipoIdentEm, 
            String numeroIdentEm, String provinciaEm, String cantonEm, 
            String distritoEm, String otrasSenasEm, int codigoPaisEm, 
            int numTelefonoEm, String correoElectronicoEm, Cliente receptor, 
            String condicionVenta, String plazoCredito, String medioPago) {
        
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
        this.receptor = receptor;
        this.condicionVenta = condicionVenta;
        this.plazoCredito = plazoCredito;
        this.medioPago = medioPago;
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
     * Establecer rl cliente que recibirá la factura
     * @param receptor cliente receptor de factura
     */
    public void setReceptor(Cliente receptor) {
        this.receptor = receptor;
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
    public Cliente getReceptor() {
        return receptor;
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
