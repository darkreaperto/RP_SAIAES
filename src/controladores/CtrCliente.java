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
     * @param codigo Código de persona.
     * @param nombre Nombre de persona.
     * @param cedula Cédula de persona.
     * @param tipoCed Tipo de la cédula del cliente.
     * @param limiteCredito Limite credito de persona.
     * @param aprobarCredito Si aprobar credito de persona.
     * @param dir direccion de la persona.
     * @param contacto Lista contactos de persona.
     * @param codCliente Codigo de cliente.
     * @param estado Estado de persona.
     */
    public CtrCliente(String codigo, String nombre, String cedula, 
            String tipoCed, float limiteCredito, boolean aprobarCredito, 
            Direccion dir, ArrayList<Contacto> contacto, String codCliente, 
            String estado) {
        
        cliente = new Cliente(codigo, nombre, cedula, tipoCed, limiteCredito, 
                aprobarCredito, dir, contacto, codCliente, estado);
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
     * Llama el método que obtiene una lista de contactos de la BD
     * @param codPersona codigo de la persona a quien pertenecen los contactos.
     * @return Lista de contactos.
     */
    public ArrayList<Contacto> obtenerContactos(String codPersona) {
        return mdlCliente.obtenerContactos(codPersona);
    }

    /**
     * Llama el método que inserta un nuevo cliente en la BD.
     * @param nombre nombre del cliente
     * @param tipoCed el tipo de cédula del cliente
     * @param cedula cédula de identificación del cliente
     * @param limiteCred cantidad límite de crédito del cliente
     * @param aprobarCred aprobación al crédito del cliente
     * @param dir dirección física del cliente
     * @param contactos contactos (teléfonos y correos) del cliente
     * @return verdadero si el cliente se crea existosamente
     */
    public boolean crearCliente(String nombre, String cedula, String tipoCed,
            double limiteCred, boolean aprobarCred, Direccion dir, 
            ArrayList<ArrayList<Object>> contactos) {

        return mdlCliente.crearCliente(nombre, cedula, tipoCed, limiteCred, 
                aprobarCred, dir, contactos);
    }
    
    /**
     * Llama el método que inserta un nuevo contacto en la BD
     * @param tipo tipo de contacto (correo o teléfono)
     * @param info número telefónico o correo electrónico correspondiente
     * @param codPersona codigo de persona a quien pertenece el contacto
     * @return verdadero si el contacto se crea existosamente
     */
    public boolean crearContacto(TipoContacto tipo, String info, String codPersona) {
        return mdlCliente.crearContacto(tipo, info, codPersona);
    }
    
    /**
     * Llama el método que inactiva un contacto existente
     * @param codigo código del contacto a inactivar
     * @return verdadero si el contacto se inactiva exitosamente
     */
    public boolean inactivarContacto(String codigo) {
        return mdlCliente.inactivarContacto(codigo);
    }

    /**
     * Llama el método que actualiza toda la información del cliente en la BD.
     * @param nombre nombre del cliente
     * @param cedula cédula de identificación del cliente
     * @param tipoCed tipo de cédula del cliente
     * @param limiteCred cantidad límite de crédito del cliente
     * @param aprobarCred aprobación al crédito del cliente
     * @param codPersona codigo de persona a quien se actualiza la información
     * @param dir toda la información de la dirección del cliente
     * @param codCliente códido del cliente a actualizar
     * @return verdadero si la información se actualiza exitosamente
     */
    public boolean actualizarCliente(String nombre, String cedula, 
            String tipoCed, double limiteCred, boolean aprobarCred, 
            Direccion dir, String codPersona, String codCliente) {
        
        return mdlCliente.actualizarCliente(nombre, cedula, tipoCed, 
                limiteCred, aprobarCred, dir, codPersona, codCliente);
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
    
    /**
     * Obtener código de persona.
     * @return el codigo
     */
    public String getCodigo() {
        return cliente.getCodigo();
    }

    /**
     * Establecer código de persona
     * @param codigo el codigo
     */
    public void setCodigo(String codigo) {
        cliente.setCodigo(codigo);
    }

    /**
     * Obtener nombre de persona.
     * @return El nombre.
     */
    public String getNombre() {
        return cliente.getNombre();
    }

    /**
     * Establecer nombre de persona
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        cliente.setNombre(nombre);
    }

    /**
     * Obtener cédula de persona.
     * @return La cedula
     */
    public String getCedula() {
        return cliente.getCedula();
    }

    /**
     * Establecer cedula de persona
     * @param cedula la cedula
     */
    public void setCedula(String cedula) {
        cliente.setCedula(cedula);
    }
    
    /**
     * Otener el tipo de cédula de la persona.
     * @return el tipo de cédula de la persona
     */
    public TipoCedula getTipoCedula() {
        return cliente.getTipoCedula();
    }
    
    /**
     * Estalecer el tipo de cédula de la persona.
     * @param tipoCedula el tipo de cédula a establecer
     */
    public void setTipoCedula(TipoCedula tipoCedula) {
        cliente.setTipoCedula(tipoCedula);
    }

    /**
     * Obtener límite de crédito de persona.
     * @return El limiteCredito
     */
    public double getLimiteCredito() {
        return cliente.getLimiteCredito();
    }

    /**
     * Establecer limite de crédito de persona
     * @param limiteCredito the limiteCredito to set
     */
    public void setLimiteCredito(double limiteCredito) {
        cliente.setLimiteCredito(limiteCredito);
    }

    /**
     * Obtener si el credito de persona se aprueba.
     * @return La aprobación de credito
     */
    public boolean isAprobarCredito() {
        return cliente.isAprobarCredito();
    }

    /**
     * Establecer si se aprueba credito de contactos
     * @param aprobarCredito el aprobarCredito
     */
    public void setAprobarCredito(boolean aprobarCredito) {
        cliente.setAprobarCredito(aprobarCredito);
    }

    /**
     * Obtener lista de contactos de persona.
     * @return La lista de contacto.
     */
    public ArrayList<Contacto> getContactos() {
        return cliente.getContactos();
    }

    /**
     * Establecer lista de contactos
     * @param contactos El contacto
     */
    public void setContactos(ArrayList<Contacto> contactos) {
        cliente.setContactos(contactos);
    }
    
    /**
     * Obtener estado de persona.
     * @return El estado
     */
    public Estado getEstado() {
        return cliente.getEstado();
    }

    /**
     * Establecer estado de persona
     * @param estado el estado
     */
    public void setEstado(Estado estado) {
        cliente.setEstado(estado);
    }
    /**
     * Obtener codigo de cliente.
     * @return el codigo de cliente. 
    */
    public String getCodCliente() {
        return cliente.getCodCliente();
    }

    /**
     * Establecer codigo de cliente.
     * @param codCliente el codigo de cliente
     */
    public void setCodCliente(String codCliente) {
        cliente.setCodCliente(codCliente);
    }
}