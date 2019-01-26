/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica.negocio;

/**
 * Instancia con sus atributos, la clase para mecanismo de seguridad de las 
 * facturas.
 * @author darkreaper
 */
public class FacSeguridad {
    
    private String dsSignature;
    
    /**
     * Constructor vacío de la clase Seguridad.
     */
    public FacSeguridad() {
        
    }
    
    /**
     * Constructor de la clase Seguridad.
     * @param dsSignature firma de seguridad
     */
    public FacSeguridad(String dsSignature) {
        this.dsSignature = dsSignature;
    }
    
    /**
     * Obtener la firma de seguridad.
     * @return la firma de seguridad
     */
    public String getDsSignature() {
        return dsSignature;
    }
    
    /**
     * Establecer la firma de seguridad.
     * @param dsSignature la firma a establecer.
     */
    public void setDsSignature(String dsSignature) {
        this.dsSignature = dsSignature;
    }
}
