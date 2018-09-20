/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica.servicios;

/**
 * Clase para recuperación de la contraseña tras verificar coincidencia 
 * del código enviado.
 * @author dark-reaper
 */
public class Recover {
    
    private final String correo;
    private final String codigo;
    
    /**
     * Constructor de clase Recuperación, inicializa variables.
     * @param correo Correo de recuperación.
     */
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
     * @param correo Correo del usuario
     * @param codigo Codigo de confirmación enviado al correo
     * @return true si el codigo  coincide, false de lo contrario.
     */
    public boolean confirmarCodigo(String correo, String codigo) {
        return (this.correo.equals(correo) && 
                this.codigo.equals(codigo));
    }
    
    /**
     * Obtener el correo del usuario.
     * @return correo del usuario
     */
    public String getCorreo() {
        return correo;
    }
    
    /**
     * Obtener el código del usuario.
     * @return código del usuario.
     */
    public String getCodigo() {
        return codigo;
    }
}
