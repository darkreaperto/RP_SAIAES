/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

/**
 * Instancia la persona con sus atributos.
 * @author dark-reaper
 */
public class Persona {
    
    private int codigo_persona;
    private String nombre_persona;
    private String apellido1_persona;
    private String apellido2_persona;
    private String cedula_persona;
    private float limiteCredito_persona;
    
    
    private Persona() {
        
    }
    
    /**
     * @return the codigo_persona
     */
    public int getCodigo_persona() {
        return codigo_persona;
    }

    /**
     * @return the nombre_persona
     */
    public String getNombre_persona() {
        return nombre_persona;
    }

    /**
     * @return the apellido1_persona
     */
    public String getApellido1_persona() {
        return apellido1_persona;
    }

    /**
     * @return the apellido2_persona
     */
    public String getApellido2_persona() {
        return apellido2_persona;
    }

    /**
     * @return the cedula_persona
     */
    public String getCedula_persona() {
        return cedula_persona;
    }

    /**
     * @return the limiteCredito_persona
     */
    public float getLimiteCredito_persona() {
        return limiteCredito_persona;
    }

    /**
     * @param codigo_persona the codigo_persona to set
     */
    public void setCodigo_persona(int codigo_persona) {
        this.codigo_persona = codigo_persona;
    }

    /**
     * @param nombre_persona the nombre_persona to set
     */
    public void setNombre_persona(String nombre_persona) {
        this.nombre_persona = nombre_persona;
    }

    /**
     * @param apellido1_persona the apellido1_persona to set
     */
    public void setApellido1_persona(String apellido1_persona) {
        this.apellido1_persona = apellido1_persona;
    }

    /**
     * @param apellido2_persona the apellido2_persona to set
     */
    public void setApellido2_persona(String apellido2_persona) {
        this.apellido2_persona = apellido2_persona;
    }

    /**
     * @param cedula_persona the cedula_persona to set
     */
    public void setCedula_persona(String cedula_persona) {
        this.cedula_persona = cedula_persona;
    }

    /**
     * @param limiteCredito_persona the limiteCredito_persona to set
     */
    public void setLimiteCredito_persona(float limiteCredito_persona) {
        this.limiteCredito_persona = limiteCredito_persona;
    }
    
}
