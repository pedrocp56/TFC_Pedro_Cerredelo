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
import modelo.dao.ArmaDAO;
import modelo.dao.ArmaPersonajeDAO;
import modelo.dao.PersonajeDAO;
import modelo.dao.UsuarioDAO;
import modelo.vo.Arma;
import modelo.vo.Arma_Personaje;
import modelo.vo.Arma_PersonajePK;
import modelo.vo.Personaje;
import org.hibernate.Session;
import vista.VistaArmaPersonaje;

/**
 *
 * @author pedro
 */
public class ControladorArmaPersonaje {

    public static Session session;

    public static VistaArmaPersonaje ventana = new VistaArmaPersonaje();
    public static ArmaDAO armaDAO;
    public static ArmaPersonajeDAO armaPerDAO;
    public static PersonajeDAO perDAO;
    public static UsuarioDAO userDAO;
    public static DefaultComboBoxModel modelcbPersonaje = new DefaultComboBoxModel();
    public static DefaultComboBoxModel modelcbArma = new DefaultComboBoxModel();
    public static DefaultTableModel modelotbArmaPersonaje = new DefaultTableModel();

    public static void iniciar() {
        ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ventana.setVisible(true);
        ventana.setLocationRelativeTo(null);
        ventana.getCbArmaPerPersonaje().setModel(modelcbPersonaje);
        ventana.getCbArmaPerArma().setModel(modelcbArma);
        modelotbArmaPersonaje = (DefaultTableModel) ventana.getTbArmaPersonaje().getModel();
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

    public static void guardarArmaPersonaje() {
        if (!comprobarBono(ventana.getTxtArmaPerBonificador().getText())) {
            return;
        }
        try {
            Personaje p = (Personaje) ventana.getCbArmaPerPersonaje().getSelectedItem();
            Arma a = (Arma) ventana.getCbArmaPerArma().getSelectedItem();
            int personajeID = p.getPersonajePK().getPersonajeID();
            int armaID = a.getId();
            Arma_Personaje ap = null;
            HibernateUtil.beginTx(session);
            ap = armaPerDAO.buscarArmaPersonaje(session, personajeID, armaID);
            if (ap == null) {
                int ataque = calcularAtaque(p, a, Integer.parseInt(ventana.getTxtArmaPerBonificador().getText()), ventana.getCbCompetencia().isSelected());
                Arma_Personaje apNueva = new Arma_Personaje(new Arma_PersonajePK(a.getId(), p.getPersonajePK().getUsuarioID(), p.getPersonajePK().getPersonajeID()),
                        ataque, Integer.valueOf(ventana.getTxtArmaPerBonificador().getText()), ventana.getCbCompetencia().isSelected());
                apNueva.setArma(a);
                apNueva.setPersonaje(p);
                armaPerDAO.insertarArmaPersonaje(session, apNueva);
                JOptionPane.showMessageDialog(null, p.getPersonajeNombre() + " se equipa " + a.getNombre());
            } else {
                int ataque = calcularAtaque(p, a, Integer.parseInt(ventana.getTxtArmaPerBonificador().getText()), ventana.getCbCompetencia().isSelected());
                armaPerDAO.modificarArmaPersonaje(session, ap, ataque, Integer.parseInt(ventana.getTxtArmaPerBonificador().getText()), ventana.getCbCompetencia().isSelected());
                JOptionPane.showMessageDialog(null, p.getPersonajeNombre() + " cambia su " + a.getNombre());
            }
            HibernateUtil.commitTx(session);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Error de formato");
            HibernateUtil.rollbackTx(session);
        } catch (Exception ex) {
            HibernateUtil.rollbackTx(session);
            Logger.getLogger(ControladorArma.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void borrarArmaPersonaje() {
        try {
            Personaje p = (Personaje) ventana.getCbArmaPerPersonaje().getSelectedItem();
            Arma a = (Arma) ventana.getCbArmaPerArma().getSelectedItem();
            int personajeID = p.getPersonajePK().getPersonajeID();
            int armaID = a.getId();
            Arma_Personaje ap = null;
            HibernateUtil.beginTx(session);
            ap = armaPerDAO.buscarArmaPersonaje(session, personajeID, armaID);
            if (ap == null) {
                JOptionPane.showMessageDialog(null, p.getPersonajeNombre() + " no tiene " + a.getNombre());
            } else {
                armaPerDAO.borrarArmaPersonaje(session, ap);
                JOptionPane.showMessageDialog(null, p.getPersonajeNombre() + " se desequipa " + a.getNombre());
                limpiarDatos();
            }
            HibernateUtil.commitTx(session);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Error de formato");
            HibernateUtil.rollbackTx(session);
        } catch (Exception ex) {
            HibernateUtil.rollbackTx(session);
            Logger.getLogger(ControladorArma.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void cargarCb() {
        perDAO.cargarCb(session, modelcbPersonaje);
        armaDAO.cargarCb(session, modelcbArma);
    }

    public static void mostrarArmasPersonajes() {
        armaPerDAO.cargarTb(session, modelotbArmaPersonaje);
    }

    public static void cargarDatosArmaPersonaje() {
        try {
            Personaje p = (Personaje) ventana.getCbArmaPerPersonaje().getSelectedItem();
            Arma a = (Arma) ventana.getCbArmaPerArma().getSelectedItem();

            int personajeID = p.getPersonajePK().getPersonajeID();
            int armaID = a.getId();
            Arma_Personaje ap = null;
            HibernateUtil.beginTx(session);
            ap = armaPerDAO.buscarArmaPersonaje(session, personajeID, armaID);
            if (ap == null) {
                limpiarDatos();
            } else {
                ventana.getTxtAtaque().setText(ap.getAtaqueTotal().toString());
                ventana.getTxtArmaPerBonificador().setText(ap.getBonificaciónAdicional().toString());
                ventana.getCbCompetencia().setSelected(ap.getCompetencia());
            }
            HibernateUtil.commitTx(session);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Error de formato");
            HibernateUtil.rollbackTx(session);
        } catch (Exception ex) {
            HibernateUtil.rollbackTx(session);
            Logger
                    .getLogger(ControladorArmaPersonaje.class
                            .getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void limpiarDatos() {
        ventana.getTxtAtaque().setText("0");
        ventana.getTxtArmaPerBonificador().setText("0");
        ventana.getCbCompetencia().setSelected(false);
    }

    private static int safeParseInt(String text, String fieldName) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, fieldName + " debe ser un número válido.");
            throw e;
        }
    }

    private static boolean comprobarBono(String bonoStr) {
        try {
            int bono = safeParseInt(bonoStr, "Bono");
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    private static int calcularAtaque(Personaje p, Arma a, int bono, boolean competente) {
        int ataque = 0;
        ataque += a.getAtaque();
        ataque += bono;
        switch (a.getCar()) {
            case "Fuerza" ->
                ataque += calcularBono(p.getCaracteristicaFuerza());
            case "Destreza" ->
                ataque += calcularBono(p.getCaracteristicaDestreza());
            case "Constitucion" ->
                ataque += calcularBono(p.getCaracteristicaConstitucion());
            case "Inteligencia" ->
                ataque += calcularBono(p.getCaracteristicaInteligencia());
            case "Sabiduria" ->
                ataque += calcularBono(p.getCaracteristicaSabiduria());
            case "Carisma" ->
                ataque += calcularBono(p.getCaracteristicaCarisma());
            default -> {
            }
        }
        if (competente) {
            ataque += p.getBonoCompetencia();
        }
        return ataque;
    }

    public static int calcularBono(int caracteristica) {
        return Math.floorDiv(caracteristica - 10, 2);
    }
}
