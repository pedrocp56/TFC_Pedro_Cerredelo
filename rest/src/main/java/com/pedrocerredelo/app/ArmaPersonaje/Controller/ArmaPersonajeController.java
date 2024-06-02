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
    public ResponseEntity<String> addArmaPersonaje(
            @PathVariable Long armaId,
            @PathVariable Long personajeId,
            @PathVariable Long usuarioId,
            @RequestBody ArmaPersonaje armaPersonaje) {
        try {
            ArmaPersonajePK id = new ArmaPersonajePK(armaId, personajeId, usuarioId);
            Optional<ArmaPersonaje> existingArmaPersonaje = armaPersonajeRepository.findById(id);
            if (existingArmaPersonaje.isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("El personaje ya tiene esa arma");
            } else {
                armaPersonaje.setId(id);
                armaPersonajeRepository.save(armaPersonaje);
                return ResponseEntity.status(HttpStatus.CREATED).body("Arma asignada al personaje exitosamente");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al asignar el arma al personaje");
        }
    }

    @PutMapping(Variables.ARMA_PERSONAJE_UPDATE)
    public ResponseEntity<ArmaPersonaje> update(
            @PathVariable Long armaId,
            @PathVariable Long personajeId,
            @PathVariable Long usuarioId,
            @RequestBody ArmaPersonaje armaPersonaje) {
        try {
            ArmaPersonajePK id = new ArmaPersonajePK(armaId, personajeId, usuarioId);
            Optional<ArmaPersonaje> existingArmaPersonajeOptional = armaPersonajeRepository.findById(id);

            if (existingArmaPersonajeOptional.isPresent()) {
                ArmaPersonaje existingArmaPersonaje = existingArmaPersonajeOptional.get();

                // Actualizar los campos necesarios del objeto existente
                existingArmaPersonaje.setAtaqueTotal(armaPersonaje.getAtaqueTotal());
                existingArmaPersonaje.setBonificacionAdicional(armaPersonaje.getBonificacionAdicional());
                existingArmaPersonaje.setCompetencia(armaPersonaje.isCompetencia());

                // Guardar los cambios en la base de datos
                ArmaPersonaje updatedArmaPersonaje = armaPersonajeRepository.save(existingArmaPersonaje);
                return ResponseEntity.ok(updatedArmaPersonaje);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping(Variables.ARMA_PERSONAJE_DELETE)
    public ResponseEntity<String> delete(
            @PathVariable Long personajeId,
            @PathVariable Long armaId,
            @PathVariable Long usuarioId) {

        try {
            ArmaPersonajePK id = new ArmaPersonajePK(armaId, personajeId, usuarioId);

            // Verificamos si la entidad existe antes de intentar eliminarla
            if (armaPersonajeRepository.existsById(id)) {
                armaPersonajeRepository.deleteById(id);
                return ResponseEntity.ok("Arma desequipada");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La combinación de arma y personaje no existe");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al desequipar el arma del personaje");
        }
    }

    @GetMapping(Variables.ARMA_PERSONAJE_SEARCH_BY_PERSONAJE)
    public ResponseEntity<List<ArmaPersonaje>> getByPersonajeId(@PathVariable("personajeId") Long personajeId) {
        List<ArmaPersonaje> armaPersonajes = armaPersonajeRepository.findById_PersonajeId(personajeId);
        return ResponseEntity.ok(armaPersonajes);
    }
}
