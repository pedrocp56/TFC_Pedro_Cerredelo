package Clases;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class ArmaPersonaje implements Serializable {
    private Arma arma;
    private Personaje personaje;
    private int ataqueTotal;
    private int bonificacionAdicional;
    private boolean competencia;

    public ArmaPersonaje(Arma arma, Personaje personaje, int ataqueTotal, int bonificacionAdicional, boolean competencia) {
        this.arma = arma;
        this.personaje = personaje;
        this.ataqueTotal = ataqueTotal;
        this.bonificacionAdicional = bonificacionAdicional;
        this.competencia = competencia;
    }

    public ArmaPersonaje(JSONObject obj) throws JSONException {
        this.arma = new Arma(obj.getJSONObject("arma"));
        this.personaje = new Personaje(obj.getJSONObject("personaje"));
        this.ataqueTotal = obj.getInt("ataqueTotal");
        this.bonificacionAdicional = obj.getInt("bonificacionAdicional");
        this.competencia = obj.getBoolean("competencia");
    }

    // Getters y setters
    public Arma getArma() {
        return arma;
    }

    public void setArma(Arma arma) {
        this.arma = arma;
    }

    public Personaje getPersonaje() {
        return personaje;
    }

    public void setPersonaje(Personaje personaje) {
        this.personaje = personaje;
    }

    public int getAtaqueTotal() {
        return ataqueTotal;
    }

    public void setAtaqueTotal(int ataqueTotal) {
        this.ataqueTotal = ataqueTotal;
    }

    public int getBonificacionAdicional() {
        return bonificacionAdicional;
    }

    public void setBonificacionAdicional(int bonificacionAdicional) {
        this.bonificacionAdicional = bonificacionAdicional;
    }

    public boolean isCompetencia() {
        return competencia;
    }

    public void setCompetencia(boolean competencia) {
        this.competencia = competencia;
    }

    @Override
    public String toString() {
        return "ArmaPersonaje{" +
                "arma=" + arma +
                ", personaje=" + personaje +
                ", ataqueTotal=" + ataqueTotal +
                ", bonificacionAdicional=" + bonificacionAdicional +
                ", competencia=" + competencia +
                '}';
    }
}

