/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bd;

/**
 *
 * @author dark-reaper
 */
public class Recover {
    
    private final String correo;
    private final String codigo;
    
    public Recover(String correo) {
        this.correo = correo;
        this.codigo = genCodigo();
    }
    
    private final String genCodigo() {
        return String.valueOf((int) (Math.random() * (9999-1000)) + 1000);
    }
    
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
