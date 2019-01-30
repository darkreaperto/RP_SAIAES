/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacion;

import controladores.CtrExoneracion;
import controladores.CtrImpuesto;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.JOptionPane;
import logica.negocio.Exoneracion;
import logica.negocio.Impuesto;
import logica.servicios.Mensaje;
import util.TipoMensaje;

/**
 * Inicializa la ventana de dialog que contiene la información de exoneraciones.
 * @author aoihanabi
 */
public class DlgFacImpuesto extends javax.swing.JDialog {

    public static ItnFrmFacturacion ifrmFacturacion;
    private static DlgFacExoneracion dialogExoneracion;
    private static CtrImpuesto ctrImpuesto;
    private static CtrExoneracion ctrExoneracion;
    private static Mensaje msg;

    /**
     * Creates new form DlgFacImpuesto.
     * @param parent ventana padre de este Jdialog
     * @param modal establece si la ventana permite acceso a otras mientras está
     * abierta.
     */
    public DlgFacImpuesto(ItnFrmFacturacion parent /*java.awt.Frame parent*/,
            boolean modal) {
        //super(parent, modal);
        this.ifrmFacturacion = parent;
        this.setModal(modal);
        initComponents();
        setLocationRelativeTo(this);
        msg = new Mensaje();
        ctrImpuesto = new CtrImpuesto();
        ctrExoneracion = new CtrExoneracion();
        pnlExoneracion.setVisible(false);
    }
    /**
     * 
     * @return 
     */
    public double getPrecio() {
        return ifrmFacturacion.getPrecioSinImpuesto();
    }
    /**
     * 
     * @return 
     */
    public double calcularImpuesto() {
        double porcentajeImpuesto;
        double valorImpuesto = 0.0;
        try{
            if(!txtImpuesto.getText().isEmpty()) {
                porcentajeImpuesto = Double.valueOf(txtImpuesto.getText())/100;
                valorImpuesto = getPrecio() * porcentajeImpuesto;
                
            } else {
                msg.mostrarMensaje(JOptionPane.INFORMATION_MESSAGE, 
                        TipoMensaje.EMPTY_TEXT_FIELD);
            }
        } catch (NumberFormatException ex) {
            msg.mostrarMensaje(JOptionPane.INFORMATION_MESSAGE, 
                    TipoMensaje.WRONG_DECIMAL_NUMBER);
            System.out.println("Number exception: " + ex);
        } catch (Exception ex) {
            msg.mostrarMensaje(JOptionPane.INFORMATION_MESSAGE,
                    TipoMensaje.SOMETHING_WENT_WRONG);
            System.out.println("Exception: " + ex);
        } finally {
            return valorImpuesto;
        }
    }

    /**
     * Obtiene el código del tipo de impuesto seleccionado en la interfaz.
     * @return 
     */
    public String codImpuestosHac() {
        String codigo = "98";
        if(rbGeneralVentas.isSelected()) {
            codigo = "01";
        } else if (rbSelectivoConsumo.isSelected()) {
            codigo = "02";
        } else if(rbServicio.isSelected()) {
            codigo = "07";
        }
        return codigo;
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
     * Crea el impuesto con la información pertinente para ser enviado a 
     * facturación.
     * @return 
     */
    public Impuesto crearImpuesto() {
        
        if(!txtImpuesto.getText().isEmpty()) {
            if(calcularImpuesto() > 0.0) {
                
                if(ckbExonerar.isSelected()) {
                    if(verificarExoneracion()) {
                        
                        Impuesto impuesto = new Impuesto("1", codImpuestosHac(), 
                        Double.valueOf(txtImpuesto.getText())/100, 
                        calcularImpuesto(), crearExoneracion());
                        
                        return impuesto;
                    }
                    
                } else {
                    Impuesto impuesto = new Impuesto("1", codImpuestosHac(), 
                        Double.valueOf(txtImpuesto.getText())/100, 
                        calcularImpuesto(), null);
                    
                    return impuesto;
                }                
//                ctrImpuesto.crearImpuesto(codImpuestosHac(), 
//                        Double.valueOf(txtImpuesto.getText())/100, 
//                        calcularImpuesto());
                this.dispose();
            } else {
                msg.mostrarMensaje(JOptionPane.INFORMATION_MESSAGE, 
                    TipoMensaje.TAX_MISSING);
            }
        } else {
            msg.mostrarMensaje(JOptionPane.INFORMATION_MESSAGE, 
                    TipoMensaje.EMPTY_TEXT_FIELD);
        }
        return null;
    }
    /**
     * Devuelve la fecha actual.
     * @return 
     */
    public static Timestamp noow() {
        Calendar cal = Calendar.getInstance();
        Timestamp sqlDate = new Timestamp(cal.getTimeInMillis());
        return sqlDate;
        
    }
    
    /**
     * Impresión prueba: obtiene la fecha de las exoneraciones y las imprime en 
     * el formato de la documentación de Hacienda.
     */
    public void fchformatHacienda() {
        
        ArrayList<Timestamp> fechas = ctrExoneracion.obtenerExoneraciones();
        SimpleDateFormat formatter1 = new SimpleDateFormat("YYYY-MM-dd");
        SimpleDateFormat formatter2 = new SimpleDateFormat("hh:mm:ss[Z|(+|-)hh:mm]");
        
        for(int i = 0; i < fechas.size(); i++) {
            String dateInString = formatter1.format(fechas.get(i)) + "T" +
                    formatter2.format(fechas.get(i).getTime());
            System.out.println("FORMATTED DATE: "+dateInString);
        }
    }
    
    /**
     * Verifica que los campos con la información de la exoneración estén 
     * correctamente ingresados.
     * @return 
     */
    public boolean verificarExoneracion() {
        boolean exito = false;
        try {
            double prctCompra = Integer.valueOf(txtPorcentajeCompra.getText());

            if(!txtPorcentajeCompra.getText().trim().isEmpty() && 
                    !txtNombreInstitucion.getText().trim().isEmpty() && 
                    !txtNumeroDoc.getText().trim().isEmpty()) {
                
                exito = true;
            } else {
                msg.mostrarMensaje(JOptionPane.ERROR_MESSAGE, 
                        TipoMensaje.EMPTY_TEXT_FIELD);                    
            }            
        } catch (NumberFormatException ex) {
            msg.mostrarMensaje(JOptionPane.ERROR_MESSAGE,
                    TipoMensaje.NUMBER_FORMAT_EXCEPTION);
            txtPorcentajeCompra.setText("");
        } catch (Exception ex) {
            msg.mostrarMensaje(JOptionPane.ERROR_MESSAGE,
                    TipoMensaje.SOMETHING_WENT_WRONG);
            txtPorcentajeCompra.setText("");
        }
        return exito;
    }
    /**
     * Calcula y retorna el monto que se exonerará del impuesto.
     * @return monto que se exonerará del impuesto.
     */
    public double calcularExoneracion() {
        int porcentCompra;
        double valorExonerar = 0.0;
        try{
            if(!txtImpuesto.getText().isEmpty()) {
                porcentCompra = Integer.valueOf(txtPorcentajeCompra.getText())/100;
                valorExonerar = calcularImpuesto() * porcentCompra;
                
            } else {
                msg.mostrarMensaje(JOptionPane.INFORMATION_MESSAGE, 
                        TipoMensaje.EMPTY_TEXT_FIELD);
            }
        } catch (NumberFormatException ex) {
            msg.mostrarMensaje(JOptionPane.INFORMATION_MESSAGE, 
                    TipoMensaje.WRONG_DECIMAL_NUMBER);
            System.out.println("Number exception: " + ex);
        } catch (Exception ex) {
            msg.mostrarMensaje(JOptionPane.INFORMATION_MESSAGE,
                    TipoMensaje.SOMETHING_WENT_WRONG);
            System.out.println("Exception: " + ex);
        } finally {
            return valorExonerar;
        }
    }
    /**
     * Crea la exoneración con el monto de impuesto especificado.
     * @return la exoneración creada
     */
    public Exoneracion crearExoneracion() {
        String numDoc = txtNumeroDoc.getText();
        String institucion = txtNombreInstitucion.getText();
        
        Exoneracion exoneracion = new Exoneracion(tipoDocExoneracion(), numDoc, 
                institucion, noow(), calcularExoneracion(), 
                Integer.valueOf(txtPorcentajeCompra.getText()));
        
        return exoneracion;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgTipoImpuesto = new javax.swing.ButtonGroup();
        bgTipoExoneracion = new javax.swing.ButtonGroup();
        pnlPrincipal = new javax.swing.JPanel();
        pnlImpuesto = new javax.swing.JPanel();
        pnlTipoImpuesto = new javax.swing.JPanel();
        rbGeneralVentas = new javax.swing.JRadioButton();
        rbServicio = new javax.swing.JRadioButton();
        rbOtro = new javax.swing.JRadioButton();
        rbSelectivoConsumo = new javax.swing.JRadioButton();
        txtImpuesto = new javax.swing.JTextField();
        lblMontoImpuesto = new javax.swing.JLabel();
        lblTextTarifaImpuesto = new javax.swing.JLabel();
        lblTextMontoImpuesto = new javax.swing.JLabel();
        pnlExoneracion = new javax.swing.JPanel();
        pnlTipoExoneracion = new javax.swing.JPanel();
        rbCompraAutorizada = new javax.swing.JRadioButton();
        rbVentaDiplomatico = new javax.swing.JRadioButton();
        rbOrdenCompra = new javax.swing.JRadioButton();
        rbExencionesHacienda = new javax.swing.JRadioButton();
        rbZonaFranca = new javax.swing.JRadioButton();
        rbOtros = new javax.swing.JRadioButton();
        lblTextMontoImpExonerado = new javax.swing.JLabel();
        lblTextPorcentCompra = new javax.swing.JLabel();
        txtPorcentajeCompra = new javax.swing.JTextField();
        lblMontoImpExonerado = new javax.swing.JLabel();
        lblTextNomInstitucion = new javax.swing.JLabel();
        txtNombreInstitucion = new javax.swing.JTextField();
        txtNumeroDoc = new javax.swing.JTextField();
        lblTextNumDoc = new javax.swing.JLabel();
        ckbExonerar = new javax.swing.JCheckBox();
        btnAceptarImpEx = new javax.swing.JButton();
        btnCancelarImpEx = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Agregar impuesto");

        pnlImpuesto.setBorder(javax.swing.BorderFactory.createTitledBorder("Impuesto:"));

        pnlTipoImpuesto.setBorder(javax.swing.BorderFactory.createTitledBorder("Tipo de impuesto:"));

        bgTipoImpuesto.add(rbGeneralVentas);
        rbGeneralVentas.setText("General sobre ventas");

        bgTipoImpuesto.add(rbServicio);
        rbServicio.setText("Servicio");

        bgTipoImpuesto.add(rbOtro);
        rbOtro.setSelected(true);
        rbOtro.setText("Otros");

        bgTipoImpuesto.add(rbSelectivoConsumo);
        rbSelectivoConsumo.setText("Selectivo de consumo");

        javax.swing.GroupLayout pnlTipoImpuestoLayout = new javax.swing.GroupLayout(pnlTipoImpuesto);
        pnlTipoImpuesto.setLayout(pnlTipoImpuestoLayout);
        pnlTipoImpuestoLayout.setHorizontalGroup(
            pnlTipoImpuestoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTipoImpuestoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlTipoImpuestoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rbOtro)
                    .addComponent(rbServicio)
                    .addComponent(rbGeneralVentas)
                    .addComponent(rbSelectivoConsumo))
                .addContainerGap(39, Short.MAX_VALUE))
        );
        pnlTipoImpuestoLayout.setVerticalGroup(
            pnlTipoImpuestoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTipoImpuestoLayout.createSequentialGroup()
                .addComponent(rbGeneralVentas)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rbSelectivoConsumo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 1, Short.MAX_VALUE)
                .addComponent(rbServicio)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rbOtro)
                .addGap(26, 26, 26))
        );

        txtImpuesto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtImpuestoKeyReleased(evt);
            }
        });

        lblMontoImpuesto.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.SystemColor.activeCaption));

        lblTextTarifaImpuesto.setText("Tarifa %");

        lblTextMontoImpuesto.setText("Monto:");

        javax.swing.GroupLayout pnlImpuestoLayout = new javax.swing.GroupLayout(pnlImpuesto);
        pnlImpuesto.setLayout(pnlImpuestoLayout);
        pnlImpuestoLayout.setHorizontalGroup(
            pnlImpuestoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlImpuestoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlImpuestoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlTipoImpuesto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlImpuestoLayout.createSequentialGroup()
                        .addGroup(pnlImpuestoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lblTextTarifaImpuesto, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
                            .addComponent(txtImpuesto, javax.swing.GroupLayout.Alignment.LEADING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlImpuestoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTextMontoImpuesto, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblMontoImpuesto, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        pnlImpuestoLayout.setVerticalGroup(
            pnlImpuestoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlImpuestoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlTipoImpuesto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 139, Short.MAX_VALUE)
                .addGroup(pnlImpuestoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTextTarifaImpuesto, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTextMontoImpuesto, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlImpuestoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtImpuesto, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMontoImpuesto, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pnlExoneracion.setBorder(javax.swing.BorderFactory.createTitledBorder("Exoneración:"));

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
                .addContainerGap(18, Short.MAX_VALUE)
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

        lblTextMontoImpExonerado.setText("Monto Exonerado:");
        lblTextMontoImpExonerado.setOpaque(true);

        lblTextPorcentCompra.setText("Porcentaje Compra:");
        lblTextPorcentCompra.setToolTipText("");
        lblTextPorcentCompra.setOpaque(true);

        lblMontoImpExonerado.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.SystemColor.activeCaption));
        lblMontoImpExonerado.setOpaque(true);

        lblTextNomInstitucion.setText("Nombre Institución:");
        lblTextNomInstitucion.setToolTipText("");
        lblTextNomInstitucion.setOpaque(true);

        lblTextNumDoc.setText("Número de documento:");
        lblTextNumDoc.setToolTipText("");
        lblTextNumDoc.setOpaque(true);

        javax.swing.GroupLayout pnlExoneracionLayout = new javax.swing.GroupLayout(pnlExoneracion);
        pnlExoneracion.setLayout(pnlExoneracionLayout);
        pnlExoneracionLayout.setHorizontalGroup(
            pnlExoneracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlExoneracionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlExoneracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlExoneracionLayout.createSequentialGroup()
                        .addComponent(pnlTipoExoneracion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlExoneracionLayout.createSequentialGroup()
                        .addGroup(pnlExoneracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblTextNumDoc, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtNombreInstitucion, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTextNomInstitucion, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtNumeroDoc, javax.swing.GroupLayout.Alignment.LEADING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlExoneracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblTextPorcentCompra, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                            .addComponent(txtPorcentajeCompra)
                            .addComponent(lblTextMontoImpExonerado, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblMontoImpExonerado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(256, 256, 256))))
        );
        pnlExoneracionLayout.setVerticalGroup(
            pnlExoneracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlExoneracionLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlTipoExoneracion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlExoneracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTextNomInstitucion, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTextPorcentCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlExoneracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNombreInstitucion, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPorcentajeCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlExoneracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlExoneracionLayout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(lblTextMontoImpExonerado, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE))
                    .addComponent(lblTextNumDoc, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlExoneracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNumeroDoc, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(lblMontoImpExonerado, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12))
        );

        ckbExonerar.setText("Exonerar");
        ckbExonerar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ckbExonerarActionPerformed(evt);
            }
        });

        btnAceptarImpEx.setText("Aceptar");
        btnAceptarImpEx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarImpExActionPerformed(evt);
            }
        });

        btnCancelarImpEx.setText("Cancelar");
        btnCancelarImpEx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarImpExActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlPrincipalLayout = new javax.swing.GroupLayout(pnlPrincipal);
        pnlPrincipal.setLayout(pnlPrincipalLayout);
        pnlPrincipalLayout.setHorizontalGroup(
            pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPrincipalLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlPrincipalLayout.createSequentialGroup()
                        .addComponent(btnCancelarImpEx, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAceptarImpEx, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlPrincipalLayout.createSequentialGroup()
                        .addComponent(pnlImpuesto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(pnlExoneracion, javax.swing.GroupLayout.PREFERRED_SIZE, 421, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(ckbExonerar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        pnlPrincipalLayout.setVerticalGroup(
            pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPrincipalLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlExoneracion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlImpuesto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ckbExonerar)
                .addGap(27, 27, 27)
                .addGroup(pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAceptarImpEx, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelarImpEx, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 2, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarImpExActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarImpExActionPerformed
        
        this.dispose();
    }//GEN-LAST:event_btnCancelarImpExActionPerformed

    private void btnAceptarImpExActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarImpExActionPerformed
        Impuesto imp = crearImpuesto();
        
        if (imp != null) {
            ifrmFacturacion.impuesto = imp;
        }
        this.dispose();
    }//GEN-LAST:event_btnAceptarImpExActionPerformed

    private void txtImpuestoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtImpuestoKeyReleased
        //String porcentaje = txtImpuesto.getText();
        double valor = 0;
        try {
            valor = calcularImpuesto();
        } catch (NumberFormatException ex) {
            msg.mostrarMensaje(JOptionPane.INFORMATION_MESSAGE, TipoMensaje.WRONG_DECIMAL_NUMBER);
            System.out.println("Number exception: " + ex);
        }
        lblMontoImpuesto.setText(String.valueOf(valor));
    }//GEN-LAST:event_txtImpuestoKeyReleased

    private void ckbExonerarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ckbExonerarActionPerformed
        if(ckbExonerar.isSelected()) {
            pnlExoneracion.setVisible(true);
        } else {
           pnlExoneracion.setVisible(false);
        }
    }//GEN-LAST:event_ckbExonerarActionPerformed

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
            java.util.logging.Logger.getLogger(DlgFacImpuesto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DlgFacImpuesto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DlgFacImpuesto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DlgFacImpuesto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DlgFacImpuesto dialog = new DlgFacImpuesto(ifrmFacturacion, true);
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
    private javax.swing.ButtonGroup bgTipoImpuesto;
    private javax.swing.JButton btnAceptarImpEx;
    private javax.swing.JButton btnCancelarImpEx;
    private javax.swing.JCheckBox ckbExonerar;
    private javax.swing.JLabel lblMontoImpExonerado;
    private javax.swing.JLabel lblMontoImpuesto;
    private javax.swing.JLabel lblTextMontoImpExonerado;
    private javax.swing.JLabel lblTextMontoImpuesto;
    private javax.swing.JLabel lblTextNomInstitucion;
    private javax.swing.JLabel lblTextNumDoc;
    private javax.swing.JLabel lblTextPorcentCompra;
    private javax.swing.JLabel lblTextTarifaImpuesto;
    private javax.swing.JPanel pnlExoneracion;
    private javax.swing.JPanel pnlImpuesto;
    private javax.swing.JPanel pnlPrincipal;
    private javax.swing.JPanel pnlTipoExoneracion;
    private javax.swing.JPanel pnlTipoImpuesto;
    private javax.swing.JRadioButton rbCompraAutorizada;
    private javax.swing.JRadioButton rbExencionesHacienda;
    private javax.swing.JRadioButton rbGeneralVentas;
    private javax.swing.JRadioButton rbOrdenCompra;
    private javax.swing.JRadioButton rbOtro;
    private javax.swing.JRadioButton rbOtros;
    private javax.swing.JRadioButton rbSelectivoConsumo;
    private javax.swing.JRadioButton rbServicio;
    private javax.swing.JRadioButton rbVentaDiplomatico;
    private javax.swing.JRadioButton rbZonaFranca;
    private javax.swing.JTextField txtImpuesto;
    private javax.swing.JTextField txtNombreInstitucion;
    private javax.swing.JTextField txtNumeroDoc;
    private javax.swing.JTextField txtPorcentajeCompra;
    // End of variables declaration//GEN-END:variables
}
