package com.capstone.sjseed.repository;

import com.capstone.sjseed.domain.Plant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlantRepository extends JpaRepository<Plant, Long> {
}
