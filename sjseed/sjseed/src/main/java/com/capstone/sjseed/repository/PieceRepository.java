package com.capstone.sjseed.repository;

import com.capstone.sjseed.domain.Collection;
import com.capstone.sjseed.domain.Piece;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PieceRepository extends JpaRepository<Piece, Long> {
    List<Piece> findByCollection(Collection collection);
}
