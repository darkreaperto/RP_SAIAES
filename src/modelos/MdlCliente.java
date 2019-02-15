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
import logica.negocio.Cliente;
import logica.negocio.Contacto;
import logica.negocio.Direccion;
import logica.servicios.AESEncrypt;
import logica.servicios.Mensaje;
import util.Estado;
import util.TipoContacto;

/**
 * Modelo de cliente con los procedimientos y consultas de base de datos
 * @author ahoihanabi
 */
public class MdlCliente {
    
    /** Controlador de conexión. */
    private static CtrConexion conexion;
    /** Variable para encriptar datos. */
    private static AESEncrypt crypter;
    /** Procedimiento a ejecutar en la base. */
    private static String procedimiento;
    /** Resultado de las consultas a la base. */
    private static ResultSet resultado;
    /** Lista de clientes en la base. */
    private static ArrayList<Cliente> clientes;
    /** Mensaje de error desde la base. */
    private static Mensaje msgError;
    /** Controlador de contacto. */
    private static CtrContacto ctrContacto;
    private static CtrDireccion ctrDireccion;
    
    /**
     * Constructor de clase modelo de cliente.
     */
    public MdlCliente() {
        conexion = new CtrConexion();
        crypter = new AESEncrypt();        
        crypter.addKey("SAI");
        msgError = new Mensaje();
        ctrContacto = CtrContacto.getInstancia();
        ctrDireccion = CtrDireccion.getInstancia();
    }

    /**
     * Llena una lista con todos los clientes almacenados en la BD.
     * @return lista de clientes.
     */
    public ArrayList<Cliente> obtenerClientes() {
        clientes = new ArrayList<>();

        try {
            procedimiento = "pc_obtener_clientes()";
            conexion.abrirConexion();
            resultado = conexion.ejecutarProcedimiento(procedimiento);

            String codPersona;
            String nombrePersona;
            String apellido1Persona;
            String apellido2Persona;
            String cedulaPersona;
            float limiteCredPersona;
            boolean aprobarCredPersona;
            String codDireccion;
            String codCliente;
            String estadoCliente;
            ArrayList<Contacto> contactos;
            Direccion dirPersona;

            while (resultado.next()) {
                
                codPersona = resultado.getString("cod_Personas");
                nombrePersona = resultado.getString("nom_Personas");
                apellido1Persona = resultado.getString("apellido1_Personas");
                apellido2Persona = resultado.getString("apellido2_Personas");
                cedulaPersona = resultado.getString("ced_Personas");
                limiteCredPersona = resultado.getFloat("limCred_Personas");
                aprobarCredPersona = resultado.getInt("aprobCred_Personas") == 1;
                codDireccion = resultado.getString("codDireccion_Personas");
                codCliente = resultado.getString("cod_Clientes");
                estadoCliente = resultado.getString("estado_Clientes");
                
                contactos = ctrContacto.consultarContactos(codPersona);
                System.out.println("HERE: " + codDireccion);
                if(codDireccion == null) {
                    dirPersona = null;
                } else {
                    dirPersona = ctrDireccion.consultarDireccion(codDireccion);
                }
                
                Cliente usuario
                        = new Cliente(codPersona, nombrePersona, 
                                apellido1Persona, apellido2Persona, 
                                cedulaPersona, limiteCredPersona, 
                                aprobarCredPersona, dirPersona, contactos, 
                                codCliente, estadoCliente);

                if (!clientes.contains(usuario)) {
                    clientes.add(usuario);
                }
            }
        } catch (SQLException ex) {
            System.err.println(ex);
            ex.printStackTrace();
        } finally {
            conexion.cerrarConexion();
            return clientes;
        }
    }
    
    public ArrayList<Contacto> obtenerContactos(String codPersona) {
        return ctrContacto.consultarContactos(codPersona);
    }

    /**
     * Inserta un nuevo cliente en la BD.
     * @param nombre
     * @param apellido1
     * @param apellido2
     * @param cedula
     * @param limiteCred
     * @param aprobarCred
     * @param dir
     * @param contactos
     * @return 
     */
    public boolean crearCliente(String nombre, String apellido1, 
            String apellido2, String cedula, double limiteCred, 
            boolean aprobarCred, Direccion dir, ArrayList<ArrayList<Object>> contactos) {
        
        int codDireccion;
        if(dir != null) {
            codDireccion = ctrDireccion.crearDireccion(dir.getCodProvincia(), 
                dir.getCodCanton(), dir.getCodDistrito(), dir.getCodBarrio(), 
                dir.getOtrasSenas());
        } else {
            codDireccion = 0;
        }
        
        ArrayList<Object> params = new ArrayList<>();
        params.add(nombre);
        params.add(apellido1);
        params.add(apellido2);
        params.add(cedula);
        params.add(limiteCred);
        int aprobar = aprobarCred ? 1 : 0;
        params.add(aprobar);
        params.add(codDireccion);
        params.add(Types.BIGINT);

        boolean creacionExitosa = true;
        try {
            procedimiento = "pc_crear_cliente(?, ?, ?, ?, ?, ?, ?, ?)";

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
    
    public boolean crearContacto(TipoContacto tipo, String info, String codPersona) {
        return ctrContacto.crearContacto(info, codPersona, tipo);
    }
    
    public boolean inactivarContacto(String codigo) {
        return ctrContacto.inactivarContacto(codigo);
    }

    /**
     * Actualiza toda la información del cliente en la BD.
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
    public boolean actualizarCliente(String nombre, String apellido1, 
            String apellido2, String cedula, double limiteCred, 
            boolean aprobarCred, String codPersona, ) {
        
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
            ex.printStackTrace();
        } finally {
            conexion.cerrarConexion();
            return creacionExitosa;
        }
    }
    
    /**
     * Inactiva el cliente en la bd.
     * @param cedula cédula unívoca del cliente
     * @return 
     */
    public boolean inactivarCliente(String cedula) {
        
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
            ex.printStackTrace();
        } finally {
            conexion.cerrarConexion();
            return creacionExitosa;
        }
    }
    
    /**
     * Activa el cliente en la bd.
     * @param cedula cédula unívoca del cliente
     * @return 
     */
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
            ex.printStackTrace();
        } finally {
            conexion.cerrarConexion();
            return creacionExitosa;
        }
    }

    /**
     * Buscar usuario enviando por parámetro el criterio de búsqueda.
     * @param param Parametros para consultar usuario en la base de datos
     * @return lista de clientes
     */
    public ArrayList consultarClientes(String param) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(param);
        
        clientes = new ArrayList<>();
        
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
            String codDireccion;
            String estadoCliente;
            Direccion direccion;

            while (resultado.next()) {
                codPersona = resultado.getString("cod_Personas");
                nombre = resultado.getString("nom_Personas");
                apellido1 = resultado.getString("apellido1_Personas");
                apellido2 = resultado.getString("apellido2_Personas");
                cedula = resultado.getString("ced_Personas");
                limiteCred = resultado.getFloat("limCred_Personas");
                aprobarCred = resultado.getInt("aprobCred_Personas") == 1;
                codDireccion = resultado.getString("codDireccion_Personas");
                codCliente = resultado.getString("cod_Clientes");
                estadoCliente = resultado.getString("estado_Clientes");

                ArrayList<Contacto> contactos = ctrContacto.consultarContactos(codPersona);
                direccion = ctrDireccion.consultarDireccion(codDireccion);
                
                Cliente cliente
                        = new Cliente(codPersona, nombre, apellido1, apellido2, 
                                cedula, limiteCred, aprobarCred, direccion,
                                contactos, codCliente, estadoCliente);

                if (!clientes.contains(cliente)) {
                    clientes.add(cliente);
                }
            }
        } catch (SQLException ex) {
            System.err.println(ex);
            ex.printStackTrace();
        } finally {
            conexion.cerrarConexion();
            return clientes;
        }
    }
    
    public void agregarCliente(String nombre, String apellido1, 
            String apellido2, String cedula, float limiteCred, 
            boolean aprobarCred, ArrayList<Contacto> contactos) {
        
    }
    
    public ArrayList<Cliente> getClientes() {
        return clientes;
    }
}
