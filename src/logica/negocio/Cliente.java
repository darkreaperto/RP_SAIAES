/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica.negocio;

import java.util.ArrayList;

/**
 * Instancia los clientes con sus atributos.
 * @author dark-reaper
 */
public class Cliente extends Persona {
    
    private String codCliente;
    private double limiteCredito;
    private boolean aprobarCredito;
    
    /**
     * Constructor de clase Cliente.
     */
    public Cliente() {
        super();
    }
    
    /**
     * Constructor de clase cliente, con parámetros.
     * @param codigo Código de persona.
     * @param nombre Nombre de persona.
     * @param cedula Cédula de persona.
     * @param tipoCed El tipo decédula de la persona (física o jurídica).
     * @param limiteCredito Limite credito de persona.
     * @param aprobarCredito Si aprobar credito de persona.
     * @param dir dirección de la persona
     * @param contacto Lista contactos de persona.
     * @param codCliente Codigo de cliente.
     * @param estado Estado de persona.
     */
    public Cliente(String codigo, String nombre, String cedula, String tipoCed, 
            double limiteCredito, boolean aprobarCredito, Direccion dir, 
            ArrayList<Contacto> contacto, String codCliente, String estado) {
        
        super(codigo, nombre, cedula, tipoCed, dir, contacto, estado);
        this.codCliente = codCliente;
        this.limiteCredito = limiteCredito;
        this.aprobarCredito = aprobarCredito;
    }

    /**
     * Obtener codigo de cliente.
     * @return el codigo de cliente. 
    */
    public String getCodCliente() {
        return codCliente;
    }

    /**
     * Establecer codigo de cliente.
     * @param codCliente el codigo de cliente
     */
    public void setCodCliente(String codCliente) {
        this.codCliente = codCliente;
    }
    
    /**
     * Obtener límite de crédito de persona.
     * @return El limiteCredito
     */
    public double getLimiteCredito() {
        return limiteCredito;
    }

    /**
     * Establecer limite de crédito de persona
     * @param limiteCredito the limiteCredito to set
     */
    public void setLimiteCredito(double limiteCredito) {
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
     * Sobrescritura del metodo to string para accesar información básica
     * del cliente
     * @return el nombre completo del cliente.
     */
    @Override
    public String toString() {
        return this.getCedula() + ":_" + this.getNombre();
    }
}
