package com.pedrocerredelo.app.Arma.Controller;

import com.pedrocerredelo.app.Arma.Model.Arma;
import com.pedrocerredelo.app.Arma.Repository.ArmaRest;
import com.pedrocerredelo.app.Variables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping(Variables.ARMA_BASE_PATH) // Ruta base para las operaciones relacionadas con Arma
public class ArmaController {

    @Autowired
    private ArmaRest armaRest;

    @GetMapping(Variables.ARMA_GET_ALL)
    public List<Arma> getArmas() {
        return armaRest.findAll();
    }

    @GetMapping(Variables.ARMA_SEARCH)
    public ResponseEntity<Arma> getArma(@PathVariable long id) {
        Optional<Arma> arma = armaRest.findById(id);
        return arma.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(Variables.ARMA_SAVE)
    public ResponseEntity<?> guardarArma(@RequestBody Arma arma) {
        try {
            armaRest.save(arma);
            return ResponseEntity.ok().body("{\"success\": true}");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\": \"Error al guardar el arma\"}");
        }
    }

    @PutMapping(Variables.ARMA_UPDATE)
    public ResponseEntity<String> actualizarArma(@PathVariable long id, @RequestBody Arma arma) {
        Arma armaExistente = armaRest.findById(id)
                .orElseThrow(() -> new RuntimeException("Arma no encontrada"));

        // Actualizar los campos del arma
        armaExistente.setNombre(arma.getNombre());
        armaExistente.setAtaque(arma.getAtaque());
        armaExistente.setDanho(arma.getDanho());
        armaExistente.setTipo(arma.getTipo());
        armaExistente.setArrojadiza(arma.isArrojadiza());
        armaExistente.setCar(arma.getCar());
        armaExistente.setCaracteristicas(arma.getCaracteristicas());
        armaExistente.setFoto(arma.getFoto());

        // Guardar los cambios en la base de datos
        armaRest.save(armaExistente);

        // Devolver una respuesta con un mensaje indicando que la actualización fue exitosa
        return ResponseEntity.ok().body("{\"message\": \"success\"}");
    }

    @DeleteMapping(Variables.ARMA_DELETE)
    public String eliminarArma(@PathVariable long id) {
        Arma arma = armaRest.findById(id).orElseThrow(() -> new RuntimeException("Arma no encontrada"));
        armaRest.delete(arma);
        return "Arma " + id + " eliminada";
    }
}


