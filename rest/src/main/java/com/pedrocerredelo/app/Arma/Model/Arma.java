package com.pedrocerredelo.app.Arma.Model;

import jakarta.persistence.*;

@Entity
@Table(schema = "Arma")
public class Arma {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "Nombre", unique = true, nullable = false, length = 50)
    private String nombre;

    @Column(name = "Ataque", nullable = false)
    private int ataque;

    @Column(name = "Danho", nullable = false, length = 15)
    private String danho;

    @Column(name = "Tipo", nullable = false, length = 15)
    private String tipo;

    @Column(name = "Arrojadiza")
    private boolean arrojadiza;

    @Column(name = "Car", length = 15)
    private String car;

    @Column(name = "Caracteristicas")
    private String caracteristicas;

    @Lob
    @Column(name = "Foto")
    private byte[] foto;

    // Getters y Setters

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

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }
}

