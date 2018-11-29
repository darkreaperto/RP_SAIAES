/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacion;

import controladores.CtrAcceso;
import controladores.CtrCliente;
import controladores.CtrMadera;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JTextField;
import logica.negocio.Cliente;
import logica.negocio.Madera;

/**
 * Inicializa la ventana que contiene la información de los facturación.
 * @author aoihanabi
 */
public class ItnFrmFacturacion extends javax.swing.JInternalFrame {
    
    private static ItnFrmFacturacion instancia = null;
    private static CtrMadera ctrInventario = new CtrMadera();
    private static CtrCliente ctrCliente = new CtrCliente();
    private static ArrayList<Madera> listaProd;
    /**
     * Instancia un formulario interno de facturación
     * @param sesionAcc Usuario en la sesion actual
     */
    protected ItnFrmFacturacion(CtrAcceso sesionAcc) {
        initComponents();
        addDate();
        lblUsuarioFac.setText(sesionAcc.getUsuario().getNombre());
    }
    /**
     * Retorna la única instancia de la clase.
     *
     * @param sesionAcc Usuario en sesión actual.
     * @return instancia.
     */
    public static ItnFrmFacturacion getInstancia(CtrAcceso sesionAcc) {
        
        if (instancia == null) {
            instancia = new ItnFrmFacturacion(sesionAcc);
        }
        return instancia;
    }
    /**
     * Obtener la fecha del sistema y agregarla a un campo de texto
     */
    public final void addDate() {
        lblFecha.setText(LocalDateTime.now().format(DateTimeFormatter.
                ofPattern("dd-MM-yyyy")));
    }
    /**
     * Obtener la lista de productos consultados y mostrarla en la lista 
     * de la interfaz
     * @param c atributo con que se consultan los productos
     */
    public void llenarListaProductos(String c) {
        listaProd =  new ArrayList<>();
        DefaultListModel<Object> mProductos = new DefaultListModel<>();
        listaProd = ctrInventario.consultarProductos(c);
        
        for (Madera m : listaProd) {
            mProductos.addElement(m);             
        }
        lsEscogerProd.setModel(mProductos);        
    }
    
    /**
     * Obtener de la lista clietes el cliente ingresado por cédula en el 
     * campo de texto
     * @param p cedula del cliente para consultar
     */
    public void llenarListaClientes(String p) {
        JList<Object> lsEscogerCli = new JList();

        ArrayList<Cliente> listaCli; //=  new ArrayList<>();
        DefaultListModel<Object> mClientes = new DefaultListModel<>();
        listaCli = ctrCliente.consultarClientes(p);
        System.out.println(listaCli);
        
        for (Cliente c : listaCli) {
            mClientes.addElement(c);             
        }
        System.out.println(listaProd);
        lsEscogerCli.setModel(mClientes);
        String cli = "ESTIMADO CLIENTE";
        
        for (Cliente c: listaCli) {
            System.out.println("CED "+c.getCedula());
            if (c.getCedula().equals(p)) {
                cli = c.toString();
                break;
            }
        }        
        txtClienteFac.setText(cli);
        lblClienteNom.setText(cli);
    }
        
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnl_modFactura = new javax.swing.JPanel();
        pnlEncabezado = new javax.swing.JPanel();
        lblTextUsuario = new javax.swing.JLabel();
        lblUsuarioFac = new javax.swing.JLabel();
        lblTextConsecutivoFac = new javax.swing.JLabel();
        lblConsecutivo = new javax.swing.JLabel();
        lblTextFechaFac = new javax.swing.JLabel();
        lblFecha = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        lblTextClienteFac = new javax.swing.JLabel();
        txtClienteFac = new javax.swing.JTextField();
        lblClienteNom = new javax.swing.JLabel();
        ftClienteFac = new javax.swing.JFormattedTextField();
        lblTextProducto = new javax.swing.JLabel();
        txtProducto = new javax.swing.JTextField();
        scpnlList = new javax.swing.JScrollPane();
        lsEscogerProd = new javax.swing.JList<>();
        scpnlTblLineaPedido = new javax.swing.JScrollPane();
        tblLineaPedido = new javax.swing.JTable();
        pnlTotales = new javax.swing.JPanel();
        lblTextImpuestos = new javax.swing.JLabel();
        lblTextSubtotal = new javax.swing.JLabel();
        lblTextTotal = new javax.swing.JLabel();
        lblImpuesto = new javax.swing.JLabel();
        lblSubtotal = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        btnFacturar = new javax.swing.JButton();
        btnAddProduct = new javax.swing.JButton();
        lblTextCantidad = new javax.swing.JLabel();
        txtCantidad = new javax.swing.JTextField();

        setClosable(true);
        setIconifiable(true);
        setTitle("Módulo de Facturación");
        setPreferredSize(new java.awt.Dimension(1240, 670));

        pnlEncabezado.setBorder(new javax.swing.border.LineBorder(javax.swing.UIManager.getDefaults().getColor("Button.darkShadow"), 1, true));

        lblTextUsuario.setText("Facturado por: ");

        lblUsuarioFac.setToolTipText("Usuario en sesión.");
        lblUsuarioFac.setBorder(javax.swing.BorderFactory.createLineBorder(javax.swing.UIManager.getDefaults().getColor("Button.shadow")));

        lblTextConsecutivoFac.setText("Consecutivo Factura:");

        lblConsecutivo.setToolTipText("Usuario en sesión.");
        lblConsecutivo.setBorder(javax.swing.BorderFactory.createLineBorder(javax.swing.UIManager.getDefaults().getColor("Button.shadow")));

        lblTextFechaFac.setText("Fecha de facturación:");

        lblFecha.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblFecha.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblFecha.setToolTipText("Usuario en sesión.");
        lblFecha.setBorder(javax.swing.BorderFactory.createLineBorder(javax.swing.UIManager.getDefaults().getColor("Button.shadow")));

        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 255)));

        lblTextClienteFac.setText("Cliente:");

        txtClienteFac.setText("Nombre del cliente...");
        txtClienteFac.setSelectionStart(0);
        txtClienteFac.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtClienteFacFocusGained(evt);
            }
        });
        txtClienteFac.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtClienteFacKeyReleased(evt);
            }
        });

        lblClienteNom.setText("CLIENTE GENÉRICO");

        try {
            ftClienteFac.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("#-####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        ftClienteFac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ftClienteFacActionPerformed(evt);
            }
        });
        ftClienteFac.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                ftClienteFacKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout pnlEncabezadoLayout = new javax.swing.GroupLayout(pnlEncabezado);
        pnlEncabezado.setLayout(pnlEncabezadoLayout);
        pnlEncabezadoLayout.setHorizontalGroup(
            pnlEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEncabezadoLayout.createSequentialGroup()
                .addGroup(pnlEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlEncabezadoLayout.createSequentialGroup()
                        .addGroup(pnlEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlEncabezadoLayout.createSequentialGroup()
                                .addGap(37, 37, 37)
                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 1109, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlEncabezadoLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lblTextClienteFac, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblClienteNom, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 33, Short.MAX_VALUE))
                    .addGroup(pnlEncabezadoLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(pnlEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlEncabezadoLayout.createSequentialGroup()
                                .addGroup(pnlEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlEncabezadoLayout.createSequentialGroup()
                                        .addComponent(lblTextUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(48, 48, 48)
                                        .addComponent(lblUsuarioFac, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(pnlEncabezadoLayout.createSequentialGroup()
                                        .addComponent(lblTextConsecutivoFac, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblConsecutivo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGap(424, 424, 424)
                                .addGroup(pnlEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblFecha, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlEncabezadoLayout.createSequentialGroup()
                                        .addComponent(lblTextFechaFac, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(72, 72, 72))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlEncabezadoLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(ftClienteFac, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtClienteFac, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        pnlEncabezadoLayout.setVerticalGroup(
            pnlEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEncabezadoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(lblTextUsuario, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(lblUsuarioFac, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblTextFechaFac, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblTextConsecutivoFac, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(lblConsecutivo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblFecha, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlEncabezadoLayout.createSequentialGroup()
                        .addGroup(pnlEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblTextClienteFac, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblClienteNom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ftClienteFac, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(13, 13, 13))
                    .addGroup(pnlEncabezadoLayout.createSequentialGroup()
                        .addComponent(txtClienteFac)
                        .addContainerGap())))
        );

        lblTextProducto.setText("Producto:");

        txtProducto.setText("Buscar producto...");
        txtProducto.setSelectionStart(0);
        txtProducto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtProductoFocusGained(evt);
            }
        });
        txtProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtProductoKeyReleased(evt);
            }
        });

        scpnlList.setViewportView(lsEscogerProd);

        tblLineaPedido.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Descripción", "Medida", "Cantidad", "Prec. Unidad", "Subtotal"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblLineaPedido.getTableHeader().setReorderingAllowed(false);
        scpnlTblLineaPedido.setViewportView(tblLineaPedido);

        pnlTotales.setBorder(new javax.swing.border.LineBorder(javax.swing.UIManager.getDefaults().getColor("Button.darkShadow"), 1, true));

        lblTextImpuestos.setText("Impuestos:");

        lblTextSubtotal.setText("Subtotal:");

        lblTextTotal.setText("Total:");

        lblImpuesto.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblImpuesto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblImpuesto.setText("10%");
        lblImpuesto.setToolTipText("Usuario en sesión.");
        lblImpuesto.setBorder(javax.swing.BorderFactory.createLineBorder(javax.swing.UIManager.getDefaults().getColor("Button.shadow")));

        lblSubtotal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSubtotal.setToolTipText("Usuario en sesión.");
        lblSubtotal.setBorder(javax.swing.BorderFactory.createLineBorder(javax.swing.UIManager.getDefaults().getColor("Button.shadow")));

        lblTotal.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblTotal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTotal.setToolTipText("Usuario en sesión.");
        lblTotal.setBorder(javax.swing.BorderFactory.createLineBorder(javax.swing.UIManager.getDefaults().getColor("Button.shadow")));

        btnFacturar.setText("Emitir Factura");

        javax.swing.GroupLayout pnlTotalesLayout = new javax.swing.GroupLayout(pnlTotales);
        pnlTotales.setLayout(pnlTotalesLayout);
        pnlTotalesLayout.setHorizontalGroup(
            pnlTotalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlTotalesLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlTotalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTextImpuestos)
                    .addComponent(lblTextSubtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlTotalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblSubtotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblImpuesto, javax.swing.GroupLayout.DEFAULT_SIZE, 72, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlTotalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlTotalesLayout.createSequentialGroup()
                        .addComponent(lblTextTotal)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnFacturar, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(97, 97, 97))
        );
        pnlTotalesLayout.setVerticalGroup(
            pnlTotalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTotalesLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(pnlTotalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnlTotalesLayout.createSequentialGroup()
                        .addComponent(lblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnFacturar, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE))
                    .addGroup(pnlTotalesLayout.createSequentialGroup()
                        .addGroup(pnlTotalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlTotalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(lblTextImpuestos, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                                .addComponent(lblImpuesto, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(lblTextTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlTotalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblTextSubtotal, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                            .addComponent(lblSubtotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        btnAddProduct.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/add.png"))); // NOI18N

        lblTextCantidad.setText("Cantidad:");

        txtCantidad.setText("Cantidad en unidades...");
        txtCantidad.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCantidadFocusGained(evt);
            }
        });

        javax.swing.GroupLayout pnl_modFacturaLayout = new javax.swing.GroupLayout(pnl_modFactura);
        pnl_modFactura.setLayout(pnl_modFacturaLayout);
        pnl_modFacturaLayout.setHorizontalGroup(
            pnl_modFacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(pnl_modFacturaLayout.createSequentialGroup()
                .addGroup(pnl_modFacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_modFacturaLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(pnlEncabezado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnl_modFacturaLayout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addGroup(pnl_modFacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(scpnlTblLineaPedido)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_modFacturaLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(pnlTotales, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
            .addGroup(pnl_modFacturaLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(pnl_modFacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_modFacturaLayout.createSequentialGroup()
                        .addGroup(pnl_modFacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTextProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 715, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pnl_modFacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblTextCantidad)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_modFacturaLayout.createSequentialGroup()
                        .addComponent(scpnlList, javax.swing.GroupLayout.PREFERRED_SIZE, 959, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAddProduct)))
                .addGap(95, 95, 95))
        );
        pnl_modFacturaLayout.setVerticalGroup(
            pnl_modFacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_modFacturaLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(pnlEncabezado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl_modFacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTextCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTextProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl_modFacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl_modFacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnAddProduct, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(scpnlList, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(scpnlTblLineaPedido, javax.swing.GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlTotales, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnl_modFactura, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnl_modFactura, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtProductoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtProductoKeyReleased
        llenarListaProductos(txtProducto.getText().trim());
    }//GEN-LAST:event_txtProductoKeyReleased

    private void txtProductoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtProductoFocusGained
        if (!(evt.getSource() instanceof JTextField)) return;
        txtProducto = (JTextField)evt.getSource();
        txtProducto.selectAll();
    }//GEN-LAST:event_txtProductoFocusGained

    private void txtClienteFacFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtClienteFacFocusGained
        if (!(evt.getSource() instanceof JTextField)) return;
        txtClienteFac = (JTextField)evt.getSource();
        txtClienteFac.selectAll();
    }//GEN-LAST:event_txtClienteFacFocusGained

    private void txtCantidadFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCantidadFocusGained
        if (!(evt.getSource() instanceof JTextField)) return;
        txtCantidad = (JTextField)evt.getSource();
        txtCantidad.selectAll();
    }//GEN-LAST:event_txtCantidadFocusGained

    private void txtClienteFacKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtClienteFacKeyReleased
        System.out.println("EVT "+evt.getKeyCode());
        if (evt.getKeyCode() == 10) { //enter
            System.out.println("EVT");
            llenarListaClientes(txtClienteFac.getText().trim());
        }
    }//GEN-LAST:event_txtClienteFacKeyReleased

    private void ftClienteFacKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ftClienteFacKeyReleased
        System.out.println("EVT "+evt.getKeyCode());
        if (evt.getKeyCode() == 10) { //enter
            System.out.println("EVT");
            llenarListaClientes(ftClienteFac.getText().replace("-", "").trim());
        }
    }//GEN-LAST:event_ftClienteFacKeyReleased

    private void ftClienteFacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ftClienteFacActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ftClienteFacActionPerformed
   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddProduct;
    private javax.swing.JButton btnFacturar;
    private javax.swing.JFormattedTextField ftClienteFac;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblClienteNom;
    private javax.swing.JLabel lblConsecutivo;
    private javax.swing.JLabel lblFecha;
    private javax.swing.JLabel lblImpuesto;
    private javax.swing.JLabel lblSubtotal;
    private javax.swing.JLabel lblTextCantidad;
    private javax.swing.JLabel lblTextClienteFac;
    private javax.swing.JLabel lblTextConsecutivoFac;
    private javax.swing.JLabel lblTextFechaFac;
    private javax.swing.JLabel lblTextImpuestos;
    private javax.swing.JLabel lblTextProducto;
    private javax.swing.JLabel lblTextSubtotal;
    private javax.swing.JLabel lblTextTotal;
    private javax.swing.JLabel lblTextUsuario;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JLabel lblUsuarioFac;
    private javax.swing.JList<Object> lsEscogerProd;
    private javax.swing.JPanel pnlEncabezado;
    private javax.swing.JPanel pnlTotales;
    private javax.swing.JPanel pnl_modFactura;
    private javax.swing.JScrollPane scpnlList;
    private javax.swing.JScrollPane scpnlTblLineaPedido;
    private javax.swing.JTable tblLineaPedido;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtClienteFac;
    private javax.swing.JTextField txtProducto;
    // End of variables declaration//GEN-END:variables
}
