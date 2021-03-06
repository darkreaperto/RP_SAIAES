/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import controladores.CtrConexion;
import controladores.CtrDireccion;
import java.sql.ResultSet;
import java.sql.SQLException;
import logica.negocio.Direccion;
import logica.negocio.Emisor;
import logica.servicios.Logger;
import logica.servicios.Mensaje;

/**
 *
 * @author darkreaper
 */
public class MdlEmisor {
    
    private static CtrConexion conexion;
    private static CtrDireccion ctrDireccion;
    private static String procedimiento;
    private static ResultSet resultado;
    private static Mensaje msgError;
    private static Emisor emisor;
    
    /**
     * 
     */
    public MdlEmisor() {
        conexion = new CtrConexion();
        ctrDireccion = new CtrDireccion();
        msgError = new Mensaje();
    }
    
    /**
     * Obtener la información de la BD del emisor.
     * @return el emisor
     */
    public Emisor obtenerEmisor() {
        
        Emisor emi = new Emisor();
        
        try {
            procedimiento = "pc_obtener_emisor()";
            conexion.abrirConexion();
            resultado = conexion.ejecutarProcedimiento(procedimiento);

            String cod;
            String nombre;
            String tipoId;
            String numId;
            String nomComercial;
            int codPais;
            int numTel;
            String correoElec;
            int codDireccion;

            while (resultado.next()) {
                cod = resultado.getString("cod_Emisor");
                nombre = resultado.getString("nombre_Emisor");
                tipoId = resultado.getString("tipoId_Emisor");
                numId = resultado.getString("numId_Emisor");
                nomComercial = resultado.getString("nomComercial_Emisor");
                codPais = resultado.getInt("codPaisTel_Emisor");
                numTel = resultado.getInt("numTel_Emisor");
                correoElec = resultado.getString("correoElec_Emisor");
                codDireccion = resultado.getInt("codDireccion_Emisor");
                
                Direccion dir = ctrDireccion.consultarDireccion(
                        String.valueOf(codDireccion));

                emi = new Emisor(cod, nombre, tipoId, numId, nomComercial, 
                        codPais, numTel, correoElec, dir);

            }
        } catch (SQLException ex) {
            Logger.registerNewError(ex);
            
            System.err.println(ex);
            ex.printStackTrace();
        } finally {
            conexion.cerrarConexion();
            return emi;
        }
    }
    
}
