/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import java.util.ArrayList;
import logica.negocio.LineaPedido;
import modelos.MdlLineaPedido;

/**
 * Controlador de la clase linea de pedido.
 * @author aoihanabi
 */
public class CtrLineaPedido {
    private static CtrLineaPedido instancia = null;
    MdlLineaPedido mdlLineaPedido;
    LineaPedido lineaPedido;
    ArrayList <LineaPedido> lineasPedido;

    /**
     * Constructor del controlador de línea de pedido, inicializa variables.
     */
    public CtrLineaPedido() {
        lineasPedido = new ArrayList<>();
        mdlLineaPedido = new MdlLineaPedido();
    }
    
    /**
     * Obtener instancia única del controlador de línea de pedido.
     * @return Instancia única de línea de pedido
     */
    public static CtrLineaPedido getInstancia() {
        return  instancia == null ? new CtrLineaPedido() : instancia;
    }
}
