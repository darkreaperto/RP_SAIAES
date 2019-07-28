/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import controladores.CtrConexion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import logica.negocio.TipoMadera;
import logica.servicios.Logger;
/**
 *
 * @author aoihanabi
 */
public class MdlTipoMadera {
    /** Controlador de conexión. */
    private static CtrConexion conexion;
    /** Procedimiento a ejecutar en la base. */
    private static String procedimiento;
    /** Resultado de las consultas a la base. */
    private static ResultSet resultado;
    /** Lista de tipos de madera en la base. */
    private static ArrayList<TipoMadera> tipos;
    
    /**
     * Constructor de clase modelo de tipo de madera.
     */
    public MdlTipoMadera() {
        conexion = new CtrConexion();
        
    }
        /**
         * Obtener listado de todos los tipos de madera que hay
         * @return listado
         */
        public ArrayList<TipoMadera> obtenerTiposMadera() {
         tipos = new ArrayList<>();

        try {
            procedimiento = "pc_obtener_tiposMadera()";
            conexion.abrirConexion();
            resultado = conexion.ejecutarProcedimiento(procedimiento);

            String codTipoMadera;
            String descTipoMadera;
            String estadoTipoMadera;

            while (resultado.next()) {
                
                codTipoMadera = resultado.getString("cod_TipoMadera");
                descTipoMadera = resultado.getString("desc_TipoMadera");
                estadoTipoMadera = resultado.getString("estado_TipoMadera");
                
                TipoMadera tmadera
                        = new TipoMadera(codTipoMadera, descTipoMadera, 
                                estadoTipoMadera);

                if (!tipos.contains(tmadera)) {
                    tipos.add(tmadera);
                }
            }
        } catch (SQLException ex) {
            Logger.registerNewError(ex);
            
            System.err.println(ex);
            ex.printStackTrace();
        } finally {
            conexion.cerrarConexion();
            return tipos;
        }
    }
}
