package com.pedrocerredelo.app.Usuario.Repository;

import com.pedrocerredelo.app.Usuario.Model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRest extends JpaRepository<Usuario, Long> {
}
