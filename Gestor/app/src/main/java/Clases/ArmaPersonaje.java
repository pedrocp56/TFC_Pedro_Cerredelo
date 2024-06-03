package Clases;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class ArmaPersonaje implements Serializable {
    private Long armaId;
    private Long personajeId;
    private Long usuarioId;
    private int ataqueTotal;
    private int bonificacionAdicional;
    private boolean competencia;

    public ArmaPersonaje(Long armaId, Long personajeId, Long usuarioId, int ataqueTotal, int bonificacionAdicional, boolean competencia) {
        this.armaId = armaId;
        this.personajeId = personajeId;
        this.usuarioId = usuarioId;
        this.ataqueTotal = ataqueTotal;
        this.bonificacionAdicional = bonificacionAdicional;
        this.competencia = competencia;
    }

    public ArmaPersonaje() {
    }

    public ArmaPersonaje(JSONObject obj) throws JSONException {
        JSONObject idObject = obj.getJSONObject("id");
        this.armaId = idObject.getLong("armaId");
        this.personajeId = idObject.getLong("personajeId");
        this.usuarioId = idObject.getLong("usuarioId");
        this.ataqueTotal = obj.getInt("ataqueTotal");
        this.bonificacionAdicional = obj.getInt("bonificacionAdicional");
        this.competencia = obj.getBoolean("competencia");
    }


    // Getters y setters
    public Long getArmaId() {
        return armaId;
    }

    public void setArmaId(Long armaId) {
        this.armaId = armaId;
    }

    public Long getPersonajeId() {
        return personajeId;
    }

    public void setPersonajeId(Long personajeId) {
        this.personajeId = personajeId;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
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
                "armaId=" + armaId +
                ", personajeId=" + personajeId +
                ", usuarioId=" + usuarioId +
                ", ataqueTotal=" + ataqueTotal +
                ", bonificacionAdicional=" + bonificacionAdicional +
                ", competencia=" + competencia +
                '}';
    }
}


