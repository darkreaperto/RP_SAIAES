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
import java.util.ArrayList;
import logica.negocio.Cliente;
import logica.negocio.Contacto;
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
    
    /**
     * Constructor de clase modelo de cliente.
     */
    public MdlCliente() {
        conexion = new CtrConexion();
        crypter = new AESEncrypt();        
        crypter.addKey("SAI");
        msgError = new Mensaje();
        ctrContacto = CtrContacto.getInstancia();
    }

    /**
     * Llena una lista con todos los clientes almacenados en la BD.
     *
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
            String codCliente;
            String estadoCliente;
            ArrayList<Contacto> contactos;

            while (resultado.next()) {
                
                codPersona = resultado.getString("cod_Personas");
                nombrePersona = resultado.getString("nom_Personas");
                apellido1Persona = resultado.getString("apellido1_Personas");
                apellido2Persona = resultado.getString("apellido2_Personas");
                cedulaPersona = resultado.getString("ced_Personas");
                limiteCredPersona = resultado.getFloat("limCred_Personas");
                
                aprobarCredPersona = resultado.getInt("aprobCred_Personas") == 1;
                codCliente = resultado.getString("cod_Clientes");
                estadoCliente = resultado.getString("estado_Clientes");
                
                contactos = ctrContacto.consultarContactos(codPersona);
                
                Cliente usuario
                        = new Cliente(codPersona, nombrePersona, 
                                apellido1Persona, apellido2Persona, 
                                cedulaPersona, limiteCredPersona, 
                                aprobarCredPersona, contactos, codCliente, 
                                estadoCliente);

                if (!clientes.contains(usuario)) {
                    clientes.add(usuario);
                }
            }
        } catch (SQLException ex) {
            System.err.println(ex);
        } finally {
            conexion.cerrarConexion();
            return clientes;
        }
    }

    /**
     * Inserta un nuevo cliente en la BD.
     * @param nombre
     * @param apellido1
     * @param apellido2
     * @param cedula
     * @param limiteCred
     * @param aprobarCred
     * @param contactos
     * @return 
     */
    public boolean crearCliente(String nombre, String apellido1, 
            String apellido2, String cedula, float limiteCred, 
            boolean aprobarCred, ArrayList<ArrayList<Object>> contactos) {

        ArrayList<Object> params = new ArrayList<>();
        params.add(nombre);
        params.add(apellido1);
        params.add(apellido2);
        params.add(cedula);
        params.add(limiteCred);
        int aprobar = aprobarCred ? 1 : 0;
        params.add(aprobar);

        boolean creacionExitosa = true;
        try {
            procedimiento = "pc_crear_cliente(?, ?, ?, ?, ?, ?, ?)";

            conexion.abrirConexion();
            resultado = conexion.ejecutarProcedimiento(procedimiento, params);
            
            String indice = "0";
            //obtener el índice de la fila insertada
            while (resultado.next()) {
                indice = String.valueOf(resultado.getInt("@indice"));
            }
            
            for (int i = 0; i < contactos.size(); i++) {
                TipoContacto tipo = (TipoContacto) contactos.get(i).get(0);
                String info = contactos.get(i).get(1).toString();
                
                params.clear();
                params.add(info);
                params.add(indice);
                params.add(tipo);
                
                ctrContacto.crearContacto(info, indice, tipo);

                //procedimiento = "pc_crear_contacto(?, ?, ?)";
                //resultado = conexion.ejecutarProcedimiento(procedimiento, params);
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
            String apellido2, String cedula, float limiteCred, 
            boolean aprobarCred, ArrayList<Contacto> contactos, Estado estado,
            String codPersona, String codigo) {
        
        String varEstado = estado.equals(Estado.Activo) ? "A" : "I";

        ArrayList<Object> params = new ArrayList<>();
        params.add(nombre);
        params.add(apellido1);
        params.add(apellido2);
        params.add(cedula);
        params.add(limiteCred);
        params.add(aprobarCred ? 1 : 0);
        params.add(varEstado);
        params.add(codPersona);
        params.add(codigo);

        boolean creacionExitosa = false;
        try {
            procedimiento = "pc_actualizar_cliente(?, ?, ?, ?, ?, ?, ?, ?, ?)";

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

                if (!clientes.contains(cliente)) {
                    clientes.add(cliente);
                }
            }
        } catch (SQLException ex) {
            System.err.println(ex);
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