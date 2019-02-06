/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica.negocio;

import java.util.ArrayList;
import util.Estado;

/**
 * Instancia la persona con sus atributos.
 * @author dark-reaper
 */
public class Persona {
    
    private String codigo;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private String cedula;
    private float limiteCredito;
    private boolean aprobarCredito;
    private Direccion dir;
    private ArrayList<Contacto> contactos;
    private Estado estado;
    
    /**
     * Constructor vacío de clase persona.
     */
    public Persona() {
        
    }
    /**
     * Constructor de clase persona, inicializa variables.
     * @param codigo codigo persona.
     * @param nombre nombre persona.
     * @param apellido1 primer apellido persona.
     * @param apellido2 segundo apellido persona.
     * @param cedula cedula persona.
     * @param limiteCredito limite credito persona.
     * @param aprobarCredito aprobar credito persona.
     * @param dir codigo de la direccion de la persona.
     * @param contactos contactos persona.
     * @param estado Estado de persona.
     */
    public Persona(String codigo, String nombre, String apellido1, 
            String apellido2, String cedula, float limiteCredito,
            boolean aprobarCredito, Direccion dir,
            ArrayList<Contacto> contactos, String estado) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.cedula = cedula;
        this.limiteCredito = limiteCredito;
        this.aprobarCredito = aprobarCredito;
        this.dir = dir;
        this.contactos = contactos;
        this.estado = estado.equals("A") ? Estado.Activo : Estado.Deshabilitado;
    }
    /**
     * Obtener código de persona.
     * @return el codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Establecer código de persona
     * @param codigo el codigo
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
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
     * Obtener apellido 1 de persona.
     * @return El apellido1
     */
    public String getApellido1() {
        return apellido1;
    }

    /**
     * Establecer apellido 1 de persona
     * @param apellido1 el apellido1
     */
    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    /**
     * Obtener apellido 2 de persona.
     * @return El apellido2
     */
    public String getApellido2() {
        return apellido2;
    }

    /**
     * Establecer apellido 2 de persona
     * @param apellido2 the apellido2 to set
     */
    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
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
     * Obtener límite de crédito de persona.
     * @return El limiteCredito
     */
    public float getLimiteCredito() {
        return limiteCredito;
    }

    /**
     * Establecer limite de crédito de persona
     * @param limiteCredito the limiteCredito to set
     */
    public void setLimiteCredito(float limiteCredito) {
        this.limiteCredito = limiteCredito;
    }

    /**
     * Obtener si el credito de persona se aprueba.
     * @return La aprobación de credito
     */
    public boolean isAprobarCredito() {
        return aprobarCredito;
    }

    /**
     * Establecer si se aprueba credito de contactos
     * @param aprobarCredito el aprobarCredito
     */
    public void setAprobarCredito(boolean aprobarCredito) {
        this.aprobarCredito = aprobarCredito;
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
    
    /**
     * Obtener el estado.
     * @return El estado
     */
    public Estado getEstado() {
        return estado;
    }

    /**
     * Establecer estado de persona
     * @param estado El estado.
     */
    public void setEstado(Estado estado) {
        this.estado = estado;
    }
}
