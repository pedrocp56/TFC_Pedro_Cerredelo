/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.vo;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author pedro
 */

@Embeddable
public class PersonajePK implements Serializable {

    @Column(name = "Usuario_ID")
    private Integer usuarioID;

    @Column(name = "Campanha_ID")
    private Integer campanhaID;

    // Constructor, getters y setters

    public Integer getUsuarioID() {
        return usuarioID;
    }

    public void setUsuarioID(Integer usuarioID) {
        this.usuarioID = usuarioID;
    }

    public Integer getCampanhaID() {
        return campanhaID;
    }

    public void setCampanhaID(Integer campanhaID) {
        this.campanhaID = campanhaID;
    }

    public PersonajePK(Integer usuarioID, Integer campanhaID) {
        this.usuarioID = usuarioID;
        this.campanhaID = campanhaID;
    }

    public PersonajePK() {
    }

    @Override
    public int hashCode() {
        int hash = 3;
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
        return Objects.equals(this.campanhaID, other.campanhaID);
    }

    @Override
    public String toString() {
        return "PersonajePK{" + "usuarioID=" + usuarioID + ", campa\u00f1aID=" + campanhaID + '}';
    }
    
    
}
