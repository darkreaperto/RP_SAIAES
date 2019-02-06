/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica.negocio;

/**
 * Instancia las direcciones con sus atributos.
 * @author aoihanabi
 */
public class Direccion {
    private int codigo;
    private String codProvincia;
    private String nomProvincia;
    private String codCanton;
    private String nomCanton;
    private String codDistrito;
    private String nomDistrito;
    private String codBarrio;
    private String nomBarrio;
    private String otrasSenas; 

    /**
     * Construccior vacio de direccion.
     */
    public Direccion() {
    }

    /**
     * Constructor de clase dirección, con parámetros.
     * @param codigo Código direccion.
     * @param codProvincia codigo de provincia para la BD.
     * @param nomProvincia nombre de provincia para la BD.
     * @param codCanton codigo de cantón para la BD.
     * @param nomCanton nombre de cantón para la BD.
     * @param codDistrito codigo de distrito para la BD.
     * @param nomDistrito nombre de distrito para la BD.
     * @param codBarrio codigo de barrio para la BD.
     * @param nomBarrio nombre de barrio para la BD.
     */
    public Direccion(int codigo, String codProvincia, String nomProvincia, 
            String codCanton, String nomCanton, String codDistrito, 
            String nomDistrito, String codBarrio, String nomBarrio, 
            String otrasSenas) {
        this.codigo = codigo;
        this.codProvincia = codProvincia;
        this.nomProvincia = nomProvincia;
        this.codCanton = codCanton;
        this.nomCanton = nomCanton;
        this.codDistrito = codDistrito;
        this.nomDistrito = nomDistrito;
        this.codBarrio = codBarrio;
        this.nomBarrio = nomBarrio;
        this.otrasSenas = otrasSenas;
    }
    
    /**
     * Obtener código.
     * @return Codigo de contacto
     */
    public int getCodigo() {
        return codigo;
    }

    /**
     * Establecer código
     * @param codigo el código a establecer
     */
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
    
    /**
     * Obtener código según hacienda de la provincia.
     * @return Codigo de provincia
     */
    public String getCodProvincia() {
        return codProvincia;
    }

    /**
     * Establecer código de provincia
     * @param codProvincia código de provincia
     */
    public void setCodProvincia(String codProvincia) {
        this.codProvincia = codProvincia;
    }

    /**
     * Obtener nombre de la provincia
     * @return 
     */
    public String getNomProvincia() {
        return nomProvincia;
    }

    /**
     * Establecer nombre de la provincia.
     * @param nomProvincia 
     */
    public void setNomProvincia(String nomProvincia) {
        this.nomProvincia = nomProvincia;
    }
    
    /**
     * Obtener código según hacienda del cantón.
     * @return código de cantón
     */
    public String getCodCanton() {
        return codCanton;
    }
    /** 
     * Establecer código de cantón
     * @param codCanton código de canton
     */
    public void setCodCanton(String codCanton) {
        this.codCanton = codCanton;
    }

    /**
     * Obtener nombre del cantón.
     * @return el nombre del cantón
     */
    public String getNomCanton() {
        return nomCanton;
    }

    /**
     * Establecer nombre del canton
     * @param nomCanton nombre del cantón
     */
    public void setNomCanton(String nomCanton) {
        this.nomCanton = nomCanton;
    }

    /**
     * Obtener código según hacienda del distrito.
     * @return el código del distrito
     */
    public String getCodDistrito() {
        return codDistrito;
    }

    /**
     * Establecer código de distrito
     * @param codDistrito código del distrito
     */
    public void setCodDistrito(String codDistrito) {
        this.codDistrito = codDistrito;
    }

    /**
     * Obtener nombre del distrito
     * @return nombre del distrito
     */
    public String getNomDistrito() {
        return nomDistrito;
    }

    /**
     * Establecer nombre del distrito
     * @param nomDistrito nombre del distrito
     */
    public void setNomDistrito(String nomDistrito) {
        this.nomDistrito = nomDistrito;
    }

    /**
     * Obtener código según hacienda del barrio.
     * @return codigo del barrio
     */
    public String getCodBarrio() {
        return codBarrio;
    }

    /**
     * Establecer código de barrio
     * @param codBarrio 
     */
    public void setCodBarrio(String codBarrio) {
        this.codBarrio = codBarrio;
    }

    /**
     * Obtener nombre del barrio
     * @return nombre del barrio
     */
    public String getNomBarrio() {
        return nomBarrio;
    }

    /**
     * Establecer nombre del barrio
     * @param nomBarrio nombre del barrio
     */
    public void setNomBarrio(String nomBarrio) {
        this.nomBarrio = nomBarrio;
    }
    
    /**
     * Obtener nombre del barrio
     * @return nombre del barrio
     */
    public String getOtrasSenas() {
        return otrasSenas;
    }

    /**
     * Establecer otras señas
     * @param otrasSenas otras señas
     */
    public void setOtrasSenas(String otrasSenas) {
        this.otrasSenas = otrasSenas;
    }
    
}
