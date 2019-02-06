/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica.negocio;

import util.Estado;
import util.TipoContacto;

/**
 * Instancia los contactos con sus atributos.
 * @author aoihanabi
 */
public class Contacto {
    
    private String codigo;
    private String info;
    private TipoContacto tipo;
    private String codTipo;
    private Estado estado;
    
    /**
     * Constructor de clase contacto, con parámetros.
     * @param codigo Código contacto.
     * @param info Informacion del tipo de contacto.
     * @param codTipo Código tipo de contacto del cliente.
     * @param estado Estado de contacto.
     */
    public Contacto(String codigo, String info, String codTipo, String estado) {
        this.codigo = codigo;
        this.info = info;
        this.tipo = codTipo.equals("1") ? TipoContacto.CORREO : 
                TipoContacto.TELEFONO;
        this.codTipo = codTipo;
        this.estado = estado.equals("A") ? Estado.Activo : Estado.Deshabilitado;
    }
    
    /**
     * Obtener código.
     * @return Codigo de contacto
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Establecer código
     * @param codigo el código a establecer
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * Obtener la información.
     * @return La información
     */
    public String getInfo() {
        return info;
    }

    /**
     * Establecer la información
     * @param info la inforamción
     */
    public void setInfo(String info) {
        this.info = info;
    }

    /**
     * Obtener el tipo de contacto
     * @return El tipo
     */
    public TipoContacto getTipo() {
        return tipo;
    }

    /**
     * Establecer el tipo de contacto
     * @param tipo El tipo de contacto
     */
    public void setTipo(TipoContacto tipo) {
        this.tipo = tipo;
    }

    /** Obtener codigo del tipo.
     * @return el codigo del tipo
     */
    public String getCodTipo() {
        return codTipo;
    }

    /**
     * Establecer código del tipo
     * @param codTipo El código del tipo
     */
    public void setCodTipo(String codTipo) {
        this.codTipo = codTipo;
    }
    
    /**
     * Obtener el estado.
     * @return El estado
     */
    public Estado getEstado() {
        return estado;
    }

    /**
     * Establecer el estado del contacto.
     * @param estado El estado del contacto.
     */
    public void setEstado(Estado estado) {
        this.estado = estado;
    }    
}
