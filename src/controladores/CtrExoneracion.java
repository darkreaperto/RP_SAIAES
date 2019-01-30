/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import java.util.ArrayList;
import java.util.Date;
import java.sql.Timestamp;
import logica.negocio.Exoneracion;
import logica.negocio.Impuesto;
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
    
    /**
     * Llama el método que inserta un nuevo registro 'exoneración' en la BD
     * @param codigoImpuesto codigo del tipo de impuesto indicado por haciendda
     * @param tipoDocumento tipo de documento de exoneración
     * @param numDocumento numero de documento de exoneración
     * @param institucion nombre de institución que emitió la exoneración 
     * @param fechaEmision fecha en que se emite el documento de exoneración
     * @param montoImpuesto monto de impuesto exonerado/autorizado sin impuesto
     * @param porcentajeCompra porcentaje de la compra autorizada o exoneración
     * @return verdadero si el impuesto se crea exitosamente
     */
    public boolean crearExoneracion(Impuesto impuesto, String tipoDocumento, 
            String numDocumento, String institucion, Date fechaEmision,
            double montoImpuesto, int porcentajeCompra) {
        return mdlExoneracion.crearExoneración(impuesto, tipoDocumento, 
                numDocumento, institucion, fechaEmision, montoImpuesto, 
                porcentajeCompra);
    }
    /**
     * Llama el método que inserta un nuevo registro 'exoneración' en la BD
     * @param codigoImpuesto codigo del tipo de impuesto indicado por haciendda
     * @param tipoDocumento tipo de documento de exoneración
     * @param numDocumento numero de documento de exoneración
     * @param institucion nombre de institución que emitió la exoneración 
     * @param fechaEmision fecha en que se emite el documento de exoneración
     * @param montoImpuesto monto de impuesto exonerado/autorizado sin impuesto
     * @param porcentajeCompra porcentaje de la compra autorizada o exoneración
     * @return verdadero si el impuesto se crea exitosamente
     */
    public boolean crearExoneracion(int codigoImpuesto, String tipoDocumento, 
            String numDocumento, String institucion, String fechaEmision,
            double montoImpuesto, int porcentajeCompra) {
        return mdlExoneracion.crearExoneración(codigoImpuesto, tipoDocumento, 
                numDocumento, institucion, fechaEmision, montoImpuesto, 
                porcentajeCompra);
    }
    
    
    public ArrayList<Timestamp> obtenerExoneraciones() {
        return mdlExoneracion.obtenerExoneraciones();
    }
}
