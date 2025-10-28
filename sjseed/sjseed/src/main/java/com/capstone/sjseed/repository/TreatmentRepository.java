package com.capstone.sjseed.repository;

import com.capstone.sjseed.domain.Member;
import com.capstone.sjseed.domain.Treatment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TreatmentRepository extends JpaRepository<Treatment, Long> {
    List<Treatment> findByMember(Member member);
}
