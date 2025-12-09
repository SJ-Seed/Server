package com.capstone.sjseed.repository;

import com.capstone.sjseed.domain.Member;
import com.capstone.sjseed.domain.Plant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlantRepository extends JpaRepository<Plant, Long> {
    List<Plant> findByPlantId(String plantId);

    List<Plant> findByName(String name);

    long countByMember(Member member);

    boolean existsByNameAndMember(String name, Member member);
}
