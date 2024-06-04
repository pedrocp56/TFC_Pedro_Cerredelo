/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.dao;

import java.util.Iterator;
import java.util.List;
import modelo.vo.Arma_Personaje;
import modelo.vo.Personaje;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 *
 * @author pedro
 */
public class PersonajeDAO {

    public Personaje buscarPersonaje(Session session, int personajeID) throws Exception {
        Personaje p = null;
        Query q = session.createQuery("from Personaje u where u.ID=:personajeID");
        q.setParameter("personajeID", personajeID);
        p = (Personaje) q.uniqueResult();
        return p;
    }

    public void insertarPersonaje(Session session, Personaje u) {
        session.save(u);
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
        session.delete(p);
    }

    public void borrarArmaPersonaje(Session session, Personaje p) {

        String consulta = "Select * from Arma_Personaje a where a.Personaje_ID=:Personaje_ID";
        Query q = session.createQuery(consulta);
        q.setParameter("Personaje_ID", p.getPersonajePK().getPersonajeId());

        Iterator it = q.list().iterator();
        Arma_Personaje ap = null;
        while (it.hasNext()) {
            ap = (Arma_Personaje) it.next();
            session.delete(ap);
        }
    }
}
