package com.capstone.sjseed.repository;

import com.capstone.sjseed.domain.PlantSpecies;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlantSpeciesRepository extends JpaRepository<PlantSpecies, Long> {
    Optional<PlantSpecies> findByCode(String code);

    PlantSpecies findByName(String name);

    boolean existsByName(String name);
}
