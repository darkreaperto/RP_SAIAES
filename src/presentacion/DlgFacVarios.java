/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacion;

import controladores.CtrVarios;
import javax.swing.JOptionPane;
import logica.servicios.Mensaje;
import logica.servicios.Regex;
import util.TipoMensaje;

/**
 *
 * @author aoihanabi
 */
public class DlgFacVarios extends javax.swing.JDialog {
    public static ItnFrmFacturacion ifrmFacturacion;
    public static CtrVarios controlador;
    private final Regex verificacion;
    private static Mensaje msg;
    
    /**
     * Creates new form DlgFacVarios.
     * @param parent ventana padre de este Jdialog
     * @param modal establece si la ventana permite acceso a otras mientras
     * está abierta.
     */
    public DlgFacVarios(ItnFrmFacturacion parent,/*java.awt.Frame parent,*/
            boolean modal) {
        //super(parent, modal);
        this.ifrmFacturacion = parent;
        this.setModal(modal);
        initComponents();
        setLocationRelativeTo(this);
        
        controlador = new CtrVarios();
        verificacion = new Regex();
        msg = new Mensaje();
    }
    
    /**
     * Verificar la información solicitada y agregar el producto a la bd.
     * @param descripcion detalle que describe el producto
     * @param precio precio del producto
     * @return verdadero si agrega el producto exitosamente
     */
    public boolean agregarVarios(String descripcion, String precio) {
               
        if(!descripcion.isEmpty() && !precio.isEmpty()) {
            if(verificacion.validaPrecio(precio)) {
                //System.out.println(verificacion.validaPrecio(precio));
                double preciodou = Double.valueOf(precio);
                //System.out.println(preciodou);
                boolean crear = controlador.crearVarios(descripcion, preciodou);
                if (crear) {                        
                    msg.mostrarMensaje(JOptionPane.INFORMATION_MESSAGE,
                            TipoMensaje.PRODUCT_INSERTION_SUCCESS);                        
                    return true;
                } else {
                    msg.mostrarMensaje(JOptionPane.ERROR_MESSAGE,
                            TipoMensaje.PRODUCT_INSERTION_FAILURE);                        
                }
            }else{
                msg.mostrarMensaje(JOptionPane.ERROR_MESSAGE, 
                        TipoMensaje.PRICE_SYNTAX_FAILURE);
            }
        } else {
            msg.mostrarMensaje(JOptionPane.ERROR_MESSAGE, 
                    TipoMensaje.EMPTY_TEXT_FIELD);
        }
        return false;
    }
    
    private boolean validarPrecio(String precio) {
        boolean exito = false;
        try {
            double prec = Double.parseDouble(precio);
            if (prec > 0) {
                exito = true;
            }
        } catch (NumberFormatException ex) {
            System.out.println(ex.toString());
        } catch (Exception ex) {
            System.out.println(ex.toString());
        } finally {
            return exito;
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgClasificiacionProd = new javax.swing.ButtonGroup();
        pnlFacVarios = new javax.swing.JPanel();
        lblDescripcionVarios = new javax.swing.JLabel();
        lblPrecioVarios = new javax.swing.JLabel();
        txtDescripcionVarios = new javax.swing.JTextField();
        txtPrecioVarios = new javax.swing.JTextField();
        btnCancelarVarios = new javax.swing.JButton();
        btnAgregarVarios = new javax.swing.JButton();
        pnlClasificacionProductos = new javax.swing.JPanel();
        rbMercancía = new javax.swing.JRadioButton();
        rbServicio = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        lblDescripcionVarios.setText("Descripción: ");

        lblPrecioVarios.setText("Precio:");

        btnCancelarVarios.setText("Cancelar");

        btnAgregarVarios.setText("Agregar");
        btnAgregarVarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarVariosActionPerformed(evt);
            }
        });

        pnlClasificacionProductos.setBorder(javax.swing.BorderFactory.createTitledBorder("Clasificación:"));

        rbMercancía.setText("Mercancía");

        rbServicio.setSelected(true);
        rbServicio.setText("Servicio");

        javax.swing.GroupLayout pnlClasificacionProductosLayout = new javax.swing.GroupLayout(pnlClasificacionProductos);
        pnlClasificacionProductos.setLayout(pnlClasificacionProductosLayout);
        pnlClasificacionProductosLayout.setHorizontalGroup(
            pnlClasificacionProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlClasificacionProductosLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(rbServicio)
                .addGap(90, 90, 90))
            .addGroup(pnlClasificacionProductosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rbMercancía)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlClasificacionProductosLayout.setVerticalGroup(
            pnlClasificacionProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlClasificacionProductosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rbMercancía)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rbServicio)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlFacVariosLayout = new javax.swing.GroupLayout(pnlFacVarios);
        pnlFacVarios.setLayout(pnlFacVariosLayout);
        pnlFacVariosLayout.setHorizontalGroup(
            pnlFacVariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFacVariosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlFacVariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnCancelarVarios, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlClasificacionProductos, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(pnlFacVariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlFacVariosLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(pnlFacVariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblPrecioVarios, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblDescripcionVarios, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlFacVariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDescripcionVarios, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPrecioVarios, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlFacVariosLayout.createSequentialGroup()
                        .addGap(199, 199, 199)
                        .addComponent(btnAgregarVarios, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlFacVariosLayout.setVerticalGroup(
            pnlFacVariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlFacVariosLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(pnlFacVariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlClasificacionProductos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlFacVariosLayout.createSequentialGroup()
                        .addGroup(pnlFacVariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblDescripcionVarios, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDescripcionVarios, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pnlFacVariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblPrecioVarios, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPrecioVarios, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(pnlFacVariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelarVarios, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAgregarVarios, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlFacVarios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlFacVarios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAgregarVariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarVariosActionPerformed
        
        String descripcion = txtDescripcionVarios.getText().trim();
        String precio = txtPrecioVarios.getText().trim();
        boolean mercancia = rbMercancía.isSelected();
        
//        if (!descripcion.isEmpty() && validarPrecio(precio)) {
//            ifrmFacturacion.agregarLineaVarios(descripcion, 
//                    Double.parseDouble(precio), mercancia);
//        } else {
//            //NO SE INGRESARON BIEN LOS DATOS
//        }
        
        //System.out.println(txtDescripcionVarios.getText() + " " + txtPrecioVarios.getText());
//        boolean agregado = agregarVarios(descripcion, precio);
//        if(agregado) {
//            txtDescripcionVarios.setText("");
//            txtPrecioVarios.setText("");
//        }
    }//GEN-LAST:event_btnAgregarVariosActionPerformed

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
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DlgFacVarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DlgFacVarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DlgFacVarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DlgFacVarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DlgFacVarios dialog = new DlgFacVarios(ifrmFacturacion, true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgClasificiacionProd;
    private javax.swing.JButton btnAgregarVarios;
    private javax.swing.JButton btnCancelarVarios;
    private javax.swing.JLabel lblDescripcionVarios;
    private javax.swing.JLabel lblPrecioVarios;
    private javax.swing.JPanel pnlClasificacionProductos;
    private javax.swing.JPanel pnlFacVarios;
    private javax.swing.JRadioButton rbMercancía;
    private javax.swing.JRadioButton rbServicio;
    private javax.swing.JTextField txtDescripcionVarios;
    private javax.swing.JTextField txtPrecioVarios;
    // End of variables declaration//GEN-END:variables
}
