/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import java.util.ArrayList;
import logica.negocio.TipoMadera;
import modelos.MdlTipoMadera;

/**
 * Controlador de la clase Tipo de madera.
 * @author aoihanabi
 */
public class CtrTipoMadera {
    
    MdlTipoMadera mdlTipoMadera;
    TipoMadera tipoMadera;
    ArrayList <TipoMadera> tipos;
    
    /**
     * Constructor del controlador de usuario, inicializa variables.
     */
    public CtrTipoMadera() {
        tipos = new ArrayList<>();
        mdlTipoMadera = new MdlTipoMadera();
    }
    /**
    * Constructor del controlador de tipo de madera, crea un objeto de la 
    * clase tipo de madera
    * @param codigo codigo
    * @param descripcion descripcion o nombre
    * @param estado estado
    */
    public CtrTipoMadera(String codigo, String descripcion, String estado) {
        tipoMadera = new TipoMadera(codigo, descripcion, estado);
    }
    
    /**
     * Llena una lista con todos los tipos de madera almacenados en la BD.
     *
     * @return lista de tipos de madera.
     */
    public ArrayList<TipoMadera> obtenerTiposMadera() {
        return mdlTipoMadera.obtenerTiposMadera();
    }
}
