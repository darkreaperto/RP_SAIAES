/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica.negocio;

/**
 *
 * @author aoihanabi
 */
public class Impuesto {
    
    private String codImpuesto;
    private String codigoImpuesto;
    private double tarifaImpuesto;
    private double montoImpuesto;
    //Exoneración
    private Exoneracion exoneracion;

    /**
     * Constructor vacío de clase Impuesto.
     */
    public Impuesto() {
    }

    /**
     * Constructor de clase Impuesto, inicializa variables.
     * @param codImpuesto codigo de impuesto para la bd
     * @param codigoImpuesto codigo de tipo de impuesto (según Hacienda)
     * @param tarifaImpuesto tarifa(en porcentaje) del impuesto
     * @param montoImpuesto monto(subtotal * tarifa impuesto) del impuesto
     * @param exoneracion objeto exoneracion en caso de existir
     */
    public Impuesto(String codImpuesto, String codigoImpuesto, 
            double tarifaImpuesto, double montoImpuesto, Exoneracion exoneracion) {
        this.codImpuesto = codImpuesto;
        this.codigoImpuesto = codigoImpuesto;
        this.tarifaImpuesto = tarifaImpuesto;
        this.montoImpuesto = montoImpuesto;
        this.exoneracion = exoneracion;
    }

    /**
     * Obtener el código de impuesto de la BD.
     * @return the codImpuesto
     */
    public String getCodImpuesto() {
        return codImpuesto;
    }

    /**
     * Obtener el código de impuesto de hacienda.
     * @return the codigoImpuesto
     */
    public String getCodigoImpuesto() {
        return codigoImpuesto;
    }

    /**
     * Obtener la tarifa del impuesto.
     * @return the tarifaImpuesto
     */
    public double getTarifaImpuesto() {
        return tarifaImpuesto;
    }

    /**
     * Obtener el monto del impuesto.
     * @return the montoImpuesto
     */
    public double getMontoImpuesto() {
        return montoImpuesto;
    }

    /**
     * Obtener el objeto exoneación.
     * @return the codExoneImpu
     */
    public Exoneracion getExoneracion() {
        return exoneracion;
    }

    /**
     * Establecer codigo de impuesto para la bd
     * @param codImpuesto codigo del impuesto para bd
     */
    public void setCodImpuesto(String codImpuesto) {
        this.codImpuesto = codImpuesto;
    }

    /**
     * Establecer código del tipo de impuesto según hacienda
     * @param codigoImpuesto codigo del tipo de impuesto específicado por hacieda
     */
    public void setCodigoImpuesto(String codigoImpuesto) {
        this.codigoImpuesto = codigoImpuesto;
    }

    /**
     * Establecer la tarifa de impuesto en porcentaje
     * @param tarifaImpuesto tarifa de impuesto en porcentaje
     */
    public void setTarifaImpuesto(double tarifaImpuesto) {
        this.tarifaImpuesto = tarifaImpuesto;
    }

    /**
     * Establecer el monto/valor de impuesto
     * @param montoImpuesto monto/valor de impuesto
     */
    public void setMontoImpuesto(double montoImpuesto) {
        this.montoImpuesto = montoImpuesto;
    }
    
    /**
     * Establecer la exoneración en caso de existir.
     * @param exoneracion objeto exoneración a establecer
     */
    public void setExoneracion(Exoneracion exoneracion) {
        this.exoneracion = exoneracion;
    }
    
    
    
}
