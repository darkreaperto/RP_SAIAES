/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import java.util.ArrayList;
import logica.negocio.Impuesto;
import modelos.MdlImpuesto;

/**
 * Controlador de la clase impuesto
 * @author aoihanabi
 */
public class CtrImpuesto {
    private static CtrImpuesto instancia = null;
    MdlImpuesto mdlImpuesto;
    Impuesto impuesto;
    ArrayList <Impuesto> impuestos;

    /**
     * Constructor del controlador de impuesto, inicializa variables.
     */
    public CtrImpuesto() {
        impuestos = new ArrayList<>();
        mdlImpuesto = new MdlImpuesto();
    }
    
    /**
     * Obtener instancia única del controlador de impuesto
     * @return Instancia única del controlador de impuesto
     */
    public static CtrImpuesto getInstancia() {
        return instancia == null ? new CtrImpuesto() : instancia;
    }
    
}
