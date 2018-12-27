/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica.negocio;

import util.Estado;
import util.TipoProd;

/**
 *
 * @author aoihanabi
 */
public class TipoProducto {
    private String codigo;
    private String descripcion;
    private Estado estado;
    private TipoProd tipo;
    /**
     * Constructor vacío del tipo de producto.
     */
    public TipoProducto() {
    }
    /**
     * Constructor de la clase tipo de producto, inicializa variables
     * @param codigo codigo del tipo de producto
     * @param descripcion descripción del tipo de producto
     * @param estado estado del tipo de producto
     */
    public TipoProducto(String codigo, String descripcion, String estado) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.estado = estado.equals("A") ? Estado.Activo : Estado.Deshabilitado;
        if(descripcion.equals("Troza")) {
            this.tipo = TipoProd.TROZA;
        } else if (descripcion.equals("Acerrada")) {
            this.tipo = TipoProd.ASERRADA;
        } else if (descripcion.equals("Terminada")) {
            this.tipo = TipoProd.TERMINADA;
        }
        
    }
    /**
     * Obtener el código del tipo de producto.
     * @return el código del tipo de producto.
     */
    public String getCodigo() {
        return codigo;
    }
    /**
     * Establecer el código del tipo de producto
     * @param codigo el código  del tipo de producto
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    /**
     * Obtener la descripción del tipo de producto.
     * @return la descripción del tipo de producto.
     */
    public String getDescripcion() {
        return descripcion;
    }
    /**
     * Establecer la descripción del tipo de producto
     * @param descripcion la descripción  del tipo de producto
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    /**
     * Obtener el estado del tipo de producto.
     * @return el estado del tipo de producto.
     */
    public Estado getEstado() {
        return estado;
    }
    /**
     * Establecer el estado del tipo de producto
     * @param estado el estado  del tipo de producto
     */
    public void setEstado(Estado estado) {
        this.estado = estado;
    }
    /**
     * Obtener el tipo de producto.
     * @return el tipo de producto.
     */
    public TipoProd getTipo() {
        return tipo;
    }
    /**
     * Establecer el tipo de producto
     * @param tipo el tipo de producto
     */
    public void setTipo(TipoProd tipo) {
        this.tipo = tipo;
    }
    /**
     * Sobreescribe el método toString para obtener información detallada del 
     * tipo de madera
     * @return la descripción del tipo de madera
     */
    @Override
    public String toString()
    {
     return descripcion;
    }
}
