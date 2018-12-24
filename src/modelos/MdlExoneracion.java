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
 * Modelo de clase exoneración
 * @author aoihanabi
 */
public class MdlExoneracion {
    private static CtrConexion conexion;
    private static String procedimiento;
    private static ResultSet resultado;
    private static Mensaje msgError;

    /**
     * Constructor de clase modelo exoneración.
     */
    public MdlExoneracion() {
        conexion = new CtrConexion();
        msgError = new Mensaje();
    }
    
    
}
