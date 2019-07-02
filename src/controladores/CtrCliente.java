/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import java.util.ArrayList;
import logica.negocio.Cliente;
import logica.negocio.Contacto;
import logica.negocio.Direccion;
import modelos.MdlCliente;
import util.Estado;
import util.TipoCedula;
import util.TipoContacto;

/**
 * Controlador de la clase Cliente.
 * @author ahoihanabi
 */
public class CtrCliente {
    
    private static CtrCliente instancia = null;
    MdlCliente mdlCliente;
    Cliente cliente;
    ArrayList <Cliente> clientes;
    
    /**
     * Constructor del controlador de usuario, inicializa variables.
     */
    public CtrCliente() {
        clientes = new ArrayList<>();
        mdlCliente = new MdlCliente();
    }
    
    /**
     * Constructor del controlador de cliente, inicializa variables.
     * @param nombre Nombre de persona.
     * @param cedula Cédula de persona.
     * @param tipoCed Tipo de la cédula del cliente.
     * @param limiteCredito Limite credito de persona.
     * @param aprobarCredito Si aprobar credito de persona.
     * @param dir direccion de la persona.
     * @param contactos Lista contactos de persona.
     * @param estado Estado de persona.
     */
    public CtrCliente(String cedula, String tipoCed, String nombre, 
            Direccion dir, float limiteCredito, boolean aprobarCredito, 
            String estado, ArrayList<Contacto> contactos) {
        
        cliente = new Cliente(cedula, tipoCed, nombre, dir, limiteCredito, 
                aprobarCredito, estado, contactos);
    }
    
    /**
     * Obtener instancia única del controlador de cliente.
     * @return Instancia única de Cliente
     */
    public static CtrCliente getInstancia() {
        return instancia == null ? new CtrCliente() : instancia;
    }
    
    /**
     * Llena una lista con todos los clientes almacenados en la BD.
     * @return lista de clientes.
     */
    public ArrayList<Cliente> obtenerClientes() {
        return mdlCliente.obtenerClientes();
    }
    
    /**
     * Llama el método que inserta un nuevo cliente en la BD.
     * @param tipoCed el tipo de cédula del cliente
     * @param cedula cédula de identificación del cliente
     * @param nombre nombre del cliente
     * @param dir dirección física del cliente
     * @param limiteCred cantidad límite de crédito del cliente
     * @param aprobarCred aprobación al crédito del cliente
     * @param contactos contactos (teléfonos y correos) del cliente
     * @return verdadero si el cliente se crea existosamente
     */
    public boolean crearCliente(String cedula, String tipoCed, String nombre, 
            Direccion dir, double limiteCred, boolean aprobarCred, 
            ArrayList<ArrayList<Object>> contactos) {

        return mdlCliente.crearCliente(cedula, tipoCed, nombre, dir, 
                limiteCred, aprobarCred, contactos);
    }

    /**
     * Llama el método que actualiza toda la información del cliente en la BD.
     * @param cedula cédula de identificación del cliente
     * @param nombre nombre del cliente
     * @param dir toda la información de la dirección del cliente
     * @param limiteCred cantidad límite de crédito del cliente
     * @param aprobarCred aprobación al crédito del cliente
     * @return verdadero si la información se actualiza exitosamente
     */
    public boolean actualizarCliente(String cedula, String nombre, 
            Direccion dir, double limiteCred, boolean aprobarCred) {
        
        return mdlCliente.actualizarCliente(cedula, nombre, dir, limiteCred, 
                aprobarCred);
    }
    
    /**
     * Llama el método que inactiva el cliente en la bd.
     * @param cedula cédula unívoca del cliente
     * @return verdadero si la inactivación del cliente es exitosa
     */
    public boolean inactivarCliente(String cedula) {
        
        return mdlCliente.inactivarCliente(cedula);
    }
    
    /**
     * Llama el método que activa el cliente en la bd.
     * @param cedula cédula unívoca del cliente
     * @return verdadero si la activación del cliente es exitosa
     */
    public boolean activarCliente(String cedula) {
        
        return mdlCliente.activarCliente(cedula);
    }

    /**
     * Buscar cliente enviando por parámetro el criterio de búsqueda.
     * @param param Parametros para consultar cliente en la base de datos
     * @return lista de clientes
     */
    public ArrayList consultarClientes(String param) {
        return mdlCliente.consultarClientes(param);
    }
    
    public boolean crearContacto(String info, String cedPersona, TipoContacto tipo) {
        return mdlCliente.crearContacto(info, cedPersona, tipo);
    }
    
    public boolean actualizarContacto(String info, String cedPersona, 
            TipoContacto tipo, Estado estado, String codigo) {
        return mdlCliente.actualizarContacto(info, cedPersona, tipo, estado, codigo);
    }
    
    public ArrayList<Contacto> consultarContactos(String param) {
        return mdlCliente.consultarContactos(param);
    }
    
    public boolean inactivarContacto(String codigo) {
        return mdlCliente.inactivarCliente(codigo);
    }
}