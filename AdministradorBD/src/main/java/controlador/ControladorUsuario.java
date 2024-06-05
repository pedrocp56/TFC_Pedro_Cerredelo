/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import controlador.factory.HibernateUtil;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.dao.UsuarioDAO;
import modelo.vo.Usuario;
import org.hibernate.Session;
import vista.VistaUsuario;

/**
 *
 * @author pedro
 */
public class ControladorUsuario {

    public static Session session;

    public static VistaUsuario ventana = new VistaUsuario();
    public static UsuarioDAO userDAO;
    public static DefaultTableModel modelotbUsuario = new DefaultTableModel();

    public static void iniciar() {
        ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ventana.setVisible(true);
        ventana.setLocationRelativeTo(null);
        modelotbUsuario = (DefaultTableModel) ventana.getTbUsuario().getModel();
    }

    public static void iniciarSession() {
        session = HibernateUtil.getSessionFactory().openSession();
        userDAO = HibernateUtil.getUsuarioDAO();
        System.out.println(session);
    }

    public static void cerrarSession() {
        session.close();
    }

    public static void cargarDatosUsuario() {
        if (!comprobarID(ventana.getTxtUsuarioID().getText())) {
            return;
        }
        try {
            HibernateUtil.beginTx(session);
            int id = Integer.parseInt(ventana.getTxtUsuarioID().getText());
            Usuario u = userDAO.buscarUsuario(session, id);
            if (u == null) {
                limpiarDatos();
            } else {
                ventana.getTxtUsuarioNombre().setText(u.getNombre());
                ventana.getTxtUsuarioContraseña().setText(u.getContrasenha());
                ventana.getTxtUsuarioEstado().setText(u.getEstado());
                //ventana.getTxtUsuarioFoto().setText(u.getFoto());
            }
            session.getTransaction().commit();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Error de formato");
            session.getTransaction().rollback();
            ventana.getTxtUsuarioID().grabFocus();
        } catch (Exception ex) {
            session.getTransaction().rollback();
            Logger.getLogger(ControladorUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private static void limpiarDatos() {
        ventana.getTxtUsuarioNombre().setText("");
        ventana.getTxtUsuarioContraseña().setText("");
        ventana.getTxtUsuarioEstado().setText("");
        ventana.getTxtUsuarioFoto().setText("");
    }

    public static void guardarUsuario() {
        if (!comprobarDatos(ventana.getTxtUsuarioNombre().getText(), ventana.getTxtUsuarioContraseña().getText(), ventana.getTxtUsuarioEstado().getText())) {
            return;
        }
        try {
            HibernateUtil.beginTx(session);
            Usuario uNombre = userDAO.buscarUsuarioPorNombre(session, ventana.getTxtUsuarioNombre().getText());
            Usuario u = null;
            if (comprobarID(ventana.getTxtUsuarioID().getText())) {
                u = userDAO.buscarUsuario(session, Integer.parseInt(ventana.getTxtUsuarioID().getText()));
            }
            if (u == null && uNombre == null) {
                Usuario uNuevo = new Usuario(null, ventana.getTxtUsuarioNombre().getText(), ventana.getTxtUsuarioContraseña().getText(), ventana.getTxtUsuarioEstado().getText(), null);
                userDAO.insertarUsaurio(session, uNuevo);
                JOptionPane.showMessageDialog(null, "Usuario añadido");
                limpiarDatos();
            } else if (u != null && (uNombre == null || uNombre.getId() == u.getId())) {
                userDAO.modificarUsuario(session, u, ventana.getTxtUsuarioNombre().getText(), ventana.getTxtUsuarioContraseña().getText(), ventana.getTxtUsuarioEstado().getText(), null);
                JOptionPane.showMessageDialog(null, "Usuario modificado");
            } else if (uNombre != null) {
                JOptionPane.showMessageDialog(null, "Ya existe el usuario " + uNombre.getId().toString() + " con ese nombre");
            }
            session.getTransaction().commit();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Error de formato");
            session.getTransaction().rollback();
            ventana.getTxtUsuarioID().grabFocus();
        } catch (Exception ex) {
            session.getTransaction().rollback();
            Logger.getLogger(ControladorUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void borrarUsuario() {
        if (!comprobarID(ventana.getTxtUsuarioID().getText())) {
            return;
        }
        try {
            HibernateUtil.beginTx(session);
            int id = Integer.parseInt(ventana.getTxtUsuarioID().getText());
            Usuario u = userDAO.buscarUsuario(session, id);
            if (u == null) {
                JOptionPane.showMessageDialog(null, "No existe ningun usuario con ese id");
            } else {
                userDAO.borrarUsuario(session, u);
                JOptionPane.showMessageDialog(null, "Usuario borrado");
                limpiarDatos();
            }
            session.getTransaction().commit();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Error de formato");
            session.getTransaction().rollback();
            ventana.getTxtUsuarioID().grabFocus();
        } catch (Exception ex) {
            session.getTransaction().rollback();
            Logger.getLogger(ControladorUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void mostrarUsuarios() {
        userDAO.cargarTb(session, modelotbUsuario);
    }

    private static boolean comprobarID(String id) {
        return !id.isEmpty();
    }

    //falta revisar
    private static boolean comprobarNombre(String nombre) {
        if (nombre.isEmpty()) {
            return false;
        }
        //comprobar que es un correo
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return nombre.matches(emailRegex);

    }

    private static boolean comprobarContraseña(String contraseña) {
        return contraseña.length() >= 5 && contraseña.length() <= 13 && contraseña.matches("[a-zA-Z0-9]+");
    }

    private static boolean comprobarEstado(String estado) {
        return !estado.isEmpty();
    }

    private static boolean comprobarDatos(String nombre, String contraseña, String estado) {
        if (!comprobarNombre(nombre)) {
            JOptionPane.showMessageDialog(null, "Nombre incorrecto, tiene que ser un correo");
            return (false);
        }
        if (!comprobarContraseña(contraseña)) {
            JOptionPane.showMessageDialog(null, "Contraseña incorrecta, tiene que ser una cadena alfanumerica entre 5 y 13 (incluidos)");
            return (false);
        }
        if (!comprobarEstado(estado)) {
            JOptionPane.showMessageDialog(null, "Estado incorrecto, no puede ser nulo");
            return (false);
        }
        return (true);
    }
}
