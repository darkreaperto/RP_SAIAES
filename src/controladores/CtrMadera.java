/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import java.util.ArrayList;
import logica.negocio.Madera;
import modelos.MdlMadera;

/**
 * Controlador de la clase Madera.
 * @author aoihanabi
 */
public class CtrMadera {
    private static CtrMadera instancia = null;
    MdlMadera mdlMadera;
    Madera madera;
    ArrayList <Madera> productos;
    
    /**
     * Constructor del controlador de madera, inicializa variables.
     */
    public CtrMadera() {
        productos = new ArrayList<>();
        mdlMadera = new MdlMadera();
    }
    public CtrMadera(String codigo, String codProducto, String nombre, 
            String codTipoMadera, String descTipoMadera, String medidas, 
            String codTipoProducto, String descTipoProducto, int cantidad, 
            double precioXvara, String descripcion, String estado,
            String codProveedor) {
        madera = new Madera(codigo, codProducto, nombre, codTipoMadera, 
                descTipoMadera, medidas, codTipoProducto, descTipoProducto,
                cantidad, precioXvara, descripcion, estado, codProveedor);
    }
    /**
     * Obtener instancia única del controlador de madera.
     * @return Instancia única de Madera
     */
    public static CtrMadera getInstancia() {
        return instancia == null ? new CtrMadera() : instancia;
    }
    
    /**
     * Llena una lista con todos los tipos de productos almacenados en la BD.
     *
     * @return lista de tipos de productos.
     */
    public ArrayList<Madera> obtenerProductos() {
        return mdlMadera.obtenerProductos();
    }
    
    public boolean crearProducto(int codProd, String nombre, int codTipoMadera, 
            String medida, int codTipoProducto, int cantidad, double precio, 
            String descripcion) {
        return mdlMadera.crearProducto(codProd, nombre, codTipoMadera, medida, 
                codTipoProducto, cantidad, precio, descripcion);
    }
}
