/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica.negocio;

import java.util.ArrayList;

/**
 * Instancia las facturas con sus atributos.
 * @author aoihanabi
 */
public class Factura {
    
    private String codFactura;
    //Encabezado
    private FacEncabezado encabezado;
    //Linea de pedido
    private ArrayList<LineaDetalle> lineasDetalle;
    //ResumenFactura
    private FacResumen resumen;
    //Información de referencia
    private FacReferencia infoReferencia;
    //Normativa vigente (resolución)
    private FacNormativa normativa;
    //Mecanismo de seguridad
    private FacSeguridad mecanSeguridad;
    

    /**
     * Constructor vacío de clase de factura.
     */
    public Factura() {
        this.lineasDetalle = new ArrayList<>();
    }
    
    /**
     * Constructor de clase de factura, inicializa variables.
     * @param encabezado encabezado de la factura
     * @param lineasDetalle lineas de detalle (lista de productos a facturar)
     * @param resumen resumen de la factura
     * @param infoReferencia información de referencia de la factura
     * @param normativa información de normativa de la factura
     * @param mecanSeguridad mecanismo de seguridad de la factura
     */
    public Factura(String codFactura, FacEncabezado encabezado, ArrayList<LineaDetalle> lineasDetalle, 
            FacResumen resumen, FacReferencia infoReferencia, 
            FacNormativa normativa, FacSeguridad mecanSeguridad) {
        
        this.codFactura = codFactura;
        this.encabezado = encabezado;
        this.lineasDetalle = lineasDetalle;
        this.resumen = resumen;
        this.infoReferencia = infoReferencia;
        this.normativa = normativa;
        this.mecanSeguridad = mecanSeguridad;
    }

    /**
     * Obtener el codigo de la bd para la factura.
     * @return the codigo
     */
    public String getCodFactura() {
        return codFactura;
    }
    
    /**
     * Establecer el codigo de la bd para la factura.
     * @param codFactura the encabezado to set
     */
    public void setEncabezado(String codFactura) {
        this.codFactura = codFactura;
    }
    /**
     * Establecer el encabezado de la factura.
     * @param encabezado the encabezado to set
     */
    public void setEncabezado(FacEncabezado encabezado) {
        this.encabezado = encabezado;
    }
    
    /**
     * Obtener el encabezado de la factura.
     * @return the encabezado
     */
    public FacEncabezado getEncabezado() {
        return encabezado;
    }

    /**
     * Obtener las líneas de detalle de la factura.
     * @return the lineasDetalle
     */
    public ArrayList<LineaDetalle> getLineasDetalle() {
        return lineasDetalle;
    }

    /**
     * Establecer la línea de detalle de la factura.
     * @param lineasDetalle the lineaDetalle to set
     */
    public void setLineaDetalle(ArrayList<LineaDetalle> lineasDetalle) {
        this.lineasDetalle = lineasDetalle;
    }

    /**
     * Obtener el resumen de la factura.
     * @return the resumen
     */
    public FacResumen getResumen() {
        return resumen;
    }

    /**
     * Establecer el resumen de la factura.
     * @param resumen the resumen to set
     */
    public void setResumen(FacResumen resumen) {
        this.resumen = resumen;
    }

    /**
     * Obtener la información de referencia de la factura.
     * @return the infoReferencia
     */
    public FacReferencia getInfoReferencia() {
        return infoReferencia;
    }

    /**
     * Establecer la información de referencia de la factura.
     * @param infoReferencia the infoReferencia to set
     */
    public void setInfoReferencia(FacReferencia infoReferencia) {
        this.infoReferencia = infoReferencia;
    }

    /**
     * Obtener la normativa de la factura.
     * @return the normativa
     */
    public FacNormativa getNormativa() {
        return normativa;
    }

    /**
     * Establecer la normativa de la factura.
     * @param normativa the normativa to set
     */
    public void setNormativa(FacNormativa normativa) {
        this.normativa = normativa;
    }

    /**
     * Obtener el mecanismo de seguridad de la factura.
     * @return the mecanSeguridad
     */
    public FacSeguridad getMecanSeguridad() {
        return mecanSeguridad;
    }

    /**
     * Establecer el mecanismo de seguridad de la factura.
     * @param mecanSeguridad the mecanSeguridad to set
     */
    public void setMecanSeguridad(FacSeguridad mecanSeguridad) {
        this.mecanSeguridad = mecanSeguridad;
    }
}
