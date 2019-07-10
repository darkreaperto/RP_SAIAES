/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import java.util.ArrayList;
import logica.negocio.Contacto;
import logica.negocio.Direccion;
import logica.negocio.Proveedor;
import modelos.MdlProveedor;
import util.Estado;
import util.TipoCedula;
import util.TipoContacto;

/**
 * Controlador de la clase Proveedor
 * @author aoihanabi
 */
public class CtrProveedor {
    
    /** Instancia de esta clase para singleton. */
    private static CtrProveedor instancia = null;
    /** Modelo del la clase Proveedor. */
    MdlProveedor mdlProveedor;
    /** Clase proveedor. */
    Proveedor proveedor;
    /** Lista de proveedores. */
    ArrayList <Proveedor> proveedores;
    
    /**
     * Constructor del controlador de usuario, inicializa variables.
     */
    public CtrProveedor() {
        proveedores = new ArrayList<>();
        mdlProveedor = new MdlProveedor();
    }
    
    /**
     * Constructor del controlador de proveedor, inicializa variables.
     * @param cedula Número de cédula de persona.
     * @param tipoCed El tipo de la cédula de la persona (Física o Júrica).
     * @param nombre Nombre completo de la persona.
     * @param dir Dirección física de la persona.
     * @param estado Estado activo o deshabilitado del proveedor.
     * @param contactos Lista contactos de persona.
     */
    public CtrProveedor(String cedula, String tipoCed, String nombre, 
            Direccion dir,  String estado, ArrayList<Contacto> contactos) {
        
        proveedor = new Proveedor(cedula, tipoCed, nombre, dir, 
                estado, contactos);
    }
    
    /**
     * Obtener instancia única del controlador de proveedor.
     * @return Instancia única de proveedor.
     */
    public static CtrProveedor getInstancia() {
        return instancia == null ? new CtrProveedor() : instancia;
    }
    
    /**
     * Llena una lista con todos los proveedores almacenados en la BD.
     * @return Lista de proveedores.
     */
    public ArrayList<Proveedor> obtenerProveedores() {
        return mdlProveedor.obtenerProveedores();
    }
    
    /**
     * Llama el método que inserta un nuevo proveedor en la BD.
     * @param cedula Número de cedula del proveedor
     * @param tipoCed El tipo de la cédula del proveedor
     * @param nombre Nombre del proveedor
     * @param dir Información de dirección del proveedor
     * @param contactos Lista de contactos del proveedor
     * @return Verdadera si la inserción fue éxitosa
     */
    public boolean crearProveedor(String cedula, String tipoCed, String nombre,  
            Direccion dir, ArrayList<ArrayList<Object>> contactos) {
        return mdlProveedor.crearProveedor(cedula, tipoCed, nombre, dir, contactos);
    }
    
    /**
     * Actualiza toda la información del proveedor en la BD.
     * @param nombre El nombre completo del proveedor
     * @param dir Objeto dirección que contiene los datos de la direccion del proveedor
     * @param cedPersona Cédula unívoca y llave primaria para identificar el proveedor
     * @return Verdadero si la actualización fue exitosa.
     */
    public boolean actualizarProveedor(String nombre, Direccion dir, 
            String cedPersona) {
        return mdlProveedor.actualizarProveedor(nombre, dir, cedPersona);
    }
    
    /**
     * Inactiva el proveedor en la bd.
     * @param cedPersona Cédula unívoca y llave primaria para identificar el proveedor
     * @return Verdadero si inactiva el proveedor
     */
    public boolean inactivarProveedor(String cedPersona) {
        return mdlProveedor.inactivarProveedor(cedPersona);
    }
    
    /**
     * Activa el proveedor en la bd.
     * @param cedPersona Cédula unívoca y llave primaria para identificar el proveedor
     * @return Verdadero si la activación fue exitosa.
     */
    public boolean activarProveedor(String cedPersona) {
        return mdlProveedor.activarProveedor(cedPersona);
    }
    
    /**
     * Buscar proveedor enviando por parámetro el criterio de búsqueda.
     * @param param Parametros para consultar proveedor en la base de datos
     * @return lista de proveedores
     */
    public ArrayList consultarProveedor(String param) {
        return mdlProveedor.consultarProveedor(param);
    }
    
    /**
     * Crea el contacto/s para el proveedor en la bd
     * @param tipo tipo de contacto
     * @param info detalle del contacto
     * @param cedulaPersona codigo de la persona con el contacto
     * @return true si crea el contacto exitosamente
     */
    public boolean crearContactoProveedor(TipoContacto tipo, String info, 
            String cedulaPersona) {
        return mdlProveedor.crearContactoProveedor(tipo, info, cedulaPersona);
    }
    
    /**
     * Llama el método que inactiva un contacto en la bd
     * @param codigo Codigo de BD de contacto
     * @return Verdadero si se inactiva el contacto exitosamente
     */
    public boolean inactivarContacto(String codigo) {
        return mdlProveedor.inactivarContacto(codigo);
    }
    
    /**
     * Obtener nombre completo de persona.
     * @return El nombre completo.
     */
    public String getNombre() {
        return proveedor.getNombre();
    }

    /**
     * Establecer nombre completo de persona
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        proveedor.setNombre(nombre);
    }

    /**
     * Obtener número de cédula de persona.
     * @return El número de cédula
     */
    public String getCedula() {
        return proveedor.getCedula();
    }

    /**
     * Establecer el número de cédula de persona
     * @param cedula El número de cédula
     */
    public void setCedula(String cedula) {
        proveedor.setCedula(cedula);
    }
    
    /**
     * Obtener el tipo de cédula del la persona.
     * @return El tipo de cédula
     */
    public TipoCedula getTipoCedula() {
        return proveedor.getTipoCedula();
    }
    
    /**
     * Establecer el tipo de cédula de la persona.
     * @param tipoCed El tipo de cédula a establecer
     */
    public void setTipoCedula(TipoCedula tipoCed) {
        proveedor.setTipoCedula(tipoCed);
    }

    /**
     * Obtener la lista de contactos de persona.
     * @return La lista de contacto.
     */
    public ArrayList<Contacto> getContactos() {
        return proveedor.getContactos();
    }

    /**
     * Establecer la lista de contactos
     * @param contactos La lista de contactos
     */
    public void setContactos(ArrayList<Contacto> contactos) {
        proveedor.setContactos(contactos);
    }
    
    /**
     * Obtener estado de persona (activo o deshabilitado).
     * @return El estado (activo o deshabilitado)
     */
    public Estado getEstado() {
        return proveedor.getEstado();
    }

    /**
     * Establecer estado de persona (activo o deshabilitado)
     * @param estado El estado (activo o deshabilitado)
     */
    public void setEstado(Estado estado) {
        proveedor.setEstado(estado);
    }
}
