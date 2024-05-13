/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author pedro
 */


@Entity
@Table(name = "Campanha")
public class Campanha implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    
    @Basic(optional = false)
    @Column(name = "Nombre")
    private String nombre;
    
    @Column(name = "Descripcion")
    private String descripcion;
    
    @Column(name = "Fecha")
    private Date fecha;
    
    @Column(name = "Foto")
    private String foto;
    
    @Column(name = "ID_Creador")
    private Integer idCreador;
    
    @OneToMany(mappedBy = "campanha")
    private List<Personaje> personajes;

    // Getters y setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Integer getIdCreador() {
        return idCreador;
    }

    public void setIdCreador(Integer idCreador) {
        this.idCreador = idCreador;
    }

    public List<Personaje> getPersonajes() {
        return personajes;
    }

    public void setPersonajes(List<Personaje> personajes) {
        this.personajes = personajes;
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
        final Campanha other = (Campanha) obj;
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        if (!Objects.equals(this.descripcion, other.descripcion)) {
            return false;
        }
        if (!Objects.equals(this.foto, other.foto)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.fecha, other.fecha)) {
            return false;
        }
        if (!Objects.equals(this.idCreador, other.idCreador)) {
            return false;
        }
        return Objects.equals(this.personajes, other.personajes);
    }

    @Override
    public String toString() {
        return "Campanha{" + "id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", fecha=" + fecha + ", foto=" + foto + ", idCreador=" + idCreador + ", personajes=" + personajes + '}';
    }

    public Campanha(Integer id, String nombre, String descripcion, Date fecha, String foto, Integer idCreador, List<Personaje> personajes) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.foto = foto;
        this.idCreador = idCreador;
        this.personajes = personajes;
    }
    
    
}

