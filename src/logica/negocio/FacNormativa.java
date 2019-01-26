/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica.negocio;

/**
 * Instancia con sus atributos, la clase para normativa vigente de las 
 * facturas.
 * @author darkreaper
 */
public class FacNormativa {
    
    private static final String NUMERO_RESOLUCION = "DGT-R-48-2016";
    private static final String FECHA_RESOLUCION = "07-10-2016 08:00:00";
    
    /**
     * Constructor vacío de la clase Normativa.
     */
    public FacNormativa() {
        
    }
    
    /**
     * Obtener el númeo de resolución de la normativa.
     * @return el número de resolución
     */
    public String getNumeroResolucion() {
        return NUMERO_RESOLUCION;
    }
    
    /**
     * Obtener la fecha de resolución de la normativa.
     * @return la fecha de resolución
     */
    public String getFechaResolucion() {
        return FECHA_RESOLUCION;
    }
    
}
