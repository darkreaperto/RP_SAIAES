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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;
import javax.swing.DefaultListModel;
import javax.swing.JList;
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
    //private static ArrayList<LineaDetalle> lineas = new ArrayList<>();
    public Impuesto impuesto;
    public Factura factura;
    private Emisor emisor;
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
        preparaEncab("01");
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
                    limpiarCampos();
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
                prodSelected.getMedidas();
        
        LineaDetalle linea = new LineaDetalle(numLinea, "04", 
                prodSelected.getCodProducto(), cantSolicitada, 
                "unidades", detalle, precioUni,
                descuento, "No se realizó descuento",
                impuesto, true);

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
            limpiarCampos();
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
        
        Object[] row = new Object[7];
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
     */
   public void limpiarCampos() {
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
    public FacEncabezado preparaEncab(String codComprob) {
        
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
        String provinciaEm = "";
        String cantonEm = "";
        String distritoEm = "";
        String otrasSenasEm = "";
        int codigoPaisEm = 0;
        int numTelefonoEm = 0;
        String correoElectronicoEm = "";
        String codReceptor = "";
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
       
        FacEncabezado encab = new FacEncabezado(codigoFac, clave, 
               numeroConsecutivo, fechaEmision, nombreEmisor, tipoIdentEm, 
               numeroIdentEm, provinciaEm, cantonEm, distritoEm, otrasSenasEm, 
               codigoPaisEm, numTelefonoEm, correoElectronicoEm, codReceptor, 
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
     * @param totalServ
     * @param totalServEx
     * @param totalMerc
     * @param totalMercEx
     * @param totalGrav
     * @param totalEx
     * @param totalVenta
     * @param totalDescuentos
     * @param totalVentaNeta
     * @param totalImpuesto
     * @param totalComprob 
     * @return  
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
        factura.setResumen(prepararResumen());
        
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
        bgClasifProducto = new javax.swing.ButtonGroup();
        pnl_modFactura = new javax.swing.JPanel();
        pnlInfoCliente = new javax.swing.JPanel();
        lblMostrarNombreCl = new javax.swing.JLabel();
        btnMostrarClien = new javax.swing.JButton();
        lblMostrarCedulaCl = new javax.swing.JLabel();
        lblMostrarTelefonoCl = new javax.swing.JLabel();
        lblMostrarCorreoCl = new javax.swing.JLabel();
        lblMostrarCreditoCl = new javax.swing.JLabel();
        lblUsuarioFac = new javax.swing.JLabel();
        lblTextUsuario = new javax.swing.JLabel();
        lblTextClienteFac = new javax.swing.JLabel();
        lblClienteNom = new javax.swing.JLabel();
        txtClienteFac = new javax.swing.JTextField();
        ftClienteFac = new javax.swing.JFormattedTextField();
        scpnlTblLineaPedido = new javax.swing.JScrollPane();
        tblLineaPedido = new javax.swing.JTable();
        pnlTotales = new javax.swing.JPanel();
        lblTextSubtotal = new javax.swing.JLabel();
        lblTextTotal = new javax.swing.JLabel();
        lblSubtotal = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        btnFacturar = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JSeparator();
        btnAddProduct = new javax.swing.JButton();
        btnBusquedaAv = new javax.swing.JButton();
        btnCrearCliente = new javax.swing.JButton();
        btnBuscarCliente = new javax.swing.JButton();
        btnAddImpuesto = new javax.swing.JButton();
        pnlAgregarProd = new javax.swing.JPanel();
        txtProducto = new javax.swing.JTextField();
        scpnlList = new javax.swing.JScrollPane();
        lsEscogerProd = new javax.swing.JList<>();
        btnAddProduct1 = new javax.swing.JButton();
        txtCantidad = new javax.swing.JTextField();
        lblTextExistencias = new javax.swing.JLabel();
        lblCantExistencia = new javax.swing.JLabel();
        lblTextPrecioUnit = new javax.swing.JLabel();
        lblPrecioUnit = new javax.swing.JLabel();
        btnAgregarVarios = new javax.swing.JButton();

        pnlFacVarios.setBorder(javax.swing.BorderFactory.createTitledBorder("Agregar Producto"));

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

        javax.swing.GroupLayout pnlFacVariosLayout = new javax.swing.GroupLayout(pnlFacVarios);
        pnlFacVarios.setLayout(pnlFacVariosLayout);
        pnlFacVariosLayout.setHorizontalGroup(
            pnlFacVariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFacVariosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlFacVariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnAgregarVarios2)
                    .addGroup(pnlFacVariosLayout.createSequentialGroup()
                        .addComponent(pnlClasificacionProductos, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addGroup(pnlFacVariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblDescripcionVarios, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblPrecioVarios, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(pnlFacVariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDescripcionVarios, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPrecioVarios, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(37, Short.MAX_VALUE))
        );
        pnlFacVariosLayout.setVerticalGroup(
            pnlFacVariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFacVariosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlFacVariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlFacVariosLayout.createSequentialGroup()
                        .addGroup(pnlFacVariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblDescripcionVarios, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDescripcionVarios, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlFacVariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblPrecioVarios, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPrecioVarios, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(pnlClasificacionProductos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addComponent(btnAgregarVarios2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        setClosable(true);
        setIconifiable(true);
        setTitle("Módulo de Facturación");
        setPreferredSize(new java.awt.Dimension(1240, 670));

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

        lblUsuarioFac.setToolTipText("Usuario en sesión.");
        lblUsuarioFac.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.SystemColor.activeCaption));

        lblTextUsuario.setText("Facturado por: ");

        lblTextClienteFac.setText("Cliente:");

        lblClienteNom.setText("CLIENTE GENÉRICO");
        lblClienteNom.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.SystemColor.activeCaption));

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
        btnFacturar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFacturarActionPerformed(evt);
            }
        });

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

        btnAddImpuesto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/add_impuesto.png"))); // NOI18N
        btnAddImpuesto.setToolTipText("Agregar impuesto");
        btnAddImpuesto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddImpuestoActionPerformed(evt);
            }
        });

        pnlAgregarProd.setBorder(javax.swing.BorderFactory.createTitledBorder("Agregar Producto"));

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

        lsEscogerProd.setForeground(new java.awt.Color(102, 102, 102));
        lsEscogerProd.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        lsEscogerProd.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                lsEscogerProdValueChanged(evt);
            }
        });
        scpnlList.setViewportView(lsEscogerProd);

        btnAddProduct1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/f_crearProducto.png"))); // NOI18N
        btnAddProduct1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddProduct1ActionPerformed(evt);
            }
        });

        txtCantidad.setText("Cantidad en unidades...");
        txtCantidad.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCantidadFocusGained(evt);
            }
        });

        lblTextExistencias.setText("Cant. en Exist.");

        lblCantExistencia.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.SystemColor.activeCaption));

        lblTextPrecioUnit.setText("Precio Unitario");

        lblPrecioUnit.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.SystemColor.activeCaption));

        btnAgregarVarios.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/add.png"))); // NOI18N
        btnAgregarVarios.setText("Productos varios");
        btnAgregarVarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarVariosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlAgregarProdLayout = new javax.swing.GroupLayout(pnlAgregarProd);
        pnlAgregarProd.setLayout(pnlAgregarProdLayout);
        pnlAgregarProdLayout.setHorizontalGroup(
            pnlAgregarProdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAgregarProdLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlAgregarProdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(scpnlList, javax.swing.GroupLayout.PREFERRED_SIZE, 435, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlAgregarProdLayout.createSequentialGroup()
                        .addComponent(txtProducto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAddProduct1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(21, 21, 21)
                .addGroup(pnlAgregarProdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlAgregarProdLayout.createSequentialGroup()
                        .addGroup(pnlAgregarProdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTextExistencias, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblTextPrecioUnit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlAgregarProdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblPrecioUnit, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblCantExistencia, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(btnAgregarVarios, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlAgregarProdLayout.setVerticalGroup(
            pnlAgregarProdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlAgregarProdLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlAgregarProdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtProducto, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAddProduct1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlAgregarProdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnAgregarVarios, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlAgregarProdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scpnlList, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlAgregarProdLayout.createSequentialGroup()
                        .addGroup(pnlAgregarProdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lblCantExistencia, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblTextExistencias, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlAgregarProdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lblPrecioUnit, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblTextPrecioUnit, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(23, 23, 23))
        );

        javax.swing.GroupLayout pnl_modFacturaLayout = new javax.swing.GroupLayout(pnl_modFactura);
        pnl_modFactura.setLayout(pnl_modFacturaLayout);
        pnl_modFacturaLayout.setHorizontalGroup(
            pnl_modFacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(pnl_modFacturaLayout.createSequentialGroup()
                .addGroup(pnl_modFacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_modFacturaLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(pnl_modFacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(pnl_modFacturaLayout.createSequentialGroup()
                                .addComponent(btnBusquedaAv, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(pnlTotales, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_modFacturaLayout.createSequentialGroup()
                                .addComponent(pnlAgregarProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnAddImpuesto, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnAddProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(scpnlTblLineaPedido, javax.swing.GroupLayout.PREFERRED_SIZE, 1170, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnl_modFacturaLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 1194, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(24, Short.MAX_VALUE))
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
                .addGap(43, 43, 43))
        );
        pnl_modFacturaLayout.setVerticalGroup(
            pnl_modFacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_modFacturaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_modFacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
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
                                        .addComponent(txtClienteFac, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                    .addComponent(pnlInfoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(pnl_modFacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_modFacturaLayout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(pnlAgregarProd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_modFacturaLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(pnl_modFacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnAddImpuesto, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnAddProduct, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(7, 7, 7)
                .addComponent(scpnlTblLineaPedido, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl_modFacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlTotales, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBusquedaAv, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
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
        int x = pnlAgregarProd.getX();
        int y = pnlAgregarProd.getY();
        int h = pnlAgregarProd.getHeight();
        pnlFacVarios.setVisible(true);
        pnlFacVarios.setBounds(x, y, 520, h);
        pnl_modFactura.add(pnlFacVarios);
        pnlAgregarProd.setVisible(false);
        txtDescripcionVarios.requestFocus();
        //instancia.pack();

//        dialogVarios = new DlgFacVarios(this, true);
//        dialogVarios.setVisible(true);
        //agregarLineaVarios();
    }//GEN-LAST:event_btnAgregarVariosActionPerformed

    private void btnAddProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddProductActionPerformed
        if (pnlAgregarProd.isVisible()) {
            prepararLinea();
        } else {
            prepararLineaVarios();
        }
    }//GEN-LAST:event_btnAddProductActionPerformed

    private void btnAgregarVarios2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarVarios2ActionPerformed
        int x = pnlFacVarios.getX();
        int y = pnlFacVarios.getY();
        int h = pnlFacVarios.getHeight();
        pnlAgregarProd.setVisible(true);
        pnlAgregarProd.setBounds(x, y, 680, h);
        pnl_modFactura.add(pnlAgregarProd);
        pnlFacVarios.setVisible(false);
//        txtProducto.requestFocus();
//        txtProducto.selectAll();
    }//GEN-LAST:event_btnAgregarVarios2ActionPerformed

    private void lsEscogerProdValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_lsEscogerProdValueChanged
        Madera prod = verificarSeleccionLista();
        if(prod != null) {
            lblCantExistencia.setText(String.valueOf(prod.getUnidades()));
            lblPrecioUnit.setText(String.valueOf(prod.getPrecioXvara()));
        }
    }//GEN-LAST:event_lsEscogerProdValueChanged

    private void btnFacturarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFacturarActionPerformed
        
        crearLinea();
        
    }//GEN-LAST:event_btnFacturarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgClasifProducto;
    private javax.swing.JButton btnAddImpuesto;
    private javax.swing.JButton btnAddProduct;
    private javax.swing.JButton btnAddProduct1;
    private javax.swing.JButton btnAgregarVarios;
    private javax.swing.JButton btnAgregarVarios2;
    private javax.swing.JButton btnBuscarCliente;
    private javax.swing.JButton btnBusquedaAv;
    private javax.swing.JButton btnCrearCliente;
    private javax.swing.JButton btnFacturar;
    private javax.swing.JButton btnMostrarClien;
    private javax.swing.JFormattedTextField ftClienteFac;
    private javax.swing.JSeparator jSeparator3;
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
    private javax.swing.JLabel lblTextTotal;
    private javax.swing.JLabel lblTextUsuario;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JLabel lblUsuarioFac;
    private javax.swing.JList<Madera> lsEscogerProd;
    private javax.swing.JPanel pnlAgregarProd;
    private javax.swing.JPanel pnlClasificacionProductos;
    private javax.swing.JPanel pnlFacVarios;
    private javax.swing.JPanel pnlInfoCliente;
    private javax.swing.JPanel pnlTotales;
    private javax.swing.JPanel pnl_modFactura;
    private javax.swing.JRadioButton rbMercancia;
    private javax.swing.JRadioButton rbServicio;
    private javax.swing.JScrollPane scpnlList;
    private javax.swing.JScrollPane scpnlTblLineaPedido;
    private javax.swing.JTable tblLineaPedido;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtClienteFac;
    private javax.swing.JTextField txtDescripcionVarios;
    private javax.swing.JTextField txtPrecioVarios;
    private javax.swing.JTextField txtProducto;
    // End of variables declaration//GEN-END:variables
}
