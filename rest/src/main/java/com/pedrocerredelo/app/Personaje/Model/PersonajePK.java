package com.pedrocerredelo.app.Personaje.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class PersonajePK implements Serializable {

    @Column(name = "Personaje_ID")
    private Long personajeId;

    @Column(name = "Usuario_ID")
    private Long usuarioId;

    // Constructores, getters y setters, y métodos equals() y hashCode() necesarios.

    public PersonajePK(Long personajeId, Long usuarioId) {
        this.personajeId = personajeId;
        this.usuarioId = usuarioId;
    }

    public PersonajePK() {
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

}
