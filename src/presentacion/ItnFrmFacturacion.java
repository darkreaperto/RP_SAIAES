/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacion;

import controladores.CtrAcceso;
import controladores.CtrCliente;
import controladores.CtrFactura;
import controladores.CtrImpuesto;
import controladores.CtrLineaDetalle;
import controladores.CtrMadera;
import java.awt.Container;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import logica.negocio.Cliente;
import logica.negocio.LineaDetalle;
import logica.negocio.Madera;
import logica.servicios.Mensaje;
import util.TipoMensaje;

/**
 * Inicializa la ventana que contiene la información de los facturación.
 * @author aoihanabi
 */
public class ItnFrmFacturacion extends javax.swing.JInternalFrame {

    private static DlgFacBusqueda dialogBusqueda;
    private static DlgFacImpuesto dialogImpuesto;
    private static DlgFacVarios dialogVarios;
    private static ItnFrmFacturacion instancia = null;
    private static CtrAcceso sesionAcc;
    private static CtrFactura ctrFactura;
    private static CtrMadera ctrInventario = new CtrMadera();
    private static CtrCliente ctrCliente = new CtrCliente();
    private static CtrImpuesto ctrImpuesto;
    private static CtrLineaDetalle ctrLineaDetalle;
    private static FrmPrincipal ventanaPrincipal;
    private static ItnFrmCliente modCliente;
    private static ItnFrmInventario modInventario;
    private static Mensaje msg;
    private static ArrayList<Madera> listaProd;
    private static ArrayList<Cliente> listaClientes;
    //private static Madera selectedProd;// = new Madera();
    private static ArrayList<Object> totales = new ArrayList<>();
    private static double precioSinImpuesto = 0.0;
    private static ArrayList<LineaDetalle> lineas = new ArrayList<>();
    public double montoImpuesto;
    public boolean exonerado = false;
    public Object[] facVarios = new Object[2];  //productos varios [descripcion, precio]
    

    /**
     * Instancia un formulario interno de facturación.
     * @param sesion usuario en sesión actual
     * @param clientes Lista con los clientes en la base de datos
     * @param productos Lista con los productos en la bd.
     */
    protected ItnFrmFacturacion(CtrAcceso sesion, ArrayList<Cliente> clientes,
            ArrayList<Madera> productos) {
        initComponents();

        ctrFactura = CtrFactura.getInstancia();
        ItnFrmFacturacion.sesionAcc = sesion;
        ItnFrmFacturacion.listaClientes = clientes;
        ItnFrmFacturacion.listaProd = productos;
        System.out.println("SE " + sesionAcc);
        //lblUsuarioFac.setText(sesionAcc.getUsuario().getNombre());
        ctrImpuesto = new CtrImpuesto();
        ctrLineaDetalle = new CtrLineaDetalle();
        msg = new Mensaje();
    }

    /**
     * Retorna la única instancia de la clase.
     *
     * @param sesionAcc Usuario en sesión actual.
     * @param clientes Lista con los clientes en la base de datos
     * @param productos Lista con los productos en la bd.
     * @return instancia.
     */
    public static ItnFrmFacturacion getInstancia(CtrAcceso sesionAcc,
            ArrayList<Cliente> clientes, ArrayList<Madera> productos) {

        if (instancia == null) {
            instancia = new ItnFrmFacturacion(sesionAcc, clientes, productos);
        }
        return instancia;
    }

    /**
     * Obtener la lista de productos consultados y mostrarla en la lista de la
     * interfaz.
     * @param paramProd Datos del producto para consultar producto en la bd
     * @param codBusq código de clasificación/especificación de búsqueda
     */
    public void llenarListaProductos(String paramProd, int codBusq) {
        listaProd = new ArrayList<>();
        DefaultListModel<Madera> mProductos = new DefaultListModel<>();
        listaProd = ctrInventario.busqAvzProductos(paramProd, codBusq);

        for (Madera m : listaProd) {
            mProductos.addElement(m);
        }
        lsEscogerProd.setModel(mProductos);
    }

    /**
     * Obtener de la lista clietes el cliente ingresado por cédula en el campo
     * de texto
     * @param p cedula del cliente para consultar
     */
    public void llenarListaClientes(String p) {
        JList<Object> lsEscogerCli = new JList();

        ArrayList<Cliente> listaCli; //=  new ArrayList<>();
        DefaultListModel<Object> mClientes = new DefaultListModel<>();
        listaCli = ctrCliente.consultarClientes(p);
        System.out.println("LISTA: " +listaCli.get(0));

        for (Cliente c : listaCli) {
            mClientes.addElement(c);
        }
        //System.out.println(listaProd);
        lsEscogerCli.setModel(mClientes);
        String cli = "ESTIMADO CLIENTE";

        for (Cliente c : listaCli) {
            System.out.println("CED " + c.getCedula());
            if (c.getCedula().equals(p)) {
                cli = c.toString();
                break;
            }
        }
        txtClienteFac.setText(cli);
        lblClienteNom.setText(cli);
        if (!cli.equals("ESTIMADO CLIENTE")) {
            lblMostrarNombreCl.setText(cli);
        } else {
            lblMostrarNombreCl.setText("");
        }
    }

    /**
     * 
     */
    public void accederModuloCliente() {
        try {
            ventanaPrincipal = FrmPrincipal.getInstancia();
            Container frameParent = this.getParent().getParent().getParent().getParent().getParent();
            ventanaPrincipal.accederModulos(frameParent, modCliente, 1);
            modCliente.toFront();
        } catch (Exception e) {
            System.out.println(e.getCause());
        }

    }

    /**
     * 
     */
    public void accederModuloProducto() {
        try {
            ventanaPrincipal = FrmPrincipal.getInstancia();
            Container frameParent = this.getParent().getParent().getParent().getParent().getParent();
            ventanaPrincipal.accederModulos(frameParent, modInventario, 1);
            modInventario.toFront();
        } catch (Exception e) {
            System.out.println(e.getCause());
        }

    }
    
    /**
     * 
     */
    public void abrirVentanaImpuesto() {
        dialogImpuesto = new DlgFacImpuesto(this, true);
        dialogImpuesto.setVisible(true);
    }
    
    /**
     * Verifica si un ítem de la lista(en la interfaz) está
     * seleccionado, luego llama el método escoger producto utilizando el código
     * del ítem seleccionado.
     * @return codigo del producto seleccionado.
     */
    public Madera verificarSeleccionLista() {
        for (int i = 0; i < lsEscogerProd.getModel().getSize(); i++) {
            //si cualquiera de los indices está seleccionado
            if (lsEscogerProd.isSelectedIndex(i)) {
                return lsEscogerProd.getModel().getElementAt(i);
            }
        }
        return null;
    }
    
    /**
     * Obtener los datos pertinentes del producto para realizar los calculos 
     * para la venta.
     */
    public void prepararLinea() {
        
        Madera prodSelected = verificarSeleccionLista();
        
        //si se obtuvo toda la información del producto seleccionado
        if (prodSelected!=null) {
            try {
                int cantTotal = prodSelected.getUnidades();//total de productos en inventario
                int cantSolicitada = Integer.valueOf(
                        txtCantidad.getText().trim());
                double precio = prodSelected.getPrecioXvara(); //precio unitario
                double descuento = 0.0;
                calcularTotales(cantTotal, cantSolicitada, precio, descuento);           
                abrirVentanaImpuesto();

                //Sumar impuestos al precioXcantidad del producto
                double precioConImpuesto = Double.valueOf(totales.get(0).toString())
                        + montoImpuesto;
                //En caso de estar exonerado no se realiza la suma;
                if(exonerado) {
                   precioConImpuesto =  Double.valueOf(totales.get(0).toString());
                   montoImpuesto = 0.0;
                }

                //si los totales se obtuvieron con éxito
                if(!totales.isEmpty()) {
                    agregarLinea(prodSelected, cantSolicitada, precio, 
                            descuento, precioConImpuesto);
                }
            } catch (NumberFormatException ex) {
                msg.mostrarMensaje(JOptionPane.INFORMATION_MESSAGE, 
                    TipoMensaje.NUMBER_FORMAT_EXCEPTION);
                System.out.println("Number exception: " + ex);
            }
            jTableAgregar();
        }
        
    }
    
    public void agregarLinea(Madera prodSelected, int cantSolicitada, 
            double precio, double descuento, double precioConImpuesto) {
        
        int numLinea = lineas.size() + 1;
        String detalle = prodSelected.getTipoProducto() + ": " + 
                prodSelected.getDescTipoMadera() + " " + 
                prodSelected.getMedidas();
        //Crea objeto líneaDetalle
        LineaDetalle linea = new LineaDetalle(numLinea, "04", 
        prodSelected.getCodProducto(), cantSolicitada, 
        "unidades", detalle, precio, 
        Double.valueOf(totales.get(0).toString()),
        descuento, "No se realizó descuento", 
        Double.valueOf(totales.get(2).toString()),
        String.valueOf(ctrImpuesto.getCodImpuesto()), 
                precioConImpuesto);

        lineas.add(linea); //Agregarlos a la tabla en interfaz
        //Crea líneaDetalle en la base de datos
        ctrLineaDetalle.crearLineaDetalle(
                String.valueOf(ctrImpuesto.getCodImpuesto()), 
                numLinea, "04", prodSelected.getCodProducto(),
                cantSolicitada, "unidades", detalle, precio, 
                getPrecioSinImpuesto(), descuento,
                "No se realizó descuento", 
                Double.valueOf(totales.get(0).toString()), 
                precioConImpuesto);
    }
    
    public void agregarLineaVarios() {
        if (facVarios[0] != null && facVarios[1] != null) {
            int numLinea = lineas.size() + 1;
            LineaDetalle linea = new LineaDetalle(numLinea, "04", "99", 1,
                    "unidades", String.valueOf(facVarios[0].toString()), 
                    Double.valueOf(facVarios[1].toString()),
                    Double.valueOf(facVarios[1].toString()),
                    0.0, "No se realizó descuento",
                    Double.valueOf(facVarios[1].toString()), "1",
                    Double.valueOf(facVarios[1].toString()));
            
            lineas.add(linea);
            
            ctrLineaDetalle.crearLineaDetalle("1",numLinea, "04", "99", 1, 
                        "unidades", String.valueOf(facVarios[0].toString()), 
                        Double.valueOf(facVarios[1].toString()),
                        Double.valueOf(facVarios[1].toString()), 
                        0.0, "No se realizó descuento", 
                        Double.valueOf(facVarios[1].toString()), 
                        Double.valueOf(facVarios[1].toString()));
        }
        jTableAgregar();
    }
    
    public void jTableAgregar() {
        System.out.println("LINEAS SIZE: "+ lineas.size());
        
        Object[] row = new Object[7];
        DefaultTableModel model = (DefaultTableModel) tblLineaPedido.getModel();
        model.setRowCount(0);
        model.setColumnCount(7);
            
        for (int i = 0; i<lineas.size(); i++) {

            row[0] = lineas.get(i).getDetalle();
            row[1] = lineas.get(i).getUnidadMedida(); 
            row[2] = lineas.get(i).getCantidad();
            row[3] = lineas.get(i).getPrecioUnitario();
            row[4] = montoImpuesto;
            row[5] = lineas.get(i).getSubtotal();
            row[6] = lineas.get(i).getMontoTotalLinea();

            model.addRow(row);
            
        }
    }
    
    /**
     * Realiza los cálculos correspondientes referentes al precio total de la
     * venta y el subtotal; además obtiene la cantidad restante 
     * en inventario del producto seleccionado para actualizar la bd.
     * @param cantTotal total de unidades del produto existentes en inventario.
     * @param cantSolicitada cantidad de unidades del producto solicitada por el
     * comprador.
     * @param precio precio unitario del producto.
     * @param descuento monto de descuento (en caso de aplicarse).
     * @return Lista con los totales resultantes trans hacer los calculos
     * correspondientes.
     */
    public ArrayList calcularTotales(int cantTotal, int cantSolicitada,
            double precio, double descuento) {
        try {
            //Si la cantidad solicitada no excede la existente en inventario
            if (cantTotal >= cantSolicitada) {
                double precioTotal = precio * cantSolicitada;
                int cantRestante = cantTotal - cantSolicitada;
                double subtotal = precioTotal - descuento;

                totales.add(precioTotal);
                precioSinImpuesto = precioTotal;
                System.out.println("PRECIO SIN: " + precioSinImpuesto);
                totales.add(cantRestante);
                totales.add(subtotal);
            } else {
                msg.mostrarMensaje(JOptionPane.ERROR_MESSAGE,
                        TipoMensaje.PRODUCT_AMOUNT_EXCEEDED);
            }
            System.out.println("0: "+totales.get(0));
            System.out.println("1: "+totales.get(1));
            System.out.println("2: "+totales.get(2));
        } catch(Exception ex) {
            msg.mostrarMensaje(JOptionPane.ERROR_MESSAGE,
                        TipoMensaje.TOTALS_CALCULATION_FAILURE);
        } finally {
            return totales;
        }
        
    }
    public double getPrecioSinImpuesto() {
        return precioSinImpuesto;
    }
    
   public void limpiarCampos() {
       txtProducto.setText("Código del producto...");
       txtCantidad.setText("Cantidad en unidades...");
       lsEscogerProd.clearSelection();
   }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnl_modFactura = new javax.swing.JPanel();
        lblTextProducto = new javax.swing.JLabel();
        lblTextCantidad = new javax.swing.JLabel();
        lblUsuarioFac = new javax.swing.JLabel();
        lblTextUsuario = new javax.swing.JLabel();
        lblTextClienteFac = new javax.swing.JLabel();
        lblClienteNom = new javax.swing.JLabel();
        txtProducto = new javax.swing.JTextField();
        txtCantidad = new javax.swing.JTextField();
        txtClienteFac = new javax.swing.JTextField();
        ftClienteFac = new javax.swing.JFormattedTextField();
        scpnlList = new javax.swing.JScrollPane();
        lsEscogerProd = new javax.swing.JList<>();
        scpnlTblLineaPedido = new javax.swing.JScrollPane();
        tblLineaPedido = new javax.swing.JTable();
        pnlTotales = new javax.swing.JPanel();
        lblTextSubtotal = new javax.swing.JLabel();
        lblTextTotal = new javax.swing.JLabel();
        lblSubtotal = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        btnFacturar = new javax.swing.JButton();
        pnlInfoCliente = new javax.swing.JPanel();
        lblMostrarNombreCl = new javax.swing.JLabel();
        btnMostrarClien = new javax.swing.JButton();
        lblMostrarCedulaCl = new javax.swing.JLabel();
        lblMostrarTelefonoCl = new javax.swing.JLabel();
        lblMostrarCorreoCl = new javax.swing.JLabel();
        lblMostrarCreditoCl = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        btnAddProduct = new javax.swing.JButton();
        btnBusquedaAv = new javax.swing.JButton();
        btnCrearCliente = new javax.swing.JButton();
        btnBuscarCliente = new javax.swing.JButton();
        btnAddProduct1 = new javax.swing.JButton();
        btnAddImpuesto = new javax.swing.JButton();
        btnAgregarVarios = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setTitle("Módulo de Facturación");
        setPreferredSize(new java.awt.Dimension(1240, 670));

        lblTextProducto.setText("Producto:");

        lblTextCantidad.setText("Cantidad:");

        lblUsuarioFac.setToolTipText("Usuario en sesión.");
        lblUsuarioFac.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.SystemColor.activeCaption));

        lblTextUsuario.setText("Facturado por: ");

        lblTextClienteFac.setText("Cliente:");

        lblClienteNom.setText("CLIENTE GENÉRICO");
        lblClienteNom.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.SystemColor.activeCaption));

        txtProducto.setText("Codigo del producto...");
        txtProducto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtProductoFocusGained(evt);
            }
        });
        txtProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtProductoKeyReleased(evt);
            }
        });

        txtCantidad.setText("Cantidad en unidades...");
        txtCantidad.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCantidadFocusGained(evt);
            }
        });

        txtClienteFac.setText("Nombre del cliente...");
        txtClienteFac.setSelectionStart(0);
        txtClienteFac.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtClienteFacFocusGained(evt);
            }
        });
        txtClienteFac.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtClienteFacKeyReleased(evt);
            }
        });

        try {
            ftClienteFac.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("#-####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        ftClienteFac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ftClienteFacActionPerformed(evt);
            }
        });
        ftClienteFac.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                ftClienteFacKeyReleased(evt);
            }
        });

        lsEscogerProd.setForeground(new java.awt.Color(102, 102, 102));
        lsEscogerProd.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        scpnlList.setViewportView(lsEscogerProd);

        tblLineaPedido.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Descripción", "Medida", "Cantidad", "Prec. Unidad", "Impuesto", "Subtotal", "Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblLineaPedido.getTableHeader().setReorderingAllowed(false);
        scpnlTblLineaPedido.setViewportView(tblLineaPedido);

        pnlTotales.setBorder(new javax.swing.border.LineBorder(javax.swing.UIManager.getDefaults().getColor("Button.darkShadow"), 1, true));

        lblTextSubtotal.setText("Subtotal:");

        lblTextTotal.setText("Total:");

        lblSubtotal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSubtotal.setToolTipText("Usuario en sesión.");
        lblSubtotal.setBorder(javax.swing.BorderFactory.createLineBorder(javax.swing.UIManager.getDefaults().getColor("Button.shadow")));

        lblTotal.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblTotal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTotal.setToolTipText("Usuario en sesión.");
        lblTotal.setBorder(javax.swing.BorderFactory.createLineBorder(javax.swing.UIManager.getDefaults().getColor("Button.shadow")));

        btnFacturar.setText("Emitir Factura");

        javax.swing.GroupLayout pnlTotalesLayout = new javax.swing.GroupLayout(pnlTotales);
        pnlTotales.setLayout(pnlTotalesLayout);
        pnlTotalesLayout.setHorizontalGroup(
            pnlTotalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlTotalesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTextSubtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblSubtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTextTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnFacturar, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlTotalesLayout.setVerticalGroup(
            pnlTotalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlTotalesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlTotalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlTotalesLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(pnlTotalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblSubtotal, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnlTotalesLayout.createSequentialGroup()
                                .addComponent(lblTextSubtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(2, 2, 2))
                            .addGroup(pnlTotalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblTextTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnFacturar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );

        pnlInfoCliente.setBorder(javax.swing.BorderFactory.createTitledBorder("Información del Cliente"));

        lblMostrarNombreCl.setForeground(new java.awt.Color(153, 153, 153));
        lblMostrarNombreCl.setText(" Nombre del Cliente");
        lblMostrarNombreCl.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.SystemColor.activeCaption));

        btnMostrarClien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/desplegar.png"))); // NOI18N
        btnMostrarClien.setText("Mostrar");
        btnMostrarClien.setBorder(null);
        btnMostrarClien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMostrarClienActionPerformed(evt);
            }
        });

        lblMostrarCedulaCl.setForeground(new java.awt.Color(153, 153, 153));
        lblMostrarCedulaCl.setText(" Cédula del Cliente");
        lblMostrarCedulaCl.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.SystemColor.activeCaption));

        lblMostrarTelefonoCl.setForeground(new java.awt.Color(153, 153, 153));
        lblMostrarTelefonoCl.setText(" Teléfono");
        lblMostrarTelefonoCl.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.SystemColor.activeCaption));

        lblMostrarCorreoCl.setForeground(new java.awt.Color(153, 153, 153));
        lblMostrarCorreoCl.setText(" Correo Electrónico");
        lblMostrarCorreoCl.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.SystemColor.activeCaption));

        lblMostrarCreditoCl.setForeground(new java.awt.Color(153, 153, 153));
        lblMostrarCreditoCl.setText(" Crédito");
        lblMostrarCreditoCl.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.SystemColor.activeCaption));

        javax.swing.GroupLayout pnlInfoClienteLayout = new javax.swing.GroupLayout(pnlInfoCliente);
        pnlInfoCliente.setLayout(pnlInfoClienteLayout);
        pnlInfoClienteLayout.setHorizontalGroup(
            pnlInfoClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInfoClienteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlInfoClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnMostrarClien, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlInfoClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(lblMostrarTelefonoCl, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblMostrarCedulaCl, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addGroup(pnlInfoClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblMostrarCorreoCl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblMostrarNombreCl, javax.swing.GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE)
                    .addComponent(lblMostrarCreditoCl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlInfoClienteLayout.setVerticalGroup(
            pnlInfoClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInfoClienteLayout.createSequentialGroup()
                .addGroup(pnlInfoClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnMostrarClien, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMostrarCreditoCl, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlInfoClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMostrarNombreCl, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMostrarCedulaCl, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlInfoClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMostrarTelefonoCl, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMostrarCorreoCl, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jSeparator3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 255)));

        btnAddProduct.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/f_add.png"))); // NOI18N
        btnAddProduct.setToolTipText("Agregar producto a la linea");
        btnAddProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddProductActionPerformed(evt);
            }
        });

        btnBusquedaAv.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/busqueda.png"))); // NOI18N
        btnBusquedaAv.setText("Búsqueda Avanzada");
        btnBusquedaAv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBusquedaAvActionPerformed(evt);
            }
        });

        btnCrearCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/f_crearCliente.png"))); // NOI18N
        btnCrearCliente.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnCrearCliente.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        btnCrearCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCrearClienteActionPerformed(evt);
            }
        });

        btnBuscarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/busqueda.png"))); // NOI18N
        btnBuscarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarClienteActionPerformed(evt);
            }
        });

        btnAddProduct1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/f_crearProducto.png"))); // NOI18N
        btnAddProduct1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddProduct1ActionPerformed(evt);
            }
        });

        btnAddImpuesto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/add_impuesto.png"))); // NOI18N
        btnAddImpuesto.setToolTipText("Agregar impuesto");
        btnAddImpuesto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddImpuestoActionPerformed(evt);
            }
        });

        btnAgregarVarios.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/add.png"))); // NOI18N
        btnAgregarVarios.setText("Agregar productos varios");
        btnAgregarVarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarVariosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnl_modFacturaLayout = new javax.swing.GroupLayout(pnl_modFactura);
        pnl_modFactura.setLayout(pnl_modFacturaLayout);
        pnl_modFacturaLayout.setHorizontalGroup(
            pnl_modFacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(pnl_modFacturaLayout.createSequentialGroup()
                .addGroup(pnl_modFacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_modFacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnl_modFacturaLayout.createSequentialGroup()
                            .addGap(23, 23, 23)
                            .addGroup(pnl_modFacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(pnl_modFacturaLayout.createSequentialGroup()
                                    .addComponent(lblTextClienteFac, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(lblClienteNom, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(pnl_modFacturaLayout.createSequentialGroup()
                                    .addComponent(lblTextUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(pnl_modFacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lblUsuarioFac, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(pnl_modFacturaLayout.createSequentialGroup()
                                            .addComponent(ftClienteFac, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(txtClienteFac, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(pnl_modFacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(btnBuscarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnCrearCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(pnlInfoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(34, 34, 34))
                        .addGroup(pnl_modFacturaLayout.createSequentialGroup()
                            .addGap(19, 19, 19)
                            .addGroup(pnl_modFacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(pnl_modFacturaLayout.createSequentialGroup()
                                    .addComponent(btnBusquedaAv, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(pnlTotales, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(scpnlTblLineaPedido)
                                .addGroup(pnl_modFacturaLayout.createSequentialGroup()
                                    .addGroup(pnl_modFacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(scpnlList, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnl_modFacturaLayout.createSequentialGroup()
                                            .addComponent(lblTextProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(txtProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(btnAddProduct1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGap(95, 95, 95)
                                    .addComponent(lblTextCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 61, Short.MAX_VALUE)
                                    .addComponent(btnAgregarVarios)))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(pnl_modFacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(btnAddProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnAddImpuesto, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(4, 4, 4)))
                    .addGroup(pnl_modFacturaLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 1194, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnl_modFacturaLayout.setVerticalGroup(
            pnl_modFacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_modFacturaLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(pnl_modFacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_modFacturaLayout.createSequentialGroup()
                        .addComponent(lblTextClienteFac, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(59, 59, 59)
                        .addGroup(pnl_modFacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblUsuarioFac, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblTextUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnl_modFacturaLayout.createSequentialGroup()
                        .addGroup(pnl_modFacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lblClienteNom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnCrearCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(pnl_modFacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnl_modFacturaLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnBuscarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_modFacturaLayout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addGroup(pnl_modFacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(ftClienteFac, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtClienteFac, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addComponent(pnlInfoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnl_modFacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_modFacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(lblTextProducto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAddProduct1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGroup(pnl_modFacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblTextCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(txtProducto))
                    .addComponent(btnAgregarVarios, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnl_modFacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(scpnlList, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAddProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(pnl_modFacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_modFacturaLayout.createSequentialGroup()
                        .addComponent(scpnlTblLineaPedido, javax.swing.GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnl_modFacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pnlTotales, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnBusquedaAv, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnl_modFacturaLayout.createSequentialGroup()
                        .addComponent(btnAddImpuesto, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnl_modFactura, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnl_modFactura, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtProductoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtProductoKeyReleased
        llenarListaProductos(txtProducto.getText(), 5);//5=código búsqueda
    }//GEN-LAST:event_txtProductoKeyReleased

    private void txtProductoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtProductoFocusGained
        if (!(evt.getSource() instanceof JTextField)) {
            return;
        }
        txtProducto = (JTextField) evt.getSource();
        txtProducto.selectAll();
    }//GEN-LAST:event_txtProductoFocusGained

    private void txtClienteFacFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtClienteFacFocusGained
        if (!(evt.getSource() instanceof JTextField)) {
            return;
        }
        txtClienteFac = (JTextField) evt.getSource();
        txtClienteFac.selectAll();
    }//GEN-LAST:event_txtClienteFacFocusGained

    private void txtCantidadFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCantidadFocusGained
        if (!(evt.getSource() instanceof JTextField)) {
            return;
        }
        txtCantidad = (JTextField) evt.getSource();
        txtCantidad.selectAll();
    }//GEN-LAST:event_txtCantidadFocusGained

    private void txtClienteFacKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtClienteFacKeyReleased
        System.out.println("EVT " + evt.getKeyCode());
        if (evt.getKeyCode() == 10) { //enter
            System.out.println("EVT");
            llenarListaClientes(txtClienteFac.getText().trim());
        }
    }//GEN-LAST:event_txtClienteFacKeyReleased

    private void ftClienteFacKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ftClienteFacKeyReleased
        System.out.println("EVT " + evt.getKeyCode());
        if (evt.getKeyCode() == 10) { //enter
            System.out.println("EVT");
            llenarListaClientes(ftClienteFac.getText().replace("-", "").trim());
        }
    }//GEN-LAST:event_ftClienteFacKeyReleased

    private void ftClienteFacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ftClienteFacActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ftClienteFacActionPerformed

    private void btnBusquedaAvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBusquedaAvActionPerformed
        dialogBusqueda = new DlgFacBusqueda(this, true);
        dialogBusqueda.setVisible(true);
    }//GEN-LAST:event_btnBusquedaAvActionPerformed

    private void btnBuscarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnBuscarClienteActionPerformed

    private void btnCrearClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearClienteActionPerformed
        modCliente = ItnFrmCliente.getInstancia(sesionAcc, listaClientes);
        accederModuloCliente();
    }//GEN-LAST:event_btnCrearClienteActionPerformed

    private void btnMostrarClienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMostrarClienActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnMostrarClienActionPerformed

    private void btnAddProduct1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddProduct1ActionPerformed
        modInventario = ItnFrmInventario.getInstancia(sesionAcc, listaProd);
        accederModuloProducto();
    }//GEN-LAST:event_btnAddProduct1ActionPerformed

    private void btnAddImpuestoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddImpuestoActionPerformed
        abrirVentanaImpuesto();
    }//GEN-LAST:event_btnAddImpuestoActionPerformed

    private void btnAgregarVariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarVariosActionPerformed
        dialogVarios = new DlgFacVarios(this, true);
        dialogVarios.setVisible(true);
        agregarLineaVarios();
    }//GEN-LAST:event_btnAgregarVariosActionPerformed

    private void btnAddProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddProductActionPerformed
        prepararLinea();
        limpiarCampos();
    }//GEN-LAST:event_btnAddProductActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddImpuesto;
    private javax.swing.JButton btnAddProduct;
    private javax.swing.JButton btnAddProduct1;
    private javax.swing.JButton btnAgregarVarios;
    private javax.swing.JButton btnBuscarCliente;
    private javax.swing.JButton btnBusquedaAv;
    private javax.swing.JButton btnCrearCliente;
    private javax.swing.JButton btnFacturar;
    private javax.swing.JButton btnMostrarClien;
    private javax.swing.JFormattedTextField ftClienteFac;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JLabel lblClienteNom;
    private javax.swing.JLabel lblMostrarCedulaCl;
    private javax.swing.JLabel lblMostrarCorreoCl;
    private javax.swing.JLabel lblMostrarCreditoCl;
    private javax.swing.JLabel lblMostrarNombreCl;
    private javax.swing.JLabel lblMostrarTelefonoCl;
    private javax.swing.JLabel lblSubtotal;
    private javax.swing.JLabel lblTextCantidad;
    private javax.swing.JLabel lblTextClienteFac;
    private javax.swing.JLabel lblTextProducto;
    private javax.swing.JLabel lblTextSubtotal;
    private javax.swing.JLabel lblTextTotal;
    private javax.swing.JLabel lblTextUsuario;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JLabel lblUsuarioFac;
    private javax.swing.JList<Madera> lsEscogerProd;
    private javax.swing.JPanel pnlInfoCliente;
    private javax.swing.JPanel pnlTotales;
    private javax.swing.JPanel pnl_modFactura;
    private javax.swing.JScrollPane scpnlList;
    private javax.swing.JScrollPane scpnlTblLineaPedido;
    private javax.swing.JTable tblLineaPedido;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtClienteFac;
    private javax.swing.JTextField txtProducto;
    // End of variables declaration//GEN-END:variables
}
