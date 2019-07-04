/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/**
 * Clase enumeración para los tipos de cédula.
 * @author darkreaper
 */
public enum TipoCedula {

    /**
     *
     */
    FISICA ("Física"),

    /**
     *
     */
    JURIDICA ("Jurídica");
    
    /**
     * Constructor and methods class.
     */
    String desc; //Descripción del tipo de cédula
    private TipoCedula(String desc) {
        this.desc = desc;
    }
    
    /**
     * Retorna todos los valores de la enumeración.
     * @return el arreglo con los valores
     */
    public static String[] getValues() {
        String[] values = new String[TipoCedula.values().length];
        
        int i=0;
        for (TipoCedula tc: TipoCedula.values()) {
            values[i] = tc.desc;
            i++;
        }
        
        return values;
    }
    
    /**
     * Retorna la enumeración correspodiente a su valor.
     * @param value descripción de la enumeración
     * @return la enumeración
     */
    public static TipoCedula getEnum(String value) {
        switch (value.toUpperCase()) {
            case "FÍSICA":
                return FISICA;
            case "FISICA":
                return FISICA;
            case "JURÍDICA":
                return JURIDICA;
            case "JURIDICA":
                return JURIDICA;
            default:
                return FISICA;
        }
    }
    
    @Override
    public String toString() {
        return this.desc;
    }
}
