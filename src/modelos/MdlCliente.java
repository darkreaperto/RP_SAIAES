/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import controladores.CtrConexion;
import java.util.ArrayList;
import logica.negocio.Cliente;

/**
 * Modelo de cliente con los procedimientos y consultas de base de datos
 * @author ahoihanabi
 */
public class MdlCliente {
    private static CtrConexion conexion;
    private static ArrayList<Cliente> usuarios;

    /**
     * Constructor de clase modelo de cliente.
     */
    public MdlCliente() {
        conexion = new CtrConexion();
    }
    
    
}
