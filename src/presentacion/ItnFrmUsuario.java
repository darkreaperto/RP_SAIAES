/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacion;

import logica.servicios.Mensaje;
import util.Estado;
import logica.servicios.AESEncrypt;
import controladores.CtrAcceso;
import controladores.CtrUsuario;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import logica.negocio.Usuario;
import util.TipoMensaje;
import logica.servicios.Regex;
import util.Rol;

/**
 * Inicializa la ventana que contiene la información de los usuarios.
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
    private final Regex verificacion;

    /**
     * Instancia un nuevo formulario interno de usuario.
     *
     * @param sesionAcc Usuario en sesión actual 
     * @param usuarios Lista con los usuarios en la base de datos
     */
    protected ItnFrmUsuario(CtrAcceso sesionAcc, ArrayList<Usuario> usuarios) {
        initComponents();
        //Inicializar variables
        controlador = CtrUsuario.getInstancia();
        crypter = new AESEncrypt();
        crypter.addKey("SAI");
        msg = new Mensaje();
        verificacion = new Regex();
        ItnFrmUsuario.usuarios = usuarios;
        ItnFrmUsuario.sesion = sesionAcc;
        cargarTablas();
        pnlActualizarClave.setVisible(false);
    }

    /**
     * Retorna la única instancia de la clase.
     *
     * @param sesionAcc Usuario en sesión actual.
     * @param usuarios Lista de usuarios en la base de datos.
     * @return instancia.
     */
    public static ItnFrmUsuario getInstancia(CtrAcceso sesionAcc,
            ArrayList<Usuario> usuarios) {
        if (instancia == null) {
            instancia = new ItnFrmUsuario(sesionAcc, usuarios);
        }
        return instancia;
    }

    /**
     * Llena las tablas del modulo con los usuarios.
     */
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

    /**
     * Limpia los campos de texto del panel, según el nombre del botón que se
     * presiona.
     *
     * @param boton presionado
     */
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

    /**
     * Crea un nuevo usuario con la información enviada por parámetro.
     *
     * @param nombre Nombre del nuevo usuario
     * @param contra Contraseña del nuevo usuario
     * @param contraConf Confirmación de contraseña del nuevo usuario
     * @param correo Correo del nuevo usuario
     * @param rol Rol del nuevo usuario
     */
    public void crearUsuario(String nombre, String contra, String contraConf,
            String correo, Rol rol) {

        if (!nombre.isEmpty()) {
            if (verificacion.validaNombreUsuario(nombre)) {
                if (!correo.isEmpty()) {
                    if (verificacion.validaEmail(correo)) {
                        if (!contra.isEmpty()) {
                            if (verificacion.validaClave(contra)) {
                                if (contra.equals(contraConf)) {
                                    contra = crypter.encriptar(contra);
                                    boolean crear = 
                                            controlador.crearUsuario(nombre, 
                                                    contra, correo, rol);
                                    System.out.println(crear);
                                    if (crear) {
                                        cargarTablas();
//                                        sesion.setUsuario(usuarios.get(
//                                                usuarios.indexOf(
//                                                        sesion.getUsuario())));
                                        msg.mostrarMensaje(JOptionPane.INFORMATION_MESSAGE,
                                                TipoMensaje.USER_INSERTION_SUCCESS);
                                    } else {
                                        msg.mostrarMensaje(JOptionPane.ERROR_MESSAGE,
                                                TipoMensaje.USER_INSERTION_FAILURE);
                                        limpiarTexto("Crear");
                                    }
                                } else {
                                    msg.mostrarMensaje(JOptionPane.WARNING_MESSAGE,
                                            TipoMensaje.MISMATCHING_PASSWORD_FIELDS);
                                    pw_crear_confContra.requestFocus();
                                    pw_crear_confContra.selectAll();
                                }
                            } else {
                                msg.mostrarMensaje(JOptionPane.INFORMATION_MESSAGE,
                                        TipoMensaje.PASSWORD_SYNTAX_FAILURE);
                                pw_crear_contra.requestFocus();
                                pw_crear_contra.selectAll();
                            }
                        } else {
                            msg.mostrarMensaje(JOptionPane.WARNING_MESSAGE,
                                    TipoMensaje.EMPTY_PASSWORD_FIELD);
                            pw_crear_contra.requestFocus();
                        }
                    } else {
                        msg.mostrarMensaje(JOptionPane.INFORMATION_MESSAGE,
                                TipoMensaje.EMAIL_SYNTAX_FAILURE);
                        txt_actuali_correo.requestFocus();
                        txt_actuali_correo.selectAll();
                    }
                } else {
                    msg.mostrarMensaje(JOptionPane.WARNING_MESSAGE,
                            TipoMensaje.EMPTY_EMAIL_FIELD);
                    txt_crear_correo.requestFocus();
                }
            } else {
                msg.mostrarMensaje(JOptionPane.ERROR_MESSAGE,
                        TipoMensaje.USERNAME_SYNTAX_FAILURE);
            }
        } else {
            msg.mostrarMensaje(JOptionPane.WARNING_MESSAGE,
                    TipoMensaje.EMPTY_USERNAME_FIELD);
            txt_crear_nombreUsuario.requestFocus();
        }
    }

    /**
     * Actualiza la información del usuario en sesión.
     *
     * @param nombreUsuario nuevo nombre de usuario
     * @param correo Nuevo correo del usuario
     * @param clave Clave actual del usuario
     * @param nuevaClave Nueva clave de usuario
     * @param nuevaClaveConf Confirmación de nueva clave de usuario
     */
    public void actualizarUsuario(String nombreUsuario, String correo,
            String clave, String nuevaClave, String nuevaClaveConf) {

        if (!nombreUsuario.isEmpty() && !correo.isEmpty()) {
            if (pnlActualizarClave.isVisible()) {
                if (!clave.isEmpty() && !nuevaClave.isEmpty()
                        && !nuevaClaveConf.isEmpty()) {
                    if (sesion.compararClave(sesion.getUsuario().getNombre(),
                            clave)) {
                        if (verificacion.validaEmail(correo)) {
                            if (verificacion.validaClave(nuevaClave)) {
                                if (nuevaClave.equals(nuevaClaveConf)) {
                                    nuevaClave = crypter.encriptar(nuevaClave);
                                    controlador.actualizarUsuario(nombreUsuario,
                                            nuevaClave, correo,
                                            sesion.getUsuario().getRol(),
                                            sesion.getUsuario().getEstado(),
                                            sesion.getUsuario().getCodigo());

                                    msg.mostrarMensaje(JOptionPane.INFORMATION_MESSAGE,
                                            TipoMensaje.USER_UPDATE_SUCCESS);
                                } else {
                                    msg.mostrarMensaje(JOptionPane.ERROR_MESSAGE,
                                            TipoMensaje.MISMATCHING_PASSWORD_FIELDS);
                                }
                            } else {
                                msg.mostrarMensaje(JOptionPane.ERROR_MESSAGE,
                                        TipoMensaje.PASSWORD_SYNTAX_FAILURE);
                            }
                        } else {
                            msg.mostrarMensaje(JOptionPane.ERROR_MESSAGE,
                                    TipoMensaje.EMAIL_SYNTAX_FAILURE);
                        }
                    } else {
                        msg.mostrarMensaje(JOptionPane.ERROR_MESSAGE,
                                TipoMensaje.USER_UPDATE_FAILURE);
                    }
                } //comprobar contraseña y nombre de usuario

            } else {
                if (verificacion.validaEmail(correo)) {
                    controlador.actualizarUsuario(nombreUsuario,
                            sesion.getUsuario().getContrasenna(),
                            correo, sesion.getUsuario().getRol(),
                            sesion.getUsuario().getEstado(),
                            sesion.getUsuario().getCodigo());
                    msg.mostrarMensaje(JOptionPane.INFORMATION_MESSAGE,
                            TipoMensaje.USER_UPDATE_SUCCESS);
                } else {
                    msg.mostrarMensaje(JOptionPane.ERROR_MESSAGE,
                            TipoMensaje.EMAIL_SYNTAX_FAILURE);
                }
            }
        } else {
            msg.mostrarMensaje(JOptionPane.ERROR_MESSAGE,
                    TipoMensaje.USER_ACCESS_FAILURE);
        }
    }

    /**
     * Actualiza la variable estática de sesión.
     *
     * @param usuario nuevo usuario en sesión
     */
    public void actualizarSesion(String usuario) {
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getNombre().equals(usuario)) {
                sesion.setUsuario(usuarios.get(i));
            }
        }
    }

    /**
     * Cargar la tabla (modelo) con los usuarios existentes.
     *
     * @param tabla Nombre de la tabla a llenar
     * @param estado Estado del usuario a incresar
     */
    public void cargarUsuariosJTable(JTable tabla, boolean estado) {
        Object[] row = new Object[5];
        model = (DefaultTableModel) tabla.getModel();
        model.setRowCount(0);
        for (int i = 0; i < usuarios.size(); i++) {

            if (usuarios.get(i).getEstado().equals(Estado.Activo) && estado) {
                //row[0] = usuarios.get(i).getCodigo();
                row[0] = usuarios.get(i).getNombre();
                //row[2] = usuarios.get(i).getContrasenna();
                row[1] = usuarios.get(i).getCorreo();
                row[2] = usuarios.get(i).getDescRol();
                //row[5] = lista.get(i).getEstado();
                model.addRow(row);
            }
            if (usuarios.get(i).getEstado().equals(Estado.Deshabilitado) && !estado) {
                //row[0] = usuarios.get(i).getCodigo();
                row[0] = usuarios.get(i).getNombre();
                //row[2] = usuarios.get(i).getContrasenna();
                row[1] = usuarios.get(i).getCorreo();
                row[2] = usuarios.get(i).getDescRol();
                //row[5] = lista.get(i).getEstado();
                model.addRow(row);
            }
        }
    }

    /**
     * Deshabilita o habilita los paneles del modulo de acuerdo al rol del
     * usuario en sesión.
     */
    public void deshabilitarPaneles() {

        tb_modUsuario_permisos.removeAll();
        if (sesion.getUsuario().getRol().equals(Rol.Administrador)) {
            tb_modUsuario_permisos.add(pnl_listado);
            tb_modUsuario_permisos.add(pnl_crear);
            tb_modUsuario_permisos.add(pnl_deshabilitar);
            tb_modUsuario_permisos.add(pnl_actualizarPermisos);
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
        btn_cancelarActualiUsuario = new javax.swing.JButton();
        btnActualiContrasenna = new javax.swing.JButton();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 35), new java.awt.Dimension(0, 35), new java.awt.Dimension(32767, 35));
        filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 200), new java.awt.Dimension(0, 200), new java.awt.Dimension(32767, 200));
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

        setClosable(true);
        setIconifiable(true);
        setTitle("Módulo de Usuario");
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosing(evt);
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
        });

        pnl_modUsuario.setPreferredSize(new java.awt.Dimension(1239, 680));

        tb_modUsuario_permisos.setName("Listado Usuarios"); // NOI18N
        tb_modUsuario_permisos.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tb_modUsuario_permisosStateChanged(evt);
            }
        });

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
                "Nombre de Usuario", "Correo", "Rol"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        scpnl_tbl_usuarioListado.setViewportView(tbl_usuarioListado);

        javax.swing.GroupLayout pnl_listadoLayout = new javax.swing.GroupLayout(pnl_listado);
        pnl_listado.setLayout(pnl_listadoLayout);
        pnl_listadoLayout.setHorizontalGroup(
            pnl_listadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(pnl_listadoLayout.createSequentialGroup()
                .addContainerGap(27, Short.MAX_VALUE)
                .addGroup(pnl_listadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(scpnl_tbl_usuarioListado, javax.swing.GroupLayout.PREFERRED_SIZE, 1134, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnl_listadoLayout.createSequentialGroup()
                        .addComponent(lbl_listado_buscarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_listado_buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 995, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(27, 27, 27))
        );
        pnl_listadoLayout.setVerticalGroup(
            pnl_listadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_listadoLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(pnl_listadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_listado_buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_listado_buscarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addComponent(scpnl_tbl_usuarioListado, javax.swing.GroupLayout.PREFERRED_SIZE, 472, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
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
        rb_crear_rolEstandar.setSelected(true);
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
                .addContainerGap()
                .addComponent(rb_crear_rolAdmin)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                .addComponent(rb_crear_rolEstandar)
                .addContainerGap())
        );
        pnl_crear_rolContainerLayout.setVerticalGroup(
            pnl_crear_rolContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_crear_rolContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_crear_rolContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rb_crear_rolEstandar)
                    .addComponent(rb_crear_rolAdmin))
                .addGap(0, 19, Short.MAX_VALUE))
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
                "Nombre de Usuario", "Correo", "Rol"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
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
            .addGroup(pnl_crearLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(pnl_crearLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(scpnl_tbl_usuarioCreado)
                    .addGroup(pnl_crearLayout.createSequentialGroup()
                        .addGroup(pnl_crearLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_crear_nombreUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_crear_correo, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_crear_nombreUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_crear_correo, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(96, 96, 96)
                        .addGroup(pnl_crearLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pw_crear_contra, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pw_crear_confContra, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_crear_password, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_crear_confirmPassw, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 113, Short.MAX_VALUE)
                        .addGroup(pnl_crearLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pnl_crear_rolContainer, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_crearUsuario, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(25, 25, 25))
        );
        pnl_crearLayout.setVerticalGroup(
            pnl_crearLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_crearLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(pnl_crearLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnl_crearLayout.createSequentialGroup()
                        .addComponent(pnl_crear_rolContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(43, 43, 43)
                        .addComponent(btn_crearUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnl_crearLayout.createSequentialGroup()
                        .addGroup(pnl_crearLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_crear_nombreUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_crear_password, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnl_crearLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pw_crear_contra, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_crear_nombreUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pnl_crearLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_crear_confirmPassw, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_crear_correo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnl_crearLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(pw_crear_confContra, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_crear_correo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(50, 50, 50)
                .addComponent(scpnl_tbl_usuarioCreado, javax.swing.GroupLayout.DEFAULT_SIZE, 326, Short.MAX_VALUE)
                .addContainerGap())
        );

        tb_modUsuario_permisos.addTab("Agregar usuario", pnl_crear);

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

        btn_cancelarActualiUsuario.setText("Cancelar");
        btn_cancelarActualiUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cancelarActualiUsuarioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlActualizarClaveLayout = new javax.swing.GroupLayout(pnlActualizarClave);
        pnlActualizarClave.setLayout(pnlActualizarClaveLayout);
        pnlActualizarClaveLayout.setHorizontalGroup(
            pnlActualizarClaveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlActualizarClaveLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(pnlActualizarClaveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btn_cancelarActualiUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlActualizarClaveLayout.createSequentialGroup()
                        .addGroup(pnlActualizarClaveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pw_actuali_lastpass, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_actuali_passActual))
                        .addGap(33, 33, 33)
                        .addGroup(pnlActualizarClaveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_actuali_confpassNew)
                            .addComponent(pw_actuali_newPass, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pw_actuali_confNewPass, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_actuali_passNew))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlActualizarClaveLayout.setVerticalGroup(
            pnlActualizarClaveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlActualizarClaveLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(pnlActualizarClaveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_actuali_passActual, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_actuali_passNew, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlActualizarClaveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pw_actuali_newPass, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(pw_actuali_lastpass))
                .addGap(18, 18, 18)
                .addComponent(lbl_actuali_confpassNew, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pw_actuali_confNewPass, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_cancelarActualiUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7))
        );

        btnActualiContrasenna.setForeground(javax.swing.UIManager.getDefaults().getColor("Button.darkShadow"));
        btnActualiContrasenna.setText("Actualizar Contraseña");
        btnActualiContrasenna.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, javax.swing.UIManager.getDefaults().getColor("Button.darkShadow")));
        btnActualiContrasenna.setContentAreaFilled(false);
        btnActualiContrasenna.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
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
                .addContainerGap()
                .addGroup(pnl_actualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_actualizarLayout.createSequentialGroup()
                        .addGroup(pnl_actualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnActualiContrasenna, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnl_actualizarLayout.createSequentialGroup()
                                .addComponent(lbl_actuali_nombreUsuario)
                                .addGap(254, 254, 254)
                                .addGroup(pnl_actualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_actuali_correo, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbl_actuali_nombreUsuario1)))
                            .addComponent(pnlActualizarClave, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(35, 35, 35)
                        .addComponent(filler3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(452, Short.MAX_VALUE))
                    .addGroup(pnl_actualizarLayout.createSequentialGroup()
                        .addGroup(pnl_actualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(filler1, javax.swing.GroupLayout.PREFERRED_SIZE, 611, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_actuali_nombreUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_actualizarLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_actualiUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
        );
        pnl_actualizarLayout.setVerticalGroup(
            pnl_actualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_actualizarLayout.createSequentialGroup()
                .addComponent(filler1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl_actualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_actualizarLayout.createSequentialGroup()
                        .addComponent(lbl_actuali_nombreUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnl_actualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_actuali_nombreUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_actuali_correo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(pnl_actualizarLayout.createSequentialGroup()
                        .addComponent(lbl_actuali_nombreUsuario1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(33, 33, 33)))
                .addGroup(pnl_actualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_actualizarLayout.createSequentialGroup()
                        .addComponent(btnActualiContrasenna, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnlActualizarClave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(filler3, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addComponent(btn_actualiUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(77, 77, 77))
        );

        tb_modUsuario_permisos.addTab("Actualizar información", pnl_actualizar);
        pnl_actualizar.getAccessibleContext().setAccessibleName("");

        pnl_deshabilitar.setName("Habilitar usuarios"); // NOI18N

        lbl_deshab_selectUsuario.setText("Seleccionar Usuario:");

        pnl_deshab_deshabContainer.setBorder(javax.swing.BorderFactory.createTitledBorder("Activo:"));

        bg_crear_rol.add(rb_deshab_deshabilitar);
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
                "Nombre de Usuario", "Correo", "Rol"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
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
                "Nombre de Usuario", "Correo", "Rol"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
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
                .addContainerGap()
                .addGroup(pnl_deshabilitarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_deshabilitarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnl_deshabilitarLayout.createSequentialGroup()
                            .addComponent(pnl_deshab_deshabContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn_deshabilitar, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(tb_deshab, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 1110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lbl_deshab_selectUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(66, Short.MAX_VALUE))
        );
        pnl_deshabilitarLayout.setVerticalGroup(
            pnl_deshabilitarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_deshabilitarLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(lbl_deshab_selectUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tb_deshab, javax.swing.GroupLayout.DEFAULT_SIZE, 419, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(pnl_deshabilitarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnl_deshab_deshabContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnl_deshabilitarLayout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(btn_deshabilitar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
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
                "Nombre de Usuario", "Correo", "Rol"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
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
                .addContainerGap()
                .addGroup(pnl_actualizarPermisosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_actPermi_selectUsuario)
                    .addGroup(pnl_actualizarPermisosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnl_actualizarPermisosLayout.createSequentialGroup()
                            .addComponent(pnl_actPermi_rolContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn_actPermi, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(scpnl_tbl_usuarioActPermiso, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 1095, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(81, Short.MAX_VALUE))
        );
        pnl_actualizarPermisosLayout.setVerticalGroup(
            pnl_actualizarPermisosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_actualizarPermisosLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(lbl_actPermi_selectUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(scpnl_tbl_usuarioActPermiso, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addGroup(pnl_actualizarPermisosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnl_actPermi_rolContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_actPermi, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        tb_modUsuario_permisos.addTab("Actualizar permisos", pnl_actualizarPermisos);

        javax.swing.GroupLayout pnl_modUsuarioLayout = new javax.swing.GroupLayout(pnl_modUsuario);
        pnl_modUsuario.setLayout(pnl_modUsuarioLayout);
        pnl_modUsuarioLayout.setHorizontalGroup(
            pnl_modUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_modUsuarioLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(tb_modUsuario_permisos)
                .addContainerGap())
        );
        pnl_modUsuarioLayout.setVerticalGroup(
            pnl_modUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_modUsuarioLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(tb_modUsuario_permisos, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnl_modUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, 1230, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnl_modUsuario, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 641, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_crearUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_crearUsuarioActionPerformed
        //Si el radio button rol Estándar está seleccionado
        Rol rol = rb_crear_rolAdmin.isSelected() ? Rol.Administrador : Rol.Estándar;

        crearUsuario(txt_crear_nombreUsuario.getText(),
                new String(pw_crear_contra.getPassword()),
                new String(pw_crear_confContra.getPassword()),
                txt_crear_correo.getText(), rol);
        txt_crear_nombreUsuario.requestFocus();
        txt_crear_nombreUsuario.selectAll();
    }//GEN-LAST:event_btn_crearUsuarioActionPerformed

    private void btn_actualiUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_actualiUsuarioActionPerformed
        actualizarUsuario(txt_actuali_nombreUsuario.getText(),
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

    private void btn_confUsuario_recClvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_confUsuario_recClvActionPerformed

    }//GEN-LAST:event_btn_confUsuario_recClvActionPerformed

    private void btn_enviarConf_recClvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_enviarConf_recClvActionPerformed

    }//GEN-LAST:event_btn_enviarConf_recClvActionPerformed

    private void btn_codigoConf_recClvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_codigoConf_recClvActionPerformed

    }//GEN-LAST:event_btn_codigoConf_recClvActionPerformed

    private void btn_nuevaClave_recClvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_nuevaClave_recClvActionPerformed

    }//GEN-LAST:event_btn_nuevaClave_recClvActionPerformed

    private void tbl_deshabilitarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_deshabilitarMouseClicked
        try {
            model = (DefaultTableModel) tbl_deshabilitar.getModel();
            int selectedRowIndex = tbl_deshabilitar.getSelectedRow();
            String nombre
                    = String.valueOf(model.getValueAt(selectedRowIndex, 0).toString());

            for (int i = 0; i < usuarios.size(); i++) {
                if (usuarios.get(i).getNombre().equals(nombre)) {
                    //Si el codigo coincide                    
                    if (usuarios.get(i).getEstado().equals(Estado.Activo)) {
                        //Verifica el tipo de estado
                        rb_deshab_deshabilitar.setSelected(true);
                    } else {
                        rb_deshab_habilitar.setSelected(true);
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
            String nombre = String.valueOf(model.getValueAt(selectedRowIndex, 0).toString());

            for (int i = 0; i < usuarios.size(); i++) {
                if (usuarios.get(i).getNombre().equals(nombre)) {
                    //Si el codigo coincide
                    if (usuarios.get(i).getCodRol().equals("1")) {
                        //Verifica el tipo de permiso
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
            model = (DefaultTableModel) tbl_actPermisos.getModel();
            int selectedRowIndex = tbl_actPermisos.getSelectedRow();
            String codigo
                    = String.valueOf(model.getValueAt(selectedRowIndex, 0));
            Rol rol
                    = rb_actPermi_Admin.isSelected() ? Rol.Administrador : Rol.Estándar;

            controlador.actualizarUsuario(
                    String.valueOf(model.getValueAt(selectedRowIndex, 1)),
                    String.valueOf(model.getValueAt(selectedRowIndex, 2)),
                    String.valueOf(model.getValueAt(selectedRowIndex, 3)),
                    rol, Estado.Activo, codigo);
            //Actualizar
            cargarTablas();
        } catch (Exception e) {
            msg.mostrarMensaje(JOptionPane.INFORMATION_MESSAGE,
                    TipoMensaje.ANY_ROW_SELECTED);
        }
    }//GEN-LAST:event_btn_actPermiActionPerformed

    private void btn_deshabilitarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deshabilitarActionPerformed
        try {
            model = (DefaultTableModel) tbl_deshabilitar.getModel();
            int selectedRowIndex = tbl_deshabilitar.getSelectedRow();
            String codigo = String.valueOf(model.getValueAt(selectedRowIndex, 0));
            Estado estado
                    = rb_deshab_habilitar.isSelected() ? Estado.Activo : Estado.Deshabilitado;

            controlador.actualizarUsuario(
                    String.valueOf(model.getValueAt(selectedRowIndex, 1)),
                    String.valueOf(model.getValueAt(selectedRowIndex, 2)),
                    String.valueOf(model.getValueAt(selectedRowIndex, 3)),
                    Rol.Administrador, estado, codigo);
            //Actualizar
            cargarTablas();
        } catch (Exception e) {
            msg.mostrarMensaje(JOptionPane.INFORMATION_MESSAGE,
                    TipoMensaje.ANY_ROW_SELECTED);
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
                String nombre
                        = String.valueOf(model.getValueAt(selectedRowIndex, 0).toString());

                for (int i = 0; i < usuarios.size(); i++) {
                    if (usuarios.get(i).getNombre().equals(nombre)) {
                        //Si el codigo coincide
                        if (usuarios.get(i).getCodRol().equals("1")) {
                            //Verifica el tipo de permiso
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
                String nombre
                        = String.valueOf(model.getValueAt(selectedRowIndex, 0).toString());

                for (int i = 0; i < usuarios.size(); i++) {
                    if (usuarios.get(i).getNombre().equals(nombre)) {
                        //Si el codigo coincide                                                
                        if (usuarios.get(i).getEstado().equals(Estado.Activo)) {
                            //Verifica el tipo de estado
                            rb_deshab_deshabilitar.setSelected(true);
                        } else {
                            rb_deshab_habilitar.setSelected(true);
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
            String nombre
                    = String.valueOf(model.getValueAt(selectedRowIndex, 0).toString());

            for (int i = 0; i < usuarios.size(); i++) {
                if (usuarios.get(i).getNombre().equals(nombre)) {
                    //Si el codigo coincide
                    if (usuarios.get(i).getEstado().equals(Estado.Deshabilitado)) {
                        //Verifica el tipo de estado
                        rb_deshab_habilitar.setSelected(true);
                    } else {
                        rb_deshab_deshabilitar.setSelected(true);
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
                String nombre
                        = String.valueOf(model.getValueAt(selectedRowIndex, 0).toString());

                for (int i = 0; i < usuarios.size(); i++) {
                    if (usuarios.get(i).getNombre().equals(nombre)) {
                        if (usuarios.get(i).getEstado().equals(Estado.Deshabilitado)) {
                            rb_deshab_habilitar.setSelected(true);
                        } else {
                            rb_deshab_deshabilitar.setSelected(true);
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

    private void btn_cancelarActualiUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cancelarActualiUsuarioActionPerformed
        pnlActualizarClave.setVisible(false);
        btnActualiContrasenna.setVisible(true);
    }//GEN-LAST:event_btn_cancelarActualiUsuarioActionPerformed

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        JOptionPane.showMessageDialog(null, "Haro─!");
    }//GEN-LAST:event_formInternalFrameClosing

    private void tb_modUsuario_permisosStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tb_modUsuario_permisosStateChanged
        if (tb_modUsuario_permisos.isShowing())
            System.out.println(evt);
    }//GEN-LAST:event_tb_modUsuario_permisosStateChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bg_crear_rol;
    private javax.swing.JButton btnActualiContrasenna;
    private javax.swing.JButton btn_actPermi;
    private javax.swing.JButton btn_actualiUsuario;
    private javax.swing.JButton btn_cancelarActualiUsuario;
    private javax.swing.JButton btn_crearUsuario;
    private javax.swing.JButton btn_deshabilitar;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler3;
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
