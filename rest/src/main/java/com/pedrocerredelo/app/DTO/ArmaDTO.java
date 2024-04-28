package com.pedrocerredelo.app.DTO;

import com.pedrocerredelo.app.Arma.Model.Arma;

//lombok añadir no me va XD
//@Data
public class ArmaDTO {
    private Long id;
    private String nombre;
    private int ataque;
    private int daño;
    private String tipo;
    private boolean arrojadiza;
    private String car;
    private String caracteristicas;
    private String foto;


    public static ArmaDTO converter(Arma arma) {
        ArmaDTO a = new ArmaDTO();
        a.setId(arma.getID());
        a.setNombre(arma.getNombre());
        a.setAtaque(arma.getAtaque());
        a.setDaño(arma.getDaño());
        a.setTipo(arma.getTipo());
        a.setArrojadiza(arma.isArrojadiza());
        a.setCar(arma.getCar());
        a.setCaracteristicas(arma.getCaracteristicas());
        a.setFoto(arma.getFoto());
        return a;
    }

    public ArmaDTO() {
    }

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
