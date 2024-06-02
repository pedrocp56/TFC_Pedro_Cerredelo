package com.pedrocerredelo.app.ArmaPersonaje.Controller;

import com.pedrocerredelo.app.ArmaPersonaje.Model.ArmaPersonaje;
import com.pedrocerredelo.app.ArmaPersonaje.Model.ArmaPersonajePK;
import com.pedrocerredelo.app.ArmaPersonaje.Repository.ArmaPersonajeRest;
import com.pedrocerredelo.app.Variables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(Variables.ARMA_PERSONAJE_BASE_PATH)
public class ArmaPersonajeController {

    @Autowired
    private ArmaPersonajeRest armaPersonajeRepository;

    @GetMapping(Variables.ARMA_PERSONAJE_GET_ALL)
    public List<ArmaPersonaje> getAll() {
        return armaPersonajeRepository.findAll();
    }

    @GetMapping(Variables.ARMA_PERSONAJE_SEARCH_BY_IDS)
    public ResponseEntity<ArmaPersonaje> getByPersonajeIdAndArmaId(
            @PathVariable Long armaId,
            @PathVariable Long personajeId,
            @PathVariable Long usuarioId) {

        Optional<ArmaPersonaje> armaPersonaje = armaPersonajeRepository.findById(
                new ArmaPersonajePK(armaId, personajeId, usuarioId));
        return armaPersonaje.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(Variables.ARMA_PERSONAJE_SAVE)
    public ResponseEntity<ArmaPersonaje> addArmaPersonaje(
            @PathVariable Long armaId,
            @PathVariable Long personajeId,
            @PathVariable Long usuarioId,
            @RequestBody ArmaPersonaje armaPersonaje) {
        try {
            armaPersonaje.setId(new ArmaPersonajePK(armaId, personajeId, usuarioId));
            ArmaPersonaje createdArmaPersonaje = armaPersonajeRepository.save(armaPersonaje);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdArmaPersonaje);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping(Variables.ARMA_PERSONAJE_UPDATE)
    public ResponseEntity<ArmaPersonaje> update(
            @PathVariable Long armaId,
            @PathVariable Long personajeId,
            @PathVariable Long usuarioId,
            @RequestBody ArmaPersonaje armaPersonaje) {
        ArmaPersonajePK id = new ArmaPersonajePK(armaId,personajeId,usuarioId);
        Optional<ArmaPersonaje> existingArmaPersonaje = armaPersonajeRepository.findById(id);
        if (existingArmaPersonaje.isPresent()) {
            ArmaPersonaje updatedArmaPersonaje = armaPersonajeRepository.save(armaPersonaje);
            return ResponseEntity.ok(updatedArmaPersonaje);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(Variables.ARMA_PERSONAJE_DELETE)
    public ResponseEntity<Void> delete(
            @PathVariable Long personajeId,
            @PathVariable Long armaId,
            @PathVariable Long usuarioId) {

        ArmaPersonajePK id = new ArmaPersonajePK(armaId, personajeId, usuarioId);
        armaPersonajeRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(Variables.ARMA_PERSONAJE_SEARCH_BY_PERSONAJE)
    public ResponseEntity<List<ArmaPersonaje>> getByPersonajeId(@PathVariable("personajeId") Long personajeId) {
        List<ArmaPersonaje> armaPersonajes = armaPersonajeRepository.findById_PersonajeId(personajeId);
        return ResponseEntity.ok(armaPersonajes);
    }

}


