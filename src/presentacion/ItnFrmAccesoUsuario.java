/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacion;
import bd.AESEncrypt;
import controladores.CtrAcceso;
import controladores.CtrUsuario;
import java.awt.Component;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import logica.Acceso;
import util.MessageHelper;
import util.MessageType;
/**
 *
 * @author ahoihanabi
 */
public class ItnFrmAccesoUsuario extends javax.swing.JInternalFrame {
    
    private static ItnFrmAccesoUsuario instancia = null;
    private static CtrAcceso controlador;
    private AESEncrypt crypter = new AESEncrypt();
    Mensaje msg = new Mensaje();
    /**
     * Creates new form ItnFrmAcesoUsuario
     */
    public ItnFrmAccesoUsuario() {
        initComponents();
        controlador = CtrAcceso.getInstancia();
        crypter.addKey("SAI");
        
        //No mover el internalFrame de acceso
        BasicInternalFrameUI bif = ((javax.swing.plaf.basic.BasicInternalFrameUI)this.getUI());
        for(MouseListener listener : bif.getNorthPane().getMouseListeners()) {
            bif.getNorthPane().removeMouseListener(listener);
        }
    }

    public static ItnFrmAccesoUsuario getInstancia() {
        if(instancia == null) {
            instancia = new ItnFrmAccesoUsuario();
        }
        return instancia;
    }
    
    public void entrada() {
//        if() {
//            //ES ADMIN O ESTÁNDAR?
//        }
        //Habilitar botones    
        for (Component c : ItnFrmAccesoUsuario.getInstancia().getParent().getParent().getComponents()) {
            //System.out.println(c);
            if (c instanceof JToolBar) {
                for (Component b : ((JToolBar) c).getComponents()) {
                    //System.out.println(b);
                    if (b instanceof JButton) {
                        b.setEnabled(true);
                    }
                }
            }
        }
        instancia.dispose();
    }
    
    public void iniciarSesion() {
        
        if (!txt_NombreUsuario.getText().isEmpty() && pw_acc_password.getPassword().length > 0) {
            //comprobar contraseña y nombre de usuario
            if (controlador.comparacion(txt_NombreUsuario.getText(), new String(pw_acc_password.getPassword()))) {                
                entrada();
                msg.mostrarMensaje(MessageType.INFORMATION, MessageHelper.USER_ACCESS_SUCCESS);
            } else {
                msg.mostrarMensaje(MessageType.ERROR, MessageHelper.USER_ACCESS_FAILURE);                
            }
        } else {
            msg.mostrarMensaje(MessageType.ERROR, MessageHelper.USER_ACCESS_FAILURE);
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnl_modAccesoUsuario = new javax.swing.JPanel();
        lbl_acc_NombreUsuario = new javax.swing.JLabel();
        lbl_acc_password = new javax.swing.JLabel();
        txt_NombreUsuario = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        pw_acc_password = new javax.swing.JPasswordField();
        lbl_acc_logo = new javax.swing.JLabel();
        btn_acc_entrar = new javax.swing.JButton();
        btn_acc_recup = new javax.swing.JButton();

        setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        setTitle("ACCESO A SISTEMA");
        setPreferredSize(new java.awt.Dimension(600, 315));

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

        btn_acc_recup.setText("Recuperar");
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
                    .addGroup(pnl_modAccesoUsuarioLayout.createSequentialGroup()
                        .addGroup(pnl_modAccesoUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_acc_password)
                            .addComponent(lbl_acc_NombreUsuario))
                        .addGap(111, 111, 111))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_modAccesoUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txt_NombreUsuario, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(pw_acc_password, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_modAccesoUsuarioLayout.createSequentialGroup()
                            .addComponent(btn_acc_recup, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btn_acc_entrar, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(27, 27, 27)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_acc_logo, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnl_modAccesoUsuarioLayout.setVerticalGroup(
            pnl_modAccesoUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_modAccesoUsuarioLayout.createSequentialGroup()
                .addGroup(pnl_modAccesoUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnl_modAccesoUsuarioLayout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addComponent(lbl_acc_NombreUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_NombreUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lbl_acc_password, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pw_acc_password, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(pnl_modAccesoUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_acc_recup)
                            .addComponent(btn_acc_entrar))
                        .addGap(0, 39, Short.MAX_VALUE))
                    .addGroup(pnl_modAccesoUsuarioLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(pnl_modAccesoUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator1)
                            .addComponent(lbl_acc_logo, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnl_modAccesoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnl_modAccesoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
        
    }//GEN-LAST:event_btn_acc_recupActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_acc_entrar;
    private javax.swing.JButton btn_acc_recup;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lbl_acc_NombreUsuario;
    private javax.swing.JLabel lbl_acc_logo;
    private javax.swing.JLabel lbl_acc_password;
    private javax.swing.JPanel pnl_modAccesoUsuario;
    private javax.swing.JPasswordField pw_acc_password;
    private javax.swing.JTextField txt_NombreUsuario;
    // End of variables declaration//GEN-END:variables
}
