/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.dao;

import java.util.Iterator;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;
import modelo.vo.Arma;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 *
 * @author pedro
 */
public class ArmaDAO {

    public void cargarCb(Session session, DefaultComboBoxModel modelcb) {
        modelcb.removeAllElements();
        Arma a;
        String consulta = "from Arma a";
        Query q = session.createQuery(consulta);
        Iterator it = q.list().iterator();
        while (it.hasNext()) {
            modelcb.addElement(it.next());
        }
    }

    public void cargarTb(Session session, DefaultTableModel modelotb) {
        modelotb.setRowCount(0);

        Arma a;
        String consulta = "from Arma a";
        Query q = session.createQuery(consulta);
        Iterator it = q.list().iterator();

        while (it.hasNext()) {
            modelotb.setRowCount(modelotb.getRowCount() + 1);
            a = (Arma) it.next();
            modelotb.setValueAt(a.getId(), modelotb.getRowCount() - 1, 0);
            modelotb.setValueAt(a.getNombre(), modelotb.getRowCount() - 1, 1);
            modelotb.setValueAt(a.getAtaque(), modelotb.getRowCount() - 1, 2);
            modelotb.setValueAt(a.getDanho(), modelotb.getRowCount() - 1, 3);
            modelotb.setValueAt(a.getTipo(), modelotb.getRowCount() - 1, 4);
            modelotb.setValueAt(a.getArrojadiza(), modelotb.getRowCount() - 1, 5);
            modelotb.setValueAt(a.getCar(), modelotb.getRowCount() - 1, 6);
        }
    }

    public Arma buscarArma(Session session, int armaID) throws Exception {
        Arma a = null;
        Query q = session.createQuery("from Arma a where a.ID=:armaID");
        q.setParameter("armaID", armaID);
        a = (Arma) q.uniqueResult();
        return a;
    }

    public Arma buscarArmaPorNombre(Session session, String nombre) throws Exception {
        Arma a = null;
        Query q = session.createQuery("from Arma a where a.nombre=:nombre");
        q.setParameter("nombre", nombre);
        a = (Arma) q.uniqueResult();
        return a;
    }

    public void insertarArma(Session session, Arma a) {
        session.save(a);
    }

    public void modificarArma(Session session, Arma a, String nombre, int ataque, String danho, String tipo, boolean arrojadiza, String car, String caracteristicas, byte[] foto) {
        a.setNombre(nombre);
        a.setAtaque(ataque);
        a.setDanho(danho);
        a.setTipo(tipo);
        a.setArrojadiza(arrojadiza);
        a.setCar(car);
        a.setCaracteristicas(caracteristicas);
        //a.setFoto(foto);
        session.update(a);
    }

    public void borrarArma(Session session, Arma a) {
        //borrado en cascada
        session.delete(a);
    }

}
