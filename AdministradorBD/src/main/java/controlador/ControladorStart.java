/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import Aux.Variables;
import controlador.factory.HibernateUtil;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.dao.ArmaDAO;
import modelo.dao.ArmaPersonajeDAO;
import modelo.dao.PersonajeDAO;
import modelo.dao.UsuarioDAO;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.hibernate.Session;
import vista.Start;

/**
 *
 * @author pedro
 */
public class ControladorStart {

    public static Session session;
    public static Variables v = new Variables();
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

    public static void generarReporteArmas() throws ClassNotFoundException, InstantiationException {
        String baseDatos = "jdbc:mysql://" + v.IP + v.nombreBD;
        String usuario = v.usuario;
        String clave = v.contraseña;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            Connection conexion = DriverManager.getConnection(baseDatos, usuario, clave);
            String archivoJasper = v.rutaJasperArma;
            Map parametros = new HashMap();
            JasperPrint print = JasperFillManager.fillReport(archivoJasper, parametros, conexion);
            JasperExportManager.exportReportToPdfFile(print, v.rutaReportesArma);
            JOptionPane.showMessageDialog(null, "Informe generado");

        } catch (IllegalAccessException ex) {
            JOptionPane.showMessageDialog(null, ex);
            Logger.getLogger(ControladorStart.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
            Logger.getLogger(ControladorStart.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JRException ex) {
            JOptionPane.showMessageDialog(null, ex);
            Logger.getLogger(ControladorStart.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void generarReporteUsuarios() throws ClassNotFoundException, InstantiationException {
        String baseDatos = "jdbc:mysql://" + v.IP + v.nombreBD;
        String usuario = v.usuario;
        String clave = v.contraseña;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            Connection conexion = DriverManager.getConnection(baseDatos, usuario, clave);
            String archivoJasper = v.rutaJasperUsuario;
            Map parametros = new HashMap();
            JasperPrint print = JasperFillManager.fillReport(archivoJasper, parametros, conexion);
            JasperExportManager.exportReportToPdfFile(print, v.rutaReportesUsuario);
            JOptionPane.showMessageDialog(null, "Informe generado");

        } catch (IllegalAccessException ex) {
            JOptionPane.showMessageDialog(null, ex);
            Logger.getLogger(ControladorStart.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
            Logger.getLogger(ControladorStart.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JRException ex) {
            JOptionPane.showMessageDialog(null, ex);
            Logger.getLogger(ControladorStart.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
