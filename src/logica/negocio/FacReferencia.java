/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica.negocio;

import java.util.Date;

/**
 * Instancia con sus atributos, la clase para información de referencia de las 
 * facturas.
 * @author darkreaper
 */
public class FacReferencia {
    
    private String tipoDoc;
    private String numero;
    private Date fechaEmision;
    private String codigo;
    private String razon;
    
    /**
     * Constructor vacío de la clase Información de Referencia contenida en factura.
     */
    public FacReferencia() {
        
    }
    
    /**
     * Constructor de la clase Información de Referencia contenida en factura.
     * @param tipoDoc tipo de documento de referencia
     * @param numero numero de referencia
     * @param fechaEmision fecha de emisión de la referencia
     * @param codigo código del documento de referencia.
     * @param razon razón del documento de referencia.
     */
    public FacReferencia(String tipoDoc, String numero, Date fechaEmision, 
            String codigo, String razon) {
        this.tipoDoc = tipoDoc;
        this.numero = numero;
        this.fechaEmision = fechaEmision;
        this.codigo = codigo;
        this.razon = razon;
    }

    /**
     * Obtener el tipo de documento de la referencia.
     * @return the tipoDoc
     */
    public String getTipoDoc() {
        return tipoDoc;
    }

    /**
     * Obtener el número de la referencia.
     * @return the numero
     */
    public String getNumero() {
        return numero;
    }

    /**
     * Obtener la fecha de emisión de la referencia.
     * @return the fechaEmision
     */
    public Date getFechaEmision() {
        return fechaEmision;
    }
    
    /**
     * Obtener el código de la referencia.
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }
    
    /**
     * Obtener la razón de la referencia.
     * @return the razon
     */
    public String getRazon() {
        return razon;
    }

    /**
     * Establecer el tipo de documento de la referencia.
     * @param tipoDoc the tipoDoc to set
     */
    public void setTipoDoc(String tipoDoc) {
        this.tipoDoc = tipoDoc;
    }

    /**
     * Establecer el número de la referencia.
     * @param numero the numero to set
     */
    public void setNumero(String numero) {
        this.numero = numero;
    }

    /**
     * Establecer la fecha de emisión de la referencia.
     * @param fechaEmision the fechaEmision to set
     */
    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }
    
    /**
     * Establecer el código de la referencia.
     * @param codigo the codigo to set
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    
    /**
     * Establecer la razón de la referencia.
     * @param razon the razon to set
     */
    public void setRazon(String razon) {
        this.razon = razon;
    }
}
