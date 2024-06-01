package com.pedrocerredelo.app.Personaje.Repository;

import com.pedrocerredelo.app.Personaje.Model.Personaje;
import com.pedrocerredelo.app.Personaje.Model.PersonajePK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface PersonajeRest extends JpaRepository<Personaje, PersonajePK> {

    List<Personaje> findByIdUsuarioId(Long usuarioId);
    Personaje findByIdPersonajeId(Long personakeId);

    Personaje findByPersonajeNombre(String personajeNombre);
}

