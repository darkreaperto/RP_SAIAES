/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica.negocio;

import java.util.Date;

/**
 * Instancia los detalles de exoneración. 
 * @author aoihanabi
 */
public class Exoneracion {
    
    private String tipoDocumento;
    private String numeroDocumento;
    private String nombreInstitucion;
    private Date fechaEmision;
    private double montoImpuesto;
    private double porcentajeCompra; //porcentaje de la compra autorizada o exoneración

    /**
     * Constructor vacío de clase Exoneración.
     */
    public Exoneracion() {
        
    }
    
    /**
     * Constructor de clase Exoneración, inicializa variables.
     * @param tipoDocumento tipo de documento de exoneración
     * @param numeroDocumento numero de documento de exoneración
     * @param nombreInstitucion nombre de institución que emitió la exoneración 
     * @param fechaEmision fecha en que se emite el documento de exoneración
     * @param montoImpuesto monto de impuesto exonerado o autorizado sin impuesto
     * @param porcentajeCompra porcentaje de la compra autorizada o exoneración
     */
    public Exoneracion(String tipoDocumento, 
            String numeroDocumento, String nombreInstitucion, Date fechaEmision, 
            double montoImpuesto, double porcentajeCompra) {
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
        this.nombreInstitucion = nombreInstitucion;
        this.fechaEmision = fechaEmision;
        this.montoImpuesto = montoImpuesto;
        this.porcentajeCompra = porcentajeCompra * 100;
    }

    /**
     * Establecer tipo de documento de exoneración
     * @param tipoDocumento tipo de documento de exoneración
     */
    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    /**
     * Establecer número de documento de exoneración
     * @param numeroDocumento 
     */
    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    /**
     * Establecer nombre de institución que emite el documento de exoneración
     * @param nombreInstitucion institución que emite el documento
     */
    public void setNombreInstitucion(String nombreInstitucion) {
        this.nombreInstitucion = nombreInstitucion;
    }

    /**
     * Establecer fecha de emisión del documento de exoneración
     * @param fechaEmision 
     */
    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    /**
     * Establecer monto del impuesto a exonerar
     * @param montoImpuesto monto del impuesto a exonerar
     */
    public void setMontoImpuesto(double montoImpuesto) {
        this.montoImpuesto = montoImpuesto;
    }

    /**
     * Establecer porcentaje de exoneración del documento.
     * @param porcentajeCompra porcentaje de exoneración
     */
    public void setPorcentajeCompra(double porcentajeCompra) {
        this.porcentajeCompra = porcentajeCompra;
    }

    /**
     * Obtener el tipo de documento de exoneración
     * @return tipo de documento de exoneración
     */
    public String getTipoDocumento() {
        return tipoDocumento;
    }

    /**
     * Obtener el número de documento de exoneración
     * @return número de documento de exoneración
     */
    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    /**
     * Obtener el nombre de la institución que emite la exoneración
     * @return nombre de la institución que emite la exoneración
     */
    public String getNombreInstitucion() {
        return nombreInstitucion;
    }

    /**
     * Obtener la fecha de emisión del doc de exoneración
     * @return fecha de emisión del documento de exoneración
     */
    public Date getFechaEmision() {
        return fechaEmision;
    }

    /**
     * Obtener el monto de impuesto de exoneración
     * @return monto de impuesto a exonerar
     */
    public double getMontoImpuesto() {
        return montoImpuesto;
    }

    /**
     * Obtener el porcentaje de exoneración
     * @return porcentaje de exoneración
     */
    public double getPorcentajeCompra() {
        return porcentajeCompra;
    }
    
}
