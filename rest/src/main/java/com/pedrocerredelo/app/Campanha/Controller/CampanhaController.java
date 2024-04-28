package com.pedrocerredelo.app.Campanha.Controller;

import com.pedrocerredelo.app.Campanha.Model.Campanha;
import com.pedrocerredelo.app.Campanha.Repository.CampanhaRest;
import com.pedrocerredelo.app.Variables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Variables.CAMPANHA_BASE_PATH) //Ruta para todas las campanhas
public class CampanhaController {
    @Autowired
    private CampanhaRest campanhaRest;

    @GetMapping(Variables.CAMPANHA_GET_ALL)
    public List<Campanha> getCampanhas() {
        return campanhaRest.findAll();
    }

    @GetMapping(Variables.CAMPANHA_SEARCH)
    public Campanha getCampanha(@PathVariable long id) {
        Campanha c = campanhaRest.findById(id).orElseThrow(() -> new RuntimeException("campanha no encontrada"));
        return c;
    }
    /*
    @GetMapping(Variables.CAMPANHA_SEARCH)
    public Campanha getCampanha(@PathVariable String nombre) {
        new RuntimeException("Aun no planteado");
        //campanha c = campanhaRest.findByNombre(nombre).orElseThrow(() -> new RuntimeException("campanha no encontrada"));
        Campanha c = campanhaRest.findById(1L).orElseThrow(() -> new RuntimeException("campanha no encontrada"));

        return c;
    }
    */


    @PostMapping(Variables.CAMPANHA_SAVE)
    public String guardarCampanha(@RequestBody Campanha campanha) {
        campanhaRest.save(campanha);
        return "campanha guardada";
    }

    @PutMapping(Variables.CAMPANHA_UPDATE)
    public String actualizarCampanha(@PathVariable long id, @RequestBody Campanha campanha) {
        Campanha c = campanhaRest.findById(id).orElseThrow(() -> new RuntimeException("campanha no encontrada"));
        c.setNombre(campanha.getNombre());
        c.setDescripcion(campanha.getDescripcion());
        c.setFecha(campanha.getFecha());
        c.setFoto(campanha.getFoto());
        c.setFoto(campanha.getFoto());
        campanhaRest.save(c);
        return "campanha " + id + " actualizada";
    }

    @PutMapping(Variables.CAMPANHA_DELETE)
    public String eliminarCampanha(@PathVariable long id) {
        Campanha c = campanhaRest.findById(id).orElseThrow(() -> new RuntimeException("campanha no encontrada"));
        campanhaRest.delete(c);
        return "campanha " + id + " eliminada";
    }

}
