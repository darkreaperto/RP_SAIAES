/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import controladores.CtrConexion;
import java.sql.ResultSet;
import logica.servicios.Mensaje;

/**
 * Modelo de clase impuesto
 * @author aoihanabi
 */
public class MdlImpuesto {
    private static CtrConexion conexion;
    private static String procedimiento;
    private static ResultSet resultado;
    private static Mensaje msgError;

    /**
     * Constructor de clase modelo de impuesto.
     */
    public MdlImpuesto() {
        conexion = new CtrConexion();
        msgError = new Mensaje();
    }
    
}
