/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.dao;

import java.util.Iterator;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;
import modelo.vo.Personaje;
import modelo.vo.Usuario;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 *
 * @author pedro
 */
public class UsuarioDAO {

    public void cargarCb(Session session, DefaultComboBoxModel modelcb) {
        Usuario u;
        String consulta = "Select u from Empleado u";
        Query q = session.createQuery(consulta);
        Iterator it = q.list().iterator();
        while (it.hasNext()) {
            modelcb.addElement(it.next());
        }
    }

    public void cargarTb(Session session, DefaultTableModel modelotb) {
        modelotb.setRowCount(0);

        Usuario u;
        String consulta = "Select u from Empleado u";
        Query q = session.createQuery(consulta);
        Iterator it = q.list().iterator();

        while (it.hasNext()) {
            modelotb.setRowCount(modelotb.getRowCount() + 1);
            u = (Usuario) it.next();
            modelotb.setValueAt(u.getId(), modelotb.getRowCount() - 1, 0);
            modelotb.setValueAt(u.getNombre(), modelotb.getRowCount() - 1, 1);
            modelotb.setValueAt(u.getContrasenha(), modelotb.getRowCount() - 1, 2);
            modelotb.setValueAt(u.getEstado(), modelotb.getRowCount() - 1, 3);
            modelotb.setValueAt(u.getFoto(), modelotb.getRowCount() - 1, 4);
            modelotb.setValueAt(u.getPersonajeList().size(), modelotb.getRowCount() - 1, 5);
        }
    }

    public Usuario buscarUsuario(Session session, int usuarioID) throws Exception {
        Usuario u = null;
        Query q = session.createQuery("from Usuario u where u.ID=:usuarioID");
        q.setParameter("usuarioID", usuarioID);
        u = (Usuario) q.uniqueResult();
        return u;
    }

    public void insertarUsaurio(Session session, Usuario u) {
        session.save(u);
    }

    public void modificarUsuario(Session session, Usuario u, String contraseha, String estado, String tel) {
        if (!contraseha.isEmpty()) {
            u.setContrasenha(contraseha);
        }
        u.setEstado(estado);
        //u.setFoto(tel);
        session.update(u);
    }

    public void borrarUsuario(Session session, Usuario u) {
        session.delete(u);
    }

    public void borrarPersonajes(Session session, List<Personaje> personajeList) {
        for (Personaje p : personajeList) {
            session.delete(p);
        }
    }

}
