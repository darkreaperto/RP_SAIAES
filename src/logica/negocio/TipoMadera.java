/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica.negocio;

/**
 * Clase que contiene los tipos de madera.
 * @author aoihanabi
 */
public class TipoMadera {
    private String codigo;
    private String descripcion;
    private String estado;

    /**
     * Constructor vacío de la clase tipo de madera.
     */
    public TipoMadera() {
    }
    /**
     * Constructor de la clase tipo de madera, inicializa variables
     * @param codigo codigo del tipo de madera
     * @param descripcion descripción del tipo de madera (cedro, pino, etc.)
     * @param estado estado del tipo de madera
     */
    public TipoMadera(String codigo, String descripcion, String estado) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.estado = estado;        
    }
    /**
     * Obtener el código del tipo de madera
     * @return el código del tipo de madera
     */
    public String getCodigo() {
        return codigo;
    }
    /**
     * Establecer el código del tipo de madera
     * @param codigo el código  del tipo de madera
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    /**
     * Obtener la descripción del tipo de madera
     * @return la descripción del tipo de madera
     */
    public String getDescripcion() {
        return descripcion;
    }
    /**
     * Establecer la descripción del tipo de madera (nombre)
     * @param descripcion la descripción del tipo de madera
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    /**
     * Obtener el estado del tipo de madera
     * @return el estado del tipo de madera
     */
    public String getEstado() {
        return estado;
    }
    /**
     * Establecer el estado
     * @param estado El estado del tipo de madera
     */
    public void setEstado(String estado) {
        this.estado = estado;
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
