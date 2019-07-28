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
import logica.servicios.Logger;
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
            String cedProv;
            String nomProv;
            String estado;            
                    
            while (resultado.next()) {
                
                codigo = resultado.getString("cod_Trozas");
                codInterno = resultado.getString("codInterno_Trozas");
                codTmadera = resultado.getString("codTipoMadera_Trozas");
                descTmadera = resultado.getString("desc_TipoMadera");
                pulgadas = resultado.getDouble("pulgadas_Trozas");
                tipoProducto = resultado.getString("tipoProducto_Trozas");
                descripcion = resultado.getString("descripcion_Trozas");
                cedProv = resultado.getString("cedPer_Trozas");
                nomProv = resultado.getString("nom_Personas");
                estado = resultado.getString("estado_Trozas");
                                
                Troza trocita
                        = new Troza(codigo, codInterno, codTmadera, descTmadera,
                                pulgadas, tipoProducto, descripcion, cedProv, 
                                nomProv, estado);

                if (!trozas.contains(trocita)) {
                    trozas.add(trocita);
                }
            }
        } catch (SQLException ex) {
            Logger.registerNewError(ex);
            
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
            String cedProv;
            String nomProv;
            String estado;   

            while (resultado.next()) {
                codigo = resultado.getString("cod_Trozas");
                codInterno = resultado.getString("codInterno_Trozas");
                codTmadera = resultado.getString("codTipoMadera_Trozas");
                descTmadera = resultado.getString("desc_TipoMadera");
                pulgadas = resultado.getDouble("pulgadas_Trozas");
                tipoProducto = resultado.getString("tipoProducto_Trozas");
                descripcion = resultado.getString("descripcion_Trozas");
                cedProv = resultado.getString("cedPer_Trozas");
                nomProv = resultado.getString("nom_Proveedor");
                estado = resultado.getString("estado_Trozas");
                
                Troza trocita
                        = new Troza(codigo, codInterno, codTmadera, descTmadera,
                                pulgadas, tipoProducto, descripcion, cedProv, 
                                nomProv, estado);

                if (!trozas.contains(trocita)) {
                    trozas.add(trocita);
                }
            }
        } catch (SQLException ex) {
            Logger.registerNewError(ex);
            
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
     * @param pulgadas cantidad de troza en pulgadas
     * @param descripcion descripción de la troza
     * @param cedProveedor codigo del proveedor de la troza
     * @return verdadero si fue insertada correctamente
     */
    public boolean crearTroza(String codInterno, String codTipoMadera,  
            double pulgadas, String descripcion, String cedProveedor) {
        
        ArrayList<Object> params = new ArrayList<>();
        params.add(codInterno);
        params.add(codTipoMadera);
        params.add(pulgadas);
        params.add(descripcion);
        params.add(cedProveedor);

        boolean creacionExitosa = true;
        try {
            procedimiento = "pc_crear_troza(?, ?, ?, ?, ?)";

            conexion.abrirConexion();
            resultado = conexion.ejecutarProcedimiento(procedimiento, params);
            //creacionExitosa = true;
            System.out.println(resultado);

        } catch (SQLException ex) {
            Logger.registerNewError(ex);
            
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
    
    /**
     * Actualiza los diferentes atributos de la troza y los almacena en la BD
     * @param codInterno codigo de la troza para uso interno del sistema
     * @param pulgadas cantidad de troza en pulgadas
     * @param descripcion descripción de la troza
     * @param cedProveedor cédula del proveedor de la troza
     * @param codigo codigo de la bd
     * @return verdadero si fue actualizada correctamente
     */
    public boolean actualizarTroza(String codInterno, double pulgadas, 
            String descripcion, String cedProveedor, String codigo) {
        
        ArrayList<Object> params = new ArrayList<>();
        params.add(codInterno);
        params.add(pulgadas);
        params.add(descripcion);
        params.add(cedProveedor);
        params.add(codigo);

        boolean creacionExitosa = false;
        try {
            procedimiento = "pc_actualizar_troza(?, ?, ?, ?, ?)";

            conexion.abrirConexion();
            resultado = conexion.ejecutarProcedimiento(procedimiento, params);
            creacionExitosa = true;

        } catch (SQLException ex) {
            Logger.registerNewError(ex);
            
            creacionExitosa = false;
            System.err.println(ex);
            ex.printStackTrace();
        } finally {
            conexion.cerrarConexion();
            return creacionExitosa;
        }
    }
    
    public boolean inactivarTroza(String codigo) {
        
        ArrayList<Object> params = new ArrayList<>();
        params.add(codigo);
        
        boolean creacionExitosa = false;
        try {
            procedimiento = "pc_inactivar_troza(?)";

            conexion.abrirConexion();
            resultado = conexion.ejecutarProcedimiento(procedimiento, params);
            creacionExitosa = true;

        } catch (SQLException ex) {
            Logger.registerNewError(ex);
            
            creacionExitosa = false;
            System.err.println(ex);
            ex.printStackTrace();
        } finally {
            conexion.cerrarConexion();
            return creacionExitosa;
        }
    }
    
    public boolean activarTroza(String codigo) {
        
        ArrayList<Object> params = new ArrayList<>();
        params.add(codigo);
        
        boolean creacionExitosa = false;
        try {
            procedimiento = "pc_activar_troza(?)";

            conexion.abrirConexion();
            resultado = conexion.ejecutarProcedimiento(procedimiento, params);
            creacionExitosa = true;

        } catch (SQLException ex) {
            Logger.registerNewError(ex);
            
            creacionExitosa = false;
            System.err.println(ex);
            ex.printStackTrace();
        } finally {
            conexion.cerrarConexion();
            return creacionExitosa;
        }
    }
    
    /**
     * Modelo que accede al procedimiento de sumar cantidad de madera
     * @param tipoProd tipo de madera (aserrada, terminada)
     * @param pulgadas cantidad de pulgadas a sumar
     * @param codigo codigo del registro a sumar
     * @return Verdadero si suma correctamente
     */
    public boolean sumarRegMadera(String tipoProd, double pulgadas,
            String codigo) {
        
        ArrayList<Object> params = new ArrayList<>();
        params.add(tipoProd);
        params.add(pulgadas);
        params.add(codigo);
        
        boolean creacionExitosa = false;
        try {
            procedimiento = "pc_sumar_reg_madera(?, ?, ?)";

            conexion.abrirConexion();
            resultado = conexion.ejecutarProcedimiento(procedimiento, params);
            creacionExitosa = true;

        } catch (SQLException ex) {
            Logger.registerNewError(ex);
            
            creacionExitosa = false;
            System.err.println(ex);
            ex.printStackTrace();
        } finally {
            conexion.cerrarConexion();
            return creacionExitosa;
        }
    }
    /**
     * Modelo que accede al procedimiento de restar cantidad de madera
     * @param tipoProd tipo de madera (aserrada, terminada)
     * @param pulgadas cantidad de pulgadas a sumar
     * @param codigo codigo del registro a sumar
     * @return Verdadero si suma correctamente
     */
    public boolean restarRegMadera(String tipoProd, double pulgadas,
            String codigo) {
        
        ArrayList<Object> params = new ArrayList<>();
        params.add(tipoProd);
        params.add(pulgadas);
        params.add(codigo);
        
        boolean creacionExitosa = false;
        try {
            procedimiento = "pc_restar_reg_madera(?, ?, ?)";

            conexion.abrirConexion();
            resultado = conexion.ejecutarProcedimiento(procedimiento, params);
            creacionExitosa = true;

        } catch (SQLException ex) {
            Logger.registerNewError(ex);
            
            creacionExitosa = false;
            System.err.println(ex);
            ex.printStackTrace();
        } finally {
            conexion.cerrarConexion();
            return creacionExitosa;
        }
    }
}
