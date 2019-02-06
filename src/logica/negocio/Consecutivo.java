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
public class Consecutivo {
    
    private String cod;
    private String codComprob;
    private String tipoComprob;
    private int consecutivo;
    
    /**
     * Constructor vacío de la clase Consecutivo.
     */
    public Consecutivo() {
        
    }
    
    /**
     * Constructor de la clase Consecutivo: Almacena la información de los 
     * consecutivos de todos los comprobantes.
     * @param cod código del consecutivo en la BD
     * @param codComprob código del comprobante electrónico
     * @param tipoComprob tipo de comprobante electrónico
     * @param consecutivo  consecutivo del comprobante electrónico
     */
    public Consecutivo(String cod, String codComprob, String tipoComprob, 
            int consecutivo) {
        
        this.cod = cod;
        this.codComprob = codComprob;
        this.tipoComprob = tipoComprob;
        this.consecutivo = consecutivo;
    }

    /**
     * Obtener el código del consecutivo en la BD.
     * @return the cod
     */
    public String getCod() {
        return cod;
    }

    /**
     * Obtener el código del comprobante electrónico.
     * @return the codComprob
     */
    public String getCodComprob() {
        return codComprob;
    }

    /**
     * Obtener el tipo del comprobante electrónico.
     * @return the tipoComprob
     */
    public String getTipoComprob() {
        return tipoComprob;
    }

    /**
     * Obtener el número consecutivo.
     * @return the consecutivo
     */
    public int getConsecutivo() {
        return consecutivo;
    }

    /**
     * Establecer el código de consecutivo de la BD.
     * @param cod the cod to set
     */
    public void setCod(String cod) {
        this.cod = cod;
    }

    /**
     * Establecer el código de comprobante electrónico.
     * @param codComprob the codComprob to set
     */
    public void setCodComprob(String codComprob) {
        this.codComprob = codComprob;
    }

    /**
     * Establecer el tipo de comprobante electrónico.
     * @param tipoComprob the tipoComprob to set
     */
    public void setTipoComprob(String tipoComprob) {
        this.tipoComprob = tipoComprob;
    }

    /**
     * Establecer el número de consecutivo.
     * @param consecutivo the consecutivo to set
     */
    public void setConsecutivo(int consecutivo) {
        this.consecutivo = consecutivo;
    }
}
