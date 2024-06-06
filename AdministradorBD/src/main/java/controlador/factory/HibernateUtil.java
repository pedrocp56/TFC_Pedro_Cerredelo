package controlador.factory;

import Aux.Variables;
import modelo.dao.ArmaDAO;
import modelo.dao.ArmaPersonajeDAO;
import modelo.dao.PersonajeDAO;
import modelo.dao.UsuarioDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static final SessionFactory sessionFactory;

    static {
        try {
            // Crear una instancia de Variables
            Variables variables = new Variables();

            // Configurar Hibernate programáticamente
            Configuration configuration = new Configuration();
            configuration.configure(); // Cargar configuración desde hibernate.cfg.xml

            // Sobrescribir propiedades con valores de Variables
            configuration.setProperty("hibernate.connection.url", "jdbc:mysql://" + variables.IP + variables.nombreBD + "?zeroDateTimeBehavior=CONVERT_TO_NULL");
            configuration.setProperty("hibernate.connection.username", variables.usuario);
            configuration.setProperty("hibernate.connection.password", variables.contraseña);
            configuration.setProperty("hibernate.default_schema", variables.nombreBD);

            sessionFactory = configuration.buildSessionFactory();

        } catch (Throwable ex) {
            System.err.println("Session Factory could not be created." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /**
     * *************** PARA GESTIONAR LAS TRANSACCIONES **************
     */
    public static Transaction beginTx(Session s) {
        if (s.getTransaction() == null || !s.getTransaction().isActive()) {
            return s.beginTransaction();
        }
        return sessionFactory.getCurrentSession().getTransaction();
    }

    public static void commitTx(Session s) {
        if (s.getTransaction().isActive()) {
            s.getTransaction().commit();
        }
    }

    public static void rollbackTx(Session s) {
        if (s.getTransaction().isActive()) {
            s.getTransaction().rollback();
        }
    }

    /**
     * ********************** INCORPORA LOS MÉTODOS PARA CREAR LOS OBJETOS DAO
     * *******
     */
    public static ArmaDAO getArmaDAO() {
        return new ArmaDAO();
    }

    public static ArmaPersonajeDAO getArmaPersonajeDAO() {
        return new ArmaPersonajeDAO();
    }

    public static PersonajeDAO getPersonajeDAO() {
        return new PersonajeDAO();
    }

    public static UsuarioDAO getUsuarioDAO() {
        return new UsuarioDAO();
    }

}
