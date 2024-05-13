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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author pedro
 */

@Entity
@Table(name = "Personaje")
public class Personaje implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @EmbeddedId
    protected PersonajePK personajePK;
    
    @Basic(optional = false)
    @Column(name = "Personaje_Nombre")
    private String personajeNombre;
    
    @Column(name = "Caracteristica_Fuerza")
    private Integer caracteristicaFuerza;
    
    @Column(name = "Caracteristica_Destreza")
    private Integer caracteristicaDestreza;
    
    @Column(name = "Caracteristica_Constitución")
    private Integer caracteristicaConstitución;
    
    @Column(name = "Caracteristica_Inteligencia")
    private Integer caracteristicaInteligencia;
    
    @Column(name = "Caracteristica_Sabiduria")
    private Integer caracteristicaSabiduria;
    
    @Column(name = "Caracteristica_Carisma")
    private Integer caracteristicaCarisma;
    
    @Column(name = "Bono_Competencia")
    private Integer bonoCompetencia;
    
    @Column(name = "Foto")
    private String foto;
    
    @JoinColumn(name = "Usuario_ID", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Usuario usuario;
    
    @JoinColumn(name = "Campanha_ID", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Campanha campanha;

    // Getters y setters

    public PersonajePK getPersonajePK() {
        return personajePK;
    }

    public void setPersonajePK(PersonajePK personajePK) {
        this.personajePK = personajePK;
    }

    public String getPersonajeNombre() {
        return personajeNombre;
    }

    public void setPersonajeNombre(String personajeNombre) {
        this.personajeNombre = personajeNombre;
    }

    public Integer getCaracteristicaFuerza() {
        return caracteristicaFuerza;
    }

    public void setCaracteristicaFuerza(Integer caracteristicaFuerza) {
        this.caracteristicaFuerza = caracteristicaFuerza;
    }

    public Integer getCaracteristicaDestreza() {
        return caracteristicaDestreza;
    }

    public void setCaracteristicaDestreza(Integer caracteristicaDestreza) {
        this.caracteristicaDestreza = caracteristicaDestreza;
    }

    public Integer getCaracteristicaConstitución() {
        return caracteristicaConstitución;
    }

    public void setCaracteristicaConstitución(Integer caracteristicaConstitución) {
        this.caracteristicaConstitución = caracteristicaConstitución;
    }

    public Integer getCaracteristicaInteligencia() {
        return caracteristicaInteligencia;
    }

    public void setCaracteristicaInteligencia(Integer caracteristicaInteligencia) {
        this.caracteristicaInteligencia = caracteristicaInteligencia;
    }

    public Integer getCaracteristicaSabiduria() {
        return caracteristicaSabiduria;
    }

    public void setCaracteristicaSabiduria(Integer caracteristicaSabiduria) {
        this.caracteristicaSabiduria = caracteristicaSabiduria;
    }

    public Integer getCaracteristicaCarisma() {
        return caracteristicaCarisma;
    }

    public void setCaracteristicaCarisma(Integer caracteristicaCarisma) {
        this.caracteristicaCarisma = caracteristicaCarisma;
    }

    public Integer getBonoCompetencia() {
        return bonoCompetencia;
    }

    public void setBonoCompetencia(Integer bonoCompetencia) {
        this.bonoCompetencia = bonoCompetencia;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
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
        final Personaje other = (Personaje) obj;
        if (!Objects.equals(this.personajeNombre, other.personajeNombre)) {
            return false;
        }
        if (!Objects.equals(this.foto, other.foto)) {
            return false;
        }
        if (!Objects.equals(this.personajePK, other.personajePK)) {
            return false;
        }
        if (!Objects.equals(this.caracteristicaFuerza, other.caracteristicaFuerza)) {
            return false;
        }
        if (!Objects.equals(this.caracteristicaDestreza, other.caracteristicaDestreza)) {
            return false;
        }
        if (!Objects.equals(this.caracteristicaConstitución, other.caracteristicaConstitución)) {
            return false;
        }
        if (!Objects.equals(this.caracteristicaInteligencia, other.caracteristicaInteligencia)) {
            return false;
        }
        if (!Objects.equals(this.caracteristicaSabiduria, other.caracteristicaSabiduria)) {
            return false;
        }
        if (!Objects.equals(this.caracteristicaCarisma, other.caracteristicaCarisma)) {
            return false;
        }
        if (!Objects.equals(this.bonoCompetencia, other.bonoCompetencia)) {
            return false;
        }
        if (!Objects.equals(this.usuario, other.usuario)) {
            return false;
        }
        return Objects.equals(this.campanha, other.campanha);
    }

    @Override
    public String toString() {
        return "Personaje{" + "personajePK=" + personajePK + ", personajeNombre=" + personajeNombre + ", caracteristicaFuerza=" + caracteristicaFuerza + ", caracteristicaDestreza=" + caracteristicaDestreza + ", caracteristicaConstituci\u00f3n=" + caracteristicaConstitución + ", caracteristicaInteligencia=" + caracteristicaInteligencia + ", caracteristicaSabiduria=" + caracteristicaSabiduria + ", caracteristicaCarisma=" + caracteristicaCarisma + ", bonoCompetencia=" + bonoCompetencia + ", foto=" + foto + ", usuario=" + usuario + ", campa\u00f1a=" + campanha + '}';
    }

    public Personaje(PersonajePK personajePK, String personajeNombre, Integer caracteristicaFuerza, Integer caracteristicaDestreza, Integer caracteristicaConstitución, Integer caracteristicaInteligencia, Integer caracteristicaSabiduria, Integer caracteristicaCarisma, Integer bonoCompetencia, String foto, Usuario usuario, Campanha campanha) {
        this.personajePK = personajePK;
        this.personajeNombre = personajeNombre;
        this.caracteristicaFuerza = caracteristicaFuerza;
        this.caracteristicaDestreza = caracteristicaDestreza;
        this.caracteristicaConstitución = caracteristicaConstitución;
        this.caracteristicaInteligencia = caracteristicaInteligencia;
        this.caracteristicaSabiduria = caracteristicaSabiduria;
        this.caracteristicaCarisma = caracteristicaCarisma;
        this.bonoCompetencia = bonoCompetencia;
        this.foto = foto;
        this.usuario = usuario;
        this.campanha = campanha;
    }
    
}

