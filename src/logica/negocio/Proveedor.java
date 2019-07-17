/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica.negocio;
import java.util.ArrayList;
import util.Estado;

/**
 * Instancia los proveedores con sus atributos.
 * @author dark-reaper
 */
public class Proveedor extends Persona {
    
    /** Estado del proveedor (activo o deshabilitado). */
    private Estado estado;
    
    /**
     * Constructor vacío de clase Proveedor.
     */
    public Proveedor() {
        super();
    }
    
    /**
     * Constructor sobrecarcado de clase Proveedor.
     * @param cedula cédula del proveedor.
     * @param nombre nombre del proveedor.
     */
    public Proveedor(String cedula, String nombre) {
        super(cedula, nombre);
    }
    
    /**
     * Constructor de clase proveedor, con parámetros.
     * @param cedula Número de cédula de la persona.
     * @param tipoCed El tipo de cédula de la persona (física o jurídica).
     * @param nombre Nombre completo de la persona.
     * @param dir Dirección física de la persona.
     * @param estado Estado activo o deshabilitado del proveedor.
     * @param contactos Lista contactos de persona.
     */
    public Proveedor(String cedula, String tipoCed, String nombre, Direccion dir, 
            String estado, ArrayList<Contacto> contactos) {
        
        super(cedula, tipoCed, nombre, dir, contactos);
        
        this.estado = estado.equals("A") ? Estado.Activo : Estado.Deshabilitado;
    }
    
    /**
     * Obtener el estado (activo o deshabilitado) de la persona.
     * @return El estado (activo o deshabilitado).
     */
    public Estado getEstado() {
        return estado;
    }

    /**
     * Establecer estado (activo o deshabilitado) de persona
     * @param estado El estado (activo o deshabilitado).
     */
    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    /**
     * Sobreescritura del método toString para obtener información básica del
     * proveedor
     * @return información básica del proveedor (Cédula: Nombre completo).
     */
    @Override
    public String toString(){
     return this.getCedula() + ": " + this.getNombre();
    }
}
