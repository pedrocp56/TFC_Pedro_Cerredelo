/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.vo;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author pedro
 */
@Entity
@Table(name = "Arma_Personaje")
public class Arma_Personaje implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "Arma_ID")
    private Integer armaID;

    @Id
    @Basic(optional = false)
    @Column(name = "Usuario_ID")
    private Integer usuarioID;

    @Id
    @Basic(optional = false)
    @Column(name = "Campanha_ID")
    private Integer campanhaID;

    @Column(name = "Ataque_Total")
    private Integer ataqueTotal;

    @Column(name = "Bonificación_Adicional")
    private Integer bonificaciónAdicional;

    @Column(name = "Competencia")
    private Boolean competencia;

    @JoinColumn(name = "Arma_ID", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Arma arma;

    @JoinColumn(name = "Usuario_ID", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Usuario usuario;

    @JoinColumn(name = "Campanha_ID", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Campanha campanha;

    // Getters y setters

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

    public Integer getCampanhaID() {
        return campanhaID;
    }

    public void setCampanhaID(Integer campanhaID) {
        this.campanhaID = campanhaID;
    }

    public Integer getAtaqueTotal() {
        return ataqueTotal;
    }

    public void setAtaqueTotal(Integer ataqueTotal) {
        this.ataqueTotal = ataqueTotal;
    }

    public Integer getBonificaciónAdicional() {
        return bonificaciónAdicional;
    }

    public void setBonificaciónAdicional(Integer bonificaciónAdicional) {
        this.bonificaciónAdicional = bonificaciónAdicional;
    }

    public Boolean getCompetencia() {
        return competencia;
    }

    public void setCompetencia(Boolean competencia) {
        this.competencia = competencia;
    }

    public Arma getArma() {
        return arma;
    }

    public void setArma(Arma arma) {
        this.arma = arma;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Campanha getCampanha() {
        return campanha;
    }

    public void setCampanha(Campanha campanha) {
        this.campanha = campanha;
    }

    @Override
    public int hashCode() {
        int hash = 5;
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
        final Arma_Personaje other = (Arma_Personaje) obj;
        if (!Objects.equals(this.armaID, other.armaID)) {
            return false;
        }
        if (!Objects.equals(this.usuarioID, other.usuarioID)) {
            return false;
        }
        return Objects.equals(this.campanhaID, other.campanhaID);
    }

    @Override
    public String toString() {
        return "ArmaPersonaje{" + "armaID=" + armaID + ", usuarioID=" + usuarioID + ", campa\u00f1aID=" + campanhaID + ", ataqueTotal=" + ataqueTotal + ", bonificaci\u00f3nAdicional=" + bonificaciónAdicional + ", competencia=" + competencia + ", arma=" + arma + ", usuario=" + usuario + ", campa\u00f1a=" + campanha + '}';
    }

    public Arma_Personaje(Integer armaID, Integer usuarioID, Integer campanhaID, Integer ataqueTotal, Integer bonificaciónAdicional, Boolean competencia, Arma arma, Usuario usuario, Campanha campanha) {
        this.armaID = armaID;
        this.usuarioID = usuarioID;
        this.campanhaID = campanhaID;
        this.ataqueTotal = ataqueTotal;
        this.bonificaciónAdicional = bonificaciónAdicional;
        this.competencia = competencia;
        this.arma = arma;
        this.usuario = usuario;
        this.campanha = campanha;
    }
    
    
}
