package com.pedrocerredelo.app.Usuario.Controller;


import com.pedrocerredelo.app.Usuario.Model.Usuario;
import com.pedrocerredelo.app.Usuario.Repository.UsuarioRest;
import com.pedrocerredelo.app.Variables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(Variables.USUARIO_BASE_PATH) //Ruta para todos usuarios
public class UsuarioController {
    @Autowired
    private UsuarioRest usuarioRest;

    @GetMapping(Variables.USUARIO_GET_ALL)
    public List<Usuario> getUsuarios() {
        return usuarioRest.findAll();
    }

    @GetMapping(Variables.USUARIO_SEARCH)
    public Usuario getUsuario(@PathVariable long id) {
        Optional<Usuario> s = usuarioRest.findById(id);
        return s.orElse(null);
    }

    @GetMapping(Variables.USUARIO_SEARCH_BY_LOGIN)
    public ResponseEntity<Usuario> getUsuario(@PathVariable("nombre") String nombre, @PathVariable("contrasenha") String contrasenha) {
        Usuario usuario = usuarioRest.findByNombreAndContrasenha(nombre, contrasenha);
        if (usuario != null) {
            return ResponseEntity.ok(usuario); // Devuelve el usuario encontrado
        } else {
            return ResponseEntity.notFound().build(); // Devuelve una respuesta 404 Not Found
        }
    }

    @GetMapping(Variables.USUARIO_SEARCH_BY_NOMBRE)
    public ResponseEntity<Usuario> getUsuario(@PathVariable("nombre") String nombre) {
        Usuario usuario = usuarioRest.findByNombre(nombre);
        if (usuario != null) {
            return ResponseEntity.ok(usuario); // Devuelve el usuario encontrado
        } else {
            return ResponseEntity.notFound().build(); // Devuelve una respuesta 404 Not Found
        }
    }


    @PostMapping(Variables.USUARIO_SAVE)
    public ResponseEntity<?> guardarUsuario(@RequestBody Usuario usuario) {
        try {
            usuarioRest.save(usuario);
            return ResponseEntity.ok().body("{\"success\": true}");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\": \"Error al guardar el usuario\"}");
        }
    }

    @PutMapping(Variables.USUARIO_UPDATE)
    public String actualizarUsuario(@PathVariable long id, @RequestBody Usuario Usuario) {
        Usuario s = usuarioRest.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrada"));
        s.setNombre(Usuario.getNombre());
        s.setContrasenha(Usuario.getContrasenha());
        s.setEstado(Usuario.getEstado());
        s.setFoto(Usuario.getFoto());
        usuarioRest.save(s);
        return "Usuario " + id + " actualizada";
    }

    @DeleteMapping(Variables.USUARIO_DELETE)
    public String eliminarUsuario(@PathVariable long id) {
        Usuario s = usuarioRest.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrada"));
        usuarioRest.delete(s);
        return "Usuario " + id + " eliminada";
    }

}