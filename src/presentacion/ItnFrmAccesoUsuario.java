/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacion;

import com.sun.prism.image.ViewPort;
import logica.servicios.Mensaje;
import logica.servicios.AESEncrypt;
import controladores.CtrMail;
import controladores.CtrRecover;
import controladores.CtrAcceso;
import controladores.CtrUsuario;
import controladores.CtrVerificacion;
import java.awt.Component;
import java.awt.Container;
import java.awt.ScrollPane;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import logica.negocio.Usuario;
import util.TipoMensaje;
import logica.servicios.MailThread;
import javax.swing.JDesktopPane;
import javax.swing.JLayeredPane;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JViewport;
import logica.negocio.Cliente;
import logica.negocio.Madera;
import logica.servicios.Logger;
import logica.servicios.UI;

/**
 * Inicializa la ventana de acceso para ingresar el usuario y contraseña.
 * @author ahoihanabi
 */
public class ItnFrmAccesoUsuario extends javax.swing.JInternalFrame {

    private static ItnFrmAccesoUsuario instancia = null;
    private static CtrAcceso sesionAcc;
    private static ArrayList<Usuario> usuarios;
    private static ArrayList<Cliente> clientes;
    private static ArrayList<Madera> productos;
    private final AESEncrypt crypter;
    private final Mensaje msg;
    private static CtrRecover recover;
    private static CtrMail mail;
    private static CtrUsuario ctrUsuario;
    private static CtrVerificacion ctrVerificacion;
    private static UI estilo;

    /**
     * Instancia el formulario interno de acceso al sistema.
     *
     * @param sesionAcc Usuario en sesión actual.
     * @param usuarios Lista de usuario en la base de datos.
     */
    public ItnFrmAccesoUsuario(CtrAcceso sesionAcc, ArrayList<Usuario> usuarios) {
        initComponents();
        ItnFrmAccesoUsuario.sesionAcc = sesionAcc;
        ItnFrmAccesoUsuario.usuarios = usuarios;
        crypter  = new AESEncrypt();
        crypter.addKey("SAI");
        ctrUsuario = CtrUsuario.getInstancia();
        ctrVerificacion = new CtrVerificacion();
        msg = new Mensaje();
        estilo = new UI();
        //Bloquear(no mover) el internalFrame de acceso
        BasicInternalFrameUI bif = 
                ((javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI());
        for (MouseListener listener : bif.getNorthPane().getMouseListeners()) {
            bif.getNorthPane().removeMouseListener(listener);
        }
        
        estilo.setLogoImg(lbl_acc_logo);
    }

    /**
     * Retorna la única instancia de la clase.
     * @param sesionAcc Usuario en sesión actual.
     * @param usuarios Lista de usuarios en la base de datos.
     * @return instancia.
     */
    public static ItnFrmAccesoUsuario getInstancia(CtrAcceso sesionAcc, 
            ArrayList<Usuario> usuarios) {
        System.out.println("SESION " + sesionAcc.getUsuario());
        if (instancia == null) {
            instancia = new ItnFrmAccesoUsuario(sesionAcc, usuarios);
        }
        return instancia;
    }

    /**
     * Carga la lista y variable de sesión.
     * @param usuario Nombre de usuario que ingresa.
     */
    public void ingresar(String usuario) {

        //CARGAR LISTA DE USUARIOS CUANDO SE HA DADO ACCESO. 
        //DE ALLÍ OBTENER USUARIO EN SESION
        usuarios = ctrUsuario.obtenerUsuarios();

        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getNombre().equals(usuario)) {
                sesionAcc.setUsuario(usuarios.get(i));
                break;
            }
        }
        System.out.println(sesionAcc.getUsuario().getNombre());
        
        //System.out.println("Root----------------------------------------");
        Container root = this.getParent().getParent().getParent();
        for (Component c : root.getComponents()) {
            //System.out.println("Compon "+c);
            if(c instanceof JMenuBar) {
                for (Component m : ((JMenuBar) c).getComponents()) {
                    if (m instanceof JMenuItem) {
                        m.setEnabled(true);
                    }
                }
            }            
        }
        
        //System.out.println("Frame----------------------------------------");
        //Habilitar botones de los modulos
        Container frameParent = this.getParent().getParent();  
        for (Component c : frameParent.getComponents()) {
            //System.out.println("Compon "+c);
            
            if(c instanceof JScrollPane) {                
                JScrollPane scroll = ((JScrollPane)c); //ScrollPane Principal
                JViewport viewport; //ViewPort para accesar a elementos en el scrollpane
                viewport = scroll.getViewport();
                JToolBar toolbar = ((JToolBar)viewport.getView());

                for (Component b : toolbar.getComponents()) {                  
                    //System.out.println("Button "+b);
                    if (b instanceof JButton) {
                        b.setEnabled(true);
                    }
                }
            }
                    
            /**
             * Mostrar formulario interno después de acceder
             */
            if (c instanceof JDesktopPane) {
                //ItnFrmCliente modCliente = ItnFrmCliente.getInstancia(sesionAcc, clientes);
                //ItnFrmInventario modInventario = ItnFrmInventario.getInstancia(sesionAcc, productos);
                ItnFrmFacturacion modFactura = ItnFrmFacturacion.getInstancia(sesionAcc, clientes, productos);
                //modUsuario.deshabilitarPaneles();
                modFactura.setVisible(true);
                if (((JDesktopPane) c).getComponentCount() == 0) {
                    ((JDesktopPane) c).add(modFactura);

                } else {
                    if (!(((JDesktopPane) c).getComponent(0) instanceof ItnFrmCliente)) {
                        ((JDesktopPane) c).add(modFactura);
                    }
                }
                for (Component i: modFactura.getComponents()) {
                    //System.out.println("MOD " + i);
                    if (i instanceof JRootPane) {
                        for (Component r: ((JRootPane) i).getComponents()) {
                            //System.out.println("ROOT " + r);
                            if (r instanceof JLayeredPane) {
                                for (Component l: ((JLayeredPane) r).getComponents()) {
                                    //System.out.println("LAY " + l);
                                    if (l instanceof JPanel) {
                                        for (Component p: ((JPanel) l).getComponents()) {
                                            //System.out.println("PAN " + p);
                                            if (p instanceof JPanel) {
                                                for (Component t: ((JPanel) p).getComponents()) {
                                                    //System.out.println("TAB " + t);
                                                    if (t instanceof JTabbedPane) {
                                                        ((JTabbedPane) t).setSelectedIndex(1);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        //Limpiar campos de texto, Nombre de usuario y contraseña
        instancia.txt_NombreUsuario.setText("");
        instancia.pw_acc_password.setText("");
        instancia.dispose();
    }

    /**
     * Validar información de inicio de sesión ingresada por el usuario.
     * @param usuario nombre usuario que ingresa
     * @param clave clave de usuario
     */
    public void iniciarSesion(String usuario, String clave) {

        //usuario = "usuario";
        //clave = "usuario2018";
        
        if (!usuario.isEmpty() && clave.length() > 0) {
            //comprobar contraseña y nombre de usuario
            if (sesionAcc.compararClave(usuario, clave)) {
                ingresar(usuario);
                msg.mostrarMensaje(JOptionPane.INFORMATION_MESSAGE,
                        TipoMensaje.USER_ACCESS_SUCCESS);
            } else {
                msg.mostrarMensaje(JOptionPane.ERROR_MESSAGE,
                        TipoMensaje.USER_ACCESS_FAILURE);
            }
        } else {
            msg.mostrarMensaje(JOptionPane.ERROR_MESSAGE,
                    TipoMensaje.USER_ACCESS_FAILURE);
        }
    }

    /**
     * Obtener el correo del usuario especificado por parámetro.
     * @param usuario
     * @return el índice del correo en la lista de usuarios, -1 de lo contrario.
     */
    private int obtenerCorreo(String usuario) {
        int correoIndice = -1;

        for (Usuario u : usuarios) {
            if (u.getNombre().equals(usuario)) {
                System.out.println(u.getNombre());
                correoIndice = usuarios.indexOf(u);
                break;
            }
        }
        System.out.println(correoIndice);
        return correoIndice;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnl_recuperar_clave = new javax.swing.JPanel();
        txt_usuario_recClv = new javax.swing.JTextField();
        txt_codigoConf_recClv = new javax.swing.JTextField();
        btn_nuevaClave_recClv = new javax.swing.JButton();
        btn_confUsuario_recClv = new javax.swing.JButton();
        btn_codigoConf_recClv = new javax.swing.JButton();
        btn_cancelar_recClv = new javax.swing.JButton();
        lblConfUsuario = new javax.swing.JLabel();
        lblConfUsuario1 = new javax.swing.JLabel();
        lblConfUsuario2 = new javax.swing.JLabel();
        lblConfUsuario3 = new javax.swing.JLabel();
        lblConfUsuario4 = new javax.swing.JLabel();
        pw_nuevaClave_recClv = new javax.swing.JPasswordField();
        pw_nuevaClaveConf_recClv = new javax.swing.JPasswordField();
        pb_enviarCorreo = new javax.swing.JProgressBar();
        lbl_correo_recClv = new javax.swing.JLabel();
        pnl_cargarListas = new javax.swing.JPanel();
        btn_salir_carLis = new javax.swing.JButton();
        lblConfUsuario5 = new javax.swing.JLabel();
        pb_cargar = new javax.swing.JProgressBar();
        pnl_modAccesoUsuario = new javax.swing.JPanel();
        lbl_acc_NombreUsuario = new javax.swing.JLabel();
        lbl_acc_password = new javax.swing.JLabel();
        txt_NombreUsuario = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        pw_acc_password = new javax.swing.JPasswordField();
        lbl_acc_logo = new javax.swing.JLabel();
        btn_acc_entrar = new javax.swing.JButton();
        btn_acc_recup = new javax.swing.JButton();

        pnl_recuperar_clave.setAutoscrolls(true);
        pnl_recuperar_clave.setPreferredSize(new java.awt.Dimension(590, 420));

        txt_usuario_recClv.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        txt_usuario_recClv.setNextFocusableComponent(btn_confUsuario_recClv);

        txt_codigoConf_recClv.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        txt_codigoConf_recClv.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(163, 36, 29)));
        txt_codigoConf_recClv.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        txt_codigoConf_recClv.setEnabled(false);
        txt_codigoConf_recClv.setNextFocusableComponent(btn_codigoConf_recClv);

        btn_nuevaClave_recClv.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        btn_nuevaClave_recClv.setText("Recuperar contraseña");
        btn_nuevaClave_recClv.setEnabled(false);
        btn_nuevaClave_recClv.setNextFocusableComponent(txt_usuario_recClv);
        btn_nuevaClave_recClv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_nuevaClave_recClvActionPerformed(evt);
            }
        });

        btn_confUsuario_recClv.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        btn_confUsuario_recClv.setText("Confirmar usuario");
        btn_confUsuario_recClv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_confUsuario_recClvActionPerformed(evt);
            }
        });

        btn_codigoConf_recClv.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        btn_codigoConf_recClv.setText("Confirmar código de recuperación");
        btn_codigoConf_recClv.setEnabled(false);
        btn_codigoConf_recClv.setNextFocusableComponent(pw_nuevaClave_recClv);
        btn_codigoConf_recClv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_codigoConf_recClvActionPerformed(evt);
            }
        });

        btn_cancelar_recClv.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        btn_cancelar_recClv.setText("Cancelar");
        btn_cancelar_recClv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cancelar_recClvActionPerformed(evt);
            }
        });

        lblConfUsuario.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        lblConfUsuario.setText("Nombre de usuario:");

        lblConfUsuario1.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        lblConfUsuario1.setText("Correo asociado:");

        lblConfUsuario2.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        lblConfUsuario2.setText("Código de recuperación:");

        lblConfUsuario3.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        lblConfUsuario3.setText("Contraseña nueva:");

        lblConfUsuario4.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        lblConfUsuario4.setText("Confirmar contraseña:");

        pw_nuevaClave_recClv.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        pw_nuevaClave_recClv.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(163, 36, 29)));
        pw_nuevaClave_recClv.setEnabled(false);

        pw_nuevaClaveConf_recClv.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        pw_nuevaClaveConf_recClv.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(163, 36, 29)));
        pw_nuevaClaveConf_recClv.setEnabled(false);

        pb_enviarCorreo.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        pb_enviarCorreo.setIndeterminate(true);
        pb_enviarCorreo.setString("Enviando correo...");
        pb_enviarCorreo.setStringPainted(true);
        pb_enviarCorreo.setVisible(false);

        lbl_correo_recClv.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        lbl_correo_recClv.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(163, 36, 29)));

        javax.swing.GroupLayout pnl_recuperar_claveLayout = new javax.swing.GroupLayout(pnl_recuperar_clave);
        pnl_recuperar_clave.setLayout(pnl_recuperar_claveLayout);
        pnl_recuperar_claveLayout.setHorizontalGroup(
            pnl_recuperar_claveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_recuperar_claveLayout.createSequentialGroup()
                .addGroup(pnl_recuperar_claveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_recuperar_claveLayout.createSequentialGroup()
                        .addGap(461, 461, 461)
                        .addComponent(btn_cancelar_recClv, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnl_recuperar_claveLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(pnl_recuperar_claveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblConfUsuario3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblConfUsuario2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblConfUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblConfUsuario1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(pnl_recuperar_claveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txt_codigoConf_recClv, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pw_nuevaClaveConf_recClv, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pb_enviarCorreo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txt_usuario_recClv)
                            .addComponent(lbl_correo_recClv, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_recuperar_claveLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_confUsuario_recClv, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnl_recuperar_claveLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblConfUsuario4, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(pw_nuevaClave_recClv))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_recuperar_claveLayout.createSequentialGroup()
                        .addGap(0, 0, 0)
                        .addGroup(pnl_recuperar_claveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn_codigoConf_recClv, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_nuevaClave_recClv, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        pnl_recuperar_claveLayout.setVerticalGroup(
            pnl_recuperar_claveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_recuperar_claveLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_recuperar_claveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblConfUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_usuario_recClv))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_confUsuario_recClv)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pb_enviarCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnl_recuperar_claveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblConfUsuario1, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                    .addComponent(lbl_correo_recClv, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl_recuperar_claveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_codigoConf_recClv, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblConfUsuario2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_codigoConf_recClv)
                .addGap(18, 18, 18)
                .addGroup(pnl_recuperar_claveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pw_nuevaClave_recClv, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblConfUsuario4, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl_recuperar_claveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pw_nuevaClaveConf_recClv, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblConfUsuario3, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_nuevaClave_recClv)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_cancelar_recClv)
                .addContainerGap())
        );

        pnl_cargarListas.setAutoscrolls(true);
        pnl_cargarListas.setPreferredSize(new java.awt.Dimension(590, 385));

        btn_salir_carLis.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        btn_salir_carLis.setText("Salir");
        btn_salir_carLis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_salir_carLisActionPerformed(evt);
            }
        });

        lblConfUsuario5.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        lblConfUsuario5.setText("<html><body>\nCargando...<br>\nCargando... x2\n</body></html>");

        pb_cargar.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        pb_cargar.setIndeterminate(true);
        pb_cargar.setString("Cargando...");
        pb_cargar.setStringPainted(true);
        pb_enviarCorreo.setVisible(false);

        javax.swing.GroupLayout pnl_cargarListasLayout = new javax.swing.GroupLayout(pnl_cargarListas);
        pnl_cargarListas.setLayout(pnl_cargarListasLayout);
        pnl_cargarListasLayout.setHorizontalGroup(
            pnl_cargarListasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_cargarListasLayout.createSequentialGroup()
                .addGroup(pnl_cargarListasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_cargarListasLayout.createSequentialGroup()
                        .addGap(461, 461, 461)
                        .addComponent(btn_salir_carLis, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnl_cargarListasLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblConfUsuario5, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(pb_cargar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnl_cargarListasLayout.setVerticalGroup(
            pnl_cargarListasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_cargarListasLayout.createSequentialGroup()
                .addGroup(pnl_cargarListasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_cargarListasLayout.createSequentialGroup()
                        .addGap(70, 70, 70)
                        .addComponent(pb_cargar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnl_cargarListasLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblConfUsuario5, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 227, Short.MAX_VALUE)
                .addComponent(btn_salir_carLis, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        setIconifiable(true);
        setTitle("ACCESO A SISTEMA");
        setPreferredSize(new java.awt.Dimension(659, 420));

        pnl_modAccesoUsuario.setPreferredSize(new java.awt.Dimension(590, 280));

        lbl_acc_NombreUsuario.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        lbl_acc_NombreUsuario.setText("Nombre de usuario: ");

        lbl_acc_password.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        lbl_acc_password.setText("Contraseña:");

        txt_NombreUsuario.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        txt_NombreUsuario.setNextFocusableComponent(pw_acc_password);
        txt_NombreUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_NombreUsuarioActionPerformed(evt);
            }
        });

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(163, 36, 29)));

        pw_acc_password.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        pw_acc_password.setNextFocusableComponent(btn_acc_entrar);
        pw_acc_password.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pw_acc_passwordActionPerformed(evt);
            }
        });

        lbl_acc_logo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_acc_logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/aes_logo.png"))); // NOI18N

        btn_acc_entrar.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        btn_acc_entrar.setText("Iniciar Sesión");
        btn_acc_entrar.setNextFocusableComponent(btn_acc_recup);
        btn_acc_entrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_acc_entrarActionPerformed(evt);
            }
        });

        btn_acc_recup.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        btn_acc_recup.setForeground(javax.swing.UIManager.getDefaults().getColor("Button.darkShadow"));
        btn_acc_recup.setText("Olvidé mi contraseña");
        btn_acc_recup.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, javax.swing.UIManager.getDefaults().getColor("Button.darkShadow")));
        btn_acc_recup.setContentAreaFilled(false);
        btn_acc_recup.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_acc_recup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_acc_recupActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnl_modAccesoUsuarioLayout = new javax.swing.GroupLayout(pnl_modAccesoUsuario);
        pnl_modAccesoUsuario.setLayout(pnl_modAccesoUsuarioLayout);
        pnl_modAccesoUsuarioLayout.setHorizontalGroup(
            pnl_modAccesoUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_modAccesoUsuarioLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(pnl_modAccesoUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_acc_password)
                    .addComponent(lbl_acc_NombreUsuario)
                    .addGroup(pnl_modAccesoUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(pw_acc_password)
                        .addComponent(btn_acc_entrar)
                        .addComponent(btn_acc_recup, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txt_NombreUsuario, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lbl_acc_logo, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnl_modAccesoUsuarioLayout.setVerticalGroup(
            pnl_modAccesoUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_modAccesoUsuarioLayout.createSequentialGroup()
                .addGap(85, 85, 85)
                .addComponent(lbl_acc_NombreUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_NombreUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lbl_acc_password, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pw_acc_password, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_acc_entrar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_acc_recup, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
            .addGroup(pnl_modAccesoUsuarioLayout.createSequentialGroup()
                .addGroup(pnl_modAccesoUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_modAccesoUsuarioLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnl_modAccesoUsuarioLayout.createSequentialGroup()
                        .addGap(96, 96, 96)
                        .addComponent(lbl_acc_logo, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnl_modAccesoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 611, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnl_modAccesoUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, 392, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void pw_acc_passwordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pw_acc_passwordActionPerformed
        iniciarSesion(txt_NombreUsuario.getText(), 
                new String(pw_acc_password.getPassword()));
    }//GEN-LAST:event_pw_acc_passwordActionPerformed

    private void btn_acc_entrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_acc_entrarActionPerformed
        iniciarSesion(txt_NombreUsuario.getText(), 
                new String(pw_acc_password.getPassword()));
    }//GEN-LAST:event_btn_acc_entrarActionPerformed

    private void btn_acc_recupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_acc_recupActionPerformed
        pnl_recuperar_clave.setVisible(true);
        pnl_recuperar_clave.setBounds(0, 0, 590, 585);
        instancia.add(pnl_recuperar_clave);
        instancia.pnl_modAccesoUsuario.setVisible(false);
        txt_usuario_recClv.requestFocus();
        txt_usuario_recClv.selectAll();
        instancia.pack();

        usuarios = ctrUsuario.obtenerUsuarios();
    }//GEN-LAST:event_btn_acc_recupActionPerformed

    private void btn_cancelar_recClvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cancelar_recClvActionPerformed
        int dialogResult = msg.mostrarDialogo(JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE,
                TipoMensaje.CONFIRM_RESET_PASSWORD_CANCEL);
        
        if (dialogResult == JOptionPane.YES_OPTION) {
            txt_usuario_recClv.setText("");
            lbl_correo_recClv.setText("");
            txt_codigoConf_recClv.setEnabled(false);
            pw_nuevaClave_recClv.setEnabled(false);
            pw_nuevaClaveConf_recClv.setEnabled(false);
            btn_codigoConf_recClv.setEnabled(false);
            btn_nuevaClave_recClv.setEnabled(false);

            //Mostrar de nuevo el dialogo de acceso
            pnl_recuperar_clave.setVisible(false);
            instancia.pnl_modAccesoUsuario.setVisible(true);
            this.pack();
        }
    }//GEN-LAST:event_btn_cancelar_recClvActionPerformed

    private void btn_codigoConf_recClvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_codigoConf_recClvActionPerformed
        String correo = lbl_correo_recClv.getText();
        String codigo = txt_codigoConf_recClv.getText();

        if (!codigo.isEmpty()) {
            if (recover.confirmarCodigo(correo, codigo)) {
                pw_nuevaClave_recClv.setEnabled(true);
                pw_nuevaClaveConf_recClv.setEnabled(true);
                btn_nuevaClave_recClv.setEnabled(true);
            } else {
                msg.mostrarMensaje(JOptionPane.ERROR_MESSAGE, 
                        TipoMensaje.WRONG_CONFIRMATION_CODE);
            }
        } else {
            msg.mostrarMensaje(JOptionPane.WARNING_MESSAGE, 
                        TipoMensaje.EMPTY_CONFIRMATION_CODE_FIELD);
        }
    }//GEN-LAST:event_btn_codigoConf_recClvActionPerformed

    private void btn_confUsuario_recClvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_confUsuario_recClvActionPerformed
        String usuario = txt_usuario_recClv.getText().trim();
        String correo;

        if (!usuario.isEmpty()) {
            int correoIndice = obtenerCorreo(usuario);

            if (correoIndice >= 0) {
                lbl_correo_recClv.setText(usuarios.get(correoIndice).getCorreo());

                pb_enviarCorreo.setVisible(true);

                correo = lbl_correo_recClv.getText().trim();
                if (!correo.isEmpty()) {
                    recover = new CtrRecover(correo);
                    mail = new CtrMail();

                    /* Se implementa un hilo separado para enviar el correo 
                    para evitar el freeze temporal que causa el envío. */
                    Thread thread = new Thread(new MailThread(msg, correo, 
                            recover, mail, txt_codigoConf_recClv, 
                            btn_codigoConf_recClv, pb_enviarCorreo));
                    thread.start();
                    
                } else {
                    msg.mostrarMensaje(JOptionPane.ERROR_MESSAGE,
                            TipoMensaje.EMPTY_EMAIL_FIELD);
                }
            } else {
                msg.mostrarMensaje(JOptionPane.ERROR_MESSAGE,
                        TipoMensaje.CONFIRMATION_EMAIL_NOT_FOUND);
            }
        } else {
            msg.mostrarMensaje(JOptionPane.WARNING_MESSAGE,
                    TipoMensaje.EMPTY_USERNAME_FIELD);
        }
    }//GEN-LAST:event_btn_confUsuario_recClvActionPerformed

    private void btn_nuevaClave_recClvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_nuevaClave_recClvActionPerformed
        String usuario = txt_usuario_recClv.getText().trim();
        String clave = new String(pw_nuevaClave_recClv.getPassword());
        String claveConf = new String(pw_nuevaClaveConf_recClv.getPassword());
        
        if (clave.equals(claveConf)) {
            if (ctrVerificacion.validatePassword(clave)) {
                //Se encripta la contraseña
                clave = crypter.encriptar(clave);
            
                if (ctrUsuario.restablecerClave(usuario, clave)) {
                    //El restablecimiento se ha realizado con éxito
                    msg.mostrarMensaje(JOptionPane.INFORMATION_MESSAGE,
                            TipoMensaje.RESET_PASSWORD_SUCCESS);
                
                    //Limpiar los campos de texto y deshabilitar los botones
                    txt_usuario_recClv.setText("");
                    lbl_correo_recClv.setText("");
                    txt_codigoConf_recClv.setEnabled(false);
                    pw_nuevaClave_recClv.setEnabled(false);
                    pw_nuevaClaveConf_recClv.setEnabled(false);
                    btn_codigoConf_recClv.setEnabled(false);
                    btn_nuevaClave_recClv.setEnabled(false);

                    //Mostrar de nuevo el dialogo de acceso
                    pnl_recuperar_clave.setVisible(false);
                    instancia.pnl_modAccesoUsuario.setVisible(true);
                    this.pack();
            
                } else {
                    msg.mostrarMensaje(JOptionPane.ERROR_MESSAGE,
                            TipoMensaje.RESET_PASSWORD_FAILURE);
                }
            } else {
                msg.mostrarMensaje(JOptionPane.INFORMATION_MESSAGE, 
                        TipoMensaje.PASSWORD_SYNTAX_FAILURE);
            }
        } else {
            msg.mostrarMensaje(JOptionPane.ERROR_MESSAGE,
                    TipoMensaje.MISMATCHING_PASSWORD_FIELDS);
        }
    }//GEN-LAST:event_btn_nuevaClave_recClvActionPerformed

    private void txt_NombreUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_NombreUsuarioActionPerformed
        iniciarSesion(txt_NombreUsuario.getText(), 
                new String(pw_acc_password.getPassword()));
    }//GEN-LAST:event_txt_NombreUsuarioActionPerformed

    private void btn_salir_carLisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_salir_carLisActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_salir_carLisActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_acc_entrar;
    private javax.swing.JButton btn_acc_recup;
    private javax.swing.JButton btn_cancelar_recClv;
    private javax.swing.JButton btn_codigoConf_recClv;
    private javax.swing.JButton btn_confUsuario_recClv;
    private javax.swing.JButton btn_nuevaClave_recClv;
    private javax.swing.JButton btn_salir_carLis;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblConfUsuario;
    private javax.swing.JLabel lblConfUsuario1;
    private javax.swing.JLabel lblConfUsuario2;
    private javax.swing.JLabel lblConfUsuario3;
    private javax.swing.JLabel lblConfUsuario4;
    private javax.swing.JLabel lblConfUsuario5;
    private javax.swing.JLabel lbl_acc_NombreUsuario;
    private javax.swing.JLabel lbl_acc_logo;
    private javax.swing.JLabel lbl_acc_password;
    private javax.swing.JLabel lbl_correo_recClv;
    private javax.swing.JProgressBar pb_cargar;
    private javax.swing.JProgressBar pb_enviarCorreo;
    private javax.swing.JPanel pnl_cargarListas;
    private javax.swing.JPanel pnl_modAccesoUsuario;
    private javax.swing.JPanel pnl_recuperar_clave;
    private javax.swing.JPasswordField pw_acc_password;
    private javax.swing.JPasswordField pw_nuevaClaveConf_recClv;
    private javax.swing.JPasswordField pw_nuevaClave_recClv;
    private javax.swing.JTextField txt_NombreUsuario;
    private javax.swing.JTextField txt_codigoConf_recClv;
    private javax.swing.JTextField txt_usuario_recClv;
    // End of variables declaration//GEN-END:variables
}
