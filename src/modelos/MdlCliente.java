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
import logica.servicios.Logger;
import logica.servicios.Mensaje;
import util.Estado;
import util.TipoCedula;
import util.TipoContacto;

/**
 * Modelo de cliente con los procedimientos y consultas de base de datos
 * @author ahoihanabi
 */
public class MdlCliente {
    
    /** Controlador de conexión. */
    private static CtrConexion conexion;
    /** Variable para encriptar datos. */
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

            String cedulaPersona;
            String tipoCedPersonas;
            String nombrePersona;
            String codDireccion;
            Direccion dirPersona;
            double limiteCredPersona;
            boolean aprobarCredPersona;
            String estadoCliente;
            ArrayList<Contacto> contactos;

            while (resultado.next()) {
                
                cedulaPersona = resultado.getString("ced_Personas");
                tipoCedPersonas = resultado.getString("tipoCed_Personas");
                nombrePersona = resultado.getString("nom_Personas");
                codDireccion = resultado.getString("codDireccion_Personas");
                limiteCredPersona = resultado.getDouble("limCred_Clientes");
                aprobarCredPersona = resultado.getBoolean("aprobCred_Clientes");
                estadoCliente = resultado.getString("estado_Clientes");
                
                contactos = ctrContacto.consultarContactos(cedulaPersona);
                System.out.println("HERE: " + codDireccion);
                if(codDireccion == null) {
                    dirPersona = null;
                } else {
                    dirPersona = ctrDireccion.consultarDireccion(codDireccion);
                }
                
                Cliente cliente
                        = new Cliente(cedulaPersona, tipoCedPersonas, 
                                nombrePersona, dirPersona, limiteCredPersona, 
                                aprobarCredPersona, estadoCliente, contactos);

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
    
    /**
     * Inserta un nuevo cliente en la BD.
     * @param cedula cédula del cliente.
     * @param tipoCed tipo de cédula del cliente.
     * @param nombre nombre del cliente.
     * @param dir dirección del cliente.
     * @param limiteCred límite de crédito del cliente.
     * @param aprobarCred aprobar crédito.
     * @param contactos contactos del cliente.
     * @return verdadero si la inserción fue éxitosa.
     */
    public boolean crearCliente(String cedula, String tipoCed, String nombre, 
            Direccion dir, double limiteCred, boolean aprobarCred, 
            ArrayList<ArrayList<Object>> contactos) {
        
        int codDireccion = 0;
        if(dir != null) {
            codDireccion = ctrDireccion.crearDireccion(dir.getCodProvincia(), 
                dir.getCodCanton(), dir.getCodDistrito(), dir.getCodBarrio(), 
                dir.getOtrasSenas());
        }
        
        ArrayList<Object> params = new ArrayList<>();
        params.add(cedula);
        params.add(tipoCed);
        params.add(nombre);
        params.add(codDireccion);
        params.add(limiteCred);
        params.add(aprobarCred);
        
        boolean creacionExitosa = false;
        try {
            procedimiento = "pc_crear_cliente(?, ?, ?, ?, ?, ?)";

            conexion.abrirConexion();
            resultado = conexion.ejecutarProcedimiento(procedimiento, params);
            
            /*System.out.println("RES CED: "+resultado.next());
            
            String pCedula = "";
            //obtener el índice de la fila insertada
            while (resultado.next()) {
                pCedula = resultado.getString("cedula");
            }*/
            
            
            
            for (int i = 0; i < contactos.size(); i++) {
                TipoContacto tipo = (TipoContacto) contactos.get(i).get(0);
                String info = contactos.get(i).get(1).toString();
                
                ctrContacto.crearContacto(cedula, tipo, info);
                //ctrContacto.crearContacto("INUIB", tipo, info);
            }
            
            creacionExitosa = true;
            System.out.println(resultado);
        } catch (SQLException ex) {
            System.err.println(ex);   
            ex.printStackTrace();            
            System.out.println("ERROR SQL " + ex.getErrorCode());
            msgError.mostrarMensajeErrorSQL(ex.getErrorCode());
            
            Logger.registerNewError(ex);
        } finally {
            conexion.cerrarConexion();
            return creacionExitosa;
        }
    }
    
    /**
     * Actualiza toda la información del cliente en la BD.
     * @param cedula cédula del cliente.
     * @param nombre nombre del cliente.
     * @param dir dirección del cliente.
     * @param limiteCred lìmite de crèdito del cliente.
     * @param aprobarCred aprobar crédito.
     * @return verdadero si la actualización fue éxitosa.
     */
    public boolean actualizarCliente(String cedula, String nombre, 
            Direccion dir, double limiteCred, boolean aprobarCred) {
        
        ArrayList<Object> params = new ArrayList<>();
        params.add(cedula);
        params.add(nombre);
        
        boolean creacionExitosa = false;
        try {
            int codDir = 0;
            if(dir != null) {
                if(dir.getCodigo() == 0) {
                    codDir = ctrDireccion.crearDireccion(dir.getCodProvincia(), 
                            dir.getCodCanton(), dir.getCodDistrito(), 
                            dir.getCodBarrio(), dir.getOtrasSenas());
                } else {
                    ctrDireccion.actualizarDireccion(dir.getCodProvincia(), 
                            dir.getCodCanton(), dir.getCodDistrito(), 
                            dir.getCodBarrio(), dir.getOtrasSenas(), 
                            dir.getCodigo());
                }
            }
            params.add(codDir);
            params.add(limiteCred);
            params.add(aprobarCred);
            
            procedimiento = "pc_actualizar_cliente(?, ?, ?, ?, ?)";

            conexion.abrirConexion();
            resultado = conexion.ejecutarProcedimiento(procedimiento, params);
            creacionExitosa = true;

        } catch (SQLException ex) {
            System.err.println(ex);
            ex.printStackTrace();
        } catch (Exception ex) {
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
     * @return verdadero si la inactivación fue éxitosa.
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
     * @return verdadero si la activación fue éxitosa.
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

            String cedula;
            String tipoCed;
            String nombre;
            String codDireccion;
            Direccion direccion;
            double limiteCred;
            boolean aprobarCred;
            String estadoCliente;

            while (resultado.next()) {
                cedula = resultado.getString("ced_Personas");
                tipoCed = resultado.getString("tipoCed_Personas");
                nombre = resultado.getString("nom_Personas");
                codDireccion = resultado.getString("codDireccion_Personas");
                limiteCred = resultado.getFloat("limCred_Clientes");
                aprobarCred = resultado.getInt("aprobCred_Clientes") == 1;
                estadoCliente = resultado.getString("estado_Clientes");

                ArrayList<Contacto> contactos = ctrContacto.consultarContactos(cedula);
                direccion = ctrDireccion.consultarDireccion(codDireccion);
                
                Cliente cliente
                        = new Cliente(cedula, tipoCed, nombre, direccion,
                                limiteCred, aprobarCred, estadoCliente, 
                                contactos);

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
    
    public boolean crearContacto(String info, String cedPersona, TipoContacto tipo) {
        return ctrContacto.crearContacto(cedPersona, tipo, info);
    }
    
    public boolean actualizarContacto(String info, String cedPersona, 
            TipoContacto tipo, Estado estado, String codigo) {
        return ctrContacto.actualizarContacto(info, cedPersona, tipo, estado, codigo);
    }
    
    public ArrayList<Contacto> consultarContactos(String param) {
        return ctrContacto.consultarContactos(param);
    }
    
    public boolean inactivarContacto(String codigo) {
        return ctrContacto.inactivarContacto(codigo);
    }
    
    /**
     * Obtener la lista de clientes en memoria.
     * @return los clientes.
     */
    public ArrayList<Cliente> getClientes() {
        return clientes;
    }
}
