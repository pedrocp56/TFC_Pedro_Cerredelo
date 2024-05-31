package com.pedrocerredelo.app.Personaje.Repository;

import com.pedrocerredelo.app.Personaje.Model.Personaje;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonajeRest extends JpaRepository<Personaje, Long> {
    Personaje findByPersonajeNombre(String personajeNombre);
    List<Personaje> findByUsuarioId(Long usuarioId);  // Método para buscar personajes por ID de usuario

}
