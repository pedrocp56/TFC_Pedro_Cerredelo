package Clases;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Arrays;

public class Arma implements Serializable {
    private long id;
    private String nombre;
    private int ataque;
    private String danho;
    private String tipo;
    private boolean arrojadiza;
    private String car;
    private String caracteristicas;
    private byte[] foto;

    public Arma(long id, String nombre, int ataque, String danho, String tipo, boolean arrojadiza, String car, String caracteristicas, byte[] foto) {
        this.id = id;
        this.nombre = nombre;
        this.ataque = ataque;
        this.danho = danho;
        this.tipo = tipo;
        this.arrojadiza = arrojadiza;
        this.car = car;
        this.caracteristicas = caracteristicas;
        this.foto = foto;
    }

    public Arma(JSONObject obj) throws JSONException {
        this.id = obj.getLong("id");
        this.nombre = obj.getString("nombre");
        this.ataque = obj.getInt("ataque");
        this.danho = obj.getString("danho");
        this.tipo = obj.getString("tipo");
        this.arrojadiza = obj.getBoolean("arrojadiza");

        // Manejar campos opcionales
        if (obj.has("car")) {
            this.car = obj.getString("car");
        } else {
            this.car = null;
        }

        if (obj.has("caracteristicas")) {
            this.caracteristicas = obj.getString("caracteristicas");
        } else {
            this.caracteristicas = null;
        }

        // Si la imagen es nula, puedes asignar null directamente al campo foto
        this.foto = null;
    }


    // Getters and setters

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    @Override
    public String toString() {
        return "Arma{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", ataque=" + ataque +
                ", danho=" + danho +
                ", tipo='" + tipo + '\'' +
                ", arrojadiza=" + arrojadiza +
                ", car='" + car + '\'' +
                ", caracteristicas='" + caracteristicas + '\'' +
                ", foto=" + Arrays.toString(foto) +
                '}';
    }
}

