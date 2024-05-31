package com.pedrocerredelo.app.Personaje.Model;

import com.pedrocerredelo.app.Usuario.Model.Usuario;
import jakarta.persistence.*;

@Entity
@Table(name = "Personaje")
public class Personaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Personaje_ID")
    private Long personajeId;

    @ManyToOne
    @JoinColumn(name = "Usuario_ID", nullable = false)
    private Usuario usuario;

    @Column(name = "Personaje_Nombre", nullable = false, length = 50)
    private String personajeNombre;

    @Column(name = "Caracteristica_Fuerza", nullable = false)
    private int caracteristicaFuerza;

    @Column(name = "Caracteristica_Destreza", nullable = false)
    private int caracteristicaDestreza;

    @Column(name = "Caracteristica_Constitución", nullable = false)
    private int caracteristicaConstitucion;

    @Column(name = "Caracteristica_Inteligencia", nullable = false)
    private int caracteristicaInteligencia;

    @Column(name = "Caracteristica_Sabiduria", nullable = false)
    private int caracteristicaSabiduria;

    @Column(name = "Caracteristica_Carisma", nullable = false)
    private int caracteristicaCarisma;

    @Column(name = "Bono_Competencia", nullable = false)
    private int bonoCompetencia;

    @Column(name = "Foto", length = 255)
    private String foto;

    // Getters y setters

    public Long getPersonajeId() {
        return personajeId;
    }

    public void setPersonajeId(Long personajeId) {
        this.personajeId = personajeId;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getPersonajeNombre() {
        return personajeNombre;
    }

    public void setPersonajeNombre(String personajeNombre) {
        this.personajeNombre = personajeNombre;
    }

    public int getCaracteristicaFuerza() {
        return caracteristicaFuerza;
    }

    public void setCaracteristicaFuerza(int caracteristicaFuerza) {
        this.caracteristicaFuerza = caracteristicaFuerza;
    }

    public int getCaracteristicaDestreza() {
        return caracteristicaDestreza;
    }

    public void setCaracteristicaDestreza(int caracteristicaDestreza) {
        this.caracteristicaDestreza = caracteristicaDestreza;
    }

    public int getCaracteristicaConstitucion() {
        return caracteristicaConstitucion;
    }

    public void setCaracteristicaConstitucion(int caracteristicaConstitucion) {
        this.caracteristicaConstitucion = caracteristicaConstitucion;
    }

    public int getCaracteristicaInteligencia() {
        return caracteristicaInteligencia;
    }

    public void setCaracteristicaInteligencia(int caracteristicaInteligencia) {
        this.caracteristicaInteligencia = caracteristicaInteligencia;
    }

    public int getCaracteristicaSabiduria() {
        return caracteristicaSabiduria;
    }

    public void setCaracteristicaSabiduria(int caracteristicaSabiduria) {
        this.caracteristicaSabiduria = caracteristicaSabiduria;
    }

    public int getCaracteristicaCarisma() {
        return caracteristicaCarisma;
    }

    public void setCaracteristicaCarisma(int caracteristicaCarisma) {
        this.caracteristicaCarisma = caracteristicaCarisma;
    }

    public int getBonoCompetencia() {
        return bonoCompetencia;
    }

    public void setBonoCompetencia(int bonoCompetencia) {
        this.bonoCompetencia = bonoCompetencia;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }


}
