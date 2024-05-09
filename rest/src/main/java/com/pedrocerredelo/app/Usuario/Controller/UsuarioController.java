package com.pedrocerredelo.app.Usuario.Controller;


import com.pedrocerredelo.app.Usuario.Model.Usuario;
import com.pedrocerredelo.app.Usuario.Repository.UsuarioRest;
import com.pedrocerredelo.app.Variables;
import org.springframework.beans.factory.annotation.Autowired;
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
/*
    @GetMapping(Variables.USUARIO_SEARCH_BY_LOGIN)
    public Usuario getUsuario(@RequestBody Map<String, String> login) {
        Usuario s = usuarioRest.findByNombreAndContrasenha(login.get("nombre"), login.get("contrasenha"));
        return s;
    }
*/

    @PostMapping(Variables.USUARIO_SAVE)
    public boolean guardarUsuario(@RequestBody Usuario Usuario) {
        try {
            usuarioRest.save(Usuario);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
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