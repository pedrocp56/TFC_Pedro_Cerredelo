package com.pedrocerredelo.app.Arma_Personaje.Model;

import com.pedrocerredelo.app.Arma.Model.Arma;
import com.pedrocerredelo.app.Personaje.Model.Personaje;
import com.pedrocerredelo.app.Usuario.Model.Usuario;
import jakarta.persistence.*;

@Entity
@Table(name = "Arma_Personaje")
public class Arma_Personaje {
    @Id
    @ManyToOne
    @JoinColumn(name = "Arma_ID", referencedColumnName = "ID")
    private Arma arma;

    @Id
    @ManyToOne
    @JoinColumn(name = "Usuario_ID", referencedColumnName = "ID")
    private Usuario usuario;

    @Id
    @ManyToOne
    @JoinColumn(name = "Personaje_ID", referencedColumnName = "ID")
    private Personaje personaje;

    @Column(name = "Ataque_Total", nullable = false)
    private int ataqueTotal;

    @Column(name = "Bonificación_Adicional", nullable = false)
    private int bonificacionAdicional;

    @Column(name = "Competencia", nullable = false)
    private boolean competencia;

    public Arma getArma() {
        return arma;
    }

    public void setArma(Arma arma) {
        this.arma = arma;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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
}
