/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacion;

import controladores.CtrAcceso;
import controladores.CtrCliente;
import controladores.CtrEmisor;
import controladores.CtrFactura;
import controladores.CtrImpuesto;
import controladores.CtrLineaDetalle;
import controladores.CtrMadera;
import java.awt.Container;
import java.awt.Font;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import logica.negocio.Cliente;
import logica.negocio.Consecutivo;
import logica.negocio.Emisor;
import logica.negocio.FacEncabezado;
import logica.negocio.FacResumen;
import logica.negocio.Factura;
import logica.negocio.Impuesto;
import logica.negocio.LineaDetalle;
import logica.negocio.Madera;
import logica.servicios.Mensaje;
import util.TipoContacto;
import util.TipoMensaje;
import util.TextPrompt;

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
    private static CtrEmisor ctrEmisor;
    private static CtrLineaDetalle ctrLineaDetalle;
    private static FrmPrincipal ventanaPrincipal;
    private static ItnFrmCliente modCliente;
    private static ItnFrmInventario modInventario;
    private static Mensaje msg;
    private static ArrayList<Madera> listaProd;
    private static ArrayList<Cliente> listaClientes;
    private static ArrayList<Consecutivo> consecutivos;
    //private static Madera selectedProd;// = new Madera();
    private static ArrayList<Object> totales;
    private static double precioSinImpuesto = 0.0;
    private static ArrayList<LineaDetalle> lineas = new ArrayList<>();
    public Impuesto impuesto;
    public Factura factura;
    private Emisor emisor;
    private TextPrompt placeholder;
    public boolean exonerado = false;
    

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
        ctrEmisor = CtrEmisor.getInstancia();
        msg = new Mensaje();
        factura = new Factura();
        
        //ctrFactura.crearFacResumen("CRC", 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        preparaEncabezado("01");
        llenarListaProductos("", 5);
        llenarComboClientes(new Cliente(), "");
    }

    /**
     * Retorna la única instancia de la clase.
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
        
        try {
            listaProd = ctrInventario.busqAvzProductos(paramProd, codBusq);
        } catch (SQLException ex) {
            System.err.print("Error: SQL Exception "+ex.getMessage());
        } catch (Exception ex) {
            System.err.print("Error: Exception "+ex.getMessage());
        }

        for (Madera m : listaProd) {
            mProductos.addElement(m);
        }
        lsEscogerProd.setModel(mProductos);
    }

    /**
     * 
     * @param cli Objeto Cliente para mostrar en el combo
     * @param nomOrCed parámetro para búsqueda, el nombre o cédula del cliente
     * @return La lista con los clientes que coinciden con la consulta
     */
    public ArrayList<Cliente> llenarComboClientes(Cliente cli, String nomOrCed) {
        cbxClientes.removeAllItems();
        
        ArrayList<Cliente> listaCli = ctrCliente.consultarClientes(nomOrCed);
        System.out.println("sizeCLi "+listaCli.size());
        for (int i = 0; i<listaCli.size(); i++) {
            cbxClientes.addItem(listaCli.get(i));
        }
        for (int i = 0; i<listaCli.size(); i++) {            
            
            if (listaCli.get(i).getCedula().equals(nomOrCed)) {
                cli = listaCli.get(i);
                break;
//            } else if (listaCli.get(i).getNombre().concat(" "+
//                    listaCli.get(i).getApellido1()).concat(" "+
//                            listaCli.get(i).getApellido2()).toUpperCase().
//                    contains(p.toUpperCase())) {
//                
//                cli = listaCli.get(i);
//                //cli.setNombre(listaCli.get(i).getNombre());
//                System.out.println(cli.getNombre());
//                cbxClientes.setSelectedItem(cli);
//                break;
            }
        }
        return listaCli;
    }
    /**
     * Obtener de la lista clietes el cliente ingresado por cédula en el campo
     * de texto
     * @param p parámetro con la cedula o nombre del cliente a consultar
     */
    public void llenarListaClientes(String p) {
        
        //cbxClientes.showPopup(); //Desplegar el combo con los clientes
        
        Cliente cli = new Cliente();
        cli.setNombre("CLIENTE GENÉRICO");
        System.out.println("Nombre del cliente: " + p);
        
        //Llenar combo con clientes
        ArrayList<Cliente> listaCli = llenarComboClientes(cli, p);
        
        cli = listaCli.size()>0 ? listaCli.get(0) : cli;
        //cuando la size sea 0 indicar que no se encontró un usuario.
        if(listaCli.size() > 0) {
            
            //cbxClientes.setSelectedItem(cli);
            System.out.println("Cli nombre: " + cli.getNombre());
            lblClienteNom.setText(cli.getNombre());

            //ESTO ERA PARA EL PANEL QUE HABÍA CON INFO EXTRA DEL CLIENTE
//            if (!cli.getNombre().equals("CLIENTE GENÉRICO")) {
//    //            lblMostrarNombreCl.setText(cli.getNombre()+" "+cli.getApellido1() 
//    //                    +" "+ cli.getApellido2());
//
//                lblClienteNom.setText(cli.getNombre());
//                ftClienteFac.setText(cli.getCedula());
//    //            lblMostrarCedulaCl.setText(cli.getCedula());
//    //            lblMostrarCreditoCl.setText(String.valueOf(cli.isAprobarCredito()));
//                for(int i = 0; i < cli.getContactos().size(); i++) {
//                    TipoContacto tContacto = cli.getContactos().get(i).getTipo();
//
//                    if(tContacto == TipoContacto.CORREO) {
//                        lblMostrarCorreoCl.setText(cli.getContactos().get(i).getInfo());
//                        break;
//                    } else {
//                        lblMostrarTelefonoCl.setText(cli.getContactos().get(i).getInfo());
//                        break;
//                    }
//                }
//                //CAMBIAR FORMA DE MOTRAR CORREO Y TELEFONO DEL CLIENTE?
//    //            lblMostrarTelefonoCl.setText(cli.getContactos());
//    //            lblMostrarCorreoCl.setText(p);
//            } else {
//                lblMostrarNombreCl.setText("Nombre del Cliente");
//                lblMostrarCedulaCl.setText("Cédula del Cliente");
//                lblMostrarCreditoCl.setText("Crédito");
//            }
        } else {
            //Limpiar campos si no encuentre el cliente
            ftClienteFac.setText("");
            txtNomCliente.setText("");
            llenarComboClientes(new Cliente(), "");
            msg.mostrarMensaje(JOptionPane.INFORMATION_MESSAGE, TipoMensaje.CUSTOMER_NOT_FOUND);
        }
    }

    /**
     * Obtener la lista de productos consultados y eliminar el producto solicitado
     */
    public void eliminarProducto() {
//        try {
            int filaSelect = tblLineaPedido.getSelectedRow();
            System.out.println("fac -> eliminar prod() - > selected row: " + (filaSelect));
            System.out.println("listaDetalle size: " + tblLineaPedido.getRowCount());
            tblLineaPedido.remove(1);
//        } catch(Exception e) {
//            msg.mostrarMensaje(JOptionPane.ERROR_MESSAGE, TipoMensaje.ANY_ROW_SELECTED);
//        }
        System.out.println("listaDetalle size: " + tblLineaPedido.getRowCount());
        
//        for(int i = 0; i<listaProd.size(); i++) {
//            System.out.println(listaProd.get(i).getDescripcion());
//        }
//        System.out.println("---------------------------------------------");
//        listaProd.remove(filaSelect);
//        for(int i = 0; i<listaProd.size(); i++) {
//            System.out.println(listaProd.get(i).getDescripcion());
//        }
        
//        DefaultListModel<Madera> mProductos = new DefaultListModel<>();
//        listaProd = ctrInventario.busqAvzProductos(paramProd, codBusq);
//        for (Madera m : listaProd) {
//            mProductos.addElement(m);
//        }
//        lsEscogerProd.setModel(mProductos);
    }
    /**
     * Abre el módulo de clientes, específicamente en la pestaña agregar
     * clientes para permitir el rápido acceso a la creación de un nuevo cliente.
     */
    public void accederModuloCliente() {
        try {
            ventanaPrincipal = FrmPrincipal.getInstancia();
            Container frameParent = this.getParent().getParent().getParent()
                    .getParent().getParent();
            ventanaPrincipal.accederModulos(frameParent, modCliente, 1);
            modCliente.toFront();
        } catch (Exception e) {
            System.out.println(e.getCause());
        }
    }

    /**
     * Abre el módulo de producto, específicamente en la pestaña agregar
     * productos para permitir el rápido acceso a la creación de un nuevo 
     * producto.
     */
    public void accederModuloProducto() {
        try {
            ventanaPrincipal = FrmPrincipal.getInstancia();
            Container frameParent = this.getParent().getParent().getParent()
                    .getParent().getParent();
            ventanaPrincipal.accederModulos(frameParent, modInventario, 1);
            modInventario.toFront();
        } catch (Exception e) {
            System.out.println(e.getCause());
        }
    }
    
    public void buscarCliente() {
        try {
            String cedula = ftClienteFac.getText().replace("-", "").trim();
            String nombre = txtNomCliente.getText();
            System.out.println("cedula " + cedula + " y nombre " + nombre);
            if(!nombre.isEmpty()) {
                llenarListaClientes(nombre);
            } else if(!cedula.isEmpty()) {
                llenarListaClientes(cedula);
            }
        } catch (Exception ex) {
            msg.mostrarMensaje(JOptionPane.ERROR_MESSAGE, TipoMensaje.EMPTY_TEXT_FIELD);
            
        }
    }
    /**
     * Abre la ventana de impuestos para permitir especificarlo.
     */
    public void abrirVentanaImpuesto() {
        dialogImpuesto = new DlgFacImpuesto(this, true);
        dialogImpuesto.setVisible(true);
    }
    
    /**
     * Verifica si un ítem de la lista (en la interfaz) está
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
    
//    /**
//     * Realiza los cálculos correspondientes referentes al precio total de la
//     * venta y el subtotal; además obtiene la cantidad restante 
//     * en inventario del producto seleccionado para actualizar la bd.
//     * @param cantTotal total de unidades del produto existentes en inventario.
//     * @param cantSolicitada cantidad de unidades del producto solicitada por el
//     * comprador.
//     * @param precio precio unitario del producto.
//     * @param descuento monto de descuento (en caso de aplicarse).
//     * @return Lista con los totales resultantes trans hacer los calculos
//     * correspondientes.
//     */
//    public ArrayList calcularTotales(int cantTotal, int cantSolicitada,
//            double precio, double descuento) {
//        
//        totales = new ArrayList<>();
//        try {
//            //Si la cantidad solicitada no excede la existente en inventario
//            if (cantTotal >= cantSolicitada) {
//                double precioTotal = precio * cantSolicitada;
//                int cantRestante = cantTotal - cantSolicitada;
//                double subtotal = precioTotal - descuento;
//
//                totales.add(precioTotal);
//                precioSinImpuesto = precioTotal;
//                System.out.println("PRECIO SIN: " + precioSinImpuesto);
//                totales.add(cantRestante);
//                totales.add(subtotal);
//            } else {
//                msg.mostrarMensaje(JOptionPane.ERROR_MESSAGE,
//                        TipoMensaje.PRODUCT_AMOUNT_EXCEEDED);
//            }
//            System.out.println("0: "+totales.get(0));
//            System.out.println("1: "+totales.get(1));
//            System.out.println("2: "+totales.get(2));
//        } catch(Exception ex) {
//            msg.mostrarMensaje(JOptionPane.ERROR_MESSAGE,
//                        TipoMensaje.TOTALS_CALCULATION_FAILURE);
//        } finally {
//            return totales;
//        }
//    }
    
    /**
     * Obtener los datos pertinentes del producto para realizar los calculos 
     * para la venta.
     */
    public void prepararLinea() {
        
        Madera prodSelected = verificarSeleccionLista();
        
        //si se obtuvo toda la información del producto seleccionado
        if (prodSelected!=null) {
            try {
                int cantSolicitada = Integer.valueOf(
                        txtCantidad.getText().trim());
                double precio = prodSelected.getPrecioXvara(); //precio unitario
                double descuento = 0.0;
                
                precioSinImpuesto = precio * cantSolicitada;        
                abrirVentanaImpuesto();

                if (impuesto != null) {
                    agregarLinea(prodSelected, cantSolicitada, precio, descuento);

                    jTableAgregar();
                    calcularSubtotalTotal();
                    limpiarCampos(false);
                }
                
            } catch (NumberFormatException ex) {
                msg.mostrarMensaje(JOptionPane.INFORMATION_MESSAGE, 
                    TipoMensaje.NUMBER_FORMAT_EXCEPTION);
                System.out.println("Number exception: " + ex);
            }
            
        } else {
            msg.mostrarMensaje(JOptionPane.ERROR_MESSAGE, 
                    TipoMensaje.EMPTY_TEXT_FIELD);
        }
    }
    
    /**
     * Agrega una linea de detalle como objeto y a la base de datos.
     * @param prodSelected producto seleccionado en la interfaz
     * @param cantSolicitada cantidad solicitada por el cliente
     * @param precioUni precio unitario del producto solicitado
     * @param descuento descuento del producto
     */
    public void agregarLinea(Madera prodSelected, int cantSolicitada, 
            double precioUni, double descuento) {
        
        int numLinea = factura.getLineasDetalle().size() + 1; 
        String detalle = prodSelected.getTipoProducto() + ": " + 
                prodSelected.getDescTipoMadera() + " " + 
                prodSelected.getGrueso() + "x" + prodSelected.getAncho();
        
        LineaDetalle linea = new LineaDetalle(numLinea, "04", 
                prodSelected.getCodProducto(), cantSolicitada, 
                "unidades", detalle, precioUni,
                descuento, "No se realizó descuento",
                impuesto, true);

        //Agregar las lineas creadas a la lista de lineas en Factura 
        factura.getLineasDetalle().add(linea);
        
        //Limpiar variables globales
        impuesto = null;
        precioSinImpuesto = 0;
    }
    
    /**
     * Verifica que la información ingresada sea correcta para crear la línea
     * de detalle.
     */
    public void prepararLineaVarios() {
        
        String descripcion = txtDescripcionVarios.getText().trim();
        String precio = txtPrecioVarios.getText().trim();
        boolean mercancia = rbMercancia.isSelected();
        
        if (!descripcion.isEmpty() && validarPrecio(precio)) {
            
            precioSinImpuesto = Double.valueOf(precio);
            abrirVentanaImpuesto();
            
            agregarLineaVarios(descripcion, Double.parseDouble(precio), 
                    impuesto, mercancia);
            
            jTableAgregar();
            calcularSubtotalTotal();
            limpiarCampos(false);
        } else {
            msg.mostrarMensaje(JOptionPane.ERROR_MESSAGE, 
                    TipoMensaje.EMPTY_TEXT_FIELD);
        }
    }
    
    /**
     * Agrega una linea de detalle "varios" (producto no definido, 
     * como transporte o cepillados) como objeto y a la base de datos.
     * @param descripcion descripción del producto o servicio a vender
     * @param precio precio total de producto o servicio
     * @param imp impuesto de la linea
     * @param mercancia true si se trata de mercancia o false si es un servicio
     */
    public void agregarLineaVarios(String descripcion, double precio, 
            Impuesto imp, boolean mercancia) {
        
        int numLinea = factura.getLineasDetalle().size() + 1;
        LineaDetalle linea = new LineaDetalle(numLinea, "04", "99", 1,
                "unidades", descripcion, precio, 0.0, 
                "No se realizó descuento", imp, mercancia);

        factura.getLineasDetalle().add(linea);
            
        //Limpiar variables globales
        impuesto = null;
        precioSinImpuesto = 0;
        
    }
    
    /**
     * Validar si el precio ingresado es un número decimal válido.
     * @param precio precio a revisar
     * @return true si cumple, false si no
     */
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
     * Calcular el subtotal y el total acumulados de todas as líneas y mostrarlo 
     * en la interfaz.
     */
    private void calcularSubtotalTotal() {
        
       double subtotal = 0;
       double total = 0;
       
       for (LineaDetalle l: factura.getLineasDetalle()) {
           subtotal += l.getSubtotal();
           total += l.getMontoTotalLinea();
       }
       
       lblSubtotal.setText(String.valueOf(subtotal));
       lblTotal.setText(String.valueOf(total));
    }
    
    /**
     * Agregar productos a la tabla en la interfaz para mostrar las lineas.
     */
    public void jTableAgregar() {
        System.out.println("LINEAS SIZE: "+ factura.getLineasDetalle().size());
        
        Object[] row = new Object[8];
        DefaultTableModel model = (DefaultTableModel) tblLineaPedido.getModel();
        model.setRowCount(0);
        model.setColumnCount(7);
        
        for (int i = 0; i<factura.getLineasDetalle().size(); i++) {
            
            row[0] = factura.getLineasDetalle().get(i).getDetalle();
            row[1] = factura.getLineasDetalle().get(i).getUnidadMedida(); 
            row[2] = factura.getLineasDetalle().get(i).getCantidad();
            row[3] = factura.getLineasDetalle().get(i).getPrecioUnitario();
            row[4] = factura.getLineasDetalle().get(i).getImpuesto().getMontoImpuesto();
            row[5] = factura.getLineasDetalle().get(i).getSubtotal();
            row[6] = factura.getLineasDetalle().get(i).getMontoTotalLinea();

            model.addRow(row);
        }        
    }
    
    /**
     * Obtener el precio de un producto sin el impuesto incluido.
     * @return el precio sin el impuesto.
     */
    public double getPrecioSinImpuesto() {
        return precioSinImpuesto;
    }
    
    /**
     * Limpiar los campos de la interfaz de facturación.
     * @param cliente verdadero si se quiere limpiar los datos del cliente y 
     * su linea de detalle
     */
   public void limpiarCampos(boolean cliente) {
       if (pnlAgregarProd.isVisible()) {
            txtProducto.setText("Código del producto...");
            txtCantidad.setText("Cantidad en unidades...");
            lblCantExistencia.setText("");
            lblPrecioUnit.setText("");
            lsEscogerProd.clearSelection();
        } else {
            txtDescripcionVarios.setText("");
            txtPrecioVarios.setText("");
            rbMercancia.setSelected(true);
        }
       
       if(cliente) {
           lblClienteNom.setText("CLIENTE GENÉRICO");
           ftClienteFac.setText("");
           txtNomCliente.setText("");
           cbxClientes.removeAllItems();
           tblLineaPedido.removeAll();
       }
       
    }
    
   /**
    * Realiza la inserción en la base de datos de todas las lineas de detalle 
    * actuales a facturar.
    */
   public void crearLinea() {
        for(LineaDetalle li : factura.getLineasDetalle()) {
            ctrLineaDetalle.crearLineaDetalle(li.getImpuesto(), 
                    li.getNumeroLinea(), li.getTipoCodProducto(), 
                    li.getCodigoProducto(), li.getCantidad(),
                    li.getUnidadMedida(), li.getDetalle(), 
                    li.getPrecioUnitario(), li.getTotal(), 
                    li.getDescuento(), li.getNaturalezaDescuento(), 
                    li.getSubtotal(), li.getMontoTotalLinea(), 
                    li.isMercancia());
        }
        
        jTableAgregar();
    }
   
    private String formatearConsecutivo(String tipoComprob, int consec) {
        
        String casaMatriz = "001";
        String terminal = "00001";
        
        return casaMatriz + terminal + tipoComprob + 
                String.format("%010d", consec);
    }
    
    private String formatearClave(String numId, String consec, String sit) {
        
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT-06:00"));
                
        String codPais = "506";
        String dia = String.format("%02d", cal.get(Calendar.DAY_OF_MONTH));
        String mes = String.format("%02d", cal.get(Calendar.MONTH)+1);
        String anno = String.valueOf(cal.get(Calendar.YEAR)).substring(2, 4);
        //codigo random (no importa si se repite, el punto es que no sea predecible)
        Random r = new Random();
        String codSeg = String.format("%08d", 1+r.nextInt(99999999));
        
        return codPais + dia + mes + anno + 
                String.format("%012d", Integer.valueOf(numId)) + 
                consec + sit + codSeg;
    }
    
    /**
     * Preparar la información del encabezado y retornarlo.
     * @param codComprob código de tipo de comprobante
     * @return el encabezado de la factura
     */
    public FacEncabezado preparaEncabezado(String codComprob) {
        
        emisor = ctrEmisor.obtenerEmisor();
        consecutivos = ctrFactura.obtenerConsecutivos();
        
        String codigoFac = "1";
        String clave = "";
        String numeroConsecutivo = "";
        Date fechaEmision = new Date();
        String nombreEmisor = emisor.getNombre();
        String tipoIdentEm = emisor.getTipoId();
        String numeroIdentEm = emisor.getNumId();
        
        System.out.println(nombreEmisor);
        System.out.println(tipoIdentEm);
        System.out.println(numeroIdentEm);
        
        //DIRECCIONS
        String provinciaEm = emisor.getDireccion().getCodProvincia();
        String cantonEm = emisor.getDireccion().getCodCanton();
        String distritoEm = emisor.getDireccion().getCodBarrio();
        String otrasSenasEm = emisor.getDireccion().getOtrasSenas();
        int codigoPaisEm = emisor.getCodPais();
        int numTelefonoEm = emisor.getNumTel();
        String correoElectronicoEm = emisor.getCorreoElec();
        
        //CLIENTE/RECEPTOR
        Cliente receptor = (Cliente) cbxClientes.getSelectedItem();
        
        String condicionVenta = "";
        String plazoCredito = "";
        String medioPago = "";
        
        System.out.println("----- INICIO CONSECUTIVOS -----");
        for (Consecutivo c: consecutivos) {
            System.out.println("Código: "+c.getCod());
            System.out.println("Código comprob.: "+c.getCodComprob());
            System.out.println("Tipo comprob.: "+c.getTipoComprob());
            System.out.println("Consec.: "+c.getConsecutivo());
            
            if (c.getCodComprob().equals(codComprob)) {
                //Se formatea el consecutivo con largo = 10
                numeroConsecutivo = formatearConsecutivo("01", c.getConsecutivo());
                clave = formatearClave("116210768", numeroConsecutivo, "1");
                System.out.println(clave);
            }
        }
        System.out.println("----- FIN CONSECUTIVOS -----");
       
        
       
        return crearEncabezado(codigoFac, clave, numeroConsecutivo, 
                fechaEmision, nombreEmisor, tipoIdentEm, numeroIdentEm, 
                provinciaEm, cantonEm, distritoEm, otrasSenasEm, codigoPaisEm, 
                numTelefonoEm, correoElectronicoEm, receptor, condicionVenta, 
                plazoCredito, medioPago);
    }
    
    /**
     * Crea el objeto Encabezado y lo retorna.
     * @param codigoFac código de la factura
     * @param clave clave numérica de la factura
     * @param numeroConsecutivo número consecutivo de la factura
     * @param fechaEmision fecha de emisión de la factura
     * @param nombreEmisor nombre del emisor de la factura
     * @param tipoIdentEm tipo de identificación del emisor
     * @param numeroIdentEm número de identiifcación del emisor
     * @param provinciaEm provincia de ubicación del emisor
     * @param cantonEm cantón de ubicación del emisor
     * @param distritoEm distrito de ubicación del emisor
     * @param otrasSenasEm otras señas de ubicación del emisor
     * @param codigoPaisEm códio de país del emisor
     * @param numTelefonoEm número telefónico del emisor
     * @param correoElectronicoEm correo electrónico del emisor
     * @param receptor receptor/cliente
     * @param condicionVenta condición de la venta
     * @param plazoCredito plazo del crédito de la venta
     * @param medioPago medio de pago
     * @return el encabezado creado
     */
    private FacEncabezado crearEncabezado(String codigoFac, String clave, 
               String numeroConsecutivo, Date fechaEmision, String nombreEmisor, 
               String tipoIdentEm, String numeroIdentEm, String provinciaEm, 
               String cantonEm, String distritoEm, String otrasSenasEm, 
               int codigoPaisEm, int numTelefonoEm, 
               String correoElectronicoEm, Cliente receptor, 
               String condicionVenta, String plazoCredito, String medioPago) {
        
        FacEncabezado encab = new FacEncabezado(codigoFac, clave, 
               numeroConsecutivo, fechaEmision, nombreEmisor, tipoIdentEm, 
               numeroIdentEm, provinciaEm, cantonEm, distritoEm, otrasSenasEm, 
               codigoPaisEm, numTelefonoEm, correoElectronicoEm, receptor, 
               condicionVenta, plazoCredito, medioPago);
        
        return encab;
    }
    
    /**
    * Prepara/obtiene los datos de totales/montos para el resumen.
    * @return el resumen de la factura
    */
    public FacResumen prepararResumen() {
        
        double totalServ = 0;
        double totalServEx = 0;
        double totalMerc = 0;
        double totalMercEx = 0;
        double totalGrav  = 0;
        double totalEx = 0; 
        double totalVenta  = 0;
        double totalDescuentos = 0;
        double totalVentaNeta = 0;
        double totalImpuesto = 0;
        double totalComprob = 0;
        
        for(LineaDetalle l: factura.getLineasDetalle()) {
            
            if (l.getImpuesto() != null) {
                totalImpuesto += l.getImpuesto().getMontoImpuesto();
            }
            if(l.isMercancia()) {
                totalMerc += l.getTotal();
            } else {
                totalServ += l.getTotal();
            }
        }
        
        totalGrav = totalMerc + totalServ;
        totalEx = totalMercEx + totalServEx;
        totalVenta = totalGrav + totalEx;
        totalVentaNeta = totalVenta - totalDescuentos;
        totalComprob = totalVentaNeta + totalImpuesto;
        
        return agregarResumen(totalServ, totalServEx, totalMerc, totalMercEx, 
                totalGrav, totalEx, totalVenta, totalDescuentos, totalVentaNeta, 
                totalImpuesto, totalComprob);
        
    }
    
    /**
     * Crea un objeto resumen con la información/datos correspondientes.
     * @param totalServ total de los servicios.
     * @param totalServEx total de los servicios exentos.
     * @param totalMerc total de las mercancìas.
     * @param totalMercEx total de las mercancias exentas.
     * @param totalGrav total gravado.
     * @param totalEx total exento.
     * @param totalVenta total de la venta.
     * @param totalDescuentos total de descuentos.
     * @param totalVentaNeta total de venta neta.
     * @param totalImpuesto total de impuestos.
     * @param totalComprob total del comprobante.
     * @return  resumen de la factura.
     */
    public FacResumen agregarResumen(double totalServ, double totalServEx, 
            double totalMerc, double totalMercEx, double totalGrav, 
            double totalEx, double totalVenta, double totalDescuentos, 
            double totalVentaNeta, double totalImpuesto, double totalComprob) {
        
        FacResumen resumen = new FacResumen("CRC", 1, totalServ, totalServEx, 
                totalMerc, totalMercEx, totalGrav, totalEx, totalVenta, 
                totalDescuentos, totalVentaNeta, totalImpuesto, totalComprob);
        
        return resumen;
    }
    
    public void emitirFactura() {
        System.out.println(factura.getLineasDetalle().size()); 
        factura.setResumen(prepararResumen());
        factura.setEncabezado(preparaEncabezado("01"));
        factura.setInfoReferencia(null);       
        ctrFactura.crearFactura(factura);
        limpiarCampos(true);//Limpia los datos del cliente también
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlFacVarios = new javax.swing.JPanel();
        lblDescripcionVarios = new javax.swing.JLabel();
        lblPrecioVarios = new javax.swing.JLabel();
        txtDescripcionVarios = new javax.swing.JTextField();
        txtPrecioVarios = new javax.swing.JTextField();
        pnlClasificacionProductos = new javax.swing.JPanel();
        rbMercancia = new javax.swing.JRadioButton();
        rbServicio = new javax.swing.JRadioButton();
        btnAgregarVarios2 = new javax.swing.JButton();
        btnAddProduct2 = new javax.swing.JButton();
        bgClasifProducto = new javax.swing.ButtonGroup();
        pnlInfoCliente = new javax.swing.JPanel();
        lblMostrarNombreCl = new javax.swing.JLabel();
        btnMostrarClien = new javax.swing.JButton();
        lblMostrarCedulaCl = new javax.swing.JLabel();
        lblMostrarTelefonoCl = new javax.swing.JLabel();
        lblMostrarCorreoCl = new javax.swing.JLabel();
        lblMostrarCreditoCl = new javax.swing.JLabel();
        bgMedioPago = new javax.swing.ButtonGroup();
        btnAddImpuesto = new javax.swing.JButton();
        pnl_modFactura = new javax.swing.JPanel();
        scpnlTblLineaPedido = new javax.swing.JScrollPane();
        tblLineaPedido = new javax.swing.JTable();
        pnlTotales = new javax.swing.JPanel();
        lblTextSubtotal = new javax.swing.JLabel();
        lblTextTotal = new javax.swing.JLabel();
        lblSubtotal = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        btnFacturar = new javax.swing.JButton();
        rbMpagoCredito = new javax.swing.JRadioButton();
        rbMpagoContado = new javax.swing.JRadioButton();
        lblTextSubtotal1 = new javax.swing.JLabel();
        pnlAgregarProd = new javax.swing.JPanel();
        scpnlList = new javax.swing.JScrollPane();
        lsEscogerProd = new javax.swing.JList<>();
        lblTextExistencias = new javax.swing.JLabel();
        lblCantExistencia = new javax.swing.JLabel();
        lblTextPrecioUnit = new javax.swing.JLabel();
        lblPrecioUnit = new javax.swing.JLabel();
        btnAgregarVarios = new javax.swing.JButton();
        txtProducto = new javax.swing.JTextField();
        placeholder = new TextPrompt("Código de producto...", txtProducto);
        placeholder.changeAlpha(0.75f);
        placeholder.changeStyle(Font.ITALIC);
        btnAddProduct1 = new javax.swing.JButton();
        txtCantidad = new javax.swing.JTextField();
        placeholder = new TextPrompt("Cant. solicitada...", txtCantidad);
        placeholder.changeAlpha(0.75f);
        placeholder.changeStyle(Font.ITALIC);
        btnAddProduct = new javax.swing.JButton();
        btnBusquedaAv = new javax.swing.JButton();
        pnlSelectCliente = new javax.swing.JPanel();
        lblTextClienteFac = new javax.swing.JLabel();
        ftClienteFac = new javax.swing.JFormattedTextField();
        lblTextUsuario = new javax.swing.JLabel();
        lblUsuarioFac = new javax.swing.JLabel();
        cbxClientes = new javax.swing.JComboBox<>();
        lblClienteNom = new javax.swing.JLabel();
        btnCrearCliente = new javax.swing.JButton();
        btnBuscarCliente = new javax.swing.JButton();
        txtNomCliente = new javax.swing.JTextField();
        btnBorrarLinea = new javax.swing.JButton();

        pnlFacVarios.setBorder(javax.swing.BorderFactory.createTitledBorder("Agregar Producto"));
        pnlFacVarios.setPreferredSize(new java.awt.Dimension(636, 202));

        lblDescripcionVarios.setText("Descripción: ");

        lblPrecioVarios.setText("Precio:");

        pnlClasificacionProductos.setBorder(javax.swing.BorderFactory.createTitledBorder("Clasificación:"));

        bgClasifProducto.add(rbMercancia);
        rbMercancia.setText("Mercancía");

        bgClasifProducto.add(rbServicio);
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
                .addComponent(rbMercancia)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlClasificacionProductosLayout.setVerticalGroup(
            pnlClasificacionProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlClasificacionProductosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rbMercancia)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rbServicio)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnAgregarVarios2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnAgregarVarios2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/return_back.png"))); // NOI18N
        btnAgregarVarios2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarVarios2ActionPerformed(evt);
            }
        });

        btnAddProduct2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/f_add_30x30.png"))); // NOI18N
        btnAddProduct2.setText("Agregar a la línea");
        btnAddProduct2.setToolTipText("Agregar producto a la linea");
        btnAddProduct2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddProduct2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlFacVariosLayout = new javax.swing.GroupLayout(pnlFacVarios);
        pnlFacVarios.setLayout(pnlFacVariosLayout);
        pnlFacVariosLayout.setHorizontalGroup(
            pnlFacVariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFacVariosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlFacVariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlFacVariosLayout.createSequentialGroup()
                        .addComponent(btnAgregarVarios2)
                        .addGap(351, 351, 351)
                        .addComponent(btnAddProduct2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(pnlFacVariosLayout.createSequentialGroup()
                        .addComponent(pnlClasificacionProductos, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addGroup(pnlFacVariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblDescripcionVarios, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
                            .addComponent(lblPrecioVarios, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlFacVariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDescripcionVarios)
                            .addComponent(txtPrecioVarios))
                        .addGap(12, 12, 12))))
        );
        pnlFacVariosLayout.setVerticalGroup(
            pnlFacVariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFacVariosLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(pnlFacVariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnlFacVariosLayout.createSequentialGroup()
                        .addGroup(pnlFacVariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtDescripcionVarios, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblDescripcionVarios, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(pnlFacVariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblPrecioVarios, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPrecioVarios, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(pnlClasificacionProductos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addGroup(pnlFacVariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAgregarVarios2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAddProduct2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pnlFacVarios.getAccessibleContext().setAccessibleName("");

        pnlInfoCliente.setBorder(javax.swing.BorderFactory.createTitledBorder("Información del Cliente"));

        lblMostrarNombreCl.setForeground(new java.awt.Color(153, 153, 153));
        lblMostrarNombreCl.setText(" Nombre del Cliente");
        lblMostrarNombreCl.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.SystemColor.activeCaption));

        btnMostrarClien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/desplegar.png"))); // NOI18N
        btnMostrarClien.setText("Desplegar datos");
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

        btnAddImpuesto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/add_impuesto.png"))); // NOI18N
        btnAddImpuesto.setToolTipText("Agregar impuesto");
        btnAddImpuesto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddImpuestoActionPerformed(evt);
            }
        });

        setClosable(true);
        setIconifiable(true);
        setTitle("Módulo de Facturación");
        setPreferredSize(new java.awt.Dimension(1240, 670));

        tblLineaPedido.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Descripción", "Medida", "Cantidad", "Prec. Unidad", "Impuesto", "Subtotal", "Total", "Codigo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
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

        btnFacturar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/cash-register_40x40.png"))); // NOI18N
        btnFacturar.setText("Emitir Factura");
        btnFacturar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFacturarActionPerformed(evt);
            }
        });

        bgMedioPago.add(rbMpagoCredito);
        rbMpagoCredito.setText("Crédito");

        bgMedioPago.add(rbMpagoContado);
        rbMpagoContado.setSelected(true);
        rbMpagoContado.setText("Contado");

        lblTextSubtotal1.setText("Medio de Pago:");

        javax.swing.GroupLayout pnlTotalesLayout = new javax.swing.GroupLayout(pnlTotales);
        pnlTotales.setLayout(pnlTotalesLayout);
        pnlTotalesLayout.setHorizontalGroup(
            pnlTotalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlTotalesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTextSubtotal1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rbMpagoContado)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rbMpagoCredito)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblTextSubtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblSubtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblTextTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnFacturar, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlTotalesLayout.setVerticalGroup(
            pnlTotalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTotalesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlTotalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblSubtotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblTextTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnFacturar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblTotal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblTextSubtotal1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblTextSubtotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(rbMpagoCredito, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(rbMpagoContado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pnlAgregarProd.setBorder(javax.swing.BorderFactory.createTitledBorder("Agregar Producto"));

        lsEscogerProd.setForeground(new java.awt.Color(102, 102, 102));
        lsEscogerProd.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        lsEscogerProd.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                lsEscogerProdValueChanged(evt);
            }
        });
        scpnlList.setViewportView(lsEscogerProd);

        lblTextExistencias.setText("Cant. en Exist.");

        lblCantExistencia.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.SystemColor.activeCaption));

        lblTextPrecioUnit.setText("Precio por vara");
        lblTextPrecioUnit.setToolTipText("");

        lblPrecioUnit.setToolTipText("Precio por vara");
        lblPrecioUnit.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.SystemColor.activeCaption));

        btnAgregarVarios.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/prod_varios_30x30.png"))); // NOI18N
        btnAgregarVarios.setToolTipText("Agregar productos varios");
        btnAgregarVarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarVariosActionPerformed(evt);
            }
        });

        txtProducto.setToolTipText("Código del producto a buscar");
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

        btnAddProduct1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/f_create_product_30.png"))); // NOI18N
        btnAddProduct1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddProduct1ActionPerformed(evt);
            }
        });

        txtCantidad.setToolTipText("Cantidad solicitada en varas...");
        txtCantidad.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCantidadFocusGained(evt);
            }
        });

        btnAddProduct.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/f_add_40x40.png"))); // NOI18N
        btnAddProduct.setText("Agregar a la línea");
        btnAddProduct.setToolTipText("Agregar producto a la linea");
        btnAddProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddProductActionPerformed(evt);
            }
        });

        btnBusquedaAv.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/adv_search.png"))); // NOI18N
        btnBusquedaAv.setToolTipText("Búsqueda avanzada de productos");
        btnBusquedaAv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBusquedaAvActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlAgregarProdLayout = new javax.swing.GroupLayout(pnlAgregarProd);
        pnlAgregarProd.setLayout(pnlAgregarProdLayout);
        pnlAgregarProdLayout.setHorizontalGroup(
            pnlAgregarProdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAgregarProdLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlAgregarProdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlAgregarProdLayout.createSequentialGroup()
                        .addComponent(btnAddProduct1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtProducto))
                    .addGroup(pnlAgregarProdLayout.createSequentialGroup()
                        .addComponent(lblTextExistencias, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblCantExistencia, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblTextPrecioUnit, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblPrecioUnit, javax.swing.GroupLayout.DEFAULT_SIZE, 87, Short.MAX_VALUE))
                    .addComponent(scpnlList))
                .addGap(12, 12, 12)
                .addGroup(pnlAgregarProdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlAgregarProdLayout.createSequentialGroup()
                        .addComponent(btnBusquedaAv, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnAgregarVarios, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(txtCantidad)
                    .addComponent(btnAddProduct, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlAgregarProdLayout.setVerticalGroup(
            pnlAgregarProdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAgregarProdLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(pnlAgregarProdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnAddProduct1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlAgregarProdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(8, 8, 8)
                .addGroup(pnlAgregarProdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnlAgregarProdLayout.createSequentialGroup()
                        .addComponent(scpnlList, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGroup(pnlAgregarProdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlAgregarProdLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblPrecioUnit, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlAgregarProdLayout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addGroup(pnlAgregarProdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblTextPrecioUnit, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(pnlAgregarProdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(lblCantExistencia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lblTextExistencias, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE))))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlAgregarProdLayout.createSequentialGroup()
                        .addComponent(btnAddProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(pnlAgregarProdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnBusquedaAv, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnAgregarVarios, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlSelectCliente.setBorder(javax.swing.BorderFactory.createTitledBorder("Seleccionar Cliente"));

        lblTextClienteFac.setText("Cliente:");

        try {
            ftClienteFac.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("#-####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        ftClienteFac.setToolTipText("Presione 'Enter' para buscar el cliente");
        ftClienteFac.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                ftClienteFacKeyReleased(evt);
            }
        });

        lblTextUsuario.setText("Facturado por: ");

        lblUsuarioFac.setToolTipText("Usuario en sesión.");
        lblUsuarioFac.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.SystemColor.activeCaption));

        cbxClientes.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxClientesItemStateChanged(evt);
            }
        });
        cbxClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxClientesActionPerformed(evt);
            }
        });
        cbxClientes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cbxClientesKeyReleased(evt);
            }
        });

        lblClienteNom.setText("CLIENTE GENÉRICO");
        lblClienteNom.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.SystemColor.activeCaption));

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

        txtNomCliente.setToolTipText("Presione 'Enter' para buscar el cliente");
        txtNomCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNomClienteKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout pnlSelectClienteLayout = new javax.swing.GroupLayout(pnlSelectCliente);
        pnlSelectCliente.setLayout(pnlSelectClienteLayout);
        pnlSelectClienteLayout.setHorizontalGroup(
            pnlSelectClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSelectClienteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlSelectClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlSelectClienteLayout.createSequentialGroup()
                        .addGroup(pnlSelectClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(ftClienteFac)
                            .addComponent(lblTextClienteFac, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlSelectClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNomCliente)
                            .addComponent(lblClienteNom, javax.swing.GroupLayout.DEFAULT_SIZE, 324, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlSelectClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnCrearCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnBuscarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlSelectClienteLayout.createSequentialGroup()
                        .addComponent(lblTextUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblUsuarioFac, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(cbxClientes, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlSelectClienteLayout.setVerticalGroup(
            pnlSelectClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSelectClienteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlSelectClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlSelectClienteLayout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(pnlSelectClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblTextClienteFac, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblClienteNom, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlSelectClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtNomCliente, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                            .addComponent(ftClienteFac)))
                    .addGroup(pnlSelectClienteLayout.createSequentialGroup()
                        .addComponent(btnCrearCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBuscarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbxClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlSelectClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblUsuarioFac, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblTextUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnBorrarLinea.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/delete.png"))); // NOI18N
        btnBorrarLinea.setText("Remover de la línea");
        btnBorrarLinea.setToolTipText("Remover producto de la línea de pedido");
        btnBorrarLinea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrarLineaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnl_modFacturaLayout = new javax.swing.GroupLayout(pnl_modFactura);
        pnl_modFactura.setLayout(pnl_modFacturaLayout);
        pnl_modFacturaLayout.setHorizontalGroup(
            pnl_modFacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(pnl_modFacturaLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(pnl_modFacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_modFacturaLayout.createSequentialGroup()
                        .addComponent(pnlSelectCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(pnlAgregarProd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(scpnlTblLineaPedido)
                    .addGroup(pnl_modFacturaLayout.createSequentialGroup()
                        .addComponent(btnBorrarLinea)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(pnlTotales, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnl_modFacturaLayout.setVerticalGroup(
            pnl_modFacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_modFacturaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_modFacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlSelectCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlAgregarProd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(scpnlTblLineaPedido, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBorrarLinea, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlTotales, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnl_modFactura, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnl_modFactura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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

    private void txtCantidadFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCantidadFocusGained
        if (!(evt.getSource() instanceof JTextField)) {
            return;
        }
        txtCantidad = (JTextField) evt.getSource();
        txtCantidad.selectAll();
    }//GEN-LAST:event_txtCantidadFocusGained

    private void ftClienteFacKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ftClienteFacKeyReleased
        if (evt.getKeyCode() == 10) { //enter
           buscarCliente();
        }
    }//GEN-LAST:event_ftClienteFacKeyReleased

    private void btnBusquedaAvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBusquedaAvActionPerformed
        
        dialogBusqueda = new DlgFacBusqueda(this, true);
        
//        dialogBusqueda.addWindowListener(new WindowAdapter() {
//
//            @Override
//            public void windowClosed(WindowEvent e) {
//                System.out.println("CLOSED");
//            }
//        });
        
        dialogBusqueda.setVisible(true);
        
        //Obtener el producto seleccionado en búsqueda avnzada, si hay
        Madera prod = dialogBusqueda.obtenerProducto();
        System.out.println(prod);
        
        lsEscogerProd.clearSelection();//limpiar la selección de la lista
        //Si la búsqueda avnzada devuelve un producto se llena la lista
        if (prod != null) {
            //cargar el producto en la lista
            llenarListaProductos(prod.getCodProducto(), 5);
            
            //Si solo hay un elemento en la lista de productos, se selecciona
            if (lsEscogerProd.getComponentCount() == 1) {
                lsEscogerProd.setSelectedIndex(0);
            }
        }
        
        
        
    }//GEN-LAST:event_btnBusquedaAvActionPerformed

    private void btnBuscarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarClienteActionPerformed
        //if(cbxClientes.getItemCount() > 0) {
            buscarCliente();
        //}        
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

        pnlFacVarios.setVisible(true);
        pnlFacVarios.setBounds(585, 31, 636, 202);
        pnl_modFactura.add(pnlFacVarios);
        pnlAgregarProd.setVisible(false);
        txtDescripcionVarios.requestFocus();
        instancia.pack();
    }//GEN-LAST:event_btnAgregarVariosActionPerformed

    private void btnAddProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddProductActionPerformed
        if (pnlAgregarProd.isVisible()) {
            prepararLinea();
            //limpiarCampos();
        } else {
            prepararLineaVarios();
        }
    }//GEN-LAST:event_btnAddProductActionPerformed

    private void btnAgregarVarios2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarVarios2ActionPerformed

        pnlAgregarProd.setVisible(true);
        pnlAgregarProd.setBounds(585, 31, 636, 202);
        pnl_modFactura.add(pnlAgregarProd);
        pnlFacVarios.setVisible(false);
    }//GEN-LAST:event_btnAgregarVarios2ActionPerformed

    private void lsEscogerProdValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_lsEscogerProdValueChanged
        Madera prod = verificarSeleccionLista();
        if(prod != null) {
            lblCantExistencia.setText(String.valueOf(prod.getCantVaras()));
            lblPrecioUnit.setText(String.valueOf(prod.getPrecioXvara()));
        }
    }//GEN-LAST:event_lsEscogerProdValueChanged

    private void btnFacturarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFacturarActionPerformed
        emitirFactura();
    }//GEN-LAST:event_btnFacturarActionPerformed

    private void cbxClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxClientesActionPerformed
        if(cbxClientes.getItemCount()>0){
        System.out.println("CBX ACTION PERF");
            Cliente cl = (Cliente) cbxClientes.getSelectedItem();
            lblClienteNom.setText(cl.getNombre());
            ftClienteFac.setText(cl.getCedula());
        }
    }//GEN-LAST:event_cbxClientesActionPerformed
   
    private void cbxClientesKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbxClientesKeyReleased
        System.out.println(evt.getKeyCode());
        //if (evt.getKeyCode() == 10) {
        System.out.println("CBX KEY REL");
            buscarCliente();
        //}
    }//GEN-LAST:event_cbxClientesKeyReleased

    private void cbxClientesItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxClientesItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxClientesItemStateChanged

    private void txtNomClienteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNomClienteKeyReleased
        if (evt.getKeyCode() == 10) {
            buscarCliente();
        }
    }//GEN-LAST:event_txtNomClienteKeyReleased

    private void btnBorrarLineaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarLineaActionPerformed
        eliminarProducto();
    }//GEN-LAST:event_btnBorrarLineaActionPerformed

    private void btnAddProduct2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddProduct2ActionPerformed
        if (pnlAgregarProd.isVisible()) {
            prepararLinea();
            //limpiarCampos();
        } else {
            prepararLineaVarios();
        }
    }//GEN-LAST:event_btnAddProduct2ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgClasifProducto;
    private javax.swing.ButtonGroup bgMedioPago;
    private javax.swing.JButton btnAddImpuesto;
    private javax.swing.JButton btnAddProduct;
    private javax.swing.JButton btnAddProduct1;
    private javax.swing.JButton btnAddProduct2;
    private javax.swing.JButton btnAgregarVarios;
    private javax.swing.JButton btnAgregarVarios2;
    private javax.swing.JButton btnBorrarLinea;
    private javax.swing.JButton btnBuscarCliente;
    private javax.swing.JButton btnBusquedaAv;
    private javax.swing.JButton btnCrearCliente;
    private javax.swing.JButton btnFacturar;
    private javax.swing.JButton btnMostrarClien;
    private javax.swing.JComboBox<Cliente> cbxClientes;
    private javax.swing.JFormattedTextField ftClienteFac;
    private javax.swing.JLabel lblCantExistencia;
    private javax.swing.JLabel lblClienteNom;
    private javax.swing.JLabel lblDescripcionVarios;
    private javax.swing.JLabel lblMostrarCedulaCl;
    private javax.swing.JLabel lblMostrarCorreoCl;
    private javax.swing.JLabel lblMostrarCreditoCl;
    private javax.swing.JLabel lblMostrarNombreCl;
    private javax.swing.JLabel lblMostrarTelefonoCl;
    private javax.swing.JLabel lblPrecioUnit;
    private javax.swing.JLabel lblPrecioVarios;
    private javax.swing.JLabel lblSubtotal;
    private javax.swing.JLabel lblTextClienteFac;
    private javax.swing.JLabel lblTextExistencias;
    private javax.swing.JLabel lblTextPrecioUnit;
    private javax.swing.JLabel lblTextSubtotal;
    private javax.swing.JLabel lblTextSubtotal1;
    private javax.swing.JLabel lblTextTotal;
    private javax.swing.JLabel lblTextUsuario;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JLabel lblUsuarioFac;
    private javax.swing.JList<Madera> lsEscogerProd;
    private javax.swing.JPanel pnlAgregarProd;
    private javax.swing.JPanel pnlClasificacionProductos;
    private javax.swing.JPanel pnlFacVarios;
    private javax.swing.JPanel pnlInfoCliente;
    private javax.swing.JPanel pnlSelectCliente;
    private javax.swing.JPanel pnlTotales;
    private javax.swing.JPanel pnl_modFactura;
    private javax.swing.JRadioButton rbMercancia;
    private javax.swing.JRadioButton rbMpagoContado;
    private javax.swing.JRadioButton rbMpagoCredito;
    private javax.swing.JRadioButton rbServicio;
    private javax.swing.JScrollPane scpnlList;
    private javax.swing.JScrollPane scpnlTblLineaPedido;
    private javax.swing.JTable tblLineaPedido;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtDescripcionVarios;
    private javax.swing.JTextField txtNomCliente;
    private javax.swing.JTextField txtPrecioVarios;
    private javax.swing.JTextField txtProducto;
    // End of variables declaration//GEN-END:variables
}
