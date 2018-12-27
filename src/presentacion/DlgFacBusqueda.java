/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacion;

/**
 *
 * @author aoihanabi
 */
public class DlgFacBusqueda extends javax.swing.JDialog {
    public static ItnFrmFacturacion ifrmFacturacion;
    /**
     * Creates new form DlgFacBusqueda.
     * @param parent ventana padre de este Jdialog
     * @param modal establece si la ventana permite acceso a otras mientras
     * está abierta.
     */
    public DlgFacBusqueda(ItnFrmFacturacion parent,/*java.awt.Frame parent,*/
            boolean modal) {
        //super(parent, modal);
        this.ifrmFacturacion = parent;
        this.setModal(modal);
        initComponents();
        setLocationRelativeTo(this);
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
        tb_facBusqueda = new javax.swing.JTabbedPane();
        pnlBuscarCliente = new javax.swing.JPanel();
        txtBusquedaCliente = new javax.swing.JTextField();
        scpnlTblListadoCliente = new javax.swing.JScrollPane();
        tbListadoCliente = new javax.swing.JTable();
        btnBuscarCliente = new javax.swing.JButton();
        pnlFiltroCliente = new javax.swing.JPanel();
        rbNombre = new javax.swing.JRadioButton();
        rbCodigoCliente = new javax.swing.JRadioButton();
        rbTelefono = new javax.swing.JRadioButton();
        rbCorreo = new javax.swing.JRadioButton();
        rbEstado = new javax.swing.JRadioButton();
        pnlBuscarProducto = new javax.swing.JPanel();
        txtBusquedaProd = new javax.swing.JTextField();
        scpnlTblListadoProd = new javax.swing.JScrollPane();
        tbListadoProd = new javax.swing.JTable();
        btnBuscarProd = new javax.swing.JButton();
        pnlFiltroProd = new javax.swing.JPanel();
        rbNombre1 = new javax.swing.JRadioButton();
        rbCodigoCliente1 = new javax.swing.JRadioButton();
        rbTelefono1 = new javax.swing.JRadioButton();
        rbCorreo1 = new javax.swing.JRadioButton();
        rbEstado1 = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Búsqueda Avanzada");

        btnCancelarBusqueda.setText("Cancelar");
        btnCancelarBusqueda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarBusquedaActionPerformed(evt);
            }
        });

        tbListadoCliente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cédula", "Primer Apellido", "Segundo Apellido", "Nombre", "Crédito", "Límite de crédito", "Contactos", "Cod. Cliente", "Codigo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbListadoCliente.getTableHeader().setReorderingAllowed(false);
        scpnlTblListadoCliente.setViewportView(tbListadoCliente);

        btnBuscarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/busqueda.png"))); // NOI18N
        btnBuscarCliente.setText("Buscar");

        pnlFiltroCliente.setBorder(javax.swing.BorderFactory.createTitledBorder("Especificación de búsqueda:"));

        bgCriterioBusqueda.add(rbNombre);
        rbNombre.setText("Nombre");

        bgCriterioBusqueda.add(rbCodigoCliente);
        rbCodigoCliente.setText("Código");

        bgCriterioBusqueda.add(rbTelefono);
        rbTelefono.setText("Teléfono/Celular");

        bgCriterioBusqueda.add(rbCorreo);
        rbCorreo.setText("Correo Electrónico");

        bgCriterioBusqueda.add(rbEstado);
        rbEstado.setText("Estado: Activo/Inactivo");

        javax.swing.GroupLayout pnlFiltroClienteLayout = new javax.swing.GroupLayout(pnlFiltroCliente);
        pnlFiltroCliente.setLayout(pnlFiltroClienteLayout);
        pnlFiltroClienteLayout.setHorizontalGroup(
            pnlFiltroClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFiltroClienteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlFiltroClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(rbTelefono)
                    .addComponent(rbCodigoCliente)
                    .addComponent(rbNombre)
                    .addComponent(rbCorreo)
                    .addComponent(rbEstado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlFiltroClienteLayout.setVerticalGroup(
            pnlFiltroClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFiltroClienteLayout.createSequentialGroup()
                .addComponent(rbNombre)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rbCodigoCliente)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rbTelefono)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rbCorreo)
                .addGap(3, 3, 3)
                .addComponent(rbEstado)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlBuscarClienteLayout = new javax.swing.GroupLayout(pnlBuscarCliente);
        pnlBuscarCliente.setLayout(pnlBuscarClienteLayout);
        pnlBuscarClienteLayout.setHorizontalGroup(
            pnlBuscarClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBuscarClienteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlBuscarClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnlBuscarClienteLayout.createSequentialGroup()
                        .addComponent(scpnlTblListadoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 596, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(pnlFiltroCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlBuscarClienteLayout.createSequentialGroup()
                        .addComponent(txtBusquedaCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 596, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnBuscarCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlBuscarClienteLayout.setVerticalGroup(
            pnlBuscarClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBuscarClienteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlBuscarClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBusquedaCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlBuscarClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlBuscarClienteLayout.createSequentialGroup()
                        .addComponent(pnlFiltroCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 146, Short.MAX_VALUE))
                    .addComponent(scpnlTblListadoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        tb_facBusqueda.addTab("Buscar Cliente", pnlBuscarCliente);

        tbListadoProd.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tipo de Producto", "Código", "Variedad Madera", "Medidas", "Proveedor", "Unidades", "Descripcion", "Codigo"
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

        btnBuscarProd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/busqueda.png"))); // NOI18N
        btnBuscarProd.setText("Buscar");

        pnlFiltroProd.setBorder(javax.swing.BorderFactory.createTitledBorder("Especificación de búsqueda:"));

        bgCriterioBusqueda.add(rbNombre1);
        rbNombre1.setText("Tipo de producto");

        bgCriterioBusqueda.add(rbCodigoCliente1);
        rbCodigoCliente1.setText("Código");

        bgCriterioBusqueda.add(rbTelefono1);
        rbTelefono1.setText("Variedad madera");

        bgCriterioBusqueda.add(rbCorreo1);
        rbCorreo1.setText("Medidas");

        bgCriterioBusqueda.add(rbEstado1);
        rbEstado1.setText("Precio");

        javax.swing.GroupLayout pnlFiltroProdLayout = new javax.swing.GroupLayout(pnlFiltroProd);
        pnlFiltroProd.setLayout(pnlFiltroProdLayout);
        pnlFiltroProdLayout.setHorizontalGroup(
            pnlFiltroProdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFiltroProdLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlFiltroProdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(rbTelefono1)
                    .addComponent(rbCodigoCliente1)
                    .addComponent(rbNombre1)
                    .addComponent(rbCorreo1)
                    .addComponent(rbEstado1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(35, Short.MAX_VALUE))
        );
        pnlFiltroProdLayout.setVerticalGroup(
            pnlFiltroProdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFiltroProdLayout.createSequentialGroup()
                .addComponent(rbNombre1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rbCodigoCliente1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rbTelefono1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rbCorreo1)
                .addGap(3, 3, 3)
                .addComponent(rbEstado1)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlBuscarProductoLayout = new javax.swing.GroupLayout(pnlBuscarProducto);
        pnlBuscarProducto.setLayout(pnlBuscarProductoLayout);
        pnlBuscarProductoLayout.setHorizontalGroup(
            pnlBuscarProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBuscarProductoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlBuscarProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlBuscarProductoLayout.createSequentialGroup()
                        .addComponent(scpnlTblListadoProd, javax.swing.GroupLayout.PREFERRED_SIZE, 596, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(pnlFiltroProd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnlBuscarProductoLayout.createSequentialGroup()
                        .addComponent(txtBusquedaProd, javax.swing.GroupLayout.PREFERRED_SIZE, 596, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnBuscarProd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlBuscarProductoLayout.setVerticalGroup(
            pnlBuscarProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBuscarProductoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlBuscarProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBusquedaProd, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscarProd, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlBuscarProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlBuscarProductoLayout.createSequentialGroup()
                        .addComponent(pnlFiltroProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 146, Short.MAX_VALUE))
                    .addComponent(scpnlTblListadoProd, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        tb_facBusqueda.addTab("Buscar Producto", pnlBuscarProducto);

        javax.swing.GroupLayout pnlBusquedaAvanzadaLayout = new javax.swing.GroupLayout(pnlBusquedaAvanzada);
        pnlBusquedaAvanzada.setLayout(pnlBusquedaAvanzadaLayout);
        pnlBusquedaAvanzadaLayout.setHorizontalGroup(
            pnlBusquedaAvanzadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlBusquedaAvanzadaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlBusquedaAvanzadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tb_facBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 799, Short.MAX_VALUE)
                    .addGroup(pnlBusquedaAvanzadaLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnCancelarBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        pnlBusquedaAvanzadaLayout.setVerticalGroup(
            pnlBusquedaAvanzadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlBusquedaAvanzadaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tb_facBusqueda)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancelarBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        tb_facBusqueda.getAccessibleContext().setAccessibleName("BuscarCliente");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlBusquedaAvanzada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlBusquedaAvanzada, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarBusquedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarBusquedaActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCancelarBusquedaActionPerformed

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
    private javax.swing.JButton btnBuscarCliente;
    private javax.swing.JButton btnBuscarProd;
    private javax.swing.JButton btnCancelarBusqueda;
    private javax.swing.JPanel pnlBuscarCliente;
    private javax.swing.JPanel pnlBuscarProducto;
    private javax.swing.JPanel pnlBusquedaAvanzada;
    private javax.swing.JPanel pnlFiltroCliente;
    private javax.swing.JPanel pnlFiltroProd;
    private javax.swing.JRadioButton rbCodigoCliente;
    private javax.swing.JRadioButton rbCodigoCliente1;
    private javax.swing.JRadioButton rbCorreo;
    private javax.swing.JRadioButton rbCorreo1;
    private javax.swing.JRadioButton rbEstado;
    private javax.swing.JRadioButton rbEstado1;
    private javax.swing.JRadioButton rbNombre;
    private javax.swing.JRadioButton rbNombre1;
    private javax.swing.JRadioButton rbTelefono;
    private javax.swing.JRadioButton rbTelefono1;
    private javax.swing.JScrollPane scpnlTblListadoCliente;
    private javax.swing.JScrollPane scpnlTblListadoProd;
    private javax.swing.JTable tbListadoCliente;
    private javax.swing.JTable tbListadoProd;
    private javax.swing.JTabbedPane tb_facBusqueda;
    private javax.swing.JTextField txtBusquedaCliente;
    private javax.swing.JTextField txtBusquedaProd;
    // End of variables declaration//GEN-END:variables
}
