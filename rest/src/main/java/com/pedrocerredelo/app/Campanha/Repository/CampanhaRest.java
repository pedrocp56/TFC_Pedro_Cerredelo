package com.pedrocerredelo.app.Campanha.Repository;

import com.pedrocerredelo.app.Campanha.Model.Campanha;
import com.pedrocerredelo.app.Usuario.Model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampanhaRest extends JpaRepository<Campanha, Long> {

    //Campanha fingByNombre(String nombre);
}
