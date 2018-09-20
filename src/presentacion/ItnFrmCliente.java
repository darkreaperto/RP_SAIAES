/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacion;

import controladores.CtrAcceso;
import controladores.CtrCliente;
import java.util.ArrayList;
import logica.Cliente;

/**
 * Inicializa la ventana que contiene la información de los clientes.
 * @author ahoihanabi
 */
public class ItnFrmCliente extends javax.swing.JInternalFrame {
    
    private static ItnFrmCliente instancia = null;
    private static CtrCliente controlador;
    
    private static ArrayList<Cliente> clientes;
    private static CtrAcceso sesion;
    
    /**
     * Instancia un nuevo formulario interno de usuario.
     * @param sesionAcc Usuario en sesión actual 
     * @param clientes Lista con los clientes en la base de datos
     */
    protected ItnFrmCliente(CtrAcceso sesionAcc, ArrayList<Cliente> clientes) {
        initComponents();
        //Inicializar variables
        controlador = CtrCliente.getInstancia();
        
        ItnFrmCliente.clientes = clientes;
        ItnFrmCliente.sesion = sesionAcc;
        //cargarTablas();
        //verificacion = new Regex();
        //msg = new Mensaje();
    }
    
    /**
     * Retorna la única instancia de la clase.
     *
     * @param sesionAcc Usuario en sesión actual.
     * @param clientes Lista de clientes en la base de datos.
     * @return instancia.
     */
    public static ItnFrmCliente getInstancia(CtrAcceso sesionAcc,
            ArrayList<Cliente> clientes) {
        if (instancia == null) {
            instancia = new ItnFrmCliente(sesionAcc, clientes);
        }
        return instancia;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnl_modCliente = new javax.swing.JPanel();
        tb_modCliente = new javax.swing.JTabbedPane();
        pnl_listado = new javax.swing.JPanel();
        pnl_agregar = new javax.swing.JPanel();
        lbl_crear_nombreCliente = new javax.swing.JLabel();
        txt_crear_nombreCliente = new javax.swing.JTextField();
        txt_crear_nombreCliente1 = new javax.swing.JTextField();
        lbl_crear_apellidoCliente1 = new javax.swing.JLabel();
        txt_crear_nombreCliente2 = new javax.swing.JTextField();
        lbl_crear_apellidoCliente2 = new javax.swing.JLabel();
        lbl_crear_cedulaCliente = new javax.swing.JLabel();
        txt_crear_cedulaCliente = new javax.swing.JTextField();
        lbl_crear_limiteCliente = new javax.swing.JLabel();
        txt_crear_limiteCliente = new javax.swing.JTextField();
        pnl_crear_creditoCliente = new javax.swing.JPanel();
        rb_crear_sinCredito = new javax.swing.JRadioButton();
        rb_crear_conCredito = new javax.swing.JRadioButton();
        spnl_crear_clientes = new javax.swing.JScrollPane();
        tbl_crear = new javax.swing.JTable();
        btn_deshabilitar = new javax.swing.JButton();
        pnl_crear_creditoCliente1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        txt_crear_telefono = new javax.swing.JTextField();
        lbl_crear_correo1 = new javax.swing.JLabel();
        btn_deshabilitar3 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        txt_crear_correo = new javax.swing.JTextField();
        btn_deshabilitar2 = new javax.swing.JButton();
        lbl_crear_correo = new javax.swing.JLabel();
        pnl_actualizar = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();

        setPreferredSize(new java.awt.Dimension(1240, 693));

        javax.swing.GroupLayout pnl_listadoLayout = new javax.swing.GroupLayout(pnl_listado);
        pnl_listado.setLayout(pnl_listadoLayout);
        pnl_listadoLayout.setHorizontalGroup(
            pnl_listadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1172, Short.MAX_VALUE)
        );
        pnl_listadoLayout.setVerticalGroup(
            pnl_listadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 580, Short.MAX_VALUE)
        );

        tb_modCliente.addTab("Listado Clientes", pnl_listado);

        lbl_crear_nombreCliente.setText("Nombre:");

        lbl_crear_apellidoCliente1.setText("Primer Apellido:");

        lbl_crear_apellidoCliente2.setText("Segundo Apellido:");

        lbl_crear_cedulaCliente.setText("Cédula:");

        lbl_crear_limiteCliente.setText("Límite de crédito:");

        txt_crear_limiteCliente.setEnabled(false);

        pnl_crear_creditoCliente.setBorder(javax.swing.BorderFactory.createTitledBorder("Crédito de Cliente:"));

        rb_crear_sinCredito.setText("Sin crédigo");

        rb_crear_conCredito.setText("Con crédito");

        javax.swing.GroupLayout pnl_crear_creditoClienteLayout = new javax.swing.GroupLayout(pnl_crear_creditoCliente);
        pnl_crear_creditoCliente.setLayout(pnl_crear_creditoClienteLayout);
        pnl_crear_creditoClienteLayout.setHorizontalGroup(
            pnl_crear_creditoClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_crear_creditoClienteLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rb_crear_conCredito)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(rb_crear_sinCredito)
                .addGap(19, 19, 19))
        );
        pnl_crear_creditoClienteLayout.setVerticalGroup(
            pnl_crear_creditoClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_crear_creditoClienteLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnl_crear_creditoClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rb_crear_conCredito)
                    .addComponent(rb_crear_sinCredito)))
        );

        tbl_crear.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cédula", "Nombre", "Primer Apellido", "Segundo Apellido", "Teléfono", "Correo?", "Límite de Crédito"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        spnl_crear_clientes.setViewportView(tbl_crear);
        if (tbl_crear.getColumnModel().getColumnCount() > 0) {
            tbl_crear.getColumnModel().getColumn(0).setResizable(false);
            tbl_crear.getColumnModel().getColumn(1).setResizable(false);
            tbl_crear.getColumnModel().getColumn(2).setResizable(false);
            tbl_crear.getColumnModel().getColumn(3).setResizable(false);
            tbl_crear.getColumnModel().getColumn(4).setResizable(false);
            tbl_crear.getColumnModel().getColumn(5).setResizable(false);
            tbl_crear.getColumnModel().getColumn(6).setResizable(false);
        }

        btn_deshabilitar.setText("Crear Cliente");
        btn_deshabilitar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deshabilitarActionPerformed(evt);
            }
        });

        pnl_crear_creditoCliente1.setBorder(javax.swing.BorderFactory.createTitledBorder("Contacto:"));
        pnl_crear_creditoCliente1.setAutoscrolls(true);

        jPanel3.setRequestFocusEnabled(false);

        lbl_crear_correo1.setText("Teléfono:");

        btn_deshabilitar3.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        btn_deshabilitar3.setText("+");
        btn_deshabilitar3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deshabilitar3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_crear_correo1)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(txt_crear_telefono, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_deshabilitar3, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(lbl_crear_correo1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_crear_telefono, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_deshabilitar3, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btn_deshabilitar2.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        btn_deshabilitar2.setText("+");
        btn_deshabilitar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deshabilitar2ActionPerformed(evt);
            }
        });

        lbl_crear_correo.setText("Correo Electrónico:");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(txt_crear_correo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_deshabilitar2, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(lbl_crear_correo)
                .addGap(0, 186, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addComponent(lbl_crear_correo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_crear_correo, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_deshabilitar2, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnl_crear_creditoCliente1Layout = new javax.swing.GroupLayout(pnl_crear_creditoCliente1);
        pnl_crear_creditoCliente1.setLayout(pnl_crear_creditoCliente1Layout);
        pnl_crear_creditoCliente1Layout.setHorizontalGroup(
            pnl_crear_creditoCliente1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_crear_creditoCliente1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_crear_creditoCliente1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnl_crear_creditoCliente1Layout.setVerticalGroup(
            pnl_crear_creditoCliente1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_crear_creditoCliente1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(76, 76, 76))
        );

        javax.swing.GroupLayout pnl_agregarLayout = new javax.swing.GroupLayout(pnl_agregar);
        pnl_agregar.setLayout(pnl_agregarLayout);
        pnl_agregarLayout.setHorizontalGroup(
            pnl_agregarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_agregarLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(pnl_agregarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(spnl_crear_clientes)
                    .addGroup(pnl_agregarLayout.createSequentialGroup()
                        .addGroup(pnl_agregarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lbl_crear_cedulaCliente)
                            .addComponent(txt_crear_cedulaCliente, javax.swing.GroupLayout.DEFAULT_SIZE, 294, Short.MAX_VALUE)
                            .addComponent(lbl_crear_nombreCliente)
                            .addComponent(txt_crear_nombreCliente, javax.swing.GroupLayout.DEFAULT_SIZE, 294, Short.MAX_VALUE)
                            .addComponent(pnl_crear_creditoCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn_deshabilitar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 61, Short.MAX_VALUE)
                        .addGroup(pnl_agregarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_crear_apellidoCliente1)
                            .addComponent(txt_crear_nombreCliente1, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_crear_apellidoCliente2)
                            .addComponent(txt_crear_nombreCliente2, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_crear_limiteCliente)
                            .addComponent(txt_crear_limiteCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(59, 59, 59)
                        .addComponent(pnl_crear_creditoCliente1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(77, 77, 77))
        );
        pnl_agregarLayout.setVerticalGroup(
            pnl_agregarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_agregarLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(pnl_agregarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_agregarLayout.createSequentialGroup()
                        .addGroup(pnl_agregarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(pnl_agregarLayout.createSequentialGroup()
                                .addComponent(lbl_crear_cedulaCliente)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_crear_cedulaCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(23, 23, 23)
                                .addComponent(lbl_crear_nombreCliente)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_crear_nombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(pnl_crear_creditoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_agregarLayout.createSequentialGroup()
                                .addGroup(pnl_agregarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnl_agregarLayout.createSequentialGroup()
                                        .addGap(71, 71, 71)
                                        .addComponent(lbl_crear_apellidoCliente2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txt_crear_nombreCliente2, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(pnl_agregarLayout.createSequentialGroup()
                                        .addComponent(lbl_crear_apellidoCliente1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txt_crear_nombreCliente1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addComponent(lbl_crear_limiteCliente)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_crear_limiteCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(btn_deshabilitar, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(pnl_crear_creditoCliente1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addComponent(spnl_crear_clientes, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnl_crear_creditoCliente1.getAccessibleContext().setAccessibleName("Contacto:");

        tb_modCliente.addTab("Agregar cliente", pnl_agregar);

        javax.swing.GroupLayout pnl_actualizarLayout = new javax.swing.GroupLayout(pnl_actualizar);
        pnl_actualizar.setLayout(pnl_actualizarLayout);
        pnl_actualizarLayout.setHorizontalGroup(
            pnl_actualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1172, Short.MAX_VALUE)
        );
        pnl_actualizarLayout.setVerticalGroup(
            pnl_actualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 580, Short.MAX_VALUE)
        );

        tb_modCliente.addTab("Editar cliente", pnl_actualizar);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1172, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 580, Short.MAX_VALUE)
        );

        tb_modCliente.addTab("Habilitar clientes", jPanel1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1172, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 580, Short.MAX_VALUE)
        );

        tb_modCliente.addTab("Límite de crédito", jPanel2);

        javax.swing.GroupLayout pnl_modClienteLayout = new javax.swing.GroupLayout(pnl_modCliente);
        pnl_modCliente.setLayout(pnl_modClienteLayout);
        pnl_modClienteLayout.setHorizontalGroup(
            pnl_modClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_modClienteLayout.createSequentialGroup()
                .addContainerGap(46, Short.MAX_VALUE)
                .addComponent(tb_modCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 1177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(67, 67, 67))
        );
        pnl_modClienteLayout.setVerticalGroup(
            pnl_modClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_modClienteLayout.createSequentialGroup()
                .addContainerGap(40, Short.MAX_VALUE)
                .addComponent(tb_modCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 607, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(77, 77, 77))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnl_modCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnl_modCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_deshabilitarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deshabilitarActionPerformed
     
    }//GEN-LAST:event_btn_deshabilitarActionPerformed

    private void btn_deshabilitar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deshabilitar2ActionPerformed
        jPanel4.setSize(jPanel4.getWidth(), jPanel4.getHeight() + txt_crear_correo.getHeight());
        
    }//GEN-LAST:event_btn_deshabilitar2ActionPerformed

    private void btn_deshabilitar3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deshabilitar3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_deshabilitar3ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_deshabilitar;
    private javax.swing.JButton btn_deshabilitar2;
    private javax.swing.JButton btn_deshabilitar3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel lbl_crear_apellidoCliente1;
    private javax.swing.JLabel lbl_crear_apellidoCliente2;
    private javax.swing.JLabel lbl_crear_cedulaCliente;
    private javax.swing.JLabel lbl_crear_correo;
    private javax.swing.JLabel lbl_crear_correo1;
    private javax.swing.JLabel lbl_crear_limiteCliente;
    private javax.swing.JLabel lbl_crear_nombreCliente;
    private javax.swing.JPanel pnl_actualizar;
    private javax.swing.JPanel pnl_agregar;
    private javax.swing.JPanel pnl_crear_creditoCliente;
    private javax.swing.JPanel pnl_crear_creditoCliente1;
    private javax.swing.JPanel pnl_listado;
    private javax.swing.JPanel pnl_modCliente;
    private javax.swing.JRadioButton rb_crear_conCredito;
    private javax.swing.JRadioButton rb_crear_sinCredito;
    private javax.swing.JScrollPane spnl_crear_clientes;
    private javax.swing.JTabbedPane tb_modCliente;
    private javax.swing.JTable tbl_crear;
    private javax.swing.JTextField txt_crear_cedulaCliente;
    private javax.swing.JTextField txt_crear_correo;
    private javax.swing.JTextField txt_crear_limiteCliente;
    private javax.swing.JTextField txt_crear_nombreCliente;
    private javax.swing.JTextField txt_crear_nombreCliente1;
    private javax.swing.JTextField txt_crear_nombreCliente2;
    private javax.swing.JTextField txt_crear_telefono;
    // End of variables declaration//GEN-END:variables
}