/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import java.util.ArrayList;
import logica.negocio.Exoneracion;
import modelos.MdlExoneracion;

/**
 * Controlador de la clase exoneración
 * @author aoihanabi
 */
public class CtrExoneracion {
    private static CtrExoneracion instancia = null;
    MdlExoneracion mdlExoneracion;
    Exoneracion exoneracion; 
    ArrayList <Exoneracion> exoneraciones;

    /**
     * Constructor del controlador de exoneración, inicializa variables.
     */
    public CtrExoneracion() {
        exoneraciones = new ArrayList<>();
        mdlExoneracion = new MdlExoneracion();
    }
    
    /**
     * Obtener instancia única del controlador de exoneración
     * @return Instancia única de exoneración
     */
    public static CtrExoneracion getInstancia() {
        return instancia == null ? new CtrExoneracion() : instancia;
    }
    
    
}
