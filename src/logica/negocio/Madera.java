/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica.negocio;

import util.Estado;

/**
 * Instancia los productos con sus atributos. 
 * @author dark-reaper
 */
public class Madera {
    private int cantidad;
    private double precioXvara;
    private String codigo;
    private String codTipoProducto;
    private String descTipoProducto;
    private String codProducto;
    private String nombre;
    private String codTipoMadera;
    private String descTipoMadera;
    private String medidas;
    
    
    private String descripcion;
    private String codProveedor;   
    private Estado estado;
    
    
    /**
     * Constructor vacío de clase madera.
     */
    public Madera() {
        
    }
    /**
     * Constructor de clase madera, inicializa variables.
     * @param codigo codigo producto.
     * @param codProducto codigo especificado de producto.
     * @param nombre nombre del producto(madera).
     * @param codTipoMadera codigo del tipo de madera.
     * @param descTipoMadera descripcion del tipo de madera.
     * @param medidas medidas del producto de acuerdo a su tipo.
     * @param codTipoProducto tipo de producto(troza, acerrada, terminada).
     * @param cantidad unidades de producto.
     * @param descTipoProducto descripcion del tipo de producto.
     * @param precioXvara precio por vara del producto.
     * @param descripcion descripciones detalladas del producto.
     * @param estado Estado de producto.
     * @param codProveedor codigo del proveedor.
     */
    public Madera(String codigo, String codProducto, String nombre, 
            String codTipoMadera, String descTipoMadera, String medidas, 
            String codTipoProducto, String descTipoProducto, int cantidad, 
            double precioXvara, String descripcion, String estado,
            String codProveedor) {
        this.codigo = codigo;
        this.codProducto = codProducto;
        this.nombre = nombre;
        this.codTipoMadera = codTipoMadera;
        this.descTipoMadera = descTipoMadera;
        this.medidas = medidas;
        this.codTipoProducto = codTipoProducto;
        this.descTipoProducto = descTipoProducto;
        this.cantidad = cantidad;
        this.precioXvara = precioXvara;
        this.descripcion = descripcion;
        this.estado = estado.equals("A") ? Estado.Activo : Estado.Deshabilitado;
        this.codProveedor = codProveedor;
        
    }

    /**
     * Obtener el codigo.
     * @return el codigo.
     */
    public String getCodigo() {
        return codigo;
    }
    
    /**
     * Establecer codigo de producto
     * @param codigo El codigo en la BD.
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    
    /**
     * Obtener el codigo personalizado del producto.
     * @return el codigo del producto
     */
    public String getCodProducto() {
        return codProducto;
    }
    
    /**
     * Establecer codigo personalizado del producto
     * @param codProducto  El codigo especificado del producto.
     */
    public void setCodProducto(String codProducto) {
        this.codProducto = codProducto;
    }
    
    /**
     * Obtener el nombre del producto.
     * @return el nombre 
     */
    public String getNombre() {
        return nombre;
    }
    
    /**
     * Establecer nombre de producto
     * @param nombre El nombre.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    /**
     * Obtener el codigo del tipo de madera del producto.
     * @return el codigo de tipo de madera
     */
    public String getCodTipoMadera() {
        return codTipoMadera;
    }
    
    /**
     * Establecer codigo de tipo de madera de producto
     * @param codTipoMadera codigo del tipo de madera
     */
    public void setCodTipoMadera(String codTipoMadera) {
        this.codTipoMadera = codTipoMadera;
    }

    public String getDescTipoMadera() {
        return descTipoMadera;
    }

    public void setDescTipoMadera(String descTipoMadera) {
        this.descTipoMadera = descTipoMadera;
    }
    
    /**
     * Obtener las medidas.
     * @return las medidas
     */
    public String getMedidas() {
        return medidas;
    }
    
    /**
     * Establecer medidas de producto
     * @param medidas las medidas.
     */
    public void setMedidas(String medidas) {
        this.medidas = medidas;
    }
    
    /**
     * Obtener el codigo del tipo de producto.
     * @return codigo del tipo
     */
    public String getCodTipoProducto() {
        return codTipoProducto;
    }
    
    /**
     * Establecer codigo de tipo de producto del producto
     * @param codTipoProducto el codigo del tipo.
     */
    public void setCodTipoProducto(String codTipoProducto) {
        this.codTipoProducto = codTipoProducto;
    }

    public String getDescTipoProducto() {
        return descTipoProducto;
    }

    public void setDescTipoProducto(String descTipoProducto) {
        this.descTipoProducto = descTipoProducto;
    }
    
    /**
     * Obtener la cantidad por unidad.
     * @return la cantidad
     */
    public int getCantidad() {
        return cantidad;
    }
    
    /**
     * Establecer cantidad de producto
     * @param cantidad la cantidad
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    
    /**
     * Obtener el precio por vara.
     * @return el precio por vara
     */
    public double getPrecioXvara() {
        return precioXvara;
    }
    
    /**
     * Establecer precio por cada vara de producto
     * @param precioXvara El precio.
     */
    public void setPrecioXvara(double precioXvara) {
        this.precioXvara = precioXvara;
    }
    
    /**
     * Obtener la descripción.
     * @return la descripcion.
     */
    public String getDescripcion() {
        return descripcion;
    }
    
    /**
     * Establecer descripción de producto
     * @param descripcion la descripción.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    /**
     * Obtener el estado.
     * @return El estado
     */
    public Estado getEstado() {
        return estado;
    }
    
    /**
     * Establecer estado de producto
     * @param estado El estado.
     */
    public void setEstado(Estado estado) {
        this.estado = estado;
    }
    
    /**
     * Obtener el codigo del proveedor.
     * @return El codigo del proveedor
     */
    public String getCodProveedor() {
        return codProveedor;
    }
    /**
     * Establecer el codigo del proveedor
     * @param codProveedor El codigo del proveedor
     */
    public void setCodProveedor(String codProveedor) {
        this.codProveedor = codProveedor;
    }
    
}
