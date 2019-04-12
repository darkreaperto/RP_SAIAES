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
import logica.negocio.Troza;
import logica.servicios.Mensaje;

/**
 * Modelo de la clase Troza
 * @author fuent
 */
public class MdlTroza {
    /** Controlador de conexión. */
    private static CtrConexion conexion;
    /** Procedimiento a ejecutar en la base. */
    private static String procedimiento;
    /** Resultado de las consultas a la base. */
    private static ResultSet resultado;
    /** Lista de productos en la base. */
    private static ArrayList<Troza> trozas;
    /** Clase Mensaje para mostrar errores capturados. */
    private static Mensaje msgError;
    
    /**
     * Constructor de clase modelo de madera.
     */
    public MdlTroza() {
        conexion = new CtrConexion();
        msgError = new Mensaje();
    }
    /**
     * Llena una lista con todas las trozas almacenadas en la BD.
     * @return lista de productos.
     */
    public ArrayList<Troza> obtenerTrozas() {
        trozas = new ArrayList<>();

        try {
            procedimiento = "pc_obtener_trozas()";
            conexion.abrirConexion();
            resultado = conexion.ejecutarProcedimiento(procedimiento);

            String codigo;
            String codInterno;
            String codTmadera;
            String descTmadera;
            double pulgadas;
            String tipoProducto;
            String descripcion;            
            String codProv;
            String nomProv;
            String estado;            
                    
            while (resultado.next()) {
                
                codigo = resultado.getString("cod_Trozas");
                codInterno = resultado.getString("codInterno_Trozas");
                codTmadera = resultado.getString("codTipoMadera_Trozas");
                descTmadera = resultado.getString("descTipoMadera_Trozas");
                pulgadas = resultado.getDouble("pulgadas_Trozas");
                tipoProducto = resultado.getString("tipoProducto_Trozas");
                descripcion = resultado.getString("descripcion_Trozas");
                codProv = resultado.getString("codProveedor_Trozas");
                nomProv = resultado.getString("nomProveedor_Trozas");
                estado = resultado.getString("estado_Productos");
                                
                Troza trocita
                        = new Troza(codigo, codInterno, codTmadera, descTmadera,
                                pulgadas, tipoProducto, descripcion, codProv, 
                                nomProv, estado);

                if (!trozas.contains(trocita)) {
                    trozas.add(trocita);
                }
            }
        } catch (SQLException ex) {
            System.err.println(ex);
            ex.printStackTrace();
        } finally {
            conexion.cerrarConexion();
            return trozas;
        }
    }
    /**
     * Buscar Troza enviando por parámetro el criterio de búsqueda.
     * @param param Parametros para consultar productos en la base de datos
     * @return lista de productos
     */
    public ArrayList consultarTrozas(String param) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(param);

        trozas = new ArrayList<>();

        try {
            procedimiento = "pc_consultar_trozas(?)";
            conexion.abrirConexion();
            resultado = conexion.ejecutarProcedimiento(procedimiento, params);

            String codigo;
            String codInterno;
            String codTmadera;
            String descTmadera;
            double pulgadas;
            String tipoProducto;
            String descripcion;            
            String codProv;
            String nomProv;
            String estado;   

            while (resultado.next()) {
                codigo = resultado.getString("cod_Trozas");
                codInterno = resultado.getString("codInterno_Trozas");
                codTmadera = resultado.getString("codTipoMadera_Trozas");
                descTmadera = resultado.getString("descTipoMadera_Trozas");
                pulgadas = resultado.getDouble("pulgadas_Trozas");
                tipoProducto = resultado.getString("tipoProducto_Trozas");
                descripcion = resultado.getString("descripcion_Trozas");
                codProv = resultado.getString("codProveedor_Trozas");
                nomProv = resultado.getString("nomProveedor_Trozas");
                estado = resultado.getString("estado_Productos");
                
                Troza trocita
                        = new Troza(codigo, codInterno, codTmadera, descTmadera,
                                pulgadas, tipoProducto, descripcion, codProv, 
                                nomProv, estado);

                if (!trozas.contains(trocita)) {
                    trozas.add(trocita);
                }
            }
        } catch (SQLException ex) {
            System.err.println(ex);
            ex.printStackTrace();
        } finally {
            conexion.cerrarConexion();
            return trozas;
        }
    }    
    /**
     * Inserta una nueva troza en la BD
     * @param codInterno codigo de la troza para uso interno del sistema
     * @param codTipoMadera codigo del tipo de madera (variedad)
     * @param descTipoMadera nombre del tipo de madera
     * @param pulgadas cantidad de troza en pulgadas
     * @param tipoProducto TROZA duh
     * @param descripcion descripción de la troza
     * @param codProveedor codigo del proveedor de la troza
     * @param nomProveedor nombre del proveedor de la troza
     * @return verdadero si fue insertada correctamente
     */
    public boolean crearTroza(String codInterno, String codTipoMadera, 
            String descTipoMadera, double pulgadas, String tipoProducto, 
            String descripcion, String codProveedor, String nomProveedor) {
        
        ArrayList<Object> params = new ArrayList<>();
        params.add(codProd);
        params.add(codOrigen);
        params.add(descripcion);
        params.add(precio);
        params.add(cantVaras);
        params.add(grueso);
        params.add(ancho);
        params.add(codTipoMadera);
        params.add(tipoProducto);

        boolean creacionExitosa = true;
        try {
            procedimiento = "pc_crear_producto(?, ?, ?, ?, ?, ?, ?, ?, ?)";

            conexion.abrirConexion();
            resultado = conexion.ejecutarProcedimiento(procedimiento, params);
            //creacionExitosa = true;
            System.out.println(resultado);

        } catch (SQLException ex) {
            System.err.println(ex);  
            ex.printStackTrace();
            creacionExitosa = false;
            System.out.println("ERROR SQL " + ex.getErrorCode());
            msgError.mostrarMensajeErrorSQL(ex.getErrorCode());
        } finally {
            conexion.cerrarConexion();
            return creacionExitosa;
        }
    }
    
    public boolean actualizarProducto(String codProd, String codOrigen, 
            String descripcion, double precio, double cantVaras,
            String grueso, String ancho, String codTipoMadera,
            String tipoProducto, String codigo) {
        
        ArrayList<Object> params = new ArrayList<>();
        params.add(codProd);
        params.add(codOrigen);
        params.add(descripcion);
        params.add(precio);
        params.add(cantVaras);
        params.add(grueso);
        params.add(ancho);
        params.add(codTipoMadera);
        params.add(codigo);

        boolean creacionExitosa = false;
        try {
            procedimiento = "pc_actualizar_producto(?, ?, ?, ?, ?, ?, ?, ?, ?)";

            conexion.abrirConexion();
            resultado = conexion.ejecutarProcedimiento(procedimiento, params);
            creacionExitosa = true;

        } catch (SQLException ex) {
            creacionExitosa = false;
            System.err.println(ex);
            ex.printStackTrace();
        } finally {
            conexion.cerrarConexion();
            return creacionExitosa;
        }
    }
    
    public boolean inactivarProducto(String codigo) {
        
        ArrayList<Object> params = new ArrayList<>();
        params.add(codigo);
        
        boolean creacionExitosa = false;
        try {
            procedimiento = "pc_inactivar_producto(?)";

            conexion.abrirConexion();
            resultado = conexion.ejecutarProcedimiento(procedimiento, params);
            creacionExitosa = true;

        } catch (SQLException ex) {
            creacionExitosa = false;
            System.err.println(ex);
            ex.printStackTrace();
        } finally {
            conexion.cerrarConexion();
            return creacionExitosa;
        }
    }
    
    public boolean activarProducto(String codigo) {
        
        ArrayList<Object> params = new ArrayList<>();
        params.add(codigo);
        
        boolean creacionExitosa = false;
        try {
            procedimiento = "pc_activar_producto(?)";

            conexion.abrirConexion();
            resultado = conexion.ejecutarProcedimiento(procedimiento, params);
            creacionExitosa = true;

        } catch (SQLException ex) {
            creacionExitosa = false;
            System.err.println(ex);
            ex.printStackTrace();
        } finally {
            conexion.cerrarConexion();
            return creacionExitosa;
        }
    }
    
    public boolean actualizarInventario(string tipoProd, int unidades, String codigo) {
        
        ArrayList<Object> params = new ArrayList<>();
        params.add(tipoProd);
        params.add(unidades);
        params.add(codigo);
        
        boolean creacionExitosa = false;
        try {
            procedimiento = "pc_actualizar_inventario(?, ?, ?)";

            conexion.abrirConexion();
            resultado = conexion.ejecutarProcedimiento(procedimiento, params);
            creacionExitosa = true;

        } catch (SQLException ex) {
            creacionExitosa = false;
            System.err.println(ex);
            ex.printStackTrace();
        } finally {
            conexion.cerrarConexion();
            return creacionExitosa;
        }
    }
}
