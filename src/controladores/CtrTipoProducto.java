/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import java.util.ArrayList;
import logica.negocio.TipoProducto;
import modelos.MdlTipoProducto;

/**
 * Controlador de la clase Tipo de Producto.
 * @author aoihanabi
 */
public class CtrTipoProducto {
    
    MdlTipoProducto mdlTipoProducto;
    TipoProducto tipoProducto;
    ArrayList <TipoProducto> tipos;
    
    /**
     * Constructor del controlador de usuario, inicializa variables.
     */
    public CtrTipoProducto() {
        tipos = new ArrayList<>();
        mdlTipoProducto = new MdlTipoProducto();
    }
    /**
     * Constructor del controlador de tipo de producto, crea un objeto de la 
     * clase tipo de producto
     * @param codigo
     * @param descripcion
     * @param estado 
     */
    public CtrTipoProducto(String codigo, String descripcion, String estado) {
        tipoProducto = new TipoProducto(codigo, descripcion, estado);
    }
    
    /**
     * Llena una lista con todos los tipos de producto almacenados en la BD.
     * @return lista de tipos de producto.
     */
    public ArrayList<TipoProducto> obtenerTiposProducto() {
        return mdlTipoProducto.obtenerTiposProducto();
    }
}
