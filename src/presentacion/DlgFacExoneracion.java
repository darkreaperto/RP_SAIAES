/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacion;

import controladores.CtrExoneracion;
import controladores.CtrImpuesto;
import java.util.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.JOptionPane;
import logica.servicios.Mensaje;
import static presentacion.DlgFacImpuesto.ifrmFacturacion;
import util.TipoMensaje;

/**
 * Inicializa la ventana de dialog que contiene la información de exoneraciones.
 * @author aoihanabi
 */
public class DlgFacExoneracion extends javax.swing.JDialog {
    public static DlgFacImpuesto dlgFacImpuesto;
    private static CtrExoneracion controlador;
    private static CtrImpuesto ctrImpuesto;
    private static Mensaje msg;
    /**
     * Creates new form DlgFacExoneracion
     * @param parent ventana padre de este Jdialog
     * @param modal establece si la ventana permite acceso a otras mientras
     * está abierta.
     */
    public DlgFacExoneracion(DlgFacImpuesto parent, /*java.awt.Frame parent,*/
            boolean modal) {
        //super(parent, modal);
        this.dlgFacImpuesto = parent;
        this.setModal(modal);
        initComponents();
//        setLocationRelativeTo(this);
        
        controlador = new CtrExoneracion(); 
        ctrImpuesto = new CtrImpuesto();
        msg = new Mensaje();
        lblMontoImpuesto.setText(String.valueOf(dlgFacImpuesto.calcularImpuesto()));
        
    }
    
    /**
     * 
     * @return 
     */
    public String tipoDocExoneracion() {
        String codigo = "99";
        if(rbCompraAutorizada.isSelected()) {
            codigo = "01";
        } else if(rbVentaDiplomatico.isSelected()) {
            codigo = "02";
        } else if(rbOrdenCompra.isSelected()) {
            codigo = "03";
        } else if(rbExencionesHacienda.isSelected()) {
            codigo = "04";
        } else if(rbZonaFranca.isSelected()) {
            codigo = "05";
        } else if(rbOtros.isSelected()) {
            codigo = "99";
        } 
        return codigo;
    }
    
    /**
     * 
     * @return 
     */
    public static Timestamp noow() {
        Calendar cal = Calendar.getInstance();
        Timestamp sqlDate = new Timestamp(cal.getTimeInMillis());
        return sqlDate;
        
    }
    
    /**
     * 
     */
    public void fchformatHacienda() {
        
        ArrayList<Timestamp> fechas = controlador.obtenerExoneraciones();
        SimpleDateFormat formatter1 = new SimpleDateFormat("YYYY-MM-dd");
        SimpleDateFormat formatter2 = new SimpleDateFormat("hh:mm:ss[Z|(+|-)hh:mm]");
        
        for(int i = 0; i < fechas.size(); i++) {
            String dateInString = formatter1.format(fechas.get(i)) + "T" +
                    formatter2.format(fechas.get(i).getTime());
            System.out.println("FORMATTED DATE: "+dateInString);
        }
    }
    
    /**
     * 
     */
    public void crearExoneracion() {
        try{
            if(!lblMontoImpuesto.getText().isEmpty() && 
                    !txtPorcentajeCompra.getText().isEmpty()) {
                if(ctrImpuesto.getCodImpuesto() != 0) {
                    controlador.crearExoneracion(ctrImpuesto.getCodImpuesto(),
                        tipoDocExoneracion(), "00", lblAserradero.getText(),
                        noow(), Double.valueOf(lblMontoImpuesto.getText()), 
                        Integer.valueOf(txtPorcentajeCompra.getText()));
                } else {
                    msg.mostrarMensaje(JOptionPane.INFORMATION_MESSAGE, 
                        TipoMensaje.TAX_CODE_MISSING);
                }                
            } else {
                msg.mostrarMensaje(JOptionPane.INFORMATION_MESSAGE, 
                        TipoMensaje.EMPTY_TEXT_FIELD);
            }
        } catch (NumberFormatException ex) {
            msg.mostrarMensaje(JOptionPane.INFORMATION_MESSAGE, 
                    TipoMensaje.WRONG_DECIMAL_NUMBER);
            System.out.println("Number exception: " + ex);
        }
    }
    
    /**
     * 
     */
    public void exonerar() {
        ifrmFacturacion.exonerado = true;
        this.dispose();
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgTipoExoneracion = new javax.swing.ButtonGroup();
        pnlExoneracion = new javax.swing.JPanel();
        pnlTipoExoneracion = new javax.swing.JPanel();
        rbCompraAutorizada = new javax.swing.JRadioButton();
        rbVentaDiplomatico = new javax.swing.JRadioButton();
        rbOrdenCompra = new javax.swing.JRadioButton();
        rbExencionesHacienda = new javax.swing.JRadioButton();
        rbZonaFranca = new javax.swing.JRadioButton();
        rbOtros = new javax.swing.JRadioButton();
        lblTextMontoImpuesto = new javax.swing.JLabel();
        lblTextPocentCompra = new javax.swing.JLabel();
        lblTextNombreInstitucion = new javax.swing.JLabel();
        lblMontoImpuesto = new javax.swing.JLabel();
        txtPorcentajeCompra = new javax.swing.JTextField();
        btnCancelarExon = new javax.swing.JButton();
        btnAceptarExon = new javax.swing.JButton();
        lblAserradero = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Agregar exoneración");

        pnlTipoExoneracion.setBorder(javax.swing.BorderFactory.createTitledBorder("Seleccione el tipo de exoneración:"));

        bgTipoExoneracion.add(rbCompraAutorizada);
        rbCompraAutorizada.setSelected(true);
        rbCompraAutorizada.setText("Compras autorizadas");

        bgTipoExoneracion.add(rbVentaDiplomatico);
        rbVentaDiplomatico.setText("Ventas exentas a diplomáticos");

        bgTipoExoneracion.add(rbOrdenCompra);
        rbOrdenCompra.setText("Orden de compra (Instituciones públicas y otros)");

        bgTipoExoneracion.add(rbExencionesHacienda);
        rbExencionesHacienda.setText("Exenciones Dirección General de Hacienda");

        bgTipoExoneracion.add(rbZonaFranca);
        rbZonaFranca.setText("Zonas Francas");

        bgTipoExoneracion.add(rbOtros);
        rbOtros.setText("Otros");

        javax.swing.GroupLayout pnlTipoExoneracionLayout = new javax.swing.GroupLayout(pnlTipoExoneracion);
        pnlTipoExoneracion.setLayout(pnlTipoExoneracionLayout);
        pnlTipoExoneracionLayout.setHorizontalGroup(
            pnlTipoExoneracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTipoExoneracionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlTipoExoneracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlTipoExoneracionLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(pnlTipoExoneracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rbOrdenCompra)
                            .addComponent(rbVentaDiplomatico)
                            .addComponent(rbCompraAutorizada)
                            .addComponent(rbExencionesHacienda)))
                    .addGroup(pnlTipoExoneracionLayout.createSequentialGroup()
                        .addGroup(pnlTipoExoneracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rbZonaFranca)
                            .addComponent(rbOtros))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        pnlTipoExoneracionLayout.setVerticalGroup(
            pnlTipoExoneracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTipoExoneracionLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(rbCompraAutorizada)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rbVentaDiplomatico)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rbOrdenCompra)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rbExencionesHacienda)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rbZonaFranca)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rbOtros))
        );

        lblTextMontoImpuesto.setText("Monto Impuesto:");

        lblTextPocentCompra.setText("Porcentaje Compra:");
        lblTextPocentCompra.setToolTipText("");

        lblTextNombreInstitucion.setText("Nombre de Institución:");

        lblMontoImpuesto.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.SystemColor.activeCaption));

        btnCancelarExon.setText("Cancelar");
        btnCancelarExon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarExonActionPerformed(evt);
            }
        });

        btnAceptarExon.setText("Aceptar");
        btnAceptarExon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarExonActionPerformed(evt);
            }
        });

        lblAserradero.setFont(new java.awt.Font("Sitka Small", 1, 14)); // NOI18N
        lblAserradero.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAserradero.setText("ASERRADERO EL SOLDADO");
        lblAserradero.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.SystemColor.activeCaption));

        javax.swing.GroupLayout pnlExoneracionLayout = new javax.swing.GroupLayout(pnlExoneracion);
        pnlExoneracion.setLayout(pnlExoneracionLayout);
        pnlExoneracionLayout.setHorizontalGroup(
            pnlExoneracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlExoneracionLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(pnlExoneracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlExoneracionLayout.createSequentialGroup()
                        .addComponent(pnlTipoExoneracion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(pnlExoneracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlExoneracionLayout.createSequentialGroup()
                                .addComponent(lblTextNombreInstitucion, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(74, 74, 74))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlExoneracionLayout.createSequentialGroup()
                                .addGroup(pnlExoneracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lblTextMontoImpuesto, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblTextPocentCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pnlExoneracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtPorcentajeCompra)
                                    .addComponent(lblMontoImpuesto, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(lblAserradero, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(pnlExoneracionLayout.createSequentialGroup()
                        .addComponent(btnCancelarExon, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAceptarExon, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        pnlExoneracionLayout.setVerticalGroup(
            pnlExoneracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlExoneracionLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnlExoneracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnlExoneracionLayout.createSequentialGroup()
                        .addComponent(lblTextNombreInstitucion, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblAserradero, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(76, 76, 76)
                        .addGroup(pnlExoneracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTextMontoImpuesto, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblMontoImpuesto, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlExoneracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblTextPocentCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPorcentajeCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(pnlTipoExoneracion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlExoneracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelarExon, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAceptarExon, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlExoneracion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlExoneracion, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarExonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarExonActionPerformed
        ifrmFacturacion.exonerado = false;
        this.dispose();
    }//GEN-LAST:event_btnCancelarExonActionPerformed

    private void btnAceptarExonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarExonActionPerformed
        crearExoneracion();
    }//GEN-LAST:event_btnAceptarExonActionPerformed

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
            java.util.logging.Logger.getLogger(DlgFacExoneracion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DlgFacExoneracion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DlgFacExoneracion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DlgFacExoneracion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DlgFacExoneracion dialog = new DlgFacExoneracion(dlgFacImpuesto, 
                        true);
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
    private javax.swing.ButtonGroup bgTipoExoneracion;
    private javax.swing.JButton btnAceptarExon;
    private javax.swing.JButton btnCancelarExon;
    private javax.swing.JLabel lblAserradero;
    private javax.swing.JLabel lblMontoImpuesto;
    private javax.swing.JLabel lblTextMontoImpuesto;
    private javax.swing.JLabel lblTextNombreInstitucion;
    private javax.swing.JLabel lblTextPocentCompra;
    private javax.swing.JPanel pnlExoneracion;
    private javax.swing.JPanel pnlTipoExoneracion;
    private javax.swing.JRadioButton rbCompraAutorizada;
    private javax.swing.JRadioButton rbExencionesHacienda;
    private javax.swing.JRadioButton rbOrdenCompra;
    private javax.swing.JRadioButton rbOtros;
    private javax.swing.JRadioButton rbVentaDiplomatico;
    private javax.swing.JRadioButton rbZonaFranca;
    private javax.swing.JTextField txtPorcentajeCompra;
    // End of variables declaration//GEN-END:variables
}
