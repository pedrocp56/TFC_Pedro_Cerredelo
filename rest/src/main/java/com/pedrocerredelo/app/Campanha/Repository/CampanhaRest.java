package com.pedrocerredelo.app.Campanha.Repository;

import com.pedrocerredelo.app.Campanha.Model.Campanha;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampanhaRest extends JpaRepository<Campanha, Long> {
}
