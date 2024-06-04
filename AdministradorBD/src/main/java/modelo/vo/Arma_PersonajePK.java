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
public class Arma_PersonajePK implements Serializable {

    @Basic(optional = false)
    @Column(name = "Arma_ID")
    private Integer armaID;

    @Basic(optional = false)
    @Column(name = "Usuario_ID")
    private Integer usuarioID;

    @Basic(optional = false)
    @Column(name = "Personaje_ID")
    private Integer personajeId;

    // Constructor, getters y setters
    public Arma_PersonajePK() {
    }

    public Arma_PersonajePK(Integer armaID, Integer usuarioID, Integer personajeId) {
        this.armaID = armaID;
        this.usuarioID = usuarioID;
        this.personajeId = personajeId;
    }

    public Integer getArmaID() {
        return armaID;
    }

    public void setArmaID(Integer armaID) {
        this.armaID = armaID;
    }

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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) armaID;
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
        final Arma_PersonajePK other = (Arma_PersonajePK) obj;
        if (!Objects.equals(this.armaID, other.armaID)) {
            return false;
        }
        if (!Objects.equals(this.usuarioID, other.usuarioID)) {
            return false;
        }
        return Objects.equals(this.personajeId, other.personajeId);
    }

    @Override
    public String toString() {
        return "Arma_PersonajePK{" + "armaID=" + armaID + ", usuarioID=" + usuarioID + ", personajeId=" + personajeId + '}';
    }
}
