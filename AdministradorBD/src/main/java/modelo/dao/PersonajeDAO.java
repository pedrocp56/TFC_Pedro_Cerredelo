/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.dao;

import java.util.Iterator;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;
import modelo.vo.Arma_Personaje;
import modelo.vo.Personaje;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 *
 * @author pedro
 */
public class PersonajeDAO {

    public void cargarCb(Session session, DefaultComboBoxModel modelcb) {
        modelcb.removeAllElements();
        Personaje p;
        String consulta = "from Personaje p";
        Query q = session.createQuery(consulta);
        Iterator it = q.list().iterator();
        while (it.hasNext()) {
            modelcb.addElement(it.next());
        }
    }

    public void cargarTb(Session session, DefaultTableModel modelotb) {
        modelotb.setRowCount(0);

        Personaje p;
        String consulta = "from Personaje p";
        Query q = session.createQuery(consulta);
        Iterator it = q.list().iterator();

        while (it.hasNext()) {
            modelotb.setRowCount(modelotb.getRowCount() + 1);
            p = (Personaje) it.next();
            modelotb.setValueAt(p.getPersonajePK().getPersonajeID(), modelotb.getRowCount() - 1, 0);
            modelotb.setValueAt(p.getUsuario().getNombre(), modelotb.getRowCount() - 1, 1);
            modelotb.setValueAt(p.getPersonajeNombre(), modelotb.getRowCount() - 1, 2);
            modelotb.setValueAt(p.getCaracteristicaFuerza() + "(" + calcularBono(p.getCaracteristicaFuerza()) + ")", modelotb.getRowCount() - 1, 3);
            modelotb.setValueAt(p.getCaracteristicaDestreza() + "(" + calcularBono(p.getCaracteristicaDestreza()) + ")", modelotb.getRowCount() - 1, 4);
            modelotb.setValueAt(p.getCaracteristicaConstitucion() + "(" + calcularBono(p.getCaracteristicaConstitucion()) + ")", modelotb.getRowCount() - 1, 5);
            modelotb.setValueAt(p.getCaracteristicaInteligencia() + "(" + calcularBono(p.getCaracteristicaInteligencia()) + ")", modelotb.getRowCount() - 1, 6);
            modelotb.setValueAt(p.getCaracteristicaSabiduria() + "(" + calcularBono(p.getCaracteristicaSabiduria()) + ")", modelotb.getRowCount() - 1, 7);
            modelotb.setValueAt(p.getCaracteristicaCarisma() + "(" + calcularBono(p.getCaracteristicaCarisma()) + ")", modelotb.getRowCount() - 1, 8);
            modelotb.setValueAt(p.getBonoCompetencia(), modelotb.getRowCount() - 1, 9);
            //modelotb.setValueAt(p.getPersonajeList().size(), modelotb.getRowCount() - 1, 11);
        }
    }

    public Personaje buscarPersonaje(Session session, int personajeID) throws Exception {
        Personaje p = null;
        Query q = session.createQuery("from Personaje p where p.personajePK.personajeID=:personajeID");
        q.setParameter("personajeID", personajeID);
        p = (Personaje) q.uniqueResult();
        return p;
    }

    public void insertarPersonaje(Session session, Personaje p) {
        session.save(p);
    }

    public void modificarPersonaje(Session session, Personaje p, String nombre, int fuerza, int destreza, int constitucion, int inteligencia, int sabiduria, int carisma, int competencia, String foto) {
        p.setPersonajeNombre(nombre);
        p.setCaracteristicaFuerza(fuerza);
        p.setCaracteristicaDestreza(destreza);
        p.setCaracteristicaConstitucion(constitucion);
        p.setCaracteristicaInteligencia(inteligencia);
        p.setCaracteristicaSabiduria(sabiduria);
        p.setCaracteristicaCarisma(carisma);
        p.setBonoCompetencia(competencia);
        //p.setFoto(tel);
        session.update(p);
    }

    public void borrarPersonaje(Session session, Personaje p) {
        //borrado en cascada
        session.delete(p);
    }

    public int calcularBono(int caracteristica) {
        return Math.floorDiv(caracteristica - 10, 2);
    }
}
