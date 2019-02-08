/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import logica.negocio.Emisor;
import modelos.MdlEmisor;

/**
 *
 * @author darkreaper
 */
public class CtrEmisor {
    
    private static CtrEmisor instancia = null;
    MdlEmisor mdlEmisor;
    
    /**
     * Constructor del controlador de Emisor.
     */
    public CtrEmisor() {
        mdlEmisor = new MdlEmisor();
    }
    
    /**
     * Obtener instancia única del controlador de Emisor.
     * @return Instancia única de línea de detalle
     */
    public static CtrEmisor getInstancia() {
        return  instancia == null ? new CtrEmisor() : instancia;
    }
    
    /**
     * Obtener la información de la BD del emisor.
     * @return el emisor
     */
    public Emisor obtenerEmisor() {
        return mdlEmisor.obtenerEmisor();
    }
    
}
