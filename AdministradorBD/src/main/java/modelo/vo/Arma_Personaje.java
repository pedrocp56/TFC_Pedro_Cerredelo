/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.vo;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author pedro
 */
import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "Arma_Personaje")
public class Arma_Personaje implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    protected Arma_PersonajePK armaPersonajePK;

    @Column(name = "Ataque_Total")
    private Integer ataqueTotal;

    @Basic(optional = false)
    @Column(name = "Bonificacion_Adicional")
    private Integer bonificaciónAdicional;

    @Basic(optional = false)
    @Column(name = "Competencia")
    private Boolean competencia;

    @ManyToOne(optional = false)
    @JoinColumn(name = "Arma_ID", referencedColumnName = "ID", insertable = false, updatable = false)
    private Arma arma;

    @ManyToOne(optional = false)
    @JoinColumns({
        @JoinColumn(name = "Usuario_ID", referencedColumnName = "Usuario_ID", insertable = false, updatable = false),
        @JoinColumn(name = "Personaje_ID", referencedColumnName = "Personaje_ID", insertable = false, updatable = false)})
    private Personaje personaje;

    // Constructor, getters y setters
    public Arma_Personaje() {
    }

    public Arma_Personaje(Arma_PersonajePK armaPersonajePK, Integer ataqueTotal, Integer bonificaciónAdicional, Boolean competencia) {
        this.armaPersonajePK = armaPersonajePK;
        this.ataqueTotal = ataqueTotal;
        this.bonificaciónAdicional = bonificaciónAdicional;
        this.competencia = competencia;
    }

    @Override
    public String toString() {
        return "Arma_Personaje{" + "armaPersonajePK=" + armaPersonajePK + ", ataqueTotal=" + ataqueTotal + ", bonificaci\u00f3nAdicional=" + bonificaciónAdicional + ", competencia=" + competencia + ", arma=" + arma + ", personaje=" + personaje + '}';
    }

    public Arma_PersonajePK getArmaPersonajePK() {
        return armaPersonajePK;
    }

    public void setArmaPersonajePK(Arma_PersonajePK armaPersonajePK) {
        this.armaPersonajePK = armaPersonajePK;
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

    public Personaje getPersonaje() {
        return personaje;
    }

    public void setPersonaje(Personaje personaje) {
        this.personaje = personaje;
    }
}
