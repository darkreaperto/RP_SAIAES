/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import java.util.ArrayList;
import logica.negocio.Usuario;
import logica.servicios.Tarea;
import util.EstadoTarea;
import util.Modulo;
import util.Panel;

/**
 *
 * @author dark-reaper
 */
public class CtrTarea {
    
    private static ArrayList<Tarea> tareas;
    
    /**
     * Constructor del controlador de tareas.
     */
    public CtrTarea() {
        tareas = new ArrayList<>();
    }
    
    /**
     * Iniciar una nueva tarea, crear si no existe.
     * @param usuario usuario en sesión
     * @param modulo módulo en edición
     * @param panel panel en edición
     * @param objetos objetos del panel
     */
    public void iniciarTarea(Usuario usuario, Modulo modulo, Panel panel, 
            ArrayList<Object> objetos) {
        
        Tarea tarea = encontrarTarea(modulo, panel);
        if (tarea == null) {
            tareas.add(new Tarea(usuario, EstadoTarea.INICIADA, modulo, panel,
                objetos));
        } else {
            tarea.setUsuario(usuario);
            tarea.setEstado(EstadoTarea.INICIADA);
        }
    }
    
    /**
     * Encontrar la tarea según el módulo y panel especificados.
     * @param modulo módulo en edición
     * @param panel panel en edición
     * @return Tarea encontrada, null en caso contrario
     */
    public Tarea encontrarTarea(Modulo modulo, Panel panel) {
        for (Tarea t: tareas) {
            if (t.getModulo().equals(modulo) && t.getPanel().equals(panel)) {
                return t;
            }
        }
        return null;
    }

    /**
     * Obtener el usuario que inició la tarea.
     * @param modulo modulo de procedencia en la interfaz
     * @param panel panel de procedencia en la interfaz
     * @return el usuario en sesión
     */
    public Usuario getUsuario(Modulo modulo, Panel panel) {
        return encontrarTarea(modulo, panel).getUsuario();
    }

    /**
     * Establecer el usuario para la tarea.
     * @param usuario el usuario a establecer
     * @param modulo modulo de procedencia en la interfaz
     * @param panel panel de procedencia en la interfaz
     */
    public void setUsuario(Usuario usuario, Modulo modulo, Panel panel) {
        encontrarTarea(modulo, panel).setUsuario(usuario);
    }

    /**
     * Obtener el estado de la tarea en el módulo y panel especificado.
     * @param modulo modulo de procedencia en la interfaz
     * @param panel panel de procedencia en la interfaz
     * @return el estado de la tarea
     */
    public EstadoTarea getEstado(Modulo modulo, Panel panel) {
        return encontrarTarea(modulo, panel).getEstado();
    }

    /**
     * Establecer el estado de la tarea en el módulo y panel especificado.
     * @param estado el estado a establecer
     * @param modulo módulo de la tarea
     * @param panel panel de la tarea
     */
    public void setEstado(EstadoTarea estado, Modulo modulo, Panel panel) {
        encontrarTarea(modulo, panel).setEstado(estado);
    }

    /**
     * Obtener los objetos editables/seleccionables del panel en el módulo.
     * @param modulo en edición
     * @param panel en edición
     * @return los objetos del panel
     */
    public ArrayList<Object> getObjetos(Modulo modulo, Panel panel) {
        return encontrarTarea(modulo, panel).getObjetos();
    }

    /**
     * Establecer los objetos editables/seleccionables del panel en el módulo.
     * @param objetos los objetos a establecer
     * @param modulo módulo en edición
     * @param panel panel en edición
     */
    public void setObjetos(ArrayList<Object> objetos, Modulo modulo, 
            Panel panel) {
        encontrarTarea(modulo, panel).setObjetos(objetos);
    }
}
