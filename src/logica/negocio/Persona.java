/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica.negocio;

import java.util.ArrayList;
import util.TipoCedula;

/**
 * Instancia la persona con sus atributos.
 * @author dark-reaper
 */
public class Persona {
    
    private String cedula;
    private TipoCedula tipoCed;
    private String nombre;
    private Direccion dir;
    private ArrayList<Contacto> contactos;
    
    /**
     * Constructor vacío de clase persona.
     */
    public Persona() {
        
    }
    /**
     * Constructor de clase persona, inicializa variables.
     * @param nombre nombre persona.
     * @param cedula cedula persona.
     * @param tipoCed tipo de cédula de la persona (física o jurídica).
     * @param dir codigo de la direccion de la persona.
     * @param contactos contactos persona.
     */
    public Persona(String cedula, String nombre, String tipoCed, 
            Direccion dir, ArrayList<Contacto> contactos) {
        this.nombre = nombre;
        this.cedula = cedula;
        this.tipoCed = tipoCed.toUpperCase().equals("FISICA") ? 
                TipoCedula.FISICA : TipoCedula.JURIDICA;
        this.dir = dir;
        this.contactos = contactos;
    }
    
    /**
     * Obtener nombre de persona.
     * @return El nombre.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establecer nombre de persona
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtener cédula de persona.
     * @return La cedula
     */
    public String getCedula() {
        return cedula;
    }

    /**
     * Establecer cedula de persona
     * @param cedula la cedula
     */
    public void setCedula(String cedula) {
        this.cedula = cedula;
    }
    
    /**
     * Obtener el tipo de cédula de la persona.
     * @return el tipo de cédula
     */
    public TipoCedula getTipoCedula() {
        return tipoCed;
    }

    /**
     * Establecer el tipo de cédula de la persona.
     * @param tipoCed el tipo de cédula
     */
    public void setTipoCedula(TipoCedula tipoCed) {
        this.tipoCed = tipoCed;
    }

    /**
     * Obtener direccion de persona.
     * @return La dirección
     */
    public Direccion getDireccion () {
        return dir;
    }

    /**
     * Establecer direccion de persona
     * @param dir la direccion
     */
    public void setDireccion(Direccion dir) {
        this.dir = dir;
    }
    
    /**
     * Obtener lista de contactos de persona.
     * @return La lista de contacto.
     */
    public ArrayList<Contacto> getContactos() {
        return contactos;
    }

    /**
     * Establecer lista de contactos
     * @param contactos El contacto
     */
    public void setContactos(ArrayList<Contacto> contactos) {
        this.contactos = contactos;
    }
}
