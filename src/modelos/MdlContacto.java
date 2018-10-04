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
import logica.negocio.Contacto;
import logica.servicios.AESEncrypt;
import logica.servicios.Mensaje;
import util.TipoContacto;
import util.Estado;

/**
 *
 * @author dark-reaper
 */
public class MdlContacto {
    
    /** Controlador de conexión. */
    private static CtrConexion conexion;
    /** Variable para encriptar datos. */
    private static AESEncrypt crypter;
    /** Procedimiento a ejecutar en la base. */
    private static String procedimiento;
    /** Resultado de las consultas a la base. */
    private static ResultSet resultado;
    /** Lista de contactos en la base. */
    private static ArrayList<Contacto> contactos;
    /** Mensaje de error desde la base. */
    private static Mensaje msgError;

    /**
     * Constructor de clase modelo de contacto.
     */
    public MdlContacto() {
        conexion = new CtrConexion();
        crypter = new AESEncrypt();        
        crypter.addKey("SAI");
        msgError = new Mensaje();
    }

    /**
     * Llena una lista con todos los contactos almacenados en la BD.
     *
     * @return lista de contactos.
     */
    public ArrayList<Contacto> obtenerContactos() {
        contactos = new ArrayList<>();

        try {
            procedimiento = "pc_obtener_contactos()";
            conexion.abrirConexion();
            resultado = conexion.ejecutarProcedimiento(procedimiento);

            String codContacto;
            String infoContacto;
            String codPersona;
            String codTipoContacto;
            String estadoContacto;

            while (resultado.next()) {
                codContacto = resultado.getString("cod_Contactos");
                infoContacto = resultado.getString("info_Contactos");
                codPersona = resultado.getString("cod_Personas");
                codTipoContacto = resultado.getString("codigo_TipoContactos");
                estadoContacto = resultado.getString("estado_Contactos");

                Contacto contacto
                        = new Contacto(codContacto, infoContacto, 
                                codTipoContacto, estadoContacto);

                if (!contactos.contains(contacto)) {
                    contactos.add(contacto);
                }
            }
        } catch (SQLException ex) {
            System.err.println(ex);
        } finally {
            conexion.cerrarConexion();
            return contactos;
        }
    }

    /**
     * Inserta un nuevo contacto en la BD
     *
     * @param info nueva información del contacto
     * @param codPersona persona a quien pertenece el contacto
     * @param tipo tipo de contacto de la persona
     * @return true si inserta el contacto.
     */
    public boolean crearContacto(String info, String codPersona, 
            TipoContacto tipo) {

        //Código de tipo de contacto. 1: CORREO, 2: TELËFONO
        int codTipo = tipo.equals(TipoContacto.CORREO) ? 1 : 2;
        int codPer = Integer.valueOf(codPersona);
        
        ArrayList<Object> params = new ArrayList<>();
        params.add(info);
        params.add(codPer);
        params.add(codTipo);

        boolean creacionExitosa = true;
        try {
            procedimiento = "pc_crear_contacto(?, ?, ?)";

            conexion.abrirConexion();
            resultado = conexion.ejecutarProcedimiento(procedimiento, params);
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
     * Actualiza toda la información del contacto en la BD.
     * @param info Infor del contacto
     * @param codPersona Código de persona
     * @param tipo Tipo de contacto
     * @param estado Estado del contacto
     * @param codigo Codigo para identificar el contacto
     * @return true si actualiza.
     */
    public boolean actualizarContacto(String info, String codPersona, 
            TipoContacto tipo, Estado estado, String codigo) {
        
        int codTipo = tipo.equals(TipoContacto.CORREO) ? 1 : 2;
        String varEstado = estado.equals(Estado.Activo) ? "A" : "I";

        ArrayList<Object> params = new ArrayList<>();
        params.add(info);
        params.add(codPersona);
        params.add(codTipo);
        params.add(varEstado);
        params.add(codigo);

        boolean creacionExitosa = false;
        try {
            procedimiento = "pc_actualizar_contacto(?, ?, ?, ?, ?)";

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
     * Buscar contacto enviando por parámetro el criterio de búsqueda.
     * @param param Parametros para consultar contacto en la base de datos
     * @return lista de contactos
     */
    public ArrayList consultarContactos(String param) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(param);

        contactos = new ArrayList<>();

        try {
            procedimiento = "pc_consultar_contactos(?)";
            conexion.abrirConexion();
            resultado = conexion.ejecutarProcedimiento(procedimiento, params);

            String codContacto;
            String infoContacto;
            String codTipoContacto;
            String estadoContacto;

            while (resultado.next()) {
                codContacto = resultado.getString("cod_Contactos");
                infoContacto = resultado.getString("info_Contactos");
                codTipoContacto = resultado.getString("codigo_TipoContactos");
                estadoContacto = resultado.getString("estado_Contactos");

                Contacto contacto
                        = new Contacto(codContacto, infoContacto, 
                                codTipoContacto, estadoContacto);

                if (!contactos.contains(contacto)) {
                    contactos.add(contacto);
                }
            }
        } catch (SQLException ex) {
            System.err.println(ex);
        } finally {
            conexion.cerrarConexion();
            return contactos;
        }
    }
}
