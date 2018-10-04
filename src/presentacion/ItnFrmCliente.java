/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacion;

import controladores.CtrAcceso;
import controladores.CtrCliente;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import logica.negocio.Cliente;
import logica.negocio.Contacto;
import util.Estado;
import util.TipoContacto;

/**
 * Inicializa la ventana que contiene la información de los clientes.
 * @author ahoihanabi
 */
public class ItnFrmCliente extends javax.swing.JInternalFrame {
    
    private static ItnFrmCliente instancia = null;
    private static CtrCliente controlador;
    
    private static ArrayList<Cliente> clientes;
    private static CtrAcceso sesion;
    private static ArrayList<JTextField> telefonos;
    private static ArrayList<JTextField> correos;
    private DefaultTableModel model;

    private int masTelefono = 0;
    private int masCorreo = 0;
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

        //Cambiar tamaño del panel con campos de texto
        pnlCrearCorreo.setSize(pnlCrearCorreo.getWidth(), 
                pnlCrearCorreo.getHeight()+3);
        pnlCrearCorreo.setPreferredSize(new Dimension (
                pnlCrearCorreo.getWidth(), 
                pnlCrearCorreo.getHeight()+textoCorreo.getHeight()));


        correos.add(textoCorreo); //añadir a la lista
        //pnlCrearCorreo.repaint();        
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

        //Cambiar tamaño del panel con campos de texto
        pnlCrearTelefono.setSize(pnlCrearTelefono.getWidth(), 
                pnlCrearTelefono.getHeight()+3);
        pnlCrearTelefono.setPreferredSize(new Dimension (
                pnlCrearTelefono.getWidth(), 
                pnlCrearTelefono.getHeight()+textoTelefono.getHeight()));
        
        telefonos.add(textoTelefono); //añadir a la lista
        
    
    }
    
    public void cargarTablas() {
        //usuarios.clear();
        clientes = controlador.obtenerClientes();
        cargarClientesJTable(tbl_usuarioListado, true);
        //cargarUsuariosJTable(tbl_usuarioCreado, true);
        //cargarUsuariosJTable(tbl_deshabilitar, true);
        //cargarUsuariosJTable(tbl_habilitar, false);
        //cargarUsuariosJTable(tbl_actPermisos, true);

//        for (int i = 0; i < usuarios.size(); i++) {
//            if (sesion.getUsuario().getNombre()
//                    .equals(usuarios.get(i).getNombre())) {
//                txt_actuali_nombreUsuario.setText(usuarios.get(i).getNombre());
//                txt_actuali_correo.setText(usuarios.get(i).getCorreo());
//            }
//        }
        System.out.println(sesion.getUsuario().getNombre());
    }
    
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
                row[4] = clientes.get(i).isAprobarCredito() ? "Crédito" : "Sin crédito";
                row[5] = clientes.get(i).getLimiteCredito();
                
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
    
    int num = 0;
    public void nuevoContacto(int cantidad, boolean correo) {
        int pos; 
        if(correo) {
            JTextField textoCorreo = new JTextField("HOLA "+ num++);
            //Eliminar panel a modificar
            //scpnlClientesCrearCorreo.remove(pnlCrearCorreo);
            pnlCrearCorreo.add(textoCorreo); //añadir al panel
            
            //Posición del último campo de texto 
            pos = correos.get(correos.size()-1).getY() + 
                    correos.get(correos.size()-1).getHeight() + 5;
            textoCorreo.setBounds(5, pos, 220, 30);
                
            //Cambiar tamaño del panel con campos de texto
            pnlCrearCorreo.setSize(pnlCrearCorreo.getWidth(), 
                    pnlCrearCorreo.getHeight()+15);
            pnlCrearCorreo.setPreferredSize(new Dimension (
                    pnlCrearCorreo.getWidth(), 
                    pnlCrearCorreo.getHeight()+textoCorreo.getHeight()));
            
            pnlCrearTelefono.setBounds(pnlCrearTelefono.getX(), pnlCrearCorreo.getHeight()+10, pnlCrearTelefono.getWidth(), pnlCrearTelefono.getHeight());
            System.out.println("Altura panel: " + pnlCrearCorreo.getHeight());
            //Añadir el panel modificado y cambiar tamaño de su panel padre
            scpnlClientesCrearCorreo.add(pnlCrearCorreo);
//            pnlCrearContacto.setSize(pnlCrearContacto.getWidth(), 
//                    pnlCrearCorreo.getHeight()+1);
//            pnlCrearContacto.setPreferredSize(new Dimension (
//                    pnlCrearContacto.getWidth(), pnlCrearCorreo.getHeight()+1));
//            pnlCrearContacto.setBounds(pnlCrearContacto.getX(), 
//                    pnlCrearContacto.getY(), pnlCrearContacto.getWidth(), 
//                    pnlCrearContacto.getHeight());
            
            correos.add(textoCorreo); //añadir a la lista
            //System.out.println(correos.get(correos.size()-1).getText());
            
            //nuevoContacto(masCorreo++, true);
            
        } else {
            JTextField textoTelefono;
            int posi = 0;
            //Eliminar panel a modificar
            scpnlClientesCrearTelefono.remove(pnlCrearTelefono);
            
            //Crear nuevos campos de texto
            textoTelefono = new JTextField();
            pnlCrearTelefono.add(textoTelefono);
            posi = telefonos.get(telefonos.size()-1).getY() + 
                telefonos.get(telefonos.size()-1).getHeight() + 5;
            textoTelefono.setBounds(5, posi, 220, 30);
                
            //Cambiar tamaño del panel con campos de texto
            pnlCrearTelefono.setSize(pnlCrearTelefono.getWidth(), 
                    pnlCrearTelefono.getHeight()+15);
            pnlCrearTelefono.setPreferredSize(new Dimension (
                    pnlCrearTelefono.getWidth(), 
                    pnlCrearTelefono.getHeight()+textoTelefono.getHeight()));
            //}
            //Añadir el panel modificado y cambiar tamaño de su panel padre
            scpnlClientesCrearTelefono.add(pnlCrearTelefono);
//            scpnlClientesCrearTelefono.setSize(scpnlClientesCrearTelefono.getWidth(), 
//                    pnlCrearTelefono.getHeight());
//            pnlCrearContacto.setPreferredSize(new Dimension (
//                    pnlCrearContacto.getWidth(), pnlCrearTelefono.getHeight()+pnlCrearCorreo.getHeight()));
//            pnlCrearContacto.setBounds(pnlCrearContacto.getX(), 
//                    pnlCrearContacto.getY(), pnlCrearContacto.getWidth(), 
//                    pnlCrearContacto.getHeight());
        }
        //nuevoContacto(masTelefono++, false);
    }
    public void limpiarCampos() {
        masCorreo = 0;
        masTelefono = 0;
    }
    
    public boolean crearCliente(String nombre, String apellido1, 
            String apellido2, String cedula, float limiteCred, 
            boolean aprobarCred, ArrayList<ArrayList<Object>> contactos) {
        
        return controlador.crearCliente(nombre, apellido1, apellido2, cedula, 
                limiteCred, aprobarCred, contactos);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgCredito = new javax.swing.ButtonGroup();
        pnl_modCliente = new javax.swing.JPanel();
        tb_modCliente = new javax.swing.JTabbedPane();
        pnl_listado = new javax.swing.JPanel();
        txt_listado_buscar = new javax.swing.JTextField();
        scpnl_tbl_clienteListado = new javax.swing.JScrollPane();
        tbl_usuarioListado = new javax.swing.JTable();
        lbl_listado_buscarUsuario = new javax.swing.JLabel();
        pnl_agregar = new javax.swing.JPanel();
        lbl_crear_nombreCliente = new javax.swing.JLabel();
        txt_crear_nombreCliente = new javax.swing.JTextField();
        txt_crear_nombreCliente1 = new javax.swing.JTextField();
        lbl_crear_apellidoCliente1 = new javax.swing.JLabel();
        txt_crear_nombreCliente2 = new javax.swing.JTextField();
        lbl_crear_apellidoCliente2 = new javax.swing.JLabel();
        lbl_crear_cedulaCliente = new javax.swing.JLabel();
        txt_crear_cedulaCliente = new javax.swing.JTextField();
        lbl_crear_limiteCliente = new javax.swing.JLabel();
        txt_crear_limiteCliente = new javax.swing.JTextField();
        pnl_crear_creditoCliente = new javax.swing.JPanel();
        rb_crear_sinCredito = new javax.swing.JRadioButton();
        rb_crear_conCredito = new javax.swing.JRadioButton();
        spnl_crear_clientes = new javax.swing.JScrollPane();
        tbl_crear = new javax.swing.JTable();
        btnCrearCliente = new javax.swing.JButton();
        pnl_crear_creditoCliente1 = new javax.swing.JPanel();
        tbClientesContactos = new javax.swing.JTabbedPane();
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
        jToggleButton1 = new javax.swing.JToggleButton();
        pnl_actualizar = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();

        setPreferredSize(new java.awt.Dimension(1240, 693));

        txt_listado_buscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_listado_buscarKeyReleased(evt);
            }
        });

        tbl_usuarioListado.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cédula", "Primer Apellido", "Segundo Apellido", "Nombre", "Crédito", "Límite Crédito", "Contactos"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_usuarioListado.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        scpnl_tbl_clienteListado.setViewportView(tbl_usuarioListado);

        lbl_listado_buscarUsuario.setText("Buscar cliente: ");

        javax.swing.GroupLayout pnl_listadoLayout = new javax.swing.GroupLayout(pnl_listado);
        pnl_listado.setLayout(pnl_listadoLayout);
        pnl_listadoLayout.setHorizontalGroup(
            pnl_listadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_listadoLayout.createSequentialGroup()
                .addContainerGap(39, Short.MAX_VALUE)
                .addGroup(pnl_listadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(pnl_listadoLayout.createSequentialGroup()
                        .addComponent(lbl_listado_buscarUsuario)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txt_listado_buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 976, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(scpnl_tbl_clienteListado, javax.swing.GroupLayout.PREFERRED_SIZE, 1100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        pnl_listadoLayout.setVerticalGroup(
            pnl_listadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_listadoLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(pnl_listadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_listado_buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_listado_buscarUsuario))
                .addGap(18, 18, 18)
                .addComponent(scpnl_tbl_clienteListado, javax.swing.GroupLayout.PREFERRED_SIZE, 407, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(84, Short.MAX_VALUE))
        );

        tb_modCliente.addTab("Listado Clientes", pnl_listado);

        lbl_crear_nombreCliente.setText("Nombre:");

        lbl_crear_apellidoCliente1.setText("Primer Apellido:");

        lbl_crear_apellidoCliente2.setText("Segundo Apellido:");

        lbl_crear_cedulaCliente.setText("Cédula:");

        lbl_crear_limiteCliente.setText("Límite de crédito:");

        txt_crear_limiteCliente.setEnabled(false);

        pnl_crear_creditoCliente.setBorder(javax.swing.BorderFactory.createTitledBorder("Crédito de Cliente:"));

        bgCredito.add(rb_crear_sinCredito);
        rb_crear_sinCredito.setText("Sin crédigo");

        bgCredito.add(rb_crear_conCredito);
        rb_crear_conCredito.setText("Con crédito");

        javax.swing.GroupLayout pnl_crear_creditoClienteLayout = new javax.swing.GroupLayout(pnl_crear_creditoCliente);
        pnl_crear_creditoCliente.setLayout(pnl_crear_creditoClienteLayout);
        pnl_crear_creditoClienteLayout.setHorizontalGroup(
            pnl_crear_creditoClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_crear_creditoClienteLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rb_crear_conCredito)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 68, Short.MAX_VALUE)
                .addComponent(rb_crear_sinCredito)
                .addContainerGap())
        );
        pnl_crear_creditoClienteLayout.setVerticalGroup(
            pnl_crear_creditoClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_crear_creditoClienteLayout.createSequentialGroup()
                .addGroup(pnl_crear_creditoClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rb_crear_conCredito)
                    .addComponent(rb_crear_sinCredito))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        tbl_crear.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cédula", "Nombre", "Primer Apellido", "Segundo Apellido", "Teléfono", "Correo?", "Límite de Crédito"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
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
            tbl_crear.getColumnModel().getColumn(4).setResizable(false);
            tbl_crear.getColumnModel().getColumn(5).setResizable(false);
            tbl_crear.getColumnModel().getColumn(6).setResizable(false);
        }

        btnCrearCliente.setText("Crear Cliente");
        btnCrearCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCrearClienteActionPerformed(evt);
            }
        });

        pnl_crear_creditoCliente1.setBorder(javax.swing.BorderFactory.createTitledBorder("Contacto:"));
        pnl_crear_creditoCliente1.setAutoscrolls(true);

        tbClientesContactos.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        tbClientesContactos.setTabPlacement(javax.swing.JTabbedPane.RIGHT);

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

        tbClientesContactos.addTab("", new javax.swing.ImageIcon(getClass().getResource("/recursos/telefono.png")), scpnlClientesCrearTelefono); // NOI18N

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

        tbClientesContactos.addTab("", new javax.swing.ImageIcon(getClass().getResource("/recursos/email.png")), scpnlClientesCrearCorreo); // NOI18N

        javax.swing.GroupLayout pnl_crear_creditoCliente1Layout = new javax.swing.GroupLayout(pnl_crear_creditoCliente1);
        pnl_crear_creditoCliente1.setLayout(pnl_crear_creditoCliente1Layout);
        pnl_crear_creditoCliente1Layout.setHorizontalGroup(
            pnl_crear_creditoCliente1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tbClientesContactos, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        pnl_crear_creditoCliente1Layout.setVerticalGroup(
            pnl_crear_creditoCliente1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tbClientesContactos, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jToggleButton1.setText("jToggleButton1");

        javax.swing.GroupLayout pnl_agregarLayout = new javax.swing.GroupLayout(pnl_agregar);
        pnl_agregar.setLayout(pnl_agregarLayout);
        pnl_agregarLayout.setHorizontalGroup(
            pnl_agregarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_agregarLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(pnl_agregarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(spnl_crear_clientes)
                    .addGroup(pnl_agregarLayout.createSequentialGroup()
                        .addGroup(pnl_agregarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_crear_cedulaCliente, javax.swing.GroupLayout.DEFAULT_SIZE, 294, Short.MAX_VALUE)
                            .addComponent(txt_crear_nombreCliente, javax.swing.GroupLayout.DEFAULT_SIZE, 294, Short.MAX_VALUE)
                            .addComponent(pnl_crear_creditoCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbl_crear_cedulaCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_crear_nombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(62, 62, 62)
                        .addGroup(pnl_agregarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_crear_limiteCliente)
                            .addComponent(lbl_crear_apellidoCliente2)
                            .addComponent(txt_crear_nombreCliente2, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_crear_nombreCliente1, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_crear_apellidoCliente1, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_crear_limiteCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                        .addGroup(pnl_agregarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(pnl_crear_creditoCliente1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnCrearCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(71, 71, 71))
            .addGroup(pnl_agregarLayout.createSequentialGroup()
                .addGap(136, 136, 136)
                .addComponent(jToggleButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnl_agregarLayout.setVerticalGroup(
            pnl_agregarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_agregarLayout.createSequentialGroup()
                .addContainerGap(34, Short.MAX_VALUE)
                .addGroup(pnl_agregarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_agregarLayout.createSequentialGroup()
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
                            .addComponent(pnl_crear_creditoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnl_agregarLayout.createSequentialGroup()
                                .addComponent(lbl_crear_limiteCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_crear_limiteCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_agregarLayout.createSequentialGroup()
                        .addComponent(pnl_crear_creditoCliente1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38)
                        .addComponent(btnCrearCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jToggleButton1)
                .addGap(13, 13, 13)
                .addComponent(spnl_crear_clientes, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        tb_modCliente.addTab("Agregar cliente", pnl_agregar);

        javax.swing.GroupLayout pnl_actualizarLayout = new javax.swing.GroupLayout(pnl_actualizar);
        pnl_actualizar.setLayout(pnl_actualizarLayout);
        pnl_actualizarLayout.setHorizontalGroup(
            pnl_actualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1172, Short.MAX_VALUE)
        );
        pnl_actualizarLayout.setVerticalGroup(
            pnl_actualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 580, Short.MAX_VALUE)
        );

        tb_modCliente.addTab("Editar cliente", pnl_actualizar);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1172, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 580, Short.MAX_VALUE)
        );

        tb_modCliente.addTab("Habilitar clientes", jPanel1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1172, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 580, Short.MAX_VALUE)
        );

        tb_modCliente.addTab("Límite de crédito", jPanel2);

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
                .addComponent(tb_modCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 607, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(77, 77, 77))
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
        limpiarCampos();
        ArrayList<ArrayList<Object>> contactos = new ArrayList<>();
        ArrayList<Object> temp =  new ArrayList<>();
        
        temp.add(TipoContacto.CORREO);
        temp.add("user@user.com");
        contactos.add(temp);
        
        temp =  new ArrayList<>();
        temp.add(TipoContacto.TELEFONO);
        temp.add("88659321");
        contactos.add(temp);
        
        temp =  new ArrayList<>();
        temp.add(TipoContacto.CORREO);
        temp.add("user@user.com");
        contactos.add(temp);
        
        System.out.println(controlador.crearCliente("Q", "W", "R", "01", 0, true, contactos));
    }//GEN-LAST:event_btnCrearClienteActionPerformed

    private void btnNuevoCorreoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoCorreoActionPerformed
        nuevoCorreo(masCorreo++);        
    }//GEN-LAST:event_btnNuevoCorreoActionPerformed

    private void btnNuevoTelefonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoTelefonoActionPerformed
        nuevoTelefono(masTelefono++);
    }//GEN-LAST:event_btnNuevoTelefonoActionPerformed

    private void txt_listado_buscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_listado_buscarKeyReleased
        System.out.println(tbl_usuarioListado);
        tbl_usuarioListado.setShowGrid(true);
        
        Object[] row = new Object[5];
        DefaultTableModel model = (DefaultTableModel) tbl_usuarioListado.getModel();
        model.setRowCount(0);
        
        JTextArea text =  new JTextArea();
        text.setText("<html><body>Line One<br>Line two<br>Line Three</body></html>");
        
        row[0] = "Lancaster";
        row[1] = "<html><body>Line One<br>Line two<br>Line Three</body></html>";
        
        
        model.addRow(row);
        tbl_usuarioListado.setRowHeight(tbl_usuarioListado.getRowHeight() + 50);
    }//GEN-LAST:event_txt_listado_buscarKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgCredito;
    private javax.swing.JButton btnCrearCliente;
    private javax.swing.JButton btnNuevoCorreo;
    private javax.swing.JButton btnNuevoTelefono;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JLabel lblCrearTelefono;
    private javax.swing.JLabel lbl_crear_apellidoCliente1;
    private javax.swing.JLabel lbl_crear_apellidoCliente2;
    private javax.swing.JLabel lbl_crear_cedulaCliente;
    private javax.swing.JLabel lbl_crear_correo;
    private javax.swing.JLabel lbl_crear_limiteCliente;
    private javax.swing.JLabel lbl_crear_nombreCliente;
    private javax.swing.JLabel lbl_listado_buscarUsuario;
    private javax.swing.JPanel pnlCrearCorreo;
    private javax.swing.JPanel pnlCrearTelefono;
    private javax.swing.JPanel pnl_actualizar;
    private javax.swing.JPanel pnl_agregar;
    private javax.swing.JPanel pnl_crear_creditoCliente;
    private javax.swing.JPanel pnl_crear_creditoCliente1;
    private javax.swing.JPanel pnl_listado;
    private javax.swing.JPanel pnl_modCliente;
    private javax.swing.JRadioButton rb_crear_conCredito;
    private javax.swing.JRadioButton rb_crear_sinCredito;
    private javax.swing.JScrollPane scpnlClientesCrearCorreo;
    private javax.swing.JScrollPane scpnlClientesCrearTelefono;
    private javax.swing.JScrollPane scpnl_tbl_clienteListado;
    private javax.swing.JScrollPane spnl_crear_clientes;
    private javax.swing.JTabbedPane tbClientesContactos;
    private javax.swing.JTabbedPane tb_modCliente;
    private javax.swing.JTable tbl_crear;
    private javax.swing.JTable tbl_usuarioListado;
    private javax.swing.JTextField txt_crear_cedulaCliente;
    private javax.swing.JTextField txt_crear_correo;
    private javax.swing.JTextField txt_crear_limiteCliente;
    private javax.swing.JTextField txt_crear_nombreCliente;
    private javax.swing.JTextField txt_crear_nombreCliente1;
    private javax.swing.JTextField txt_crear_nombreCliente2;
    private javax.swing.JTextField txt_crear_telefono;
    private javax.swing.JTextField txt_listado_buscar;
    // End of variables declaration//GEN-END:variables
}
