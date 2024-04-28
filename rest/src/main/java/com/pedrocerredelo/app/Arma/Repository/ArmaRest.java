package com.pedrocerredelo.app.Arma.Repository;

import com.pedrocerredelo.app.Arma.Model.Arma;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArmaRest extends JpaRepository<Arma, Long> {
    /*
        @Query(Select * from Arma a Where a.Arma_Nombre= ?arma1")
        Optional<Arma> buscarPorNombre(String nombre);
     */

    Optional<Arma> findByNombre(String nombre);
}

