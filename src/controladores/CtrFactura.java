/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import java.util.ArrayList;
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
    public boolean crearFacResumen(String codigoMoneda, double tipoCambio, 
            double totalServGravados, double totalSerExentos, 
            double totalMercanciasGravadas, double totalMercanciasExentas, 
            double totalGravado, double totalExento, double totalVenta, 
            double totalDescuentos, double totalVentaNeta, double totalImpuesto, 
            double totalComprobante) {
        
        return mdlFactura.crearFacResumen(codigoMoneda, tipoCambio, totalServGravados, 
                totalSerExentos, totalMercanciasGravadas, 
                totalMercanciasExentas, totalGravado, totalExento, totalVenta, 
                totalDescuentos, totalVentaNeta, totalImpuesto, 
                totalComprobante);
        
    }
}
