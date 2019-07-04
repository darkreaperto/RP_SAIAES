/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import controladores.CtrConexion;
import controladores.CtrContacto;
import controladores.CtrDireccion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import logica.negocio.Contacto;
import logica.negocio.Direccion;
import logica.negocio.Proveedor;
import logica.servicios.Mensaje;
import util.TipoContacto;

/**
 * Modelo de la clase Proveedor
 * @author aoihanabi
 */
public class MdlProveedor {
    /** Controlador de la clase conexión. */
    private static CtrConexion conexion;
    /** Procedimiento a ejecutar en la base. */
    private static String procedimiento;
    /** Resultado de las consultas a la base. */
    private static ResultSet resultado;
    /** Lista de proveedores en la base. */
    private static ArrayList<Proveedor> proveedores;
    /** Mensaje de error desde la base. */
    private static Mensaje msgError;
    /** Controlador de contacto. */
    private static CtrContacto ctrContacto;
    /** Controlador de direccion. */
    private static CtrDireccion ctrDireccion;
    
    /**
     * Constructor de clase modelo de proveedor.
     */
    public MdlProveedor() {
        conexion = new CtrConexion();
        msgError = new Mensaje();
        ctrContacto = CtrContacto.getInstancia();
        ctrDireccion = CtrDireccion.getInstancia();
    }

    /**
     * Llena la lista de proveedores con todos los almacenados en la BD.
     * @return Lista de todos los proveedores en la BD.
     */
    public ArrayList<Proveedor> obtenerProveedores() {
        proveedores = new ArrayList<>();

        try {
            procedimiento = "pc_obtener_proveedores()";
            conexion.abrirConexion();
            resultado = conexion.ejecutarProcedimiento(procedimiento);

            String cedulaPersonas;
            String tipoCedPersonas;
            String nombrePersona;
            String codDireccion;
            Direccion dirPersona;
            String estadoProveedor;
            ArrayList<Contacto> contactos;

            while (resultado.next()) {
                
                cedulaPersonas = resultado.getString("ced_Personas");
                tipoCedPersonas = resultado.getString("tipoCed_Personas");
                nombrePersona = resultado.getString("nom_Personas");
                codDireccion = resultado.getString("codDireccion_Personas");
                estadoProveedor = resultado.getString("estado_Proveedores");
                
                contactos = ctrContacto.consultarContactos(cedulaPersonas);
                System.out.println("COD DIRECCION FROM PROVEEDOR DB: " + codDireccion);
//                direccion = codDireccion == null ? null: 
//                        ctrDireccion.consultarDireccion(codDireccion);
                if (codDireccion == null) {
                    System.out.println("DIRECCION IS NULL FROM DB");
                    dirPersona = null;
                } else {
                    System.out.println("DIRECCION IS NOT NULL FROM DB");
                    dirPersona = ctrDireccion.consultarDireccion(codDireccion);
                }
                
                Proveedor proveedor
                        = new Proveedor(cedulaPersonas, tipoCedPersonas,
                                nombrePersona, dirPersona,estadoProveedor,
                                contactos);

                if (!proveedores.contains(proveedor)) {
                    proveedores.add(proveedor);
                }
            }
        } catch (SQLException ex) {
            System.err.println(ex);
            ex.printStackTrace();
        } finally {
            conexion.cerrarConexion();
            return proveedores;
        }
    }
    
    /**
     * Inserta un nuevo proveedor en la BD.
     * @param cedula Cedula del proveedor
     * @param tipoCed Tipo de cédula del proveedor (Física o Jurídica)
     * @param nombre Nombre completo del proveedor
     * @param dir Objeto dirección que contiene los datos de la direccion del proveedor
     * @param contactos Lista de contactos del proveedor
     * @return Verdadero si agrega el proveedor exitosamente
     */
    public boolean crearProveedor(String cedula, String tipoCed, String nombre,  
            Direccion dir, ArrayList<ArrayList<Object>> contactos) {
        
        int codDireccion = 0; // Asigna 0 para señalar que no hay dirección
        if (dir != null) {
            codDireccion = ctrDireccion.crearDireccion(dir.getCodProvincia(), 
                    dir.getCodCanton(), dir.getCodDistrito(), dir.getCodBarrio(), 
                    dir.getOtrasSenas());
        }
        
        System.out.println("MDLPROVEEDOR->CREAR->COD DIRECCION: " + codDireccion);
        
        ArrayList<Object> params = new ArrayList<>();
        params.add(cedula);
        params.add(tipoCed);
        params.add(nombre);
        params.add(codDireccion);
//        params.add(Types.BIGINT); // Estado verdadero por defecto

        boolean creacionExitosa = true;
        try {
            procedimiento = "pc_crear_proveedor(?, ?, ?, ?)";

            conexion.abrirConexion();
            resultado = conexion.ejecutarProcedimiento(procedimiento, params);
//            
//            int indice = 0;
//            //obtener el índice de la fila insertada
//            while (resultado.next()) {
//                indice = resultado.getInt("@indice");
//            }
            
            //Crear un registro en la BD para cada contacto del proveedor
            for (int i = 0; i < contactos.size(); i++) {
                TipoContacto tipo = (TipoContacto) contactos.get(i).get(0); //telefono o correo
                String info = contactos.get(i).get(1).toString(); // Datos del contacto (número telefónico o direccion de correo)
                
                params.clear();
                params.add(cedula);
                params.add(tipo);
                params.add(info);
                ctrContacto.crearContacto(cedula, tipo, info);
            }
            System.out.println("FROM CREAR PROVEEDOR: " + resultado);
            
        } catch (SQLException ex) {
            System.err.println(ex);      
            ex.printStackTrace();
            creacionExitosa = false;
            System.out.println("ERROR SQL: " + ex.getErrorCode());
            msgError.mostrarMensajeErrorSQL(ex.getErrorCode());
            
        } finally {
            conexion.cerrarConexion();
            return creacionExitosa;
        }
    }
    
    /**
     * Actualiza la información del proveedor en la BD.
     * @param nombre El nombre completo del proveedor
     * @param dir Objeto dirección que contiene los datos de la direccion del proveedor
     * @param cedPersona Cédula unívoca y llave primaria para identificar el proveedor
     * @return Verdadero si la actualización fue exitosa.
     */    
    public boolean actualizarProveedor(String nombre, Direccion dir, 
            String cedPersona) {
        
        ArrayList<Object> params = new ArrayList<>();
        params.add(nombre);
        
        boolean creacionExitosa = false;
        try {
            int codDir = 0; // Asigna 0 para señalar que no hay dirección
            if (dir != null) {
                if (dir.getCodigo() == 0) {//si no hay direccion anterior, crear
                    codDir = ctrDireccion.crearDireccion(dir.getCodProvincia(), 
                            dir.getCodCanton(), dir.getCodDistrito(), 
                            dir.getCodBarrio(), dir.getOtrasSenas());
                } else { //si ya existía una direccion, se actualiza
                    ctrDireccion.actualizarDireccion(dir.getCodProvincia(), 
                            dir.getCodCanton(), dir.getCodDistrito(), 
                            dir.getCodBarrio(), dir.getOtrasSenas(), 
                            dir.getCodigo());
                }
            }
            //agregar código de dirección para actualizar
            params.add(codDir);
            params.add(cedPersona);
            procedimiento = "pc_actualizar_proveedor(?, ?, ?)";

            conexion.abrirConexion();
            resultado = conexion.ejecutarProcedimiento(procedimiento, params);
            creacionExitosa = true;

        } catch (SQLException ex) {
            creacionExitosa = false;
            System.err.println(ex);
            ex.printStackTrace();
            msgError.mostrarMensajeErrorSQL(ex.getErrorCode());
        } finally {
            conexion.cerrarConexion();
            return creacionExitosa;
        }
    }
    
    /**
     * Inactiva el proveedor en la bd.
     * @param cedPersona Cédula unívoca y llave primaria para identificar el proveedor
     * @return Verdadero si inactiva el proveedor
     */
    public boolean inactivarProveedor(String cedPersona) {
        
        ArrayList<Object> params = new ArrayList<>();
        params.add(cedPersona);
        
        boolean creacionExitosa = false;
        try {
            procedimiento = "pc_inactivar_proveedor(?)";

            conexion.abrirConexion();
            resultado = conexion.ejecutarProcedimiento(procedimiento, params);
            creacionExitosa = true;

        } catch (SQLException ex) {
            creacionExitosa = false;
            System.err.println(ex);
            ex.printStackTrace();
            msgError.mostrarMensajeErrorSQL(ex.getErrorCode());
        } finally {
            conexion.cerrarConexion();
            return creacionExitosa;
        }
    }
    
    /**
     * Activa el proveedor en la bd.
     * @param cedPersona Cédula unívoca y llave primaria para identificar el proveedor
     * @return Verdadero si la activación fue exitosa.
     */
    public boolean activarProveedor(String cedPersona) {
        
        ArrayList<Object> params = new ArrayList<>();
        params.add(cedPersona);
        
        boolean creacionExitosa = false;
        try {
            procedimiento = "pc_activar_proveedor(?)";

            conexion.abrirConexion();
            resultado = conexion.ejecutarProcedimiento(procedimiento, params);
            creacionExitosa = true;

        } catch (SQLException ex) {
            creacionExitosa = false;
            System.err.println(ex);
            ex.printStackTrace();
            msgError.mostrarMensajeErrorSQL(ex.getErrorCode());
            
        } finally {
            conexion.cerrarConexion();
            return creacionExitosa;
        }
    }

    /**
     * Buscar usuario enviando por parámetro el criterio de búsqueda.
     * @param param Parametros para consultar usuario en la base de datos
     * @return lista de proveedores
     */
    public ArrayList consultarProveedor(String param) {
        
        ArrayList<Object> params = new ArrayList<>();
        params.add(param);
        
        proveedores = new ArrayList<>();
        
        try {
            procedimiento = "pc_consultar_proveedores(?)";
            conexion.abrirConexion();
            resultado = conexion.ejecutarProcedimiento(procedimiento, params);

            String cedPersonas;
            String tipoCedPersonas;
            String nombrePersonas;
            String codDireccion;
            Direccion dirPersonas;
            String estadoProveedor;
            

            while (resultado.next()) {
                cedPersonas = resultado.getString("ced_Personas");
                tipoCedPersonas = resultado.getString("tipoCed_Personas");
                nombrePersonas = resultado.getString("nom_Personas");
                codDireccion = resultado.getString("codDireccion_Personas");
                estadoProveedor = resultado.getString("estado_Proveedores");

                ArrayList<Contacto> contactos;
                contactos = ctrContacto.consultarContactos(cedPersonas);
                dirPersonas = ctrDireccion.consultarDireccion(codDireccion);
                
                Proveedor proveedor
                        = new Proveedor(cedPersonas, tipoCedPersonas, 
                                nombrePersonas, dirPersonas, estadoProveedor,
                                contactos);

                if (!proveedores.contains(proveedor)) {
                    proveedores.add(proveedor);
                }
            }
        } catch (SQLException ex) {
            System.err.println(ex);
            ex.printStackTrace();
            msgError.mostrarMensajeErrorSQL(ex.getErrorCode());
            
        } finally {
            conexion.cerrarConexion();
            return proveedores;
        }
    }
    
    /**
     * Insertar un nuevo contacto para el proveedor.
     * @param tipo tipo de contacto correo o telefono
     * @param info detalle del contacto (numero de telefono o direccion de correo)
     * @param cedulaPersona codigo de la persona con el contacto
     * @return Verdadero si agrega el contacto exitosamente
     */
    public boolean crearContactoProveedor (TipoContacto tipo, String info, 
            String cedulaPersona) {
        return ctrContacto.crearContacto(cedulaPersona, tipo, info);
    }
    
    /**
     * Inactivar el contacto.
     * @param codigo Código de BD del contacto.
     * @return Verdadero si la inactivación fue exitosa.
     */
    public boolean inactivarContacto(String codigo) {
        return ctrContacto.inactivarContacto(codigo);
    }
    
    /**
     * Obtener la lista de proveedores.
     * @return La lista con todos los proveedores.
     */
    public ArrayList<Proveedor> getProveedores() {
        return proveedores;
    }
}
