package com.pedrocerredelo.app.Arma.Controller;

import com.pedrocerredelo.app.Arma.Model.Arma;
import com.pedrocerredelo.app.Arma.Repository.ArmaRest;
import com.pedrocerredelo.app.Variables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(Variables.ARMA_BASE_PATH) //Ruta para todas las armas
public class ArmaController {
    @Autowired
    private ArmaRest armaRest;

    @GetMapping(Variables.ARMA_GET_ALL)
    public List<Arma> getArmas() {
        return armaRest.findAll();
    }

    @GetMapping(Variables.ARMA_SEARCH)
    public Arma getArma(@PathVariable long id) {
        Optional<Arma> a = armaRest.findById(id);
        return a.orElse(null);
    }

    @GetMapping(Variables.ARMA_SEARCH_BY_NAME)
    public Arma getArma(@PathVariable String name) {
        Optional<Arma> a = armaRest.findByNombre(name);
        return a.orElse(null);
    }


    @PostMapping(Variables.ARMA_SAVE)
    public boolean guardarArma(@RequestBody Arma arma) {
        try {
            armaRest.save(arma);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @PutMapping(Variables.ARMA_UPDATE)
    public String actualizarArma(@PathVariable long id, @RequestBody Arma arma) {
        Arma a = armaRest.findById(id).orElseThrow(() -> new RuntimeException("Arma no encontrada"));
        a.setNombre(arma.getNombre());
        a.setAtaque(arma.getAtaque());
        a.setDaño(arma.getDaño());
        a.setTipo(arma.getTipo());
        a.setArrojadiza(arma.isArrojadiza());
        a.setCar(arma.getCar());
        a.setCaracteristicas(arma.getCaracteristicas());
        a.setFoto(arma.getFoto());
        armaRest.save(a);
        return "Arma " + id + " actualizada";
    }

    @DeleteMapping(Variables.ARMA_DELETE)
    public String eliminarArma(@PathVariable long id) {
        Arma a = armaRest.findById(id).orElseThrow(() -> new RuntimeException("Arma no encontrada"));
        armaRest.delete(a);
        return "Arma " + id + " eliminada";
    }

}

