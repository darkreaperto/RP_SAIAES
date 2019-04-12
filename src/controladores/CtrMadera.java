/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import java.util.ArrayList;
import logica.negocio.Madera;
import modelos.MdlMadera;
import util.Estado;

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
     * @param codOrigen codigo de la madera que dio origen a la actual.
     * @param codProducto codigo del producto (personalizado)
     * @param codTipoMadera codigo del tipo de madera
     * @param descTipoMadera descripción del tipo de madera
     * @param grueso grueso de la madera/producto
     * @param ancho ancho de la madera/producto
     * @param tipoProducto tipo de producto (aserrada/troza/terminada)
     * @param precioXvara precio del producto 
     * @param descripcion detalles que describen el producto
     * @param estado estado activo o inactivo del producto
     * @param cantVaras cantidad del producto en varas
     * 
     */
    public CtrMadera(String codigo, String codProducto, String codOrigen, 
            String descripcion, double precioXvara, double cantVaras,
            String grueso, String ancho, String codTipoMadera, 
            String descTipoMadera, String tipoProducto, String estado) {
        
        madera = new Madera(codigo, codProducto, codOrigen, descripcion,
                precioXvara, cantVaras, grueso, ancho, codTipoMadera, 
            descTipoMadera,tipoProducto, estado);
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
     * @param precio precio por vara del producto
     * @param descripcion detalle del producto (opcional)
     * @return verdadero si inserta el producto.
     */
    public boolean crearProducto(String codProd, String codOrigen, 
            String descripcion, double precio, double cantVaras,
            String grueso, String ancho, String codTipoMadera, 
            String tipoProducto) {
        
        return mdlMadera.crearProducto(codProd, codOrigen, descripcion, precio, 
                cantVaras, grueso, ancho, codTipoMadera, tipoProducto);
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
    public boolean actualizarProducto(String codProd, String codOrigen, 
            String descripcion, double precio, double cantVaras,
            String grueso, String ancho, String codTipoMadera, 
            String tipoProducto, String codigo) {
        
        return mdlMadera.actualizarProducto(codProd, codOrigen, descripcion, 
                precio, cantVaras,grueso,ancho, codTipoMadera, tipoProducto,
                codigo);
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