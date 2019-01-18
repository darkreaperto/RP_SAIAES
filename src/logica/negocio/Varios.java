/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica.negocio;

/**
 * Instancia productos o servicios varios/generales con sus atributos.
 * @author aoihanabi
 */
public class Varios {
    private String codigo;
    private String descripcion;
    private double precio;
    //private String unidadMedida;
    //private String cantidad;
    
    /**
     * Constructor vacío de clase varios.
     */
    public Varios() {
    }

    /**
     * Constructor de la clase varios, inicializa variables.
     * @param codigo codigo del producto/venta en la bd
     * @param descripcion descripción del producto/venta
     * @param precio monto de la venta 
     */
    public Varios(String codigo, String descripcion, double precio) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.precio = precio;
    }

    /**
     * Establecer codigo del producto/venta
     * @param codigo El codigo en la BD.
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * Establecer la descripción del producto/venta
     * @param descripcion  La descripcion del producto en la BD.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Establecer el precio del producto/venta
     * @param precio El precio del producto/venta en la BD.
     */
    public void setPrecio(double precio) {
        this.precio = precio;
    }

    /**
     * Obtener el codigo.
     * @return el codigo.
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Obtener la descripción.
     * @return la descripción.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Obtener el precio.
     * @return el precio.
     */
    public double getPrecio() {
        return precio;
    }   
}
