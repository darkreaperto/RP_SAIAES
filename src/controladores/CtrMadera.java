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
    public CtrMadera(String codigo, String codProducto, String codTipoMadera, 
            String descTipoMadera, String medidas, String tipoProducto, 
            int unidades, double precioXvara, String descripcion, String estado,
            String codProveedor, String nomProveedor) {
        madera = new Madera(codigo, codProducto, codTipoMadera, 
                descTipoMadera, medidas, tipoProducto, unidades, precioXvara, 
                descripcion, estado, codProveedor, nomProveedor);
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
    
    /**
     * Buscar producto enviando por parámetro el criterio de búsqueda.
     * @param param Parametros para consultar producto en la base de datos
     * @return lista de productos
     */
    public ArrayList consultarProductos(String param) {
        return mdlMadera.consultarProductos(param);
    }
    
    public boolean crearProducto(int codProd, int codTipoMadera, 
            String medida, String tipoProducto, int unidades, double precio, 
            String descripcion, String codProveedor, String nomProveedor) {
        return mdlMadera.crearProducto(codProd, codTipoMadera, medida, 
                tipoProducto, unidades, precio, descripcion, codProveedor, 
                nomProveedor);
    }
}
