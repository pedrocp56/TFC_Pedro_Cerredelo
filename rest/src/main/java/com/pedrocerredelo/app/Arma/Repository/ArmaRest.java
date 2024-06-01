package com.pedrocerredelo.app.Arma.Repository;

import com.pedrocerredelo.app.Arma.Model.Arma;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArmaRest extends JpaRepository<Arma, Long> {
}

