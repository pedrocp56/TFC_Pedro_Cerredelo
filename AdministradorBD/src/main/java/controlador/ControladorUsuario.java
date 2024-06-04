/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import controlador.factory.HibernateUtil;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.dao.ArmaDAO;
import modelo.dao.ArmaPersonajeDAO;
import modelo.dao.PersonajeDAO;
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
    public static ArmaDAO armaDAO;
    public static ArmaPersonajeDAO armaPerDAO;
    public static PersonajeDAO perDAO;
    public static UsuarioDAO userDAO;
    public static DefaultComboBoxModel modelcbEmpleado = new DefaultComboBoxModel();
    public static DefaultTableModel modelotbCoches = new DefaultTableModel();

    public static void iniciar() {
        ventana.setVisible(true);
        ventana.setLocationRelativeTo(null);
    }

    public static void iniciarSession() {
        session = HibernateUtil.getSessionFactory().openSession();
        armaDAO = HibernateUtil.getArmaDAO();
        armaPerDAO = HibernateUtil.getArmaPersonajeDAO();
        perDAO = HibernateUtil.getPersonajeDAO();
        userDAO = HibernateUtil.getUsuarioDAO();
        System.out.println(session);
    }

    public static void cerrarSession() {
        session.close();
    }

    public static void cargarDatosUsuario() {
        if (ventana.getTxtUsuarioID().getText().isEmpty()) {
            return;
        }
        try {
            HibernateUtil.beginTx(session);
            Usuario u = userDAO.buscarUsuario(session, Integer.parseInt(ventana.getTxtUsuarioID().getText()));
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
        //ventana.getTxtUsuarioFoto().setText(u.getFoto());    
    }
}
