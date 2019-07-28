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
import logica.negocio.TipoProducto;
import logica.servicios.Logger;

/**
 * Modelo de tipo de producto con los procedimientos y consultas de base de datos
 * @author aoihanabi
 */
public class MdlTipoProducto {
    
    /** Controlador de conexión. */
    private static CtrConexion conexion;
    /** Procedimiento a ejecutar en la base. */
    private static String procedimiento;
    /** Resultado de las consultas a la base. */
    private static ResultSet resultado;
    /** Lista de tipos de producto en la base. */
    private static ArrayList<TipoProducto> tipos;
    
    /**
     * Constructor de clase modelo de tipo de producto.
     */
    public MdlTipoProducto() {
        conexion = new CtrConexion();
    }

    public ArrayList<TipoProducto> obtenerTiposProducto() {
         tipos = new ArrayList<>();

        try {
            procedimiento = "pc_obtener_tiposProducto()";
            conexion.abrirConexion();
            resultado = conexion.ejecutarProcedimiento(procedimiento);

            String codTipoProducto;
            String descTipoProducto;
            String estadoTipoProducto;

            while (resultado.next()) {
                
                codTipoProducto = resultado.getString("cod_TipoProducto");
                descTipoProducto = resultado.getString("desc_TipoProducto");
                estadoTipoProducto = resultado.getString("estado_TipoProducto");
                
                TipoProducto tproducto
                        = new TipoProducto(codTipoProducto, descTipoProducto, 
                                estadoTipoProducto);

                if (!tipos.contains(tproducto)) {
                    tipos.add(tproducto);
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
