/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import java.util.ArrayList;
import logica.negocio.FacEncabezado;
import logica.negocio.FacNormativa;
import logica.negocio.FacReferencia;
import logica.negocio.FacResumen;
import logica.negocio.Factura;
import logica.negocio.Madera;
import modelos.MdlFactura;

/**
 * Controlador de la clase de Facturación
 * @author aoihanabi
 */
public class CtrFactura {
    private static CtrFactura instancia = null;
    MdlFactura mdlFactura;
    Factura Factura;
    ArrayList <Factura> Facturaes;

    /**
     * Constructor del controlador de línea de detalle, inicializa variables.
     */
    public CtrFactura() {
        Facturaes = new ArrayList<>();
        mdlFactura = new MdlFactura();
    }
    
    /**
     * Obtener instancia única del controlador de línea de detalle.
     * @return Instancia única de línea de detalle
     */
    public static CtrFactura getInstancia() {
        return  instancia == null ? new CtrFactura() : instancia;
    }    
    public int crearResumen(String codigoMoneda, double tipoCambio, 
            double totalServGravados, double totalSerExentos, 
            double totalMercanciasGravadas, double totalMercanciasExentas, 
            double totalGravado, double totalExento, double totalVenta, 
            double totalDescuentos, double totalVentaNeta, double totalImpuesto, 
            double totalComprobante) {
        
        return mdlFactura.crearResumen(codigoMoneda, tipoCambio, totalServGravados, 
                totalSerExentos, totalMercanciasGravadas, 
                totalMercanciasExentas, totalGravado, totalExento, totalVenta, 
                totalDescuentos, totalVentaNeta, totalImpuesto, 
                totalComprobante);
        
    }
    
    /**
     * Llama el método que crea un registro 'factura' en la bd
     * @param factura Objeto factura del que se obtienen los atributos necesarios
     * @return true si la factura se creó exitosamente
     */
    public boolean crearFactura(Factura factura) {

        return mdlFactura.crearFactura(factura);
    }
    
    /**
     * Llama el método que crea un registro 'linea por factura' en la bd
     * @param codLinea codigo de la linea de detalle
     * @param codFactura codigo de la factura con que se relaciona la linea
     * @return true si la linea por factura se creó exitosamente
     */
    public boolean crearLineaxFactura(int codLinea, int codFactura) {

        return mdlFactura.crearLineaxFactura(codLinea, codFactura);
    }
}
