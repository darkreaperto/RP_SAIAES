/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacion;

import controladores.CtrAcceso;
import controladores.CtrCliente;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import logica.negocio.Cliente;
import logica.negocio.Contacto;
import util.Estado;
import util.TipoContacto;
import util.TipoCredito;

/**
 * Inicializa la ventana que contiene la información de los clientes.
 * @author ahoihanabi
 */
public class ItnFrmCliente extends javax.swing.JInternalFrame {
    
    private static ItnFrmCliente instancia = null;
    private static CtrCliente controlador;
    private static CtrAcceso sesion;
    
    private static ArrayList<Cliente> clientes;
    private static ArrayList<JTextField> telefonos;
    private static ArrayList<JTextField> correos;
    private static DefaultTableModel model;
    
    private int masTelefono = 1;
    private int masCorreo = 1;
    /**
     * Instancia un nuevo formulario interno de clientes.
     * @param sesionAcc Usuario en sesión actual 
     * @param clientes Lista con los clientes en la base de datos
     */
    protected ItnFrmCliente(CtrAcceso sesionAcc, ArrayList<Cliente> clientes) {
        initComponents();
        //Inicializar variables
        controlador = CtrCliente.getInstancia();
        
        ItnFrmCliente.clientes = clientes;
        ItnFrmCliente.sesion = sesionAcc;
        correos = new ArrayList<>();
        telefonos = new ArrayList<>();
        //correos.add(txt_crear_correo);
        //telefonos.add(txt_crear_telefono);
        cargarTablas();
        //verificacion = new Regex();
        //msg = new Mensaje();
    }
    
    /**
     * Retorna la única instancia de la clase.
     *
     * @param sesionAcc Usuario en sesión actual.
     * @param clientes Lista de clientes en la base de datos.
     * @return instancia.
     */
    public static ItnFrmCliente getInstancia(CtrAcceso sesionAcc,
            ArrayList<Cliente> clientes) {
        if (instancia == null) {
            instancia = new ItnFrmCliente(sesionAcc, clientes);
        }
        return instancia;
    }
    /**
     * Para todas las tablas en la interfaz, llama el método que carga una tabla 
     * con la información de los clientes.
     */
    public void cargarTablas() {
        //usuarios.clear();
        clientes = controlador.obtenerClientes();
        cargarClientesJTable(tbListadoCliente, true);
        cargarClientesJTable(tbl_crear, true);
        cargarClientesJTable(tbl_editar, true);
        cargarClientesJTable(tblClientesActivos, true);
        cargarClientesJTable(tblClientesInactivos, false);

//        for (int i = 0; i < usuarios.size(); i++) {
//            if (sesion.getUsuario().getNombre()
//                    .equals(usuarios.get(i).getNombre())) {
//                txt_actuali_nombreUsuario.setText(usuarios.get(i).getNombre());
//                txt_actuali_correo.setText(usuarios.get(i).getCorreo());
//            }
//        }
        System.out.println(sesion.getUsuario().getNombre());
    }
    /**
     * Limpia los elementos en la interfaz.
     */
    public void limpiarCampos() {
        masCorreo = 0;
        masTelefono = 0;
    }
      /**
     * Carga/llena una de la interfaz con la información de 
     * los clientes.
     * @param tabla Tabla a llenar
     * @param estado Indica si el cliente está o no inactivo
     */
    public void cargarClientesJTable(JTable tabla, boolean estado) {
        Object[] row = new Object[7];
        model = (DefaultTableModel) tabla.getModel();
        model.setRowCount(0);
        for (int i = 0; i < clientes.size(); i++) {

            if (clientes.get(i).getEstado().equals(Estado.Activo) && estado) {
                
                row[0] = clientes.get(i).getCedula();
                row[1] = clientes.get(i).getApellido1(); 
                row[2] = clientes.get(i).getApellido2();
                row[3] = clientes.get(i).getNombre();
                row[4] = clientes.get(i).isAprobarCredito() ? "✔" : "✘";
                row[5] = "₡ " + clientes.get(i).getLimiteCredito();
                
                ArrayList<Contacto> contactos = clientes.get(i).getContactos();
                
                String texto = "<html><body>";
                for (Contacto c: contactos) {
                    String tipo = c.getTipo().equals(TipoContacto.CORREO) ? "✉" : "✆";
                    texto += tipo + " " + c.getInfo() + "<br>";
                }
                texto += "</body></html>";
                row[6] = texto;
                    
                model.addRow(row);
                
                tabla.setRowHeight(i, contactos.size() > 0 ? 
                        contactos.size()*20 : tabla.getRowHeight(i));
            }
            if (clientes.get(i).getEstado().equals(Estado.Deshabilitado) && !estado) {
                
                row[0] = clientes.get(i).getCedula();
                row[1] = clientes.get(i).getApellido1(); 
                row[2] = clientes.get(i).getApellido2();
                row[3] = clientes.get(i).getNombre();
                row[4] = clientes.get(i).isAprobarCredito() ? "✔" : "✘";
                row[5] = "₡ " + clientes.get(i).getLimiteCredito();
                
                ArrayList<Contacto> contactos = clientes.get(i).getContactos();
                
                String texto = "<html><body>";
                for (Contacto c: contactos) {
                    String tipo = c.getTipo().equals(TipoContacto.CORREO) ? "✉" : "✆";
                    texto += tipo + " " + c.getInfo() + "<br>";
                }
                texto += "</body></html>";
                row[6] = texto;
                    
                model.addRow(row);
                
                tabla.setRowHeight(i, contactos.size() > 0 ? 
                        contactos.size()*20 : tabla.getRowHeight(i));
            }
        }
    }
    
    
    
    public void agregarCliente(String nombre, String apellido1, 
            String apellido2, String cedula, float limiteCred, 
            boolean aprobarCred, ArrayList<ArrayList<Object>> contactos) {
        
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bg_crearCredito = new javax.swing.ButtonGroup();
        pnl_modCliente = new javax.swing.JPanel();
        tb_modCliente = new javax.swing.JTabbedPane();
        pnl_listado = new javax.swing.JPanel();
        lblListadoCliente = new javax.swing.JLabel();
        txtListadoCliente = new javax.swing.JTextField();
        scpnlTblListadoCliente = new javax.swing.JScrollPane();
        tbListadoCliente = new javax.swing.JTable();
        pnl_agregar = new javax.swing.JPanel();
        lbl_crear_cedulaCliente = new javax.swing.JLabel();
        txt_crear_cedulaCliente = new javax.swing.JTextField();
        lbl_crear_nombreCliente = new javax.swing.JLabel();
        txt_crear_nombreCliente = new javax.swing.JTextField();
        lbl_crear_apellidoCliente1 = new javax.swing.JLabel();
        txt_crear_apellidoCliente1 = new javax.swing.JTextField();
        lbl_crear_apellidoCliente2 = new javax.swing.JLabel();
        txt_crear_apellidoCliente2 = new javax.swing.JTextField();
        pnl_crear_creditoCliente = new javax.swing.JPanel();
        rbCrearCreditoLim = new javax.swing.JRadioButton();
        rbCrearCredito = new javax.swing.JRadioButton();
        rbCrearSinCredito = new javax.swing.JRadioButton();
        lbl_crear_limiteCliente = new javax.swing.JLabel();
        txt_crear_limiteCliente = new javax.swing.JTextField();
        pnlCrearContactoCliente = new javax.swing.JPanel();
        tbCrearContactoClientes = new javax.swing.JTabbedPane();
        scpnlClientesCrearTelefono = new javax.swing.JScrollPane();
        pnlCrearTelefono = new javax.swing.JPanel();
        lblCrearTelefono = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList<>();
        txt_crear_nombreCliente4 = new javax.swing.JTextField();
        btnCrearCliente2 = new javax.swing.JButton();
        pnlCrearCorreo1 = new javax.swing.JPanel();
        lbl_crear_correo1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        txt_crear_nombreCliente3 = new javax.swing.JTextField();
        btnCrearCliente1 = new javax.swing.JButton();
        spnl_crear_clientes = new javax.swing.JScrollPane();
        tbl_crear = new javax.swing.JTable();
        btnCrearCliente = new javax.swing.JButton();
        pnl_actualizar = new javax.swing.JPanel();
        spnl_editar_clientes = new javax.swing.JScrollPane();
        tbl_editar = new javax.swing.JTable();
        btnEditarCliente = new javax.swing.JButton();
        tbEditarContactoClientes = new javax.swing.JTabbedPane();
        scpnlClientesEditarCliente = new javax.swing.JScrollPane();
        pnlEditarTelefono = new javax.swing.JPanel();
        txtEditarCedulaCliente = new javax.swing.JTextField();
        lblEditarCedulaCliente = new javax.swing.JLabel();
        txtEditarNombreCliente = new javax.swing.JTextField();
        lblEditarNombreCliente = new javax.swing.JLabel();
        pnlEditarCreditoCliente = new javax.swing.JPanel();
        rbEditarCreditoLim = new javax.swing.JRadioButton();
        rbEditarCredito = new javax.swing.JRadioButton();
        rbEditarSinCredito = new javax.swing.JRadioButton();
        txtEditarPrimerApellido = new javax.swing.JTextField();
        lblEditarApellidoCliente = new javax.swing.JLabel();
        lblEditarSegundoApellido = new javax.swing.JLabel();
        txtEditarSegundoApellido = new javax.swing.JTextField();
        lblEditarLimiteCliente = new javax.swing.JLabel();
        txtEditarLimiteCliente = new javax.swing.JTextField();
        scpnlClientesEditarContacto = new javax.swing.JScrollPane();
        pnlEditarCorreo = new javax.swing.JPanel();
        pnlEditarContactoCliente = new javax.swing.JPanel();
        lblEditarTelefono = new javax.swing.JLabel();
        lblEditarCorreo = new javax.swing.JLabel();
        btnEditarGuardarTel = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        scpnlEditarListaTelef = new javax.swing.JScrollPane();
        lsTelefonos = new javax.swing.JList<>();
        txtEditarTelefono = new javax.swing.JTextField();
        btnEditarCancelTel = new javax.swing.JButton();
        scpnlEditarListaCorreo = new javax.swing.JScrollPane();
        lsCorreos = new javax.swing.JList<>();
        txtEditarCorreoCliente = new javax.swing.JTextField();
        btnEditarGuardarCorreo = new javax.swing.JButton();
        btnEditarCancelCorreo = new javax.swing.JButton();
        pnlHabilitar = new javax.swing.JPanel();
        lblDeshabSelectCliente = new javax.swing.JLabel();
        tbDeshab = new javax.swing.JTabbedPane();
        scpnlClientesDeshab = new javax.swing.JScrollPane();
        tblClientesActivos = new javax.swing.JTable();
        scpnlClientesHabilitar = new javax.swing.JScrollPane();
        tblClientesInactivos = new javax.swing.JTable();
        pnlDeshabContainer = new javax.swing.JPanel();
        rbDeshabDeshabCliente = new javax.swing.JRadioButton();
        rbDeshabHabilitarCliente = new javax.swing.JRadioButton();
        btn_deshabilitar = new javax.swing.JButton();
        pnlLimCredito = new javax.swing.JPanel();

        setClosable(true);
        setIconifiable(true);
        setTitle("Módulo de Clientes");
        setPreferredSize(new java.awt.Dimension(1240, 670));

        lblListadoCliente.setText("Buscar cliente: ");

        txtListadoCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtListadoClienteKeyReleased(evt);
            }
        });

        tbListadoCliente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cédula", "Primer Apellido", "Segundo Apellido", "Nombre", "Crédito", "Límite de crédito", "Contactos"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        scpnlTblListadoCliente.setViewportView(tbListadoCliente);

        javax.swing.GroupLayout pnl_listadoLayout = new javax.swing.GroupLayout(pnl_listado);
        pnl_listado.setLayout(pnl_listadoLayout);
        pnl_listadoLayout.setHorizontalGroup(
            pnl_listadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(pnl_listadoLayout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addGroup(pnl_listadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(scpnlTblListadoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 1165, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnl_listadoLayout.createSequentialGroup()
                        .addComponent(lblListadoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtListadoCliente)))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        pnl_listadoLayout.setVerticalGroup(
            pnl_listadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_listadoLayout.createSequentialGroup()
                .addGroup(pnl_listadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_listadoLayout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(lblListadoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_listadoLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(txtListadoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(scpnlTblListadoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 476, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        tb_modCliente.addTab("Listado Clientes", pnl_listado);

        lbl_crear_cedulaCliente.setText("Cédula:");

        lbl_crear_nombreCliente.setText("Nombre:");

        lbl_crear_apellidoCliente1.setText("Primer Apellido:");

        lbl_crear_apellidoCliente2.setText("Segundo Apellido:");

        pnl_crear_creditoCliente.setBorder(javax.swing.BorderFactory.createTitledBorder("Crédito de Cliente:"));

        bg_crearCredito.add(rbCrearCreditoLim);
        rbCrearCreditoLim.setText("Crédito limitado");

        bg_crearCredito.add(rbCrearCredito);
        rbCrearCredito.setText("Crédito");

        bg_crearCredito.add(rbCrearSinCredito);
        rbCrearSinCredito.setText("Sin crédigo");

        javax.swing.GroupLayout pnl_crear_creditoClienteLayout = new javax.swing.GroupLayout(pnl_crear_creditoCliente);
        pnl_crear_creditoCliente.setLayout(pnl_crear_creditoClienteLayout);
        pnl_crear_creditoClienteLayout.setHorizontalGroup(
            pnl_crear_creditoClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_crear_creditoClienteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_crear_creditoClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_crear_creditoClienteLayout.createSequentialGroup()
                        .addComponent(rbCrearSinCredito)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(pnl_crear_creditoClienteLayout.createSequentialGroup()
                        .addComponent(rbCrearCredito)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
                        .addComponent(rbCrearCreditoLim)))
                .addContainerGap())
        );
        pnl_crear_creditoClienteLayout.setVerticalGroup(
            pnl_crear_creditoClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_crear_creditoClienteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_crear_creditoClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbCrearCredito)
                    .addComponent(rbCrearCreditoLim))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rbCrearSinCredito)
                .addGap(0, 8, Short.MAX_VALUE))
        );

        lbl_crear_limiteCliente.setText("Límite de crédito:");

        txt_crear_limiteCliente.setEnabled(false);

        pnlCrearContactoCliente.setBorder(javax.swing.BorderFactory.createTitledBorder("Contacto:"));
        pnlCrearContactoCliente.setAutoscrolls(true);

        tbCrearContactoClientes.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        tbCrearContactoClientes.setTabPlacement(javax.swing.JTabbedPane.RIGHT);

        scpnlClientesCrearTelefono.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        pnlCrearTelefono.setRequestFocusEnabled(false);

        lblCrearTelefono.setText("Teléfono:");

        jList2.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(jList2);

        btnCrearCliente2.setText("+");
        btnCrearCliente2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCrearCliente2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlCrearTelefonoLayout = new javax.swing.GroupLayout(pnlCrearTelefono);
        pnlCrearTelefono.setLayout(pnlCrearTelefonoLayout);
        pnlCrearTelefonoLayout.setHorizontalGroup(
            pnlCrearTelefonoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCrearTelefonoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlCrearTelefonoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlCrearTelefonoLayout.createSequentialGroup()
                        .addComponent(lblCrearTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 236, Short.MAX_VALUE))
                    .addComponent(jScrollPane2)
                    .addGroup(pnlCrearTelefonoLayout.createSequentialGroup()
                        .addComponent(txt_crear_nombreCliente4, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCrearCliente2, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlCrearTelefonoLayout.setVerticalGroup(
            pnlCrearTelefonoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCrearTelefonoLayout.createSequentialGroup()
                .addComponent(lblCrearTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlCrearTelefonoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txt_crear_nombreCliente4)
                    .addComponent(btnCrearCliente2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        scpnlClientesCrearTelefono.setViewportView(pnlCrearTelefono);

        tbCrearContactoClientes.addTab("", new javax.swing.ImageIcon(getClass().getResource("/recursos/telefono.png")), scpnlClientesCrearTelefono); // NOI18N

        lbl_crear_correo1.setText("Correo Electrónico:");

        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(jList1);

        btnCrearCliente1.setText("+");
        btnCrearCliente1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCrearCliente1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlCrearCorreo1Layout = new javax.swing.GroupLayout(pnlCrearCorreo1);
        pnlCrearCorreo1.setLayout(pnlCrearCorreo1Layout);
        pnlCrearCorreo1Layout.setHorizontalGroup(
            pnlCrearCorreo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCrearCorreo1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlCrearCorreo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(pnlCrearCorreo1Layout.createSequentialGroup()
                        .addComponent(lbl_crear_correo1, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 168, Short.MAX_VALUE))
                    .addGroup(pnlCrearCorreo1Layout.createSequentialGroup()
                        .addComponent(txt_crear_nombreCliente3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCrearCliente1)))
                .addContainerGap())
        );
        pnlCrearCorreo1Layout.setVerticalGroup(
            pnlCrearCorreo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCrearCorreo1Layout.createSequentialGroup()
                .addComponent(lbl_crear_correo1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlCrearCorreo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txt_crear_nombreCliente3, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(btnCrearCliente1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        tbCrearContactoClientes.addTab("", new javax.swing.ImageIcon(getClass().getResource("/recursos/email.png")), pnlCrearCorreo1); // NOI18N

        javax.swing.GroupLayout pnlCrearContactoClienteLayout = new javax.swing.GroupLayout(pnlCrearContactoCliente);
        pnlCrearContactoCliente.setLayout(pnlCrearContactoClienteLayout);
        pnlCrearContactoClienteLayout.setHorizontalGroup(
            pnlCrearContactoClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCrearContactoClienteLayout.createSequentialGroup()
                .addComponent(tbCrearContactoClientes, javax.swing.GroupLayout.DEFAULT_SIZE, 435, Short.MAX_VALUE)
                .addGap(10, 10, 10))
        );
        pnlCrearContactoClienteLayout.setVerticalGroup(
            pnlCrearContactoClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCrearContactoClienteLayout.createSequentialGroup()
                .addComponent(tbCrearContactoClientes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 7, Short.MAX_VALUE))
        );

        tbl_crear.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cédula", "Primer Apellido", "Segundo Apellido", "Nombre", "Crédito", "Límite de Crédito", "Contactos"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        spnl_crear_clientes.setViewportView(tbl_crear);
        if (tbl_crear.getColumnModel().getColumnCount() > 0) {
            tbl_crear.getColumnModel().getColumn(0).setResizable(false);
            tbl_crear.getColumnModel().getColumn(1).setResizable(false);
            tbl_crear.getColumnModel().getColumn(2).setResizable(false);
            tbl_crear.getColumnModel().getColumn(3).setResizable(false);
            tbl_crear.getColumnModel().getColumn(5).setResizable(false);
            tbl_crear.getColumnModel().getColumn(6).setResizable(false);
        }

        btnCrearCliente.setText("Crear Cliente");
        btnCrearCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCrearClienteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnl_agregarLayout = new javax.swing.GroupLayout(pnl_agregar);
        pnl_agregar.setLayout(pnl_agregarLayout);
        pnl_agregarLayout.setHorizontalGroup(
            pnl_agregarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_agregarLayout.createSequentialGroup()
                .addGroup(pnl_agregarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnl_agregarLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCrearCliente))
                    .addGroup(pnl_agregarLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(pnl_agregarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(spnl_crear_clientes)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnl_agregarLayout.createSequentialGroup()
                                .addGroup(pnl_agregarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lbl_crear_nombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbl_crear_cedulaCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_crear_nombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(pnl_crear_creditoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_crear_cedulaCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(50, 50, 50)
                                .addGroup(pnl_agregarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txt_crear_apellidoCliente2, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_crear_limiteCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbl_crear_apellidoCliente1, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbl_crear_apellidoCliente2, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbl_crear_limiteCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_crear_apellidoCliente1, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                                .addComponent(pnlCrearContactoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(24, 24, 24))
        );
        pnl_agregarLayout.setVerticalGroup(
            pnl_agregarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_agregarLayout.createSequentialGroup()
                .addContainerGap(38, Short.MAX_VALUE)
                .addGroup(pnl_agregarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_agregarLayout.createSequentialGroup()
                        .addComponent(lbl_crear_cedulaCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_crear_cedulaCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_crear_nombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_crear_nombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(16, 16, 16)
                        .addComponent(pnl_crear_creditoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(pnlCrearContactoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnl_agregarLayout.createSequentialGroup()
                        .addComponent(lbl_crear_apellidoCliente1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_crear_apellidoCliente1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_crear_apellidoCliente2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(txt_crear_apellidoCliente2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(16, 16, 16)
                        .addComponent(lbl_crear_limiteCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_crear_limiteCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(spnl_crear_clientes, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCrearCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        tb_modCliente.addTab("Agregar cliente", pnl_agregar);

        tbl_editar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cédula", "Primer Apellido", "Segundo Apellido", "Nombre", "Crédito", "Límite de Crédito", "Contactos"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        spnl_editar_clientes.setViewportView(tbl_editar);
        if (tbl_editar.getColumnModel().getColumnCount() > 0) {
            tbl_editar.getColumnModel().getColumn(0).setResizable(false);
            tbl_editar.getColumnModel().getColumn(1).setResizable(false);
            tbl_editar.getColumnModel().getColumn(2).setResizable(false);
            tbl_editar.getColumnModel().getColumn(3).setResizable(false);
            tbl_editar.getColumnModel().getColumn(5).setResizable(false);
            tbl_editar.getColumnModel().getColumn(6).setResizable(false);
        }

        btnEditarCliente.setText("Guardar Cambios");
        btnEditarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarClienteActionPerformed(evt);
            }
        });

        tbEditarContactoClientes.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        tbEditarContactoClientes.setTabPlacement(javax.swing.JTabbedPane.LEFT);

        scpnlClientesEditarCliente.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        pnlEditarTelefono.setRequestFocusEnabled(false);

        lblEditarCedulaCliente.setText("Cédula:");

        lblEditarNombreCliente.setText("Nombre:");

        pnlEditarCreditoCliente.setBorder(javax.swing.BorderFactory.createTitledBorder("Crédito de Cliente:"));

        bg_crearCredito.add(rbEditarCreditoLim);
        rbEditarCreditoLim.setText("Crédito limitado");

        bg_crearCredito.add(rbEditarCredito);
        rbEditarCredito.setText("Crédito");

        bg_crearCredito.add(rbEditarSinCredito);
        rbEditarSinCredito.setText("Sin Crédito");

        javax.swing.GroupLayout pnlEditarCreditoClienteLayout = new javax.swing.GroupLayout(pnlEditarCreditoCliente);
        pnlEditarCreditoCliente.setLayout(pnlEditarCreditoClienteLayout);
        pnlEditarCreditoClienteLayout.setHorizontalGroup(
            pnlEditarCreditoClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEditarCreditoClienteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlEditarCreditoClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlEditarCreditoClienteLayout.createSequentialGroup()
                        .addComponent(rbEditarCredito, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                        .addComponent(rbEditarCreditoLim)
                        .addGap(25, 25, 25))
                    .addGroup(pnlEditarCreditoClienteLayout.createSequentialGroup()
                        .addComponent(rbEditarSinCredito)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        pnlEditarCreditoClienteLayout.setVerticalGroup(
            pnlEditarCreditoClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEditarCreditoClienteLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlEditarCreditoClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbEditarCredito)
                    .addComponent(rbEditarCreditoLim))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rbEditarSinCredito))
        );

        lblEditarApellidoCliente.setText("Primer Apellido:");

        lblEditarSegundoApellido.setText("Segundo Apellido:");

        lblEditarLimiteCliente.setText("Límite de crédito:");

        txtEditarLimiteCliente.setEnabled(false);

        javax.swing.GroupLayout pnlEditarTelefonoLayout = new javax.swing.GroupLayout(pnlEditarTelefono);
        pnlEditarTelefono.setLayout(pnlEditarTelefonoLayout);
        pnlEditarTelefonoLayout.setHorizontalGroup(
            pnlEditarTelefonoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEditarTelefonoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlEditarTelefonoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblEditarCedulaCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEditarCedulaCliente, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                    .addComponent(lblEditarNombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEditarNombreCliente))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 108, Short.MAX_VALUE)
                .addGroup(pnlEditarTelefonoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblEditarApellidoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEditarPrimerApellido)
                    .addComponent(lblEditarSegundoApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEditarSegundoApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(106, 106, 106)
                .addGroup(pnlEditarTelefonoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlEditarCreditoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEditarLimiteCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEditarLimiteCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(159, 159, 159))
        );
        pnlEditarTelefonoLayout.setVerticalGroup(
            pnlEditarTelefonoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEditarTelefonoLayout.createSequentialGroup()
                .addGroup(pnlEditarTelefonoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlEditarTelefonoLayout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(pnlEditarTelefonoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlEditarTelefonoLayout.createSequentialGroup()
                                .addComponent(lblEditarCedulaCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(8, 8, 8)
                                .addComponent(txtEditarCedulaCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlEditarTelefonoLayout.createSequentialGroup()
                                .addComponent(pnlEditarCreditoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblEditarLimiteCliente)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtEditarLimiteCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(pnlEditarTelefonoLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(lblEditarApellidoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtEditarPrimerApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11)
                        .addGroup(pnlEditarTelefonoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblEditarSegundoApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblEditarNombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlEditarTelefonoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtEditarNombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtEditarSegundoApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(48, Short.MAX_VALUE))
        );

        scpnlClientesEditarCliente.setViewportView(pnlEditarTelefono);

        tbEditarContactoClientes.addTab("", new javax.swing.ImageIcon(getClass().getResource("/recursos/cliente_pequeno.png")), scpnlClientesEditarCliente); // NOI18N

        scpnlClientesEditarContacto.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        pnlEditarContactoCliente.setBorder(javax.swing.BorderFactory.createTitledBorder("Contacto:"));
        pnlEditarContactoCliente.setAutoscrolls(true);

        lblEditarTelefono.setText("Teléfono:");

        lblEditarCorreo.setText("Correo Electrónico:");

        btnEditarGuardarTel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/save.png"))); // NOI18N
        btnEditarGuardarTel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnEditarGuardarTel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarGuardarTelActionPerformed(evt);
            }
        });

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 255)));

        lsTelefonos.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        scpnlEditarListaTelef.setViewportView(lsTelefonos);

        btnEditarCancelTel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/cancel.png"))); // NOI18N
        btnEditarCancelTel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnEditarCancelTel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarCancelTelActionPerformed(evt);
            }
        });

        lsCorreos.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        scpnlEditarListaCorreo.setViewportView(lsCorreos);

        btnEditarGuardarCorreo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/save.png"))); // NOI18N
        btnEditarGuardarCorreo.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnEditarGuardarCorreo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarGuardarCorreoActionPerformed(evt);
            }
        });

        btnEditarCancelCorreo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/cancel.png"))); // NOI18N
        btnEditarCancelCorreo.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnEditarCancelCorreo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarCancelCorreoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlEditarContactoClienteLayout = new javax.swing.GroupLayout(pnlEditarContactoCliente);
        pnlEditarContactoCliente.setLayout(pnlEditarContactoClienteLayout);
        pnlEditarContactoClienteLayout.setHorizontalGroup(
            pnlEditarContactoClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEditarContactoClienteLayout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(pnlEditarContactoClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblEditarTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlEditarContactoClienteLayout.createSequentialGroup()
                        .addComponent(txtEditarTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEditarGuardarTel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEditarCancelTel))
                    .addComponent(scpnlEditarListaTelef, javax.swing.GroupLayout.PREFERRED_SIZE, 408, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(79, 79, 79)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addGroup(pnlEditarContactoClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlEditarContactoClienteLayout.createSequentialGroup()
                        .addComponent(txtEditarCorreoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEditarGuardarCorreo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEditarCancelCorreo))
                    .addComponent(scpnlEditarListaCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 408, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEditarCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(102, Short.MAX_VALUE))
        );
        pnlEditarContactoClienteLayout.setVerticalGroup(
            pnlEditarContactoClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEditarContactoClienteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlEditarContactoClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlEditarContactoClienteLayout.createSequentialGroup()
                        .addComponent(lblEditarCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(scpnlEditarListaCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlEditarContactoClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(pnlEditarContactoClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtEditarCorreoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnEditarGuardarCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnEditarCancelCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnlEditarContactoClienteLayout.createSequentialGroup()
                        .addComponent(lblEditarTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(7, 7, 7)
                        .addComponent(scpnlEditarListaTelef, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlEditarContactoClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(pnlEditarContactoClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtEditarTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnEditarGuardarTel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnEditarCancelTel, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(35, Short.MAX_VALUE))
            .addGroup(pnlEditarContactoClienteLayout.createSequentialGroup()
                .addComponent(jSeparator1)
                .addContainerGap())
        );

        javax.swing.GroupLayout pnlEditarCorreoLayout = new javax.swing.GroupLayout(pnlEditarCorreo);
        pnlEditarCorreo.setLayout(pnlEditarCorreoLayout);
        pnlEditarCorreoLayout.setHorizontalGroup(
            pnlEditarCorreoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEditarCorreoLayout.createSequentialGroup()
                .addComponent(pnlEditarContactoCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlEditarCorreoLayout.setVerticalGroup(
            pnlEditarCorreoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEditarCorreoLayout.createSequentialGroup()
                .addComponent(pnlEditarContactoCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        scpnlClientesEditarContacto.setViewportView(pnlEditarCorreo);

        tbEditarContactoClientes.addTab("", new javax.swing.ImageIcon(getClass().getResource("/recursos/clientesEditContact.png")), scpnlClientesEditarContacto); // NOI18N

        javax.swing.GroupLayout pnl_actualizarLayout = new javax.swing.GroupLayout(pnl_actualizar);
        pnl_actualizar.setLayout(pnl_actualizarLayout);
        pnl_actualizarLayout.setHorizontalGroup(
            pnl_actualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_actualizarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_actualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(spnl_editar_clientes)
                    .addGroup(pnl_actualizarLayout.createSequentialGroup()
                        .addGap(954, 954, 954)
                        .addComponent(btnEditarCliente)
                        .addGap(0, 60, Short.MAX_VALUE))
                    .addComponent(tbEditarContactoClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnl_actualizarLayout.setVerticalGroup(
            pnl_actualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_actualizarLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(tbEditarContactoClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(spnl_editar_clientes, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnEditarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        tb_modCliente.addTab("Editar cliente", pnl_actualizar);

        lblDeshabSelectCliente.setText("Seleccionar Cliente:");

        tblClientesActivos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cédula", "Primer Apellido", "Segundo Apellido", "Nombre", "Crédito", "Límite de Crédiito", "Contacto"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblClientesActivos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblClientesActivosMouseClicked(evt);
            }
        });
        tblClientesActivos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblClientesActivosKeyReleased(evt);
            }
        });
        scpnlClientesDeshab.setViewportView(tblClientesActivos);

        tbDeshab.addTab("Activos", scpnlClientesDeshab);

        tblClientesInactivos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cédula ", "Primer Apellido", "Segundo Apellido", "Nombre", "Crédito", "Límite de crédito", "Contacto"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, true, false, true, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblClientesInactivos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblClientesInactivosMouseClicked(evt);
            }
        });
        tblClientesInactivos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblClientesInactivosKeyReleased(evt);
            }
        });
        scpnlClientesHabilitar.setViewportView(tblClientesInactivos);

        tbDeshab.addTab("Inactivos", scpnlClientesHabilitar);

        pnlDeshabContainer.setBorder(javax.swing.BorderFactory.createTitledBorder("Activo:"));

        rbDeshabDeshabCliente.setText("Deshabilitar");

        rbDeshabHabilitarCliente.setText("Habilitar");

        javax.swing.GroupLayout pnlDeshabContainerLayout = new javax.swing.GroupLayout(pnlDeshabContainer);
        pnlDeshabContainer.setLayout(pnlDeshabContainerLayout);
        pnlDeshabContainerLayout.setHorizontalGroup(
            pnlDeshabContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDeshabContainerLayout.createSequentialGroup()
                .addComponent(rbDeshabHabilitarCliente)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                .addComponent(rbDeshabDeshabCliente)
                .addContainerGap())
        );
        pnlDeshabContainerLayout.setVerticalGroup(
            pnlDeshabContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDeshabContainerLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlDeshabContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbDeshabHabilitarCliente)
                    .addComponent(rbDeshabDeshabCliente)))
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
                .addContainerGap()
                .addGroup(pnlHabilitarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlHabilitarLayout.createSequentialGroup()
                        .addComponent(lblDeshabSelectCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 970, Short.MAX_VALUE))
                    .addComponent(tbDeshab, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlHabilitarLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(pnlDeshabContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_deshabilitar, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
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
                        .addComponent(lblDeshabSelectCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tbDeshab, javax.swing.GroupLayout.DEFAULT_SIZE, 416, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(pnlDeshabContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(17, 17, 17))
        );

        tb_modCliente.addTab("Habilitar clientes", pnlHabilitar);

        javax.swing.GroupLayout pnlLimCreditoLayout = new javax.swing.GroupLayout(pnlLimCredito);
        pnlLimCredito.setLayout(pnlLimCreditoLayout);
        pnlLimCreditoLayout.setHorizontalGroup(
            pnlLimCreditoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1194, Short.MAX_VALUE)
        );
        pnlLimCreditoLayout.setVerticalGroup(
            pnlLimCreditoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 569, Short.MAX_VALUE)
        );

        tb_modCliente.addTab("Límite de crédito", pnlLimCredito);

        javax.swing.GroupLayout pnl_modClienteLayout = new javax.swing.GroupLayout(pnl_modCliente);
        pnl_modCliente.setLayout(pnl_modClienteLayout);
        pnl_modClienteLayout.setHorizontalGroup(
            pnl_modClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_modClienteLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(tb_modCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 1199, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnl_modClienteLayout.setVerticalGroup(
            pnl_modClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_modClienteLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(tb_modCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 596, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnl_modCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnl_modCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCrearClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearClienteActionPerformed
        ArrayList<ArrayList<Object>> contactos;
        
        /*agregarCliente(txtEditarNombreCliente, txtEditarPrimerApellido, 
                txtEditarSegundoApellido, txtEditarCedulaCliente, 
                txtEditarLimiteCliente, true, );*/
        limpiarCampos();
        
    }//GEN-LAST:event_btnCrearClienteActionPerformed

    private void btnEditarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEditarClienteActionPerformed

    private void tblClientesActivosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblClientesActivosMouseClicked
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
    }//GEN-LAST:event_tblClientesActivosMouseClicked

    private void tblClientesActivosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblClientesActivosKeyReleased
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
    }//GEN-LAST:event_tblClientesActivosKeyReleased

    private void tblClientesInactivosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblClientesInactivosMouseClicked
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
    }//GEN-LAST:event_tblClientesInactivosMouseClicked

    private void tblClientesInactivosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblClientesInactivosKeyReleased
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
    }//GEN-LAST:event_tblClientesInactivosKeyReleased

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

    private void btnEditarGuardarTelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarGuardarTelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEditarGuardarTelActionPerformed

    private void txtListadoClienteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtListadoClienteKeyReleased
        clientes = controlador.consultarClientes(txtListadoCliente.getText().trim());
        cargarClientesJTable(tbListadoCliente, true);
    }//GEN-LAST:event_txtListadoClienteKeyReleased

    private void btnEditarCancelTelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarCancelTelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEditarCancelTelActionPerformed

    private void btnEditarGuardarCorreoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarGuardarCorreoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEditarGuardarCorreoActionPerformed

    private void btnEditarCancelCorreoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarCancelCorreoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEditarCancelCorreoActionPerformed

    private void btnCrearCliente1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearCliente1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCrearCliente1ActionPerformed

    private void btnCrearCliente2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearCliente2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCrearCliente2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bg_crearCredito;
    private javax.swing.JButton btnCrearCliente;
    private javax.swing.JButton btnCrearCliente1;
    private javax.swing.JButton btnCrearCliente2;
    private javax.swing.JButton btnEditarCancelCorreo;
    private javax.swing.JButton btnEditarCancelTel;
    private javax.swing.JButton btnEditarCliente;
    private javax.swing.JButton btnEditarGuardarCorreo;
    private javax.swing.JButton btnEditarGuardarTel;
    private javax.swing.JButton btn_deshabilitar;
    private javax.swing.JList<String> jList1;
    private javax.swing.JList<String> jList2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblCrearTelefono;
    private javax.swing.JLabel lblDeshabSelectCliente;
    private javax.swing.JLabel lblEditarApellidoCliente;
    private javax.swing.JLabel lblEditarCedulaCliente;
    private javax.swing.JLabel lblEditarCorreo;
    private javax.swing.JLabel lblEditarLimiteCliente;
    private javax.swing.JLabel lblEditarNombreCliente;
    private javax.swing.JLabel lblEditarSegundoApellido;
    private javax.swing.JLabel lblEditarTelefono;
    private javax.swing.JLabel lblListadoCliente;
    private javax.swing.JLabel lbl_crear_apellidoCliente1;
    private javax.swing.JLabel lbl_crear_apellidoCliente2;
    private javax.swing.JLabel lbl_crear_cedulaCliente;
    private javax.swing.JLabel lbl_crear_correo1;
    private javax.swing.JLabel lbl_crear_limiteCliente;
    private javax.swing.JLabel lbl_crear_nombreCliente;
    private javax.swing.JList<String> lsCorreos;
    private javax.swing.JList<String> lsTelefonos;
    private javax.swing.JPanel pnlCrearContactoCliente;
    private javax.swing.JPanel pnlCrearCorreo1;
    private javax.swing.JPanel pnlCrearTelefono;
    private javax.swing.JPanel pnlDeshabContainer;
    private javax.swing.JPanel pnlEditarContactoCliente;
    private javax.swing.JPanel pnlEditarCorreo;
    private javax.swing.JPanel pnlEditarCreditoCliente;
    private javax.swing.JPanel pnlEditarTelefono;
    private javax.swing.JPanel pnlHabilitar;
    private javax.swing.JPanel pnlLimCredito;
    private javax.swing.JPanel pnl_actualizar;
    private javax.swing.JPanel pnl_agregar;
    private javax.swing.JPanel pnl_crear_creditoCliente;
    private javax.swing.JPanel pnl_listado;
    private javax.swing.JPanel pnl_modCliente;
    private javax.swing.JRadioButton rbCrearCredito;
    private javax.swing.JRadioButton rbCrearCreditoLim;
    private javax.swing.JRadioButton rbCrearSinCredito;
    private javax.swing.JRadioButton rbDeshabDeshabCliente;
    private javax.swing.JRadioButton rbDeshabHabilitarCliente;
    private javax.swing.JRadioButton rbEditarCredito;
    private javax.swing.JRadioButton rbEditarCreditoLim;
    private javax.swing.JRadioButton rbEditarSinCredito;
    private javax.swing.JScrollPane scpnlClientesCrearTelefono;
    private javax.swing.JScrollPane scpnlClientesDeshab;
    private javax.swing.JScrollPane scpnlClientesEditarCliente;
    private javax.swing.JScrollPane scpnlClientesEditarContacto;
    private javax.swing.JScrollPane scpnlClientesHabilitar;
    private javax.swing.JScrollPane scpnlEditarListaCorreo;
    private javax.swing.JScrollPane scpnlEditarListaTelef;
    private javax.swing.JScrollPane scpnlTblListadoCliente;
    private javax.swing.JScrollPane spnl_crear_clientes;
    private javax.swing.JScrollPane spnl_editar_clientes;
    private javax.swing.JTabbedPane tbCrearContactoClientes;
    private javax.swing.JTabbedPane tbDeshab;
    private javax.swing.JTabbedPane tbEditarContactoClientes;
    private javax.swing.JTable tbListadoCliente;
    private javax.swing.JTabbedPane tb_modCliente;
    private javax.swing.JTable tblClientesActivos;
    private javax.swing.JTable tblClientesInactivos;
    private javax.swing.JTable tbl_crear;
    private javax.swing.JTable tbl_editar;
    private javax.swing.JTextField txtEditarCedulaCliente;
    private javax.swing.JTextField txtEditarCorreoCliente;
    private javax.swing.JTextField txtEditarLimiteCliente;
    private javax.swing.JTextField txtEditarNombreCliente;
    private javax.swing.JTextField txtEditarPrimerApellido;
    private javax.swing.JTextField txtEditarSegundoApellido;
    private javax.swing.JTextField txtEditarTelefono;
    private javax.swing.JTextField txtListadoCliente;
    private javax.swing.JTextField txt_crear_apellidoCliente1;
    private javax.swing.JTextField txt_crear_apellidoCliente2;
    private javax.swing.JTextField txt_crear_cedulaCliente;
    private javax.swing.JTextField txt_crear_limiteCliente;
    private javax.swing.JTextField txt_crear_nombreCliente;
    private javax.swing.JTextField txt_crear_nombreCliente3;
    private javax.swing.JTextField txt_crear_nombreCliente4;
    // End of variables declaration//GEN-END:variables
}
