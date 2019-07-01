/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica.negocio;

import java.util.ArrayList;
import util.Estado;

/**
 * Instancia los clientes con sus atributos.
 * @author dark-reaper
 */
public class Cliente extends Persona {
    
    private double limiteCredito;
    private boolean aprobarCredito;
    private Estado estado;
    
    /**
     * Constructor de clase Cliente.
     */
    public Cliente() {
        super();
    }
    
    /**
     * Constructor de clase cliente, con parámetros.
     * @param cedula Cédula de persona.
     * @param tipoCed El tipo decédula de la persona (física o jurídica).
     * @param nombre Nombre de persona.
     * @param dir dirección de la persona
     * @param limiteCredito Limite credito de persona.
     * @param aprobarCredito Si aprobar credito de persona.
     * @param estado Estado de persona.
     * @param contactos Lista contactos de persona.
     */
    public Cliente(String cedula, String tipoCed, String nombre, Direccion dir, 
            double limiteCredito, boolean aprobarCredito, 
            String estado, ArrayList<Contacto> contactos) {
        
        super(cedula, tipoCed, nombre, dir, contactos);
        this.limiteCredito = limiteCredito;
        this.aprobarCredito = aprobarCredito;
        this.estado = estado.equals("A") ? Estado.Activo : Estado.Deshabilitado;
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
