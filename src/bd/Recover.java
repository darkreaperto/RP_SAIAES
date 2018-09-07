/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bd;

/**
 * Clase para recuperar la contraseña.
 * @author dark-reaper
 */
public class Recover {
    
    private final String correo;
    private final String codigo;
    
    public Recover(String correo) {
        this.correo = correo;
        this.codigo = genCodigo();
    }
    
    /**
     * Genera un codigo aleatorio.
     * @return el codigo.
     */
    private final String genCodigo() {
        return String.valueOf((int) (Math.random() * (9999-1000)) + 1000);
    }
    
    /**
     * Coincide el correo de recuperacion con el codigo generado.
     * @param correo
     * @param codigo
     * @return true si el codigo  coincide, false de lo contrario.
     */
    public boolean confirmarCodigo(String correo, String codigo) {
        return (this.correo.equals(correo) && 
                this.codigo.equals(codigo));
    }
    
    public String getCorreo() {
        return correo;
    }
    
    public String getCodigo() {
        return codigo;
    }
}
