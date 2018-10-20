/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacion;

import controladores.CtrAcceso;
import controladores.CtrMadera;
import controladores.CtrTipoMadera;
import controladores.CtrTipoProducto;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import logica.negocio.Cliente;
import logica.negocio.Madera;
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

    private static ItnFrmInventario instancia = null;
    private static CtrMadera controlador;
    private static CtrAcceso sesion;
    private static ArrayList<Madera> productos;
    private static CtrTipoProducto ctrTipoProducto;
    private static CtrTipoMadera ctrTipoMadera;
    private static ArrayList<TipoProducto> tproductos;
    private static ArrayList<TipoMadera> tmaderas;
    private DefaultTableModel model;
    private final Regex verificacion;
    private static Mensaje msg;

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
        ctrTipoProducto = new CtrTipoProducto();
        ctrTipoMadera = new CtrTipoMadera();
        tproductos = new ArrayList<>();
        tmaderas = new ArrayList<>();
        verificacion = new Regex();
        msg = new Mensaje();
        
        cargarCombos();
        
        cargarTablas();

        pnlCrearMedidasTroza.setVisible(false);
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
     * Coloca el panel de medidas indicada de acuerdo al tipo de producto que se
     * selecciona.
     */
    private void cambiarPanelMedidas(TipoProd tipo) {
                
        switch (tipo) {
            case TROZA:
                pnlCrearMedidasAcerrada.setVisible(false);
                pnlCrearMedidasTerminada.setVisible(false);
                pnlCrearMedidasTroza.setVisible(true);
                pnlCrearMedidasTroza.setBounds(25, 160, 295, 74);
                pnl_agregar.add(pnlCrearMedidasTroza);
                txtCrearPrecioVara.setEnabled(false);
                txtCrearPrecioVara.setText("0");
                pnl_agregar.repaint();
                //instancia.pack();
                break;
            case ASERRADA:
                pnlCrearMedidasTroza.setVisible(false);
                pnlCrearMedidasTerminada.setVisible(false);
                pnlCrearMedidasAcerrada.setVisible(true);
                pnlCrearMedidasAcerrada.setBounds(25, 160, 295, 74);
                pnl_agregar.add(pnlCrearMedidasAcerrada);
                txtCrearPrecioVara.setEnabled(true);
                txtCrearPrecioVara.setText("");
                instancia.pack();
                break;
            case TERMINADA:
                pnlCrearMedidasTroza.setVisible(false);
                pnlCrearMedidasAcerrada.setVisible(false);
                pnlCrearMedidasTerminada.setVisible(true);
                pnlCrearMedidasTerminada.setBounds(25, 160, 295, 74);
                pnl_agregar.add(pnlCrearMedidasTerminada);
                txtCrearPrecioVara.setEnabled(true);
                txtCrearPrecioVara.setText("");
                instancia.pack();
                break;
            default:
                pnlCrearMedidasAcerrada.setVisible(false);
                pnlCrearMedidasTroza.setVisible(false);
                txtCrearPrecioVara.setEnabled(true);
                txtCrearPrecioVara.setText("");
                break;
        }
    }

    /**
     * Carga los combo box según corresponda con el tipo de madera y producto.
     */
    private void cargarCombos() {
        
        tproductos = ctrTipoProducto.obtenerTiposProducto();  
        for (TipoProducto item : tproductos) {
            cmbCrearTipoProducto.addItem(item);
            cmbEditarTipoProducto.addItem(item);
        }
        
        tmaderas = ctrTipoMadera.obtenerTiposMadera();
        tmaderas.forEach((item) -> {
            cmbCrearTipoMadera.addItem(item);
            cmbEditarTipoMadera.addItem(item);
        });
        cmbCrearTipoProducto.setSelectedIndex(0);
        cmbCrearTipoMadera.setSelectedIndex(0);
    }

    /**
     * Llena las tablas del modulo con los productos.
     */
    public void cargarTablas() {

        productos = controlador.obtenerProductos();
        cargarProductosJTable(tbListadoInventario, true);
        cargarProductosJTable(tbl_crear, true);
        cargarProductosJTable(tblProductosInactivos, false);
        cargarProductosJTable(tblProductosActivos, true);
        cargarProductosJTable(tbl_editar, true);
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

            if (productos.get(i).getEstado().equals(Estado.Activo) && estado) {
                row[0] = productos.get(i).getCodProducto();
                row[1] = productos.get(i).getNombre();
                row[2] = productos.get(i).getDescTipoMadera();
                row[3] = productos.get(i).getMedidas();
                row[4] = productos.get(i).getDescTipoProducto();
                row[5] = productos.get(i).getCantidad() <= 0 ? 
                        "No aplica" : productos.get(i).getCantidad();
                row[6] = productos.get(i).getPrecioXvara();
                row[7] = productos.get(i).getDescripcion();
                model.addRow(row);
            }
            if (productos.get(i).getEstado().equals(Estado.Deshabilitado) && !estado) {
                row[0] = productos.get(i).getCodProducto();
                row[1] = productos.get(i).getNombre();
                row[2] = productos.get(i).getDescTipoMadera();
                row[3] = productos.get(i).getMedidas();
                row[4] = productos.get(i).getDescTipoProducto();
                row[5] = productos.get(i).getCantidad() <= 0 ? 
                        "No aplica" : productos.get(i).getCantidad();
                row[6] = productos.get(i).getPrecioXvara();
                row[7] = productos.get(i).getDescripcion();
                model.addRow(row);
            }
        }
    }

    /**
     * Limpia los campos de texto del panel, según el nombre del botón que se
     * presiona.
     *
     * @param panel presionado
     */
    public void limpiarTexto(String panel) {

        if (panel.equals("Crear")) {
            cmbCrearTipoProducto.setSelectedIndex(0);
            cambiarPanelMedidas(TipoProd.TROZA);
            txtCrearMedTroza.setText("");
            cmbCrearTipoMadera.setSelectedIndex(0);
            txtCrearCodigoProducto.setText("");
            txtCrearCantidad.setText("");
            txtCrearPrecioVara.setText("");
            txtCrearDescripcionProducto.setText("");
        } else if (panel.equals("Actualizar")) {
//            txt_actuali_nombreUsuario.setText("");
//            txt_actuali_correo.setText("");
//            pw_actuali_lastpass.setText("");
//            pw_actuali_newPass.setText("");
//            pw_actuali_confNewPass.setText("");
        }
    }
    /**
     * Crea un nuevo producto.
     */
    private void crearProducto(String codProd, String nombre, 
            String codTipoMadera, String medida, String codTipoProducto, 
            String cantidad, String precio, String descripcion) {
        
        //Campos no están vacíos
        if (!codProd.isEmpty() && !nombre.isEmpty() && !medida.isEmpty() && 
                !codTipoMadera.isEmpty() && !codTipoProducto.isEmpty() && 
                !cantidad.isEmpty() && !precio.isEmpty()) {
                        
            //Verificar precio
            if (verificacion.validaPrecio(precio)) {
                //Verificar cantidades
                if (verificacion.validaCantidadUnidades(cantidad) && 
                        verificacion.validaCantidadUnidades(codProd) && 
                        verificacion.validaCantidadUnidades(codTipoMadera) && 
                        verificacion.validaCantidadUnidades(codTipoProducto)) {
                    double preci = Double.valueOf(precio);
                    int cant = Integer.valueOf(cantidad);
                    int cProd = Integer.valueOf(codProd);
                    int cTmadera = Integer.valueOf(codTipoMadera);
                    int cTproducto = Integer.valueOf(codTipoProducto);
                    
                    System.out.println("AGREGANDO PRODUCTO, PLEASE WAIT... "+ preci);
                    boolean crear = controlador.crearProducto(cProd, nombre, 
                            cTmadera, medida, cTproducto, cant, preci, descripcion);
                    if (crear) {
                        cargarTablas();
                        cargarCombos();
                        
                        msg.mostrarMensaje(JOptionPane.INFORMATION_MESSAGE,
                                TipoMensaje.PRODUCT_INSERTION_SUCCESS);
                    } else {
                        msg.mostrarMensaje(JOptionPane.ERROR_MESSAGE,
                                TipoMensaje.PRODUCT_INSERTION_FAILURE);
                        limpiarTexto("Crear");
                    }
                } else {
                    msg.mostrarMensaje(JOptionPane.WARNING_MESSAGE,
                            TipoMensaje.UNITQUANTITY_SYNTAX_FAILURE);
                    txtCrearCantidad.requestFocus();
                }
            } else {
                msg.mostrarMensaje(JOptionPane.INFORMATION_MESSAGE,
                        TipoMensaje.PRICE_SYNTAX_FAILURE);
                txtCrearPrecioVara.requestFocus();
                txtCrearPrecioVara.selectAll();
            }
        } else {
            msg.mostrarMensaje(JOptionPane.WARNING_MESSAGE,
                    TipoMensaje.EMPTY_TEXT_FIELD);
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

        pnlCrearMedidasAcerrada = new javax.swing.JPanel();
        lblCrearMedDE = new javax.swing.JLabel();
        txtCrearMedVaras = new javax.swing.JTextField();
        lblCrearMedVaras = new javax.swing.JLabel();
        txtCrearMedAncho = new javax.swing.JTextField();
        lblCrearAncho = new javax.swing.JLabel();
        lblCrearMedX = new javax.swing.JLabel();
        txtCrearMedGrueso = new javax.swing.JTextField();
        lblCrearMedGrueso = new javax.swing.JLabel();
        pnlCrearMedidasTroza = new javax.swing.JPanel();
        txtCrearMedTroza = new javax.swing.JTextField();
        lblCrearMedTroza = new javax.swing.JLabel();
        pnlCrearMedidasTerminada = new javax.swing.JPanel();
        txtCrearMedTermi = new javax.swing.JTextField();
        lblCrearMedTermiLargo = new javax.swing.JLabel();
        pnl_modInventario = new javax.swing.JPanel();
        tbpnl_modInventario = new javax.swing.JTabbedPane();
        pnl_listado = new javax.swing.JPanel();
        jFormattedTextField1 = new javax.swing.JFormattedTextField();
        lblListadoInventario = new javax.swing.JLabel();
        txtListadoInventario = new javax.swing.JTextField();
        scpnlTblListadoInventario = new javax.swing.JScrollPane();
        tbListadoInventario = new javax.swing.JTable();
        pnl_agregar = new javax.swing.JPanel();
        lblCrearCodigoProducto = new javax.swing.JLabel();
        lblCrearDescripcionProducto = new javax.swing.JLabel();
        lblCrearTipoProducto = new javax.swing.JLabel();
        lblCrearCantidad = new javax.swing.JLabel();
        spnlCrearProductos = new javax.swing.JScrollPane();
        tbl_crear = new javax.swing.JTable();
        btnCrearProducto = new javax.swing.JButton();
        cmbCrearTipoProducto = new javax.swing.JComboBox<>();
        txtCrearCantidad = new javax.swing.JTextField();
        scpnlCrearDescripcion = new javax.swing.JScrollPane();
        txtCrearDescripcionProducto = new javax.swing.JTextArea();
        cmbCrearTipoMadera = new javax.swing.JComboBox<>();
        lblCrearTipoMadera = new javax.swing.JLabel();
        lblCrearPrecioVara = new javax.swing.JLabel();
        txtCrearPrecioVara = new javax.swing.JTextField();
        txtCrearCodigoProducto = new javax.swing.JTextField();
        pnl_actualizar = new javax.swing.JPanel();
        lblEditarCodigoProducto = new javax.swing.JLabel();
        lblEditarDescripcionProducto = new javax.swing.JLabel();
        lblEditarTipoProducto = new javax.swing.JLabel();
        lblEditarCantidad = new javax.swing.JLabel();
        spnlEditarProductos = new javax.swing.JScrollPane();
        tbl_editar = new javax.swing.JTable();
        btnEditarProducto = new javax.swing.JButton();
        cmbEditarTipoProducto = new javax.swing.JComboBox<>();
        txtEditarCantidad = new javax.swing.JTextField();
        scpnlEditarDescripcion = new javax.swing.JScrollPane();
        txtEditarDescripcionProducto = new javax.swing.JTextField();
        cmbEditarTipoMadera = new javax.swing.JComboBox<>();
        lblEditarTipoMadera = new javax.swing.JLabel();
        lblEditarPrecioVara = new javax.swing.JLabel();
        txtEditarPrecioVara = new javax.swing.JTextField();
        txtEditarCodigoProducto = new javax.swing.JTextField();
        pnlHabilitar = new javax.swing.JPanel();
        lblDeshabSelectInventario = new javax.swing.JLabel();
        tbDeshab = new javax.swing.JTabbedPane();
        scpnlDeshabProducto = new javax.swing.JScrollPane();
        tblProductosActivos = new javax.swing.JTable();
        scpnlHabilitarProducto = new javax.swing.JScrollPane();
        tblProductosInactivos = new javax.swing.JTable();
        pnlDeshabContainer = new javax.swing.JPanel();
        rbDeshabDeshabProducto = new javax.swing.JRadioButton();
        rbDeshabHabilitarProducto = new javax.swing.JRadioButton();
        btn_deshabilitar = new javax.swing.JButton();

        pnlCrearMedidasAcerrada.setBorder(javax.swing.BorderFactory.createTitledBorder("Medidas:"));

        lblCrearMedDE.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCrearMedDE.setText("de");
        lblCrearMedDE.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        lblCrearMedVaras.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCrearMedVaras.setText("Varas");
        lblCrearMedVaras.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        lblCrearAncho.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCrearAncho.setText("Ancho");
        lblCrearAncho.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        lblCrearMedX.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCrearMedX.setText("x");
        lblCrearMedX.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        lblCrearMedGrueso.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCrearMedGrueso.setText("Grueso");
        lblCrearMedGrueso.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout pnlCrearMedidasAcerradaLayout = new javax.swing.GroupLayout(pnlCrearMedidasAcerrada);
        pnlCrearMedidasAcerrada.setLayout(pnlCrearMedidasAcerradaLayout);
        pnlCrearMedidasAcerradaLayout.setHorizontalGroup(
            pnlCrearMedidasAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCrearMedidasAcerradaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlCrearMedidasAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtCrearMedVaras)
                    .addComponent(lblCrearMedVaras, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblCrearMedDE)
                .addGap(18, 18, 18)
                .addGroup(pnlCrearMedidasAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblCrearAncho)
                    .addComponent(txtCrearMedAncho, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblCrearMedX, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlCrearMedidasAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblCrearMedGrueso, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtCrearMedGrueso, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        pnlCrearMedidasAcerradaLayout.setVerticalGroup(
            pnlCrearMedidasAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCrearMedidasAcerradaLayout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addGroup(pnlCrearMedidasAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCrearMedVaras, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCrearMedAncho, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCrearMedGrueso, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCrearMedX, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCrearMedDE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlCrearMedidasAcerradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCrearMedGrueso)
                    .addComponent(lblCrearAncho, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblCrearMedVaras)))
        );

        pnlCrearMedidasTroza.setBorder(javax.swing.BorderFactory.createTitledBorder("Medidas:"));

        lblCrearMedTroza.setText("Pulgadas:");

        javax.swing.GroupLayout pnlCrearMedidasTrozaLayout = new javax.swing.GroupLayout(pnlCrearMedidasTroza);
        pnlCrearMedidasTroza.setLayout(pnlCrearMedidasTrozaLayout);
        pnlCrearMedidasTrozaLayout.setHorizontalGroup(
            pnlCrearMedidasTrozaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCrearMedidasTrozaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblCrearMedTroza, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(txtCrearMedTroza, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );
        pnlCrearMedidasTrozaLayout.setVerticalGroup(
            pnlCrearMedidasTrozaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCrearMedidasTrozaLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlCrearMedidasTrozaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(lblCrearMedTroza, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtCrearMedTroza, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pnlCrearMedidasTerminada.setBorder(javax.swing.BorderFactory.createTitledBorder("Medidas:"));

        lblCrearMedTermiLargo.setText("Largo:");

        javax.swing.GroupLayout pnlCrearMedidasTerminadaLayout = new javax.swing.GroupLayout(pnlCrearMedidasTerminada);
        pnlCrearMedidasTerminada.setLayout(pnlCrearMedidasTerminadaLayout);
        pnlCrearMedidasTerminadaLayout.setHorizontalGroup(
            pnlCrearMedidasTerminadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCrearMedidasTerminadaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblCrearMedTermiLargo, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(txtCrearMedTermi, javax.swing.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlCrearMedidasTerminadaLayout.setVerticalGroup(
            pnlCrearMedidasTerminadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCrearMedidasTerminadaLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlCrearMedidasTerminadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(lblCrearMedTermiLargo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtCrearMedTermi, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        setClosable(true);
        setIconifiable(true);
        setTitle("Módulo de Inventario");

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
                "Código de Producto", "Nombre", "Tipo de Madera", "Medidas", "Tipo de Producto", "Cantidad", "Precio por vara", "Descripción"
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
                .addComponent(scpnlTblListadoInventario, javax.swing.GroupLayout.DEFAULT_SIZE, 458, Short.MAX_VALUE)
                .addGap(10, 10, 10))
        );

        tbpnl_modInventario.addTab("Listado Inventario", pnl_listado);

        lblCrearCodigoProducto.setText("Código:");

        lblCrearDescripcionProducto.setText("Descripción:");

        lblCrearTipoProducto.setText("Tipo de producto:");

        lblCrearCantidad.setText("Cantidad:");

        tbl_crear.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código de Producto", "Nombre", "Tipo de Madera", "Medidas", "Tipo de Producto", "Cantidad", "Precio por vara", "Descripción"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        spnlCrearProductos.setViewportView(tbl_crear);

        btnCrearProducto.setText("Agregar Producto");
        btnCrearProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCrearProductoActionPerformed(evt);
            }
        });

        cmbCrearTipoProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbCrearTipoProductoActionPerformed(evt);
            }
        });

        txtCrearDescripcionProducto.setColumns(20);
        txtCrearDescripcionProducto.setRows(5);
        scpnlCrearDescripcion.setViewportView(txtCrearDescripcionProducto);

        lblCrearTipoMadera.setText("Tipo de madera:");

        lblCrearPrecioVara.setText("Precio por vara:");

        javax.swing.GroupLayout pnl_agregarLayout = new javax.swing.GroupLayout(pnl_agregar);
        pnl_agregar.setLayout(pnl_agregarLayout);
        pnl_agregarLayout.setHorizontalGroup(
            pnl_agregarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_agregarLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(pnl_agregarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(spnlCrearProductos, javax.swing.GroupLayout.PREFERRED_SIZE, 1146, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnl_agregarLayout.createSequentialGroup()
                        .addGroup(pnl_agregarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCrearTipoProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblCrearCodigoProducto)
                            .addComponent(txtCrearCodigoProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbCrearTipoProducto, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(108, 108, 108)
                        .addGroup(pnl_agregarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnl_agregarLayout.createSequentialGroup()
                                .addGroup(pnl_agregarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnl_agregarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txtCrearCantidad, javax.swing.GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE)
                                        .addComponent(lblCrearTipoMadera, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblCrearCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(cmbCrearTipoMadera, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addComponent(txtCrearPrecioVara, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(pnl_agregarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblCrearDescripcionProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(scpnlCrearDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnCrearProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(pnl_agregarLayout.createSequentialGroup()
                                .addComponent(lblCrearPrecioVara, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        pnl_agregarLayout.setVerticalGroup(
            pnl_agregarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_agregarLayout.createSequentialGroup()
                .addContainerGap(25, Short.MAX_VALUE)
                .addGroup(pnl_agregarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnl_agregarLayout.createSequentialGroup()
                        .addComponent(lblCrearDescripcionProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(scpnlCrearDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnCrearProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18))
                    .addGroup(pnl_agregarLayout.createSequentialGroup()
                        .addGroup(pnl_agregarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnl_agregarLayout.createSequentialGroup()
                                .addComponent(lblCrearTipoMadera, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(cmbCrearTipoMadera, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pnl_agregarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblCrearCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblCrearCodigoProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(6, 6, 6)
                                .addGroup(pnl_agregarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtCrearCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtCrearCodigoProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(pnl_agregarLayout.createSequentialGroup()
                                .addComponent(lblCrearTipoProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(cmbCrearTipoProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblCrearPrecioVara, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtCrearPrecioVara, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)))
                .addComponent(spnlCrearProductos, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        tbpnl_modInventario.addTab("Agregar producto", pnl_agregar);

        lblEditarCodigoProducto.setText("Código:");

        lblEditarDescripcionProducto.setText("Descripción:");

        lblEditarTipoProducto.setText("Tipo de producto:");

        lblEditarCantidad.setText("Cantidad:");

        tbl_editar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código de Producto", "Nombre", "Tipo de Madera", "Medidas", "Tipo de Producto", "Cantidad", "Precio por vara", "Descripción"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        spnlEditarProductos.setViewportView(tbl_editar);

        btnEditarProducto.setText("Editar Producto");
        btnEditarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarProductoActionPerformed(evt);
            }
        });

        cmbEditarTipoProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbEditarTipoProductoActionPerformed(evt);
            }
        });

        scpnlEditarDescripcion.setViewportView(txtEditarDescripcionProducto);

        lblEditarTipoMadera.setText("Tipo de madera:");

        lblEditarPrecioVara.setText("Precio por vara:");

        javax.swing.GroupLayout pnl_actualizarLayout = new javax.swing.GroupLayout(pnl_actualizar);
        pnl_actualizar.setLayout(pnl_actualizarLayout);
        pnl_actualizarLayout.setHorizontalGroup(
            pnl_actualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_actualizarLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(pnl_actualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(spnlEditarProductos, javax.swing.GroupLayout.PREFERRED_SIZE, 1146, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnl_actualizarLayout.createSequentialGroup()
                        .addGroup(pnl_actualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblEditarTipoProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtEditarCodigoProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbEditarTipoProducto, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblEditarCodigoProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(108, 108, 108)
                        .addGroup(pnl_actualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtEditarPrecioVara, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnl_actualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(pnl_actualizarLayout.createSequentialGroup()
                                    .addGroup(pnl_actualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txtEditarCantidad, javax.swing.GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE)
                                        .addComponent(lblEditarTipoMadera, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblEditarCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(cmbEditarTipoMadera, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(pnl_actualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lblEditarDescripcionProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(scpnlEditarDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnEditarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(pnl_actualizarLayout.createSequentialGroup()
                                    .addComponent(lblEditarPrecioVara, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(0, 0, Short.MAX_VALUE))))))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        pnl_actualizarLayout.setVerticalGroup(
            pnl_actualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_actualizarLayout.createSequentialGroup()
                .addContainerGap(27, Short.MAX_VALUE)
                .addGroup(pnl_actualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_actualizarLayout.createSequentialGroup()
                        .addComponent(lblEditarDescripcionProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(scpnlEditarDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnEditarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnl_actualizarLayout.createSequentialGroup()
                        .addGroup(pnl_actualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnl_actualizarLayout.createSequentialGroup()
                                .addComponent(lblEditarTipoMadera, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(cmbEditarTipoMadera, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pnl_actualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblEditarCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblEditarCodigoProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(6, 6, 6)
                                .addGroup(pnl_actualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtEditarCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtEditarCodigoProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(pnl_actualizarLayout.createSequentialGroup()
                                .addComponent(lblEditarTipoProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(cmbEditarTipoProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblEditarPrecioVara, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtEditarPrecioVara, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(spnlEditarProductos, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        tbpnl_modInventario.addTab("Editar producto", pnl_actualizar);

        lblDeshabSelectInventario.setText("Seleccionar Producto:");

        tblProductosActivos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código de Producto", "Nombre", "Tipo de Madera", "Medidas", "Tipo de Producto", "Cantidad", "Precio por vara", "Descripción"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblProductosActivos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblProductosActivosMouseClicked(evt);
            }
        });
        tblProductosActivos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblProductosActivosKeyReleased(evt);
            }
        });
        scpnlDeshabProducto.setViewportView(tblProductosActivos);

        tbDeshab.addTab("Activos", scpnlDeshabProducto);

        tblProductosInactivos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código de Producto", "Nombre", "Tipo de Madera", "Medidas", "Tipo de Producto", "Cantidad", "Precio por vara", "Descripción"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblProductosInactivos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblProductosInactivosMouseClicked(evt);
            }
        });
        tblProductosInactivos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblProductosInactivosKeyReleased(evt);
            }
        });
        scpnlHabilitarProducto.setViewportView(tblProductosInactivos);

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
                .addContainerGap(26, Short.MAX_VALUE))
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
                        .addComponent(tbDeshab, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
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
                .addComponent(tbpnl_modInventario, javax.swing.GroupLayout.PREFERRED_SIZE, 1200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(303, 303, 303))
        );
        pnl_modInventarioLayout.setVerticalGroup(
            pnl_modInventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_modInventarioLayout.createSequentialGroup()
                .addContainerGap(35, Short.MAX_VALUE)
                .addComponent(tbpnl_modInventario, javax.swing.GroupLayout.PREFERRED_SIZE, 580, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnl_modInventario, javax.swing.GroupLayout.PREFERRED_SIZE, 1230, Short.MAX_VALUE)
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

    private void tblProductosInactivosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblProductosInactivosKeyReleased
        //        try {
        //            if (evt.getKeyCode() == 38 || evt.getKeyCode() == 40) {
        //                model = (DefaultTableModel) tblClientesInactivos.getModel();
        //                int selectedRowIndex = tblClientesInactivos.getSelectedRow();
        //                String nombre
        //                = String.valueOf(model.getValueAt(selectedRowIndex, 0).toString());
        //
        //                for (int i = 0; i < usuarios.size(); i++) {
        //                    if (usuarios.get(i).getNombre().equals(nombre)) {
        //                        if (usuarios.get(i).getEstado().equals(Estado.Deshabilitado)) {
        //                            rbDeshabHabilitarCliente.setSelected(true);
        //                        } else {
        //                            rbDeshabDeshabCliente.setSelected(true);
        //                        }
        //                    }
        //                }
        //            }
        //        } catch (Exception ex) {
        //
        //        }
    }//GEN-LAST:event_tblProductosInactivosKeyReleased

    private void tblProductosInactivosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblProductosInactivosMouseClicked
        //        try {
        //            model = (DefaultTableModel) tblClientesInactivos.getModel();
        //            int selectedRowIndex = tblClientesInactivos.getSelectedRow();
        //            String nombre
        //            = String.valueOf(model.getValueAt(selectedRowIndex, 0).toString());
        //
        //            for (int i = 0; i < usuarios.size(); i++) {
        //                if (usuarios.get(i).getNombre().equals(nombre)) {
        //                    //Si el codigo coincide
        //                    if (usuarios.get(i).getEstado().equals(Estado.Deshabilitado)) {
        //                        //Verifica el tipo de estado
        //                        rbDeshabHabilitarCliente.setSelected(true);
        //                    } else {
        //                        rbDeshabDeshabCliente.setSelected(true);
        //                    }
        //                }
        //            }
        //        } catch (Exception ex) {
        //
        //        }
    }//GEN-LAST:event_tblProductosInactivosMouseClicked

    private void tblProductosActivosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblProductosActivosKeyReleased
        //        try {
        //            if (evt.getKeyCode() == 38 || evt.getKeyCode() == 40) {
        //                model = (DefaultTableModel) tblClientesActivos.getModel();
        //                int selectedRowIndex = tblClientesActivos.getSelectedRow();
        //                String nombre
        //                = String.valueOf(model.getValueAt(selectedRowIndex, 0).toString());
        //
        //                for (int i = 0; i < usuarios.size(); i++) {
        //                    if (usuarios.get(i).getNombre().equals(nombre)) {
        //                        //Si el codigo coincide
        //                        if (usuarios.get(i).getEstado().equals(Estado.Activo)) {
        //                            //Verifica el tipo de estado
        //                            rbDeshabDeshabCliente.setSelected(true);
        //                        } else {
        //                            rbDeshabHabilitarCliente.setSelected(true);
        //                        }
        //                    }
        //                }
        //            }
        //        } catch (Exception ex) {
        //
        //        }
    }//GEN-LAST:event_tblProductosActivosKeyReleased

    private void tblProductosActivosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblProductosActivosMouseClicked
        //        try {
        //            model = (DefaultTableModel) tblClientesActivos.getModel();
        //            int selectedRowIndex = tblClientesActivos.getSelectedRow();
        //            String nombre
        //            = String.valueOf(model.getValueAt(selectedRowIndex, 0).toString());
        //
        //            for (int i = 0; i < usuarios.size(); i++) {
        //                if (usuarios.get(i).getNombre().equals(nombre)) {
        //                    //Si el codigo coincide
        //                    if (usuarios.get(i).getEstado().equals(Estado.Activo)) {
        //                        //Verifica el tipo de estado
        //                        rbDeshabDeshabCliente.setSelected(true);
        //                    } else {
        //                        rbDeshabHabilitarCliente.setSelected(true);
        //                    }
        //                }
        //            }
        //        } catch (Exception ex) {
        //
        //        }
    }//GEN-LAST:event_tblProductosActivosMouseClicked

    private void cmbCrearTipoProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbCrearTipoProductoActionPerformed
        if (cmbCrearTipoProducto.getItemCount() > 0) {
            cambiarPanelMedidas(cmbCrearTipoProducto.getItemAt(cmbCrearTipoProducto.getSelectedIndex()).getTipo());
        }
        
    }//GEN-LAST:event_cmbCrearTipoProductoActionPerformed

    private void btnCrearProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearProductoActionPerformed
        
        TipoMadera tMadera = cmbCrearTipoMadera.getItemAt(
                cmbCrearTipoMadera.getSelectedIndex());
        TipoProducto tProducto = cmbCrearTipoProducto.getItemAt(
                cmbCrearTipoProducto.getSelectedIndex());
        
        String medida; TipoProd tipo = tProducto.getTipo();
        switch (tipo) {
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
        }

        String nombre = tMadera + " " + medida;

        crearProducto(txtCrearCodigoProducto.getText(), nombre, 
            tMadera.getCodigo(), medida, tProducto.getCodigo(), 
            txtCrearCantidad.getText(), txtCrearPrecioVara.getText(), 
            txtCrearDescripcionProducto.getText());
        System.out.println(txtCrearCodigoProducto.getText() +"|"+ nombre +"|"+ tMadera.getCodigo() +"|"+ medida+"|"+ tProducto.getCodigo() +"|"+ txtCrearCantidad.getText()+"|"+ txtCrearPrecioVara.getText()+"|"+ txtCrearDescripcionProducto.getText());
    }//GEN-LAST:event_btnCrearProductoActionPerformed

    private void txtListadoInventarioKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtListadoInventarioKeyReleased

    }//GEN-LAST:event_txtListadoInventarioKeyReleased

    private void jFormattedTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFormattedTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jFormattedTextField1ActionPerformed

    private void btnEditarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarProductoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEditarProductoActionPerformed

    private void cmbEditarTipoProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbEditarTipoProductoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbEditarTipoProductoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCrearProducto;
    private javax.swing.JButton btnEditarProducto;
    private javax.swing.JButton btn_deshabilitar;
    private javax.swing.JComboBox<TipoMadera> cmbCrearTipoMadera;
    private javax.swing.JComboBox<TipoProducto> cmbCrearTipoProducto;
    private javax.swing.JComboBox<TipoMadera> cmbEditarTipoMadera;
    private javax.swing.JComboBox<TipoProducto> cmbEditarTipoProducto;
    private javax.swing.JFormattedTextField jFormattedTextField1;
    private javax.swing.JLabel lblCrearAncho;
    private javax.swing.JLabel lblCrearCantidad;
    private javax.swing.JLabel lblCrearCodigoProducto;
    private javax.swing.JLabel lblCrearDescripcionProducto;
    private javax.swing.JLabel lblCrearMedDE;
    private javax.swing.JLabel lblCrearMedGrueso;
    private javax.swing.JLabel lblCrearMedTermiLargo;
    private javax.swing.JLabel lblCrearMedTroza;
    private javax.swing.JLabel lblCrearMedVaras;
    private javax.swing.JLabel lblCrearMedX;
    private javax.swing.JLabel lblCrearPrecioVara;
    private javax.swing.JLabel lblCrearTipoMadera;
    private javax.swing.JLabel lblCrearTipoProducto;
    private javax.swing.JLabel lblDeshabSelectInventario;
    private javax.swing.JLabel lblEditarCantidad;
    private javax.swing.JLabel lblEditarCodigoProducto;
    private javax.swing.JLabel lblEditarDescripcionProducto;
    private javax.swing.JLabel lblEditarPrecioVara;
    private javax.swing.JLabel lblEditarTipoMadera;
    private javax.swing.JLabel lblEditarTipoProducto;
    private javax.swing.JLabel lblListadoInventario;
    private javax.swing.JPanel pnlCrearMedidasAcerrada;
    private javax.swing.JPanel pnlCrearMedidasTerminada;
    private javax.swing.JPanel pnlCrearMedidasTroza;
    private javax.swing.JPanel pnlDeshabContainer;
    private javax.swing.JPanel pnlHabilitar;
    private javax.swing.JPanel pnl_actualizar;
    private javax.swing.JPanel pnl_agregar;
    private javax.swing.JPanel pnl_listado;
    private javax.swing.JPanel pnl_modInventario;
    private javax.swing.JRadioButton rbDeshabDeshabProducto;
    private javax.swing.JRadioButton rbDeshabHabilitarProducto;
    private javax.swing.JScrollPane scpnlCrearDescripcion;
    private javax.swing.JScrollPane scpnlDeshabProducto;
    private javax.swing.JScrollPane scpnlEditarDescripcion;
    private javax.swing.JScrollPane scpnlHabilitarProducto;
    private javax.swing.JScrollPane scpnlTblListadoInventario;
    private javax.swing.JScrollPane spnlCrearProductos;
    private javax.swing.JScrollPane spnlEditarProductos;
    private javax.swing.JTabbedPane tbDeshab;
    private javax.swing.JTable tbListadoInventario;
    private javax.swing.JTable tblProductosActivos;
    private javax.swing.JTable tblProductosInactivos;
    private javax.swing.JTable tbl_crear;
    private javax.swing.JTable tbl_editar;
    private javax.swing.JTabbedPane tbpnl_modInventario;
    private javax.swing.JTextField txtCrearCantidad;
    private javax.swing.JTextField txtCrearCodigoProducto;
    private javax.swing.JTextArea txtCrearDescripcionProducto;
    private javax.swing.JTextField txtCrearMedAncho;
    private javax.swing.JTextField txtCrearMedGrueso;
    private javax.swing.JTextField txtCrearMedTermi;
    private javax.swing.JTextField txtCrearMedTroza;
    private javax.swing.JTextField txtCrearMedVaras;
    private javax.swing.JTextField txtCrearPrecioVara;
    private javax.swing.JTextField txtEditarCantidad;
    private javax.swing.JTextField txtEditarCodigoProducto;
    private javax.swing.JTextField txtEditarDescripcionProducto;
    private javax.swing.JTextField txtEditarPrecioVara;
    private javax.swing.JTextField txtListadoInventario;
    // End of variables declaration//GEN-END:variables

}
