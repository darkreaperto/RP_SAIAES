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
        cargarProductosJTable(tblAgregarInv, true);
        cargarProductosJTable(tblEditar, true);
        cargarProductosJTable(tbProductosActivos, true);
        cargarProductosJTable(tbProductosInactivos, false);
        cargarProductosJTable(tbActAcerrada, true);
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
            //tipo prod- codigo- variedad- medidas- proveedor- unidades- precio- descripción
            if (productos.get(i).getEstado().equals(Estado.Activo) && estado) {
                System.out.println("AGREGAR A TABLA");
                row[0] = productos.get(i).getTipoProducto();
                row[1] = productos.get(i).getCodProducto();////////
                row[2] = productos.get(i).getDescTipoMadera();
                row[3] = productos.get(i).getMedidas();
                row[4] = productos.get(i).getNomProveedor();//getCodProveedor() == null ? "No aplica" : productos.get(i).getCodProveedor();
                row[5] = productos.get(i).getUnidades() <= 0 ? 
                        "No aplica" : productos.get(i).getUnidades();
                row[6] = productos.get(i).getPrecioXvara();
                row[7] = productos.get(i).getDescripcion();
                
                row[8] = productos.get(i).getCodigo();
                
                model.addRow(row);
            }
            if (productos.get(i).getEstado().equals(Estado.Deshabilitado) && !estado) {
                row[0] = productos.get(i).getTipoProducto();
                row[1] = productos.get(i).getCodProducto();////////
                row[2] = productos.get(i).getDescTipoMadera();
                row[3] = productos.get(i).getMedidas();
                row[4] = productos.get(i).getNomProveedor();//getCodProveedor() == null ? "No aplica" : productos.get(i).getCodProveedor();
                row[5] = productos.get(i).getUnidades() <= 0 ? 
                        "No aplica" : productos.get(i).getUnidades();
                row[6] = productos.get(i).getPrecioXvara();
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
                    txtNuevoAcUnidades.setText("");
                    txtaNuevoAcDescripcion.setText("");
                    //cbxNuevoAcVariedad.removeAllItems();
                    cargarCombos();
                    break;
                case "TROZA":
                    txtNuevoTCodigo.setText("");
                    txtNuevoTMedPulgadas.setText("");
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
                    txtEditarAcMedVaras.setText("");
                    txtEditarAcPrecio.setText("");
                    txtEditarAcUnidades.setText("");
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
     * Crea un nuevo producto.
     */
    private boolean crearProducto(String codProd, String codTipoMadera, 
            String medida, String tipoProducto, String unidades, String precio, 
            String descripcion, String codProveedor) {
        
        //Campos no están vacíos
        if (!codProd.isEmpty() && !medida.isEmpty() && !codTipoMadera.isEmpty() 
                && !tipoProducto.isEmpty() && !unidades.isEmpty() && !precio.isEmpty() 
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
                   
                    System.out.println("AGREGANDO PRODUCTO, PLEASE WAIT... "+ preci);
                    boolean crear = controlador.crearProducto(codProd, cTmadera,
                            medida, tipoProducto, unit, preci, descripcion,
                            cProveedor);
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
                /*} else {
                    msg.mostrarMensaje(JOptionPane.WARNING_MESSAGE,
                            TipoMensaje.UNITQUANTITY_SYNTAX_FAILURE);
//                    txtCrearCantidad.requestFocus();
                }*/
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
        return false;
    }
    
    private boolean actualizarProducto(String codProd, String codTipoMadera, 
            String medida, String unidades, String precio, String descripcion, 
            String codProveedor, String codigo) {
        
        //Campos no están vacíos
        if (!codProd.isEmpty() && !medida.isEmpty() && !codTipoMadera.isEmpty() 
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
        pnl_agregarNuevo = new javax.swing.JPanel();
        tbNuevoTipoProd = new javax.swing.JTabbedPane();
        pnlNuevoAcerrada = new javax.swing.JPanel();
        lblNuevoAcCodigo = new javax.swing.JLabel();
        lblNuevoAcVariedad = new javax.swing.JLabel();
        lblNuevoAcMedidas = new javax.swing.JLabel();
        lblNuevoAcUnidades = new javax.swing.JLabel();
        lblNuevoAcPrecio = new javax.swing.JLabel();
        lblNuevoAcDescripcion = new javax.swing.JLabel();
        lblNuevoAcMedDE = new javax.swing.JLabel();
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
        txtNuevoAcUnidades = new javax.swing.JTextField();
        txtNuevoAcPrecio = new javax.swing.JTextField();
        scpnlNuevoAcDescripcion = new javax.swing.JScrollPane();
        txtaNuevoAcDescripcion = new javax.swing.JTextArea();
        cbxNuevoAcVariedad = new javax.swing.JComboBox<>();
        pnlNuevoTroza = new javax.swing.JPanel();
        lblNuevoTCodigo = new javax.swing.JLabel();
        lblNuevoTVariedadMadera = new javax.swing.JLabel();
        lblNuevoTMedidas = new javax.swing.JLabel();
        lblNuevoTProveedor = new javax.swing.JLabel();
        lblNuevoTDescripcion = new javax.swing.JLabel();
        txtNuevoTCodigo = new javax.swing.JTextField();
        scpnlNuevoTDescripcion = new javax.swing.JScrollPane();
        txtaNuevoTDescripcion = new javax.swing.JTextArea();
        cbxNuevoTVariedad = new javax.swing.JComboBox<>();
        cbxNuevoTProveedor = new javax.swing.JComboBox<>();
        txtNuevoTMedPulgadas = new javax.swing.JTextField();
        placeholder = new TextPrompt("Pulgadas", txtNuevoTMedPulgadas);
        placeholder.changeAlpha(0.75f);
        placeholder.changeStyle(Font.ITALIC);
        btnCrearProv = new javax.swing.JButton();
        pnlNuevoTerminada = new javax.swing.JPanel();
        lblNuevoTmCodigo = new javax.swing.JLabel();
        lblNuevoTmNombre = new javax.swing.JLabel();
        lblNuevoTmVariedad = new javax.swing.JLabel();
        lblNuevoTmPrecio = new javax.swing.JLabel();
        txtNuevoTmCodigo = new javax.swing.JTextField();
        txtNuevoTmNombre = new javax.swing.JTextField();
        txtNuevoTmPrecio = new javax.swing.JTextField();
        cbxNuevoTmVariedad = new javax.swing.JComboBox<>();
        btnNuevo = new javax.swing.JButton();
        scpnlTbNuevo = new javax.swing.JScrollPane();
        tblAgregarInv = new javax.swing.JTable();
        pnl_actualizar = new javax.swing.JPanel();
        tbActualizarTipoProd = new javax.swing.JTabbedPane();
        pnlActualizarAcerrada = new javax.swing.JPanel();
        lblActAcCodigo = new javax.swing.JLabel();
        lblActAcIngresa = new javax.swing.JLabel();
        lblActAcDetalle = new javax.swing.JLabel();
        lblActAcIngresaUNID = new javax.swing.JLabel();
        cbxActAcCodigo = new javax.swing.JComboBox<>();
        txtActAcIngresa = new javax.swing.JTextField();
        scpnltxtaActAcDetalle = new javax.swing.JScrollPane();
        txtaActAcDetalle = new javax.swing.JTextArea();
        scpnlTblAcActualizar = new javax.swing.JScrollPane();
        tbActAcerrada = new javax.swing.JTable();
        btnActualizarAserrada = new javax.swing.JButton();
        pnlActualizarTroza = new javax.swing.JPanel();
        lblActTCodigo = new javax.swing.JLabel();
        lblActTIngresaPULG = new javax.swing.JLabel();
        lblActTDetalle = new javax.swing.JLabel();
        lblActTIngresa = new javax.swing.JLabel();
        txtActTIngresa = new javax.swing.JTextField();
        scpnltxtaActTDetalle = new javax.swing.JScrollPane();
        txtaActTDetalle = new javax.swing.JTextArea();
        scpnlTblTActualizar = new javax.swing.JScrollPane();
        tbActTroza = new javax.swing.JTable();
        cbxActTCodigo = new javax.swing.JComboBox<>();
        btnActualizarTroza = new javax.swing.JButton();
        pnlActualizarTerminada = new javax.swing.JPanel();
        lblActTmNombre = new javax.swing.JLabel();
        lblActTmIngresaUNID = new javax.swing.JLabel();
        lblActTmDetalle = new javax.swing.JLabel();
        lblActTmIngresa = new javax.swing.JLabel();
        txtActTmIngresa = new javax.swing.JTextField();
        scpnltxtaActTmDetalle = new javax.swing.JScrollPane();
        txtaActTmDetalle = new javax.swing.JTextArea();
        scpnlTblTmActualizar = new javax.swing.JScrollPane();
        tbActTerminada = new javax.swing.JTable();
        cbxActTmCodigo = new javax.swing.JComboBox<>();
        btnActualizarTerminada = new javax.swing.JButton();
        pnl_editar = new javax.swing.JPanel();
        tbEditarTipoProd = new javax.swing.JTabbedPane();
        pnlEditarAcerrada = new javax.swing.JPanel();
        lblEditarAcCodigo = new javax.swing.JLabel();
        lblEditarAcVariedad = new javax.swing.JLabel();
        lblEditarAcMedidas = new javax.swing.JLabel();
        lblEditarAcUnidade = new javax.swing.JLabel();
        lblEditarAcPrecio = new javax.swing.JLabel();
        lblEditarAcDescripcion = new javax.swing.JLabel();
        lblEditarAcMedDE = new javax.swing.JLabel();
        lblEditarAcMedX = new javax.swing.JLabel();
        lblEditarAcMedAncho = new javax.swing.JLabel();
        lblEditarAcMedGrueso = new javax.swing.JLabel();
        lblEditarAcMedVaras = new javax.swing.JLabel();
        txtEditarAcCodigo = new javax.swing.JTextField();
        txtEditarAcMedAncho = new javax.swing.JTextField();
        txtEditarAcMedVaras = new javax.swing.JTextField();
        txtEditarAcMedGrueso = new javax.swing.JTextField();
        txtEditarAcUnidades = new javax.swing.JTextField();
        txtEditarAcPrecio = new javax.swing.JTextField();
        cbxEditarAcVariedad = new javax.swing.JComboBox<>();
        scpnlEditarAcDescripcion = new javax.swing.JScrollPane();
        txtaEditarAcDescripcion = new javax.swing.JTextArea();
        pnlEditarTroza = new javax.swing.JPanel();
        lblEditarTCodigo = new javax.swing.JLabel();
        lblEditarTVariedadMadera = new javax.swing.JLabel();
        lblEditarTMedidas = new javax.swing.JLabel();
        lblEditarTProveedor = new javax.swing.JLabel();
        lblEditarTDescripcion = new javax.swing.JLabel();
        lblEditarTMedPulgadas = new javax.swing.JLabel();
        txtEditarTCodigo = new javax.swing.JTextField();
        scpnlEditarTDescripcion = new javax.swing.JScrollPane();
        txtaEditarTDescripcion = new javax.swing.JTextArea();
        cbxEditarTVariedad = new javax.swing.JComboBox<>();
        cbxEditarTProveedor = new javax.swing.JComboBox<>();
        txtEditarTMedPulgadas = new javax.swing.JTextField();
        pnlEditarTerminada = new javax.swing.JPanel();
        lblEditarTmCodigo = new javax.swing.JLabel();
        lblEditarTmNombre = new javax.swing.JLabel();
        lblEditarTmVariedad = new javax.swing.JLabel();
        lblEditarTmPrecio = new javax.swing.JLabel();
        txtEditarTmCodigo = new javax.swing.JTextField();
        txtEditarTmNombre = new javax.swing.JTextField();
        txtEditarTmPrecio = new javax.swing.JTextField();
        cbxEditarTmVariedad = new javax.swing.JComboBox<>();
        scpnlTbEditar = new javax.swing.JScrollPane();
        tblEditar = new javax.swing.JTable();
        btnEditar = new javax.swing.JButton();
        pnlHabilitar = new javax.swing.JPanel();
        lblDeshabSelectInventario = new javax.swing.JLabel();
        tbDeshab = new javax.swing.JTabbedPane();
        scpnlDeshabProducto = new javax.swing.JScrollPane();
        tbProductosActivos = new javax.swing.JTable();
        scpnlHabilitarProducto = new javax.swing.JScrollPane();
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
                "Tipo de producto", "Código", "Variedad de madera", "Medidas", "Proveedor", "Unidades", "Precio", "Descripción", "Codigo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
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
                .addGap(26, 26, 26)
                .addGroup(pnl_listadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_listadoLayout.createSequentialGroup()
                        .addComponent(lblListadoInventario, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtListadoInventario, javax.swing.GroupLayout.PREFERRED_SIZE, 1017, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(scpnlTblListadoInventario))
                .addGap(23, 23, 23))
        );
        pnl_listadoLayout.setVerticalGroup(
            pnl_listadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_listadoLayout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(pnl_listadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtListadoInventario, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblListadoInventario, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(scpnlTblListadoInventario, javax.swing.GroupLayout.PREFERRED_SIZE, 432, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tbpnl_modInventario.addTab("Listado productos", pnl_listado);

        tbNuevoTipoProd.setTabPlacement(javax.swing.JTabbedPane.LEFT);

        lblNuevoAcCodigo.setText("Código:");

        lblNuevoAcVariedad.setText("Variedad de madera:");

        lblNuevoAcMedidas.setText("Medidas:");

        lblNuevoAcUnidades.setText("Cantidad en varas:");

        lblNuevoAcPrecio.setText("Precio por vara:");

        lblNuevoAcDescripcion.setText("Descripción:");

        lblNuevoAcMedDE.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblNuevoAcMedDE.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNuevoAcMedDE.setText("de");

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

        javax.swing.GroupLayout pnlNuevoAcerradaLayout = new javax.swing.GroupLayout(pnlNuevoAcerrada);
        pnlNuevoAcerrada.setLayout(pnlNuevoAcerradaLayout);
        pnlNuevoAcerradaLayout.setHorizontalGroup(
            pnlNuevoAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNuevoAcerradaLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnlNuevoAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblNuevoAcCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNuevoAcCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlNuevoAcerradaLayout.createSequentialGroup()
                        .addGroup(pnlNuevoAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNuevoAcUnidades, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblNuevoAcUnidades, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlNuevoAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNuevoAcMedVaras, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblNuevoAcMedDE, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(27, 27, 27)
                .addGroup(pnlNuevoAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblNuevoAcPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNuevoAcVariedad, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNuevoAcPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxNuevoAcVariedad, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(pnlNuevoAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlNuevoAcerradaLayout.createSequentialGroup()
                        .addGap(98, 98, 98)
                        .addComponent(lblNuevoAcDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlNuevoAcerradaLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 99, Short.MAX_VALUE)
                        .addGroup(pnlNuevoAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlNuevoAcerradaLayout.createSequentialGroup()
                                .addComponent(txtNuevoAcMedGrueso, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lblNuevoAcMedX, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtNuevoAcMedAncho, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlNuevoAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(scpnlNuevoAcDescripcion, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlNuevoAcerradaLayout.createSequentialGroup()
                                    .addComponent(lblNuevoAcMedidas, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(190, 190, 190))))))
                .addContainerGap())
        );
        pnlNuevoAcerradaLayout.setVerticalGroup(
            pnlNuevoAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNuevoAcerradaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlNuevoAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlNuevoAcerradaLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lblNuevoAcMedDE, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNuevoAcMedVaras, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlNuevoAcerradaLayout.createSequentialGroup()
                        .addGroup(pnlNuevoAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNuevoAcMedidas, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnlNuevoAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblNuevoAcCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblNuevoAcVariedad, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlNuevoAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlNuevoAcerradaLayout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(txtNuevoAcCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pnlNuevoAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblNuevoAcUnidades, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblNuevoAcPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(pnlNuevoAcerradaLayout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addGroup(pnlNuevoAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtNuevoAcMedGrueso, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblNuevoAcMedX, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtNuevoAcMedAncho, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblNuevoAcDescripcion, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE))
                            .addComponent(cbxNuevoAcVariedad, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlNuevoAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlNuevoAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtNuevoAcUnidades, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtNuevoAcPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(scpnlNuevoAcDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tbNuevoTipoProd.addTab("", new javax.swing.ImageIcon(getClass().getResource("/recursos/inv_acerrada.png")), pnlNuevoAcerrada); // NOI18N

        lblNuevoTCodigo.setText("Código:");

        lblNuevoTVariedadMadera.setText("Variedad de madera:");

        lblNuevoTMedidas.setText("Medidas:");

        lblNuevoTProveedor.setText("Proveedor:");

        lblNuevoTDescripcion.setText("Descripción: ");

        txtaNuevoTDescripcion.setColumns(20);
        txtaNuevoTDescripcion.setRows(5);
        scpnlNuevoTDescripcion.setViewportView(txtaNuevoTDescripcion);

        btnCrearProv.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/f_crearCliente.png"))); // NOI18N
        btnCrearProv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCrearProvActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlNuevoTrozaLayout = new javax.swing.GroupLayout(pnlNuevoTroza);
        pnlNuevoTroza.setLayout(pnlNuevoTrozaLayout);
        pnlNuevoTrozaLayout.setHorizontalGroup(
            pnlNuevoTrozaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNuevoTrozaLayout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(pnlNuevoTrozaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlNuevoTrozaLayout.createSequentialGroup()
                        .addComponent(lblNuevoTMedidas, javax.swing.GroupLayout.DEFAULT_SIZE, 214, Short.MAX_VALUE)
                        .addGap(146, 146, 146))
                    .addGroup(pnlNuevoTrozaLayout.createSequentialGroup()
                        .addGroup(pnlNuevoTrozaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtNuevoTMedPulgadas)
                            .addComponent(lblNuevoTCodigo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtNuevoTCodigo, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlNuevoTrozaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblNuevoTVariedadMadera, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxNuevoTVariedad, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNuevoTProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlNuevoTrozaLayout.createSequentialGroup()
                        .addComponent(cbxNuevoTProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCrearProv, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(95, 95, 95)
                .addGroup(pnlNuevoTrozaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scpnlNuevoTDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNuevoTDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(57, 57, 57))
        );
        pnlNuevoTrozaLayout.setVerticalGroup(
            pnlNuevoTrozaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNuevoTrozaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlNuevoTrozaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(lblNuevoTVariedadMadera, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblNuevoTCodigo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(lblNuevoTDescripcion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlNuevoTrozaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlNuevoTrozaLayout.createSequentialGroup()
                        .addGroup(pnlNuevoTrozaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtNuevoTCodigo)
                            .addComponent(cbxNuevoTVariedad, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlNuevoTrozaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblNuevoTMedidas, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblNuevoTProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlNuevoTrozaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNuevoTMedPulgadas, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbxNuevoTProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCrearProv, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 7, Short.MAX_VALUE))
                    .addComponent(scpnlNuevoTDescripcion))
                .addGap(34, 34, 34))
        );

        tbNuevoTipoProd.addTab("", new javax.swing.ImageIcon(getClass().getResource("/recursos/inv_troza.png")), pnlNuevoTroza); // NOI18N

        lblNuevoTmCodigo.setText("Código:");

        lblNuevoTmNombre.setText("Nombre:");

        lblNuevoTmVariedad.setText("Variedad de madera:");

        lblNuevoTmPrecio.setText("Precio:");

        javax.swing.GroupLayout pnlNuevoTerminadaLayout = new javax.swing.GroupLayout(pnlNuevoTerminada);
        pnlNuevoTerminada.setLayout(pnlNuevoTerminadaLayout);
        pnlNuevoTerminadaLayout.setHorizontalGroup(
            pnlNuevoTerminadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNuevoTerminadaLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnlNuevoTerminadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNuevoTmNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNuevoTmNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlNuevoTerminadaLayout.createSequentialGroup()
                        .addGroup(pnlNuevoTerminadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNuevoTmCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNuevoTmCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(100, 100, 100)
                        .addGroup(pnlNuevoTerminadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNuevoTmVariedad, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbxNuevoTmVariedad, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(94, 94, 94)
                        .addGroup(pnlNuevoTerminadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNuevoTmPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNuevoTmPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlNuevoTerminadaLayout.setVerticalGroup(
            pnlNuevoTerminadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNuevoTerminadaLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnlNuevoTerminadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNuevoTmCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNuevoTmVariedad, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNuevoTmPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlNuevoTerminadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNuevoTmCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNuevoTmPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxNuevoTmVariedad, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(lblNuevoTmNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNuevoTmNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tbNuevoTipoProd.addTab("", new javax.swing.ImageIcon(getClass().getResource("/recursos/inv_terminada.png")), pnlNuevoTerminada); // NOI18N

        btnNuevo.setText("Agregar producto");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        tblAgregarInv.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tipo de producto", "Código", "Variedad de madera", "Medidas", "Proveedor", "Unidades", "Precio", "Descripción", "Codigo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        scpnlTbNuevo.setViewportView(tblAgregarInv);

        javax.swing.GroupLayout pnl_agregarNuevoLayout = new javax.swing.GroupLayout(pnl_agregarNuevo);
        pnl_agregarNuevo.setLayout(pnl_agregarNuevoLayout);
        pnl_agregarNuevoLayout.setHorizontalGroup(
            pnl_agregarNuevoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_agregarNuevoLayout.createSequentialGroup()
                .addGroup(pnl_agregarNuevoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_agregarNuevoLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnl_agregarNuevoLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(pnl_agregarNuevoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(scpnlTbNuevo)
                            .addComponent(tbNuevoTipoProd))))
                .addContainerGap())
        );
        pnl_agregarNuevoLayout.setVerticalGroup(
            pnl_agregarNuevoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_agregarNuevoLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(tbNuevoTipoProd, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(scpnlTbNuevo, javax.swing.GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        tbpnl_modInventario.addTab("Agregar producto nuevo", pnl_agregarNuevo);

        tbActualizarTipoProd.setTabPlacement(javax.swing.JTabbedPane.LEFT);

        lblActAcCodigo.setText("Código:");

        lblActAcIngresa.setText("Ingresa:");

        lblActAcDetalle.setText("Detalle:");

        lblActAcIngresaUNID.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        lblActAcIngresaUNID.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblActAcIngresaUNID.setText("unidades.");

        txtaActAcDetalle.setEditable(false);
        txtaActAcDetalle.setBackground(new java.awt.Color(238, 238, 238));
        txtaActAcDetalle.setColumns(5);
        txtaActAcDetalle.setRows(3);
        txtaActAcDetalle.setBorder(javax.swing.BorderFactory.createLineBorder(javax.swing.UIManager.getDefaults().getColor("Button.darkShadow")));
        scpnltxtaActAcDetalle.setViewportView(txtaActAcDetalle);

        tbActAcerrada.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tipo de producto", "Código", "Variedad de madera", "Medidas", "Proveedor", "Unidades", "Precio", "Descripción", "Codigo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        scpnlTblAcActualizar.setViewportView(tbActAcerrada);

        btnActualizarAserrada.setText("Actualizar Inventario");
        btnActualizarAserrada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarAserradaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlActualizarAcerradaLayout = new javax.swing.GroupLayout(pnlActualizarAcerrada);
        pnlActualizarAcerrada.setLayout(pnlActualizarAcerradaLayout);
        pnlActualizarAcerradaLayout.setHorizontalGroup(
            pnlActualizarAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlActualizarAcerradaLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnlActualizarAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblActAcIngresa, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxActAcCodigo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblActAcCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(scpnltxtaActAcDetalle, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlActualizarAcerradaLayout.createSequentialGroup()
                        .addComponent(txtActAcIngresa, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblActAcIngresaUNID))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlActualizarAcerradaLayout.createSequentialGroup()
                        .addComponent(lblActAcDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(155, 155, 155))
                    .addComponent(btnActualizarAserrada, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 81, Short.MAX_VALUE)
                .addComponent(scpnlTblAcActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 688, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlActualizarAcerradaLayout.setVerticalGroup(
            pnlActualizarAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlActualizarAcerradaLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnlActualizarAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlActualizarAcerradaLayout.createSequentialGroup()
                        .addComponent(lblActAcCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxActAcCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(42, 42, 42)
                        .addComponent(lblActAcDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(scpnltxtaActAcDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41)
                        .addComponent(lblActAcIngresa, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlActualizarAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtActAcIngresa, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblActAcIngresaUNID, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 65, Short.MAX_VALUE)
                        .addComponent(btnActualizarAserrada, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(scpnlTblAcActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        tbActualizarTipoProd.addTab("", new javax.swing.ImageIcon(getClass().getResource("/recursos/inv_acerrada.png")), pnlActualizarAcerrada, "Madera Acerrada"); // NOI18N

        lblActTCodigo.setText("Código/VariedadMadera?:");

        lblActTIngresaPULG.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        lblActTIngresaPULG.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblActTIngresaPULG.setText("pulgadas.");

        lblActTDetalle.setText("Detalle:");

        lblActTIngresa.setText("Ingresa:");

        txtaActTDetalle.setEditable(false);
        txtaActTDetalle.setBackground(new java.awt.Color(238, 238, 238));
        txtaActTDetalle.setColumns(20);
        txtaActTDetalle.setRows(3);
        txtaActTDetalle.setBorder(javax.swing.BorderFactory.createLineBorder(javax.swing.UIManager.getDefaults().getColor("Button.darkShadow")));
        scpnltxtaActTDetalle.setViewportView(txtaActTDetalle);

        tbActTroza.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tipo de producto", "Código", "Variedad de madera", "Medidas", "Proveedor", "Unidades", "Precio", "Descripción", "Codigo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        scpnlTblTActualizar.setViewportView(tbActTroza);

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
                .addGroup(pnlActualizarTrozaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblActTIngresa, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxActTCodigo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(scpnltxtaActTDetalle, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlActualizarTrozaLayout.createSequentialGroup()
                        .addComponent(txtActTIngresa, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblActTIngresaPULG))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlActualizarTrozaLayout.createSequentialGroup()
                        .addComponent(lblActTDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(155, 155, 155))
                    .addComponent(lblActTCodigo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnActualizarTroza, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 81, Short.MAX_VALUE)
                .addComponent(scpnlTblTActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 688, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlActualizarTrozaLayout.setVerticalGroup(
            pnlActualizarTrozaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlActualizarTrozaLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnlActualizarTrozaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlActualizarTrozaLayout.createSequentialGroup()
                        .addComponent(lblActTCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxActTCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45)
                        .addComponent(lblActTDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(scpnltxtaActTDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41)
                        .addComponent(lblActTIngresa, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlActualizarTrozaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtActTIngresa, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblActTIngresaPULG, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnActualizarTroza, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(scpnlTblTActualizar, javax.swing.GroupLayout.DEFAULT_SIZE, 416, Short.MAX_VALUE))
                .addContainerGap())
        );

        tbActualizarTipoProd.addTab("", new javax.swing.ImageIcon(getClass().getResource("/recursos/inv_troza.png")), pnlActualizarTroza, "Madera en Troza"); // NOI18N

        lblActTmNombre.setText("Nombre:");

        lblActTmIngresaUNID.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        lblActTmIngresaUNID.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblActTmIngresaUNID.setText("unidades.");

        lblActTmDetalle.setText("Detalle:");

        lblActTmIngresa.setText("Ingresa:");

        txtaActTmDetalle.setEditable(false);
        txtaActTmDetalle.setBackground(new java.awt.Color(238, 238, 238));
        txtaActTmDetalle.setColumns(20);
        txtaActTmDetalle.setRows(3);
        txtaActTmDetalle.setBorder(javax.swing.BorderFactory.createLineBorder(javax.swing.UIManager.getDefaults().getColor("Button.darkShadow")));
        scpnltxtaActTmDetalle.setViewportView(txtaActTmDetalle);

        tbActTerminada.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tipo de producto", "Código", "Variedad de madera", "Medidas", "Proveedor", "Unidades", "Precio", "Descripción", "Codigo"
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

        btnActualizarTerminada.setText("Actualizar Inventario");
        btnActualizarTerminada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarTerminadaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlActualizarTerminadaLayout = new javax.swing.GroupLayout(pnlActualizarTerminada);
        pnlActualizarTerminada.setLayout(pnlActualizarTerminadaLayout);
        pnlActualizarTerminadaLayout.setHorizontalGroup(
            pnlActualizarTerminadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlActualizarTerminadaLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnlActualizarTerminadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblActTmIngresa, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxActTmCodigo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblActTmNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(scpnltxtaActTmDetalle, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlActualizarTerminadaLayout.createSequentialGroup()
                        .addComponent(txtActTmIngresa, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblActTmIngresaUNID))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlActualizarTerminadaLayout.createSequentialGroup()
                        .addComponent(lblActTmDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(155, 155, 155))
                    .addComponent(btnActualizarTerminada, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 81, Short.MAX_VALUE)
                .addComponent(scpnlTblTmActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 688, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlActualizarTerminadaLayout.setVerticalGroup(
            pnlActualizarTerminadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlActualizarTerminadaLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnlActualizarTerminadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlActualizarTerminadaLayout.createSequentialGroup()
                        .addComponent(lblActTmNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxActTmCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45)
                        .addComponent(lblActTmDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(scpnltxtaActTmDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41)
                        .addComponent(lblActTmIngresa, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlActualizarTerminadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtActTmIngresa, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblActTmIngresaUNID, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnActualizarTerminada, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(scpnlTblTmActualizar, javax.swing.GroupLayout.DEFAULT_SIZE, 416, Short.MAX_VALUE))
                .addContainerGap())
        );

        tbActualizarTipoProd.addTab("", new javax.swing.ImageIcon(getClass().getResource("/recursos/inv_terminada.png")), pnlActualizarTerminada, "Madera Terminada"); // NOI18N

        javax.swing.GroupLayout pnl_actualizarLayout = new javax.swing.GroupLayout(pnl_actualizar);
        pnl_actualizar.setLayout(pnl_actualizarLayout);
        pnl_actualizarLayout.setHorizontalGroup(
            pnl_actualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_actualizarLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(tbActualizarTipoProd)
                .addContainerGap())
        );
        pnl_actualizarLayout.setVerticalGroup(
            pnl_actualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_actualizarLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(tbActualizarTipoProd, javax.swing.GroupLayout.PREFERRED_SIZE, 455, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(72, Short.MAX_VALUE))
        );

        tbpnl_modInventario.addTab("Actualizar inventario", pnl_actualizar);

        tbEditarTipoProd.setTabPlacement(javax.swing.JTabbedPane.LEFT);

        lblEditarAcCodigo.setText("Código:");

        lblEditarAcVariedad.setText("Variedad de madera:");

        lblEditarAcMedidas.setText("Medidas:");

        lblEditarAcUnidade.setText("Unidades:");

        lblEditarAcPrecio.setText("Precio por vara:");

        lblEditarAcDescripcion.setText("Descripcion:");

        lblEditarAcMedDE.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        lblEditarAcMedDE.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblEditarAcMedDE.setText("de");

        lblEditarAcMedX.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        lblEditarAcMedX.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblEditarAcMedX.setText("x");

        lblEditarAcMedAncho.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        lblEditarAcMedAncho.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblEditarAcMedAncho.setText("Ancho");

        lblEditarAcMedGrueso.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        lblEditarAcMedGrueso.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblEditarAcMedGrueso.setText("Grueso");

        lblEditarAcMedVaras.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        lblEditarAcMedVaras.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblEditarAcMedVaras.setText("Varas");

        txtaEditarAcDescripcion.setColumns(20);
        txtaEditarAcDescripcion.setRows(3);
        scpnlEditarAcDescripcion.setViewportView(txtaEditarAcDescripcion);

        javax.swing.GroupLayout pnlEditarAcerradaLayout = new javax.swing.GroupLayout(pnlEditarAcerrada);
        pnlEditarAcerrada.setLayout(pnlEditarAcerradaLayout);
        pnlEditarAcerradaLayout.setHorizontalGroup(
            pnlEditarAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEditarAcerradaLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnlEditarAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblEditarAcCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEditarAcCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEditarAcUnidade, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEditarAcUnidades, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(100, 100, 100)
                .addGroup(pnlEditarAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblEditarAcPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEditarAcVariedad, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEditarAcPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxEditarAcVariedad, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 92, Short.MAX_VALUE)
                .addGroup(pnlEditarAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblEditarAcMedidas, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlEditarAcerradaLayout.createSequentialGroup()
                        .addGroup(pnlEditarAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lblEditarAcMedVaras, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtEditarAcMedVaras, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(4, 4, 4)
                        .addComponent(lblEditarAcMedDE, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)
                        .addGroup(pnlEditarAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtEditarAcMedGrueso, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblEditarAcMedGrueso))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblEditarAcMedX, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(3, 3, 3)
                        .addGroup(pnlEditarAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtEditarAcMedAncho, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblEditarAcMedAncho)))
                    .addComponent(lblEditarAcDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(scpnlEditarAcDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        pnlEditarAcerradaLayout.setVerticalGroup(
            pnlEditarAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEditarAcerradaLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnlEditarAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblEditarAcMedidas, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlEditarAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblEditarAcCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblEditarAcVariedad, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlEditarAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlEditarAcerradaLayout.createSequentialGroup()
                        .addGroup(pnlEditarAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlEditarAcerradaLayout.createSequentialGroup()
                                .addGroup(pnlEditarAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtEditarAcMedVaras, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblEditarAcMedDE, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cbxEditarAcVariedad, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblEditarAcMedVaras))
                            .addGroup(pnlEditarAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(pnlEditarAcerradaLayout.createSequentialGroup()
                                    .addComponent(txtEditarAcMedAncho, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(pnlEditarAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblEditarAcMedAncho, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lblEditarAcMedGrueso)))
                                .addGroup(pnlEditarAcerradaLayout.createSequentialGroup()
                                    .addGroup(pnlEditarAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtEditarAcMedGrueso, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblEditarAcMedX, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(21, 21, 21))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblEditarAcDescripcion, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE))
                    .addGroup(pnlEditarAcerradaLayout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(txtEditarAcCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(pnlEditarAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblEditarAcUnidade, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblEditarAcPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlEditarAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtEditarAcPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(scpnlEditarAcDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEditarAcUnidades, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        tbEditarTipoProd.addTab("", new javax.swing.ImageIcon(getClass().getResource("/recursos/inv_acerrada.png")), pnlEditarAcerrada); // NOI18N

        lblEditarTCodigo.setText("Código:");

        lblEditarTVariedadMadera.setText("Variedad de madera:");

        lblEditarTMedidas.setText("Medidas:");

        lblEditarTProveedor.setText("Proveedor:");

        lblEditarTDescripcion.setText("Descripción: ");

        lblEditarTMedPulgadas.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        lblEditarTMedPulgadas.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblEditarTMedPulgadas.setText("pulgadas.");

        txtaEditarTDescripcion.setColumns(20);
        txtaEditarTDescripcion.setRows(3);
        txtaEditarTDescripcion.setTabSize(5);
        scpnlEditarTDescripcion.setViewportView(txtaEditarTDescripcion);

        javax.swing.GroupLayout pnlEditarTrozaLayout = new javax.swing.GroupLayout(pnlEditarTroza);
        pnlEditarTroza.setLayout(pnlEditarTrozaLayout);
        pnlEditarTrozaLayout.setHorizontalGroup(
            pnlEditarTrozaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEditarTrozaLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnlEditarTrozaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblEditarTCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEditarTProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxEditarTProveedor, 0, 295, Short.MAX_VALUE)
                    .addComponent(txtEditarTCodigo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 102, Short.MAX_VALUE)
                .addGroup(pnlEditarTrozaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlEditarTrozaLayout.createSequentialGroup()
                        .addComponent(lblEditarTDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(557, Short.MAX_VALUE))
                    .addGroup(pnlEditarTrozaLayout.createSequentialGroup()
                        .addGroup(pnlEditarTrozaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(scpnlEditarTDescripcion, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblEditarTVariedadMadera, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbxEditarTVariedad, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 92, Short.MAX_VALUE)
                        .addGroup(pnlEditarTrozaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblEditarTMedidas, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnlEditarTrozaLayout.createSequentialGroup()
                                .addComponent(txtEditarTMedPulgadas, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblEditarTMedPulgadas)))
                        .addGap(88, 88, 88))))
        );
        pnlEditarTrozaLayout.setVerticalGroup(
            pnlEditarTrozaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEditarTrozaLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnlEditarTrozaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlEditarTrozaLayout.createSequentialGroup()
                        .addComponent(lblEditarTCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtEditarTCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(19, 19, 19)
                        .addComponent(lblEditarTProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlEditarTrozaLayout.createSequentialGroup()
                        .addGroup(pnlEditarTrozaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(pnlEditarTrozaLayout.createSequentialGroup()
                                .addComponent(lblEditarTVariedadMadera, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbxEditarTVariedad, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlEditarTrozaLayout.createSequentialGroup()
                                .addComponent(lblEditarTMedidas, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pnlEditarTrozaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtEditarTMedPulgadas, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblEditarTMedPulgadas, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(24, 24, 24)
                        .addComponent(lblEditarTDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlEditarTrozaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scpnlEditarTDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxEditarTProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        tbEditarTipoProd.addTab("", new javax.swing.ImageIcon(getClass().getResource("/recursos/inv_troza.png")), pnlEditarTroza); // NOI18N

        lblEditarTmCodigo.setText("Código:");

        lblEditarTmNombre.setText("Nombre:");

        lblEditarTmVariedad.setText("Variedad de madera:");

        lblEditarTmPrecio.setText("Precio:");

        javax.swing.GroupLayout pnlEditarTerminadaLayout = new javax.swing.GroupLayout(pnlEditarTerminada);
        pnlEditarTerminada.setLayout(pnlEditarTerminadaLayout);
        pnlEditarTerminadaLayout.setHorizontalGroup(
            pnlEditarTerminadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEditarTerminadaLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnlEditarTerminadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblEditarTmNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlEditarTerminadaLayout.createSequentialGroup()
                        .addGroup(pnlEditarTerminadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblEditarTmCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtEditarTmCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtEditarTmNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(100, 100, 100)
                        .addGroup(pnlEditarTerminadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblEditarTmVariedad, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbxEditarTmVariedad, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(94, 94, 94)
                        .addGroup(pnlEditarTerminadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblEditarTmPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtEditarTmPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlEditarTerminadaLayout.setVerticalGroup(
            pnlEditarTerminadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEditarTerminadaLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnlEditarTerminadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEditarTmCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEditarTmVariedad, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEditarTmPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlEditarTerminadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEditarTmCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEditarTmPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxEditarTmVariedad, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(lblEditarTmNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtEditarTmNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tbEditarTipoProd.addTab("", new javax.swing.ImageIcon(getClass().getResource("/recursos/inv_terminada.png")), pnlEditarTerminada); // NOI18N

        tblEditar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tipo de producto", "Código", "Variedad de madera", "Medidas", "Proveedor", "Unidades", "Precio", "Descripción", "Codigo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblEditar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblEditarMouseClicked(evt);
            }
        });
        scpnlTbEditar.setViewportView(tblEditar);

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
                        .addGroup(pnl_editarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tbEditarTipoProd)
                            .addComponent(scpnlTbEditar)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_editarLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        pnl_editarLayout.setVerticalGroup(
            pnl_editarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_editarLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(tbEditarTipoProd, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(scpnlTbEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        tbpnl_modInventario.addTab("Editar producto", pnl_editar);

        lblDeshabSelectInventario.setText("Seleccionar Producto:");

        tbProductosActivos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tipo de producto", "Código", "Variedad de madera", "Medidas", "Proveedor", "Unidades", "Precio", "Descripción", "Codigo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
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
        scpnlDeshabProducto.setViewportView(tbProductosActivos);

        tbDeshab.addTab("Activos", scpnlDeshabProducto);

        tbProductosInactivos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tipo de producto", "Código", "Variedad de madera", "Medidas", "Proveedor", "Unidades", "Precio", "Descripción", "Codigo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
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
        scpnlHabilitarProducto.setViewportView(tbProductosInactivos);

        tbDeshab.addTab("Inactivos", scpnlHabilitarProducto);

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
                    .addComponent(lblDeshabSelectInventario, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlHabilitarLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(pnlDeshabContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(654, 654, 654)
                        .addComponent(btn_deshabilitar, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(tbDeshab, javax.swing.GroupLayout.PREFERRED_SIZE, 1144, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlHabilitarLayout.setVerticalGroup(
            pnlHabilitarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlHabilitarLayout.createSequentialGroup()
                .addGroup(pnlHabilitarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlHabilitarLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_deshabilitar, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlHabilitarLayout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(lblDeshabSelectInventario, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tbDeshab, javax.swing.GroupLayout.DEFAULT_SIZE, 409, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(pnlDeshabContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(17, 17, 17))
        );

        tbpnl_modInventario.addTab("Habilitar inventario", pnlHabilitar);

        javax.swing.GroupLayout pnl_modInventarioLayout = new javax.swing.GroupLayout(pnl_modInventario);
        pnl_modInventario.setLayout(pnl_modInventarioLayout);
        pnl_modInventarioLayout.setHorizontalGroup(
            pnl_modInventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_modInventarioLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tbpnl_modInventario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnl_modInventarioLayout.setVerticalGroup(
            pnl_modInventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_modInventarioLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(tbpnl_modInventario, javax.swing.GroupLayout.DEFAULT_SIZE, 589, Short.MAX_VALUE)
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

        String medidas;
        TipoMadera tipoMad;
        if (verificarTipoMadera("CREAR").equals("ASERRADA")) {

            medidas = txtNuevoAcMedVaras.getText().trim() + " de "
                    + txtNuevoAcMedGrueso.getText().trim() + "x"
                    + txtNuevoAcMedAncho.getText().trim();
            tipoMad = (TipoMadera) cbxNuevoAcVariedad.getSelectedItem();

            boolean cprod = crearProducto(txtNuevoAcCodigo.getText().trim(),
                    tipoMad.getCodigo(), medidas, verificarTipoMadera("CREAR"),
                    txtNuevoAcUnidades.getText().trim(),
                    txtNuevoAcPrecio.getText().trim(),
                    txtaNuevoAcDescripcion.getText().trim(), "0");
            if(cprod) {
                limpiarCampos("Crear","ASERRADA");
            }
        } else if (verificarTipoMadera("CREAR").equals("TROZA")) {

            medidas = txtNuevoTMedPulgadas.getText().trim() + " pulgadas";
            tipoMad = (TipoMadera) cbxNuevoTVariedad.getSelectedItem();
            Proveedor pv = (Proveedor) cbxNuevoTProveedor.getSelectedItem();

            boolean cprod = crearProducto(txtNuevoTCodigo.getText(),
                    tipoMad.getCodigo(), medidas, verificarTipoMadera("CREAR"), "0", "0",
                    txtaNuevoTDescripcion.getText().trim(),
                    pv.getCodProveedor());
            if(cprod) {
                limpiarCampos("Crear","TROZA");
            }
        } else if (verificarTipoMadera("CREAR").equals("TERMINADA")) {

            tipoMad = (TipoMadera) cbxNuevoTmVariedad.getSelectedItem();

            boolean cprod = crearProducto(txtNuevoTmCodigo.getText(),
                    tipoMad.getCodigo(), "0", verificarTipoMadera("CREAR"), "0",
                    txtNuevoTmPrecio.getText().trim(),
                    txtNuevoTmNombre.getText().trim(), "0");
            if(cprod) {
                limpiarCampos("Crear","TERMINADA");
            }
        }
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnActualizarTerminadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarTerminadaActionPerformed
        if (!txtActTmIngresa.getText().isEmpty()) {
            if (cbxActTmCodigo.getSelectedIndex() >= 0) {
                try {
                    int unidades = Integer.parseInt(txtActTmIngresa.getText());
                    String codigo = cbxActTmCodigo.getItemAt(cbxActTmCodigo.getSelectedIndex()).getCodigo();
                    controlador.actualizarInventario("TERMINADA", unidades, codigo);
                    cargarTablas();
                    cargarCombos();
                    limpiarCampos("ACTUALIZAR", "TERMINADA");
                } catch (NumberFormatException ex) {
                    
                }catch (NullPointerException ex) {
                    
                }
            } else {
                
            }
        } else {
            
        }
    }//GEN-LAST:event_btnActualizarTerminadaActionPerformed

    private void btnActualizarTrozaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarTrozaActionPerformed
        if (!txtActTIngresa.getText().isEmpty()) {
            if (cbxActTCodigo.getSelectedIndex() >= 0) {
                try {
                    int unidades = Integer.parseInt(txtActTIngresa.getText());
                    String codigo = cbxActTCodigo.getItemAt(cbxActTCodigo.getSelectedIndex()).getCodigo();
                    controlador.actualizarInventario("TROZA", unidades, codigo);
                    cargarTablas();
                    cargarCombos();
                    limpiarCampos("ACTUALIZAR", "TROZA");
                } catch (NumberFormatException ex) {
                    
                }catch (NullPointerException ex) {
                    
                }
            } else {
                
            }
        } else {
            
        }
    }//GEN-LAST:event_btnActualizarTrozaActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        try {
            model = (DefaultTableModel) tblEditar.getModel();
            int indiceFila = tblEditar.getSelectedRow();
            String codigo = (String) model.getValueAt(indiceFila, 8);
            
            String medidas;
            TipoMadera tipoMad;
            if (verificarTipoMadera("EDITAR").equals("ASERRADA")) {

                medidas = txtEditarAcMedVaras.getText().trim() + " varas de "
                        + txtEditarAcMedGrueso.getText().trim() + "x"
                        + txtEditarAcMedAncho.getText().trim();
                tipoMad = (TipoMadera) cbxEditarAcVariedad.getSelectedItem();

                boolean aprod = actualizarProducto(txtEditarAcCodigo.getText().trim(),
                        tipoMad.getCodigo(), medidas, txtEditarAcUnidades.getText().trim(),
                        txtEditarAcPrecio.getText().trim(),
                        txtaEditarAcDescripcion.getText().trim(), "0", codigo);
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

    private void tblEditarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblEditarMouseClicked
        try {
            model = (DefaultTableModel) tblEditar.getModel();
            
            int indiceFila = tblEditar.getSelectedRow();
            System.out.println(tblEditar.getValueAt(indiceFila, 7));
            String codigo = (String) model.getValueAt(indiceFila, 8);
            
            Madera prod = new Madera();
            for (Madera p: productos) {
                if (p.getCodigo().equals(codigo)) {
                    prod = p;
                    break;
                }
            }
            System.out.println("FLARBI");
            if (prod.getTipoProducto().equals("ASERRADA")) {
                String[] medidas = new String[3];
                System.out.println(prod.getMedidas().substring(0, prod.getMedidas().indexOf(" varas de ")));
                medidas[0] = prod.getMedidas().substring(0, prod.getMedidas().indexOf(" varas de "));
                medidas[1] = prod.getMedidas().substring(prod.getMedidas().indexOf("varas de ")+8, prod.getMedidas().indexOf("x"));
                medidas[2] = prod.getMedidas().substring(prod.getMedidas().indexOf("x")+1, prod.getMedidas().length());
                
                txtEditarAcMedVaras.setText(medidas[0]);
                txtEditarAcMedGrueso.setText(medidas[1]);
                txtEditarAcMedAncho.setText(medidas[2]);
                
                for (int i=0; i<cbxEditarAcVariedad.getItemCount(); i++) {
                    if (cbxEditarAcVariedad.getItemAt(i).getDescripcion().equals(prod.getDescTipoMadera())) {
                        cbxEditarAcVariedad.setSelectedIndex(i);
                    }
                }

                txtEditarAcCodigo.setText(prod.getCodTipoMadera());
                txtEditarAcUnidades.setText(String.valueOf(prod.getUnidades()));
                txtEditarAcPrecio.setText(String.valueOf(prod.getPrecioXvara()));
                txtaEditarAcDescripcion.setText(prod.getDescripcion());
                
                tbEditarTipoProd.setSelectedIndex(0);
                
            } else if (prod.getTipoProducto().equals("TROZA")) {
                
                String medidas = prod.getMedidas().substring(0, prod.getMedidas().indexOf("pulgadas"));
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
    }//GEN-LAST:event_tblEditarMouseClicked

    private void tbProductosActivosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbProductosActivosMouseClicked
        try {
            model = (DefaultTableModel) tbProductosActivos.getModel();
            int selectedRowIndex = tbProductosActivos.getSelectedRow();
            String codigo = (String) model.getValueAt(selectedRowIndex, 8);

            for (int i = 0; i < productos.size(); i++) {
                if (productos.get(i).getCodigo().equals(codigo)) {
                    //Si el codigo coincide
                    if (productos.get(i).getEstado().equals(Estado.Activo)) {
                        //Verifica el tipo de estado
                        rbDeshabDeshabProducto.setSelected(true);
                    } else {
                        rbDeshabHabilitarProducto.setSelected(true);
                    }
                }
            }
        } catch (ArrayIndexOutOfBoundsException | NullPointerException ex) {
            msg.mostrarMensaje(JOptionPane.ERROR_MESSAGE, TipoMensaje.ANY_ROW_SELECTED);
        }
        catch (Exception ex) {
            msg.mostrarMensaje(JOptionPane.ERROR_MESSAGE, TipoMensaje.SOMETHING_WENT_WRONG);
        }
    }//GEN-LAST:event_tbProductosActivosMouseClicked

    private void tbProductosInactivosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbProductosInactivosMouseClicked
        try {
            model = (DefaultTableModel) tbProductosInactivos.getModel();
            int selectedRowIndex = tbProductosInactivos.getSelectedRow();
            String codigo = (String) model.getValueAt(selectedRowIndex, 8);

            for (int i = 0; i < productos.size(); i++) {
                if (productos.get(i).getCodigo().equals(codigo)) {
                    //Si el codigo coincide
                    if (productos.get(i).getEstado().equals(Estado.Activo)) {
                        //Verifica el tipo de estado
                        rbDeshabDeshabProducto.setSelected(true);
                    } else {
                        rbDeshabHabilitarProducto.setSelected(true);
                    }
                }
            }
        } catch (ArrayIndexOutOfBoundsException | NullPointerException ex) {
            msg.mostrarMensaje(JOptionPane.ERROR_MESSAGE, TipoMensaje.ANY_ROW_SELECTED);
        }
        catch (Exception ex) {
            msg.mostrarMensaje(JOptionPane.ERROR_MESSAGE, TipoMensaje.SOMETHING_WENT_WRONG);
        }
    }//GEN-LAST:event_tbProductosInactivosMouseClicked

    private void txtNuevoAcMedGruesoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNuevoAcMedGruesoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNuevoAcMedGruesoActionPerformed

    private void btnCrearProvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearProvActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCrearProvActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgDeshab;
    private javax.swing.JButton btnActualizarAserrada;
    private javax.swing.JButton btnActualizarTerminada;
    private javax.swing.JButton btnActualizarTroza;
    private javax.swing.JButton btnCrearProv;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btn_deshabilitar;
    private javax.swing.JComboBox<Madera> cbxActAcCodigo;
    private javax.swing.JComboBox<Madera> cbxActTCodigo;
    private javax.swing.JComboBox<Madera> cbxActTmCodigo;
    private javax.swing.JComboBox<TipoMadera> cbxEditarAcVariedad;
    private javax.swing.JComboBox<Proveedor> cbxEditarTProveedor;
    private javax.swing.JComboBox<TipoMadera> cbxEditarTVariedad;
    private javax.swing.JComboBox<TipoMadera> cbxEditarTmVariedad;
    private javax.swing.JComboBox<Object> cbxNuevoAcVariedad;
    private javax.swing.JComboBox<Object> cbxNuevoTProveedor;
    private javax.swing.JComboBox<Object> cbxNuevoTVariedad;
    private javax.swing.JComboBox<Object> cbxNuevoTmVariedad;
    private javax.swing.JFormattedTextField jFormattedTextField1;
    private javax.swing.JLabel lblActAcCodigo;
    private javax.swing.JLabel lblActAcDetalle;
    private javax.swing.JLabel lblActAcIngresa;
    private javax.swing.JLabel lblActAcIngresaUNID;
    private javax.swing.JLabel lblActTCodigo;
    private javax.swing.JLabel lblActTDetalle;
    private javax.swing.JLabel lblActTIngresa;
    private javax.swing.JLabel lblActTIngresaPULG;
    private javax.swing.JLabel lblActTmDetalle;
    private javax.swing.JLabel lblActTmIngresa;
    private javax.swing.JLabel lblActTmIngresaUNID;
    private javax.swing.JLabel lblActTmNombre;
    private javax.swing.JLabel lblDeshabSelectInventario;
    private javax.swing.JLabel lblEditarAcCodigo;
    private javax.swing.JLabel lblEditarAcDescripcion;
    private javax.swing.JLabel lblEditarAcMedAncho;
    private javax.swing.JLabel lblEditarAcMedDE;
    private javax.swing.JLabel lblEditarAcMedGrueso;
    private javax.swing.JLabel lblEditarAcMedVaras;
    private javax.swing.JLabel lblEditarAcMedX;
    private javax.swing.JLabel lblEditarAcMedidas;
    private javax.swing.JLabel lblEditarAcPrecio;
    private javax.swing.JLabel lblEditarAcUnidade;
    private javax.swing.JLabel lblEditarAcVariedad;
    private javax.swing.JLabel lblEditarTCodigo;
    private javax.swing.JLabel lblEditarTDescripcion;
    private javax.swing.JLabel lblEditarTMedPulgadas;
    private javax.swing.JLabel lblEditarTMedidas;
    private javax.swing.JLabel lblEditarTProveedor;
    private javax.swing.JLabel lblEditarTVariedadMadera;
    private javax.swing.JLabel lblEditarTmCodigo;
    private javax.swing.JLabel lblEditarTmNombre;
    private javax.swing.JLabel lblEditarTmPrecio;
    private javax.swing.JLabel lblEditarTmVariedad;
    private javax.swing.JLabel lblListadoInventario;
    private javax.swing.JLabel lblNuevoAcCodigo;
    private javax.swing.JLabel lblNuevoAcDescripcion;
    private javax.swing.JLabel lblNuevoAcMedDE;
    private javax.swing.JLabel lblNuevoAcMedX;
    private javax.swing.JLabel lblNuevoAcMedidas;
    private javax.swing.JLabel lblNuevoAcPrecio;
    private javax.swing.JLabel lblNuevoAcUnidades;
    private javax.swing.JLabel lblNuevoAcVariedad;
    private javax.swing.JLabel lblNuevoTCodigo;
    private javax.swing.JLabel lblNuevoTDescripcion;
    private javax.swing.JLabel lblNuevoTMedidas;
    private javax.swing.JLabel lblNuevoTProveedor;
    private javax.swing.JLabel lblNuevoTVariedadMadera;
    private javax.swing.JLabel lblNuevoTmCodigo;
    private javax.swing.JLabel lblNuevoTmNombre;
    private javax.swing.JLabel lblNuevoTmPrecio;
    private javax.swing.JLabel lblNuevoTmVariedad;
    private javax.swing.JPanel pnlActualizarAcerrada;
    private javax.swing.JPanel pnlActualizarTerminada;
    private javax.swing.JPanel pnlActualizarTroza;
    private javax.swing.JPanel pnlDeshabContainer;
    private javax.swing.JPanel pnlEditarAcerrada;
    private javax.swing.JPanel pnlEditarTerminada;
    private javax.swing.JPanel pnlEditarTroza;
    private javax.swing.JPanel pnlHabilitar;
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
    private javax.swing.JScrollPane scpnlDeshabProducto;
    private javax.swing.JScrollPane scpnlEditarAcDescripcion;
    private javax.swing.JScrollPane scpnlEditarTDescripcion;
    private javax.swing.JScrollPane scpnlHabilitarProducto;
    private javax.swing.JScrollPane scpnlNuevoAcDescripcion;
    private javax.swing.JScrollPane scpnlNuevoTDescripcion;
    private javax.swing.JScrollPane scpnlTbEditar;
    private javax.swing.JScrollPane scpnlTbNuevo;
    private javax.swing.JScrollPane scpnlTblAcActualizar;
    private javax.swing.JScrollPane scpnlTblListadoInventario;
    private javax.swing.JScrollPane scpnlTblTActualizar;
    private javax.swing.JScrollPane scpnlTblTmActualizar;
    private javax.swing.JScrollPane scpnltxtaActAcDetalle;
    private javax.swing.JScrollPane scpnltxtaActTDetalle;
    private javax.swing.JScrollPane scpnltxtaActTmDetalle;
    private javax.swing.JTable tbActAcerrada;
    private javax.swing.JTable tbActTerminada;
    private javax.swing.JTable tbActTroza;
    private javax.swing.JTabbedPane tbActualizarTipoProd;
    private javax.swing.JTabbedPane tbDeshab;
    private javax.swing.JTabbedPane tbEditarTipoProd;
    private javax.swing.JTable tbListadoInventario;
    private javax.swing.JTabbedPane tbNuevoTipoProd;
    private javax.swing.JTable tbProductosActivos;
    private javax.swing.JTable tbProductosInactivos;
    private javax.swing.JTable tblAgregarInv;
    private javax.swing.JTable tblEditar;
    private javax.swing.JTabbedPane tbpnl_modInventario;
    private javax.swing.JTextField txtActAcIngresa;
    private javax.swing.JTextField txtActTIngresa;
    private javax.swing.JTextField txtActTmIngresa;
    private javax.swing.JTextField txtEditarAcCodigo;
    private javax.swing.JTextField txtEditarAcMedAncho;
    private javax.swing.JTextField txtEditarAcMedGrueso;
    private javax.swing.JTextField txtEditarAcMedVaras;
    private javax.swing.JTextField txtEditarAcPrecio;
    private javax.swing.JTextField txtEditarAcUnidades;
    private javax.swing.JTextField txtEditarTCodigo;
    private javax.swing.JTextField txtEditarTMedPulgadas;
    private javax.swing.JTextField txtEditarTmCodigo;
    private javax.swing.JTextField txtEditarTmNombre;
    private javax.swing.JTextField txtEditarTmPrecio;
    private javax.swing.JTextField txtListadoInventario;
    private javax.swing.JTextField txtNuevoAcCodigo;
    private javax.swing.JTextField txtNuevoAcMedAncho;
    private javax.swing.JTextField txtNuevoAcMedGrueso;
    private javax.swing.JTextField txtNuevoAcMedVaras;
    private javax.swing.JTextField txtNuevoAcPrecio;
    private javax.swing.JTextField txtNuevoAcUnidades;
    private javax.swing.JTextField txtNuevoTCodigo;
    private javax.swing.JTextField txtNuevoTMedPulgadas;
    private javax.swing.JTextField txtNuevoTmCodigo;
    private javax.swing.JTextField txtNuevoTmNombre;
    private javax.swing.JTextField txtNuevoTmPrecio;
    private javax.swing.JTextArea txtaActAcDetalle;
    private javax.swing.JTextArea txtaActTDetalle;
    private javax.swing.JTextArea txtaActTmDetalle;
    private javax.swing.JTextArea txtaEditarAcDescripcion;
    private javax.swing.JTextArea txtaEditarTDescripcion;
    private javax.swing.JTextArea txtaNuevoAcDescripcion;
    private javax.swing.JTextArea txtaNuevoTDescripcion;
    // End of variables declaration//GEN-END:variables

}
