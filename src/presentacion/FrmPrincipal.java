/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacion;

import controladores.CtrAcceso;
import java.awt.Component;
import java.awt.Container;
import java.util.ArrayList;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import logica.Usuario;

/**
 * Inicializa la ventana principal del sistema.
 * @author ahoihanabi
 */
public class FrmPrincipal extends javax.swing.JFrame {

    //Internal frames de los modulos
    private static ItnFrmAccesoUsuario modUsuarioAcceso;
    private static ItnFrmUsuario modUsuario;
    private static CtrAcceso sesionAcc;
    private static ArrayList<Usuario> usuarios;

    /**
     * Crea el form principal, instancia variables para almacenar el usuario en
     * sesión y la lista de todos los usuarios.
     */
    public FrmPrincipal() {
        initComponents();

        sesionAcc = new CtrAcceso();
        usuarios = new ArrayList<>();
        ventanaAcceso();
    }

    /**
     * Inhablita el acceso a la interfaz (bloquea botones).
     */
    public void cerrarSesion() {
        cerrarInternalFrame();
        bloquearBotones();
    }

    /**
     * Mostrar formulario interno de acceso al sistema.
     */
    public void ventanaAcceso() {
        modUsuarioAcceso = ItnFrmAccesoUsuario.getInstancia(sesionAcc, usuarios);
        modUsuarioAcceso.setVisible(true);

        try {
            dpn_principal.add(modUsuarioAcceso);
        } catch (Exception e) {
            System.out.println(e);
        }
        modUsuarioAcceso.setLocation(300, 150);
    }
    
    /**
     * Deshabilita los botones de los modulos.
     */
    public void bloquearBotones() {
        btn_usuarios.setEnabled(false);
        btn_clientes.setEnabled(false);
        btn_facturacion.setEnabled(false);
        btn_consultas.setEnabled(false);
        btn_inventario.setEnabled(false);
        btn_maquinaria.setEnabled(false);
        btn_proveedor.setEnabled(false);
    }
    
    /**
     * Cerrar formulario interno en el desktop panel principal.
     */
    public void cerrarInternalFrame() {

        Container frameParent = this.getRootPane().getContentPane();
        System.out.println("fp " + frameParent);

        //Frame principal, tiene Jtoolbar DesktopPane..
        for (Component c : frameParent.getComponents()) {
            if (c instanceof JDesktopPane) {
                for (Component i : ((JDesktopPane) c).getComponents()) {
                    System.out.println("I" + i);
                    if (i instanceof JInternalFrame) {
                        System.out.println("Internal: " + i);
                        //((JInternalFrame) i).dispose();
                        dpn_principal.remove(i);
                    }
                }
                this.pack();
            }
        }
        sesionAcc.setUsuario(null);
        usuarios.clear();
        ventanaAcceso();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dpn_principal = new javax.swing.JDesktopPane();
        tlb_modulos = new javax.swing.JToolBar();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        btn_facturacion = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        btn_inventario = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        btn_consultas = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JToolBar.Separator();
        btn_clientes = new javax.swing.JButton();
        jSeparator5 = new javax.swing.JToolBar.Separator();
        btn_proveedor = new javax.swing.JButton();
        jSeparator6 = new javax.swing.JToolBar.Separator();
        btn_maquinaria = new javax.swing.JButton();
        jSeparator7 = new javax.swing.JToolBar.Separator();
        btn_usuarios = new javax.swing.JButton();
        mnb_principal = new javax.swing.JMenuBar();
        mnbtn_archivo = new javax.swing.JMenu();
        mnbtn_editar = new javax.swing.JMenu();
        mnbtn_ver = new javax.swing.JMenu();
        mnbtn_salir = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("SAI-AES");
        setLocation(new java.awt.Point(0, 0));
        setUndecorated(true);
        setResizable(false);

        javax.swing.GroupLayout dpn_principalLayout = new javax.swing.GroupLayout(dpn_principal);
        dpn_principal.setLayout(dpn_principalLayout);
        dpn_principalLayout.setHorizontalGroup(
            dpn_principalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1239, Short.MAX_VALUE)
        );
        dpn_principalLayout.setVerticalGroup(
            dpn_principalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        tlb_modulos.setFloatable(false);
        tlb_modulos.setOrientation(javax.swing.SwingConstants.VERTICAL);
        tlb_modulos.setRollover(true);
        tlb_modulos.setPreferredSize(new java.awt.Dimension(104, 707));

        jSeparator1.setSeparatorSize(new java.awt.Dimension(15, 10));
        tlb_modulos.add(jSeparator1);

        btn_facturacion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/c_facturacion.png"))); // NOI18N
        btn_facturacion.setText(" Facturación");
        btn_facturacion.setEnabled(false);
        btn_facturacion.setFocusable(false);
        btn_facturacion.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_facturacion.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_facturacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_facturacionActionPerformed(evt);
            }
        });
        tlb_modulos.add(btn_facturacion);

        jSeparator2.setSeparatorSize(new java.awt.Dimension(15, 10));
        tlb_modulos.add(jSeparator2);

        btn_inventario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/c_inventario.png"))); // NOI18N
        btn_inventario.setText("  Inventario  ");
        btn_inventario.setEnabled(false);
        btn_inventario.setFocusable(false);
        btn_inventario.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_inventario.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_inventario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_inventarioActionPerformed(evt);
            }
        });
        tlb_modulos.add(btn_inventario);

        jSeparator3.setSeparatorSize(new java.awt.Dimension(15, 10));
        tlb_modulos.add(jSeparator3);

        btn_consultas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/c_consulta.png"))); // NOI18N
        btn_consultas.setText("  Consultas  ");
        btn_consultas.setEnabled(false);
        btn_consultas.setFocusable(false);
        btn_consultas.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_consultas.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_consultas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_consultasActionPerformed(evt);
            }
        });
        tlb_modulos.add(btn_consultas);

        jSeparator4.setSeparatorSize(new java.awt.Dimension(15, 10));
        tlb_modulos.add(jSeparator4);

        btn_clientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/c_cliente.png"))); // NOI18N
        btn_clientes.setText("   Clientes    ");
        btn_clientes.setEnabled(false);
        btn_clientes.setFocusable(false);
        btn_clientes.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_clientes.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_clientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_clientesActionPerformed(evt);
            }
        });
        tlb_modulos.add(btn_clientes);

        jSeparator5.setSeparatorSize(new java.awt.Dimension(15, 10));
        tlb_modulos.add(jSeparator5);

        btn_proveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/c_proveedor.png"))); // NOI18N
        btn_proveedor.setText("Proveedores");
        btn_proveedor.setEnabled(false);
        btn_proveedor.setFocusable(false);
        btn_proveedor.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_proveedor.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_proveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_proveedorActionPerformed(evt);
            }
        });
        tlb_modulos.add(btn_proveedor);

        jSeparator6.setSeparatorSize(new java.awt.Dimension(15, 10));
        tlb_modulos.add(jSeparator6);

        btn_maquinaria.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/c_maquinaria.png"))); // NOI18N
        btn_maquinaria.setText(" Maquinaria ");
        btn_maquinaria.setEnabled(false);
        btn_maquinaria.setFocusable(false);
        btn_maquinaria.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_maquinaria.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_maquinaria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_maquinariaActionPerformed(evt);
            }
        });
        tlb_modulos.add(btn_maquinaria);

        jSeparator7.setSeparatorSize(new java.awt.Dimension(15, 10));
        tlb_modulos.add(jSeparator7);

        btn_usuarios.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/c_usuario.png"))); // NOI18N
        btn_usuarios.setText("   Usuarios   ");
        btn_usuarios.setEnabled(false);
        btn_usuarios.setFocusable(false);
        btn_usuarios.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_usuarios.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_usuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_usuariosActionPerformed(evt);
            }
        });
        tlb_modulos.add(btn_usuarios);

        mnbtn_archivo.setText("Archivo");
        mnb_principal.add(mnbtn_archivo);

        mnbtn_editar.setText("Editar");
        mnb_principal.add(mnbtn_editar);

        mnbtn_ver.setText("Ver");
        mnb_principal.add(mnbtn_ver);

        mnbtn_salir.setText("Salir");

        jMenuItem1.setText("Cerrar Sesión");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        mnbtn_salir.add(jMenuItem1);

        mnb_principal.add(mnbtn_salir);

        setJMenuBar(mnb_principal);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(tlb_modulos, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(dpn_principal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(tlb_modulos, javax.swing.GroupLayout.PREFERRED_SIZE, 721, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(dpn_principal)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    /**
     *
     * @param evt
     */
    private void btn_usuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_usuariosActionPerformed
        //Abrir formulario de usuarios.
        modUsuario = ItnFrmUsuario.getInstancia(sesionAcc, usuarios);
        modUsuario.deshabilitarPaneles();
        modUsuario.setVisible(true);
        if (dpn_principal.getComponentCount() == 0) {
            dpn_principal.add(modUsuario);
        } else {
            if (!(dpn_principal.getComponent(0) instanceof ItnFrmUsuario)) 
                dpn_principal.add(modUsuario);
        }
    }//GEN-LAST:event_btn_usuariosActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        cerrarSesion();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void btn_facturacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_facturacionActionPerformed
        JOptionPane.showMessageDialog(null, "Hi! An amazing billing module will "
                + "be developed here! \n Hold on a little more please. We are "
                + "working hard!");
    }//GEN-LAST:event_btn_facturacionActionPerformed

    private void btn_inventarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_inventarioActionPerformed
        JOptionPane.showMessageDialog(null, "Hi! An amazing stocktaking module "
                + "will be developed here! \n Hold on a little more please. "
                + "We are working hard!");
    }//GEN-LAST:event_btn_inventarioActionPerformed

    private void btn_consultasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_consultasActionPerformed
        JOptionPane.showMessageDialog(null, "Hi! An amazing query module "
                + "will be developed here! \n Hold on a little more please. "
                + "We are working hard!");
    }//GEN-LAST:event_btn_consultasActionPerformed

    private void btn_clientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_clientesActionPerformed
        JOptionPane.showMessageDialog(null, "Hi! An amazing costumers module "
                + "will be developed here! \n Hold on a little more please. "
                + "We are working hard!");
    }//GEN-LAST:event_btn_clientesActionPerformed

    private void btn_proveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_proveedorActionPerformed
        JOptionPane.showMessageDialog(null, "Hi! An amazing providers module "
                + "will be developed here! \n Hold on a little more please. "
                + "We are working hard!");
    }//GEN-LAST:event_btn_proveedorActionPerformed

    private void btn_maquinariaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_maquinariaActionPerformed
        JOptionPane.showMessageDialog(null, "Hi! An amazing enginery module "
                + "will be developed here! \n Hold on a little more please. "
                + "We are working hard!");
    }//GEN-LAST:event_btn_maquinariaActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            /*for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Linux".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }*/
            javax.swing.UIManager.setLookAndFeel(
                    javax.swing.UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        FrmPrincipal frame = new FrmPrincipal();
        java.awt.EventQueue.invokeLater(() -> {
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_clientes;
    private javax.swing.JButton btn_consultas;
    private javax.swing.JButton btn_facturacion;
    private javax.swing.JButton btn_inventario;
    private javax.swing.JButton btn_maquinaria;
    private javax.swing.JButton btn_proveedor;
    private javax.swing.JButton btn_usuarios;
    private javax.swing.JDesktopPane dpn_principal;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JToolBar.Separator jSeparator5;
    private javax.swing.JToolBar.Separator jSeparator6;
    private javax.swing.JToolBar.Separator jSeparator7;
    private javax.swing.JMenuBar mnb_principal;
    private javax.swing.JMenu mnbtn_archivo;
    private javax.swing.JMenu mnbtn_editar;
    private javax.swing.JMenu mnbtn_salir;
    private javax.swing.JMenu mnbtn_ver;
    private javax.swing.JToolBar tlb_modulos;
    // End of variables declaration//GEN-END:variables
}
