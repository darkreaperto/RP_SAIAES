/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import controladores.CtrConexion;
import controladores.CtrContacto;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import logica.negocio.Cliente;
import logica.negocio.Contacto;
import logica.negocio.Proveedor;
import logica.servicios.Mensaje;
import util.TipoContacto;

/**
 *
 * @author aoihanabi
 */
public class MdlProveedor {
    /** Controlador de conexión. */
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
    
    /**
     * Constructor de clase modelo de proveedor.
     */
    public MdlProveedor() {
        conexion = new CtrConexion();
        msgError = new Mensaje();
        ctrContacto = CtrContacto.getInstancia();
    }

    /**
     * Llena una lista con todos los proveedores almacenados en la BD.
     *
     * @return lista de proveedores.
     */
    public ArrayList<Proveedor> obtenerProveedores() {
        proveedores = new ArrayList<>();

        try {
            procedimiento = "pc_obtener_proveedores()";
            conexion.abrirConexion();
            resultado = conexion.ejecutarProcedimiento(procedimiento);

            String codPersona;
            String nombrePersona;
            String apellido1Persona;
            String apellido2Persona;
            String cedulaPersona;
            String codProveedor;
            String estadoProveedor;
            ArrayList<Contacto> contactos;

            while (resultado.next()) {
                
                codPersona = resultado.getString("cod_Personas");
                nombrePersona = resultado.getString("nom_Personas");
                apellido1Persona = resultado.getString("apellido1_Personas");
                apellido2Persona = resultado.getString("apellido2_Personas");
                cedulaPersona = resultado.getString("ced_Personas");
                codProveedor = resultado.getString("cod_Proveedores");
                estadoProveedor = resultado.getString("estado_Proveedores");
                
                contactos = ctrContacto.consultarContactos(codPersona);
                
                Proveedor proveedor
                        = new Proveedor(codPersona, nombrePersona, 
                                apellido1Persona, apellido2Persona, 
                                cedulaPersona, contactos, codProveedor, 
                                estadoProveedor);

                if (!proveedores.contains(proveedor)) {
                    proveedores.add(proveedor);
                }
            }
        } catch (SQLException ex) {
            System.err.println(ex);
        } finally {
            conexion.cerrarConexion();
            return proveedores;
        }
    }
    
    public ArrayList<Contacto> obtenerContactos(String codPersona) {
        return ctrContacto.consultarContactos(codPersona);
    }

    /**
     * Inserta un nuevo proveedor en la BD.
     * @param nombre nombre del proveedor
     * @param apellido1 apellido 1 del proveedor
     * @param apellido2 apellido 2 del proveedor
     * @param cedula cedula del proveedor
     * @param limiteCred limite de crédito del proveedor
     * @param aprobarCred aprobación de crédito del proveedor
     * @param contactos contactos del proveedor
     * @return True si agrega el proveedor exitosamente
     */
    /*public boolean crearCliente(String nombre, String apellido1, 
            String apellido2, String cedula, double limiteCred, 
            boolean aprobarCred, ArrayList<ArrayList<Object>> contactos) {

        ArrayList<Object> params = new ArrayList<>();
        params.add(nombre);
        params.add(apellido1);
        params.add(apellido2);
        params.add(cedula);
        params.add(limiteCred);
        int aprobar = aprobarCred ? 1 : 0;
        params.add(aprobar);
        params.add(Types.BIGINT);

        boolean creacionExitosa = true;
        try {
            procedimiento = "pc_crear_cliente(?, ?, ?, ?, ?, ?, ?)";

            conexion.abrirConexion();
            resultado = conexion.ejecutarProcedimiento(procedimiento, params);
            
            int indice = 0;
            //obtener el índice de la fila insertada
            while (resultado.next()) {
                indice = resultado.getInt("@indice");
            }
            
            for (int i = 0; i < contactos.size(); i++) {
                TipoContacto tipo = (TipoContacto) contactos.get(i).get(0);
                String info = contactos.get(i).get(1).toString();
                
                params.clear();
                params.add(info);
                params.add(indice);
                params.add(tipo);
                
                ctrContacto.crearContacto(info, String.valueOf(indice), tipo);
            }
            
            //creacionExitosa = true;
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
    }*/
    
    /**
     * 
     * @param tipo tipo de contacto correo o telefono
     * @param info detalle del contacto
     * @param codPersona codigo de la persona con el contacto
     * @return True si agrega el contacto exitosamente
     */
    public boolean crearContacto(TipoContacto tipo, String info, String codPersona) {
        return ctrContacto.crearContacto(info, codPersona, tipo);
    }
    
    public boolean inactivarContacto(String codigo) {
        return ctrContacto.inactivarContacto(codigo);
    }

    /**
     * Actualiza toda la información del proveedor en la BD.
     * @param nombre
     * @param apellido1
     * @param apellido2
     * @param cedula
     * @param limiteCred
     * @param aprobarCred
     * @param contactos
     * @param estado
     * @param codPersona
     * @param codigo
     * @return 
     */
    
    /*
    public boolean actualizarCliente(String nombre, String apellido1, 
            String apellido2, String cedula, double limiteCred, 
            boolean aprobarCred, String codPersona) {
        
        ArrayList<Object> params = new ArrayList<>();
        params.add(nombre);
        params.add(apellido1);
        params.add(apellido2);
        params.add(cedula);
        params.add(limiteCred);
        params.add(aprobarCred ? 1 : 0);
        params.add(codPersona);

        boolean creacionExitosa = false;
        try {
            procedimiento = "pc_actualizar_cliente(?, ?, ?, ?, ?, ?, ?)";

            conexion.abrirConexion();
            resultado = conexion.ejecutarProcedimiento(procedimiento, params);
            creacionExitosa = true;

        } catch (SQLException ex) {
            creacionExitosa = false;
            System.err.println(ex);
        } finally {
            conexion.cerrarConexion();
            return creacionExitosa;
        }
    }*/
    
    /**
     * Inactiva el proveedor en la bd.
     * @param cedula cédula unívoca del proveedor
     * @return true si inactiva el proveedor
     */
    /*public boolean inactivarCliente(String cedula) {
        
        ArrayList<Object> params = new ArrayList<>();
        params.add(cedula);
        
        boolean creacionExitosa = false;
        try {
            procedimiento = "pc_inactivar_cliente(?)";

            conexion.abrirConexion();
            resultado = conexion.ejecutarProcedimiento(procedimiento, params);
            creacionExitosa = true;

        } catch (SQLException ex) {
            creacionExitosa = false;
            System.err.println(ex);
        } finally {
            conexion.cerrarConexion();
            return creacionExitosa;
        }
    }*/
    
    /**
     * Activa el proveedor en la bd.
     * @param cedula cédula unívoca del proveedor
     * @return 
     */
    /*
    public boolean activarCliente(String cedula) {
        
        ArrayList<Object> params = new ArrayList<>();
        params.add(cedula);
        
        boolean creacionExitosa = false;
        try {
            procedimiento = "pc_activar_cliente(?)";

            conexion.abrirConexion();
            resultado = conexion.ejecutarProcedimiento(procedimiento, params);
            creacionExitosa = true;

        } catch (SQLException ex) {
            creacionExitosa = false;
            System.err.println(ex);
        } finally {
            conexion.cerrarConexion();
            return creacionExitosa;
        }
    }*/

    
    /**
     * Buscar usuario enviando por parámetro el criterio de búsqueda.
     * @param param Parametros para consultar usuario en la base de datos
     * @return lista de proveedores
     */
    /*
    public ArrayList consultarClientes(String param) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(param);
        
        proveedores = new ArrayList<>();
        
        try {
            procedimiento = "pc_consultar_clientes(?)";
            conexion.abrirConexion();
            resultado = conexion.ejecutarProcedimiento(procedimiento, params);

            String codPersona;
            String nombre;
            String apellido1;
            String apellido2;
            String cedula;
            float limiteCred;
            boolean aprobarCred;
            String codCliente;
            String estadoCliente;

            while (resultado.next()) {
                codPersona = resultado.getString("cod_Personas");
                nombre = resultado.getString("nom_Personas");
                apellido1 = resultado.getString("apellido1_Personas");
                apellido2 = resultado.getString("apellido2_Personas");
                cedula = resultado.getString("ced_Personas");
                limiteCred = resultado.getFloat("limCred_Personas");
                aprobarCred = resultado.getInt("aprobCred_Personas") == 1;
                codCliente = resultado.getString("cod_Clientes");
                estadoCliente = resultado.getString("estado_Clientes");

                ArrayList<Contacto> contactos = ctrContacto.consultarContactos(codPersona);
                Cliente cliente
                        = new Cliente(codPersona, nombre, apellido1, apellido2, 
                                cedula, limiteCred, aprobarCred, contactos, 
                                codCliente, estadoCliente);

                if (!proveedores.contains(cliente)) {
                    proveedores.add(cliente);
                }
            }
        } catch (SQLException ex) {
            System.err.println(ex);
        } finally {
            conexion.cerrarConexion();
            return proveedores;
        }
    }*/
    
    /*
    public void agregarCliente(String nombre, String apellido1, 
            String apellido2, String cedula, float limiteCred, 
            boolean aprobarCred, ArrayList<Contacto> contactos) {
    }*/
    
    public ArrayList<Proveedor> getProveedores() {
        return proveedores;
    }

}