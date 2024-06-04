/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import controlador.factory.HibernateUtil;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;
import modelo.dao.ArmaDAO;
import modelo.dao.ArmaPersonajeDAO;
import modelo.dao.PersonajeDAO;
import modelo.dao.UsuarioDAO;
import org.hibernate.Session;
import vista.Start;

/**
 *
 * @author pedro
 */
public class ControladorArmaPersonaje {
    public static Session session;

    public static Start ventana = new Start();
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
    }

    public static void cerrarSession() {
        session.close();
    }
}
