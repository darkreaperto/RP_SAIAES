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
    
    /** Código del cliente */
    private String codCliente;
    
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
     * @param apellido1 Apellido 1 de persona.
     * @param apellido2 Apellido 2 de persona.
     * @param cedula Cédula de persona.
     * @param limiteCredito Limite credito de persona.
     * @param aprobarCredito Si aprobar credito de persona.
     * @param contactos Lista contactos de persona.
     * @param codCliente Codigo de cliente.
     * @param estado Estado de persona.
     */
    public Cliente(String codigo, String nombre, String apellido1, 
            String apellido2, String cedula, float limiteCredito,
            boolean aprobarCredito, ArrayList<Contacto> contactos, 
            String codCliente, String estado) {
        
        super(codigo, nombre, apellido1, apellido2, cedula, limiteCredito,
                aprobarCredito, contactos, estado);
        this.codCliente = codCliente;
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
}
