/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacion;

import com.sun.webkit.dom.RGBColorImpl;
import controladores.CtrAcceso;
import controladores.CtrMadera;
import controladores.CtrProveedor;
import controladores.CtrTipoMadera;
import controladores.CtrTroza;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListModel;
import javax.swing.table.DefaultTableModel;
import logica.negocio.Madera;
import logica.negocio.Proveedor;
import logica.negocio.TipoMadera;
import logica.negocio.Troza;
import logica.servicios.Mensaje;
import logica.servicios.Regex;
import logica.servicios.UI;
import util.Estado;
import util.TextPrompt;
import util.TipoMensaje;


/**
 * Inicializa la ventana que contiene la información de los productos.
 * 
 * @author ahoihanabi
 */
public class ItnFrmInventario extends javax.swing.JInternalFrame {

    private static ArrayList<Madera> productos;
    private static ArrayList<Troza> trozas;
    private static ArrayList<Proveedor> proveedores;
    private static ArrayList<TipoMadera> tmaderas;
    private static CtrAcceso sesion;
    private static CtrMadera ctrMadera;
    private static CtrTroza ctrTroza;
    private static CtrProveedor ctrProveedor;
    private static CtrTipoMadera ctrTipoMadera;
    private static DefaultTableModel model;
    private static ItnFrmInventario instancia = null;    
    private static Mensaje msg;
    private final Regex verificacion;
    private TextPrompt placeholder;
    private final UI estilo;

    /**
     * Instancia un nuevo formulario interno de clientes.
     *
     * @param sesionAcc Usuario en sesión actual
     * @param productos Lista con los productos en la base de datos
     */
    public ItnFrmInventario(CtrAcceso sesionAcc, ArrayList<Madera> productos) {
        initComponents();

        //Inicializar variables
        ctrMadera = CtrMadera.getInstancia();
        ctrTroza = CtrTroza.getInstancia();
        ItnFrmInventario.sesion = sesionAcc;
        ItnFrmInventario.productos = productos;
        ItnFrmInventario.trozas = ctrTroza.obtenerTrozas();
        ctrProveedor = new CtrProveedor();
        ctrTipoMadera = new CtrTipoMadera();
        proveedores = new ArrayList<>();
        tmaderas = new ArrayList<>();
        verificacion = new Regex();
        msg = new Mensaje();
        estilo = new UI();
        //Estilizar intefaz
        estilo.estilizarTablas(tbListadoInventario);
        estilo.estilizarTablas(tbActAserrada);
        estilo.estilizarTablas(tbActTerminada);
        estilo.estilizarTablas(tbActTroza);        
        estilo.estilizarTablas(tblAgregarAserrada);
        estilo.estilizarTablas(tblAgregarTerminada);
        estilo.estilizarTablas(tblAgregarTroza);
        estilo.estilizarTablas(tblEditarAserrada);
        estilo.estilizarTablas(tblEditarTerminada);
        estilo.estilizarTablas(tblEditarTroza);
        //Cargar datos a la interfaz
        cargarCombos();
        cargarTablas();
        cargarListas();
    }

    /**
     * Retorna la única instancia de la clase.
     *
     * @param sesionAcc Usuario en sesión actual.
     * @param productos Lista de productos en la base de datos.
     * @return instancia.
     */
    public static ItnFrmInventario getInstancia(CtrAcceso sesionAcc,
            ArrayList<Madera> productos) {
        if (instancia == null) {
            instancia = new ItnFrmInventario(sesionAcc, productos);
        }
        return instancia;
    }
      
    /**
     * Carga los combo box según corresponda con el tipo de madera y producto.
     */
    private void cargarCombos() {
        
        //cargar todos los proveedores
        proveedores = ctrProveedor.obtenerProveedores(); 
        //limpiar cada combo
        cbxNuevoTProveedor.removeAllItems();
        cbxEditarTProveedor.removeAllItems();
        
        cbxNuevoTProveedor.addItem(new Proveedor("0", "Sin proveedor"));
        cbxEditarTProveedor.addItem(new Proveedor("0", "Sin proveedor"));
        
        //cargar los combos de proveedores
        for (Proveedor item : proveedores) {
            cbxNuevoTProveedor.addItem(item);
            cbxEditarTProveedor.addItem(item);
        }
        if(proveedores.size() > 0) {
            cbxNuevoTProveedor.setSelectedIndex(0);
        }
        
        //cargar tipos de madera
        tmaderas = ctrTipoMadera.obtenerTiposMadera();
        //limpiar combos primero
        cbxNuevoTVariedad.removeAllItems();
        cbxNuevoAcVariedad.removeAllItems();
        cbxNuevoTmVariedad.removeAllItems();
        //cargar los combos
        tmaderas.forEach((item) -> {
            cbxNuevoTVariedad.addItem(item);
            cbxNuevoAcVariedad.addItem(item);
            cbxNuevoTmVariedad.addItem(item);
        });
        cbxNuevoTVariedad.setSelectedIndex(0);
    }
    
    /**
     * Lleas las listas para seleccionar los registros origen en la pestaña de 
     * actualizar inventario.
     */
    private void cargarListas() {
        buscarTrozaOrigen("");
        buscarAserradaOrigen("");
    }

    /**
     * Llena las tablas del modulo con los productos.
     */
    private void cargarTablas() {

        productos = ctrMadera.obtenerProductos();
        trozas = ctrTroza.obtenerTrozas();
        
        cargarJTableGeneral(tbListadoInventario, true, "troza&producto");
        cargarJTableGeneral(tbProductosActivos, true, "troza&producto");
        cargarJTableGeneral(tbProductosInactivos, false, "troza&producto");
        
        cargarJTableGeneral(tbActAserrada, true, "aserrada");        
        cargarJTableGeneral(tbActTerminada, true, "terminada");
        cargarJTableGeneral(tbActTroza, true, "troza");
        
        cargarJTableGeneral(tblAgregarAserrada, true,"aserrada");
        cargarJTableGeneral(tblAgregarTerminada, true, "terminada");
        cargarJTableGeneral(tblAgregarTroza, true, "troza");
        
        cargarJTableGeneral(tblEditarAserrada, true, "aserrada");
        cargarJTableGeneral(tblEditarTerminada, true, "terminada");
        cargarJTableGeneral(tblEditarTroza, true, "troza");
    }

    /**
     * Cargar la tabla (modelo) con los productos y trozas existentes.
     * @param tabla Nombre de la tabla a llenar
     * @param estado Estado del producto a ingresar
     * @param tpTabla "troza y producto si la tabla aguanta ambos, troza o producto si aguanta solo uno"
     */
    public void cargarJTableGeneral(JTable tabla, boolean estado, String tpTabla) {
        
        if(tpTabla.equals("troza&producto")) {//Agregar todas en una tabla, aserrada, terminada y trozas
            Object[] row = new Object[9];
            model = (DefaultTableModel) tabla.getModel();
            model.setRowCount(0);
            model.setColumnCount(9);
            //PONER PRODUCTOS
            for (int i = 0; i < productos.size(); i++) {
                if (estado && productos.get(i).getEstado().equals(Estado.Activo)) {
                    row[0] = productos.get(i).getCodProducto();
                    row[1] = productos.get(i).getTipoProducto();
                    row[2] = productos.get(i).getDescTipoMadera();
                    row[3] = productos.get(i).getGrueso() + " x " + productos.get(i).getAncho();               
                    row[4] = productos.get(i).getCantVaras();
                    row[5] = productos.get(i).getPrecioXvara();
                    row[6] = productos.get(i).getDescripcion();
                    row[7] = "No Aplica"; //Proveedor
                    row[8] = productos.get(i).getCodigo();

                    model.addRow(row);
                }
                if (!estado && productos.get(i).getEstado().equals(Estado.Deshabilitado)) {
                    row[0] = productos.get(i).getCodProducto();
                    row[1] = productos.get(i).getTipoProducto();
                    row[2] = productos.get(i).getDescTipoMadera();
                    row[3] = productos.get(i).getGrueso() + " x " + productos.get(i).getAncho();               
                    row[4] = productos.get(i).getCantVaras();
                    row[5] = productos.get(i).getPrecioXvara();
                    row[6] = productos.get(i).getDescripcion();
                    row[7] = "No Aplica";
                    row[8] = productos.get(i).getCodigo();

                    model.addRow(row);
                }
            }
            //PONER TROZAS
            for (int i = 0; i < trozas.size(); i++) {
                if (estado && trozas.get(i).getEstado().equals(Estado.Activo)) {
                    row[0] = trozas.get(i).getCodInterno();
                    row[1] = trozas.get(i).getTipoProducto();
                    row[2] = trozas.get(i).getDescTipoMadera();
                    row[3] = "No aplica"; //Medidas               
                    row[4] = trozas.get(i).getPulgadas();
                    row[5] = "No aplica"; //Precio
                    row[6] = trozas.get(i).getDescripcion();
                    row[7] = trozas.get(i).getNomProveedor();
                    row[8] = trozas.get(i).getCodigo();

                    model.addRow(row);
                }
                //trozas.get(i).getEstado().equals(estado) && !estado
                if (!estado && trozas.get(i).getEstado().equals(Estado.Deshabilitado)) {
                    row[0] = trozas.get(i).getCodInterno();
                    row[1] = trozas.get(i).getTipoProducto();
                    row[2] = trozas.get(i).getDescTipoMadera();
                    row[3] = "No aplica"; //Medidas               
                    row[4] = trozas.get(i).getPulgadas();
                    row[5] = "No aplica"; //Precio
                    row[6] = trozas.get(i).getDescripcion();
                    row[7] = trozas.get(i).getNomProveedor();
                    row[8] = trozas.get(i).getCodigo();

                    model.addRow(row);
                }
            }
            tabla.removeColumn(tabla.getColumnModel().getColumn(8));
            
        } else if (tpTabla.equals("aserrada")) { //Agregar solo aserrada
            
            Object[] row = new Object[8];
            model = (DefaultTableModel) tabla.getModel();
            model.setRowCount(0);
            model.setColumnCount(8);
            
            llenarRowAserrada(row, estado);            
            tabla.removeColumn(tabla.getColumnModel().getColumn(7));
            
        }
        else if (tpTabla.equals("terminada")) { //Agregar solo terminada
            
            Object[] row = new Object[7];
            model = (DefaultTableModel) tabla.getModel();
            model.setRowCount(0);
            model.setColumnCount(7);
            
            llenarRowTerminada(row, estado);            
            tabla.removeColumn(tabla.getColumnModel().getColumn(6));
            
        } else if(tpTabla.equals("troza")) { //Agregar solo troza
            Object[] row = new Object[7];
            model = (DefaultTableModel) tabla.getModel();
            model.setRowCount(0);
            model.setColumnCount(7);
            
            llenarRowTroza(row, estado);            
            tabla.removeColumn(tabla.getColumnModel().getColumn(6));
        }
    }
    
    /**
     * Llena cada columna del modelo de JTable, con el dato que le corresponda 
     * dependiendo del tipo de tabla, en este caso la tabla 
     * es para aserrada
     * @param row Fila de la tabla que se está llenando
     * @param estado Estado de la aserrada a ingresar
     */
    public void llenarRowAserrada(Object[] row, boolean estado) {
        System.out.println("llenarRowAserrada PRODUCTOS SIZE: " + productos.size());
        for (int i = 0; i < productos.size(); i++) {
            if(productos.get(i).getTipoProducto().equals("ASERRADA")) {
                if (estado && productos.get(i).getEstado().equals(Estado.Activo)) {
                    row[0] = productos.get(i).getCodProducto();
                    row[1] = productos.get(i).getTipoProducto();
                    row[2] = productos.get(i).getDescTipoMadera();
                    row[3] = productos.get(i).getGrueso() + " x " + productos.get(i).getAncho();               
                    row[4] = productos.get(i).getCantVaras();
                    row[5] = productos.get(i).getPrecioXvara();
                    row[6] = productos.get(i).getDescripcion();
                    row[7] = productos.get(i).getCodigo();

                    model.addRow(row);
                }
                if (!estado && productos.get(i).getEstado().equals(Estado.Deshabilitado)) {
                    row[0] = productos.get(i).getCodProducto();
                    row[1] = productos.get(i).getTipoProducto();
                    row[2] = productos.get(i).getDescTipoMadera();
                    row[3] = productos.get(i).getGrueso() + " x " + productos.get(i).getAncho();               
                    row[4] = productos.get(i).getCantVaras();
                    row[5] = productos.get(i).getPrecioXvara();
                    row[6] = productos.get(i).getDescripcion();
                    row[7] = productos.get(i).getCodigo();

                    model.addRow(row);
                }
            }
        }
    }
    
    /**
     * Llena cada columna del modelo de JTable, con el dato que le corresponda 
     * dependiendo del tipo de tabla, en este caso la tabla 
     * es para madera terminada
     * @param row Fila de la tabla que se está llenando
     * @param estado Estado de la terminada a ingresar
     */
    public void llenarRowTerminada(Object[] row, boolean estado) {
        System.out.println("llenarRowTerminada PRODUCTOS SIZE: " + productos.size());
        for (int i = 0; i < productos.size(); i++) {
            if(productos.get(i).getTipoProducto().equals("TERMINADA")) {
                if (estado && productos.get(i).getEstado().equals(Estado.Activo)) {
                    row[0] = productos.get(i).getCodProducto();
                    row[1] = productos.get(i).getTipoProducto();
                    row[2] = productos.get(i).getDescTipoMadera();              
                    row[3] = productos.get(i).getCantVaras();
                    row[4] = productos.get(i).getPrecioXvara();
                    row[5] = productos.get(i).getDescripcion();
                    row[6] = productos.get(i).getCodigo();

                    model.addRow(row);
                }
                if (!estado && productos.get(i).getEstado().equals(Estado.Deshabilitado)) {
                    row[0] = productos.get(i).getCodProducto();
                    row[1] = productos.get(i).getTipoProducto();
                    row[2] = productos.get(i).getDescTipoMadera();              
                    row[3] = productos.get(i).getCantVaras();
                    row[4] = productos.get(i).getPrecioXvara();
                    row[5] = productos.get(i).getDescripcion();
                    row[6] = productos.get(i).getCodigo();

                    model.addRow(row);
                }
            }
        }
    }
    
    /**
     * Llena cada columna del modelo de JTable, con el dato que le corresponda 
     * dependiendo del tipo de tabla, en este caso la tabla es para troza
     * @param row Fila de la tabla que se está llenando
     * @param estado Estado de la troza a ingresar
     */
    public void llenarRowTroza(Object[] row, boolean estado) {
        System.out.println("cargarJTableGeneral TROZAS SIZE: " + trozas.size());
        for (int i = 0; i < trozas.size(); i++) {
            if (estado && trozas.get(i).getEstado().equals(Estado.Activo)) {
                row[0] = trozas.get(i).getCodInterno();
                row[1] = trozas.get(i).getTipoProducto();
                row[2] = trozas.get(i).getDescTipoMadera();   
                row[3] = trozas.get(i).getPulgadas();
                row[4] = trozas.get(i).getDescripcion();
                row[5] = trozas.get(i).getNomProveedor();
                row[6] = trozas.get(i).getCodigo();
                
                model.addRow(row);
            }
            //trozas.get(i).getEstado().equals(estado) && !estado
            if (!estado && trozas.get(i).getEstado().equals(Estado.Deshabilitado)) {
                row[0] = trozas.get(i).getCodInterno();
                row[1] = trozas.get(i).getTipoProducto();
                row[2] = trozas.get(i).getDescTipoMadera();
                row[3] = trozas.get(i).getPulgadas();
                row[4] = trozas.get(i).getDescripcion();
                row[5] = trozas.get(i).getNomProveedor();
                row[6] = trozas.get(i).getCodigo();
                
                model.addRow(row);
            }
        }
    }
    
    /**
     * Indica el tipo de producto de acuerdo a lo seleccionado en el combo de 
     * tipo de producto (Aserrada, troza o terminada)
     * @param panel panel en que se encuentra el combo
     * @return el nombre del tipo de producto
     */
    public String verificarTipoProducto(String panel) {
        
        panel = panel.toUpperCase();
        int tipoProd = 0;
        if (panel.toUpperCase().equals("CREAR")) {
            tipoProd = tbNuevoTipoProd.getSelectedIndex();
        } else if (panel.toUpperCase().equals("EDITAR")) {
            tipoProd = tbEditarTipoProd.getSelectedIndex();
        }
        switch (tipoProd) {
            case 0:
                return "ASERRADA";
            case 1:
                return "TROZA";
            case 2:
                return "TERMINADA";
            default:                
                break;
        }
        return "NO IDENTIFICADO";
    }
    
    /**
     * 
     * @param panel
     * @param prod 
     */
    private void seleccionarPanel(String panel, String prod) {
        
        int tipoProd = 0;
        if (panel.toUpperCase().equals("CREAR")) {
            tipoProd = tbNuevoTipoProd.getSelectedIndex();
        } else if (panel.toUpperCase().equals("EDITAR")) {
            tipoProd = tbEditarTipoProd.getSelectedIndex();
        }
        
        switch (prod) {
            case "ASERRADA":
                break;
            case "TERMINADA":
                break;
            case "TROZA":
                break;
        }
    }
    
    /**
     * Limpia los campos de texto del panel, según el nombre del botón que se
     * presiona.
     * @param panel presionado
     * @param tmad tipo de madera
     */
    public void limpiarCampos(String panel, String tmad) {
        
        panel = panel.toUpperCase();
        tmad = tmad.toUpperCase();
        
        if (panel.equals("CREAR")) {
            switch (tmad) {
                case "ASERRADA":
                    txtNuevoAcCodigo.setText("");
                    txtNuevoAcMedAncho.setText("");
                    txtNuevoAcMedGrueso.setText("");
                    txtNuevoAcMedVaras.setText("");
                    txtNuevoAcPrecio.setText("");
                    txtNuevoAcMedVaras.setText("");
                    txtaNuevoAcDescripcion.setText("");
                    cbxNuevoAcVariedad.removeAllItems();
                    cargarCombos();
                    break;
                case "TROZA":
                    txtNuevoTCodigo.setText("");
                    txtNuevoTpulgadas.setText("");
                    txtaNuevoTDescripcion.setText("");
                    cbxNuevoTVariedad.removeAllItems();
                    cbxNuevoTProveedor.removeAllItems();
                    cargarCombos();
                    break;
                case "TERMINADA":
                    txtNuevoTmCodigo.setText("");
                    txtNuevoTmDescripcion.setText("");
                    txtNuevoTmPrecio.setText("");
                    cbxNuevoTmVariedad.removeAll();
                    cargarCombos();
                    break;
                default:
                    break;
            }
        } else if (panel.equals("EDITAR")) {
            switch (tmad) {
                case "ASERRADA":
                    txtEditarAcCodigo.setText("");
                    txtEditarAcMedAncho.setText("");
                    txtEditarAcMedGrueso.setText("");
                    txtEditarAcPrecio.setText("");
                    txtEditarAcVaras.setText("");
                    txtaEditarAcDescripcion.setText("");
                    //cbxNuevoAcVariedad.removeAllItems();
                    cargarCombos();
                    break;
                case "TROZA":
                    txtEditarTCodigo.setText("");
                    txtEditarTPulgadas.setText("");
                    txtaEditarTDescripcion.setText("");
                    //cbxEditarTProveedor.removeAllItems();
                    cargarCombos();
                    break;
                case "TERMINADA":
                    txtEditarTmCodigo.setText("");
                    txtEditarTmNombre.setText("");
                    txtEditarTmPrecio.setText("");
                    txtEditarTmCantVaras.setText("");
                    //cbxEditarTmVariedad.removeAll();
                    cargarCombos();
                    break;
                default:
                    break;
            }
        } else if (panel.equals("ACTUALIZAR")) {
            switch (tmad) {
                case "ASERRADA":
                    txtActAcEntra.setText("");
                    break;
                case "TROZA":
                    txtActTEntra.setText("");
                    break;
                case "TERMINADA":
                    txtActTmEntra.setText("");
                    break;
                default:
                    break;
            }
        }
    }   
    
    /**
     * Crear el producto con la información recibida y enviarlo a insertar a la BD.
     * @param codProd código interno del producto
     * @param codTipoMadera código del tipo de madera
     * @param medida medidas de la madera
     * @param tipoProducto tipo de madera
     * @param cantVaras cantidad que ingresa en varas
     * @param precio precio por vara
     * @param descripcion descripción de la madera ingresada
     * @param codProveedor código del proveedor par la troza (si existe)
     * @return Verdadero si crea el producto exitosamente
     */
    private boolean crearProducto(String codProd, String descripcion, 
            double precio, double cantVaras, String grueso, String ancho,
            String codTipoMadera, String tipoProducto) {
        
        String p = String.valueOf(precio);
        String cv = String.valueOf(cantVaras);
        
//        System.out.println("COD PROD: " + codProd);
//        System.out.println("DESC: " + descripcion);
//        System.out.println("PRECIO: " + precio);
//        System.out.println("VARAS: " + cantVaras);
//        System.out.println("GRUESO: " + grueso);
//        System.out.println("ANCHO: " + ancho);
//        System.out.println("COD TIPO MADERA: " + codTipoMadera);
//        System.out.println("TIPO: " + tipoProducto);
        
        //Campos no están vacíos
        if (!codProd.isEmpty() && !p.isEmpty() && !cv.isEmpty() && 
                !grueso.isEmpty() && !ancho.isEmpty() && 
                !codTipoMadera.isEmpty()) {
                        
            //Verificar precio
            if (verificacion.validaPrecio(p)) {
                                   
                System.out.println("AGREGANDO PRODUCTO, PLEASE WAIT... ");
                boolean crear = ctrMadera.crearProducto(codProd, descripcion,
                        precio, cantVaras, grueso, ancho, codTipoMadera, 
                        tipoProducto);
                System.out.println("CREAR: " + crear);

                if (crear) {
                    cargarTablas();
                    cargarCombos();                                             
                    return true;
                } 
            } else {
                msg.mostrarMensaje(JOptionPane.INFORMATION_MESSAGE,
                        TipoMensaje.PRICE_SYNTAX_FAILURE);
            }
        } else {
            msg.mostrarMensaje(JOptionPane.WARNING_MESSAGE,
                    TipoMensaje.EMPTY_TEXT_FIELD);
        }
        return false;
    }
    
    /**
     * Crear la troza con la información recibida y enviarla a insertar a la BD.
     * @param codProd código interno del producto
     * @param codTipoMadera código del tipo de madera
     * @param medida medidas de la madera
     * @param cantVaras cantidad que ingresa en varas
     * @param precio precio por vara
     * @param descripcion descripción de la madera ingresada
     * @param cedProveedor código del proveedor par la troza (si existe)
     * @return  Verdadero si crea la troza exitosamente
     */
    private boolean crearTroza(String codInte, String codTipoMadera, 
            double pulgadas, String descrip, String cedProveedor) {
        
        String pulg = String.valueOf(pulgadas);
        
        //Campos no están vacíos
        if (!codInte.isEmpty() && !codTipoMadera.isEmpty() && !pulg.isEmpty() && 
                !descrip.isEmpty() && !cedProveedor.isEmpty()) {
                        
            System.out.println("AGREGANDO TROZA, PLEASE WAIT... ");
            boolean crear = ctrTroza.crearTroza(codInte, codTipoMadera, 
                    pulgadas, descrip, cedProveedor);
            System.out.println("CREAR: " + crear);

            if (crear) {
                cargarTablas();
                cargarCombos();                                         
                return true;
            } 
        } else {
            msg.mostrarMensaje(JOptionPane.WARNING_MESSAGE,
                    TipoMensaje.EMPTY_TEXT_FIELD);
        }
        return false;
    }
    
    /**
     * Actualizar los atributos del producto (aserrada y terminada).
     * @param codProd codigo de producto personalizado
     * @param descripcion descripción del producto
     * @param precio precio por vara del producto
     * @param cantVaras cantidad en varas del producto
     * @param grueso grueso del producto
     * @param ancho ancho del producto
     * @param codigo codigo bd del producto
     * @return Verdadero si el producto se actualiza exitosamente
     */                   
    private boolean actualizarProducto(String codProd, String descripcion, 
            String precio, String cantVaras, String grueso, String ancho, 
            String codigo) {
        
        //Campos no están vacíos
        if (!codProd.isEmpty() && !descripcion.isEmpty() && !precio.isEmpty() 
                && !cantVaras.isEmpty() && !grueso.isEmpty() && !ancho.isEmpty() 
                && !codigo.isEmpty()) {
                        
            //Verificar precio
            if (verificacion.validaPrecio(precio)) {
                
                try {
                    double preci = Double.valueOf(precio);
                    double cvaras = Double.valueOf(cantVaras);
                    //TipoMadera tipoMad = 
                      //      (TipoMadera) cbxEditarAcVariedad.getSelectedItem();                  
                    System.out.println("ACTUALIZANDO PRODUCTO, PLEASE WAIT... "+ preci);
                    boolean editar = ctrMadera.actualizarProducto(codProd, 
                            descripcion, preci, cvaras, grueso, ancho, codigo);

                    if (editar) {
                        cargarTablas();
                        cargarCombos();                        
                        return true;
                    }
                } catch (NumberFormatException ex) {
                    msg.mostrarMensaje(JOptionPane.ERROR_MESSAGE, 
                            TipoMensaje.NUMBER_FORMAT_EXCEPTION);
                    ex.printStackTrace();
                } catch (Exception ex) {
                    msg.mostrarMensaje(JOptionPane.ERROR_MESSAGE, 
                            TipoMensaje.SOMETHING_WENT_WRONG);
                    ex.printStackTrace();
                }
            } else {
                msg.mostrarMensaje(JOptionPane.WARNING_MESSAGE,
                        TipoMensaje.PRICE_SYNTAX_FAILURE);
            }
        } else {
            msg.mostrarMensaje(JOptionPane.WARNING_MESSAGE,
                    TipoMensaje.EMPTY_TEXT_FIELD);
        }
        return false;
    }
    
    
    /**
     * Actualizar los atributos de la troza
     * @param codIn Codigo de la troza personalizado por la empresa
     * @param pulgadas Cantidad de troza en pulgadas
     * @param descripcion Detalle o descripción de la troza
     * @param cedProveedor Cedula que identifica el proveedor
     * @param tipoMad Variedad de madera de la Troza
     * @param codigo Codigo bd del producto
     * @return Verdadero si el producto se actualiza exitosamente
     */
    private boolean actualizarTroza(String codIn, String pulgadas, 
            String descripcion, String cedProveedor, String codigo) {
        
        //Campos no están vacíos
        if (!codIn.isEmpty() && !pulgadas.isEmpty() && !codigo.isEmpty()) {
                
            try {
                double pulgs = Double.valueOf(pulgadas);
                boolean editar = ctrTroza.actualizarTroza(codIn, pulgs, 
                        descripcion, cedProveedor, codigo);

                if (editar) {
                    cargarTablas();
                    cargarCombos();                        
                    return true;
                } 
            } catch (NumberFormatException ex) {
                msg.mostrarMensaje(JOptionPane.ERROR_MESSAGE, 
                        TipoMensaje.NUMBER_FORMAT_EXCEPTION);
                ex.printStackTrace();
            } catch (Exception ex) {
                msg.mostrarMensaje(JOptionPane.ERROR_MESSAGE, 
                        TipoMensaje.SOMETHING_WENT_WRONG);
                ex.printStackTrace();
            }
        } else {
            msg.mostrarMensaje(JOptionPane.WARNING_MESSAGE,
                    TipoMensaje.EMPTY_TEXT_FIELD);
        }
        return false;
    }
    
    /**
     * Habilita/Inhabilita los campos necesarios 
     * para realizar la operación (sumar-restar cantidades).
     * @param tab Pestaña actual
     * @param enable Habiltar-Inhabilitar
     */
    private void cambiarOperacion(String tab, boolean enable) {
        switch (tab) {
            case "ASERRADA":
                txtActAcBuscTrz.setEnabled(enable);
                lsActAsSelTrz.setEnabled(enable);
                txtActAcSalenPulg.setEnabled(enable);
                break;
            case "TERMINADA":
                txtActTmBuscAs.setEnabled(enable);
                lsActTmSelAs.setEnabled(enable);
                txtActTmSalenVaras.setEnabled(enable);
                break;
        }
    }
    
     /**
     * Preparar la información del producto/troza ingresada en la interfaz
     * para enviarlo a crear.
     */
    private void prepararCrearProducto(String tipoProduc) {
        
        tipoProduc = tipoProduc.toUpperCase();
        TipoMadera tipoMad;
        
        switch (tipoProduc) {
            //CREAR MADERA ASERRADA
            case "ASERRADA": {
                double cantVaras = 0;
                double precio = 0;
                
                try {
                    cantVaras = Double.valueOf(txtNuevoAcMedVaras.getText().trim());
                    precio = Double.valueOf(txtNuevoAcPrecio.getText().trim());
                } catch(NumberFormatException ex) {
                    msg.mostrarMensaje(
                            JOptionPane.ERROR_MESSAGE, 
                            TipoMensaje.NUMBER_FORMAT_EXCEPTION
                    );
                    ex.printStackTrace();
                }catch (Exception ex) {
                    msg.mostrarMensaje(
                            JOptionPane.ERROR_MESSAGE, 
                            TipoMensaje.SOMETHING_WENT_WRONG
                    );
                    ex.printStackTrace();
                }
                tipoMad = (TipoMadera) cbxNuevoAcVariedad.getSelectedItem();
                
                System.out.println("INVENTARIO -> CREAR PRODUCTO -> ASERRADA");
                boolean cprod = crearProducto(
                        txtNuevoAcCodigo.getText().trim(),
                        txtaNuevoAcDescripcion.getText().trim(), 
                        precio, cantVaras,
                        txtNuevoAcMedGrueso.getText().trim(),
                        txtNuevoAcMedAncho.getText().trim(),
                        tipoMad.getCodigo(), tipoProduc);
                
                if(cprod) {
                    msg.mostrarMensaje(
                            JOptionPane.INFORMATION_MESSAGE, 
                            TipoMensaje.PRODUCT_INSERTION_SUCCESS
                    );
                    limpiarCampos("Crear","ASERRADA");
                    break;
                }else {
                    msg.mostrarMensaje(JOptionPane.ERROR_MESSAGE,
                            TipoMensaje.PRODUCT_INSERTION_FAILURE);                        
                }
                break;
            //CREAR MADERA TROZA
            } case "TROZA": {
                double pulgadas = 0;
                try {
                    pulgadas = Double.valueOf(txtNuevoTpulgadas.getText().trim());
                } catch(NumberFormatException ex) {
                    msg.mostrarMensaje(
                            JOptionPane.ERROR_MESSAGE, 
                            TipoMensaje.NUMBER_FORMAT_EXCEPTION
                    );
                    ex.printStackTrace();
                }catch (Exception ex) {
                    msg.mostrarMensaje(
                            JOptionPane.ERROR_MESSAGE, 
                            TipoMensaje.SOMETHING_WENT_WRONG
                    );
                    ex.printStackTrace();
                }
                tipoMad = (TipoMadera) cbxNuevoTVariedad.getSelectedItem();
                Proveedor pv = (Proveedor) cbxNuevoTProveedor.getSelectedItem();
                System.out.println("INVENTARIO->prepararCrearProducto->CEDPROVEEDOR: " + pv.getCedula());

                boolean cprod = crearTroza(txtNuevoTCodigo.getText().trim(),
                        tipoMad.getCodigo(), pulgadas,
                        txtaNuevoTDescripcion.getText().trim(), pv.getCedula());
                
                if(cprod) {
                    msg.mostrarMensaje(
                            JOptionPane.INFORMATION_MESSAGE, 
                            TipoMensaje.PRODUCT_INSERTION_SUCCESS
                    );
                    limpiarCampos("Crear","TROZA");
                    break;
                } else {
                    msg.mostrarMensaje(JOptionPane.ERROR_MESSAGE,
                            TipoMensaje.PRODUCT_INSERTION_FAILURE);                        
                }
                break;
            //CREAR MADERA TERMINADA
            } case "TERMINADA": {
                double cantVaras = 0;
                double precio = 0;
                try {
                    cantVaras = Double.valueOf(txtNuevoTmCantVaras.getText().trim());
                    precio = Double.valueOf(txtNuevoTmPrecio.getText());
                } catch(NumberFormatException ex) {
                    msg.mostrarMensaje(
                            JOptionPane.ERROR_MESSAGE, 
                            TipoMensaje.NUMBER_FORMAT_EXCEPTION
                    );
                    ex.printStackTrace();
                    break;
                }catch (Exception ex) {
                    msg.mostrarMensaje(
                            JOptionPane.ERROR_MESSAGE, 
                            TipoMensaje.SOMETHING_WENT_WRONG
                    );
                    ex.printStackTrace();
                    break;
                }
                tipoMad = (TipoMadera) cbxNuevoTmVariedad.getSelectedItem();
                
                boolean cprod = crearProducto(txtNuevoTmCodigo.getText(),
                        txtNuevoTmDescripcion.getText(), precio, cantVaras, 
                        "0", "0", tipoMad.getCodigo(), tipoProduc);
                if(cprod) {
                    msg.mostrarMensaje(
                            JOptionPane.INFORMATION_MESSAGE, 
                            TipoMensaje.PRODUCT_INSERTION_SUCCESS
                    );
                    limpiarCampos("Crear","TERMINADA");
                    break;
                } else {
                    msg.mostrarMensaje(JOptionPane.ERROR_MESSAGE,
                            TipoMensaje.PRODUCT_INSERTION_FAILURE);                        
                }
                break;
            } default:
                break;
        }
    }
    
    /**
     * Cargar la información del producto seleccionado (Aserrada y Terminada).
     * @param tipo tipo de producto seleccionado (Aserrada o Terminada).
     */
    private void cargarEditarProducto(String tipoProd) {
        
        try {
            int indiceFila = -1;
            String codigo = "";
            Madera prod = null;
            
            if (tipoProd.toUpperCase().equals("ASERRADA")) {
                model = (DefaultTableModel) tblEditarAserrada.getModel();
                indiceFila = tblEditarAserrada.getSelectedRow();
                codigo = (String) model.getValueAt(indiceFila, 7);
            } else if (tipoProd.toUpperCase().equals("TERMINADA")) {
                model = (DefaultTableModel) tblEditarTerminada.getModel();
                indiceFila = tblEditarTerminada.getSelectedRow();
                codigo = (String) model.getValueAt(indiceFila, 6);
            }
            
            System.out.println("CODIGO MODEL: "+codigo);
            for (Madera p: productos) {
                if (p.getCodigo().equals(codigo)) {
                    prod = p;
                    break;
                }
            }
            System.out.println("----------");
            System.out.println("Inventario -> Editar -> Tabla editar -> MouseClicked");
            if (prod != null && prod.getTipoProducto().equals("ASERRADA")) {
                
                txtEditarAcVaras.setText(String.valueOf(prod.getCantVaras()));
                txtEditarAcPrecio.setText(String.valueOf(prod.getPrecioXvara()));
                txtEditarAcMedGrueso.setText(prod.getGrueso());
                txtEditarAcMedAncho.setText(prod.getAncho());
                txtaEditarAcDescripcion.setText(prod.getDescripcion());
                txtEditarAcCodigo.setText(prod.getCodProducto());
                
                //Seleccionar la pestaña para Aserrada (0)
                tbEditarTipoProd.setSelectedIndex(0);
                
            } else if (prod != null && prod.getTipoProducto().equals("TERMINADA")) {

                txtEditarTmCodigo.setText(prod.getCodProducto());
                txtEditarTmPrecio.setText(String.valueOf(prod.getPrecioXvara()));
                txtEditarTmNombre.setText(prod.getDescripcion());
                txtEditarTmCantVaras.setText(String.valueOf(prod.getCantVaras()));
                
                //Seleccionar la pestaña para Terminada (1)
                tbEditarTipoProd.setSelectedIndex(1);
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
            ex.printStackTrace();
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Cargar la información de la troza seleccionada.
     */
    private void cargarEditarTroza() {
        try {
            model = (DefaultTableModel) tblEditarTroza.getModel();
            
            int indiceFila = tblEditarTroza.getSelectedRow();
            
            System.out.println(model.getValueAt(indiceFila, 6));
            
            String codigo = (String) model.getValueAt(indiceFila, 6);
            
            Troza prod = null;
            for (Troza t: trozas) {
                if (t.getCodigo().equals(codigo)) {
                    prod = t;
                    break;
                }
            }
            
            System.out.println("----------");
            System.out.println("Inventario -> Editar -> Tabla editar -> MouseClicked");
            
            if (prod != null) {
                txtEditarTPulgadas.setText(String.valueOf(prod.getPulgadas()));
                txtaEditarTDescripcion.setText(prod.getDescripcion());
                txtEditarTCodigo.setText(prod.getCodInterno());
                
                if (prod.getCedProveedor() == null) {
                    cbxEditarTProveedor.setSelectedIndex(0);
                } else {
                    for (int i=0; i<cbxEditarTProveedor.getItemCount(); i++) {
                        
                        String ced = cbxEditarTProveedor.getItemAt(i).getCedula();
                        if (prod.getCedProveedor().equals(ced)) {
                            cbxEditarTProveedor.setSelectedIndex(i);
                            break;
                        }
                    }
                }

                //Seleccionar la pestaña para Troza (2)
                tbEditarTipoProd.setSelectedIndex(2);
            }
            
        } catch (ArrayIndexOutOfBoundsException ex) {
            ex.printStackTrace();
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Suma unidades al registro de madera aserrada seleccionado.
     */
    private void sumarAserrada() {
        if (!txtActAcEntra.getText().isEmpty() && 
                !txtActAcSalenPulg.getText().isEmpty()) {
            try {
                double cVarasEntra = Double.valueOf(txtActAcEntra.getText());
                double cPulgSale = Double.valueOf(txtActAcSalenPulg.getText());
                
                //Obtener codigo de la madera aserrada que se seleccionó
                model = (DefaultTableModel) tbActAserrada.getModel();
                int indiceFila = tbActAserrada.getSelectedRow();
                String codRegAserrada = 
                        (String) model.getValueAt(indiceFila, 7);
                //Obtener código de la troza que se seleccionó en la lista
                ListModel lista = lsActAsSelTrz.getModel();
                                
                if (lista != null) {
                    int filaLsTroza = lsActAsSelTrz.getSelectedIndex();

                    Troza trozaRestar = (Troza) lista.getElementAt(filaLsTroza);

                    boolean sumaAserrada = ctrMadera.sumarRegMadera("ASERRADA", 
                            cVarasEntra, codRegAserrada);
                    boolean restaTroza = ctrTroza.restarRegMadera("TROZA", 
                            cPulgSale, trozaRestar.getCodigo());
                    if(sumaAserrada && restaTroza) {
                        cargarTablas();
                        cargarCombos();
                        limpiarCampos("ACTUALIZAR", "ASERRADA");
                    } else {
                        msg.mostrarMensaje(JOptionPane.ERROR_MESSAGE, 
                                TipoMensaje.PRODUCT_SUM_RES_FAILURE);
                    }
                } else {
                    msg.mostrarMensaje(JOptionPane.ERROR_MESSAGE, 
                            TipoMensaje.PRODUCT_SUM_RES_FAILURE);
                }          
            } catch (NumberFormatException ex) {
                msg.mostrarMensaje(JOptionPane.ERROR_MESSAGE, 
                            TipoMensaje.NUMBER_FORMAT_EXCEPTION);
            } catch (ArrayIndexOutOfBoundsException ex) {
                msg.mostrarMensaje(JOptionPane.ERROR_MESSAGE, 
                            TipoMensaje.ANY_ROW_SELECTED);
            } catch (NullPointerException ex) {
                msg.mostrarMensaje(JOptionPane.ERROR_MESSAGE, 
                            TipoMensaje.SOMETHING_WENT_WRONG);
            } catch (Exception ex) {
                msg.mostrarMensaje(JOptionPane.ERROR_MESSAGE, 
                            TipoMensaje.SOMETHING_WENT_WRONG);
            }
        } else {
            msg.mostrarMensaje(JOptionPane.ERROR_MESSAGE, 
                            TipoMensaje.EMPTY_TEXT_FIELD);
        }
    }
    
    /**
     * Preparar la información ingresada en la interfaz para sumar a una terminada
     * y restar en una aserrada y luego enviarlo a actualizar.
     */
    private void sumarTerminada(){
        if (!txtActTmEntra.getText().isEmpty() && 
                !txtActTmSalenVaras.getText().isEmpty()) {
            try {
                double cVarasEntra = Double.valueOf(txtActTmEntra.getText());
                double cVarasSale = Double.valueOf(txtActTmSalenVaras.getText());
                
                //Obtener codigo de la madera aserrada que se seleccionó
                model = (DefaultTableModel) tbActTerminada.getModel();
                int indiceFila = tbActTerminada.getSelectedRow();
                String codRegTermi = 
                        (String) model.getValueAt(indiceFila, 6);
                //Obtener código de la troza que se seleccionó en la lista
                DefaultListModel lista = 
                        (DefaultListModel) lsActTmSelAs.getModel();
                int filaLsAserr = lsActTmSelAs.getSelectedIndex();
                Madera aserrRestar = (Madera) lista.get(filaLsAserr);
                
                boolean sumaTermi = ctrMadera.sumarRegMadera("TERMINADA", 
                        cVarasEntra, codRegTermi);
                boolean restaAserr = ctrMadera.restarRegMadera("ASERRADA", 
                        cVarasSale, aserrRestar.getCodigo());
                if(sumaTermi && restaAserr) {
                    cargarTablas();
                    cargarCombos();
                    limpiarCampos("ACTUALIZAR", "TERMINADA");
                } else {
                    msg.mostrarMensaje(JOptionPane.ERROR_MESSAGE, 
                            TipoMensaje.PRODUCT_SUM_RES_FAILURE);
                }                
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
                msg.mostrarMensaje(JOptionPane.ERROR_MESSAGE, 
                            TipoMensaje.NUMBER_FORMAT_EXCEPTION);
            } catch (ArrayIndexOutOfBoundsException ex) {
                ex.printStackTrace();
                msg.mostrarMensaje(JOptionPane.ERROR_MESSAGE, 
                            TipoMensaje.ANY_ROW_SELECTED);
            } catch (NullPointerException ex) {
                ex.printStackTrace();
                msg.mostrarMensaje(JOptionPane.ERROR_MESSAGE, 
                            TipoMensaje.ANY_ROW_SELECTED);
            } catch (Exception ex) {
                ex.printStackTrace();
                msg.mostrarMensaje(JOptionPane.ERROR_MESSAGE, 
                            TipoMensaje.SOMETHING_WENT_WRONG);
            }
        } else {
            msg.mostrarMensaje(JOptionPane.ERROR_MESSAGE, 
                            TipoMensaje.EMPTY_TEXT_FIELD);
        } 
    }
    
    /**
     * Preparar la información ingresada en la interfaz para sumar a una troza
     * y luego enviarlo a actualizar.
     */
    public void sumarTroza() {
        if (!txtActTEntra.getText().isEmpty()) {
            try {
                double cVarasEntra = Double.valueOf(txtActTEntra.getText());
                
                //Obtener codigo de la madera aserrada que se seleccionó
                model = (DefaultTableModel) tbActTroza.getModel();
                int indiceFila = tbActTroza.getSelectedRow();                
                String codRegTroza = 
                        (String) model.getValueAt(indiceFila, 6);
                
                boolean sumaTroz = ctrTroza.sumarRegMadera("TROZA", 
                        cVarasEntra, codRegTroza);
                
                if(sumaTroz) {
                    cargarTablas();
                    cargarCombos();
                    limpiarCampos("ACTUALIZAR", "TROZA");
                } else {
                    msg.mostrarMensaje(JOptionPane.ERROR_MESSAGE, 
                            TipoMensaje.PRODUCT_SUM_RES_FAILURE);
                }                
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
                msg.mostrarMensaje(JOptionPane.ERROR_MESSAGE, 
                            TipoMensaje.NUMBER_FORMAT_EXCEPTION);
            } catch (ArrayIndexOutOfBoundsException ex) {
                ex.printStackTrace();
                msg.mostrarMensaje(JOptionPane.ERROR_MESSAGE, 
                            TipoMensaje.ANY_ROW_SELECTED);
            } catch (NullPointerException ex) {
                ex.printStackTrace();
                msg.mostrarMensaje(JOptionPane.ERROR_MESSAGE, 
                            TipoMensaje.ANY_ROW_SELECTED);
            } catch (Exception ex) {
                ex.printStackTrace();
                msg.mostrarMensaje(JOptionPane.ERROR_MESSAGE, 
                            TipoMensaje.SOMETHING_WENT_WRONG);
            }
        } else {
            msg.mostrarMensaje(JOptionPane.ERROR_MESSAGE, 
                            TipoMensaje.EMPTY_TEXT_FIELD);
        }
    }
    /**
     * Preparar la información ingresada en la interfaz para editar un 
     * producto (Aserrada, Terminada y Troza) y luego enviarlo a actualizar.
     */
    private void prepararEditarProducto(String tipo) {
        
        tipo = tipo.toUpperCase();
        
        try {
            String grueso;
            String ancho;
            String pulgadas;
            
            if (tipo.equals("ASERRADA")) {
                model = (DefaultTableModel) tblEditarAserrada.getModel();
                int indiceFila = tblEditarAserrada.getSelectedRow();
                //#revisar indice#
                String codigo = (String) model.getValueAt(indiceFila, 7);
            
                grueso = txtEditarAcMedGrueso.getText().trim();
                ancho = txtEditarAcMedAncho.getText().trim();
                
                boolean aprod = actualizarProducto(
                        txtEditarAcCodigo.getText().trim(),
                        txtaEditarAcDescripcion.getText().trim(),
                        txtEditarAcPrecio.getText().trim(),
                        txtEditarAcVaras.getText().trim(),
                        grueso, ancho, codigo);
                if(aprod) {
                    limpiarCampos("Editar","ASERRADA");
                }
            } else if (tipo.equals("TROZA")) {

                 model = (DefaultTableModel) tblEditarTroza.getModel();
                int indiceFila = tblEditarTroza.getSelectedRow();
                //#revisar indice#
                String codigo = (String) model.getValueAt(indiceFila, 6);
                pulgadas = txtEditarTPulgadas.getText().trim();
                Proveedor pv = (Proveedor) cbxEditarTProveedor.getSelectedItem();

                boolean aprod = actualizarTroza(
                        txtEditarTCodigo.getText().trim(), pulgadas,
                        txtaEditarTDescripcion.getText().trim(), 
                        pv.getCedula(), codigo);
                if(aprod) {
                    msg.mostrarMensaje(JOptionPane.INFORMATION_MESSAGE,
                                TipoMensaje.PRODUCT_UPDATE_SUCCESS);
                    limpiarCampos("Editar","TROZA");
                } else {
                    msg.mostrarMensaje(JOptionPane.ERROR_MESSAGE,
                        TipoMensaje.PRODUCT_UPDATE_FAILURE);
                }
                
            } else if (tipo.equals("TERMINADA")) {
                model = (DefaultTableModel) tblEditarTerminada.getModel();
                int indiceFila = tblEditarTerminada.getSelectedRow();
                //#revisar indice#
                String codigo = (String) model.getValueAt(indiceFila, 6);

                boolean aprod = actualizarProducto(txtEditarTmCodigo.getText(),
                        txtEditarTmNombre.getText(), txtEditarTmPrecio.getText(),
                        txtEditarTmCantVaras.getText().trim(), "0", "0", codigo);
                if(aprod) {
                    limpiarCampos("Editar","TERMINADA");
                    msg.mostrarMensaje(JOptionPane.INFORMATION_MESSAGE, 
                            TipoMensaje.PRODUCT_UPDATE_SUCCESS);
                } else {
                    msg.mostrarMensaje(JOptionPane.ERROR_MESSAGE, 
                            TipoMensaje.PRODUCT_UPDATE_FAILURE);
                }
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
            ex.printStackTrace();
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Preparar la información del producto/troza seleccionada en la interfaz
     * para enviarlo a Deshabilitar/Habilitar.
     */
    public void prepararDesHabProducto() {
       try {
            //Escoger modelo de la tabla correspondiente (activos/inactivos)
            model = tbDeshab.getSelectedIndex() == 0 ? 
                    (DefaultTableModel) tbProductosActivos.getModel() : 
                    (DefaultTableModel) tbProductosInactivos.getModel();
            //Tomar la fila seleccionada dependiendo de la tabla
            int selectedRowIndex = tbDeshab.getSelectedIndex() == 0 ? 
                    tbProductosActivos.getSelectedRow() : 
                    tbProductosInactivos.getSelectedRow();
            //Ver estado del radiobutton seleccionado
            Estado estado = rbDeshabHabilitarProducto.isSelected() ? 
                    Estado.Activo : Estado.Deshabilitado;
            
            String codigo = (String) model.getValueAt(selectedRowIndex, 8);
            
            if (estado.equals(Estado.Deshabilitado)) {
                ctrMadera.inactivarProducto(codigo);
                System.out.println(codigo);
            } else {
                ctrMadera.activarProducto(codigo);
                System.out.println(codigo);
            }
            //Actualizar las tablas
            cargarTablas();
        } catch (Exception e) {
            e.printStackTrace();
            msg.mostrarMensaje(JOptionPane.INFORMATION_MESSAGE,
                    TipoMensaje.ANY_ROW_SELECTED);
        } 
    }
    
    /**
     * Busca los registros de troza que coinciden con el parámetro ingresado.
     * @param text llave de búsqueda.
     */
    private void buscarTrozaOrigen(String text) {
        ArrayList<Troza> tempTrozas = ctrTroza.consultarTrozas(text);
        
        DefaultListModel<Troza> mTrozas = new DefaultListModel<>();
        for (Troza tr: tempTrozas) {
            if (tr.getEstado().equals(Estado.Activo)) {
                mTrozas.addElement(tr);
            }
        }
        
        lsActAsSelTrz.setModel(mTrozas);
    }
    
    /**
     * Busca los registros de madera que coinciden con el parámetro ingresado.
     * @param text llave de búsqueda.
     */
    private void buscarAserradaOrigen(String text) {
        ArrayList<Madera> tempProds = ctrMadera.consultarProductos(text);
        
        DefaultListModel<Madera> mProds = new DefaultListModel<>();
        for (Madera p: tempProds) {
            if (p.getTipoProducto().equals("ASERRADA") && 
                    p.getEstado().equals(Estado.Activo)) {
                mProds.addElement(p);
            }
        }
        
        lsActTmSelAs.setModel(mProds);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgDeshab = new javax.swing.ButtonGroup();
        jFormattedTextField1 = new javax.swing.JFormattedTextField();
        pnl_modInventario = new javax.swing.JPanel();
        tbpnl_modInventario = new javax.swing.JTabbedPane();
        pnl_listado = new javax.swing.JPanel();
        lblListadoInventario = new javax.swing.JLabel();
        txtListadoInventario = new javax.swing.JTextField();
        scpnlTblListadoInventario = new javax.swing.JScrollPane();
        tbListadoInventario = new javax.swing.JTable();
        btnListarProductosRefrescar = new javax.swing.JButton();
        pnl_actualizar = new javax.swing.JPanel();
        tbActualizarTipoProd = new javax.swing.JTabbedPane();
        pnlActualizarAcerrada = new javax.swing.JPanel();
        btnActualizarAserrada = new javax.swing.JButton();
        pnSeleccionarRegAserrada = new javax.swing.JPanel();
        lblActAcOp = new javax.swing.JLabel();
        scpnlTblAcActualizar = new javax.swing.JScrollPane();
        tbActAserrada = new javax.swing.JTable();
        lblActAcEntra = new javax.swing.JLabel();
        txtActAcEntra = new javax.swing.JTextField();
        placeholder = new TextPrompt("Cantidad en varas", txtActAcEntra);
        placeholder.changeAlpha(0.75f);
        placeholder.changeStyle(Font.ITALIC);
        jPanel1 = new javax.swing.JPanel();
        lblActAcOp2 = new javax.swing.JLabel();
        txtActAcBuscTrz = new javax.swing.JTextField();
        placeholder = new TextPrompt("Buscar troza de origen por código...", txtActAcBuscTrz);
        placeholder.changeAlpha(0.75f);
        placeholder.changeStyle(Font.ITALIC);
        spActAsSelTrz = new javax.swing.JScrollPane();
        lsActAsSelTrz = new javax.swing.JList();
        lblActAcSalen = new javax.swing.JLabel();
        txtActAcSalenPulg = new javax.swing.JTextField();
        placeholder = new TextPrompt("Cantidad en pulgadas", txtActAcSalenPulg);
        placeholder.changeAlpha(0.75f);
        placeholder.changeStyle(Font.ITALIC);
        pnlActualizarTerminada = new javax.swing.JPanel();
        btnActualizarTerminada = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        lblActAcOp1 = new javax.swing.JLabel();
        scpnlTblTmActualizar = new javax.swing.JScrollPane();
        tbActTerminada = new javax.swing.JTable();
        txtActTmEntra = new javax.swing.JTextField();
        placeholder = new TextPrompt("Cantidad en varas", txtActTmEntra);
        placeholder.changeAlpha(0.75f);
        placeholder.changeStyle(Font.ITALIC);
        lblActTmEntra = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        lblActAcOp3 = new javax.swing.JLabel();
        txtActTmBuscAs = new javax.swing.JTextField();
        placeholder = new TextPrompt("Buscar madera aserrada de origen...", txtActTmBuscAs);
        placeholder.changeAlpha(0.75f);
        placeholder.changeStyle(Font.ITALIC);
        spActTmSelAs = new javax.swing.JScrollPane();
        lsActTmSelAs = new javax.swing.JList<>();
        lblActTmSalen = new javax.swing.JLabel();
        txtActTmSalenVaras = new javax.swing.JTextField();
        placeholder = new TextPrompt("Cantidad en varas...", txtActTmSalenVaras);
        placeholder.changeAlpha(0.75f);
        placeholder.changeStyle(Font.ITALIC);
        pnlActualizarTroza = new javax.swing.JPanel();
        scpnlTblTActualizar = new javax.swing.JScrollPane();
        tbActTroza = new javax.swing.JTable();
        lblActTEntra = new javax.swing.JLabel();
        txtActTEntra = new javax.swing.JTextField();
        btnActualizarTroza = new javax.swing.JButton();
        lblActAcOp4 = new javax.swing.JLabel();
        pnl_agregarNuevo = new javax.swing.JPanel();
        tbNuevoTipoProd = new javax.swing.JTabbedPane();
        pnlNuevoAcerrada = new javax.swing.JPanel();
        lblNuevoAcCodigo = new javax.swing.JLabel();
        lblNuevoAcVariedad = new javax.swing.JLabel();
        lblNuevoAcMedidas = new javax.swing.JLabel();
        lblNuevoAcUnidades = new javax.swing.JLabel();
        lblNuevoAcPrecio = new javax.swing.JLabel();
        lblNuevoAcDescripcion = new javax.swing.JLabel();
        lblNuevoAcMedX = new javax.swing.JLabel();
        txtNuevoAcCodigo = new javax.swing.JTextField();
        txtNuevoAcMedVaras = new javax.swing.JTextField();
        placeholder = new TextPrompt("Varas", txtNuevoAcMedVaras);
        placeholder.changeAlpha(0.75f);
        placeholder.changeStyle(Font.ITALIC);
        txtNuevoAcMedAncho = new javax.swing.JTextField();
        placeholder = new TextPrompt("Ancho", txtNuevoAcMedAncho);
        placeholder.changeAlpha(0.75f);
        placeholder.changeStyle(Font.ITALIC);
        txtNuevoAcMedGrueso = new javax.swing.JTextField();
        placeholder = new TextPrompt("Grueso", txtNuevoAcMedGrueso);
        placeholder.changeAlpha(0.75f);
        placeholder.changeStyle(Font.ITALIC);
        txtNuevoAcPrecio = new javax.swing.JTextField();
        scpnlNuevoAcDescripcion = new javax.swing.JScrollPane();
        txtaNuevoAcDescripcion = new javax.swing.JTextArea();
        cbxNuevoAcVariedad = new javax.swing.JComboBox<>();
        scpnlTbAcNuevo = new javax.swing.JScrollPane();
        tblAgregarAserrada = new javax.swing.JTable();
        btnNuevaAserrada = new javax.swing.JButton();
        pnlNuevoTerminada = new javax.swing.JPanel();
        lblNuevoTmCodigo = new javax.swing.JLabel();
        lblNuevoTmNombre = new javax.swing.JLabel();
        lblNuevoTmVariedad = new javax.swing.JLabel();
        lblNuevoTmPrecio = new javax.swing.JLabel();
        txtNuevoTmCodigo = new javax.swing.JTextField();
        txtNuevoTmDescripcion = new javax.swing.JTextField();
        txtNuevoTmPrecio = new javax.swing.JTextField();
        cbxNuevoTmVariedad = new javax.swing.JComboBox<>();
        lblNuevoTmCantVaras = new javax.swing.JLabel();
        txtNuevoTmCantVaras = new javax.swing.JTextField();
        scpnlTbTmNuevo = new javax.swing.JScrollPane();
        tblAgregarTerminada = new javax.swing.JTable();
        btnNuevaTerminada = new javax.swing.JButton();
        pnlNuevoTroza = new javax.swing.JPanel();
        lblNuevoTCodigo = new javax.swing.JLabel();
        lblNuevoTVariedadMadera = new javax.swing.JLabel();
        lblNuevoTpulgadas = new javax.swing.JLabel();
        lblNuevoTProveedor = new javax.swing.JLabel();
        lblNuevoTDescripcion = new javax.swing.JLabel();
        txtNuevoTCodigo = new javax.swing.JTextField();
        scpnlNuevoTDescripcion = new javax.swing.JScrollPane();
        txtaNuevoTDescripcion = new javax.swing.JTextArea();
        cbxNuevoTVariedad = new javax.swing.JComboBox<>();
        cbxNuevoTProveedor = new javax.swing.JComboBox<>();
        txtNuevoTpulgadas = new javax.swing.JTextField();
        placeholder = new TextPrompt("Pulgadas", txtNuevoTpulgadas);
        placeholder.changeAlpha(0.75f);
        placeholder.changeStyle(Font.ITALIC);
        btnCrearProv = new javax.swing.JButton();
        scpnlTbTNuevo = new javax.swing.JScrollPane();
        tblAgregarTroza = new javax.swing.JTable();
        btnNuevaTroza = new javax.swing.JButton();
        pnl_editar = new javax.swing.JPanel();
        tbEditarTipoProd = new javax.swing.JTabbedPane();
        pnlEditarAcerrada = new javax.swing.JPanel();
        lblEditarAcCodigo = new javax.swing.JLabel();
        lblEditarAcMedidas = new javax.swing.JLabel();
        lblEditarAcUnidade = new javax.swing.JLabel();
        lblEditarAcPrecio = new javax.swing.JLabel();
        lblEditarAcDescripcion = new javax.swing.JLabel();
        lblEditarAcMedX = new javax.swing.JLabel();
        txtEditarAcCodigo = new javax.swing.JTextField();
        txtEditarAcMedAncho = new javax.swing.JTextField();
        txtEditarAcMedGrueso = new javax.swing.JTextField();
        txtEditarAcVaras = new javax.swing.JTextField();
        txtEditarAcPrecio = new javax.swing.JTextField();
        scpnlEditarAcDescripcion = new javax.swing.JScrollPane();
        txtaEditarAcDescripcion = new javax.swing.JTextArea();
        scpnlTbAcEditar = new javax.swing.JScrollPane();
        tblEditarAserrada = new javax.swing.JTable();
        btnEditarAserrada = new javax.swing.JButton();
        pnlEditarTerminada = new javax.swing.JPanel();
        lblEditarTmCodigo = new javax.swing.JLabel();
        lblEditarTmNombre = new javax.swing.JLabel();
        lblEditarTmPrecio = new javax.swing.JLabel();
        txtEditarTmCodigo = new javax.swing.JTextField();
        txtEditarTmNombre = new javax.swing.JTextField();
        txtEditarTmPrecio = new javax.swing.JTextField();
        lblEditarTmCantVaras = new javax.swing.JLabel();
        txtEditarTmCantVaras = new javax.swing.JTextField();
        scpnlTbTmEditar = new javax.swing.JScrollPane();
        tblEditarTerminada = new javax.swing.JTable();
        btnEditarTerminada = new javax.swing.JButton();
        pnlEditarTroza = new javax.swing.JPanel();
        lblEditarTCodigo = new javax.swing.JLabel();
        lblEditarTProveedor = new javax.swing.JLabel();
        lblEditarTDescripcion = new javax.swing.JLabel();
        txtEditarTCodigo = new javax.swing.JTextField();
        scpnlEditarTDescripcion = new javax.swing.JScrollPane();
        txtaEditarTDescripcion = new javax.swing.JTextArea();
        cbxEditarTProveedor = new javax.swing.JComboBox<>();
        btnCrearProv1 = new javax.swing.JButton();
        lblEditarTpulgadas = new javax.swing.JLabel();
        txtEditarTPulgadas = new javax.swing.JTextField();
        placeholder = new TextPrompt("Pulgadas", txtNuevoTpulgadas);
        placeholder.changeAlpha(0.75f);
        placeholder.changeStyle(Font.ITALIC);
        scpnlTbTEditar = new javax.swing.JScrollPane();
        tblEditarTroza = new javax.swing.JTable();
        btnEditarTroza = new javax.swing.JButton();
        pnlHabilitar = new javax.swing.JPanel();
        tbDeshab = new javax.swing.JTabbedPane();
        pnlActivos = new javax.swing.JPanel();
        txtBuscarActivo = new javax.swing.JTextField();
        scpnlProductoActivo = new javax.swing.JScrollPane();
        tbProductosActivos = new javax.swing.JTable();
        pnlInactivos = new javax.swing.JPanel();
        txtBuscarInactivo = new javax.swing.JTextField();
        scpnlProductoInactivo = new javax.swing.JScrollPane();
        tbProductosInactivos = new javax.swing.JTable();
        pnlDeshabContainer = new javax.swing.JPanel();
        rbDeshabDeshabProducto = new javax.swing.JRadioButton();
        rbDeshabHabilitarProducto = new javax.swing.JRadioButton();
        btn_deshabilitar = new javax.swing.JButton();

        try {
            javax.swing.text.MaskFormatter mask = new javax.swing.text.MaskFormatter("#-####-####");
            mask.setPlaceholderCharacter('_');
            jFormattedTextField1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(mask));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jFormattedTextField1.setText("_-____-____");
        jFormattedTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFormattedTextField1ActionPerformed(evt);
            }
        });

        setClosable(true);
        setIconifiable(true);
        setTitle("Módulo de Inventario");
        setPreferredSize(new java.awt.Dimension(1240, 670));

        tbpnl_modInventario.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        tbpnl_modInventario.setPreferredSize(new java.awt.Dimension(1208, 645));

        lblListadoInventario.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        lblListadoInventario.setText("Buscar producto: ");

        txtListadoInventario.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        txtListadoInventario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtListadoInventarioKeyReleased(evt);
            }
        });

        tbListadoInventario.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Tipo de producto", "Variedad de madera", "Medidas", "Cantidad", "Precio por vara", "Descripción", "Proveedor", "Codigo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbListadoInventario.setRowHeight(15);
        tbListadoInventario.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        scpnlTblListadoInventario.setViewportView(tbListadoInventario);

        btnListarProductosRefrescar.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        btnListarProductosRefrescar.setText("Refrescar");
        btnListarProductosRefrescar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnListarProductosRefrescarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnl_listadoLayout = new javax.swing.GroupLayout(pnl_listado);
        pnl_listado.setLayout(pnl_listadoLayout);
        pnl_listadoLayout.setHorizontalGroup(
            pnl_listadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_listadoLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(pnl_listadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnl_listadoLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lblListadoInventario)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtListadoInventario, javax.swing.GroupLayout.PREFERRED_SIZE, 899, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnListarProductosRefrescar)
                        .addGap(25, 25, 25))
                    .addComponent(scpnlTblListadoInventario))
                .addGap(25, 25, 25))
        );
        pnl_listadoLayout.setVerticalGroup(
            pnl_listadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_listadoLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(pnl_listadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtListadoInventario, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblListadoInventario, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnListarProductosRefrescar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(scpnlTblListadoInventario, javax.swing.GroupLayout.DEFAULT_SIZE, 553, Short.MAX_VALUE)
                .addContainerGap())
        );

        tbpnl_modInventario.addTab("Listado productos", pnl_listado);

        tbActualizarTipoProd.setTabPlacement(javax.swing.JTabbedPane.LEFT);
        tbActualizarTipoProd.setPreferredSize(new java.awt.Dimension(1000, 611));

        btnActualizarAserrada.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        btnActualizarAserrada.setText("Actualizar Inventario");
        btnActualizarAserrada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarAserradaActionPerformed(evt);
            }
        });

        pnSeleccionarRegAserrada.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Registro a actualizar", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Yu Gothic UI Semibold", 0, 18))); // NOI18N

        lblActAcOp.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        lblActAcOp.setText("Seleccione el registro a actualizar:");

        tbActAserrada.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Tipo de producto", "Variedad de madera", "Medidas", "Cantidad varas", "Precio por vara", "Descripción", "Codigo bd"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbActAserrada.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        scpnlTblAcActualizar.setViewportView(tbActAserrada);

        lblActAcEntra.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        lblActAcEntra.setText("Entra:");

        txtActAcEntra.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        txtActAcEntra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtActAcEntraActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnSeleccionarRegAserradaLayout = new javax.swing.GroupLayout(pnSeleccionarRegAserrada);
        pnSeleccionarRegAserrada.setLayout(pnSeleccionarRegAserradaLayout);
        pnSeleccionarRegAserradaLayout.setHorizontalGroup(
            pnSeleccionarRegAserradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnSeleccionarRegAserradaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnSeleccionarRegAserradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scpnlTblAcActualizar, javax.swing.GroupLayout.DEFAULT_SIZE, 700, Short.MAX_VALUE)
                    .addGroup(pnSeleccionarRegAserradaLayout.createSequentialGroup()
                        .addGroup(pnSeleccionarRegAserradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblActAcOp, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnSeleccionarRegAserradaLayout.createSequentialGroup()
                                .addComponent(lblActAcEntra, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtActAcEntra, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnSeleccionarRegAserradaLayout.setVerticalGroup(
            pnSeleccionarRegAserradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnSeleccionarRegAserradaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblActAcOp, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scpnlTblAcActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnSeleccionarRegAserradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblActAcEntra, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtActAcEntra, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Registro de origen", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Yu Gothic UI Semibold", 0, 18))); // NOI18N

        lblActAcOp2.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        lblActAcOp2.setText("Seleccione la troza de origen:");

        txtActAcBuscTrz.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 16)); // NOI18N
        txtActAcBuscTrz.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtActAcBuscTrzKeyReleased(evt);
            }
        });

        lsActAsSelTrz.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 16)); // NOI18N
        spActAsSelTrz.setViewportView(lsActAsSelTrz);

        lblActAcSalen.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        lblActAcSalen.setText("Sale:");

        txtActAcSalenPulg.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblActAcSalen, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtActAcSalenPulg))
                    .addComponent(spActAsSelTrz, javax.swing.GroupLayout.DEFAULT_SIZE, 328, Short.MAX_VALUE)
                    .addComponent(txtActAcBuscTrz)
                    .addComponent(lblActAcOp2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblActAcOp2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtActAcBuscTrz, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(spActAsSelTrz, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtActAcSalenPulg, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGap(3, 3, 3))
                    .addComponent(lblActAcSalen, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14))
        );

        javax.swing.GroupLayout pnlActualizarAcerradaLayout = new javax.swing.GroupLayout(pnlActualizarAcerrada);
        pnlActualizarAcerrada.setLayout(pnlActualizarAcerradaLayout);
        pnlActualizarAcerradaLayout.setHorizontalGroup(
            pnlActualizarAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlActualizarAcerradaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnSeleccionarRegAserrada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlActualizarAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnActualizarAserrada, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(980, 980, 980))
        );
        pnlActualizarAcerradaLayout.setVerticalGroup(
            pnlActualizarAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlActualizarAcerradaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlActualizarAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlActualizarAcerradaLayout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(183, 183, 183)
                        .addComponent(btnActualizarAserrada, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(pnSeleccionarRegAserrada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(200, Short.MAX_VALUE))
        );

        tbActualizarTipoProd.addTab("", new javax.swing.ImageIcon(getClass().getResource("/recursos/inv_acerrada.png")), pnlActualizarAcerrada, "Madera Acerrada"); // NOI18N

        btnActualizarTerminada.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        btnActualizarTerminada.setText("Actualizar Inventario");
        btnActualizarTerminada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarTerminadaActionPerformed(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Registro a actualizar", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Yu Gothic UI Semibold", 0, 16))); // NOI18N
        jPanel2.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        lblActAcOp1.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        lblActAcOp1.setText("Seleccione el registro a actualizar:");

        tbActTerminada.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Tipo de producto", "Variedad de madera", "Cantidad varas", "Precio por vara", "Descripción", "Codigo bd"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbActTerminada.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        scpnlTblTmActualizar.setViewportView(tbActTerminada);

        txtActTmEntra.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        lblActTmEntra.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        lblActTmEntra.setText("Entra:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scpnlTblTmActualizar, javax.swing.GroupLayout.DEFAULT_SIZE, 700, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblActAcOp1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(lblActTmEntra, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtActTmEntra, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblActAcOp1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scpnlTblTmActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblActTmEntra, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtActTmEntra, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(106, 106, 106))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Registro de origen", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Yu Gothic UI Semibold", 0, 16))); // NOI18N
        jPanel3.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        lblActAcOp3.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        lblActAcOp3.setText("Seleccione la madera de origen:");

        txtActTmBuscAs.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        txtActTmBuscAs.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtActTmBuscAsKeyReleased(evt);
            }
        });

        lsActTmSelAs.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        spActTmSelAs.setViewportView(lsActTmSelAs);

        lblActTmSalen.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        lblActTmSalen.setText("Sale:");

        txtActTmSalenVaras.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblActAcOp3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtActTmBuscAs)
                    .addComponent(spActTmSelAs, javax.swing.GroupLayout.DEFAULT_SIZE, 328, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(lblActTmSalen, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtActTmSalenVaras)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblActAcOp3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtActTmBuscAs, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(spActTmSelAs, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblActTmSalen, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtActTmSalenVaras, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlActualizarTerminadaLayout = new javax.swing.GroupLayout(pnlActualizarTerminada);
        pnlActualizarTerminada.setLayout(pnlActualizarTerminadaLayout);
        pnlActualizarTerminadaLayout.setHorizontalGroup(
            pnlActualizarTerminadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlActualizarTerminadaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlActualizarTerminadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnActualizarTerminada, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(65, Short.MAX_VALUE))
        );
        pnlActualizarTerminadaLayout.setVerticalGroup(
            pnlActualizarTerminadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlActualizarTerminadaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlActualizarTerminadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 555, Short.MAX_VALUE)
                    .addGroup(pnlActualizarTerminadaLayout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnActualizarTerminada, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(135, 135, 135))
        );

        tbActualizarTipoProd.addTab("", new javax.swing.ImageIcon(getClass().getResource("/recursos/inv_terminada.png")), pnlActualizarTerminada, "Madera Terminada"); // NOI18N

        tbActTroza.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Tipo de producto", "Variedad de madera", "Cantidad pulgadas", "Descripción", "Proveedor", "Codigo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbActTroza.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        scpnlTblTActualizar.setViewportView(tbActTroza);

        lblActTEntra.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        lblActTEntra.setText("Entra:");

        placeholder = new TextPrompt("Pulgadas", txtActTEntra);
        placeholder.changeAlpha(0.75f);
        placeholder.changeStyle(Font.ITALIC);
        txtActTEntra.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        btnActualizarTroza.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        btnActualizarTroza.setText("Actualizar Inventario");
        btnActualizarTroza.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarTrozaActionPerformed(evt);
            }
        });

        lblActAcOp4.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        lblActAcOp4.setText("Seleccione el registro a actualizar:");

        javax.swing.GroupLayout pnlActualizarTrozaLayout = new javax.swing.GroupLayout(pnlActualizarTroza);
        pnlActualizarTroza.setLayout(pnlActualizarTrozaLayout);
        pnlActualizarTrozaLayout.setHorizontalGroup(
            pnlActualizarTrozaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlActualizarTrozaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlActualizarTrozaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblActAcOp4)
                    .addGroup(pnlActualizarTrozaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlActualizarTrozaLayout.createSequentialGroup()
                            .addComponent(lblActTEntra, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(txtActTEntra, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnActualizarTroza, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(scpnlTblTActualizar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 1119, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(53, Short.MAX_VALUE))
        );
        pnlActualizarTrozaLayout.setVerticalGroup(
            pnlActualizarTrozaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlActualizarTrozaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblActAcOp4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scpnlTblTActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addGroup(pnlActualizarTrozaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtActTEntra, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblActTEntra, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnActualizarTroza, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        tbActualizarTipoProd.addTab("", new javax.swing.ImageIcon(getClass().getResource("/recursos/inv_troza.png")), pnlActualizarTroza, "Madera en Troza"); // NOI18N

        javax.swing.GroupLayout pnl_actualizarLayout = new javax.swing.GroupLayout(pnl_actualizar);
        pnl_actualizar.setLayout(pnl_actualizarLayout);
        pnl_actualizarLayout.setHorizontalGroup(
            pnl_actualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_actualizarLayout.createSequentialGroup()
                .addComponent(tbActualizarTipoProd, javax.swing.GroupLayout.PREFERRED_SIZE, 1249, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnl_actualizarLayout.setVerticalGroup(
            pnl_actualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_actualizarLayout.createSequentialGroup()
                .addComponent(tbActualizarTipoProd, javax.swing.GroupLayout.PREFERRED_SIZE, 708, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        tbpnl_modInventario.addTab("Actualizar inventario", pnl_actualizar);

        tbNuevoTipoProd.setTabPlacement(javax.swing.JTabbedPane.LEFT);

        lblNuevoAcCodigo.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        lblNuevoAcCodigo.setText("Código:");

        lblNuevoAcVariedad.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        lblNuevoAcVariedad.setText("Variedad de madera:");

        lblNuevoAcMedidas.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        lblNuevoAcMedidas.setText("Medidas:");

        lblNuevoAcUnidades.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        lblNuevoAcUnidades.setText("Cantidad en varas:");

        lblNuevoAcPrecio.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        lblNuevoAcPrecio.setText("Precio por vara:");

        lblNuevoAcDescripcion.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        lblNuevoAcDescripcion.setText("Detalle del producto:");

        lblNuevoAcMedX.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        lblNuevoAcMedX.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNuevoAcMedX.setText("x");

        txtNuevoAcCodigo.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        txtNuevoAcMedVaras.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        txtNuevoAcMedAncho.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        txtNuevoAcMedGrueso.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        txtNuevoAcPrecio.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        placeholder = new TextPrompt("(Opcional)", txtaNuevoAcDescripcion);
        placeholder.changeAlpha(0.75f);
        placeholder.changeStyle(Font.ITALIC);
        txtaNuevoAcDescripcion.setColumns(20);
        txtaNuevoAcDescripcion.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        txtaNuevoAcDescripcion.setRows(5);
        scpnlNuevoAcDescripcion.setViewportView(txtaNuevoAcDescripcion);

        cbxNuevoAcVariedad.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        tblAgregarAserrada.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 14)); // NOI18N
        tblAgregarAserrada.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Tipo de producto", "Variedad de madera", "Medidas", "Cantidad varas", "Precio por vara", "Descripción", "Codigo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblAgregarAserrada.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblAgregarAserrada.getTableHeader().setReorderingAllowed(false);
        scpnlTbAcNuevo.setViewportView(tblAgregarAserrada);
        if (tblAgregarAserrada.getColumnModel().getColumnCount() > 0) {
            tblAgregarAserrada.getColumnModel().getColumn(0).setResizable(false);
            tblAgregarAserrada.getColumnModel().getColumn(0).setPreferredWidth(13);
            tblAgregarAserrada.getColumnModel().getColumn(0).setHeaderValue("Código");
            tblAgregarAserrada.getColumnModel().getColumn(1).setResizable(false);
            tblAgregarAserrada.getColumnModel().getColumn(1).setPreferredWidth(50);
            tblAgregarAserrada.getColumnModel().getColumn(1).setHeaderValue("Tipo de producto");
            tblAgregarAserrada.getColumnModel().getColumn(2).setResizable(false);
            tblAgregarAserrada.getColumnModel().getColumn(2).setPreferredWidth(70);
            tblAgregarAserrada.getColumnModel().getColumn(2).setHeaderValue("Variedad de madera");
            tblAgregarAserrada.getColumnModel().getColumn(3).setResizable(false);
            tblAgregarAserrada.getColumnModel().getColumn(3).setPreferredWidth(30);
            tblAgregarAserrada.getColumnModel().getColumn(3).setHeaderValue("Medidas");
            tblAgregarAserrada.getColumnModel().getColumn(4).setResizable(false);
            tblAgregarAserrada.getColumnModel().getColumn(4).setPreferredWidth(25);
            tblAgregarAserrada.getColumnModel().getColumn(4).setHeaderValue("Cantidad varas");
            tblAgregarAserrada.getColumnModel().getColumn(5).setResizable(false);
            tblAgregarAserrada.getColumnModel().getColumn(5).setHeaderValue("Precio por vara");
            tblAgregarAserrada.getColumnModel().getColumn(6).setResizable(false);
            tblAgregarAserrada.getColumnModel().getColumn(6).setHeaderValue("Descripción");
            tblAgregarAserrada.getColumnModel().getColumn(7).setResizable(false);
            tblAgregarAserrada.getColumnModel().getColumn(7).setPreferredWidth(15);
            tblAgregarAserrada.getColumnModel().getColumn(7).setHeaderValue("Codigo");
        }

        btnNuevaAserrada.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        btnNuevaAserrada.setText("Agregar producto");
        btnNuevaAserrada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevaAserradaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlNuevoAcerradaLayout = new javax.swing.GroupLayout(pnlNuevoAcerrada);
        pnlNuevoAcerrada.setLayout(pnlNuevoAcerradaLayout);
        pnlNuevoAcerradaLayout.setHorizontalGroup(
            pnlNuevoAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNuevoAcerradaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlNuevoAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnlNuevoAcerradaLayout.createSequentialGroup()
                        .addGroup(pnlNuevoAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblNuevoAcCodigo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblNuevoAcVariedad, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
                            .addComponent(cbxNuevoAcVariedad, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtNuevoAcCodigo))
                        .addGap(55, 55, 55)
                        .addGroup(pnlNuevoAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblNuevoAcPrecio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblNuevoAcUnidades, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtNuevoAcMedVaras)
                            .addComponent(txtNuevoAcPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(55, 55, 55)
                        .addGroup(pnlNuevoAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(pnlNuevoAcerradaLayout.createSequentialGroup()
                                .addComponent(txtNuevoAcMedGrueso, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblNuevoAcMedX, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNuevoAcMedAncho, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblNuevoAcMedidas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(55, 55, 55)
                        .addGroup(pnlNuevoAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNuevoAcDescripcion, javax.swing.GroupLayout.DEFAULT_SIZE, 326, Short.MAX_VALUE)
                            .addGroup(pnlNuevoAcerradaLayout.createSequentialGroup()
                                .addComponent(scpnlNuevoAcDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(pnlNuevoAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(btnNuevaAserrada, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(scpnlTbAcNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 1084, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlNuevoAcerradaLayout.setVerticalGroup(
            pnlNuevoAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNuevoAcerradaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlNuevoAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlNuevoAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblNuevoAcCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblNuevoAcUnidades, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblNuevoAcDescripcion, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNuevoAcMedidas, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlNuevoAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlNuevoAcerradaLayout.createSequentialGroup()
                        .addGroup(pnlNuevoAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlNuevoAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtNuevoAcMedAncho, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblNuevoAcMedX, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtNuevoAcMedGrueso, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlNuevoAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(txtNuevoAcMedVaras, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtNuevoAcCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlNuevoAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlNuevoAcerradaLayout.createSequentialGroup()
                                .addComponent(lblNuevoAcVariedad, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(8, 8, 8)
                                .addGroup(pnlNuevoAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(cbxNuevoAcVariedad, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtNuevoAcPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(lblNuevoAcPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(42, 42, 42))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlNuevoAcerradaLayout.createSequentialGroup()
                        .addComponent(scpnlNuevoAcDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)))
                .addComponent(scpnlTbAcNuevo, javax.swing.GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnNuevaAserrada, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        tbNuevoTipoProd.addTab("", new javax.swing.ImageIcon(getClass().getResource("/recursos/inv_acerrada.png")), pnlNuevoAcerrada); // NOI18N

        lblNuevoTmCodigo.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        lblNuevoTmCodigo.setText("Código:");

        lblNuevoTmNombre.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        lblNuevoTmNombre.setText("Nombre/Descripción:");

        lblNuevoTmVariedad.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        lblNuevoTmVariedad.setText("Variedad de madera:");

        lblNuevoTmPrecio.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        lblNuevoTmPrecio.setText("Precio:");

        txtNuevoTmCodigo.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        txtNuevoTmDescripcion.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        txtNuevoTmPrecio.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        cbxNuevoTmVariedad.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        lblNuevoTmCantVaras.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        lblNuevoTmCantVaras.setText("Cantidad en varas:");

        txtNuevoTmCantVaras.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        tblAgregarTerminada.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Tipo de producto", "Variedad de madera", "Cantidad varas", "Precio", "Descripción", "Codigo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblAgregarTerminada.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        scpnlTbTmNuevo.setViewportView(tblAgregarTerminada);

        btnNuevaTerminada.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        btnNuevaTerminada.setText("Agregar producto");
        btnNuevaTerminada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevaTerminadaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlNuevoTerminadaLayout = new javax.swing.GroupLayout(pnlNuevoTerminada);
        pnlNuevoTerminada.setLayout(pnlNuevoTerminadaLayout);
        pnlNuevoTerminadaLayout.setHorizontalGroup(
            pnlNuevoTerminadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNuevoTerminadaLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnlNuevoTerminadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtNuevoTmCodigo)
                    .addComponent(lblNuevoTmNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE)
                    .addComponent(lblNuevoTmCodigo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtNuevoTmDescripcion))
                .addGap(95, 95, 95)
                .addGroup(pnlNuevoTerminadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlNuevoTerminadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(cbxNuevoTmVariedad, 0, 295, Short.MAX_VALUE)
                        .addComponent(lblNuevoTmVariedad, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnlNuevoTerminadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(lblNuevoTmCantVaras, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtNuevoTmCantVaras, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE)))
                .addGap(100, 100, 100)
                .addGroup(pnlNuevoTerminadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txtNuevoTmPrecio, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblNuevoTmPrecio, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlNuevoTerminadaLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlNuevoTerminadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scpnlTbTmNuevo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 1084, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNuevaTerminada, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        pnlNuevoTerminadaLayout.setVerticalGroup(
            pnlNuevoTerminadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNuevoTerminadaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlNuevoTerminadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlNuevoTerminadaLayout.createSequentialGroup()
                        .addGroup(pnlNuevoTerminadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNuevoTmVariedad, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblNuevoTmCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlNuevoTerminadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNuevoTmCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbxNuevoTmVariedad, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNuevoTmPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnlNuevoTerminadaLayout.createSequentialGroup()
                        .addComponent(lblNuevoTmPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37)))
                .addGroup(pnlNuevoTerminadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNuevoTmNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNuevoTmCantVaras, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlNuevoTerminadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNuevoTmDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNuevoTmCantVaras, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(scpnlTbTmNuevo, javax.swing.GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnNuevaTerminada, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        tbNuevoTipoProd.addTab("", new javax.swing.ImageIcon(getClass().getResource("/recursos/inv_terminada.png")), pnlNuevoTerminada); // NOI18N

        lblNuevoTCodigo.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        lblNuevoTCodigo.setText("Código:");

        lblNuevoTVariedadMadera.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        lblNuevoTVariedadMadera.setText("Variedad de madera:");

        lblNuevoTpulgadas.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        lblNuevoTpulgadas.setText("Cantidad en pulgadas:");

        lblNuevoTProveedor.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        lblNuevoTProveedor.setText("Proveedor:");

        lblNuevoTDescripcion.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        lblNuevoTDescripcion.setText("Descripción: ");

        txtNuevoTCodigo.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        placeholder = new TextPrompt("(Opcional)", txtaNuevoAcDescripcion);
        placeholder.changeAlpha(0.75f);
        placeholder.changeStyle(Font.ITALIC);
        txtaNuevoTDescripcion.setColumns(20);
        txtaNuevoTDescripcion.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        txtaNuevoTDescripcion.setRows(5);
        scpnlNuevoTDescripcion.setViewportView(txtaNuevoTDescripcion);

        cbxNuevoTVariedad.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        cbxNuevoTProveedor.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        txtNuevoTpulgadas.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        btnCrearProv.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/f_crearCliente.png"))); // NOI18N

        tblAgregarTroza.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Tipo de producto", "Variedad de madera", "Cantidad pulgadas", "Descripción", "Proveedor", "Codigo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblAgregarTroza.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        scpnlTbTNuevo.setViewportView(tblAgregarTroza);

        btnNuevaTroza.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        btnNuevaTroza.setText("Agregar producto");
        btnNuevaTroza.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevaTrozaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlNuevoTrozaLayout = new javax.swing.GroupLayout(pnlNuevoTroza);
        pnlNuevoTroza.setLayout(pnlNuevoTrozaLayout);
        pnlNuevoTrozaLayout.setHorizontalGroup(
            pnlNuevoTrozaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNuevoTrozaLayout.createSequentialGroup()
                .addGroup(pnlNuevoTrozaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlNuevoTrozaLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(pnlNuevoTrozaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(scpnlTbTNuevo)
                            .addGroup(pnlNuevoTrozaLayout.createSequentialGroup()
                                .addGroup(pnlNuevoTrozaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlNuevoTrozaLayout.createSequentialGroup()
                                        .addComponent(lblNuevoTpulgadas, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(95, 95, 95)
                                        .addGroup(pnlNuevoTrozaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblNuevoTProveedor, javax.swing.GroupLayout.DEFAULT_SIZE, 332, Short.MAX_VALUE)
                                            .addGroup(pnlNuevoTrozaLayout.createSequentialGroup()
                                                .addComponent(cbxNuevoTProveedor, 0, 278, Short.MAX_VALUE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnCrearProv, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addGroup(pnlNuevoTrozaLayout.createSequentialGroup()
                                        .addGroup(pnlNuevoTrozaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtNuevoTpulgadas)
                                            .addComponent(txtNuevoTCodigo)
                                            .addComponent(lblNuevoTCodigo, javax.swing.GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE))
                                        .addGap(95, 95, 95)
                                        .addGroup(pnlNuevoTrozaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(cbxNuevoTVariedad, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(lblNuevoTVariedadMadera, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                .addGap(95, 95, 95)
                                .addGroup(pnlNuevoTrozaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(scpnlNuevoTDescripcion, javax.swing.GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE)
                                    .addComponent(lblNuevoTDescripcion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlNuevoTrozaLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnNuevaTroza, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        pnlNuevoTrozaLayout.setVerticalGroup(
            pnlNuevoTrozaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNuevoTrozaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlNuevoTrozaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(lblNuevoTCodigo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNuevoTDescripcion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblNuevoTVariedadMadera, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlNuevoTrozaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnlNuevoTrozaLayout.createSequentialGroup()
                        .addGroup(pnlNuevoTrozaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNuevoTCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbxNuevoTVariedad, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlNuevoTrozaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblNuevoTpulgadas, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblNuevoTProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlNuevoTrozaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnCrearProv, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnlNuevoTrozaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtNuevoTpulgadas, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cbxNuevoTProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(scpnlNuevoTDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(scpnlTbTNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnNuevaTroza, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        tbNuevoTipoProd.addTab("", new javax.swing.ImageIcon(getClass().getResource("/recursos/inv_troza.png")), pnlNuevoTroza); // NOI18N

        javax.swing.GroupLayout pnl_agregarNuevoLayout = new javax.swing.GroupLayout(pnl_agregarNuevo);
        pnl_agregarNuevo.setLayout(pnl_agregarNuevoLayout);
        pnl_agregarNuevoLayout.setHorizontalGroup(
            pnl_agregarNuevoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_agregarNuevoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tbNuevoTipoProd)
                .addContainerGap())
        );
        pnl_agregarNuevoLayout.setVerticalGroup(
            pnl_agregarNuevoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_agregarNuevoLayout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(tbNuevoTipoProd, javax.swing.GroupLayout.PREFERRED_SIZE, 502, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(101, Short.MAX_VALUE))
        );

        tbpnl_modInventario.addTab("Agregar producto nuevo", pnl_agregarNuevo);

        tbEditarTipoProd.setTabPlacement(javax.swing.JTabbedPane.LEFT);

        lblEditarAcCodigo.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        lblEditarAcCodigo.setText("Código:");

        lblEditarAcMedidas.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        lblEditarAcMedidas.setText("Medidas:");

        lblEditarAcUnidade.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        lblEditarAcUnidade.setText("Cantidad en varas: ");

        lblEditarAcPrecio.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        lblEditarAcPrecio.setText("Precio por vara:");

        lblEditarAcDescripcion.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        lblEditarAcDescripcion.setText("Descripción:");

        lblEditarAcMedX.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblEditarAcMedX.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblEditarAcMedX.setText("x");

        txtEditarAcCodigo.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        txtEditarAcMedAncho.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        txtEditarAcMedGrueso.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        txtEditarAcVaras.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        txtEditarAcPrecio.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        txtaEditarAcDescripcion.setColumns(20);
        txtaEditarAcDescripcion.setRows(3);
        scpnlEditarAcDescripcion.setViewportView(txtaEditarAcDescripcion);

        tblEditarAserrada.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Tipo de producto", "Variedad de madera", "Medidas", "Cantidad varas", "Precio por vara", "Descripción", "Codigo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblEditarAserrada.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblEditarAserrada.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblEditarAserradaMouseClicked(evt);
            }
        });
        scpnlTbAcEditar.setViewportView(tblEditarAserrada);

        btnEditarAserrada.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        btnEditarAserrada.setText("Editar Producto");
        btnEditarAserrada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarAserradaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlEditarAcerradaLayout = new javax.swing.GroupLayout(pnlEditarAcerrada);
        pnlEditarAcerrada.setLayout(pnlEditarAcerradaLayout);
        pnlEditarAcerradaLayout.setHorizontalGroup(
            pnlEditarAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEditarAcerradaLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnlEditarAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scpnlTbAcEditar)
                    .addGroup(pnlEditarAcerradaLayout.createSequentialGroup()
                        .addGroup(pnlEditarAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtEditarAcCodigo, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblEditarAcCodigo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE))
                        .addGap(55, 55, 55)
                        .addGroup(pnlEditarAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtEditarAcVaras)
                            .addComponent(lblEditarAcUnidade, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
                            .addComponent(lblEditarAcPrecio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtEditarAcPrecio))
                        .addGroup(pnlEditarAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlEditarAcerradaLayout.createSequentialGroup()
                                .addGap(57, 57, 57)
                                .addComponent(txtEditarAcMedGrueso, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(197, 197, 197))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlEditarAcerradaLayout.createSequentialGroup()
                                .addGap(55, 55, 55)
                                .addGroup(pnlEditarAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lblEditarAcMedidas, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(pnlEditarAcerradaLayout.createSequentialGroup()
                                        .addComponent(lblEditarAcMedX, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtEditarAcMedAncho, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(65, 65, 65)))
                        .addGroup(pnlEditarAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblEditarAcDescripcion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(scpnlEditarAcDescripcion, javax.swing.GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlEditarAcerradaLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btnEditarAserrada, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(19, 19, 19))
        );
        pnlEditarAcerradaLayout.setVerticalGroup(
            pnlEditarAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEditarAcerradaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlEditarAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnlEditarAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblEditarAcUnidade, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblEditarAcMedidas, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblEditarAcDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblEditarAcCodigo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlEditarAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlEditarAcerradaLayout.createSequentialGroup()
                        .addGroup(pnlEditarAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtEditarAcCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtEditarAcVaras, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblEditarAcPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtEditarAcPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlEditarAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtEditarAcMedGrueso, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblEditarAcMedX, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtEditarAcMedAncho, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(scpnlEditarAcDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(scpnlTbAcEditar, javax.swing.GroupLayout.DEFAULT_SIZE, 294, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEditarAserrada, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        tbEditarTipoProd.addTab("", new javax.swing.ImageIcon(getClass().getResource("/recursos/inv_acerrada.png")), pnlEditarAcerrada); // NOI18N

        lblEditarTmCodigo.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        lblEditarTmCodigo.setText("Código:");

        lblEditarTmNombre.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        lblEditarTmNombre.setText("Nombre:");

        lblEditarTmPrecio.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        lblEditarTmPrecio.setText("Precio:");

        lblEditarTmCantVaras.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        lblEditarTmCantVaras.setText("Cantidad en varas:");

        tblEditarTerminada.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Tipo de producto", "Variedad de madera", "Cantidad varas", "Precio ", "Descripción", "Codigo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblEditarTerminada.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblEditarTerminada.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblEditarTerminadaMouseClicked(evt);
            }
        });
        scpnlTbTmEditar.setViewportView(tblEditarTerminada);

        btnEditarTerminada.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        btnEditarTerminada.setText("Editar Producto");
        btnEditarTerminada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarTerminadaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlEditarTerminadaLayout = new javax.swing.GroupLayout(pnlEditarTerminada);
        pnlEditarTerminada.setLayout(pnlEditarTerminadaLayout);
        pnlEditarTerminadaLayout.setHorizontalGroup(
            pnlEditarTerminadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEditarTerminadaLayout.createSequentialGroup()
                .addGroup(pnlEditarTerminadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlEditarTerminadaLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(scpnlTbTmEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 1062, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlEditarTerminadaLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(pnlEditarTerminadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblEditarTmNombre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtEditarTmCodigo, javax.swing.GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE)
                            .addComponent(txtEditarTmNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE)
                            .addComponent(lblEditarTmCodigo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(95, 95, 95)
                        .addGroup(pnlEditarTerminadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlEditarTerminadaLayout.createSequentialGroup()
                                .addGap(0, 391, Short.MAX_VALUE)
                                .addGroup(pnlEditarTerminadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblEditarTmPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtEditarTmPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnEditarTerminada, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(pnlEditarTerminadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(lblEditarTmCantVaras, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtEditarTmCantVaras, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        pnlEditarTerminadaLayout.setVerticalGroup(
            pnlEditarTerminadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEditarTerminadaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblEditarTmCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtEditarTmCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlEditarTerminadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEditarTmNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEditarTmCantVaras, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEditarTmPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlEditarTerminadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEditarTmNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEditarTmCantVaras, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEditarTmPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(scpnlTbTmEditar, javax.swing.GroupLayout.DEFAULT_SIZE, 293, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEditarTerminada, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        tbEditarTipoProd.addTab("", new javax.swing.ImageIcon(getClass().getResource("/recursos/inv_terminada.png")), pnlEditarTerminada); // NOI18N

        lblEditarTCodigo.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        lblEditarTCodigo.setText("Código:");

        lblEditarTProveedor.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        lblEditarTProveedor.setText("Proveedor:");

        lblEditarTDescripcion.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        lblEditarTDescripcion.setText("Descripción: ");

        txtEditarTCodigo.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        txtaEditarTDescripcion.setColumns(20);
        txtaEditarTDescripcion.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        txtaEditarTDescripcion.setRows(3);
        txtaEditarTDescripcion.setTabSize(5);
        scpnlEditarTDescripcion.setViewportView(txtaEditarTDescripcion);

        cbxEditarTProveedor.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        btnCrearProv1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/f_crearCliente.png"))); // NOI18N

        lblEditarTpulgadas.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        lblEditarTpulgadas.setText("Cantidad en pulgadas:");

        txtEditarTPulgadas.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        tblEditarTroza.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Tipo de producto", "Variedad de madera", "Cantidad pulgadas", "Descripción", "Proveedor", "Codigo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblEditarTroza.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblEditarTroza.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblEditarTrozaMouseClicked(evt);
            }
        });
        scpnlTbTEditar.setViewportView(tblEditarTroza);

        btnEditarTroza.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        btnEditarTroza.setText("Editar Producto");
        btnEditarTroza.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarTrozaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlEditarTrozaLayout = new javax.swing.GroupLayout(pnlEditarTroza);
        pnlEditarTroza.setLayout(pnlEditarTrozaLayout);
        pnlEditarTrozaLayout.setHorizontalGroup(
            pnlEditarTrozaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEditarTrozaLayout.createSequentialGroup()
                .addGroup(pnlEditarTrozaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlEditarTrozaLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(pnlEditarTrozaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlEditarTrozaLayout.createSequentialGroup()
                                .addGroup(pnlEditarTrozaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtEditarTCodigo)
                                    .addComponent(lblEditarTCodigo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblEditarTpulgadas, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtEditarTPulgadas))
                                .addGap(95, 95, 95)
                                .addGroup(pnlEditarTrozaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlEditarTrozaLayout.createSequentialGroup()
                                        .addComponent(cbxEditarTProveedor, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnCrearProv1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(lblEditarTProveedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(96, 96, 96)
                                .addGroup(pnlEditarTrozaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblEditarTDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(scpnlEditarTDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(scpnlTbTEditar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 1061, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlEditarTrozaLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnEditarTroza, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(91, 91, 91))
        );
        pnlEditarTrozaLayout.setVerticalGroup(
            pnlEditarTrozaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEditarTrozaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlEditarTrozaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlEditarTrozaLayout.createSequentialGroup()
                        .addGap(109, 109, 109)
                        .addGroup(pnlEditarTrozaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnCrearProv1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbxEditarTProveedor)))
                    .addGroup(pnlEditarTrozaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlEditarTrozaLayout.createSequentialGroup()
                            .addComponent(lblEditarTDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(scpnlEditarTDescripcion))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlEditarTrozaLayout.createSequentialGroup()
                            .addComponent(lblEditarTCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtEditarTCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(pnlEditarTrozaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblEditarTpulgadas, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblEditarTProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtEditarTPulgadas, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(scpnlTbTEditar, javax.swing.GroupLayout.DEFAULT_SIZE, 298, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEditarTroza, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        tbEditarTipoProd.addTab("", new javax.swing.ImageIcon(getClass().getResource("/recursos/inv_troza.png")), pnlEditarTroza); // NOI18N

        javax.swing.GroupLayout pnl_editarLayout = new javax.swing.GroupLayout(pnl_editar);
        pnl_editar.setLayout(pnl_editarLayout);
        pnl_editarLayout.setHorizontalGroup(
            pnl_editarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_editarLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(tbEditarTipoProd, javax.swing.GroupLayout.PREFERRED_SIZE, 1164, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85))
        );
        pnl_editarLayout.setVerticalGroup(
            pnl_editarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_editarLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(tbEditarTipoProd, javax.swing.GroupLayout.PREFERRED_SIZE, 522, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        tbpnl_modInventario.addTab("Editar producto", pnl_editar);

        tbDeshab.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        pnlActivos.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        txtBuscarActivo.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        txtBuscarActivo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarActivoKeyReleased(evt);
            }
        });

        tbProductosActivos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Tipo de producto", "Variedad de madera", "Medidas", "Cantidad", "Precio por vara", "Descripción", "Proveedor", "Codigo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbProductosActivos.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tbProductosActivos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbProductosActivosMouseClicked(evt);
            }
        });
        scpnlProductoActivo.setViewportView(tbProductosActivos);

        javax.swing.GroupLayout pnlActivosLayout = new javax.swing.GroupLayout(pnlActivos);
        pnlActivos.setLayout(pnlActivosLayout);
        pnlActivosLayout.setHorizontalGroup(
            pnlActivosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlActivosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlActivosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtBuscarActivo)
                    .addComponent(scpnlProductoActivo, javax.swing.GroupLayout.DEFAULT_SIZE, 1128, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlActivosLayout.setVerticalGroup(
            pnlActivosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlActivosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtBuscarActivo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scpnlProductoActivo, javax.swing.GroupLayout.DEFAULT_SIZE, 345, Short.MAX_VALUE)
                .addContainerGap())
        );

        tbDeshab.addTab("Activos", pnlActivos);

        txtBuscarInactivo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarInactivoKeyReleased(evt);
            }
        });

        tbProductosInactivos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Tipo de producto", "Variedad de madera", "Medidas", "Cantidad", "Precio por vara", "Descripción", "Proveedor", "Codigo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbProductosInactivos.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tbProductosInactivos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbProductosInactivosMouseClicked(evt);
            }
        });
        scpnlProductoInactivo.setViewportView(tbProductosInactivos);

        javax.swing.GroupLayout pnlInactivosLayout = new javax.swing.GroupLayout(pnlInactivos);
        pnlInactivos.setLayout(pnlInactivosLayout);
        pnlInactivosLayout.setHorizontalGroup(
            pnlInactivosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInactivosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlInactivosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtBuscarInactivo, javax.swing.GroupLayout.DEFAULT_SIZE, 1128, Short.MAX_VALUE)
                    .addComponent(scpnlProductoInactivo))
                .addContainerGap())
        );
        pnlInactivosLayout.setVerticalGroup(
            pnlInactivosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInactivosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtBuscarInactivo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scpnlProductoInactivo, javax.swing.GroupLayout.DEFAULT_SIZE, 345, Short.MAX_VALUE)
                .addContainerGap())
        );

        tbDeshab.addTab("Inactivos", pnlInactivos);

        pnlDeshabContainer.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Activo:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Yu Gothic UI", 0, 18))); // NOI18N

        bgDeshab.add(rbDeshabDeshabProducto);
        rbDeshabDeshabProducto.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        rbDeshabDeshabProducto.setSelected(true);
        rbDeshabDeshabProducto.setText("Deshabilitar");

        bgDeshab.add(rbDeshabHabilitarProducto);
        rbDeshabHabilitarProducto.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        rbDeshabHabilitarProducto.setText("Habilitar");

        javax.swing.GroupLayout pnlDeshabContainerLayout = new javax.swing.GroupLayout(pnlDeshabContainer);
        pnlDeshabContainer.setLayout(pnlDeshabContainerLayout);
        pnlDeshabContainerLayout.setHorizontalGroup(
            pnlDeshabContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDeshabContainerLayout.createSequentialGroup()
                .addComponent(rbDeshabHabilitarProducto)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                .addComponent(rbDeshabDeshabProducto)
                .addContainerGap())
        );
        pnlDeshabContainerLayout.setVerticalGroup(
            pnlDeshabContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDeshabContainerLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlDeshabContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbDeshabHabilitarProducto)
                    .addComponent(rbDeshabDeshabProducto)))
        );

        btn_deshabilitar.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        btn_deshabilitar.setText("Guardar Cambios");
        btn_deshabilitar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deshabilitarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlHabilitarLayout = new javax.swing.GroupLayout(pnlHabilitar);
        pnlHabilitar.setLayout(pnlHabilitarLayout);
        pnlHabilitarLayout.setHorizontalGroup(
            pnlHabilitarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHabilitarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlHabilitarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnlHabilitarLayout.createSequentialGroup()
                        .addComponent(pnlDeshabContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_deshabilitar, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(tbDeshab, javax.swing.GroupLayout.PREFERRED_SIZE, 1157, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(90, 90, 90))
        );
        pnlHabilitarLayout.setVerticalGroup(
            pnlHabilitarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlHabilitarLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(tbDeshab, javax.swing.GroupLayout.PREFERRED_SIZE, 447, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlHabilitarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pnlDeshabContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_deshabilitar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17))
        );

        tbpnl_modInventario.addTab("Habilitar inventario", pnlHabilitar);

        javax.swing.GroupLayout pnl_modInventarioLayout = new javax.swing.GroupLayout(pnl_modInventario);
        pnl_modInventario.setLayout(pnl_modInventarioLayout);
        pnl_modInventarioLayout.setHorizontalGroup(
            pnl_modInventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_modInventarioLayout.createSequentialGroup()
                .addComponent(tbpnl_modInventario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 89, Short.MAX_VALUE))
        );
        pnl_modInventarioLayout.setVerticalGroup(
            pnl_modInventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_modInventarioLayout.createSequentialGroup()
                .addGap(0, 13, Short.MAX_VALUE)
                .addComponent(tbpnl_modInventario, javax.swing.GroupLayout.PREFERRED_SIZE, 678, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnl_modInventario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnl_modInventario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_deshabilitarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deshabilitarActionPerformed
        prepararDesHabProducto();
    }//GEN-LAST:event_btn_deshabilitarActionPerformed

    private void btnActualizarAserradaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarAserradaActionPerformed
        sumarAserrada();
    }//GEN-LAST:event_btnActualizarAserradaActionPerformed

    private void txtListadoInventarioKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtListadoInventarioKeyReleased
        productos = ctrMadera.consultarProductos(txtListadoInventario.getText().trim());
        trozas = ctrTroza.consultarTrozas(txtListadoInventario.getText().trim());
        cargarJTableGeneral(tbListadoInventario, true,"troza&producto");
    }//GEN-LAST:event_txtListadoInventarioKeyReleased

    private void jFormattedTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFormattedTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jFormattedTextField1ActionPerformed

    private void btnNuevaTrozaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevaTrozaActionPerformed
        prepararCrearProducto("TROZA");
    }//GEN-LAST:event_btnNuevaTrozaActionPerformed

    private void btnEditarAserradaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarAserradaActionPerformed
        prepararEditarProducto("ASERRADA");
    }//GEN-LAST:event_btnEditarAserradaActionPerformed

    private void tblEditarTrozaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblEditarTrozaMouseClicked
        cargarEditarTroza();
    }//GEN-LAST:event_tblEditarTrozaMouseClicked

    private void txtBuscarActivoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarActivoKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarActivoKeyReleased

    private void tbProductosActivosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbProductosActivosMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbProductosActivosMouseClicked

    private void txtBuscarInactivoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarInactivoKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarInactivoKeyReleased

    private void tbProductosInactivosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbProductosInactivosMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbProductosInactivosMouseClicked

    private void btnActualizarTerminadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarTerminadaActionPerformed
        sumarTerminada();
    }//GEN-LAST:event_btnActualizarTerminadaActionPerformed

    private void btnActualizarTrozaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarTrozaActionPerformed
        sumarTroza();
    }//GEN-LAST:event_btnActualizarTrozaActionPerformed

    private void txtActAcEntraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtActAcEntraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtActAcEntraActionPerformed

    private void btnEditarTerminadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarTerminadaActionPerformed
        prepararEditarProducto("TERMINADA");
    }//GEN-LAST:event_btnEditarTerminadaActionPerformed

    private void btnEditarTrozaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarTrozaActionPerformed
        prepararEditarProducto("TROZA");
    }//GEN-LAST:event_btnEditarTrozaActionPerformed

    private void btnNuevaTerminadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevaTerminadaActionPerformed
        prepararCrearProducto("TERMINADA");
    }//GEN-LAST:event_btnNuevaTerminadaActionPerformed

    private void btnNuevaAserradaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevaAserradaActionPerformed
        prepararCrearProducto("ASERRADA");
    }//GEN-LAST:event_btnNuevaAserradaActionPerformed

    private void tblEditarTerminadaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblEditarTerminadaMouseClicked
        System.out.println("Mouse clicked FROM TBLEDITARTERMINADA");
        cargarEditarProducto("TERMINADA");
    }//GEN-LAST:event_tblEditarTerminadaMouseClicked

    private void tblEditarAserradaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblEditarAserradaMouseClicked
        cargarEditarProducto("ASERRADA");
    }//GEN-LAST:event_tblEditarAserradaMouseClicked

    private void txtActAcBuscTrzKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtActAcBuscTrzKeyReleased
        //if (txtActAcBuscTrz.getText().trim().isEmpty()) {
            //System.out.println("LIST IS EMPTY!");
            //lsActAsSelTrz.setModel(new DefaultListModel());
        //} else {
            buscarTrozaOrigen(txtActAcBuscTrz.getText().trim());
        //}
    }//GEN-LAST:event_txtActAcBuscTrzKeyReleased

    private void btnListarProductosRefrescarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnListarProductosRefrescarActionPerformed
        cargarTablas();
    }//GEN-LAST:event_btnListarProductosRefrescarActionPerformed

    private void txtActTmBuscAsKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtActTmBuscAsKeyReleased
        buscarAserradaOrigen(txtActTmBuscAs.getText().trim());
    }//GEN-LAST:event_txtActTmBuscAsKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgDeshab;
    private javax.swing.JButton btnActualizarAserrada;
    private javax.swing.JButton btnActualizarTerminada;
    private javax.swing.JButton btnActualizarTroza;
    private javax.swing.JButton btnCrearProv;
    private javax.swing.JButton btnCrearProv1;
    private javax.swing.JButton btnEditarAserrada;
    private javax.swing.JButton btnEditarTerminada;
    private javax.swing.JButton btnEditarTroza;
    private javax.swing.JButton btnListarProductosRefrescar;
    private javax.swing.JButton btnNuevaAserrada;
    private javax.swing.JButton btnNuevaTerminada;
    private javax.swing.JButton btnNuevaTroza;
    private javax.swing.JButton btn_deshabilitar;
    private javax.swing.JComboBox<Proveedor> cbxEditarTProveedor;
    private javax.swing.JComboBox<Object> cbxNuevoAcVariedad;
    private javax.swing.JComboBox<Object> cbxNuevoTProveedor;
    private javax.swing.JComboBox<Object> cbxNuevoTVariedad;
    private javax.swing.JComboBox<Object> cbxNuevoTmVariedad;
    private javax.swing.JFormattedTextField jFormattedTextField1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lblActAcEntra;
    private javax.swing.JLabel lblActAcOp;
    private javax.swing.JLabel lblActAcOp1;
    private javax.swing.JLabel lblActAcOp2;
    private javax.swing.JLabel lblActAcOp3;
    private javax.swing.JLabel lblActAcOp4;
    private javax.swing.JLabel lblActAcSalen;
    private javax.swing.JLabel lblActTEntra;
    private javax.swing.JLabel lblActTmEntra;
    private javax.swing.JLabel lblActTmSalen;
    private javax.swing.JLabel lblEditarAcCodigo;
    private javax.swing.JLabel lblEditarAcDescripcion;
    private javax.swing.JLabel lblEditarAcMedX;
    private javax.swing.JLabel lblEditarAcMedidas;
    private javax.swing.JLabel lblEditarAcPrecio;
    private javax.swing.JLabel lblEditarAcUnidade;
    private javax.swing.JLabel lblEditarTCodigo;
    private javax.swing.JLabel lblEditarTDescripcion;
    private javax.swing.JLabel lblEditarTProveedor;
    private javax.swing.JLabel lblEditarTmCantVaras;
    private javax.swing.JLabel lblEditarTmCodigo;
    private javax.swing.JLabel lblEditarTmNombre;
    private javax.swing.JLabel lblEditarTmPrecio;
    private javax.swing.JLabel lblEditarTpulgadas;
    private javax.swing.JLabel lblListadoInventario;
    private javax.swing.JLabel lblNuevoAcCodigo;
    private javax.swing.JLabel lblNuevoAcDescripcion;
    private javax.swing.JLabel lblNuevoAcMedX;
    private javax.swing.JLabel lblNuevoAcMedidas;
    private javax.swing.JLabel lblNuevoAcPrecio;
    private javax.swing.JLabel lblNuevoAcUnidades;
    private javax.swing.JLabel lblNuevoAcVariedad;
    private javax.swing.JLabel lblNuevoTCodigo;
    private javax.swing.JLabel lblNuevoTDescripcion;
    private javax.swing.JLabel lblNuevoTProveedor;
    private javax.swing.JLabel lblNuevoTVariedadMadera;
    private javax.swing.JLabel lblNuevoTmCantVaras;
    private javax.swing.JLabel lblNuevoTmCodigo;
    private javax.swing.JLabel lblNuevoTmNombre;
    private javax.swing.JLabel lblNuevoTmPrecio;
    private javax.swing.JLabel lblNuevoTmVariedad;
    private javax.swing.JLabel lblNuevoTpulgadas;
    private javax.swing.JList lsActAsSelTrz;
    private javax.swing.JList<Madera> lsActTmSelAs;
    private javax.swing.JPanel pnSeleccionarRegAserrada;
    private javax.swing.JPanel pnlActivos;
    private javax.swing.JPanel pnlActualizarAcerrada;
    private javax.swing.JPanel pnlActualizarTerminada;
    private javax.swing.JPanel pnlActualizarTroza;
    private javax.swing.JPanel pnlDeshabContainer;
    private javax.swing.JPanel pnlEditarAcerrada;
    private javax.swing.JPanel pnlEditarTerminada;
    private javax.swing.JPanel pnlEditarTroza;
    private javax.swing.JPanel pnlHabilitar;
    private javax.swing.JPanel pnlInactivos;
    private javax.swing.JPanel pnlNuevoAcerrada;
    private javax.swing.JPanel pnlNuevoTerminada;
    private javax.swing.JPanel pnlNuevoTroza;
    private javax.swing.JPanel pnl_actualizar;
    private javax.swing.JPanel pnl_agregarNuevo;
    private javax.swing.JPanel pnl_editar;
    private javax.swing.JPanel pnl_listado;
    private javax.swing.JPanel pnl_modInventario;
    private javax.swing.JRadioButton rbDeshabDeshabProducto;
    private javax.swing.JRadioButton rbDeshabHabilitarProducto;
    private javax.swing.JScrollPane scpnlEditarAcDescripcion;
    private javax.swing.JScrollPane scpnlEditarTDescripcion;
    private javax.swing.JScrollPane scpnlNuevoAcDescripcion;
    private javax.swing.JScrollPane scpnlNuevoTDescripcion;
    private javax.swing.JScrollPane scpnlProductoActivo;
    private javax.swing.JScrollPane scpnlProductoInactivo;
    private javax.swing.JScrollPane scpnlTbAcEditar;
    private javax.swing.JScrollPane scpnlTbAcNuevo;
    private javax.swing.JScrollPane scpnlTbTEditar;
    private javax.swing.JScrollPane scpnlTbTNuevo;
    private javax.swing.JScrollPane scpnlTbTmEditar;
    private javax.swing.JScrollPane scpnlTbTmNuevo;
    private javax.swing.JScrollPane scpnlTblAcActualizar;
    private javax.swing.JScrollPane scpnlTblListadoInventario;
    private javax.swing.JScrollPane scpnlTblTActualizar;
    private javax.swing.JScrollPane scpnlTblTmActualizar;
    private javax.swing.JScrollPane spActAsSelTrz;
    private javax.swing.JScrollPane spActTmSelAs;
    private javax.swing.JTable tbActAserrada;
    private javax.swing.JTable tbActTerminada;
    private javax.swing.JTable tbActTroza;
    private javax.swing.JTabbedPane tbActualizarTipoProd;
    private javax.swing.JTabbedPane tbDeshab;
    private javax.swing.JTabbedPane tbEditarTipoProd;
    private javax.swing.JTable tbListadoInventario;
    private javax.swing.JTabbedPane tbNuevoTipoProd;
    private javax.swing.JTable tbProductosActivos;
    private javax.swing.JTable tbProductosInactivos;
    private javax.swing.JTable tblAgregarAserrada;
    private javax.swing.JTable tblAgregarTerminada;
    private javax.swing.JTable tblAgregarTroza;
    private javax.swing.JTable tblEditarAserrada;
    private javax.swing.JTable tblEditarTerminada;
    private javax.swing.JTable tblEditarTroza;
    private javax.swing.JTabbedPane tbpnl_modInventario;
    private javax.swing.JTextField txtActAcBuscTrz;
    private javax.swing.JTextField txtActAcEntra;
    private javax.swing.JTextField txtActAcSalenPulg;
    private javax.swing.JTextField txtActTEntra;
    private javax.swing.JTextField txtActTmBuscAs;
    private javax.swing.JTextField txtActTmEntra;
    private javax.swing.JTextField txtActTmSalenVaras;
    private javax.swing.JTextField txtBuscarActivo;
    private javax.swing.JTextField txtBuscarInactivo;
    private javax.swing.JTextField txtEditarAcCodigo;
    private javax.swing.JTextField txtEditarAcMedAncho;
    private javax.swing.JTextField txtEditarAcMedGrueso;
    private javax.swing.JTextField txtEditarAcPrecio;
    private javax.swing.JTextField txtEditarAcVaras;
    private javax.swing.JTextField txtEditarTCodigo;
    private javax.swing.JTextField txtEditarTPulgadas;
    private javax.swing.JTextField txtEditarTmCantVaras;
    private javax.swing.JTextField txtEditarTmCodigo;
    private javax.swing.JTextField txtEditarTmNombre;
    private javax.swing.JTextField txtEditarTmPrecio;
    private javax.swing.JTextField txtListadoInventario;
    private javax.swing.JTextField txtNuevoAcCodigo;
    private javax.swing.JTextField txtNuevoAcMedAncho;
    private javax.swing.JTextField txtNuevoAcMedGrueso;
    private javax.swing.JTextField txtNuevoAcMedVaras;
    private javax.swing.JTextField txtNuevoAcPrecio;
    private javax.swing.JTextField txtNuevoTCodigo;
    private javax.swing.JTextField txtNuevoTmCantVaras;
    private javax.swing.JTextField txtNuevoTmCodigo;
    private javax.swing.JTextField txtNuevoTmDescripcion;
    private javax.swing.JTextField txtNuevoTmPrecio;
    private javax.swing.JTextField txtNuevoTpulgadas;
    private javax.swing.JTextArea txtaEditarAcDescripcion;
    private javax.swing.JTextArea txtaEditarTDescripcion;
    private javax.swing.JTextArea txtaNuevoAcDescripcion;
    private javax.swing.JTextArea txtaNuevoTDescripcion;
    // End of variables declaration//GEN-END:variables

}
