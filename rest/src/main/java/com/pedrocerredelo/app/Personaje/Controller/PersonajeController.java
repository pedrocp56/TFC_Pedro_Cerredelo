package com.pedrocerredelo.app.Personaje.Controller;

import com.pedrocerredelo.app.Personaje.Model.Personaje;
import com.pedrocerredelo.app.Personaje.Model.PersonajePK;
import com.pedrocerredelo.app.Personaje.Repository.PersonajeRest;
import com.pedrocerredelo.app.Variables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(Variables.PERSONAJE_BASE_PATH) // Ruta para todos personajes
public class PersonajeController {

    private static final Logger log = LoggerFactory.getLogger(PersonajeController.class);
    @Autowired
    private PersonajeRest personajeRepository;

    @GetMapping(Variables.PERSONAJE_GET_ALL)
    public List<Personaje> getPersonajes() {
        log.info("Mondongo");
        return personajeRepository.findAll();
    }

    @GetMapping(Variables.PERSONAJE_SEARCH)
    public ResponseEntity<Personaje> getPersonaje(@PathVariable Long usuarioId, @PathVariable Long personajeId) {
        PersonajePK id = new PersonajePK(personajeId, usuarioId);
        Optional<Personaje> personaje = personajeRepository.findById(id);
        return personaje.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(Variables.PERSONAJE_SEARCH_BY_NAME)
    public ResponseEntity<Personaje> getPersonajePorNombre(@PathVariable("personajeNombre") String personajeNombre) {
        Personaje personaje = personajeRepository.findByPersonajeNombre(personajeNombre);
        if (personaje != null) {
            return ResponseEntity.ok(personaje);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(Variables.PERSONAJE_SAVE)
    public ResponseEntity<?> guardarPersonaje(@RequestBody Personaje personaje) {
        try {
            personajeRepository.save(personaje);
            return ResponseEntity.ok().body("{\"success\": true}");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\": \"Error al guardar el personaje\"}");
        }
    }

    @PutMapping(Variables.PERSONAJE_UPDATE)
    public ResponseEntity<Personaje> actualizarPersonaje( @RequestBody Personaje personaje) {
        try {
            PersonajePK id = new PersonajePK(personaje.getId().getPersonajeId(), personaje.getId().getUsuarioId());
            Personaje personajeExistente = personajeRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Personaje no encontrado"));

            // Actualizar los campos del personaje
            personajeExistente.setPersonajeNombre(personaje.getPersonajeNombre());
            personajeExistente.setCaracteristicaFuerza(personaje.getCaracteristicaFuerza());
            personajeExistente.setCaracteristicaDestreza(personaje.getCaracteristicaDestreza());
            personajeExistente.setCaracteristicaConstitucion(personaje.getCaracteristicaConstitucion());
            personajeExistente.setCaracteristicaInteligencia(personaje.getCaracteristicaInteligencia());
            personajeExistente.setCaracteristicaSabiduria(personaje.getCaracteristicaSabiduria());
            personajeExistente.setCaracteristicaCarisma(personaje.getCaracteristicaCarisma());
            personajeExistente.setBonoCompetencia(personaje.getBonoCompetencia());
            personajeExistente.setFoto(personaje.getFoto());

            // Guardar los cambios en la base de datos
            personajeRepository.save(personajeExistente);

            // Devolver una respuesta con el objeto Personaje actualizado
            return ResponseEntity.ok(personajeExistente);
        } catch (Exception e) {
            // Si ocurre algún error, devuelve una respuesta de error con el mensaje correspondiente
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null); // Aquí podrías devolver un objeto ResponseEntity con un mensaje de error específico si lo deseas
        }
    }

    @DeleteMapping(Variables.PERSONAJE_DELETE)
    public ResponseEntity<String> eliminarPersonaje(@PathVariable Long usuarioId, @PathVariable Long personajeId) {
        try {
            // Intenta encontrar y eliminar el personaje con el ID proporcionado
            PersonajePK id = new PersonajePK(personajeId, usuarioId);
            Personaje personaje = personajeRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Personaje no encontrado"));
            personajeRepository.delete(personaje);
            // Devuelve una respuesta de éxito con el mensaje indicando que el personaje fue eliminado
            return ResponseEntity.ok("Personaje " + personaje.getPersonajeNombre() + " eliminado");
        } catch (Exception e) {
            // Si ocurre algún error, devuelve una respuesta de error con el mensaje correspondiente
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar el personaje con ID: " + personajeId);
        }
    }

    @GetMapping(Variables.PERSONAJE_SEARCH_BY_USER)
    public ResponseEntity<List<Personaje>> getPersonajesPorUsuario(@PathVariable("usuarioId") Long usuarioId) {
        List<Personaje> personajes = personajeRepository.findByIdUsuarioId(usuarioId);
        return ResponseEntity.ok(personajes); // Devuelve la lista de personajes encontrados
    }

    @GetMapping(Variables.PERSONAJE_SEARCH_BY_ID)
    public ResponseEntity<Personaje> getPersonaje(@PathVariable Long personajeId) {
        Personaje personaje = personajeRepository.findByIdPersonajeId(personajeId);
        return ResponseEntity.ok(personaje);
    }

}

