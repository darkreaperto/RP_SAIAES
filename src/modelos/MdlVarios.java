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
import logica.negocio.Varios;
import logica.servicios.Mensaje;

/**
 * Modelo de la clase varios
 * @author aoihanabi
 */
public class MdlVarios {
    private static CtrConexion conexion;
    private static String procedimiento;
    private static ResultSet resultado;
    private static ArrayList<Varios> prodVarios;
    private static Mensaje msgError;

    public MdlVarios() {
        conexion = new CtrConexion();
        msgError = new Mensaje();
    }
    
    /**
     * Inserta un nuevo producto "varios" en la BD
     * @param precio precio del producto
     * @param descripcion detalle del producto
     * @return true si inserta el producto.
     */
    public boolean crearVarios(String descripcion, double precio) {
        
        ArrayList<Object> params = new ArrayList<>();
        params.add(descripcion);
        params.add(precio);

        boolean creacionExitosa = true;
        try {
            procedimiento = "pc_crear_varios(?, ?)";

            conexion.abrirConexion();
            resultado = conexion.ejecutarProcedimiento(procedimiento, params);
            System.out.println(resultado);

        } catch (SQLException ex) {
            System.err.println(ex);            
            creacionExitosa = false;
            System.out.println("ERROR SQL " + ex.getErrorCode());
            msgError.mostrarMensajeErrorSQL(ex.getErrorCode());
        } finally {
            conexion.cerrarConexion();
            return creacionExitosa;
        }
    }
}
