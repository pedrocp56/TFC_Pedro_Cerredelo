/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.vo;

import com.mysql.cj.jdbc.Blob;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author pedro
 */
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "Personaje")
public class Personaje implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    protected PersonajePK personajePK;

    @Basic(optional = false)
    @Column(name = "Personaje_Nombre")
    private String personajeNombre;

    @Basic(optional = false)
    @Column(name = "Caracteristica_Fuerza")
    private Integer caracteristicaFuerza;

    @Basic(optional = false)
    @Column(name = "Caracteristica_Destreza")
    private Integer caracteristicaDestreza;

    @Basic(optional = false)
    @Column(name = "Caracteristica_Constitucion")
    private Integer caracteristicaConstitucion;

    @Basic(optional = false)
    @Column(name = "Caracteristica_Inteligencia")
    private Integer caracteristicaInteligencia;

    @Basic(optional = false)
    @Column(name = "Caracteristica_Sabiduria")
    private Integer caracteristicaSabiduria;

    @Basic(optional = false)
    @Column(name = "Caracteristica_Carisma")
    private Integer caracteristicaCarisma;

    @Basic(optional = false)
    @Column(name = "Bono_Competencia")
    private Integer bonoCompetencia;

    @Column(name = "Foto")
    private byte[] foto;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "personaje")
    private List<Arma_Personaje> armaPersonajeList;

    @ManyToOne(optional = false)
    @JoinColumn(name = "Usuario_ID", referencedColumnName = "ID", insertable = false, updatable = false)
    private Usuario usuario;

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

    public Integer getCaracteristicaConstitucion() {
        return caracteristicaConstitucion;
    }

    public void setCaracteristicaConstitucion(Integer caracteristicaConstitucion) {
        this.caracteristicaConstitucion = caracteristicaConstitucion;
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

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Arma_Personaje> getArmaPersonajeList() {
        return armaPersonajeList;
    }

    public void setArmaPersonajeList(List<Arma_Personaje> armaPersonajeList) {
        this.armaPersonajeList = armaPersonajeList;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Personaje)) {
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
        if (!Objects.equals(this.caracteristicaConstitucion, other.caracteristicaConstitucion)) {
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
        return true;
    }

    @Override
    public String toString() {
        return personajeNombre;
    }

    public Personaje() {
    }

    public Personaje(PersonajePK personajePK, String personajeNombre, Integer caracteristicaFuerza, Integer caracteristicaDestreza, Integer caracteristicaConstitucion, Integer caracteristicaInteligencia, Integer caracteristicaSabiduria, Integer caracteristicaCarisma, Integer bonoCompetencia, byte[] foto, Usuario usuario) {
        this.personajePK = personajePK;
        this.personajeNombre = personajeNombre;
        this.caracteristicaFuerza = caracteristicaFuerza;
        this.caracteristicaDestreza = caracteristicaDestreza;
        this.caracteristicaConstitucion = caracteristicaConstitucion;
        this.caracteristicaInteligencia = caracteristicaInteligencia;
        this.caracteristicaSabiduria = caracteristicaSabiduria;
        this.caracteristicaCarisma = caracteristicaCarisma;
        this.bonoCompetencia = bonoCompetencia;
        this.foto = null;
        //this.foto = foto;
        this.usuario = usuario;
    }

}
