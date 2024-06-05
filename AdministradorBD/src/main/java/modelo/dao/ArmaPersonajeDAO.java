/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.dao;

import java.util.Iterator;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;
import modelo.vo.Arma_Personaje;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 *
 * @author pedro
 */
public class ArmaPersonajeDAO {

    public void cargarCb(Session session, DefaultComboBoxModel modelcb) {
        Arma_Personaje a;
        String consulta = "from Arma_Personaje a";
        Query q = session.createQuery(consulta);
        Iterator it = q.list().iterator();
        while (it.hasNext()) {
            modelcb.addElement(it.next());
        }
    }

    public void cargarTb(Session session, DefaultTableModel modelotb) {
        modelotb.setRowCount(0);

        Arma_Personaje a;
        String consulta = "from Arma_Personaje a";
        Query q = session.createQuery(consulta);
        Iterator it = q.list().iterator();

        while (it.hasNext()) {
            modelotb.setRowCount(modelotb.getRowCount() + 1);
            a = (Arma_Personaje) it.next();
            modelotb.setValueAt(a.getPersonaje().getPersonajeNombre(), modelotb.getRowCount() - 1, 0);
            modelotb.setValueAt(a.getArma().getNombre(), modelotb.getRowCount() - 1, 1);
            modelotb.setValueAt(a.getAtaqueTotal(), modelotb.getRowCount() - 1, 2);
            modelotb.setValueAt(a.getBonificaciónAdicional(), modelotb.getRowCount() - 1, 3);
            modelotb.setValueAt(a.getCompetencia(), modelotb.getRowCount() - 1, 4);
        }
    }

    public Arma_Personaje buscarArmaPersonaje(Session session, int personajeID, int armaID) throws Exception {
        Arma_Personaje a = null;
        Query q = session.createQuery("from Arma_Personaje a where"
                + " a.armaPersonajePK.personajeID=:personajeID "
                + "and a.armaPersonajePK.armaID=:armaID");
        q.setParameter("personajeID", personajeID);
        q.setParameter("armaID", armaID);
        a = (Arma_Personaje) q.uniqueResult();
        return a;
    }

    public void insertarArmaPersonaje(Session session, Arma_Personaje a) {
        session.save(a);
    }

    public void modificarArmaPersonaje(Session session, Arma_Personaje a, int ataque, int bono, boolean competencia) {
        a.setAtaqueTotal(ataque);
        a.setBonificaciónAdicional(bono);
        a.setCompetencia(competencia);
        session.update(a);
    }

    public void borrarArmaPersonaje(Session session, Arma_Personaje a) {
        session.delete(a);
    }

}
