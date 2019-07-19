/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacion;

import controladores.CtrAcceso;
import java.awt.Component;
import java.awt.Container;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JTabbedPane;
import logica.negocio.Cliente;
import logica.negocio.Madera;
import logica.negocio.Proveedor;
import logica.negocio.Usuario;
import logica.servicios.Autoguardado;

/**
 * Inicializa la ventana principal del sistema.
 * @author ahoihanabi
 */
public class FrmPrincipal extends javax.swing.JFrame {

    private static FrmPrincipal instancia = null;
    //Internal frames de los modulos
    private static ItnFrmAccesoUsuario modUsuarioAcceso;
    private static ItnFrmUsuario modUsuario;
    private static ItnFrmCliente modCliente;
    private static ItnFrmInventario modInventario;
    private static ItnFrmProveedor modProveedor;
    private static ItnFrmFacturacion modFacturacion;
    //Controlador de acceso: variable almacena sesión actual
    private static CtrAcceso sesionAcc;
    //Listas
    private static ArrayList<Usuario> usuarios;
    private static ArrayList<Cliente> clientes;
    private static ArrayList<Madera> productos;
    private static ArrayList<Proveedor> proveedores;
    private static Autoguardado a;

    /**
     * Crea el form principal, instancia variables para almacenar el usuario en
     * sesión y la lista de todos los usuarios.
     */
    public FrmPrincipal() {
        initComponents();
        
        System.out.println("MY FRAME " + this);
        a =  Autoguardado.getInstancia("lol.txt");
        try {
            a.abrirArchivo();
            a.cerrarArchivo();
        } catch (IOException ex) {
            
        }
        
        sesionAcc = new CtrAcceso();
        usuarios = new ArrayList<>();
        ventanaAcceso();
    }

    public static FrmPrincipal getInstancia() {
        return instancia == null ? new FrmPrincipal() : instancia;
    }
    /**
     * Mostrar formulario interno de acceso al sistema.
     */
    public void ventanaAcceso() {
        modUsuarioAcceso = ItnFrmAccesoUsuario.getInstancia(sesionAcc, usuarios);
        modUsuarioAcceso.setVisible(true);

        if (dpn_principal.getComponentCount() == 0) {
            dpn_principal.add(modUsuarioAcceso);
        } else {
            if (!(dpn_principal.getComponent(0) instanceof ItnFrmAccesoUsuario)) 
                dpn_principal.add(modUsuarioAcceso);
        }
        
        modUsuarioAcceso.setLocation(300, 150);
    }
    
    /**
     * Mostrar formulario interno de facturación.
     */
    public void ventanaFacturacion() {
        modFacturacion = ItnFrmFacturacion.getInstancia(sesionAcc, clientes,
                productos);
        modFacturacion.setVisible(true);
        if (dpn_principal.getComponentCount() == 0) {
            dpn_principal.add(modFacturacion);
        } else {
            if (!(dpn_principal.getComponent(0) instanceof ItnFrmFacturacion)) 
                dpn_principal.add(modFacturacion);
        }
//        JOptionPane.showMessageDialog(null, "Hi! An amazing billing module will "
//                + "be developed here! \n Hold on a little more please. We are "
//                + "working hard!");
    }
    
    /**
     * Mostrar formulario interno de inventario.
     */
    public void ventanaInventario() {
        modInventario = ItnFrmInventario.getInstancia(sesionAcc, productos);
        //modCliente.deshabilitarPaneles();
        modInventario.setVisible(true);
        if (dpn_principal.getComponentCount() == 0) {
            dpn_principal.add(modInventario);
        } else {
            if (!(dpn_principal.getComponent(0) instanceof ItnFrmInventario)) 
                dpn_principal.add(modInventario);
        }
    }
    
    /**
     * Mostrar formulario interno de consultas.
     */
    public void ventanaConsultas() {
        JOptionPane.showMessageDialog(null, "Hi! An amazing query module "
                + "will be developed here! \n Hold on a little more please. "
                + "We are working hard!");
    }
    
    /**
     * Mostrar formulario interno de clientes.
     */
    public void ventanaClientes() {
        //Abrir formulario de usuarios.
        modCliente = ItnFrmCliente.getInstancia(sesionAcc, clientes);
        modCliente.setVisible(true);
        if (dpn_principal.getComponentCount() == 0) {
            dpn_principal.add(modCliente);
        } else {
            if (!(dpn_principal.getComponent(0) instanceof ItnFrmCliente)) 
                dpn_principal.add(modCliente);
        }
//        JOptionPane.showMessageDialog(null, "Hi! An amazing costumers module "
//                + "will be developed here! \n Hold on a little more please. "
//                + "We are working hard!");
    }
    
    /**
     * Mostrar formulario interno de proveedores.
     */
    public void ventanaProveedores() {
        modProveedor = ItnFrmProveedor.getInstancia(sesionAcc, proveedores);
        
        modProveedor.setVisible(true);
        if (dpn_principal.getComponentCount() == 0) {
            dpn_principal.add(modProveedor);
        } else {
            if (!(dpn_principal.getComponent(0) instanceof ItnFrmCliente)) 
                dpn_principal.add(modProveedor);
        }
//        JOptionPane.showMessageDialog(null, "Hi! An amazing providers module "
//                + "will be developed here! \n Hold on a little more please. "
//                + "We are working hard!");
    }
    
    /**
     * Mostrar formulario interno de usuarios.
     */
    public void ventanaUsuarios() {
        modUsuario = ItnFrmUsuario.getInstancia(sesionAcc, usuarios);
        modUsuario.deshabilitarPaneles();
        modUsuario.setVisible(true);
        if (dpn_principal.getComponentCount() == 0) {
            dpn_principal.add(modUsuario);
        } else {
            if (!(dpn_principal.getComponent(0) instanceof ItnFrmUsuario)) 
                dpn_principal.add(modUsuario);
        }
    }
    
    /**
     * Acceder al desktopPane para tener acceso a los distintos módulos del 
     * sistema.
     * @param frameParent componente padre.
     * @param internal Frame interno al que se desea accesar
     * @param num Número de pestaña a la que se ingresa
     */
    public void accederModulos(Container frameParent, JInternalFrame internal, 
            int num) {
        
        //Acceder DesktopPane
        //Container frameParent = this;//.getParent(); 
        System.out.println("FRAMEPARENT" + frameParent);
        for (Component c : frameParent.getComponents()) {
            System.out.println("Componentes " + c);
            
            if (c instanceof JRootPane) {
                for (Component r: ((JRootPane) c).getComponents()) {
                    System.out.println("ROOT " + r);
                    if (r instanceof JLayeredPane) {
                        for (Component l: ((JLayeredPane) r).getComponents()) {
                            System.out.println("LAY " + l);
                            if (l instanceof JPanel) {
                                for (Component p: ((JPanel) l).getComponents()) {
                                    System.out.println("PAN " + p);
                                    accederModuloEspecifico(internal, num, p);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    /**
     * Acceder a los distintos modulos del sistema en una pestaña específica.
     * @param internal Frame interno al que se desea accesar
     * @param num Número de pestaña a la que se ingresa
     * @param p componente que contiene el desktopPane
     */
    public void accederModuloEspecifico(JInternalFrame internal, int num, Component p) {
        /**
        * Mostrar formulario interno después de acceder al desktopPane
        */
        System.out.println("CLASS " + internal.getClass());
       if (p instanceof JDesktopPane) {
           internal.setVisible(true);
           if (((JDesktopPane) p).getComponentCount() == 0) {
               ((JDesktopPane) p).add(internal);

           } else {
               ///Object modClass = internal.getClass();
               if(internal.getClass().toString().contains(
                       "ItnFrmFacturacion")) {
                   if (!(((JDesktopPane) p).getComponent(0) instanceof 
                           ItnFrmFacturacion )) {
                        ((JDesktopPane) p).add(internal);
                    }
               } else if (internal.getClass().toString().contains(
                       "ItnFrmInventario")) {
                   if (!(((JDesktopPane) p).getComponent(0) instanceof 
                           ItnFrmInventario )) {
                        ((JDesktopPane) p).add(internal);
                    }
               } else if (internal.getClass().toString().contains(
                       "ItnFrmConsultas")) {
//                  if (!(((JDesktopPane) p).getComponent(0) instanceof 
                            //ItnFrmConsultas )) {
//                      ((JDesktopPane) p).add(internal);
//                  }
               } else if (internal.getClass().toString().contains(
                       "ItnFrmCliente")) {
                   System.out.println("well but..");
                   if (!(((JDesktopPane) p).getComponent(0) instanceof 
                           ItnFrmCliente )) {
                        ((JDesktopPane) p).add(internal);
                        System.out.println("well i'll open it");
                    }
               } else if (internal.getClass().toString().contains(
                       "ItnFrmProveedor")) {
                   if (!(((JDesktopPane) p).getComponent(0) instanceof 
                           ItnFrmProveedor )) {
                        ((JDesktopPane) p).add(internal);
                    }
               } else if (internal.getClass().toString().contains(
                       "ItnFrmUsuario")) {
                   if (!(((JDesktopPane) p).getComponent(0) instanceof 
                           ItnFrmUsuario )) {
                        ((JDesktopPane) p).add(internal);
                    }
               }

           }
           for (Component i: internal.getComponents()) {
               System.out.println("MODD " + i);
               if (i instanceof JRootPane) {
                   for (Component rm: ((JRootPane) i).getComponents()) {
                       System.out.println("ROOTT " + rm);
                       if (rm instanceof JLayeredPane) {
                           for (Component lm: ((JLayeredPane) rm).
                                   getComponents()) {
                               System.out.println("LAYY " + lm);
                               if (lm instanceof JPanel) {
                                   for (Component pm: ((JPanel) lm).
                                           getComponents()) {
                                       System.out.println("PAN " + p);
                                       if (pm instanceof JPanel) {
                                           for (Component tm: ((JPanel) pm).
                                                   getComponents()) {
                                               System.out.println("TAB " + tm);
                                               if (tm instanceof JTabbedPane) {
                                                   ((JTabbedPane) tm).
                                                           setSelectedIndex(num);
                                               }
                                           }
                                       }
                                   }
                               }
                           }
                       }
                   }
               }
           }
       }
                                        
    }
    /**
     * ???
     */
    public void mostrarInternalFrame() {
        switch (sesionAcc.getModuloActual()) {
            case MODULO_ACCESO:
                break;
            case MODULO_CLIENTES:
                break;
            case MODULO_CONSULTAS:
                break;
            case MODULO_FACTURACION:
                break;
            case MODULO_INVENTARIO:
                break;
            case MODULO_MAQUINARIA:
                break;
            case MODULO_PROVEEDORES:
                break;
            case MODULO_USUARIOS:
                break;
            default:
                break;
        }
    }
    
    /**
     * Inhablita el acceso a la interfaz (bloquea botones).
     */
    public void cerrarSesion() {
        cerrarInternalFrame();
        bloquearBotones();
    }

    /**
     * Deshabilita los botones de los modulos.
     */
    public void bloquearBotones() {
        
        for (Component c: tlb_modulos.getComponents())
            c.setEnabled(false);
        
        mnb_principal.setEnabled(false);
        for (Component c: mnb_principal.getComponents())
            c.setEnabled(false);
    }
    
    /**
     * Cerrar formulario interno en el desktop panel principal.
     */
    public void cerrarInternalFrame() {

        Container frameParent = this.getRootPane().getContentPane();
        System.out.println("fp " + frameParent);
        
        //Frame principal, tiene JToolbar DesktopPane..
        for (Component c : frameParent.getComponents()) {
            if (c instanceof JDesktopPane) {
                for (Component i : ((JDesktopPane) c).getComponents()) {
                    System.out.println("I " + i);
                    if (i instanceof JInternalFrame) {
                        System.out.println("Internal: " + ((JInternalFrame) i).getName());
                        //((JInternalFrame) i).dispose();
                        dpn_principal.remove(i);
                    }
                }
                this.pack();
            }
        }
        sesionAcc.setUsuario(null);
        
        usuarios.clear();
        ventanaAcceso();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dpn_principal = new javax.swing.JDesktopPane();
        scpnlModulos = new javax.swing.JScrollPane();
        tlb_modulos = new javax.swing.JToolBar();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        btn_facturacion = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        btn_inventario = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        btn_consultas = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JToolBar.Separator();
        btn_clientes = new javax.swing.JButton();
        jSeparator5 = new javax.swing.JToolBar.Separator();
        btn_proveedor = new javax.swing.JButton();
        jSeparator7 = new javax.swing.JToolBar.Separator();
        btn_usuarios = new javax.swing.JButton();
        mnb_principal = new javax.swing.JMenuBar();
        mnbtn_archivo = new javax.swing.JMenu();
        mniCerrarSesion = new javax.swing.JMenuItem();
        mniSalir = new javax.swing.JMenuItem();
        mnbtn_modulos = new javax.swing.JMenu();
        mnClientes = new javax.swing.JMenu();
        mniAgregarCliente = new javax.swing.JMenuItem();
        mniEditarCliente = new javax.swing.JMenuItem();
        mniHabilitarCliente = new javax.swing.JMenuItem();
        mniListadoClientes = new javax.swing.JMenuItem();
        jSeparator6 = new javax.swing.JPopupMenu.Separator();
        mnConsultas = new javax.swing.JMenu();
        jSeparator8 = new javax.swing.JPopupMenu.Separator();
        mnFacturacion = new javax.swing.JMenu();
        jSeparator9 = new javax.swing.JPopupMenu.Separator();
        mnInventario = new javax.swing.JMenu();
        mniActualizarInventario = new javax.swing.JMenuItem();
        mniAgregarProdNuevo = new javax.swing.JMenuItem();
        mniEditarProd = new javax.swing.JMenuItem();
        mniHabilitarInventario = new javax.swing.JMenuItem();
        mniListadoProd = new javax.swing.JMenuItem();
        jSeparator10 = new javax.swing.JPopupMenu.Separator();
        mnProveedores = new javax.swing.JMenu();
        mniAgregarProveedor = new javax.swing.JMenuItem();
        mniEditarProveedor = new javax.swing.JMenuItem();
        mniHabilitarProveedor = new javax.swing.JMenuItem();
        mniListadoProveedores = new javax.swing.JMenuItem();
        jSeparator11 = new javax.swing.JPopupMenu.Separator();
        mnUsuarios = new javax.swing.JMenu();
        mniActualizarInfo = new javax.swing.JMenuItem();
        mniActualizarPermisos = new javax.swing.JMenuItem();
        mniAgregarUsuario = new javax.swing.JMenuItem();
        mniHabilitarUsuarios = new javax.swing.JMenuItem();
        mniListadoUsuarios = new javax.swing.JMenuItem();
        mnbtn_ver = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("SAI-AES");
        setBackground(new java.awt.Color(153, 0, 0));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setIconImage(Toolkit.getDefaultToolkit().getImage("recursos/aes_logo.png"));
        setLocation(new java.awt.Point(0, 0));
        setUndecorated(true);
        setResizable(false);

        dpn_principal.setBackground(new java.awt.Color(163, 36, 29));
        dpn_principal.setName("DesktopPanelPrincipal"); // NOI18N

        javax.swing.GroupLayout dpn_principalLayout = new javax.swing.GroupLayout(dpn_principal);
        dpn_principal.setLayout(dpn_principalLayout);
        dpn_principalLayout.setHorizontalGroup(
            dpn_principalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1245, Short.MAX_VALUE)
        );
        dpn_principalLayout.setVerticalGroup(
            dpn_principalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 670, Short.MAX_VALUE)
        );

        scpnlModulos.setName("ScrollPanePrincipal"); // NOI18N

        tlb_modulos.setFloatable(false);
        tlb_modulos.setOrientation(javax.swing.SwingConstants.VERTICAL);
        tlb_modulos.setRollover(true);
        tlb_modulos.setName("ToolbarBotones"); // NOI18N
        tlb_modulos.setPreferredSize(new java.awt.Dimension(104, 707));

        jSeparator1.setSeparatorSize(new java.awt.Dimension(15, 10));
        tlb_modulos.add(jSeparator1);

        btn_facturacion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/facturacion-60.png"))); // NOI18N
        btn_facturacion.setText(" Facturación");
        btn_facturacion.setEnabled(false);
        btn_facturacion.setFocusable(false);
        btn_facturacion.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_facturacion.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_facturacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_facturacionActionPerformed(evt);
            }
        });
        tlb_modulos.add(btn_facturacion);

        jSeparator2.setSeparatorSize(new java.awt.Dimension(15, 10));
        tlb_modulos.add(jSeparator2);

        btn_inventario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/Inventario-60.png"))); // NOI18N
        btn_inventario.setText("  Inventario  ");
        btn_inventario.setEnabled(false);
        btn_inventario.setFocusable(false);
        btn_inventario.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_inventario.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_inventario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_inventarioActionPerformed(evt);
            }
        });
        tlb_modulos.add(btn_inventario);

        jSeparator3.setSeparatorSize(new java.awt.Dimension(15, 10));
        tlb_modulos.add(jSeparator3);

        btn_consultas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/consulta-60.png"))); // NOI18N
        btn_consultas.setText("  Consultas  ");
        btn_consultas.setEnabled(false);
        btn_consultas.setFocusable(false);
        btn_consultas.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_consultas.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_consultas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_consultasActionPerformed(evt);
            }
        });
        tlb_modulos.add(btn_consultas);

        jSeparator4.setSeparatorSize(new java.awt.Dimension(15, 10));
        tlb_modulos.add(jSeparator4);

        btn_clientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/clientes-60.png"))); // NOI18N
        btn_clientes.setText("   Clientes    ");
        btn_clientes.setEnabled(false);
        btn_clientes.setFocusable(false);
        btn_clientes.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_clientes.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_clientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_clientesActionPerformed(evt);
            }
        });
        tlb_modulos.add(btn_clientes);

        jSeparator5.setSeparatorSize(new java.awt.Dimension(15, 10));
        tlb_modulos.add(jSeparator5);

        btn_proveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/proveedor-60.png"))); // NOI18N
        btn_proveedor.setText("Proveedores");
        btn_proveedor.setEnabled(false);
        btn_proveedor.setFocusable(false);
        btn_proveedor.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_proveedor.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_proveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_proveedorActionPerformed(evt);
            }
        });
        tlb_modulos.add(btn_proveedor);

        jSeparator7.setSeparatorSize(new java.awt.Dimension(15, 10));
        tlb_modulos.add(jSeparator7);

        btn_usuarios.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/user-60.png"))); // NOI18N
        btn_usuarios.setText("   Usuarios   ");
        btn_usuarios.setEnabled(false);
        btn_usuarios.setFocusable(false);
        btn_usuarios.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_usuarios.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_usuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_usuariosActionPerformed(evt);
            }
        });
        tlb_modulos.add(btn_usuarios);

        scpnlModulos.setViewportView(tlb_modulos);

        mnb_principal.setName("MenuPrincipal"); // NOI18N

        mnbtn_archivo.setText("Archivo");
        mnbtn_archivo.setEnabled(false);

        mniCerrarSesion.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        mniCerrarSesion.setText("Cerrar Sesión");
        mniCerrarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniCerrarSesionActionPerformed(evt);
            }
        });
        mnbtn_archivo.add(mniCerrarSesion);

        mniSalir.setText("Salir");
        mnbtn_archivo.add(mniSalir);

        mnb_principal.add(mnbtn_archivo);

        mnbtn_modulos.setText("Módulos");
        mnbtn_modulos.setEnabled(false);

        mnClientes.setText("Clientes");

        mniAgregarCliente.setText("Agregar");
        mniAgregarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniAgregarClienteActionPerformed(evt);
            }
        });
        mnClientes.add(mniAgregarCliente);

        mniEditarCliente.setText("Editar");
        mniEditarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniEditarClienteActionPerformed(evt);
            }
        });
        mnClientes.add(mniEditarCliente);

        mniHabilitarCliente.setText("Habilitar/Deshabilitar");
        mniHabilitarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniHabilitarClienteActionPerformed(evt);
            }
        });
        mnClientes.add(mniHabilitarCliente);

        mniListadoClientes.setText("Listado de clientes");
        mniListadoClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniListadoClientesActionPerformed(evt);
            }
        });
        mnClientes.add(mniListadoClientes);

        mnbtn_modulos.add(mnClientes);
        mnbtn_modulos.add(jSeparator6);

        mnConsultas.setText("Consultas");
        mnbtn_modulos.add(mnConsultas);
        mnbtn_modulos.add(jSeparator8);

        mnFacturacion.setText("Facturación");
        mnbtn_modulos.add(mnFacturacion);
        mnbtn_modulos.add(jSeparator9);

        mnInventario.setText("Inventario");

        mniActualizarInventario.setText("Actualizar inventario");
        mniActualizarInventario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniActualizarInventarioActionPerformed(evt);
            }
        });
        mnInventario.add(mniActualizarInventario);

        mniAgregarProdNuevo.setText("Agregar nuevo");
        mniAgregarProdNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniAgregarProdNuevoActionPerformed(evt);
            }
        });
        mnInventario.add(mniAgregarProdNuevo);

        mniEditarProd.setText("Editar");
        mniEditarProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniEditarProdActionPerformed(evt);
            }
        });
        mnInventario.add(mniEditarProd);

        mniHabilitarInventario.setText("Habilitar/Deshabilitar");
        mniHabilitarInventario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniHabilitarInventarioActionPerformed(evt);
            }
        });
        mnInventario.add(mniHabilitarInventario);

        mniListadoProd.setText("Listado de productos");
        mniListadoProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniListadoProdActionPerformed(evt);
            }
        });
        mnInventario.add(mniListadoProd);

        mnbtn_modulos.add(mnInventario);
        mnbtn_modulos.add(jSeparator10);

        mnProveedores.setText("Proveedores");

        mniAgregarProveedor.setText("Agregar");
        mniAgregarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniAgregarProveedorActionPerformed(evt);
            }
        });
        mnProveedores.add(mniAgregarProveedor);

        mniEditarProveedor.setText("Editar");
        mniEditarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniEditarProveedorActionPerformed(evt);
            }
        });
        mnProveedores.add(mniEditarProveedor);

        mniHabilitarProveedor.setText("Habilitar/Deshabilitar");
        mniHabilitarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniHabilitarProveedorActionPerformed(evt);
            }
        });
        mnProveedores.add(mniHabilitarProveedor);

        mniListadoProveedores.setText("Listado de proveedores");
        mniListadoProveedores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniListadoProveedoresActionPerformed(evt);
            }
        });
        mnProveedores.add(mniListadoProveedores);

        mnbtn_modulos.add(mnProveedores);
        mnbtn_modulos.add(jSeparator11);

        mnUsuarios.setText("Usuarios");

        mniActualizarInfo.setText("Actualizar informacion");
        mniActualizarInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniActualizarInfoActionPerformed(evt);
            }
        });
        mnUsuarios.add(mniActualizarInfo);

        mniActualizarPermisos.setText("Actualizar permisos");
        mniActualizarPermisos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniActualizarPermisosActionPerformed(evt);
            }
        });
        mnUsuarios.add(mniActualizarPermisos);

        mniAgregarUsuario.setText("Agregar");
        mniAgregarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniAgregarUsuarioActionPerformed(evt);
            }
        });
        mnUsuarios.add(mniAgregarUsuario);

        mniHabilitarUsuarios.setText("Habilitar/Deshabilitar");
        mniHabilitarUsuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniHabilitarUsuariosActionPerformed(evt);
            }
        });
        mnUsuarios.add(mniHabilitarUsuarios);

        mniListadoUsuarios.setText("Listado de usuarios");
        mniListadoUsuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniListadoUsuariosActionPerformed(evt);
            }
        });
        mnUsuarios.add(mniListadoUsuarios);

        mnbtn_modulos.add(mnUsuarios);

        mnb_principal.add(mnbtn_modulos);

        mnbtn_ver.setText("Ayuda");
        mnbtn_ver.setEnabled(false);
        mnb_principal.add(mnbtn_ver);

        setJMenuBar(mnb_principal);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(scpnlModulos, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dpn_principal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scpnlModulos)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(dpn_principal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    /**
     *
     * @param evt
     */
    private void btn_usuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_usuariosActionPerformed
        //Abrir formulario de usuarios.
        ventanaUsuarios();
    }//GEN-LAST:event_btn_usuariosActionPerformed

    private void mniCerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniCerrarSesionActionPerformed
        cerrarSesion();
    }//GEN-LAST:event_mniCerrarSesionActionPerformed

    private void btn_facturacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_facturacionActionPerformed
        //Abrir formulario de facturación.
        ventanaFacturacion();
    }//GEN-LAST:event_btn_facturacionActionPerformed

    private void btn_inventarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_inventarioActionPerformed
        //Abrir formulario de inventario.
        ventanaInventario();
    }//GEN-LAST:event_btn_inventarioActionPerformed

    private void btn_consultasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_consultasActionPerformed
        ventanaConsultas();
    }//GEN-LAST:event_btn_consultasActionPerformed

    private void btn_clientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_clientesActionPerformed
        ventanaClientes();
    }//GEN-LAST:event_btn_clientesActionPerformed

    private void btn_proveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_proveedorActionPerformed
        ventanaProveedores();
    }//GEN-LAST:event_btn_proveedorActionPerformed

    private void mniAgregarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniAgregarClienteActionPerformed
        modCliente = ItnFrmCliente.getInstancia(sesionAcc, clientes);
        //accederModulos(modCliente,1);
        accederModulos(this,modCliente,1);
        System.out.println(FrmPrincipal.getInstancia());
    }//GEN-LAST:event_mniAgregarClienteActionPerformed

    private void mniHabilitarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniHabilitarClienteActionPerformed
        modCliente = ItnFrmCliente.getInstancia(sesionAcc, clientes);
        //accederModulos(modCliente,3);
        accederModulos(this,modCliente,3);
    }//GEN-LAST:event_mniHabilitarClienteActionPerformed

    private void mniListadoClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniListadoClientesActionPerformed
        modCliente = ItnFrmCliente.getInstancia(sesionAcc, clientes);
       // accederModulos(modCliente,0);
       accederModulos(this,modCliente,0);
    }//GEN-LAST:event_mniListadoClientesActionPerformed

    private void mniActualizarInventarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniActualizarInventarioActionPerformed
        modInventario = ItnFrmInventario.getInstancia(sesionAcc, productos);
        //accederModulos(modInventario,2);
        accederModulos(this,modInventario,2);
    }//GEN-LAST:event_mniActualizarInventarioActionPerformed

    private void mniAgregarProdNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniAgregarProdNuevoActionPerformed
        modInventario = ItnFrmInventario.getInstancia(sesionAcc, productos);
        //accederModulos(modInventario,1);
        accederModulos(this,modInventario,1);
    }//GEN-LAST:event_mniAgregarProdNuevoActionPerformed

    private void mniEditarProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniEditarProdActionPerformed
        modInventario = ItnFrmInventario.getInstancia(sesionAcc, productos);
        //accederModulos(modInventario,3);
        accederModulos(this,modInventario,3);
    }//GEN-LAST:event_mniEditarProdActionPerformed

    private void mniHabilitarInventarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniHabilitarInventarioActionPerformed
        modInventario = ItnFrmInventario.getInstancia(sesionAcc, productos);
       // accederModulos(modInventario,4);
       accederModulos(this,modInventario,4);
    }//GEN-LAST:event_mniHabilitarInventarioActionPerformed

    private void mniListadoProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniListadoProdActionPerformed
        modInventario = ItnFrmInventario.getInstancia(sesionAcc, productos);
        //accederModulos(modInventario,0);
        accederModulos(this,modInventario,0);
    }//GEN-LAST:event_mniListadoProdActionPerformed

    private void mniAgregarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniAgregarProveedorActionPerformed
        modProveedor = ItnFrmProveedor.getInstancia(sesionAcc, proveedores);
        //accederModulos(modProveedor,1);
        accederModulos(this,modProveedor,1);
    }//GEN-LAST:event_mniAgregarProveedorActionPerformed

    private void mniEditarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniEditarProveedorActionPerformed
        modProveedor = ItnFrmProveedor.getInstancia(sesionAcc, proveedores);
        //accederModulos(modProveedor,2);
        accederModulos(this,modProveedor,2);
    }//GEN-LAST:event_mniEditarProveedorActionPerformed

    private void mniHabilitarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniHabilitarProveedorActionPerformed
        modProveedor = ItnFrmProveedor.getInstancia(sesionAcc, proveedores);
        //accederModulos(modProveedor,3);
        accederModulos(this,modProveedor,3);
    }//GEN-LAST:event_mniHabilitarProveedorActionPerformed

    private void mniListadoProveedoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniListadoProveedoresActionPerformed
        modProveedor = ItnFrmProveedor.getInstancia(sesionAcc, proveedores);
        //accederModulos(modProveedor,0);
        accederModulos(this,modProveedor,0);
    }//GEN-LAST:event_mniListadoProveedoresActionPerformed

    private void mniActualizarInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniActualizarInfoActionPerformed
        modUsuario = ItnFrmUsuario.getInstancia(sesionAcc, usuarios);
        //accederModulos(modUsuario,2);
        accederModulos(this,modUsuario,2);
    }//GEN-LAST:event_mniActualizarInfoActionPerformed

    private void mniActualizarPermisosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniActualizarPermisosActionPerformed
        modUsuario = ItnFrmUsuario.getInstancia(sesionAcc, usuarios);
        //accederModulos(modUsuario,4);
        accederModulos(this,modUsuario,4);
    }//GEN-LAST:event_mniActualizarPermisosActionPerformed

    private void mniAgregarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniAgregarUsuarioActionPerformed
        modUsuario = ItnFrmUsuario.getInstancia(sesionAcc, usuarios);
        //accederModulos(modUsuario,1);
        accederModulos(this,modUsuario,1);
    }//GEN-LAST:event_mniAgregarUsuarioActionPerformed

    private void mniHabilitarUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniHabilitarUsuariosActionPerformed
        modUsuario = ItnFrmUsuario.getInstancia(sesionAcc, usuarios);
        //accederModulos(modUsuario,3);
        accederModulos(this,modUsuario,3);
    }//GEN-LAST:event_mniHabilitarUsuariosActionPerformed

    private void mniListadoUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniListadoUsuariosActionPerformed
        modUsuario = ItnFrmUsuario.getInstancia(sesionAcc, usuarios);
        //accederModulos(modUsuario,0);
        accederModulos(this,modUsuario,0);
    }//GEN-LAST:event_mniListadoUsuariosActionPerformed

    private void mniEditarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniEditarClienteActionPerformed
        modCliente = ItnFrmCliente.getInstancia(sesionAcc, clientes);
        //accederModulos(modCliente, 2);
        accederModulos(this,modCliente, 2);
    }//GEN-LAST:event_mniEditarClienteActionPerformed

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
            /*for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Linux".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }*/
            javax.swing.UIManager.setLookAndFeel(
                    javax.swing.UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        FrmPrincipal frame = new FrmPrincipal();
        java.awt.EventQueue.invokeLater(() -> {
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_clientes;
    private javax.swing.JButton btn_consultas;
    private javax.swing.JButton btn_facturacion;
    private javax.swing.JButton btn_inventario;
    private javax.swing.JButton btn_proveedor;
    private javax.swing.JButton btn_usuarios;
    private javax.swing.JDesktopPane dpn_principal;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator10;
    private javax.swing.JPopupMenu.Separator jSeparator11;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JToolBar.Separator jSeparator5;
    private javax.swing.JPopupMenu.Separator jSeparator6;
    private javax.swing.JToolBar.Separator jSeparator7;
    private javax.swing.JPopupMenu.Separator jSeparator8;
    private javax.swing.JPopupMenu.Separator jSeparator9;
    private javax.swing.JMenu mnClientes;
    private javax.swing.JMenu mnConsultas;
    private javax.swing.JMenu mnFacturacion;
    private javax.swing.JMenu mnInventario;
    private javax.swing.JMenu mnProveedores;
    private javax.swing.JMenu mnUsuarios;
    private javax.swing.JMenuBar mnb_principal;
    private javax.swing.JMenu mnbtn_archivo;
    private javax.swing.JMenu mnbtn_modulos;
    private javax.swing.JMenu mnbtn_ver;
    private javax.swing.JMenuItem mniActualizarInfo;
    private javax.swing.JMenuItem mniActualizarInventario;
    private javax.swing.JMenuItem mniActualizarPermisos;
    private javax.swing.JMenuItem mniAgregarCliente;
    private javax.swing.JMenuItem mniAgregarProdNuevo;
    private javax.swing.JMenuItem mniAgregarProveedor;
    private javax.swing.JMenuItem mniAgregarUsuario;
    private javax.swing.JMenuItem mniCerrarSesion;
    private javax.swing.JMenuItem mniEditarCliente;
    private javax.swing.JMenuItem mniEditarProd;
    private javax.swing.JMenuItem mniEditarProveedor;
    private javax.swing.JMenuItem mniHabilitarCliente;
    private javax.swing.JMenuItem mniHabilitarInventario;
    private javax.swing.JMenuItem mniHabilitarProveedor;
    private javax.swing.JMenuItem mniHabilitarUsuarios;
    private javax.swing.JMenuItem mniListadoClientes;
    private javax.swing.JMenuItem mniListadoProd;
    private javax.swing.JMenuItem mniListadoProveedores;
    private javax.swing.JMenuItem mniListadoUsuarios;
    private javax.swing.JMenuItem mniSalir;
    private javax.swing.JScrollPane scpnlModulos;
    private javax.swing.JToolBar tlb_modulos;
    // End of variables declaration//GEN-END:variables
}
