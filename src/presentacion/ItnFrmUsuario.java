/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacion;

import bd.Conexion;
import bd.AESEncrypt;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import logica.Usuario;
import util.MessageHelper;
import util.MessageType;
import util.Rol;
import controladores.Ctr_Recover;
import controladores.Ctr_Mail;

/**
 *
 * @author ahoihanabi
 */
public class ItnFrmUsuario extends javax.swing.JInternalFrame {
    
    private static ItnFrmUsuario instancia = null;
    private static Conexion conexion;
    private static AESEncrypt crypter;
    private static Mensaje mensaje;
    private static Ctr_Recover recover;
    private static Ctr_Mail mail;
    
    private static ArrayList<Usuario> usuarios;
    
    /**
     * Creates new form intfrmUsuario
     */
    protected ItnFrmUsuario() {
        initComponents();
        
        //Inicializar variables
        conexion = Conexion.getInstancia();
        crypter = new AESEncrypt();
        crypter.addKey("SAI");
        mensaje = new Mensaje();
        
        obtenerUsuarios();
    }
    
    public static ItnFrmUsuario getInstancia() {
        if (instancia == null) {
            instancia = new ItnFrmUsuario();
        }
        return instancia;
    }
    
    public void obtenerUsuarios() {
        try {
            //Para no instanciar los usuarios sin necesidad
            usuarios = usuarios == null ? new ArrayList<>() : usuarios;
            
            String consulta = "SELECT cod_Usuarios, nombre_Usuarios, "
                            + "clave_Usuarios, correo_Usuarios, desc_RolUsuar"
                            + " FROM Usuarios u, RolUsuarios r"
                            + " WHERE r.cod_RolUsuar = u.cod_RolUsuar";
            
            conexion.abrirConexion();
            ResultSet result = conexion.ejecutarConsulta(consulta);

            String codUsuario = "", nombreUsuario = "", claveUsuario = "", 
                    correoUsuario = "", codRolUsuario = "";

            while (result.next()) {
                codUsuario = result.getString("cod_Usuarios");
                nombreUsuario = result.getString("nombre_Usuarios");
                claveUsuario = result.getString("clave_Usuarios");
                correoUsuario = result.getString("correo_Usuarios");
                codRolUsuario = result.getString("desc_RolUsuar");
                
                System.out.println("Codigo: " + codUsuario + 
                                    "\nNombre: " + nombreUsuario + 
                                    "\nClave: " + claveUsuario + 
                                    "\nRol: " + codRolUsuario);
                
                Usuario usuario = new Usuario(nombreUsuario, claveUsuario, correoUsuario);
                usuarios.add(usuario);
            }

        } catch (SQLException ex) {
            System.err.println(ex);
        }
        finally {
            conexion.cerrarConexion();
        }
    }
    
    public boolean crearUsuario(String nombre, String contra, Rol rol) {
        
        //Código de rol de usuario. 1: Administrador, 2: Estándar
        int codRol = rol.equals(Rol.Administrador) ? 1 : 2;
        contra = crypter.encriptar(contra);
        
        boolean res = false;
        try {
            String consulta = "INSERT INTO `Usuarios`(`cod_Usuarios`, "
                    + "`nombre_Usuarios`, `clave_Usuarios`, `cod_RolUsuar`) "
                    + "VALUES (NULL, '" + nombre + "', '" + contra + "', " + codRol + ")";
            
            conexion.abrirConexion();
            res = conexion.ejecutarActualizar(consulta) != -1;

        } catch (SQLException ex) {
            System.err.println(ex);
        }
        finally {
            conexion.cerrarConexion();
        }
        return res;
    }
    
    private int obtenerCorreo(String usuario) {
        int correoIndice = -1;
        
        for (Usuario u : usuarios) {
            if (u.getNombre().equals(usuario)) {
                correoIndice = usuarios.indexOf(u);
                break;
            }
        }
        
        return correoIndice;
    }
    
    private void mostrarMensaje(MessageType tipo, MessageHelper msg) {
        
        int pan;
        
        switch (tipo) {
            case INFORMATION:
                pan = JOptionPane.INFORMATION_MESSAGE;
                break;
            case WARNING:
                pan = JOptionPane.WARNING_MESSAGE;
                break;
            case ERROR:
                pan = JOptionPane.ERROR_MESSAGE;
                break;
            default:
                pan = JOptionPane.INFORMATION_MESSAGE;
                break;
        }
        
        JOptionPane.showMessageDialog(null, mensaje.obtenerMensaje(msg), tipo.toString(), pan);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        bg_crear_rol = new javax.swing.ButtonGroup();
        entityManager0 = java.beans.Beans.isDesignTime() ? null : javax.persistence.Persistence.createEntityManagerFactory("sai_aes?zeroDateTimeBehavior=convertToNullPU").createEntityManager();
        usuarios_1Query = java.beans.Beans.isDesignTime() ? null : entityManager0.createQuery("SELECT u FROM Usuarios_1 u");
        usuarios_1List = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : usuarios_1Query.getResultList();
        pnl_recuperar_clave = new javax.swing.JPanel();
        txt_correo_recClv = new javax.swing.JTextField();
        txt_usuario_recClv = new javax.swing.JTextField();
        btn_confUsuario_recClv = new javax.swing.JButton();
        txt_codigoConf_recClv = new javax.swing.JTextField();
        btn_enviarConf_recClv = new javax.swing.JButton();
        btn_codigoConf_recClv = new javax.swing.JButton();
        txt_nuevaClave_recClv = new javax.swing.JTextField();
        txt_nuevaClaveConf_recClv = new javax.swing.JTextField();
        btn_nuevaClave_recClv = new javax.swing.JButton();
        pnl_modUsuario = new javax.swing.JPanel();
        tb_modUsuario_permisos = new javax.swing.JTabbedPane();
        pnl_listado = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txt_listado_buscar = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        pnl_crear = new javax.swing.JPanel();
        txt_crear_nombreUsuario = new javax.swing.JTextField();
        lbl_crear_nombreUsuario = new javax.swing.JLabel();
        lbl_crear_nombreUsuario1 = new javax.swing.JLabel();
        lbl_crear_nombreUsuario2 = new javax.swing.JLabel();
        pw_crear_contra = new javax.swing.JPasswordField();
        pw_crear_confContra = new javax.swing.JPasswordField();
        btn_crearUsuario = new javax.swing.JButton();
        scpnl_tbl_usuarioCreado = new javax.swing.JScrollPane();
        tbl_usuarioCreado = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        rb_crear_rolEstandar = new javax.swing.JRadioButton();
        rb_crear_rolAdmin = new javax.swing.JRadioButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();

        pnl_recuperar_clave.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        pnl_recuperar_clave.setPreferredSize(new java.awt.Dimension(590, 280));

        txt_correo_recClv.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        txt_correo_recClv.setEnabled(false);

        btn_confUsuario_recClv.setText("Confirmar usuario");
        btn_confUsuario_recClv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_confUsuario_recClvActionPerformed(evt);
            }
        });

        txt_codigoConf_recClv.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        txt_codigoConf_recClv.setEnabled(false);

        btn_enviarConf_recClv.setText("Enviar correo recuperación");
        btn_enviarConf_recClv.setEnabled(false);
        btn_enviarConf_recClv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_enviarConf_recClvActionPerformed(evt);
            }
        });

        btn_codigoConf_recClv.setText("Confirmar código");
        btn_codigoConf_recClv.setEnabled(false);
        btn_codigoConf_recClv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_codigoConf_recClvActionPerformed(evt);
            }
        });

        txt_nuevaClave_recClv.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        txt_nuevaClave_recClv.setEnabled(false);

        txt_nuevaClaveConf_recClv.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        txt_nuevaClaveConf_recClv.setEnabled(false);

        btn_nuevaClave_recClv.setText("Restablecer contraseña");
        btn_nuevaClave_recClv.setEnabled(false);
        btn_nuevaClave_recClv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_nuevaClave_recClvActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnl_recuperar_claveLayout = new javax.swing.GroupLayout(pnl_recuperar_clave);
        pnl_recuperar_clave.setLayout(pnl_recuperar_claveLayout);
        pnl_recuperar_claveLayout.setHorizontalGroup(
            pnl_recuperar_claveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_recuperar_claveLayout.createSequentialGroup()
                .addGroup(pnl_recuperar_claveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnl_recuperar_claveLayout.createSequentialGroup()
                        .addGap(68, 68, 68)
                        .addGroup(pnl_recuperar_claveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txt_correo_recClv, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_usuario_recClv, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnl_recuperar_claveLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(pnl_recuperar_claveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_nuevaClaveConf_recClv, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                            .addComponent(txt_nuevaClave_recClv)
                            .addComponent(txt_codigoConf_recClv))))
                .addGap(18, 18, 18)
                .addGroup(pnl_recuperar_claveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btn_confUsuario_recClv, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_enviarConf_recClv, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                    .addComponent(btn_codigoConf_recClv, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_nuevaClave_recClv, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(102, Short.MAX_VALUE))
        );
        pnl_recuperar_claveLayout.setVerticalGroup(
            pnl_recuperar_claveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_recuperar_claveLayout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(pnl_recuperar_claveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_usuario_recClv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_confUsuario_recClv))
                .addGap(18, 18, 18)
                .addGroup(pnl_recuperar_claveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_correo_recClv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_enviarConf_recClv))
                .addGap(44, 44, 44)
                .addGroup(pnl_recuperar_claveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_codigoConf_recClv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_codigoConf_recClv))
                .addGap(18, 18, 18)
                .addComponent(txt_nuevaClave_recClv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl_recuperar_claveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_nuevaClaveConf_recClv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_nuevaClave_recClv))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        jLabel1.setText("Buscar usuario: ");

        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, usuarios_1List, jTable1);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${codRolUsuar}"));
        columnBinding.setColumnName("Cod Rol Usuar");
        columnBinding.setColumnClass(Long.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${claveUsuarios}"));
        columnBinding.setColumnName("Clave Usuarios");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${nombreUsuarios}"));
        columnBinding.setColumnName("Nombre Usuarios");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${codUsuarios}"));
        columnBinding.setColumnName("Cod Usuarios");
        columnBinding.setColumnClass(Long.class);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();

        jScrollPane1.setViewportView(jTable1);

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
                    .addComponent(jButton1)
                    .addGroup(pnl_listadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jScrollPane1)
                        .addGroup(pnl_listadoLayout.createSequentialGroup()
                            .addComponent(jLabel1)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txt_listado_buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 900, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(56, Short.MAX_VALUE))
        );
        pnl_listadoLayout.setVerticalGroup(
            pnl_listadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_listadoLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(pnl_listadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txt_listado_buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 446, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        tb_modUsuario_permisos.addTab("Listado Usuarios", pnl_listado);

        lbl_crear_nombreUsuario.setText("Nombre de Usuario:");

        lbl_crear_nombreUsuario1.setText("Contraseña:");

        lbl_crear_nombreUsuario2.setText("Confirmar Contraseña:");

        btn_crearUsuario.setText("Crear Usuario");
        btn_crearUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_crearUsuarioActionPerformed(evt);
            }
        });

        scpnl_tbl_usuarioCreado.setViewportView(tbl_usuarioCreado);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Rol de Usuarios:"));

        bg_crear_rol.add(rb_crear_rolEstandar);
        rb_crear_rolEstandar.setSelected(true);
        rb_crear_rolEstandar.setText("Estándar");

        bg_crear_rol.add(rb_crear_rolAdmin);
        rb_crear_rolAdmin.setText("Administrador");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(rb_crear_rolAdmin)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 57, Short.MAX_VALUE)
                .addComponent(rb_crear_rolEstandar)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rb_crear_rolAdmin)
                    .addComponent(rb_crear_rolEstandar)))
        );

        javax.swing.GroupLayout pnl_crearLayout = new javax.swing.GroupLayout(pnl_crear);
        pnl_crear.setLayout(pnl_crearLayout);
        pnl_crearLayout.setHorizontalGroup(
            pnl_crearLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_crearLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(pnl_crearLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(scpnl_tbl_usuarioCreado)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnl_crearLayout.createSequentialGroup()
                        .addGroup(pnl_crearLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_crear_nombreUsuario)
                            .addComponent(txt_crear_nombreUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(179, 179, 179)
                        .addGroup(pnl_crearLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnl_crearLayout.createSequentialGroup()
                                .addGroup(pnl_crearLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(pw_crear_confContra, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(pw_crear_contra, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbl_crear_nombreUsuario2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 78, Short.MAX_VALUE)
                                .addComponent(btn_crearUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnl_crearLayout.createSequentialGroup()
                                .addComponent(lbl_crear_nombreUsuario1)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addGap(72, 72, 72))
        );
        pnl_crearLayout.setVerticalGroup(
            pnl_crearLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_crearLayout.createSequentialGroup()
                .addGroup(pnl_crearLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_crearLayout.createSequentialGroup()
                        .addGap(96, 96, 96)
                        .addComponent(btn_crearUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnl_crearLayout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addGroup(pnl_crearLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_crear_nombreUsuario)
                            .addComponent(lbl_crear_nombreUsuario1))
                        .addGap(14, 14, 14)
                        .addGroup(pnl_crearLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_crear_nombreUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pw_crear_contra, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pnl_crearLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnl_crearLayout.createSequentialGroup()
                                .addComponent(lbl_crear_nombreUsuario2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(pw_crear_confContra, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(37, 37, 37)
                .addComponent(scpnl_tbl_usuarioCreado, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(39, Short.MAX_VALUE))
        );

        tb_modUsuario_permisos.addTab("Crear", pnl_crear);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1103, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 593, Short.MAX_VALUE)
        );

        tb_modUsuario_permisos.addTab("Deshabilitar", jPanel3);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1103, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 593, Short.MAX_VALUE)
        );

        tb_modUsuario_permisos.addTab("Permisos", jPanel4);

        javax.swing.GroupLayout pnl_modUsuarioLayout = new javax.swing.GroupLayout(pnl_modUsuario);
        pnl_modUsuario.setLayout(pnl_modUsuarioLayout);
        pnl_modUsuarioLayout.setHorizontalGroup(
            pnl_modUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_modUsuarioLayout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(tb_modUsuario_permisos, javax.swing.GroupLayout.PREFERRED_SIZE, 1108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(48, Short.MAX_VALUE))
        );
        pnl_modUsuarioLayout.setVerticalGroup(
            pnl_modUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_modUsuarioLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(tb_modUsuario_permisos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnl_modUsuario, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnl_modUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        bindingGroup.bind();

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_crearUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_crearUsuarioActionPerformed
        
        String nombre =  txt_crear_nombreUsuario.getText();
        String contra =  new String(pw_crear_contra.getPassword());
        String contraConf =  new String(pw_crear_confContra.getPassword());
        //Si el radio button rol Estándar está seleccionado
        Rol rol = rb_crear_rolEstandar.isSelected() ? Rol.Estándar : Rol.Administrador;
        
        if (!nombre.isEmpty()) {
            if (!contra.isEmpty()) {
                if (contra.equals(contraConf)) {
                    if (crearUsuario(nombre, contra, rol)) {
                        mostrarMensaje(MessageType.INFORMATION, MessageHelper.USER_INSERTION_SUCCESS);
                    } else {
                        mostrarMensaje(MessageType.ERROR, MessageHelper.USER_INSERTION_FAILURE);
                    }
                } else {
                    mostrarMensaje(MessageType.WARNING, MessageHelper.MISMATCHING_PASSWORD_FIELDS);
                }
            } else {
                mostrarMensaje(MessageType.WARNING, MessageHelper.EMPTY_PASSWORD_FIELD);
            }
        } else {
            mostrarMensaje(MessageType.WARNING, MessageHelper.EMPTY_USERNAME_FIELD);
        }
    }//GEN-LAST:event_btn_crearUsuarioActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        pnl_recuperar_clave.setVisible(true);
        pnl_recuperar_clave.setBounds(0, 0, 590, 280);
        pnl_modUsuario.setVisible(false);
        this.add(pnl_recuperar_clave);
        //this.pack();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btn_confUsuario_recClvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_confUsuario_recClvActionPerformed
        String usuario = txt_usuario_recClv.getText();
        
        if (!usuario.isEmpty()) {
            int correoIndice = obtenerCorreo(usuario);
            
            if (correoIndice > 0) {
                txt_correo_recClv.setText(usuarios.get(correoIndice).getCorreo());
                //Se habilita el boton de enviar correo
                btn_enviarConf_recClv.setEnabled(true);
                
            } else {
                
            }
        } else {
            mostrarMensaje(MessageType.WARNING, MessageHelper.EMPTY_USERNAME_FIELD);
        }
    }//GEN-LAST:event_btn_confUsuario_recClvActionPerformed

    private void btn_enviarConf_recClvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_enviarConf_recClvActionPerformed
        String correo = txt_correo_recClv.getText();
        
        if (!correo.isEmpty()) {
            recover = new Ctr_Recover(correo);
            mail = new Ctr_Mail();
            
            boolean enviarCorreo = mail.enviarCorreoRecuperacion(correo, recover.getCodigo());
            
            if (enviarCorreo) {
                //Habilitar campo para ingresar codigo de recuperacion
                txt_codigoConf_recClv.setEnabled(true);
                //Habilitar boton para confirmar codigo de recuperacion
                btn_codigoConf_recClv.setEnabled(true);
            } else {
                
            }
        } else {
            
        }
    }//GEN-LAST:event_btn_enviarConf_recClvActionPerformed

    private void btn_codigoConf_recClvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_codigoConf_recClvActionPerformed
        String correo = txt_correo_recClv.getText();
        String codigo = txt_codigoConf_recClv.getText();
        
        if (!codigo.isEmpty()) {
            if (recover.confirmarCodigo(correo, codigo)) {
                txt_nuevaClave_recClv.setEnabled(true);
                txt_nuevaClaveConf_recClv.setEnabled(true);
                btn_nuevaClave_recClv.setEnabled(true);
            } else {
                
            }
        } else {
            
        }
    }//GEN-LAST:event_btn_codigoConf_recClvActionPerformed

    private void btn_nuevaClave_recClvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_nuevaClave_recClvActionPerformed
        
    }//GEN-LAST:event_btn_nuevaClave_recClvActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bg_crear_rol;
    private javax.swing.JButton btn_codigoConf_recClv;
    private javax.swing.JButton btn_confUsuario_recClv;
    private javax.swing.JButton btn_crearUsuario;
    private javax.swing.JButton btn_enviarConf_recClv;
    private javax.swing.JButton btn_nuevaClave_recClv;
    private javax.persistence.EntityManager entityManager0;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lbl_crear_nombreUsuario;
    private javax.swing.JLabel lbl_crear_nombreUsuario1;
    private javax.swing.JLabel lbl_crear_nombreUsuario2;
    private javax.swing.JPanel pnl_crear;
    private javax.swing.JPanel pnl_listado;
    private javax.swing.JPanel pnl_modUsuario;
    private javax.swing.JPanel pnl_recuperar_clave;
    private javax.swing.JPasswordField pw_crear_confContra;
    private javax.swing.JPasswordField pw_crear_contra;
    private javax.swing.JRadioButton rb_crear_rolAdmin;
    private javax.swing.JRadioButton rb_crear_rolEstandar;
    private javax.swing.JScrollPane scpnl_tbl_usuarioCreado;
    private javax.swing.JTabbedPane tb_modUsuario_permisos;
    private javax.swing.JTable tbl_usuarioCreado;
    private javax.swing.JTextField txt_codigoConf_recClv;
    private javax.swing.JTextField txt_correo_recClv;
    private javax.swing.JTextField txt_crear_nombreUsuario;
    private javax.swing.JTextField txt_listado_buscar;
    private javax.swing.JTextField txt_nuevaClaveConf_recClv;
    private javax.swing.JTextField txt_nuevaClave_recClv;
    private javax.swing.JTextField txt_usuario_recClv;
    private java.util.List<presentacion.Usuarios_1> usuarios_1List;
    private javax.persistence.Query usuarios_1Query;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}