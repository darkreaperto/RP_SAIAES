/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacion;

import util.Estado;
import bd.AESEncrypt;
import controladores.CtrAcceso;
import controladores.CtrUsuario;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import logica.Usuario;
import logica.Verificacion;
import util.MessageHelper;
import util.Rol;

/**
 *
 * @author ahoihanabi
 */
public class ItnFrmUsuario extends javax.swing.JInternalFrame {

    private static ItnFrmUsuario instancia = null;
    private static AESEncrypt crypter;
    private static Mensaje msg;
    private static CtrUsuario controlador;
    private static ArrayList<Usuario> usuarios;
    private static CtrAcceso sesion;
    private DefaultTableModel model;
    private final Verificacion verificacion;

    /**
     * Creates new form intfrmUsuario
     *
     * @param sesionAcc
     * @param usuarios
     */
    protected ItnFrmUsuario(CtrAcceso sesionAcc, ArrayList<Usuario> usuarios) {
        initComponents();
        //Inicializar variables
        controlador = CtrUsuario.getInstancia();
        crypter = new AESEncrypt();
        crypter.addKey("SAI");
        msg = new Mensaje();
        verificacion = new Verificacion();
        ItnFrmUsuario.usuarios = usuarios;
        ItnFrmUsuario.sesion = sesionAcc;
        cargarTablas();
        pnlActualizarClave.setVisible(false);

    }

    public static ItnFrmUsuario getInstancia(CtrAcceso sesionAcc, ArrayList<Usuario> usuarios) {
        if (instancia == null) {
            instancia = new ItnFrmUsuario(sesionAcc, usuarios);
        }
        return instancia;
    }

    public void actualiUsuario(String nombreUsuario, String correo,
            String clave, String nuevaClave, String nuevaClaveConf) {

        if (!nombreUsuario.isEmpty() && !correo.isEmpty()) {
            if (pnlActualizarClave.isVisible()) {
                if (!clave.isEmpty() && !nuevaClave.isEmpty()
                        && !nuevaClaveConf.isEmpty()) {
                    if (sesion.compararClave(sesion.getUsuario().getNombre(),
                            clave)) {
                        if (verificacion.validateEmail(correo)) {
                            if (verificacion.validatePassword(nuevaClave)) {
                                if (nuevaClave.equals(nuevaClaveConf)) {
                                    nuevaClave = crypter.encriptar(nuevaClave);
                                    controlador.actualizarUsuario(nombreUsuario,
                                            nuevaClave, correo,
                                            sesion.getUsuario().getRol(),
                                            sesion.getUsuario().getEstado(),
                                            sesion.getUsuario().getCodigo());

                                    msg.mostrarMensaje(
                                            JOptionPane.INFORMATION_MESSAGE,
                                            MessageHelper.USER_UPDATE_SUCCESS);
                                } else {
                                    msg.mostrarMensaje(
                                            JOptionPane.ERROR_MESSAGE,
                                            MessageHelper.MISMATCHING_PASSWORD_FIELDS);
                                }
                            } else {
                                msg.mostrarMensaje(JOptionPane.ERROR_MESSAGE,
                                        MessageHelper.PASSWORD_SYNTAX_FAILURE);
                            }
                        } else {
                            msg.mostrarMensaje(JOptionPane.ERROR_MESSAGE, MessageHelper.EMAIL_SYNTAX_FAILURE);
                        }
                    } else {
                        msg.mostrarMensaje(JOptionPane.ERROR_MESSAGE,
                                MessageHelper.USER_UPDATE_FAILURE);
                    }
                } //comprobar contraseña y nombre de usuario

            } else {
                if (verificacion.validateEmail(correo)) {
                    controlador.actualizarUsuario(nombreUsuario, sesion.getUsuario().getContrasenna(),
                            correo, sesion.getUsuario().getRol(),
                            sesion.getUsuario().getEstado(), sesion.getUsuario().getCodigo());
                    msg.mostrarMensaje(JOptionPane.INFORMATION_MESSAGE, MessageHelper.USER_UPDATE_SUCCESS);
                } else {
                    msg.mostrarMensaje(JOptionPane.ERROR_MESSAGE, MessageHelper.EMAIL_SYNTAX_FAILURE);
                }
            }
        } else {
            msg.mostrarMensaje(JOptionPane.ERROR_MESSAGE,
                    MessageHelper.USER_ACCESS_FAILURE);
        }

    }

    public void cargarUsuariosJTable(JTable tabla, boolean estado) {
        Object[] row = new Object[5];
        model = (DefaultTableModel) tabla.getModel();
        model.setRowCount(0);
        for (int i = 0; i < usuarios.size(); i++) {

            if (usuarios.get(i).getEstado().equals(Estado.Activo) && estado) {
                row[0] = usuarios.get(i).getCodigo();
                row[1] = usuarios.get(i).getNombre();
                //row[2] = usuarios.get(i).getContrasenna();
                row[3] = usuarios.get(i).getCorreo();
                row[4] = usuarios.get(i).getDescRol();
                //row[5] = lista.get(i).getEstado();
                model.addRow(row);
            }
            if (usuarios.get(i).getEstado().equals(Estado.Deshabilitado) && !estado) {
                row[0] = usuarios.get(i).getCodigo();
                row[1] = usuarios.get(i).getNombre();
                //row[2] = usuarios.get(i).getContrasenna();
                row[3] = usuarios.get(i).getCorreo();
                row[4] = usuarios.get(i).getDescRol();
                //row[5] = lista.get(i).getEstado();
                model.addRow(row);
            }
        }
    }

    public void actualizarSesion(String usuario) {
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getNombre().equals(usuario)) {
                sesion.setUsuario(usuarios.get(i));
            }
        }
    }

    public void cargarTablas() {
        //usuarios.clear();
        usuarios = controlador.obtenerUsuarios();
        cargarUsuariosJTable(tbl_usuarioListado, true);
        cargarUsuariosJTable(tbl_usuarioCreado, true);
        cargarUsuariosJTable(tbl_deshabilitar, true);
        cargarUsuariosJTable(tbl_habilitar, false);
        cargarUsuariosJTable(tbl_actPermisos, true);

        for (int i = 0; i < usuarios.size(); i++) {
            if (sesion.getUsuario().getNombre()
                    .equals(usuarios.get(i).getNombre())) {
                txt_actuali_nombreUsuario.setText(usuarios.get(i).getNombre());
                txt_actuali_correo.setText(usuarios.get(i).getCorreo());
            }
        }
        System.out.println(sesion.getUsuario().getNombre());
    }

    public void limpiarTexto(String boton) {

        if (boton.equals("Crear")) {
            txt_crear_nombreUsuario.setText("");
            txt_crear_correo.setText("");
            pw_crear_contra.setText("");
            pw_crear_confContra.setText("");
            rb_crear_rolEstandar.setSelected(true);
        } else if (boton.equals("Actualizar")) {
            txt_actuali_nombreUsuario.setText("");
            txt_actuali_correo.setText("");
            pw_actuali_lastpass.setText("");
            pw_actuali_newPass.setText("");
            pw_actuali_confNewPass.setText("");
        }
    }

    public void deshabilitarPaneles() {

        tb_modUsuario_permisos.removeAll();
        if (sesion.getUsuario().getRol().equals(Rol.Administrador)) {
            tb_modUsuario_permisos.add(pnl_listado);
            tb_modUsuario_permisos.add(pnl_crear);
            tb_modUsuario_permisos.add(pnl_actualizarPermisos);
            tb_modUsuario_permisos.add(pnl_deshabilitar);
            tb_modUsuario_permisos.add(pnl_actualizar);
        } else {
            tb_modUsuario_permisos.add(pnl_actualizar);
        }
        cargarTablas();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bg_crear_rol = new javax.swing.ButtonGroup();
        pnl_modUsuario = new javax.swing.JPanel();
        tb_modUsuario_permisos = new javax.swing.JTabbedPane();
        pnl_listado = new javax.swing.JPanel();
        lbl_listado_buscarUsuario = new javax.swing.JLabel();
        txt_listado_buscar = new javax.swing.JTextField();
        scpnl_tbl_usuarioListado = new javax.swing.JScrollPane();
        tbl_usuarioListado = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        pnl_crear = new javax.swing.JPanel();
        lbl_crear_nombreUsuario = new javax.swing.JLabel();
        txt_crear_nombreUsuario = new javax.swing.JTextField();
        lbl_crear_correo = new javax.swing.JLabel();
        txt_crear_correo = new javax.swing.JTextField();
        lbl_crear_password = new javax.swing.JLabel();
        pw_crear_contra = new javax.swing.JPasswordField();
        lbl_crear_confirmPassw = new javax.swing.JLabel();
        pw_crear_confContra = new javax.swing.JPasswordField();
        pnl_crear_rolContainer = new javax.swing.JPanel();
        rb_crear_rolEstandar = new javax.swing.JRadioButton();
        rb_crear_rolAdmin = new javax.swing.JRadioButton();
        btn_crearUsuario = new javax.swing.JButton();
        scpnl_tbl_usuarioCreado = new javax.swing.JScrollPane();
        tbl_usuarioCreado = new javax.swing.JTable();
        pnl_deshabilitar = new javax.swing.JPanel();
        lbl_deshab_selectUsuario = new javax.swing.JLabel();
        pnl_deshab_deshabContainer = new javax.swing.JPanel();
        rb_deshab_deshabilitar = new javax.swing.JRadioButton();
        rb_deshab_habilitar = new javax.swing.JRadioButton();
        btn_deshabilitar = new javax.swing.JButton();
        tb_deshab = new javax.swing.JTabbedPane();
        scpnl_tbl_usuarioDeshab = new javax.swing.JScrollPane();
        tbl_deshabilitar = new javax.swing.JTable();
        scpnl_tbl_usuarioHabilitar = new javax.swing.JScrollPane();
        tbl_habilitar = new javax.swing.JTable();
        pnl_actualizarPermisos = new javax.swing.JPanel();
        lbl_actPermi_selectUsuario = new javax.swing.JLabel();
        pnl_actPermi_rolContainer = new javax.swing.JPanel();
        rb_actPermi_estandar = new javax.swing.JRadioButton();
        rb_actPermi_Admin = new javax.swing.JRadioButton();
        btn_actPermi = new javax.swing.JButton();
        scpnl_tbl_usuarioActPermiso = new javax.swing.JScrollPane();
        tbl_actPermisos = new javax.swing.JTable();
        pnl_actualizar = new javax.swing.JPanel();
        lbl_actuali_nombreUsuario = new javax.swing.JLabel();
        txt_actuali_nombreUsuario = new javax.swing.JTextField();
        btn_actualiUsuario = new javax.swing.JButton();
        lbl_actuali_nombreUsuario1 = new javax.swing.JLabel();
        txt_actuali_correo = new javax.swing.JTextField();
        pnlActualizarClave = new javax.swing.JPanel();
        pw_actuali_lastpass = new javax.swing.JPasswordField();
        lbl_actuali_passActual = new javax.swing.JLabel();
        pw_actuali_newPass = new javax.swing.JPasswordField();
        lbl_actuali_passNew = new javax.swing.JLabel();
        pw_actuali_confNewPass = new javax.swing.JPasswordField();
        lbl_actuali_confpassNew = new javax.swing.JLabel();
        btnActualiContrasenna = new javax.swing.JButton();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 35), new java.awt.Dimension(0, 35), new java.awt.Dimension(32767, 35));
        filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 200), new java.awt.Dimension(0, 200), new java.awt.Dimension(32767, 200));

        pnl_modUsuario.setPreferredSize(new java.awt.Dimension(1239, 680));

        tb_modUsuario_permisos.setName("Listado Usuarios"); // NOI18N

        pnl_listado.setName("Listado de usuarios"); // NOI18N
        pnl_listado.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                pnl_listadoFocusGained(evt);
            }
        });
        pnl_listado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnl_listadoMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnl_listadoMouseEntered(evt);
            }
        });

        lbl_listado_buscarUsuario.setText("Buscar usuario: ");

        txt_listado_buscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_listado_buscarKeyReleased(evt);
            }
        });

        tbl_usuarioListado.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo", "Nombre de Usuario", "Correo", "Rol"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_usuarioListado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_usuarioListadoMouseClicked(evt);
            }
        });
        tbl_usuarioListado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbl_usuarioListadoKeyReleased(evt);
            }
        });
        scpnl_tbl_usuarioListado.setViewportView(tbl_usuarioListado);

        jButton1.setText("jButton1");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnl_listadoLayout = new javax.swing.GroupLayout(pnl_listado);
        pnl_listado.setLayout(pnl_listadoLayout);
        pnl_listadoLayout.setHorizontalGroup(
            pnl_listadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_listadoLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(pnl_listadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scpnl_tbl_usuarioListado, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 1073, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnl_listadoLayout.createSequentialGroup()
                        .addComponent(lbl_listado_buscarUsuario)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_listado_buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 946, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        pnl_listadoLayout.setVerticalGroup(
            pnl_listadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_listadoLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(pnl_listadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_listado_buscarUsuario)
                    .addComponent(txt_listado_buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(scpnl_tbl_usuarioListado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(66, Short.MAX_VALUE))
        );

        tb_modUsuario_permisos.addTab("Listado de usuarios", pnl_listado);

        pnl_crear.setName("Agregar usuario"); // NOI18N

        lbl_crear_nombreUsuario.setText("Nombre de Usuario:");

        txt_crear_nombreUsuario.setNextFocusableComponent(txt_crear_correo);

        lbl_crear_correo.setText("Correo Electrónico:");

        txt_crear_correo.setNextFocusableComponent(pw_crear_contra);

        lbl_crear_password.setText("Contraseña:");

        pw_crear_contra.setNextFocusableComponent(pw_crear_confContra);

        lbl_crear_confirmPassw.setText("Confirmar Contraseña:");

        pw_crear_confContra.setNextFocusableComponent(rb_crear_rolAdmin);

        pnl_crear_rolContainer.setBorder(javax.swing.BorderFactory.createTitledBorder("Rol de Usuarios:"));

        bg_crear_rol.add(rb_crear_rolEstandar);
        rb_crear_rolEstandar.setText("Estándar");
        rb_crear_rolEstandar.setNextFocusableComponent(btn_crearUsuario);

        bg_crear_rol.add(rb_crear_rolAdmin);
        rb_crear_rolAdmin.setText("Administrador");
        rb_crear_rolAdmin.setNextFocusableComponent(rb_crear_rolEstandar);

        javax.swing.GroupLayout pnl_crear_rolContainerLayout = new javax.swing.GroupLayout(pnl_crear_rolContainer);
        pnl_crear_rolContainer.setLayout(pnl_crear_rolContainerLayout);
        pnl_crear_rolContainerLayout.setHorizontalGroup(
            pnl_crear_rolContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_crear_rolContainerLayout.createSequentialGroup()
                .addComponent(rb_crear_rolAdmin)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 57, Short.MAX_VALUE)
                .addComponent(rb_crear_rolEstandar)
                .addContainerGap())
        );
        pnl_crear_rolContainerLayout.setVerticalGroup(
            pnl_crear_rolContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_crear_rolContainerLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnl_crear_rolContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rb_crear_rolAdmin)
                    .addComponent(rb_crear_rolEstandar)))
        );

        btn_crearUsuario.setText("Crear Usuario");
        btn_crearUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_crearUsuarioActionPerformed(evt);
            }
        });

        tbl_usuarioCreado.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo", "Nombre de Usuario", "Contraseña", "Correo", "Rol"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_usuarioCreado.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tbl_usuarioCreado.setUpdateSelectionOnSort(false);
        scpnl_tbl_usuarioCreado.setViewportView(tbl_usuarioCreado);

        javax.swing.GroupLayout pnl_crearLayout = new javax.swing.GroupLayout(pnl_crear);
        pnl_crear.setLayout(pnl_crearLayout);
        pnl_crearLayout.setHorizontalGroup(
            pnl_crearLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_crearLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(pnl_crearLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(scpnl_tbl_usuarioCreado)
                    .addGroup(pnl_crearLayout.createSequentialGroup()
                        .addGroup(pnl_crearLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lbl_crear_nombreUsuario)
                            .addComponent(lbl_crear_correo)
                            .addComponent(txt_crear_correo, javax.swing.GroupLayout.DEFAULT_SIZE, 294, Short.MAX_VALUE)
                            .addComponent(txt_crear_nombreUsuario))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 80, Short.MAX_VALUE)
                        .addGroup(pnl_crearLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(pw_crear_contra)
                            .addComponent(lbl_crear_password)
                            .addComponent(lbl_crear_confirmPassw)
                            .addComponent(pw_crear_confContra, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(72, 72, 72)
                        .addGroup(pnl_crearLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(pnl_crear_rolContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn_crearUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(72, 72, 72))
        );
        pnl_crearLayout.setVerticalGroup(
            pnl_crearLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_crearLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(pnl_crearLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnl_crearLayout.createSequentialGroup()
                        .addGroup(pnl_crearLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnl_crearLayout.createSequentialGroup()
                                .addComponent(lbl_crear_nombreUsuario)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_crear_nombreUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(pnl_crear_rolContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(lbl_crear_correo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnl_crearLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_crear_correo, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_crearUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnl_crearLayout.createSequentialGroup()
                        .addComponent(lbl_crear_password)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pw_crear_contra, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24)
                        .addComponent(lbl_crear_confirmPassw)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pw_crear_confContra, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addComponent(scpnl_tbl_usuarioCreado, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(46, Short.MAX_VALUE))
        );

        tb_modUsuario_permisos.addTab("Agregar usuario", pnl_crear);

        pnl_deshabilitar.setName("Habilitar usuarios"); // NOI18N

        lbl_deshab_selectUsuario.setText("Seleccionar Usuario:");

        pnl_deshab_deshabContainer.setBorder(javax.swing.BorderFactory.createTitledBorder("Activo:"));

        bg_crear_rol.add(rb_deshab_deshabilitar);
        rb_deshab_deshabilitar.setSelected(true);
        rb_deshab_deshabilitar.setText("Deshabilitar");
        rb_deshab_deshabilitar.setNextFocusableComponent(btn_deshabilitar);

        bg_crear_rol.add(rb_deshab_habilitar);
        rb_deshab_habilitar.setText("Habilitar");
        rb_deshab_habilitar.setNextFocusableComponent(rb_deshab_deshabilitar);

        javax.swing.GroupLayout pnl_deshab_deshabContainerLayout = new javax.swing.GroupLayout(pnl_deshab_deshabContainer);
        pnl_deshab_deshabContainer.setLayout(pnl_deshab_deshabContainerLayout);
        pnl_deshab_deshabContainerLayout.setHorizontalGroup(
            pnl_deshab_deshabContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_deshab_deshabContainerLayout.createSequentialGroup()
                .addComponent(rb_deshab_habilitar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                .addComponent(rb_deshab_deshabilitar)
                .addContainerGap())
        );
        pnl_deshab_deshabContainerLayout.setVerticalGroup(
            pnl_deshab_deshabContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_deshab_deshabContainerLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnl_deshab_deshabContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rb_deshab_habilitar)
                    .addComponent(rb_deshab_deshabilitar)))
        );

        btn_deshabilitar.setText("Guardar Cambios");
        btn_deshabilitar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deshabilitarActionPerformed(evt);
            }
        });

        tbl_deshabilitar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo", "Nombre de Usuario", "Contraseña", "Correo", "Rol"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_deshabilitar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_deshabilitarMouseClicked(evt);
            }
        });
        tbl_deshabilitar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbl_deshabilitarKeyReleased(evt);
            }
        });
        scpnl_tbl_usuarioDeshab.setViewportView(tbl_deshabilitar);

        tb_deshab.addTab("Activos", scpnl_tbl_usuarioDeshab);

        tbl_habilitar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo", "Nombre de Usuario", "Contraseña", "Correo", "Rol"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_habilitar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_habilitarMouseClicked(evt);
            }
        });
        tbl_habilitar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbl_habilitarKeyReleased(evt);
            }
        });
        scpnl_tbl_usuarioHabilitar.setViewportView(tbl_habilitar);

        tb_deshab.addTab("Inactivos", scpnl_tbl_usuarioHabilitar);

        javax.swing.GroupLayout pnl_deshabilitarLayout = new javax.swing.GroupLayout(pnl_deshabilitar);
        pnl_deshabilitar.setLayout(pnl_deshabilitarLayout);
        pnl_deshabilitarLayout.setHorizontalGroup(
            pnl_deshabilitarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_deshabilitarLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(pnl_deshabilitarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbl_deshab_selectUsuario)
                    .addGroup(pnl_deshabilitarLayout.createSequentialGroup()
                        .addComponent(pnl_deshab_deshabContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(576, 576, 576)
                        .addComponent(btn_deshabilitar, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(tb_deshab))
                .addContainerGap(71, Short.MAX_VALUE))
        );
        pnl_deshabilitarLayout.setVerticalGroup(
            pnl_deshabilitarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_deshabilitarLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(lbl_deshab_selectUsuario)
                .addGap(18, 18, 18)
                .addComponent(tb_deshab, javax.swing.GroupLayout.PREFERRED_SIZE, 386, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addGroup(pnl_deshabilitarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pnl_deshab_deshabContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_deshabilitar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tb_modUsuario_permisos.addTab("Habilitar usuarios", pnl_deshabilitar);

        pnl_actualizarPermisos.setName("Actualizar permisos"); // NOI18N

        lbl_actPermi_selectUsuario.setText("Seleccionar Usuario:");

        pnl_actPermi_rolContainer.setBorder(javax.swing.BorderFactory.createTitledBorder("Rol Usuario:"));

        bg_crear_rol.add(rb_actPermi_estandar);
        rb_actPermi_estandar.setText("Estándar");
        rb_actPermi_estandar.setNextFocusableComponent(btn_actPermi);

        bg_crear_rol.add(rb_actPermi_Admin);
        rb_actPermi_Admin.setText("Administrador");
        rb_actPermi_Admin.setNextFocusableComponent(rb_actPermi_estandar);

        javax.swing.GroupLayout pnl_actPermi_rolContainerLayout = new javax.swing.GroupLayout(pnl_actPermi_rolContainer);
        pnl_actPermi_rolContainer.setLayout(pnl_actPermi_rolContainerLayout);
        pnl_actPermi_rolContainerLayout.setHorizontalGroup(
            pnl_actPermi_rolContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_actPermi_rolContainerLayout.createSequentialGroup()
                .addComponent(rb_actPermi_Admin)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                .addComponent(rb_actPermi_estandar)
                .addContainerGap())
        );
        pnl_actPermi_rolContainerLayout.setVerticalGroup(
            pnl_actPermi_rolContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_actPermi_rolContainerLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnl_actPermi_rolContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rb_actPermi_Admin)
                    .addComponent(rb_actPermi_estandar)))
        );

        btn_actPermi.setText("Guardar Cambios");
        btn_actPermi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_actPermiActionPerformed(evt);
            }
        });

        tbl_actPermisos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo", "Nombre de Usuario", "Contraseña", "Correo", "Rol"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_actPermisos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_actPermisosMouseClicked(evt);
            }
        });
        tbl_actPermisos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbl_actPermisosKeyReleased(evt);
            }
        });
        scpnl_tbl_usuarioActPermiso.setViewportView(tbl_actPermisos);

        javax.swing.GroupLayout pnl_actualizarPermisosLayout = new javax.swing.GroupLayout(pnl_actualizarPermisos);
        pnl_actualizarPermisos.setLayout(pnl_actualizarPermisosLayout);
        pnl_actualizarPermisosLayout.setHorizontalGroup(
            pnl_actualizarPermisosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_actualizarPermisosLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(pnl_actualizarPermisosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_actPermi_selectUsuario)
                    .addGroup(pnl_actualizarPermisosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnl_actualizarPermisosLayout.createSequentialGroup()
                            .addComponent(pnl_actPermi_rolContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn_actPermi, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(scpnl_tbl_usuarioActPermiso, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 1030, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(71, Short.MAX_VALUE))
        );
        pnl_actualizarPermisosLayout.setVerticalGroup(
            pnl_actualizarPermisosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_actualizarPermisosLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(lbl_actPermi_selectUsuario)
                .addGap(35, 35, 35)
                .addComponent(scpnl_tbl_usuarioActPermiso, javax.swing.GroupLayout.PREFERRED_SIZE, 364, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addGroup(pnl_actualizarPermisosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pnl_actPermi_rolContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_actPermi, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        tb_modUsuario_permisos.addTab("Actualizar permisos", pnl_actualizarPermisos);

        pnl_actualizar.setName("Actualizar información"); // NOI18N

        lbl_actuali_nombreUsuario.setText("Nombre de Usuario:");

        txt_actuali_nombreUsuario.setNextFocusableComponent(txt_actuali_correo);

        btn_actualiUsuario.setText("Actualizar Usuario");
        btn_actualiUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_actualiUsuarioActionPerformed(evt);
            }
        });

        lbl_actuali_nombreUsuario1.setText("Correo Electrónico:");

        txt_actuali_correo.setNextFocusableComponent(btn_actualiUsuario);

        pnlActualizarClave.setBorder(javax.swing.BorderFactory.createTitledBorder("Actualización de contraseña"));

        pw_actuali_lastpass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pw_actuali_lastpassActionPerformed(evt);
            }
        });

        lbl_actuali_passActual.setText("Contraseña actual:");

        lbl_actuali_passNew.setText("Nueva contraseña:");

        lbl_actuali_confpassNew.setText("Confirmar contraseña:");

        javax.swing.GroupLayout pnlActualizarClaveLayout = new javax.swing.GroupLayout(pnlActualizarClave);
        pnlActualizarClave.setLayout(pnlActualizarClaveLayout);
        pnlActualizarClaveLayout.setHorizontalGroup(
            pnlActualizarClaveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlActualizarClaveLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlActualizarClaveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pw_actuali_lastpass, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_actuali_passActual))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlActualizarClaveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pw_actuali_newPass, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_actuali_passNew)
                    .addComponent(lbl_actuali_confpassNew)
                    .addComponent(pw_actuali_confNewPass, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(67, 67, 67))
        );
        pnlActualizarClaveLayout.setVerticalGroup(
            pnlActualizarClaveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlActualizarClaveLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(pnlActualizarClaveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlActualizarClaveLayout.createSequentialGroup()
                        .addComponent(lbl_actuali_passActual, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(pw_actuali_lastpass, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlActualizarClaveLayout.createSequentialGroup()
                        .addComponent(lbl_actuali_passNew)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pw_actuali_newPass, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addComponent(lbl_actuali_confpassNew)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pw_actuali_confNewPass, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(65, 65, 65))
        );

        btnActualiContrasenna.setText("Actualizar Contraseña");
        btnActualiContrasenna.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualiContrasennaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnl_actualizarLayout = new javax.swing.GroupLayout(pnl_actualizar);
        pnl_actualizar.setLayout(pnl_actualizarLayout);
        pnl_actualizarLayout.setHorizontalGroup(
            pnl_actualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_actualizarLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(pnl_actualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_actualizarLayout.createSequentialGroup()
                        .addComponent(filler1, javax.swing.GroupLayout.PREFERRED_SIZE, 611, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(pnl_actualizarLayout.createSequentialGroup()
                        .addGroup(pnl_actualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnActualiContrasenna, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnl_actualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(pnlActualizarClave, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(pnl_actualizarLayout.createSequentialGroup()
                                    .addGroup(pnl_actualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(pnl_actualizarLayout.createSequentialGroup()
                                            .addComponent(lbl_actuali_nombreUsuario)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_actualizarLayout.createSequentialGroup()
                                            .addComponent(txt_actuali_nombreUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(169, 169, 169)))
                                    .addGroup(pnl_actualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lbl_actuali_nombreUsuario1)
                                        .addComponent(txt_actuali_correo, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(filler3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(338, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_actualizarLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_actualiUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46))
        );
        pnl_actualizarLayout.setVerticalGroup(
            pnl_actualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_actualizarLayout.createSequentialGroup()
                .addComponent(filler1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl_actualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_actualizarLayout.createSequentialGroup()
                        .addGroup(pnl_actualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnl_actualizarLayout.createSequentialGroup()
                                .addComponent(lbl_actuali_nombreUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(6, 6, 6))
                            .addComponent(lbl_actuali_nombreUsuario1, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(18, 18, 18)
                        .addComponent(txt_actuali_nombreUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_actualizarLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(txt_actuali_correo, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(pnl_actualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_actualizarLayout.createSequentialGroup()
                        .addComponent(btnActualiContrasenna)
                        .addGap(30, 30, 30)
                        .addComponent(pnlActualizarClave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(filler3, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addComponent(btn_actualiUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(61, 61, 61))
        );

        tb_modUsuario_permisos.addTab("Actualizar información", pnl_actualizar);
        pnl_actualizar.getAccessibleContext().setAccessibleName("");

        javax.swing.GroupLayout pnl_modUsuarioLayout = new javax.swing.GroupLayout(pnl_modUsuario);
        pnl_modUsuario.setLayout(pnl_modUsuarioLayout);
        pnl_modUsuarioLayout.setHorizontalGroup(
            pnl_modUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_modUsuarioLayout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(tb_modUsuario_permisos, javax.swing.GroupLayout.DEFAULT_SIZE, 1136, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnl_modUsuarioLayout.setVerticalGroup(
            pnl_modUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_modUsuarioLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(tb_modUsuario_permisos, javax.swing.GroupLayout.PREFERRED_SIZE, 590, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnl_modUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 1194, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(36, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnl_modUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 642, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 19, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_crearUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_crearUsuarioActionPerformed

        String nombre = txt_crear_nombreUsuario.getText();
        String contra = new String(pw_crear_contra.getPassword());
        String contraConf = new String(pw_crear_confContra.getPassword());
        String correo = txt_crear_correo.getText();
        //Si el radio button rol Estándar está seleccionado
        Rol rol = rb_crear_rolEstandar.isSelected() ? Rol.Estándar : Rol.Administrador;

        if (!nombre.isEmpty()) {
            if (verificacion.validateUserName(nombre)) {
                if (!correo.isEmpty()) {
                    if (verificacion.validateEmail(correo)) {
                        if (!contra.isEmpty()) {
                            if (verificacion.validatePassword(contra)) {
                                if (contra.equals(contraConf)) {
                                    if (controlador.crearUsuario(nombre, contra, correo, rol)) {
                                        cargarTablas();
                                        sesion.setUsuario(usuarios.get(usuarios.indexOf(sesion.getUsuario())));
                                        msg.mostrarMensaje(JOptionPane.INFORMATION_MESSAGE, MessageHelper.USER_INSERTION_SUCCESS);
                                    } else {
                                        msg.mostrarMensaje(JOptionPane.ERROR_MESSAGE, MessageHelper.USER_INSERTION_FAILURE);
                                        limpiarTexto("Crear");
                                    }
                                } else {
                                    msg.mostrarMensaje(JOptionPane.WARNING_MESSAGE, MessageHelper.MISMATCHING_PASSWORD_FIELDS);
                                    pw_crear_confContra.requestFocus();
                                    pw_crear_confContra.selectAll();
                                }
                            } else {
                                msg.mostrarMensaje(JOptionPane.INFORMATION_MESSAGE, MessageHelper.PASSWORD_SYNTAX_FAILURE);
                                pw_crear_contra.requestFocus();
                                pw_crear_contra.selectAll();
                            }
                        } else {
                            msg.mostrarMensaje(JOptionPane.WARNING_MESSAGE, MessageHelper.EMPTY_PASSWORD_FIELD);
                            pw_crear_contra.requestFocus();
                        }
                    } else {
                        msg.mostrarMensaje(JOptionPane.INFORMATION_MESSAGE, MessageHelper.EMAIL_SYNTAX_FAILURE);
                        txt_actuali_correo.requestFocus();
                        txt_actuali_correo.selectAll();
                    }
                } else {
                    msg.mostrarMensaje(JOptionPane.WARNING_MESSAGE, MessageHelper.EMPTY_EMAIL_FIELD);
                    txt_crear_correo.requestFocus();
                }
                msg.mostrarMensaje(JOptionPane.INFORMATION_MESSAGE, MessageHelper.ANY_ROW_SELECTED);
            } else {
                msg.mostrarMensaje(JOptionPane.ERROR_MESSAGE, MessageHelper.CONFIRMATION_EMAIL_NOT_FOUND);
            }
        } else {
            msg.mostrarMensaje(JOptionPane.WARNING_MESSAGE, MessageHelper.EMPTY_USERNAME_FIELD);
            txt_crear_nombreUsuario.requestFocus();
        }
    }//GEN-LAST:event_btn_crearUsuarioActionPerformed

    private void btn_actualiUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_actualiUsuarioActionPerformed
        actualiUsuario(txt_actuali_nombreUsuario.getText(),
                txt_actuali_correo.getText(),
                new String(pw_actuali_lastpass.getPassword()),
                new String(pw_actuali_newPass.getPassword()),
                new String(pw_actuali_confNewPass.getPassword()));
        txt_actuali_nombreUsuario.requestFocus();
        txt_actuali_nombreUsuario.selectAll();

        cargarTablas();
        actualizarSesion(txt_actuali_nombreUsuario.getText());
        System.out.println("USUARIO SESION " + sesion.getUsuario().getNombre());

    }//GEN-LAST:event_btn_actualiUsuarioActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

    }//GEN-LAST:event_jButton1ActionPerformed

    private void btn_confUsuario_recClvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_confUsuario_recClvActionPerformed

    }//GEN-LAST:event_btn_confUsuario_recClvActionPerformed

    private void btn_enviarConf_recClvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_enviarConf_recClvActionPerformed

    }//GEN-LAST:event_btn_enviarConf_recClvActionPerformed

    private void btn_codigoConf_recClvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_codigoConf_recClvActionPerformed

    }//GEN-LAST:event_btn_codigoConf_recClvActionPerformed

    private void btn_nuevaClave_recClvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_nuevaClave_recClvActionPerformed

    }//GEN-LAST:event_btn_nuevaClave_recClvActionPerformed

    private void tbl_usuarioListadoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_usuarioListadoMouseClicked
        model = (DefaultTableModel) tbl_usuarioListado.getModel();
        int selectedRowIndex = tbl_usuarioListado.getSelectedRow();
        String codigo = String.valueOf(model.getValueAt(selectedRowIndex, 0).toString());

        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getCodigo().equals(codigo)) { //Si el codigo coincide
                txt_listado_buscar.setText(usuarios.get(i).getNombre());
            }
        }
    }//GEN-LAST:event_tbl_usuarioListadoMouseClicked

    private void tbl_deshabilitarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_deshabilitarMouseClicked
        try {
            model = (DefaultTableModel) tbl_deshabilitar.getModel();
            int selectedRowIndex = tbl_deshabilitar.getSelectedRow();
            String codigo = String.valueOf(model.getValueAt(selectedRowIndex, 0).toString());

            for (int i = 0; i < usuarios.size(); i++) {
                if (usuarios.get(i).getCodigo().equals(codigo)) { //Si el codigo coincide                    
                    if (usuarios.get(i).getEstado().equals("A")) { //Verifica el tipo de estado
                        rb_deshab_deshabilitar.setSelected(true);
                    } else {
                        rb_deshab_deshabilitar.setSelected(true);
                    }
                }
            }
        } catch (Exception ex) {

        }
    }//GEN-LAST:event_tbl_deshabilitarMouseClicked
    //SELECCIONAR Y MOSTRAR INFO EN PANTALLA.
    private void tbl_actPermisosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_actPermisosMouseClicked

        try {
            model = (DefaultTableModel) tbl_actPermisos.getModel();
            int selectedRowIndex = tbl_actPermisos.getSelectedRow();
            String codigo = String.valueOf(model.getValueAt(selectedRowIndex, 0).toString());

            for (int i = 0; i < usuarios.size(); i++) {
                if (usuarios.get(i).getCodigo().equals(codigo)) { //Si el codigo coincide
                    if (usuarios.get(i).getCodRol().equals("1")) { //Verifica el tipo de permiso
                        rb_actPermi_Admin.setSelected(true);
                    } else {
                        rb_actPermi_estandar.setSelected(true);
                    }
                }
            }
        } catch (Exception ex) {

        }
    }//GEN-LAST:event_tbl_actPermisosMouseClicked

    private void btn_actPermiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_actPermiActionPerformed
        try {
            //--------------------------------------------------------------------COMO MANTENER INFO DE ROL SI NO SE PUEDE PASAR A STRING
            model = (DefaultTableModel) tbl_actPermisos.getModel();
            int selectedRowIndex = tbl_actPermisos.getSelectedRow();
            String codigo = String.valueOf(model.getValueAt(selectedRowIndex, 0));
            Rol rol = rb_actPermi_Admin.isSelected() ? Rol.Administrador : Rol.Estándar;

            controlador.actualizarUsuario(String.valueOf(model.getValueAt(selectedRowIndex, 1)),
                    String.valueOf(model.getValueAt(selectedRowIndex, 2)),
                    String.valueOf(model.getValueAt(selectedRowIndex, 3)),
                    rol, Estado.Activo, codigo);
            cargarTablas();
        } catch (Exception e) {
            msg.mostrarMensaje(JOptionPane.INFORMATION_MESSAGE, MessageHelper.ANY_ROW_SELECTED);
        }
    }//GEN-LAST:event_btn_actPermiActionPerformed

    private void tbl_usuarioListadoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbl_usuarioListadoKeyReleased
        try {
            if (evt.getKeyCode() == 38 || evt.getKeyCode() == 40) {
                model = (DefaultTableModel) tbl_usuarioListado.getModel();
                int selectedRowIndex = tbl_usuarioListado.getSelectedRow();
                String codigo = String.valueOf(model.getValueAt(selectedRowIndex, 0).toString());

                for (int i = 0; i < usuarios.size(); i++) {
                    if (usuarios.get(i).getCodigo().equals(codigo)) { //Si el codigo coincide
                        txt_listado_buscar.setText(usuarios.get(i).getNombre());
                    }
                }
            }
        } catch (Exception ex) {

        }
    }//GEN-LAST:event_tbl_usuarioListadoKeyReleased

    private void btn_deshabilitarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deshabilitarActionPerformed
        try {
            //--------------------------------------------------------------------COMO MANTENER INFO DE ROL SI NO SE PUEDE PASAR A STRING
            model = (DefaultTableModel) tbl_deshabilitar.getModel();
            int selectedRowIndex = tbl_deshabilitar.getSelectedRow();
            String codigo = String.valueOf(model.getValueAt(selectedRowIndex, 0));
            Estado estado = rb_deshab_habilitar.isSelected() ? Estado.Activo : Estado.Deshabilitado;

            controlador.actualizarUsuario(String.valueOf(model.getValueAt(selectedRowIndex, 1)),
                    String.valueOf(model.getValueAt(selectedRowIndex, 2)),
                    String.valueOf(model.getValueAt(selectedRowIndex, 3)),
                    Rol.Administrador, estado, codigo);
            cargarTablas();
        } catch (Exception e) {
            msg.mostrarMensaje(JOptionPane.INFORMATION_MESSAGE, MessageHelper.ANY_ROW_SELECTED);
        }
    }//GEN-LAST:event_btn_deshabilitarActionPerformed

    private void tbl_actualizarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_actualizarMouseClicked

    }//GEN-LAST:event_tbl_actualizarMouseClicked

    private void tbl_actualizarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbl_actualizarKeyReleased

    }//GEN-LAST:event_tbl_actualizarKeyReleased

    private void tbl_actPermisosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbl_actPermisosKeyReleased
        try {
            if (evt.getKeyCode() == 38 || evt.getKeyCode() == 40) {
                model = (DefaultTableModel) tbl_actPermisos.getModel();
                int selectedRowIndex = tbl_actPermisos.getSelectedRow();
                String codigo = String.valueOf(model.getValueAt(selectedRowIndex, 0).toString());

                for (int i = 0; i < usuarios.size(); i++) {
                    if (usuarios.get(i).getCodigo().equals(codigo)) { //Si el codigo coincide
                        if (usuarios.get(i).getCodRol().equals("1")) { //Verifica el tipo de permiso
                            System.out.println(usuarios.get(i).getNombre());
                            rb_actPermi_Admin.setSelected(true);
                        } else {
                            rb_actPermi_estandar.setSelected(true);
                        }
                    }
                }
            }
        } catch (Exception ex) {

        }
    }//GEN-LAST:event_tbl_actPermisosKeyReleased

    private void tbl_deshabilitarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbl_deshabilitarKeyReleased
        try {
            if (evt.getKeyCode() == 38 || evt.getKeyCode() == 40) {
                model = (DefaultTableModel) tbl_deshabilitar.getModel();
                int selectedRowIndex = tbl_deshabilitar.getSelectedRow();
                String codigo = String.valueOf(model.getValueAt(selectedRowIndex, 0).toString());

                for (int i = 0; i < usuarios.size(); i++) {
                    if (usuarios.get(i).getCodigo().equals(codigo)) { //Si el codigo coincide                                                
                        if (usuarios.get(i).getEstado().equals(Estado.Activo)) { //Verifica el tipo de estado
                            rb_deshab_deshabilitar.setSelected(true);
                        } else {
                            rb_deshab_deshabilitar.setSelected(true);
                        }
                    }
                }
            }
        } catch (Exception ex) {

        }
    }//GEN-LAST:event_tbl_deshabilitarKeyReleased

    private void tbl_habilitarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_habilitarMouseClicked
        try {
            model = (DefaultTableModel) tbl_habilitar.getModel();
            int selectedRowIndex = tbl_habilitar.getSelectedRow();
            String codigo = String.valueOf(model.getValueAt(selectedRowIndex, 0).toString());

            for (int i = 0; i < usuarios.size(); i++) {
                if (usuarios.get(i).getCodigo().equals(codigo)) { //Si el codigo coincide
                    if (usuarios.get(i).getEstado().equals(Estado.Activo)) { //Verifica el tipo de estado

                        rb_deshab_habilitar.setSelected(true);
                    } else {
                        rb_deshab_habilitar.setSelected(true);
                    }
                }
            }
        } catch (Exception ex) {

        }
    }//GEN-LAST:event_tbl_habilitarMouseClicked

    private void tbl_habilitarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbl_habilitarKeyReleased
        try {
            if (evt.getKeyCode() == 38 || evt.getKeyCode() == 40) {
                model = (DefaultTableModel) tbl_habilitar.getModel();
                int selectedRowIndex = tbl_habilitar.getSelectedRow();
                String codigo = String.valueOf(model.getValueAt(selectedRowIndex, 0).toString());

                for (int i = 0; i < usuarios.size(); i++) {
                    if (usuarios.get(i).getCodigo().equals(codigo)) { //Si el codigo coincide
                        if (usuarios.get(i).getEstado().equals(Estado.Activo)) { //Verifica el tipo de estado                            
                            rb_deshab_habilitar.setSelected(true);
                        } else {
                            rb_deshab_habilitar.setSelected(true);
                        }
                    }
                }
            }
        } catch (Exception ex) {

        }
    }//GEN-LAST:event_tbl_habilitarKeyReleased

    private void pnl_listadoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_pnl_listadoFocusGained

    }//GEN-LAST:event_pnl_listadoFocusGained

    private void pnl_listadoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnl_listadoMouseEntered

    }//GEN-LAST:event_pnl_listadoMouseEntered

    private void pnl_listadoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnl_listadoMouseClicked

    }//GEN-LAST:event_pnl_listadoMouseClicked

    private void btnActualiContrasennaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualiContrasennaActionPerformed
        btnActualiContrasenna.setVisible(false);
        pnlActualizarClave.setVisible(true);
        pw_actuali_lastpass.requestFocus();
        pw_actuali_lastpass.selectAll();
    }//GEN-LAST:event_btnActualiContrasennaActionPerformed

    private void pw_actuali_lastpassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pw_actuali_lastpassActionPerformed

    }//GEN-LAST:event_pw_actuali_lastpassActionPerformed

    private void txt_listado_buscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_listado_buscarKeyReleased
        usuarios = controlador.consultarUsuarios(txt_listado_buscar.getText().trim());
        cargarUsuariosJTable(tbl_usuarioListado, true);
    }//GEN-LAST:event_txt_listado_buscarKeyReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bg_crear_rol;
    private javax.swing.JButton btnActualiContrasenna;
    private javax.swing.JButton btn_actPermi;
    private javax.swing.JButton btn_actualiUsuario;
    private javax.swing.JButton btn_crearUsuario;
    private javax.swing.JButton btn_deshabilitar;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler3;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel lbl_actPermi_selectUsuario;
    private javax.swing.JLabel lbl_actuali_confpassNew;
    private javax.swing.JLabel lbl_actuali_nombreUsuario;
    private javax.swing.JLabel lbl_actuali_nombreUsuario1;
    private javax.swing.JLabel lbl_actuali_passActual;
    private javax.swing.JLabel lbl_actuali_passNew;
    private javax.swing.JLabel lbl_crear_confirmPassw;
    private javax.swing.JLabel lbl_crear_correo;
    private javax.swing.JLabel lbl_crear_nombreUsuario;
    private javax.swing.JLabel lbl_crear_password;
    private javax.swing.JLabel lbl_deshab_selectUsuario;
    private javax.swing.JLabel lbl_listado_buscarUsuario;
    private javax.swing.JPanel pnlActualizarClave;
    private javax.swing.JPanel pnl_actPermi_rolContainer;
    private javax.swing.JPanel pnl_actualizar;
    private javax.swing.JPanel pnl_actualizarPermisos;
    private javax.swing.JPanel pnl_crear;
    private javax.swing.JPanel pnl_crear_rolContainer;
    private javax.swing.JPanel pnl_deshab_deshabContainer;
    private javax.swing.JPanel pnl_deshabilitar;
    private javax.swing.JPanel pnl_listado;
    private javax.swing.JPanel pnl_modUsuario;
    private javax.swing.JPasswordField pw_actuali_confNewPass;
    private javax.swing.JPasswordField pw_actuali_lastpass;
    private javax.swing.JPasswordField pw_actuali_newPass;
    private javax.swing.JPasswordField pw_crear_confContra;
    private javax.swing.JPasswordField pw_crear_contra;
    private javax.swing.JRadioButton rb_actPermi_Admin;
    private javax.swing.JRadioButton rb_actPermi_estandar;
    private javax.swing.JRadioButton rb_crear_rolAdmin;
    private javax.swing.JRadioButton rb_crear_rolEstandar;
    private javax.swing.JRadioButton rb_deshab_deshabilitar;
    private javax.swing.JRadioButton rb_deshab_habilitar;
    private javax.swing.JScrollPane scpnl_tbl_usuarioActPermiso;
    private javax.swing.JScrollPane scpnl_tbl_usuarioCreado;
    private javax.swing.JScrollPane scpnl_tbl_usuarioDeshab;
    private javax.swing.JScrollPane scpnl_tbl_usuarioHabilitar;
    private javax.swing.JScrollPane scpnl_tbl_usuarioListado;
    private javax.swing.JTabbedPane tb_deshab;
    private javax.swing.JTabbedPane tb_modUsuario_permisos;
    private javax.swing.JTable tbl_actPermisos;
    private javax.swing.JTable tbl_deshabilitar;
    private javax.swing.JTable tbl_habilitar;
    private javax.swing.JTable tbl_usuarioCreado;
    private javax.swing.JTable tbl_usuarioListado;
    private javax.swing.JTextField txt_actuali_correo;
    private javax.swing.JTextField txt_actuali_nombreUsuario;
    private javax.swing.JTextField txt_crear_correo;
    private javax.swing.JTextField txt_crear_nombreUsuario;
    private javax.swing.JTextField txt_listado_buscar;
    // End of variables declaration//GEN-END:variables
}
