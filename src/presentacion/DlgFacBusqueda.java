/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacion;

import controladores.CtrMadera;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import logica.negocio.Madera;
import logica.servicios.Mensaje;
import logica.servicios.UI;
import logica.servicios.Logger;
import util.TipoMensaje;

/**
 *
 * @author aoihanabi
 */
public class DlgFacBusqueda extends javax.swing.JDialog {
    
    public static ItnFrmFacturacion ifrmFacturacion;
    private static CtrMadera ctrInventario = new CtrMadera();
    private static ArrayList<Madera> productos;
    private static DefaultTableModel model;
    private static Mensaje msg;
    /** Instancia de la clase UI. */
    private final UI estilo;
    
    /**
     * Creates new form DlgFacBusqueda.
     * @param parent ventana padre de este Jdialog
     * @param modal establece si la ventana permite acceso a otras mientras
     * está abierta.
     */
    public DlgFacBusqueda(ItnFrmFacturacion parent,/*java.awt.Frame parent,*/
            boolean modal) {
        //super(parent, modal);
        DlgFacBusqueda.ifrmFacturacion = parent;
        this.setModal(modal);
        initComponents();
        setLocationRelativeTo(this);
        
        estilo = new UI();
        //Estilizar interfaz
        estilo.estilizarTablas(tbListadoProd);
    }
    
    /**
     * Realiza la búsqueda de acuerdo al radiobutton seleccionado.
     */
    public void filtrarBusqueda() {
        try {
            if(rbVariedadProd.isSelected()) {
                cargarProductosJTable(tbListadoProd, 
                        txtBusquedaProd.getText().trim(), 0);
            } else if (rbMedidasProd.isSelected()) {
                cargarProductosJTable(tbListadoProd, 
                        txtBusquedaProd.getText().trim(), 1);
            } else if (rbDescripProd.isSelected()) {
                cargarProductosJTable(tbListadoProd, 
                        txtBusquedaProd.getText().trim(), 2);
            } else if(rbTipoProd.isSelected()) {
                cargarProductosJTable(tbListadoProd, 
                        txtBusquedaProd.getText().trim(), 4);
            } else if(rbCodigoProd.isSelected()) {
                cargarProductosJTable(tbListadoProd, 
                        txtBusquedaProd.getText().trim(), 5);
            }
        } catch (Exception ex) {
            Logger.registerNewError(ex);
            ex.printStackTrace();
            msg.mostrarMensaje(JOptionPane.ERROR_MESSAGE, TipoMensaje.ANY_ROW_SELECTED);
        }
    }
    
    /**
     *  Obtiene la lista de productos filtrados y la carga en la tabla (modelo).
     * @param tabla Nombre de la tabla a llenar
     * @param paramProd Datos del producto para consultar producto en la bd
     * @param codBusq código de clasificación/especificación de búsqueda
     */
    public void cargarProductosJTable(JTable tabla, String paramProd, int codBusq) {
        Object[] row = new Object[9];
        try {
            productos = ctrInventario.busqAvzProductos(paramProd, codBusq);
            
            model = (DefaultTableModel) tabla.getModel();
            model.setRowCount(0);
            model.setColumnCount(8);
            
            for (int i = 0; i < productos.size(); i++) {
                //codigo- tipoProducto- variedad- medidas- cantvaras- precio- descripción- codigo bd
                row[0] = productos.get(i).getCodProducto();
                row[1] = productos.get(i).getTipoProducto();////////
                row[2] = productos.get(i).getDescTipoMadera();//variedad
                row[3] = productos.get(i).getGrueso() + " x " + 
                        productos.get(i).getAncho();
                row[4] = productos.get(i).getCantVaras();
                row[5] = productos.get(i).getPrecioXvara();
                row[6] = productos.get(i).getDescripcion();                
                row[7] = productos.get(i).getCodigo();

                model.addRow(row);
            }
            
            if (productos.size() > 0) {
                tabla.removeColumn(tabla.getColumnModel().getColumn(7));
            }
            
        } catch (SQLException ex) {
            Logger.registerNewError(ex);
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Ocurrió un error de SQL:\n" + ex.getMessage());
        } catch (Exception ex) {
            Logger.registerNewError(ex);
            ex.printStackTrace();
        }       
    }
    
    /**
     * Seleccionar el producto a enviar
     * @return el producto.
     */
    public Madera obtenerProducto() {
        Madera prod = null;
        
        int row = tbListadoProd.getSelectedRow();
        model = (DefaultTableModel) tbListadoProd.getModel();
        
        if (row >= 0) {
            String cod = (String) model.getValueAt(row, 7);
            for (Madera p: productos) {
                if (p.getCodigo().equals(cod)) {
                    prod = p;
                    break;
                }
            }
        }
        
        return prod;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgCriterioBusqueda = new javax.swing.ButtonGroup();
        pnlBusquedaAvanzada = new javax.swing.JPanel();
        btnCancelarBusqueda = new javax.swing.JButton();
        scpnlTblListadoProd = new javax.swing.JScrollPane();
        tbListadoProd = new javax.swing.JTable();
        txtBusquedaProd = new javax.swing.JTextField();
        btnBuscarProd = new javax.swing.JButton();
        pnlFiltroProd = new javax.swing.JPanel();
        rbTipoProd = new javax.swing.JRadioButton();
        rbCodigoProd = new javax.swing.JRadioButton();
        rbVariedadProd = new javax.swing.JRadioButton();
        rbMedidasProd = new javax.swing.JRadioButton();
        rbDescripProd = new javax.swing.JRadioButton();
        btnAceptarBusqueda = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Búsqueda Avanzada");

        btnCancelarBusqueda.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        btnCancelarBusqueda.setText("Cancelar");
        btnCancelarBusqueda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarBusquedaActionPerformed(evt);
            }
        });

        tbListadoProd.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Tipo de Producto", "Variedad Madera", "Medidas", "Cantidad varas", "Precio", "Descripcion", "Codigo bd"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbListadoProd.getTableHeader().setReorderingAllowed(false);
        scpnlTblListadoProd.setViewportView(tbListadoProd);

        txtBusquedaProd.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        txtBusquedaProd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBusquedaProdKeyReleased(evt);
            }
        });

        btnBuscarProd.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        btnBuscarProd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/busqueda.png"))); // NOI18N
        btnBuscarProd.setText("Buscar");
        btnBuscarProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarProdActionPerformed(evt);
            }
        });

        pnlFiltroProd.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Especificación de búsq.:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Yu Gothic UI Semibold", 1, 18))); // NOI18N

        bgCriterioBusqueda.add(rbTipoProd);
        rbTipoProd.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        rbTipoProd.setText("Tipo de producto");

        bgCriterioBusqueda.add(rbCodigoProd);
        rbCodigoProd.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        rbCodigoProd.setText("Código");

        bgCriterioBusqueda.add(rbVariedadProd);
        rbVariedadProd.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        rbVariedadProd.setSelected(true);
        rbVariedadProd.setText("Variedad madera");

        bgCriterioBusqueda.add(rbMedidasProd);
        rbMedidasProd.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        rbMedidasProd.setText("Medidas");

        bgCriterioBusqueda.add(rbDescripProd);
        rbDescripProd.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        rbDescripProd.setText("Descripción");

        javax.swing.GroupLayout pnlFiltroProdLayout = new javax.swing.GroupLayout(pnlFiltroProd);
        pnlFiltroProd.setLayout(pnlFiltroProdLayout);
        pnlFiltroProdLayout.setHorizontalGroup(
            pnlFiltroProdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFiltroProdLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlFiltroProdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rbVariedadProd)
                    .addComponent(rbTipoProd)
                    .addComponent(rbMedidasProd)
                    .addComponent(rbCodigoProd)
                    .addComponent(rbDescripProd, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        pnlFiltroProdLayout.setVerticalGroup(
            pnlFiltroProdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlFiltroProdLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rbVariedadProd)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rbMedidasProd)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rbDescripProd)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rbTipoProd)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rbCodigoProd)
                .addContainerGap(39, Short.MAX_VALUE))
        );

        btnAceptarBusqueda.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        btnAceptarBusqueda.setText("Aceptar");
        btnAceptarBusqueda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarBusquedaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlBusquedaAvanzadaLayout = new javax.swing.GroupLayout(pnlBusquedaAvanzada);
        pnlBusquedaAvanzada.setLayout(pnlBusquedaAvanzadaLayout);
        pnlBusquedaAvanzadaLayout.setHorizontalGroup(
            pnlBusquedaAvanzadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlBusquedaAvanzadaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlBusquedaAvanzadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCancelarBusqueda, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlBusquedaAvanzadaLayout.createSequentialGroup()
                        .addComponent(pnlFiltroProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 3, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlBusquedaAvanzadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnlBusquedaAvanzadaLayout.createSequentialGroup()
                        .addComponent(txtBusquedaProd)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBuscarProd, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(scpnlTblListadoProd, javax.swing.GroupLayout.PREFERRED_SIZE, 710, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAceptarBusqueda, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        pnlBusquedaAvanzadaLayout.setVerticalGroup(
            pnlBusquedaAvanzadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlBusquedaAvanzadaLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(pnlBusquedaAvanzadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlBusquedaAvanzadaLayout.createSequentialGroup()
                        .addGroup(pnlBusquedaAvanzadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtBusquedaProd, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnBuscarProd, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                        .addComponent(scpnlTblListadoProd, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlBusquedaAvanzadaLayout.createSequentialGroup()
                        .addComponent(pnlFiltroProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addGroup(pnlBusquedaAvanzadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelarBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAceptarBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlBusquedaAvanzada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlBusquedaAvanzada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarBusquedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarBusquedaActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCancelarBusquedaActionPerformed

    private void btnAceptarBusquedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarBusquedaActionPerformed
        this.dispose();
        obtenerProducto();//enviar el producto seleccionado a facturación
    }//GEN-LAST:event_btnAceptarBusquedaActionPerformed

    private void txtBusquedaProdKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBusquedaProdKeyReleased
        filtrarBusqueda();
    }//GEN-LAST:event_txtBusquedaProdKeyReleased

    private void btnBuscarProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarProdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnBuscarProdActionPerformed

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
            java.util.logging.Logger.getLogger(DlgFacBusqueda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DlgFacBusqueda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DlgFacBusqueda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DlgFacBusqueda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DlgFacBusqueda dialog = new DlgFacBusqueda(ifrmFacturacion, true);
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
    private javax.swing.ButtonGroup bgCriterioBusqueda;
    private javax.swing.JButton btnAceptarBusqueda;
    private javax.swing.JButton btnBuscarProd;
    private javax.swing.JButton btnCancelarBusqueda;
    private javax.swing.JPanel pnlBusquedaAvanzada;
    private javax.swing.JPanel pnlFiltroProd;
    private javax.swing.JRadioButton rbCodigoProd;
    private javax.swing.JRadioButton rbDescripProd;
    private javax.swing.JRadioButton rbMedidasProd;
    private javax.swing.JRadioButton rbTipoProd;
    private javax.swing.JRadioButton rbVariedadProd;
    private javax.swing.JScrollPane scpnlTblListadoProd;
    private javax.swing.JTable tbListadoProd;
    private javax.swing.JTextField txtBusquedaProd;
    // End of variables declaration//GEN-END:variables
}
