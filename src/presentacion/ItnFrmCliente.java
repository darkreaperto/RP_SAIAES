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
     * Instancia un nuevo formulario interno de usuario.
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
        correos.add(txt_crear_correo);
        telefonos.add(txt_crear_telefono);
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
     * Crea nuevo campo de texto para ingresar otro correo electrónico al cliente
     * @param cantidad Cantidad de campos de texto creados
     */
    public void nuevoCorreo(int cantidad) {
        int pos;
        JTextField textoCorreo = new JTextField();
        pnlCrearCorreo.add(textoCorreo); //añadir al panel

        //Posición del último campo de texto 
        pos = correos.get(correos.size()-1).getY() + 
                correos.get(correos.size()-1).getHeight() + 5;
        textoCorreo.setBounds(14, pos, 209, 30);
        
        //Mover el botón de agregar nuevo
        btnNuevoCorreo.setBounds(btnNuevoCorreo.getX(), pos, 
                btnNuevoCorreo.getWidth(), btnNuevoCorreo.getHeight());
        pnlCrearCorreo.add(btnNuevoCorreo);
        
        //Cambiar tamaño del panel con campos de texto
        pnlCrearCorreo.setBounds(pnlCrearCorreo.getX(), pnlCrearCorreo.getY(), 
                pnlCrearCorreo.getWidth(), 
                textoCorreo.getY() + textoCorreo.getHeight()+5);
        pnlCrearCorreo.setPreferredSize(new Dimension (
                pnlCrearCorreo.getWidth(), 
                pnlCrearCorreo.getHeight()));
        
        correos.add(textoCorreo); //añadir a la lista
        //pnlCrearCorreo.repaint();
        
        //mover scroll al final
        pnlCrearCorreo.scrollRectToVisible(new Rectangle(0, pnlCrearCorreo.getHeight()-1, 1, 1));
        
    }
    /**
     * Crea nuevo campo de texto para ingresar otro teléfono al cliente
     * @param cantidad Cantidad de campos de texto creados
     */
    public void nuevoTelefono(int cantidad) {
        int pos;
        JTextField textoTelefono = new JTextField();
        pnlCrearTelefono.add(textoTelefono);

        pos = telefonos.get(telefonos.size()-1).getY() + 
            telefonos.get(telefonos.size()-1).getHeight() + 5;
        textoTelefono.setBounds(14, pos, 209, 30);//.setBounds(5, posCorreo, 220, 30);
        
        //Mover el botón de agregar nuevo
        btnNuevoTelefono.setBounds(btnNuevoTelefono.getX(), pos, 
                btnNuevoTelefono.getWidth(), btnNuevoTelefono.getHeight());
        pnlCrearTelefono.add(btnNuevoTelefono);
        
        //Cambiar tamaño del panel con campos de texto
        pnlCrearTelefono.setBounds(pnlCrearTelefono.getX(), pnlCrearTelefono.getY(), 
                pnlCrearTelefono.getWidth(), 
                textoTelefono.getY() + textoTelefono.getHeight()+5);
        pnlCrearTelefono.setPreferredSize(new Dimension (
                pnlCrearTelefono.getWidth(), 
                pnlCrearTelefono.getHeight()));
        
        telefonos.add(textoTelefono); //añadir a la lista
        
        //mover scroll al final
        pnlCrearTelefono.scrollRectToVisible(new Rectangle(0, pnlCrearTelefono.getHeight()-1, 1, 1));
        
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
        txt_crear_nombreCliente1 = new javax.swing.JTextField();
        lbl_crear_apellidoCliente2 = new javax.swing.JLabel();
        txt_crear_nombreCliente2 = new javax.swing.JTextField();
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
        txt_crear_telefono = new javax.swing.JTextField();
        lblCrearTelefono = new javax.swing.JLabel();
        btnNuevoTelefono = new javax.swing.JButton();
        scpnlClientesCrearCorreo = new javax.swing.JScrollPane();
        pnlCrearCorreo = new javax.swing.JPanel();
        txt_crear_correo = new javax.swing.JTextField();
        btnNuevoCorreo = new javax.swing.JButton();
        lbl_crear_correo = new javax.swing.JLabel();
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
        lblEditarCorreo = new javax.swing.JLabel();
        lblEditarTelefono = new javax.swing.JLabel();
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
        lblDeshabSelectUsuario = new javax.swing.JLabel();
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

        setPreferredSize(new java.awt.Dimension(1240, 693));

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
            pnl_listadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_listadoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblListadoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(txtListadoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 976, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(56, Short.MAX_VALUE))
            .addGroup(pnl_listadoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scpnlTblListadoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 1100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnl_listadoLayout.setVerticalGroup(
            pnl_listadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_listadoLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(pnl_listadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtListadoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblListadoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(scpnlTblListadoCliente, javax.swing.GroupLayout.DEFAULT_SIZE, 541, Short.MAX_VALUE)
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

        pnlCrearTelefono.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 153, 255)));
        pnlCrearTelefono.setRequestFocusEnabled(false);

        lblCrearTelefono.setText("Teléfono:");

        btnNuevoTelefono.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        btnNuevoTelefono.setText("+");
        btnNuevoTelefono.setPreferredSize(new java.awt.Dimension(49, 30));
        btnNuevoTelefono.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoTelefonoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlCrearTelefonoLayout = new javax.swing.GroupLayout(pnlCrearTelefono);
        pnlCrearTelefono.setLayout(pnlCrearTelefonoLayout);
        pnlCrearTelefonoLayout.setHorizontalGroup(
            pnlCrearTelefonoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCrearTelefonoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlCrearTelefonoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblCrearTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlCrearTelefonoLayout.createSequentialGroup()
                        .addComponent(txt_crear_telefono, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnNuevoTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        pnlCrearTelefonoLayout.setVerticalGroup(
            pnlCrearTelefonoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCrearTelefonoLayout.createSequentialGroup()
                .addComponent(lblCrearTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlCrearTelefonoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnNuevoTelefono, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                    .addComponent(txt_crear_telefono))
                .addContainerGap(39, Short.MAX_VALUE))
        );

        scpnlClientesCrearTelefono.setViewportView(pnlCrearTelefono);

        tbCrearContactoClientes.addTab("", new javax.swing.ImageIcon(getClass().getResource("/recursos/telefono.png")), scpnlClientesCrearTelefono); // NOI18N

        scpnlClientesCrearCorreo.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        pnlCrearCorreo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 204, 0)));

        btnNuevoCorreo.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        btnNuevoCorreo.setText("+");
        btnNuevoCorreo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoCorreoActionPerformed(evt);
            }
        });

        lbl_crear_correo.setText("Correo Electrónico:");

        javax.swing.GroupLayout pnlCrearCorreoLayout = new javax.swing.GroupLayout(pnlCrearCorreo);
        pnlCrearCorreo.setLayout(pnlCrearCorreoLayout);
        pnlCrearCorreoLayout.setHorizontalGroup(
            pnlCrearCorreoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCrearCorreoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlCrearCorreoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlCrearCorreoLayout.createSequentialGroup()
                        .addComponent(txt_crear_correo, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnNuevoCorreo))
                    .addComponent(lbl_crear_correo, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        pnlCrearCorreoLayout.setVerticalGroup(
            pnlCrearCorreoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCrearCorreoLayout.createSequentialGroup()
                .addComponent(lbl_crear_correo, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlCrearCorreoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnNuevoCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(txt_crear_correo, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
                .addContainerGap(41, Short.MAX_VALUE))
        );

        scpnlClientesCrearCorreo.setViewportView(pnlCrearCorreo);

        tbCrearContactoClientes.addTab("", new javax.swing.ImageIcon(getClass().getResource("/recursos/email.png")), scpnlClientesCrearCorreo); // NOI18N

        javax.swing.GroupLayout pnlCrearContactoClienteLayout = new javax.swing.GroupLayout(pnlCrearContactoCliente);
        pnlCrearContactoCliente.setLayout(pnlCrearContactoClienteLayout);
        pnlCrearContactoClienteLayout.setHorizontalGroup(
            pnlCrearContactoClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tbCrearContactoClientes, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        pnlCrearContactoClienteLayout.setVerticalGroup(
            pnlCrearContactoClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tbCrearContactoClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addContainerGap()
                .addGroup(pnl_agregarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(spnl_crear_clientes)
                    .addGroup(pnl_agregarLayout.createSequentialGroup()
                        .addGroup(pnl_agregarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lbl_crear_nombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_crear_cedulaCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_crear_nombreCliente)
                            .addComponent(pnl_crear_creditoCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txt_crear_cedulaCliente))
                        .addGap(70, 70, 70)
                        .addGroup(pnl_agregarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_crear_nombreCliente2, javax.swing.GroupLayout.DEFAULT_SIZE, 294, Short.MAX_VALUE)
                            .addComponent(txt_crear_limiteCliente, javax.swing.GroupLayout.DEFAULT_SIZE, 294, Short.MAX_VALUE)
                            .addComponent(lbl_crear_apellidoCliente1, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_crear_apellidoCliente2, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_crear_limiteCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_crear_nombreCliente1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 65, Short.MAX_VALUE)
                        .addGroup(pnl_agregarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(pnlCrearContactoCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnCrearCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(71, 71, 71))
        );
        pnl_agregarLayout.setVerticalGroup(
            pnl_agregarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_agregarLayout.createSequentialGroup()
                .addContainerGap(36, Short.MAX_VALUE)
                .addGroup(pnl_agregarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_agregarLayout.createSequentialGroup()
                        .addComponent(pnlCrearContactoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38)
                        .addComponent(btnCrearCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnl_agregarLayout.createSequentialGroup()
                        .addGroup(pnl_agregarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_crear_cedulaCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_crear_apellidoCliente1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnl_agregarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_crear_cedulaCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_crear_nombreCliente1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnl_agregarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_crear_nombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_crear_apellidoCliente2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(pnl_agregarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnl_agregarLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_crear_nombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_agregarLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(txt_crear_nombreCliente2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(16, 16, 16)
                        .addGroup(pnl_agregarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnl_agregarLayout.createSequentialGroup()
                                .addComponent(lbl_crear_limiteCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_crear_limiteCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(pnl_crear_creditoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addComponent(spnl_crear_clientes, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addGap(80, 80, 80)
                .addGroup(pnlEditarTelefonoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblEditarApellidoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEditarPrimerApellido, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                    .addComponent(lblEditarSegundoApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEditarSegundoApellido))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 61, Short.MAX_VALUE)
                .addGroup(pnlEditarTelefonoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblEditarLimiteCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlEditarCreditoCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtEditarLimiteCliente))
                .addGap(232, 232, 232))
        );
        pnlEditarTelefonoLayout.setVerticalGroup(
            pnlEditarTelefonoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEditarTelefonoLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(pnlEditarTelefonoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlEditarTelefonoLayout.createSequentialGroup()
                        .addComponent(lblEditarCedulaCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8)
                        .addComponent(txtEditarCedulaCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlEditarTelefonoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(pnlEditarTelefonoLayout.createSequentialGroup()
                            .addComponent(pnlEditarCreditoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblEditarLimiteCliente)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtEditarLimiteCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(pnlEditarTelefonoLayout.createSequentialGroup()
                            .addComponent(lblEditarApellidoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtEditarPrimerApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(11, 11, 11)
                            .addGroup(pnlEditarTelefonoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblEditarSegundoApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblEditarNombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(pnlEditarTelefonoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtEditarSegundoApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtEditarNombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(60, Short.MAX_VALUE))
        );

        scpnlClientesEditarCliente.setViewportView(pnlEditarTelefono);

        tbEditarContactoClientes.addTab("", new javax.swing.ImageIcon(getClass().getResource("/recursos/cliente_pequeno.png")), scpnlClientesEditarCliente); // NOI18N

        scpnlClientesEditarContacto.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        pnlEditarContactoCliente.setBorder(javax.swing.BorderFactory.createTitledBorder("Contacto:"));
        pnlEditarContactoCliente.setAutoscrolls(true);

        lblEditarCorreo.setText("Correo Electrónico:");

        lblEditarTelefono.setText("Teléfono:");

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
                .addContainerGap()
                .addGroup(pnlEditarContactoClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblEditarTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlEditarContactoClienteLayout.createSequentialGroup()
                        .addComponent(txtEditarTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEditarGuardarTel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEditarCancelTel))
                    .addComponent(scpnlEditarListaTelef))
                .addGap(88, 88, 88)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlEditarContactoClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlEditarContactoClienteLayout.createSequentialGroup()
                        .addComponent(txtEditarCorreoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEditarGuardarCorreo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEditarCancelCorreo))
                    .addComponent(scpnlEditarListaCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 408, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEditarCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(77, Short.MAX_VALUE))
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
                .addContainerGap(23, Short.MAX_VALUE))
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
                .addContainerGap())
        );

        scpnlClientesEditarContacto.setViewportView(pnlEditarCorreo);

        tbEditarContactoClientes.addTab("", new javax.swing.ImageIcon(getClass().getResource("/recursos/clientesEditContact.png")), scpnlClientesEditarContacto); // NOI18N

        javax.swing.GroupLayout pnl_actualizarLayout = new javax.swing.GroupLayout(pnl_actualizar);
        pnl_actualizar.setLayout(pnl_actualizarLayout);
        pnl_actualizarLayout.setHorizontalGroup(
            pnl_actualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_actualizarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_actualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnEditarCliente)
                    .addGroup(pnl_actualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(spnl_editar_clientes, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(tbEditarContactoClientes, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 1110, Short.MAX_VALUE)))
                .addContainerGap(50, Short.MAX_VALUE))
        );
        pnl_actualizarLayout.setVerticalGroup(
            pnl_actualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_actualizarLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(tbEditarContactoClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(spnl_editar_clientes, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEditarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        tb_modCliente.addTab("Editar cliente", pnl_actualizar);

        lblDeshabSelectUsuario.setText("Seleccionar Cliente:");

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
                    .addComponent(lblDeshabSelectUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlHabilitarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(pnlHabilitarLayout.createSequentialGroup()
                            .addGap(12, 12, 12)
                            .addComponent(pnlDeshabContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn_deshabilitar, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(tbDeshab, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 1110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(50, Short.MAX_VALUE))
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
                        .addComponent(lblDeshabSelectUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tbDeshab, javax.swing.GroupLayout.DEFAULT_SIZE, 483, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(pnlDeshabContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(17, 17, 17))
        );

        tb_modCliente.addTab("Habilitar clientes", pnlHabilitar);

        javax.swing.GroupLayout pnlLimCreditoLayout = new javax.swing.GroupLayout(pnlLimCredito);
        pnlLimCredito.setLayout(pnlLimCreditoLayout);
        pnlLimCreditoLayout.setHorizontalGroup(
            pnlLimCreditoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1172, Short.MAX_VALUE)
        );
        pnlLimCreditoLayout.setVerticalGroup(
            pnlLimCreditoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 636, Short.MAX_VALUE)
        );

        tb_modCliente.addTab("Límite de crédito", pnlLimCredito);

        javax.swing.GroupLayout pnl_modClienteLayout = new javax.swing.GroupLayout(pnl_modCliente);
        pnl_modCliente.setLayout(pnl_modClienteLayout);
        pnl_modClienteLayout.setHorizontalGroup(
            pnl_modClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_modClienteLayout.createSequentialGroup()
                .addContainerGap(46, Short.MAX_VALUE)
                .addComponent(tb_modCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 1177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(67, 67, 67))
        );
        pnl_modClienteLayout.setVerticalGroup(
            pnl_modClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_modClienteLayout.createSequentialGroup()
                .addContainerGap(40, Short.MAX_VALUE)
                .addComponent(tb_modCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnl_modCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnl_modCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
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

    private void btnNuevoCorreoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoCorreoActionPerformed
        nuevoCorreo(masCorreo++);        
    }//GEN-LAST:event_btnNuevoCorreoActionPerformed

    private void btnNuevoTelefonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoTelefonoActionPerformed
        nuevoTelefono(masTelefono++);
    }//GEN-LAST:event_btnNuevoTelefonoActionPerformed

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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bg_crearCredito;
    private javax.swing.JButton btnCrearCliente;
    private javax.swing.JButton btnEditarCancelCorreo;
    private javax.swing.JButton btnEditarCancelTel;
    private javax.swing.JButton btnEditarCliente;
    private javax.swing.JButton btnEditarGuardarCorreo;
    private javax.swing.JButton btnEditarGuardarTel;
    private javax.swing.JButton btnNuevoCorreo;
    private javax.swing.JButton btnNuevoTelefono;
    private javax.swing.JButton btn_deshabilitar;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblCrearTelefono;
    private javax.swing.JLabel lblDeshabSelectUsuario;
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
    private javax.swing.JLabel lbl_crear_correo;
    private javax.swing.JLabel lbl_crear_limiteCliente;
    private javax.swing.JLabel lbl_crear_nombreCliente;
    private javax.swing.JList<String> lsCorreos;
    private javax.swing.JList<String> lsTelefonos;
    private javax.swing.JPanel pnlCrearContactoCliente;
    private javax.swing.JPanel pnlCrearCorreo;
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
    private javax.swing.JScrollPane scpnlClientesCrearCorreo;
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
    private javax.swing.JTextField txt_crear_cedulaCliente;
    private javax.swing.JTextField txt_crear_correo;
    private javax.swing.JTextField txt_crear_limiteCliente;
    private javax.swing.JTextField txt_crear_nombreCliente;
    private javax.swing.JTextField txt_crear_nombreCliente1;
    private javax.swing.JTextField txt_crear_nombreCliente2;
    private javax.swing.JTextField txt_crear_telefono;
    // End of variables declaration//GEN-END:variables
}
