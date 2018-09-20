/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica.servicios;

import java.io.File;

/**
 *
 * @author dark-reaper
 */
public class Autoguardado {
    
    private static Autoguardado instancia;
    private static String directorio;
    private String nombreArchivo;
    private final String separador;
    private File archivo;
    
    
    public Autoguardado(String nombreArchivo) {
        separador = System.getProperty("file.separator");
        Autoguardado.directorio = System.getProperty("user.dir").concat(separador);
        
        System.out.println(directorio.concat(nombreArchivo));
        this.archivo = new File(directorio.concat(nombreArchivo));
    }
    
    public static Autoguardado getInstancia(String nombreArchivo) {
        if (instancia == null) {
            instancia =  new Autoguardado(nombreArchivo);
        }
        instancia.setNombreArchivo(nombreArchivo);
        return instancia;
    }
    
//    public boolean abrirArchivo() {
//        
//    }
//    
//    public boolean cerrarArchivo() {
//        
//    }

    /**
     * @return the directorio
     */
    public String getDirectorio() {
        return directorio;
    }

    /**
     * @param directorio the directorio to set
     */
    public void setDirectorio(String directorio) {
        this.directorio = directorio;
    }

    /**
     * @return the nombreArchivo
     */
    public String getNombreArchivo() {
        return nombreArchivo;
    }

    /**
     * @param nombreArchivo the nombreArchivo to set
     */
    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    /**
     * @return the archivo
     */
    public File getArchivo() {
        return archivo;
    }

    /**
     * @param archivo the archivo to set
     */
    public void setArchivo(File archivo) {
        this.archivo = archivo;
    }
}
