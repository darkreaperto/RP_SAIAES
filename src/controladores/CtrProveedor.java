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
     * @param dir Dirección de la persona.
     * @param contacto Lista contactos de persona.
     * @param codCliente Codigo de proveedor.
     * @param estado Estado de persona.
     */
    public CtrProveedor(String codigo, String nombre, String apellido1, 
            String apellido2, String cedula, float limiteCredito,
            boolean aprobarCredito, Direccion dir, ArrayList<Contacto> contacto, 
            String codCliente, String estado) {
        
        proveedor = new Proveedor(codigo, nombre,apellido1, apellido2, cedula, 
                dir, contacto, codCliente, estado);
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
    /**
     * Llama el método que obtiene la lista de contactos de la persona indicada
     * @param codPersona codigo de persona
     * @return verdadero si se obtiene la lista de contactos exitosamente.
     */
    public ArrayList<Contacto> obtenerContactos(String codPersona) {
        return mdlProveedor.obtenerContactos(codPersona);
    }

    /**
     * Llama el método que inserta un nuevo proveedor en la BD.
     * @param nombre nombre del proveedor
     * @param apellido1 primer apellido del proveedor
     * @param apellido2 segundo apellido del proveedor
     * @param cedula cedula del proveedor
     * @param dir información de dirección del proveedor
     * @param contactos lista de contactos del proveedor
     * @return 
     */
    public boolean crearProveedor(String nombre, String apellido1, 
            String apellido2, String cedula, Direccion dir, 
            ArrayList<ArrayList<Object>> contactos) {

        return mdlProveedor.crearProveedor(nombre, apellido1, apellido2, 
                cedula, dir, contactos);
    }
    
    /**
     * Crea el contacto/s para el proveedor en la bd
     * @param tipo tipo de contacto
     * @param info detalle del contacto
     * @param codPersona codigo de persona con el contacto
     * @return true si crea el contacto exitosamente
     */
    public boolean crearContacto(TipoContacto tipo, String info, String codPersona) {
        return mdlProveedor.crearContacto(tipo, info, codPersona);
    }
    
    /**
     * Llama el método que inactiva un contacto en la bd
     * @param codigo codigo de contacto
     * @return verdadero si inactiva el contacto eixtosamente
     */
    public boolean inactivarContacto(String codigo) {
        return mdlProveedor.inactivarContacto(codigo);
    }

    /**
     * Actualiza toda la información del proveedor en la BD.
     * @param nombre nombre del proveedor
     * @param apellido1 primer apellido del proveedor
     * @param apellido2 segundo apellido del proveedor
     * @param cedula cédula del proveedor
     * @param codPersona codigo de persona
     * @return verdadero si actualiza el proveedor exitosamente
     */
    public boolean actualizarProveedor(String nombre, String apellido1, 
            String apellido2, String cedula, String codPersona) {
        
        return mdlProveedor.actualizarProveedor(nombre, apellido1, apellido2, 
                cedula, codPersona);
    }
    
    /**
     * Inactiva el proveedor en la bd.
     * @param cedula cédula unívoca del proveedor
     * @return True si lo inactivó correctamente
     */
    public boolean inactivarProveedor(String cedula) {
        
        return mdlProveedor.inactivarProveedor(cedula);
    }
    
    /**
     * Activa el proveedor en la bd.
     * @param cedula cédula unívoca del proveedor
     * @return True si lo activó correctamente
     */
    public boolean activarProveedor(String cedula) {
        
        return mdlProveedor.activarProveedor(cedula);
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
