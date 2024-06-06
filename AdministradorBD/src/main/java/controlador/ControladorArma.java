/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import controlador.factory.HibernateUtil;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
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
import org.hibernate.Session;
import vista.Start;
import vista.VistaArma;

/**
 *
 * @author pedro
 */
public class ControladorArma {

    public static Session session;

    public static VistaArma ventana = new VistaArma();
    public static ArmaDAO armaDAO;
    public static DefaultComboBoxModel modelcbTipo = new DefaultComboBoxModel();
    public static DefaultComboBoxModel modelocbCar = new DefaultComboBoxModel();
    public static DefaultTableModel modelotbArma = new DefaultTableModel();

    public static void iniciar() {
        ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ventana.setVisible(true);
        ventana.setLocationRelativeTo(null);
        ventana.getCbArmaTipo().setModel(modelcbTipo);
        ventana.getCbArmaCar().setModel(modelocbCar);
        modelotbArma = (DefaultTableModel) ventana.getTbArma().getModel();
    }

    public static void iniciarSession() {
        session = HibernateUtil.getSessionFactory().openSession();
        armaDAO = HibernateUtil.getArmaDAO();
    }

    public static void cerrarSession() {
        session.close();
    }

    public static void cargarCb() {
        modelcbTipo.removeAllElements();
        List<String> tipos = Arrays.asList("Contundente", "Cortante", "Frio", "Fuego", "Fuerza", "Necrotico", "Perforante", "Psiquico", "Radiante", "Relampago", "Trueno", "Veneno");
        for (String tipo : tipos) {
            modelcbTipo.addElement(tipo);
        }
        modelocbCar.removeAllElements();
        List<String> caracteristicas = Arrays.asList("Fuerza", "Destreza", "Constitucion", "Inteligencia", "Sabiduria", "Carisma");
        for (String caracteristica : caracteristicas) {
            modelocbCar.addElement(caracteristica);
        }
    }

    public static void mostrarArmas() {
        try {
            //java.lang.IllegalStateException: Session/EntityManager is closed
            HibernateUtil.beginTx(session);
            armaDAO.cargarTb(session, modelotbArma);
            HibernateUtil.commitTx(session);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Error de formato");
            HibernateUtil.rollbackTx(session);
        } catch (Exception ex) {
            HibernateUtil.rollbackTx(session);
            Logger.getLogger(ControladorArma.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void guardarArma() {
        System.out.println((String) modelcbTipo.getSelectedItem());
        System.out.println(modelcbTipo.getSelectedItem().toString());
        System.out.println((String) ventana.getCbArmaTipo().getSelectedItem());
        System.out.println(ventana.getCbArmaTipo().getSelectedItem().toString());

        if (!comprobarDatos(ventana.getTxtArmaNombre().getText(), ventana.getTxtArmaAtaque().getText(), ventana.getTxtArmaDaño().getText())) {
            return;
        }
        try {
            HibernateUtil.beginTx(session);
            Arma aNombre = armaDAO.buscarArmaPorNombre(session, ventana.getTxtArmaNombre().getText());
            Arma a = null;
            if (comprobarID(ventana.getTxtArmaID().getText())) {
                a = armaDAO.buscarArma(session, Integer.parseInt(ventana.getTxtArmaID().getText()));
            }
            if (a == null && aNombre == null) {
                Arma aNuevo = new Arma(null, ventana.getTxtArmaNombre().getText(), Integer.valueOf(ventana.getTxtArmaAtaque().getText()),
                        ventana.getTxtArmaDaño().getText(), (String) ventana.getCbArmaTipo().getSelectedItem(),
                        ventana.getCheckArmaArrojadiza().isSelected(), (String) ventana.getCbArmaCar().getSelectedItem(),
                        ventana.getTxtArmaCaracteristicas().getText(), null);
                armaDAO.insertarArma(session, aNuevo);
                JOptionPane.showMessageDialog(null, "Arma añadida");
                limpiarDatos();
            } else if (a != null && (aNombre == null || aNombre.getId() == a.getId())) {
                armaDAO.modificarArma(session, a, ventana.getTxtArmaNombre().getText(), Integer.parseInt(ventana.getTxtArmaAtaque().getText()),
                        ventana.getTxtArmaDaño().getText(), (String) ventana.getCbArmaTipo().getSelectedItem(),
                        ventana.getCheckArmaArrojadiza().isSelected(), (String) ventana.getCbArmaCar().getSelectedItem(),
                        ventana.getTxtArmaCaracteristicas().getText(), null);
                JOptionPane.showMessageDialog(null, "Arma modificada");
            } else if (aNombre != null) {
                JOptionPane.showMessageDialog(null, "Ya existe el arma " + aNombre.getId().toString() + " con ese nombre");
            }
            //cambiar a hibernate Util
            HibernateUtil.commitTx(session);
            //HibernateUtil.commitTx(session);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Error de formato");
            HibernateUtil.rollbackTx(session);
        } catch (Exception ex) {
            HibernateUtil.rollbackTx(session);
            Logger.getLogger(ControladorArma.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void eliminarArma() {
        if (!comprobarID(ventana.getTxtArmaID().getText())) {
            return;
        }
        try {
            HibernateUtil.beginTx(session);
            int id = Integer.parseInt(ventana.getTxtArmaID().getText());
            Arma a = armaDAO.buscarArma(session, id);
            if (a == null) {
                JOptionPane.showMessageDialog(null, "No existe ningun Arma con ese id");
            } else {
                armaDAO.borrarArma(session, a);
                JOptionPane.showMessageDialog(null, "Arma borrada");
                limpiarDatos();
            }
            HibernateUtil.commitTx(session);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Error de formato");
            HibernateUtil.rollbackTx(session);
            ventana.getTxtArmaID().grabFocus();
        } catch (Exception ex) {
            HibernateUtil.rollbackTx(session);
            Logger.getLogger(ControladorArma.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void cargarDatos() {
        if (!comprobarID(ventana.getTxtArmaID().getText())) {
            return;
        }
        try {
            HibernateUtil.beginTx(session);
            int id = Integer.parseInt(ventana.getTxtArmaID().getText());
            Arma a = armaDAO.buscarArma(session, id);
            if (a == null) {
                limpiarDatos();
            } else {
                ventana.getTxtArmaNombre().setText(a.getNombre());
                ventana.getTxtArmaAtaque().setText(a.getAtaque().toString());
                ventana.getTxtArmaDaño().setText(a.getDanho());
                //modelcbTipo.setSelectedItem(a);
                ventana.getCbArmaTipo().setSelectedItem(a.getTipo());
                ventana.getCheckArmaArrojadiza().setSelected(a.getArrojadiza());
                ventana.getCbArmaCar().setSelectedItem(a.getCar());
                ventana.getTxtArmaCaracteristicas().setText(a.getCaracteristicas());
                ventana.getTxtArmaFoto().setText("");
            }
            HibernateUtil.commitTx(session);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Error de formato");
            HibernateUtil.rollbackTx(session);
            ventana.getTxtArmaID().grabFocus();
        } catch (Exception ex) {
            HibernateUtil.rollbackTx(session);
            Logger.getLogger(ControladorUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void limpiarDatos() {
        ventana.getTxtArmaNombre().setText("");
        ventana.getTxtArmaAtaque().setText("");
        ventana.getTxtArmaDaño().setText("");
        ventana.getTxtArmaCaracteristicas().setText("");
        ventana.getTxtArmaFoto().setText("");
    }

    private static boolean comprobarID(String id) {
        return !id.isEmpty();
    }

    private static boolean comprobarVacio(String txt) {
        return !txt.isEmpty();
    }

    private static int safeParseInt(String text, String fieldName) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, fieldName + " debe ser un número válido.");
            throw e;
        }
    }

    private static boolean comprobarDatos(String nombre, String ataqueStr, String daño) {
        if (!comprobarVacio(nombre)) {
            JOptionPane.showMessageDialog(null, "Nombre incorrecto, tiene que contener algo");
            return false;
        }
        int ataque;
        try {
            ataque = safeParseInt(ataqueStr, "Ataque");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Ataque incorrecto, tiene que ser un numero");
            return false;
        }
        if (!comprobarVacio(daño)) {
            JOptionPane.showMessageDialog(null, "Daño incorrecto, tiene que contener algo");
            return false;
        }
        return true;
    }

}
