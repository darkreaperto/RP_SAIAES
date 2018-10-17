/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

/**
 *
 * @author aoihanabi
 */
public class CtrMadera {
    private static CtrMadera instancia = null;
    
    /**
     * Obtener instancia única del controlador de madera.
     * @return Instancia única de Madera
     */
    public static CtrMadera getInstancia() {
        return instancia == null ? new CtrMadera() : instancia;
    }
}
