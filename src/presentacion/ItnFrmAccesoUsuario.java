/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacion;

import bd.AESEncrypt;
import controladores.CtrMail;
import controladores.CtrRecover;
import controladores.CtrAcceso;
import controladores.CtrUsuario;
import controladores.CtrVerificacion;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import logica.Usuario;
import util.MessageHelper;
import bd.MailThread;

/**
 *
 * @author ahoihanabi
 */
public class ItnFrmAccesoUsuario extends javax.swing.JInternalFrame {

    private static ItnFrmAccesoUsuario instancia = null;
    private static CtrAcceso sesionAcc;
    private static ArrayList<Usuario> usuarios;
    private final AESEncrypt crypter;
    private final Mensaje msg;
    private static CtrRecover recover;
    private static CtrMail mail;
    private static CtrUsuario ctrUsuario;
    private static CtrVerificacion ctrVerificacion;

    /**
     * Creates new form ItnFrmAcesoUsuario
     *
     * @param sesionAcc
     * @param usuarios
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
        
        //No mover el internalFrame de acceso
        BasicInternalFrameUI bif = ((javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI());
        for (MouseListener listener : bif.getNorthPane().getMouseListeners()) {
            bif.getNorthPane().removeMouseListener(listener);
        }
    }

    public static ItnFrmAccesoUsuario getInstancia(CtrAcceso sesionAcc, ArrayList<Usuario> usuarios) {
        System.out.println("SESION " + sesionAcc.getUsuario());
        if (instancia == null) {
            instancia = new ItnFrmAccesoUsuario(sesionAcc, usuarios);
        }
        return instancia;
    }

    public void entrada(String usuarioNow) {

        //CARGAR LISTA DE USUARIOS CUANDO SE HA DADO ACCESO. 
        //DE ALLÍ OBTENER USUARIO EN SESION
        usuarios = ctrUsuario.obtenerUsuarios();

        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getNombre().equals(usuarioNow)) {
                sesionAcc.setUsuario(usuarios.get(i));
                break;
            }
        }
        System.out.println(sesionAcc.getUsuario().getNombre());

        //Frame Principal
        Container frameParent = this.getParent().getParent();

        //Habilitar botones   
        for (Component c : frameParent.getComponents()) {
            //System.out.println("C "+c);
            if (c instanceof JToolBar) {
                for (Component b : ((JToolBar) c).getComponents()) {
                    //System.out.println("B "+b);
                    if (b instanceof JButton) {                        
                        b.setEnabled(true);
                    }
                }
            }
        }
        //Limpiar campos de texto, Nombre de usuario y contraseña
        instancia.txt_NombreUsuario.setText("");
        instancia.pw_acc_password.setText("");
        instancia.dispose();
    }

    public void iniciarSesion() {

        if (!txt_NombreUsuario.getText().isEmpty() && pw_acc_password.getPassword().length > 0) {
            //comprobar contraseña y nombre de usuario
            if (sesionAcc.compararClave(txt_NombreUsuario.getText(), new String(pw_acc_password.getPassword()))) {
                entrada(txt_NombreUsuario.getText());
                msg.mostrarMensaje(JOptionPane.INFORMATION_MESSAGE,
                        MessageHelper.USER_ACCESS_SUCCESS);
            } else {
                msg.mostrarMensaje(JOptionPane.ERROR_MESSAGE,
                        MessageHelper.USER_ACCESS_FAILURE);
            }
        } else {
            msg.mostrarMensaje(JOptionPane.ERROR_MESSAGE,
                    MessageHelper.USER_ACCESS_FAILURE);
        }
    }

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
        txt_correo_recClv = new javax.swing.JTextField();
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
        pnl_recuperar_clave.setPreferredSize(new java.awt.Dimension(590, 385));

        txt_usuario_recClv.setNextFocusableComponent(btn_confUsuario_recClv);

        txt_codigoConf_recClv.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        txt_codigoConf_recClv.setEnabled(false);
        txt_codigoConf_recClv.setNextFocusableComponent(btn_codigoConf_recClv);

        txt_correo_recClv.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        txt_correo_recClv.setEnabled(false);

        btn_nuevaClave_recClv.setText("Recuperar contraseña");
        btn_nuevaClave_recClv.setEnabled(false);
        btn_nuevaClave_recClv.setNextFocusableComponent(txt_usuario_recClv);
        btn_nuevaClave_recClv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_nuevaClave_recClvActionPerformed(evt);
            }
        });

        btn_confUsuario_recClv.setText("Confirmar usuario");
        btn_confUsuario_recClv.setNextFocusableComponent(txt_correo_recClv);
        btn_confUsuario_recClv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_confUsuario_recClvActionPerformed(evt);
            }
        });

        btn_codigoConf_recClv.setText("Confirmar código de recuperación");
        btn_codigoConf_recClv.setEnabled(false);
        btn_codigoConf_recClv.setNextFocusableComponent(pw_nuevaClave_recClv);
        btn_codigoConf_recClv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_codigoConf_recClvActionPerformed(evt);
            }
        });

        btn_cancelar_recClv.setText("Cancelar");
        btn_cancelar_recClv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cancelar_recClvActionPerformed(evt);
            }
        });

        lblConfUsuario.setText("Nombre de usuario:");

        lblConfUsuario1.setText("Correo asociado:");

        lblConfUsuario2.setText("Código de recuperación:");

        lblConfUsuario3.setText("Contraseña nueva:");

        lblConfUsuario4.setText("Confirmar contraseña:");

        pw_nuevaClave_recClv.setEnabled(false);

        pw_nuevaClaveConf_recClv.setEnabled(false);

        pb_enviarCorreo.setIndeterminate(true);
        pb_enviarCorreo.setString("Enviando correo...");
        pb_enviarCorreo.setStringPainted(true);
        pb_enviarCorreo.setVisible(false);

        javax.swing.GroupLayout pnl_recuperar_claveLayout = new javax.swing.GroupLayout(pnl_recuperar_clave);
        pnl_recuperar_clave.setLayout(pnl_recuperar_claveLayout);
        pnl_recuperar_claveLayout.setHorizontalGroup(
            pnl_recuperar_claveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_recuperar_claveLayout.createSequentialGroup()
                .addGroup(pnl_recuperar_claveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_recuperar_claveLayout.createSequentialGroup()
                        .addGap(461, 461, 461)
                        .addComponent(btn_cancelar_recClv, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_recuperar_claveLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_confUsuario_recClv, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_recuperar_claveLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(pnl_recuperar_claveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn_codigoConf_recClv, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_nuevaClave_recClv, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnl_recuperar_claveLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(pnl_recuperar_claveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lblConfUsuario3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblConfUsuario, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblConfUsuario1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblConfUsuario2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                            .addComponent(lblConfUsuario4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(pnl_recuperar_claveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_usuario_recClv)
                            .addComponent(txt_correo_recClv)
                            .addComponent(txt_codigoConf_recClv)
                            .addComponent(pw_nuevaClave_recClv)
                            .addComponent(pw_nuevaClaveConf_recClv)
                            .addComponent(pb_enviarCorreo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        pnl_recuperar_claveLayout.setVerticalGroup(
            pnl_recuperar_claveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_recuperar_claveLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_recuperar_claveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblConfUsuario)
                    .addComponent(txt_usuario_recClv, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addComponent(btn_confUsuario_recClv)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pb_enviarCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addGroup(pnl_recuperar_claveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_correo_recClv, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblConfUsuario1))
                .addGap(18, 18, 18)
                .addGroup(pnl_recuperar_claveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblConfUsuario2)
                    .addComponent(txt_codigoConf_recClv, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_codigoConf_recClv)
                .addGap(18, 18, 18)
                .addGroup(pnl_recuperar_claveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblConfUsuario3)
                    .addComponent(pw_nuevaClave_recClv, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl_recuperar_claveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblConfUsuario4)
                    .addComponent(pw_nuevaClaveConf_recClv, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_nuevaClave_recClv)
                .addGap(18, 18, 18)
                .addComponent(btn_cancelar_recClv, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        setTitle("ACCESO A SISTEMA");
        setPreferredSize(new java.awt.Dimension(600, 420));

        pnl_modAccesoUsuario.setPreferredSize(new java.awt.Dimension(590, 280));

        lbl_acc_NombreUsuario.setText("Nombre de usuario: ");

        lbl_acc_password.setText("Contraseña:");

        txt_NombreUsuario.setNextFocusableComponent(pw_acc_password);

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 255)));

        pw_acc_password.setNextFocusableComponent(btn_acc_entrar);
        pw_acc_password.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pw_acc_passwordActionPerformed(evt);
            }
        });

        lbl_acc_logo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_acc_logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/LOGO_ASERRADERO.jpg"))); // NOI18N

        btn_acc_entrar.setText("Iniciar Sesión");
        btn_acc_entrar.setNextFocusableComponent(btn_acc_recup);
        btn_acc_entrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_acc_entrarActionPerformed(evt);
            }
        });

        btn_acc_recup.setForeground(java.awt.SystemColor.inactiveCaption);
        btn_acc_recup.setText("¿Olvidaste tu contraseña?");
        btn_acc_recup.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, javax.swing.UIManager.getDefaults().getColor("Button.darkShadow")));
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
                .addGroup(pnl_modAccesoUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_modAccesoUsuarioLayout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(pnl_modAccesoUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_NombreUsuario)
                            .addComponent(pw_acc_password)
                            .addGroup(pnl_modAccesoUsuarioLayout.createSequentialGroup()
                                .addGroup(pnl_modAccesoUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbl_acc_password)
                                    .addComponent(lbl_acc_NombreUsuario))
                                .addGap(111, 111, 111))))
                    .addGroup(pnl_modAccesoUsuarioLayout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(btn_acc_recup, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_modAccesoUsuarioLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btn_acc_entrar)))
                .addGap(26, 26, 26)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbl_acc_logo, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnl_modAccesoUsuarioLayout.setVerticalGroup(
            pnl_modAccesoUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_modAccesoUsuarioLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_modAccesoUsuarioLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lbl_acc_logo, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(64, 64, 64))
            .addGroup(pnl_modAccesoUsuarioLayout.createSequentialGroup()
                .addGap(101, 101, 101)
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
                .addGap(23, 23, 23))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnl_modAccesoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnl_modAccesoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 384, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void pw_acc_passwordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pw_acc_passwordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pw_acc_passwordActionPerformed

    private void btn_acc_entrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_acc_entrarActionPerformed
        iniciarSesion();
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
                MessageHelper.CONFIRM_RESET_PASSWORD_CANCEL);
        
        if (dialogResult == JOptionPane.YES_OPTION) {
            txt_usuario_recClv.setText("");
            txt_correo_recClv.setText("");
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
        String correo = txt_correo_recClv.getText();
        String codigo = txt_codigoConf_recClv.getText();

        if (!codigo.isEmpty()) {
            if (recover.confirmarCodigo(correo, codigo)) {
                pw_nuevaClave_recClv.setEnabled(true);
                pw_nuevaClaveConf_recClv.setEnabled(true);
                btn_nuevaClave_recClv.setEnabled(true);
            } else {
                msg.mostrarMensaje(JOptionPane.ERROR_MESSAGE, 
                        MessageHelper.WRONG_CONFIRMATION_CODE);
            }
        } else {
            msg.mostrarMensaje(JOptionPane.WARNING_MESSAGE, 
                        MessageHelper.EMPTY_CONFIRMATION_CODE_FIELD);
        }
    }//GEN-LAST:event_btn_codigoConf_recClvActionPerformed

    private void btn_confUsuario_recClvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_confUsuario_recClvActionPerformed
        String usuario = txt_usuario_recClv.getText().trim();
        String correo;

        if (!usuario.isEmpty()) {
            int correoIndice = obtenerCorreo(usuario);

            if (correoIndice >= 0) {
                txt_correo_recClv.setText(usuarios.get(correoIndice).getCorreo());

                pb_enviarCorreo.setVisible(true);

                correo = txt_correo_recClv.getText().trim();
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
                            MessageHelper.EMPTY_EMAIL_FIELD);
                }
            } else {
                msg.mostrarMensaje(JOptionPane.ERROR_MESSAGE,
                        MessageHelper.CONFIRMATION_EMAIL_NOT_FOUND);
            }
        } else {
            msg.mostrarMensaje(JOptionPane.WARNING_MESSAGE,
                    MessageHelper.EMPTY_USERNAME_FIELD);
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
                            MessageHelper.RESET_PASSWORD_SUCCESS);
                
                    //Limpiar los campos de texto y deshabilitar los botones
                    txt_usuario_recClv.setText("");
                    txt_correo_recClv.setText("");
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
                            MessageHelper.RESET_PASSWORD_FAILURE);
                }
            } else {
                msg.mostrarMensaje(JOptionPane.INFORMATION_MESSAGE, 
                        MessageHelper.PASSWORD_SYNTAX_FAILURE);
            }
        } else {
            msg.mostrarMensaje(JOptionPane.ERROR_MESSAGE,
                    MessageHelper.MISMATCHING_PASSWORD_FIELDS);
        }
    }//GEN-LAST:event_btn_nuevaClave_recClvActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_acc_entrar;
    private javax.swing.JButton btn_acc_recup;
    private javax.swing.JButton btn_cancelar_recClv;
    private javax.swing.JButton btn_codigoConf_recClv;
    private javax.swing.JButton btn_confUsuario_recClv;
    private javax.swing.JButton btn_nuevaClave_recClv;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblConfUsuario;
    private javax.swing.JLabel lblConfUsuario1;
    private javax.swing.JLabel lblConfUsuario2;
    private javax.swing.JLabel lblConfUsuario3;
    private javax.swing.JLabel lblConfUsuario4;
    private javax.swing.JLabel lbl_acc_NombreUsuario;
    private javax.swing.JLabel lbl_acc_logo;
    private javax.swing.JLabel lbl_acc_password;
    private javax.swing.JProgressBar pb_enviarCorreo;
    private javax.swing.JPanel pnl_modAccesoUsuario;
    private javax.swing.JPanel pnl_recuperar_clave;
    private javax.swing.JPasswordField pw_acc_password;
    private javax.swing.JPasswordField pw_nuevaClaveConf_recClv;
    private javax.swing.JPasswordField pw_nuevaClave_recClv;
    private javax.swing.JTextField txt_NombreUsuario;
    private javax.swing.JTextField txt_codigoConf_recClv;
    private javax.swing.JTextField txt_correo_recClv;
    private javax.swing.JTextField txt_usuario_recClv;
    // End of variables declaration//GEN-END:variables
}
