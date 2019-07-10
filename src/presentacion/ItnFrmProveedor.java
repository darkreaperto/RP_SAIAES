/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacion;

import controladores.CtrAcceso;
import controladores.CtrDireccion;
import controladores.CtrProveedor;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import logica.negocio.Contacto;
import logica.negocio.Direccion;
import logica.negocio.Proveedor;
import logica.servicios.Mensaje;
import logica.servicios.Regex;
import logica.servicios.DirFiltro;
import logica.servicios.UI;
import util.Estado;
import util.TipoCedula;
import util.TipoContacto;
import util.TipoMensaje;

/**
 * Formulario de Proveedores
 * @author aoihanabi
 */
public class ItnFrmProveedor extends javax.swing.JInternalFrame {

    /** Instancia de esta clase para singleton. */
    private static ItnFrmProveedor instancia = null;
    /** Instancia de la clase controlador de Proveedor. */
    private static CtrProveedor controlador;
    /** Instancia del controlador de Direccion. */
    private static CtrDireccion ctrDireccion;
    /** Instancia del controlador de Acceso. */
    private static CtrAcceso sesion;
    /** Lista completa de proveedores. */
    private static ArrayList<Proveedor> proveedores;
    /** Lista de números de teléfono a crear en la BD. */
    private static ArrayList<String> crearTelefonos;
    /** Lista de correos electrónicos a crear en la BD. */
    private static ArrayList<String> crearCorreos;
    /** Lista de números de teléfono a actualizar en la BD. */
    private static ArrayList<Contacto> editarTelefonos;
    /** Lista de correos electrónicos a actualizar en la BD. */
    private static ArrayList<Contacto> editarCorreos;
    /** Modelo utlizado para las tablas de la interfaz. */
    private static DefaultTableModel model;
    /** Instancia de la clase Regex para verificaciones. */
    private final Regex verificacion;
    /** Instancia de la clase Mensaje. */
    private static Mensaje msg;
    /** Instancia de la clase UI. */
    private final UI estilo;

    /**
     * Creates new form ItnFrmProveedor.
     * @param sesionAcc usuario en sesión
     * @param proveedores lista de proveedores obtenida desde la BD.
     */
    public ItnFrmProveedor(CtrAcceso sesionAcc, ArrayList<Proveedor> proveedores) {
        initComponents();
        //Inicializar variables
        controlador = CtrProveedor.getInstancia();
        ctrDireccion = CtrDireccion.getInstancia();
        ItnFrmProveedor.sesion = sesionAcc;
        ItnFrmProveedor.proveedores = proveedores;
        crearCorreos = new ArrayList<>();
        crearTelefonos = new ArrayList<>();
        verificacion = new Regex();
        msg = new Mensaje();
        estilo = new UI();
        //Estilizar interfaz
        estilo.estilizarTablas(tbListadoProveedor);
        estilo.estilizarTablas(tbl_crear);
        estilo.estilizarTablas(tbl_editar);
        estilo.estilizarTablas(tblProveedoresActivos);
        estilo.estilizarTablas(tblProveedoresInactivos);
        
        
        
        //Cargar información en la interfaz
        cargarTablas();
        cargarDirJCombo("P", "", "", "", cbxProvincia);
        cargarDirJCombo("P", "", "", "", cbxEditarProvincia);
        //pnlCrearDireccion.setVisible(false);
        pnlEditarDireccion.setVisible(false);
    }

    /**
     * Retorna la única instancia de la clase.
     * @param sesionAcc Usuario en sesión actual.
     * @param proveedores Lista de proveedores en la base de datos.
     * @return instancia de la clase de proveedores.
     */
    public static ItnFrmProveedor getInstancia(CtrAcceso sesionAcc,
            ArrayList<Proveedor> proveedores) {
        if (instancia == null) {
            instancia = new ItnFrmProveedor(sesionAcc, proveedores);
        }
        return instancia;
    }

    /**
     * Para todas las tablas en la interfaz, llama el método que carga una tabla
     * con la información de los proveedores.
     */
    public final void cargarTablas() {
        proveedores = controlador.obtenerProveedores();
        cargarProveedorJTable(tbListadoProveedor, true);
        cargarProveedorJTable(tbl_crear, true);
        cargarProveedorJTable(tbl_editar, true);
        cargarProveedorJTable(tblProveedoresActivos, true);
        cargarProveedorJTable(tblProveedoresInactivos, false);
    }

    /**
     * Carga/llena una tabla de la interfaz con la información de los 
     * proveedores.
     * @param tabla Tabla a llenar
     * @param estado Indica si el proveedor está o no activo
     */
    public void cargarProveedorJTable(JTable tabla, boolean estado) {
        Object[] row = new Object[5];
        model = (DefaultTableModel) tabla.getModel();
        model.setRowCount(0);
        model.setColumnCount(5);
        int i = 0;
        
        for (Proveedor pv : proveedores) {

            if (pv.getEstado().equals(Estado.Activo) && estado) {

                row[0] = pv.getCedula();
                row[1] = pv.getTipoCedula();
                row[2] = pv.getNombre();
                //Agregar lista de contactos con saltos de línea en una celda
                ArrayList<Contacto> contactos = pv.getContactos();
                String texto = "<html><body>";
                for (Contacto ct : contactos) {
                    String tipo = ct.getTipo().equals(TipoContacto.CORREO) ? "✉" : "✆";
                    texto += tipo + " " + ct.getInfo() + "<br>";
                }
                texto += "</body></html>";
                row[3] = texto;
                //Agregar dirección con saltos de línea en una celda
                String dir = pv.getDireccion() != null ?
                        //si hay dirección
                        "<html><body>"
                        + pv.getDireccion().getNomProvincia() + ",<br>"
                        + pv.getDireccion().getNomCanton() + ",<br>"
                        + pv.getDireccion().getNomDistrito() + ",<br>"
                        + pv.getDireccion().getNomBarrio()
                        + "</body></html>" : 
                        //si no hay dirección
                        "Sin dirección disponible";
                row[4] = dir;
                //Agregar datos al modelo
                model.addRow(row); 
                //Aumentar el tamaño de las filas
                tabla.setRowHeight(i, contactos.size() > 3
                        ? tabla.getRowHeight(i) * contactos.size()
                        : tabla.getRowHeight(i) * 4);

                i++;
            }
            if (pv.getEstado().equals(Estado.Deshabilitado) && !estado) {

                row[0] = pv.getCedula();
                row[1] = pv.getTipoCedula();
                row[2] = pv.getNombre();

                ArrayList<Contacto> contactos = pv.getContactos();
                String texto = "<html><body>";
                for (Contacto ct : contactos) {
                    String tipo = ct.getTipo().equals(TipoContacto.CORREO) ? "✉" : "✆";
                    texto += tipo + " " + ct.getInfo() + "<br>";
                }
                texto += "</body></html>";
                row[3] = texto;

                String dir = pv.getDireccion() != null ?
                        //si hay dirección
                        "<html><body>"
                        + pv.getDireccion().getNomProvincia() + ",<br>"
                        + pv.getDireccion().getNomCanton() + ",<br>"
                        + pv.getDireccion().getNomDistrito() + ",<br>"
                        + pv.getDireccion().getNomBarrio()
                        + "</body></html>" : 
                        //si no hay dirección
                        "Sin dirección";
                row[4] = dir;

                model.addRow(row); // agregar al modelo
                //Aumentar el tamaño de las filas
                tabla.setRowHeight(i, contactos.size() > 3
                        ? tabla.getRowHeight(i) * contactos.size()
                        : tabla.getRowHeight(i) * 4);
                i++;
            }
        }
    }

    /**
     * Cargar los combos con los lugares que componen la dirección.
     * @param campo identificador de campo (provincia, cantón, distritio, barrio)
     * @param codP código de provincia
     * @param codC código de cantón
     * @param codD código de distrito
     * @param combo Combobox que se cargará
     */
    public final void cargarDirJCombo(String campo, String codP, String codC,
            String codD, JComboBox combo) {

        combo.removeAllItems();
        ArrayList<DirFiltro> listaLugares = ctrDireccion.filtrarDireccion(campo,
                codP, codC, codD);
        for (int i = 0; i < listaLugares.size(); i++) {
            combo.addItem(listaLugares.get(i));
        }
    }
    
    /**
     * Carga la información del proveedor seleccionado desde la interfaz (tabla) 
     * en los campos correspondientes para su edición.
     * @param proveedor Proveedor seleccionado
     */
    private void cargarEditarProveedor(Proveedor proveedor) {

        txtEditarCedulaProveedor.setText(proveedor.getCedula());
        txtEditarNombreProveedor.setText(proveedor.getNombre());
        //cargar contactos
        editarTelefonos = new ArrayList<>();
        editarCorreos = new ArrayList<>();
        DefaultListModel<String> mTelefonos = new DefaultListModel<>();
        DefaultListModel<String> mCorreos = new DefaultListModel<>();
        //agregar contactos del proveedor
        for (Contacto ct : proveedor.getContactos()) {
            //agregar correos
            if (ct.getTipo().equals(TipoContacto.CORREO)) {
                editarCorreos.add(ct);
                mCorreos.addElement(ct.getInfo());
            //agregar teléfonos
            } else {
                if (ct.getTipo().equals(TipoContacto.TELEFONO)) {
                    editarTelefonos.add(ct);
                    mTelefonos.addElement(ct.getInfo());
                }
            }
        }
        lsTelefonos.setModel(mTelefonos);
        lsCorreos.setModel(mCorreos);
        
        //cargar información de la dirección, si existe
        if (proveedor.getDireccion() != null) {
            ckbEditarDireccion.setSelected(true);
            ckbEditarDireccion.setEnabled(false);
            mostrarDireccion(false, ckbEditarDireccion);
            cargarEditDirProveedor(proveedor);
        } else {
            ckbEditarDireccion.setSelected(false);
            ckbEditarDireccion.setEnabled(true);
            pnlEditarDireccion.setVisible(false);
            //limpiar los campos en caso de haber sido llenados anteriormente
            txaEditarOtrasSenas.setText("");
        }
    }

    /**
     * Cargar la dirección del proveedor seleccionado.
     * @param proveedor Proveedor seleccionado en JTable
     */
    private void cargarEditDirProveedor(Proveedor proveedor) {
        //Cargar dirección del proveedor
        String iP = proveedor.getDireccion().getCodProvincia();
        String iC = proveedor.getDireccion().getCodCanton();
        String iD = proveedor.getDireccion().getCodDistrito();
        String iB = proveedor.getDireccion().getCodBarrio();
        String oS = proveedor.getDireccion().getOtrasSenas();

        //PROVINCIA
        cargarDirJCombo("P", "", "", "", cbxEditarProvincia);
        for (int i = 0; i < cbxEditarProvincia.getItemCount(); i++) {
            if (cbxEditarProvincia.getItemAt(i).getCodigo().equals(iP)) {
                cbxEditarProvincia.setSelectedIndex(i);
            }
        }
        //codigo de provincia seleccionada
        String codP = cbxEditarProvincia.getItemAt(
                cbxEditarProvincia.getSelectedIndex()).getCodigo();

        //CANTON
        cargarDirJCombo("C", codP, "", "", cbxEditarCanton);
        for (int i = 0; i < cbxEditarCanton.getItemCount(); i++) {
            if (cbxEditarCanton.getItemAt(i).getCodigo().equals(iC)) {
                cbxEditarCanton.setSelectedIndex(i);
            }
        }
        // codigo del cantón seleccionado
        String codC = cbxEditarCanton.getItemAt(
                cbxEditarCanton.getSelectedIndex()).getCodigo();

        //DISTRITO
        cargarDirJCombo("D", codP, codC, "", cbxEditarDistrito);
        for (int i = 0; i < cbxEditarDistrito.getItemCount(); i++) {
            if (cbxEditarDistrito.getItemAt(i).getCodigo().equals(iD)) {
                cbxEditarDistrito.setSelectedIndex(i);
            }
        }
        String codD = cbxEditarDistrito.getItemAt(
                cbxEditarDistrito.getSelectedIndex()).getCodigo();

        //BARRIO
        cargarDirJCombo("B", codP, codC, codD, cbxEditarBarrio);
        for (int i = 0; i < cbxEditarBarrio.getItemCount(); i++) {
            if (cbxEditarBarrio.getItemAt(i).getCodigo().equals(iB)) {
                cbxEditarBarrio.setSelectedIndex(i);
            }
        }
        txaEditarOtrasSenas.setText(oS);
    }

    /**
     * Reacciona a los eventos de selección de la tabla editar proveedores y 
     * obtiene la información del proveedor seleccionado.
     */
    private void selectProveedorEditar() {
        try {
            model = (DefaultTableModel) tbl_editar.getModel();
            int selectedRowIndex = tbl_editar.getSelectedRow();
            String cedula
                    = String.valueOf(model.getValueAt(selectedRowIndex, 0).toString());

            for (int i = 0; i < proveedores.size(); i++) {
                if (proveedores.get(i).getCedula().equals(cedula)) {
                    cargarEditarProveedor(proveedores.get(i));
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Carga los combos de dirección consecutivamente,
     * de acuerdo al lugar seleccionado en el combobox predecesor.
     * @param cbxCargar Combo a cargar
     * @param cbxP combo de provincia
     * @param cbxC combo de cantón
     * @param cbxD combo de distrito
     * @param p Inicial(Letra) del combo que siguiente a cargar
     */
    private void selectDir(JComboBox cbxCargar, JComboBox<DirFiltro> cbxP,
            JComboBox<DirFiltro> cbxC, JComboBox<DirFiltro> cbxD, String p) {

        String codP = "";
        String codC = "";
        String codD = "";

        if (p.equals("C")) {
            if (cbxP.getItemCount() > 0) {
                codP = cbxP.getItemAt(
                        cbxP.getSelectedIndex()).getCodigo();
            }
        } else if (p.equals("D")) {
            if (cbxC.getItemCount() > 0) {
                codP = cbxP.getItemAt(
                        cbxP.getSelectedIndex()).getCodigo();
                codC = cbxC.getItemAt(
                        cbxC.getSelectedIndex()).getCodigo();
            }
        } else if (p.equals("B")) {
            if (cbxD.getItemCount() > 0) {
                codP = cbxP.getItemAt(
                        cbxP.getSelectedIndex()).getCodigo();
                codC = cbxC.getItemAt(
                        cbxC.getSelectedIndex()).getCodigo();
                codD = cbxD.getItemAt(
                        cbxD.getSelectedIndex()).getCodigo();
            }
        }
        cargarDirJCombo(p, codP, codC, codD, cbxCargar);
    }
    
    /**
     * Preparar la información de la dirección, toma desde la interfaz todoos
     * los datos de la dirección
     * @param pnlEditar ¿es panel editar?
     * @param codDir código de dirección
     * @return la dirección
     */
    public Direccion prepararDireccion(boolean pnlEditar, int codDir) {

        String cP = cbxProvincia.getItemAt(cbxProvincia.getSelectedIndex()).getCodigo();
        String nP = cbxProvincia.getItemAt(cbxProvincia.getSelectedIndex()).getNombre();
        String cC = cbxCanton.getItemAt(cbxCanton.getSelectedIndex()).getCodigo();
        String nC = cbxCanton.getItemAt(cbxCanton.getSelectedIndex()).getNombre();
        String cD = cbxDistrito.getItemAt(cbxDistrito.getSelectedIndex()).getCodigo();
        String nD = cbxDistrito.getItemAt(cbxDistrito.getSelectedIndex()).getNombre();
        String cB = cbxBarrio.getItemAt(cbxBarrio.getSelectedIndex()).getCodigo();
        String nB = cbxBarrio.getItemAt(cbxBarrio.getSelectedIndex()).getNombre();
        String senas = txaOtrasSenas.getText();

        if (pnlEditar) {
            cP = cbxEditarProvincia.getItemAt(cbxEditarProvincia.getSelectedIndex()).getCodigo();
            nP = cbxEditarProvincia.getItemAt(cbxEditarProvincia.getSelectedIndex()).getNombre();
            cC = cbxEditarCanton.getItemAt(cbxEditarCanton.getSelectedIndex()).getCodigo();
            nC = cbxEditarCanton.getItemAt(cbxEditarCanton.getSelectedIndex()).getNombre();
            cD = cbxEditarDistrito.getItemAt(cbxEditarDistrito.getSelectedIndex()).getCodigo();
            nD = cbxEditarDistrito.getItemAt(cbxEditarDistrito.getSelectedIndex()).getNombre();
            cB = cbxEditarBarrio.getItemAt(cbxEditarBarrio.getSelectedIndex()).getCodigo();
            nB = cbxEditarBarrio.getItemAt(cbxEditarBarrio.getSelectedIndex()).getNombre();
            senas = txaEditarOtrasSenas.getText();
        }
        
        //verificar el panel y el check box para retornar null o la dirección
        //on toda la información ingresada
        if (pnlEditar) {
            if (ckbEditarDireccion.isSelected()) {
                return new Direccion(codDir, cP, nP, cC, nC, cD, nD, cB, nB, senas);
            } return null;
        } else {
            if (ckbAgregarDireccion.isSelected()) {
                return new Direccion(codDir, cP, nP, cC, nC, cD, nD, cB, nB, senas);
            } return null;
        }
    }
    
    /**
     * Preparar la información del proveedor.
     */
    private void prepararProveedor() {

        ArrayList<ArrayList<Object>> contactos = new ArrayList<>();
        ArrayList<Object> correo;
        for (int i = 0; i < lsCrearCorreos.getModel().getSize(); i++) {
            correo = new ArrayList<>();
            correo.add(TipoContacto.CORREO);
            correo.add(lsCrearCorreos.getModel().getElementAt(i));
            contactos.add(correo);
        }

        ArrayList<Object> telefono;
        for (int i = 0; i < lsCrearTelefonos.getModel().getSize(); i++) {
            telefono = new ArrayList<>();
            telefono.add(TipoContacto.TELEFONO);
            telefono.add(lsCrearTelefonos.getModel().getElementAt(i));
            contactos.add(telefono);
        }
        
        //enviar la información del proveedor para crear
        agregarProveedor(txt_crear_cedulaProveedor.getText().trim(), 
                cbxCrearTipoCedula.getSelectedItem().toString(), 
                txt_crear_nombreProveedor.getText().trim(), 
                prepararDireccion(false, 1), 
                contactos);
    }
    
    /**
     * Verificar la información suministrada y crear el proveedor.
     * @param nombre nombre del proveedor
     * @param cedula número de cédula del proveedor
     * @param dir dirección del proveedor
     * @param contactos lista de contactos
     */
    private void agregarProveedor(String cedula, String tipoCed, String nombre,  
            Direccion dir, ArrayList<ArrayList<Object>> contactos) {

//        System.out.println("Proveedor->Agregar->nombre: " + nombre);
//        System.out.println("Proveedor->Agregar->cedula: " + cedula);
//        System.out.println("Proveedor->Agregar->tipoCed: " + tipoCed);
//        System.out.println("Proveedor->Agregar->dir: " + dir);
//        System.out.println("Proveedor->Agregar->contactos: " + contactos.size());
        
        if (!nombre.isEmpty()) {
            try {
                boolean creado = controlador.crearProveedor(cedula, tipoCed, 
                        nombre, dir, contactos);
                if (creado) {
                    msg.mostrarMensaje(JOptionPane.INFORMATION_MESSAGE,
                            TipoMensaje.CUSTOMER_INSERTION_SUCCESS);
                    cargarTablas();
                    limpiarAgregar();
                    
                } else {
                    msg.mostrarMensaje(JOptionPane.ERROR_MESSAGE,
                            TipoMensaje.CUSTOMER_INSERTION_FAILURE);
                }
            } catch (NumberFormatException ex) {
                msg.mostrarMensaje(JOptionPane.ERROR_MESSAGE,
                        TipoMensaje.NUMBER_FORMAT_EXCEPTION);
                
            } catch (Exception ex) {
                msg.mostrarMensaje(JOptionPane.ERROR_MESSAGE,
                        TipoMensaje.SOMETHING_WENT_WRONG);
            }
        } else {
            msg.mostrarMensaje(JOptionPane.ERROR_MESSAGE,
                    TipoMensaje.EMPTY_CUSTOMER_FIELDS);
        }
    }

    /**
     * Obtiene de la interfaz toda la información necesaria para editar el
     * proveedor.
     */
    private void prepararEditarProveedor() {
        try {
            model = (DefaultTableModel) tbl_editar.getModel();
            int indiceFila = tbl_editar.getSelectedRow();
            
            System.out.println("PROVEEDOR->PREP-ACTUALIZAR->ÍNDICE_FILA: "+indiceFila);
            
            if (indiceFila >= 0) {
                String cedPersona = (String) model.getValueAt(indiceFila, 0);
                System.out.println("CEDULA PER EDITAR PROV: " + cedPersona);

                int codDir = 0;
                for (int i = 0; i < proveedores.size(); i++) {
                    if (proveedores.get(i).getCedula().equals(cedPersona)) {
                        if (proveedores.get(i).getDireccion() != null) {
                            codDir = proveedores.get(i).getDireccion().getCodigo();
                        }
                    }
                }
                actualizarProveedor(txtEditarNombreProveedor.getText().trim(),
                        prepararDireccion(true, codDir), cedPersona);
            } else {
                System.out.println("PROVEEDOR->PREP-ACTUALIZAR->NO_FILA_SELECCIONADA");
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
            ex.printStackTrace();
            msg.mostrarMensaje(JOptionPane.ERROR_MESSAGE, 
                    TipoMensaje.LIST_HANDLER_ERROR);
        } catch (NullPointerException ex) {
            ex.printStackTrace();
            msg.mostrarMensaje(JOptionPane.ERROR_MESSAGE, 
                    TipoMensaje.LIST_HANDLER_ERROR);
        } catch (Exception ex) {
            ex.printStackTrace();
            msg.mostrarMensaje(JOptionPane.ERROR_MESSAGE, 
                    TipoMensaje.SOMETHING_WENT_WRONG);
        } 
    }
    
    /**
     * Verifica la nueva información ingresada y actualiza la información del 
     * proveedor.
     * @param nombre El nuevo nombre del proveedor
     * @param cedula Cédula (llave primaria) para identificar el proveedor.
     * @param dir Objeto dirección con la información.
     */
    private void actualizarProveedor(String nombre, Direccion dir, 
            String cedula) {
        
        if (!nombre.isEmpty()) {
            try {
                boolean actualizado = controlador.actualizarProveedor(
                        nombre, dir, cedula);

                if (actualizado) {
                    //System.out.println("PROVEEDOR->ACTUALIZAR->actualizado: VERDADERO!");
                    cargarTablas();
                    //limpiar los campos con la información
                    txtEditarCorreoProveedor.setText("");
                    txtEditarNombreProveedor.setText("");
                    txtEditarTelefono.setText("");
                    txaEditarOtrasSenas.setText("");
                    lsCorreos.setModel(new DefaultListModel());
                    lsCorreos.setModel(new DefaultListModel());
                } else {
                    System.out.println("PROVEEDOR->ACTUALIZAR->actualizado: FALSO!");
                }
            } catch (NumberFormatException ex) {
                System.err.println(ex);
                msg.mostrarMensaje(JOptionPane.ERROR_MESSAGE, 
                        TipoMensaje.NUMBER_FORMAT_EXCEPTION);
            } catch (Exception ex) {
                System.err.println(ex);
                msg.mostrarMensaje(JOptionPane.ERROR_MESSAGE, 
                        TipoMensaje.SOMETHING_WENT_WRONG);
            }
        } else {
            System.out.println("PROVEEDOR->EDITAR->CAMPOS_VACÍOS");
        }
    }

    /**
     * Guarda los contactos ingresados desde la pestaña de edición.
     * @param tel ¿es teléfono (true) o correo (false)?
     */
    private void guardarEditContacto(boolean tel) {
        if(tel) {
            String telefono = txtEditarTelefono.getText().trim();
            int indice = 0;
            try { // TELEFONOS
                if (verificacion.validaTelefono(telefono)) {

                    indice = tbl_editar.getSelectedRow();
                    String cedula = 
                        tbl_editar.getModel().getValueAt(indice, 0).toString();
                    for (Proveedor p: proveedores) {
                        if (p.getCedula().equals(cedula)) {
                            controlador.crearContactoProveedor(
                                TipoContacto.TELEFONO, telefono, p.getCedula());
                            
                            editarTelefonos = new ArrayList<>();
                            System.out.println("Proveedor->Editar->GuardarContacto: " + controlador.getContactos().size());
                            for (Contacto ct: controlador.getContactos()) {
                                if (ct.getTipo().equals(TipoContacto.TELEFONO)) {
                                    
                                    editarTelefonos.add(ct);
                                    DefaultListModel<String> m = 
                                            new DefaultListModel<>();
                                    for (int i=0; i<editarTelefonos.size(); i++) {
                                        m.addElement(
                                            editarTelefonos.get(i).getInfo());
                                    }
                                    lsTelefonos.setModel(m);
                                    txtEditarTelefono.setText("");
                                }
                            }
                        }
                    }
                } else {
                    msg.mostrarMensaje(JOptionPane.ERROR_MESSAGE, 
                            TipoMensaje.PHONE_SYNTAX_FAILURE);
                }
//            } catch (ArrayIndexOutOfBoundsException | IllegalArgumentException | NullPointerException ex) {
//                //Imprimir la pila de excepciones
//                for (StackTraceElement ste: ex.getStackTrace()) {
//                    System.out.println(ste);
//                }
//                System.out.println(ex.getStackTrace());
//                msg.mostrarMensaje(JOptionPane.ERROR_MESSAGE, TipoMensaje.ANY_ROW_SELECTED);
            } catch (Exception ex) {
                ex.printStackTrace();
                msg.mostrarMensaje(JOptionPane.ERROR_MESSAGE, TipoMensaje.SOMETHING_WENT_WRONG);
            } finally {
                cargarTablas();
                if (indice >= 0) {
                    tbl_editar.setRowSelectionInterval(indice, indice);
                }
            }
        } else { // CORREOS
            String correo = txtEditarCorreoProveedor.getText().trim();
            int indice = 0;
            try {
                if (verificacion.validaEmail(correo)) {
                    indice = tbl_editar.getSelectedRow();
                    String cedula = 
                        tbl_editar.getModel().getValueAt(indice, 0).toString();
                    for (Proveedor p: proveedores) {
                        if (p.getCedula().equals(cedula)) {
                            controlador.crearContactoProveedor(
                                    TipoContacto.CORREO, correo, p.getCedula());
                            editarCorreos = new ArrayList<>();
                            
                            for (Contacto ct: controlador.getContactos()) {
                                if (ct.getTipo().equals(TipoContacto.CORREO)) {
                                    
                                    editarCorreos.add(ct);
                                    DefaultListModel<String> m = 
                                            new DefaultListModel<>();
                                    for (int i=0; i<editarCorreos.size(); i++) {
                                        m.addElement(
                                                editarCorreos.get(i).getInfo());
                                    }
                                    lsCorreos.setModel(m);
                                    txtEditarCorreoProveedor.setText("");
                                }
                            }
                        }
                    }
                } else {
                    msg.mostrarMensaje(JOptionPane.ERROR_MESSAGE, 
                            TipoMensaje.EMAIL_SYNTAX_FAILURE);
                }
            } catch (ArrayIndexOutOfBoundsException | IllegalArgumentException | NullPointerException ex) {
                msg.mostrarMensaje(JOptionPane.ERROR_MESSAGE, TipoMensaje.ANY_ROW_SELECTED);
            } catch (Exception ex) {
                msg.mostrarMensaje(JOptionPane.ERROR_MESSAGE, TipoMensaje.SOMETHING_WENT_WRONG);
            } finally {
                cargarTablas();
                if (indice >= 0) {
                    tbl_editar.setRowSelectionInterval(indice, indice);
                }
            }
        }
    }
    
    /**
     * Borra el contacto seleccionado desde la pestaña de edición.
     * @param tel ¿es teléfono (true) o correo (false)?
     */
    private void cancelEditContacto(boolean tel) {
        if(tel) {
            int indiceProveedor = tbl_editar.getSelectedRow();//para recordar el proveedor seleccionado
            try {
                
                int indiceTel = lsTelefonos.getSelectedIndex();            
                controlador.inactivarContacto(
                        editarTelefonos.get(indiceTel).getCodigo());
                editarTelefonos.remove(indiceTel);

                DefaultListModel<String> m = new DefaultListModel<>();
                for (int i=0; i<editarTelefonos.size(); i++) {
                    m.addElement(editarTelefonos.get(i).getInfo());
                }
                lsTelefonos.setModel(m);
                
            } catch(NullPointerException ex) {
                System.out.println("Cancel editar telefono: null pointer");
                ex.printStackTrace();
                
            } catch (ArrayIndexOutOfBoundsException ex) {
                System.out.println("Cancel editar telefono: index out of bounds");
                ex.printStackTrace();
                
            } catch (Exception ex) {
                System.out.println("Cancel editar telefono: exception");
                ex.printStackTrace();
                
            } finally {
                cargarTablas();
                tbl_editar.setRowSelectionInterval(indiceProveedor, indiceProveedor);// mantener activa la seleccion del proveedor editado
            }
        } else {
            int indice = 0;
            try {
                indice = lsCorreos.getSelectedIndex();
                controlador.inactivarContacto(editarCorreos.get(indice).getCodigo());
                editarCorreos.remove(indice);

                DefaultListModel<String> m = new DefaultListModel<>();
                for (int i=0; i<editarCorreos.size(); i++) {
                    m.addElement(editarCorreos.get(i).getInfo());
                }
                lsCorreos.setModel(m);
            } catch(NullPointerException ex) {
                System.out.println("Cancel editar correo: null pointer ");
                ex.printStackTrace();
            } catch (ArrayIndexOutOfBoundsException ex) {
                System.out.println("Cancel editar correo: index out of bounds");
                ex.printStackTrace();
            } catch (Exception ex) {
                System.out.println("Cancel editar correo: exception");
                ex.printStackTrace();
            } finally {
                cargarTablas();
                tbl_editar.setRowSelectionInterval(indice, indice);
            }
        }
    }
    
    /**
     * Guarda la información de contacto al agregar un provedor.
     * @param tel ¿es teléfono (true) o correo (false)?
     */
    private void guardarAgregarContacto(boolean tel) {
        if(tel) {
            String telefono = txt_agregarTelefono.getText().trim();
            if (verificacion.validaTelefono(telefono) && 
                    !crearTelefonos.contains(telefono)) {
                
                crearTelefonos.add(telefono);
                DefaultListModel<String> m = new DefaultListModel<>();
                for (int i=0; i<crearTelefonos.size(); i++) {
                    m.addElement(crearTelefonos.get(i));
                }
                lsCrearTelefonos.setModel(m);
            } else {
                msg.mostrarMensaje(JOptionPane.ERROR_MESSAGE, 
                        TipoMensaje.PHONE_SYNTAX_FAILURE);
            }
            txt_agregarTelefono.setText("");
        } else {
            
            String correo = txt_agregarCorreo.getText().trim();
            if (verificacion.validaEmail(correo) 
                    && !crearCorreos.contains(correo)) {
                crearCorreos.add(correo);
                DefaultListModel<String> m = new DefaultListModel<>();
                for (int i=0; i<crearCorreos.size(); i++) {
                    m.addElement(crearCorreos.get(i));
                }
                lsCrearCorreos.setModel(m);
            } else {
                msg.mostrarMensaje(JOptionPane.ERROR_MESSAGE, 
                        TipoMensaje.EMAIL_SYNTAX_FAILURE);
            }
            txt_agregarCorreo.setText("");
        }
        
    }
    
    /**
     * Marca los proveedores como activos o inactivos en la tabla 
     * correspondiente dependiendo del estado que tengan en la BD.
     * @param tabla tabla a cargar con proveedores
     * @param rbD radio botón deshabilitar
     * @param rbH  radio botón habilitar
     */
    private void selecProveedorPorEstado(JTable tabla, 
            JRadioButton rbD, JRadioButton rbH) {
        //CLIENTES ACTIVOS MOUSE
        try {
            model = (DefaultTableModel) tabla.getModel();
            int selectedRowIndex = tabla.getSelectedRow();
            String cedula
            = String.valueOf(model.getValueAt(selectedRowIndex, 0).toString());

            for (int i = 0; i < proveedores.size(); i++) {
                if (proveedores.get(i).getCedula().equals(cedula)) {
                    //Si el codigo coincide
                    if (proveedores.get(i).getEstado().equals(Estado.Activo)) {
                        //Verifica el tipo de estado
                        rbD.setSelected(true);
                    } else {
                        rbH.setSelected(true);
                    }
                }
            }
        } catch (ArrayIndexOutOfBoundsException | NullPointerException ex) {
            ex.printStackTrace();
            msg.mostrarMensaje(JOptionPane.ERROR_MESSAGE, TipoMensaje.ANY_ROW_SELECTED);
        }
        catch (Exception ex) {
            msg.mostrarMensaje(JOptionPane.ERROR_MESSAGE, TipoMensaje.SOMETHING_WENT_WRONG);
        }
    }
    
    /**
     * Cambia el estado del proveedor (activo o inactivo).
     */
    private void activarInactivarProveedor() {
        
        try {
            model = tbDeshab.getSelectedIndex() == 0 ? 
                    (DefaultTableModel) tblProveedoresActivos.getModel() : 
                    (DefaultTableModel) tblProveedoresInactivos.getModel();

            int selectedRowIndex = tbDeshab.getSelectedIndex() == 0 ? 
                    tblProveedoresActivos.getSelectedRow() : 
                    tblProveedoresInactivos.getSelectedRow();

            Estado estado
                    = rbDeshabHabilitarProveedor.isSelected() ? Estado.Activo : 
                    Estado.Deshabilitado;

            if (estado.equals(Estado.Deshabilitado)) {
                controlador.inactivarProveedor(
                        model.getValueAt(selectedRowIndex, 0).toString());
            } else {
                controlador.activarProveedor(
                        model.getValueAt(selectedRowIndex, 0).toString());
            }
            //Actualizar tablas
            cargarTablas();
        } catch (Exception e) {
            e.printStackTrace();
            msg.mostrarMensaje(JOptionPane.INFORMATION_MESSAGE,
                    TipoMensaje.ANY_ROW_SELECTED);
        }
    }
    
    /**
     * Muestra el panel de dirección para agregarla o editarla.
     * @param pnlCrear ¿es el panel crear?
     * @param ckb check box agregar dirección
     */
    private void mostrarDireccion(boolean pnlCrear, JCheckBox ckb) {
                
        if (pnlCrear) {
            if (ckb.isSelected()) {
                
                int x = pnl_agregar.getWidth()-363;
                int y = 10;
                int w = 334;
                int h = 235; 
                
                pnlCrearDireccion.setVisible(true);
                pnlCrearDireccion.setBounds(x, y, w, h);
                pnl_agregar.add(pnlCrearDireccion);
            } else {
                pnlCrearDireccion.setVisible(false);
                //pnl_agregar.repaint();
            }
        //panel editar o actualizar
        } else {
            if (ckb.isSelected()) {
                int x = pnlEditarInfoBase.getX() + pnlEditarInfoBase.getWidth() +12;
                int y = 12;
                int w = 560;
                int h = 205; 
                
                pnlEditarDireccion.setVisible(true);
                pnlEditarDireccion.setBounds(x, y, w, h);
                //pnlEditarTelefono.add(pnlEditarDireccion);
            } else {
                pnlEditarDireccion.setVisible(false);
            }
        }
    }
    
    /**
     * Limpia los campos de texto y demás componentes de interfaz 
     * ubicados en la pestaña AgregarProveedor.
     */
    private void limpiarAgregar() {
        cbxCrearTipoCedula.setSelectedIndex(0);
        txt_crear_cedulaProveedor.setText("");
        txt_crear_nombreProveedor.setText("");
        ckbAgregarDireccion.setSelected(true);
        lsCrearTelefonos.removeAll();
        txt_agregarTelefono.setText("");
        lsCrearCorreos.removeAll();
        txt_agregarCorreo.setText("");
        cargarDirJCombo("P", "", "", "", cbxProvincia);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bg_Habilitar = new javax.swing.ButtonGroup();
        pnl_modProveedor = new javax.swing.JPanel();
        tb_modProveedor = new javax.swing.JTabbedPane();
        pnl_listado = new javax.swing.JPanel();
        lblListadoProveedor = new javax.swing.JLabel();
        txtListadoProveedor = new javax.swing.JTextField();
        scpnlTblListadoProveedor = new javax.swing.JScrollPane();
        tbListadoProveedor = new javax.swing.JTable();
        pnl_agregar = new javax.swing.JPanel();
        pnlCrearContactoProveedor = new javax.swing.JPanel();
        tbCrearContactoProveedores = new javax.swing.JTabbedPane();
        scpnlProveedoresCrearTelefono = new javax.swing.JScrollPane();
        pnlCrearTelefono = new javax.swing.JPanel();
        lblCrearTelefono = new javax.swing.JLabel();
        spnlCrearTelefonos = new javax.swing.JScrollPane();
        lsCrearTelefonos = new javax.swing.JList<>();
        txt_agregarTelefono = new javax.swing.JTextField();
        btnAgregarTelefono = new javax.swing.JButton();
        pnlCrearCorreo = new javax.swing.JPanel();
        lbl_crear_correo = new javax.swing.JLabel();
        scpnl_crearCorreo = new javax.swing.JScrollPane();
        lsCrearCorreos = new javax.swing.JList<>();
        txt_agregarCorreo = new javax.swing.JTextField();
        btnAgregarCorreo = new javax.swing.JButton();
        spnl_crear_proveedores = new javax.swing.JScrollPane();
        tbl_crear = new javax.swing.JTable();
        pnlCrearDireccion = new javax.swing.JPanel();
        cbxDistrito = new javax.swing.JComboBox<>();
        cbxBarrio = new javax.swing.JComboBox<>();
        scpnlCrearOtrasSenas = new javax.swing.JScrollPane();
        txaOtrasSenas = new javax.swing.JTextArea();
        cbxCanton = new javax.swing.JComboBox<>();
        lbl_crear_distrito = new javax.swing.JLabel();
        lbl_crear_otrasSenas = new javax.swing.JLabel();
        lbl_crear_barrio = new javax.swing.JLabel();
        cbxProvincia = new javax.swing.JComboBox<>();
        lbl_crear_provincia = new javax.swing.JLabel();
        lbl_crear_canton = new javax.swing.JLabel();
        btnCrearProveedor = new javax.swing.JButton();
        pnlCrearInfoBase = new javax.swing.JPanel();
        txt_crear_cedulaProveedor = new javax.swing.JTextField();
        txt_crear_nombreProveedor = new javax.swing.JTextField();
        ckbAgregarDireccion = new javax.swing.JCheckBox();
        lbl_crear_nombreProveedor = new javax.swing.JLabel();
        lbl_crear_cedulaProveedor = new javax.swing.JLabel();
        lblCrearTipoCedula = new javax.swing.JLabel();
        cbxCrearTipoCedula = new javax.swing.JComboBox<>();
        pnl_actualizar = new javax.swing.JPanel();
        spnl_editar_proveedor = new javax.swing.JScrollPane();
        tbl_editar = new javax.swing.JTable();
        btnEditarProveedor = new javax.swing.JButton();
        tbEditarContactoProveedor = new javax.swing.JTabbedPane();
        scpnl_EditarProveedor = new javax.swing.JScrollPane();
        pnlEditarTelefono = new javax.swing.JPanel();
        pnlEditarDireccion = new javax.swing.JPanel();
        cbxEditarDistrito = new javax.swing.JComboBox<>();
        lblEditarDistrito = new javax.swing.JLabel();
        cbxEditarBarrio = new javax.swing.JComboBox<>();
        lblEditarBarrio = new javax.swing.JLabel();
        lblEditarCanton = new javax.swing.JLabel();
        scpnlEditarOtrasSenas = new javax.swing.JScrollPane();
        txaEditarOtrasSenas = new javax.swing.JTextArea();
        cbxEditarProvincia = new javax.swing.JComboBox<>();
        lblEditarProvincia = new javax.swing.JLabel();
        lblEditarOtrasSenas = new javax.swing.JLabel();
        cbxEditarCanton = new javax.swing.JComboBox<>();
        pnlEditarInfoBase = new javax.swing.JPanel();
        lblEditarNombreProveedor = new javax.swing.JLabel();
        txtEditarCedulaProveedor = new javax.swing.JTextField();
        lblEditarCedulaProveedor = new javax.swing.JLabel();
        txtEditarNombreProveedor = new javax.swing.JTextField();
        ckbEditarDireccion = new javax.swing.JCheckBox();
        scpnl_EditarContactoProveedor = new javax.swing.JScrollPane();
        pnlEditarCorreo = new javax.swing.JPanel();
        pnlEditarContactoProveedor = new javax.swing.JPanel();
        lblEditarTelefono = new javax.swing.JLabel();
        lblEditarCorreo = new javax.swing.JLabel();
        scpnlEditarListaTelef = new javax.swing.JScrollPane();
        lsTelefonos = new javax.swing.JList<>();
        txtEditarTelefono = new javax.swing.JTextField();
        btnEditarGuardarTel = new javax.swing.JButton();
        btnEditarCancelTel = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        scpnlEditarListaCorreo = new javax.swing.JScrollPane();
        lsCorreos = new javax.swing.JList<>();
        txtEditarCorreoProveedor = new javax.swing.JTextField();
        btnEditarGuardarCorreo = new javax.swing.JButton();
        btnEditarCancelCorreo = new javax.swing.JButton();
        pnlHabilitar = new javax.swing.JPanel();
        lblDeshabSelectProveedor = new javax.swing.JLabel();
        tbDeshab = new javax.swing.JTabbedPane();
        scpnlProveedorDeshab = new javax.swing.JScrollPane();
        tblProveedoresActivos = new javax.swing.JTable();
        scpnlProveedorHabilitar = new javax.swing.JScrollPane();
        tblProveedoresInactivos = new javax.swing.JTable();
        pnlDeshabContainer = new javax.swing.JPanel();
        rbDeshabDeshabProveedor = new javax.swing.JRadioButton();
        rbDeshabHabilitarProveedor = new javax.swing.JRadioButton();
        btn_deshabilitar = new javax.swing.JButton();

        setBackground(new java.awt.Color(153, 51, 0));
        setClosable(true);
        setIconifiable(true);
        setTitle("Módulo Proveedores");
        setPreferredSize(new java.awt.Dimension(1240, 680));

        tb_modProveedor.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        lblListadoProveedor.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        lblListadoProveedor.setText("Buscar proveedor: ");

        txtListadoProveedor.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        txtListadoProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtListadoProveedorKeyReleased(evt);
            }
        });

        tbListadoProveedor.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cédula", "T. Cédula", "Nombre", "Contactos", "Dirección"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbListadoProveedor.getTableHeader().setReorderingAllowed(false);
        scpnlTblListadoProveedor.setViewportView(tbListadoProveedor);

        javax.swing.GroupLayout pnl_listadoLayout = new javax.swing.GroupLayout(pnl_listado);
        pnl_listado.setLayout(pnl_listadoLayout);
        pnl_listadoLayout.setHorizontalGroup(
            pnl_listadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_listadoLayout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addGroup(pnl_listadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scpnlTblListadoProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 1165, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnl_listadoLayout.createSequentialGroup()
                        .addComponent(lblListadoProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtListadoProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 992, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        pnl_listadoLayout.setVerticalGroup(
            pnl_listadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_listadoLayout.createSequentialGroup()
                .addGroup(pnl_listadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_listadoLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(lblListadoProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_listadoLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(txtListadoProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(scpnlTblListadoProveedor, javax.swing.GroupLayout.DEFAULT_SIZE, 506, Short.MAX_VALUE)
                .addContainerGap())
        );

        tb_modProveedor.addTab("Listado proveedores", pnl_listado);

        pnlCrearContactoProveedor.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Contacto:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Yu Gothic UI", 0, 18))); // NOI18N
        pnlCrearContactoProveedor.setAutoscrolls(true);

        tbCrearContactoProveedores.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        tbCrearContactoProveedores.setTabPlacement(javax.swing.JTabbedPane.RIGHT);

        scpnlProveedoresCrearTelefono.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        pnlCrearTelefono.setRequestFocusEnabled(false);

        lblCrearTelefono.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        lblCrearTelefono.setText("Teléfono:");

        lsCrearTelefonos.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        spnlCrearTelefonos.setViewportView(lsCrearTelefonos);

        txt_agregarTelefono.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        btnAgregarTelefono.setFont(new java.awt.Font("Yu Gothic UI", 1, 16)); // NOI18N
        btnAgregarTelefono.setText("+");
        btnAgregarTelefono.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarTelefonoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlCrearTelefonoLayout = new javax.swing.GroupLayout(pnlCrearTelefono);
        pnlCrearTelefono.setLayout(pnlCrearTelefonoLayout);
        pnlCrearTelefonoLayout.setHorizontalGroup(
            pnlCrearTelefonoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCrearTelefonoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlCrearTelefonoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnlCrearTelefonoLayout.createSequentialGroup()
                        .addComponent(txt_agregarTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAgregarTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblCrearTelefono, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(spnlCrearTelefonos))
                .addContainerGap(105, Short.MAX_VALUE))
        );
        pnlCrearTelefonoLayout.setVerticalGroup(
            pnlCrearTelefonoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCrearTelefonoLayout.createSequentialGroup()
                .addComponent(lblCrearTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(spnlCrearTelefonos, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlCrearTelefonoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnAgregarTelefono, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_agregarTelefono))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        scpnlProveedoresCrearTelefono.setViewportView(pnlCrearTelefono);

        tbCrearContactoProveedores.addTab("", new javax.swing.ImageIcon(getClass().getResource("/recursos/telefono.png")), scpnlProveedoresCrearTelefono); // NOI18N

        lbl_crear_correo.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        lbl_crear_correo.setText("Correo Electrónico:");

        scpnl_crearCorreo.setViewportView(lsCrearCorreos);

        btnAgregarCorreo.setFont(new java.awt.Font("Yu Gothic UI", 1, 16)); // NOI18N
        btnAgregarCorreo.setText("+");
        btnAgregarCorreo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarCorreoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlCrearCorreoLayout = new javax.swing.GroupLayout(pnlCrearCorreo);
        pnlCrearCorreo.setLayout(pnlCrearCorreoLayout);
        pnlCrearCorreoLayout.setHorizontalGroup(
            pnlCrearCorreoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCrearCorreoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlCrearCorreoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(pnlCrearCorreoLayout.createSequentialGroup()
                        .addComponent(txt_agregarCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAgregarCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(scpnl_crearCorreo, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_crear_correo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        pnlCrearCorreoLayout.setVerticalGroup(
            pnlCrearCorreoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCrearCorreoLayout.createSequentialGroup()
                .addComponent(lbl_crear_correo, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scpnl_crearCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlCrearCorreoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnAgregarCorreo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_agregarCorreo))
                .addContainerGap(32, Short.MAX_VALUE))
        );

        tbCrearContactoProveedores.addTab("", new javax.swing.ImageIcon(getClass().getResource("/recursos/email.png")), pnlCrearCorreo); // NOI18N

        javax.swing.GroupLayout pnlCrearContactoProveedorLayout = new javax.swing.GroupLayout(pnlCrearContactoProveedor);
        pnlCrearContactoProveedor.setLayout(pnlCrearContactoProveedorLayout);
        pnlCrearContactoProveedorLayout.setHorizontalGroup(
            pnlCrearContactoProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tbCrearContactoProveedores, javax.swing.GroupLayout.DEFAULT_SIZE, 358, Short.MAX_VALUE)
        );
        pnlCrearContactoProveedorLayout.setVerticalGroup(
            pnlCrearContactoProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCrearContactoProveedorLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tbCrearContactoProveedores))
        );

        tbl_crear.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cédula", "T. Cédula", "Nombre", "Contactos", "Dirección"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_crear.getTableHeader().setReorderingAllowed(false);
        spnl_crear_proveedores.setViewportView(tbl_crear);

        pnlCrearDireccion.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Dirección", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Yu Gothic UI", 0, 18))); // NOI18N

        cbxDistrito.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cbxDistrito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxDistritoActionPerformed(evt);
            }
        });

        cbxBarrio.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        txaOtrasSenas.setColumns(20);
        txaOtrasSenas.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        txaOtrasSenas.setLineWrap(true);
        txaOtrasSenas.setRows(3);
        txaOtrasSenas.setWrapStyleWord(true);
        scpnlCrearOtrasSenas.setViewportView(txaOtrasSenas);

        cbxCanton.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cbxCanton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxCantonActionPerformed(evt);
            }
        });

        lbl_crear_distrito.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        lbl_crear_distrito.setText("Distrito: ");

        lbl_crear_otrasSenas.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        lbl_crear_otrasSenas.setText("Otras señas: ");

        lbl_crear_barrio.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        lbl_crear_barrio.setText("Barrio: ");

        cbxProvincia.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cbxProvincia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxProvinciaActionPerformed(evt);
            }
        });

        lbl_crear_provincia.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        lbl_crear_provincia.setText("Provincia: ");

        lbl_crear_canton.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        lbl_crear_canton.setText("Cantón: ");

        javax.swing.GroupLayout pnlCrearDireccionLayout = new javax.swing.GroupLayout(pnlCrearDireccion);
        pnlCrearDireccion.setLayout(pnlCrearDireccionLayout);
        pnlCrearDireccionLayout.setHorizontalGroup(
            pnlCrearDireccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCrearDireccionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlCrearDireccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_crear_otrasSenas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(scpnlCrearOtrasSenas, javax.swing.GroupLayout.DEFAULT_SIZE, 310, Short.MAX_VALUE)
                    .addGroup(pnlCrearDireccionLayout.createSequentialGroup()
                        .addGroup(pnlCrearDireccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlCrearDireccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(lbl_crear_provincia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lbl_crear_barrio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(lbl_crear_canton)
                            .addComponent(lbl_crear_distrito, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(31, 31, 31)
                        .addGroup(pnlCrearDireccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbxDistrito, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbxBarrio, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbxCanton, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbxProvincia, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        pnlCrearDireccionLayout.setVerticalGroup(
            pnlCrearDireccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCrearDireccionLayout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(pnlCrearDireccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbxProvincia, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlCrearDireccionLayout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(lbl_crear_provincia, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(6, 6, 6)
                .addGroup(pnlCrearDireccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cbxCanton, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlCrearDireccionLayout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(lbl_crear_canton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlCrearDireccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cbxDistrito, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(lbl_crear_distrito, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlCrearDireccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbl_crear_barrio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbxBarrio))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbl_crear_otrasSenas)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scpnlCrearOtrasSenas, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        btnCrearProveedor.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        btnCrearProveedor.setText("Crear Proveedor");
        btnCrearProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCrearProveedorActionPerformed(evt);
            }
        });

        pnlCrearInfoBase.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Información básica", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Yu Gothic UI", 0, 18))); // NOI18N
        pnlCrearInfoBase.setFont(new java.awt.Font("Yu Gothic UI", 0, 16)); // NOI18N

        txt_crear_cedulaProveedor.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        txt_crear_cedulaProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_crear_cedulaProveedorActionPerformed(evt);
            }
        });

        txt_crear_nombreProveedor.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        ckbAgregarDireccion.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        ckbAgregarDireccion.setSelected(true);
        ckbAgregarDireccion.setText("Agregar dirección");
        ckbAgregarDireccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ckbAgregarDireccionActionPerformed(evt);
            }
        });

        lbl_crear_nombreProveedor.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        lbl_crear_nombreProveedor.setText("Nombre:");

        lbl_crear_cedulaProveedor.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        lbl_crear_cedulaProveedor.setText("Cédula:");

        lblCrearTipoCedula.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        lblCrearTipoCedula.setText("Tipo de cédula:");

        cbxCrearTipoCedula.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        cbxCrearTipoCedula.setModel(new javax.swing.DefaultComboBoxModel<>( TipoCedula.getValues() ));

        javax.swing.GroupLayout pnlCrearInfoBaseLayout = new javax.swing.GroupLayout(pnlCrearInfoBase);
        pnlCrearInfoBase.setLayout(pnlCrearInfoBaseLayout);
        pnlCrearInfoBaseLayout.setHorizontalGroup(
            pnlCrearInfoBaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCrearInfoBaseLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlCrearInfoBaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlCrearInfoBaseLayout.createSequentialGroup()
                        .addGap(0, 183, Short.MAX_VALUE)
                        .addComponent(ckbAgregarDireccion))
                    .addGroup(pnlCrearInfoBaseLayout.createSequentialGroup()
                        .addGroup(pnlCrearInfoBaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCrearTipoCedula, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbl_crear_cedulaProveedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbl_crear_nombreProveedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlCrearInfoBaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(cbxCrearTipoCedula, 0, 215, Short.MAX_VALUE)
                            .addComponent(txt_crear_cedulaProveedor)
                            .addComponent(txt_crear_nombreProveedor))))
                .addContainerGap())
        );
        pnlCrearInfoBaseLayout.setVerticalGroup(
            pnlCrearInfoBaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCrearInfoBaseLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlCrearInfoBaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxCrearTipoCedula, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCrearTipoCedula, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlCrearInfoBaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_crear_cedulaProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_crear_cedulaProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlCrearInfoBaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_crear_nombreProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_crear_nombreProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(ckbAgregarDireccion)
                .addContainerGap())
        );

        javax.swing.GroupLayout pnl_agregarLayout = new javax.swing.GroupLayout(pnl_agregar);
        pnl_agregar.setLayout(pnl_agregarLayout);
        pnl_agregarLayout.setHorizontalGroup(
            pnl_agregarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_agregarLayout.createSequentialGroup()
                .addGroup(pnl_agregarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnl_agregarLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCrearProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnl_agregarLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(pnl_agregarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnl_agregarLayout.createSequentialGroup()
                                .addComponent(pnlCrearInfoBase, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(30, 30, 30)
                                .addComponent(pnlCrearContactoProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(28, 28, 28)
                                .addComponent(pnlCrearDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(spnl_crear_proveedores))))
                .addContainerGap())
        );
        pnl_agregarLayout.setVerticalGroup(
            pnl_agregarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_agregarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_agregarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(pnlCrearDireccion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlCrearContactoProveedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlCrearInfoBase, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(spnl_crear_proveedores, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCrearProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5))
        );

        tb_modProveedor.addTab("Agregar proveedor", pnl_agregar);

        tbl_editar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cédula", "T. Cédula", "Nombre", "Contactos", "Dirección"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_editar.getTableHeader().setReorderingAllowed(false);
        tbl_editar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_editarMouseClicked(evt);
            }
        });
        tbl_editar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbl_editarKeyReleased(evt);
            }
        });
        spnl_editar_proveedor.setViewportView(tbl_editar);

        btnEditarProveedor.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        btnEditarProveedor.setText("Guardar Cambios");
        btnEditarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarProveedorActionPerformed(evt);
            }
        });

        tbEditarContactoProveedor.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        tbEditarContactoProveedor.setTabPlacement(javax.swing.JTabbedPane.LEFT);

        scpnl_EditarProveedor.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        pnlEditarTelefono.setRequestFocusEnabled(false);

        pnlEditarDireccion.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Dirección", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Yu Gothic UI", 0, 18))); // NOI18N

        cbxEditarDistrito.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        cbxEditarDistrito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxEditarDistritoActionPerformed(evt);
            }
        });

        lblEditarDistrito.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        lblEditarDistrito.setText("Distrito:");

        cbxEditarBarrio.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        lblEditarBarrio.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        lblEditarBarrio.setText("Barrio:");

        lblEditarCanton.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        lblEditarCanton.setText("Cantón:");

        txaEditarOtrasSenas.setColumns(20);
        txaEditarOtrasSenas.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        txaEditarOtrasSenas.setLineWrap(true);
        txaEditarOtrasSenas.setRows(3);
        txaEditarOtrasSenas.setWrapStyleWord(true);
        scpnlEditarOtrasSenas.setViewportView(txaEditarOtrasSenas);

        cbxEditarProvincia.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        cbxEditarProvincia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxEditarProvinciaActionPerformed(evt);
            }
        });

        lblEditarProvincia.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        lblEditarProvincia.setText("Provincia:");

        lblEditarOtrasSenas.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        lblEditarOtrasSenas.setText("Otras señas:");

        cbxEditarCanton.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        cbxEditarCanton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxEditarCantonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlEditarDireccionLayout = new javax.swing.GroupLayout(pnlEditarDireccion);
        pnlEditarDireccion.setLayout(pnlEditarDireccionLayout);
        pnlEditarDireccionLayout.setHorizontalGroup(
            pnlEditarDireccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEditarDireccionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlEditarDireccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlEditarDireccionLayout.createSequentialGroup()
                        .addComponent(lblEditarProvincia, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbxEditarProvincia, 0, 216, Short.MAX_VALUE))
                    .addGroup(pnlEditarDireccionLayout.createSequentialGroup()
                        .addComponent(lblEditarBarrio, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbxEditarBarrio, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnlEditarDireccionLayout.createSequentialGroup()
                        .addComponent(lblEditarCanton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbxEditarCanton, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnlEditarDireccionLayout.createSequentialGroup()
                        .addComponent(lblEditarDistrito, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbxEditarDistrito, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addGroup(pnlEditarDireccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(scpnlEditarOtrasSenas, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                    .addComponent(lblEditarOtrasSenas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlEditarDireccionLayout.setVerticalGroup(
            pnlEditarDireccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEditarDireccionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlEditarDireccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEditarProvincia, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxEditarProvincia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEditarOtrasSenas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlEditarDireccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnlEditarDireccionLayout.createSequentialGroup()
                        .addGroup(pnlEditarDireccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblEditarCanton, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbxEditarCanton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlEditarDireccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblEditarDistrito, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbxEditarDistrito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlEditarDireccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblEditarBarrio, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbxEditarBarrio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(scpnlEditarOtrasSenas))
                .addContainerGap())
        );

        pnlEditarInfoBase.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Información básica", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Yu Gothic UI", 0, 18))); // NOI18N

        lblEditarNombreProveedor.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        lblEditarNombreProveedor.setText("Nombre:");

        txtEditarCedulaProveedor.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        txtEditarCedulaProveedor.setToolTipText("No se puede editar, agregue un proveedor nuevo si lo necesita");
        txtEditarCedulaProveedor.setEnabled(false);

        lblEditarCedulaProveedor.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        lblEditarCedulaProveedor.setText("Cédula:");

        txtEditarNombreProveedor.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        ckbEditarDireccion.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        ckbEditarDireccion.setSelected(true);
        ckbEditarDireccion.setText("Agregar dirección");
        ckbEditarDireccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ckbEditarDireccionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlEditarInfoBaseLayout = new javax.swing.GroupLayout(pnlEditarInfoBase);
        pnlEditarInfoBase.setLayout(pnlEditarInfoBaseLayout);
        pnlEditarInfoBaseLayout.setHorizontalGroup(
            pnlEditarInfoBaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlEditarInfoBaseLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlEditarInfoBaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlEditarInfoBaseLayout.createSequentialGroup()
                        .addGap(0, 144, Short.MAX_VALUE)
                        .addComponent(ckbEditarDireccion))
                    .addGroup(pnlEditarInfoBaseLayout.createSequentialGroup()
                        .addGroup(pnlEditarInfoBaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblEditarCedulaProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblEditarNombreProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlEditarInfoBaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtEditarNombreProveedor)
                            .addComponent(txtEditarCedulaProveedor))))
                .addContainerGap())
        );
        pnlEditarInfoBaseLayout.setVerticalGroup(
            pnlEditarInfoBaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEditarInfoBaseLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlEditarInfoBaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEditarCedulaProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEditarCedulaProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlEditarInfoBaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEditarNombreProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEditarNombreProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(ckbEditarDireccion)
                .addContainerGap())
        );

        javax.swing.GroupLayout pnlEditarTelefonoLayout = new javax.swing.GroupLayout(pnlEditarTelefono);
        pnlEditarTelefono.setLayout(pnlEditarTelefonoLayout);
        pnlEditarTelefonoLayout.setHorizontalGroup(
            pnlEditarTelefonoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEditarTelefonoLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(pnlEditarInfoBase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pnlEditarDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlEditarTelefonoLayout.setVerticalGroup(
            pnlEditarTelefonoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEditarTelefonoLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(pnlEditarTelefonoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlEditarDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 203, Short.MAX_VALUE)
                    .addComponent(pnlEditarInfoBase, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        scpnl_EditarProveedor.setViewportView(pnlEditarTelefono);

        tbEditarContactoProveedor.addTab("", new javax.swing.ImageIcon(getClass().getResource("/recursos/proveedor-45.png")), scpnl_EditarProveedor); // NOI18N

        scpnl_EditarContactoProveedor.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        pnlEditarContactoProveedor.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Contacto:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Yu Gothic UI", 0, 18))); // NOI18N
        pnlEditarContactoProveedor.setAutoscrolls(true);

        lblEditarTelefono.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        lblEditarTelefono.setText("Teléfono:");

        lblEditarCorreo.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        lblEditarCorreo.setText("Correo Electrónico:");

        lsTelefonos.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        scpnlEditarListaTelef.setViewportView(lsTelefonos);

        txtEditarTelefono.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        btnEditarGuardarTel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/save-45.png"))); // NOI18N
        btnEditarGuardarTel.setToolTipText("Digite el número de telefono en el campo de texto para guardarlo");
        btnEditarGuardarTel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnEditarGuardarTel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarGuardarTelActionPerformed(evt);
            }
        });

        btnEditarCancelTel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/trash-can-45.png"))); // NOI18N
        btnEditarCancelTel.setToolTipText("Seleccione un número de la lista para eliminarlo");
        btnEditarCancelTel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnEditarCancelTel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarCancelTelActionPerformed(evt);
            }
        });

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(163, 36, 29)));

        lsCorreos.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        lsCorreos.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        lsCorreos.setLayoutOrientation(javax.swing.JList.VERTICAL_WRAP);
        scpnlEditarListaCorreo.setViewportView(lsCorreos);

        txtEditarCorreoProveedor.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        btnEditarGuardarCorreo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/save-45.png"))); // NOI18N
        btnEditarGuardarCorreo.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnEditarGuardarCorreo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarGuardarCorreoActionPerformed(evt);
            }
        });

        btnEditarCancelCorreo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/trash-can-45.png"))); // NOI18N
        btnEditarCancelCorreo.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnEditarCancelCorreo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarCancelCorreoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlEditarContactoProveedorLayout = new javax.swing.GroupLayout(pnlEditarContactoProveedor);
        pnlEditarContactoProveedor.setLayout(pnlEditarContactoProveedorLayout);
        pnlEditarContactoProveedorLayout.setHorizontalGroup(
            pnlEditarContactoProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEditarContactoProveedorLayout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(pnlEditarContactoProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlEditarContactoProveedorLayout.createSequentialGroup()
                        .addGroup(pnlEditarContactoProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlEditarContactoProveedorLayout.createSequentialGroup()
                                .addComponent(lblEditarTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(txtEditarTelefono))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEditarGuardarTel, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(168, 168, 168))
                    .addGroup(pnlEditarContactoProveedorLayout.createSequentialGroup()
                        .addGroup(pnlEditarContactoProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(scpnlEditarListaTelef, javax.swing.GroupLayout.PREFERRED_SIZE, 448, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnEditarCancelTel, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(84, 84, 84)))
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addGroup(pnlEditarContactoProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblEditarCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlEditarContactoProveedorLayout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addGroup(pnlEditarContactoProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(scpnlEditarListaCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 446, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnlEditarContactoProveedorLayout.createSequentialGroup()
                                .addComponent(txtEditarCorreoProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnEditarGuardarCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnEditarCancelCorreo)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlEditarContactoProveedorLayout.setVerticalGroup(
            pnlEditarContactoProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEditarContactoProveedorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlEditarContactoProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlEditarContactoProveedorLayout.createSequentialGroup()
                        .addComponent(lblEditarCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlEditarContactoProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnEditarCancelCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(btnEditarGuardarCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(txtEditarCorreoProveedor, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(scpnlEditarListaCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlEditarContactoProveedorLayout.createSequentialGroup()
                        .addComponent(lblEditarTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(7, 7, 7)
                        .addGroup(pnlEditarContactoProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnEditarCancelTel, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(btnEditarGuardarTel, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(txtEditarTelefono, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(scpnlEditarListaTelef, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jSeparator1)
        );

        javax.swing.GroupLayout pnlEditarCorreoLayout = new javax.swing.GroupLayout(pnlEditarCorreo);
        pnlEditarCorreo.setLayout(pnlEditarCorreoLayout);
        pnlEditarCorreoLayout.setHorizontalGroup(
            pnlEditarCorreoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEditarCorreoLayout.createSequentialGroup()
                .addComponent(pnlEditarContactoProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 54, Short.MAX_VALUE))
        );
        pnlEditarCorreoLayout.setVerticalGroup(
            pnlEditarCorreoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEditarCorreoLayout.createSequentialGroup()
                .addComponent(pnlEditarContactoProveedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(5, 5, 5))
        );

        scpnl_EditarContactoProveedor.setViewportView(pnlEditarCorreo);

        tbEditarContactoProveedor.addTab("", new javax.swing.ImageIcon(getClass().getResource("/recursos/contactos-45.png")), scpnl_EditarContactoProveedor); // NOI18N

        javax.swing.GroupLayout pnl_actualizarLayout = new javax.swing.GroupLayout(pnl_actualizar);
        pnl_actualizar.setLayout(pnl_actualizarLayout);
        pnl_actualizarLayout.setHorizontalGroup(
            pnl_actualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_actualizarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_actualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(spnl_editar_proveedor)
                    .addComponent(tbEditarContactoProveedor)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_actualizarLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnEditarProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        pnl_actualizarLayout.setVerticalGroup(
            pnl_actualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_actualizarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tbEditarContactoProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(spnl_editar_proveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEditarProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        tb_modProveedor.addTab("Editar proveedor", pnl_actualizar);

        lblDeshabSelectProveedor.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        lblDeshabSelectProveedor.setText("Seleccionar Proveedor:");

        tbDeshab.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N

        tblProveedoresActivos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cédula", "T. Cédula", "Nombre", "Contactos", "Dirección"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblProveedoresActivos.getTableHeader().setReorderingAllowed(false);
        tblProveedoresActivos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblProveedoresActivosMouseClicked(evt);
            }
        });
        tblProveedoresActivos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblProveedoresActivosKeyReleased(evt);
            }
        });
        scpnlProveedorDeshab.setViewportView(tblProveedoresActivos);

        tbDeshab.addTab("Activos", scpnlProveedorDeshab);

        tblProveedoresInactivos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cédula ", "T. Cédula", "Nombre", "Contactos", "Dirección"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblProveedoresInactivos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblProveedoresInactivosMouseClicked(evt);
            }
        });
        tblProveedoresInactivos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblProveedoresInactivosKeyReleased(evt);
            }
        });
        scpnlProveedorHabilitar.setViewportView(tblProveedoresInactivos);

        tbDeshab.addTab("Inactivos", scpnlProveedorHabilitar);

        pnlDeshabContainer.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Activo:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Yu Gothic UI", 0, 18))); // NOI18N

        bg_Habilitar.add(rbDeshabDeshabProveedor);
        rbDeshabDeshabProveedor.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        rbDeshabDeshabProveedor.setSelected(true);
        rbDeshabDeshabProveedor.setText("Deshabilitar");

        bg_Habilitar.add(rbDeshabHabilitarProveedor);
        rbDeshabHabilitarProveedor.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        rbDeshabHabilitarProveedor.setText("Habilitar");

        javax.swing.GroupLayout pnlDeshabContainerLayout = new javax.swing.GroupLayout(pnlDeshabContainer);
        pnlDeshabContainer.setLayout(pnlDeshabContainerLayout);
        pnlDeshabContainerLayout.setHorizontalGroup(
            pnlDeshabContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDeshabContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rbDeshabHabilitarProveedor)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 61, Short.MAX_VALUE)
                .addComponent(rbDeshabDeshabProveedor)
                .addContainerGap())
        );
        pnlDeshabContainerLayout.setVerticalGroup(
            pnlDeshabContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDeshabContainerLayout.createSequentialGroup()
                .addGroup(pnlDeshabContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbDeshabHabilitarProveedor)
                    .addComponent(rbDeshabDeshabProveedor))
                .addGap(0, 9, Short.MAX_VALUE))
        );

        btn_deshabilitar.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        btn_deshabilitar.setText("Guardar Cambios");
        btn_deshabilitar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deshabilitarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlHabilitarLayout = new javax.swing.GroupLayout(pnlHabilitar);
        pnlHabilitar.setLayout(pnlHabilitarLayout);
        pnlHabilitarLayout.setHorizontalGroup(
            pnlHabilitarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHabilitarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlHabilitarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tbDeshab, javax.swing.GroupLayout.DEFAULT_SIZE, 1170, Short.MAX_VALUE)
                    .addGroup(pnlHabilitarLayout.createSequentialGroup()
                        .addGroup(pnlHabilitarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblDeshabSelectProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnlHabilitarLayout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(pnlDeshabContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_deshabilitar, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        pnlHabilitarLayout.setVerticalGroup(
            pnlHabilitarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlHabilitarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlHabilitarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlHabilitarLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btn_deshabilitar, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlHabilitarLayout.createSequentialGroup()
                        .addComponent(lblDeshabSelectProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tbDeshab, javax.swing.GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(pnlDeshabContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(17, 17, 17))
        );

        tb_modProveedor.addTab("Habilitar proveedor", pnlHabilitar);

        javax.swing.GroupLayout pnl_modProveedorLayout = new javax.swing.GroupLayout(pnl_modProveedor);
        pnl_modProveedor.setLayout(pnl_modProveedorLayout);
        pnl_modProveedorLayout.setHorizontalGroup(
            pnl_modProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_modProveedorLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(tb_modProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 1199, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnl_modProveedorLayout.setVerticalGroup(
            pnl_modProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_modProveedorLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tb_modProveedor)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnl_modProveedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnl_modProveedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_deshabilitarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deshabilitarActionPerformed
        activarInactivarProveedor();
    }//GEN-LAST:event_btn_deshabilitarActionPerformed

    private void tblProveedoresInactivosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblProveedoresInactivosKeyReleased
        if (evt.getKeyCode() == 38 || evt.getKeyCode() == 40) {
            selecProveedorPorEstado(tblProveedoresInactivos, rbDeshabDeshabProveedor,
                rbDeshabHabilitarProveedor);
        }
    }//GEN-LAST:event_tblProveedoresInactivosKeyReleased

    private void tblProveedoresInactivosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblProveedoresInactivosMouseClicked
        selecProveedorPorEstado(tblProveedoresInactivos, rbDeshabDeshabProveedor,
            rbDeshabHabilitarProveedor);
    }//GEN-LAST:event_tblProveedoresInactivosMouseClicked

    private void tblProveedoresActivosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblProveedoresActivosKeyReleased
        if (evt.getKeyCode() == 38 || evt.getKeyCode() == 40) {
            selecProveedorPorEstado(tblProveedoresInactivos, rbDeshabDeshabProveedor,
                rbDeshabHabilitarProveedor);
        }
    }//GEN-LAST:event_tblProveedoresActivosKeyReleased

    private void tblProveedoresActivosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblProveedoresActivosMouseClicked
        selecProveedorPorEstado(tblProveedoresInactivos, rbDeshabDeshabProveedor,
            rbDeshabHabilitarProveedor);
    }//GEN-LAST:event_tblProveedoresActivosMouseClicked

    private void btnEditarCancelCorreoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarCancelCorreoActionPerformed
        cancelEditContacto(false);
    }//GEN-LAST:event_btnEditarCancelCorreoActionPerformed

    private void btnEditarGuardarCorreoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarGuardarCorreoActionPerformed
        guardarEditContacto(false);
    }//GEN-LAST:event_btnEditarGuardarCorreoActionPerformed

    private void btnEditarCancelTelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarCancelTelActionPerformed
        cancelEditContacto(true);
    }//GEN-LAST:event_btnEditarCancelTelActionPerformed

    private void btnEditarGuardarTelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarGuardarTelActionPerformed
        guardarEditContacto(true);
    }//GEN-LAST:event_btnEditarGuardarTelActionPerformed

    private void ckbEditarDireccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ckbEditarDireccionActionPerformed
        mostrarDireccion(false, ckbEditarDireccion);
    }//GEN-LAST:event_ckbEditarDireccionActionPerformed

    private void cbxEditarCantonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxEditarCantonActionPerformed
        selectDir(cbxEditarDistrito, cbxEditarProvincia, cbxEditarCanton,
            cbxEditarDistrito, "D");
    }//GEN-LAST:event_cbxEditarCantonActionPerformed

    private void cbxEditarProvinciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxEditarProvinciaActionPerformed
        selectDir(cbxEditarCanton, cbxEditarProvincia, cbxEditarCanton,
            cbxEditarDistrito, "C");
    }//GEN-LAST:event_cbxEditarProvinciaActionPerformed

    private void cbxEditarDistritoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxEditarDistritoActionPerformed
        selectDir(cbxEditarBarrio, cbxEditarProvincia, cbxEditarCanton,
            cbxEditarDistrito, "B");
    }//GEN-LAST:event_cbxEditarDistritoActionPerformed

    private void btnEditarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarProveedorActionPerformed
        prepararEditarProveedor();
    }//GEN-LAST:event_btnEditarProveedorActionPerformed

    private void tbl_editarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbl_editarKeyReleased
        if (evt.getKeyCode() == 38 || evt.getKeyCode() == 40) {
            selectProveedorEditar();
        }
    }//GEN-LAST:event_tbl_editarKeyReleased

    private void tbl_editarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_editarMouseClicked
        selectProveedorEditar();
    }//GEN-LAST:event_tbl_editarMouseClicked

    private void ckbAgregarDireccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ckbAgregarDireccionActionPerformed
        mostrarDireccion(true, ckbAgregarDireccion);
        System.out.println("CKB EVENT");
    }//GEN-LAST:event_ckbAgregarDireccionActionPerformed

    private void txt_crear_cedulaProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_crear_cedulaProveedorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_crear_cedulaProveedorActionPerformed

    private void cbxCantonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxCantonActionPerformed
        selectDir(cbxDistrito, cbxProvincia, cbxCanton, cbxDistrito, "D");
    }//GEN-LAST:event_cbxCantonActionPerformed

    private void cbxProvinciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxProvinciaActionPerformed
        selectDir(cbxCanton, cbxProvincia, cbxCanton, cbxDistrito, "C");
    }//GEN-LAST:event_cbxProvinciaActionPerformed

    private void cbxDistritoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxDistritoActionPerformed
        selectDir(cbxBarrio, cbxProvincia, cbxCanton, cbxDistrito, "B");
    }//GEN-LAST:event_cbxDistritoActionPerformed

    private void btnCrearProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearProveedorActionPerformed
        prepararProveedor();
    }//GEN-LAST:event_btnCrearProveedorActionPerformed

    private void btnAgregarCorreoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarCorreoActionPerformed
        guardarAgregarContacto(false);
    }//GEN-LAST:event_btnAgregarCorreoActionPerformed

    private void btnAgregarTelefonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarTelefonoActionPerformed
        guardarAgregarContacto(true);
    }//GEN-LAST:event_btnAgregarTelefonoActionPerformed

    private void txtListadoProveedorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtListadoProveedorKeyReleased
        proveedores = controlador.consultarProveedor(txtListadoProveedor.getText().trim());
        
        cargarProveedorJTable(tbListadoProveedor, true);
    }//GEN-LAST:event_txtListadoProveedorKeyReleased
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bg_Habilitar;
    private javax.swing.JButton btnAgregarCorreo;
    private javax.swing.JButton btnAgregarTelefono;
    private javax.swing.JButton btnCrearProveedor;
    private javax.swing.JButton btnEditarCancelCorreo;
    private javax.swing.JButton btnEditarCancelTel;
    private javax.swing.JButton btnEditarGuardarCorreo;
    private javax.swing.JButton btnEditarGuardarTel;
    private javax.swing.JButton btnEditarProveedor;
    private javax.swing.JButton btn_deshabilitar;
    private javax.swing.JComboBox<DirFiltro> cbxBarrio;
    private javax.swing.JComboBox<DirFiltro> cbxCanton;
    private javax.swing.JComboBox<String> cbxCrearTipoCedula;
    private javax.swing.JComboBox<DirFiltro> cbxDistrito;
    private javax.swing.JComboBox<DirFiltro> cbxEditarBarrio;
    private javax.swing.JComboBox<DirFiltro> cbxEditarCanton;
    private javax.swing.JComboBox<DirFiltro> cbxEditarDistrito;
    private javax.swing.JComboBox<DirFiltro> cbxEditarProvincia;
    private javax.swing.JComboBox<DirFiltro> cbxProvincia;
    private javax.swing.JCheckBox ckbAgregarDireccion;
    private javax.swing.JCheckBox ckbEditarDireccion;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblCrearTelefono;
    private javax.swing.JLabel lblCrearTipoCedula;
    private javax.swing.JLabel lblDeshabSelectProveedor;
    private javax.swing.JLabel lblEditarBarrio;
    private javax.swing.JLabel lblEditarCanton;
    private javax.swing.JLabel lblEditarCedulaProveedor;
    private javax.swing.JLabel lblEditarCorreo;
    private javax.swing.JLabel lblEditarDistrito;
    private javax.swing.JLabel lblEditarNombreProveedor;
    private javax.swing.JLabel lblEditarOtrasSenas;
    private javax.swing.JLabel lblEditarProvincia;
    private javax.swing.JLabel lblEditarTelefono;
    private javax.swing.JLabel lblListadoProveedor;
    private javax.swing.JLabel lbl_crear_barrio;
    private javax.swing.JLabel lbl_crear_canton;
    private javax.swing.JLabel lbl_crear_cedulaProveedor;
    private javax.swing.JLabel lbl_crear_correo;
    private javax.swing.JLabel lbl_crear_distrito;
    private javax.swing.JLabel lbl_crear_nombreProveedor;
    private javax.swing.JLabel lbl_crear_otrasSenas;
    private javax.swing.JLabel lbl_crear_provincia;
    private javax.swing.JList<String> lsCorreos;
    private javax.swing.JList<String> lsCrearCorreos;
    private javax.swing.JList<String> lsCrearTelefonos;
    private javax.swing.JList<String> lsTelefonos;
    private javax.swing.JPanel pnlCrearContactoProveedor;
    private javax.swing.JPanel pnlCrearCorreo;
    private javax.swing.JPanel pnlCrearDireccion;
    private javax.swing.JPanel pnlCrearInfoBase;
    private javax.swing.JPanel pnlCrearTelefono;
    private javax.swing.JPanel pnlDeshabContainer;
    private javax.swing.JPanel pnlEditarContactoProveedor;
    private javax.swing.JPanel pnlEditarCorreo;
    private javax.swing.JPanel pnlEditarDireccion;
    private javax.swing.JPanel pnlEditarInfoBase;
    private javax.swing.JPanel pnlEditarTelefono;
    private javax.swing.JPanel pnlHabilitar;
    private javax.swing.JPanel pnl_actualizar;
    private javax.swing.JPanel pnl_agregar;
    private javax.swing.JPanel pnl_listado;
    private javax.swing.JPanel pnl_modProveedor;
    private javax.swing.JRadioButton rbDeshabDeshabProveedor;
    private javax.swing.JRadioButton rbDeshabHabilitarProveedor;
    private javax.swing.JScrollPane scpnlCrearOtrasSenas;
    private javax.swing.JScrollPane scpnlEditarListaCorreo;
    private javax.swing.JScrollPane scpnlEditarListaTelef;
    private javax.swing.JScrollPane scpnlEditarOtrasSenas;
    private javax.swing.JScrollPane scpnlProveedorDeshab;
    private javax.swing.JScrollPane scpnlProveedorHabilitar;
    private javax.swing.JScrollPane scpnlProveedoresCrearTelefono;
    private javax.swing.JScrollPane scpnlTblListadoProveedor;
    private javax.swing.JScrollPane scpnl_EditarContactoProveedor;
    private javax.swing.JScrollPane scpnl_EditarProveedor;
    private javax.swing.JScrollPane scpnl_crearCorreo;
    private javax.swing.JScrollPane spnlCrearTelefonos;
    private javax.swing.JScrollPane spnl_crear_proveedores;
    private javax.swing.JScrollPane spnl_editar_proveedor;
    private javax.swing.JTabbedPane tbCrearContactoProveedores;
    private javax.swing.JTabbedPane tbDeshab;
    private javax.swing.JTabbedPane tbEditarContactoProveedor;
    private javax.swing.JTable tbListadoProveedor;
    private javax.swing.JTabbedPane tb_modProveedor;
    private javax.swing.JTable tblProveedoresActivos;
    private javax.swing.JTable tblProveedoresInactivos;
    private javax.swing.JTable tbl_crear;
    private javax.swing.JTable tbl_editar;
    private javax.swing.JTextArea txaEditarOtrasSenas;
    private javax.swing.JTextArea txaOtrasSenas;
    private javax.swing.JTextField txtEditarCedulaProveedor;
    private javax.swing.JTextField txtEditarCorreoProveedor;
    private javax.swing.JTextField txtEditarNombreProveedor;
    private javax.swing.JTextField txtEditarTelefono;
    private javax.swing.JTextField txtListadoProveedor;
    private javax.swing.JTextField txt_agregarCorreo;
    private javax.swing.JTextField txt_agregarTelefono;
    private javax.swing.JTextField txt_crear_cedulaProveedor;
    private javax.swing.JTextField txt_crear_nombreProveedor;
    // End of variables declaration//GEN-END:variables
}
