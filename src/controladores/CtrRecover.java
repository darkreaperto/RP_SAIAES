/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import logica.servicios.Recover;

/**
 * Controlador de la clase Recover.
 * @author dark-reaper
 */
public class CtrRecover {
    
    private static Recover recover;
    
    /**
     * Controlador de clase de recuperación, inicializa variables
     * @param correo Correo de recuperación.
     */
    public CtrRecover(String correo) {
        recover = new Recover(correo);
    }
    
    /**
     * Crear nueva instancia de recuperación.
     * @param correo Correo de recuperación.
     */
    public void nuevoRecovery(String correo) {
        recover = null;
        recover = new Recover(correo);
    }
    
    /**
     * Llama método que verifica si coincide el correo de recuperacion con el 
     * codigo generado.
     * @param correo Correo de recuperación.
     * @param codigo Código para recuperar.
     * @return Verdadero si el código es correcto.
     */
    public boolean confirmarCodigo(String correo, String codigo) {
        return recover.confirmarCodigo(correo, codigo);
    }
    
    /**
     * Obtener recover.
     * @return Recover.
     */
    public Recover getRecovery() {
        return recover;
    }
    
    /**
     * Obtener correo.
     * @return Correo.
     */
    public String getCorreo() {
        return recover.getCorreo();
    }
    
    /**
     * Obtener codigo.
     * @return Código.
     */
    public String getCodigo() {
        return recover.getCodigo();
    }
}
