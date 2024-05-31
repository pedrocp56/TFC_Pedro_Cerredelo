package com.pedrocerredelo.app.Arma.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "Arma")
public class Arma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "Nombre", unique = true, nullable = false, length = 50)
    private String nombre;

    @Column(name = "Ataque", nullable = false)
    private int ataque;

    @Column(name = "Daño", nullable = false)
    private int daño;

    @Column(name = "Tipo", nullable = false, length = 15)
    private String tipo;

    @Column(name = "Arrojadiza")
    private boolean arrojadiza;

    @Column(name = "Car", length = 15)
    private String car;

    @Column(name = "Caracteristicas", columnDefinition = "TEXT")
    private String caracteristicas;

    @Column(name = "Foto", length = 255)
    private String foto;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getAtaque() {
        return ataque;
    }

    public void setAtaque(int ataque) {
        this.ataque = ataque;
    }

    public int getDaño() {
        return daño;
    }

    public void setDaño(int daño) {
        this.daño = daño;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public boolean isArrojadiza() {
        return arrojadiza;
    }

    public void setArrojadiza(boolean arrojadiza) {
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

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
