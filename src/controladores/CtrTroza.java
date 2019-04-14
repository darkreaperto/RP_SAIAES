/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import java.util.ArrayList;
import logica.negocio.Troza;
import modelos.MdlTroza;

/**
 *
 * @author fuent
 */
public class CtrTroza {
    private static CtrTroza instancia = null;
    MdlTroza mdlTroza;
    Troza trocita;
    ArrayList <Troza> trozas;
    
    /**
     * Constructor del controlador de trocita, inicializa variables.
     */
    public CtrTroza() {
        trozas = new ArrayList<>();
        mdlTroza = new MdlTroza();
    }
    
    /**
     * Constructor de controlador de trocita, crea un objeto trocita con 
     * sus parámetros.
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
    public CtrTroza(String codigo, String codInterno, String codTipoMadera, 
            String descTipoMadera, double pulgadas, String tipoProducto, 
            String descripcion, String codProveedor, String nomProveedor, 
            String estado) {
        
        trocita = new Troza(codigo, codInterno, codTipoMadera, descTipoMadera,
                pulgadas, tipoProducto, descripcion, codProveedor, nomProveedor, 
                estado);
    }
    /**
     * Obtener instancia única del controlador de trocita.
     * @return Instancia única de Troza
     */
    public static CtrTroza getInstancia() {
        return instancia == null ? new CtrTroza() : instancia;
    }
    
    /**
     * Llena una lista con todos los tipos de trozas almacenados en la BD.
     * @return lista de tipos de trozas.
     */
    public ArrayList<Troza> obtenerTrozas() {
        return mdlTroza.obtenerTrozas();
    }
    /**
     * Verifica si la lista de trozas está vacía para llamar el método que 
     * la llena desde la bd, en caso de no estar vacía ya se tienen los trozas 
     * así que se retorna la lista existente.
     * @return lista de trozas existentes
     */
    public ArrayList<Troza> getListaTrozas() {
        if(trozas == null || trozas.isEmpty()) {
            trozas = obtenerTrozas();
            System.out.println("CtrMadera says: I'M EMPTY");
            return obtenerTrozas();
        } else {
            System.out.println("CtrMadera says: I was empty");
            return trozas;
        }
    }
    /**
     * Buscar producto enviando por parámetro el criterio de búsqueda.
     * @param param Parametros para consultar producto en la base de datos
     * @return lista de trozas
     */
    public ArrayList consultarTrozas(String param) {
        return mdlTroza.consultarTrozas(param);
    }
    /**
     * Inserta una nueva troza en la BD
     * @param codInterno codigo de la troza para uso interno del sistema
     * @param codTipoMadera codigo del tipo de madera (variedad)
     * @param pulgadas cantidad de troza en pulgadas
     * @param tipoProducto TROZA duh
     * @param descripcion descripción de la troza
     * @param codProveedor codigo del proveedor de la troza
     * @return verdadero si fue insertada correctamente
     */
    public boolean crearTroza(String codInterno, String codTipoMadera, 
            double pulgadas, String tipoProducto, String descripcion, 
            String codProveedor) {
        
        return mdlTroza.crearTroza(codInterno, codTipoMadera,
                pulgadas, tipoProducto, descripcion, codProveedor);
    }
    
    /**
     * Actualiza los diferentes atributos de la trocita y los almacena en la BD
     * @param codInterno codigo de la troza para uso interno del sistema
     * @param codTipoMadera codigo del tipo de madera (variedad)
     * @param pulgadas cantidad de troza en pulgadas
     * @param descripcion descripción de la troza
     * @param codProveedor codigo del proveedor de la troza
     * @param codigo codigo de la bd
     * @return verdadero actualizada fue correctamente
     */
    public boolean actualizarTroza(String codInterno, String codTipoMadera, 
            double pulgadas,String descripcion, String codProveedor,
            String codigo) {
        
        return mdlTroza.actualizarTroza(codInterno, codTipoMadera, 
                pulgadas, descripcion, codProveedor, codigo);
    }
    /**
     * Llama el método que inactiva una troza en la bd
     * @param codigo codigo de la troza
     * @return Verdadero si inactiva el producto
     */
    public boolean inactivarTroza(String codigo) {
        
        return mdlTroza.inactivarTroza(codigo);
    }
    /**
     * Llama el método que activa una troza en la bd
     * @param codigo codigo de la troza
     * @return verdadero si activa la troza exitosamente
     */
    public boolean activarTroza(String codigo) {
        
        return mdlTroza.activarTroza(codigo);
    }
    /**
     * Llama el método que suma cantidad de un producto ya existente 
     * en el inventario
     * @param tipoProd tipo de madera (aserrada, terminada)
     * @param pulgadas cantidad de pulgadas a sumar
     * @param codigo codigo del registro a sumar
     * @return Verdadero si suma correctamente
     */
    public boolean sumarRegMadera(String tipoProd, double pulgadas, 
            String codigo) {        
        return mdlTroza.sumarRegMadera(tipoProd, pulgadas, codigo);
    }
    /**
     * Llama el método que resta cantidad de un producto ya existente 
     * en el inventario
     * @param tipoProd tipo de madera (aserrada, terminada)
     * @param pulgadas cantidad de pulgadas a sumar
     * @param codigo codigo del registro a sumar
     * @return Verdadero si suma correctamente
     */
    public boolean restarRegMadera(String tipoProd, double pulgadas, 
            String codigo) {        
        return mdlTroza.restarRegMadera(tipoProd, pulgadas, codigo);
    }
}
