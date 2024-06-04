/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.vo;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author pedro
 */
@Embeddable
public class PersonajePK implements Serializable {

    @Basic(optional = false)
    @Column(name = "Usuario_ID")
    private Integer usuarioID;

    @Basic(optional = false)
    @Column(name = "Personaje_ID")
    private Integer personajeId;

    // Constructor, getters y setters
    public Integer getUsuarioID() {
        return usuarioID;
    }

    public void setUsuarioID(Integer usuarioID) {
        this.usuarioID = usuarioID;
    }

    public Integer getPersonajeId() {
        return personajeId;
    }

    public void setPersonajeId(Integer personajeId) {
        this.personajeId = personajeId;
    }

    public PersonajePK(Integer usuarioID, Integer personajeId) {
        this.usuarioID = usuarioID;
        this.personajeId = personajeId;
    }

    public PersonajePK() {
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) usuarioID;
        hash += (int) personajeId;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PersonajePK other = (PersonajePK) obj;
        if (!Objects.equals(this.usuarioID, other.usuarioID)) {
            return false;
        }
        return Objects.equals(this.personajeId, other.personajeId);
    }

    @Override
    public String toString() {
        return "PersonajePK{" + "usuarioID=" + usuarioID + ", personajeID=" + personajeId + '}';
    }

}
