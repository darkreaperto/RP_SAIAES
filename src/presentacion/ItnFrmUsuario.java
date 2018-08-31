/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacion;

import util.Estado;
import bd.Conexion;
import bd.AESEncrypt;
import controladores.CtrUsuario;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import logica.Usuario;
import util.MessageHelper;
import util.MessageType;
import util.Rol;

/**
 *
 * @author ahoihanabi
 */
public class ItnFrmUsuario extends javax.swing.JInternalFrame {
    
    private static ItnFrmUsuario instancia = null;
    private static Conexion conexion;
    private static AESEncrypt crypter;
    private static Mensaje mensaje;    
    private static CtrUsuario controlador;    
    //private static ArrayList<Usuario> usuarios;
    private static ArrayList<Usuario> lista;
    private DefaultTableModel model; 
    /**
     * Creates new form intfrmUsuario
     */
    protected ItnFrmUsuario() {
        initComponents();        
        //Inicializar variables
        conexion = Conexion.getInstancia();
        controlador = CtrUsuario.getInstancia();
        crypter = new AESEncrypt();
        crypter.addKey("SAI");
        mensaje = new Mensaje();
        lista = controlador.obtenerUsuarios();
        updateTables();
        
    }
    
    public static ItnFrmUsuario getInstancia() {
        if (instancia == null) {
            instancia = new ItnFrmUsuario();
        }
        return instancia;
    }
    
//    public ArrayList<Usuario> obtenerUsuarios() {
//        try {
//            //Para no instanciar los usuarios sin necesidad
//            usuarios = usuarios == null ? new ArrayList<>() : usuarios;
//            
//            String consulta = "SELECT cod_Usuarios, nombre_Usuarios, "
//                            + "clave_Usuarios, correo_Usuarios, cod_RolUsuar, estado_Usuarios"
//                            + " FROM Usuarios";            
//            conexion.abrirConexion();
//            ResultSet result = conexion.ejecutarConsulta(consulta);
//
//            String codUsuario = "", nombreUsuario = "", claveUsuario = "", 
//                   correoUsuario = "", codRolUsuario = "", estadoUsuario = "";
//
//            while (result.next()) {
//                codUsuario = result.getString("cod_Usuarios");
//                nombreUsuario = result.getString("nombre_Usuarios");
//                claveUsuario = result.getString("clave_Usuarios");
//                correoUsuario = result.getString("correo_Usuarios");
//                codRolUsuario = result.getString("cod_RolUsuar"); 
//                estadoUsuario = result.getString("estado_Usuarios");
////                System.out.println("**Codigo: " + codUsuario + 
////                                    "\nNombre: " + nombreUsuario + 
////                                    "\nClave: " + claveUsuario + 
////                                    "\nRol: " + codRolUsuario);
//                Usuario usuario = new Usuario(codUsuario, nombreUsuario, claveUsuario, 
//                                        correoUsuario, codRolUsuario, estadoUsuario);
//                if (!usuarios.contains(usuario))
//                    usuarios.add(usuario);                
//            }
//
//        } catch (SQLException ex) {
//            System.err.println(ex);
//        }
//        finally {
//            conexion.cerrarConexion();
//        }
//        return usuarios;
//    }
    public void getActualUser() {
        //txt_actuali_nombreUsuario.setText(lista.get(i).getNombre());  FALTA TENER USUARIO EN SESIÓN
    }
//    public boolean crearUsuario(String nombre, String contra, String correo, Rol rol) {
//        
//        //Código de rol de usuario. 1: Administrador, 2: Estándar
//        int codRol = rol.equals(Rol.Administrador) ? 1 : 2;
//        contra = crypter.encriptar(contra);
//        
//        boolean res = false;
//        try {
//            String consulta = "INSERT INTO `Usuarios`(`cod_Usuarios`, "
//                    + "`nombre_Usuarios`, `clave_Usuarios`, `correo_Usuarios`,"
//                    + " `cod_RolUsuar`, `estado_Usuarios`) "
//                    + "VALUES (NULL, '" + nombre + "', '" + contra + "', '" 
//                                + correo + "', " + codRol + ", 'A' )";
//            
//            conexion.abrirConexion();
//            res = conexion.ejecutarActualizar(consulta) != -1;
//
//        } catch (SQLException ex) {
//            System.err.println(ex);
//        }
//        finally {
//            conexion.cerrarConexion();
//        }
//        return res;
//    }
    
//    public boolean updateUsuario( String nombre, String contra, String correo, Rol rol, Estado estado, int codigo) {
//        //Código de rol de usuario. 1: Administrador, 2: Estándar
//        int codRol = rol.equals(Rol.Administrador) ? 1 : 2;
//        //contra = crypter.encriptar(contra);
//        String state = estado.equals(Estado.Activo) ? "A" : "I";
//        boolean res = false;
//        try {
//            String consulta =  "UPDATE Usuarios"+ 
//                               " SET nombre_Usuarios = '"+nombre+"', "+ 
//                               " clave_Usuarios = '"+contra+"' , correo_Usuarios = '"+correo+"', "+
//                               " cod_RolUsuar = "+codRol+", estado_Usuarios = '"+state+"' "+
//                               " WHERE cod_Usuarios = "+codigo+";";
//            conexion.abrirConexion();
//            res = conexion.ejecutarActualizar(consulta) != -1;
//
//        } catch (SQLException ex) {
//            System.err.println(ex);
//        }
//        finally {
//            conexion.cerrarConexion();
//        }
//        return res;
//    }
    
    public void mostrarUsuariosJTable(JTable tabla, boolean estado) {
        Object[] row = new Object[5];        
        model = (DefaultTableModel)tabla.getModel();         
        model.setRowCount(0);
        for(int i = 0; i < lista.size(); i++) {
                                                   
            if(lista.get(i).getEstado().equals("A") && estado) {
                row[0] = lista.get(i).getCodigo();
                row[1] = lista.get(i).getNombre();
                row[2] = lista.get(i).getContrasenna();
                row[3] = lista.get(i).getCorreo();
                row[4] = lista.get(i).getRol();
                //row[5] = lista.get(i).getEstado();
                model.addRow(row);
            }
            if(lista.get(i).getEstado().equals("I") && !estado) {
                row[0] = lista.get(i).getCodigo();
                row[1] = lista.get(i).getNombre();
                row[2] = lista.get(i).getContrasenna();
                row[3] = lista.get(i).getCorreo();
                row[4] = lista.get(i).getRol();
                //row[5] = lista.get(i).getEstado();
                model.addRow(row);
            }
        }
    }
    
    public void updateTables() {
        lista.clear();
        lista = controlador.obtenerUsuarios();
        mostrarUsuariosJTable(tbl_usuarioListado, true);
        mostrarUsuariosJTable(tbl_usuarioCreado, true);
        mostrarUsuariosJTable(tbl_deshabilitar, true);
        mostrarUsuariosJTable(tbl_habilitar, false);
        mostrarUsuariosJTable(tbl_actPermisos, true);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bg_crear_rol = new javax.swing.ButtonGroup();
        pnl_modUsuario = new javax.swing.JPanel();
        tb_modUsuario_permisos = new javax.swing.JTabbedPane();
        pnl_listado = new javax.swing.JPanel();
        lbl_listado_buscarUsuario = new javax.swing.JLabel();
        txt_listado_buscar = new javax.swing.JTextField();
        scpnl_tbl_usuarioListado = new javax.swing.JScrollPane();
        tbl_usuarioListado = new javax.swing.JTable();
        pnl_crear = new javax.swing.JPanel();
        lbl_crear_nombreUsuario = new javax.swing.JLabel();
        txt_crear_nombreUsuario = new javax.swing.JTextField();
        lbl_crear_correo = new javax.swing.JLabel();
        txt_crear_correo = new javax.swing.JTextField();
        lbl_crear_password = new javax.swing.JLabel();
        pw_crear_contra = new javax.swing.JPasswordField();
        lbl_crear_confirmPassw = new javax.swing.JLabel();
        pw_crear_confContra = new javax.swing.JPasswordField();
        pnl_crear_rolContainer = new javax.swing.JPanel();
        rb_crear_rolEstandar = new javax.swing.JRadioButton();
        rb_crear_rolAdmin = new javax.swing.JRadioButton();
        btn_crearUsuario = new javax.swing.JButton();
        scpnl_tbl_usuarioCreado = new javax.swing.JScrollPane();
        tbl_usuarioCreado = new javax.swing.JTable();
        pnl_deshabilitar = new javax.swing.JPanel();
        lbl_deshab_selectUsuario = new javax.swing.JLabel();
        pnl_deshab_deshabContainer = new javax.swing.JPanel();
        rb_deshab_deshabilitar = new javax.swing.JRadioButton();
        rb_deshab_habilitar = new javax.swing.JRadioButton();
        btn_deshabilitar = new javax.swing.JButton();
        tb_deshab = new javax.swing.JTabbedPane();
        scpnl_tbl_usuarioDeshab = new javax.swing.JScrollPane();
        tbl_deshabilitar = new javax.swing.JTable();
        scpnl_tbl_usuarioHabilitar = new javax.swing.JScrollPane();
        tbl_habilitar = new javax.swing.JTable();
        pnl_actualizarPermisos = new javax.swing.JPanel();
        lbl_actPermi_selectUsuario = new javax.swing.JLabel();
        pnl_actPermi_rolContainer = new javax.swing.JPanel();
        rb_actPermi_estandar = new javax.swing.JRadioButton();
        rb_actPermi_Admin = new javax.swing.JRadioButton();
        btn_actPermi = new javax.swing.JButton();
        scpnl_tbl_usuarioActPermiso = new javax.swing.JScrollPane();
        tbl_actPermisos = new javax.swing.JTable();
        pnl_actualizar = new javax.swing.JPanel();
        lbl_actuali_nombreUsuario = new javax.swing.JLabel();
        txt_actuali_nombreUsuario = new javax.swing.JTextField();
        lbl_actuali_passActual = new javax.swing.JLabel();
        btn_actualiUsuario = new javax.swing.JButton();
        lbl_actuali_passNew = new javax.swing.JLabel();
        pw_actuali_newPass = new javax.swing.JPasswordField();
        pw_actuali_confNewPass = new javax.swing.JPasswordField();
        lbl_actuali_nombreUsuario1 = new javax.swing.JLabel();
        txt_actuali_correo = new javax.swing.JTextField();
        pw_actuali_lastpass = new javax.swing.JPasswordField();
        lbl_actuali_nombreUsuario2 = new javax.swing.JLabel();

        pnl_modUsuario.setPreferredSize(new java.awt.Dimension(1239, 680));

        lbl_listado_buscarUsuario.setText("Buscar usuario: ");

        tbl_usuarioListado.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo", "Nombre de Usuario", "Contraseña", "Correo", "Rol"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_usuarioListado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_usuarioListadoMouseClicked(evt);
            }
        });
        tbl_usuarioListado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbl_usuarioListadoKeyReleased(evt);
            }
        });
        scpnl_tbl_usuarioListado.setViewportView(tbl_usuarioListado);

        javax.swing.GroupLayout pnl_listadoLayout = new javax.swing.GroupLayout(pnl_listado);
        pnl_listado.setLayout(pnl_listadoLayout);
        pnl_listadoLayout.setHorizontalGroup(
            pnl_listadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_listadoLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(pnl_listadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scpnl_tbl_usuarioListado, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 1073, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnl_listadoLayout.createSequentialGroup()
                        .addComponent(lbl_listado_buscarUsuario)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_listado_buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 946, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        pnl_listadoLayout.setVerticalGroup(
            pnl_listadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_listadoLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(pnl_listadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_listado_buscarUsuario)
                    .addComponent(txt_listado_buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(scpnl_tbl_usuarioListado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(66, Short.MAX_VALUE))
        );

        tb_modUsuario_permisos.addTab("Listado Usuarios", pnl_listado);

        lbl_crear_nombreUsuario.setText("Nombre de Usuario:");

        txt_crear_nombreUsuario.setNextFocusableComponent(txt_crear_correo);

        lbl_crear_correo.setText("Correo Electrónico:");

        txt_crear_correo.setNextFocusableComponent(pw_crear_contra);

        lbl_crear_password.setText("Contraseña:");

        pw_crear_contra.setNextFocusableComponent(pw_crear_confContra);

        lbl_crear_confirmPassw.setText("Confirmar Contraseña:");

        pw_crear_confContra.setNextFocusableComponent(rb_crear_rolAdmin);

        pnl_crear_rolContainer.setBorder(javax.swing.BorderFactory.createTitledBorder("Rol de Usuarios:"));

        bg_crear_rol.add(rb_crear_rolEstandar);
        rb_crear_rolEstandar.setText("Estándar");
        rb_crear_rolEstandar.setNextFocusableComponent(btn_crearUsuario);

        bg_crear_rol.add(rb_crear_rolAdmin);
        rb_crear_rolAdmin.setText("Administrador");
        rb_crear_rolAdmin.setNextFocusableComponent(rb_crear_rolEstandar);

        javax.swing.GroupLayout pnl_crear_rolContainerLayout = new javax.swing.GroupLayout(pnl_crear_rolContainer);
        pnl_crear_rolContainer.setLayout(pnl_crear_rolContainerLayout);
        pnl_crear_rolContainerLayout.setHorizontalGroup(
            pnl_crear_rolContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_crear_rolContainerLayout.createSequentialGroup()
                .addComponent(rb_crear_rolAdmin)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 57, Short.MAX_VALUE)
                .addComponent(rb_crear_rolEstandar)
                .addContainerGap())
        );
        pnl_crear_rolContainerLayout.setVerticalGroup(
            pnl_crear_rolContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_crear_rolContainerLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnl_crear_rolContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rb_crear_rolAdmin)
                    .addComponent(rb_crear_rolEstandar)))
        );

        btn_crearUsuario.setText("Crear Usuario");
        btn_crearUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_crearUsuarioActionPerformed(evt);
            }
        });

        tbl_usuarioCreado.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo", "Nombre de Usuario", "Contraseña", "Correo", "Rol"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_usuarioCreado.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tbl_usuarioCreado.setUpdateSelectionOnSort(false);
        scpnl_tbl_usuarioCreado.setViewportView(tbl_usuarioCreado);

        javax.swing.GroupLayout pnl_crearLayout = new javax.swing.GroupLayout(pnl_crear);
        pnl_crear.setLayout(pnl_crearLayout);
        pnl_crearLayout.setHorizontalGroup(
            pnl_crearLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_crearLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(pnl_crearLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(scpnl_tbl_usuarioCreado)
                    .addGroup(pnl_crearLayout.createSequentialGroup()
                        .addGroup(pnl_crearLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lbl_crear_nombreUsuario)
                            .addComponent(lbl_crear_correo)
                            .addComponent(txt_crear_correo, javax.swing.GroupLayout.DEFAULT_SIZE, 294, Short.MAX_VALUE)
                            .addComponent(txt_crear_nombreUsuario))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 80, Short.MAX_VALUE)
                        .addGroup(pnl_crearLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(pw_crear_contra)
                            .addComponent(lbl_crear_password)
                            .addComponent(lbl_crear_confirmPassw)
                            .addComponent(pw_crear_confContra, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(72, 72, 72)
                        .addGroup(pnl_crearLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(pnl_crear_rolContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn_crearUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(72, 72, 72))
        );
        pnl_crearLayout.setVerticalGroup(
            pnl_crearLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_crearLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(pnl_crearLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnl_crearLayout.createSequentialGroup()
                        .addGroup(pnl_crearLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnl_crearLayout.createSequentialGroup()
                                .addComponent(lbl_crear_nombreUsuario)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_crear_nombreUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(pnl_crear_rolContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(lbl_crear_correo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnl_crearLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_crear_correo, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_crearUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnl_crearLayout.createSequentialGroup()
                        .addComponent(lbl_crear_password)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pw_crear_contra, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24)
                        .addComponent(lbl_crear_confirmPassw)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pw_crear_confContra, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addComponent(scpnl_tbl_usuarioCreado, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(46, Short.MAX_VALUE))
        );

        tb_modUsuario_permisos.addTab("Crear", pnl_crear);

        lbl_deshab_selectUsuario.setText("Seleccionar Usuario:");

        pnl_deshab_deshabContainer.setBorder(javax.swing.BorderFactory.createTitledBorder("Activo:"));

        bg_crear_rol.add(rb_deshab_deshabilitar);
        rb_deshab_deshabilitar.setSelected(true);
        rb_deshab_deshabilitar.setText("Deshabilitar");
        rb_deshab_deshabilitar.setNextFocusableComponent(btn_deshabilitar);

        bg_crear_rol.add(rb_deshab_habilitar);
        rb_deshab_habilitar.setText("Habilitar");
        rb_deshab_habilitar.setNextFocusableComponent(rb_deshab_deshabilitar);

        javax.swing.GroupLayout pnl_deshab_deshabContainerLayout = new javax.swing.GroupLayout(pnl_deshab_deshabContainer);
        pnl_deshab_deshabContainer.setLayout(pnl_deshab_deshabContainerLayout);
        pnl_deshab_deshabContainerLayout.setHorizontalGroup(
            pnl_deshab_deshabContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_deshab_deshabContainerLayout.createSequentialGroup()
                .addComponent(rb_deshab_habilitar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                .addComponent(rb_deshab_deshabilitar)
                .addContainerGap())
        );
        pnl_deshab_deshabContainerLayout.setVerticalGroup(
            pnl_deshab_deshabContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_deshab_deshabContainerLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnl_deshab_deshabContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rb_deshab_habilitar)
                    .addComponent(rb_deshab_deshabilitar)))
        );

        btn_deshabilitar.setText("Guardar Cambios");
        btn_deshabilitar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deshabilitarActionPerformed(evt);
            }
        });

        tbl_deshabilitar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo", "Nombre de Usuario", "Contraseña", "Correo", "Rol"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_deshabilitar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_deshabilitarMouseClicked(evt);
            }
        });
        tbl_deshabilitar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbl_deshabilitarKeyReleased(evt);
            }
        });
        scpnl_tbl_usuarioDeshab.setViewportView(tbl_deshabilitar);

        tb_deshab.addTab("Deshabilitar", scpnl_tbl_usuarioDeshab);

        tbl_habilitar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo", "Nombre de Usuario", "Contraseña", "Correo", "Rol"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_habilitar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_habilitarMouseClicked(evt);
            }
        });
        tbl_habilitar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbl_habilitarKeyReleased(evt);
            }
        });
        scpnl_tbl_usuarioHabilitar.setViewportView(tbl_habilitar);

        tb_deshab.addTab("Habilitar", scpnl_tbl_usuarioHabilitar);

        javax.swing.GroupLayout pnl_deshabilitarLayout = new javax.swing.GroupLayout(pnl_deshabilitar);
        pnl_deshabilitar.setLayout(pnl_deshabilitarLayout);
        pnl_deshabilitarLayout.setHorizontalGroup(
            pnl_deshabilitarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_deshabilitarLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(pnl_deshabilitarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbl_deshab_selectUsuario)
                    .addGroup(pnl_deshabilitarLayout.createSequentialGroup()
                        .addComponent(pnl_deshab_deshabContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(576, 576, 576)
                        .addComponent(btn_deshabilitar, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(tb_deshab))
                .addContainerGap(71, Short.MAX_VALUE))
        );
        pnl_deshabilitarLayout.setVerticalGroup(
            pnl_deshabilitarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_deshabilitarLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(lbl_deshab_selectUsuario)
                .addGap(18, 18, 18)
                .addComponent(tb_deshab, javax.swing.GroupLayout.PREFERRED_SIZE, 386, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addGroup(pnl_deshabilitarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pnl_deshab_deshabContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_deshabilitar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        tb_modUsuario_permisos.addTab("Eliminar", pnl_deshabilitar);

        lbl_actPermi_selectUsuario.setText("Seleccionar Usuario:");

        pnl_actPermi_rolContainer.setBorder(javax.swing.BorderFactory.createTitledBorder("Rol Usuario:"));

        bg_crear_rol.add(rb_actPermi_estandar);
        rb_actPermi_estandar.setText("Estándar");
        rb_actPermi_estandar.setNextFocusableComponent(btn_actPermi);

        bg_crear_rol.add(rb_actPermi_Admin);
        rb_actPermi_Admin.setText("Administrador");
        rb_actPermi_Admin.setNextFocusableComponent(rb_actPermi_estandar);

        javax.swing.GroupLayout pnl_actPermi_rolContainerLayout = new javax.swing.GroupLayout(pnl_actPermi_rolContainer);
        pnl_actPermi_rolContainer.setLayout(pnl_actPermi_rolContainerLayout);
        pnl_actPermi_rolContainerLayout.setHorizontalGroup(
            pnl_actPermi_rolContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_actPermi_rolContainerLayout.createSequentialGroup()
                .addComponent(rb_actPermi_Admin)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                .addComponent(rb_actPermi_estandar)
                .addContainerGap())
        );
        pnl_actPermi_rolContainerLayout.setVerticalGroup(
            pnl_actPermi_rolContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_actPermi_rolContainerLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnl_actPermi_rolContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rb_actPermi_Admin)
                    .addComponent(rb_actPermi_estandar)))
        );

        btn_actPermi.setText("Guardar Cambios");
        btn_actPermi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_actPermiActionPerformed(evt);
            }
        });

        tbl_actPermisos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo", "Nombre de Usuario", "Contraseña", "Correo", "Rol"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_actPermisos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_actPermisosMouseClicked(evt);
            }
        });
        tbl_actPermisos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbl_actPermisosKeyReleased(evt);
            }
        });
        scpnl_tbl_usuarioActPermiso.setViewportView(tbl_actPermisos);

        javax.swing.GroupLayout pnl_actualizarPermisosLayout = new javax.swing.GroupLayout(pnl_actualizarPermisos);
        pnl_actualizarPermisos.setLayout(pnl_actualizarPermisosLayout);
        pnl_actualizarPermisosLayout.setHorizontalGroup(
            pnl_actualizarPermisosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_actualizarPermisosLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(pnl_actualizarPermisosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_actPermi_selectUsuario)
                    .addGroup(pnl_actualizarPermisosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnl_actualizarPermisosLayout.createSequentialGroup()
                            .addComponent(pnl_actPermi_rolContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn_actPermi, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(scpnl_tbl_usuarioActPermiso, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 1030, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(71, Short.MAX_VALUE))
        );
        pnl_actualizarPermisosLayout.setVerticalGroup(
            pnl_actualizarPermisosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_actualizarPermisosLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(lbl_actPermi_selectUsuario)
                .addGap(35, 35, 35)
                .addComponent(scpnl_tbl_usuarioActPermiso, javax.swing.GroupLayout.PREFERRED_SIZE, 364, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addGroup(pnl_actualizarPermisosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pnl_actPermi_rolContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_actPermi, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        tb_modUsuario_permisos.addTab("Actualizar permisos", pnl_actualizarPermisos);

        lbl_actuali_nombreUsuario.setText("Nombre de Usuario:");

        txt_actuali_nombreUsuario.setNextFocusableComponent(txt_actuali_correo);

        lbl_actuali_passActual.setText("Nueva Contraseña:");

        btn_actualiUsuario.setText("Actualizar Usuario");
        btn_actualiUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_actualiUsuarioActionPerformed(evt);
            }
        });

        lbl_actuali_passNew.setText("Confirmar Contraseña:");

        lbl_actuali_nombreUsuario1.setText("Correo Electrónico:");

        txt_actuali_correo.setNextFocusableComponent(btn_actualiUsuario);

        lbl_actuali_nombreUsuario2.setText("Correo Electrónico:");

        javax.swing.GroupLayout pnl_actualizarLayout = new javax.swing.GroupLayout(pnl_actualizar);
        pnl_actualizar.setLayout(pnl_actualizarLayout);
        pnl_actualizarLayout.setHorizontalGroup(
            pnl_actualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_actualizarLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(pnl_actualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_actuali_nombreUsuario2)
                    .addGroup(pnl_actualizarLayout.createSequentialGroup()
                        .addGroup(pnl_actualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(pw_actuali_lastpass, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_actuali_nombreUsuario, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_actuali_nombreUsuario1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_actuali_nombreUsuario, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 294, Short.MAX_VALUE)
                            .addComponent(txt_actuali_correo, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(129, 129, 129)
                        .addGroup(pnl_actualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lbl_actuali_passActual)
                            .addComponent(lbl_actuali_passNew)
                            .addComponent(pw_actuali_confNewPass)
                            .addComponent(pw_actuali_newPass, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(384, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_actualizarLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_actualiUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(71, 71, 71))
        );
        pnl_actualizarLayout.setVerticalGroup(
            pnl_actualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_actualizarLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(pnl_actualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_actuali_nombreUsuario)
                    .addComponent(lbl_actuali_passActual))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl_actualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_actuali_nombreUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pw_actuali_newPass, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnl_actualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_actuali_nombreUsuario1)
                    .addComponent(lbl_actuali_passNew))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl_actualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_actuali_correo, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pw_actuali_confNewPass, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(lbl_actuali_nombreUsuario2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pw_actuali_lastpass, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 286, Short.MAX_VALUE)
                .addComponent(btn_actualiUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
        );

        tb_modUsuario_permisos.addTab("Actualizar", pnl_actualizar);

        javax.swing.GroupLayout pnl_modUsuarioLayout = new javax.swing.GroupLayout(pnl_modUsuario);
        pnl_modUsuario.setLayout(pnl_modUsuarioLayout);
        pnl_modUsuarioLayout.setHorizontalGroup(
            pnl_modUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_modUsuarioLayout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(tb_modUsuario_permisos)
                .addContainerGap())
        );
        pnl_modUsuarioLayout.setVerticalGroup(
            pnl_modUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_modUsuarioLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(tb_modUsuario_permisos, javax.swing.GroupLayout.PREFERRED_SIZE, 590, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnl_modUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 1194, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(36, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnl_modUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 642, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 19, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_crearUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_crearUsuarioActionPerformed
        Mensaje msg = new Mensaje();
        
        String nombre =  txt_crear_nombreUsuario.getText();
        String contra =  new String(pw_crear_contra.getPassword());
        String contraConf =  new String(pw_crear_confContra.getPassword());
        String correo =  txt_crear_correo.getText();
        //Si el radio button rol Estándar está seleccionado
        Rol rol = rb_crear_rolEstandar.isSelected() ? Rol.Estándar : Rol.Administrador;
        
        if (!nombre.isEmpty()) {
            if(!correo.isEmpty()) {
                if (!contra.isEmpty()) {
                    if (contra.equals(contraConf)) {                    
                        if (controlador.crearUsuario(nombre, contra, correo, rol)) {
                            updateTables();
                            msg.mostrarMensaje(MessageType.INFORMATION, MessageHelper.USER_INSERTION_SUCCESS);
                        } else {
                            msg.mostrarMensaje(MessageType.ERROR, MessageHelper.USER_INSERTION_FAILURE);
                        }
                    } else {
                        msg.mostrarMensaje(MessageType.WARNING, MessageHelper.MISMATCHING_PASSWORD_FIELDS);
                    }
                } else {
                    msg.mostrarMensaje(MessageType.WARNING, MessageHelper.EMPTY_PASSWORD_FIELD);
                }
            } else {
                msg.mostrarMensaje(MessageType.WARNING, MessageHelper.EMPTY_EMAIL_FIELD);
            }            
        } else {
            msg.mostrarMensaje(MessageType.WARNING, MessageHelper.EMPTY_USERNAME_FIELD);
        }
    }//GEN-LAST:event_btn_crearUsuarioActionPerformed
        
    private void btn_actualiUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_actualiUsuarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_actualiUsuarioActionPerformed

    private void tbl_usuarioListadoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_usuarioListadoMouseClicked
        model = (DefaultTableModel)tbl_usuarioListado.getModel();
        int selectedRowIndex = tbl_usuarioListado.getSelectedRow();
        String codigo = String.valueOf(model.getValueAt(selectedRowIndex, 0).toString());
        
        for(int i = 0; i<lista.size(); i++) {
            if(lista.get(i).getCodigo().equals(codigo)) { //Si el codigo coincide
                txt_listado_buscar.setText(lista.get(i).getNombre());                                    
            }
        }
    }//GEN-LAST:event_tbl_usuarioListadoMouseClicked

    private void tbl_deshabilitarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_deshabilitarMouseClicked
        try {             
            model = (DefaultTableModel)tbl_deshabilitar.getModel();
            int selectedRowIndex = tbl_deshabilitar.getSelectedRow();
            String codigo = String.valueOf(model.getValueAt(selectedRowIndex, 0).toString());
            
            for(int i = 0; i<lista.size(); i++) {
                if(lista.get(i).getCodigo().equals(codigo)) { //Si el codigo coincide                    
                    if(lista.get(i).getEstado().equals("A")) { //Verifica el tipo de estado
                        rb_deshab_deshabilitar.setSelected(true);
                    } else {
                        rb_deshab_deshabilitar.setSelected(true);
                    }                        
                }
            }            
        } catch (Exception ex) {
            
        }        
    }//GEN-LAST:event_tbl_deshabilitarMouseClicked
    //SELECCIONAR Y MOSTRAR INFO EN PANTALLA.
    private void tbl_actPermisosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_actPermisosMouseClicked

        try { 
            model = (DefaultTableModel)tbl_actPermisos.getModel();
            int selectedRowIndex = tbl_actPermisos.getSelectedRow();
            String codigo = String.valueOf(model.getValueAt(selectedRowIndex, 0).toString());
            
            for(int i = 0; i<lista.size(); i++) {
                if(lista.get(i).getCodigo().equals(codigo)) { //Si el codigo coincide
                    if(lista.get(i).getRol().equals("1")) { //Verifica el tipo de permiso
                        rb_actPermi_Admin.setSelected(true);
                    } else {
                        rb_actPermi_estandar.setSelected(true);
                    }                        
                }
            }
        } catch (Exception ex) {
            
        }        
    }//GEN-LAST:event_tbl_actPermisosMouseClicked

    private void btn_actPermiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_actPermiActionPerformed
        try {
            //--------------------------------------------------------------------COMO MANTENER INFO DE ROL SI NO SE PUEDE PASAR A STRING
            model = (DefaultTableModel)tbl_actPermisos.getModel();
            int selectedRowIndex = tbl_actPermisos.getSelectedRow();
            int codigo = Integer.parseInt(String.valueOf(model.getValueAt(selectedRowIndex, 0)));
            Rol rol = rb_actPermi_Admin.isSelected() ? Rol.Administrador : Rol.Estándar;
            
            controlador.updateUsuario(String.valueOf(model.getValueAt(selectedRowIndex, 1)),
                    String.valueOf(model.getValueAt(selectedRowIndex, 2)), 
                    String.valueOf(model.getValueAt(selectedRowIndex, 3)), 
                    rol, Estado.Activo, codigo);
            updateTables();
        } catch (Exception e) {
            mensaje.mostrarMensaje(MessageType.INFORMATION, MessageHelper.ANY_ROW_SELECTED);
        }
    }//GEN-LAST:event_btn_actPermiActionPerformed

    private void tbl_usuarioListadoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbl_usuarioListadoKeyReleased
        try {
            if(evt.getKeyCode() == 38 || evt.getKeyCode() == 40) {
                model = (DefaultTableModel)tbl_usuarioListado.getModel();
                int selectedRowIndex = tbl_usuarioListado.getSelectedRow();
                String codigo = String.valueOf(model.getValueAt(selectedRowIndex, 0).toString());
                
                for(int i = 0; i<lista.size(); i++) {
                    if(lista.get(i).getCodigo().equals(codigo)) { //Si el codigo coincide
                        txt_listado_buscar.setText(lista.get(i).getNombre());                                    
                    }
                }              
            }
        } catch (Exception ex) {
            
        }        
    }//GEN-LAST:event_tbl_usuarioListadoKeyReleased

    private void btn_deshabilitarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deshabilitarActionPerformed
        try {
            //--------------------------------------------------------------------COMO MANTENER INFO DE ROL SI NO SE PUEDE PASAR A STRING
            model = (DefaultTableModel)tbl_deshabilitar.getModel();
            int selectedRowIndex = tbl_deshabilitar.getSelectedRow();
            int codigo = Integer.parseInt(String.valueOf(model.getValueAt(selectedRowIndex, 0)));
            Estado estado = rb_deshab_habilitar.isSelected() ? Estado.Activo : Estado.Deshabilitado;
            
            controlador.updateUsuario(String.valueOf(model.getValueAt(selectedRowIndex, 1)),
                    String.valueOf(model.getValueAt(selectedRowIndex, 2)), 
                    String.valueOf(model.getValueAt(selectedRowIndex, 3)), 
                    Rol.Administrador, estado, codigo);
            updateTables();
        } catch (Exception e) {
            mensaje.mostrarMensaje(MessageType.INFORMATION, MessageHelper.ANY_ROW_SELECTED);
        }        
    }//GEN-LAST:event_btn_deshabilitarActionPerformed

    private void tbl_actPermisosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbl_actPermisosKeyReleased
        try { 
            if(evt.getKeyCode() == 38 || evt.getKeyCode() == 40) {
                model = (DefaultTableModel)tbl_actPermisos.getModel();
                int selectedRowIndex = tbl_actPermisos.getSelectedRow();
                String codigo = String.valueOf(model.getValueAt(selectedRowIndex, 0).toString());
                
                for(int i = 0; i<lista.size(); i++) {
                    if(lista.get(i).getCodigo().equals(codigo)) { //Si el codigo coincide
                        if(lista.get(i).getRol().equals("1")) { //Verifica el tipo de permiso
                            System.out.println(lista.get(i).getNombre());
                            rb_actPermi_Admin.setSelected(true);
                        } else {
                            rb_actPermi_estandar.setSelected(true);
                        }                        
                    }
                }
            }            
        } catch (Exception ex) {
            
        }  
    }//GEN-LAST:event_tbl_actPermisosKeyReleased

    private void tbl_deshabilitarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbl_deshabilitarKeyReleased
        try { 
            if(evt.getKeyCode() == 38 || evt.getKeyCode() == 40) {
                model = (DefaultTableModel)tbl_deshabilitar.getModel();
                int selectedRowIndex = tbl_deshabilitar.getSelectedRow();
                String codigo = String.valueOf(model.getValueAt(selectedRowIndex, 0).toString());
                
                for(int i = 0; i<lista.size(); i++) {
                    if(lista.get(i).getCodigo().equals(codigo)) { //Si el codigo coincide                                                
                        if(lista.get(i).getEstado().equals("A")) { //Verifica el tipo de estado
                            rb_deshab_deshabilitar.setSelected(true);                            
                        } else {
                            rb_deshab_deshabilitar.setSelected(true);
                        }                        
                    }
                }
            }            
        } catch (Exception ex) {
            
        } 
    }//GEN-LAST:event_tbl_deshabilitarKeyReleased

    private void tbl_habilitarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_habilitarMouseClicked
        try {             
            model = (DefaultTableModel)tbl_habilitar.getModel();
            int selectedRowIndex = tbl_habilitar.getSelectedRow();
            String codigo = String.valueOf(model.getValueAt(selectedRowIndex, 0).toString());
                
            for(int i = 0; i<lista.size(); i++) {
                if(lista.get(i).getCodigo().equals(codigo)) { //Si el codigo coincide
                    if(lista.get(i).getEstado().equals("A")) { //Verifica el tipo de estado
                        
                        rb_deshab_habilitar.setSelected(true);
                    } else {
                        rb_deshab_habilitar.setSelected(true);
                    }                        
                }
            }      
        } catch (Exception ex) {
            
        }
    }//GEN-LAST:event_tbl_habilitarMouseClicked

    private void tbl_habilitarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbl_habilitarKeyReleased
        try { 
            if(evt.getKeyCode() == 38 || evt.getKeyCode() == 40) {
                model = (DefaultTableModel)tbl_habilitar.getModel();
                int selectedRowIndex = tbl_habilitar.getSelectedRow();
                String codigo = String.valueOf(model.getValueAt(selectedRowIndex, 0).toString());
                
                for(int i = 0; i<lista.size(); i++) {
                    if(lista.get(i).getCodigo().equals(codigo)) { //Si el codigo coincide
                        if(lista.get(i).getEstado().equals("A")) { //Verifica el tipo de estado                            
                            rb_deshab_habilitar.setSelected(true);
                        } else {
                            rb_deshab_habilitar.setSelected(true);
                        }                        
                    }
                }
            }            
        } catch (Exception ex) {
            
        } 
    }//GEN-LAST:event_tbl_habilitarKeyReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bg_crear_rol;
    private javax.swing.JButton btn_actPermi;
    private javax.swing.JButton btn_actualiUsuario;
    private javax.swing.JButton btn_crearUsuario;
    private javax.swing.JButton btn_deshabilitar;
    private javax.swing.JLabel lbl_actPermi_selectUsuario;
    private javax.swing.JLabel lbl_actuali_nombreUsuario;
    private javax.swing.JLabel lbl_actuali_nombreUsuario1;
    private javax.swing.JLabel lbl_actuali_nombreUsuario2;
    private javax.swing.JLabel lbl_actuali_passActual;
    private javax.swing.JLabel lbl_actuali_passNew;
    private javax.swing.JLabel lbl_crear_confirmPassw;
    private javax.swing.JLabel lbl_crear_correo;
    private javax.swing.JLabel lbl_crear_nombreUsuario;
    private javax.swing.JLabel lbl_crear_password;
    private javax.swing.JLabel lbl_deshab_selectUsuario;
    private javax.swing.JLabel lbl_listado_buscarUsuario;
    private javax.swing.JPanel pnl_actPermi_rolContainer;
    private javax.swing.JPanel pnl_actualizar;
    private javax.swing.JPanel pnl_actualizarPermisos;
    private javax.swing.JPanel pnl_crear;
    private javax.swing.JPanel pnl_crear_rolContainer;
    private javax.swing.JPanel pnl_deshab_deshabContainer;
    private javax.swing.JPanel pnl_deshabilitar;
    private javax.swing.JPanel pnl_listado;
    private javax.swing.JPanel pnl_modUsuario;
    private javax.swing.JPasswordField pw_actuali_confNewPass;
    private javax.swing.JPasswordField pw_actuali_lastpass;
    private javax.swing.JPasswordField pw_actuali_newPass;
    private javax.swing.JPasswordField pw_crear_confContra;
    private javax.swing.JPasswordField pw_crear_contra;
    private javax.swing.JRadioButton rb_actPermi_Admin;
    private javax.swing.JRadioButton rb_actPermi_estandar;
    private javax.swing.JRadioButton rb_crear_rolAdmin;
    private javax.swing.JRadioButton rb_crear_rolEstandar;
    private javax.swing.JRadioButton rb_deshab_deshabilitar;
    private javax.swing.JRadioButton rb_deshab_habilitar;
    private javax.swing.JScrollPane scpnl_tbl_usuarioActPermiso;
    private javax.swing.JScrollPane scpnl_tbl_usuarioCreado;
    private javax.swing.JScrollPane scpnl_tbl_usuarioDeshab;
    private javax.swing.JScrollPane scpnl_tbl_usuarioHabilitar;
    private javax.swing.JScrollPane scpnl_tbl_usuarioListado;
    private javax.swing.JTabbedPane tb_deshab;
    private javax.swing.JTabbedPane tb_modUsuario_permisos;
    private javax.swing.JTable tbl_actPermisos;
    private javax.swing.JTable tbl_deshabilitar;
    private javax.swing.JTable tbl_habilitar;
    private javax.swing.JTable tbl_usuarioCreado;
    private javax.swing.JTable tbl_usuarioListado;
    private javax.swing.JTextField txt_actuali_correo;
    private javax.swing.JTextField txt_actuali_nombreUsuario;
    private javax.swing.JTextField txt_crear_correo;
    private javax.swing.JTextField txt_crear_nombreUsuario;
    private javax.swing.JTextField txt_listado_buscar;
    // End of variables declaration//GEN-END:variables
}