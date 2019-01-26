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
    
    /**
     * Llama el método que inserta un registro 'impuesto' en la bd
     * @param codigoImpuesto codigo del tipo de impuesto indicado por hacienda
     * @param tarifaImpuesto porcentaje de impuesto aplicado al producto
     * @param montoImpuesto valor extra(impuesto) que se sumará al precio del 
     * producto
     * @return verdadero si el impuesto se crea exitosamente
     */
    public boolean crearImpuesto(String codigoImpuesto, double tarifaImpuesto, 
            double montoImpuesto) {

        return mdlImpuesto.crearImpuesto(codigoImpuesto, tarifaImpuesto, 
                montoImpuesto);
    }
    
    public int getCodImpuesto() {
        return mdlImpuesto.getCodImpuesto();
    }
}
