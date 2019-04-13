/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacion;

import controladores.CtrAcceso;
import controladores.CtrMadera;
import controladores.CtrProveedor;
import controladores.CtrTipoMadera;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import logica.negocio.Madera;
import logica.negocio.Proveedor;
import logica.negocio.TipoMadera;
import logica.servicios.Mensaje;
import logica.servicios.Regex;
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
    private static ArrayList<Proveedor> proveedores;
    private static ArrayList<TipoMadera> tmaderas;
    private static CtrAcceso sesion;
    private static CtrMadera controlador;
    private static CtrProveedor ctrProveedor;
    private static CtrTipoMadera ctrTipoMadera;
    private static DefaultTableModel model;
    private static ItnFrmInventario instancia = null;    
    private static Mensaje msg;
    private final Regex verificacion;
    private TextPrompt placeholder;

    /**
     * Instancia un nuevo formulario interno de clientes.
     *
     * @param sesionAcc Usuario en sesión actual
     * @param productos Lista con los productos en la base de datos
     */
    public ItnFrmInventario(CtrAcceso sesionAcc, ArrayList<Madera> productos) {
        initComponents();

        //Inicializar variables
        controlador = CtrMadera.getInstancia();
        ItnFrmInventario.sesion = sesionAcc;
        ItnFrmInventario.productos = productos;
        ctrProveedor = new CtrProveedor();
        ctrTipoMadera = new CtrTipoMadera();
        proveedores = new ArrayList<>();
        tmaderas = new ArrayList<>();
        verificacion = new Regex();
        msg = new Mensaje();
        
        cargarCombos();
        cargarTablas();
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
        
        proveedores = ctrProveedor.obtenerProveedores();  
        for (Proveedor item : proveedores) {
            cbxNuevoTProveedor.addItem(item);
            cbxEditarTProveedor.addItem(item);
        }
        if(proveedores.size() >0) {
            cbxNuevoTProveedor.setSelectedIndex(0);
        }
        
        
        tmaderas = ctrTipoMadera.obtenerTiposMadera();
        tmaderas.forEach((item) -> {
            cbxNuevoTVariedad.addItem(item);            
            cbxEditarTVariedad.addItem(item);
            cbxNuevoAcVariedad.addItem(item);
            cbxEditarAcVariedad.addItem(item);
            cbxNuevoTmVariedad.addItem(item);
            cbxEditarTmVariedad.addItem(item);
        });
        cbxNuevoTVariedad.setSelectedIndex(0);
        
        productos = controlador.obtenerProductos();  
        for (Madera item : productos) {
            if (item.getTipoProducto().toUpperCase().equals("ASERRADA")) {
                cbxActAcCodigo.addItem(item);
            } else if (item.getTipoProducto().toUpperCase().equals("TROZA")) {
                cbxActTCodigo.addItem(item);
            } else {
                cbxActTmCodigo.addItem(item);
            }
        }
        
    }

    /**
     * Llena las tablas del modulo con los productos.
     */
    private void cargarTablas() {

        productos = controlador.obtenerProductos();
        cargarProductosJTable(tbListadoInventario, true);
        cargarProductosJTable(tblAcAgregarInv, true);
        cargarProductosJTable(tblAcEditar, true);
        cargarProductosJTable(tbProductosActivos, true);
        cargarProductosJTable(tbProductosInactivos, false);
        cargarProductosJTable(tbActAserrada, true);
        cargarProductosJTable(tbActTroza, true);
        cargarProductosJTable(tbActTerminada, true);
    }

    /**
     * Cargar la tabla (modelo) con los usuarios existentes.
     * @param tabla Nombre de la tabla a llenar
     * @param estado Estado del producto a incresar
     */
    public void cargarProductosJTable(JTable tabla, boolean estado) {
        Object[] row = new Object[9];
        model = (DefaultTableModel) tabla.getModel();
        model.setRowCount(0);
        model.setColumnCount(9);
        
        System.out.println("PROD SIZE: " + productos.size());
        for (int i = 0; i < productos.size(); i++) {
            //tipo prod- codigo- variedad- grueso- ancho- proveedor- unidades- precio- descripción
            if (productos.get(i).getEstado().equals(Estado.Activo) && estado) {
                System.out.println("AGREGAR A TABLA");
                row[0] = productos.get(i).getTipoProducto();
                row[1] = productos.get(i).getCodProducto();////////
                row[2] = productos.get(i).getDescTipoMadera();
                row[3] = productos.get(i).getGrueso() + " x " + productos.get(i).getAncho();               
                row[4] = productos.get(i).getTipoProducto().equals("TROZA") ? 
                        productos.get(i).getPulgadas() + " pulg" : 
                        productos.get(i).getCantVaras() + "varas";
                row[5] = productos.get(i).getPrecioXvara();
                row[6] = productos.get(i).getNomProveedor();//getCodProveedor() == null ? "No aplica" : productos.get(i).getCodProveedor();
                row[7] = productos.get(i).getDescripcion();
                row[8] = productos.get(i).getCodigo();
                
                model.addRow(row);
            }
            if (productos.get(i).getEstado().equals(Estado.Deshabilitado) && !estado) {
                row[0] = productos.get(i).getTipoProducto();
                row[1] = productos.get(i).getCodProducto();////////
                row[2] = productos.get(i).getDescTipoMadera();
                row[3] = productos.get(i).getGrueso() + " x " + productos.get(i).getAncho();               
                row[4] = productos.get(i).getTipoProducto().equals("TROZA") ? 
                        productos.get(i).getPulgadas() + " pulg" : 
                        productos.get(i).getCantVaras() + "varas";
                row[5] = productos.get(i).getPrecioXvara();
                row[6] = productos.get(i).getNomProveedor();//getCodProveedor() == null ? "No aplica" : productos.get(i).getCodProveedor();
                row[7] = productos.get(i).getDescripcion();
                row[8] = productos.get(i).getCodigo();
                
                model.addRow(row);
            }
        }
        
        tabla.removeColumn(tabla.getColumnModel().getColumn(8));
    }
    public String verificarTipoMadera(String panel) {
        
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
     * Limpia los campos de texto del panel, según el nombre del botón que se
     * presiona.
     *
     * @param panel presionado
     * @param tmad tipo de madera
     */
    public void limpiarCampos(String panel, String tmad) {

        if (panel.toUpperCase().equals("CREAR")) {
            switch (tmad.toUpperCase()) {
                case "ASERRADA":
                    txtNuevoAcCodigo.setText("");
                    txtNuevoAcMedAncho.setText("");
                    txtNuevoAcMedGrueso.setText("");
                    txtNuevoAcMedVaras.setText("");
                    txtNuevoAcPrecio.setText("");
                    txtNuevoAcMedVaras.setText("");
                    txtaNuevoAcDescripcion.setText("");
                    //cbxNuevoAcVariedad.removeAllItems();
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
                    txtNuevoTmNombre.setText("");
                    txtNuevoTmPrecio.setText("");
                    cbxNuevoTmVariedad.removeAll();
                    cargarCombos();
                    break;
                default:
                    break;
            }
        } else if (panel.toUpperCase().equals("EDITAR")) {
            switch (tmad.toUpperCase()) {
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
                    txtEditarTMedPulgadas.setText("");
                    txtaEditarTDescripcion.setText("");
                    cbxEditarTVariedad.removeAllItems();
                    //cbxEditarTProveedor.removeAllItems();
                    cargarCombos();
                    break;
                case "TERMINADA":
                    txtEditarTmCodigo.setText("");
                    txtEditarTmNombre.setText("");
                    txtEditarTmPrecio.setText("");
                    //cbxEditarTmVariedad.removeAll();
                    cargarCombos();
                    break;
                default:
                    break;
            }
        } else if (panel.toUpperCase().equals("ACTUALIZAR")) {
            switch (tmad.toUpperCase()) {
                case "ASERRADA":
                    txtActAcIngresa.setText("");
                    break;
                case "TROZA":
                    txtActTIngresa.setText("");
                    break;
                case "TERMINADA":
                    txtActTmIngresa.setText("");
                    break;
                default:
                    break;
            }
        }
    }
    
    /**
     * Preparar la información ingresada del producto para enviarlo a crear.
     */
    private void prepararProducto() {
        String codProveedor;
        TipoMadera tipoMad;
        //CREAR MADERA ASERRADA
        if (verificarTipoMadera("CREAR").equals("ASERRADA")) {
            Double cantVaras = Double.valueOf(txtNuevoAcMedVaras.getText().trim());            
            tipoMad = (TipoMadera) cbxNuevoAcVariedad.getSelectedItem();
            Double precio = Double.valueOf(txtNuevoAcPrecio.getText());

            boolean cprod = crearProducto(txtNuevoAcCodigo.getText().trim(),
                    txtaNuevoAcDescripcion.getText().trim(), precio, cantVaras, 
                    txtNuevoAcMedGrueso.getText().trim(),
                    txtNuevoAcMedAncho.getText().trim(),
                    0.0, Integer.valueOf(tipoMad.getCodigo()), 
                    verificarTipoMadera("CREAR"), 0);
            if(cprod) {
                limpiarCampos("Crear","ASERRADA");
            }
        //CREAR MADERA TROZA
        } else if (verificarTipoMadera("CREAR").equals("TROZA")) {

            Double pulgadas = Double.valueOf(txtNuevoTpulgadas.getText().trim());
            tipoMad = (TipoMadera) cbxNuevoTVariedad.getSelectedItem();
            Proveedor pv = (Proveedor) cbxNuevoTProveedor.getSelectedItem();

            boolean cprod = crearProducto(txtNuevoTCodigo.getText(),
                    txtaNuevoTDescripcion.getText().trim(),
                    0, 0, "0", "0", pulgadas, Integer.valueOf(tipoMad.getCodigo()), 
                    verificarTipoMadera("CREAR"), 
                    Integer.valueOf(pv.getCodProveedor()));
            if(cprod) {
                limpiarCampos("Crear","TROZA");
            }
        //CREAR MADERA TEMRINADA
        } else if (verificarTipoMadera("CREAR").equals("TERMINADA")) {

            tipoMad = (TipoMadera) cbxNuevoTmVariedad.getSelectedItem();
            cantVaras = txtNuevoTmCantVaras.getText().trim();

            boolean cprod = crearProducto(txtNuevoTmCodigo.getText(),
                    tipoMad.getCodigo(), "0", verificarTipoMadera("CREAR"), cantVaras,
                    txtNuevoTmPrecio.getText().trim(),
                    txtNuevoTmNombre.getText().trim(), "0");
            if(cprod) {
                limpiarCampos("Crear","TERMINADA");
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
     * @return 
     */
    private boolean crearProducto(String codProd, String descripcion, 
            double precio, double cantVaras, String grueso, String ancho,
            double pulgadas, int codTipoMadera, String tipoProducto,
            int codProveedor) {
        
        String p = String.valueOf(precio);
        String cv = String.valueOf(cantVaras);
        String pg = String.valueOf(pulgadas);
        String cdTpM = String.valueOf(codTipoMadera);
        String cdP = String.valueOf(codProveedor);
        
        //Campos no están vacíos
        if (!codProd.isEmpty() && descripcion.isEmpty() && !p.isEmpty() &&
                !cv.isEmpty() && !grueso.isEmpty() && !ancho.isEmpty() &&
                !pg.isEmpty() && !cdTpM.isEmpty() && 
                !tipoProducto.isEmpty() && !cdP.isEmpty()) {
                        
            //Verificar precio
            if (verificacion.validaPrecio(p)) {
                                   
                System.out.println("AGREGANDO PRODUCTO, PLEASE WAIT... ");
                boolean crear = controlador.crearProducto(codProd, descripcion,
                        precio, cantVaras, grueso, ancho, pulgadas,
                        codTipoMadera, tipoProducto, codProveedor);
                System.out.println("CREAR: " + crear);

                if (crear) {
                    cargarTablas();
                    cargarCombos();                        
                    msg.mostrarMensaje(JOptionPane.INFORMATION_MESSAGE,
                            TipoMensaje.PRODUCT_INSERTION_SUCCESS);                        
                    return true;
                } else {
                    msg.mostrarMensaje(JOptionPane.ERROR_MESSAGE,
                            TipoMensaje.PRODUCT_INSERTION_FAILURE);                        
                }
            } else {
                msg.mostrarMensaje(JOptionPane.INFORMATION_MESSAGE,
                        TipoMensaje.PRICE_SYNTAX_FAILURE);
//                txtCrearPrecioVara.requestFocus();
//                txtCrearPrecioVara.selectAll();
            }
        } else {
            msg.mostrarMensaje(JOptionPane.WARNING_MESSAGE,
                    TipoMensaje.EMPTY_TEXT_FIELD);
        }
        //return false;
    }
    
    /**
     * 
     * @param codProd
     * @param codTipoMadera
     * @param medida
     * @param unidades
     * @param precio
     * @param descripcion
     * @param codProveedor
     * @param codigo
     * @return 
     */
    String codProd, String descripcion, 
            double precio, double cantVaras, String grueso, String ancho,
            double pulgadas, int codTipoMadera, int codProveedor, String codigo
                    
    private boolean actualizarProducto(String codProd, String descripcion, 
            String precio, String cantVaras, String grueso, String ancho, 
            String pulgadas,  String codTipoMadera, String codProveedor, 
            String codigo) {
        
        //Campos no están vacíos
        if (!codProd.isEmpty() && !grueso.isEmpty() !ancho.isEmpty() && !pulgadas.isEmpty() !codTipoMadera.isEmpty() 
                && !unidades.isEmpty() && !precio.isEmpty() 
                && !codProveedor.isEmpty()) {
                        
            //Verificar precio
            if (verificacion.validaPrecio(precio)) {
                
                //Verificar numeros enteros
                /*if (verificacion.validaEnteros(unidades) && 
                        verificacion.validaEnteros(codTipoMadera)) {
                    */
                    double preci = Double.valueOf(precio);
                    int unit = Integer.valueOf(unidades);
                    int cTmadera = Integer.valueOf(codTipoMadera);
                    int cProveedor = Integer.valueOf(codProveedor);
                   
                    System.out.println("ACTUALIZANDO PRODUCTO, PLEASE WAIT... "+ preci);
                    boolean editar = controlador.actualizarProducto(codProd, 
                            cTmadera, medida, unit, preci, descripcion, 
                            cProveedor, codigo);
                    
                    if (editar) {
                        cargarTablas();
                        cargarCombos();                        
                        msg.mostrarMensaje(JOptionPane.INFORMATION_MESSAGE,
                                TipoMensaje.PRODUCT_UPDATE_SUCCESS);
                        
                        return true;
                    } else {
                        msg.mostrarMensaje(JOptionPane.ERROR_MESSAGE,
                                TipoMensaje.PRODUCT_UPDATE_FAILURE);                        
                    }
                /*} else {
                    msg.mostrarMensaje(JOptionPane.WARNING_MESSAGE,
                            TipoMensaje.UNITQUANTITY_SYNTAX_FAILURE);
//                    txtCrearCantidad.requestFocus();
                }*/
            } else {
                msg.mostrarMensaje(JOptionPane.WARNING_MESSAGE,
                        TipoMensaje.PRICE_SYNTAX_FAILURE);
//                txtCrearPrecioVara.requestFocus();
//                txtCrearPrecioVara.selectAll();
            }
        } else {
            msg.mostrarMensaje(JOptionPane.WARNING_MESSAGE,
                    TipoMensaje.EMPTY_TEXT_FIELD);
        }
        return false;
    }
    
    private void cambiarOperacion(String tab, boolean enable) {
        switch (tab) {
            case "ASERRADA":
                txtActAcBuscTrz.setEnabled(enable);
                lsActAsSelTrz.setEnabled(enable);
                txtActAcSalenPulg.setEnabled(enable);
                break;
            case "TERMINADA":
                break;
        }
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
        pnl_actualizar = new javax.swing.JPanel();
        tbActualizarTipoProd = new javax.swing.JTabbedPane();
        pnlActualizarAcerrada = new javax.swing.JPanel();
        lblActAcOp = new javax.swing.JLabel();
        lblActAcIngresa = new javax.swing.JLabel();
        lblActAcDetalle = new javax.swing.JLabel();
        lblActAsVaras = new javax.swing.JLabel();
        txtActAcIngresa = new javax.swing.JTextField();
        scpnltxtaActAcDetalle = new javax.swing.JScrollPane();
        txtaActAcDetalle = new javax.swing.JTextArea();
        scpnlTblAcActualizar = new javax.swing.JScrollPane();
        tbActAserrada = new javax.swing.JTable();
        btnActualizarAserrada = new javax.swing.JButton();
        cbxActAcOp = new javax.swing.JComboBox<>();
        spActAsSelTrz = new javax.swing.JScrollPane();
        lsActAsSelTrz = new javax.swing.JList<>();
        txtActAcBuscTrz = new javax.swing.JTextField();
        lblActAcSalen = new javax.swing.JLabel();
        txtActAcSalenPulg = new javax.swing.JTextField();
        lblActAsPulg = new javax.swing.JLabel();
        pnlActualizarTerminada = new javax.swing.JPanel();
        lblActTmDetalle = new javax.swing.JLabel();
        scpnltxtaActAcDetalle1 = new javax.swing.JScrollPane();
        txtaActTmDetalle = new javax.swing.JTextArea();
        lblActTmOp = new javax.swing.JLabel();
        cbxActTmOp = new javax.swing.JComboBox<>();
        txtActTmBuscAs = new javax.swing.JTextField();
        spActTmSelAs = new javax.swing.JScrollPane();
        lsActTmSelAs = new javax.swing.JList<>();
        lblActTmIngresa = new javax.swing.JLabel();
        txtActTmIngresa = new javax.swing.JTextField();
        lblActTmInVaras = new javax.swing.JLabel();
        txtActTmSalenVaras = new javax.swing.JTextField();
        lblActTmSalen = new javax.swing.JLabel();
        btnActualizarTerminada = new javax.swing.JButton();
        lblActAcSalVaras = new javax.swing.JLabel();
        scpnlTblTmActualizar = new javax.swing.JScrollPane();
        tbActTerminada = new javax.swing.JTable();
        pnlActualizarTroza = new javax.swing.JPanel();
        scpnlTblTActualizar = new javax.swing.JScrollPane();
        tbActTroza = new javax.swing.JTable();
        lblActTDetalle = new javax.swing.JLabel();
        scpnltxtaActAcDetalle2 = new javax.swing.JScrollPane();
        txtaActTDetalle = new javax.swing.JTextArea();
        lblActTOp = new javax.swing.JLabel();
        cbxActTOp = new javax.swing.JComboBox<>();
        lblActTIngresa = new javax.swing.JLabel();
        txtActTIngresa = new javax.swing.JTextField();
        lblActTVaras = new javax.swing.JLabel();
        btnActualizarTroza = new javax.swing.JButton();
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
        cbxNuevoAcOrigen = new javax.swing.JComboBox<>();
        lblNuevoAcOrigen = new javax.swing.JLabel();
        scpnlTbAcNuevo = new javax.swing.JScrollPane();
        tblAcAgregarInv = new javax.swing.JTable();
        pnlNuevoTerminada = new javax.swing.JPanel();
        lblNuevoTmCodigo = new javax.swing.JLabel();
        lblNuevoTmNombre = new javax.swing.JLabel();
        lblNuevoTmVariedad = new javax.swing.JLabel();
        lblNuevoTmPrecio = new javax.swing.JLabel();
        txtNuevoTmCodigo = new javax.swing.JTextField();
        txtNuevoTmNombre = new javax.swing.JTextField();
        txtNuevoTmPrecio = new javax.swing.JTextField();
        cbxNuevoTmVariedad = new javax.swing.JComboBox<>();
        lblNuevoTmCantVaras = new javax.swing.JLabel();
        txtNuevoTmCantVaras = new javax.swing.JTextField();
        cbxNuevoTmOrigen = new javax.swing.JComboBox<>();
        lblNuevoTmOrigen = new javax.swing.JLabel();
        scpnlTbTmNuevo = new javax.swing.JScrollPane();
        tblTmAgregarInv = new javax.swing.JTable();
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
        tblTAgregarInv = new javax.swing.JTable();
        btnNuevo = new javax.swing.JButton();
        pnl_editar = new javax.swing.JPanel();
        tbEditarTipoProd = new javax.swing.JTabbedPane();
        pnlEditarAcerrada = new javax.swing.JPanel();
        lblEditarAcCodigo = new javax.swing.JLabel();
        lblEditarAcVariedad = new javax.swing.JLabel();
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
        cbxEditarAcVariedad = new javax.swing.JComboBox<>();
        scpnlEditarAcDescripcion = new javax.swing.JScrollPane();
        txtaEditarAcDescripcion = new javax.swing.JTextArea();
        cbxEditarAcOrigen = new javax.swing.JComboBox<>();
        lblEditarAcOrigen = new javax.swing.JLabel();
        scpnlTbAcEditar = new javax.swing.JScrollPane();
        tblAcEditar = new javax.swing.JTable();
        pnlEditarTerminada = new javax.swing.JPanel();
        lblEditarTmCodigo = new javax.swing.JLabel();
        lblEditarTmNombre = new javax.swing.JLabel();
        lblEditarTmVariedad = new javax.swing.JLabel();
        lblEditarTmPrecio = new javax.swing.JLabel();
        txtEditarTmCodigo = new javax.swing.JTextField();
        txtEditarTmNombre = new javax.swing.JTextField();
        txtEditarTmPrecio = new javax.swing.JTextField();
        cbxEditarTmVariedad = new javax.swing.JComboBox<>();
        lblEditarTmCantVaras = new javax.swing.JLabel();
        txtEditarTmCantVaras = new javax.swing.JTextField();
        cbxNuevoTmOrigen1 = new javax.swing.JComboBox<>();
        lblNuevoTmOrigen1 = new javax.swing.JLabel();
        scpnlTbTmEditar = new javax.swing.JScrollPane();
        tblTmEditar = new javax.swing.JTable();
        pnlEditarTroza = new javax.swing.JPanel();
        lblEditarTCodigo = new javax.swing.JLabel();
        lblEditarTVariedadMadera = new javax.swing.JLabel();
        lblEditarTProveedor = new javax.swing.JLabel();
        lblEditarTDescripcion = new javax.swing.JLabel();
        txtEditarTCodigo = new javax.swing.JTextField();
        scpnlEditarTDescripcion = new javax.swing.JScrollPane();
        txtaEditarTDescripcion = new javax.swing.JTextArea();
        cbxEditarTVariedad = new javax.swing.JComboBox<>();
        cbxEditarTProveedor = new javax.swing.JComboBox<>();
        btnCrearProv1 = new javax.swing.JButton();
        lblEditarTpulgadas = new javax.swing.JLabel();
        txtEditarTpulgadas1 = new javax.swing.JTextField();
        placeholder = new TextPrompt("Pulgadas", txtNuevoTpulgadas);
        placeholder.changeAlpha(0.75f);
        placeholder.changeStyle(Font.ITALIC);
        scpnlTbTEditar = new javax.swing.JScrollPane();
        tblTEditar = new javax.swing.JTable();
        btnEditar = new javax.swing.JButton();
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

        tbpnl_modInventario.setPreferredSize(new java.awt.Dimension(1208, 645));

        lblListadoInventario.setText("Buscar producto: ");

        txtListadoInventario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtListadoInventarioKeyReleased(evt);
            }
        });

        tbListadoInventario.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Tipo de producto", "Variedad de madera", "Medidas", "Cantidad", "Precio por vara", "Descripción", "Origen", "Proveedor", "Codigo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        scpnlTblListadoInventario.setViewportView(tbListadoInventario);

        javax.swing.GroupLayout pnl_listadoLayout = new javax.swing.GroupLayout(pnl_listado);
        pnl_listado.setLayout(pnl_listadoLayout);
        pnl_listadoLayout.setHorizontalGroup(
            pnl_listadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_listadoLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(pnl_listadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_listadoLayout.createSequentialGroup()
                        .addComponent(lblListadoInventario, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtListadoInventario))
                    .addComponent(scpnlTblListadoInventario, javax.swing.GroupLayout.DEFAULT_SIZE, 1155, Short.MAX_VALUE))
                .addGap(23, 23, 23))
        );
        pnl_listadoLayout.setVerticalGroup(
            pnl_listadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_listadoLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(pnl_listadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtListadoInventario, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblListadoInventario, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(scpnlTblListadoInventario, javax.swing.GroupLayout.DEFAULT_SIZE, 469, Short.MAX_VALUE)
                .addContainerGap())
        );

        tbpnl_modInventario.addTab("Listado productos", pnl_listado);

        tbActualizarTipoProd.setTabPlacement(javax.swing.JTabbedPane.LEFT);

        lblActAcOp.setText("Operación");

        lblActAcIngresa.setText("Ingresa:");

        lblActAcDetalle.setText("Detalle:");

        lblActAsVaras.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        lblActAsVaras.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblActAsVaras.setText("varas.");

        txtaActAcDetalle.setEditable(false);
        txtaActAcDetalle.setBackground(new java.awt.Color(238, 238, 238));
        txtaActAcDetalle.setColumns(5);
        txtaActAcDetalle.setRows(3);
        txtaActAcDetalle.setBorder(javax.swing.BorderFactory.createLineBorder(javax.swing.UIManager.getDefaults().getColor("Button.darkShadow")));
        scpnltxtaActAcDetalle.setViewportView(txtaActAcDetalle);

        tbActAserrada.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Tipo de producto", "Variedad de madera", "Medidas", "Cantidad varas", "Precio por vara", "Descripción", "Origen", "Codigo bd"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        scpnlTblAcActualizar.setViewportView(tbActAserrada);

        btnActualizarAserrada.setText("Actualizar Inventario");
        btnActualizarAserrada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarAserradaActionPerformed(evt);
            }
        });

        cbxActAcOp.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SUMAR", "RESTAR" }));
        cbxActAcOp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxActAcOpActionPerformed(evt);
            }
        });

        spActAsSelTrz.setViewportView(lsActAsSelTrz);

        lblActAcSalen.setText("Sale:");

        lblActAsPulg.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        lblActAsPulg.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblActAsPulg.setText("pulgadas.");

        javax.swing.GroupLayout pnlActualizarAcerradaLayout = new javax.swing.GroupLayout(pnlActualizarAcerrada);
        pnlActualizarAcerrada.setLayout(pnlActualizarAcerradaLayout);
        pnlActualizarAcerradaLayout.setHorizontalGroup(
            pnlActualizarAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlActualizarAcerradaLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnlActualizarAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scpnlTblAcActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 1085, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlActualizarAcerradaLayout.createSequentialGroup()
                        .addGroup(pnlActualizarAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(scpnltxtaActAcDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblActAcDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblActAcOp, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbxActAcOp, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(50, 50, 50)
                        .addGroup(pnlActualizarAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtActAcBuscTrz, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(spActAsSelTrz, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(50, 50, 50)
                        .addGroup(pnlActualizarAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlActualizarAcerradaLayout.createSequentialGroup()
                                .addComponent(txtActAcIngresa, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblActAsVaras, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblActAcIngresa, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnlActualizarAcerradaLayout.createSequentialGroup()
                                .addComponent(txtActAcSalenPulg, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblActAsPulg, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblActAcSalen, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnActualizarAserrada, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlActualizarAcerradaLayout.setVerticalGroup(
            pnlActualizarAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlActualizarAcerradaLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(pnlActualizarAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblActAcDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtActAcBuscTrz, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblActAcIngresa, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlActualizarAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnlActualizarAcerradaLayout.createSequentialGroup()
                        .addGroup(pnlActualizarAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtActAcIngresa, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblActAsVaras, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(lblActAcSalen, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlActualizarAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblActAsPulg, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtActAcSalenPulg, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnActualizarAserrada, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(spActAsSelTrz)
                    .addGroup(pnlActualizarAcerradaLayout.createSequentialGroup()
                        .addComponent(scpnltxtaActAcDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(lblActAcOp, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxActAcOp, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(25, 25, 25)
                .addComponent(scpnlTblAcActualizar, javax.swing.GroupLayout.DEFAULT_SIZE, 253, Short.MAX_VALUE)
                .addContainerGap())
        );

        tbActualizarTipoProd.addTab("", new javax.swing.ImageIcon(getClass().getResource("/recursos/inv_acerrada.png")), pnlActualizarAcerrada, "Madera Acerrada"); // NOI18N

        lblActTmDetalle.setText("Detalle:");

        txtaActTmDetalle.setEditable(false);
        txtaActTmDetalle.setBackground(new java.awt.Color(238, 238, 238));
        txtaActTmDetalle.setColumns(5);
        txtaActTmDetalle.setRows(3);
        txtaActTmDetalle.setBorder(javax.swing.BorderFactory.createLineBorder(javax.swing.UIManager.getDefaults().getColor("Button.darkShadow")));
        scpnltxtaActAcDetalle1.setViewportView(txtaActTmDetalle);

        lblActTmOp.setText("Operación");

        cbxActTmOp.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SUMAR", "RESTAR" }));
        cbxActTmOp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxActTmOpActionPerformed(evt);
            }
        });

        spActTmSelAs.setViewportView(lsActTmSelAs);

        lblActTmIngresa.setText("Ingresa:");

        lblActTmInVaras.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        lblActTmInVaras.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblActTmInVaras.setText("varas.");

        lblActTmSalen.setText("Sale:");

        btnActualizarTerminada.setText("Actualizar Inventario");
        btnActualizarTerminada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarTerminadaActionPerformed(evt);
            }
        });

        lblActAcSalVaras.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        lblActAcSalVaras.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblActAcSalVaras.setText("varas.");

        tbActTerminada.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Tipo de producto", "Variedad de madera", "Medidas", "Cantidad varas", "Precio por vara", "Descripción", "Origen", "Codigo bd"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        scpnlTblTmActualizar.setViewportView(tbActTerminada);

        javax.swing.GroupLayout pnlActualizarTerminadaLayout = new javax.swing.GroupLayout(pnlActualizarTerminada);
        pnlActualizarTerminada.setLayout(pnlActualizarTerminadaLayout);
        pnlActualizarTerminadaLayout.setHorizontalGroup(
            pnlActualizarTerminadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlActualizarTerminadaLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnlActualizarTerminadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scpnlTblTmActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 1085, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlActualizarTerminadaLayout.createSequentialGroup()
                        .addGroup(pnlActualizarTerminadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(scpnltxtaActAcDetalle1, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblActTmDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblActTmOp, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbxActTmOp, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(50, 50, 50)
                        .addGroup(pnlActualizarTerminadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtActTmBuscAs, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(spActTmSelAs, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(50, 50, 50)
                        .addGroup(pnlActualizarTerminadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlActualizarTerminadaLayout.createSequentialGroup()
                                .addComponent(txtActTmIngresa, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblActTmInVaras, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblActTmIngresa, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnlActualizarTerminadaLayout.createSequentialGroup()
                                .addComponent(txtActTmSalenVaras, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblActAcSalVaras, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblActTmSalen, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnActualizarTerminada, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlActualizarTerminadaLayout.setVerticalGroup(
            pnlActualizarTerminadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlActualizarTerminadaLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(pnlActualizarTerminadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblActTmDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtActTmBuscAs, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblActTmIngresa, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlActualizarTerminadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnlActualizarTerminadaLayout.createSequentialGroup()
                        .addGroup(pnlActualizarTerminadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtActTmIngresa, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblActTmInVaras, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(lblActTmSalen, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlActualizarTerminadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblActAcSalVaras, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtActTmSalenVaras, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnActualizarTerminada, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(spActTmSelAs)
                    .addGroup(pnlActualizarTerminadaLayout.createSequentialGroup()
                        .addComponent(scpnltxtaActAcDetalle1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(lblActTmOp, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxActTmOp, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(25, 25, 25)
                .addComponent(scpnlTblTmActualizar, javax.swing.GroupLayout.DEFAULT_SIZE, 253, Short.MAX_VALUE)
                .addContainerGap())
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
        scpnlTblTActualizar.setViewportView(tbActTroza);

        lblActTDetalle.setText("Detalle:");

        txtaActTDetalle.setEditable(false);
        txtaActTDetalle.setBackground(new java.awt.Color(238, 238, 238));
        txtaActTDetalle.setColumns(5);
        txtaActTDetalle.setRows(3);
        txtaActTDetalle.setBorder(javax.swing.BorderFactory.createLineBorder(javax.swing.UIManager.getDefaults().getColor("Button.darkShadow")));
        scpnltxtaActAcDetalle2.setViewportView(txtaActTDetalle);

        lblActTOp.setText("Operación");

        cbxActTOp.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SUMAR", "RESTAR" }));
        cbxActTOp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxActTOpActionPerformed(evt);
            }
        });

        lblActTIngresa.setText("Ingresa:");

        lblActTVaras.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        lblActTVaras.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblActTVaras.setText("varas.");

        btnActualizarTroza.setText("Actualizar Inventario");
        btnActualizarTroza.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarTrozaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlActualizarTrozaLayout = new javax.swing.GroupLayout(pnlActualizarTroza);
        pnlActualizarTroza.setLayout(pnlActualizarTrozaLayout);
        pnlActualizarTrozaLayout.setHorizontalGroup(
            pnlActualizarTrozaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlActualizarTrozaLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnlActualizarTrozaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scpnlTblTActualizar)
                    .addGroup(pnlActualizarTrozaLayout.createSequentialGroup()
                        .addGroup(pnlActualizarTrozaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(scpnltxtaActAcDetalle2, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblActTDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblActTOp, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbxActTOp, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(443, 443, 443)
                        .addGroup(pnlActualizarTrozaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlActualizarTrozaLayout.createSequentialGroup()
                                .addComponent(txtActTIngresa, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblActTVaras, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblActTIngresa, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnActualizarTroza, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 182, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlActualizarTrozaLayout.setVerticalGroup(
            pnlActualizarTrozaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlActualizarTrozaLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(pnlActualizarTrozaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlActualizarTrozaLayout.createSequentialGroup()
                        .addComponent(lblActTIngresa, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlActualizarTrozaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtActTIngresa, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblActTVaras, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(95, 95, 95)
                        .addComponent(btnActualizarTroza, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(pnlActualizarTrozaLayout.createSequentialGroup()
                        .addComponent(lblActTDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(scpnltxtaActAcDetalle2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(lblActTOp, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxActTOp, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25)
                        .addComponent(scpnlTblTActualizar, javax.swing.GroupLayout.DEFAULT_SIZE, 257, Short.MAX_VALUE)))
                .addContainerGap())
        );

        tbActualizarTipoProd.addTab("", new javax.swing.ImageIcon(getClass().getResource("/recursos/inv_troza.png")), pnlActualizarTroza, "Madera en Troza"); // NOI18N

        javax.swing.GroupLayout pnl_actualizarLayout = new javax.swing.GroupLayout(pnl_actualizar);
        pnl_actualizar.setLayout(pnl_actualizarLayout);
        pnl_actualizarLayout.setHorizontalGroup(
            pnl_actualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_actualizarLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(tbActualizarTipoProd)
                .addContainerGap())
        );
        pnl_actualizarLayout.setVerticalGroup(
            pnl_actualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_actualizarLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(tbActualizarTipoProd)
                .addContainerGap())
        );

        tbpnl_modInventario.addTab("Actualizar inventario", pnl_actualizar);

        tbNuevoTipoProd.setTabPlacement(javax.swing.JTabbedPane.LEFT);

        lblNuevoAcCodigo.setText("Código:");

        lblNuevoAcVariedad.setText("Variedad de madera:");

        lblNuevoAcMedidas.setText("Medidas:");

        lblNuevoAcUnidades.setText("Cantidad en varas:");

        lblNuevoAcPrecio.setText("Precio por vara:");

        lblNuevoAcDescripcion.setText("Descripción:");

        lblNuevoAcMedX.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblNuevoAcMedX.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNuevoAcMedX.setText("x");

        txtNuevoAcMedGrueso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNuevoAcMedGruesoActionPerformed(evt);
            }
        });

        txtaNuevoAcDescripcion.setColumns(20);
        txtaNuevoAcDescripcion.setRows(5);
        scpnlNuevoAcDescripcion.setViewportView(txtaNuevoAcDescripcion);

        lblNuevoAcOrigen.setText("Madera de origen:");

        tblAcAgregarInv.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Tipo de producto", "Variedad de madera", "Medidas", "Cantidad varas", "Precio por vara", "Descripción", "Origen", "Codigo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        scpnlTbAcNuevo.setViewportView(tblAcAgregarInv);

        javax.swing.GroupLayout pnlNuevoAcerradaLayout = new javax.swing.GroupLayout(pnlNuevoAcerrada);
        pnlNuevoAcerrada.setLayout(pnlNuevoAcerradaLayout);
        pnlNuevoAcerradaLayout.setHorizontalGroup(
            pnlNuevoAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNuevoAcerradaLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnlNuevoAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(scpnlTbAcNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 1084, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlNuevoAcerradaLayout.createSequentialGroup()
                        .addGroup(pnlNuevoAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lblNuevoAcVariedad, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
                            .addComponent(txtNuevoAcCodigo, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbxNuevoAcVariedad, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblNuevoAcCodigo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(95, 95, 95)
                        .addGroup(pnlNuevoAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lblNuevoAcPrecio, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtNuevoAcMedVaras, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNuevoAcUnidades, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
                            .addComponent(txtNuevoAcPrecio))
                        .addGap(85, 85, 85)
                        .addGroup(pnlNuevoAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(pnlNuevoAcerradaLayout.createSequentialGroup()
                                .addComponent(txtNuevoAcMedGrueso, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblNuevoAcMedX, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNuevoAcMedAncho, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(cbxNuevoAcOrigen, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblNuevoAcOrigen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblNuevoAcMedidas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(87, 87, 87)
                        .addGroup(pnlNuevoAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNuevoAcDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(scpnlNuevoAcDescripcion))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlNuevoAcerradaLayout.setVerticalGroup(
            pnlNuevoAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNuevoAcerradaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlNuevoAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblNuevoAcDescripcion, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                    .addComponent(lblNuevoAcUnidades, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlNuevoAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblNuevoAcMedidas, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblNuevoAcCodigo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlNuevoAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlNuevoAcerradaLayout.createSequentialGroup()
                        .addGroup(pnlNuevoAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNuevoAcCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNuevoAcMedVaras, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlNuevoAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlNuevoAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblNuevoAcPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblNuevoAcVariedad, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblNuevoAcOrigen, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlNuevoAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbxNuevoAcVariedad, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnlNuevoAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtNuevoAcPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cbxNuevoAcOrigen, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(pnlNuevoAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtNuevoAcMedGrueso, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblNuevoAcMedX, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtNuevoAcMedAncho, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(scpnlNuevoAcDescripcion))
                .addGap(18, 18, 18)
                .addComponent(scpnlTbAcNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        tbNuevoTipoProd.addTab("", new javax.swing.ImageIcon(getClass().getResource("/recursos/inv_acerrada.png")), pnlNuevoAcerrada); // NOI18N

        lblNuevoTmCodigo.setText("Código:");

        lblNuevoTmNombre.setText("Nombre:");

        lblNuevoTmVariedad.setText("Variedad de madera:");

        lblNuevoTmPrecio.setText("Precio:");

        lblNuevoTmCantVaras.setText("Cantidad en varas:");

        lblNuevoTmOrigen.setText("Madera de origen:");

        tblTmAgregarInv.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Tipo de producto", "Variedad de madera", "Cantidad varas", "Precio", "Descripción", "Origen", "Codigo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        scpnlTbTmNuevo.setViewportView(tblTmAgregarInv);

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
                    .addComponent(txtNuevoTmNombre))
                .addGap(95, 95, 95)
                .addGroup(pnlNuevoTerminadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlNuevoTerminadaLayout.createSequentialGroup()
                        .addGroup(pnlNuevoTerminadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cbxNuevoTmVariedad, 0, 295, Short.MAX_VALUE)
                            .addComponent(lblNuevoTmVariedad, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(95, 95, 95)
                        .addGroup(pnlNuevoTerminadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtNuevoTmPrecio)
                            .addComponent(lblNuevoTmPrecio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblNuevoTmOrigen, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                            .addComponent(cbxNuevoTmOrigen, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(pnlNuevoTerminadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(lblNuevoTmCantVaras, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtNuevoTmCantVaras, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE)))
                .addContainerGap(34, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlNuevoTerminadaLayout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addComponent(scpnlTbTmNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 1084, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlNuevoTerminadaLayout.setVerticalGroup(
            pnlNuevoTerminadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNuevoTerminadaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlNuevoTerminadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblNuevoTmVariedad, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlNuevoTerminadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblNuevoTmCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblNuevoTmOrigen, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlNuevoTerminadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNuevoTmCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxNuevoTmVariedad, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxNuevoTmOrigen, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlNuevoTerminadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNuevoTmNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNuevoTmCantVaras, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNuevoTmPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlNuevoTerminadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNuevoTmNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNuevoTmCantVaras, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNuevoTmPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(scpnlTbTmNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        tbNuevoTipoProd.addTab("", new javax.swing.ImageIcon(getClass().getResource("/recursos/inv_terminada.png")), pnlNuevoTerminada); // NOI18N

        lblNuevoTCodigo.setText("Código:");

        lblNuevoTVariedadMadera.setText("Variedad de madera:");

        lblNuevoTpulgadas.setText("Cantidad en pulgadas:");

        lblNuevoTProveedor.setText("Proveedor:");

        lblNuevoTDescripcion.setText("Descripción: ");

        txtaNuevoTDescripcion.setColumns(20);
        txtaNuevoTDescripcion.setRows(5);
        scpnlNuevoTDescripcion.setViewportView(txtaNuevoTDescripcion);

        cbxNuevoTVariedad.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Amarillón", "Cedro", "Eucalipto", "Pino" }));

        cbxNuevoTProveedor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Juan", "Rodolfo", "Perineo" }));

        btnCrearProv.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/f_crearCliente.png"))); // NOI18N

        tblTAgregarInv.setModel(new javax.swing.table.DefaultTableModel(
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
        scpnlTbTNuevo.setViewportView(tblTAgregarInv);

        javax.swing.GroupLayout pnlNuevoTrozaLayout = new javax.swing.GroupLayout(pnlNuevoTroza);
        pnlNuevoTroza.setLayout(pnlNuevoTrozaLayout);
        pnlNuevoTrozaLayout.setHorizontalGroup(
            pnlNuevoTrozaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNuevoTrozaLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnlNuevoTrozaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlNuevoTrozaLayout.createSequentialGroup()
                        .addComponent(scpnlTbTNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 1074, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnlNuevoTrozaLayout.createSequentialGroup()
                        .addGroup(pnlNuevoTrozaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlNuevoTrozaLayout.createSequentialGroup()
                                .addComponent(lblNuevoTpulgadas, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(95, 95, 95)
                                .addGroup(pnlNuevoTrozaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlNuevoTrozaLayout.createSequentialGroup()
                                        .addComponent(cbxNuevoTProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnCrearProv, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(lblNuevoTProveedor, javax.swing.GroupLayout.DEFAULT_SIZE, 308, Short.MAX_VALUE)))
                            .addGroup(pnlNuevoTrozaLayout.createSequentialGroup()
                                .addGroup(pnlNuevoTrozaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtNuevoTpulgadas)
                                    .addComponent(txtNuevoTCodigo)
                                    .addComponent(lblNuevoTCodigo, javax.swing.GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE))
                                .addGap(95, 95, 95)
                                .addGroup(pnlNuevoTrozaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbxNuevoTVariedad, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblNuevoTVariedadMadera, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(95, 95, 95)
                        .addGroup(pnlNuevoTrozaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(scpnlNuevoTDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblNuevoTDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20))))
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
                        .addGroup(pnlNuevoTrozaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNuevoTCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbxNuevoTVariedad, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlNuevoTrozaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblNuevoTpulgadas, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblNuevoTProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlNuevoTrozaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNuevoTpulgadas, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbxNuevoTProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCrearProv, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(scpnlNuevoTDescripcion))
                .addGap(18, 18, 18)
                .addComponent(scpnlTbTNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        tbNuevoTipoProd.addTab("", new javax.swing.ImageIcon(getClass().getResource("/recursos/inv_troza.png")), pnlNuevoTroza); // NOI18N

        btnNuevo.setText("Agregar producto");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnl_agregarNuevoLayout = new javax.swing.GroupLayout(pnl_agregarNuevo);
        pnl_agregarNuevo.setLayout(pnl_agregarNuevoLayout);
        pnl_agregarNuevoLayout.setHorizontalGroup(
            pnl_agregarNuevoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_agregarNuevoLayout.createSequentialGroup()
                .addGroup(pnl_agregarNuevoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnl_agregarNuevoLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnl_agregarNuevoLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(tbNuevoTipoProd)))
                .addContainerGap())
        );
        pnl_agregarNuevoLayout.setVerticalGroup(
            pnl_agregarNuevoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_agregarNuevoLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(tbNuevoTipoProd)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        tbpnl_modInventario.addTab("Agregar producto nuevo", pnl_agregarNuevo);

        tbEditarTipoProd.setTabPlacement(javax.swing.JTabbedPane.LEFT);

        lblEditarAcCodigo.setText("Código:");

        lblEditarAcVariedad.setText("Variedad de madera:");

        lblEditarAcMedidas.setText("Medidas:");

        lblEditarAcUnidade.setText("Cantidad en varas: ");

        lblEditarAcPrecio.setText("Precio por vara:");

        lblEditarAcDescripcion.setText("Descripción:");

        lblEditarAcMedX.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblEditarAcMedX.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblEditarAcMedX.setText("x");

        txtaEditarAcDescripcion.setColumns(20);
        txtaEditarAcDescripcion.setRows(3);
        scpnlEditarAcDescripcion.setViewportView(txtaEditarAcDescripcion);

        lblEditarAcOrigen.setText("Madera de origen:");

        tblAcEditar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Tipo de producto", "Variedad de madera", "Medidas", "Cantidad varas", "Precio por vara", "Descripción", "Origen", "Codigo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblAcEditar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblAcEditarMouseClicked(evt);
            }
        });
        scpnlTbAcEditar.setViewportView(tblAcEditar);
        if (tblAcEditar.getColumnModel().getColumnCount() > 0) {
            tblAcEditar.getColumnModel().getColumn(3).setHeaderValue("Medidas");
            tblAcEditar.getColumnModel().getColumn(5).setHeaderValue("Precio por vara");
        }

        javax.swing.GroupLayout pnlEditarAcerradaLayout = new javax.swing.GroupLayout(pnlEditarAcerrada);
        pnlEditarAcerrada.setLayout(pnlEditarAcerradaLayout);
        pnlEditarAcerradaLayout.setHorizontalGroup(
            pnlEditarAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEditarAcerradaLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnlEditarAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scpnlTbAcEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 1062, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlEditarAcerradaLayout.createSequentialGroup()
                        .addGroup(pnlEditarAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblEditarAcCodigo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblEditarAcVariedad, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
                            .addComponent(txtEditarAcCodigo)
                            .addComponent(cbxEditarAcVariedad, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(95, 95, 95)
                        .addGroup(pnlEditarAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lblEditarAcPrecio, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtEditarAcPrecio)
                            .addComponent(txtEditarAcVaras)
                            .addComponent(lblEditarAcUnidade, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE))
                        .addGroup(pnlEditarAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlEditarAcerradaLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pnlEditarAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlEditarAcerradaLayout.createSequentialGroup()
                                        .addComponent(txtEditarAcMedGrueso, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblEditarAcMedX, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtEditarAcMedAncho, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(lblEditarAcMedidas, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(pnlEditarAcerradaLayout.createSequentialGroup()
                                .addGap(85, 85, 85)
                                .addGroup(pnlEditarAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbxEditarAcOrigen, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblEditarAcOrigen, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(87, 87, 87)
                        .addGroup(pnlEditarAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(scpnlEditarAcDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblEditarAcDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        pnlEditarAcerradaLayout.setVerticalGroup(
            pnlEditarAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEditarAcerradaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlEditarAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblEditarAcCodigo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlEditarAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblEditarAcUnidade, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblEditarAcMedidas, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblEditarAcDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlEditarAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnlEditarAcerradaLayout.createSequentialGroup()
                        .addGroup(pnlEditarAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtEditarAcCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtEditarAcVaras, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtEditarAcMedGrueso, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblEditarAcMedX, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtEditarAcMedAncho, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlEditarAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblEditarAcVariedad, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblEditarAcPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblEditarAcOrigen, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlEditarAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbxEditarAcVariedad, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtEditarAcPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbxEditarAcOrigen, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(scpnlEditarAcDescripcion))
                .addGap(18, 18, 18)
                .addComponent(scpnlTbAcEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        tbEditarTipoProd.addTab("", new javax.swing.ImageIcon(getClass().getResource("/recursos/inv_acerrada.png")), pnlEditarAcerrada); // NOI18N

        lblEditarTmCodigo.setText("Código:");

        lblEditarTmNombre.setText("Nombre:");

        lblEditarTmVariedad.setText("Variedad de madera:");

        lblEditarTmPrecio.setText("Precio:");

        lblEditarTmCantVaras.setText("Cantidad en varas:");

        lblNuevoTmOrigen1.setText("Madera de origen:");

        tblTmEditar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Tipo de producto", "Variedad de madera", "Cantidad varas", "Precio ", "Descripción", "Origen", "Codigo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblTmEditar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblTmEditarMouseClicked(evt);
            }
        });
        scpnlTbTmEditar.setViewportView(tblTmEditar);
        if (tblTmEditar.getColumnModel().getColumnCount() > 0) {
            tblTmEditar.getColumnModel().getColumn(4).setHeaderValue("Precio por vara");
        }

        javax.swing.GroupLayout pnlEditarTerminadaLayout = new javax.swing.GroupLayout(pnlEditarTerminada);
        pnlEditarTerminada.setLayout(pnlEditarTerminadaLayout);
        pnlEditarTerminadaLayout.setHorizontalGroup(
            pnlEditarTerminadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEditarTerminadaLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnlEditarTerminadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scpnlTbTmEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 1062, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlEditarTerminadaLayout.createSequentialGroup()
                        .addGroup(pnlEditarTerminadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblEditarTmNombre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtEditarTmCodigo, javax.swing.GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE)
                            .addComponent(txtEditarTmNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE)
                            .addComponent(lblEditarTmCodigo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(95, 95, 95)
                        .addGroup(pnlEditarTerminadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlEditarTerminadaLayout.createSequentialGroup()
                                .addGroup(pnlEditarTerminadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblEditarTmVariedad, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cbxEditarTmVariedad, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(95, 95, 95)
                                .addGroup(pnlEditarTerminadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblEditarTmPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtEditarTmPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cbxNuevoTmOrigen1, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblNuevoTmOrigen1, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(pnlEditarTerminadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(lblEditarTmCantVaras, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtEditarTmCantVaras, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        pnlEditarTerminadaLayout.setVerticalGroup(
            pnlEditarTerminadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEditarTerminadaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlEditarTerminadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEditarTmCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEditarTmVariedad, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNuevoTmOrigen1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlEditarTerminadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEditarTmCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxEditarTmVariedad, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxNuevoTmOrigen1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                .addComponent(scpnlTbTmEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        tbEditarTipoProd.addTab("", new javax.swing.ImageIcon(getClass().getResource("/recursos/inv_terminada.png")), pnlEditarTerminada); // NOI18N

        lblEditarTCodigo.setText("Código:");

        lblEditarTVariedadMadera.setText("Variedad de madera:");

        lblEditarTProveedor.setText("Proveedor:");

        lblEditarTDescripcion.setText("Descripción: ");

        txtaEditarTDescripcion.setColumns(20);
        txtaEditarTDescripcion.setRows(3);
        txtaEditarTDescripcion.setTabSize(5);
        scpnlEditarTDescripcion.setViewportView(txtaEditarTDescripcion);

        btnCrearProv1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/f_crearCliente.png"))); // NOI18N

        lblEditarTpulgadas.setText("Cantidad en pulgadas:");

        tblTEditar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Tipo de producto", "Variedad de madera", "Cantidad pulgadas", "Descripción", "Proveedor", "Origen", "Codigo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblTEditar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblTEditarMouseClicked(evt);
            }
        });
        scpnlTbTEditar.setViewportView(tblTEditar);

        javax.swing.GroupLayout pnlEditarTrozaLayout = new javax.swing.GroupLayout(pnlEditarTroza);
        pnlEditarTroza.setLayout(pnlEditarTrozaLayout);
        pnlEditarTrozaLayout.setHorizontalGroup(
            pnlEditarTrozaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEditarTrozaLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnlEditarTrozaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scpnlTbTEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 1062, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlEditarTrozaLayout.createSequentialGroup()
                        .addGroup(pnlEditarTrozaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtEditarTCodigo)
                            .addComponent(lblEditarTCodigo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblEditarTpulgadas, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtEditarTpulgadas1))
                        .addGap(95, 95, 95)
                        .addGroup(pnlEditarTrozaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lblEditarTProveedor, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(pnlEditarTrozaLayout.createSequentialGroup()
                                .addComponent(cbxEditarTProveedor, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnCrearProv1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblEditarTVariedadMadera, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbxEditarTVariedad, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(96, 96, 96)
                        .addGroup(pnlEditarTrozaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblEditarTDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(scpnlEditarTDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
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
                            .addGroup(pnlEditarTrozaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblEditarTCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblEditarTVariedadMadera, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(pnlEditarTrozaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtEditarTCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cbxEditarTVariedad, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(pnlEditarTrozaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblEditarTpulgadas, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblEditarTProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtEditarTpulgadas1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addComponent(scpnlTbTEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        tbEditarTipoProd.addTab("", new javax.swing.ImageIcon(getClass().getResource("/recursos/inv_troza.png")), pnlEditarTroza); // NOI18N

        btnEditar.setText("Editar Producto");
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnl_editarLayout = new javax.swing.GroupLayout(pnl_editar);
        pnl_editar.setLayout(pnl_editarLayout);
        pnl_editarLayout.setHorizontalGroup(
            pnl_editarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_editarLayout.createSequentialGroup()
                .addGroup(pnl_editarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_editarLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(tbEditarTipoProd))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_editarLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        pnl_editarLayout.setVerticalGroup(
            pnl_editarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_editarLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(tbEditarTipoProd)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        tbpnl_modInventario.addTab("Editar producto", pnl_editar);

        txtBuscarActivo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarActivoKeyReleased(evt);
            }
        });

        tbProductosActivos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Tipo de producto", "Variedad de madera", "Medidas", "Cantidad", "Precio", "Descripción", "Origen", "Proveedor", "Codigo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, true, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
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
                    .addComponent(scpnlProductoActivo, javax.swing.GroupLayout.DEFAULT_SIZE, 1119, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlActivosLayout.setVerticalGroup(
            pnlActivosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlActivosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtBuscarActivo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scpnlProductoActivo, javax.swing.GroupLayout.DEFAULT_SIZE, 361, Short.MAX_VALUE)
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
                "Código", "Tipo de producto", "Variedad de madera", "Medidas", "Cantidad", "Precio por vara", "Descripción", "Origen", "Proveedor", "Codigo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, true, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
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
                    .addComponent(txtBuscarInactivo, javax.swing.GroupLayout.DEFAULT_SIZE, 1119, Short.MAX_VALUE)
                    .addComponent(scpnlProductoInactivo))
                .addContainerGap())
        );
        pnlInactivosLayout.setVerticalGroup(
            pnlInactivosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInactivosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtBuscarInactivo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scpnlProductoInactivo, javax.swing.GroupLayout.DEFAULT_SIZE, 361, Short.MAX_VALUE)
                .addContainerGap())
        );

        tbDeshab.addTab("Inactivos", pnlInactivos);

        pnlDeshabContainer.setBorder(javax.swing.BorderFactory.createTitledBorder("Activo:"));

        bgDeshab.add(rbDeshabDeshabProducto);
        rbDeshabDeshabProducto.setText("Deshabilitar");

        bgDeshab.add(rbDeshabHabilitarProducto);
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
                .addGap(25, 25, 25)
                .addGroup(pnlHabilitarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlHabilitarLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(pnlDeshabContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(654, 654, 654)
                        .addComponent(btn_deshabilitar, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(tbDeshab, javax.swing.GroupLayout.PREFERRED_SIZE, 1144, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(34, Short.MAX_VALUE))
        );
        pnlHabilitarLayout.setVerticalGroup(
            pnlHabilitarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlHabilitarLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(pnlHabilitarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btn_deshabilitar, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlHabilitarLayout.createSequentialGroup()
                        .addComponent(tbDeshab, javax.swing.GroupLayout.PREFERRED_SIZE, 447, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(pnlDeshabContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(17, 17, 17))
        );

        tbpnl_modInventario.addTab("Habilitar inventario", pnlHabilitar);

        javax.swing.GroupLayout pnl_modInventarioLayout = new javax.swing.GroupLayout(pnl_modInventario);
        pnl_modInventario.setLayout(pnl_modInventarioLayout);
        pnl_modInventarioLayout.setHorizontalGroup(
            pnl_modInventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_modInventarioLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(tbpnl_modInventario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnl_modInventarioLayout.setVerticalGroup(
            pnl_modInventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_modInventarioLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(tbpnl_modInventario, javax.swing.GroupLayout.PREFERRED_SIZE, 591, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnl_modInventario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnl_modInventario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_deshabilitarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deshabilitarActionPerformed
        try {
            
            model = tbDeshab.getSelectedIndex() == 0 ? 
                    (DefaultTableModel) tbProductosActivos.getModel() : 
                    (DefaultTableModel) tbProductosInactivos.getModel();
            
            int selectedRowIndex = tbDeshab.getSelectedIndex() == 0 ? 
                    tbProductosActivos.getSelectedRow() : 
                    tbProductosInactivos.getSelectedRow();
            
            Estado estado
                    = rbDeshabHabilitarProducto.isSelected() ? Estado.Activo : Estado.Deshabilitado;
            
            String codigo = (String) model.getValueAt(selectedRowIndex, 8);
            
            if (estado.equals(Estado.Deshabilitado)) {
                controlador.inactivarProducto(codigo);
                System.out.println(codigo);
            } else {
                controlador.activarProducto(codigo);
                System.out.println(codigo);
            }
            //Actualizar
            cargarTablas();
        } catch (Exception e) {
            e.printStackTrace();
            msg.mostrarMensaje(JOptionPane.INFORMATION_MESSAGE,
                    TipoMensaje.ANY_ROW_SELECTED);
        }
    }//GEN-LAST:event_btn_deshabilitarActionPerformed

    private void btnActualizarAserradaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarAserradaActionPerformed
        if (!txtActAcIngresa.getText().isEmpty()) {
            if (cbxActAcCodigo.getSelectedIndex() >= 0) {
                try {
                    int unidades = Integer.parseInt(txtActAcIngresa.getText());
                    String codigo = cbxActAcCodigo.getItemAt(cbxActAcCodigo.getSelectedIndex()).getCodigo();
                    controlador.actualizarInventario("ASERRADA", unidades, codigo);
                    cargarTablas();
                    cargarCombos();
                    limpiarCampos("ACTUALIZAR", "ASERRADA");
                } catch (NumberFormatException ex) {
                    
                }catch (NullPointerException ex) {
                    
                }
            } else {
                
            }
        } else {
            
        } 
    }//GEN-LAST:event_btnActualizarAserradaActionPerformed

    private void txtListadoInventarioKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtListadoInventarioKeyReleased
        productos = controlador.consultarProductos(txtListadoInventario.getText().trim());
        cargarProductosJTable(tbListadoInventario, true);
    }//GEN-LAST:event_txtListadoInventarioKeyReleased

    private void jFormattedTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFormattedTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jFormattedTextField1ActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        prepararProducto();
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        try {
            model = (DefaultTableModel) tblAcEditar.getModel();
            int indiceFila = tblAcEditar.getSelectedRow();
            String codigo = (String) model.getValueAt(indiceFila, 8);
            
            String grueso;
            String ancho;
            TipoMadera tipoMad;
            if (verificarTipoMadera("EDITAR").equals("ASERRADA")) {

                grueso = txtEditarAcMedGrueso.getText();
                ancho = txtEditarAcMedAncho.getText();
                tipoMad = (TipoMadera) cbxEditarAcVariedad.getSelectedItem();
 
                
                boolean aprod = actualizarProducto(
                        txtEditarAcCodigo.getText().trim(),
                        txtaEditarAcDescripcion.getText().trim(),
                        txtEditarAcPrecio.getText().trim(),
                        txtEditarAcVaras.getText().trim(),
                        txtEditarAcMedGrueso.getText().trim(), 
                        txtEditarAcMedAncho.getText().trim(), 
                        "0", tipoMad.getCodigo(), "0", codigo);
                if(aprod) {
                    limpiarCampos("Editar","ASERRADA");
                }
            } else if (verificarTipoMadera("EDITAR").equals("TROZA")) {

                medidas = txtEditarTMedPulgadas.getText().trim() + " pulgadas";
                tipoMad = (TipoMadera) cbxEditarTVariedad.getSelectedItem();
                Proveedor pv = (Proveedor) cbxEditarTProveedor.getSelectedItem();

                boolean aprod = actualizarProducto(txtEditarTCodigo.getText().trim(),
                        tipoMad.getCodigo(), medidas, "0", "0",
                        txtaEditarTDescripcion.getText().trim(), 
                        pv.getCodProveedor(), codigo);
                if(aprod) {
                    limpiarCampos("Editar","TROZA");
                }
            } else if (verificarTipoMadera("EDITAR").equals("TERMINADA")) {

                tipoMad = (TipoMadera) cbxEditarTmVariedad.getSelectedItem();

                boolean aprod = actualizarProducto(txtEditarTmCodigo.getText(),
                        tipoMad.getCodigo(), "0", "0", 
                        txtEditarTmPrecio.getText().trim(),
                        txtEditarTmNombre.getText().trim(), "0", codigo);
                if(aprod) {
                    limpiarCampos("Editar","TERMINADA");
                }
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
            ex.printStackTrace();
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_btnEditarActionPerformed

    private void tblAcEditarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblAcEditarMouseClicked
        try {
            model = (DefaultTableModel) tblAcEditar.getModel();
            
            int indiceFila = tblAcEditar.getSelectedRow();
            System.out.println(tblAcEditar.getValueAt(indiceFila, 7));
            String codigo = (String) model.getValueAt(indiceFila, 8);
            
            Madera prod = new Madera();
            for (Madera p: productos) {
                if (p.getCodigo().equals(codigo)) {
                    prod = p;
                    break;
                }
            }
            System.out.println("----------");
            System.out.println("Inventario -> Editar -> Tabla editar -> MouseClicked");
            if (prod.getTipoProducto().equals("ASERRADA")) {
                
                txtEditarAcVaras.setText(String.valueOf(prod.getUnidades()));
                txtEditarAcMedGrueso.setText(prod.getGrueso());
                txtEditarAcMedAncho.setText(prod.getAncho());
                
                for (int i=0; i<cbxEditarAcVariedad.getItemCount(); i++) {
                    if (cbxEditarAcVariedad.getItemAt(i).getDescripcion().equals(prod.getDescTipoMadera())) {
                        cbxEditarAcVariedad.setSelectedIndex(i);
                    }
                }

                txtEditarAcCodigo.setText(prod.getCodTipoMadera());
                txtEditarAcVaras.setText(String.valueOf(prod.getUnidades()));
                txtEditarAcPrecio.setText(String.valueOf(prod.getPrecioXvara()));
                txtaEditarAcDescripcion.setText(prod.getDescripcion());
                
                tbEditarTipoProd.setSelectedIndex(0);
                
            } else if (prod.getTipoProducto().equals("TROZA")) {
                
                String medidas = String.valueOf(prod.getUnidades());
                txtEditarTMedPulgadas.setText(medidas);
                
                txtEditarTCodigo.setText(prod.getCodProducto());
                
                for (int i=0; i<cbxEditarTVariedad.getItemCount(); i++) {
                    if (cbxEditarTVariedad.getItemAt(i).getDescripcion().equals(prod.getDescTipoMadera())) {
                        cbxEditarTVariedad.setSelectedIndex(i);
                    }
                }
                
                for (int i=0; i<cbxEditarTProveedor.getItemCount(); i++) {
                    String nom  = cbxEditarTProveedor.getItemAt(i).getNombre() 
                            + " " + cbxEditarTProveedor.getItemAt(i).getApellido1();
                    if (nom.equals(prod.getNomProveedor())) {
                        cbxEditarTProveedor.setSelectedIndex(i);
                    }
                }

                txtaEditarTDescripcion.setText(prod.getDescripcion());
                
                tbEditarTipoProd.setSelectedIndex(1);
                
            } else if (prod.getTipoProducto().equals("TERMINADA")) {

                for (int i=0; i<cbxEditarTmVariedad.getItemCount(); i++) {
                    if (cbxEditarTmVariedad.getItemAt(i).getDescripcion().equals(prod.getDescTipoMadera())) {
                        cbxEditarTmVariedad.setSelectedIndex(i);
                    }
                }

                txtEditarTmCodigo.setText(prod.getCodProducto());
                txtEditarTmPrecio.setText(String.valueOf(prod.getPrecioXvara()));
                txtEditarTmNombre.setText(prod.getDescripcion());
                
                tbEditarTipoProd.setSelectedIndex(2);
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
            ex.printStackTrace();
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_tblAcEditarMouseClicked

    private void txtNuevoAcMedGruesoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNuevoAcMedGruesoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNuevoAcMedGruesoActionPerformed

    private void tblTmEditarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblTmEditarMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblTmEditarMouseClicked

    private void tblTEditarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblTEditarMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblTEditarMouseClicked

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

    private void cbxActAcOpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxActAcOpActionPerformed
        if (cbxActAcOp.getSelectedIndex() == 0) {
            cambiarOperacion("ASERRADA", true);
        } else {
            cambiarOperacion("ASERRADA", false);
        }
    }//GEN-LAST:event_cbxActAcOpActionPerformed

    private void cbxActTmOpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxActTmOpActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxActTmOpActionPerformed

    private void btnActualizarTerminadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarTerminadaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnActualizarTerminadaActionPerformed

    private void cbxActTOpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxActTOpActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxActTOpActionPerformed

    private void btnActualizarTrozaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarTrozaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnActualizarTrozaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgDeshab;
    private javax.swing.JButton btnActualizarAserrada;
    private javax.swing.JButton btnActualizarTerminada;
    private javax.swing.JButton btnActualizarTroza;
    private javax.swing.JButton btnCrearProv;
    private javax.swing.JButton btnCrearProv1;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btn_deshabilitar;
    private javax.swing.JComboBox<Madera> cbxActAcOp;
    private javax.swing.JComboBox<Madera> cbxActTOp;
    private javax.swing.JComboBox<Madera> cbxActTmOp;
    private javax.swing.JComboBox<Object> cbxEditarAcOrigen;
    private javax.swing.JComboBox<TipoMadera> cbxEditarAcVariedad;
    private javax.swing.JComboBox<Proveedor> cbxEditarTProveedor;
    private javax.swing.JComboBox<TipoMadera> cbxEditarTVariedad;
    private javax.swing.JComboBox<TipoMadera> cbxEditarTmVariedad;
    private javax.swing.JComboBox<Object> cbxNuevoAcOrigen;
    private javax.swing.JComboBox<Object> cbxNuevoAcVariedad;
    private javax.swing.JComboBox<Object> cbxNuevoTProveedor;
    private javax.swing.JComboBox<Object> cbxNuevoTVariedad;
    private javax.swing.JComboBox<Object> cbxNuevoTmOrigen;
    private javax.swing.JComboBox<Object> cbxNuevoTmOrigen1;
    private javax.swing.JComboBox<Object> cbxNuevoTmVariedad;
    private javax.swing.JFormattedTextField jFormattedTextField1;
    private javax.swing.JLabel lblActAcDetalle;
    private javax.swing.JLabel lblActAcIngresa;
    private javax.swing.JLabel lblActAcOp;
    private javax.swing.JLabel lblActAcSalVaras;
    private javax.swing.JLabel lblActAcSalen;
    private javax.swing.JLabel lblActAsPulg;
    private javax.swing.JLabel lblActAsVaras;
    private javax.swing.JLabel lblActTDetalle;
    private javax.swing.JLabel lblActTIngresa;
    private javax.swing.JLabel lblActTOp;
    private javax.swing.JLabel lblActTVaras;
    private javax.swing.JLabel lblActTmDetalle;
    private javax.swing.JLabel lblActTmInVaras;
    private javax.swing.JLabel lblActTmIngresa;
    private javax.swing.JLabel lblActTmOp;
    private javax.swing.JLabel lblActTmSalen;
    private javax.swing.JLabel lblEditarAcCodigo;
    private javax.swing.JLabel lblEditarAcDescripcion;
    private javax.swing.JLabel lblEditarAcMedX;
    private javax.swing.JLabel lblEditarAcMedidas;
    private javax.swing.JLabel lblEditarAcOrigen;
    private javax.swing.JLabel lblEditarAcPrecio;
    private javax.swing.JLabel lblEditarAcUnidade;
    private javax.swing.JLabel lblEditarAcVariedad;
    private javax.swing.JLabel lblEditarTCodigo;
    private javax.swing.JLabel lblEditarTDescripcion;
    private javax.swing.JLabel lblEditarTProveedor;
    private javax.swing.JLabel lblEditarTVariedadMadera;
    private javax.swing.JLabel lblEditarTmCantVaras;
    private javax.swing.JLabel lblEditarTmCodigo;
    private javax.swing.JLabel lblEditarTmNombre;
    private javax.swing.JLabel lblEditarTmPrecio;
    private javax.swing.JLabel lblEditarTmVariedad;
    private javax.swing.JLabel lblEditarTpulgadas;
    private javax.swing.JLabel lblListadoInventario;
    private javax.swing.JLabel lblNuevoAcCodigo;
    private javax.swing.JLabel lblNuevoAcDescripcion;
    private javax.swing.JLabel lblNuevoAcMedX;
    private javax.swing.JLabel lblNuevoAcMedidas;
    private javax.swing.JLabel lblNuevoAcOrigen;
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
    private javax.swing.JLabel lblNuevoTmOrigen;
    private javax.swing.JLabel lblNuevoTmOrigen1;
    private javax.swing.JLabel lblNuevoTmPrecio;
    private javax.swing.JLabel lblNuevoTmVariedad;
    private javax.swing.JLabel lblNuevoTpulgadas;
    private javax.swing.JList<String> lsActAsSelTrz;
    private javax.swing.JList<String> lsActTmSelAs;
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
    private javax.swing.JScrollPane scpnltxtaActAcDetalle;
    private javax.swing.JScrollPane scpnltxtaActAcDetalle1;
    private javax.swing.JScrollPane scpnltxtaActAcDetalle2;
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
    private javax.swing.JTable tblAcAgregarInv;
    private javax.swing.JTable tblAcEditar;
    private javax.swing.JTable tblTAgregarInv;
    private javax.swing.JTable tblTEditar;
    private javax.swing.JTable tblTmAgregarInv;
    private javax.swing.JTable tblTmEditar;
    private javax.swing.JTabbedPane tbpnl_modInventario;
    private javax.swing.JTextField txtActAcBuscTrz;
    private javax.swing.JTextField txtActAcIngresa;
    private javax.swing.JTextField txtActAcSalenPulg;
    private javax.swing.JTextField txtActTIngresa;
    private javax.swing.JTextField txtActTmBuscAs;
    private javax.swing.JTextField txtActTmIngresa;
    private javax.swing.JTextField txtActTmSalenVaras;
    private javax.swing.JTextField txtBuscarActivo;
    private javax.swing.JTextField txtBuscarInactivo;
    private javax.swing.JTextField txtEditarAcCodigo;
    private javax.swing.JTextField txtEditarAcMedAncho;
    private javax.swing.JTextField txtEditarAcMedGrueso;
    private javax.swing.JTextField txtEditarAcPrecio;
    private javax.swing.JTextField txtEditarAcVaras;
    private javax.swing.JTextField txtEditarTCodigo;
    private javax.swing.JTextField txtEditarTmCantVaras;
    private javax.swing.JTextField txtEditarTmCodigo;
    private javax.swing.JTextField txtEditarTmNombre;
    private javax.swing.JTextField txtEditarTmPrecio;
    private javax.swing.JTextField txtEditarTpulgadas1;
    private javax.swing.JTextField txtListadoInventario;
    private javax.swing.JTextField txtNuevoAcCodigo;
    private javax.swing.JTextField txtNuevoAcMedAncho;
    private javax.swing.JTextField txtNuevoAcMedGrueso;
    private javax.swing.JTextField txtNuevoAcMedVaras;
    private javax.swing.JTextField txtNuevoAcPrecio;
    private javax.swing.JTextField txtNuevoTCodigo;
    private javax.swing.JTextField txtNuevoTmCantVaras;
    private javax.swing.JTextField txtNuevoTmCodigo;
    private javax.swing.JTextField txtNuevoTmNombre;
    private javax.swing.JTextField txtNuevoTmPrecio;
    private javax.swing.JTextField txtNuevoTpulgadas;
    private javax.swing.JTextArea txtaActAcDetalle;
    private javax.swing.JTextArea txtaActTDetalle;
    private javax.swing.JTextArea txtaActTmDetalle;
    private javax.swing.JTextArea txtaEditarAcDescripcion;
    private javax.swing.JTextArea txtaEditarTDescripcion;
    private javax.swing.JTextArea txtaNuevoAcDescripcion;
    private javax.swing.JTextArea txtaNuevoTDescripcion;
    // End of variables declaration//GEN-END:variables

}
