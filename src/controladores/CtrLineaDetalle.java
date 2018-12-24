/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import java.util.ArrayList;
import logica.negocio.LineaDetalle;
import modelos.MdlLineaDetalle;

/**
 * Constrolador de la clase linea de detalle
 * @author aoihanabi
 */
public class CtrLineaDetalle {
    private static CtrLineaDetalle instancia = null;
    MdlLineaDetalle mdlLineaDetalle;
    LineaDetalle lineaDetalle;
    ArrayList <LineaDetalle> lineasDetalle;

    /**
     * Constructor del controlador de línea de detalle, inicializa variables.
     */
    public CtrLineaDetalle() {
        lineasDetalle = new ArrayList<>();
        mdlLineaDetalle = new MdlLineaDetalle();
    }
    
    /**
     * Obtener instancia única del controlador de línea de detalle.
     * @return Instancia única de línea de detalle
     */
    public static CtrLineaDetalle getInstancia() {
        return  instancia == null ? new CtrLineaDetalle() : instancia;
    }

}
