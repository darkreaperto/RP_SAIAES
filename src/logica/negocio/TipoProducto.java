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
    
    public TipoProducto() {
    }

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
    
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public TipoProd getTipo() {
        return tipo;
    }

    public void setTipo(TipoProd tipo) {
        this.tipo = tipo;
    }
    
    @Override
    public String toString()
    {
     return descripcion;
    }
}
