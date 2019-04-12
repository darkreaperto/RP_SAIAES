/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica.negocio;

import util.Estado;

/**
 *
 * @author fuent
 */
public class Troza {    
    private String codigo;
    private String codInterno;
    private String codTipoMadera;
    private String descTipoMadera;
    private double pulgadas;
    private String tipoProducto;
    private String descripcion;  
    private String codProveedor;
    private String nomProveedor; 
    private Estado estado;

    /**
     * Constructor de la clase troza
     * @param codigo codigo para la bd
     * @param codInterno codigo para uso en el sistema
     * @param codTipoMadera codigo bd del tipo de madera
     * @param descTipoMadera nombre del tipo de madera
     * @param pulgadas cantidad de madera en pulgadas
     * @param tipoProducto TROZA
     * @param descripcion descripcion de la troza
     * @param codProveedor codigo bd del proveedor
     * @param nomProveedor nombre del proveedor
     * @param estado estado del registro troza
     */
    public Troza(String codigo, String codInterno, String codTipoMadera, 
            String descTipoMadera, double pulgadas, String tipoProducto, 
            String descripcion, String codProveedor, String nomProveedor, 
            String estado) {
        this.codigo = codigo;
        this.codInterno = codInterno;
        this.codTipoMadera = codTipoMadera;
        this.descTipoMadera = descTipoMadera;
        this.pulgadas = pulgadas;
        this.tipoProducto = tipoProducto;
        this.descripcion = descripcion;
        this.codProveedor = codProveedor;
        this.nomProveedor = nomProveedor;
        this.estado = estado.equals("A") ? Estado.Activo : Estado.Deshabilitado;
    }

    /**
     * Obtener el codigo bd de la troza
     * @return el codigo bd de la troza
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Establecer el codigo bd de la troza
     * @param codigo el codigo bd de la troza
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * Obtener el codigo interno para sistema
     * @return el codigo interno para sistema
     */
    public String getCodInterno() {
        return codInterno;
    }

    /**
     * Establecer el codigo interno para sistema
     * @param codInterno el codigo interno para sistema
     */
    public void setCodInterno(String codInterno) {
        this.codInterno = codInterno;
    }

    /**
     * Obtener el codigo bd del tipo de madera
     * @return el codigo bd del tipo de madera
     */
    public String getCodTipoMadera() {
        return codTipoMadera;
    }

    /**
     * Establecer el codigo bd del tipo de madera
     * @param codTipoMadera el codigo bd del tipo de madera
     */
    public void setCodTipoMadera(String codTipoMadera) {
        this.codTipoMadera = codTipoMadera;
    }

    /**
     * Obtener el nombre del tipo de madera
     * @return el nombre del tipo de madera
     */
    public String getDescTipoMadera() {
        return descTipoMadera;
    }

    /**
     * Establecer el nombre del tipo de madera
     * @param descTipoMadera el nombre del tipo de madera
     */
    public void setDescTipoMadera(String descTipoMadera) {
        this.descTipoMadera = descTipoMadera;
    }

    /**
     * Obtener la cantidad en pulgadas
     * @return la cantidad en pulgadas
     */
    public double getPulgadas() {
        return pulgadas;
    }

    /**
     * Establecer la cantidad en pulgadas
     * @param pulgadas la cantidad en pulgadas
     */
    public void setPulgadas(double pulgadas) {
        this.pulgadas = pulgadas;
    }

    /**
     * Obtener el tipo de producto (troza duh)
     * @return el tipo de producto (troza duh)
     */
    public String getTipoProducto() {
        return tipoProducto;
    }

    /**
     * Establecer el tipo de producto (troza duh)
     * @param tipoProducto el tipo de producto (troza duh)
     */
    public void setTipoProducto(String tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

    /**
     * Obtener la descripcion de la troza 
     * @return la descripcion de la troza 
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establecer la descripcion de la troza 
     * @param descripcion la descripcion de la troza 
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtener codigo del proveedor
     * @return codigo del proveedor
     */
    public String getCodProveedor() {
        return codProveedor;
    }

    /**
     * Establecer codigo del proveedor
     * @param codProveedor codigo del proveedor
     */
    public void setCodProveedor(String codProveedor) {
        this.codProveedor = codProveedor;
    }

    /**
     * Obtener el nombre del proveedor
     * @return el nombre del proveedor
     */
    public String getNomProveedor() {
        return nomProveedor;
    }

    /**
     * Establecer el nombre del proveedor
     * @param nomProveedor el nombre del proveedor
     */
    public void setNomProveedor(String nomProveedor) {
        this.nomProveedor = nomProveedor;
    }

    /**
     * Obtener el estado de la troza
     * @return el estado de la troza
     */
    public Estado getEstado() {
        return estado;
    }

    /**
     * Establecer el estado de la troza
     * @param estado el estado de la troza
     */
    public void setEstado(Estado estado) {
        this.estado = estado;
    }
}
