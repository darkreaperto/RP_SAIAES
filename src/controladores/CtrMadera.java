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
    
    /**
     * Constructor de controlador de madera, crea un objeto madera con
     * sus parámetros.
     * @param codigo codigo de madera
     * @param codProducto codigo del producto (personalizado)
     * @param codTipoMadera codigo del tipo de madera
     * @param descTipoMadera descripción del tipo de madera
     * @param grueso grueso de la madera/producto
     * @param ancho ancho de la madera/producto
     * @param tipoProducto tipo de producto (aserrada/troza/terminada)
     * @param unidades cantidad de productos en unidades
     * @param precioXvara precio del producto 
     * @param descripcion detalles que describen el producto
     * @param estado estado activo o inactivo del producto
     * @param codProveedor codigo del proveedor del producto(solo troza)
     * @param nomProveedor nombre del proveedor del producto(solo troza)
     * @param cantVaras cantidad del producto en varas
     * @param pulgadas cantidad del producto en pulgadas (si es troza)
     * 
     */
    public CtrMadera(String codigo, String codProducto, String codTipoMadera, 
            String descTipoMadera, String grueso, String ancho, 
            String tipoProducto, double precioXvara, 
            String descripcion, String estado, String codProveedor, 
            String nomProveedor, double cantVaras, double pulgadas
    ) {
        
        madera = new Madera(codigo, codProducto, codTipoMadera, 
            descTipoMadera, grueso, ancho, 
            tipoProducto, precioXvara, descripcion, estado, codProveedor, 
            nomProveedor, cantVaras, pulgadas);
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
     * @return lista de tipos de productos.
     */
    public ArrayList<Madera> obtenerProductos() {
        return mdlMadera.obtenerProductos();
    }
    /**
     * Verifica si la lista de productos está vacía para llamar el método que
     * la llena desde la bd, en caso de no estar vacía ya se tienen los productos
     * así que se retorna la lista existente.
     * @return lista de productos existentes
     */
    public ArrayList<Madera> getListaProductos() {
        if(productos == null || productos.isEmpty()) {
            productos = obtenerProductos();
            System.out.println("CtrMadera says: I'M EMPTY");
            return obtenerProductos();
        } else {
            System.out.println("CtrMadera says: I was empty");
            return productos;
        }
    }
    /**
     * Buscar producto enviando por parámetro el criterio de búsqueda.
     * @param param Parametros para consultar producto en la base de datos
     * @return lista de productos
     */
    public ArrayList consultarProductos(String param) {
        return mdlMadera.consultarProductos(param);
    }
    
    /**
     * Llena una lista con productos según la información especificada para 
     * la búsqueda.
     * @param paramProd Datos del producto para consultar usuario en la bd
     * @param codBusq código de clasificación/especificación de búsqueda
     * @return lista de productos.
     */
    public ArrayList<Madera> busqAvzProductos(String paramProd, int codBusq) {
        return mdlMadera.busqAvzProductos(paramProd, codBusq);
    }
    /**
     * Inserta un nuevo producto en la BD
     *
     * @param codProd codigo personalizado asignado al producto
     * @param codTipoMadera codigo del tipo de madera del producto
     * @param grueso grueso del producto
     * @param cantVaras cantidad de varas que entran del producto
     * @param ancho ancho del producto
     * @param tipoProducto descripcion de cual es el tipo de producto
     * @param pulgadas cantidad de madera en pulgadas
     * @param precio precio por vara del producto
     * @param descripcion detalle del producto (opcional)
     * @param codProveedor codigo del proveedor
     * @return verdadero si inserta el producto.
     */
    public boolean crearProducto(String codProd, String descripcion, 
            double precio, double cantVaras, String grueso, String ancho,
            double pulgadas, int codTipoMadera, String tipoProducto,
            int codProveedor) {
        
        return mdlMadera.crearProducto(codProd, descripcion, precio, cantVaras,
                grueso, ancho, pulgadas, codTipoMadera, tipoProducto, 
                codProveedor);
    }
    
    /**
     * 
     * @param codProd codigo personalizado asignado al producto
     * @param codTipoMadera codigo del tipo de madera del producto
     * @param grueso grueso del producto
     * @param cantVaras cantidad del producto en varas
     * @param ancho ancho del producto
     * @param precio precio por vara del producto
     * @param pulgadas cantidad del producto en pulgadas (si es troza)
     * @param descripcion detalle del producto (opcional)
     * @param codProveedor codigo del proveedor
     * @param codigo codigo de madera
     * @return verdadero si actualiza el producto exitosamente
     */
    public boolean actualizarProducto(String codProd, String descripcion, 
            double precio, double cantVaras, String grueso, String ancho,
            double pulgadas, int codTipoMadera, int codProveedor, String codigo) {
        
        return mdlMadera.actualizarProducto(codProd, descripcion, precio,
                cantVaras, grueso, ancho, pulgadas, codTipoMadera, 
                codProveedor, codigo);
    }
    /**
     * Llama el método que inactiva un producto en la bd
     * @param codigo codigo del producto/madera
     * @return Verdadero si inactiva el producto
     */
    public boolean inactivarProducto(String codigo) {
        
        return mdlMadera.inactivarProducto(codigo);
    }
    /**
     * Llama el método que activa un producto en la bd
     * @param codigo codigo del producto/madera
     * @return verdadero si activa el producto exitosamente
     */
    public boolean activarProducto(String codigo) {
        
        return mdlMadera.activarProducto(codigo);
    }
    /**
     * Llama el método que actualiza un producto ya existente en el inventario
     * @param tipoProd tipo de producto (aserrada/troza/terminada)
     * @param unidades cantidad en unidades del producto
     * @param codigo codigo de madera
     * @return verdadero si el producto se actualiza exitosamente.
     */
    public boolean actualizarInventario(String tipoProd, int unidades, String codigo) {
        
        return mdlMadera.actualizarInventario(tipoProd, unidades, codigo);
    }
}