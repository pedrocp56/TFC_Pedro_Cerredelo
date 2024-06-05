/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.vo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author pedro
 */
@Entity
@Table(name = "Arma")
public class Arma implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer ID;

    @Basic(optional = false)
    @Column(name = "Nombre", unique = true)
    private String nombre;

    @Column(name = "Ataque")
    private Integer ataque;

    // Cambiar el tipo de dato de 'danho' a 'String'
    @Column(name = "Danho")
    private String danho;

    @Column(name = "Tipo")
    private String tipo;

    @Column(name = "Arrojadiza")
    private Boolean arrojadiza;

    @Column(name = "Car")
    private String car;

    @Column(name = "Caracteristicas")
    private String caracteristicas;

    @Column(name = "Foto")
    private byte[] foto;

    // Getters y setters

    public Integer getId() {
        return ID;
    }

    public void setId(Integer ID) {
        this.ID = ID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getAtaque() {
        return ataque;
    }

    public void setAtaque(Integer ataque) {
        this.ataque = ataque;
    }

    public String getDanho() {
        return danho;
    }

    public void setDanho(String danho) {
        this.danho = danho;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Boolean getArrojadiza() {
        return arrojadiza;
    }

    public void setArrojadiza(Boolean arrojadiza) {
        this.arrojadiza = arrojadiza;
    }

    public String getCar() {
        return car;
    }

    public void setCar(String car) {
        this.car = car;
    }

    public String getCaracteristicas() {
        return caracteristicas;
    }

    public void setCaracteristicas(String caracteristicas) {
        this.caracteristicas = caracteristicas;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    /*
    public List<Arma_Personaje> getArmaPersonajeList() {
        return armaPersonajeList;
    }

    public void setArmaPersonajeList(List<Arma_Personaje> armaPersonajeList) {
        this.armaPersonajeList = armaPersonajeList;
    }
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ID != null ? ID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Arma)) {
            return false;
        }
        Arma other = (Arma) obj;
        if ((this.ID == null && other.ID != null) || (this.ID != null && !this.ID.equals(other.ID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nombre;
    }

    public Arma() {
    }

    public Arma(Integer ID, String nombre, Integer ataque, String danho, String tipo, Boolean arrojadiza, String car, String caracteristicas, byte[] foto) {
        this.ID = ID;
        this.nombre = nombre;
        this.ataque = ataque;
        this.danho = danho;
        this.tipo = tipo;
        this.arrojadiza = arrojadiza;
        this.car = car;
        this.caracteristicas = caracteristicas;
        this.foto = foto;
    }

}
