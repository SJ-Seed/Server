package com.capstone.sjseed.repository;

import com.capstone.sjseed.domain.PlantData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface PlantDataRepository extends JpaRepository<PlantData, String> {
    void deleteByCreatedAtBefore(LocalDateTime localDateTime);
}
