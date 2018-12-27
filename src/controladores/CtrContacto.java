/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import java.util.ArrayList;
import logica.negocio.Contacto;
import modelos.MdlContacto;
import util.Estado;
import util.TipoContacto;

/**
 *
 * @author dark-reaper
 */
public class CtrContacto {
    
    private static CtrContacto instancia = null;
    MdlContacto mdlContacto;
    Contacto contacto;
    ArrayList <Contacto> contactos;
    
    /**
     * Constructor del controlador de usuario, inicializa variables.
     */
    public CtrContacto() {
        contactos = new ArrayList<>();
        mdlContacto = new MdlContacto();
    }
    
    /**
     * Constructor de clase contacto, con parámetros.
     * @param codigo Código contacto.
     * @param info Informacion del tipo de contacto.
     * @param codTipo Código tipo de contacto del cliente.
     * @param estado Estado de contacto.
     */
    public CtrContacto(String codigo, String info, String codTipo, String estado) {
        contacto = new Contacto(codigo, info, codTipo, estado);
    }
    
    /**
     * Obtener instancia única del controlador de usuario
     * @return Instancia única de Usuario
     */
    public static CtrContacto getInstancia() {
        return instancia == null ? new CtrContacto() : instancia;
    }
    
    /**
     * Llama el método que llena una lista con todos los usuarios almacenados 
     * en la BD.
     * @return Lista de contactos obtenidos
     */
    public ArrayList<Contacto> obtenerUsuarios() {
        return mdlContacto.obtenerContactos();
    }
    
    /**
     * Llama el método que inserta un contacto en la BD
     * @param info información de contacto correspondiente (tel o correo)
     * @param codPersona codigo de persona a quien pertenece el contacto
     * @param tipo tipo de contacto (tel o correo)
     * @return verdadero si el contacto se inserta exitosamente
     */
    public boolean crearContacto(String info, String codPersona, 
            TipoContacto tipo) {
        return mdlContacto.crearContacto(info, codPersona, tipo);
    }
    
    /**
     * Llama el método que actualiza un contacto en la BD
     * @param info información de contacto correspondiente (tel o correo)
     * @param codPersona codigo de persona a quien pertenece el contacto
     * @param tipo tipo de contacto (tel o correo)
     * @param estado estado activo o inactivo en que está el contacto
     * @param codigo código del contacto
     * @return verdadero si el contacto se actualiza existosamente
     */
    public boolean actualizarContacto(String info, String codPersona, 
            TipoContacto tipo, Estado estado, String codigo) {
        return mdlContacto.actualizarContacto(info, codPersona, tipo, estado, 
                codigo);
    }
    
    /**
     * Llama el método que activa el contacto
     * @param codigo código del contacto
     * @return verdadero si el contacto se activa exitosamente 
     */
    public boolean activarContacto(String codigo) {        
        return mdlContacto.activarContacto(codigo);
    }
    
    /**
     * Llama el método que inactiva el contacto
     * @param codigo código de contacto
     * @return verdadero si el contato se inactiva exitosamente
     */
    public boolean inactivarContacto(String codigo) {        
        return mdlContacto.inactivarContacto(codigo);
    }
    
    /**
     * Buscar contacto enviando por parámetro el criterio de búsqueda.
     * @param param Parametros para consultar contacto en la base de datos
     * @return lista de contactos
     */
    public ArrayList consultarContactos(String param) {
        return mdlContacto.consultarContactos(param);
    }
    
    /**
     * Obtener código.
     * @return codigo de contacto
     */
    public String getCodigo() {
        return contacto.getCodigo();
    }

    /**
     * Establecer código
     * @param codigo el código a establecer
     */
    public void setCodigo(String codigo) {
        contacto.setCodigo(codigo);
    }

    /**
     * Obtener la información.
     * @return La información
     */
    public String getInfo() {
        return contacto.getInfo();
    }

    /**
     * Establecer la información
     * @param info la inforamción
     */
    public void setInfo(String info) {
        contacto.setInfo(info);
    }

    /**
     * Obtener el tipo de contacto
     * @return El tipo
     */
    public TipoContacto getTipo() {
        return contacto.getTipo();
    }

    /**
     * Establecer el tipo de contacto
     * @param tipo El tipo de contacto
     */
    public void setTipo(TipoContacto tipo) {
        contacto.setTipo(tipo);
    }
    
    /**
     * Obtener codigo del tipo.
     * @return el codigo del tipo
     */
    public String getCodTipo() {
        return contacto.getCodTipo();
    }

    /**
     * Establecer código del tipo
     * @param codTipo El código del tipo
     */
    public void setCodTipo(String codTipo) {
        contacto.setCodTipo(codTipo);
    }
    
    /**
     * Obtener el estado.
     * @return El estado
     */
    public Estado getEstado() {
        return contacto.getEstado();
    }

    /**
     * Establecer el estado del contacto.
     * @param estado El estado del contacto.
     */
    public void setEstado(Estado estado) {
        contacto.setEstado(estado);
    }
}
