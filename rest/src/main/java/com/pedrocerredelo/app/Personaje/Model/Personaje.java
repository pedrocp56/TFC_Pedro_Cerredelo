package com.pedrocerredelo.app.Personaje.Model;

import jakarta.persistence.*;


@Entity
@Table
public class Personaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "Usuario_ID", nullable = false)
    private Long usuarioId;

    @Column(name = "Campaña_ID", nullable = false)
    private Long campañaId;

    @Column(name = "Personaje_Nombre", unique = true, nullable = false, length = 50)
    private String nombre;

    @Column(name = "Caracteristica_Fuerza", nullable = false)
    private int fuerza;

    @Column(name = "Caracteristica_Destreza", nullable = false)
    private int destreza;

    @Column(name = "Caracteristica_Constitución", nullable = false)
    private int constitucion;

    @Column(name = "Caracteristica_Inteligencia", nullable = false)
    private int inteligencia;

    @Column(name = "Caracteristica_Sabiduria", nullable = false)
    private int sabiduria;

    @Column(name = "Caracteristica_Carisma", nullable = false)
    private int carisma;

    @Column(name = "Bono_Competencia", nullable = false)
    private int bonoCompetencia;

    @Column(name = "foto", length = 255)
    private String foto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Long getCampañaId() {
        return campañaId;
    }

    public void setCampañaId(Long campañaId) {
        this.campañaId = campañaId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getFuerza() {
        return fuerza;
    }

    public void setFuerza(int fuerza) {
        this.fuerza = fuerza;
    }

    public int getDestreza() {
        return destreza;
    }

    public void setDestreza(int destreza) {
        this.destreza = destreza;
    }

    public int getConstitucion() {
        return constitucion;
    }

    public void setConstitucion(int constitucion) {
        this.constitucion = constitucion;
    }

    public int getInteligencia() {
        return inteligencia;
    }

    public void setInteligencia(int inteligencia) {
        this.inteligencia = inteligencia;
    }

    public int getSabiduria() {
        return sabiduria;
    }

    public void setSabiduria(int sabiduria) {
        this.sabiduria = sabiduria;
    }

    public int getCarisma() {
        return carisma;
    }

    public void setCarisma(int carisma) {
        this.carisma = carisma;
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
