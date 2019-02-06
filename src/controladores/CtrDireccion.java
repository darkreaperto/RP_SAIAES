/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import java.util.ArrayList;
import logica.negocio.Direccion;
import modelos.MdlDireccion;

/**
 * 
 * @author aoihanabi
 */
public class CtrDireccion {
    private static CtrDireccion instancia = null;
    Direccion direccion;
    MdlDireccion mdlDireccion;
//    ArrayList <Direccion> direcciones;
    /**
     * Constructor del controlador de usuario, inicializa variables.
     */
    public CtrDireccion() {
//        direcciones = new ArrayList<>();
        mdlDireccion = new MdlDireccion();
    }
    /**
     * Constructor del controlador de dirección, con atributos
     * @param codigo Código direccion.
     * @param codProvincia codigo de provincia para la BD.
     * @param nomProvincia nombre de provincia para la BD.
     * @param codCanton codigo de cantón para la BD.
     * @param nomCanton nombre de cantón para la BD.
     * @param codDistrito codigo de distrito para la BD.
     * @param nomDistrito nombre de distrito para la BD.
     * @param codBarrio codigo de barrio para la BD.
     * @param nomBarrio nombre de barrio para la BD.
     * @param otrasSenas
     */
    public CtrDireccion(int codigo, String codProvincia, String nomProvincia, 
            String codCanton, String nomCanton, String codDistrito, 
            String nomDistrito, String codBarrio, String nomBarrio, 
            String otrasSenas) {
        
        direccion = new Direccion(codigo, codProvincia, nomProvincia, codCanton,
        nomCanton, codDistrito, nomDistrito, codBarrio, nomBarrio, otrasSenas);
    }
    /**
     * Obtener instancia única del controlador de dirección.
     * @return Instancia única de dirección
     */
    public static CtrDireccion getInstancia() {
        return instancia == null ? new CtrDireccion() : instancia;
    }
    
    /**
     * Buscar contacto enviando por parámetro el criterio de búsqueda.
     * @param param Parametros para consultar contacto en la base de datos
     * @return lista de contactos
     */
    public Direccion consultarDireccion(String param) {
        return mdlDireccion.consultarDireccion(param);
    }
}
