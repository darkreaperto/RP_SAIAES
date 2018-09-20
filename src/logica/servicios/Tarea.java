/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica.servicios;

import java.util.ArrayList;
import logica.negocio.Usuario;
import util.EstadoTarea;
import util.Modulo;
import util.Panel;

/**
 *
 * @author dark-reaper
 */
public class Tarea {

    private Usuario usuario;
    private EstadoTarea estado;
    private Modulo modulo;
    private Panel panel;
    private ArrayList<Object> objetos;

    /**
     * Crear una nueva tarea.
     * @param usuario usuario que inicia la tarea
     * @param estado estado de la tarea
     * @param modulo módulo en edición
     * @param panel panel en edición
     * @param objetos objetos del panel
     */
    public Tarea(Usuario usuario, EstadoTarea estado, Modulo modulo,
            Panel panel, ArrayList<Object> objetos) {
        this.usuario = usuario;
        this.estado = estado;
        this.modulo = modulo;
        this.panel = panel;
        this.objetos = objetos;
    }

    /**
     * Obtener el usuario que inicia la tarea.
     * @return el usuario que inicia la tarea
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * Establecer el usuario que inicia la tarea
     * @param usuario el usuario a establecer
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * Obtener el estado de la tarea.
     * @return el estado de la tarea
     */
    public EstadoTarea getEstado() {
        return estado;
    }

    /**
     * Establecer el estado de la tarea.
     * @param estado el estado a establecer
     */
    public void setEstado(EstadoTarea estado) {
        this.estado = estado;
    }

    /**
     * Obtener el módulo de la tarea.
     * @return el módulo de la tarea
     */
    public Modulo getModulo() {
        return modulo;
    }

    /**
     * Establecer el módulo de la tarea.
     * @param modulo el modulo a establecer
     */
    public void setModulo(Modulo modulo) {
        this.modulo = modulo;
    }

    /**
     * Obtener el panel de la tarea.
     * @return el panel del módulo
     */
    public Panel getPanel() {
        return panel;
    }

    /**
     * Establecer el panel de la tarea.
     * @param panel el panel a establecer
     */
    public void setPanel(Panel panel) {
        this.panel = panel;
    }

    /**
     * Obtener los objetos del panel del módulo de la tarea.
     * @return los objetos en edición en el panel del módulo
     */
    public ArrayList<Object> getObjetos() {
        return objetos;
    }

    /**
     * Establecer los objetos del panel del módulo de la tarea.
     * @param objetos los objetos a establecer
     */
    public void setObjetos(ArrayList<Object> objetos) {
        this.objetos = objetos;
    }
}
