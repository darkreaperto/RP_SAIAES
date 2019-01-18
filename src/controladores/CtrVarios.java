/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import java.util.ArrayList;
import logica.negocio.Varios;
import modelos.MdlVarios;

/**
 * Controlador de la clase varios.
 * @author aoihanabi
 */
public class CtrVarios {
    private static CtrVarios instancia = null;
    MdlVarios mdlVarios;
    Varios prodVario;
    ArrayList <Varios> prodVarios;
    
    /**
     * Constructor del controlador de varios, inicializa variables.
     */
    public CtrVarios() {
        prodVarios = new ArrayList<>();
        mdlVarios = new MdlVarios();
    }
    
    
    public CtrVarios(String codigo, String descripcion, double precio) {
        prodVario = new Varios(codigo, descripcion, precio);
    }
    
    /**
     * Obtener instancia única del controlador de varios.
     * @return Instancia única de clase varios
     */
    public static CtrVarios getInstancia() {
        return instancia == null ? new CtrVarios() : instancia;
    }
    
    /**
     * Inserta un nuevo producto "varios" en la BD.
     * @param precio precio del producto
     * @param descripcion detalle del producto (opcional)
     * @return verdadero si inserta el producto.
     */
    public boolean crearVarios(String descripcion, double precio) {
        return mdlVarios.crearVarios(descripcion, precio);
    }
}
