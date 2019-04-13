/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica.negocio;
import java.util.ArrayList;

/**
 * Instancia los proveedores con sus atributos.
 * @author dark-reaper
 */
public class Proveedor extends Persona {
    
    private String codProveedor;
    
    /**
     * Constructor de clase Proveedor.
     */
    public Proveedor() {
        super();
    }
    
    /**
     * Constructor de clase proveedor, con parámetros.
     * @param codigo Código de persona.
     * @param nombre Nombre de persona.
     * @param cedula Cédula de persona.
     * @param tipoCed El tipo de cédula de la persona (física o jurídica).
     * @param dir Direccón de la persona.
     * @param contactos Lista contactos de persona.
     * @param codProveedor Codigo de proveedor
     * @param estado Estado de persona.
     */
    public Proveedor(String codigo, String nombre, String cedula, String tipoCed, 
            Direccion dir, ArrayList<Contacto> contactos, String codProveedor, 
            String estado) {
        
        super(codigo, nombre, cedula, tipoCed, dir, contactos, estado);
        this.codProveedor = codProveedor;
    }
    
    /**
     * Obtener codigo de proveedor.
     * @return el codigo de proveedor. 
    */
    public String getCodProveedor() {
        return codProveedor;
    }

    /**
     * Establecer codigo de proveedor.
     * @param codProveedor el codigo de proveedor
     */
    public void setCodProveedor(String codProveedor) {
        this.codProveedor = codProveedor;
    }
    /**
     * Sobreescritura del método toString para obtener información básica del
     * proveedor
     * @return información básica del producto/madera
     */
    @Override
    public String toString(){
     return this.getCedula() + ":_" + this.getNombre();
    }
}
