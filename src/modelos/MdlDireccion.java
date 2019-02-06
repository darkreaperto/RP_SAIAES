/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import controladores.CtrConexion;
import controladores.CtrDireccion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import logica.negocio.Direccion;
import logica.servicios.Mensaje;

/**
 *
 * @author aoihanabi
 */
public class MdlDireccion {
    private static CtrConexion conexion;
    private static ResultSet resultado;
    private static Mensaje msgError;
    private static CtrDireccion ctrDireccion;
    private static Direccion direccion;
    private static String procedimiento;
    
    public MdlDireccion() {
        conexion = new CtrConexion();
        msgError = new Mensaje();
        ctrDireccion = CtrDireccion.getInstancia();
    }
    
    /**
     * Buscar contacto enviando por parámetro el criterio de búsqueda.
     * @param param Parametros para consultar contacto en la base de datos
     * @return lista de contactos
     */
    public Direccion consultarDireccion(String param) {
        
        Direccion dir = new Direccion();
        ArrayList<Object> params = new ArrayList<>();
        params.add(param);
        
        try {
            procedimiento = "pc_consultar_direcciones(?)";
            conexion.abrirConexion();
            resultado = conexion.ejecutarProcedimiento(procedimiento, params);

            int codDireccion;
            String idProvincia;
            String nomProvincia;
            String idCanton;
            String nomCanton;
            String idDistrito;
            String nomDistrito;
            String idBarrio;
            String nomBarrio;            
            String otrasSenas;

            while (resultado.next()) {
                codDireccion = resultado.getInt("cod_Direcciones");
                idProvincia = resultado.getString("idProvincia");
                nomProvincia = resultado.getString("nombreProvincia");
                idCanton = resultado.getString("idCanton");
                nomCanton = resultado.getString("nombreCanton");
                idDistrito = resultado.getString("idDistrito");
                nomDistrito = resultado.getString("nombreDistrito");
                idBarrio = resultado.getString("idBarrio");
                nomBarrio = resultado.getString("nombreBarrio");
                otrasSenas = resultado.getString("otrasSenas_Direcciones");

                
                    dir = new Direccion(codDireccion, idProvincia, 
                                nomProvincia, idCanton, nomCanton, idDistrito, nomDistrito,
                                idBarrio, nomBarrio, otrasSenas);
            }
        } catch (SQLException ex) {
            System.err.println(ex);
        } finally {
            conexion.cerrarConexion();
            return dir;
        }
    }
    /**
     * Llena una lista con todos los clientes almacenados en la BD.
     * @return lista de clientes.
     */
//    public ArrayList<Direccion> obtenerDirecciones() {
//        clientes = new ArrayList<>();
//
//        try {
//            procedimiento = "pc_obtener_clientes()";
//            conexion.abrirConexion();
//            resultado = conexion.ejecutarProcedimiento(procedimiento);
//
//            String codPersona;
//            String nombrePersona;
//            String apellido1Persona;
//            String apellido2Persona;
//            String cedulaPersona;
//            float limiteCredPersona;
//            boolean aprobarCredPersona;
//            String codCliente;
//            String estadoCliente;
//            ArrayList<Contacto> contactos;
//
//            while (resultado.next()) {
//                
//                codPersona = resultado.getString("cod_Personas");
//                nombrePersona = resultado.getString("nom_Personas");
//                apellido1Persona = resultado.getString("apellido1_Personas");
//                apellido2Persona = resultado.getString("apellido2_Personas");
//                cedulaPersona = resultado.getString("ced_Personas");
//                limiteCredPersona = resultado.getFloat("limCred_Personas");
//                
//                aprobarCredPersona = resultado.getInt("aprobCred_Personas") == 1;
//                codCliente = resultado.getString("cod_Clientes");
//                estadoCliente = resultado.getString("estado_Clientes");
//                
//                contactos = ctrContacto.consultarContactos(codPersona);
//                
//                Cliente usuario
//                        = new Cliente(codPersona, nombrePersona, 
//                                apellido1Persona, apellido2Persona, 
//                                cedulaPersona, limiteCredPersona, 
//                                aprobarCredPersona, contactos, codCliente, 
//                                estadoCliente);
//
//                if (!clientes.contains(usuario)) {
//                    clientes.add(usuario);
//                }
//            }
//        } catch (SQLException ex) {
//            System.err.println(ex);
//        } finally {
//            conexion.cerrarConexion();
//            return clientes;
//        }
//    }
   
}
