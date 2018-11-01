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
import controladores.CtrTipoProducto;
import java.util.ArrayList;
import javax.swing.ComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import logica.negocio.Cliente;
import logica.negocio.Madera;
import logica.negocio.Proveedor;
import logica.negocio.TipoMadera;
import logica.negocio.TipoProducto;
import logica.servicios.Mensaje;
import logica.servicios.Regex;
import util.Estado;
import util.TipoMensaje;
import util.TipoProd;

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
        
        tmaderas = ctrTipoMadera.obtenerTiposMadera();
        tmaderas.forEach((item) -> {
            cbxNuevoTVariedad.addItem(item);            
            cbxEditarTVariedad.addItem(item);
            cbxNuevoAcVariedad.addItem(item);
            cbxEditarAcVariedad.addItem(item);
            cbxNuevoTmVariedad.addItem(item);
            cbxEditarTmVariedad.addItem(item);
        });
        cbxNuevoTProveedor.setSelectedIndex(0);
        cbxNuevoTVariedad.setSelectedIndex(0);
    }

    /**
     * Llena las tablas del modulo con los productos.
     */
    public void cargarTablas() {

        productos = controlador.obtenerProductos();
        cargarProductosJTable(tbListadoInventario, true);
        cargarProductosJTable(tblAgregarInv, true);
        cargarProductosJTable(tblEditar, true);
        cargarProductosJTable(tbProductosActivos, true);
        cargarProductosJTable(tbProductosInactivos, false);
    }

    /**
     * Cargar la tabla (modelo) con los usuarios existentes.
     *
     * @param tabla Nombre de la tabla a llenar
     * @param estado Estado del producto a incresar
     */
    public void cargarProductosJTable(JTable tabla, boolean estado) {
        Object[] row = new Object[8];
        model = (DefaultTableModel) tabla.getModel();
        model.setRowCount(0);

        for (int i = 0; i < productos.size(); i++) {
            //tipo prod- codigo- variedad- medidas- proveedor- unidades- precio- descripción
            if (productos.get(i).getEstado().equals(Estado.Activo) && estado) {
                row[0] = productos.get(i).getTipoProducto();
                row[1] = productos.get(i).getCodProducto();////////
                row[2] = productos.get(i).getDescTipoMadera();
                row[3] = productos.get(i).getMedidas();
                row[4] = productos.get(i).getNomProveedor();//getCodProveedor() == null ? "No aplica" : productos.get(i).getCodProveedor();
                row[5] = productos.get(i).getUnidades() <= 0 ? 
                        "No aplica" : productos.get(i).getUnidades();
                row[6] = productos.get(i).getPrecioXvara();
                row[7] = productos.get(i).getDescripcion();
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
                model.addRow(row);
            }
        }
    }
    public String verificarTipoMadera() {
        
        int tipoProd = tbNuevoTipoProd.getSelectedIndex();
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
     * @param tmad
     */
    public void limpiarCampos(String panel, String tmad) {

        if (panel.equals("Crear")) {
            switch (tmad) {
                case "aserrada":
                    txtNuevoAcCodigo.setText("");
                    txtNuevoAcMedAncho.setText("");
                    txtNuevoAcMedGrueso.setText("");
                    txtNuevoAcMedVaras.setText("");
                    txtNuevoAcPrecio.setText("");
                    txtNuevoAcUnidades.setText("");
                    txtaNuevoAcDescripcion.setText("");
                    cbxNuevoAcVariedad.removeAllItems();
                    break;
                case "troza":
                    txtNuevoTCodigo.setText("");
                    txtNuevoTMedPulgadas.setText("");
                    txtaNuevoTDescripcion.setText("");
                    cbxNuevoTVariedad.removeAllItems();
                    cbxNuevoTProveedor.removeAllItems();
                    break;
                case "terminada":
                    txtNuevoTmCodigo.setText("");
                    txtNuevoTmNombre.setText("");
                    txtNuevoTmPrecio.setText("");
                    cbxNuevoTmVariedad.removeAll();
                    break;
                default:
                    break;
            }
        } else if (panel.equals("Actualizar")) {
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnl_modInventario = new javax.swing.JPanel();
        tbpnl_modInventario = new javax.swing.JTabbedPane();
        pnl_listado = new javax.swing.JPanel();
        jFormattedTextField1 = new javax.swing.JFormattedTextField();
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
        lblNuevoAcMedAncho = new javax.swing.JLabel();
        lblNuevoAcMedGrueso = new javax.swing.JLabel();
        lblNuevoAcMedVaras = new javax.swing.JLabel();
        txtNuevoAcCodigo = new javax.swing.JTextField();
        txtNuevoAcMedVaras = new javax.swing.JTextField();
        txtNuevoAcMedAncho = new javax.swing.JTextField();
        txtNuevoAcMedGrueso = new javax.swing.JTextField();
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
        lblNuevoAcMedPulgadas = new javax.swing.JLabel();
        txtNuevoTCodigo = new javax.swing.JTextField();
        scpnlNuevoTDescripcion = new javax.swing.JScrollPane();
        txtaNuevoTDescripcion = new javax.swing.JTextArea();
        cbxNuevoTVariedad = new javax.swing.JComboBox<>();
        cbxNuevoTProveedor = new javax.swing.JComboBox<>();
        txtNuevoTMedPulgadas = new javax.swing.JTextField();
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
        btnActualizarProducto = new javax.swing.JButton();
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
        btnActualizarProducto2 = new javax.swing.JButton();
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
        btnActualizarProducto1 = new javax.swing.JButton();
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

        setClosable(true);
        setIconifiable(true);
        setTitle("Módulo de Inventario");
        setPreferredSize(new java.awt.Dimension(1240, 670));

        tbpnl_modInventario.setPreferredSize(new java.awt.Dimension(1208, 645));

        jFormattedTextField1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("₡#,##0.00"))));
        jFormattedTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFormattedTextField1ActionPerformed(evt);
            }
        });

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
                "Tipo de producto", "Código", "Variedad de madera", "Medidas", "Proveedor", "Unidades", "Precio", "Descripción"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
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
                        .addComponent(jFormattedTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
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
                .addContainerGap()
                .addComponent(jFormattedTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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

        lblNuevoAcUnidades.setText("Unidades:");

        lblNuevoAcPrecio.setText("Precio por vara:");

        lblNuevoAcDescripcion.setText("Descripcion:");

        lblNuevoAcMedDE.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        lblNuevoAcMedDE.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNuevoAcMedDE.setText("de");

        lblNuevoAcMedX.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        lblNuevoAcMedX.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNuevoAcMedX.setText("x");

        lblNuevoAcMedAncho.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        lblNuevoAcMedAncho.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNuevoAcMedAncho.setText("Ancho");

        lblNuevoAcMedGrueso.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        lblNuevoAcMedGrueso.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNuevoAcMedGrueso.setText("Grueso");

        lblNuevoAcMedVaras.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        lblNuevoAcMedVaras.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNuevoAcMedVaras.setText("Varas");

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
                    .addComponent(lblNuevoAcUnidades, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNuevoAcUnidades, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(100, 100, 100)
                .addGroup(pnlNuevoAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblNuevoAcPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNuevoAcVariedad, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNuevoAcPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxNuevoAcVariedad, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 92, Short.MAX_VALUE)
                .addGroup(pnlNuevoAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblNuevoAcMedidas, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlNuevoAcerradaLayout.createSequentialGroup()
                        .addGroup(pnlNuevoAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lblNuevoAcMedVaras, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtNuevoAcMedVaras, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(4, 4, 4)
                        .addComponent(lblNuevoAcMedDE, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)
                        .addGroup(pnlNuevoAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNuevoAcMedGrueso, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblNuevoAcMedGrueso))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblNuevoAcMedX, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(3, 3, 3)
                        .addGroup(pnlNuevoAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNuevoAcMedAncho, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblNuevoAcMedAncho)))
                    .addComponent(lblNuevoAcDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(scpnlNuevoAcDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        pnlNuevoAcerradaLayout.setVerticalGroup(
            pnlNuevoAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNuevoAcerradaLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnlNuevoAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblNuevoAcMedidas, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlNuevoAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblNuevoAcCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblNuevoAcVariedad, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlNuevoAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlNuevoAcerradaLayout.createSequentialGroup()
                        .addGroup(pnlNuevoAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlNuevoAcerradaLayout.createSequentialGroup()
                                .addGroup(pnlNuevoAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtNuevoAcMedVaras, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblNuevoAcMedDE, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cbxNuevoAcVariedad, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblNuevoAcMedVaras))
                            .addGroup(pnlNuevoAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(pnlNuevoAcerradaLayout.createSequentialGroup()
                                    .addComponent(txtNuevoAcMedAncho, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(pnlNuevoAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblNuevoAcMedAncho, javax.swing.GroupLayout.DEFAULT_SIZE, 16, Short.MAX_VALUE)
                                        .addComponent(lblNuevoAcMedGrueso)))
                                .addGroup(pnlNuevoAcerradaLayout.createSequentialGroup()
                                    .addGroup(pnlNuevoAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtNuevoAcMedGrueso, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblNuevoAcMedX, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(21, 21, 21))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblNuevoAcDescripcion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnlNuevoAcerradaLayout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(txtNuevoAcCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(pnlNuevoAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblNuevoAcUnidades, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblNuevoAcPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlNuevoAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlNuevoAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtNuevoAcUnidades, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtNuevoAcPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(scpnlNuevoAcDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tbNuevoTipoProd.addTab("", new javax.swing.ImageIcon(getClass().getResource("/recursos/inv_acerrada.png")), pnlNuevoAcerrada); // NOI18N

        lblNuevoTCodigo.setText("Código:");

        lblNuevoTVariedadMadera.setText("Variedad de madera:");

        lblNuevoTMedidas.setText("Medidas:");

        lblNuevoTProveedor.setText("Proveedor:");

        lblNuevoTDescripcion.setText("Descripción: ");

        lblNuevoAcMedPulgadas.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        lblNuevoAcMedPulgadas.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNuevoAcMedPulgadas.setText("pulgadas.");

        txtaNuevoTDescripcion.setColumns(20);
        txtaNuevoTDescripcion.setRows(5);
        scpnlNuevoTDescripcion.setViewportView(txtaNuevoTDescripcion);

        javax.swing.GroupLayout pnlNuevoTrozaLayout = new javax.swing.GroupLayout(pnlNuevoTroza);
        pnlNuevoTroza.setLayout(pnlNuevoTrozaLayout);
        pnlNuevoTrozaLayout.setHorizontalGroup(
            pnlNuevoTrozaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNuevoTrozaLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnlNuevoTrozaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblNuevoTCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNuevoTProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxNuevoTProveedor, 0, 295, Short.MAX_VALUE)
                    .addComponent(txtNuevoTCodigo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 102, Short.MAX_VALUE)
                .addGroup(pnlNuevoTrozaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlNuevoTrozaLayout.createSequentialGroup()
                        .addComponent(lblNuevoTDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(557, Short.MAX_VALUE))
                    .addGroup(pnlNuevoTrozaLayout.createSequentialGroup()
                        .addGroup(pnlNuevoTrozaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(scpnlNuevoTDescripcion, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNuevoTVariedadMadera, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbxNuevoTVariedad, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 92, Short.MAX_VALUE)
                        .addGroup(pnlNuevoTrozaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNuevoTMedidas, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnlNuevoTrozaLayout.createSequentialGroup()
                                .addComponent(txtNuevoTMedPulgadas, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblNuevoAcMedPulgadas)))
                        .addGap(88, 88, 88))))
        );
        pnlNuevoTrozaLayout.setVerticalGroup(
            pnlNuevoTrozaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNuevoTrozaLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnlNuevoTrozaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlNuevoTrozaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(pnlNuevoTrozaLayout.createSequentialGroup()
                            .addComponent(lblNuevoTVariedadMadera, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(cbxNuevoTVariedad, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(pnlNuevoTrozaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlNuevoTrozaLayout.createSequentialGroup()
                                .addGap(36, 36, 36)
                                .addGroup(pnlNuevoTrozaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtNuevoTMedPulgadas, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblNuevoAcMedPulgadas, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(lblNuevoTMedidas, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnlNuevoTrozaLayout.createSequentialGroup()
                        .addComponent(lblNuevoTCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNuevoTCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlNuevoTrozaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlNuevoTrozaLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(lblNuevoTDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(scpnlNuevoTDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlNuevoTrozaLayout.createSequentialGroup()
                        .addComponent(lblNuevoTProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxNuevoTProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addContainerGap(15, Short.MAX_VALUE))
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
                .addContainerGap(32, Short.MAX_VALUE))
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
                "Tipo de producto", "Código", "Variedad de madera", "Medidas", "Proveedor", "Unidades", "Precio", "Descripción"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
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
                .addComponent(tbNuevoTipoProd, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scpnlTbNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
        txtaActAcDetalle.setText("Dejar las tablas todas iguales y solo cargar las de acerrada?\nTodas iguales iguales?\nCambiar las columnas según corresponda?");
        txtaActAcDetalle.setBorder(javax.swing.BorderFactory.createLineBorder(javax.swing.UIManager.getDefaults().getColor("Button.darkShadow")));
        scpnltxtaActAcDetalle.setViewportView(txtaActAcDetalle);

        tbActAcerrada.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tipo de producto", "Código", "Variedad de madera", "Medidas", "Proveedor", "Unidades", "Precio", "Descripción"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        scpnlTblAcActualizar.setViewportView(tbActAcerrada);

        btnActualizarProducto.setText("Actualizar Inventario");
        btnActualizarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarProductoActionPerformed(evt);
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
                    .addComponent(btnActualizarProducto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 84, Short.MAX_VALUE)
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
                        .addComponent(btnActualizarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                "Tipo de producto", "Código", "Variedad de madera", "Medidas", "Proveedor", "Unidades", "Precio", "Descripción"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        scpnlTblTActualizar.setViewportView(tbActTroza);

        btnActualizarProducto2.setText("Actualizar Inventario");
        btnActualizarProducto2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarProducto2ActionPerformed(evt);
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
                    .addComponent(btnActualizarProducto2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 84, Short.MAX_VALUE)
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
                        .addComponent(btnActualizarProducto2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                "Tipo de producto", "Código", "Variedad de madera", "Medidas", "Proveedor", "Unidades", "Precio", "Descripción"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        scpnlTblTmActualizar.setViewportView(tbActTerminada);

        btnActualizarProducto1.setText("Actualizar Inventario");
        btnActualizarProducto1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarProducto1ActionPerformed(evt);
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
                    .addComponent(btnActualizarProducto1, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 84, Short.MAX_VALUE)
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
                        .addComponent(btnActualizarProducto1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                .addContainerGap(37, Short.MAX_VALUE))
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
                                        .addComponent(lblEditarAcMedAncho, javax.swing.GroupLayout.DEFAULT_SIZE, 16, Short.MAX_VALUE)
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
                "Tipo de producto", "Código", "Variedad de madera", "Medidas", "Proveedor", "Unidades", "Precio", "Descripción"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
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
                .addComponent(scpnlTbEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                "Tipo de producto", "Código", "Variedad de madera", "Medidas", "Proveedor", "Unidades", "Precio", "Descripción"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        scpnlDeshabProducto.setViewportView(tbProductosActivos);

        tbDeshab.addTab("Activos", scpnlDeshabProducto);

        tbProductosInactivos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tipo de producto", "Código", "Variedad de madera", "Medidas", "Proveedor", "Unidades", "Precio", "Descripción"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        scpnlHabilitarProducto.setViewportView(tbProductosInactivos);

        tbDeshab.addTab("Inactivos", scpnlHabilitarProducto);

        pnlDeshabContainer.setBorder(javax.swing.BorderFactory.createTitledBorder("Activo:"));

        rbDeshabDeshabProducto.setText("Deshabilitar");

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
                        .addComponent(tbDeshab, javax.swing.GroupLayout.DEFAULT_SIZE, 374, Short.MAX_VALUE)
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_modInventarioLayout.createSequentialGroup()
                .addContainerGap(30, Short.MAX_VALUE)
                .addComponent(tbpnl_modInventario, javax.swing.GroupLayout.PREFERRED_SIZE, 554, javax.swing.GroupLayout.PREFERRED_SIZE)
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
        //        try {
        //            model = (DefaultTableModel) tbl_deshabilitar.getModel();
        //            int selectedRowIndex = tbl_deshabilitar.getSelectedRow();
        //            String codigo = String.valueOf(model.getValueAt(selectedRowIndex, 0));
        //            Estado estado
        //            = rb_deshab_habilitar.isSelected() ? Estado.Activo : Estado.Deshabilitado;
        //
        //            controlador.actualizarUsuario(
        //                String.valueOf(model.getValueAt(selectedRowIndex, 1)),
        //                String.valueOf(model.getValueAt(selectedRowIndex, 2)),
        //                String.valueOf(model.getValueAt(selectedRowIndex, 3)),
        //                Rol.Administrador, estado, codigo);
        //            //Actualizar
        //            cargarTablas();
        //        } catch (Exception e) {
        //            msg.mostrarMensaje(JOptionPane.INFORMATION_MESSAGE,
        //                TipoMensaje.ANY_ROW_SELECTED);
        //        }
    }//GEN-LAST:event_btn_deshabilitarActionPerformed

    private void btnActualizarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarProductoActionPerformed
        
        TipoMadera tMadera;// = cmbCrearTipoMadera.getItemAt(cmbCrearTipoMadera.getSelectedIndex());
        TipoProducto tProducto;// = cmbCrearTipoProducto.getItemAt(cmbCrearTipoProducto.getSelectedIndex());
        
        String medida; TipoProd tipo;// = tProducto.getTipo();
        /*switch (tipo) {
            case TROZA:
                medida = txtCrearMedTroza.getText() + " pulgadas";
                break;
            case ASERRADA:
                medida = txtCrearMedVaras.getText() + " varas de "
                        + txtCrearMedAncho.getText() + " x " 
                        + txtCrearMedGrueso.getText();
                break;
            case TERMINADA:
                medida = txtCrearMedTermi.getText() + " de largo";
                break;
            default:
                medida = "No medida";
                break;
        }*/

        /*String nombre = tMadera + " " + medida;

        crearProducto(txtCrearCodigoProducto.getText(), nombre, 
            tMadera.getCodigo(), medida, tProducto.getCodigo(), 
            txtCrearCantidad.getText(), txtCrearPrecioVara.getText(), 
            txtCrearDescripcionProducto.getText());
        System.out.println(txtCrearCodigoProducto.getText() +"|"+ nombre +"|"+ tMadera.getCodigo() +"|"+ medida+"|"+ tProducto.getCodigo() +"|"+ txtCrearCantidad.getText()+"|"+ txtCrearPrecioVara.getText()+"|"+ txtCrearDescripcionProducto.getText());*/
    }//GEN-LAST:event_btnActualizarProductoActionPerformed

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
        if (verificarTipoMadera().equals("ASERRADA")) {

            medidas = txtNuevoAcMedVaras.getText().trim() + " de "
                    + txtNuevoAcMedGrueso.getText().trim() + "x"
                    + txtNuevoAcMedAncho.getText().trim();
            tipoMad = (TipoMadera) cbxNuevoAcVariedad.getSelectedItem();

            boolean cprod = crearProducto(txtNuevoAcCodigo.getText().trim(),
                    tipoMad.getCodigo(), medidas, verificarTipoMadera(),
                    txtNuevoAcUnidades.getText().trim(),
                    txtNuevoAcPrecio.getText().trim(),
                    txtaNuevoAcDescripcion.getText().trim(), "0");
            if(cprod) {
                limpiarCampos("Crear","ASERRADA");
            }
        } else if (verificarTipoMadera().equals("TROZA")) {

            medidas = txtNuevoTMedPulgadas.getText().trim() + " pulgadas";
            tipoMad = (TipoMadera) cbxNuevoTVariedad.getSelectedItem();
            Proveedor pv = (Proveedor) cbxNuevoTProveedor.getSelectedItem();

            boolean cprod = crearProducto(txtNuevoTCodigo.getText(),
                    tipoMad.getCodigo(), medidas, verificarTipoMadera(), "0", "0",
                    txtaNuevoTDescripcion.getText().trim(),
                    pv.getCodProveedor());
            if(cprod) {
                limpiarCampos("Crear","TROZA");
            }
        } else if (verificarTipoMadera().equals("TERMINADA")) {

            tipoMad = (TipoMadera) cbxNuevoTmVariedad.getSelectedItem();

            boolean cprod = crearProducto(txtNuevoTmCodigo.getText(),
                    tipoMad.getCodigo(), "0", verificarTipoMadera(), "0",
                    txtNuevoTmPrecio.getText().trim(),
                    txtNuevoTmNombre.getText().trim(), "0");
            if(cprod) {
                limpiarCampos("Crear","TERMINADA");
            }
        }
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnActualizarProducto1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarProducto1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnActualizarProducto1ActionPerformed

    private void btnActualizarProducto2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarProducto2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnActualizarProducto2ActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEditarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizarProducto;
    private javax.swing.JButton btnActualizarProducto1;
    private javax.swing.JButton btnActualizarProducto2;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btn_deshabilitar;
    private javax.swing.JComboBox<Object> cbxActAcCodigo;
    private javax.swing.JComboBox<Object> cbxActTCodigo;
    private javax.swing.JComboBox<Object> cbxActTmCodigo;
    private javax.swing.JComboBox<Object> cbxEditarAcVariedad;
    private javax.swing.JComboBox<Object> cbxEditarTProveedor;
    private javax.swing.JComboBox<Object> cbxEditarTVariedad;
    private javax.swing.JComboBox<Object> cbxEditarTmVariedad;
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
    private javax.swing.JLabel lblNuevoAcMedAncho;
    private javax.swing.JLabel lblNuevoAcMedDE;
    private javax.swing.JLabel lblNuevoAcMedGrueso;
    private javax.swing.JLabel lblNuevoAcMedPulgadas;
    private javax.swing.JLabel lblNuevoAcMedVaras;
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
