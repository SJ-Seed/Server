package com.capstone.sjseed.repository;

import com.capstone.sjseed.domain.Collection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollectionRepository extends JpaRepository<Collection, String> {
}
