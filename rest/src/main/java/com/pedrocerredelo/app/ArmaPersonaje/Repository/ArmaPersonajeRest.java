package com.pedrocerredelo.app.ArmaPersonaje.Repository;

import com.pedrocerredelo.app.ArmaPersonaje.Model.ArmaPersonaje;
import com.pedrocerredelo.app.ArmaPersonaje.Model.ArmaPersonajePK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArmaPersonajeRest extends JpaRepository<ArmaPersonaje, ArmaPersonajePK> {
    List<ArmaPersonaje> findById_PersonajeId(Long personajeId);
    Optional<ArmaPersonaje> findById(ArmaPersonajePK id);
    void deleteById(ArmaPersonajePK id);
}





