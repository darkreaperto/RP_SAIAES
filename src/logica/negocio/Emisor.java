/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica.negocio;

/**
 *
 * @author darkreaper
 */
public class Emisor {
    
    private String cod;
    private String nombre;
    private String tipoId;
    private String numId;
    private String nomComercial;
    private int codPais;
    private int numTel;
    private String correoElec;
    
    /**
     * Constructor vacío de la clase Emisor.
     */
    public Emisor() {
        
    }
    
    /**
     * Constructor de la clase Emisor.
     * @param cod código del emisor en la BD
     * @param nombre nombre del emisor
     * @param tipoId tipo de identificación del emisor
     * @param numId número de identificación del emisor
     * @param nomComercial nombre comercial del emisor
     * @param codPais código de país del teléfono del emisor
     * @param numTel número de teléfono del emisor
     * @param correoElec correo electrónico del emisor
     */
    public Emisor(String cod, String nombre, String tipoId, String numId, 
            String nomComercial, int codPais, int numTel, String correoElec) {
        
        this.cod = cod;
        this.nombre = nombre;
        this.tipoId = tipoId;
        this.numId = numId;
        this.nomComercial = nomComercial;
        this.codPais = codPais;
        this.numTel = numTel;
        this.correoElec = correoElec;
    }

    /**
     * Obtener el código del emisor.
     * @return the cod
     */
    public String getCod() {
        return cod;
    }

    /**
     * Obtener el nombre del emisor.
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtener el tipo de identificación del emisor.
     * @return the tipoId
     */
    public String getTipoId() {
        return tipoId;
    }
    
    /**
     * Obtener el número de identificación del emisor.
     * @return the numId
     */
    public String getNumId() {
        return numId;
    }

    /**
     * Obtener el nombre comercial del emisor.
     * @return the nomComercial
     */
    public String getNomComercial() {
        return nomComercial;
    }

    /**
     * Obtener el código de país del teléfono del emisor.
     * @return the codPais
     */
    public int getCodPais() {
        return codPais;
    }

    /**
     * Obtener el número de teléfono del emisor.
     * @return the numTel
     */
    public int getNumTel() {
        return numTel;
    }

    /**
     * Obtener el correo electrónico del emisor.
     * @return the correoElec
     */
    public String getCorreoElec() {
        return correoElec;
    }

    /**
     * Establecer el código del emisor.
     * @param cod the cod to set
     */
    public void setCod(String cod) {
        this.cod = cod;
    }

    /**
     * Establecer el nombre del emisor.
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Establecer el tipo de identificación del emisor.
     * @param tipoId the tipoId to set
     */
    public void setTipoId(String tipoId) {
        this.tipoId = tipoId;
    }
    
    /**
     * Establecerl número de identificación del emisor.
     * @param numId the numId to set
     */
    public void setNumId(String numId) {
        this.numId = numId;
    }

    /**
     * Establecer el nombre comercial del emisor.
     * @param nomComercial the nomComercial to set
     */
    public void setNomComercial(String nomComercial) {
        this.nomComercial = nomComercial;
    }

    /**
     * Establecer el código de país del teléfono del emisor.
     * @param codPais the codPais to set
     */
    public void setCodPais(int codPais) {
        this.codPais = codPais;
    }

    /**
     * Establecer el número de teléfono del emisor.
     * @param numTel the numTel to set
     */
    public void setNumTel(int numTel) {
        this.numTel = numTel;
    }

    /**
     * Establecer el correo electrónico del emisor.
     * @param correoElec the correoElec to set
     */
    public void setCorreoElec(String correoElec) {
        this.correoElec = correoElec;
    }
}
