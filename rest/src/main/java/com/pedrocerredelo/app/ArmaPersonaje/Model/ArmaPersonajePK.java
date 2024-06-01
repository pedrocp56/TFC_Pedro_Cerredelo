package com.pedrocerredelo.app.ArmaPersonaje.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;


@Embeddable
public class ArmaPersonajePK implements Serializable {

    @Column(name = "Arma_ID")
    private Long armaId;

    @Column(name = "Personaje_ID")
    private Long personajeId;

    @Column(name = "Usuario_ID")
    private Long usuarioId;

    // Constructores, getters y setters, y métodos equals() y hashCode()

    public ArmaPersonajePK() {}

    public ArmaPersonajePK(Long armaId, Long personajeId, Long usuarioId) {
        this.armaId = armaId;
        this.personajeId = personajeId;
        this.usuarioId = usuarioId;
    }

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

    // equals() y hashCode()
}





