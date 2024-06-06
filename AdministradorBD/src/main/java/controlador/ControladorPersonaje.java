/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import controlador.factory.HibernateUtil;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.dao.PersonajeDAO;
import modelo.dao.UsuarioDAO;
import modelo.vo.Personaje;
import modelo.vo.PersonajePK;
import modelo.vo.Usuario;
import org.hibernate.Session;
import vista.VistaPersonaje;
import vista.vPersonaje;

/**
 *
 * @author pedro
 */
public class ControladorPersonaje {

    public static Session session;
    public static vPersonaje ventana;
    public static PersonajeDAO perDAO;
    public static UsuarioDAO userDAO;
    public static DefaultComboBoxModel modelcbUsuario = new DefaultComboBoxModel();
    public static DefaultTableModel modelotbPersonaje = new DefaultTableModel();

    public static void iniciar(JFrame start) {
        ventana = new vPersonaje(start, true);
        ventana.getCbPersonajeUser().setModel(modelcbUsuario);
        modelotbPersonaje = (DefaultTableModel) ventana.getTbPersonaje().getModel();
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
    }

    public static void iniciarSession() {
        session = HibernateUtil.getSessionFactory().openSession();
        perDAO = HibernateUtil.getPersonajeDAO();
        userDAO = HibernateUtil.getUsuarioDAO();
    }

    public static void cerrarSession() {
        session.close();
    }

    public static void guardarPersonaje() {
        if (!comprobarDatos(ventana.getTxtPersonajeNombre().getText(), ventana.getTxtPersonajeFuerza().getText(),
                ventana.getTxtPersonajeDestreza().getText(), ventana.getTxtPersonajeConstitucion().getText(),
                ventana.getTxtPersonajeInteligencia().getText(), ventana.getTxtPersonajeSabiduria().getText(),
                ventana.getTxtPersonajeCarisma().getText(), ventana.getTxtPersonajeCompetencia().getText())) {
            return;
        }
        try {
            HibernateUtil.beginTx(session);
            Personaje p = null;
            if (comprobarID(ventana.getTxtPersonajeID().getText())) {
                p = perDAO.buscarPersonaje(session, Integer.parseInt(ventana.getTxtPersonajeID().getText()));
            }
            Usuario u = (Usuario) ventana.getCbPersonajeUser().getSelectedItem();
            if (u == null) {
                JOptionPane.showMessageDialog(null, "Falta el usuario");
            } else {
                if (p != null && (p.getUsuario().getId() == u.getId())) {
                    perDAO.modificarPersonaje(session, p, ventana.getTxtPersonajeNombre().getText(), Integer.parseInt(ventana.getTxtPersonajeFuerza().getText()),
                            Integer.parseInt(ventana.getTxtPersonajeDestreza().getText()), Integer.parseInt(ventana.getTxtPersonajeConstitucion().getText()),
                            Integer.parseInt(ventana.getTxtPersonajeInteligencia().getText()), Integer.parseInt(ventana.getTxtPersonajeSabiduria().getText()),
                            Integer.parseInt(ventana.getTxtPersonajeCarisma().getText()), Integer.parseInt(ventana.getTxtPersonajeCompetencia().getText()),
                            null);
                    JOptionPane.showMessageDialog(null, "Personaje modificado");
                } else {
                    Personaje pNuevo;
                    pNuevo = new Personaje(new PersonajePK(u.getId(), 0), ventana.getTxtPersonajeNombre().getText(), Integer.valueOf(ventana.getTxtPersonajeFuerza().getText()),
                            Integer.valueOf(ventana.getTxtPersonajeDestreza().getText()), Integer.valueOf(ventana.getTxtPersonajeConstitucion().getText()),
                            Integer.valueOf(ventana.getTxtPersonajeInteligencia().getText()), Integer.valueOf(ventana.getTxtPersonajeSabiduria().getText()),
                            Integer.valueOf(ventana.getTxtPersonajeCarisma().getText()), Integer.valueOf(ventana.getTxtPersonajeCompetencia().getText()),
                            null, u);
                    perDAO.insertarPersonaje(session, pNuevo);
                    JOptionPane.showMessageDialog(null, "Personaje añadido");
                    limpiarDatos();
                }
            }
            HibernateUtil.commitTx(session);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Error de formato");
            HibernateUtil.rollbackTx(session);
        } catch (Exception ex) {
            HibernateUtil.rollbackTx(session);
            Logger.getLogger(ControladorPersonaje.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void eliminarPersonaje() {
        if (!comprobarID(ventana.getTxtPersonajeID().getText())) {
            return;
        }
        try {
            HibernateUtil.beginTx(session);
            int id = Integer.parseInt(ventana.getTxtPersonajeID().getText());
            Personaje p = perDAO.buscarPersonaje(session, id);
            if (p == null) {
                JOptionPane.showMessageDialog(null, "No existe ningun personaje con ese id");
            } else {
                perDAO.borrarPersonaje(session, p);
                JOptionPane.showMessageDialog(null, "Personaje borrado");
                limpiarDatos();
            }
            HibernateUtil.commitTx(session);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Error de formato");
            HibernateUtil.rollbackTx(session);
            ventana.getTxtPersonajeID().grabFocus();
        } catch (Exception ex) {
            HibernateUtil.rollbackTx(session);
            Logger.getLogger(ControladorPersonaje.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void mostrarPersonajes() {
        perDAO.cargarTb(session, modelotbPersonaje);
    }

    public static void cargarCb() {
        userDAO.cargarCb(session, modelcbUsuario);
    }

    public static void cargarDatos() {
        if (!comprobarID(ventana.getTxtPersonajeID().getText())) {
            return;
        }
        try {
            HibernateUtil.beginTx(session);
            int id = Integer.parseInt(ventana.getTxtPersonajeID().getText());
            Personaje p = perDAO.buscarPersonaje(session, id);
            if (p == null) {
                limpiarDatos();
            } else {
                ventana.getTxtPersonajeNombre().setText(p.getPersonajeNombre());
                ventana.getCbPersonajeUser().setSelectedItem(p.getUsuario());
                ventana.getTxtPersonajeFuerza().setText(p.getCaracteristicaFuerza().toString());
                ventana.getTxtPersonajeDestreza().setText(p.getCaracteristicaDestreza().toString());
                ventana.getTxtPersonajeConstitucion().setText(p.getCaracteristicaConstitucion().toString());
                ventana.getTxtPersonajeInteligencia().setText(p.getCaracteristicaInteligencia().toString());
                ventana.getTxtPersonajeSabiduria().setText(p.getCaracteristicaSabiduria().toString());
                ventana.getTxtPersonajeCarisma().setText(p.getCaracteristicaCarisma().toString());
                ventana.getTxtPersonajeCompetencia().setText(p.getBonoCompetencia().toString());
                ventana.getTxtPersonajeFoto().setText("");
            }
            HibernateUtil.commitTx(session);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Error de formato");
            HibernateUtil.rollbackTx(session);
            ventana.getTxtPersonajeID().grabFocus();
        } catch (Exception ex) {
            HibernateUtil.rollbackTx(session);
            Logger.getLogger(ControladorUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void limpiarDatos() {
        ventana.getTxtPersonajeNombre().setText("");
        ventana.getTxtPersonajeFuerza().setText("");
        ventana.getTxtPersonajeDestreza().setText("");
        ventana.getTxtPersonajeConstitucion().setText("");
        ventana.getTxtPersonajeInteligencia().setText("");
        ventana.getTxtPersonajeSabiduria().setText("");
        ventana.getTxtPersonajeCarisma().setText("");
        ventana.getTxtPersonajeCompetencia().setText("");
        ventana.getTxtPersonajeFoto().setText("");
    }

    private static boolean comprobarID(String id) {
        return !id.isEmpty();
    }

    private static boolean comprobarNombre(String nombre) {
        return !nombre.isEmpty();
    }

    private static boolean comprobarCaracteristica(int caracteristica) {
        // Número entre 1 y 30 incluidos
        return caracteristica >= 1 && caracteristica <= 30;
    }

    private static boolean comprobarCompetencia(int competencia) {
        // Número entre 2 y 6 incluidos
        return competencia >= 2 && competencia <= 6;
    }

    private static int safeParseInt(String text, String fieldName) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, fieldName + " debe ser un número válido.");
            throw e; // Rethrow the exception to ensure the data checking stops.
        }
    }

    private static boolean comprobarDatos(String nombre, String fuerzaStr, String destrezaStr, String constitucionStr, String inteligenciaStr, String sabiduriaStr, String carismaStr, String competenciaStr) {
        if (!comprobarNombre(nombre)) {
            JOptionPane.showMessageDialog(null, "Nombre incorrecto, tiene que ser un correo");
            return false;
        }

        int fuerza, destreza, constitucion, inteligencia, sabiduria, carisma, competencia;
        try {
            fuerza = safeParseInt(fuerzaStr, "Fuerza");
            destreza = safeParseInt(destrezaStr, "Destreza");
            constitucion = safeParseInt(constitucionStr, "Constitución");
            inteligencia = safeParseInt(inteligenciaStr, "Inteligencia");
            sabiduria = safeParseInt(sabiduriaStr, "Sabiduría");
            carisma = safeParseInt(carismaStr, "Carisma");
            competencia = safeParseInt(competenciaStr, "Competencia");
        } catch (NumberFormatException e) {
            return false; // If any parsing fails, stop further checks.
        }

        if (!comprobarCaracteristica(fuerza)) {
            JOptionPane.showMessageDialog(null, "Fuerza incorrecta, tiene que ser un valor entre 1 y 30 (incluidos)");
            return false;
        }
        if (!comprobarCaracteristica(destreza)) {
            JOptionPane.showMessageDialog(null, "Destreza incorrecta, tiene que ser un valor entre 1 y 30 (incluidos)");
            return false;
        }
        if (!comprobarCaracteristica(constitucion)) {
            JOptionPane.showMessageDialog(null, "Constitución incorrecta, tiene que ser un valor entre 1 y 30 (incluidos)");
            return false;
        }
        if (!comprobarCaracteristica(inteligencia)) {
            JOptionPane.showMessageDialog(null, "Inteligencia incorrecta, tiene que ser un valor entre 1 y 30 (incluidos)");
            return false;
        }
        if (!comprobarCaracteristica(sabiduria)) {
            JOptionPane.showMessageDialog(null, "Sabiduría incorrecta, tiene que ser un valor entre 1 y 30 (incluidos)");
            return false;
        }
        if (!comprobarCaracteristica(carisma)) {
            JOptionPane.showMessageDialog(null, "Carisma incorrecto, tiene que ser un valor entre 1 y 30 (incluidos)");
            return false;
        }
        if (!comprobarCompetencia(competencia)) {
            JOptionPane.showMessageDialog(null, "Bono de competencia incorrecto, tiene que ser un valor entre 2 y 6 (incluidos)");
            return false;
        }
        return true;
    }

}
