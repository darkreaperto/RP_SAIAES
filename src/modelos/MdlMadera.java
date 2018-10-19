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
import logica.negocio.Madera;

/**
 *
 * @author aoihanabi
 */
public class MdlMadera {
    
    /** Controlador de conexión. */
    private static CtrConexion conexion;
    /** Procedimiento a ejecutar en la base. */
    private static String procedimiento;
    /** Resultado de las consultas a la base. */
    private static ResultSet resultado;
    /** Lista de productos en la base. */
    private static ArrayList<Madera> productos;
    
    /**
     * Constructor de clase modelo de cliente.
     */
    public MdlMadera() {
        conexion = new CtrConexion();
    }
    /**
     * Llena una lista con todos los productos almacenados en la BD.
     *
     * @return lista de productos.
     */
    public ArrayList<Madera> obtenerProductos() {
        productos = new ArrayList<>();

        try {
            procedimiento = "pc_obtener_productos()";
            conexion.abrirConexion();
            resultado = conexion.ejecutarProcedimiento(procedimiento);

            String codigo;
            String codProducto;
            String nombre;
            String codTipoMadera;
            String descTipoMadera;
            String medidas;
            String codTipoProducto;
            String descTipoProducto;
            int cantidad;
            double precioXvara;
            String descripcion;
            String estado;
            String codProveedor;
                    
            while (resultado.next()) {
                
                codigo = resultado.getString("cod_Productos");
                codProducto = resultado.getString("codProd_Productos");
                nombre = resultado.getString("nom_Productos");
                descripcion = resultado.getString("desc_Productos");
                precioXvara = resultado.getDouble("precioXvara_Productos");
                cantidad = resultado.getInt("cantidad_Productos");
                medidas = resultado.getString("medidas_Productos");
                codTipoMadera = resultado.getString("codTipoMadera_Productos");
                descTipoMadera = resultado.getString("desc_TipoMadera");
                codTipoProducto = resultado.getString("codTipoProducto_Productos");
                descTipoProducto = resultado.getString("desc_TipoProducto");
                codProveedor = resultado.getString("codigo_Proveedores");
                estado = resultado.getString("estado_Productos");
                                
                Madera producto
                        = new Madera(codigo, codProducto, nombre, codTipoMadera, 
                                descTipoMadera, medidas, codTipoProducto, 
                                descTipoProducto, cantidad, precioXvara, 
                                descripcion, estado, codProveedor);

                if (!productos.contains(producto)) {
                    productos.add(producto);
                }
            }
        } catch (SQLException ex) {
            System.err.println(ex);
        } finally {
            conexion.cerrarConexion();
            return productos;
        }
    }
    
}