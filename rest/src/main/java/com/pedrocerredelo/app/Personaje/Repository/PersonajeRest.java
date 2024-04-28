package com.pedrocerredelo.app.Personaje.Repository;

import com.pedrocerredelo.app.Personaje.Model.Personaje;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonajeRest extends JpaRepository<Personaje, Long> {
}
