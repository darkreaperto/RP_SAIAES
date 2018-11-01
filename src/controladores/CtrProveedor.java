/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import java.util.ArrayList;
import logica.negocio.Contacto;
import logica.negocio.Proveedor;
import modelos.MdlProveedor;
import util.Estado;
import util.TipoContacto;

/**
 *
 * @author aoihanabi
 */
public class CtrProveedor {
    private static CtrProveedor instancia = null;
    MdlProveedor mdlProveedor;
    Proveedor proveedor;
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
     * @param codigo Código de persona.
     * @param nombre Nombre de persona.
     * @param apellido1 Apellido 1 de persona.
     * @param apellido2 Apellido 2 de persona.
     * @param cedula Cédula de persona.
     * @param limiteCredito Limite credito de persona.
     * @param aprobarCredito Si aprobar credito de persona.
     * @param contacto Lista contactos de persona.
     * @param codCliente Codigo de proveedor.
     * @param estado Estado de persona.
     */
    public CtrProveedor(String codigo, String nombre, String apellido1, 
            String apellido2, String cedula, float limiteCredito,
            boolean aprobarCredito, ArrayList<Contacto> contacto, 
            String codCliente, String estado) {
        proveedor = new Proveedor(codigo, nombre,apellido1, apellido2, cedula, 
                contacto, codCliente, estado);
    }
    
    /**
     * Obtener instancia única del controlador de proveedor.
     * @return Instancia única de Cliente
     */
    public static CtrProveedor getInstancia() {
        return instancia == null ? new CtrProveedor() : instancia;
    }
    
    /**
     * Llena una lista con todos los proveedores almacenados en la BD.
     *
     * @return lista de proveedores.
     */
    public ArrayList<Proveedor> obtenerProveedores() {
        return mdlProveedor.obtenerProveedores();
    }
    
    public ArrayList<Contacto> obtenerContactos(String codPersona) {
        return mdlProveedor.obtenerContactos(codPersona);
    }

    /**
     * Inserta un nuevo proveedor en la BD.
     * @param nombre 
     * @param apellido1
     * @param apellido2
     * @param cedula
     * @param limiteCred
     * @param aprobarCred
     * @param contactos
     * @return 
     */
    /*public boolean crearCliente(String nombre, String apellido1, 
            String apellido2, String cedula, double limiteCred, 
            boolean aprobarCred, ArrayList<ArrayList<Object>> contactos) {

        return mdlCliente.crearCliente(nombre, apellido1, apellido2, cedula, 
                limiteCred, aprobarCred, contactos);
    }*/
    
    /**
     * 
     * @param tipo
     * @param info
     * @param codPersona
     * @return 
     */
    public boolean crearContacto(TipoContacto tipo, String info, String codPersona) {
        return mdlProveedor.crearContacto(tipo, info, codPersona);
    }
    
    public boolean inactivarContacto(String codigo) {
        return mdlProveedor.inactivarContacto(codigo);
    }

    /**
     * Actualiza toda la información del proveedor en la BD.
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
    /*public boolean actualizarCliente(String nombre, String apellido1, 
            String apellido2, String cedula, double limiteCred, 
            boolean aprobarCred, String codPersona) {
        
        return mdlCliente.actualizarCliente(nombre, apellido1, apellido2, 
                cedula, limiteCred, aprobarCred, codPersona);
    }*/
    
    /**
     * Inactiva el proveedor en la bd.
     * @param cedula cédula unívoca del proveedor
     * @return 
     */
    /*public boolean inactivarCliente(String cedula) {
        
        return mdlCliente.inactivarCliente(cedula);
    }*/
    
    /**
     * Activa el proveedor en la bd.
     * @param cedula cédula unívoca del proveedor
     * @return 
     */
    /*public boolean activarCliente(String cedula) {
        
        return mdlCliente.activarCliente(cedula);
    }*/

    /**
     * Buscar proveedor enviando por parámetro el criterio de búsqueda.
     * @param param Parametros para consultar proveedor en la base de datos
     * @return lista de proveedores
     */
    /*public ArrayList consultarClientes(String param) {
        return mdlCliente.consultarClientes(param);
    }*/
    
    /**
     * Obtener código de persona.
     * @return el codigo
     */
    public String getCodigo() {
        return proveedor.getCodigo();
    }

    /**
     * Establecer código de persona
     * @param codigo el codigo
     */
    public void setCodigo(String codigo) {
        proveedor.setCodigo(codigo);
    }

    /**
     * Obtener nombre de persona.
     * @return El nombre.
     */
    public String getNombre() {
        return proveedor.getNombre();
    }

    /**
     * Establecer nombre de persona
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        proveedor.setNombre(nombre);
    }

    /**
     * Obtener apellido 1 de persona.
     * @return El apellido1
     */
    public String getApellido1() {
        return proveedor.getApellido1();
    }

    /**
     * Establecer apellido 1 de persona
     * @param apellido1 el apellido1
     */
    public void setApellido1(String apellido1) {
        proveedor.setApellido1(apellido1);
    }

    /**
     * Obtener apellido 2 de persona.
     * @return El apellido2
     */
    public String getApellido2() {
        return proveedor.getApellido2();
    }

    /**
     * Establecer apellido 2 de persona
     * @param apellido2 the apellido2 to set
     */
    public void setApellido2(String apellido2) {
        proveedor.setApellido2(apellido2);
    }

    /**
     * Obtener cédula de persona.
     * @return La cedula
     */
    public String getCedula() {
        return proveedor.getCedula();
    }

    /**
     * Establecer cedula de persona
     * @param cedula la cedula
     */
    public void setCedula(String cedula) {
        proveedor.setCedula(cedula);
    }

    /**
     * Obtener límite de crédito de persona.
     * @return El limiteCredito
     */
    public float getLimiteCredito() {
        return proveedor.getLimiteCredito();
    }

    /**
     * Establecer limite de crédito de persona
     * @param limiteCredito the limiteCredito to set
     */
    public void setLimiteCredito(float limiteCredito) {
        proveedor.setLimiteCredito(limiteCredito);
    }

    /**
     * Obtener si el credito de persona se aprueba.
     * @return La aprobación de credito
     */
    public boolean isAprobarCredito() {
        return proveedor.isAprobarCredito();
    }

    /**
     * Establecer si se aprueba credito de contactos
     * @param aprobarCredito el aprobarCredito
     */
    public void setAprobarCredito(boolean aprobarCredito) {
        proveedor.setAprobarCredito(aprobarCredito);
    }

    /**
     * Obtener lista de contactos de persona.
     * @return La lista de contacto.
     */
    public ArrayList<Contacto> getContactos() {
        return proveedor.getContactos();
    }

    /**
     * Establecer lista de contactos
     * @param contactos El contacto
     */
    public void setContactos(ArrayList<Contacto> contactos) {
        proveedor.setContactos(contactos);
    }
    
    /**
     * Obtener estado de persona.
     * @return El estado
     */
    public Estado getEstado() {
        return proveedor.getEstado();
    }

    /**
     * Establecer estado de persona
     * @param estado el estado
     */
    public void setEstado(Estado estado) {
        proveedor.setEstado(estado);
    }
    /**
     * Obtener codigo de proveedor.
     * @return el codigo de proveedor. 
    */
    public String getCodProveedor() {
        return proveedor.getCodProveedor();
    }

    /**
     * Establecer codigo de proveedor.
     * @param codProveedor el codigo de proveedor
     */
    public void setCodProveedor(String codProveedor) {
        proveedor.setCodProveedor(codProveedor);
    }

}
