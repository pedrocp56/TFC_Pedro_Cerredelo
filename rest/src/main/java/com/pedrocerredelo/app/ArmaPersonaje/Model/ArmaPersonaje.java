package com.pedrocerredelo.app.ArmaPersonaje.Model;

import com.pedrocerredelo.app.Arma.Model.Arma;
import com.pedrocerredelo.app.Personaje.Model.Personaje;
import jakarta.persistence.*;

@Entity
@Table(name = "Arma_Personaje")
public class ArmaPersonaje {

    @EmbeddedId
    private ArmaPersonajePK id;

    @Column(name = "Ataque_Total", nullable = false)
    private int ataqueTotal;

    @Column(name = "Bonificación_Adicional", nullable = false)
    private int bonificacionAdicional;

    @Column(name = "Competencia", nullable = false)
    private boolean competencia;


    public ArmaPersonajePK getId() {
        return id;
    }

    public void setId(ArmaPersonajePK id) {
        this.id = id;
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


