/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import java.util.ArrayList;
import logica.negocio.Cliente;
import logica.negocio.Contacto;
import modelos.MdlCliente;
import util.Estado;
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
     * @param apellido1 Apellido 1 de persona.
     * @param apellido2 Apellido 2 de persona.
     * @param cedula Cédula de persona.
     * @param limiteCredito Limite credito de persona.
     * @param aprobarCredito Si aprobar credito de persona.
     * @param contacto Lista contactos de persona.
     * @param codCliente Codigo de cliente.
     * @param estado Estado de persona.
     */
    public CtrCliente(String codigo, String nombre, String apellido1, 
            String apellido2, String cedula, float limiteCredito,
            boolean aprobarCredito, ArrayList<Contacto> contacto, 
            String codCliente, String estado) {
        cliente = new Cliente(codigo, nombre,apellido1, apellido2, cedula, 
                limiteCredito, aprobarCredito, contacto, codCliente, estado);
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
     *
     * @return lista de clientes.
     */
    public ArrayList<Cliente> obtenerClientes() {
        return mdlCliente.obtenerClientes();
    }
    
    public ArrayList<Contacto> obtenerContactos(String codPersona) {
        return mdlCliente.obtenerContactos(codPersona);
    }

    /**
     * Inserta un nuevo cliente en la BD.
     * @param nombre 
     * @param apellido1
     * @param apellido2
     * @param cedula
     * @param limiteCred
     * @param aprobarCred
     * @param contactos
     * @return 
     */
    public boolean crearCliente(String nombre, String apellido1, 
            String apellido2, String cedula, double limiteCred, 
            boolean aprobarCred, ArrayList<ArrayList<Object>> contactos) {

        return mdlCliente.crearCliente(nombre, apellido1, apellido2, cedula, 
                limiteCred, aprobarCred, contactos);
    }
    
    public boolean crearContacto(TipoContacto tipo, String info, String codPersona) {
        return mdlCliente.crearContacto(tipo, info, codPersona);
    }
    
    public boolean inactivarContacto(String codigo) {
        return mdlCliente.inactivarContacto(codigo);
    }

    /**
     * Actualiza toda la información del cliente en la BD.
     * @param nombre
     * @param apellido1
     * @param apellido2
     * @param cedula
     * @param limiteCred
     * @param aprobarCred
     * @param contactos
     * @param estado
     * @param codPersona
     * @param codigo
     * @return 
     */
    public boolean actualizarCliente(String nombre, String apellido1, 
            String apellido2, String cedula, double limiteCred, 
            boolean aprobarCred, String codPersona) {
        
        return mdlCliente.actualizarCliente(nombre, apellido1, apellido2, 
                cedula, limiteCred, aprobarCred, codPersona);
    }
    
    /**
     * Inactiva el cliente en la bd.
     * @param cedula cédula unívoca del cliente
     * @return 
     */
    public boolean inactivarCliente(String cedula) {
        
        return mdlCliente.inactivarCliente(cedula);
    }
    
    /**
     * Activa el cliente en la bd.
     * @param cedula cédula unívoca del cliente
     * @return 
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
     * Obtener apellido 1 de persona.
     * @return El apellido1
     */
    public String getApellido1() {
        return cliente.getApellido1();
    }

    /**
     * Establecer apellido 1 de persona
     * @param apellido1 el apellido1
     */
    public void setApellido1(String apellido1) {
        cliente.setApellido1(apellido1);
    }

    /**
     * Obtener apellido 2 de persona.
     * @return El apellido2
     */
    public String getApellido2() {
        return cliente.getApellido2();
    }

    /**
     * Establecer apellido 2 de persona
     * @param apellido2 the apellido2 to set
     */
    public void setApellido2(String apellido2) {
        cliente.setApellido2(apellido2);
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
     * Obtener límite de crédito de persona.
     * @return El limiteCredito
     */
    public float getLimiteCredito() {
        return cliente.getLimiteCredito();
    }

    /**
     * Establecer limite de crédito de persona
     * @param limiteCredito the limiteCredito to set
     */
    public void setLimiteCredito(float limiteCredito) {
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