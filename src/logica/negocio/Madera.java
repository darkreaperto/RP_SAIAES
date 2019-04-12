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
    
    
    private String codigo;
    private String codProducto;
    private String codOrigen;
    private String codTipoMadera;
    private String descTipoMadera;
    private double cantVaras;
    private String grueso;
    private String ancho;
    private double precioXvara;
    private String descripcion;  
    private String tipoProducto;
    private Estado estado;
    
    
    /**
     * Constructor vacío de clase madera.
     */
    public Madera() {
        
    }
    /**
     * Constructor de clase madera, inicializa variables.
     * @param codigo codigo producto.
     * @param codProducto codigo especificado por el cliente.
     * @param codOrigen codigo de la madera que dio origen a la actual.
     * @param codTipoMadera codigo del tipo de madera.
     * @param descTipoMadera descripcion del tipo de madera.
     * @param grueso grueso del producto.
     * @param ancho ancho del producto.
     * @param tipoProducto tipo de producto(troza, acerrada, terminada).
     * @param precioXvara precio por vara del producto.
     * @param descripcion descripciones detalladas del producto.
     * @param estado Estado de producto.
     * @param cantvaras Producto en existencia medido en varas.
     */
    public Madera(String codigo, String codProducto, String codOrigen, 
            String descripcion, double precioXvara, double cantvaras,
            String grueso, String ancho, String codTipoMadera, 
            String descTipoMadera, String tipoProducto,  String estado) {
        this.codigo = codigo;
        this.codProducto = codProducto;
        this.codTipoMadera = codTipoMadera;
        this.descTipoMadera = descTipoMadera;
        this.cantVaras = cantvaras; 
        this.grueso = grueso;
        this.ancho = ancho;
        this.tipoProducto = tipoProducto;
        this.precioXvara = precioXvara;
        this.descripcion = descripcion;
        this.estado = estado.equals("A") ? Estado.Activo : Estado.Deshabilitado;         
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
    
    /**
     * Obtener descripción del tipo de madera de producto
     * @return descripción del tipo de madera
     */
    public String getDescTipoMadera() {
        return descTipoMadera;
    }
    /***
     * Establecer descripción del tipo de madera de producto
     * @param descTipoMadera descripción/nombre del tipo de madera
     */
    public void setDescTipoMadera(String descTipoMadera) {
        this.descTipoMadera = descTipoMadera;
    }
    
    /**
     * Obtener cantidad en varas de la madera
     * @return cantidad en varas de la madera
     */
    public double getCantVaras() {
        return cantVaras;
    }
    /***
     * Establecer cantidad en varas de la madera
     * @param cantVaras cantidad en varas de la madera
     */
    public void setCantVaras(double cantVaras) {
        this.cantVaras = cantVaras;
    }
    
    /**
     * Obtener las grueso
     * @return las grueso     */
    public String getGrueso() {
        return grueso;
    }
    
    /**
     * Establecer grueso de producto
     * @param grueso el grueso.
     */
    public void setGrueso(String grueso) {
        this.grueso = grueso;
    }
    
    /**
     * Obtener las ancho.
     * @return las ancho
     */
    public String getAncho() {
        return ancho;
    }
    
    /**
     * Establecer ancho de producto
     * @param ancho las ancho.
     */
    public void setAncho(String ancho) {
        this.ancho = ancho;
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
     * Obtener el tipo de producto.
     * @return tipo de producto
     */
    public String getTipoProducto() {
        return tipoProducto;
    }
    
    /**
     * Establecer el tipo de producto.
     * @param tipoProducto tipo de producto
     */
    public void setTipoProducto(String tipoProducto) {
        this.tipoProducto = tipoProducto;
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
     * Sobreescritura del método toString para obtener información básica del
     * producto/madera
     * @return información básica del producto/madera
     */
    @Override
    public String toString()
    {
     return codProducto +": " + descTipoMadera + " " + grueso + " " + ancho;
    }
}
