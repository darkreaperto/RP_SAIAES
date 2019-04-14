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
import logica.servicios.Mensaje;

/**
 * Modelo de la clase varios
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
    /** Clase Mensaje para mostrar errores capturados. */
    private static Mensaje msgError;
    /**
     * Constructor de clase modelo de madera.
     */
    public MdlMadera() {
        conexion = new CtrConexion();
        msgError = new Mensaje();
    }
    /**
     * Llena una lista con todos los productos almacenados en la BD.
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
            String codOrigen;
            String descripcion;
            double precioXvara;
            double cantVaras;
            String grueso;
            String ancho;            
            String codTipoMadera;
            String descTipoMadera;
            String tipoProducto;
            String estado;            
                    
            while (resultado.next()) {
                
                codigo = resultado.getString("cod_Productos");
                codProducto = resultado.getString("codProd_Productos");
                codOrigen = resultado.getString("codParent_Productos");
                descripcion = resultado.getString("desc_Productos");
                precioXvara = resultado.getDouble("precioXvara_Productos");
                cantVaras = resultado.getDouble("cantVaras_Productos");
                grueso = resultado.getString("grueso_Productos");
                ancho = resultado.getString("ancho_Productos");
                codTipoMadera = resultado.getString("codTipoMadera_Productos");
                descTipoMadera = resultado.getString("desc_TipoMadera");
                tipoProducto = resultado.getString("tipoProducto_Productos");
                estado = resultado.getString("estado_Productos");
                                
                Madera producto
                        = new Madera(codigo, codProducto, codOrigen, descripcion,
                                precioXvara, cantVaras, grueso, ancho, 
                                codTipoMadera, descTipoMadera, tipoProducto, 
                                estado);

                if (!productos.contains(producto)) {
                    productos.add(producto);
                }
            }
        } catch (SQLException ex) {
            System.err.println(ex);
            ex.printStackTrace();
        } finally {
            conexion.cerrarConexion();
            return productos;
        }
    }
    /**
     * Buscar producto enviando por parámetro el criterio de búsqueda.
     * @param param Parametros para consultar productos en la base de datos
     * @return lista de productos
     */
    public ArrayList consultarProductos(String param) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(param);

        productos = new ArrayList<>();

        try {
            procedimiento = "pc_consultar_productos(?)";
            conexion.abrirConexion();
            resultado = conexion.ejecutarProcedimiento(procedimiento, params);

            String codigo;
            String codProducto;
            String codOrigen;
            String descripcion;
            double precioXvara;
            double cantVaras;
            String grueso;
            String ancho;            
            String codTipoMadera;
            String descTipoMadera;
            String tipoProducto;
            String estado;

            while (resultado.next()) {
                codigo = resultado.getString("cod_Productos");
                codProducto = resultado.getString("codProd_Productos");
                codOrigen = resultado.getString("codParent_Productos");
                descripcion = resultado.getString("desc_Productos");
                precioXvara = resultado.getDouble("precioXvara_Productos");                
                cantVaras = resultado.getDouble("cantVaras_Productos");
                grueso = resultado.getString("grueso_Productos");
                ancho = resultado.getString("ancho_Productos");
                codTipoMadera = resultado.getString("codTipoMadera_Productos");
                descTipoMadera = resultado.getString("desc_TipoMadera");
                tipoProducto = resultado.getString("tipoProducto_Productos");
                estado = resultado.getString("estado_Productos");
                
                Madera producto
                        = new Madera(codigo, codProducto, codOrigen, descripcion,
                                precioXvara, cantVaras, grueso, ancho, 
                                codTipoMadera, descTipoMadera, tipoProducto, 
                                estado);

                if (!productos.contains(producto)) {
                    productos.add(producto);
                }
            }
        } catch (SQLException ex) {
            System.err.println(ex);
            ex.printStackTrace();
        } finally {
            conexion.cerrarConexion();
            return productos;
        }
    }
    /**
     * Buscar productos enviando por parámetro el código de clasificación de
     * búsqueda y el critério de búsqueda.
     * @param paramProd Datos del producto para consultar producto en la bd
     * @param codBusq código de clasificación/especificación de búsqueda
     * @return lista de productos
     */
    public ArrayList busqAvzProductos(String paramProd, int codBusq) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(paramProd);
        params.add(codBusq);
        
        productos = new ArrayList<>();
        
        try {
            procedimiento = "pc_busq_avz_producto(?, ?)";
            conexion.abrirConexion();
            resultado = conexion.ejecutarProcedimiento(procedimiento, params);

            String codigo;
            String codProducto;
            String codOrigen;
            String descripcion;
            double precioXvara;
            double cantVaras;
            String grueso;
            String ancho;            
            String codTipoMadera;
            String descTipoMadera;
            String tipoProducto;
            String estado;
            
            while (resultado.next()) {
                codigo = resultado.getString("cod_Productos");
                codProducto = resultado.getString("codProd_Productos");
                codOrigen = resultado.getString("codParent_Productos");
                descripcion = resultado.getString("desc_Productos");
                cantVaras = resultado.getDouble("cantVaras_Productos");
                precioXvara = resultado.getDouble("precioXvara_Productos");
                grueso = resultado.getString("grueso_Productos");
                ancho = resultado.getString("ancho_Productos");
                codTipoMadera = resultado.getString("codTipoMadera_Productos");
                descTipoMadera = resultado.getString("desc_TipoMadera");
                tipoProducto = resultado.getString("tipoProducto_Productos");
                estado = resultado.getString("estado_Productos");
                

                Madera producto
                        = new Madera(codigo, codProducto, codOrigen, descripcion,
                                precioXvara, cantVaras, grueso, ancho, 
                                codTipoMadera, descTipoMadera, tipoProducto, 
                                estado);

                if (!productos.contains(producto)) {
                    productos.add(producto);
                }
            }
        } catch (SQLException ex) {
            System.err.println(ex);
            ex.printStackTrace();
        } finally {
            conexion.cerrarConexion();
            return productos;
        }
    }
    /**
     * Inserta un nuevo producto en la BD
     * @param codProd codigo personalizado asignado al producto
     * @param descripcion detalle del producto (opcional)
     * @param precio precio por vara del producto
     * @param cantVaras cantidad de varas que entran
     * @param grueso grueso del producto
     * @param ancho ancho del producto
     * @param codTipoMadera codigo del tipo de madera del producto
     * @param tipoProducto descripcion de cual es el tipo de producto
     * @return true si inserta el producto.
     */
    public boolean crearProducto(String codProd, 
            String descripcion, double precio, double cantVaras,
            String grueso, String ancho, String codTipoMadera, String tipoProducto) {
        
        ArrayList<Object> params = new ArrayList<>();
        params.add(codProd);
        params.add(descripcion);
        params.add(precio);
        params.add(cantVaras);
        params.add(grueso);
        params.add(ancho);
        params.add(codTipoMadera);
        params.add(tipoProducto);

        boolean creacionExitosa = true;
        try {
            procedimiento = "pc_crear_producto(?, ?, ?, ?, ?, ?, ?, ?)";

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
    
    /**
     * Actualiza los diferentes atributos de la madera y los almacena en la BD
     * @param codProd codigo personalizado asignado al producto
     * @param descripcion detalle del producto (opcional)
     * @param precio precio por vara del producto
     * @param cantVaras cantidad de varas que entran
     * @param grueso grueso del producto
     * @param ancho ancho del producto
     * @param codTipoMadera codigo del tipo de madera del producto
     * @param tipoProducto descripcion de cual es el tipo de producto
     * @param codigo codigo del la bd
     * @return true si actualiza el producto.
     */
    public boolean actualizarProducto(String codProd, 
            String descripcion, double precio, double cantVaras,
            String grueso, String ancho, String codTipoMadera,
            String tipoProducto, String codigo) {
        
        ArrayList<Object> params = new ArrayList<>();
        params.add(codProd);
        params.add(descripcion);
        params.add(precio);
        params.add(cantVaras);
        params.add(grueso);
        params.add(ancho);
        params.add(codTipoMadera);
        params.add(codigo);

        boolean creacionExitosa = false;
        try {
            procedimiento = "pc_actualizar_producto(?, ?, ?, ?, ?, ?, ?, ?)";

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
    /**
     * Modelo que accede al procedimiento de sumar cantidad de madera
     * @param tipoProd tipo de madera (aserrada, terminada)
     * @param cantVaras cantidad de varas a sumar
     * @param codigo codigo del registro a sumar
     * @return Verdadero si suma correctamente
     */
    public boolean sumarRegMadera(String tipoProd, double cantVaras,
            String codigo) {
        
        ArrayList<Object> params = new ArrayList<>();
        params.add(tipoProd);
        params.add(cantVaras);
        params.add(codigo);
        
        boolean creacionExitosa = false;
        try {
            procedimiento = "pc_sumar_reg_madera(?, ?, ?)";

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
    /**
     * Modelo que accede al procedimiento de restar cantidad de madera
     * @param tipoProd tipo de madera (aserrada, terminada)
     * @param cantVaras cantidad de varas a sumar
     * @param codigo codigo del registro a sumar
     * @return Verdadero si suma correctamente
     */
    public boolean restarRegMadera(String tipoProd, double cantVaras,
            String codigo) {
        
        ArrayList<Object> params = new ArrayList<>();
        params.add(tipoProd);
        params.add(cantVaras);
        params.add(codigo);
        
        boolean creacionExitosa = false;
        try {
            procedimiento = "pc_restar_reg_madera(?, ?, ?)";

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