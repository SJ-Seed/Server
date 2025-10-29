package com.capstone.sjseed.repository;

import com.capstone.sjseed.domain.PlantData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PlantDataRepository extends JpaRepository<PlantData, String> {
    void deleteByCreatedAtBefore(LocalDateTime localDateTime);

    PlantData findTopByPlantIdOrderByCreatedAtDesc(String plantId);

    @Query("SELECT p FROM PlantData p WHERE p.plantId = :plantId AND p.createdAt >= :startDate")
    List<PlantData> findRecentTwoWeeks(@Param("plantId") String plantId,
                                       @Param("startDate") LocalDateTime startDate);
}
